package kr.green.springtest.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.green.springtest.pagination.Criteria;
import kr.green.springtest.pagination.PageMaker;
import kr.green.springtest.service.BoardService;
import kr.green.springtest.service.UserService;
import kr.green.springtest.utils.UploadFileUtils;
import kr.green.springtest.vo.BoardVo;
import kr.green.springtest.vo.UserVo;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	@Autowired
	UserService userService;
	
	private String uploadPath ="D:\\git\\uploadfiles";
	
	
	// list 연결
	@RequestMapping(value ="/board/list", method= RequestMethod.GET)
	public ModelAndView boardListGet(ModelAndView mv, Criteria cri) {
		mv.setViewName("/board/list");
		ArrayList<BoardVo> list = boardService.getBoardList(cri);//순서를 보장하기 위해서 list로 작성
		mv.addObject("list", list);
		
		PageMaker pm /*화면으로 PageMaker를 보내주기 위해서는 controller가 값을 가지고 있어야한다.*/
			= boardService.getPageMakerByBoard(cri);
				// 현재 알고 있는 정도가 cri밖에 없기 때문에 boardService에게 cri정보를 넘겨주고 다른 정보를 받아오기 위해서 설정
		mv.addObject("pm", pm);
		return mv;	
	}
	
	// detail 연결
	@RequestMapping(value ="/board/detail", method=RequestMethod.GET)
	public ModelAndView boardDetailGet(ModelAndView mv,Integer num, Criteria cri) {
		mv.setViewName("/board/detail");
		BoardVo board = boardService.view(num);
		mv.addObject("board", board);
		mv.addObject("cri", cri);
		return mv;
	}
	
	// register 연결
	@RequestMapping(value ="/board/register", method=RequestMethod.GET)
	public ModelAndView boardRegisterGet(ModelAndView mv) {
		mv.setViewName("/board/register");
		
		return mv;
	}
	@RequestMapping(value ="/board/register", method=RequestMethod.POST)
	public ModelAndView boardRegisterPost(ModelAndView mv, BoardVo board,HttpServletRequest r,  MultipartFile file2) throws IOException, Exception {
		mv.setViewName("redirect:/board/list");
		//작성자는 로그인한 정보를 가지고 와서 입력하기위한 코드
		board.setWriter(userService.getUser(r).getId());
		String file = UploadFileUtils.uploadFile(uploadPath, file2.getOriginalFilename(),file2.getBytes());
		board.setFile(file);
		boardService.insertBoard(board);
		return mv;
	}
	
	//modify 연결
	@RequestMapping(value ="/board/modify", method= RequestMethod.GET)
	public ModelAndView boardModifyGet(ModelAndView mv, Integer num,HttpServletRequest r) {
		mv.setViewName("/board/modify");
		BoardVo board = boardService.getBoard(num);
		/*작성자만 수정 페이지로 갈 수 있도록 설정하는 코드*/
		UserVo user = userService.getUser(r);// 1. 유저의 정보를 가져옴
		//2. user.getId와 board.getWriter를 비교
		if(board == null || !user.getId().equals(board.getWriter()))
			mv.setViewName("redirect:/board/list");// board가 비어있거나 id와 writer가 다른 경우 board/list로 보내는 코드
		
		mv.addObject("board", board);
		return mv;
	}
	@RequestMapping(value ="/board/modify", method= RequestMethod.POST)
	public ModelAndView boardModifyPost(ModelAndView mv,BoardVo board,HttpServletRequest r,MultipartFile file2) throws IOException, Exception  {
		mv.setViewName("redirect:/board/list");
		//post에서도 처리
		board.setWriter(userService.getUser(r).getId());	// -> 내 게시물을 수정하려고 할때
		
		if(!file2.getOriginalFilename().equals("")) {
			String fileName = UploadFileUtils.uploadFile(uploadPath, file2.getOriginalFilename(), file2.getBytes());
			board.setFile(fileName);
		}else if(board.getFile()==null || board.getFile().equals("")) {
			// 빈문자열을 무조건 null로 변경해 주는 이유는 첨부파일이 보여지는 부분에서 c:if를 board.file이 null이 아닐 경우에만 보이지 않도록 설정했기 때문에
			board.setFile(null);
		}
		
		boardService.updateBoard(board);
		return mv;
	}
	
	//delete
	@RequestMapping(value ="/board/delete", method= RequestMethod.GET)
	public ModelAndView boardDeleteGet(ModelAndView mv, Integer num/*delete?num=${board.num}를 사용하기 위해서 num을 가져와야함*/,HttpServletRequest r) {
		mv.setViewName("redirect:/board/list");
		boardService.deleteBoard(num, userService.getUser(r));
		return mv;
	}
	
	//좋아요 증가
	@RequestMapping(value ="/board/up", method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> boardUp(@RequestBody int num, HttpServletRequest r){
	    Map<Object, Object> map = new HashMap<Object, Object>();
	    //현재 로그인 중인 유저 정보
	    UserVo user = userService.getUser(r);
	    if(user == null) {
	    	map.put("isUser",false);
	    }else {
	    	map.put("isUser",true);
	    	int up = boardService.updateUp(num, user.getId());
	    	map.put("up",up);
	    }
	    return map;
	}
	@ResponseBody
	
	@RequestMapping(value="/board/download")
	public ResponseEntity<byte[]> downloadFile(String fileName)throws Exception{
	    InputStream in = null;
	    ResponseEntity<byte[]> entity = null;
	    try{
	    	//HttpHeader 객체 생성
	        HttpHeaders headers = new HttpHeaders();
	        //다운로드할 파일을 읽어옴
	        in = new FileInputStream(uploadPath+fileName);
	        //다운로드 시 저장할 파일명
	        fileName = fileName.substring(fileName.indexOf("_")+1);
	        //헤더에 컨텐츠 타입을 설정
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        //해더 정보를 추가
	        headers.add("Content-Disposition",  "attachment; filename=\"" 
				+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
	        //ResponseEntity 객체 생성, 전송할 파일, 해더 정보, 해더 상태
	        entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
	    }catch(Exception e) {
	        e.printStackTrace();
	        entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
	    }finally {
	        in.close();
	    }
	    return entity;
	}
	
	
}
