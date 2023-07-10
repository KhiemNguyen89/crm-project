package com.spring.myproject.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.myproject.crm.model.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {

}
