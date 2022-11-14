package com.reesedevelopment.greatneckzmanim.global;

public enum Zman {
    ALOT_HASHACHAR("ALOT_HASHACHAR"),
    NETZ("NETZ"),
    SZKS(s: "SZKS"),
    SZT("SZT"),
    CHATZOT("CHATZOT"),
    MINCHA_GEDOLA("MINCHA_GEDOLA"),
    MINCHA_KETANA("MINCHA_KETANA"),
    PLAG_HAMINCHA("PLAG_HAMINCHA"),
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

    public String displayName() {
        switch (this) {
            case ALOT_HASHACHAR:
                return "Alos HaShachar";
            case NETZ:
                return "Sunrise";
            case SZKS:
                return "Sof Zman Krias Shma";
            case SZT:
                return "Sof Zman Tefilla";   
            case CHATZOT:
                return "Chatzos";
            case MINCHA_GEDOLA:
                return "Mincha Gedola";
            case MINCHA_KETANA:
                return "Mincha Ketana";
            case PLAG_HAMINCHA:
                return "Plag HaMincha";
            case SHEKIYA:
                return "Shekiya";
            case TZET:
                return "Tzes";
            default:
                return null;
        }
    }
}
