package com.reesedevelopment.greatneckzmanim.admin.controllers;

import com.reesedevelopment.greatneckzmanim.admin.structure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()));
    }

    private boolean isUser() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.getName()));
    }

//    private List<String> getOrganizationsWithAccess() {
//        if (isAdmin()) {
//            return null;
//        } else {
//            return gnzUserDAO.getOrganizationsWithAccess(SecurityContextHolder.getContext().getAuthentication().getName());
//        }
//    }

    @GetMapping("/admin/dashboard")
    public ModelAndView dashbaord() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/dashboard");

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        return mv;
    }

    @GetMapping("/admin")
    public ModelAndView admin(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) boolean logout) {
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
    public ModelAndView organizations(String success, String error) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/organizations");
        mv.addObject("organizations", gnzOrganizationDAO.getAll());
        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));
        mv.getModel().put("success", success);
        mv.getModel().put("error", error);
        return mv;
    }

    @RequestMapping(value = "/admin/new-organization", method = RequestMethod.GET)
    public ModelAndView addOrganization(@RequestParam(value = "success", required = false) boolean success,
                                        @RequestParam(value = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/new-organization");

        Date today = new Date();
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
                                           @RequestParam(value = "site-url", required = false) String siteURIString,
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
        if (gnzUserDAO.find(username) != null) {
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

//        check if url string is valid
        URI siteURI = null;
        if (siteURIString != null && !siteURIString.isEmpty()) {
            try {
                siteURI = new URI(siteURIString);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println("Sorry, this site URI is not valid.");
                return addOrganization(false, "Sorry, this site URI is invalid.");
            }
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

        GNZOrganization organization = new GNZOrganization(name, address, siteURI);

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

    @RequestMapping(value = "/admin/new-account", method = RequestMethod.GET)
    public ModelAndView addAccount(@RequestParam(value = "success", required = false) boolean success,
                                   @RequestParam(value = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/new-account");

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        mv.getModel().put("success", success);
        mv.getModel().put("error", error);

        return mv;
    }

    @GetMapping("/admin/my-account")
    public ModelAndView myAccount() {
        ModelAndView mv = new ModelAndView();

//        TODO: ENSURE SECURITY
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()))) {
            mv.setViewName("admin/my-account");
        } else {
            mv.setViewName("admin/my-account");
        }

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        return mv;
    }

    @GetMapping("/admin/organization")
    public ModelAndView organization(@RequestParam(value = "id", required = false) String id,
                                     String mainSuccessMessage,
                                     String mainErrorMessage,
                                     String updateErrorMessage,
                                     String addAccountErrorMessage) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/organization");

        if (mainSuccessMessage != null && !mainSuccessMessage.isEmpty()) {
            mv.addObject("main-success", mainSuccessMessage);
        }

        if (mainErrorMessage != null && !mainErrorMessage.isEmpty()) {
            mv.addObject("main-error", mainErrorMessage);
        }

        if (updateErrorMessage != null && !updateErrorMessage.isEmpty()) {
            mv.addObject("updateError", updateErrorMessage);
        }

        if (addAccountErrorMessage != null && !addAccountErrorMessage.isEmpty()) {
            mv.addObject("add-account-error", addAccountErrorMessage);
        }



//        check permissions
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()))) {
//                find organization for id
            GNZOrganization organization = this.gnzOrganizationDAO.find(id);
            if (organization != null) {
                mv.getModel().put("organization", organization);
            } else {
                System.out.println("Organization not found.");
//                    TODO: HANDLE ERROR CORRECTLY
                throw new Exception("Organization not found.");
            }

            List<GNZUser> associatedUsers = this.gnzOrganizationDAO.getUsersForOrganization(organization);
            mv.addObject("associatedusers", associatedUsers);
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()))) {
//              check if user is associated with organization
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            GNZUser user = this.gnzUserDAO.find(username);
            String associatedOrganizationId = user.getOrganizationId();
            if (!associatedOrganizationId.equals(id)) {
                System.out.println("You do not have permission to view this organization.");
                throw new AccessDeniedException("You do not have permission to view this organization.");
            } else {
//                find organization for id
                GNZOrganization organization = this.gnzOrganizationDAO.find(id);
                if (organization != null) {
                    mv.getModel().put("organization", organization);
                } else {
                    System.out.println("Organization not found.");
//                    TODO: HANDLE ERROR CORRECTLY
                    throw new Exception("Organization not found.");
                }


                List<GNZUser> associatedUsers = this.gnzOrganizationDAO.getUsersForOrganization(organization);
                mv.addObject("associated-users", associatedUsers);
            }
        }


