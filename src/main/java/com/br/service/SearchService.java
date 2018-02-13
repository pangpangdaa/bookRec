package com.br.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.pojo.Book;


public interface SearchService {
	
	public List<Book> search(List<String> words);
	
	public List<Book> searchByTag(String tag);
	

	


}
