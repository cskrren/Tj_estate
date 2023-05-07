package com.rkr.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.rkr.domain.funInterface.CustomCellWrite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @Package com.rkr.handler
 * @auhter rkr
 * @date 2023/5/1 00:22
 * @description CustomCellWriteHandler:自定义单元格写入处理器
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomCellWriteHandler implements CellWriteHandler {

    /**
     * 自定义单元格写入接口
     */
    private CustomCellWrite customCellWrite;

    /**
     * 在创建行之前执行
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param row
     * @param head
     * @param columnIndex
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     * 在创建行之后执行
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     * 在写入单元格之前执行
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param cellData
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     * 在写入单元格之后执行
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param cellDataList
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (customCellWrite != null) {
            customCellWrite.operation(writeSheetHolder, cell, isHead);
        }
    }
}
