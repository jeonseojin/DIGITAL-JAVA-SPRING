package kr.green.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.green.spring.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired//Autowired를 쓰지 의미는 객체를 생성하는 의미를 갖고 있기 때문에 사용
	private UserService userService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv) {
		logger.info("URI:/");
		mv.setViewName("/main/home");
		//tiles의 입장으론 /*/*을 의미
		return mv;
	}
//	샘플
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView testGet(ModelAndView mv, String id, String pw) {
		logger.info("URI:/test");
		mv.setViewName("/main/test");
		mv.addObject("title", "테스트");
		logger.info("전송된 아이디 : "+id);
		logger.info("전송된 비밀번호 : "+pw);
		String userPw = userService.getPw(id);
		logger.info("조회된 비밀번호 : "+userPw);
		// 현재 BD에 몇명이 있는지 조회하는 코드를 작성
		// select count(*) from user; 을 참고해서 작성 가능.
		// (현재 등록된 회원수=)int로 userService에서 자동으로 내려 받겠다는 의미 .getCount라는 메서드를 통해서
		int count = userService.getCount();
		logger.info("조회된 회원수 : " +count);
		return mv;
	}
	
}
