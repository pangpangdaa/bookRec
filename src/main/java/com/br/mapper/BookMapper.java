package com.br.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.br.pojo.Book;

@Mapper
public interface BookMapper {
	
	public Book findOne(@Param("id") int id);
	
	public List<Book> getSimilarBook(@Param("id") int id);
	
	public List<Book> getBookByTags(@Param("tags") List<String> tags);
	
	public List<Book> getBookByTag(@Param("tag") String tag);
	
	public List<Book> searchBooks(@Param("words") List<String> words);
	
	public List<Book> searchAuthors(@Param("words") List<String> words);
	
	public List<Book> searchTags(@Param("tags") List<String> tags);
	
	public List<String> getTagsByBookId(@Param("id") int bookId);

}
