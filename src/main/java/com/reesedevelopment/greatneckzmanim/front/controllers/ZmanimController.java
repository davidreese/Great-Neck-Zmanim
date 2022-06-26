package com.reesedevelopment.greatneckzmanim.front.controllers;

import com.kosherjava.zmanim.util.GeoLocation;
import com.reesedevelopment.greatneckzmanim.global.Zman;
import com.reesedevelopment.greatneckzmanim.front.ZmanimHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.TimeZone;

@Controller
public class ZmanimController {
    TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

    String locationName = "Great Neck, NY";
    double latitude = 40.8007;
    double longitude = -73.7285;
    double elevation = 0;
    GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy | hh:mm aa");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

    ZmanimHandler zmanimHandler = new ZmanimHandler(geoLocation);

    @GetMapping("/")
    public ModelAndView home() {
        return zmanim();
    }

    @GetMapping("/zmanim")
    public ModelAndView zmanim() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("front/zmanim");

        dateFormat.setTimeZone(timeZone);

        Date today = new Date();
//        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, today);
        mv.getModel().put("date", dateFormat.format(today));

        timeFormat.setTimeZone(timeZone);

        Dictionary zmanim = zmanimHandler.getZmanim();

        mv.getModel().put("alotHashachar", timeFormat.format(zmanim.get(Zman.ALOT_HASHACHAR)));
        mv.getModel().put("sunrise", timeFormat.format(zmanim.get(Zman.NETZ)));
        mv.getModel().put("chatzot", timeFormat.format(zmanim.get(Zman.CHATZOT)));
        mv.getModel().put("minchaGedola", timeFormat.format(zmanim.get(Zman.MINCHA_GEDOLA)));
        mv.getModel().put("minchaKetana", timeFormat.format(zmanim.get(Zman.MINCHA_KETANA)));
        mv.getModel().put("plagHamincha", timeFormat.format(zmanim.get(Zman.PLAG_HAMINCHA)));
        mv.getModel().put("shekiya", timeFormat.format(zmanim.get(Zman.SHEKIYA)));
        mv.getModel().put("tzet", timeFormat.format(zmanim.get(Zman.TZET)));

        return mv;
    }
}