package com.spring.myproject.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.myproject.crm.model.CrmUser;

public interface UserRepository extends JpaRepository<CrmUser, Integer> {
	
	public CrmUser findByEmail(String email);
	
}
