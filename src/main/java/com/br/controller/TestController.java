package com.br.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.mapper.BookMapper;
import com.br.mapper.CommentMapper;
import com.br.mapper.UserMapper;
import com.br.pojo.Book;
import com.br.pojo.Comment;
import com.br.pojo.User;
import com.br.service.RecommendService;
import com.br.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	RecommendService recommendService;
	
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	TestService testService;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@GetMapping("/test")
	public Object test(HttpServletRequest request,HttpServletResponse response){
		return "about";
		
	}
		/*
		User user=new User();
		List<User> users=new ArrayList<User>();
		
		user.setName("aa");
		users.add(user);
		User user1=new User();
		user1.setName("bb");
		users.add(user1);
		redisTemplate.opsForValue().set("test1", users);
	List<User> ausers=(List<User>)redisTemplate.opsForValue().get("test1");
	return ausers.get(1);
	*/

	

}

