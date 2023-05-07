package com.rkr.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.rkr.domain.funInterface.CustomCellWrite;
import com.rkr.handler.CustomCellWriteHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Package com.rkr.utils.excel
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description ExcelUtils:Excel工具类
 */
@Component
public class ExcelUtils {
    /**
     * 创建对应数据的表格并传递给前端
     * @param response        响应
     * @param fileName        文件名称
     * @param clazz           目标数据实体类
     * @param data            数据集合
     * @param customCellWrite 自定义的单元格处理类
     */
    public void download(HttpServletResponse response, String fileName, Class clazz, List data, CustomCellWrite customCellWrite) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(response.getOutputStream(), clazz)
                    .sheet("sheet1");
            // 自定义单元格处理器如果不为空就加入拦截器
            if (customCellWrite != null) {
                excelWriterSheetBuilder = excelWriterSheetBuilder.
                        registerWriteHandler(new CustomCellWriteHandler(customCellWrite));
            }
            excelWriterSheetBuilder.doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
