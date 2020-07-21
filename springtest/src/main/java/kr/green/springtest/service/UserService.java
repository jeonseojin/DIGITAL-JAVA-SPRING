package kr.green.springtest.service;

import javax.servlet.http.HttpServletRequest;

import kr.green.springtest.vo.UserVo;

public interface UserService {

	UserVo getUser(String string);

	UserVo isUser(UserVo inputUser);

	boolean signup(UserVo user);

	// object -> UserVo 변경
	UserVo getUser(HttpServletRequest r);

}
