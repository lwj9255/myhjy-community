package com.hhu.myhjycommunity.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.hhu.myhjycommunity.common.core.exception.BaseException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    public static void exportExcel(List<?> list,
                                   Class<?> pojoClass,
                                   String fileName,
                                   HttpServletResponse response,
                                   ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downloadExcel(fileName, response, workbook);
    }

    private static void downloadExcel(String fileName,
                                      HttpServletResponse response,
                                      Workbook workbook) {
        ServletOutputStream outputStream = null;

        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-disposition",
                    "attachment;fileName=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));

            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            log.error("导出Excel异常:{}", e.getMessage());
            throw new BaseException("500", "导出Excel失败，请联系网站管理员");
        } finally {
            try {
                outputStream.close();
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
