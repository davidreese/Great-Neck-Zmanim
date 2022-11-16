package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

public enum MinyanType {
    SHACHARIT("SHACHARIT"),
    MINCHA("MINCHA"),
    MAARIV("MAARIV"),
    SELICHOT("SELICHOT"),
    MEGILA_READING("MEGILAREADING");

    private String text;

    MinyanType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static MinyanType fromString(String text) {
        if (text != null) {
            for (MinyanType b : MinyanType.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
            throw new IllegalArgumentException("No constant with text " + text + " found");
    }


    @Override
    public String toString() {
        switch (this) {
            case SHACHARIT:
                return "SHACHARIT";
            case MINCHA:
                return "MINCHA";
            case ARVIT:
                return "ARVIT";
            case SELICHOT:
                return "SELICHOT";
            case MEGILA_READING:
                return "MEGILAREADING";
            default:
                return null;
        }
    }

    public String displayName() {
        switch (this) {
            case SHACHARIT:
                return "Shacharit";
            case MINCHA:
                return "Mincha";
            case ARVIT:
                return "Arvit";
            case SELICHOT:
                return "Selichot";
            case MEGILA_READING:
                return "Megila Reading";
            default:
                return null;
        }
    }

    public boolean isShacharit() {
        return this == SHACHARIT;
    }

    public boolean isMincha() {
        return this == MINCHA;
    }

    public boolean isArvit() {
        return this == ARVIT;
    }

    public boolean isSelichot() {
        return this == SELICHOT;
    }

    public boolean isMegilaReading() {
        return this == MEGILA_READING;
    }
}
