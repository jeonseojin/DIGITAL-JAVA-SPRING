package kr.green.spring.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import kr.green.spring.pagination.Criteria;
import kr.green.spring.pagination.PageMaker;
import kr.green.spring.vo.BoardVo;
import kr.green.spring.vo.UserVo;

public interface BoardService {

	ArrayList<BoardVo> getBoardList(Criteria cri);

	BoardVo getBoard(Integer num);

	//return 타입이 없기 때문에 앞에 void가 붙음
	void increaseViews(Integer num);

	void registerBoard(BoardVo board, HttpServletRequest request);

	void updateBoard(BoardVo board, UserVo user);

	void deleteBoard(Integer num, UserVo userVo);

	PageMaker getPageMaker(Criteria cri);
}
