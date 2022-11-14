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
import com.reesedevelopment.greatneckzmanim.global.Nusach;
import com.reesedevelopment.greatneckzmanim.global.Zman;
import com.reesedevelopment.greatneckzmanim.front.ZmanimHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class ZmanimController {
    TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

    String locationName = "Teaneck, NJ";
    double latitude = 40.906871;
    double longitude = -74.020924;
    double elevation = 0;
    GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy | h:mm aa");
    SimpleDateFormat onlyDateFormat = new SimpleDateFormat("EEEE, MMMM d");
    SimpleDateFormat strippedDayFormat = new SimpleDateFormat("MMMM d");
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa");

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
        timeFormat.setTimeZone(timeZone);
        dateFormat.setTimeZone(timeZone);
        onlyDateFormat.setTimeZone(timeZone);
        strippedDayFormat.setTimeZone(timeZone);

        return zmanim(new Date());
    }

    private String timeFormatWithRoundingToMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 30);
        return timeFormat.format(calendar.getTime());
    }

    public ModelAndView zmanim(Date date) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("homepage");

//        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.US);
        mv.getModel().put("date", dateFormat.format(date));
        mv.getModel().put("onlyDate", onlyDateFormat.format(date));

        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.DATE, 1);
        mv.getModel().put("tommorowOnlyDate", onlyDateFormat.format(c.getTime()));
        mv.getModel().put("tommorowStrippedDay", strippedDayFormat.format(c.getTime()));


        c.setTime(date);
        c.add(Calendar.DATE, -1);
        mv.getModel().put("yesterdayOnlyDate", onlyDateFormat.format(c.getTime()));
        mv.getModel().put("yesterdayStrippedDay", strippedDayFormat.format(c.getTime()));

        Date today = new Date();
        mv.getModel().put("isToday", onlyDateFormat.format(date).equals(onlyDateFormat.format(today)));

        mv.getModel().put("dateString", date.toString());
//        mv.getModel(),put("longdate", )

//        add hebrew date
        mv.getModel().put("hebrewDate", zmanimHandler.getHebrewDate(date));

        timeFormat.setTimeZone(timeZone);

        Dictionary zmanim = zmanimHandler.getZmanim(LocalDate.of(date.getYear() + 1900, date.getMonth(), date.getDate()));

        mv.getModel().put("alotHashachar", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.ALOT_HASHACHAR)));
        mv.getModel().put("sunrise", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.NETZ)));
        mv.getModel().put("chatzot", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.CHATZOT)));
        mv.getModel().put("minchaGedola", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.MINCHA_GEDOLA)));
        mv.getModel().put("minchaKetana", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.MINCHA_KETANA)));
        mv.getModel().put("plagHamincha", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.PLAG_HAMINCHA)));
        mv.getModel().put("shekiya", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.SHEKIYA)));
        mv.getModel().put("tzet", timeFormatWithRoundingToMinute((Date) zmanim.get(Zman.TZET)));

