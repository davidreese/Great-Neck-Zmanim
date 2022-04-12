package com.reesedevelopment.greatneckzmanim.admin.controllers;

import com.reesedevelopment.greatneckzmanim.admin.structure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AdminController {
    @Autowired
    private GNZUserDAO gnzUserDAO;

    @Autowired
    private GNZOrganizationDAO gnzOrganizationDAO;

//    @Autowired
//    private GNZAcc gnzOrganizationDAO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy | hh:mm aa");

    @GetMapping("/admin/dashboard")
    public ModelAndView dashbaord() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/dashboard");

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

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
        mv.addObject("organizations", gnzOrganizationDAO.getAll());
        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));
        return mv;
    }

    @RequestMapping(value = "/admin/new-organization", method = RequestMethod.GET)
    public ModelAndView addOrganization(@RequestParam(value = "success", required = false) boolean success, @RequestParam(value = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/new-organization");
        Date today = new Date();
//        mv.addObject("date", dateFormat.format(today));
//        mv.addObject("success", success);
//        mv.addObject("error", error);

        mv.getModel().put("date", dateFormat.format(today));
        mv.getModel().put("success", success);
        mv.getModel().put("error", error);

        return mv;
    }

    @RequestMapping(value = "/admin/create-organization", method = RequestMethod.POST)
    public ModelAndView createOrganization(@RequestParam(value = "name", required = true) String name,
                                           @RequestParam(value = "address", required = true) String address,
                                           @RequestParam(value = "username", required = true) String username,
                                           @RequestParam(value = "email", required = true) String email,
                                           @RequestParam(value = "password", required = true) String password,
                                           @RequestParam(value = "cpassword", required = true) String cpassword) {

        System.out.println("Validating input data...");

        if (name.isEmpty() || address.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
            System.out.println("Sorry, fields cannot be left blank.");
            return addOrganization(false, "Sorry, fields cannot be left blank.");
        }

        if (!password.equals(cpassword)) {
            System.out.println("Sorry, these passwords do not match.");
            return addOrganization(false, "Sorry, passwords do not match.");
        }

//        check if username is valid
        String usernameRegex = "^[A-Za-z]\\w{5,29}$";
        Pattern usernamePatter = Pattern.compile(usernameRegex);
        Matcher m = usernamePatter.matcher(username);
        if (!m.matches()) {
            System.out.println("Sorry, this username is not valid.");
            return addOrganization(false,"Sorry, the username must be 6-30 characters, only contain letters and numbers, and start with a letter.");
        }

//        check if username already exists
        if (gnzUserDAO.findUserAccount(username) != null) {
            System.out.println("Sorry, this username already exists.");
            return addOrganization(false,"Sorry, this username already exists.");
        }

//        check if email is valid
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher m2 = emailPattern.matcher(email);
        if (!m2.matches()) {
            System.out.println("Sorry, this email address is not valid.");
            return addOrganization(false,"Sorry, this email address is invalid.");
        }

//        check if password is valid
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher m3 = passwordPattern.matcher(password);
        if (!m3.matches()) {
            System.out.println("Sorry, this password is not valid.");
            return addOrganization(false,"Sorry, the password must be at least 8 characters, contain at least one letter and one number.");
        }

        System.out.println("Creating organization...");

        GNZOrganization organization = new GNZOrganization(name, address);

        if  (this.gnzOrganizationDAO.save(organization)) {
            System.out.println("Organization created successfully.");
            System.out.println("Creating account for organization...");

            GNZUser user = new GNZUser(username, email, Encrypter.encrytedPassword(password), organization.getId(), Role.USER.getId());
            if (this.gnzUserDAO.save(user)) {
                return addOrganization(true, null);
            } else {
                System.out.println("Account creation failed. Deleting organization from database...");
//                TODO: REMOVE ORGANIZATION FROM DATABASE

                return addOrganization(false, "Sorry, there was an error creating the account.");
            }
        } else {
            System.out.println("Organization creation failed.");
            return addOrganization(false,"Sorry, there was an error creating the organization.");
        }
    }
}
