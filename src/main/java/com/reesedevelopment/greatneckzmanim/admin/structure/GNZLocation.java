package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "LOCATION")
public class GNZLocation extends GNZObject {
    @Column(name="NAME")
    private String name;

    @Column(name="ORGANIZATION_ID")
    private String organizationId;

    public GNZLocation(String name, String organizationId) {
        this.name = name;
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public String getOrganizationId() {
        return organizationId;
    }
}
