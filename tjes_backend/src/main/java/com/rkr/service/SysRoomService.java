package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.entity.SysRoom;
import com.rkr.mapper.SysRoomMapper;
import com.rkr.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据ID查询
     * @param id
     * @return SysRoom
     */
    public SysRoom findById(String id){
        return sysRoomMapper.selectById(id);
    }

    /**
     * 查询所有信息
     * @return List<SysRoom>
     */
    public List<SysRoom> list(){
        return sysRoomMapper.selectList(null);
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
        return sysRoomMapper.selectList(wrapper);
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
        if(findById(sysRoom.getId()) != null){
            sysRoomMapper.updateById(sysRoom);
            return;
        }
        sysRoomMapper.insert(sysRoom);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id){
        return sysRoomMapper.deleteById(id) > 0;
    }
}
