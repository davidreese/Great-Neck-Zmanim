package com.reesedevelopment.greatneckzmanim.admin.controllers;

import com.reesedevelopment.greatneckzmanim.LoginAttemptService;
import com.reesedevelopment.greatneckzmanim.admin.structure.user.GNZUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class LoginController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;
    @GetMapping("/admin/login")
    public ModelAndView login(@RequestParam(value = "error",required = false) String error, @RequestParam(value = "logout",	required = false) boolean logout) {
        System.out.println("LoginController.login() called");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/login");

        if (logout) {
            System.out.println("Logging out...");
            SecurityContextHolder.getContext().setAuthentication(null);
            System.out.println("Logout Successful");
            mv.getModel().put("logout", true);
        }

        if (error != null && !error.isEmpty()) {
            mv.getModel().put("error", error);
        }

        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            mv.getModel().put("blocked", true);
        } else {
            mv.getModel().put("blocked", false);
        }

        return mv;
    }

    @GetMapping("/login")
    public ModelAndView loginShortcut(@RequestParam(value = "error",required = false) String error, @RequestParam(value = "logout",	required = false) boolean logout) {
        return login(error, logout);
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
