package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URL;

@Table(name="ORGANIZATION")
public class GNZOrganization extends GNZObject implements IDGenerator {
    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="URL")
    private URL websiteURL;

    public GNZOrganization(String id, String username, String address, URL websiteURL) {
        super.id = id;
        this.name = username;
        this.address = address;
        this.websiteURL = websiteURL;
    }

    public GNZOrganization(String username, String address, URL websiteURL) {
        super.id = generateID('O');
        this.name = username;
        this.address = address;
        this.websiteURL = websiteURL;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public URL getWebsiteURL() {
        return websiteURL;
    }
}
