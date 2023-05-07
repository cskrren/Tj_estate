package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserRoleMapper:用户角色信息
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
