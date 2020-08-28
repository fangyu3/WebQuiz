package com.fangyu3.webquiz.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fangyu3.webquiz.entity.User;
import com.fangyu3.webquiz.exception.UserAlreadyExistException;
import com.fangyu3.webquiz.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User newUser) {
		
		Optional <User> user = userDetailsService.findByEmail(newUser.getEmail());
		
		if(user.isPresent())
			throw new UserAlreadyExistException("User with email " + newUser.getEmail() + " already exists!");
		
		return userDetailsService.save(newUser);
	}

}
