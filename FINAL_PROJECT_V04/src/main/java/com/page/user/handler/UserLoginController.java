package com.page.user.handler;

import com.page.user.dto.LoginDTO;
import com.page.user.dto.UserVO;
import com.page.user.biz.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    private final UserService userService;

    @Inject
    public UserLoginController(UserService userService) {
    	System.out.println("SYSTEM: UserLoginController' @Inject");
    	System.out.println("SYSTEM: UserLoginController'" + userService);
        this.userService = userService;
    }

    // 인덱스 페이지
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(@ModelAttribute("loginDTO") LoginDTO loginDTO) {
    	
    	return "redirect:/";
    } 
    
    // 재로그인 처리
    @RequestMapping(value = "/re_loginGet", method = RequestMethod.GET)
    public void re_loginGET(HttpServletRequest request, HttpServletResponse response
    		, HttpSession httpSession, Model model) throws Exception {
    	
    	System.out.println("SYSTEM: UserLoginController' auto_loginGET");
    	
    	//세션에서 로그인 중인 유저 정보 받아오기
    	Object object = httpSession.getAttribute("login");
    	UserVO userVO = (UserVO) object;
    	
    	System.out.println(userVO);
    	
    	/* 
    	 * 지금껏 알아본 바 인터셉터로 리다이렉트시 해당 URL로 매핑된
    	 * 컨트롤러로 넘어가고, 컨트롤러에서 리턴시에만 VIEW로 넘어감
    	 * 
    	 * */
    	
    	/*
    	//유저 쿠키 정보 재설정
    	System.out.println("SYSTEM: UserLoginController' user login cookie setting");
    	int amount = 60 * 60 * 24 * 7;
        Date session_limit = new Date(System.currentTimeMillis() + (1000 * amount));
        userService.keepLogin(userVO.getUser_id(), httpSession.getId(), session_limit);
        System.out.println("SYSTEM: " + userVO);
        
    	model.addAttribute("user", userVO); */
    	
    	
    }

    // 로그인 처리
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public void loginPOST(LoginDTO loginDTO, HttpSession httpSession, Model model) throws Exception {

    	System.out.println("SYSTEM: UserLoginController' loginPOST");
    	
    	System.out.println("SYSTEM: UserLoginController' loginDTO before login");
        System.out.println("SYSTEM: " + loginDTO);
         
        UserVO userVO = userService.login(loginDTO);

        System.out.println("SYSTEM: UserLoginController' userVo after login");
        System.out.println("SYSTEM: " + userVO);
        
        // 패스워드 NULL값 혹은 복호화 동일 비교
        if (userVO == null || !BCrypt.checkpw(loginDTO.getUser_pw(), userVO.getUser_pw())) {
   
            return;
        }

        model.addAttribute("user", userVO);

        if (loginDTO.isUser_cookie()) {
        	System.out.println("SYSTEM: UserLoginController' set login cookie");
            int amount = 60 * 60 * 24 * 7;
            Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
            userService.keepLogin(userVO.getUser_id(), httpSession.getId(), sessionLimit);
            System.out.println("SYSTEM: " + userVO);
        }
    }

    // 로그아웃 처리
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession httpSession) throws Exception {

    	System.out.println("SYSTEM: UserLoginController' logout");
    	
        Object object = httpSession.getAttribute("login");
        if (object != null) {
        	System.out.println("SYSTEM: UserLoginController' logout progressing");
            UserVO userVO = (UserVO) object;
            httpSession.removeAttribute("login");
            httpSession.invalidate();
            Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
            if (loginCookie != null) {
                loginCookie.setPath("/");
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
                userService.keepLogin(userVO.getUser_id(), "NONE", new Date());
            }
        }

        return "/user/logout";
    }


}
