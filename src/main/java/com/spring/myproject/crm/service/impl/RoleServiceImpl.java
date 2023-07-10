package com.spring.myproject.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myproject.crm.model.Role;
import com.spring.myproject.crm.repository.RoleRepository;
import com.spring.myproject.crm.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(int roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }
    
}