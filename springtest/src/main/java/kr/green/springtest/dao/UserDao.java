package kr.green.springtest.dao;

import org.apache.ibatis.annotations.Param;
import kr.green.springtest.vo.UserVo;

public interface UserDao {
	UserVo getUser(@Param("id")String id);
}
