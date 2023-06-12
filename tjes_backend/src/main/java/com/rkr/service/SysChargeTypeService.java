package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysChargeType;
import com.rkr.mapper.SysChargeTypeMapper;
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
 * @description SysChargeTypeService:用户缴费服务管理
 */

@Service
public class SysChargeTypeService {
    /**
     * 用户服务
     */
    @Resource
    private SysChargeTypeMapper sysChargeTypeMapper;

    @Resource
    private RedisService redisService;

    private boolean lazyFLag = false;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysChargeType
     */
    public SysChargeType findById(Integer id){
        String RedisKey = RedisKeyConstants.CHARGE_TYPE_INFO_KEY + id;
        if(redisService.hasKey(RedisKey)){
            return redisService.get(RedisKey, SysChargeType.class);
        }
        SysChargeType sysChargeType = sysChargeTypeMapper.selectById(id);
        if(sysChargeType != null){
            redisService.set(RedisKey, sysChargeType);
        }
        return sysChargeType;
    }

    /**
     * 根据用户名查询用户信息
     * @return List<SysChargeType>
     */
    public List<SysChargeType> list(){
        String redisHashKey = RedisKeyConstants.CHARGE_TYPE_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysChargeType.class);
        }
        List<SysChargeType> sysChargeTypeList = sysChargeTypeMapper.selectList(null);
        if(sysChargeTypeList != null){
            Map<String, String> map = new HashMap<>();
            for(SysChargeType sysChargeType : sysChargeTypeList){
                map.put(sysChargeType.getId().toString(), JSON.toJSONString(sysChargeType));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysChargeTypeList;
    }

    /**
     * 保存用户信息
     * @param sysChargeType
     */
    public void save(SysChargeType sysChargeType){
        String redisKey = RedisKeyConstants.CHARGE_TYPE_INFO_KEY + sysChargeType.getId();
        String redisHashKey = RedisKeyConstants.CHARGE_TYPE_LIST_KEY;
        SysChargeType oldChargeType = findById(sysChargeType.getId());
        if(oldChargeType != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysChargeType.getId().toString())) {
                redisService.deleteOne(redisHashKey, sysChargeType.getId().toString());
            }
            sysChargeTypeMapper.updateById(sysChargeType);
        } else {
            sysChargeTypeMapper.insert(sysChargeType);
        }
        lazyFLag = true;
        sysChargeTypeMapper.insert(sysChargeType);
        redisService.set(redisKey, sysChargeType);
        redisService.setOne(redisHashKey, sysChargeType.getId().toString(), sysChargeType);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return SysChargeType
     */
    public boolean delete(String id){
        boolean flag = sysChargeTypeMapper.deleteById(id) > 0;
        if(flag) {
            String redisKey = RedisKeyConstants.CHARGE_TYPE_INFO_KEY + id;
            String redisHashKey = RedisKeyConstants.CHARGE_TYPE_LIST_KEY;
            if (redisService.hasKey(redisKey)) {
                redisService.delete(redisKey);
            }
            if (redisService.hasHashKey(redisHashKey, id)) {
                redisService.deleteOne(redisHashKey, id);
            }
            lazyFLag = true;
        }
        return flag;
    }
}
