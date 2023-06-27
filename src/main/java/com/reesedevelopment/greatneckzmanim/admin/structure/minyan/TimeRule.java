package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import com.kosherjava.zmanim.util.Time;
import com.reesedevelopment.greatneckzmanim.front.ZmanimHandler;
import com.reesedevelopment.greatneckzmanim.global.Zman;

import java.time.LocalDate;
import java.util.Date;

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

    public Time getTime(LocalDate date) {
        ZmanimHandler zmanimHandler = new ZmanimHandler();
        LocalDate temp = date;
        Date zmanTime = zmanimHandler.getZmanim(temp).get(zman);
//        TODO: DEAL WITH DEPRECATED FUNCTIONS
        Time t = new Time(zmanTime.getHours(), zmanTime.getMinutes() + offsetMinutes, zmanTime.getSeconds(), 0);
        return t;
    }
}