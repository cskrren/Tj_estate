package com.rkr.domain.constant;

/**
 * @Package com.rkr.domain.constant
 * @auhter rkr
 * @date 2023/4/30 21:51
 * @description Constants:常量类
 */

public class Constants
{
    /**
     * 编码方式
     */
    public static final String UTF8 = "UTF-8";//UTF-8编码
    public static final String GBK = "GBK";//GBK编码

    /**
     * 网址前缀
     */
    public static final String HTTP = "http://";//HTTP网址前缀
    public static final String HTTPS = "https://";//HTTPS网址前缀

    /**
     * 标识
     */
    public static final String SUCCESS = "0";//成功标识
    public static final String FAIL = "1";//失败标识

    /**
     * 用户状态
     */
    public static final String LOGIN_SUCCESS = "Success";//登录成功
    public static final String LOGOUT = "Logout";//退出
    public static final String LOGIN_FAIL = "Error";//登录失败

    /**
     * 验证码相关定义
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";//验证码 key
    public static final Integer CAPTCHA_EXPIRATION = 2;//验证码有效期（分钟）

    /**
     * 令牌相关定义
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";//登录令牌
    public static final String TOKEN = "token";//令牌
    public static final String TOKEN_PREFIX = "Bearer ";//令牌前缀

    /**
     * 用户相关定义
     */
    public static final String LOGIN_USER_KEY = "login_user_key";//登录用户 key
    public static final String JWT_USERID = "userid";//用户ID
    public static final String JWT_USERNAME = "sub";//用户名称
    public static final String JWT_AVATAR = "avatar";//用户头像
    public static final String JWT_CREATED = "created";//创建时间
    public static final String JWT_AUTHORITIES = "authorities";//用户权限

    /**
     * 其他定义
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";//重复提交 key
    public static final String SYS_CONFIG_KEY = "sys_config:";//系统参数 key
    public static final String SYS_DICT_KEY = "sys_dict:";//系统字典 key
}
