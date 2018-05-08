package com.zw.rule.web.reportManage.controller;

import com.zw.base.util.DateUtils;
import net.sf.ehcache.hibernate.management.impl.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private ExcelUtil() {

    }
    /**
     * 导出excel头部标题
     * @param title
     * @param cellRangeAddressLength
     * @return
     */
    public static HSSFWorkbook makeExcelHead(String title, int cellRangeAddressLength){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle styleTitle = createStyle(workbook, (short)16);
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(25);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
        sheet.addMergedRegion(cellRangeAddress);
        HSSFRow rowTitle = sheet.createRow(0);
        rowTitle.setHeight((short)1000);
        HSSFCell cellTitle = rowTitle.createCell(0);
        // 为标题设置背景颜色
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        styleTitle.setWrapText(true);
        cellTitle.setCellStyle(styleTitle);
        cellTitle.setCellValue(title);

        return workbook;
    }
    /**
     * 设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, String[] secondTitles){
        // 创建用户属性栏
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow rowField = sheet.createRow(1);
        HSSFCellStyle styleField = createStyle(workbook, (short)13);
        for (int i = 0; i < secondTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles[i]);
            cell.setCellStyle(styleField);
        }
        return workbook;
    }



    /**
     * 插入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    public static <T> HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(j + 2);
            T t = dataList.get(j);
            for(int k=0; k<beanPropertys.length; k++){
                Object value = BeanUtils.getBeanProperty(t, beanPropertys[k]);
                HSSFCell cellData = rowData.createCell(k);

                cellData.setCellValue(value.toString());

                cellData.setCellStyle(styleData);
            }
        }
        return workbook;
    }
    /**
     * 使用批量导入方法时，请注意需要导入的Bean的字段和excel的列一一对应
     * @param clazz
     * @param file
     * @param beanPropertys
     * @return
     */
    public static <T> List<T> parserExcel(Class<T> clazz, File file, String[] beanPropertys,Integer str) {
        // 得到workbook
        List<T> list = new ArrayList<T>();
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(str);
            // 直接从第三行开始获取数据
            int rowSize = sheet.getPhysicalNumberOfRows();
            int cellSize = sheet.getRow(0).getPhysicalNumberOfCells();
            if(rowSize > 1){
                for (int i = 1; i < rowSize; i++) {
                    T t = clazz.newInstance();
                    Row row = sheet.getRow(i);
                    for(int j=0; j<cellSize; j++){
                        Object cellValue = row.getCell(j);
                        org.apache.commons.beanutils.BeanUtils.copyProperty(t, beanPropertys[j], cellValue);
                    }
                    list.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
    /**
     * 通用的读取excel单元格的处理方法
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object result = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    //对日期进行判断和解析
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        double cellValue = cell.getNumericCellValue();
                        result = HSSFDateUtil.getJavaDate(cellValue);
                    }

                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * 提取公共的样式
     * @param workbook
     * @param fontSize
     * @return
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 创建一个字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }


    /**
     * 插入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    public static HSSFWorkbook exportExcelData2(HSSFWorkbook workbook, List<Map> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(j + 2);
            Map map = dataList.get(j);
            for(int k=0; k<beanPropertys.length; k++){
                HSSFCell cellData = rowData.createCell(k);
                if(k == 0){
                    cellData.setCellValue(j+1);
                }else if(k == 1){
                    String date = map.get(beanPropertys[k]).toString();
                    cellData.setCellValue(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8));
                }else if(k == 7){
                    if(map.get("paymentRatio").toString().equals("0") || map.get("paymentRatio").toString().equals("10")){
                        cellData.setCellValue("100%");
                    }else if(map.get("paymentRatio").toString().equals("5:5")){
                        cellData.setCellValue("50%");
                    }else if(map.get("paymentRatio").toString().equals("4:4:2")){
                        if(map.get(beanPropertys[5]).toString().equals("3")){
                            cellData.setCellValue("20%");
                        }else{
                            cellData.setCellValue("40%");
                        }
                    }
                }else if(k == 5){
                    if(map.get(beanPropertys[k]).toString().equals("1")){
                        cellData.setCellValue("第一次放款");
                    }else if(map.get(beanPropertys[k]).toString().equals("2")){
                        cellData.setCellValue("第二次放款");
                    }else if(map.get(beanPropertys[k]).toString().equals("3")){
                        cellData.setCellValue("第三次放款");
                    }else{
                        cellData.setCellValue("第一次放款");
                    }
                }else{
                    cellData.setCellValue(map.get(beanPropertys[k]).toString());
                }
                cellData.setCellStyle(styleData);
            }
        }
        HSSFRow rowData = sheet.createRow(dataList.size() + 2);
        HSSFCell cellData = rowData.createCell(0);
        cellData.setCellValue("制单人：");
        HSSFCell cellData1 = rowData.createCell(1);
        cellData1.setCellValue("张诗琪");
        HSSFCell cellData2 = rowData.createCell(2);
        cellData2.setCellValue("复核人：");
        HSSFCell cellData3 = rowData.createCell(3);
        cellData3.setCellValue("刘晓园");
        HSSFCell cellData5 = rowData.createCell(5);
        cellData5.setCellValue("审批人：");
        HSSFCell cellData6 = rowData.createCell(6);
        cellData6.setCellValue("柯可");
        return workbook;
    }

    //map导出excel
    public static HSSFWorkbook mapToExcel(HSSFWorkbook workbook, List<Map> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for(int i = 0; i < dataList.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i + 2);
            for(int j = 0; j < beanPropertys.length; j++) {
                Map map = dataList.get(i);
                Iterator it = map.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object value = entry.getValue();
                    if(beanPropertys[j] == entry.getKey() || beanPropertys[j].equals(entry.getKey())) {
                        HSSFCell dataCell = dataRow.createCell(j);
                        dataCell.setCellValue(value.toString());
                        dataCell.setCellStyle(styleData);
                        break;
                    }
                }

            }
        }
        return workbook;
    }


    /**
     * map导出excel
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    public static HSSFWorkbook mapToExcelDiy(HSSFWorkbook workbook, List<Map> dataList, List<String> beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for(int i = 0; i < dataList.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i + 2);
            for(int j = 0; j < beanPropertys.size(); j++) {
                Map map = dataList.get(i);
                Iterator it = map.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object value = entry.getValue();
                    if(beanPropertys.get(j) == entry.getKey() || beanPropertys.get(j).equals(entry.getKey())) {
                        HSSFCell dataCell = dataRow.createCell(j);
                        if(value ==null || "".equals(value)){
                            dataCell.setCellValue("");
                        }else{
                            dataCell.setCellValue(value.toString());
                        }
                        dataCell.setCellStyle(styleData);
                        break;
                    }
                }

            }
        }
        return workbook;
    }



    /**
     * 设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeSecondHeadDiy(HSSFWorkbook workbook, List<String> secondTitles){
        // 创建用户属性栏
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow rowField = sheet.createRow(1);
        HSSFCellStyle styleField = createStyle(workbook, (short)13);
        for (int i = 0; i < secondTitles.size(); i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles.get(i));
            cell.setCellStyle(styleField);
        }
        return workbook;
    }
}