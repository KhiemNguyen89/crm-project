package com.spring.myproject.crm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="start")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@Column(name="end")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
	@Column(name="user_id")
	private int doerId;
	
	@Column(name="project_id")
	private int projectId;
	
	@Column(name="task_status_id")
	private int taskStatusId;
	
	@ManyToOne
	@JoinColumn(name="task_status_id", insertable=false, updatable=false)
	private TaskStatus status;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private CrmUser doer;
	
	@ManyToOne
	@JoinColumn(name="project_id", insertable=false, updatable=false)
	private Project project;

	public Task() {
		
	}

	public Task(int id, String name, String description, Date startDate, Date endDate, int doerId, int projectId,
			int taskStatusId, TaskStatus status, CrmUser doer, Project project) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.doerId = doerId;
		this.projectId = projectId;
		this.taskStatusId = taskStatusId;
		this.status = status;
		this.doer = doer;
		this.project = project;
	}

	public Task(String name, Date startDate, Date endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public CrmUser getDoer() {
		return doer;
	}

	public void setDoer(CrmUser doer) {
		this.doer = doer;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getDoerId() {
		return doerId;
	}

	public void setDoerId(int doerId) {
		this.doerId = doerId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTaskStatusId() {
		return taskStatusId;
	}

	public void setTaskStatusId(int taskStatusId) {
		this.taskStatusId = taskStatusId;
	}
}
