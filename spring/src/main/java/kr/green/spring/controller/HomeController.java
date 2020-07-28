package kr.green.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	@Autowired
	private JavaMailSender mailSender;//mail보내기용 Autowired
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
		//ajax를 테스트하기 위한 메소드
	@RequestMapping(value ="/test2")
	@ResponseBody
	public Map<Object, Object> test2(@RequestBody TestVo test){
	    Map<Object, Object> map = new HashMap<Object, Object>();
		System.out.println(test);
	    map.put("res","succecc!!");
	    return map;
	}
	
	//Gmail을 보내기 위한 메소드 추가(https://www.google.com/settings/security/lesssecureapps에서 보안 수준 사용으로 변경)
	@RequestMapping(value = "/mail/mailSending")// /mail/mailSending의 코드가 오면 아래의 내용을 실행
	public String mailSending(HttpServletRequest request) {

	    String setfrom = "stajun@naver.com";         
	    String tomail  = request.getParameter("tomail");     // 받는 사람 이메일
	    String title   = request.getParameter("title");      // 제목
	    String id = request.getParameter("content");    // 내용

	    
        // 랜덤으로 비밀번호를 생성(13자리의 비밀번호를 생성)
	    int len = 13;
	    String newPw = "";//새로운 비밀번호는 처음에 공백으로 생성
	    for(int i=0;i<13;i++) {
	    	//0~9는 숫자 0~9
	    	//10~35는 소문자 a~z
	    	//36~61은 대문자 A~Z
	    	int r = (int)(Math.random()*62);
	    	char ch;
	    	if(r <= 9) {
	    		ch = (char)('0'+r);
	    		//
	    	}else if(r <= 35) {
	    		ch = (char)('a'+(r-10));
	    		// a+0을 해야 소문자 a가 나오기 때문에 10일때 a가 나오도록
	    	}else {
	    		ch = (char)('A'+(r-36));
	    	}newPw += ch;//비밀버호를 결합
	    }
        // 생성된 비밀번호를 회원 정보에 저장(DB)
	    userService.newPw(id, newPw);
        // 메일로 변경된 비밀번호를 전송 */
	    try {//runtime exception이 아닌 다른 exception이 발생할 수 있다.
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper messageHelper 
	            = new MimeMessageHelper(message, true, "UTF-8");
	        //위의 내용은 메일을 보내기 위한 하나의 공식으로 보면 됨
	        
	       
	        String format1 = "<h1> 새로운 비밀번호입니다.</h1>"+"<h2>";
	        String format2 = "</h2>";
	        messageHelper.setFrom(setfrom);  // 보내는사람 생략하거나 하면 정상작동을 안함
	        messageHelper.setTo(tomail);     // 받는사람 이메일
	        messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
	        messageHelper.setText(format1+newPw+format2,true);  // 메일 내용

	        mailSender.send(message);
	    } catch(Exception e){
	        System.out.println(e);
	    }

	    return "redirect:/";
	}
	//메일보내기 화면
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public ModelAndView mailGet(ModelAndView mv) {
		logger.info("URI:/mail:GET");
		mv.setViewName("/main/mail");		
		return mv;
	}
	
}

	

// 서버에 2개의 정보를 ajax를 통해서 보낼 때 예시를 들기 위해서 임시로 TestVo를 생성한것임
class TestVo{
	private String id;
	private int num;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "TestVo [id=" + id + ", num=" + num + "]";
	}
	
}
