package com.rkr.utils;

import com.rkr.domain.entity.SysJob;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @Package com.rkr.utils
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description QuartzUtils:定时任务工具类
 */
@Component
public class QuartzUtils {
    private Scheduler scheduler = null;

    /**
     * 添加任务
     * @param sysJob
     * @return boolean
     */
    public boolean add(SysJob sysJob) {
        try {
            Class clazz = Class.forName(sysJob.getClassPath());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(sysJob.getName(), sysJob.getGroupName()).build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCron());
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(sysJob.getName(), sysJob.getGroupName())
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
            // 如果当前添加的任务状态为「暂停」,则无需启动此任务。
            if(sysJob.getStatus().equals("1")){
                stop(sysJob.getName(),sysJob.getGroupName());
            }

            return true;
        } catch (SchedulerException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 暂停任务
     * @param name
     * @param group
     * @return boolean
     */
    public boolean stop(String name, String group) {
        try {
            scheduler.pauseJob(new JobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    /**
     * 恢复任务
     * @param name
     * @param group
     * @return boolean
     */
    public boolean resume(String name, String group) {
        try {
            scheduler.resumeJob(new JobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    /**
     * 删除任务
     * @param name
     * @param group
     * @return boolean
     */
    public boolean delete(String name, String group) {
        try {
            scheduler.deleteJob(new JobKey(name, group));
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    /**
     * 判断任务是否存在
     * @param name
     * @param group
     * @return boolean
     */
    public boolean isExists(String name, String group) {

        try {
            return scheduler.checkExists(new JobKey(name, group));
        } catch (SchedulerException e) {
            return false;
        }
    }

    /**
     * 更新任务
     * @param sysJob
     * @return boolean
     */
    public boolean update(SysJob sysJob) {
        boolean b = isExists(sysJob.getName(), sysJob.getGroupName());
        if (b) {
            delete(sysJob.getName(), sysJob.getGroupName());
        }
        return add(sysJob);
    }
}
