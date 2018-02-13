package com.br.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.mapper.BookMapper;
import com.br.mapper.TagMapper;
import com.br.pojo.Book;
import com.br.pojo.User;
import com.br.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;







@Controller
public class TagController {
	@Autowired
	TagMapper tagMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookMapper bookMapper;
	
	@GetMapping("/select_tag")
	public String getUserTag(){
		return "select_tag";
	}
	
	@PostMapping("/userTag/operate")
	@ResponseBody
	public void addTagToUser(@RequestParam("tagname")String tagName,@RequestParam("status") String type,HttpSession session){
		//Integer userId=10;
		User currentUser=(User)session.getAttribute("CURRENT_USER");
		if(type.equals("true")) {
			userService.addTagToUser(tagName, currentUser);
			//re=userTagService.addTagByName(userId, tagName);
			System.out.println(tagName);
		}else {
			userService.deleteTagFromUser(tagName, currentUser);
			//re=userTagService.delTag(userId, tagName);
		}
	}
	
	@GetMapping("/select_book")   //获得指定某一标签的书籍
	@ResponseBody
	public Object getBooksByTags(Model model,@RequestParam Optional<Integer> page,HttpSession session){
		User currentUser=(User)session.getAttribute("CURRENT_USER");
		List<String> tags=tagMapper.getTagsByUser(currentUser.getId());
		PageHelper.startPage(page.orElse(1), 20);
		PageInfo<Book> books=new PageInfo<Book>(bookMapper.getBookByTags(tags));
		
		model.addAttribute("books",books);
		return books;
		//return "select_book";
  
	}
	
	@GetMapping("/tag/{tag}")   //获得指定某一标签的书籍
	public Object getBooksByTag(@PathVariable String tag,Model model,@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize){
		PageHelper.startPage(page.orElse(1), pageSize.orElse(20));
		PageInfo<Book> books=new PageInfo<Book>(bookMapper.getBookByTag(tag));
		
		model.addAttribute("books",books);
		model.addAttribute("count",books.getTotal());
		model.addAttribute("tag",tag);
		return "rank";
  
	}
	
	@GetMapping("ranklist")
	public Object getBooksByTag(Model model,@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize){
		PageHelper.startPage(page.orElse(1), pageSize.orElse(20));
		PageInfo<Book> books=new PageInfo<Book>(bookMapper.getBookByTag("经典"));
		
		model.addAttribute("books",books);

		return "ranklist";
  
	}
	

}
