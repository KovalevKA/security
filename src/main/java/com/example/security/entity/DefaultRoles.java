package com.example.security.entity;

public enum DefaultRoles {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), GUEST("ROLE_GUEST");

    private String code;

    private DefaultRoles(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getShortCode() {
        return code.replace("ROLE_", "");
    }
}
