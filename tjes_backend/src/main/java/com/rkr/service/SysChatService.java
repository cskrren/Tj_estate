package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysChat;
import com.rkr.domain.entity.SysUserInfo;
import com.rkr.mapper.SysChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysChatService {

    @Autowired
    private SysChatMapper sysChatMapper;

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Resource
    private RedisService redisService;

    /**
     * @param sysChat
     * @return void
     * @description 添加新群聊
     * @date 2023/4/30 23:28
     */
    public void addChat(SysChat sysChat){
        sysChatMapper.insert(sysChat);
    }

    /**
     * @param
     * @return void
     * @description 获取群聊列表
     * @date 2023/4/30 23:28
     */
    public List<SysChat> getChatList(){
        return sysChatMapper.selectList(null);
    }

    public List<SysUserInfo> getChatUserList(Integer GroupId){
        String redisKey = RedisKeyConstants.CHAT_INFO_KEY + GroupId;
        if(redisService.hasKey(redisKey)) {
            return redisService.getHash(redisKey, SysUserInfo.class);
        }
        SysChat sysChat = sysChatMapper.selectById(GroupId);
        String[] userIds = sysChat.getMembers().split(",");
        List<SysUserInfo> sysUserInfo = new ArrayList<>();
        for(int i = 0; i < userIds.length; i++){
            sysUserInfo.add(sysUserInfoService.findById(userIds[i]));
        }
        return sysUserInfo;
    }
}
