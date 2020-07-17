package kr.green.springtest.service;

import java.util.ArrayList;

import kr.green.springtest.pagination.Criteria;
import kr.green.springtest.pagination.PageMaker;
import kr.green.springtest.vo.BoardVo;

public interface BoardService {

	ArrayList<BoardVo> getBoardList(Criteria cri);

	BoardVo getBoard(Integer num);

	void insertBoard(BoardVo board);

	BoardVo view(Integer num);

	void updateBoard(BoardVo board);

	void deleteBoard(Integer num);

	PageMaker getPageMakerByBoard(Criteria cri);

}
