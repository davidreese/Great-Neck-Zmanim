package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

public enum MinyanType {
    SHACHARIT,
    MINCHA,
    ARVIT,
    SELICHOT,
    MEGILA_READING;

    @Override
    public String toString() {
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
}
