package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserMapper:用户信息
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户信息
     */
    @Results({
            @Result(property = "roomId", column = "id")
    })
    @Select("SELECT u.user_name,u.full_name,u.phone,r.building_name,r.unit_name,r.id FROM sys_user_room ui LEFT JOIN sys_room r ON ui.room_id = r.id LEFT JOIN sys_user u ON ui.user_id = u.id")
    public List<SysUserRoomData> householdInfoList();

    /**
     * 查询用户房间
     */
    @Select("SELECT r.* FROM sys_user_room ui LEFT JOIN sys_room r ON ui.room_id = r.id and ui.user_id = #{userId}")
    public List<SysRoom> userRoomList(String userId);

    /**
     * 查询用户房间
     */
    @Select("SELECT c.* FROM sys_car c LEFT JOIN sys_user u ON c.user = u.user_name and u.id = #{userId}")
    public List<SysCar> userCarList(String userId);


    /**
     * 查询用户房间
     */
    @Select("SELECT s.* FROM sys_user_info s where s.user_id = #{userId}")
    public List<SysUserInfo> userInfoList(String userId);


    /**
     * 根据角色查询用户信息
     */
    @Select("SELECT u.id,u.user_name,u.full_name,u.`status`,u.phone," +
            "r.role_name FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.user_id " +
            "LEFT JOIN sys_role r ON ur.role_id = r.id WHERE r.id = #{roleId}")
    public List<SysUser> findByUserRole(int roleId);
}
