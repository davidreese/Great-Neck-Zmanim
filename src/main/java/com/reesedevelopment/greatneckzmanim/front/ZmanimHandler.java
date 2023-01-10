package com.reesedevelopment.greatneckzmanim.front;

import com.kosherjava.zmanim.ComplexZmanimCalendar;
//import com.kosherjava.zmanim.hebrewcalendar.JewishCalendar;
import com.kosherjava.zmanim.hebrewcalendar.HebrewDateFormatter;
import com.kosherjava.zmanim.hebrewcalendar.JewishDate;
import com.kosherjava.zmanim.util.GeoLocation;
import com.reesedevelopment.greatneckzmanim.global.Zman;

import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.TimeZone;

public class ZmanimHandler {
    private GeoLocation geoLocation;
//    private ComplexZmanimCalendar complexZmanimCalendar;

    public ZmanimHandler(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
//        this.complexZmanimCalendar = new ComplexZmanimCalendar(geoLocation);
    }

    public ZmanimHandler() {
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

        String locationName = "Great Neck, NY";
        double latitude = 40.8007;
        double longitude = -73.7285;
        double elevation = 0;
        GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        this.geoLocation = geoLocation;
    }


    public Dictionary getZmanimForNow() {
        return getZmanim(LocalDate.now());
    }
    public Dictionary<Zman, Date> getZmanim(LocalDate date) {
        Dictionary<Zman, Date> dictionary = new Hashtable();

        ComplexZmanimCalendar complexZmanimCalendar = new ComplexZmanimCalendar(geoLocation);
        complexZmanimCalendar.getCalendar().set(date.getYear(), date.getMonth().getValue() - 1, date.getDayOfMonth());
//        complexZmanimCalendar.getCalendar().

        dictionary.put(Zman.ALOT_HASHACHAR, complexZmanimCalendar.getAlosHashachar());
        dictionary.put(Zman.NETZ, complexZmanimCalendar.getSunrise());
        dictionary.put(Zman.CHATZOT, complexZmanimCalendar.getChatzos());
        dictionary.put(Zman.MINCHA_GEDOLA, complexZmanimCalendar.getMinchaGedola());
        dictionary.put(Zman.MINCHA_KETANA, complexZmanimCalendar.getMinchaKetana());
        dictionary.put(Zman.PLAG_HAMINCHA, complexZmanimCalendar.getPlagHamincha());
        dictionary.put(Zman.SHEKIYA, complexZmanimCalendar.getSunset());
        dictionary.put(Zman.EARLIEST_SHEMA, complexZmanimCalendar.getTzaisGeonim5Point88Degrees());
        dictionary.put(Zman.TZET, complexZmanimCalendar.getTzais());
//        dictionary.put(Zman.L, complexZmanimCalendar.getsoshmfzmank());

        return dictionary;
    }

    public String getHebrewDate(Date date) {
        JewishDate jd = new JewishDate(date);
        HebrewDateFormatter hdf = new HebrewDateFormatter();
        hdf.setHebrewFormat(true);
        return hdf.format(jd);
    }

    public String getTodaysHebrewDate() {
        return getHebrewDate(new Date());
    }

}
