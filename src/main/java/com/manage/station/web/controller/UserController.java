package com.manage.station.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/monitor")
    String manageUser(Model model) {

        return "user/monitor";
    }

    @GetMapping("/manage-station")
    String manageRequest(Model model) {

        return "user/manage-station";
    }

    @GetMapping("/report")
    String report(Model model) {

        return "user/report";
    }

}
