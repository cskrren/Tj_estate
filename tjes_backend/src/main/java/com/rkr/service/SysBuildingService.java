package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.mapper.SysBuildingMapper;
import com.rkr.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysBuildingService:用户登录认证信息查询
 */

@Service
public class SysBuildingService {

    /**
     * 用户服务
     */
    @Resource
    private SysBuildingMapper sysBuildingMapper;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysBuilding
     */
    public SysBuilding findById(String id){
        return sysBuildingMapper.selectById(id);
    }

    /**
     * 展示所有用户信息
     * @return List<SysBuilding>
     */
    public List<SysBuilding> list(){
        return sysBuildingMapper.selectList(null);
    }

    /**
     * 限制查询字段
     * @param colName
     * @return List<SysBuilding>
     */
    public List<SysBuilding> list(String colName){
        QueryWrapper<SysBuilding> wrapper = new QueryWrapper<>();
        wrapper.select(StringUtils.format("DISTINCT {}" ,colName));
        return sysBuildingMapper.selectList(wrapper);
    }

    /**
     * 返回所有用户名
     * @return List<SysBuilding>
     */
    public List<SysBuilding> nameList(){
        return list("name");
    }

    /**
     * 保存用户信息
     * @param sysBuilding
     * @return List<SysBuilding>
     */
    public void save(SysBuilding sysBuilding){
        if(findById(sysBuilding.getId()) != null){
            sysBuildingMapper.updateById(sysBuilding);
            return;
        }
        sysBuildingMapper.insert(sysBuilding);
    }

    /**
     * 删除用户信息
     * @param id
     * @return List<SysBuilding>
     */
    public boolean delete(String id){
        return sysBuildingMapper.deleteById(id) > 0;
    }
}
