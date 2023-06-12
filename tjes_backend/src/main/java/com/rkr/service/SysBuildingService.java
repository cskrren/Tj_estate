package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.mapper.SysBuildingMapper;
import com.rkr.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysBuildingService:用户登录认证信息查询
 */

@Service
public class SysBuildingService {

    /**
     * 用户服务
     */
    @Resource
    private SysBuildingMapper sysBuildingMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysBuilding
     */
    @Transactional
    public SysBuilding findById(String id){
        String redisKey = RedisKeyConstants.BUILDING_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysBuilding.class);
        }
        SysBuilding sysBuilding = sysBuildingMapper.selectById(id);
        if(sysBuilding != null){
            redisService.set(redisKey, sysBuilding);
        }
        return sysBuilding;
    }

    /**
     * 展示所有用户信息
     * @return List<SysBuilding>
     */
    public List<SysBuilding> list() {
        String redisHashKey = RedisKeyConstants.BUILDING_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysBuilding.class);
        }
        List<SysBuilding> sysBuildings = sysBuildingMapper.selectList(null);
        if(sysBuildings != null){
            Map<String, String> map = new HashMap<>();
            for(SysBuilding sysBuilding : sysBuildings){
                map.put(sysBuilding.getId(), JSON.toJSONString(sysBuilding));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysBuildings;
    }

    /**
     * 限制查询字段
     * @param colName
     * @return List<SysBuilding>
     */
    public List<SysBuilding> list(String colName){
        QueryWrapper<SysBuilding> wrapper = new QueryWrapper<>();
        wrapper.select(StringUtils.format("DISTINCT {}" ,colName));
        List<SysBuilding> sysBuildings = sysBuildingMapper.selectList(wrapper);
        return sysBuildings;
    }

    /**
     * 返回所有用户名
     * @return List<SysBuilding>
     */
    public List<SysBuilding> nameList(){
        return list("name");
    }

    /**
     * 保存新building
     * @param sysBuilding
     * @return List<SysBuilding>
     */
    public void save(SysBuilding sysBuilding){
        String redisKey = RedisKeyConstants.BUILDING_INFO_KEY + sysBuilding.getId();
        String redisHashKey = RedisKeyConstants.BUILDING_LIST_KEY;
        SysBuilding oldBuilding = findById(sysBuilding.getId());
        if(oldBuilding != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysBuilding.getId())){
                redisService.deleteOne(redisHashKey, sysBuilding.getId());
            }
            sysBuildingMapper.updateById(sysBuilding);
        } else {
            sysBuildingMapper.insert(sysBuilding);
        }
        redisService.set(redisKey, sysBuilding);
        redisService.setOne(redisHashKey, sysBuilding.getId(), sysBuilding);
    }

    /**
     * 删除用户信息
     * @param id
     * @return List<SysBuilding>
     */
    public boolean delete(String id){
        boolean flag = sysBuildingMapper.deleteById(id) > 0;
        if(flag){
            String redisKey = RedisKeyConstants.BUILDING_INFO_KEY + id;
            String redisHashKey = RedisKeyConstants.BUILDING_LIST_KEY;
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, id)) {
                redisService.deleteOne(redisHashKey, id);
            }
        }
        return flag;
    }
}
