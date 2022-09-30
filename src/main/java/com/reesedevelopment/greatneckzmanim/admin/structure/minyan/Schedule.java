package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import com.kosherjava.zmanim.util.Time;

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

    public Schedule(String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String shabbat, String roshChodesh, String chanuka, String roshChodeshChanuka, String yomTov) {
        this.sunday = new MinyanTime(sunday);
        this.monday = new MinyanTime(monday);
        this.tuesday = new MinyanTime(tuesday);
        this.wednesday = new MinyanTime(wednesday);
        this.thursday = new MinyanTime(thursday);
        this.friday = new MinyanTime(friday);
        this.shabbat = new MinyanTime(shabbat);
        this.roshChodesh = new MinyanTime(roshChodesh);
        this.chanuka = new MinyanTime(chanuka);
        this.roshChodeshChanuka = new MinyanTime(roshChodeshChanuka);
        this.yomTov = new MinyanTime(yomTov);
    }

    public Schedule(MinyanTime sunday, MinyanTime monday, MinyanTime tuesday, MinyanTime wednesday, MinyanTime thursday, MinyanTime friday, MinyanTime shabbat, MinyanTime roshChodesh, MinyanTime chanuka, MinyanTime roshChodeshChanuka, MinyanTime yomTov) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.shabbat = shabbat;
        this.roshChodesh = roshChodesh;
        this.chanuka = chanuka;
        this.roshChodeshChanuka = roshChodeshChanuka;
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

    public HashMap<MinyanDay, MinyanTime> getMappedSchedule() {
        HashMap<MinyanDay, MinyanTime> schedule = new HashMap<>();
        schedule.put(MinyanDay.SUNDAY, sunday);
        schedule.put(MinyanDay.MONDAY, monday);
        schedule.put(MinyanDay.TUESDAY, tuesday);
        schedule.put(MinyanDay.WEDNESDAY, wednesday);
        schedule.put(MinyanDay.THURSDAY, thursday);
        schedule.put(MinyanDay.FRIDAY, friday);
        schedule.put(MinyanDay.SHABBAT, shabbat);
        schedule.put(MinyanDay.ROSH_CHODESH, roshChodesh);
        schedule.put(MinyanDay.CHANUKA, chanuka);
        schedule.put(MinyanDay.ROSH_CHODESH_CHANUKA, roshChodeshChanuka);
        schedule.put(MinyanDay.YOM_TOV, yomTov);

        return schedule;
    }

//    public MinyanTime nearestTimeToNow() {
//        HashMap<MinyanDay, MinyanTime> schedule = getMappedSchedule();
//        for (MinyanDay minyanDay : schedule.keySet()) {
//            MinyanTime time = schedule.get(minyanDay);
//
//            minyanDay
//
//            if (time.isFixed()) {
//                Time fixedTime = time.getTime();
//
//
////                Date nextDate = new Date(""
//            }
//        }
//    }
}
