package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

public class Schedule {
    private Time sunday;
    private Time monday;
    private Time tuesday;
    private Time wednesday;
    private Time thursday;
    private Time friday;
    private Time shabbat;
    private Time roshChodesh;
    private Time yomTov;

    public Schedule(Time sunday, Time monday, Time tuesday, Time wednesday, Time thursday, Time friday, Time shabbat, Time roshChodesh, Time yomTov) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.shabbat = shabbat;
        this.roshChodesh = roshChodesh;
        this.yomTov = yomTov;
    }

    public Time getSunday() {
        return sunday;
    }

    public Time getMonday() {
        return monday;
    }

    public Time getTuesday() {
        return tuesday;
    }

    public Time getWednesday() {
        return wednesday;
    }

    public Time getThursday() {
        return thursday;
    }

    public Time getFriday() {
        return friday;
    }

    public Time getShabbat() {
        return shabbat;
    }

    public Time getRoshChodesh() {
        return roshChodesh;
    }

    public Time getYomTov() {
        return yomTov;
    }
}
