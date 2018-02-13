package com.br.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.form.UserForm;
import com.br.mapper.UserMapper;
import com.br.pojo.Book;
import com.br.pojo.Favor;
import com.br.pojo.User;
import com.br.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/profile")
	public String personalCenter(HttpSession session,Model model){
		User user=(User)session.getAttribute("CURRENT_USER");
		List<Book> viewedBooks=userMapper.viewedBooks(user.getId());
		int favorBookSize=userMapper.userFavorSize(user.getId());
		model.addAttribute("viewedBooks",viewedBooks);
		model.addAttribute("user",user);
		model.addAttribute("userRead",viewedBooks.size());
		model.addAttribute("userFavor",favorBookSize);
		return "viewed_books";
	}
	
	@GetMapping("/profile/favor_books")
	public String personalCenterFavor(HttpSession session,Model model){
		User user=(User)session.getAttribute("CURRENT_USER");
		int viewedBookSize=userMapper.userReadSize(user.getId());
		List<Book> favorBooks=userService.favorBooks(user.getId());
		model.addAttribute("favorBooks",favorBooks);
		model.addAttribute("user",user);
		model.addAttribute("userRead",viewedBookSize);
		model.addAttribute("userFavor",favorBooks.size());
		return "favor_books";
	}
	
	@GetMapping("/people/{id}")
	public String peopleInfo(@PathVariable("id") int id,Model model){
		List<Book> viewedBooks=userService.viewedBooks(id);
		User user=userMapper.findUserById(id);
		model.addAttribute("user",user);
		model.addAttribute("viewedBooks",viewedBooks);
		model.addAttribute("userRead",viewedBooks.size());
		return "userPage";
	}
	
	@PutMapping("/favor/{bookId}")
	public String addFavor(@PathVariable("bookId") int bookId,HttpSession session,RedirectAttributes redirectAttributes){
		User user=(User)session.getAttribute("CURRENT_USER");
		if(user!=null){
		Favor favor=new Favor();
		favor.setBookId(bookId);
		favor.setUserId(user.getId());
		favor.setCreateDate(new Date());
		userMapper.addFavor(favor);
		}
		else{
			redirectAttributes.addFlashAttribute("message","您尚未登录，请先登录再加入想读");
		}
		return "redirect:/book/"+bookId;
	}
	
	@DeleteMapping("/favor/{bookId}")
	public String deleteFavor(@PathVariable("bookId") int bookId,HttpSession session){
		User user=(User)session.getAttribute("CURRENT_USER");
		Favor favor=new Favor();
		favor.setBookId(bookId);
		favor.setUserId(user.getId());
		userMapper.deleteFavor(favor);
		return "redirect:/profile/favor_books";
	}
	

} 
