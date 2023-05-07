package com.rkr.service;

import com.rkr.domain.entity.SysFacilities;
import com.rkr.mapper.SysFacilitiesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysFacilitiesService:用户设施服务管理
 */

@Service
public class SysFacilitiesService {

    /**
     * 用户设施服务管理
     */
    @Resource
    private SysFacilitiesMapper sysFacilitiesMapper;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysFacilities
     */
    public SysFacilities findById(String id){
        return sysFacilitiesMapper.selectById(id);
    }

    /**
     * 获取所有用户信息
     * @return List<SysFacilities>
     */
    public List<SysFacilities> list(){
        return sysFacilitiesMapper.selectList(null);
    }

    /**
     * 保存用户信息
     * @param sysFacilities
     */
    public void save(SysFacilities sysFacilities){
        if(findById(sysFacilities.getId()) != null){
            sysFacilitiesMapper.updateById(sysFacilities);
            return;
        }
        sysFacilitiesMapper.insert(sysFacilities);
    }

    /**
     * 根据用户名删除用户信息
     * @param id
     * @return boolean
     */
    public boolean delete(String id){
        return sysFacilitiesMapper.deleteById(id) > 0;
    }
}
