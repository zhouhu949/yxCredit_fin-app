package com.zw.rule.web.util.view;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * excel视图
 * @author 陈清玉
 */
public class ExcelXlsView extends AbstractXlsView {
    private static final String DATA_IN_MODEL_NAME = "data";
    private List<String> columnNames;
    private List<String> propertyNames;

    public ExcelXlsView(List<String> columnNames, List<String> propertyNames){
        this.columnNames = columnNames;
        this.propertyNames = propertyNames;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = model.get("fileName").toString() + ".xls";
        // 响应头
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        // 数据
        List list = (List) model.get(DATA_IN_MODEL_NAME);
        // 表格
        Sheet sheet = workbook.createSheet("Sheet1");
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);

        // 行数
        int rowNum = 0;
        // 表头
        Row header = sheet.createRow(rowNum++);
        header.setHeight((short) (25 * 20));
        // 标题样式
        CellStyle headerStyle = getHeaderStyle(workbook);
        // 标题
        for (int i = 0; i < columnNames.size(); i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnNames.get(i));
            cell.setCellStyle(headerStyle);
        }
        // 内容
        for (Object object : list) {
            Row row = sheet.createRow(rowNum++);
            row.setHeightInPoints(20);
            for (int i = 0; i < propertyNames.size(); i++) {
                Object value = PropertyUtils.getProperty(object, propertyNames.get(i));
                row.createCell(i).setCellValue(value == null ? "" : value.toString());
            }
        }
    }

    /**
     * 设置标题样式（灰色背景 粗体 居中）
     *
     * @param workbook
     * @return
     */
    private CellStyle getHeaderStyle(Workbook workbook) {
        // 标题样式
        CellStyle headerStyle = workbook.createCellStyle();
        // 字体
        Font font = workbook.createFont();
        // 粗体
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        // 设置字体
        headerStyle.setFont(font);
        // 字体居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return headerStyle;
    }
}
