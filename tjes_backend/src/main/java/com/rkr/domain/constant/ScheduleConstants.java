package com.rkr.domain.constant;

/**
 * @Package com.rkr.domain.constant
 * @auhter rkr
 * @date 2023/4/30 22:20
 * @description ScheduleConstants:定时任务常量信息
 */
public class ScheduleConstants
{
    /**
     * 任务调度参数key
     */
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";//任务类名字
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";//任务属性

    /**
     * 任务调度状态
     */
    public static final String MISFIRE_DEFAULT = "0";//默认 misfire策略
    public static final String MISFIRE_IGNORE_MISFIRES = "1";//忽略 misfire策略
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";//执行 misfire策略
    public static final String MISFIRE_DO_NOTHING = "3";//不触发立即执行 misfire策略

    public enum Status
    {
        NORMAL("0"),//正常
        PAUSE("1");//暂停

        private String value;

        /**
         * 构造方法
         * @param value
         */
        private Status(String value)
        {
            this.value = value;
        }

        /**
         * 获取value
         * @return value
         */
        public String getValue()
        {
            return value;
        }
    }
}
