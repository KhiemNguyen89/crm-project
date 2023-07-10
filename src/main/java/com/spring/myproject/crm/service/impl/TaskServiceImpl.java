package com.spring.myproject.crm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myproject.crm.model.Task;
import com.spring.myproject.crm.repository.TaskRepository;
import com.spring.myproject.crm.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<Task> getAllTasks() {
		logger.info("Fetching all tasks");
		return taskRepository.findAll();
	}

	@Override
	public List<Task> getTasksByProjectId(int projectId) {
		logger.info("Fetching tasks in project with id: ", projectId);
		return taskRepository.findByProjectId(projectId);
	}

	@Override
	public Task getTask(int id) {
		logger.info("Fetching task with id: {}", id);
		return taskRepository.findById(id).orElse(null);
	}

	@Override
	public Task save(Task task) {
		logger.info("Saving task with name: {}", task.getName());
		return taskRepository.save(task);
	}

	@Override
	public void deleteTask(int id) {
		logger.info("Delete task with id: {}", id);
		taskRepository.deleteById(id);
	}

}
