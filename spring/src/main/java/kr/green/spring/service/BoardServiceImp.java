package kr.green.spring.service;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.spring.dao.BoardDao;
import kr.green.spring.pagination.Criteria;
import kr.green.spring.pagination.PageMaker;
import kr.green.spring.vo.BoardVo;
import kr.green.spring.vo.UserVo;

@Service
public class BoardServiceImp implements BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public ArrayList<BoardVo> getBoardList(Criteria cri) {
		return boardDao.getBoardList(cri);
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
	public void registerBoard(BoardVo board, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo user = (UserVo)session.getAttribute("user");
		if(user == null)	return;
		board.setWriter(user.getId());//악의적으로 id를 변경해도 로그인한 아이디로 입력되도록 설정
		boardDao.registerBoard(board);//Dao에서는 따로 처리하지 않아도 됨
		
	}

	@Override
	public void updateBoard(BoardVo board,UserVo user) {
		board.setWriter(user.getId());//수정할 때 작성자의 아이디로만 수정되도록 설정
		// isDel의 값이 없기때문에 N를 넣어줌
		board.setIsDel('N');
		boardDao.updateBoard(board);
		
	}


	@Override
	public void deleteBoard(Integer num, UserVo userVo) {
		// controller에서 적용해서 확인 후 서비스에 기능을 맡김.
		if(num!=null && userVo != null) {
			BoardVo board = boardDao.getBoard(num);
				if(board!=null && board.getWriter().equals(userVo.getId())) {
					board.setIsDel('Y');
					board.setDelDate(new Date());
					boardDao.updateBoard(board);
					// 위의 내용은 isDel을 N->Y로 변경하는 과정
				}
		}
		
	}

	
	@Override
	public PageMaker getPageMaker(Criteria cri) {
		PageMaker pm = new PageMaker();
		int totalCount = boardDao.getTotalCount(cri);
		pm.setCriteria(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}



}
