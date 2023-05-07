package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.constant.Option;
import com.rkr.domain.constant.SMS;
import com.rkr.domain.entity.SysRepair;
import com.rkr.domain.entity.SysSmsCode;
import com.rkr.mapper.SysRepairMapper;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public SysRepair findById(Integer id) {
        return sysRepairMapper.selectById(id);
    }

    /**
     * 查询所有信息
     * @return
     */
    public List<SysRepair> list() {
        return sysRepairMapper.selectList(null);
    }

    /**
     * 根据用户ID进行查询
     * @param userId
     * @return
     */
    public List<SysRepair> findByUserId(String userId){
        QueryWrapper<SysRepair> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return sysRepairMapper.selectList(wrapper);
    }

    /**
     * 保存报修
     * @param sysRepair
     */
    public void save(SysRepair sysRepair) {
        if (findById(sysRepair.getId()) != null) {
            sysRepairMapper.updateById(sysRepair);
            return;
        }
        sysRepairMapper.insert(sysRepair);
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
        return sysRepairMapper.deleteById(id) > 0;
    }
}
