package com.spring.myproject.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myproject.crm.model.TaskStatus;
import com.spring.myproject.crm.repository.TaskStatusRepository;
import com.spring.myproject.crm.service.TaskStatusService;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
	
	@Autowired
	private TaskStatusRepository taskStatusRepository;

	@Override
	public List<TaskStatus> getAllStatus() {
		return taskStatusRepository.findAll();
	}

	@Override
	public TaskStatus getStatus(int id) {
		return taskStatusRepository.findById(id).orElse(null);
	}

}
