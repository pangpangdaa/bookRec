package com.br.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.br.pojo.Comment;

@Mapper
public interface CommentMapper {
	
	List<Comment> findCommentsByBookId(@Param("bookId") int bookId);
	List<Comment> findCommentsByBookIdLimit10(@Param("bookId") int bookId);
	void addComment(Comment comment);
	int numOfCommentByBookId(@Param("id") int bookId);
	


}
