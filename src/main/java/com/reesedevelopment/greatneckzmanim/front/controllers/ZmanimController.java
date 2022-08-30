package com.reesedevelopment.greatneckzmanim.front.controllers;

import com.kosherjava.zmanim.util.GeoLocation;
import com.kosherjava.zmanim.util.Time;
import com.reesedevelopment.greatneckzmanim.admin.structure.location.Location;
import com.reesedevelopment.greatneckzmanim.admin.structure.location.LocationDAO;
import com.reesedevelopment.greatneckzmanim.admin.structure.minyan.Minyan;
import com.reesedevelopment.greatneckzmanim.admin.structure.minyan.MinyanDAO;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.Organization;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.OrganizationDAO;
import com.reesedevelopment.greatneckzmanim.front.MinyanEvent;
import com.reesedevelopment.greatneckzmanim.global.Zman;
import com.reesedevelopment.greatneckzmanim.front.ZmanimHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class ZmanimController {
    TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

    String locationName = "Great Neck, NY";
    double latitude = 40.8007;
    double longitude = -73.7285;
    double elevation = 0;
    GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy | h:mm aa");
    SimpleDateFormat onlyDateFormat = new SimpleDateFormat("EEEE, MMMM d");
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss aa");

    ZmanimHandler zmanimHandler = new ZmanimHandler(geoLocation);

    @Autowired
    private MinyanDAO minyanDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private LocationDAO locationDAO;


    @GetMapping("/")
    public ModelAndView home() {
        return todaysZmanim();
    }

    @GetMapping("/zmanim")
    public ModelAndView todaysZmanim() {
        return zmanim(new Date());
    }

    public ModelAndView zmanim(Date date) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("front/home");

        dateFormat.setTimeZone(timeZone);

//        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.US);
        mv.getModel().put("date", dateFormat.format(date));
        mv.getModel().put("onlyDate", onlyDateFormat.format(date));
        mv.getModel().put("dateString", date.toString());
//        mv.getModel(),put("longdate", )

//        add hebrew date
        mv.getModel().put("hebrewDate", zmanimHandler.getHebrewDate(date));

        timeFormat.setTimeZone(timeZone);

        Dictionary zmanim = zmanimHandler.getZmanim(LocalDate.of(date.getYear() + 1900, date.getMonth(), date.getDate()));

        mv.getModel().put("alotHashachar", timeFormat.format(zmanim.get(Zman.ALOT_HASHACHAR)));
        mv.getModel().put("sunrise", timeFormat.format(zmanim.get(Zman.NETZ)));
        mv.getModel().put("chatzot", timeFormat.format(zmanim.get(Zman.CHATZOT)));
        mv.getModel().put("minchaGedola", timeFormat.format(zmanim.get(Zman.MINCHA_GEDOLA)));
        mv.getModel().put("minchaKetana", timeFormat.format(zmanim.get(Zman.MINCHA_KETANA)));
        mv.getModel().put("plagHamincha", timeFormat.format(zmanim.get(Zman.PLAG_HAMINCHA)));
        mv.getModel().put("shekiya", timeFormat.format(zmanim.get(Zman.SHEKIYA)));
        mv.getModel().put("tzet", timeFormat.format(zmanim.get(Zman.TZET)));

//        get minyanim closest in time to now
        List<Minyan> enabledMinyanim = minyanDAO.getEnabled();
        List<MinyanEvent> minyanEvents = new ArrayList<>();

        for (Minyan minyan : enabledMinyanim) {
            Date startDate = minyan.getStartDate(LocalDate.of(date.getYear() + 1900, date.getMonth(), date.getDate()).plusMonths(1));
            Date terminationDate = new Date((new Date()).getTime() - (60000 * 20));
            if (startDate != null && startDate.after(terminationDate)) {
                String organizationName;
                Organization organization = minyan.getOrganization();
                if (organization == null) {
                    organizationName = organizationDAO.findById(minyan.getOrganizationId()).getName();
                } else {
                    organizationName = organization.getName();
                }

                String locationName = null;
                Location location = minyan.getLocation();
                if (location == null) {
                    location = locationDAO.findById(minyan.getLocationId());
                    if (location != null) {
                        locationName = location.getName();
                    }
                } else {
                    locationName = location.getName();
                }

                String dynamicDisplayName = minyan.getMinyanTime().dynamicDisplayName();
                if (dynamicDisplayName != null) {
                    minyanEvents.add(new MinyanEvent(minyan.getId(), minyan.getType(), organizationName,locationName, startDate, dynamicDisplayName, minyan.getNusach(), minyan.getNotes()));
                } else {
                    minyanEvents.add(new MinyanEvent(minyan.getId(), minyan.getType(), organizationName, locationName, startDate, minyan.getNusach(), minyan.getNotes()));
                }
            }
        }

        minyanEvents.sort(Comparator.comparing(MinyanEvent::getStartTime));
        mv.getModel().put("allminyanim", minyanEvents);

        return mv;
    }

    @GetMapping("/zmanim/next")
    public ModelAndView nextAfter(@RequestParam(value = "after", required = true) String dateString) {
        Date date = new Date(dateString);
        return navigate(new Date(date.getYear(), date.getMonth(), date.getDate() + 1, date.getHours(), date.getMinutes(), date.getSeconds()));
    }

    @GetMapping("/zmanim/last")
    public ModelAndView lastBefore(@RequestParam(value = "before", required = true) String dateString) {
        Date date = new Date(dateString);
        return navigate(new Date(date.getYear(), date.getMonth(), date.getDate() - 1, date.getHours(), date.getMinutes(), date.getSeconds()));
    }

    public ModelAndView navigate(Date date) {
        return zmanim(date);
    }
}