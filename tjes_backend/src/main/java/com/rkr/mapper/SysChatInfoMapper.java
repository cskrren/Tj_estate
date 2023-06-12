package com.rkr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rkr.domain.entity.SysChat;
import com.rkr.domain.entity.SysChatInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Package com.rkr.mapper
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysChatInfoMapper:聊天信息
 */

@Mapper
public interface SysChatInfoMapper extends BaseMapper<SysChatInfo> {

    @Select("select * from sys_chat_info where group_id = #{groupId} order by create_time desc limit #{currentSize}, #{pageSize}")
    List<SysChatInfo> getHistory(Integer groupId, Integer currentSize, Integer pageSize);
}
