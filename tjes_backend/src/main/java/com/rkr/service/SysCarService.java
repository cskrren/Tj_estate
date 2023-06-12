package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.domain.entity.SysCar;
import com.rkr.mapper.SysBuildingMapper;
import com.rkr.mapper.SysCarMapper;
import com.rkr.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysCarService {
    /**
     * 用户服务
     */
    @Resource
    private SysCarMapper sysCarMapper;

    @Resource
    private RedisService redisService;

    //private boolean lazyFLag = false;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysCar
     */
    public SysCar findById(String id) {
        String redisKey = RedisKeyConstants.CAR_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysCar.class);
        }
        SysCar sysCar = sysCarMapper.selectById(id);
        if(sysCar != null){
            redisService.set(redisKey, sysCar);
        }
        return sysCar;
    }

    /**
     * 展示所有用户信息
     * @return List<SysCar>
     */
    public List<SysCar> list() {
        String redisHashKey = RedisKeyConstants.CAR_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysCar.class);
        }
        List<SysCar> sysCarList = sysCarMapper.selectList(null);
        if(sysCarList != null){
            Map<String, String> map = new HashMap<>();
            for(SysCar sysCar : sysCarList){
                map.put(sysCar.getId(), JSON.toJSONString(sysCar));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysCarList;
    }

    /**
     * 限制查询字段
     * @param colName
     * @return List<SysCar>
     */
    public List<SysCar> list(String colName){
        QueryWrapper<SysCar> wrapper = new QueryWrapper<>();
        wrapper.select(StringUtils.format("DISTINCT {}" ,colName));
        List<SysCar> sysCarList = sysCarMapper.selectList(wrapper);
        return sysCarList;
    }

    /**
     * 返回所有用户名
     * @return List<SysCar>
     */
    public List<SysCar> nameList(){
        return list("name");
    }

    /**
     * 保存用户信息
     * @param sysCar
     * @return List<SysCar>
     */
    public void save(SysCar sysCar){
        String redisKey = RedisKeyConstants.CAR_INFO_KEY + sysCar.getId();
        String redisHashKey = RedisKeyConstants.CAR_LIST_KEY;
        SysCar oldCar = findById(sysCar.getId());
        if(oldCar != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysCar.getId())){
                redisService.deleteOne(redisHashKey, sysCar.getId());
            }
            sysCarMapper.updateById(sysCar);
        } else {
            sysCarMapper.insert(sysCar);
        }
        redisService.set(redisKey, sysCar);
        redisService.setOne(redisHashKey, sysCar.getId(), sysCar);
    }

    /**
     * 删除用户信息
     * @param id
     * @return List<SysBuilding>
     */
    public boolean delete(String id){
        boolean flag = sysCarMapper.deleteById(id) > 0;
        if(flag) {
            String redisKey = RedisKeyConstants.CAR_INFO_KEY + id;
            String redisHashKey = RedisKeyConstants.CAR_LIST_KEY;
            if (redisService.hasKey(redisKey)) {
                redisService.delete(redisKey);
            }
            if (redisService.hasHashKey(redisHashKey, id)) {
                redisService.deleteOne(redisHashKey, id);
            }
        }
        return flag;
    }
}
