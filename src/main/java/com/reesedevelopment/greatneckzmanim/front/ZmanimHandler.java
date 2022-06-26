package com.reesedevelopment.greatneckzmanim.front;

import com.kosherjava.zmanim.ComplexZmanimCalendar;
import com.kosherjava.zmanim.util.GeoLocation;
import com.reesedevelopment.greatneckzmanim.global.Zman;

import java.util.Dictionary;
import java.util.Hashtable;

public class ZmanimHandler {
    private GeoLocation geoLocation;
    private ComplexZmanimCalendar complexZmanimCalendar;

    public ZmanimHandler(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        this.complexZmanimCalendar = new ComplexZmanimCalendar(geoLocation);
    }

    public Dictionary getZmanim() {
        Dictionary dictionary = new Hashtable();

        dictionary.put(Zman.ALOT_HASHACHAR, complexZmanimCalendar.getAlosHashachar());
        dictionary.put(Zman.NETZ, complexZmanimCalendar.getSunrise());
        dictionary.put(Zman.CHATZOT, complexZmanimCalendar.getChatzos());
        dictionary.put(Zman.MINCHA_GEDOLA, complexZmanimCalendar.getMinchaGedola());
        dictionary.put(Zman.MINCHA_KETANA, complexZmanimCalendar.getMinchaKetana());
        dictionary.put(Zman.SHEKIYA, complexZmanimCalendar.getSunset());
        dictionary.put(Zman.TZET, complexZmanimCalendar.getTzais());

        return dictionary;
    }
}
