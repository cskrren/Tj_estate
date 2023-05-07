package com.rkr.utils;

import com.rkr.domain.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @Package com.rkr.utils
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysLoginService:用户登录服务管理
 */

@Component
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    public static final String UNKNOWN = "XX XX";

    /**
     * 根据IP地址获取地理位置
     * @param ip IP地址
     * @return 地理位置
     */
    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }
}
