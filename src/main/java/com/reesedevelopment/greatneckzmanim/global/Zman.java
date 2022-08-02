package com.reesedevelopment.greatneckzmanim.global;

public enum Zman {
    ALOT_HASHACHAR("ALOTHASHACHAR"),
    NETZ("NETZ"),
    CHATZOT("CHATZOT"),
    MINCHA_GEDOLA("MINCHAGEDOLA"),
    MINCHA_KETANA("MINCHAKETANA"),
    PLAG_HAMINCHA("PLAGHAMINCHA"),
    SHEKIYA("SHEKIYA"),
    TZET("TZET");

    private String text;

    Zman(String s) {
        this.text = s;
    }

    public String getText() {
        return this.text;
    }

    public static Zman fromString(String text) {
        if (text != null) {
        for (Zman b : Zman.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
    }
        throw new IllegalArgumentException("No constant with text " + text + " found");

    }

    public String displayString() {
        switch (this) {
            case ALOT_HASHACHAR:
                return "Alot HaShachar";
            case NETZ:
                return "Sunrise";
            case CHATZOT:
                return "Chatzot";
            case MINCHA_GEDOLA:
                return "Mincha Gedola";
            case MINCHA_KETANA:
                return "Mincha Ketana";
            case PLAG_HAMINCHA:
                return "Plag HaMincha";
            case SHEKIYA:
                return "Shekiya";
            case TZET:
                return "Tzet";
            default:
                return null;
        }
    }
}
