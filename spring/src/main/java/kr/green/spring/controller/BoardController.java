package kr.green.spring.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.green.spring.service.BoardService;
import kr.green.spring.service.UserService;
import kr.green.spring.vo.BoardVo;

@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private BoardService boardService;
	
	//list.jsp와 연결
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public ModelAndView boardListGet(ModelAndView mv) {
		logger.info("URI:/board/list");
		mv.setViewName("/board/list");
		// 여러개가 올  수 있으니깐 Array를 사용
		ArrayList<BoardVo> list;
		list = boardService.getBoardList();
		mv.addObject("list", list);
		return mv;
	}
	// detail.jsp와 연결
	@RequestMapping(value = "/board/detail", method = RequestMethod.GET)
	public ModelAndView boardDetailGet(ModelAndView mv, Integer num) {// 정수는 integer를 이용하여 입력
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
		return mv;
	}
	
	// register.jsp와 연결
	@RequestMapping(value = "/board/register", method = RequestMethod.GET)
	public ModelAndView boardRegisterGet(ModelAndView mv) {
		logger.info("URI:/board/register:GET");
		mv.setViewName("/board/register");
		
		return mv;
	}
	// form태그의 post를 입력했기 때문에 등록버튼을 클릭했을 경우 POST를 찾아서 변경한다.
	@RequestMapping(value = "/board/register", method = RequestMethod.POST)
	public ModelAndView boardRegisterPost(ModelAndView mv, BoardVo board) {
		//String title, String content를 사용해도 가능하지만 데이터가 늘어났을때 관리하기 힘들다.
		// BoardVo board로 입력하면 해당 BoardVo에 같은 객체가 존재하면 찾아서 등록하게 된다.
		logger.info("URI:/board/register:POST");
		// redirect를 입력해야 해당 위치로 변경된다.
		mv.setViewName("redirect:/board/list");
		System.out.println(board);
		boardService.registerBoard(board);
		return mv;
	}
	
	// detail.jsp의 수정을 누르면 modify.jsp와 연결
	@RequestMapping(value = "/board/modify", method = RequestMethod.GET)
	public ModelAndView boardModifyGet(ModelAndView mv, Integer num) {
		logger.info("URI:/board/modify:GET");
		mv.setViewName("/board/modify");
		System.out.println(num);
		BoardVo board=null;
		if(num!=null) {
			// 컨트롤러의 입장에서 번호가 있을 경우 서비스에 넘겨주고 넘겨준 num을 찾아오라고 시킴 
			board = boardService.getBoard(num);
		}
		// num을 통해서 찾아온 정보를 modify.jsp에 붙여넣음
		// addObject가 if문 내에 있으면 초기값이 null값이 들어가서 진행되는 것이고 밖에 있을 경우에는 다른값이 들어갔다가 변경되는 것이라 상관은 없다.
		mv.addObject("board", board);
		return mv;
	}
	// modify.jsp에 있는 수정하기를 누르면 정보가 수정되고 list.jsp로 돌아가게 연결 
	@RequestMapping(value = "/board/modify", method = RequestMethod.POST)
	public ModelAndView boardModifyPost(ModelAndView mv, BoardVo board) {
		logger.info("URI:/board/modify:Post");
		mv.setViewName("redirect:/board/list");
		// 새로운 정보를 boardService에 넘겨줌
		boardService.updateBoard(board);
		return mv;
	}
	
	// modify.jsp에 있는 삭제버튼을 클릭하였을때
	@RequestMapping(value = "/board/delete", method = RequestMethod.GET)
	public ModelAndView boardDeleteGet(ModelAndView mv, Integer num) {
		logger.info("URI:/board/delete:GET");
		mv.setViewName("redirect:/board/list");
		boardService.deleteBoard(num);// 서비스에게 일을 완전히 시킴
		
		return mv;
	}
	

}
