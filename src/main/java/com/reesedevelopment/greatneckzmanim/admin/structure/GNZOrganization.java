package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ORGANIZATION")
public class GNZOrganization implements IDGenerator {
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

    public GNZOrganization(String username, String address) {
        this.id = generateID('O');
        this.name = username;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
