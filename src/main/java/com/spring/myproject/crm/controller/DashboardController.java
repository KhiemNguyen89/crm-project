package com.spring.myproject.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.model.Task;
import com.spring.myproject.crm.service.ProjectService;
import com.spring.myproject.crm.service.TaskService;
import com.spring.myproject.crm.util.ApplicationUtil;
import com.spring.myproject.crm.util.AuthenticationUtil;

import static com.spring.myproject.crm.constant.ApplicationConstant.TaskStatus.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;
    
    @GetMapping("")
    public String dashboard(Model theModel) {
        CrmUser user = authenticationUtil.getAccount();
        List<Task> tasks = taskService.getAllTasks();
        int tasksNotStarted = applicationUtil.splitTaskByStatus(tasks, NOT_STARTED).size();
        int tasksInProgress = applicationUtil.splitTaskByStatus(tasks, IN_PROGRESS).size();
        int tasksCompleted = applicationUtil.splitTaskByStatus(tasks, COMPLETED).size();
        int notStartedPercent = tasks.size() == 0 ? 0 : tasksNotStarted * 100 / tasks.size();
        int inProgressPercent = tasks.size() == 0 ? 0 : tasksInProgress * 100 / tasks.size();
        int completedPercent = tasks.size() == 0 ? 0 : tasksCompleted * 100 / tasks.size();
        int allProjects = projectService.getAllProjects().size();
        theModel.addAttribute("currentUser", user);
        theModel.addAttribute("allTasks", tasks.size());
        theModel.addAttribute("allProjects", allProjects);
        theModel.addAttribute("tasksNotStarted", tasksNotStarted);
        theModel.addAttribute("tasksInProgress", tasksInProgress);
        theModel.addAttribute("tasksCompleted", tasksCompleted);
        theModel.addAttribute("notStartedPercent", notStartedPercent);
        theModel.addAttribute("inProgressPercent", inProgressPercent);
        theModel.addAttribute("completedPercent", completedPercent);
        return "dashboard";
    }
}
