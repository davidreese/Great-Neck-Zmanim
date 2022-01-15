package com.reesedevelopment.greatneckzmanim;

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

    String locationName = "Great Neck, NY";
    double latitude = 40.8007;
    double longitude = -73.7285;
    double elevation = 0;
    GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd hh:mm aa");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

    ZmanimHandler zmanimHandler = new ZmanimHandler(geoLocation);

    @GetMapping("/")
    public ModelAndView zmanim() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("zmanim");

        dateFormat.setTimeZone(timeZone);

        Date today = new Date();
        mv.getModel().put("date", dateFormat.format(today));

        timeFormat.setTimeZone(timeZone);

        mv.getModel().put("chatzot", timeFormat.format(zmanimHandler.getZmanim().get(ZmanimHandler.Zmanim.chatzot)));
        return mv;
    }
}