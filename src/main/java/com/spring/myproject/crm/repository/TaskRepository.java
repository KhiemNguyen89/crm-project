package com.spring.myproject.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.myproject.crm.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProjectId(int projectId);
}
