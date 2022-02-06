package com.reesedevelopment.greatneckzmanim.front.controllers;

import com.reesedevelopment.greatneckzmanim.front.Zmanim;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.TimeZone;

@Controller
public class AboutController {
    TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy | hh:mm aa");

    @GetMapping("/about")
    public ModelAndView zmanim(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("front/about");

        mv.getModel().put("name", name);

        Date today = new Date();
        dateFormat.setTimeZone(timeZone);
//        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, today);
        mv.getModel().put("date", dateFormat.format(today));
        return mv;
    }

}