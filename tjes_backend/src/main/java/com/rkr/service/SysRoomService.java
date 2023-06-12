package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysRoom;
import com.rkr.mapper.SysRoomMapper;
import com.rkr.utils.StringUtils;
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
 * @description SysRoomService:用户房间信息查询
 */

@Service
public class SysRoomService {

    /**
     * 房间服务
     */
    @Resource
    private SysRoomMapper sysRoomMapper;

    @Resource
    private RedisService redisService;

    //private Map<String, Boolean> lazyFLag = new HashMap<>();

    /**
     * 根据ID查询
     * @param id
     * @return SysRoom
     */
    public SysRoom findById(String id){
        String redisKey = RedisKeyConstants.ROOM_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysRoom.class);
        }
        SysRoom sysRoom = sysRoomMapper.selectById(id);
        if(sysRoom != null){
            redisService.set(redisKey,sysRoom);
        }
        return sysRoomMapper.selectById(id);
    }

    /**
     * 查询所有信息
     * @return List<SysRoom>
     */
    public List<SysRoom> list(){
        String redisKey = RedisKeyConstants.ROOM_LIST_KEY;
        if(redisService.hasKey(redisKey)){
            return redisService.getHash(redisKey, SysRoom.class);
        }
        List<SysRoom> sysRoomList = sysRoomMapper.selectList(null);
        if(sysRoomList != null){
            Map<String,String> map = new HashMap<>();
            for(SysRoom sysRoom : sysRoomList){
                map.put(sysRoom.getId(), JSON.toJSONString(sysRoom));
            }
            redisService.setHash(redisKey,map);
        }
        return sysRoomList;
    }

    /**
     * 根据用户名查询用户信息
     * @param colName
     * @param buildingName
     * @return List<SysRoom>
     */
    public List<SysRoom> list(String colName,String buildingName){
        QueryWrapper<SysRoom> wrapper = new QueryWrapper<>();
        wrapper
                .select(StringUtils.format("DISTINCT {}" ,colName))
                .eq("building_name",buildingName);
        List<SysRoom> sysRoomList = sysRoomMapper.selectList(wrapper);
        return sysRoomList;
    }

    /**
     * 返回用户名信息
     * @param buildingName
     * @return List<SysRoom>
     */
    public List<SysRoom> unitNameList(String buildingName){
        return list("unit_name",buildingName);
    }

    /**
     * 保存用户信息
     * @param sysRoom
     */
    public void save(SysRoom sysRoom){
        String redisKey = RedisKeyConstants.ROOM_INFO_KEY + sysRoom.getId();
        String redisHashKey = RedisKeyConstants.ROOM_LIST_KEY;
        SysRoom oldRoom = findById(sysRoom.getId());
        if(oldRoom != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey,sysRoom.getId())){
                redisService.deleteOne(redisHashKey,sysRoom.getId());
            }
            sysRoomMapper.updateById(sysRoom);
        } else {
            sysRoomMapper.insert(sysRoom);
        }
        redisService.set(redisKey,sysRoom);
        redisService.setOne(redisHashKey,sysRoom.getId(),sysRoom);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id){
        String redisKey = RedisKeyConstants.ROOM_INFO_KEY + id;
        String redisHashKey = RedisKeyConstants.ROOM_LIST_KEY;
        SysRoom deleteRoom = findById(id);
        if(deleteRoom == null){
            return false;
        }
        if(redisService.hasKey(redisKey)){
            redisService.delete(redisKey);
        }
        if(redisService.hasHashKey(redisHashKey,id)){
            redisService.deleteOne(redisHashKey,id);
        }
        return sysRoomMapper.deleteById(id) > 0;
    }
}
