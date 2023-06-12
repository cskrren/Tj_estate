package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.Option;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.constant.SMS;
import com.rkr.domain.entity.SysComplaint;
import com.rkr.mapper.SysComplaintMapper;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysComplaintService:用户登录认证信息查询
 */

@Service
public class SysComplaintService {

    /**
     * 投诉服务
     */
    @Resource
    private SysComplaintMapper sysComplaintMapper;

    /**
     * 短信服务
     */
    @Autowired
    private SysSmsCodeService smsCodeService;

    /**
     * 系统配置服务
     */
    @Autowired
    private SysOptionsService sysOptionsService;

    @Resource
    private RedisService redisService;

    private Map<String, Boolean> lazyFLag = new HashMap<>();

    /**
     * 根据ID查询
     * @param id
     * @return SysComplaint
     */
    public SysComplaint findById(Integer id) {
        String redisKey = RedisKeyConstants.COMPLAINT_INFO_KEY + id;
        if(redisService.hasKey(redisKey)){
            return redisService.get(redisKey, SysComplaint.class);
        }
        SysComplaint sysComplaint = sysComplaintMapper.selectById(id);
        if(sysComplaint != null){
            redisService.set(redisKey, sysComplaint);
        }
        return sysComplaint;
    }

    /**
     * 根据用户ID进行查询
     * @param userId
     * @return List<SysComplaint>
     */
    public List<SysComplaint> findByUserId(String userId){
        String redisHashKey = RedisKeyConstants.COMPLAINT_LIST_KEY + userId;
        if(lazyFLag.containsKey(userId) && !lazyFLag.get(userId) && redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysComplaint.class);
        }
        QueryWrapper<SysComplaint> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysComplaint> sysComplaints = sysComplaintMapper.selectList(wrapper);
        lazyFLag.put(userId, false);
        if(sysComplaints != null){
            Map<String, String> map = new HashMap<>();
            for (SysComplaint sysComplaint : sysComplaints) {
                map.put(sysComplaint.getId().toString(), JSON.toJSONString(sysComplaint));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysComplaints;
    }

    /**
     * 展示所有用户信息
     * @return List<SysComplaint>
     */
    public List<SysComplaint> list() {
        String redisHashKey = RedisKeyConstants.COMPLAINT_LIST_KEY;
        if(redisService.hasKey(redisHashKey)){
            return redisService.getHash(redisHashKey, SysComplaint.class);
        }
        List<SysComplaint> sysComplaints = sysComplaintMapper.selectList(null);
        if(sysComplaints != null){
            Map<String, String> map = new HashMap<>();
            for (SysComplaint sysComplaint : sysComplaints) {
                map.put(sysComplaint.getId().toString(), JSON.toJSONString(sysComplaint));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysComplaints;
    }

    /**
     * 保存用户信息
     * @param sysComplaint
     * @return List<SysComplaint>
     */
    public void save(SysComplaint sysComplaint) {
        String redisKey = RedisKeyConstants.COMPLAINT_INFO_KEY + sysComplaint.getId();  //单条信息Key
        String redisHashKey = RedisKeyConstants.COMPLAINT_LIST_KEY; //整张表Key
        SysComplaint oldComplaint = findById(sysComplaint.getId());
        if(oldComplaint != null){
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysComplaint.getId().toString())){
                redisService.delete(redisHashKey);
            }
            sysComplaintMapper.updateById(sysComplaint);
            lazyFLag.put(sysComplaint.getUserId(), true);//更新后需要重新加载
        } else {
            sysComplaintMapper.insert(sysComplaint);
        }
        String UserId = RequestUtils.getCurrentLoginUser().getUser().getId();
        lazyFLag.put(UserId, true);//更新后需要重新加载
        redisService.set(redisKey, sysComplaint);
        redisService.setOne(redisHashKey, sysComplaint.getId().toString(), sysComplaint);
    }

    /**
     * 添加投诉信息，同时发送短信通知
     * @param sysComplaint
     */
    public void add(SysComplaint sysComplaint) {
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        sysComplaint.setUserId(userId);
        sysComplaint.setDate(new Date());
        JSONObject jsonObject = JSON.parseObject(sysOptionsService.findById(Option.rq_repair_info).getText().toString());
        String contactPhone = jsonObject.getString(Option.rq_repair_info_contact_phone);
        save(sysComplaint);
        //发送短信
        smsCodeService.sendSmsInfo(contactPhone, SMS.CUSTOMER_COMPLAINT_NOTICE_TEMPLATE_ID);
    }

    /**
     * 审核投诉信息，同时发送短信通知
     * @param sysComplaint
     */
    public void examine(SysComplaint sysComplaint){
        sysComplaint.setIsExamine(1);
        save(sysComplaint);
        //发送短信
        smsCodeService.sendSmsInfo(sysComplaint.getPhone(), SMS.CUSTOMER_COMPLAINT_RECEIPT_TEMPLATE_ID);
    }

    /**
     * 删除投诉信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id) {
        String redisKey = RedisKeyConstants.COMPLAINT_INFO_KEY + id;
        String redisHashKey = RedisKeyConstants.COMPLAINT_LIST_KEY;
        SysComplaint deleteComplaint = findById(Integer.parseInt(id));
        if(deleteComplaint == null){
            return false;
        }
        if(redisService.hasKey(redisKey)){
            redisService.delete(redisKey);
        }
        if(redisService.hasHashKey(redisHashKey, id)){
            redisService.deleteOne(redisHashKey, id);
        }
        lazyFLag.put(deleteComplaint.getUserId(), true);//更新后需要重新加载
        return sysComplaintMapper.deleteById(id) > 0;
    }
}
