package com.rkr.service;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.rkr.domain.entity.SysLogin;
import com.rkr.domain.entity.SysUser;
import com.rkr.service.security.LoginUser;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysLoginService:用户登录服务管理
 */

@Service
public class SysLoginService {

    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;
    /**
     * 认证管理器
     */
    @Resource
    private AuthenticationManager authenticationManager;
    /**
     * 密码加密
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Resource
    private RedisService redisService;

    /**
     * 用户登录
     * @param sysLogin
     * @return SysUser
     */
    public SysUser login(SysLogin sysLogin){
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(sysLogin.getUserName(), sysLogin.getPassWord()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //将登录信息存储到Session中
        setUserInfoToSession(loginUser);
        //将用户信息存储到Redis中
        redisService.set("user", JSON.toJSONString(loginUser.getUser()));
        //更新用户状态
        sysUserInfoService.updateStatus(loginUser.getUser().getId());
        //删除验证码信息
        removeLoginCode();
        return loginUser.getUser();
    }

    /**
     * 登出
     * @return
     */
    public void logout() {
        //删除Redis中的用户信息
        redisService.delete("user");
        //获取用户ID
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        //更新用户状态
        sysUserInfoService.updateStatus(userId);
        RequestUtils.invalidate();
    }

    /**
     * 验证码校验
     * @param code
     * @return
     */
    public boolean checkCode(String code){
        HttpServletRequest request = RequestUtils.getCurrentRequest();
        System.out.println(request.getSession().getId());
        String sCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(sCode);
        if(sCode == null || !code.equals(sCode)){
            return false;
        }
        return true;
    }

    /**
     * 将用户信息存储到Session中
     * @param loginUser
     */
    public void setUserInfoToSession(LoginUser loginUser){
        RequestUtils.setCurrentSessionAttribute(SysLogin.LOGIN_USER_SESSION_KEY,loginUser);
    }

    /**
     * 删除验证码信息
     */
    public void removeLoginCode(){
        RequestUtils.removeCurrentSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
    }


}
