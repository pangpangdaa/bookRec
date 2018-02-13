package com.br.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TagMapper {
	
	public List<String> getTagsByUser(@Param("userId") int userId);
	
	public List<String> getTagsByBookId(@Param("id") int bookId);

}
