package com.reesedevelopment.greatneckzmanim.global;

public enum Zmanim {
    ALOT_HASHACHAR("ALOTHASHACHAR"),
    SUNRISE("SUNRISE"),
    CHATZOT("CHATZOT"),
    MINCHA_GEDOLA("MINCHAGEDOLA"),
    MINCHA_KETANA("MINCHAKETANA"),
    SUNSET("SUNSET"),
    TZAIT("TZAIT");

    private String text;

    Zmanim(String s) {
        this.text = s;
    }

    public String getText() {
        return this.text;
    }

    public static Zmanim fromString(String text) {
        if (text != null) {
        for (Zmanim b : Zmanim.values()) {
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
                return "Alot Hashachar";
            case SUNRISE:
                return "Sunrise";
            case CHATZOT:
                return "Chatzot";
            case MINCHA_GEDOLA:
                return "Mincha Gedola";
            case MINCHA_KETANA:
                return "Mincha Ketana";
            case SUNSET:
                return "Sunset";
            case TZAIT:
                return "Tzait";
            default:
                return null;
        }
    }
}
