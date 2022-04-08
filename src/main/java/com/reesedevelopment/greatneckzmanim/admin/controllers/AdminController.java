package com.reesedevelopment.greatneckzmanim.admin.controllers;

import com.reesedevelopment.greatneckzmanim.admin.users.GNZOrganization;
import com.reesedevelopment.greatneckzmanim.admin.users.GNZUserDAO;
import com.reesedevelopment.greatneckzmanim.admin.users.GNZOrganizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AdminController {
    @Autowired
    private GNZUserDAO gnzUserDAO;

    @Autowired
    private GNZOrganizationDAO gnzOrganizationDAO;

    @Autowired
//    private GNZAcc gnzOrganizationDAO;

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

//    @GetMapping("/admin/organizations")
//    public ModelAndView organizations() {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("admin/organizations");
//        mv.addObject("organizations", gnzUserDAO.findAll());
//        return mv;
//    }

    @GetMapping("/admin/organizations")
    public ModelAndView organizations() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/organizations");
        mv.addObject("organizations", gnzOrganizationDAO.findAll());
        return mv;
    }

    @GetMapping("/admin/new-organization")
    public ModelAndView addOrganization(@RequestParam(value = "success", required = false) boolean success, @RequestParam(value = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/new-organization");
        return mv;
    }
}
