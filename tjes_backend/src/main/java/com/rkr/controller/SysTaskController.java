package com.rkr.controller;

import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysJob;
import com.rkr.service.SysJobService;
import com.rkr.utils.QuartzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.config
 * @auhter rkr
 * @date 2023/4/30 15:46
 * @description SysTaskController:定时任务管理
 */

@RestController
@RequestMapping("system/task")
public class SysTaskController {
    @Autowired
    private QuartzUtils quartzUtils;

    @Autowired
    private SysJobService sysJobService;

    /**
     * 构造器注入
     * @return AjaxResult
     */
    @GetMapping("/list")
    private AjaxResult list() {
        return AjaxResult.success(sysJobService.list());
    }

    /**
     * 启动定时任务
     * @param sysJob
     * @return AjaxResult
     */
    @PostMapping("/add")
    private AjaxResult start(@RequestBody SysJob sysJob) {
        if (sysJobService.add(sysJob)) {
            return AjaxResult.success();
        }
        return AjaxResult.error("请检查类是否存在或Cron表达式是否正确");
    }

    /**
     * 暂停定时任务
     * @param sysJob
     * @return AjaxResult
     */
    @PostMapping("/delete")
    private AjaxResult delete(@RequestBody SysJob sysJob) {
        if (sysJobService.delete(sysJob)) {
            return AjaxResult.success();
        }
        return AjaxResult.error("并不存在此任务");
    }

    /**
     * 更新定时任务
     * @param sysJob
     * @return AjaxResult
     */
    @PostMapping("/update")
    private AjaxResult update(@RequestBody SysJob sysJob) {
        if (sysJobService.update(sysJob)) {
            return AjaxResult.success();
        }
        return AjaxResult.error("并不存在此任务");
    }
}
