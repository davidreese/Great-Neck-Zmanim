package com.reesedevelopment.greatneckzmanim.front.controllers;

import com.kosherjava.zmanim.util.GeoLocation;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
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

    private void setTimeZone(TimeZone tz) {
        // set time format
        timeFormat.setTimeZone(tz);
        dateFormat.setTimeZone(tz);
        onlyDateFormat.setTimeZone(tz);
        strippedDayFormat.setTimeZone(tz);
    }

    @GetMapping("/zmanim")
    public ModelAndView todaysZmanim() {
        System.out.println("Displaying today's zmanim...");
        return zmanim(new Date());
    }

    private String timeFormatWithRoundingToSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 30);
        calendar.setTimeZone(timeZone);
        return timeFormat.format(calendar.getTime());
    }

    public ModelAndView zmanim(Date date) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("homepage");

        System.out.println("DEBUG: Adding dates to model");

        // adding dates to model data
        setTimeZone(timeZone);
//        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.US);
        mv.getModel().put("date", dateFormat.format(date));
        mv.getModel().put("onlyDate", onlyDateFormat.format(date));


        Calendar c = Calendar.getInstance();

        // adds model data for tommorow's date
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        mv.getModel().put("tommorowOnlyDate", onlyDateFormat.format(c.getTime()));
        mv.getModel().put("tommorowStrippedDay", strippedDayFormat.format(c.getTime()));


        // adds model data for yesterday's date
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        mv.getModel().put("yesterdayOnlyDate", onlyDateFormat.format(c.getTime()));
        mv.getModel().put("yesterdayStrippedDay", strippedDayFormat.format(c.getTime()));

        Date today = new Date();
        mv.getModel().put("isToday", onlyDateFormat.format(date).equals(onlyDateFormat.format(today)));
        // adds today's date as a string to the model
        // System.out.println(date.toString());
        mv.getModel().put("dateString", date.toString());

//        add today's hebrew date
        mv.getModel().put("hebrewDate", zmanimHandler.getHebrewDate(date));

        System.out.println("DEBUG: Fetching zmanim for model");

        LocalDate localDate = dateToLocalDate(date);
        System.out.println("Showing zmanim for date: " + localDate.getMonth() + ":" + localDate.getMonthValue() + ":" + localDate.getMonth().getValue() + ":" + localDate.toString());

        Dictionary<Zman, Date> zmanim = zmanimHandler.getZmanim(localDate);

        System.out.println("DEBUG: Putting zmanim in model");

        mv.getModel().put("alotHashachar", timeFormatWithRoundingToSecond(zmanim.get(Zman.ALOT_HASHACHAR)));
        mv.getModel().put("sunrise", timeFormatWithRoundingToSecond(zmanim.get(Zman.NETZ)));
        mv.getModel().put("chatzot", timeFormatWithRoundingToSecond(zmanim.get(Zman.CHATZOT)));
        mv.getModel().put("minchaGedola", timeFormatWithRoundingToSecond(zmanim.get(Zman.MINCHA_GEDOLA)));
        mv.getModel().put("minchaKetana", timeFormatWithRoundingToSecond(zmanim.get(Zman.MINCHA_KETANA)));
        mv.getModel().put("plagHamincha", timeFormatWithRoundingToSecond(zmanim.get(Zman.PLAG_HAMINCHA)));
        mv.getModel().put("shekiya", timeFormatWithRoundingToSecond(zmanim.get(Zman.SHEKIYA)));
        mv.getModel().put("earliestShema", timeFormatWithRoundingToSecond(zmanim.get(Zman.EARLIEST_SHEMA)));
        mv.getModel().put("tzet", timeFormatWithRoundingToSecond(zmanim.get(Zman.TZET)));

        List<MinyanEvent> upcomingMinyanim = getMinyanEventsOnDate(minyanDAO.getEnabled(), localDate, true);
        mv.getModel().put("allminyanim", upcomingMinyanim);

        List<MinyanEvent> shacharitMinyanim = new ArrayList<>();
        List<MinyanEvent> minchaMinyanim = new ArrayList<>();
        List<MinyanEvent> arvitMinyanim = new ArrayList<>();

        for (MinyanEvent me : upcomingMinyanim) {
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

    /**
     * Gets an ordered list of minyan events for this date.
     * @param date on which to find minyan events for.
     * @param skipMinyanimThatAlreadyPassed if a minyan has already passed and the date is today, don't return those minyan events.
     * @return all minyan events scheduled to happen on this day.
     */
    private List<MinyanEvent> getMinyanEventsOnDate(Collection<Minyan> minyanim, LocalDate date, boolean skipMinyanimThatAlreadyPassed) {
        // get minyanim closest in time to the date
        List<MinyanEvent> minyanEvents = new ArrayList<>();

        System.out.println("DEBUG: Filtering through minyanim");

        for (Minyan minyan : minyanim) {
            Date startDate = minyan.getStartDate(date);

            Date now = new Date();

            Date terminationDate = new Date(now.getTime() - (60000 * 8));

            System.out.println("SD: " + startDate);
            System.out.println("TD: " + terminationDate);

            // start date must be valid AND (be after the termination date OR date must not be the same date as today, to disregard the termination time when the user is looking ahead, OR must have the option to skip passed minyanim disabled)
            if (startDate != null && (startDate.after(terminationDate) || !sameDayOfMonth(now, startDate) || !skipMinyanimThatAlreadyPassed)) {
                // show the minyan
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
        }
        }

        minyanEvents.sort(Comparator.comparing(MinyanEvent::getStartTime));
        return minyanEvents;
    }

    /**
     * Converts a Date object to a LocalDate.
     * @param date the date to be converted
     * @return     the LocalDate equivalent
     */
    private static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return zonedDateTime.toLocalDate();
    }

