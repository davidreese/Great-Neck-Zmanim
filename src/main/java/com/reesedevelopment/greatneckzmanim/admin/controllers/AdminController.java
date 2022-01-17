package com.reesedevelopment.greatneckzmanim.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @GetMapping("/admin/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/login");

        return mv;
    }

    @GetMapping("/admin/dashboard")
    public ModelAndView dashbaord() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/dashboard");

        return mv;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        return login();
    }
}
