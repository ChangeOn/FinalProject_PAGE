package com.page.commons.interceptor;

import com.page.user.dto.UserVO;
import com.page.user.biz.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RememberMeInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RememberMeInterceptor.class);

	@Inject
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("SYSTEM: RememberMeInterceptor' preHandle");

		HttpSession httpSession = request.getSession();
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");

        if (loginCookie != null) {
        	
            UserVO userVO = userService.checkLoginBefore(loginCookie.getValue());
            
            if (userVO != null) {
            	
                httpSession.setAttribute("login", userVO);
                return true;
            }
        }

        return false;
	}
}
