package com.reesedevelopment.greatneckzmanim.global;

public enum Nusach {
    EDOT_HAMIZRACH("EDOT_HAMIZRACH"),
    SEFARD("SEFARD"),
    ASHKENAZ("ASHKENAZ"),
    UNSPECIFIED("UNSPECIFIED");

    private String text;

    Nusach(String s) {
        this.text = s;
    }

    public String getText() {
        return this.text;
    }

//    from string
    public static Nusach fromString(String text) {
        if (text != null) {
            for (Nusach b : Nusach.values()) {
                if (b.text.equalsIgnoreCase(text)) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    public String displayString() {
        switch (this) {
            case EDOT_HAMIZRACH:
                return "Edot Hamizrach";
            case SEFARD:
                return "Sefard";
            case ASHKENAZ:
                return "Ashkenaz";
            case UNSPECIFIED:
                return "Unspecified";
            default:
                return null;
        }
    }

    public boolean isUnspecified() {
        return this == UNSPECIFIED;
    }

    public boolean isEdotHamizrach() {
        return this == EDOT_HAMIZRACH;
    }

    public boolean isSefard() {
        return this == SEFARD;
    }

    public boolean isAshkenaz() {
        return this == ASHKENAZ;
    }
}
