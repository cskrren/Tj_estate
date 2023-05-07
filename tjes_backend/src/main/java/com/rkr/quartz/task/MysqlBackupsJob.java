package com.rkr.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @Package com.rkr.quartz.task
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description MySqlBackupsJob:数据库备份
 */
public class MysqlBackupsJob implements Job {
    /**
     * 任务执行体
     * @param jobExecutionContext
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String dbName = "estate_backup";//备份的数据库名
        String username = "root";//用户名
        String password = "021024";//密码
        String sqlPath = "db/estate.sql";//备份的路径地址
        String cmd = "mysqldump -u" + username + " -p" + password + " " + dbName + " > " + sqlPath;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            System.out.println("备份数据库成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
