package kr.green.springtest.service;

import kr.green.springtest.vo.UserVo;

public interface UserService {

	UserVo getUser(String string);

	UserVo isUser(UserVo inputUser);

}
