package com.dm.exception.runtime;

import com.dm.exception.BaseException;

public class UserPasswordNotMatchException extends BaseException {
    public UserPasswordNotMatchException(String msg) {
        super(msg);
    }
}
