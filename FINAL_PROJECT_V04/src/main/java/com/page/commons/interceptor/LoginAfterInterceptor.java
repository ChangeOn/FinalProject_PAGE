package com.page.commons.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAfterInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginAfterInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	System.out.println("SYSTEM: LoginAfterInterceptor' preHandle");
        // 로그인 처리후 회원가입을 시도 할 경우
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
        	System.out.println("SYSTEM: LoginAfterInterceptor' login cookie alive");
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        System.out.println("SYSTEM: LoginAfterInterceptor' login cookie death");
        return true;
    }
}
