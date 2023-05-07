package com.rkr;

import com.rkr.domain.entity.SysUser;
import com.rkr.service.*;
import com.rkr.utils.QuartzUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Package com.rkr
 * @auhter rkr
 * @date 2023/5/1 19:48
 * @description TJStateApplicationTests:测试类
 */

@SpringBootTest
class TJStateApplicationTests {

    @Autowired
    private QuartzUtils quartzUtils;

    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private SysUserChargeService sysUserChargeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 测试定时任务
     */
    @Test
    void QuartzTest() {
        quartzUtils.delete("job1","group1");
    }

    /**
     * 测试备份数据库
     */
    @Test
    void backups() {
        String cmd = "mysql -uroot -p021024 estate_backup -r F:\\Tj_estate\\tjes_backend\\db\\estate.sql";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            System.out.println("备份数据库成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试定时任务
     */
    @Test
    void userInfoTest() {
        System.out.println(sysNoticeService.getNumber());
    }

    /**
     * 测试用户注册
     */
    @Test
    void register(){
        SysUser sysUser = new SysUser().builder()
                .userName("hhhh")
                .fullName("王五")
                .password("123456")
                .phone("13444444444")
                .status("0")
                .build();

        String userId = sysUserService.register(sysUser);
        System.out.println(userId);
    }

    /**
     * 测试用户角色
     */
    @Test
    void FindUserRole() {
        System.out.println(sysUserRoleService.findUserRole("aaa"));
    }

    /**
     * 测试缴费
     */
    @Test
    void userPayRecordFindByOfMonth() {
        System.out.println(sysUserChargeService.findByOfMonth("aaa"));
    }
}
