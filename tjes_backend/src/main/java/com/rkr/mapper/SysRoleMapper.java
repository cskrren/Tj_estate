package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRoleMapper:角色信息
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
