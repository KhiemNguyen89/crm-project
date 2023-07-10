package com.spring.myproject.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myproject.crm.util.ApplicationUtil;
import static com.spring.myproject.crm.util.Notification.*;

@Controller
@RequestMapping("")
public class ApplicationController {

    @Autowired
    private ApplicationUtil applicationUtil;

    @GetMapping("")
    public String getLogin(Model theModel) {
        isMsgShow = applicationUtil.showMessage(theModel);
        return "login";
    }

    @GetMapping("/403")
    public String forbiden() {
        return "403";
    }
}
