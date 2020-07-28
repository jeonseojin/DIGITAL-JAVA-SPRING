package kr.green.springtest.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.green.springtest.service.UserService;
import kr.green.springtest.vo.UserVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})// get과 post를 한곳에서 처리하기 위해서 작성
	public ModelAndView home(ModelAndView mv, UserVo inputUser) {
		logger.info("URI:/");
		mv.setViewName("/main/home");
		UserVo user = userService.isUser(inputUser);
		if(user != null) {
			mv.setViewName("redirect:/board/list");
			mv.addObject("user"/*logininterceptor에 있는객체로 서로 연결*/, user);
		}
		return mv;
	}
	
	//signup과 연결되는 메서드
	@RequestMapping(value = "/user/signup", method = {RequestMethod.GET})
	public ModelAndView Signup(ModelAndView mv) {
		logger.info("URI:/signup:GET");
		mv.setViewName("/main/signup");
		return mv;
	}
	@RequestMapping(value = "/user/signup", method = {RequestMethod.POST})
	public ModelAndView SignupPost(ModelAndView mv,UserVo user) {
		logger.info("URI:/signup");
		if(userService.signup(user))
			mv.setViewName("redirect:/");
		else {
			mv.setViewName("redirect:/user/signup");
			mv.addObject("user", user);
		}
		return mv;
	}
	
	//signout과 연결
	@RequestMapping(value = "/user/signout", method = {RequestMethod.GET})
	public ModelAndView Signout(ModelAndView mv,HttpServletRequest request) {
		logger.info("URI:/signout:GET");
		mv.setViewName("redirect:/");
		/* Session에 로그인 정보가 있는지 없는지로 로그인 여부를 확인하기 때문에 
		 * 매개변수에 HttpServletRequest를 추가해준 후에
		 * request.getSession()/removeAttribure("user");로 셋션에서 지우면 로그아웃이 가능하다.*/
		request.getSession().removeAttribute("user");
		return mv;
	}
	
	//아이디중복확인
	@RequestMapping(value ="/idCheck")
	@ResponseBody
	public Map<Object, Object> idcheck(@RequestBody String id){

	    Map<Object, Object> map = new HashMap<Object, Object>();
	    map.put("res",userService.getUser(id)==null);
	    return map;
	}
	

}
