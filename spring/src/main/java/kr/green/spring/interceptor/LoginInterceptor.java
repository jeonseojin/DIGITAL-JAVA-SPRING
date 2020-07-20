package kr.green.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.green.spring.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(
	    HttpServletRequest request, 
	    HttpServletResponse response, 
	    Object handler, 
	    ModelAndView modelAndView)
	    throws Exception {
	    ModelMap modelMap = modelAndView.getModelMap();
	    UserVo user = (UserVo)modelMap.get("user");
	    /* controller에서 addObject로 보낼때 이름이 user인 정보를 가져옴
	     * controller에서 보내는 이름을 a로 수정한다면 여기에서도 a로 수정해야함*/
	    
	    if(user != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);
	        /* 세션에서 user로 저장하기 때문에 jsp에서는 ${user}로 사용할 수 있다
	         * 여기에서 이름을 b로 변경할 경우 jsp에서도 ${b}로 사용해야 한다.
	        */
	    }
	}
	
}
