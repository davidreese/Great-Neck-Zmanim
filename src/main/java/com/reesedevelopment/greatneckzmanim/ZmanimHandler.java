package com.reesedevelopment.greatneckzmanim;

import com.kosherjava.zmanim.ComplexZmanimCalendar;
import com.kosherjava.zmanim.util.GeoLocation;

import java.util.ArrayList;
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
        dictionary.put(Zmanim.alotHashachar, complexZmanimCalendar.getAlosHashachar());
        dictionary.put(Zmanim.sunrise, complexZmanimCalendar.getSunrise());
        dictionary.put(Zmanim.chatzot, complexZmanimCalendar.getChatzos());
        dictionary.put(Zmanim.minchaGedola, complexZmanimCalendar.getMinchaGedola());
        dictionary.put(Zmanim.minchaKetana, complexZmanimCalendar.getMinchaKetana());
        dictionary.put(Zmanim.sunset, complexZmanimCalendar.getSunset());
        dictionary.put(Zmanim.tzait, complexZmanimCalendar.getTzais());

        return dictionary;
    }
}
