package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysChargeType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysChargeTypeMapper:费用类型
 */

@Mapper
public interface SysChargeTypeMapper extends BaseMapper<SysChargeType> {

    /**
     * 查询所有费用类型
     */
    public void list();

}
