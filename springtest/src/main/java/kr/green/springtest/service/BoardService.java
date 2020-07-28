package kr.green.springtest.service;

import java.util.ArrayList;

import kr.green.springtest.pagination.Criteria;
import kr.green.springtest.pagination.PageMaker;
import kr.green.springtest.vo.BoardVo;
import kr.green.springtest.vo.UserVo;

public interface BoardService {

	ArrayList<BoardVo> getBoardList(Criteria cri);

	BoardVo getBoard(Integer num);

	void insertBoard(BoardVo board);

	BoardVo view(Integer num);

	void updateBoard(BoardVo board);

	void deleteBoard(Integer num, UserVo userVo);// -> UserVo를 추가

	PageMaker getPageMakerByBoard(Criteria cri);

	int updateUp(int num, String id);

}
