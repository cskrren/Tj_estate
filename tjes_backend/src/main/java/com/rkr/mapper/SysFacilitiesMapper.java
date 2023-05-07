package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysFacilities;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysFacilitiesMapper:设施信息
 */
@Mapper
public interface SysFacilitiesMapper extends BaseMapper<SysFacilities> {
}
