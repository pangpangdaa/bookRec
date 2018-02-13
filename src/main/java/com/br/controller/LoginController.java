package com.br.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.form.UserForm;
import com.br.mapper.UserMapper;
import com.br.pojo.User;
import com.br.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/register")
	public String getRegister(Model model){
		model.addAttribute("user",new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@Valid User user,BindingResult result, HttpSession session,
		    final RedirectAttributes redirectAttributes,Model model){

	if(result.hasErrors()){
		model.addAttribute("user",user);
		return "register";
	}
	
	userService.register(user);
	session.setAttribute("CURRENT_USER", user);
	redirectAttributes.addFlashAttribute("message","注册成功！");
	return "redirect:/first_page";
	}
	
	@GetMapping("/login")
	public String getLogin(Model model,RedirectAttributes redirectAttributes,HttpSession session){
		if(session.getAttribute("CURRENT_USER")!=null){
			redirectAttributes.addFlashAttribute("message","您已经登录，请在个人中心注销后再进行登录！");
			return "redirect:/profile";
		}
		model.addAttribute("user",new User());
		return "login";
	}
	
	@PostMapping("/login")
	public String postLogin(@RequestParam("next") Optional<String> next,@Valid User user,BindingResult result,HttpSession session,final RedirectAttributes redirectAttributes,Model model){
		if(result.hasErrors()){
			model.addAttribute("user", user);
			return "login";
		}
		User user1=userMapper.findUserByName(user.getName());
		if(user1!=null && user1.getPassword().equals(user.getPassword())){
			session.setAttribute("CURRENT_USER", user1);
			redirectAttributes.addFlashAttribute("message","登录成功");
			return "redirect:".concat(next.orElse("/first_page"));
		}
		else{
			redirectAttributes.addFlashAttribute("message", "登录失败,请重新登录!");
			return "redirect:/login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("message", "已退出登录");
		session.removeAttribute("CURRENT_USER");
		return "redirect:/login";
	}

}
