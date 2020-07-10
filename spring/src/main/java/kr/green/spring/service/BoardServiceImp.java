package kr.green.spring.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.spring.dao.BoardDao;
import kr.green.spring.vo.BoardVo;

@Service
public class BoardServiceImp implements BoardService {
	@Autowired
		private BoardDao boardDao;
	
	
	@Override
	public ArrayList<BoardVo> getBoardList() {
		return boardDao.getBoardList();
	}


	@Override
	public BoardVo getBoard(Integer num) {
		return boardDao.getBoard(num);
		
	}


	@Override
	public void increaseViews(Integer num) {
		boardDao.increaseViews(num);
		
	}


	@Override
	public void registerBoard(BoardVo board) {
		boardDao.registerBoard(board);
	}


	@Override
	public void updateBoard(BoardVo board) {
		// idDel의 값이 없기때문에 N를 넣어줌
		board.setIsDel('N');
		boardDao.updateBoard(board);
		
	}


	@Override
	public void deleteBoard(Integer num) {
		// controller에서 적용해서 확인 후 서비스에 기능을 맡김.
		if(num!=null) {
			BoardVo board = boardDao.getBoard(num);
				if(board!=null) {
					board.setIsDel('Y');
					board.setDelDate(new Date());
					boardDao.updateBoard(board);
					// 위의 내용은 isDel을 N->Y로 변경하는 과정
				}
		}
		
	}
}
