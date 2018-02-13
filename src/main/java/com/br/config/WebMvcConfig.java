package com.br.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.br.interceptor.CookieInterceptor;
import com.br.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/profile**").addPathPatterns("/profile/**");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/settings");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/discover");
        registry.addInterceptor(new CookieInterceptor()).addPathPatterns("/book/**");
        registry.addInterceptor(new CookieInterceptor()).addPathPatterns("/author/**");
      
    }

}
