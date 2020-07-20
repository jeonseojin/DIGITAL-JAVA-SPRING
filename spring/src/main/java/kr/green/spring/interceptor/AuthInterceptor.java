package kr.green.spring.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;

	import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
			HttpSession session = request.getSession();
		Object user = session.getAttribute("user");//UserVo로 확인하지 않아도 로그인상태인지만을 확인하기 떄문에 상관없음
		if(user == null) {
			response.sendRedirect(request.getContextPath()+"/");
		}
		return true;
	}
}

