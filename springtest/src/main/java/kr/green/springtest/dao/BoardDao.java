package kr.green.springtest.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import kr.green.springtest.pagination.Criteria;
import kr.green.springtest.vo.BoardVo;

public interface BoardDao {

	ArrayList<BoardVo> getBoardList(@Param("cri")Criteria cri);//pagination을 적용할 때 매개변수가 없다가 cri가 추가됨

	BoardVo getBoard(@Param("num")Integer num);

	void insertBoard(@Param("board")BoardVo board);

	void updateBoard(@Param("board")BoardVo board);

	// pagination
	int getTotalCountByBoard(@Param("cri")Criteria cri);

}
