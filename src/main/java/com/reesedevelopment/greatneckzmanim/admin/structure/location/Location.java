package com.reesedevelopment.greatneckzmanim.admin.structure.location;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZObject;
import com.reesedevelopment.greatneckzmanim.admin.structure.IDGenerator;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "LOCATION")
public class Location extends GNZObject implements IDGenerator {
    @Column(name="NAME")
    private String name;

    @Column(name="ORGANIZATION_ID")
    private String organizationId;

    public Location(String id, String name, String organizationId) {
        super.id = id;
        this.name = name;
        this.organizationId = organizationId;
    }

    public Location(String name, String organizationId) {
        super.id = generateID('L');
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
