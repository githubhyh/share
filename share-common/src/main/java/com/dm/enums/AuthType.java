package com.dm.enums;

/**
 * @author hu.yuhao
 * 认证结果{认证成功，认证失败，无法认证}
 * */
public enum AuthType {

    AUTH('0'),

    UNAUTH('1'),

    ERROR('2');

    private char type;

    AuthType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }
}
