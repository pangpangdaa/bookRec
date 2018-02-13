package com.br.controller;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.mapper.BookMapper;
import com.br.mapper.CommentMapper;
import com.br.pojo.Comment;
import com.br.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class CommentController {
	
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	BookMapper bookMapper;
	
	@GetMapping("/comment/{id}")
	public Object getComment(@PathVariable("id") int id,@RequestParam("page") Optional<Integer> page,@RequestParam("pageSize")Optional<Integer> pageSize,Model model){
		PageHelper.startPage(page.orElse(1), pageSize.orElse(20));
		PageInfo<Comment> comments=new PageInfo<Comment>(commentMapper.findCommentsByBookId(id));
		model.addAttribute("comments",comments);
		model.addAttribute("book",bookMapper.findOne(id));
		model.addAttribute("count",commentMapper.numOfCommentByBookId(id));
		//return comments;
		return "comments";
	}
	
	@PostMapping("/comment/{id}")
	public Object getComment(@PathVariable("id") int bookId,@RequestParam("content") String content,@RequestParam("score") int score,HttpSession session,final RedirectAttributes redirectAttributes){
		User user=(User)session.getAttribute("CURRENT_USER");
		if(user==null){
			redirectAttributes.addFlashAttribute("message", "您尚未登录，请先登录再评论");
			return "redirect:/book/"+bookId;
		}
		Comment comment=new Comment();
		comment.setBookId(bookId);
		comment.setContent(content);
		comment.setScore(score);
		comment.setUserId(user.getId());
		comment.setCreateDate(new Date());
		commentMapper.addComment(comment);
		redirectAttributes.addFlashAttribute("message", "评论添加成功");
		return "redirect:/book/"+bookId;
	}

}
