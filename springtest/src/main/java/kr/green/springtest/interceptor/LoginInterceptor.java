package kr.green.springtest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.green.springtest.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public void postHandle(
	    HttpServletRequest request, 
	    HttpServletResponse response, 
	    Object handler, 
	    ModelAndView modelAndView)
	    throws Exception {
	    ModelMap modelMap = modelAndView.getModelMap();
	    UserVo user = (UserVo)modelMap.get("user");

	    if(user != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);
	    }
	}
	/* - LoginInterceptor 클래스 생성 및 메소드 오버라이딩
	 * HandlerInterceptorAdapter 클래스를 상속 받은 LoginInterceptor 클래스 생성
	 * postHandle 메소드를 오버라이딩 함
	 * Controller에서 Model에 유저 정보를 저장하지 않았으면 modelMap.get("user")를 했을 때 null값이 들어가고 있으면 저장한 정보를 가져온다.
	 * 가져온 정보가 null이 아니면 해당 유저 정보를 세션에 저장
	 * */
}
