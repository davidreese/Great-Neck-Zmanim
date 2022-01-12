package com.reesedevelopment.greatneckzmanim;

import com.kosherjava.zmanim.ComplexZmanimCalendar;
import com.kosherjava.zmanim.util.GeoLocation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class ZmanimController {
    TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

    @GetMapping("/")
    public ModelAndView zmanim() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("zmanim");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd hh:mm aa");
        dateFormat.setTimeZone(timeZone);
        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        String locationName = "Great Neck, NY";
        double latitude = 40.8007;
        double longitude = -73.7285;
        double elevation = 0; //optional elevation
        GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        ComplexZmanimCalendar czc = new ComplexZmanimCalendar(location);

//        czc.getSunset()

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");
        timeFormat.setTimeZone(timeZone);
        Date chatzot = czc.getChatzos();

        mv.getModel().put("chatzot", timeFormat.format(chatzot));
        return mv;
    }
}