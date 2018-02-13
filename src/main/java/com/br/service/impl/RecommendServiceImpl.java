package com.br.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.br.mapper.AuthorMapper;
import com.br.mapper.BookMapper;
import com.br.mapper.UserMapper;
import com.br.pojo.Author;
import com.br.pojo.Book;
import com.br.service.RecommendService;



@Service
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	AuthorMapper authorMapper;
	
	@Override
	public List<Book> recommendByUserTags(List<String> tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> dailyRecommend(int userId)  {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Cacheable(value="redisCacheManager",key="'redis_'+#userId")
	public List<Book> getRecommendBooksBySVD(int userId) throws IOException{ 
		
		if(userMapper.userRead(userId)==0){
			return hotBookRecommend();
		}
		String link="http://127.0.0.1:5000/recommend/"+userId;
		System.out.println(link);
		URL url=new URL(link);
		BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder sb=new StringBuilder();
		String line="";
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		List<Integer> ids=new LinkedList<Integer>();
		String[] strIds=sb.toString().split("\t");
		for(String strId:strIds){
			ids.add(Integer.parseInt(strId));
			
		}
		List<Book> books=new LinkedList<Book>();
		for(Integer bookId:ids){
			books.add(bookMapper.findOne(bookId));
		}
		
		return books;
	}

	@Override
	@Cacheable(value="redisCacheManager",key="'author_rec_'+#userId")
	public List<Author> authorRecommend(int userId) {
		List<Author> authors=new ArrayList<Author>();
		authors.add(authorMapper.findAuthorById(1));
		authors.add(authorMapper.findAuthorById(2));
		authors.add(authorMapper.findAuthorById(3));
		// TODO Auto-generated method stub
		return authors;
	}

	@Override
	@Cacheable(value="redisCacheManager",key="'hot_book'")
	public List<Book> hotBookRecommend() {
		List<Book> books=new ArrayList<Book>();
		books.add(bookMapper.findOne(25942829));
		books.add(bookMapper.findOne(20283224));
		books.add(bookMapper.findOne(19967674));
		books.add(bookMapper.findOne(1902505));
		books.add(bookMapper.findOne(20429351));
		books.add(bookMapper.findOne(5956572));
		return books;
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> similarBookRecommend(int bookId) {
		return bookMapper.getSimilarBook(bookId);
		// TODO Auto-generated method stub
	}

	@Override
	@Cacheable(value="redisCacheManager",key="'rank'")
	public List<Book> rank() {
		// TODO Auto-generated method stub
		return null;
	}

	//根据历史记录寻找之前点击过的书的相似书籍
	@Override
	public List<Book> discover(HttpServletRequest request) {
		Cookie[] cookies=request.getCookies();
		List<Integer> scanedBookIds=new ArrayList<Integer>();
		for(int i=cookies.length-1;i>=0;i--){
			if(cookies[i].getName().contains("book")){
			scanedBookIds.add(Integer.parseInt(cookies[i].getValue()));
		}
		}
		
		Set<Book> set=new HashSet<Book>();
		for(Integer bookId:scanedBookIds){
			set.addAll(similarBookRecommend(bookId));
			if(set.size()>10)break;
		}
		// TODO Auto-generated method stub
		return new ArrayList<>(set);
	}
	
	@Override
	@Cacheable(value="redisCacheManager",key="'personal'+#userId")
	public Map<String, Book> personalBooks(int userId){
		Map<String,Book> books=new HashMap<String,Book>();
		books.put("first",userMapper.firstReadBook(userId));
		books.put("last",userMapper.lastReadBook(userId));
		books.put("best",userMapper.best(userId));
		books.put("worst",userMapper.worst(userId));
		
		return books;
	}
	
	@Override
	@Cacheable(value="redisCacheManager",key="'avg'+#userId")
	public Map<String,Double> personalAvgComments(int userId){
		Map<String,Double> comments=new HashMap<String,Double>();
		comments.put("avgRead", userMapper.avgScoreRead(userId));
		comments.put("avgBooks",userMapper.avgScoreBooks(userId));
		return comments;
	}

}
