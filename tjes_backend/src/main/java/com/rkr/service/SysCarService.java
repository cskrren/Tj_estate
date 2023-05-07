package com.rkr.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.domain.entity.SysCar;
import com.rkr.mapper.SysBuildingMapper;
import com.rkr.mapper.SysCarMapper;
import com.rkr.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCarService {
    /**
     * 用户服务
     */
    @Resource
    private SysCarMapper sysCarMapper;

    /**
     * 根据用户名查询用户信息
     * @param id
     * @return SysCar
     */
    public SysCar findById(String id) {
        return sysCarMapper.selectById(id);
    }

    /**
     * 展示所有用户信息
     * @return List<SysCar>
     */
    public List<SysCar> list() {
        return sysCarMapper.selectList(null);
    }

    /**
     * 限制查询字段
     * @param colName
     * @return List<SysCar>
     */
    public List<SysCar> list(String colName){
        QueryWrapper<SysCar> wrapper = new QueryWrapper<>();
        wrapper.select(StringUtils.format("DISTINCT {}" ,colName));
        return sysCarMapper.selectList(wrapper);
    }

    /**
     * 返回所有用户名
     * @return List<SysCar>
     */
    public List<SysCar> nameList(){
        return list("name");
    }

    /**
     * 保存用户信息
     * @param sysCar
     * @return List<SysCar>
     */
    public void save(SysCar sysCar){
        if(findById(sysCar.getId()) != null){
            sysCarMapper.updateById(sysCar);
            return;
        }
        sysCarMapper.insert(sysCar);
    }

    /**
     * 删除用户信息
     * @param id
     * @return List<SysBuilding>
     */
    public boolean delete(String id){
        return sysCarMapper.deleteById(id) > 0;
    }
}