//        get minyanim closest in time to now
//        todo: only get items with non null time for date
        List<Minyan> enabledMinyanim = minyanDAO.getEnabled();
        List<MinyanEvent> minyanEvents = new ArrayList<>();

        for (Minyan minyan : enabledMinyanim) {
            LocalDate ref = LocalDate.of(date.getYear() + 1900, date.getMonth(), date.getDate()).plusMonths(1);
            Date startDate = minyan.getStartDate(ref);
            Date now = new Date();
            //Date terminationDate = new Date(now.getTime() - (60000 * 8));
            System.out.println("SD: " + startDate);
            System.out.println("TD: " + terminationDate);
            //if (startDate != null && (startDate.after(terminationDate) || now.getDate() != startDate.getDate())) {
            if (startDate != null || now.getDate() != startDate.getDate())) {    
                String organizationName;
                Nusach organizationNusach;
                String organizationId;
                Organization organization = minyan.getOrganization();
                if (organization == null) {
                    Organization temp = organizationDAO.findById(minyan.getOrganizationId());
                    organizationName = temp.getName();
                    organizationNusach = temp.getNusach();
                    organizationId = temp.getId();
                } else {
                    organizationName = organization.getName();
                    organizationNusach = organization.getNusach();
                    organizationId = organization.getId();
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
                    minyanEvents.add(new MinyanEvent(minyan.getId(), minyan.getType(), organizationName, organizationNusach, organizationId, locationName, startDate, dynamicDisplayName, minyan.getNusach(), minyan.getNotes()));
                } else {
                    minyanEvents.add(new MinyanEvent(minyan.getId(), minyan.getType(), organizationName, organizationNusach, organizationId, locationName, startDate, minyan.getNusach(), minyan.getNotes()));
                }
            } /*else {
                if (startDate != null) {
                    System.out.println("Skipping minyan with start date: " + startDate.toString());
                } else {
                    System.out.println("Skipping minyan with null start date.");
                }
            }*/
        }

        minyanEvents.sort(Comparator.comparing(MinyanEvent::getStartTime));
        mv.getModel().put("allminyanim", minyanEvents);

        List<MinyanEvent> shacharitMinyanim = new ArrayList<>();
        List<MinyanEvent> minchaMinyanim = new ArrayList<>();
        List<MinyanEvent> arvitMinyanim = new ArrayList<>();
        for (MinyanEvent me : minyanEvents) {
            if (me.getType().isShacharit()) {
                shacharitMinyanim.add(me);
            } else if (me.getType().isMincha()) {
                minchaMinyanim.add(me);
            } else if (me.getType().isArvit()) {
                arvitMinyanim.add(me);
            }
        }
        mv.getModel().put("shacharitMinyanim", shacharitMinyanim);
        mv.getModel().put("minchaMinyanim", minchaMinyanim);
        mv.getModel().put("arvitMinyanim", arvitMinyanim);

        return mv;
    }

    @GetMapping("/zmanim/next")
    public ModelAndView nextZmanimAfter(@RequestParam(value = "after", required = true) String dateString) {
        Date date = new Date(dateString);
        return navigateZmanim(new Date(date.getYear(), date.getMonth(), date.getDate() + 1, date.getHours(), date.getMinutes(), date.getSeconds()));
    }

    @GetMapping("/zmanim/last")
    public ModelAndView lastZmanimBefore(@RequestParam(value = "before", required = true) String dateString) {
        Date date = new Date(dateString);
        return navigateZmanim(new Date(date.getYear(), date.getMonth(), date.getDate() - 1, date.getHours(), date.getMinutes(), date.getSeconds()));
    }

    @GetMapping("/orgs/{id}/next")
    public ModelAndView nextOrgAfter(@PathVariable String id, @RequestParam(value = "after", required = true) String dateString) throws Exception {
        Date date = new Date(dateString);
        return navigateOrg(id, new Date(date.getYear(), date.getMonth(), date.getDate() + 1, date.getHours(), date.getMinutes(), date.getSeconds()));
    }

    @GetMapping("/orgs/{id}/last")
    public ModelAndView lastOrgBefore(@PathVariable String id, @RequestParam(value = "before", required = true) String dateString) throws Exception {
        Date date = new Date(dateString);
        return navigateOrg(id, new Date(date.getYear(), date.getMonth(), date.getDate() - 1, date.getHours(), date.getMinutes(), date.getSeconds()));
    }

    public ModelAndView navigateZmanim(Date date) {
        return zmanim(date);
    }

    public ModelAndView navigateOrg(String orgId, Date date) throws Exception {
        return org(orgId, date);
    }

    public ModelAndView org(String orgId, Date date) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("org");

        dateFormat.setTimeZone(timeZone);

