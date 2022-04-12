package com.reesedevelopment.greatneckzmanim.admin.structure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class GNZObject {
    @Id
    @Column(name="ID", nullable = false, unique = true)
    protected String id;

    public GNZObject() {
    }

    public String getId() {
        return id;
    }
}
