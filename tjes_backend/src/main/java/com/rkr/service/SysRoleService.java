package com.rkr.service;

import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysRole;
import com.rkr.mapper.SysRoleMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRoleService:用户角色服务管理
 */

@Service
public class SysRoleService {

    /**
     * 用户角色服务管理
     */
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysRole
     */
    public SysRole findById(Integer id){
        String redisKey = RedisKeyConstants.ROLE_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysRole.class);
        }
        SysRole sysRole = sysRoleMapper.selectById(id);
        if(sysRole != null){
            redisService.set(redisKey, sysRole);
        }
        return sysRole;
    }
}
