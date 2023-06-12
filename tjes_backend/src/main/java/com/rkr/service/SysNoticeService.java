package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysNotice;
import com.rkr.mapper.SysNoticeMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysNoticeService:用户公告服务管理
 */

@Service
public class SysNoticeService {

    /**
     * 用户公告服务管理
     */
    @Resource
    private SysNoticeMapper sysNoticeMapper;

    @Resource
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysNotice
     */
    public SysNotice findById(String id) {
        String redisKey = RedisKeyConstants.NOTICE_INFO_KEY + id;
        if (redisService.hasKey(redisKey)) {
            return redisService.get(redisKey, SysNotice.class);
        }
        SysNotice sysNotice = sysNoticeMapper.selectById(id);
        if (sysNotice != null) {
            redisService.set(redisKey, sysNotice);
        }
        return sysNotice;
    }

    /**
     * 获取所有用户信息
     * @return List<SysNotice>
     */
    public List<SysNotice> list() {
        String redisHashKey = RedisKeyConstants.NOTICE_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysNotice.class);
        }
        List<SysNotice> sysNoticeList = sysNoticeMapper.selectList(null);
        if (sysNoticeList != null) {
            Map<String,String> map = new HashMap<>();
            for (SysNotice sysNotice : sysNoticeList) {
                map.put(sysNotice.getId(), JSON.toJSONString(sysNotice));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysNoticeList;
    }

    /**
     * 保存用户信息
     * @param sysNotice
     */
    public void save(SysNotice sysNotice) {
        String redisKey = RedisKeyConstants.NOTICE_INFO_KEY + sysNotice.getId();
        String redisHashKey = RedisKeyConstants.NOTICE_LIST_KEY;
        if (findById(sysNotice.getId()) != null) {
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysNotice.getId())){
                redisService.deleteOne(redisHashKey, sysNotice.getId());
            }
            sysNoticeMapper.updateById(sysNotice);
        } else{
            sysNoticeMapper.insert(sysNotice);
        }
        redisService.set(redisKey, sysNotice);
        redisService.setOne(redisHashKey, sysNotice.getId(), sysNotice);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id) {
        boolean flag = sysNoticeMapper.deleteById(id) > 0;
        if(flag){
            String redisKey = RedisKeyConstants.NOTICE_INFO_KEY + id;
            String redisHashKey = RedisKeyConstants.NOTICE_LIST_KEY;
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, id)){
                redisService.deleteOne(redisHashKey, id);
            }
        }
        return flag;
    }


    /**
     * 获取公告编号
     * @return String
     */
    public String getNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int noticeLength = list().size();
        String number =String.format("%d-%02d-%02dGG%05d",year, month, day, noticeLength);
        return number;
    }
}
