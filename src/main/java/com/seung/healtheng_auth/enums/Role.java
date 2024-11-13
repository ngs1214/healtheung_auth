package com.seung.healtheng_auth.enums;

public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_SELLER("판매자"),
    ROLE_CONSUMER("구매자");

    private String roleName;
    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
