package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.Option;
import com.rkr.domain.constant.SMS;
import com.rkr.domain.entity.SysComplaint;
import com.rkr.mapper.SysComplaintMapper;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    /**
     * 根据ID查询
     * @param id
     * @return SysComplaint
     */
    public SysComplaint findById(Integer id) {
        return sysComplaintMapper.selectById(id);
    }

    /**
     * 根据用户ID进行查询
     * @param userId
     * @return List<SysComplaint>
     */
    public List<SysComplaint> findByUserId(String userId){
        QueryWrapper<SysComplaint> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return sysComplaintMapper.selectList(wrapper);
    }

    /**
     * 展示所有用户信息
     * @return List<SysComplaint>
     */
    public List<SysComplaint> list() {
        return sysComplaintMapper.selectList(null);
    }

    /**
     * 保存用户信息
     * @param sysComplaint
     * @return List<SysComplaint>
     */
    public void save(SysComplaint sysComplaint) {
        if (findById(sysComplaint.getId()) != null) {
            sysComplaintMapper.updateById(sysComplaint);
            return;
        }
        sysComplaintMapper.insert(sysComplaint);
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
        return sysComplaintMapper.deleteById(id) > 0;
    }
}
