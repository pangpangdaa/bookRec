package com.br;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableCaching
@Controller
public class Application {
	
	@GetMapping("/")
	public String hello(){
		return "about";
	} 
	
	
	
	 
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	
	}

}
