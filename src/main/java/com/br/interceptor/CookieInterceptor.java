package com.br.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class CookieInterceptor extends HandlerInterceptorAdapter {


	
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 如果用户已经登录了，就会在Session中以"CURRENT_USER"属性保存当前用户对应的User对象
    	Cookie[] cookies=request.getCookies();
    	for(Cookie cookie:cookies){
    		System.out.println("-------------");
    		System.out.println(cookie.getName()+"====="+cookie.getValue());
    	}
    	System.out.println("--------------------");
    	System.out.println(request.getRequestURI());
    	String cookieName=request.getRequestURI().replaceAll("/", "");
    	System.out.println(cookieName);
    	String cookieValue=request.getRequestURI().split("/")[2];
    	Cookie cookie=new Cookie(cookieName,cookieValue);
    	cookie.setMaxAge(10*60);
    	cookie.setPath("/");
    	response.addCookie(cookie);
    	System.out.println(cookieValue);
    	return true;


}
}
