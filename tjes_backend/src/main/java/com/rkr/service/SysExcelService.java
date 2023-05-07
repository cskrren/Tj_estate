package com.rkr.service;

import com.rkr.domain.entity.SysRoom;
import com.rkr.domain.entity.SysUserRoomData;
import com.rkr.utils.excel.ExcelUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysExcelService:excel服务
 */

@Service
public class SysExcelService {

    /**
     * excel工具类
     */
    @Autowired
    private ExcelUtils excelUtils;
    /**
     * 房间服务
     */
    @Autowired
    private SysRoomService sysRoomService;
    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 下载房间信息
     * @param response
     */
    public void downloadSysRooms(HttpServletResponse response) {
        excelUtils.download(response, "room", SysRoom.class, sysRoomService.list(), (writeSheetHolder, cell, isHead) -> {
            if (!isHead && cell.getColumnIndex() == 8) {
                Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                short color = cell.getStringCellValue().equals("已有住户")
                        ? IndexedColors.RED.getIndex() : IndexedColors.BLUE.getIndex();
                font.setColor(color);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
            }
        });
    }

    /**
     * 下载住户信息
     * @param response
     */
    public void downloadSysHouseholdInfos(HttpServletResponse response){
        excelUtils.download(response, "住户信息", SysUserRoomData.class, sysUserService.HouseholdInfoList(),null);
    }
}
