package com.reesedevelopment.greatneckzmanim.admin.structure;

//import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name="ACCOUNT")
public class GNZUser implements IDGenerator {
    @Id
//    @Id
    @Column(name="ID", nullable = false, unique = true)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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
        this.id = id;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.organizationId = organizationId;
        this.roleId = role;
    }

    public GNZUser(String username, String email, String encryptedPassword, String organizationId, Integer role) {
        this.id = generateID('A');
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.organizationId = organizationId;
        this.roleId = role;
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
}
