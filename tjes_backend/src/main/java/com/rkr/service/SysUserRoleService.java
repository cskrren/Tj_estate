package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysRole;
import com.rkr.domain.entity.SysUserRole;
import com.rkr.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserRoleService:用户角色服务管理
 */

@Service
public class SysUserRoleService {

    /**
     * 用户角色服务管理
     */
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    /**
     * 角色服务管理
     */
    @Autowired
    private SysRoleService sysRoleService;

    @Resource
    RedisService redisService;

    /**
     * 根据用户ID查询用户角色信息
     * @param userId
     * @return
     */
    public SysUserRole findByUserId(String userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        String RedisKey = RedisKeyConstants.USER_ROLE_INFO_KEY + userId;
        if(redisService.hasKey(RedisKey)){
            return redisService.get(RedisKey,SysUserRole.class);
        }
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(wrapper);
        if(sysUserRoles.size() == 0){
            return null;
        }
        redisService.set(RedisKey,sysUserRoles.get(0));
        return sysUserRoles.get(0);
    }

    /**
     * 通过UserId查询对应的角色信息
     * @param userId
     * @return
     */
    public SysRole findUserRole(String userId) {
        SysUserRole byUserID = findByUserId(userId);
        System.out.println(byUserID);
        return sysRoleService.findById(byUserID.getRoleId());
    }

    /**
     * 保存用户角色信息
     * @param sysUserRole
     */
    public void save(SysUserRole sysUserRole) {
        String redisKey = RedisKeyConstants.USER_ROLE_INFO_KEY + sysUserRole.getUserId();
        if (findByUserId(sysUserRole.getUserId()) == null) {
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            sysUserRoleMapper.insert(sysUserRole);
        } else {
            sysUserRoleMapper.updateById(sysUserRole);
        }
        redisService.set(redisKey,sysUserRole);
    }

    /**
     * 根据用户ID删除用户角色信息
     * @param userId
     * @return boolean
     */
    public boolean deleteByUserId(String userId) {
        String redisKey = RedisKeyConstants.USER_ROLE_INFO_KEY + userId;
        if(redisService.hasKey(redisKey)){
            redisService.delete(redisKey);
        }
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return sysUserRoleMapper.delete(wrapper) > 0;
    }
}
