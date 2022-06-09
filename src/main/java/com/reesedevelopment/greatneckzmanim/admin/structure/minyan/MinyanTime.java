package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;
import com.kosherjava.zmanim.util.Time;
import com.reesedevelopment.greatneckzmanim.global.Zmanim;

public class MinyanTime {
    private Time time;
    private TimeRule rule;

    public MinyanTime(String rawTime) {
        if (rawTime == null || rawTime.isEmpty()) {
            return;
        } else if (rawTime.equals("INVALID")) {
            throw new IllegalArgumentException("Invalid time");
        } else if (rawTime.startsWith("T")) {
            String[] parts = rawTime.substring(1).split(":");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid time");
            }
            time = new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        } else if (rawTime.startsWith("R")) {
            String[] parts = rawTime.substring(2).split(":");
//          TODO: VERIFY THIS WORKS WITH NEGATIVE OFFSETS
//          TODO: FIX valueOf
            if (parts.length == 2) {
                rule = new TimeRule(Zmanim.fromString(parts[0]), Integer.parseInt(parts[1]));
            } else {
                System.out.println("Invalid time rule: " + rawTime);
                return;
            }
        } else {
            System.out.println("Invalid time: " + rawTime);
            return;
        }
    }

    public MinyanTime(Time time) {
        this.time = time;
        this.rule = null;
    }

    public MinyanTime(TimeRule rule) {
        this.rule = rule;
        this.time = null;
    }

    public boolean isDynamic() {
        return rule != null && time == null;
    }

    @Override
    public String toString() {
        if (time != null && rule != null) {
            return "INVALID";
        } else if (time != null) {
            return String.format("T%d:%d:%d:%d", time.getHours(), time.getMinutes(), time.getSeconds(), time.getMilliseconds());
        } else if (rule != null) {
            return String.format("R%s:%d", rule.getZman(), rule.getOffsetMinutes());
        } else {
            return "INVALID";
        }
    }

    class TimeRule {
        private Zmanim zman;
        private Integer offsetMinutes;

        public TimeRule(Zmanim zman, Integer offsetMinutes) {
            this.zman = zman;
            this.offsetMinutes = offsetMinutes;
        }

        public Zmanim getZman() {
            return zman;
        }

        public Integer getOffsetMinutes() {
            return offsetMinutes;
        }
    }

    public String displayTime() {
        if (time == null && rule == null) {
            return "No Minyan";
        } else if (time != null && rule != null) {
            return "INVALID";
        } else if (time != null && rule == null) {
            String timeString = time.toString();
            String[] parts = timeString.split(":");

            if (parts.length != 3) {
                return "INVALID";
            }

            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            double seconds = Double.parseDouble(parts[2]);
            boolean isPM = hours >= 12;

            if (seconds > 30) {
                minutes += 1;
            }

            if (minutes == 60) {
                hours += 1;
                minutes -= 60;
            }

            if (isPM) {
                hours -= 12;
            }

            String hoursString = String.valueOf(hours);
            String minutesString = minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
            String amPmString = isPM ? "PM" : "AM";

            return hoursString + ":" + minutesString + " " + amPmString;

//            int indexOfDot = timeString.indexOf(".");
//            return timeString.substring(0, indexOfDot);
//            return String.format("%s:%s:%s", time.getHours(), time.getMinutes(), time.getSeconds());
        } else if (isDynamic()) {
//            return "Dynamic";
            if (rule.getOffsetMinutes() < 0) {
                return String.format("%s minus %d minutes", rule.getZman().displayString(), Math.abs(rule.getOffsetMinutes()));
            } else if (rule.getOffsetMinutes() == 0) {
                return rule.getZman().displayString();
            }  else if (rule.getOffsetMinutes() > 0) {
                return String.format("%s plus %d minutes", rule.getZman().displayString(), rule.getOffsetMinutes());
            } else {
                return "INVALID";
            }
        } else {
            return "INVALID";
        }
    }
}
