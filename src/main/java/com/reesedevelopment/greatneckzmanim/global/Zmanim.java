package com.reesedevelopment.greatneckzmanim.global;

public enum Zmanim {
    alotHashachar,
    sunrise,
    chatzot,
    minchaGedola,
    minchaKetana,
    sunset,
    tzait;


    @Override
    public String toString() {
        switch (this) {
            case alotHashachar:
                return "alothashachar";
            case sunrise:
                return "sunrise";
            case chatzot:
                return "chatzot";
            case minchaGedola:
                return "minchagedola";
            case minchaKetana:
                return "minchaketana";
            case sunset:
                return "sunset";
            case tzait:
                return "tzait";
            default:
                return null;
        }
    }
}
