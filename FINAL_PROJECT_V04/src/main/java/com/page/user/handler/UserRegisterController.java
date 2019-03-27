package com.page.user.handler;

import com.page.user.dto.UserVO;
import com.page.user.biz.UserService;
import com.page.user.biz.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

@Controller
@RequestMapping("/user")
public class UserRegisterController {

    private final UserService userService;

    @Inject
    public UserRegisterController(UserService userService) {
    	System.out.println("SYSTEM: UserRegisterController' @Inject");
        this.userService = userService;
    }

/*  // 회원가입 페이지
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGET() throws Exception {
    	
    	System.out.println("SYSTEM: UserRegisterController' registerGET");
        return "/user/register";
    } */

    // 회원가입 처리
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPOST(UserVO userVO, RedirectAttributes redirectAttributes) throws Exception {

    	System.out.println("SYSTEM: UserRegisterController' registerPOST");
    	// 패스워드 암호화 및 유저 패스워드로 설정
        String hashedPw = BCrypt.hashpw(userVO.getUser_pw(), BCrypt.gensalt());
        userVO.setUser_pw(hashedPw);
        
        System.out.println("SYSTEM: UserRegisterController' userVo before regist");
        System.out.println("SYSTEM: " + userVO);
        userService.register(userVO);
        redirectAttributes.addFlashAttribute("msg", "REGISTERED");
        
        return "redirect:/";
    }

    // 회원 탈퇴 처리
    @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
    public String userWithdraw(String userId, String userPw, RedirectAttributes redirectAttributes) throws Exception {

        return "redirect:/user/login";
    }
}
