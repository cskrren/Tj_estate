package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.constant.UserType;
import com.rkr.domain.entity.SysUser;
import com.rkr.domain.entity.SysUserInfo;
import com.rkr.domain.entity.SysUserRoom;
import com.rkr.domain.entity.SysUserRoomData;
import com.rkr.service.SysUserChargeService;
import com.rkr.service.SysUserInfoService;
import com.rkr.service.SysUserService;
import com.rkr.service.security.LoginUser;
import com.rkr.utils.IpUtils;
import com.rkr.utils.RequestUtils;
import com.rkr.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:46
 * @description SysUserController:用户管理
 */

@RestController
@RequestMapping("/system/user")
public  class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserChargeService sysUserChargeService;
    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private UploadUtils uploadUtils;

    /**
     * 获取当前登录用户ID
     * @return AjaxResult
     */
    @GetMapping("/getUserId")
    public AjaxResult getUserId() {
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        return AjaxResult.success(loginUser.getUser().getId());
    }

    /**
     * 用户列表
     * @return AjaxResult
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermi('system:user:list')")
    public AjaxResult list() {
        return AjaxResult.success(sysUserService.list());
    }
//    @PreAuthorize("@ps.hasPermi('system:user_admin:list')")

    /**
     * 管理员列表
     * @return AjaxResult
     */
    @GetMapping("/admin/list")
    public AjaxResult listOfAdmin() {
        return AjaxResult.success(sysUserService.findByUserRole(UserType.ADMIN));
    }

    /**
     * 注册
     * @return AjaxResult
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUser sysUser, HttpServletRequest req) {
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(new Date());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", sysUserService.register(sysUser));
        return AjaxResult.success(jsonObject);
    }

    /**
     * 保存
     * @return AjaxResult
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody SysUser sysUser, HttpServletRequest req) {
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(new Date());
        if(sysUser.getId() == null){
            sysUserService.register(sysUser);
        }else{
            sysUserService.save(sysUser);
        }
        return AjaxResult.success();
    }

    /**
     * 判断是否为管理员
     * @return AjaxResult
     */
    @GetMapping("/isAdmin")
    public AjaxResult role() {
        boolean isAdmin = false;
        try {
            LoginUser loginUser = RequestUtils.getCurrentLoginUser();
            isAdmin = sysUserService.isAdmin(loginUser.getUser().getId());
        } catch (Exception e) {
            RequestUtils.Forbidden();
        }
        return AjaxResult.success(isAdmin);
    }

    /**
     * 获取信息队列
     * @return AjaxResult
     */
    @GetMapping("/household/list")
    @PreAuthorize("@ps.hasPermi('system:user_householdInfo:list')")
    public AjaxResult HouseholdInfoList() {
        return AjaxResult.success(sysUserService.HouseholdInfoList());
    }

    /**
     * 业主信息保存
     * @param sysUserRoomData
     */
    @PostMapping("/household/save")
    @PreAuthorize("@ps.hasPermi('system:user_householdInfo:save')")
    public AjaxResult HouseholdInfoSave(@RequestBody SysUserRoomData sysUserRoomData) {
        sysUserService.HouseholdInfoSave(sysUserRoomData);
        return AjaxResult.success();
    }

    /**
     * 业主信息删除
     * @param sysUserRoom
     */
    @PostMapping("/household/delete")
    @PreAuthorize("@ps.hasPermi('system:user_householdInfo:delete')")
    public AjaxResult HouseholdInfoDelete(@RequestBody SysUserRoom sysUserRoom) {
        sysUserService.HouseholdInfoDelete(sysUserRoom);
        return AjaxResult.success();
    }

    /**
     * 获取月缴费记录
     * @return AjaxResult
     */
    @GetMapping("/pay/record/month")
    public AjaxResult getPayRecordOfMonth(){
        LoginUser user = RequestUtils.getCurrentLoginUser();
        return AjaxResult.success(sysUserChargeService.findByOfMonth(user.getUser().getId()));
    }

    /**
     * 缴费
     * @param chargeTypeId
     * @return AjaxResult
     */
    @GetMapping("/pay/fess/{chargeTypeId}")
    public AjaxResult paymentFees(@PathVariable("chargeTypeId") Integer chargeTypeId){
        LoginUser user = RequestUtils.getCurrentLoginUser();
        sysUserChargeService.paymentFees(user.getUser().getId(),chargeTypeId);
        return AjaxResult.success();
    }

    /**
     * 获取房产队列
     * @return AjaxResult
     */
    @GetMapping("/room/list")
    public AjaxResult getRoomList() {
        //获取当然登录用户的id
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        System.out.println(loginUser.getUser().getId());
        return AjaxResult.success(sysUserService.RoomList(loginUser.getUser().getId()));
    }

    /**
     * 获取队列
     * @return AjaxResult
     */
    @GetMapping("/car/list")
    public AjaxResult getCarList() {
        //获取当然登录用户的id
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        System.out.println(loginUser.getUser().getId());
        return AjaxResult.success(sysUserService.CarList(loginUser.getUser().getId()));
    }

    /**
     * 获取队列
     * @return AjaxResult
     */
    @GetMapping("/information/list")
    public AjaxResult getUserInfoList() {
        //获取当然登录用户的id
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        System.out.println(loginUser.getUser().getId());
        return AjaxResult.success(sysUserService.UserInfoList(loginUser.getUser().getId()));
    }

    /**
     * 保存
     */
    @PostMapping("/information/save")
    public AjaxResult saveUserInfo(@RequestBody SysUserInfo sysUserInfo) {
        System.out.println(sysUserInfo);
        sysUserInfoService.save(sysUserInfo);
        return AjaxResult.success();
    }

    /**
     * 获取队列
     * @return AjaxResult
     */
    @GetMapping("/avatar/list")
    public AjaxResult getUserAvatar() {
        //获取当然登录用户的id
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        System.out.println(loginUser.getUser().getId());
        return AjaxResult.success(sysUserInfoService.getUserAvatar(loginUser.getUser().getId()));
    }

    /**
     * 保存
     * @param file
     * @return AjaxResult
     */
    @PostMapping("/avatar/save")
    public AjaxResult saveUserAvatar(@RequestParam("file") MultipartFile file) {
        //获取当然登录用户的id
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        String user_id = loginUser.getUser().getId();
        System.out.println(user_id);
        try{
            String url = uploadUtils.upload(file);
            sysUserInfoService.updateUserAvatar(user_id, url);
            return AjaxResult.success("上传成功");
        } catch (IOException e) {
            return AjaxResult.error("上传失败");
        }
    }

}
