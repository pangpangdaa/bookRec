package com.br.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.br.pojo.Author;
import com.br.pojo.Book;

@Mapper
public interface AuthorMapper {
	
	public Author findAuthorById(@Param("id") int id);
	
	public List<Book> authorBestFive(@Param("id") int id);

}
