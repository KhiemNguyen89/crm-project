package com.spring.myproject.crm.service;

import java.util.List;

import com.spring.myproject.crm.model.Role;

public interface RoleService {

    public List<Role> getAllRole();

    public Role getRole(int roleId);
}
