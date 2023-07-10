package com.spring.myproject.crm.service;

import java.util.List;

import com.spring.myproject.crm.model.Project;

public interface ProjectService {
    
    public List<Project> getAllProjects();

    public Project getProject(int id);

    public Project save(Project project);

    public void deleteProject(int id);
}
