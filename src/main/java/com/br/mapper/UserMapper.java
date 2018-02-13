package com.br.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.br.pojo.Book;
import com.br.pojo.Favor;
import com.br.pojo.User;

@Mapper
public interface UserMapper {
	
	public User findUserById(@Param("id") int id); 
	
	public void createUser(User user);
	
	public User findUserByName(@Param("name") String name);
	
	public void addTag2User(@Param("tag") String tag,@Param("userId") int userId);
	
	public void deleteTagFromUser(@Param("tag") String tag,@Param("userId") int userId);
	
	public int userRead(@Param("id") int id);
	public int userFavor(@Param("id") int id);
	public List<Book> viewedBooks(@Param("id") int id);
	
	public List<Book> favorBooks(@Param("id") int id);
	
	public void updateUser(User user);
	
	public Book firstReadBook(@Param("id") int userId);
	
	public Book lastReadBook(@Param("id") int userId);
	
	public Double avgScoreRead(@Param("id") int userId);
	
	public Double avgScoreBooks(@Param("id") int userId);
	
	public Book best(@Param("id") int userId);
	
	public Book worst(@Param("id") int userId);
	
	public void addFavor(Favor favor);
	
	public void deleteFavor(Favor favor);
	
	public int userReadSize(@Param("id") int userId);
	
	public int userFavorSize(@Param("id") int userId);
	
	public int userFavored(@Param("bookId") int bookId,@Param("userId") int userId);
	
	

}