//        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.US);
        mv.getModel().put("date", dateFormat.format(date));
        mv.getModel().put("onlyDate", onlyDateFormat.format(date));

        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.DATE, 1);
        mv.getModel().put("tommorowOnlyDate", onlyDateFormat.format(c.getTime()));
        mv.getModel().put("tommorowStrippedDay", strippedDayFormat.format(c.getTime()));

        c.setTime(date);
        c.add(Calendar.DATE, -1);
        mv.getModel().put("yesterdayOnlyDate", onlyDateFormat.format(c.getTime()));
        mv.getModel().put("yesterdayStrippedDay", strippedDayFormat.format(c.getTime()));

        Date today = new Date();
        mv.getModel().put("isToday", onlyDateFormat.format(date).equals(onlyDateFormat.format(today)));

        mv.getModel().put("dateString", date.toString());
//        mv.getModel(),put("longdate", )

//        add hebrew date
        mv.getModel().put("hebrewDate", zmanimHandler.getHebrewDate(date));

        try {
            Organization org = organizationDAO.findById(orgId);
            mv.addObject("org", org);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Sorry, there was an error finding the organization.");
        }

        List<Minyan> enabledMinyanim = minyanDAO.findEnabledMatching(orgId);
        List<MinyanEvent> minyanEvents = new ArrayList<>();
//        boolean usesLocations;
//        boolean nusachChanges;
//        Nusach lastNusach;
//        boolean usesNotes;

        for (Minyan minyan : enabledMinyanim) {
            Date startDate = minyan.getStartDate(LocalDate.of(date.getYear() + 1900, date.getMonth(), date.getDate()).plusMonths(1));
            //Date terminationDate = new Date((new Date()).getTime() - (60000 * 20));
            //if (startDate != null && startDate.after(terminationDate)) {
            if (startDate != null) {    
                String organizationName;
                Nusach organizationNusach;
                String organizationId;
                Organization organization = minyan.getOrganization();
                if (organization == null) {
                    Organization temp = organizationDAO.findById(minyan.getOrganizationId());
                    organizationName = temp.getName();
                    organizationId = temp.getId();
                    organizationNusach = temp.getNusach();
                } else {
                    organizationName = organization.getName();
                    organizationId = organization.getId();
                    organizationNusach = organization.getNusach();
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
                    minyanEvents.add(new MinyanEvent(minyan.getId(), minyan.getType(), organizationName, organizationNusach, organizationId, locationName, startDate, dynamicDisplayName, minyan.getNusach(), minyan.getNotes()));
                } else {
                    minyanEvents.add(new MinyanEvent(minyan.getId(), minyan.getType(), organizationName, organizationNusach, organizationId, locationName, startDate, minyan.getNusach(), minyan.getNotes()));
                }
            }
        }

        minyanEvents.sort(Comparator.comparing(MinyanEvent::getStartTime));
        mv.getModel().put("allminyanim", minyanEvents);

        List<MinyanEvent> shacharitMinyanim = new ArrayList<>();
        List<MinyanEvent> minchaMinyanim = new ArrayList<>();
        List<MinyanEvent> arvitMinyanim = new ArrayList<>();
        for (MinyanEvent me : minyanEvents) {
            if (me.getType().isShacharit()) {
                shacharitMinyanim.add(me);
            } else if (me.getType().isMincha()) {
                minchaMinyanim.add(me);
            } else if (me.getType().isArvit()) {
                arvitMinyanim.add(me);
            }
        }
        mv.getModel().put("shacharitMinyanim", shacharitMinyanim);
        mv.getModel().put("minchaMinyanim", minchaMinyanim);
        mv.getModel().put("arvitMinyanim", arvitMinyanim);

//        mv.getModel().put("usesLocations", minyanEvents.)

        return mv;
    }

    @RequestMapping("/orgs/{orgId}")
    public ModelAndView orgToday(@PathVariable String orgId) throws Exception {
        return org(orgId, new Date());
    }
}