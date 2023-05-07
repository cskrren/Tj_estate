package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysUserRoom;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserRoomMapper:用户信息
 */
@Mapper
public interface SysUserRoomMapper extends BaseMapper<SysUserRoom> {
}
