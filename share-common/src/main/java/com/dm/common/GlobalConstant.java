package com.dm.common;

/**
 * @author hu.yuhao
 * @date 2020-04-24
 * define global constant
 * */
public class GlobalConstant {
    //登录时异常信息
    public static String LOGIN_ACCOUNT_ERROR            =   "用户名或密码错误";
    public static String LOGIN_ACCOUNT_NULL             =   "用户名或密码为空";
    public static String LOGIN_ACCOUNT_NOT_REGISTER     =   "用户未注册";
    public static String LOGIN_SUCCESS                  =   "登录成功";
    public static String LOGIN_ACCOUNT_LOCKED          =   "账户已被锁定";
    public static String LOGIN_ROLE_LOCKED             =   "角色已被锁定";
    public static String LOGIN_ACCOUNT_INVALID          =   "非法账户";

    //数据验证
    public static String VALIDATE_LOGIN_INFO_ILLEGAL    =   "the data is illegal";
    public static String VALIDATE_LOGIN_INFO_ERROR      =   "check the login information";
    public static String VALIDATE_REGISTER_INFO_ILLEGAL =   "check the registering information";
    public static String VALIDATE_ILLEGAL               =   "数据验证失败，请检查输入数据";


    //shiro session redis key prefix
    public static String PREFIX_OF_REDIS_SESSION_KEY    =   "SHIRO_REDIS_SESSION_KEY:";

    //邮件服务器和发送人设置
    public static String SMTP_SERVER                    =   "smtp.163.com";
    public static String USERNAME                       =   "15907227396@163.com";
    public static String PASSWORD                       =   "RVTCBNXDAHFAXZZX";
    public static String PROTOCOL                       =   "smtp";
    public static String FROM                           =   "15907227396@163.com";
}
