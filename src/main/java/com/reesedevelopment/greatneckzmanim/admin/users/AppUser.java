package com.reesedevelopment.greatneckzmanim.admin.users;

//import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
public class AppUser {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    private String encryptedPassword;

    public AppUser(Integer id, String username, String encryptedPassword) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public AppUser() {
    }

    public Integer getId() {
    	return id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }

    public String getUsername() {
    	return username;
    }

    public void setUsername(String username) {
    	this.username = username;
    }

    public String getEncryptedPassword() {
    	return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
    	this.encryptedPassword = encryptedPassword;
    }
}
