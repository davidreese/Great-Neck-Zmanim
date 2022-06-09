package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import java.util.HashMap;

public class Schedule {
    private MinyanTime sunday;
    private MinyanTime monday;
    private MinyanTime tuesday;
    private MinyanTime wednesday;
    private MinyanTime thursday;
    private MinyanTime friday;
    private MinyanTime shabbat;
    private MinyanTime roshChodesh;
    private MinyanTime chanuka;
    private MinyanTime roshChodeshChanuka;
    private MinyanTime yomTov;

    public Schedule(String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String shabbat, String roshChodesh, String chanuka, String roshChodeshChanukah, String yomTov) {
        this.sunday = new MinyanTime(sunday);
        this.monday = new MinyanTime(monday);
        this.tuesday = new MinyanTime(tuesday);
        this.wednesday = new MinyanTime(wednesday);
        this.thursday = new MinyanTime(thursday);
        this.friday = new MinyanTime(friday);
        this.shabbat = new MinyanTime(shabbat);
        this.roshChodesh = new MinyanTime(roshChodesh);
        this.chanuka = new MinyanTime(chanuka);
        this.roshChodeshChanuka = new MinyanTime(roshChodeshChanukah);
        this.yomTov = new MinyanTime(yomTov);
    }

    public Schedule(MinyanTime sunday, MinyanTime monday, MinyanTime tuesday, MinyanTime wednesday, MinyanTime thursday, MinyanTime friday, MinyanTime shabbat, MinyanTime roshChodesh, MinyanTime chanuka, MinyanTime roshChodeshChanukah, MinyanTime yomTov) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.shabbat = shabbat;
        this.roshChodesh = roshChodesh;
        this.chanuka = chanuka;
        this.roshChodeshChanuka = roshChodeshChanukah;
        this.yomTov = yomTov;
    }

    public MinyanTime getSunday() {
        return sunday;
    }

    public MinyanTime getMonday() {
        return monday;
    }

    public MinyanTime getTuesday() {
        return tuesday;
    }

    public MinyanTime getWednesday() {
        return wednesday;
    }

    public MinyanTime getThursday() {
        return thursday;
    }

    public MinyanTime getFriday() {
        return friday;
    }

    public MinyanTime getShabbat() {
        return shabbat;
    }

    public MinyanTime getRoshChodesh() {
        return roshChodesh;
    }

    public MinyanTime getChanuka() {
        return chanuka;
    }

    public MinyanTime getRoshChodeshChanuka() {
        return roshChodeshChanuka;
    }

    public MinyanTime getYomTov() {
        return yomTov;
    }

    public HashMap<Day, MinyanTime> getMappedSchedule() {
        HashMap<Day, MinyanTime> schedule = new HashMap<>();
        schedule.put(Day.SUNDAY, sunday);
        schedule.put(Day.MONDAY, monday);
        schedule.put(Day.TUESDAY, tuesday);
        schedule.put(Day.WEDNESDAY, wednesday);
        schedule.put(Day.THURSDAY, thursday);
        schedule.put(Day.FRIDAY, friday);
        schedule.put(Day.SHABBAT, shabbat);
        schedule.put(Day.ROSH_CHODESH, roshChodesh);
        schedule.put(Day.CHANUKA, chanuka);
        schedule.put(Day.ROSH_CHODESH_CHANUKA, roshChodeshChanuka);
        schedule.put(Day.YOM_TOV, yomTov);

        return schedule;
    }
}
