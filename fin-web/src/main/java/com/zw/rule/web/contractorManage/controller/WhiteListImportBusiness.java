package com.zw.rule.web.contractorManage.controller;

import com.zw.base.util.DateUtils;
import com.zw.base.util.GeneratePrimaryKeyUtils;
import com.zw.base.util.RegexUtil;
import com.zw.base.util.StringUtils;
import com.zw.enums.WhiteContractStatusEnum;
import com.zw.enums.WhiteJobEnum;
import com.zw.enums.WhitePayTypeEnum;
import com.zw.rule.contractor.po.Contractor;
import com.zw.rule.contractor.po.WhiteList;
import com.zw.rule.contractor.service.ContractorService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 白名单导入
 * @author 陈清玉 create by 2018-06-19
 */
public class WhiteListImportBusiness {

  private  final String SUF = "xlsx";
    /**
     * 模板头
     */
    private  String [] headList ={"所属总包商名称","姓名","身份证","手机号","合同状态","合同开始日期","合同结束日期","职务","工种","发薪日","应发工资","工资发放形式","当地日最低工资" };
    /**
     * 是否有异常
     */
    private boolean isError = false;
    /**
     * 异常信息
     */
    private String  errorMsg = "";
    /**
     * 数据库存在的身份证列表
     */
    private List<String> cardList;

    private ContractorService contractorService;
    /**
     * 导入的Excel文件
     */
    private  MultipartFile file;
    /**
     * Excel文件Workbook
     */
    private Workbook workBook;

    public WhiteListImportBusiness(MultipartFile file,ContractorService contractorService){
        this.file = file;
        this.contractorService = contractorService;
    }

    public List<String>  importData() throws Exception {
        if (file == null) {
            return null;
        }
        initWorkBook();
       return returnErrorExcel();
    }

    /**
     * 初始化赋值workBook
     * @throws IOException 文件读取异常
     */
    private void initWorkBook() throws IOException {
        //查询出全部白名单身份证号
        cardList = contractorService.findALLCards();

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(SUF.equalsIgnoreCase(suffix)) {
            this.workBook  = new XSSFWorkbook(file.getInputStream());
        } else {
            this.workBook  = new HSSFWorkbook(file.getInputStream());
        }
    }

    /**
     * 返回出错的信息
     * @return  List<String> 出错的信息集合
      */
    private List<String> returnErrorExcel() throws Exception {
        //异常总数
        List<String> errors = new ArrayList<>();
        Sheet sheet = this.workBook.getSheetAt(0);
        int rowSize = sheet.getLastRowNum();
        for (int i = 1; i <= rowSize; i++) {
            errorMsg = "第" + i + "行：" ;
            Row row = sheet.getRow(i);
            isError = false;
            white = new WhiteList();
            white.setId(GeneratePrimaryKeyUtils.getUUIDKey());
            white.setWhiteStatus("1");
            for (int j = 0; j < headList.length; j++) {
                isError = checkValue(row,j);
                //如果有一个单元格出现异常就终止
                if(isError) {
                    errors.add(errorMsg);
                    break;
                }
            }
            if(!isError) {
                //保存数据
                contractorService.addWhiteList(white);
            }
        }
        return errors;
    }

    private WhiteList white = null;

