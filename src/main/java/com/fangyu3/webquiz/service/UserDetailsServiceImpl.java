package com.fangyu3.webquiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fangyu3.webquiz.dao.UserRepository;
import com.fangyu3.webquiz.entity.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserDetailsServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <User> user = userRepository.findByEmail(username);
		
		if (user.isPresent()) {
			return user.get();
	    }
		else{
			throw new UsernameNotFoundException(String.format("Username[%s] not found"));
	    }
	}
	
	public User save(User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}
	
	public Optional<User> findByEmail(String userEmail) {
		return userRepository.findByEmail(userEmail);
	}

}
