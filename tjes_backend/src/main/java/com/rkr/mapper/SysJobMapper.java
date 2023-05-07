package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysJobMapper:工作信息
 */

@Mapper
public interface SysJobMapper extends BaseMapper<SysJob> {
}
