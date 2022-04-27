package com.reesedevelopment.greatneckzmanim.admin.controllers;

import com.reesedevelopment.greatneckzmanim.admin.structure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.reesedevelopment.greatneckzmanim.admin.structure.Role.ADMIN;

@Controller
public class AdminController {
    @Autowired
    private GNZUserDAO gnzUserDAO;

    @Autowired
    private GNZOrganizationDAO gnzOrganizationDAO;

    @Autowired
    private GNZLocationDAO gnzLocationDAO;

//    @Autowired
//    private GNZAcc gnzOrganizationDAO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy | hh:mm aa");

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN.getName()));
    }

    private boolean isUser() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.getName()));
    }

    private GNZUser getCurrentUser() {
        return this.gnzUserDAO.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private boolean isSuperAdmin() {
        GNZUser user = getCurrentUser();
        return user.getOrganizationId() == null && user.role().equals(ADMIN);
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

        mv.addObject("user", getCurrentUser());

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
    public ModelAndView organizations(String successMessage, String errorMessage) {
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("You are not authorized to access this page");
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/organizations");
        mv.addObject("organizations", gnzOrganizationDAO.getAll());

        mv.addObject("user", getCurrentUser());

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        mv.getModel().put("success", successMessage);
        mv.getModel().put("error", errorMessage);
        return mv;
    }

    @RequestMapping(value = "/admin/new-organization", method = RequestMethod.GET)
    public ModelAndView addOrganization(@RequestParam(value = "success", required = false) boolean success,
                                        @RequestParam(value = "error", required = false) String error) {
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("You are not authorized to access this page.");
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/new-organization");

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        mv.getModel().put("success", success);
        mv.getModel().put("error", error);

        mv.addObject("user", getCurrentUser());

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
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("You are not authorized to access this page.");
        }

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
        if (gnzUserDAO.findByName(username) != null) {
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

            GNZUser user = new GNZUser(username, email, Encrypter.encrytedPassword(password), organization.getId(), ADMIN.getId());
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

    @RequestMapping(value = "/admin/accounts", method = RequestMethod.GET)
    public ModelAndView accounts(String successMessage, String errorMessage) {
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("You are not authorized to access this page.");
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/accounts");

        List<GNZUser> users = this.gnzUserDAO.getAll();
        mv.addObject("accounts", users);

        Map<String, String> organizationNames = new HashMap<>();
        for (GNZUser user : users) {
            GNZOrganization organization = this.gnzOrganizationDAO.findById(user.getOrganizationId());
            String organizationDisplayName = organization == null ? "" : organization.getName();
            organizationNames.put(user.getId(), organizationDisplayName);
        }
        mv.addObject("organizationNames", organizationNames);

        mv.addObject("user", getCurrentUser());

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        mv.getModel().put("successmessage", successMessage);
        mv.getModel().put("errormessahe", errorMessage);

        return mv;
    }

    @GetMapping("/admin/account")
    public ModelAndView account(@RequestParam(value = "id", required = true) String id,
                                String successMessage,
                                String errorMessage) {
        ModelAndView mv = new ModelAndView();

//        TODO: ENSURE SECURITY
        if (isSuperAdmin()) {
            mv.setViewName("admin/account");

            GNZUser queriedUser = this.gnzUserDAO.findById(id);
            System.out.println("Queried user: " + queriedUser);
            mv.addObject("queriedaccount", queriedUser);

            GNZOrganization associatedOrganization = this.gnzOrganizationDAO.findById(queriedUser.getOrganizationId());
            System.out.println("Associated organization: " + associatedOrganization);
            mv.addObject("associatedorganization", associatedOrganization);

            mv.addObject("user", getCurrentUser());
        } else if (isAdmin()) {
            GNZUser user = getCurrentUser();
            GNZUser queriedUser = this.gnzUserDAO.findById(id);
            System.out.println("Queried user: " + queriedUser);

            if (user != null && user.getOrganizationId().equals(queriedUser.getOrganizationId())) {
                mv.setViewName("admin/account");

                mv.addObject("queriedaccount", queriedUser);

                GNZOrganization associatedOrganization = this.gnzOrganizationDAO.findById(queriedUser.getOrganizationId());
                System.out.println("Associated organization: " + associatedOrganization);
                mv.addObject("associatedorganization", associatedOrganization);

                mv.addObject("user", user);
            } else {
                throw new AccessDeniedException("You are not authorized to view this account.");
            }
        } else if (isUser()) {
            GNZUser user = getCurrentUser();

            if (!user.getId().equals(id)) {
                throw new AccessDeniedException("You are not authorized to view this account.");
            }

            GNZUser queriedUser = this.gnzUserDAO.findById(id);
            System.out.println("Queried user: " + queriedUser);

            if (user.getOrganizationId().equals(queriedUser.getOrganizationId())) {
                mv.setViewName("admin/account");

                mv.addObject("queriedaccount", queriedUser);

                GNZOrganization associatedOrganization = this.gnzOrganizationDAO.findById(queriedUser.getOrganizationId());
                System.out.println("Associated organization: " + associatedOrganization);
                mv.addObject("associatedorganization", associatedOrganization);

                mv.addObject("user", user);
            } else if (isUser()) {} else {
                throw new AccessDeniedException("You are not authorized to view this account.");
            }
        } else {
            throw new AccessDeniedException("You are not authorized to view this account.");
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
            mv.addObject("mainSuccess", mainSuccessMessage);
        }

        if (mainErrorMessage != null && !mainErrorMessage.isEmpty()) {
            mv.addObject("mainError", mainErrorMessage);
        }

        if (updateErrorMessage != null && !updateErrorMessage.isEmpty()) {
            mv.addObject("updateError", updateErrorMessage);
        }

        if (addAccountErrorMessage != null && !addAccountErrorMessage.isEmpty()) {
            mv.addObject("addAccountError", addAccountErrorMessage);
        }

//        check permissions
        if (isAdmin()) {
//                find organization for id
            GNZOrganization organization = this.gnzOrganizationDAO.findById(id);
            if (organization != null) {
                mv.getModel().put("organization", organization);
            } else {
                System.out.println("Organization not found.");
//                    TODO: HANDLE ERROR CORRECTLY
                throw new Exception("Organization not found.");
            }

            List<GNZUser> associatedUsers = this.gnzOrganizationDAO.getUsersForOrganization(organization);
            mv.addObject("associatedusers", associatedUsers);

            mv.addObject("user", getCurrentUser());
        } else if (isUser()) {
//              check if user is associated with organization
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            GNZUser user = this.gnzUserDAO.findByName(username);
            String associatedOrganizationId = user.getOrganizationId();
            if (!associatedOrganizationId.equals(id)) {
                System.out.println("You do not have permission to view this organization.");
                throw new AccessDeniedException("You do not have permission to view this organization.");
            } else {
//                find organization for id
                GNZOrganization organization = this.gnzOrganizationDAO.findById(id);
                if (organization != null) {
                    mv.getModel().put("organization", organization);
                } else {
                    System.out.println("Organization not found.");
//                    TODO: HANDLE ERROR CORRECTLY
                    throw new Exception("Organization not found.");
                }


                List<GNZUser> associatedUsers = this.gnzOrganizationDAO.getUsersForOrganization(organization);
                mv.addObject("associatedusers", associatedUsers);

                mv.addObject("user", getCurrentUser());
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
        if (isAdmin()) {
            if (this.gnzOrganizationDAO.update(organization)) {
                System.out.println("Organization updated successfully.");
                return organization(id, "Successfully updated the organization details.", null, null, null);
            } else {
                System.out.println("Organization update failed.");
                return organization(id, null, null, "Sorry, the update failed.", null);
            }
        } else if (isUser()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            GNZUser user = this.gnzUserDAO.findByName(username);
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
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(ADMIN.getName()))) {
//            get organization and check if it exists
            GNZOrganization organization = this.gnzOrganizationDAO.findById(id);
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
            GNZUser user = this.gnzUserDAO.findByName(username);
            String associatedOrganizationId = user.getOrganizationId();
            if (!associatedOrganizationId.equals(id)) {
                System.out.println("You do not have permission to view this organization.");
                throw new AccessDeniedException("You do not have permission to view this organization.");
            } else {
//                get organization and check if it exists
                GNZOrganization organization = this.gnzOrganizationDAO.findById(id);
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

    @RequestMapping(value = "/admin/create-account")
    public ModelAndView createAccount(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "cpassword") String cpassword,
            @RequestParam(value = "oid", required = false) String organizationId,
            @RequestParam(value = "r", required = false) String roleInital
    ) throws Exception {
        if (!isSuperAdmin()) {
            throw new AccessDeniedException("You do not have permission to create an account.");
        }

//            validate input
        System.out.println("Validating input data...");

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
            System.out.println("Sorry, fields cannot be left blank.");
            return organization(organizationId, null, null, null, "Sorry, fields cannot be left blank.");
        }

        if (!password.equals(cpassword)) {
            System.out.println("Sorry, passwords do not match.");
            return organization(organizationId, null, null, null, "Sorry, passwords do not match.");
        }

        String usernameRegex = "^[A-Za-z]\\w{5,29}$";
        Pattern usernamePatter = Pattern.compile(usernameRegex);
        Matcher m = usernamePatter.matcher(username);
        if (!m.matches()) {
            System.out.println("Sorry, this username is not valid.");
            return organization(organizationId, null, null, null,"Sorry, the username must be 6-30 characters, only contain letters and numbers, and start with a letter.");
        }

//        check if username already exists
        if (gnzUserDAO.findByName(username) != null) {
            System.out.println("Sorry, this username already exists.");
            return organization(organizationId, null, null, null,"Sorry, this username already exists.");
        }


//        check if email is valid
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher m2 = emailPattern.matcher(email);
        if (!m2.matches()) {
            System.out.println("Sorry, this email address is not valid.");
            return organization(organizationId, null, null, null,"Sorry, this email address is invalid.");
        }


//        check if password is valid
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher m3 = passwordPattern.matcher(password);
        if (!m3.matches()) {
            System.out.println("Sorry, this password is not valid.");
            return organization(organizationId, null, null, null,"Sorry, the password must be at least 8 characters, contain at least one letter and one number.");
        }

        Role role;
        if (roleInital.toLowerCase().equals("a")) {
            role = ADMIN;
        } else if (roleInital.toLowerCase().equals("u") && organizationId != null) {
            role = Role.USER;
        } else if (organizationId != null) {
            role = Role.USER;
        } else {
            System.out.println("No organization selected.");
            return organization(organizationId, null, null, null,"Sorry, an error occurred.");
        }

        if (role == ADMIN) {
            if (isAdmin()) {
                System.out.println("Creating account...");

                GNZUser user = new GNZUser(username, email, Encrypter.encrytedPassword(password), organizationId, role);

                if (this.gnzUserDAO.save(user)) {
                    return organization(organizationId, "Successfully created a new account with username '" + user.getUsername() + ".'", null, null, null);
                } else {
                    System.out.println("Account creation failed.");
                    return organization(organizationId, null, null, null,"Sorry, an error occurred.");
                }
            } else {
                throw new AccessDeniedException("You do not have permission to create an administrator account.");
            }
        } else if (role == Role.USER) {
            if (isAdmin()) {
                System.out.println("Creating account...");

                GNZUser user = new GNZUser(username, email, Encrypter.encrytedPassword(password), organizationId, role);

                if (this.gnzUserDAO.save(user)) {
                    return organization(organizationId, "Successfully created a new account with username '" + user.getUsername() + ".'", null, null, null);
                } else {
                    System.out.println("Account creation failed.");
                    return organization(organizationId, null, null, null,"Sorry, an error occurred.");
                }
            } else if (isUser()) {
//                    check if user is in the same organization
                if (organizationId != null) {
                    if (gnzUserDAO.findByName(username).getId().equals(organizationId)) {
                        System.out.println("Creating account...");

                        GNZUser user = new GNZUser(username, email, Encrypter.encrytedPassword(password), organizationId, role);

                        if (this.gnzUserDAO.save(user)) {
                            return organization(organizationId, "Successfully created a new account with username '" + user.getUsername() + ".'", null, null, null);
                        } else {
                            System.out.println("Account creation failed.");
                            return organization(organizationId, null, null, null,"Sorry, an error occurred.");
                        }
                    } else {
                        throw new AccessDeniedException("You do not have permission to create an account for this organization.");
                    }
                } else {
                    System.out.println("No organization selected.");
                    return organization(organizationId, null, null, null,"Sorry, an error occurred.");
                }
            } else {
                throw new AccessDeniedException("You do not have permission to create an account.");
            }
        } else {
            System.out.println("Error: The role is null.");
            return organization(organizationId, null, null, null,"Sorry, an error occurred.");
        }
    }

    @RequestMapping(value = "/admin/update-account", method = RequestMethod.POST)
    public ModelAndView updateAccount(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "username", required = true) String newUsername,
            @RequestParam(value = "email", required = true) String newEmail,
            @RequestParam(value = "privileges", required = false) int roleId
    ) {
        GNZUser userToUpdate = gnzUserDAO.findById(id);
        GNZUser currentUser = getCurrentUser();

        if (!isSuperAdmin() && !(isAdmin() && currentUser.getOrganizationId().equals(userToUpdate.getOrganizationId())) && !(currentUser.getId().equals(userToUpdate.getId()))) {
            throw new AccessDeniedException("You do not have permission to update this account.");
        }

        Role newRole;
        if (roleId == 1) {
            newRole = Role.ADMIN;
        } else if (roleId == 2) {
            newRole = Role.USER;
        } else {
            newRole = userToUpdate.role();
        }

        if (!isAdmin() && !newRole.equals(userToUpdate.role())) {
            throw new AccessDeniedException("You do not have permission to update this information.");
        }

//        TODO: DECIDE ABOUT CONTROL OF SUPER ADMIN STATUSES
        if (isAdmin() && !userToUpdate.isSuperAdmin()) {
            GNZUser updatedUser = new GNZUser(id, newUsername, newEmail, userToUpdate.getEncryptedPassword(), userToUpdate.getOrganizationId(), newRole);
            if (gnzUserDAO.update(updatedUser)) {
                return account(id,"Successfully updated account with username '" + updatedUser.getUsername() + "'.", null);
            } else {
                return account(id,null, "Sorry, an error occurred. The account could not be updated.");
            }
        } else {
            GNZUser updatedUser = new GNZUser(id, newUsername, newEmail, userToUpdate.getEncryptedPassword(), userToUpdate.getOrganizationId(), userToUpdate.role());
            if (gnzUserDAO.update(updatedUser)) {
                return account(id,"Successfully updated account with username '" + updatedUser.getUsername() + "'.", null);
            } else {
                return account(id,null, "Sorry, an error occurred. The account could not be updated.");
            }
        }
    }

    @RequestMapping(value = "/admin/locations", method = RequestMethod.GET)
    public ModelAndView locations(@RequestParam(value = "oid", required = false) String oid, String successMessage, String errorMessage) {
        String oidToUse;
        if (isSuperAdmin()) {
            oidToUse = oid;
        } else {
            if (oid != null) {
                throw new AccessDeniedException("You do not have permission to view locations for this organization.");
            } else {
                oidToUse = getCurrentUser().getOrganizationId();
            }
        }

        ModelAndView mv = new ModelAndView("admin/locations");
        mv.addObject("locations", gnzLocationDAO.findMatching(oidToUse));

        GNZUser currentUser = getCurrentUser();
        mv.addObject("user", currentUser);

        GNZOrganization organization = gnzOrganizationDAO.findById(oidToUse);
        mv.addObject("organization", organization);

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));


        mv.addObject("successmessage", successMessage);
        mv.addObject("errormessage", errorMessage);

        return mv;
    }

    @RequestMapping(value = "/admin/create-location")
    public ModelAndView createLocation(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "oid", required = true) String organizationId) {
        if (!isSuperAdmin() && !getCurrentUser().getOrganizationId().equals(organizationId)) {
            throw new AccessDeniedException("You do not have permission to create a location for this organization.");
        }

        if (name.isEmpty()) {
            return locations(null, null, "Sorry, an error occurred. The location could not be created.");
        }

        GNZLocation location = new GNZLocation(name, organizationId);
        if (gnzLocationDAO.save(location)) {
            return locations(organizationId,"Successfully created location '" + location.getName() + ".'", null);
        } else {
            return locations(organizationId,null, "Sorry, an error occurred. The location could not be created.");
        }
    }

    @RequestMapping(value = "/admin/update-location")
    public ModelAndView updateLocation(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "name", required = true) String newName) {
        GNZLocation locationToUpdate = gnzLocationDAO.findById(id);
        if (!isSuperAdmin() && !getCurrentUser().getOrganizationId().equals(locationToUpdate.getOrganizationId())) {
            throw new AccessDeniedException("You do not have permission to update a location for this organization.");
        }

        String organizationId = locationToUpdate.getOrganizationId();

        if (newName.isEmpty()) {
            return locations(organizationId, null, "Sorry, an error occurred. The location could not be updated.");
        }

        GNZLocation location = new GNZLocation(id, newName, locationToUpdate.getOrganizationId());
        if (gnzLocationDAO.update(location)) {
            return locations(organizationId, "Successfully updated location '" + location.getName() + ".'", null);
        } else {
            return locations(organizationId, null, "Sorry, an error occurred. The location could not be updated.");
        }
    }

    @RequestMapping(value = "/admin/delete-location")
    public ModelAndView deleteLocation(@RequestParam(value = "id", required = true) String id) {
        GNZLocation locationToDelete = gnzLocationDAO.findById(id);
        String organizationId = locationToDelete.getOrganizationId();
        if (!isSuperAdmin() && !getCurrentUser().getOrganizationId().equals(locationToDelete.getOrganizationId())) {
            throw new AccessDeniedException("You do not have permission to delete a location for this organization.");
        }

        if (gnzLocationDAO.delete(locationToDelete)) {
            return locations(organizationId, "Successfully deleted location '" + locationToDelete.getName() + ".'", null);
        } else {
            return locations(organizationId, null, "Sorry, an error occurred. The location could not be deleted.");
        }
    }
}
