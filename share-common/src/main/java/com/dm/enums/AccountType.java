package com.dm.enums;

/**
 * @author hu.yuhao
 * 账户状态类型{正常，锁定，会员}
 * */
public enum AccountType {
    /*状态正常*/
    NORMAL('0'),

    /*状态锁定*/
    LOCKED('1'),

    /*无效状态*/
    INVALID('2');

    private char status;

    AccountType(char status) {
        this.status = status;
    }

    public char getStatus() {
        return status;
    }
}
