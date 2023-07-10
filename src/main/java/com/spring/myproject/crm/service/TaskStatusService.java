package com.spring.myproject.crm.service;

import java.util.List;

import com.spring.myproject.crm.model.TaskStatus;

public interface TaskStatusService {

	public List<TaskStatus> getAllStatus();
	
	public TaskStatus getStatus(int id);
}
