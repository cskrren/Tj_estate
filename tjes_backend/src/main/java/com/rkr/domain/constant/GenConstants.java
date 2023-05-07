package com.rkr.domain.constant;

/**
 * @Package com.rkr.domain.constant
 * @auhter rkr
 * @date 2023/4/30 21:59
 * @description GenConstants:代码生成通用常量
 */

public class GenConstants
{
    /**
     * 增删改查
     */
    public static final String TPL_CRUD = "crud";//单表（增删改查）
    public static final String TPL_TREE = "tree";//树表（增删改查）

    /**
     * 树编码字段
     */
    public static final String TREE_CODE = "treeCode";//树编码字段
    public static final String TREE_PARENT_CODE = "treeParentCode";//树父编码字段
    public static final String TREE_NAME = "treeName";//树名称字段

    /**
     * 上级菜单
     */
    public static final String PARENT_MENU_ID = "parentMenuId";//ID字段
    public static final String PARENT_MENU_NAME = "parentMenuName";//名称字段

    /**
     * 数据库类型
     */
    public static final String[] COLUMNTYPE_STR =
            { "char", "varchar", "nvarchar", "varchar2", "tinytext", "text", "mediumtext", "longtext" };//字符类型串
    public static final String[] COLUMNTYPE_TIME =
            { "datetime", "time", "date", "timestamp" };//时间类型
    public static final String[] COLUMNTYPE_NUMBER =
            { "tinyint", "smallint", "mediumint", "int", "number", "integer", "bit", "bigint", "float", "float", "double", "decimal" };//数字类型

    /**
     * 页面不需要
     */
    public static final String[] COLUMNNAME_NOT_EDIT =
            { "id", "create_by", "create_time", "del_flag" };//页面不需要编辑的字段
    public static final String[] COLUMNNAME_NOT_LIST =
            { "id", "create_by", "create_time", "del_flag", "update_by", "update_time" };//页面不需要显示的字段
    public static final String[] COLUMNNAME_NOT_QUERY =
            { "id", "create_by", "create_time", "del_flag", "update_by", "update_time", "remark" };//页面不需要查询的字段

    /**
     * 基类字段
     */
    public static final String[] BASE_ENTITY =
            { "createBy", "createTime", "updateBy", "updateTime", "remark" };//基类字段
    public static final String[] TREE_ENTITY =
            { "parentName", "parentId", "orderNum", "ancestors", "children" };//树基类字段

    /**
     * 通用常量
     */
    public static final String HTML_INPUT = "input";//输入框
    public static final String HTML_TEXTAREA = "textarea";//文本域
    public static final String HTML_SELECT = "select";//下拉框
    public static final String HTML_RADIO = "radio";//单选框
    public static final String HTML_CHECKBOX = "checkbox";//复选框
    public static final String HTML_DATETIME = "datetime";//时间控件
    public static final String HTML_EDITOR = "editor";//富文本控件
    public static final String TYPE_STRING = "String";//字符串类型
    public static final String TYPE_INTEGER = "Integer";//整型
    public static final String TYPE_LONG = "Long";//长整型
    public static final String TYPE_DOUBLE = "Double";//双精度浮点型
    public static final String TYPE_BIGDECIMAL = "BigDecimal";//高精度浮点型
    public static final String TYPE_DATE = "Date";//日期类型
    public static final String QUERY_LIKE = "LIKE";//模糊查询
    public static final String REQUIRE = "1";//必填
}
