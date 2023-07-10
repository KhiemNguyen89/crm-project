package com.spring.myproject.crm.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="user")
public class CrmUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	@Column(name="email")
	@NotBlank(message="Vui lòng nhập email đăng ký.")
	@Email(message="Email không hợp lệ.")
	private String email;

	@Column(name="password")
	@NotBlank(message="Vui lòng nhập mật khẩu.")
	private String password;

	@Column(name="full_name")
	@NotBlank(message="Vui lòng điền tên của bạn.")
	private String fullName;
	
	@Column(name="mobile_number")
	private String mobileNumber;

	@Column(name="role_id")
	private int roleId;

	@ManyToOne
	@JoinColumn(name="role_id", updatable=false, insertable=false)
	private Role role;
	
	@OneToMany(mappedBy="doer")
	private List<Task> tasks;

	public CrmUser() {
		
	}
	
	public CrmUser(int id,
			@NotBlank(message = "Vui lòng nhập email đăng ký.") @Email(message = "Email không hợp lệ.") String email,
			@NotBlank(message = "Vui lòng nhập mật khẩu.") String password,
			@NotBlank(message = "Vui lòng điền tên của bạn.") String fullName, String mobileNumber,
			int roleId, Role role, List<Task> tasks) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.mobileNumber = mobileNumber;
		this.roleId = roleId;
		this.role = role;
		this.tasks = tasks;
	}

	public CrmUser(String email, String password, String fullName, int roleId) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
