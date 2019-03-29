package com.page.user.handler;

import com.page.user.dto.UserVO;
import com.page.model.biz.PageService;
import com.page.model.dto.PageVO;
import com.page.model.dto.Page_CreateVO;
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

    private final UserService user_service;
    private final PageService page_service;

    @Inject
    public UserRegisterController(UserService user_service, PageService page_service) {
    	System.out.println("SYSTEM: UserRegisterController' @Inject");
        this.user_service = user_service;
        this.page_service = page_service;
    }

/*  // 회원가입 페이지
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGET() throws Exception {
    	
    	System.out.println("SYSTEM: UserRegisterController' registerGET");
        return "/user/register";
    } */

    // 회원가입 처리
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPOST(UserVO user_vo, RedirectAttributes redirectAttributes) throws Exception {

    	System.out.println("SYSTEM: UserRegisterController' registerPOST");
    	// 패스워드 암호화 및 유저 패스워드로 설정
        String hashedPw = BCrypt.hashpw(user_vo.getUser_pw(), BCrypt.gensalt());
        user_vo.setUser_pw(hashedPw);
        
        System.out.println("SYSTEM: UserRegisterController' userVo before regist");
        System.out.println("SYSTEM: " + user_vo);
        user_service.register(user_vo);
        redirectAttributes.addFlashAttribute("msg", "REGISTERED");
        
        /* 처음 회원가입시 기본 페이지를 데이터베이스에 
         * 같이 저장시켜줘야 한다. */
        
		//페이지 정보 생성하기
        PageVO page_vo = new PageVO();
        page_vo.setPage_name("처음 페이지");
		page_service.savePageContent(page_vo);
		
		//페이지 생성 정보 생성하기
    	Page_CreateVO page_create_vo = new Page_CreateVO();
    	page_create_vo.setPage_no(page_vo.getPage_no());
    	page_create_vo.setUser_no(user_vo.getUser_no());
    	page_service.connectUserWithPage(page_create_vo);
        
        return "redirect:/";
    }

    // 회원 탈퇴 처리
    @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
    public String userWithdraw(String userId, String userPw, RedirectAttributes redirectAttributes) throws Exception {

        return "redirect:/user/login";
    }
}