//        mv.addObject("organization", organization);

        return mv;
    }

    @RequestMapping(value = "/admin/update-organization", method = RequestMethod.POST)
    public ModelAndView updateOrganization(@RequestParam(value = "id", required = true) String id,
                                           @RequestParam(value = "name", required = true) String name,
                                           @RequestParam(value = "address", required = false) String address,
                                           @RequestParam(value = "site-url", required = false) String siteURIString) throws Exception {

//        validate input
        if (name == null || name.isEmpty()) {
            return organization(id, null, null, "Organization name cannot be empty.", null);
        }

        URI siteURI = null;
        if (siteURIString != null && !siteURIString.isEmpty()) {
            try {
                siteURI = new URI(siteURIString);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return organization(id, null, null, "Invalid website URL.", null);
            }
        }

        GNZOrganization organization = new GNZOrganization(id, name, address, siteURI);

//        check permissions
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()))) {
            if (this.gnzOrganizationDAO.update(organization)) {
                System.out.println("Organization updated successfully.");
                return organization(id, "Successfully updated the organization details.", null, null, null);
            } else {
                System.out.println("Organization update failed.");
                return organization(id, null, null, "Sorry, the update failed.", null);
            }
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.getName()))) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            GNZUser user = this.gnzUserDAO.find(username);
            String associatedOrganizationId = user.getOrganizationId();
            if (!associatedOrganizationId.equals(id)) {
                System.out.println("You do not have permission to view this organization.");
                throw new AccessDeniedException("You do not have permission to view this organization.");
            } else {
                if (this.gnzOrganizationDAO.update(organization)) {
                    System.out.println("Organization updated successfully.");
                    return organization(id, "Successfully updated the organization details.", null, null, null);
                } else {
                    System.out.println("Organization update failed.");
                    return organization(id, null, "Sorry, the update failed.", null, null);
                }
            }
        } else {
            throw new AccessDeniedException("You do not have permission to view this organization.");
        }
    }

    @RequestMapping(value = "/admin/delete-organization")
    public ModelAndView deleteOrganization(@RequestParam(value = "id", required = true) String id) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()))) {
//            get organization and check if it exists
            GNZOrganization organization = this.gnzOrganizationDAO.find(id);
            if (organization != null) {
                if (this.gnzOrganizationDAO.delete(organization)) {
                    System.out.println("Organization deleted successfully.");
                    return organizations("Successfully deleted the organization.", null);
                } else {
                    System.out.println("Organization delete failed.");
                    return organizations(null, "Sorry, the organization could not be deleted.");
                }
            } else {
                System.out.println("Organization does not exist. Failed to delete.");
                return organizations(null, "Sorry, the organization could not be deleted.");
            }
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.getName()))) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            GNZUser user = this.gnzUserDAO.find(username);
            String associatedOrganizationId = user.getOrganizationId();
            if (!associatedOrganizationId.equals(id)) {
                System.out.println("You do not have permission to view this organization.");
                throw new AccessDeniedException("You do not have permission to view this organization.");
            } else {
//                get organization and check if it exists
                GNZOrganization organization = this.gnzOrganizationDAO.find(id);
                if (organization != null) {
                    if (this.gnzOrganizationDAO.delete(organization)) {
                        System.out.println("Organization deleted successfully.");
                        return organizations("Successfully deleted the organization.", null);
                    } else {
                        System.out.println("Organization delete failed.");
                        return organizations(null, "Sorry, the delete failed.");
                    }
                } else {
                    System.out.println("Organization does not exist. Failed to delete.");
                    return organizations(null, "Sorry, the organization could not be deleted.");
                }
            }
        } else {
            throw new AccessDeniedException("You do not have permission to view this organization.");
        }
    }
}
