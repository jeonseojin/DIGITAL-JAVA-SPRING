package kr.green.springtest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	/* preHandle 메소드 오버라이딩
	 * 	- 세션에 user 정보를 가져와서 null값이면 로그인 화면으로 보냄
	 * 	- 정보가 있으면 그냥 넘어가도록 함*/
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if(user == null) {
			response.sendRedirect(request.getContextPath()+"/");
			return false;	/*controller에 가지 않고 바로 실행하라는 의미*/
		}
		return true;
	}
}
