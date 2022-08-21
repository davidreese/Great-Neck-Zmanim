package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import java.time.LocalDate;
import java.util.Date;

import static java.time.temporal.TemporalAdjusters.next;

public enum MinyanDay {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SHABBAT,
    ROSH_CHODESH,
    CHANUKA,
    ROSH_CHODESH_CHANUKA,
    YOM_TOV;

//    public LocalDate nextDate() {
//        switch (this) {
//            case SUNDAY:
//                return LocalDate.now().with( next(java.time.DayOfWeek.SUNDAY) );
//            case MONDAY:
//                return LocalDate.now().with( next(java.time.DayOfWeek.MONDAY) );
//            case TUESDAY:
//                return LocalDate.now().with( next(java.time.DayOfWeek.TUESDAY) );
//            case WEDNESDAY:
//                return LocalDate.now().with( next(java.time.DayOfWeek.WEDNESDAY) );
//            case THURSDAY:
//                return LocalDate.now().with( next(java.time.DayOfWeek.THURSDAY) );
//            case FRIDAY:
//                return LocalDate.now().with( next(java.time.DayOfWeek.FRIDAY) );
//            case SHABBAT:
//                return LocalDate.now().with( next(java.time.DayOfWeek.SATURDAY) );
//            case ROSH_CHODESH:
//                return new Date();
//            case CHANUKA:
//                return new Date();
//            case ROSH_CHODESH_CHANUKA:
//                return new Date();
//            case YOM_TOV:
//                return new Date();
//            default:
//                return new Date();
//        }
//    }
}
