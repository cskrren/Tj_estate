package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysChat;
import com.rkr.domain.entity.SysComplaint;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysChatInfoMapper:聊天信息
 */

@Mapper
public interface SysChatMapper extends BaseMapper<SysChat> {
}
