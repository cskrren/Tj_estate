package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysFacilities;
import com.rkr.mapper.SysFacilitiesMapper;
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
 * @description SysFacilitiesService:用户设施服务管理
 */

@Service
public class SysFacilitiesService {

    /**
     * 用户设施服务管理
     */
    @Resource
    private SysFacilitiesMapper sysFacilitiesMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysFacilities
     */
    public SysFacilities findById(String id){
        String RedisKey = RedisKeyConstants.FACILITIES_INFO_KEY + id;
        if(redisService.hasKey(RedisKey)){
            return redisService.get(RedisKey, SysFacilities.class);
        }
        SysFacilities sysFacilities = sysFacilitiesMapper.selectById(id);
        if(sysFacilities != null){
            redisService.set(RedisKey, sysFacilities);
        }
        return sysFacilities;
    }

    /**
     * 获取所有用户信息
     * @return List<SysFacilities>
     */
    public List<SysFacilities> list(){
        String redisHashKey = RedisKeyConstants.FACILITIES_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysFacilities.class);
        }
        List<SysFacilities> sysFacilitiesList = sysFacilitiesMapper.selectList(null);
        if(sysFacilitiesList != null){
            Map<String,String> map = new HashMap<>();
            for(SysFacilities sysFacilities : sysFacilitiesList){
                map.put(sysFacilities.getId(), JSON.toJSONString(sysFacilities));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysFacilitiesList;
    }

    /**
     * 保存用户信息
     * @param sysFacilities
     */
    public void save(SysFacilities sysFacilities){
        String redisKey = RedisKeyConstants.FACILITIES_INFO_KEY + sysFacilities.getId();
        String redisHashKey = RedisKeyConstants.FACILITIES_LIST_KEY;
        if(findById(sysFacilities.getId()) != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysFacilities.getId())){
                redisService.deleteOne(redisHashKey, sysFacilities.getId());
            }
            sysFacilitiesMapper.updateById(sysFacilities);
        } else {
            sysFacilitiesMapper.insert(sysFacilities);
        }
        redisService.set(redisKey, sysFacilities);
        redisService.setOne(redisHashKey, sysFacilities.getId(), sysFacilities);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id){
        boolean flag = sysFacilitiesMapper.deleteById(id) > 0;
        if(flag){
            String redisKey = RedisKeyConstants.FACILITIES_INFO_KEY + id;
            String redisHashKey = RedisKeyConstants.FACILITIES_LIST_KEY;
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, id)){
                redisService.deleteOne(redisHashKey, id);
            }
        }
        return flag;
    }
}
