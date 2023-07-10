package com.spring.myproject.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.model.Project;
import com.spring.myproject.crm.model.Task;
import com.spring.myproject.crm.service.ProjectService;
import com.spring.myproject.crm.service.TaskService;
import com.spring.myproject.crm.service.UserService;
import com.spring.myproject.crm.util.ApplicationUtil;
import com.spring.myproject.crm.util.AuthenticationUtil;

import static com.spring.myproject.crm.util.Notification.*;
import static com.spring.myproject.crm.constant.ApplicationConstant.RoleId.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;
    
    @GetMapping("")
    public String project(Model theModel) {
        CrmUser user = authenticationUtil.getAccount();
        List<Project> allProjects = projectService.getAllProjects();
        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("projects", allProjects);
        isMsgShow = applicationUtil.showMessage(theModel);
        return "project";
    }

    @GetMapping("/edit")
    public String projectEdit(@RequestParam("id") int projectId, Model theModel) {
        CrmUser user = authenticationUtil.getAccount();
        Project project = projectService.getProject(projectId);
        List<CrmUser> allUsers = userService.getAllUsers();
        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("project", project);
        theModel.addAttribute("allUsers", allUsers);
        return "project-edit";
    }

    @GetMapping("/add")
    public String projectAdd(Model theModel) {
        CrmUser user = authenticationUtil.getAccount();
        Project project = new Project();
        List<CrmUser> allUsers = userService.getAllUsers();
        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("project", project);
        theModel.addAttribute("allUsers", allUsers);
        return "project-edit";
    }

    @GetMapping("/details")
    public String projectDetails(@RequestParam("id") int projectId, Model theModel) {
        CrmUser user = authenticationUtil.getAccount();
        Project project = projectService.getProject(projectId);
        if (user.getRoleId() == ADMIN || project.getOriginatorId() == user.getId()) {
            CrmUser originator = userService.getUser(project.getOriginatorId());
            List<Task> allTasks = taskService.getTasksByProjectId(projectId);
            int tasksNotStarted = applicationUtil.splitTaskByStatus(allTasks, 1).size();
            int tasksInProgress = applicationUtil.splitTaskByStatus(allTasks, 2).size();
            int tasksCompleted = applicationUtil.splitTaskByStatus(allTasks, 3).size();
            int notStartedPercent = allTasks.size() == 0 ? 0 : tasksNotStarted * 100 / allTasks.size();
            int inProgressPercent = allTasks.size() == 0 ? 0 : tasksInProgress * 100 / allTasks.size();
            int completedPercent = allTasks.size() == 0 ? 0 : tasksCompleted * 100 / allTasks.size();
            theModel.addAttribute("currentUser", user);
            theModel.addAttribute("project", project);
            theModel.addAttribute("originator", originator);
            theModel.addAttribute("allTasks", allTasks);
            theModel.addAttribute("tasksNotStarted", tasksNotStarted);
            theModel.addAttribute("tasksInProgress", tasksInProgress);
            theModel.addAttribute("tasksCompleted", tasksCompleted);
            theModel.addAttribute("notStartedPercent", notStartedPercent);
            theModel.addAttribute("inProgressPercent", inProgressPercent);
            theModel.addAttribute("completedPercent", completedPercent);
            return "project-details";
        } else {
            isMsgShow = true;
            msg = "Dự án không thuộc quyền quản lý của bạn.";
            return "redirect:/project";
        }
    }

    @PutMapping("/edit/save")
    public String projectEditSave(@ModelAttribute("project") Project project) {
        projectService.save(project);
        return "redirect:/project";
    }

    @DeleteMapping("/delete")
    public String projectDelete(@RequestParam("id") int projectId) {
        Project project = projectService.getProject(projectId);
        if (project.getTasks().size() == 0) {
            projectService.deleteProject(projectId);
        } else {
            msg = "Dự án đang có công việc, không thể xóa!";
            isMsgShow = true;
        }
        return "redirect:/project";
    }
}
