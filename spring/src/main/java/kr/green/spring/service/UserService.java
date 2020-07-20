package kr.green.spring.service;

import javax.servlet.http.HttpServletRequest;

import kr.green.spring.vo.UserVo;

public interface UserService {

	boolean signup(UserVo user);

	UserVo isSignin(UserVo user);

	UserVo getUser(HttpServletRequest request);

}
