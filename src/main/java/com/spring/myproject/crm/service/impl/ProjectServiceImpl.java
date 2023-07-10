package com.spring.myproject.crm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myproject.crm.model.Project;
import com.spring.myproject.crm.repository.ProjectRepository;
import com.spring.myproject.crm.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Project> getAllProjects() {
        logger.info("Fetching all projects");
        return projectRepository.findAll();
    }

    @Override
    public Project getProject(int id) {
        logger.info("Fetching project with id: {}", id);
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public Project save(Project project) {
        logger.info("Saving project with name: {}", project.getName());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(int id) {
        logger.info("Deleting project with id: {}", id);
        projectRepository.deleteById(id);
    }
    
}
