package com.dm.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * @author hu.yuhao
 * 抽取公共属性，预留参数
 * */
public class BaseEntity implements Serializable {
    //附加字段，登录识别ID（loginName，email，phoneNumber均可）
    private String loginID;

    //repeat password
    private String repeatPassword;

    //搜索值
    private String searchValue;

    //请求参数
    private Map<String, Object> params;

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
