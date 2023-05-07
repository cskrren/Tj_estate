package com.rkr.service;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.entity.SysChargeType;
import com.rkr.domain.entity.SysUserCharge;
import com.rkr.mapper.SysUserChargeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserChargeService:用户记录管理
 */

@Service
public class SysUserChargeService {

    /**
     * 用户记录管理
     */
    @Resource
    private SysUserChargeMapper sysUserChargeMapper;
    /**
     * 用户收费类型服务管理
     */
    @Autowired
    private SysChargeTypeService sysChargeTypeService;

    /**
     * 获取所有用户记录信息
     * @return List<SysUserCharge>
     */
    public List<SysUserCharge> list() {
        return sysUserChargeMapper.selectList(null);
    }

    /**
     * 根据用户ID查询JSON信息
     * @param userId
     * @return List<JSONObject>
     */
    public List<JSONObject> findByOfMonth(String userId) {
        List<JSONObject> result = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        int currentMonth = instance.get(Calendar.MONTH) + 1;
        List<SysChargeType> list = sysChargeTypeService.list();
        list.forEach(item -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chargeTypeId", item.getId());
            jsonObject.put("chargeName", item.getChargeName());
            jsonObject.put("chargeMoney", item.getChargeMoney());
            jsonObject.put("createTime", item.getCreateTime());
            boolean isPayment = false;
            if (findByChargeTypeIdAndNowMonth(userId,item.getId(),currentMonth)) {
                isPayment = true;
            }
            jsonObject.put("isPayment", isPayment);
            result.add(jsonObject);

        });
        return result;
    }

    /**
     * 查询用户信息
     * @param userId
     * @param chargeTypeId
     * @param month
     * @return boolean
     */
    public boolean findByChargeTypeIdAndNowMonth(String userId, int chargeTypeId, int month) {
        List<JSONObject> list = sysUserChargeMapper.findByChargeTypeIdAndNowMonth(userId, chargeTypeId, month);
        return list.size() > 0;

    }

    /**
     * 支付费用
     * @param userId
     * @param chargeTypeId
     */
    public void paymentFees(String userId,Integer chargeTypeId){
        SysUserCharge Charge = new SysUserCharge()
                .builder()
                .userId(userId)
                .chargeTypeId(chargeTypeId)
                .createTime(new Date())
                .build();
        sysUserChargeMapper.insert(Charge);
    }
}
