package com.hhu.myhjycommunity.common.enums;

/**
 * 用户状态枚举
 */
public enum UserStatus {
    OK("0","正常"),
    DISABLE("1","停用"),
    DELETES("2","删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
