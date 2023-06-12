package com.rkr.service;

import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.mapper.SysOptionsMapper;
import com.rkr.domain.entity.SysOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysOptionsService:用户选项服务管理
 */

@Service
public class SysOptionsService {

    /**
     * 用户选项服务管理
     */
    @Resource
    private SysOptionsMapper sysOptionsMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     *
     * @param id
     * @return SysOptions
     */
    public SysOptions findById(String id) {
        String redisKey = RedisKeyConstants.OPTIONS_INFO_KEY + id;
        if (redisService.hasKey(redisKey)) {
            return redisService.get(redisKey, SysOptions.class);
        }
        SysOptions sysOptions = sysOptionsMapper.selectById(id);
        if (sysOptions != null) {
            redisService.set(redisKey, sysOptions);
        }
        return sysOptions;
    }

    /**
     * 保存用户信息
     *
     * @param sysOptions
     */
    public void save(SysOptions sysOptions) {
        String redisKey = RedisKeyConstants.OPTIONS_INFO_KEY + sysOptions.getId();
        if(findById(sysOptions.getId()) != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            sysOptionsMapper.updateById(sysOptions);
        } else {
            sysOptionsMapper.insert(sysOptions);
        }
        redisService.set(redisKey, sysOptions);
    }
}
