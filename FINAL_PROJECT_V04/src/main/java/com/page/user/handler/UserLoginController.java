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

    private final UserService user_service;

    @Inject
    public UserLoginController(UserService user_service) {
    	System.out.println("SYSTEM: UserLoginController' @Inject");
    	System.out.println("SYSTEM: UserLoginController'" + user_service);
        this.user_service = user_service;
    }

    /* 컨트롤러 내 로그인 전처리 메소드 */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(@ModelAttribute("loginDTO") LoginDTO loginDTO) {
        return "redirect:/";
    }

    // 로그인 처리
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public void loginPOST(LoginDTO loginDTO, HttpSession http_session, Model model) throws Exception {

    	System.out.println("SYSTEM: UserLoginController' loginPOST");
    	
    	System.out.println("SYSTEM: UserLoginController' loginDTO before login");
        System.out.println("SYSTEM: " + loginDTO);
         
        UserVO user_vo = user_service.login(loginDTO);

        System.out.println("SYSTEM: UserLoginController' user_vo after login");
        System.out.println("SYSTEM: " + user_vo);
        
        // 패스워드 NULL값 혹은 복호화 동일 비교
        if (user_vo == null || !BCrypt.checkpw(loginDTO.getUser_pw(), user_vo.getUser_pw())) {
   
            return;
        }

        model.addAttribute("user", user_vo);

        // 로그인 유지를 체크 했을 경우
        if (loginDTO.isUser_cookie()) {
        	
        	System.out.println("SYSTEM: UserLoginController' set login cookie");
        	
            int amount = 60 * 60 * 24 * 7;
            Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
            user_service.keepLogin(user_vo.getUser_id(), http_session.getId(), sessionLimit);
            System.out.println("SYSTEM: " + user_vo);
        }
    }

    // 로그아웃 처리
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession http_session) throws Exception {

    	System.out.println("SYSTEM: UserLoginController' logout");
    	
        Object object = http_session.getAttribute("login");
        if (object != null) {
        	
        	System.out.println("SYSTEM: UserLoginController' logout progressing");
            UserVO user_vo = (UserVO) object;
            http_session.removeAttribute("login");
            http_session.invalidate();
            Cookie login_cookie = WebUtils.getCookie(request, "login_cookie");
            if (login_cookie != null) {
                login_cookie.setPath("/");
                login_cookie.setMaxAge(0);
                response.addCookie(login_cookie);
                user_service.keepLogin(user_vo.getUser_id(), "NONE", new Date());
            }
        }

        return "/user/logout";
    }


}
