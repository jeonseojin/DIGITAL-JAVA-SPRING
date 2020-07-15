package kr.green.springtest.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		mv.addObject("id", inputUser.getId());
		// imp에서 로그인에 실패할 경우에 알람이 뜰 수 있도록 스크립트를 설정함
		// user가 없을 경우 false가 되기 때문에 알림창이 뜬다.
		if(user == null) {
			mv.addObject("isLogin", false);
		}
		return mv;
	}
	
	

}
