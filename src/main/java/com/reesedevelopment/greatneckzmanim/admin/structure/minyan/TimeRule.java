package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import com.reesedevelopment.greatneckzmanim.global.Zman;

class TimeRule {
    private Zman zman;
    private Integer offsetMinutes;

    public TimeRule(Zman zman, Integer offsetMinutes) {
        this.zman = zman;
        this.offsetMinutes = offsetMinutes;
    }

    public Zman getZman() {
        return zman;
    }

    public Integer getOffsetMinutes() {
        return offsetMinutes;
    }
}