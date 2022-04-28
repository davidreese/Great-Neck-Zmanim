package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

public class Schedule {
    private MinyanTime sunday;
    private MinyanTime monday;
    private MinyanTime tuesday;
    private MinyanTime wednesday;
    private MinyanTime thursday;
    private MinyanTime friday;
    private MinyanTime shabbat;
    private MinyanTime roshChodesh;
    private MinyanTime chanukah;
    private MinyanTime roshChodeshChanukah;
    private MinyanTime yomTov;

    public Schedule(String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String shabbat, String roshChodesh, String chanukah, String roshChodeshChanukah, String yomTov) {
        this.sunday = new MinyanTime(sunday);
        this.monday = new MinyanTime(monday);
        this.tuesday = new MinyanTime(tuesday);
        this.wednesday = new MinyanTime(wednesday);
        this.thursday = new MinyanTime(thursday);
        this.friday = new MinyanTime(friday);
        this.shabbat = new MinyanTime(shabbat);
        this.roshChodesh = new MinyanTime(roshChodesh);
        this.chanukah = new MinyanTime(chanukah);
        this.roshChodeshChanukah = new MinyanTime(roshChodeshChanukah);
        this.yomTov = new MinyanTime(yomTov);
    }

    public Schedule(MinyanTime sunday, MinyanTime monday, MinyanTime tuesday, MinyanTime wednesday, MinyanTime thursday, MinyanTime friday, MinyanTime shabbat, MinyanTime roshChodesh, MinyanTime chanukah, MinyanTime roshChodeshChanukah, MinyanTime yomTov) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.shabbat = shabbat;
        this.roshChodesh = roshChodesh;
        this.chanukah = chanukah;
        this.roshChodeshChanukah = roshChodeshChanukah;
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

    public MinyanTime getChanukah() {
        return chanukah;
    }

    public MinyanTime getRoshChodeshChanukah() {
        return roshChodeshChanukah;
    }

    public MinyanTime getYomTov() {
        return yomTov;
    }
}
