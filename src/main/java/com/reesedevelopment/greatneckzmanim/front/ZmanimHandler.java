package com.reesedevelopment.greatneckzmanim.front;

import com.kosherjava.zmanim.ComplexZmanimCalendar;
import com.kosherjava.zmanim.util.GeoLocation;
import com.reesedevelopment.greatneckzmanim.global.Zmanim;

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
        dictionary.put(Zmanim.ALOT_HASHACHAR, complexZmanimCalendar.getAlosHashachar());
        dictionary.put(Zmanim.SUNRISE, complexZmanimCalendar.getSunrise());
        dictionary.put(Zmanim.CHATZOT, complexZmanimCalendar.getChatzos());
        dictionary.put(Zmanim.MINCHA_GEDOLA, complexZmanimCalendar.getMinchaGedola());
        dictionary.put(Zmanim.MINCHA_KETANA, complexZmanimCalendar.getMinchaKetana());
        dictionary.put(Zmanim.SUNSET, complexZmanimCalendar.getSunset());
        dictionary.put(Zmanim.TZAIT, complexZmanimCalendar.getTzais());

        return dictionary;
    }
}
