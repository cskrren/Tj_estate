package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.UserType;
import com.rkr.domain.entity.*;
import com.rkr.mapper.SysUserMapper;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysLoginService:用户登录服务管理
 */

@Service
public class SysUserService {

    /**
     * 用户服务
     */
    @Resource
    private SysUserMapper sysUserMapper;
    /**
     * 用户信息服务
     */
    @Autowired
    private SysUserRoomService SysUserRoomService;
    /**
     * 用户角色服务
     */
    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
     * 系统配置服务
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return SysUser
     */
    public SysUser loadUserByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        List<SysUser> list = sysUserMapper.selectList(wrapper);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取所有用户信息
     * @return List<SysUser>
     */
    public List<SysUser> list() {
        return sysUserMapper.selectList(null);
    }

    /**
     * 根据角色ID查询对应所有用户信息
     * @param roleId
     * @return List<SysUser>
     */
    public List<SysUser> findByUserRole(int roleId) {
        return sysUserMapper.findByUserRole(roleId);
    }

    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return SysUser
     */
    public SysUser findById(String id) {
        return sysUserMapper.selectById(id);
    }

    /**
     * 根据用户姓名查询用户信息
     * @param userName
     * @return SysUser
     */
    public SysUser findByUserName(String userName) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 保存用户信息
     * @param sysUser
     */
    public void save(SysUser sysUser) {
        if (sysUser.getId() != null) {
            sysUserMapper.updateById(sysUser);
            return;
        }
        sysUserMapper.insert(sysUser);
    }

    /**
     * 判断当前用户是不是管理员
     * @param userId
     * @return boolean
     */
    public boolean isAdmin(String userId) {
        try {
            SysRole userRole = sysUserRoleService.findUserRole(userId);
            System.out.println(userRole);
            if (userRole.getRoleKey().equals(UserType.ROLE_ADMIN_KEY)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 注册账号
     * @param sysUser
     * @return 返回用户ID
     */
    public String register(SysUser sysUser) {
        sysUser.setPassword(
                bCryptPasswordEncoder.encode(sysUser.getPassword()));
        save(sysUser);
        String userId = loadUserByUsername(sysUser.getUserName()).getId();
        sysUserRoleService.save(new SysUserRole().toBuilder().userId(userId).roleId(1).build());
        return userId;

    }


    /**
     * 重置当前用户的密码
     * @param newPassWord
     */
    public void resetPwd(String newPassWord){
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        SysUser sysUser = findById(userId);
        sysUser.setPassword(bCryptPasswordEncoder.encode(newPassWord));
        save(sysUser);
    }

    /**
     * 重置当前用户的密码
     * @param userId
     * @param newPassWord
     */
    public void resetPwd(String userId, String newPassWord){
        SysUser sysUser = findById(userId);
        sysUser.setPassword(bCryptPasswordEncoder.encode(newPassWord));
        save(sysUser);
    }

    /**
     * 重置用户密码
     * @param sysUser
     */
    public void resetPwd(SysUser sysUser){
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        save(sysUser);
    }

    /**
     * 获取住户信息列表
     */
    public List<SysUserRoomData> HouseholdInfoList() {
        return sysUserMapper.householdInfoList();
    }

    /**
     * 保存住户信息
     * @param SysUserRoomData
     */
    public void HouseholdInfoSave(SysUserRoomData SysUserRoomData) {
        //更新用户数据
        SysUser sysUser = findById(SysUserRoomData.getUserId());
        if (sysUser != null) {
            sysUser.setFullName(SysUserRoomData.getFullName());
            sysUser.setPhone(SysUserRoomData.getPhone());
            save(sysUser);
        }
        //如果该房间已被其他住户绑定，那么就删除现绑定者
        SysUserRoom byColumnVal = SysUserRoomService.findByColumnVal("room_id", SysUserRoomData.getRoomId());
        if (byColumnVal != null) {
            SysUserRoomService.delete(byColumnVal.getId());
        }
        //处理数据并保存
        SysUserRoom SysUserRoom = new SysUserRoom();
        BeanUtils.copyProperties(SysUserRoomData, SysUserRoom);
        System.out.println(SysUserRoom.toString());
        SysUserRoomService.save(SysUserRoom);
    }

    /**
     * 获取Room列表
     */
    public List<SysRoom> RoomList(String userId) {
        return sysUserMapper.userRoomList(userId);
    }

    /**
     * 获取Car列表
     */
    public List<SysCar> CarList(String userId) {
        return sysUserMapper.userCarList(userId);
    }


    /**
     * 获取UserInfo列表
     */
    public List<SysUserInfo> UserInfoList(String userId) {
        return sysUserMapper.userInfoList(userId);
    }

    /**
     * 构造函数
     * @param SysUserRoom
     */
    public void HouseholdInfoDelete(SysUserRoom SysUserRoom) {
    }
}
