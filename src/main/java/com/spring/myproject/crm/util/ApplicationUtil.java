package com.spring.myproject.crm.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.spring.myproject.crm.model.Task;

import static com.spring.myproject.crm.util.Notification.*;

@Component
public class ApplicationUtil {

	public List<Task> splitTaskByStatus(List<Task> allTasks, int statusId) {
		List<Task> result = allTasks.stream()
				.filter(task -> task.getTaskStatusId() == statusId)
				.collect(Collectors.toList());
		return result;
	}

	public boolean showMessage(Model theModel) {
		if (isMsgShow) {
			theModel.addAttribute("message", msg);
			theModel.addAttribute("flag", isMsgShow);
		}
		return false;
	}
}
