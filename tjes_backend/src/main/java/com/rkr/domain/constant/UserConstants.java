package com.rkr.domain.constant;

/**
 * @Package com.rkr.domain.constant
 * @auhter rkr
 * @date 2023/4/30 22:20
 * @description UserConstants:用户状态信息
 */
public class UserConstants
{
    /**
     * 系统用户标志
     */
    public static final String SYS_USER = "SYS_USER";

    /**
     * 用户、部门、字典状态
     */
    public static final String NORMAL = "0";//正常状态
    public static final String EXCEPTION = "1";//异常状态
    public static final String USER_DISABLE = "1";//用户停用状态
    public static final String ROLE_DISABLE = "1";//角色停用状态
    public static final String DEPT_NORMAL = "0";//部门正常状态
    public static final String DEPT_DISABLE = "1";//部门停用状态
    public static final String DICT_NORMAL = "0";//字典正常状态

    /**
     * 系统默认状态
     */
    public static final String YES = "Y";//是

    /**
     * 菜单状态
     */
    public static final String YES_FRAME = "0";//菜单外链（是）
    public static final String NO_FRAME = "1";//菜单外链（否）
    public static final String TYPE_DIR = "M";//菜单类型（目录）
    public static final String TYPE_MENU = "C";//菜单类型（菜单）
    public static final String TYPE_BUTTON = "F";//菜单类型（按钮）

    /**
     * Layout组件标识
     */
    public final static String LAYOUT = "Layout";//Layout组件标识

    /**
     * 校验返回结果码
     */
    public final static String UNIQUE = "0";//唯一
    public final static String NOT_UNIQUE = "1";//不唯一
}
