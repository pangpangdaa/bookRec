package com.br.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.mapper.UserMapper;
import com.br.pojo.User;
import com.br.service.UserService;

@Controller
public class ProfileController {
	
	@Value("${file.dir}")
	private String ROOT;
	
	@Autowired
	private final ResourceLoader resourceLoader;
	
	@Autowired
    public ProfileController(ResourceLoader resourceLoader,
    								UserMapper userMapper) {
        this.resourceLoader = resourceLoader;
        this.userMapper = userMapper;
    }
	
	
	@Autowired
	UserMapper userMapper;

	
	@GetMapping("/setting")
	public String settings(HttpSession session,Model model){
		User user=(User)session.getAttribute("CURRENT_USER");
		model.addAttribute("user",user);
		return "profile";
	}
	
	@PostMapping("/setting")
	public String settings(@RequestParam(value="file",required=false) MultipartFile file,@Valid User user,BindingResult result,HttpSession session,Model model,
		    final RedirectAttributes redirectAttributes) throws IOException{
		User currentUser=(User)session.getAttribute("CURRENT_USER");
		if(result.hasErrors()){
		
			model.addAttribute("message","failed");
			model.addAttribute("user",user);
			return "profile";
		}
		
		currentUser.setName(user.getName());
		currentUser.setPassword(user.getPassword());
		currentUser.setMail(user.getMail());
		currentUser.setIntroduction(user.getIntroduction());
		
		
		if(file!=null && !file.isEmpty()){
			String filename=currentUser.getId()+".jpg";
			Files.deleteIfExists(Paths.get(ROOT,filename));
			Files.copy(file.getInputStream(), Paths.get(ROOT,filename));
			currentUser.setAvatar(ROOT+filename);
			
		}
		userMapper.updateUser(currentUser);
		redirectAttributes.addFlashAttribute("message","个人信息修改成功");
		return "redirect:/profile"; 
	}
	
	@GetMapping("/pic/{userId}")
	@ResponseBody
	public ResponseEntity<?> getFile(@PathVariable("userId") int userId){
		User user=userMapper.findUserById(userId);
		if(user.getAvatar()==null){
			try{
				return ResponseEntity.ok(resourceLoader.getResource("classpath:"+Paths.get("static/img/", "default.jpg")));
        	}catch(Exception e){
        		return ResponseEntity.notFound().build();
        	}
		}
		else{
			String filename = String.valueOf(userId)+".jpg";
        	return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        
		}
		
	}

}
