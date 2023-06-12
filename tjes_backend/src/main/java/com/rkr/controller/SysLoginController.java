package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysLogin;
import com.rkr.domain.entity.SysUser;
import com.rkr.service.SysLoginService;
import com.rkr.service.SysUserService;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysLoginController:登录信息管理
 */

@RestController
@RequestMapping()
public class SysLoginController {
    @Autowired
    private SysLoginService sysLoginService;//这里会报错，但是并不影响

    @Resource(name = "captchaProducer")
    private Producer producer;

    /**
     * 登录
     * @param sysLogin
     * @return AjaxResult
     */
    @PostMapping("/login")
    private AjaxResult login(@RequestBody SysLogin sysLogin) {
        if (!sysLoginService.checkCode(sysLogin.getCode())) {
            return AjaxResult.error("未输入验证码或填写错误");
        }
        SysUser user = sysLoginService.login(sysLogin);
        if (user == null) {
            return AjaxResult.error("用户名或密码错误");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",user.getUserName());
        System.out.println(jsonObject);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 登出
     * @return AjaxResult
     */
    @GetMapping("/logout")
    private AjaxResult logout() {
        sysLoginService.logout();
        return AjaxResult.success();
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/login/code")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = producer.createText();
        //一般的，将 sessionId 或其他代表用户身份的信息 && 验证码文本存入 Redis 即可。
        System.out.println(String.format("%s - %s", request.getSession().getId(), capText));
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        out.flush();
        out.close();
    }
}
