package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysUserRoom;
import com.rkr.mapper.SysUserRoomMapper;
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
 * @description SysUserRoomService:用户信息服务管理
 */

@Service
public class SysUserRoomService {

    /**
     * 用户信息服务管理
     */
    @Resource
    private SysUserRoomMapper SysUserRoomMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysUserRoom
     */
    public SysUserRoom findById(String id){
        String redisKey = RedisKeyConstants.USER_ROOM_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysUserRoom.class);
        }
        SysUserRoom SysUserRoom = SysUserRoomMapper.selectById(id);
        if(SysUserRoom != null){
            redisService.set(redisKey,SysUserRoom);
        }
        return SysUserRoom;
    }

    /**
     * 通过匹配列名进行寻找
     * @param column
     * @param val
     */
    public SysUserRoom findByColumnVal(String column, Integer val){
        QueryWrapper<SysUserRoom> wrapper = new QueryWrapper<>();
        wrapper.eq(column,val);
        SysUserRoom SysUserRoom = SysUserRoomMapper.selectOne(wrapper);
        return SysUserRoom;
    }

    /**
     * 查询所有信息
     * @return List<SysUserRoom>
     */
    public List<SysUserRoom> list(){
        String redisHashKey = RedisKeyConstants.USER_ROOM_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysUserRoom.class);
        }
        List<SysUserRoom> SysUserRoomList = SysUserRoomMapper.selectList(null);
        if(SysUserRoomList != null){
            Map<String,String> map = new HashMap<>();
            for(SysUserRoom SysUserRoom : SysUserRoomList){
                map.put(SysUserRoom.getId(), SysUserRoom.toString());
            }
            redisService.setHash(redisHashKey, map);
        }
        return SysUserRoomList;
    }

    /**
     * 保存用户信息
     * @param SysUserRoom
     */
    public void save(SysUserRoom SysUserRoom){
        String redisKey = RedisKeyConstants.USER_ROOM_INFO_KEY + SysUserRoom.getId();
        String redisHashKey = RedisKeyConstants.USER_ROOM_LIST_KEY;
        if(findById(SysUserRoom.getId()) != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, SysUserRoom.getId())){
                redisService.deleteOne(redisHashKey, SysUserRoom.getId());
            }
            SysUserRoomMapper.updateById(SysUserRoom);
        } else {
            SysUserRoomMapper.insert(SysUserRoom);
        }
        redisService.set(redisKey, SysUserRoom);
        redisService.setOne(redisHashKey, SysUserRoom.getId(), SysUserRoom.toString());
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id){
        String redisKey = RedisKeyConstants.USER_ROOM_INFO_KEY + id;
        String redisHashKey = RedisKeyConstants.USER_ROOM_LIST_KEY;
        if(redisService.hasKey(redisKey)){
            redisService.delete(redisKey);
        }
        if(redisService.hasHashKey(redisHashKey, id)){
            redisService.deleteOne(redisHashKey, id);
        }
        return SysUserRoomMapper.deleteById(id) > 0;
    }
}
