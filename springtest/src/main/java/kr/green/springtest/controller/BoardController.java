package kr.green.springtest.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.green.springtest.pagination.Criteria;
import kr.green.springtest.pagination.PageMaker;
import kr.green.springtest.service.BoardService;
import kr.green.springtest.vo.BoardVo;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
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
	public ModelAndView boardRegisterPost(ModelAndView mv, BoardVo board) {
		mv.setViewName("redirect:/board/list");
		boardService.insertBoard(board);
		return mv;
	}
	
	//modify 연결
	@RequestMapping(value ="/board/modify", method= RequestMethod.GET)
	public ModelAndView boardModifyGet(ModelAndView mv, Integer num) {
		mv.setViewName("/board/modify");
		BoardVo board = boardService.getBoard(num);
		mv.addObject("board", board);
		return mv;
	}
	@RequestMapping(value ="/board/modify", method= RequestMethod.POST)
	public ModelAndView boardModifyPost(ModelAndView mv,BoardVo board) {
		mv.setViewName("redirect:/board/list");
		boardService.updateBoard(board);
		return mv;
	}
	
	//delete
	@RequestMapping(value ="/board/delete", method= RequestMethod.GET)
	public ModelAndView boardDeleteGet(ModelAndView mv, Integer num/*delete?num=${board.num}를 사용하기 위해서 num을 가져와야함*/) {
		mv.setViewName("redirect:/board/list");
		boardService.deleteBoard(num);
		return mv;
	}
	
}
