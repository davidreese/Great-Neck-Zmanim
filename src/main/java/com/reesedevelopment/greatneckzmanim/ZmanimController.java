package com.reesedevelopment.greatneckzmanim;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class ZmanimController {

    @GetMapping("/")
    public ModelAndView zmanim() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("zmanim");
        mv.getModel().put("date", new Date());
        return mv;
    }
}