package kr.green.springtest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	//암호화
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override//로그인 관련
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
		if(user == null)// 유저가 없을 경우
			return null;
		/* 암호화를 하지 않았던 아래의 코드를 이용하여
		 * if(user.getPw().equals(inputUser.getPw()))
		 * 암호화 코드인 matches를 이용하여 변경해줌
		*/
		if(user != null && passwordEncoder.matches(inputUser.getPw(), user.getPw())){
			return user;
		}
		return null;
	}

	// signup POST의 메서드
	@Override
	public boolean signup(UserVo user) {
		if(user==null) return false; // 저장되어있는 유저의 값이 없을때
		// 로그인 예외처리
		//중복아이디
		if(userDao.getUser(user.getId()) != null || user.getId().length()==0)//중복된 아이디이거나 공백일 경우
			return false;
		
		//비밀번호 체크
		if(user.getPw() == null || user.getPw().length()==0)
			return false;
		
		// 이메일 체크
		if(user.getEmail() == null
				|| user.getEmail().length() == 0 
				|| !user.getEmail().contains("@"))
			return false;
		
		//성별 체크
		if(user.getGender() == null || user.getGender().length() == 0)
			user.setGender("male");
		user.setAuth("USER");
		user.setIsDel("N");
		
		//비밀번호 암호화
		String encodePw = passwordEncoder.encode(user.getPw());
		user.setPw(encodePw);
		userDao.insertUser(user);
		return true;
	}

	@Override
	public UserVo getUser(HttpServletRequest r) {
		return (UserVo)r.getSession().getAttribute("user");
	}
}
