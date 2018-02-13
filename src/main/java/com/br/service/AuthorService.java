package com.br.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.br.pojo.Book;
@Service
public interface AuthorService {
	
	public List<Book> bestBookByAuthor(int authorId);

}
