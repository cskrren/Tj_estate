package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
;
import com.rkr.domain.entity.SysUserRoom;
import com.rkr.mapper.SysUserRoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysUserRoom
     */
    public SysUserRoom findById(String id){
        return SysUserRoomMapper.selectById(id);
    }

    /**
     * 通过匹配列名进行寻找
     * @param column
     * @param val
     */
    public SysUserRoom findByColumnVal(String column, String val){
        QueryWrapper<SysUserRoom> wrapper = new QueryWrapper<>();
        wrapper.eq(column,val);
        return SysUserRoomMapper.selectOne(wrapper);
    }

    /**
     * 查询所有信息
     * @return List<SysUserRoom>
     */
    public List<SysUserRoom> list(){
        return SysUserRoomMapper.selectList(null);
    }

    /**
     * 保存用户信息
     * @param SysUserRoom
     */
    public void save(SysUserRoom SysUserRoom){
        if(findById(SysUserRoom.getId()) != null){
            SysUserRoomMapper.updateById(SysUserRoom);
            return;
        }
        SysUserRoomMapper.insert(SysUserRoom);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id){
        return SysUserRoomMapper.deleteById(id) > 0;
    }
}
