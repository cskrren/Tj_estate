package com.rkr.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysUserCharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserChargeMapper:用户游玩记录
 */

@Mapper
public interface SysUserChargeMapper extends BaseMapper<SysUserCharge> {

    /**
     * 根据用户id和收费类型id查询当前月份的记录
     * @param userId
     * @param chargeTypeId
     * @param currentMonth
     * @return
     */
    @Select("SELECT * FROM sys_user_charge WHERE user_id = #{userId} AND charge_type_id = #{chargeTypeId} AND MONTH(create_time) = #{currentMonth}")
    public List<JSONObject> findByChargeTypeIdAndNowMonth(@Param("userId") String userId,@Param("chargeTypeId") Integer chargeTypeId, @Param("currentMonth") Integer currentMonth);
}
