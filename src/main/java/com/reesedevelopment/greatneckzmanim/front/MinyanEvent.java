package com.reesedevelopment.greatneckzmanim.front;

import com.reesedevelopment.greatneckzmanim.admin.structure.location.Location;
import com.reesedevelopment.greatneckzmanim.admin.structure.minyan.Minyan;
import com.reesedevelopment.greatneckzmanim.admin.structure.minyan.MinyanTime;
import com.reesedevelopment.greatneckzmanim.admin.structure.minyan.MinyanType;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.Organization;
import com.reesedevelopment.greatneckzmanim.global.Nusach;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MinyanEvent {
    private String parentMinyanId;

    private MinyanType type;

    private String organizationName;

    private String locationName;

    private Date startTime;

    private String dynamicTimeString;

    private Nusach nusach;

    private String notes;

    public MinyanEvent(String parentMinyanId, MinyanType type, String organizationName, String locationName, Date startTime, Nusach nusach, String notes) {
        this.parentMinyanId = parentMinyanId;
        this.type = type;
        this.organizationName = organizationName;
        this.locationName = locationName;
        this.startTime = startTime;
        this.nusach = nusach;
        this.notes = notes;
    }

    public MinyanEvent(String parentMinyanId, MinyanType type, String organizationName, String locationName, Date startTime, String dynamicTimeString, Nusach nusach, String notes) {
        this.parentMinyanId = parentMinyanId;
        this.type = type;
        this.organizationName = organizationName;
        this.locationName = locationName;
        this.startTime = startTime;
        this.dynamicTimeString = dynamicTimeString;
        this.nusach = nusach;
        this.notes = notes;
    }

    public MinyanType getType() {
        return type;
    }

//    add getters
    public String getOrganizationName() {
        return organizationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getFormattedStartTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa");
        if (dynamicTimeString != null) {
            return timeFormat.format(startTime) +  " (" + dynamicTimeString + ")";
        } else {
            return timeFormat.format(startTime);
        }
    }

    public Nusach getNusach() {
        return nusach;
    }

    public String getNotes() {
        return notes;
    }
}
