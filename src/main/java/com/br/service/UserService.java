package com.br.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.br.pojo.Book;
import com.br.pojo.User;


public interface UserService {
	
	public boolean login(User user);
	
	public boolean register(User user);
	
	public void addTagToUser(String tag,User user);
	
	public void deleteTagFromUser(String tag,User user);
	
	public void addToCollection(int id,User user);
	 
	public List<Book> viewedBooks(int userId);
	
	public List<Book> favorBooks(int userId);
	
	public List<Book> collectedBooks(int userId);

	void addToCollection(int id);
	
	

}
