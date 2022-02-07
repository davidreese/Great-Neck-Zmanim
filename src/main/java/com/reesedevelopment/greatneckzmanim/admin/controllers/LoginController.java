package com.reesedevelopment.greatneckzmanim.admin.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/admin/login")
    public ModelAndView login(@RequestParam(value = "error",required = false) String error, @RequestParam(value = "logout",	required = false) boolean logout) {
        if (logout) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/login");

        return mv;
    }
}
