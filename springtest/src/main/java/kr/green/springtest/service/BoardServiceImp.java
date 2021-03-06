package kr.green.springtest.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.springtest.dao.BoardDao;
import kr.green.springtest.pagination.Criteria;
import kr.green.springtest.pagination.PageMaker;
import kr.green.springtest.vo.BoardVo;
import kr.green.springtest.vo.UserVo;

@Service
public class BoardServiceImp implements BoardService {

	/* @Autowired : 의존 객체의 "타입"에 해당하는 빈(bean 객체)을 찾아서 자동주입 */
	@Autowired
	private BoardDao boardDao;

	@Override
	public ArrayList<BoardVo> getBoardList(Criteria cri) {
		return boardDao.getBoardList(cri);
	}

	@Override
	public BoardVo getBoard(Integer num) {
		if(num==null)	return null;
		return boardDao.getBoard(num);
	}


	@Override
	public void insertBoard(BoardVo board) {
		boardDao.insertBoard(board);
		
	}

	@Override
	public BoardVo view(Integer num) {
		// getBoard+조회수
		BoardVo board = getBoard(num);
		if(board != null) {
			// board가 null이 아닐때 작동
			board.setViews(board.getViews()+1);
			// 현재 갖고 있는 조회수를 가지고 와서 거기에 +1을 하여 Dao에게 넘겨줌
			boardDao.updateBoard(board);
			
			//Dao에게 업데이트하라고 일을 시킴
		}
		return board;
	}

	@Override
	public void updateBoard(BoardVo board) {
		board.setIsDel('N');
		boardDao.updateBoard(board);		
	}

	//delete controller와 연결
	@Override
	public void deleteBoard(Integer num, UserVo user) {
		// 메소드의 재사용성을 높이기 위해서 만들어 놓았던 메소드들을 이용하여 삭제
		BoardVo board = boardDao.getBoard(num);
		if(board==null)
			return;
		if(!board.getWriter().equals(user.getId()))// get방식으로 처리하기 때문에 해당 코드를 추가해서 예외처리를 해줌
			return;
		board.setIsDel('Y');
		board.setDelDate(new Date());//board가 가지고 있는 DelDate의 값을 현재 시간으로 설정하는 것
		boardDao.updateBoard(board);
	}

	@Override
	public PageMaker getPageMakerByBoard(Criteria cri) {
		/* 1. return을 PageMaker로 해야하기 때문에 새로만듬
		 * 2. setCri와 pm.setTotalCount를 생성
		 * 3. 새로 생성하지 않고setTotalCount에 바로 boardDao를 통해서 연결받음 boardDao.getTotalCountByBoard()
		 */
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		pm.setTotalCount(boardDao.getTotalCountByBoard(cri));
		return pm;
	}

	@Override
	public int updateUp(int num, String id) {
		/*추천 테이블에서 게시글 번호와 아이디가 일치하는 값이 있는지 검색
			이미 추천했다면 -1을 리턴 if(xxxx) return -1;*/
		if(boardDao.selectUp(num,id) != 0) return -1;
		//추천 테이블에 추천을 등록
		boardDao.insertUp(num,id);
		//게시글의 추천수만 업데이트
		boardDao.updateBoardByUp(num);
		//게시글 정보를 가져옴
		BoardVo board = boardDao.getBoard(num);
		return board.getUp();
	}




}
