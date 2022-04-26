package com.reesedevelopment.greatneckzmanim.admin.structure;

//import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Table(name="ACCOUNT")
public class GNZUser extends GNZObject implements IDGenerator {
    @Column(name="USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="ENCRYPTED_PASSWORD", nullable = false)
    private String encryptedPassword;

    @Column(name="ORGANIZATION_ID")
    private String organizationId;

    @Column(name = "ROLE_ID")
    private Integer roleId;

    public Role role() {
        return Role.getRole(roleId);
    }

    public GNZUser(String id, String username, String email, String encryptedPassword, String organizationId, Integer role) {
        super.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.organizationId = organizationId;
        this.roleId = role;
    }

    public GNZUser(String username, String email, String encryptedPassword, String organizationId, Integer role) {
        super.id = generateID('A');
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.organizationId = organizationId;
        this.roleId = role;
    }

    public GNZUser(String id, String username, String email, String encryptedPassword, String organizationId, Role role) {
        super.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.organizationId = organizationId;
        this.roleId = role.getId();
    }

    public GNZUser(String username, String email, String encryptedPassword, String organizationId, Role role) {
        super.id = generateID('A');
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.organizationId = organizationId;
        this.roleId = role.getId();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public Integer getRoleId() {
        return roleId;
    }

//    private GNZOrganization getOrganization() {
//
//        return gnzOrganizationDAO.find(organizationId);
//    }
//
//    public String getOrganizationDisplayName() {
//        GNZOrganization org = getOrganization();
//        return (org == null) ? "" : org.getName();
//    }

    public boolean isSuperAdmin() {
        return (this.role() == Role.ADMIN && this.getOrganizationId() == null);
    }

    public boolean isAdmin() {
        return (this.role() == Role.ADMIN);
    }

    public boolean isUser() {
        return (this.role() == Role.USER);
    }

    public String getRoleDisplayName() {
        switch (this.role()) {
            case ADMIN:
                if (this.getOrganizationId() == null) {
                    return "Site Admin";
                } else {
                    return "Organization Admin";
                }
            case USER:
                return "User";
            default:
                return null;
        }
    }
}
