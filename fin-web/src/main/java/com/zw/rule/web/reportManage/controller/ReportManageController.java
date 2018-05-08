package com.zw.rule.web.reportManage.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.reportManage.Sorderdetail;
import com.zw.rule.reportManage.service.ReportManageService;
import com.zw.rule.service.SysDepartmentService;
import com.zw.rule.web.util.HttpUtil;
import com.zw.rule.web.util.PageConvert;
import com.zw.rule.web.util.PropertiesUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * Created by Administrator on 2018/3/5.
 */
@Controller
@RequestMapping("reportManage")
public class ReportManageController {
    @Resource
    private ReportManageService reportManageService;
    @Resource
    private SysDepartmentService sysDepartmentService;
    @Resource
    private OfficeClerkManageService officeClerkManageService;
    @GetMapping("getPage")
    public String getPage() {
        return "reportManage/reportManage";
    }


/***************************第三方调用情况的controller开始**/
    /**
     * 获取第三方调用情况页面
     *
     * @return
     */
    @GetMapping("getThirdpartyPage")
    public String getThirdpartyPage() {
        return "reportManage/thirdpartyPage";
    }

    /**
     * 获取第三方调取数据
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping("getThirdpartyData")
    @ResponseBody
    public Response getThirdpartyData(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        long account = (long) UserContextUtil.getUserId();

        String nowBranchId = reportManageService.getCompanyIdByUserId(String.valueOf(account));//当前系统用户机构id
        Branch branch = reportManageService.getBranchById(nowBranchId);//当前机构实体
        String nowCompanyId=officeClerkManageService.getGongSiName(branch).getId();//当前用户的公司id

        String companyId1 = nowCompanyId;//获取到的公司id
        String pname = sysDepartmentService.findById(Long.parseLong(companyId1)).getPname();//根据公司id获取公司父节点名字
        //判断是否为顶级目录
        if (pname == null || "".equals(pname)) {
            companyId1 = "null";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId1);
        map.put("startDate", queryFilter.getParam().get("beginTime"));
        map.put("endDate", queryFilter.getParam().get("endTime"));
        PropertiesUtil prop = new PropertiesUtil("properties/host.properties");
        String ruleUrlSP = prop.get("ruleUrlSP");//秒付金服规则引擎-地址(商品贷)
        String url = ruleUrlSP + "/szt/requestLog/getReportData";
        try {
            String result = HttpUtil.doPost(url, map);
            Map<String, String> mapJson = (Map) JSONObject.parse(result);
            List<Map> listData = (List<Map>) JSONObject.parse(mapJson.get("data"));//解析之后的data具体数据
            List<SysDepartment> returnDate = new ArrayList<SysDepartment>();
            for (int i = 0; i < listData.size(); i++) {
                Map<String, String> oneData = (Map) listData.get(i);
                String companyId = oneData.get("companyId");
                SysDepartment sysDepartment=new SysDepartment();
                if(companyId !=null){
                    sysDepartment = sysDepartmentService.findById(Long.parseLong(companyId));//
                    if(sysDepartment !=null){
                        sysDepartment.setTotalNum(String.valueOf(oneData.get("totalNum")));//调取次数
                        sysDepartment.setReqFirm(String.valueOf(oneData.get("reqFirm")));//机构名称
                        returnDate.add(sysDepartment);
                    }
                }
            }
            //权限处理
//            String nowBranchId = reportManageService.getCompanyIdByUserId(String.valueOf(account));//当前系统用户机构id
//            Branch branch = reportManageService.getBranchById(nowBranchId);//当前机构实体
//            String nowCompanyId=officeClerkManageService.getGongSiName(branch).getId();//当前用户的公司id
            String topId =reportManageService.getMostParsentId();//获取总公司id
            List<String> secondIds = reportManageService.getSecondIds(topId);//二级节点id集合
            //如果当前用户的公司id和总公司节点的id相等
            if(nowCompanyId.equals(topId)){
                //不对返回结果集做处理
            }
            else {//只能看到自己的
                for(int j=0;j<returnDate.size();j++){
                    if( !nowCompanyId.equals((returnDate.get(j).getId()+"")) ){
                        returnDate.remove(j);
                    }
                }
            }
            PageInfo pageInfo = new PageInfo(returnDate);
            return new Response(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 渠道调用统计页面
     *
     * @return
     */
    @GetMapping("getNumberOfChannelPage")
    public String getNumberOfChannelPage() {
        return "reportManage/numberOfChannel";
    }

