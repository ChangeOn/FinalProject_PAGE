package com.page.handler;

import com.page.user.dto.UserVO;
import com.page.model.biz.PageService;
import com.page.model.dto.PageVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/page")
public class PageController {
	
    private final PageService page_service;

    @Inject
    public PageController(PageService page_service) {
    	System.out.println("SYSTEM: PageController' @Inject");
    	System.out.println("SYSTEM: PageController'" + page_service);
        this.page_service = page_service;
    }


    // 페이지 메인 화면으로 이동
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String pageGET() throws Exception {

        return "/page/page";
    }
    
    // 페이지 컨텐츠 저장
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void pagePOST(PageVO page_vo, Model model ) throws Exception {

        System.out.println(page_vo);
        page_service.savePageContent(page_vo);
    }
}
