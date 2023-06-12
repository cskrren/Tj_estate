package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.Option;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.constant.SMS;
import com.rkr.domain.entity.SysRepair;
import com.rkr.domain.entity.SysSmsCode;
import com.rkr.mapper.SysRepairMapper;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRepairService:用户报修信息查询
 */

@Service
public class SysRepairService {

    /**
     * 报修服务
     */
    @Resource
    private SysRepairMapper sysRepairMapper;
    /**
     * 短信服务
     */
    @Autowired
    private SysSmsCodeService sysSmsCodeService;

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
     * @return
     */
    public SysRepair findById(Integer id) {
        String redisKey = RedisKeyConstants.REPAIR_INFO_KEY + id;
        if (redisService.hasKey(redisKey)) {
            return redisService.get(redisKey, SysRepair.class);
        }
        SysRepair sysRepair = sysRepairMapper.selectById(id);
        if (sysRepair != null) {
            redisService.set(redisKey, sysRepair);
        }
        return sysRepair;
    }

    /**
     * 查询所有信息
     * @return
     */
    public List<SysRepair> list() {
        String redisHashKey = RedisKeyConstants.REPAIR_LIST_KEY;
        if (redisService.hasKey(redisHashKey)) {
            return redisService.getHash(redisHashKey, SysRepair.class);
        }
        List<SysRepair> sysRepairList = sysRepairMapper.selectList(null);
        if (sysRepairList != null) {
            Map<String, String> map = new HashMap<>();
            for (SysRepair sysRepair : sysRepairList) {
                map.put(RedisKeyConstants.REPAIR_INFO_KEY + sysRepair.getId(), JSON.toJSONString(sysRepair));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysRepairList;
    }

    /**
     * 根据用户ID进行查询
     * @param userId
     * @return
     */
    public List<SysRepair> findByUserId(String userId){
        QueryWrapper<SysRepair> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        String redisHashKey = RedisKeyConstants.REPAIR_LIST_KEY + userId;
        if (lazyFLag.containsKey(userId) && !lazyFLag.get(redisHashKey) && redisService.hasKey(redisHashKey)) {
            return redisService.getHash(redisHashKey, SysRepair.class);
        }
        List<SysRepair> sysRepairList = sysRepairMapper.selectList(wrapper);
        lazyFLag.put(redisHashKey, false);
        if (sysRepairList != null) {
            Map<String, String> map = new HashMap<>();
            for (SysRepair sysRepair : sysRepairList) {
                map.put(sysRepair.getId().toString(), JSON.toJSONString(sysRepair));
            }
            redisService.setHash(redisHashKey, map);
        }
        return sysRepairList;
    }

    /**
     * 保存报修
     * @param sysRepair
     */
    public void save(SysRepair sysRepair) {
        String redisKey = RedisKeyConstants.REPAIR_INFO_KEY + sysRepair.getId();
        String redisHashKey = RedisKeyConstants.REPAIR_LIST_KEY;
        SysRepair oldRepair = findById(sysRepair.getId());
        if (oldRepair != null) {
            if(redisService.hasKey(redisKey)){
                redisService.delete(redisKey);
            }
            if(redisService.hasHashKey(redisHashKey, sysRepair.getId().toString())){
                redisService.deleteOne(redisHashKey, sysRepair.getId().toString());
            }
            sysRepairMapper.updateById(sysRepair);
            lazyFLag.put(oldRepair.getUserId(), true);
        } else {
            sysRepairMapper.insert(sysRepair);
        }
        lazyFLag.put(sysRepair.getUserId(), true);
        redisService.set(redisKey, sysRepair);
        redisService.setOne(redisHashKey, sysRepair.getId().toString(), sysRepair);
    }

    /**
     * 添加报修
     * @param sysRepair
     */
    public void add(SysRepair sysRepair) {
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        sysRepair.setUserId(userId);
        JSONObject jsonObject = JSON.parseObject(sysOptionsService.findById(Option.rq_repair_info).getText().toString());
        String contact_phone = jsonObject.getString(Option.rq_repair_info_contact_phone);
        save(sysRepair);
        //发送短信
        sysSmsCodeService.sendSmsInfo(contact_phone, SMS.CUSTOMER_REPAIR_NOTICE_TEMPLATE_ID);
    }

    /**
     * 审核报修
     * @param sysRepair
     */
    public void examine(SysRepair sysRepair){
        sysRepair.setIsExamine(1);
        save(sysRepair);
        //发送短信
        sysSmsCodeService.sendSmsInfo(sysRepair.getPhone(), SMS.CUSTOMER_REPAIR_RECEIPT_TEMPLATE_ID);
    }

    /**
     * 删除报修
     * @param id
     * @return
     */
    public boolean delete(String id) {
        SysRepair deleteRepair = findById(Integer.valueOf(id));
        if(deleteRepair == null) {
            return false;
        }
        String redisKey = RedisKeyConstants.REPAIR_INFO_KEY + id;
        String redisHashKey = RedisKeyConstants.REPAIR_LIST_KEY;
        if(redisService.hasKey(redisKey)){
            redisService.delete(redisKey);
        }
        if(redisService.hasHashKey(redisHashKey, id)){
            redisService.deleteOne(redisHashKey, id);
        }
        lazyFLag.put(deleteRepair.getUserId(), true);
        return sysRepairMapper.deleteById(id) > 0;
    }
}
