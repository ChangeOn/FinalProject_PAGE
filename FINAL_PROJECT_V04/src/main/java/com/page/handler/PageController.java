package com.page.handler;

import com.page.user.dto.UserVO;
import com.page.user.biz.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/page")
public class PageController {


    // 페이지 메인 화면으로 이동
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userInfo() throws Exception {

        return "/page/page";
    }
}
