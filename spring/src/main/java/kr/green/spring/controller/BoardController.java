package kr.green.spring.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import kr.green.spring.pagination.Criteria;
import kr.green.spring.pagination.PageMaker;
import kr.green.spring.service.BoardService;
import kr.green.spring.service.UserService;
import kr.green.spring.utils.UploadFileUtils;
import kr.green.spring.vo.BoardVo;
import kr.green.spring.vo.UserVo;

@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	
	private String uploadPath="D:\\git\\uploadfiles";
			
	//list.jsp와 연결
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public ModelAndView boardListGet(ModelAndView mv, Criteria cri/* String type, String search를 따로 입력하지 않고 Criteria에 추가함*/) {
		logger.info("URI:/board/list");
		//  현재페이지의 정보를 줄테니 이전버튼/다음버튼을 활성할지 등의 일을 결정
		PageMaker pm = boardService.getPageMaker(cri);
		mv.setViewName("/board/list");
		// 여러개가 올  수 있으니깐 Array를 사용
		ArrayList<BoardVo> list;
		list = boardService.getBoardList(cri);
		mv.addObject("list", list);
		mv.addObject("pm", pm);
		return mv;
	}
	// detail.jsp와 연결
	@RequestMapping(value = "/board/detail", method = RequestMethod.GET)
	public ModelAndView boardDetailGet(ModelAndView mv, Integer num, Criteria cri) {// 정수는 integer를 이용하여 입력
		// 이유 : 사용자가 정상적이지 않는 경로로 올 경우에도 처리를 하기 위해서 integer를 사용 int로 할 경우 null값이 입력되지 않고 에러가 발생 
		logger.info("URI:/board/detail");
		mv.setViewName("/board/detail");
		BoardVo board = null;//board를 null로 초기화
		if(num!=null) {
			// 컨트롤러의 입장에서 번호가 있을 경우 서비스에 넘겨주고 넘겨준 num을 찾아오라고 시킴 
			board = boardService.getBoard(num);
			mv.addObject("board", board);
			if(board != null) {
				// 조회수를 증가시키기 위해서 사용하는 코드
				boardService.increaseViews(num);
				board.setViews(board.getViews()+1);
			}
		}
		mv.addObject("cri", cri);
		return mv;
	}
	
	// register.jsp와 연결 (등록)
	@RequestMapping(value = "/board/register", method = RequestMethod.GET)
	public ModelAndView boardRegisterGet(ModelAndView mv) {
		logger.info("URI:/board/register:GET");
		mv.setViewName("/board/register");
		return mv;
	}
	// form태그의 post를 입력했기 때문에 등록버튼을 클릭했을 경우 POST를 찾아서 변경한다.
	@RequestMapping(value = "/board/register", method = RequestMethod.POST)
	public ModelAndView boardRegisterPost(ModelAndView mv, BoardVo board,HttpServletRequest request,MultipartFile file2) throws IOException, Exception {
		//String title, String content를 사용해도 가능하지만 데이터가 늘어났을때 관리하기 힘들다.
		// BoardVo board로 입력하면 해당 BoardVo에 같은 객체가 존재하면 찾아서 등록하게 된다.
		logger.info("URI:/board/register:POST");
		// redirect를 입력해야 해당 위치로 변경된다.
		mv.setViewName("redirect:/board/list");
		
		/* HttpServletRequest request 개발자 도구에서 readonly를 지우고 다른 아이디를 입력할 경우에도 등록이 되는 경우를 방지하기 위해서 사용*/
		String fileName =UploadFileUtils.uploadFile(uploadPath, file2.getOriginalFilename(),file2.getBytes());
				board.setFile(fileName);

		boardService.registerBoard(board,request);
		return mv;
	}
	
	// detail.jsp의 수정을 누르면 modify.jsp와 연결
	@RequestMapping(value = "/board/modify", method = RequestMethod.GET)
	public ModelAndView boardModifyGet(ModelAndView mv, Integer num,HttpServletRequest request) {
		logger.info("URI:/board/modify:GET");
		mv.setViewName("/board/modify");
		System.out.println(num);
		BoardVo board=null;
		UserVo user=userService.getUser(request);
		if(num!=null) {
			// 컨트롤러의 입장에서 번호가 있을 경우 서비스에 넘겨주고 넘겨준 num을 찾아오라고 시킴 
			board = boardService.getBoard(num);
			if(user == null || !board.getWriter().equals(user.getId()))
				mv.setViewName("redirect:/baord/list");
		}
		// num을 통해서 찾아온 정보를 modify.jsp에 붙여넣음
		// addObject가 if문 내에 있으면 초기값이 null값이 들어가서 진행되는 것이고 밖에 있을 경우에는 다른값이 들어갔다가 변경되는 것이라 상관은 없다.
		mv.addObject("board", board);
		return mv;
	}
	// modify.jsp에 있는 수정하기를 누르면 정보가 수정되고 list.jsp로 돌아가게 연결 
	@RequestMapping(value = "/board/modify", method = RequestMethod.POST)
	public ModelAndView boardModifyPost(ModelAndView mv, BoardVo board,HttpServletRequest request, MultipartFile file2) throws IOException, Exception {
		logger.info("URI:/board/modify:Post");
		mv.setViewName("redirect:/board/list");
		// 새로운 정보를 boardService에 넘겨줌
		UserVo user =userService.getUser(request);
		
		//수정에서 첨부파일 추가/삭제 작업
		if(file2.getOriginalFilename().length() != 0) {
			String fileName = UploadFileUtils.uploadFile(uploadPath, 
								file2.getOriginalFilename(),file2.getBytes());
			board.setFile(fileName);
		}else if(board.getFile().length() == 0){
			board.setFile(null);
		}
		
		boardService.updateBoard(board,user);
		System.out.println(board);
		System.out.println(file2.getOriginalFilename());
		return mv;
	}
	
	// modify.jsp에 있는 삭제버튼을 클릭하였을때
	@RequestMapping(value = "/board/delete", method = RequestMethod.GET)
	public ModelAndView boardDeleteGet(ModelAndView mv, Integer num,HttpServletRequest request) {
		logger.info("URI:/board/delete:GET");
		mv.setViewName("redirect:/board/list");
		boardService.deleteBoard(num, userService.getUser(request));// 서비스에게 일을 완전히 시킴
		
		return mv;
	}

	@RequestMapping(value = "/board/like2")
	@ResponseBody
	public Map<Object, Object> boardLike(@RequestBody String num,HttpServletRequest r){
		System.out.println(num);
	    Map<Object, Object> map = new HashMap<Object, Object>();
	    UserVo user = userService.getUser(r);
	    if(user==null) {
	    	map.put("isUser",false);
	    }else {
	    	map.put("isUser",true);
	    	int like = boardService.updateLike(num,user.getId());
	    	map.put("like",like);
	    }
	    return map;
	}
	
	// 업로드된 파일 다운로드 테스트에서 코드를 복사해옴
	@ResponseBody
	@RequestMapping("/board/download")
	public ResponseEntity<byte[]> downloadFile(String fileName)throws Exception{
	    InputStream in = null;
	    ResponseEntity<byte[]> entity = null;
	    try{
	        HttpHeaders headers = new HttpHeaders();
	        in = new FileInputStream(uploadPath+fileName);

	        fileName = fileName.substring(fileName.indexOf("_")+1);
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.add("Content-Disposition",  "attachment; filename=\"" 
				+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
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
