package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.domain.entity.SysUser;
import com.rkr.domain.entity.SysUserInfo;
import com.rkr.mapper.SysUserInfoMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserInfoService {

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysUserInfo
     */
    public SysUserInfo findById(String id){
        String redisKey = RedisKeyConstants.USERINFO + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysUserInfo.class);
        }
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(id);
        if(sysUserInfo != null){
            redisService.set(redisKey, sysUserInfo);
        }
        return sysUserInfo;
    }

    /**
     * 通过匹配列名进行寻找
     * @param user_id
     * @return String
     */
    public String getUserAvatar(String user_id){
        QueryWrapper<SysUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        String redisKey = RedisKeyConstants.USERINFO + user_id + "avatar";
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, String.class);
        }
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectOne(wrapper);
        if(sysUserInfo != null){
            redisService.set(redisKey, sysUserInfo.getUserAvatar());
        }
        return sysUserInfo.getUserAvatar();
    }

    /**
     * 保存用户信息
     * @param sysUserInfo
     * @return sysUserInfo
     */
    public void save(SysUserInfo sysUserInfo){
        String redisKey = RedisKeyConstants.USERINFO + sysUserInfo.getUserId();
        if(findById(sysUserInfo.getUserId()) != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            sysUserInfoMapper.updateById(sysUserInfo);
        } else {
            sysUserInfoMapper.insert(sysUserInfo);
        }
        redisService.set(redisKey, sysUserInfo);
    }

    /**
     * 保存用户信息
     * @param user_id
     * @param user_avatar
     * @return sysUserInfo
     */
    public void updateUserAvatar(String user_id, String user_avatar){
        String redisKey = RedisKeyConstants.USERINFO + user_id +"avatar";
        if(redisService.hasKey(redisKey)){
            redisService.delete(redisKey);
        }
        redisKey = RedisKeyConstants.USERINFO + user_id;
        if(redisService.hasKey(redisKey)){
            SysUserInfo sysUserInfo = redisService.get(redisKey, SysUserInfo.class);
            redisService.delete(redisKey);
            sysUserInfo.setUserAvatar(user_avatar);
            sysUserInfoMapper.updateById(sysUserInfo);
            redisService.set(redisKey,sysUserInfo);
        } else {
            SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(user_id);
            sysUserInfo.setUserAvatar(user_avatar);
            sysUserInfoMapper.updateById(sysUserInfo);
            redisService.set(redisKey,sysUserInfo);
        }
    }

    /**
     * 新建新用户信息
     * @param user
     * @return sysUserInfo
     */
    public void insertUserInfo(SysUser user){
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(user.getId());
        sysUserInfo.setUserName(user.getUserName());
        sysUserInfo.setUserAge("18");
        sysUserInfo.setUserGender("男");
        sysUserInfo.setUserPhone(user.getPhone());
        sysUserInfo.setUserEmail("abc@mail.com");
        sysUserInfo.setUserWorkPlace("同济大学");
        sysUserInfo.setUserAvatar("http://120.26.195.187:8001/images/1.jpg");
        sysUserInfoMapper.insert(sysUserInfo);
        String redisKey = RedisKeyConstants.USERINFO + user.getId();
        redisService.set(redisKey, sysUserInfo);
    }

    /**
     * 更新用户状态
     * @param UserId
     * @return void
     */
    public void updateStatus(String UserId) {
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(UserId);
        sysUserInfo.setOnline(1-sysUserInfo.getOnline());
        sysUserInfoMapper.updateById(sysUserInfo);
    }


}
