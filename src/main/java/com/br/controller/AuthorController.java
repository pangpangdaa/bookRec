package com.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.mapper.AuthorMapper;

@Controller
public class AuthorController {
	
	@Autowired
	AuthorMapper authorMapper;
	
	@GetMapping("/author/{id}")
	public Object getAuthor(@PathVariable("id") int id,Model model){
		model.addAttribute("author",authorMapper.findAuthorById(id));
		model.addAttribute("books",authorMapper.authorBestFive(id));
		return "authorDetails";
	}

}