    /**
     * 渠道调用明细页面加载获取数据controller
     * @param queryFilter
     * @return
     */
    @RequestMapping("getNumberData")
    @ResponseBody
    public Response getData(@RequestBody ParamFilter queryFilter) {
        long account = (long) UserContextUtil.getUserId();//当前用户id
        String nowBranchId = reportManageService.getCompanyIdByUserId(String.valueOf(account));//当前系统用户机构id
        Branch branch = reportManageService.getBranchById(nowBranchId);//当前机构实体
        String nowCompanyName=officeClerkManageService.getGongSiName(branch).getDeptName();//当前用户的公司名称
        String nowCompanyId=officeClerkManageService.getGongSiName(branch).getId();//当前用户的公司id
        Map map=queryFilter.getParam();
        map.put("nowCompanyName",nowCompanyName);
        map.put("nowCompanyId",nowCompanyId);
        List <Map> list=reportManageService.getNowCompanyOrderCount(map);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 跳转代扣统计页面
     * @return
     */
    @GetMapping("toTotalOfDeductingPage")
    public String toTotalOfDeductingPage(){
        return "reportManage/TotalOfDeducting";
    }

    @RequestMapping("getTotalOfDeductingList")
    @ResponseBody
    public Response getTotalOfDeductingList(@RequestBody ParamFilter queryFilter){
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());

        long account = (long) UserContextUtil.getUserId();//当前用户id
        String nowBranchId = reportManageService.getCompanyIdByUserId(String.valueOf(account));//当前系统用户机构id
        Branch branch = reportManageService.getBranchById(nowBranchId);//当前机构实体
        String nowCompanyName=officeClerkManageService.getGongSiName(branch).getDeptName();//当前用户的公司名称
        String nowCompanyId=officeClerkManageService.getGongSiName(branch).getId();//当前用户的公司id
        Map m =  queryFilter.getParam();
        m.put("nowCompanyName",nowCompanyName);
        m.put("nowCompanyId",nowCompanyId);
        List<Map> list = reportManageService.getNumOfDeducting(m);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
/**************************第三方调用情况的controller结束**/


    /**
     * 销售明细报表控制层
     * @param queryFilter
     * @return
     */

    @RequestMapping("getReportAll")
    @ResponseBody
    public Response getReportAll(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Map> list = reportManageService.getReportAllToExcel(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    /**
     * 销售明细报表导出到excel中
     *
     * @param response
     * @param salesOrder
     * @return
     */
    @RequestMapping("ToExcel")
    @ResponseBody
    public Response exportToExcel(HttpServletResponse response, String salesOrder) {
        Map map = JSONObject.parseObject(salesOrder);//json字符串转为json对象
        //查询结果返回List<Map<String,String>>
        List<Map> mapp = reportManageService.getReportAllToExcel(map);
        DecimalFormat df = new DecimalFormat("#.00");
        //处理返回结果集
        if (null != mapp && mapp.size() > 0) {
        }
        //前台传过来的字段集合(要作为报表字段的集合，这些的字段是固定的死的)
        List fields = (List) map.get("list");
        List<String> beanProperty = new ArrayList<String>();
        beanProperty.add("一级渠道");
        beanProperty.add("二级渠道");
        beanProperty.add("区域");
        beanProperty.add("商户名称");
        beanProperty.add("订单编号");
        beanProperty.add("姓名");
        beanProperty.add("手机号");
        beanProperty.add("身份证号");
        beanProperty.add("商品名称");
        List<String> beanProperty2 = new ArrayList<String>();
        beanProperty2.add("province");
        beanProperty2.add("city");
        beanProperty2.add("district");
        beanProperty2.add("merchantName");
        beanProperty2.add("orderNo");
        beanProperty2.add("customerName");
        beanProperty2.add("customerTel");
        beanProperty2.add("card");
        beanProperty2.add("merchantdiseName");
        //动态添加要显示的字段
        for (Object field : fields) {
            if ("predictPrice".equals(field.toString())) {
                beanProperty.add("首付金额");
                beanProperty2.add("predictPrice");
            } else if ("salesmanName".equals(field.toString())) {
                beanProperty.add("办单员");
                beanProperty2.add("salesmanName");
            } else if ("salesmanTel".equals(field.toString())) {
                beanProperty.add("办单员手机号");
                beanProperty2.add("salesmanTel");
            } else if ("yhze".equals(field.toString())) {
                beanProperty.add("应还总额");
                beanProperty2.add("yhze");
            } else if ("orderCapital".equals(field.toString())) {
                beanProperty.add("订单本金");
                beanProperty2.add("orderCapital");
            } else if ("yufukuan".equals(field.toString())) {
                beanProperty.add("预付款");
                beanProperty2.add("yufukuan");
            } else if ("payCount".equals(field.toString())) {
                beanProperty.add("期数");
                beanProperty2.add("payCount");
            } else if ("sureOrderTime".equals(field.toString())) {
                beanProperty.add("确认订单时间");
                beanProperty2.add("sureOrderTime");
            } else if ("orderState".equals(field.toString())) {
                beanProperty.add("订单状态");
                beanProperty2.add("orderState");
            } else if ("yufukuanState".equals(field.toString())) {
                beanProperty.add("预付款状态");
                beanProperty2.add("yufukuanState");
            } else if ("deliveryState".equals(field.toString())) {
                beanProperty.add("发货状态");
                beanProperty2.add("deliveryState");
            } else if ("loanState".equals(field.toString())) {
                beanProperty.add("放款状态");
                beanProperty2.add("loanState");
            } else if ("jsState".equals(field.toString())) {
                beanProperty.add("结算状态");
                beanProperty2.add("jsState");
            } else if ("fahuoTime".equals(field.toString())) {
                beanProperty.add("发货时间");
                beanProperty2.add("fahuoTime");
            } else if ("monthRate".equals(field.toString())) {
                beanProperty.add("月息");
                beanProperty2.add("monthRate");
            } else if ("dayForDueDate".equals(field.toString())) {
                beanProperty.add("离还款日天数");
                beanProperty2.add("dayForDueDate");
            } else if ("businessType".equals(field.toString())) {
                beanProperty.add("行业类型");
                beanProperty2.add("businessType");
            }
        }
        //设置标题
        HSSFWorkbook workbook = ExcelUtil.makeExcelHead("销售订单明细报表", beanProperty.size()-1);
        workbook = ExcelUtil.makeSecondHeadDiy(workbook, beanProperty);//设置二级标题
        workbook = ExcelUtil.mapToExcelDiy(workbook, mapp, beanProperty2);//插入具体数据
        try {
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=salesOrder.xls");
            response.setContentType("application/x-download");
            workbook.write(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response();
    }


    /**
     * 逾期报表导出到excel中
     *
     * @param response
     * @param salesOrder
     * @return
     */
    @RequestMapping("yuqiToExcel")
    @ResponseBody
    public Response exportYuqiToExcel(HttpServletResponse response, String salesOrder) {
        Map map = JSONObject.parseObject(salesOrder);//json字符串转为json对象
        //查询结果返回List<Map<String,String>>
        List<Map> mapp = reportManageService.getYuQiReportToExcel(map);
        //处理返回结果集
        if (null != mapp && mapp.size() > 0) {
        }

        //前台传过来的字段集合(要作为报表字段的集合，这些的字段是固定的死的)
        List fields = (List) map.get("list");
        List<String> beanProperty = new ArrayList<String>();
        beanProperty.add("渠道编号");
        beanProperty.add("一级渠道");
        beanProperty.add("二级渠道");
        beanProperty.add("区域");
        beanProperty.add("商户编号");
        beanProperty.add("商户名称");
        beanProperty.add("订单编号");
        beanProperty.add("订单日期");
        beanProperty.add("客户姓名");
        beanProperty.add("身份证号");
        beanProperty.add("手机号");
        beanProperty.add("购买商品");

        List<String> beanProperty2 = new ArrayList<String>();
        beanProperty2.add("--");//渠道编号
        beanProperty2.add("province");//一级渠道
        beanProperty2.add("city");//二级渠道
        beanProperty2.add("district");//区域
        beanProperty2.add("merchant_id");//商户编号
        beanProperty2.add("merchantName");//商户名称
        beanProperty2.add("orderNo");//订单编号
        beanProperty2.add("orderDate");//订单日期
        beanProperty2.add("customerName");//客户姓名
        beanProperty2.add("card");//身份证号
        beanProperty2.add("customerTel");//手机号
        beanProperty2.add("merchantdiseName");//购买商品
        //动态添加要显示的字段
        for (Object field : fields) {
            if ("applayMoney".equals(field.toString())) {
                beanProperty.add("申请金额");
                beanProperty2.add("applayMoney");
            } else if ("orderTotalMoney".equals(field.toString())) {
                beanProperty.add("订单总金额");
                beanProperty2.add("orderTotalMoney");
            } else if ("syyhzje".equals(field.toString())) {
                beanProperty.add("剩余应还总金额");
                beanProperty2.add("syyhzje");
            } else if ("yuqiDays".equals(field.toString())) {
                beanProperty.add("逾期天数");
                beanProperty2.add("yuqiDays");
            } else if ("yqfx".equals(field.toString())) {
                beanProperty.add("逾期罚息");
                beanProperty2.add("yqfx");
            } else if ("yhbx".equals(field.toString())) {
                beanProperty.add("应还本息");
                beanProperty2.add("yhbx");
            } else if ("payCount".equals(field.toString())) {
                beanProperty.add("分期数");
                beanProperty2.add("payCount");
            } else if ("pay_time".equals(field.toString())) {
                beanProperty.add("应还日期");
                beanProperty2.add("pay_time");
            } else if ("dqyhzje".equals(field.toString())) {
                beanProperty.add("到期应还总金额");
                beanProperty2.add("dqyhzje");
            } else if ("salesman".equals(field.toString())) {
                beanProperty.add("办单员");
                beanProperty2.add("salesman");
            } else if ("hkqs".equals(field.toString())) {
                beanProperty.add("还款期数");
                beanProperty2.add("hkqs");
            } else if ("lsyq".equals(field.toString())) {
                beanProperty.add("历史逾期");
                beanProperty2.add("lsyq");
            } else if ("hjszd".equals(field.toString())) {
                beanProperty.add("户籍所在地");
                beanProperty2.add("hjszd");
            } else if ("provinceWork".equals(field.toString())) {
                beanProperty.add("省份");
                beanProperty2.add("provinceWork");
            } else if ("cityWork".equals(field.toString())) {
                beanProperty.add("城市");
                beanProperty2.add("cityWork");
            } else if ("nowaddress".equals(field.toString())) {
                beanProperty.add("现居住地址");
                beanProperty2.add("nowaddress");
            } else if ("companyName".equals(field.toString())) {
                beanProperty.add("工作单位");
                beanProperty2.add("companyName");
            } else if ("companyPhone".equals(field.toString())) {
                beanProperty.add("电话");
                beanProperty2.add("companyPhone");
            }
        }
        //设置标题
        HSSFWorkbook workbook = ExcelUtil.makeExcelHead("逾期报表", beanProperty.size()-1);
        workbook = ExcelUtil.makeSecondHeadDiy(workbook, beanProperty);//设置二级标题
        workbook = ExcelUtil.mapToExcelDiy(workbook, mapp, beanProperty2);//插入具体数据
        try {
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=逾期报表"+ DateUtils.getCurrentTime()+".xls");
            response.setContentType("application/x-download");
            workbook.write(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response();
    }


    //更改时间格式
    public String formatTimer(String time) {
        if (null != time && !"".equals(time)) {
            StringBuffer sb = new StringBuffer(time);
            sb.insert(4, "-").insert(7, "-");
            time = sb.toString();
        }
        return time;
    }

    public int toInts(String s) {
        int i = 0;
        if (null != s && !"".equals(s)) {
            i = Integer.parseInt(s);
        }
        return i;
    }

    /**
     * 逾期报表界面跳转
     * @return
     */
    @GetMapping("getYuQiReportPage")
    public String getYuQiReportPage() {
        return "reportManage/yuqiReport";
    }

    /**
     * 加载所有逾期报表的控制层
     * @param queryFilter
     * @return
     */
    @RequestMapping("getYuQiReports")
    @ResponseBody
    public Response getYuQiReports(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<Map> list = reportManageService.getYuQiReportToExcel(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }
}
