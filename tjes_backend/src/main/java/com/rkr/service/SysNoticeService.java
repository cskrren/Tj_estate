package com.rkr.service;

import com.rkr.domain.entity.SysNotice;
import com.rkr.mapper.SysNoticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysNoticeService:用户公告服务管理
 */

@Service
public class SysNoticeService {

    /**
     * 用户公告服务管理
     */
    @Resource
    private SysNoticeMapper sysNoticeMapper;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysNotice
     */
    public SysNotice findById(String id) {
        return sysNoticeMapper.selectById(id);
    }

    /**
     * 获取所有用户信息
     * @return List<SysNotice>
     */
    public List<SysNotice> list() {
        return sysNoticeMapper.selectList(null);
    }

    /**
     * 保存用户信息
     * @param sysNotice
     */
    public void save(SysNotice sysNotice) {
        if (findById(sysNotice.getId()) != null) {
            sysNoticeMapper.updateById(sysNotice);
            return;
        }
        sysNoticeMapper.insert(sysNotice);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id) {
        return sysNoticeMapper.deleteById(id) > 0;
    }


    /**
     * 获取公告编号
     * @return String
     */
    public String getNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int noticeLength = list().size();
        String number =String.format("%d-%02d-%02dGG%05d",year, month, day, noticeLength);
        return number;
    }
}
