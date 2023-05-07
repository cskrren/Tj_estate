package com.rkr.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysNotice:公告
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysNotice {
    private String id;
    /**
     * 公告标题
     */
    private String title;
    /**
     * 公告发布时间
     */
    private Date date;
    /**
     * 公告内容
     */
    private String text;
    /**
     * 公告html内容
     */
    private String html;
    /**
     * 公告作者
     */
    private String author;
}
