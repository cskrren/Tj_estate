package com.rkr.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @Package com.rkr.quartz.task
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description TestJob:测试任务
 */
public class TestJob implements Job {
    /**
     * 任务执行体
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date());
    }
}
