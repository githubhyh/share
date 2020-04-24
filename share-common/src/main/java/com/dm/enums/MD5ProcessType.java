package com.dm.enums;

public enum MD5ProcessType {
    LOGIN(0), REGISTER(1);
    private int type;

    MD5ProcessType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
