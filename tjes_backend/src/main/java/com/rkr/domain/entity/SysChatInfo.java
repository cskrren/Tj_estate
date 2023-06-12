package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysChatInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 群聊Id
     */
    private Integer groupId;

    /**
     * 发言者名字
     */
    private String userName;

    /**
     * 发言者头像
     */
    private String userAvatar;

    /**
     * 发言内容
     */
    private String text;

    /**
     * 群聊创建时间
     */
    private Date date;
}
