package com.spring.myproject.crm.controller;

import static com.spring.myproject.crm.constant.ApplicationConstant.TaskStatus.*;
import static com.spring.myproject.crm.util.Notification.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.myproject.crm.model.CrmUser;
import com.spring.myproject.crm.model.Role;
import com.spring.myproject.crm.model.Task;
import com.spring.myproject.crm.service.RoleService;
import com.spring.myproject.crm.service.UserService;
import com.spring.myproject.crm.util.ApplicationUtil;
import com.spring.myproject.crm.util.AuthenticationUtil;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthenticationUtil authenticationUtil;

	@Autowired
	private ApplicationUtil applicationUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("")
	public String user(Model theModel) {
		CrmUser currentUser = authenticationUtil.getAccount();
		List<CrmUser> allUsers = userService.getAllUsers();
		theModel.addAttribute("currentUser", currentUser);
		theModel.addAttribute("allUsers", allUsers);
		return "user";
	}

	@GetMapping("/details")
	public String userDetails(Model theModel, @RequestParam("id") int userId) {
		CrmUser currentUser = authenticationUtil.getAccount();
		CrmUser user = userService.getUser(userId);
		List<Task> tasks = user.getTasks();
		List<Task> tasksNotStarted = applicationUtil.splitTaskByStatus(tasks, NOT_STARTED);
		List<Task> tasksInProgress = applicationUtil.splitTaskByStatus(tasks, IN_PROGRESS);
		List<Task> tasksCompleted = applicationUtil.splitTaskByStatus(tasks, COMPLETED);
		int taskCount = tasks.size();
		theModel.addAttribute("currentUser", currentUser);
		theModel.addAttribute("user", user);
		theModel.addAttribute("tasks", tasks);
		theModel.addAttribute("tasksNotStarted", tasksNotStarted);
		theModel.addAttribute("tasksInProgress", tasksInProgress);
		theModel.addAttribute("tasksCompleted", tasksCompleted);
		theModel.addAttribute("notStartedPercent",
				tasks.size() == 0 ? 0 : tasksNotStarted.size() * 100 / taskCount);
		theModel.addAttribute("inProgressPercent",
				tasks.size() == 0 ? 0 : tasksInProgress.size() * 100 / taskCount);
		theModel.addAttribute("completedPercent",
				tasks.size() == 0 ? 0 : tasksCompleted.size() * 100 / taskCount);
		return "user-details";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/add")
    public String userAdd(Model theModel) {
        isMsgShow = applicationUtil.showMessage(theModel);
		CrmUser currentUser = authenticationUtil.getAccount();
		theModel.addAttribute("currentUser", currentUser);
        theModel.addAttribute("newUser", new CrmUser());
        return "user-add";
    }

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
    public String userAddProcess(@ModelAttribute("newUser") @Valid CrmUser newUser, 
			BindingResult bindingResult) {

		isMsgShow = true;

        if (bindingResult.hasErrors()) {
            isMsgShow = false;
            return "user-add";
        } else if (userService.getUser(newUser.getEmail()) != null) {
			msg = "Tài khoản email này đã được đăng ký!";
			return "redirect:/user/add";
		} else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userService.save(newUser);
            msg = "Thêm người dùng mới thành công!";
            return "redirect:/user";
        }
    }

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit")
	public String userEdit(Model theModel, @RequestParam("id") int userId) {
		CrmUser currentUser = authenticationUtil.getAccount();
		CrmUser user = userService.getUser(userId);
		List<Role> allRole = roleService.getAllRole();
		theModel.addAttribute("currentUser", currentUser);
		theModel.addAttribute("user", user);
		theModel.addAttribute("allRole", allRole);
		return "user-edit";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/edit/save")
	public String userEditSave(@ModelAttribute("user") CrmUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return "redirect:/user";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/delete", method={ RequestMethod.GET, RequestMethod.DELETE })
	public String userDelete(@RequestParam("id") int userId) {
		isMsgShow = true;
		CrmUser user = userService.getUser(userId);
		if (user.getTasks().size() == 0) {
			userService.deleteUser(userId);
			msg = "Xóa người dùng thành công.";
		} else {
			msg = "Tài khoản đang có công việc, không thể xóa.";
		}
		return "redirect:/user";
	}

	@GetMapping("/changePassword")
	public String userChangePassword(Model theModel) {
		isMsgShow = applicationUtil.showMessage(theModel);
		CrmUser currentUser = authenticationUtil.getAccount();
		theModel.addAttribute("currentUser", currentUser);
		return "password-change";
	}

	@PostMapping("/changePassword")
	public String userPasswordSave(@RequestParam("password") String pwd,
			@RequestParam("newPassword") String newPwd,
			@RequestParam("verifyPassword") String verifyPwd) {
		isMsgShow = true;
		CrmUser currentUser = authenticationUtil.getAccount();
		if (!passwordEncoder.matches(pwd, currentUser.getPassword())) {
			msg = "Mật khẩu không chính xác";
			return "redirect:/user/changePassword";
		} else {
			if (!newPwd.equals(verifyPwd)) {
				msg = "Mật khẩu xác nhận không trùng khớp";
				return "redirect:/user/changePassword";
			} else {
				msg = "Đổi mật khẩu thành công";
				currentUser.setPassword(passwordEncoder.encode(verifyPwd));
				userService.save(currentUser);
				return "redirect:/index";
			}
		}
	}
}
