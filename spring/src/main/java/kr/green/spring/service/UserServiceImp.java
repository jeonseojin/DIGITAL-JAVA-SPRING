package kr.green.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.green.spring.dao.UserDao;

//Service라는 Annotation을 추가
@Service
public class UserServiceImp implements UserService {
//
	@Autowired
	private UserDao userDao;
	@Override
	public String getPw(String id) {
		return userDao.getPw(id);
	}
	@Override
	public int getCount() {
		// getCount의 이름을 굳이 맞추지 않아도 괜찮음.
		return userDao.getCount();
	}

}
