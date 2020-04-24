package com.dm.beans;

import com.dm.enums.AuthType;

/**
 * @author hu.yuhao
 * @date 2020-04-24
 * 授权状态
 * */
public class AuthToken {
    private AuthType type;
    private String message;
    private Object obj;

    public static AuthToken auth(String message) {
        AuthToken authToken = new AuthToken();
        authToken.setType(AuthType.AUTH);
        authToken.setMessage(message);
        return authToken;
    }

    public static AuthToken auth(String message, Object obj) {
        AuthToken authToken = new AuthToken();
        authToken.setType(AuthType.AUTH);
        authToken.setMessage(message);
        authToken.setObj(obj);
        return authToken;
    }

    public static AuthToken unauth(String message) {
        AuthToken authToken = new AuthToken();
        authToken.setType(AuthType.UNAUTH);
        authToken.setMessage(message);
        return authToken;
    }

    public static AuthToken unauth(String message, Object obj) {
        AuthToken authToken = new AuthToken();
        authToken.setType(AuthType.UNAUTH);
        authToken.setMessage(message);
        authToken.setObj(obj);
        return authToken;
    }

    public static AuthToken error(String message) {
        AuthToken authToken = new AuthToken();
        authToken.setType(AuthType.ERROR);
        authToken.setMessage(message);
        return authToken;
    }

    public static AuthToken error(String message, Object obj) {
        AuthToken authToken = new AuthToken();
        authToken.setType(AuthType.ERROR);
        authToken.setMessage(message);
        authToken.setObj(obj);
        return authToken;
    }

    public AuthType getType() {
        return type;
    }

    public void setType(AuthType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
