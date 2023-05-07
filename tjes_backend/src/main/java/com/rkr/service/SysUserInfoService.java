package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.domain.entity.SysUserInfo;
import com.rkr.mapper.SysUserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysUserInfo
     */
    public SysUserInfo findById(String id){
        return sysUserInfoMapper.selectById(id);
    }

    /**
     * 通过匹配列名进行寻找
     * @param user_id
     * @return String
     */
    public String getUserAvatar(String user_id){
        QueryWrapper<SysUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectOne(wrapper);
        return sysUserInfo.getUserAvatar();
    }

    /**
     * 保存用户信息
     * @param sysUserInfo
     * @return sysUserInfo
     */
    public void save(SysUserInfo sysUserInfo){
        if(findById(sysUserInfo.getUserId()) != null){
            sysUserInfoMapper.updateById(sysUserInfo);
            return;
        }
        sysUserInfoMapper.insert(sysUserInfo);
    }

    /**
     * 保存用户信息
     * @param user_id
     * @param user_avatar
     * @return sysUserInfo
     */
    public void updateUserAvatar(String user_id, String user_avatar){
        SysUserInfo sysUserInfo = findById(user_id);
        sysUserInfo.setUserAvatar(user_avatar);
        sysUserInfoMapper.updateById(sysUserInfo);
    }

}
