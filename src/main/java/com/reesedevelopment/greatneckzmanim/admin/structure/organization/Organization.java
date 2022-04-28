package com.reesedevelopment.greatneckzmanim.admin.structure.organization;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZObject;
import com.reesedevelopment.greatneckzmanim.admin.structure.IDGenerator;

import javax.persistence.Column;
import javax.persistence.Table;
import java.net.URI;

@Table(name="ORGANIZATION")
public class Organization extends GNZObject implements IDGenerator {
    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ADDRESS", nullable = true)
    private String address;

    @Column(name="SITE_URI", nullable = true)
    private URI websiteURI;

    public Organization(String id, String username, String address, URI websiteURI) {
        super.id = id;
        this.name = username;
        this.address = address;
        this.websiteURI = websiteURI;
    }

    public Organization(String username, String address, URI websiteURI) {
        super.id = generateID('O');
        this.name = username;
        this.address = address;
        this.websiteURI = websiteURI;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public URI getWebsiteURI() {
        return websiteURI;
    }
}
