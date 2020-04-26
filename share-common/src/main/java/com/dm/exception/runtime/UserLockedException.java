package com.dm.exception.runtime;

import com.dm.exception.BaseException;

public class UserLockedException extends BaseException {
    public UserLockedException(String msg) {
        super(msg);
    }
}
