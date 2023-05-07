package com.rkr.domain.funInterface;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @Package com.rkr.domain.funInterface
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description CustomCellWrite:自定义单元格写入接口
 */
@FunctionalInterface
public interface CustomCellWrite {
    void operation(WriteSheetHolder writeSheetHolder, Cell cell, Boolean isHead);
}
