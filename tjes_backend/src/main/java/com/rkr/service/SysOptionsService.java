package com.rkr.service;

import com.rkr.mapper.SysOptionsMapper;
import com.rkr.domain.entity.SysOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysOptionsService:用户选项服务管理
 */

@Service
public class SysOptionsService {

    /**
     * 用户选项服务管理
     */
    @Resource
    private SysOptionsMapper sysOptionsMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param id
     * @return SysOptions
     */
    public SysOptions findById(String id) {
        return sysOptionsMapper.selectById(id);
    }

    /**
     * 保存用户信息
     *
     * @param sysOptions
     */
    public void save(SysOptions sysOptions) {
        if (findById(sysOptions.getId()) != null) {
            sysOptionsMapper.updateById(sysOptions);
            return;
        }
        sysOptionsMapper.insert(sysOptions);
    }
}
