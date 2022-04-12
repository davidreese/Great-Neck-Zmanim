package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="ORGANIZATION")
public class GNZOrganization extends GNZObject implements IDGenerator {
    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ADDRESS")
    private String address;

    public GNZOrganization(String id, String username, String address) {
        super.id = id;
        this.name = username;
        this.address = address;
    }

    public GNZOrganization(String username, String address) {
        super.id = generateID('O');
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
