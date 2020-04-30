package com.dm.beans;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author hu.yuhao
 * */
public class EmailAuthentication extends Authenticator {
    private String username;
    private String password;

    public EmailAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
