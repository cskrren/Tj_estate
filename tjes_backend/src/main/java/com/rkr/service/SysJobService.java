package com.rkr.service;

import com.rkr.domain.entity.SysJob;
import com.rkr.mapper.SysJobMapper;
import com.rkr.utils.QuartzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysJobService:用户任务服务管理
 */

@Service
public class SysJobService {

    /**
     * 用户任务服务管理
     */
    @Resource
    private SysJobMapper sysJobMapper;

    /**
     * 定时任务服务
     */
    @Autowired
    private QuartzUtils quartzUtils;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysJob
     */
    public SysJob findById(String id){
        return sysJobMapper.selectById(id);
    }

    /**
     * 获取所有用户信息
     * @return List<SysJob>
     */
    public List<SysJob> list(){
        return sysJobMapper.selectList(null);
    }

    /**
     * 添加定时任务
     * @param sysJob
     * @return boolean
     */
    public boolean add(SysJob sysJob){
        if(sysJobMapper.insert(sysJob) > 0){
            return quartzUtils.add(sysJob);
        }
        return false;
    }

    /**
     * 更新定时任务
     * @param sysJob
     * @return boolean
     */
    public boolean update(SysJob sysJob){
        if(findById(sysJob.getId()) != null){
            int i = sysJobMapper.updateById(sysJob);
            return i > 0 && quartzUtils.update(sysJob);
        }
        return false;
    }

    /**
     * 根据用户名删除用户信息
     * @param sysJob
     * @return boolean
     */
    public boolean delete(SysJob sysJob){
        int i = sysJobMapper.deleteById(sysJob.getId());
        return i > 0 && quartzUtils.delete(sysJob.getName(),sysJob.getGroupName());
    }
}
