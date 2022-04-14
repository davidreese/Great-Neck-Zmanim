package com.reesedevelopment.greatneckzmanim.admin.structure;

public enum Role {
    ADMIN, USER;

    public static Role getRole(Integer id) {
        switch (id) {
            case 1 -> {
                return ADMIN;
            }
            case 2 -> {
                return USER;
            }
            default -> {
                return null;
            }
        }
    }

    public Integer getId() {
        switch (this) {
            case ADMIN -> {
                return 1;
            }
            case USER -> {
                return 2;
            }
            default -> {
                return null;
            }
        }
    }

    public String getName() {
        switch (this) {
            case ADMIN -> {
                return "ROLE_ADMIN";
            }
            case USER -> {
                return "ROLE_USER";
            }
            default -> {
                return null;
            }
        }
    }

    public String getDisplayName() {
        switch (this) {
            case ADMIN -> {
                return "Admin";
            }
            case USER -> {
                return "User";
            }
            default -> {
                return null;
            }
        }
    }
}