    /**
     * 验证每一个单元格的数据
     * @param row 行
     * @param index 索引
     * @return 返回是否有异常 true 有 false 没有
     * @throws Exception 操作异常
     */
    private boolean checkValue(Row row,int index){
        String value = getStringCellValue(row.getCell(index));
        switch (index){
            //所属总包商名称
            case 0:
                if(StringUtils.isBlank(value)){
                    errorMsg +=  headList[index] + "不能为空";
                    isError  = true;
                }else{
                    Contractor contractor = contractorService.findByName(value);
                    if(contractor == null){
                        errorMsg += headList[index] + "为：《"+ value + "》在系统里面不存在，请先完善数据在进行添加";
                        isError  = true;
                    }else{
                        white.setContractorId(contractor.getId());
                    }
                }

                break;
            //姓名
            case 1:
                if(StringUtils.isBlank(value)){
                    errorMsg += headList[index] + "不能为空";
                    isError  = true;
                }
                white.setRealName(value);
                break;
            //身份证
            case 2:
                if(StringUtils.isBlank(value)){
                    errorMsg += headList[index] + "不能为空";
                    isError  = true;
                }else if(!RegexUtil.isIdentityCard(value)){
                    errorMsg += headList[index] + "必须15位数字或18位数字";
                    isError  = true;
                }else {
                    //如果用户导入的身份证在我们的库里面存在证明是重复数据
                    if(!CollectionUtils.isEmpty(cardList) && cardList.contains(value)) {
                        errorMsg += headList[index] + "为：《"+ value + "》的数据已经存在不能重复添加";
                        isError  = true;
                    }
                }
                white.setCard(value);
                break;
            //手机号
            case 3:
                if(StringUtils.isBlank(value)){
                    errorMsg += headList[index] + "不能为空";
                    isError  = true;
                }else if(!RegexUtil.isnNewMobile(value)){
                    errorMsg += headList[index] + "手机号格式不正确";
                    isError  = true;
                }
                white.setTelphone(value);
                break;
             //合同状态
            case 4:
                if(!StringUtils.isEmpty(value)) {
                    WhiteContractStatusEnum contractStatusEnum = WhiteContractStatusEnum.getByLabel(value);
                    if(contractStatusEnum != null) {
                        value = contractStatusEnum.getCode().toString();
                    }
                }
                white.setContractStatus(value);
                break;
            //合同开始日期
            case 5:
                white.setContractStartDate(value);
                break;
            //合同结束日期
            case 6:
                white.setContractEndDate(value);
                break;
            //职务
            case 7:
                if(!StringUtils.isEmpty(value)) {
                    WhiteJobEnum whiteJobEnum = WhiteJobEnum.getByLabel(value);
                    if(whiteJobEnum != null) {
                        value = whiteJobEnum.getCode().toString();
                    }
                }
                white.setJob(value);
                break;
            //工种
            case 8:
                white.setJobType(value);
                break;
            //发薪日
            case 9:
                white.setLatestPayday(value);
                break;
            //应发工资
            case 10:
                if(StringUtils.isBlank(value)){
                    errorMsg += headList[index] +  "不能为空,";
                    isError  = true;
                }else if(!RegexUtil.isNumber(value)){
                    errorMsg += headList[index] + "必须为数字";
                    isError  = true;
                }
                white.setLatestPay(value);
                break;
            //工资发放形式
            case 11:
                if(!StringUtils.isEmpty(value)) {
                    WhitePayTypeEnum typeEnum = WhitePayTypeEnum.getByLabel(value);
                    if(typeEnum != null) {
                        value = typeEnum.getCode().toString();
                    }
                }
                white.setPayType(value);
                break;
            //当地日最低工资
            case 12:
                white.setLocalMonthlyMinWage(value);
                break;
                default:
        }
        return isError;
    }

    /**
     * 获得单元格字符串
     *
     */
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String result;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    try {
                        result = DateUtils.formatDate(cell.getDateCellValue(),DateUtils.STYLE_3);
                    }catch (Exception e){
                        result = "";
                    }
                } else {
                    String strCell=NumberToTextConverter.toText(cell.getNumericCellValue());
                    if(!strCell.contains(".")) {
                        DecimalFormat df = new DecimalFormat("#");
                        strCell=df.format(cell.getNumericCellValue());
                    }
                    result =strCell;
                }
                break;
            case STRING:
                if (cell.getRichStringCellValue() == null) {
                    result = null;
                } else {
                    result = cell.getRichStringCellValue().getString();
                }
                break;
            case BLANK:
                result = null;
                break;
            case FORMULA:
                try {
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    result = cell.getRichStringCellValue().getString();
                }
                break;
            default:
                result = "";
        }

        if (result != null) {
            result = result.trim();
        }
        return result;
    }

}
