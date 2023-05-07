package com.rkr.service;

import com.rkr.domain.entity.SysSmsCode;
import com.rkr.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysSmsCodeService:短信服务
 */

@Service
public class SysSmsCodeService {

    /**
     * 短信工具类
     */
    @Autowired
    private SmsUtils smsUtils;

    /**
     * 发送短信
     * @param sysSmsCode
     * @return  是否发送成功
     */
    public boolean sendSmsCode(SysSmsCode sysSmsCode) {
        try{
            smsUtils.sendSmsRegisterCode(sysSmsCode.getPhoneNumber(),sysSmsCode.getCode());
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    /**
     * 发送短信
     * @param phoneNumber
     * @param templateId
     * @return  是否发送成功
     */
    public boolean sendSmsInfo(String phoneNumber,String templateId) {
        try{
            smsUtils.sendSmsInfo(phoneNumber,templateId);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

}
