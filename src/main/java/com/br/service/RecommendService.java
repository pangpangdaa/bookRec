package com.br.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.br.pojo.Author;
import com.br.pojo.Book;

@Service
public interface RecommendService {
	
	List<Book> recommendByUserTags(List<String> tags);
	
	List<Book> dailyRecommend(int userId);
	
	List<Author> authorRecommend(int userId);
	
	List<Book> hotBookRecommend();
	
	List<Book> getRecommendBooksBySVD(int userId) throws IOException;

	List<Book> similarBookRecommend(int bookId);
	
	List<Book> rank();
	
	List<Book> discover(HttpServletRequest request);
	
	public Map<String,Book> personalBooks(int userId);
	
	public Map<String,Double> personalAvgComments(int userId);
	

}
