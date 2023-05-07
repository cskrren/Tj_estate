package com.rkr.service;

import com.rkr.domain.entity.SysChargeType;
import com.rkr.mapper.SysChargeTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysChargeTypeService:用户缴费服务管理
 */

@Service
public class SysChargeTypeService {

    /**
     * 用户服务
     */
    @Resource
    private SysChargeTypeMapper sysChargeTypeMapper;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysChargeType
     */
    public SysChargeType findById(Integer id){
        return sysChargeTypeMapper.selectById(id);
    }

    /**
     * 根据用户名查询用户信息
     * @return List<SysChargeType>
     */
    public List<SysChargeType> list(){
        return sysChargeTypeMapper.selectList(null);
    }

    /**
     * 保存用户信息
     * @param sysChargeType
     */
    public void save(SysChargeType sysChargeType){
        if(findById(sysChargeType.getId()) != null){
            sysChargeTypeMapper.updateById(sysChargeType);
            return;
        }
        sysChargeTypeMapper.insert(sysChargeType);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return SysChargeType
     */
    public boolean delete(String id){
        return sysChargeTypeMapper.deleteById(id) > 0;
    }
}
