package com.spring.myproject.crm.service;

import java.util.List;

import com.spring.myproject.crm.model.CrmUser;

public interface UserService {
	
	public List<CrmUser> getAllUsers();
	
	public CrmUser getUser(int id);
	
	public CrmUser getUser(String email);
	
	public CrmUser save(CrmUser user);
	
	public void deleteUser(int id);
}
