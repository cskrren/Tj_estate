package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysChatInfo;
import com.rkr.domain.entity.SysUserInfo;
import com.rkr.mapper.SysChatInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysChatInfoService {

    @Autowired
    private SysChatInfoMapper sysChatInfoMapper;

    @Resource
    private RedisService redisService;

    /**
     * @param pageSize
     * @return void
     * @description 获取聊天记录
     * @date 2023/4/30 23:28
     */
    public List<SysChatInfo> getChatHistory(Integer groupId, Integer currentSize, Integer pageSize){
        String redisKey = RedisKeyConstants.CHAT_KEY + groupId;
        if(redisService.hasKey(redisKey) && redisService.canGet(redisKey, currentSize + pageSize)) {
            return redisService.getList(redisKey, currentSize, pageSize, SysChatInfo.class);
        }
        List<SysChatInfo> sysChatInfo = sysChatInfoMapper.getHistory(groupId, currentSize, pageSize);
        if(sysChatInfo != null && sysChatInfo.size() > 0) {
            List<String> sysChatInfoStr = sysChatInfo.stream().map(JSON::toJSONString).collect(Collectors.toList());
            redisService.setListFromRight(redisKey, sysChatInfoStr);
        }
        return sysChatInfo;
    }

    public void addChatInfo(SysChatInfo sysChatInfo){
        sysChatInfoMapper.insert(sysChatInfo);
        String redisKey = RedisKeyConstants.CHAT_KEY + sysChatInfo.getGroupId();
        redisService.setOneFromLeft(redisKey, JSON.toJSONString(sysChatInfo));
    }
}
