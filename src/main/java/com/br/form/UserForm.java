package com.br.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.br.pojo.User;


public class UserForm{
	
	@Size(min = 6, max = 30)
	String name;
	

	
	@Size(min = 6, max = 30)
	String password;
	
	@Email
	String mail;

	
	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public User toUser(){
		User user=new User();
		user.setName(name);
		user.setMail(mail);
		user.setPassword(password);
		return user;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
}
