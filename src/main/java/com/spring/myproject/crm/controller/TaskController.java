package com.spring.myproject.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.myproject.crm.service.ProjectService;
import com.spring.myproject.crm.service.TaskService;
import com.spring.myproject.crm.service.TaskStatusService;
import com.spring.myproject.crm.service.UserService;
import com.spring.myproject.crm.util.ApplicationUtil;
import com.spring.myproject.crm.util.AuthenticationUtil;
import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.model.Project;
import com.spring.myproject.crm.model.Task;
import com.spring.myproject.crm.model.TaskStatus;

import static com.spring.myproject.crm.util.Notification.*;
import static com.spring.myproject.crm.constant.ApplicationConstant.RoleId.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;
    
    @GetMapping("")
    public String task(Model theModel) {
        isMsgShow = applicationUtil.showMessage(theModel);
        CrmUser user = authenticationUtil.getAccount();
        List<Task> tasks = taskService.getAllTasks();
        List<Project> projects = projectService.getAllProjects();
        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("allTasks", tasks);
        theModel.addAttribute("allProjects", projects);
        return "task";
    }

    @GetMapping("/add")
    public String addTask(Model theModel, @RequestParam("projectId") int projectId) {
        CrmUser user = authenticationUtil.getAccount();
        Project project = projectService.getProject(projectId);
        if (project.getOriginatorId() == user.getId()) {
            Task newTask = new Task();
            List<CrmUser> allUsers = userService.getAllUsers();
            newTask.setProjectId(projectId);
            List<TaskStatus> allStatus = taskStatusService.getAllStatus();
            theModel.addAttribute("currentUser", user);
            theModel.addAttribute("task", newTask);
            theModel.addAttribute("allStatus", allStatus);
            theModel.addAttribute("allUsers", allUsers);
            return "task-add";
        } else {
            isMsgShow = true;
            msg = "Dự án không thuộc quyền quản lý của bạn.";
            return "redirect:/task";
        }
    }

    @GetMapping("/edit")
    public String taskEdit(Model theModel, @RequestParam("id") int taskId) {
        CrmUser currentUser = authenticationUtil.getAccount();
        Task task = taskService.getTask(taskId);
        if (currentUser.getRoleId() == MANAGER) {
            if (currentUser.getId() != task.getProject().getOriginatorId()) {
                isMsgShow = true;
                msg = "Công việc không thuộc dự án của bạn, không thể chỉnh sửa.";
                return "redirect:/task";
            }
        }
        List<CrmUser> allUsers = userService.getAllUsers();
        List<TaskStatus> allStatus = taskStatusService.getAllStatus();
        theModel.addAttribute("currentUser", currentUser);
        theModel.addAttribute("task", task);
        theModel.addAttribute("allUsers", allUsers);
        theModel.addAttribute("allStatus", allStatus);
        return "task-edit";
    }

    @RequestMapping(value="/edit/save", method={ RequestMethod.POST, RequestMethod.PUT })
    public String taskEditSave(@ModelAttribute("task") Task task) {
        isMsgShow = true;
        if (task == null) {
            msg = "Không thể lưu.";
        } else {
            taskService.save(task);
            msg = "Lưu công việc thành công";
        }
        return "redirect:/task";
    }

    @RequestMapping(value="/delete", method= { RequestMethod.GET, RequestMethod.DELETE })
    public String taskDelete(@RequestParam("id") int taskId) {
        isMsgShow = true;
        CrmUser currentUser = authenticationUtil.getAccount();
        Task task = taskService.getTask(taskId);
        if (currentUser.getRoleId() == MANAGER) {
            if (currentUser.getId() != task.getProject().getOriginatorId()) {
                msg = "Công việc không thuộc dự án của bạn, không thể xóa.";
                return "redirect:/task";
            }
        }
        msg = "Xoá công việc thành công.";
        taskService.deleteTask(taskId);
        return "redirect:/task";
    }
}
