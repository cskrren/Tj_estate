package com.rkr.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Package com.rkr.handler
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description MyMetaObjectHandler:自动填充
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createTime",Date.class,new Date());
        this.strictInsertFill(metaObject,"updateTime",Date.class,new Date());
    }

    /**
     * 更新时填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject,"updateTime",Date.class,new Date());

    }
}