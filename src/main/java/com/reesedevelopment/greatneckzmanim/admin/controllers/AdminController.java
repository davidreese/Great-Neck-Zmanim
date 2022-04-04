package com.reesedevelopment.greatneckzmanim.admin.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    @GetMapping("/admin/dashboard")
    public ModelAndView dashbaord() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/dashboard");

        return mv;
    }

    @GetMapping("/admin")
    public ModelAndView admin(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) boolean logout) {
        return dashbaord();
    }

    @GetMapping("/admin/logout")
    public ModelAndView logout(@RequestParam(value = "error", required = false) String error) {
        return new LoginController().login(error, true);
    }

    @GetMapping("/admin/organizations")
    public ModelAndView organizations() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/organizations");
        return mv;
    }
}
