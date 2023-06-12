package com.rkr.service;

import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysJob;
import com.rkr.mapper.SysJobMapper;
import com.rkr.utils.QuartzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private RedisService redisService;

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
        String redisKey = RedisKeyConstants.JOB_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysJob.class);
        }
        SysJob sysJob = sysJobMapper.selectById(id);
        if(sysJob != null){
            redisService.set(redisKey, sysJob);
        }
        return sysJob;
    }

    /**
     * 获取所有用户信息
     * @return List<SysJob>
     */
    public List<SysJob> list(){
        String redisHashKey = RedisKeyConstants.JOB_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysJob.class);
        }
        List<SysJob> sysJobList = sysJobMapper.selectList(null);
        if(sysJobList != null){
            Map<String,String> map = new HashMap<>();
            for(SysJob sysJob : sysJobList){
                map.put(sysJob.getId(), sysJob.toString());
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysJobList;
    }

    /**
     * 添加定时任务
     * @param sysJob
     * @return boolean
     */
    public boolean add(SysJob sysJob){
        if(sysJobMapper.insert(sysJob) > 0){
            String redisKey = RedisKeyConstants.JOB_INFO_KEY + sysJob.getId();
            String redisHashKey = RedisKeyConstants.JOB_LIST_KEY;
            redisService.set(redisKey, sysJob);
            redisService.setOne(redisHashKey, sysJob.getId(), sysJob);
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
            String redisKey = RedisKeyConstants.JOB_INFO_KEY + sysJob.getId();
            String redisHashKey = RedisKeyConstants.JOB_LIST_KEY;
            redisService.set(redisKey, sysJob);
            redisService.setOne(redisHashKey, sysJob.getId(), sysJob);
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
        if(i > 0){
            String redisKey = RedisKeyConstants.JOB_INFO_KEY + sysJob.getId();
            String redisHashKey = RedisKeyConstants.JOB_LIST_KEY;
            if(redisService.hasKey(redisKey)) {
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysJob.getId())){
                redisService.deleteOne(redisHashKey, sysJob.getId());
            }
        }
        return i > 0 && quartzUtils.delete(sysJob.getName(),sysJob.getGroupName());
    }
}
