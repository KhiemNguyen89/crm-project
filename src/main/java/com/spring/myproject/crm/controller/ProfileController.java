package com.spring.myproject.crm.controller;

import static com.spring.myproject.crm.constant.ApplicationConstant.TaskStatus.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.model.Task;
import com.spring.myproject.crm.model.TaskStatus;
import com.spring.myproject.crm.service.TaskService;
import com.spring.myproject.crm.service.TaskStatusService;
import com.spring.myproject.crm.util.ApplicationUtil;
import com.spring.myproject.crm.util.AuthenticationUtil;

import static com.spring.myproject.crm.util.Notification.*;

@Controller
public class ProfileController {
	
	@Autowired
	private AuthenticationUtil authenticationUtil;
	
	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskStatusService taskStatusService;

	@GetMapping("/index")
	public String getHome(Model theModel) {
		isMsgShow = applicationUtil.showMessage(theModel);
		CrmUser currentUser = authenticationUtil.getAccount();
		List<Task> tasks = currentUser.getTasks();
		List<Task> tasksNotStarted = applicationUtil.splitTaskByStatus(tasks, NOT_STARTED);
		List<Task> tasksInProgress = applicationUtil.splitTaskByStatus(tasks, IN_PROGRESS);
		List<Task> tasksCompleted = applicationUtil.splitTaskByStatus(tasks, COMPLETED);
		int taskCount = tasks.size();
		theModel.addAttribute("currentUser", currentUser);
		theModel.addAttribute("tasks", tasks);
		theModel.addAttribute("tasksNotStarted", tasksNotStarted);
		theModel.addAttribute("tasksInProgress", tasksInProgress);
		theModel.addAttribute("tasksCompleted", tasksCompleted);
		theModel.addAttribute("notStartedPercent", 
					taskCount == 0 ? 0 : tasksNotStarted.size() * 100 / taskCount);
		theModel.addAttribute("inProgressPercent", 
					taskCount == 0 ? 0 : tasksInProgress.size() * 100 / taskCount);
		theModel.addAttribute("completedPercent", 
					taskCount == 0 ? 0 : tasksCompleted.size() * 100 / taskCount);
		return "index";
	}

	@GetMapping("/profile/task/edit")
	public String taskStatusUpdate(@RequestParam("id") int taskId, Model theModel) {
		CrmUser currentUser = authenticationUtil.getAccount();
		Task task = taskService.getTask(taskId);
		if (task.getDoerId() != currentUser.getId()) {
			isMsgShow = true;
			msg = "Công việc không thuộc quyền quản lý của bạn.";
			return "redirect:/index";
		}
		List<TaskStatus> allStatus = taskStatusService.getAllStatus();
		theModel.addAttribute("currentUser", currentUser);
		theModel.addAttribute("task", task);
		theModel.addAttribute("allStatus", allStatus);
		return "status-update";
	}
	
	@PutMapping("/profile/task/edit/save")
	public String profileTaskEditSave(@ModelAttribute("task") Task task) {
		taskService.save(task);
		isMsgShow = true;
		msg = "Lưu công việc thành công.";
		return "redirect:/index";
	}
}






