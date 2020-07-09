package kr.green.spring.dao;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
						//	작성한 id를 현재 id로 사용한다는 의미
	public String getPw(@Param("id")String id);

	public int getCount();
}
