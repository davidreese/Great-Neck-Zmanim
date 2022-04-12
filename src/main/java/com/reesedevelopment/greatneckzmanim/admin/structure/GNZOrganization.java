package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ORGANIZATION")
public class GNZOrganization {
    @Id
    @Column(name="ID", nullable = false, unique = true)
    private String id;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ADDRESS")
    private String address;

    public GNZOrganization(String id, String username, String address) {
        this.id = id;
        this.name = username;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
