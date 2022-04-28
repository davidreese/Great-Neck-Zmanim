package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;
import com.kosherjava.zmanim.util.Time;
import com.reesedevelopment.greatneckzmanim.global.Zmanim;

public class MinyanTime {
    private Time time;
    private TimeRule rule;

    public MinyanTime(String rawTime) {
        if (rawTime.equals("INVALID") || rawTime.isEmpty()) {
            throw new IllegalArgumentException("Invalid time");
        } else if (rawTime.startsWith("T")) {
            String[] parts = rawTime.substring(1).split(":");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid time");
            }
            time = new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        } else if (rawTime.startsWith("R")) {
            String[] parts = rawTime.substring(1).split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid time");
            }
//          TODO: VERIFY THIS WORKS WITH NEGATIVE OFFSETS
//          TODO: FIX valueOf
            rule = new TimeRule(Zmanim.valueOf(parts[0]), Integer.parseInt(parts[1]));
        } else {
            throw new IllegalArgumentException("Invalid time");
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
}
