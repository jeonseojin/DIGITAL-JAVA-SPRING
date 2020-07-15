package kr.green.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.springtest.dao.UserDao;
import kr.green.springtest.vo.UserVo;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public UserVo getUser(String id) {
		return userDao.getUser(id);
	}

	@Override
	public UserVo isUser(UserVo inputUser) {
		
		/* 일반적으로 로그인 과정은 DB에서 아이디와 일치하는 정보를 가져와서 입력받은 아이디와 가져온 정보 중 비밀번호를
		 * 비교하여 로그인은 결정한다. 
		 * => 쿼리로 비밀번호를 비교하지 않는다.
		 * => 입력한 비밀번호는 실제 비밀번호이고 DB에 저장된 비밀 번호는 암호화된 비밀번호이기 때문에 쿼리로 직접 비교할 수 없다.
		 * => 다른 이유로는 pw에 이상한 작업을 하면 로그인이 될 수 있기 때문
		 * 		(블라인드 SQL 인젝션이라고 함)
		 * */
		UserVo user = userDao.getUser(inputUser.getId());
		// 암호화를 하지 않았기 때문에 equals로 비교가 가능하지만 암호화를 한 경우에는  다른 방법을 사용해야한다.
		if(user == null)
			// 유저가 없을 경우
			return null;
		if(user.getPw().equals(inputUser.getPw())) {
			return user;
		}
		return null;
	}
}