/**
 * Tests two Date objects to see if they occur on the same date of the month.
 * @return whether or not the dates are on the same day of the month.
 */
    private static boolean sameDayOfMonth(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    /**
     * Navigates to the zmanim for the next day after the given one.
     * @param dateString the standard form of a date that should be incremented and displayed
     * @throws ParseException
     */
    @GetMapping("/zmanim/next")
    public ModelAndView nextZmanimAfter(@RequestParam(value = "after", required = true) String dateString) throws ParseException {
        // System.out.println("Going to zmanim page for date string: " + dateString);
        Date date = format.parse(dateString);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return navigateZmanim(calendar.getTime());
    }

    /**
     * Navigates to the zmanim for the day before the given one
     * @param dateString the standard form of a date that should be decremented and displayed
     * @throws ParseException
     */
    @GetMapping("/zmanim/last")
    public ModelAndView lastZmanimBefore(@RequestParam(value = "before", required = true) String dateString) throws ParseException {
        Date date = format.parse(dateString);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return navigateZmanim(calendar.getTime());
    }

    /**
     * Navigates to the organizaton page of an organization for the day after the given date.
     * @param id         the id of the organization whose page should be navigated to
     * @param dateString the standard form of a date that should be incremented and displayed
     * @throws ParseException
     */
    @GetMapping("/orgs/{id}/next")
    public ModelAndView nextOrgAfter(@PathVariable String id, @RequestParam(value = "after", required = true) String dateString) throws ParseException {
        Date date = format.parse(dateString);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return org(id, calendar.getTime());
    }

    /**
     * Navigates to the organizaton page of an organization for the day before the given date.
     * @param id         the id of the organization whose page should be navigated to
     * @param dateString the standard form of a date that should be decremented and displayed
     * @throws ParseException
     */
    @GetMapping("/orgs/{id}/last")
    public ModelAndView lastOrgBefore(@PathVariable String id, @RequestParam(value = "before", required = true) String dateString) throws ParseException {
        Date date = format.parse(dateString);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return org(id, calendar.getTime());
    }

    public ModelAndView navigateZmanim(Date date) {
        return zmanim(date);
    }

    public ModelAndView navigateOrg(String orgId, Date date) {
        return org(orgId, date);
    }

    public ModelAndView org(String orgId, Date date) {
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

            Organization org = organizationDAO.findById(orgId);
            mv.addObject("org", org);

        LocalDate localDate = dateToLocalDate(date);

        List<Minyan> enabledMinyanim = minyanDAO.findEnabledMatching(orgId);
        List<MinyanEvent> minyanEvents = new ArrayList<>();
//        boolean usesLocations;
//        boolean nusachChanges;
//        Nusach lastNusach;
//        boolean usesNotes;
        for (Minyan minyan : enabledMinyanim) {
            // TODO: check if this is an accurate line
            Date startDate = minyan.getStartDate(localDate);
            Date terminationDate = new Date((new Date()).getTime() - (60000 * 20));
            if (startDate != null && startDate.after(terminationDate)) {
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