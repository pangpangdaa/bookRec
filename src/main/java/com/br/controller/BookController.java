package com.br.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.mapper.BookMapper;
import com.br.mapper.CommentMapper;
import com.br.mapper.TagMapper;
import com.br.mapper.UserMapper;
import com.br.pojo.Book;
import com.br.pojo.User;
import com.br.service.RecommendService;
import com.br.service.SearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class BookController {

	
	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	TagMapper tagMapper;
	
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	RecommendService recommendService;
	
	@Autowired
	SearchService searchService;
	
	@GetMapping("/book/{id}")
	public Object getBook(@PathVariable("id") int id,Model model,HttpSession session){
		
	int judge=0;
	User user=(User)session.getAttribute("CURRENT_USER");
	if(user!=null){
		judge=userMapper.userFavored(id, user.getId());
	}
	model.addAttribute("book",bookMapper.findOne(id));
	model.addAttribute("simbooks",recommendService.similarBookRecommend(id));
	model.addAttribute("comments",commentMapper.findCommentsByBookIdLimit10(id));
	model.addAttribute("tags",tagMapper.getTagsByBookId(id));
	model.addAttribute("count",commentMapper.numOfCommentByBookId(id));
	model.addAttribute("judge", judge);
	return "bookDetails";
	//return commentMapper.findCommentsByBookIdLimit10(id);
	}
	
	
	
	@GetMapping("/first_page")
	public Object first_page(HttpSession session,Model model) throws IOException{
		User currentUser=(User)session.getAttribute("CURRENT_USER");
		List<Book> books=new ArrayList<Book>();
		if(currentUser==null){

			books=recommendService.hotBookRecommend();
		}
		else {
			
			books=recommendService.getRecommendBooksBySVD(currentUser.getId());
		}

		model.addAttribute("books",books);
		model.addAttribute("hotBooks",recommendService.hotBookRecommend());
		model.addAttribute("authors",recommendService.authorRecommend(1));

		return "first_page";
		
		
		
	}
	
	@GetMapping("/search")
	public Object search(@RequestParam("words") String words,Model model,@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize){
		String[] wordGroup=words.split(" ");
		List<String> wordList=Arrays.asList(wordGroup);
		PageHelper.startPage(page.orElse(1),pageSize.orElse(20));
		PageInfo pageInfo=new PageInfo<>(searchService.search(wordList));
		model.addAttribute("books",pageInfo);
		model.addAttribute("count",pageInfo.getTotal());
		model.addAttribute("words",words);
		return "search";

	}
	
	@GetMapping("/discover")
	public Object discover(HttpServletRequest request,Model model,HttpSession session){
		User user=(User)session.getAttribute("CURRENT_USER");
		model.addAttribute("count",userMapper.userRead(user.getId()));
		model.addAttribute("books",recommendService.discover(request));
		model.addAttribute("personal",recommendService.personalBooks(user.getId()));
		model.addAttribute("avg",recommendService.personalAvgComments(user.getId()));
		return "discover";
		
	}
	
	
	
}
