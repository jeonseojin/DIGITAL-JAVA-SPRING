package kr.green.spring.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.green.spring.dao.UserDao;
import kr.green.spring.vo.UserVo;

//Service라는 Annotation을 추가
@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDao userDao;
	
	// 회원 가입 시 비밀번호를 암호화하기 위해서 생성
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	// signup post의 메서드
	@Override
	public boolean signup(UserVo user) {
		System.out.println(user);
		if(user == null) return false;
		// 예외처리
		if(user.getId()== null || user.getId().length() == 0) return false;
		if(user.getPw()== null || user.getPw().length() == 0) return false;
		if(user.getEmail()== null || user.getEmail().length() == 0) return false;
		if(user.getGender()==null)
			user.setGender("male");
		if(!user.getGender().equals("male") && !user.getGender().equals("female")) return false;
		
		//id가 있는경우
		if(userDao.getUser(user.getId())!=null)
			return false;
		
		// 비밀번호 암호화
		String encodePw = passwordEncoder.encode(user.getPw());
		user.setPw(encodePw);
		
		// 예외처리와 id가 있는 경우를 제외하고 넘어온 경우 회원가입을 진행
		userDao.insertUser(user);
		return true;
	}

	// 로그인
	@Override
	public UserVo isSignin(UserVo user) {
//있는 아이디인지 확인하여 정보를 가져오기
		UserVo dbUser = userDao.getUser(user.getId());
/*비밀번호 확인을 하기 위해서 Encoder에서 제공하는 matches를 이용하여 입력한 비밀번호와 DB에 저장되어 있는 암호화된 비밀번호를
비교하여 서로 같은지를 확인*/
		if(dbUser != null && passwordEncoder.matches(user.getPw(), dbUser.getPw()))
			return dbUser;
		return null;
	}

	// 로그인한 회원의 정보를 가져오기 위해서 사용되며 자주 사용하기 때문에 메서드로 생성
	@Override
	public UserVo getUser(HttpServletRequest request) {
		return (UserVo)request.getSession().getAttribute("user");
	}

	@Override
	public UserVo getUser(String id) {
		return userDao.getUser(id);
	}

	// 비밀번호를 찾기
	@Override
	public void newPw(String id, String newPw) {
		// 요청한 아이디에 회원 정보를 가져옴
	    UserVo user = getUser(id.trim()/*양끝의공백을 제거하는 것*/);
		String encodePw = passwordEncoder.encode(newPw);
		if(user == null) return;//유저가 없을 경우 
		user.setPw(encodePw);
		userDao.updatePw(user);//dao에 회원정보를 주고 업뎃 시킴
	}
	


}
