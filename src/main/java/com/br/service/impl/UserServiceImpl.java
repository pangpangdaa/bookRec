package com.br.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.br.mapper.BookMapper;
import com.br.mapper.UserMapper;
import com.br.pojo.Book;
import com.br.pojo.User;
import com.br.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	BookMapper bookMapper;
	@Override
	public boolean login(User user) {
		User user1=userMapper.findUserByName(user.getName());
		if(user1.getPassword().equals(user.getPassword()))return true;
		return false;
		// TODO Auto-generated method stub
	}

	@Override
	public boolean register(User user) {
		userMapper.createUser(user);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addTagToUser(String tag,User user) {
		userMapper.addTag2User(tag, user.getId());
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteTagFromUser(String tag, User user) {
		userMapper.deleteTagFromUser(tag, user.getId());
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void addToCollection(int id,User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//@Cacheable(value="redisCacheManager",key="'viewed_'+#userId")
	public List<Book> viewedBooks(int userId) {
		return userMapper.viewedBooks(userId);
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> collectedBooks(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToCollection(int id) {
		// TODO Auto-generated method stub
		

	}

	@Override
	//@Cacheable(value="redisCacheManager",key="'favor_'+#userId")
	public List<Book> favorBooks(int userId) {
		// TODO Auto-generated method stub
		return userMapper.favorBooks(userId);
	}

}
