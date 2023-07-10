package com.spring.myproject.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.myproject.crm.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> { 
    
}
