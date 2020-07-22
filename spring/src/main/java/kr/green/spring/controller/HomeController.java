package kr.green.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Select;
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

import kr.green.spring.service.UserService;
import kr.green.spring.vo.UserVo;

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
	// 기본 화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv) {
		logger.info("URI:/");
		mv.setViewName("/main/home");
		//tiles의 입장으론 /*/*을 의미
		return mv;
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView homePost(ModelAndView mv, UserVo user) {
		logger.info("URI:/");
		UserVo dbUser = userService.isSignin(user);
		// 입력된 회원정보가 있으면 그 회원의 전체정보를 가져와라(가져온 비밀번호는 암호화되어 있는 비밀번호로 가져옴)
		if(dbUser != null){//로그인 성공
			mv.setViewName("redirect:/board/list");
			mv.addObject("user", dbUser);//user에 dbUser를 저장
		}
		else//로그인 실패
			mv.setViewName("redirect:/");
		return mv;
	}


	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView SignupGet(ModelAndView mv) {
		logger.info("URI:/signup:GET");
		mv.setViewName("/main/signup");
		return mv;
	}
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView SignupPost(ModelAndView mv, UserVo user) {
		logger.info("URI:/signup:Post");
		if(userService.signup(user)) {
			mv.setViewName("redirect:/");
		}else {
			mv.setViewName("redirect:/signup");
			mv.addObject("user", user);
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public ModelAndView SignoutGet(ModelAndView mv,HttpServletRequest request) {
		logger.info("URI:/signout:GET");
		mv.setViewName("redirect:/");
		request.getSession().removeAttribute("user");
		
		return mv;
	}
	
	//임시 ajax 문성 추가 에서 코드 가져오기
	@RequestMapping(value ="/idCheck")
	@ResponseBody
	public Map<Object, Object> idcheck(@RequestBody String id){

	    Map<Object, Object> map = new HashMap<Object, Object>();
	    UserVo user = userService.getUser(id);
	    boolean check = user == null? true : false;//참거짓으로 회원가입 여부만 확인가능
	    map.put("check", check);
	    
	    return map;
	}
}
