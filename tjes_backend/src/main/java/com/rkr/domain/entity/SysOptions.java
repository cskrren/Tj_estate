package com.rkr.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysOptions:系统选项
 */

@Data
@NoArgsConstructor
public class SysOptions {
    public static final String RQ_INFO = "rq_info";

    private String id;
    /**
     * 选项名称
     */
    private Object text;

    /**
     * 构造函数
     * @param id
     * @param text
     */
    public SysOptions(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
