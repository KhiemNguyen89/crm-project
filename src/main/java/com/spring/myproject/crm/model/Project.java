package com.spring.myproject.crm.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="project")
public class Project {

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
	private int originatorId;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private CrmUser originator;
	
	@OneToMany(mappedBy="project")
	private List<Task> tasks;

	public Project() {
		
	}

	public Project(int id, String name, String description, Date startDate, Date endDate, int originatorId,
			CrmUser originator, List<Task> tasks) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.originatorId = originatorId;
		this.originator = originator;
		this.tasks = tasks;
	}

	public Project(String name, Date startDate, Date endDate, int originatorId) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.originatorId = originatorId;
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

	public int getOriginatorId() {
		return originatorId;
	}

	public void setOriginatorId(int originatorId) {
		this.originatorId = originatorId;
	}

	public CrmUser getOriginator() {
		return originator;
	}

	public void setOriginator(CrmUser originator) {
		this.originator = originator;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
