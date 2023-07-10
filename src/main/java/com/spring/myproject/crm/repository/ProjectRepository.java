package com.spring.myproject.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.myproject.crm.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
