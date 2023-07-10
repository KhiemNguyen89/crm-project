package com.spring.myproject.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.repository.UserRepository;
import com.spring.myproject.crm.service.UserService;
import com.spring.myproject.crm.util.TextUtil;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TextUtil textUtil;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String userName, password = null;
		List<GrantedAuthority> authorities = null;
		CrmUser user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User details not found for user: " + username);
		} else {
			userName = user.getEmail();
			password = user.getPassword();
			authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
		}
		return new User(userName, password, authorities);
	}

	@Override
	public List<CrmUser> getAllUsers() {
		logger.info("Fetching all users.");
		return userRepository.findAll();
	}

	@Override
	public CrmUser getUser(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public CrmUser getUser(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public CrmUser save(CrmUser user) {
		user.setFullName(textUtil.fullNameUtil(user.getFullName()));
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
