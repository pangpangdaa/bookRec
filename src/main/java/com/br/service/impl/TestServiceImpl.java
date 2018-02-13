package com.br.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.br.mapper.BookMapper;
import com.br.pojo.Book;
import com.br.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	BookMapper bookMapper;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	public Book getTestBook() {
		// TODO Auto-generated method stub
		return bookMapper.findOne(3024737);
	}

}
