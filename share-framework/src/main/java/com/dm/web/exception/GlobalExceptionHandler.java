package com.dm.web.exception;

import com.dm.beans.AuthToken;
import com.dm.exception.runtime.UserLockedException;
import com.dm.exception.runtime.UserNotFoundException;
import com.dm.exception.runtime.UserPasswordNotMatchException;
import com.dm.exception.runtime.UserValidateException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.HashMap;

/**
 * @author hu.yuhao
 * 前端异常处理
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {UserPasswordNotMatchException.class})
    public AuthToken passwordNotMatch(UserPasswordNotMatchException e) {
        return AuthToken.unauth(e.getMessage(), null);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public AuthToken userNotFound(UserNotFoundException e) {
        return AuthToken.unauth(e.getMessage(), null);
    }

    @ExceptionHandler(value = {UserValidateException.class})
    public AuthToken userIllegal(UserValidateException e) {
        return AuthToken.unauth(e.getMessage(), null);
    }

    @ExceptionHandler(value = {UserLockedException.class})
    public AuthToken userLocked(UserLockedException e) {
        return AuthToken.unauth(e.getMessage(), null);
    }

    @ExceptionHandler(value = {AuthorizationException.class})
    public AuthToken unauth(AuthorizationException e) {
        return AuthToken.unauth(e.getMessage(), null);
    }

    @ExceptionHandler(value = {TemplateInputException.class})
    public Object notFoundTemplate(TemplateInputException e) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        map.put("code", "404");
        return map;
    }
}
