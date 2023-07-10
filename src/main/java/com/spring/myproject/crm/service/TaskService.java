package com.spring.myproject.crm.service;

import java.util.List;

import com.spring.myproject.crm.model.Task;
	
public interface TaskService {
	
	public List<Task> getAllTasks();

	public List<Task> getTasksByProjectId(int projectId);
	
	public Task getTask(int id);
	
	public Task save(Task task);
	
	public void deleteTask(int id);
}
