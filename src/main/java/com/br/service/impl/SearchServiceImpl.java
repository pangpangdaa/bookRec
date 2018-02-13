package com.br.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.mapper.BookMapper;
import com.br.pojo.Book;
import com.br.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	BookMapper bookMapper;

	@Override
	public List<Book> search(List<String> words) {
		Set<Book> books=new HashSet<Book>();
		books.addAll(bookMapper.searchAuthors(words));
		books.addAll(bookMapper.searchTags(words));
		return new ArrayList<>(books);
	}

	@Override
	public List<Book> searchByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
