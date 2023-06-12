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
public class SysChat {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 群聊名字
     */
    private String name;

    /**
     * 群聊头像
     */
    private String img;

    /**
     * 群聊简介
     */
    private String dept;

    /**
     * 群聊人员
     */
    private String members;

    /**
     * 群聊创建时间
     */
    private Date date;
}

