package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Table;
import java.net.URI;
import java.net.URL;

@Table(name="ORGANIZATION")
public class GNZOrganization extends GNZObject implements IDGenerator {
    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="SITE_URI")
    private URI websiteURI;

    public GNZOrganization(String id, String username, String address, URI websiteURI) {
        super.id = id;
        this.name = username;
        this.address = address;
        this.websiteURI = websiteURI;
    }

    public GNZOrganization(String username, String address, URI websiteURI) {
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
