package com.spring.myproject.crm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.service.UserService;

@Component
public class AuthenticationUtil {

	@Autowired
	private UserService userService;
	
	public CrmUser getAccount() {
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		return authUser != null ? userService.getUser(authUser.getName()) : null;
	}
}
