package com.zw.rule.web.contract.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.zw.base.util.DateUtils;
import com.zw.rule.contract.ContractService;
import com.zw.rule.contract.po.Contract;
import com.zw.rule.contract.po.MagOrderContract;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.service.DictService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.PageConvert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.github.pagehelper.PageHelper.startPage;

/**
 * Created by Administrator on 2017/6/27.
 */
@Controller
@RequestMapping("contract")
public class ContractController {

    @Value("${contract.host}")
    private String contractHost;

    @Resource
    private ContractService contractService;

    @Resource
    private DictService dictService;

    @GetMapping("config")//合同
    public String list() {
        return "contractOrAgreeMent/magTemplate";
    }

    @GetMapping("searchList")//已签订查询
    public String orderList() {
        return "contractOrAgreeMent/magOrderList";
    }

    /**
     * 上传合同
     *
     * @return
     * @throws Exception
     */
//    @PostMapping("uploadContract")
//    @ResponseBody
//    public Response uploadFile(HttpServletRequest request) throws Exception{
//        Response response = new Response();
//        List list = contractService.uploadContractFile(request);
//        if(!list.isEmpty()){
//            response.setMsg("上传成功！");
//            response.setData(list.get(0));
//        }else{
//            response.setCode(1);
//            response.setMsg("上传失败！");
//        }
//        return response;
//    }
    @PostMapping("orderList")
    @ResponseBody
    @WebLogger("已签订合同/协议查询")
    public Response orderList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = contractService.getOrders(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("getContractByOrderId")
    @ResponseBody
    @WebLogger("显示下载合同列表")
    public Response getContractByOrderId(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        PageHelper.startPage(pageNo, queryFilter.getPage().getPageSize());
        List<MagOrderContract> list = contractService.getContractByOrderId(queryFilter.getParam());
        for(MagOrderContract contract:list){
            contract.setContract_src(contractHost+contract.getContract_src());
        }
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("getOrdersList")
    @ResponseBody
    @WebLogger("查询客户终审订单列表")
    public Response getOrdersList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = contractService.getOrdersList(queryFilter);
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("list")
    @ResponseBody
    @WebLogger("查询模板列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = contractService.searchContract(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }


    @PostMapping("deleteContract")
    @ResponseBody
    @WebLogger("删除合同")
    public Response deleteContract(@RequestBody Map map) {
        Response response = new Response();
        int count = contractService.deleteContract(map.get("id").toString());
        if (count > 0) {
            response.setMsg("删除成功！");
        } else {
            response.setMsg("删除失败！");
        }
        return response;
    }

    @PostMapping("updateState")
    @ResponseBody
    @WebLogger("修改状态")
    public Response updateState(@RequestBody Map map) {
        Response response = new Response();
        int count = contractService.updateState(map);
        if (count > 0) {
            response.setMsg("修改成功！");
        } else {
            response.setMsg("修改失败！");
        }
        return response;
    }

    @PostMapping("add")
    @ResponseBody
    @WebLogger("添加合同")
    public Response addContract(@RequestBody Contract contract) {
        Response response = new Response();
        contract.setCreateTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        contract.setId(UUID.randomUUID().toString());
        int count = contractService.addContract(contract);
        if (count > 0) {
            response.setMsg("添加成功！");
        } else {
            response.setMsg("添加失败！");
        }
        return response;
    }

    @PostMapping("signContract")
    @ResponseBody
    @WebLogger("签订合同")
    public Response signContract(@RequestBody String orderId) {
        Response response = new Response();
        if (contractService.signContract(orderId)) {
            response.setMsg("签订成功！");
        } else {
            response.setMsg("签订失败！");
        }
        return response;
    }

    @PostMapping("config")
    @ResponseBody
    @WebLogger("模板列表")
    public Response configList(@RequestBody ParamFilter queryFilter) {
        int pageNo = PageConvert.convert(queryFilter.getPage().getFirstIndex(), queryFilter.getPage().getPageSize());
        startPage(pageNo, queryFilter.getPage().getPageSize());
        List list = contractService.searchContract(queryFilter.getParam());
        PageInfo pageInfo = new PageInfo(list);
        return new Response(pageInfo);
    }

    @PostMapping("apendSelect")
    @ResponseBody
    @WebLogger("动态加载问题分类到下拉框")
    public Response apendSelect() {
        Response response = new Response();
        List list = dictService.getDetailList("模板类型");
        if (list != null && list.size() > 0) {
            response.setData(list);
        } else {
            return null;
        }
        return response;
    }

    @PostMapping("update")
    @ResponseBody
    @WebLogger("修改模板")
    public Response updateContract(@RequestBody Contract contract) {
        Response response = new Response();
        contract.setAlterTime(DateUtils.getCurrentTime(DateUtils.STYLE_10));
        int count = contractService.updateContract(contract);
        if (count > 0) {
            response.setMsg("配置成功！");
        } else {
            response.setMsg("配置失败！");
        }
        return response;
    }

    @PostMapping("getById")
    @ResponseBody
    @WebLogger("根据id获取详情")
    public Response getById(@RequestBody Map param) {
        Response response = new Response();
        Contract contract = contractService.getById(param.get("id").toString());
        if (contract != null) {
            response.setData(contract);
        } else {
            return null;
        }
        return response;
    }

    @ResponseBody
    @PostMapping("downLoad")
    public Response upload() throws Exception {
        Response response = new Response();
        String src = dictService.getDetilNameByCode("contract_src", dictService.getListByCode("host").get(0).getId().toString());
        response.setData(src);
        return response;
    }



    //获取协议模板的内容
    @GetMapping("getContactTemplate")
    @ResponseBody
    @WebLogger("获取协议模板")
    public Response getContactTemplate(Map resultmap) throws Exception {
        //测试代码
        String template_type = "6";
        String product_id = "89d6d61b-3599-42fd-8dcf-3b21ced1a3c4";
        String phone = "18605540686";
        String type = (String) resultmap.get("type");//1,不带标签的内容，2带标签的内容
        resultmap.put("template_type", template_type);
        resultmap.put("product_id", product_id);
        resultmap.put("tel", phone);
        Response response = new Response();
        Map map = contractService.getContactTemplate(resultmap);
        String context = null;
        if (map != null && map.size() > 0) {
            if(type.equals("2")){
                context = (String) map.get("content");
            }else if (type.equals("1")){
                context = (String) map.get("content_no_bq");
            }
            if (map.get("template_type").equals("3")) { //“3”借款协议
                ;//协议内容
                //   String product_id = map.get("product_id").toString();
                String borrow_card = (String) map.get("card");//身份证
                String borrow_address = (String) map.get("card_register_address");//地址
                String name = (String) map.get("PERSON_NAME");//申请人
                String bank = (String) map.get("bank");//开户行
                String account = (String) map.get("account");//账号
                String borrow_money = (String) map.get("loan_amount");//贷款本金数额
                double benjin = Double.valueOf(borrow_money).doubleValue();
                String periods = (String) map.get("product_periods");//产品期数
                Integer periods1 = Integer.valueOf(periods);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String begin_time = (String) map.get("start_time");//合同签订时间
                String s_year = begin_time.substring(0, 4);
                String s_month = begin_time.substring(4, 6);
                String s_day = begin_time.substring(6, 8);
                Date times = sdf.parse(begin_time);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(times);
                String time1 = sdf.format(calendar.getTime());
                calendar.add(Calendar.MONTH, periods1);
                String endTime = sdf.format(calendar.getTime());//截止时间
                String e_year = endTime.substring(0, 4);
                String e_month = endTime.substring(4, 6);
                String e_day = endTime.substring(6, 8);
                //  SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");//时间转换为年月日格式
                Date date = sdf.parse(endTime);
                Date mydate = sdf.parse(begin_time);
                long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000); //借款天数
                String borrow_date = String.valueOf(day);//借款时间
                String balance = (String) map.get("li_xi");//利息
                double lixi = Double.valueOf(balance).doubleValue();
                double lixi1 = (benjin) * (lixi / 100) * (day);
                String pay_service_fee = (String) map.get("zhifu_fee");//支付服务费
                double zhifu = Double.valueOf(pay_service_fee).doubleValue();
                double zhifu1 = (benjin) * (zhifu / 100);
                String risk_assessment_fee = (String) map.get("fengxian_fee"); //风险评估费
                double fengxian = Double.valueOf(risk_assessment_fee).doubleValue();
                double fengxian1 = (benjin) * (fengxian / 100);
                String judge_service_fee = (String) map.get("shenhe_fee");//审核服务费
                double shenhe = Double.valueOf(judge_service_fee).doubleValue();
                double shenhe1 = (benjin) * (shenhe / 100);
                String account_manage_fee = (String) map.get("zhanghu_fee");//账户管理费
                double zhanghu = Double.valueOf(account_manage_fee).doubleValue();
                double zhanghu1 = (benjin) * (zhanghu / 100);
                /*    String zhina_fee=(String)map.get("zhina_fee");
                    double  zhina=Double.valueOf(zhina_fee).doubleValue();*/
                double zhina = 20;
                String borrow_all_money = (benjin + lixi1 + zhifu1 + fengxian1 + shenhe1 + zhanghu1 + zhina) + ""; //总金额
                String time = (String) map.get("create_time");//协议签订时间（20170926183434）
                String z_year = time.substring(0, 4);
                String z_month = time.substring(4, 6);
                String z_day = time.substring(6, 8);
                context.replace("$borrow_money", borrow_money);//贷款本金数额
                context.replace("$borrow_day", borrow_date);//借款时间
                context.replace("$s_year", s_year); //合同签订年、月、日
                context.replace("$s_month", s_month);
                context.replace("$s_date", s_day);
                context.replace("$e_year", e_year);//借款截止年、月、日
                context.replace("$e_month", e_month);
                context.replace("$e_date", e_day);
                context.replace("$borrow_all_money", borrow_all_money);//总金额
                context.replace("$balance", balance);//利息
                context.replace("$pay_service_fee", pay_service_fee);//支付服务费
                context.replace("$danger_alance_fee", risk_assessment_fee);//风险评估费
                context.replace("$judge_service_fee", judge_service_fee);//审核服务费
                context.replace("$account_manage_fee", account_manage_fee);//账户管理费
                context.replace("$borrow_user", name);//申请人
                context.replace("$borrow_card", borrow_card);//身份证
                context.replace("$borrow_bank", bank);//开户行
                context.replace("$bank_account", account);//账号
                context.replace("$z_year", z_year);
                context.replace("$z_month", z_month);
                context.replace("$z_day", z_day);
                context.replace("$borrow_address", borrow_address);//地址
            } else if (map.get("template_type").equals("2")) { //"2"服务协议
                ;
            } else if (map.get("template_type").equals("4")) { //"4"自动扣款协议
                ;
                String name = (String) map.get("PERSON_NAME");//申请人
                String borrow_card = (String) map.get("card");//身份证
                String time = (String) map.get("create_time");//协议签订时间（20170926183434）
                String z_year = time.substring(0, 4);
                String z_month = time.substring(4, 6);
                String z_day = time.substring(6, 8);
                String bank = (String) map.get("bank");//开户行
                String account_name = (String) map.get("account_name");//账户名称
                String account = (String) map.get("account");//账号
                context.replace("$borrow_card", borrow_card);
                context.replace("$borrow_bank", bank);//开户行
                context.replace("$account_name", account_name);//账户名称
                context.replace("$bank_account", account);//账号
                context.replace("$borrow_user", name);//申请人
                context.replace("$z_year", z_year);
                context.replace("$z_month", z_month);
                context.replace("$z_day", z_day);
            } else if (map.get("template_type").equals("5")) {//"5"用户信息授权及使用协议
                ;
                String name = (String) map.get("PERSON_NAME");//申请人
                String time = (String) map.get("create_time");//协议签订时间（20170926183434）
                String z_year = time.substring(0, 4);
                String z_month = time.substring(4, 6);
                String z_day = time.substring(6, 8);
                context.replace("$borrow_user", name);//申请人
                context.replace("$z_year", z_year);
                context.replace("$z_month", z_month);
                context.replace("$z_day", z_day);
            } else if (map.get("template_type").equals("6")) {//"6"信用查询协议
                ;
                String name = (String) map.get("PERSON_NAME");//申请人
                String borrow_card = (String) map.get("card");//身份证
                String time = (String) map.get("create_time");//协议签订时间（20170926183434）
                String z_year = time.substring(0, 4);
                String z_month = time.substring(4, 6);
                String z_day = time.substring(6, 8);
                context.replace("$borrow_user", name);//申请人
                context.replace("$borrow_card", borrow_card);//身份证
                context.replace("$z_year", z_year);
                context.replace("$z_month", z_month);
                context.replace("$z_day", z_day);
            }
        }
        if (context != null) {
            response.setMsg("获取成功！");
            resultmap.put("context", context);
            response.setData(map);
        } else {
            response.setMsg("获取失败！");
        }
        return response;
    }



    @ResponseBody
    @PostMapping("insertPDF")
    public void insertPDF(Map map) throws Exception{

        Map map1=new HashMap();
        // 1.新建document对象
        Document document = new Document();
        String context="";//协议模板
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream((File) map.get("file"));
            // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
            // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            // 3.打开文档
            document.open();
            context=String.valueOf( map.get("context"));
            BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese);
            // 4.添加一个内容段落
            document.add(new Paragraph(context,font));
            // 5.关闭文档
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(fos!=null){
                fos.close();
            }
        }
    }

    public Map  createPackage(Map map) throws Exception {

        //协议模板所需变量
        String tel="";//手机号
        String order_no="";//合同号
        String suffix="";//后缀
        String card_no="";//银行卡号
        String date="";//用户注册日期
        String fileName="";//文件名称
        String creat_time="";//银行卡绑定时间
        String begin_time="";//合同创建时间
        String order_tel="";//订单手机号


        File file=null;
        if(map!=null){
            tel=String.valueOf(map.get("tel")) ;
            order_no=String.valueOf(map.get("order_no"));
            suffix=String.valueOf(map.get("suffix"));
            card_no=String.valueOf(map.get("card_no"));
            date=String.valueOf(map.get("user_time"));
            creat_time=String.valueOf(map.get("creat_time"));
            begin_time=String.valueOf(map.get("begin_time"));
            order_tel=String.valueOf(map.get("order_tel"));
        }
        File file1=null;
        String src="C:/Users/HP/Desktop/";
        String s_year = date.substring(0, 4);
        String s_month = date.substring(4, 6);
        String s_day = date.substring(6, 8);
        String dateSfm=s_year+"年"+s_month+"月"+s_day+"日";
        String filePackage="";//文件夹名称
        filePackage=dateSfm+"/"+tel+"/协议/";
        if(suffix!=null&&"_FWXY".equals(suffix)){
            file=new File(src+filePackage);
            if(!file.exists()){
                file.mkdirs();
            }
            fileName=suffix+".pdf";
            file1=new File(file,fileName);
        }
        if(suffix!=null&&"_KKXY".equals(suffix)){
           s_year = creat_time.substring(0, 4);
            s_month = creat_time.substring(4, 6);
            s_day = creat_time.substring(6, 8);
            String creat_date=s_year+"年"+s_month+"月"+s_day+"日";
            file=new File(src+filePackage);
            if(!file.exists()){
                file.mkdirs();
            }
            fileName=card_no+"_"+creat_date+suffix+".pdf";
            file1=new File(file,fileName);
        }
        if(suffix!=null&&"_SQXY".equals(suffix)){
            file=new File(src+filePackage);
            if(!file.exists()){
                file.mkdirs();
            }
            fileName=suffix+".pdf";
            file1=new File(file,fileName);
        }
        if(suffix!=null&&"_JKXY".equals(suffix)){
            filePackage=dateSfm+"/"+"合同";
            file=new File(src+filePackage);
            s_year = begin_time.substring(0, 4);
            s_month = begin_time.substring(4, 6);
            s_day = begin_time.substring(6, 8);
            String begin_date=s_year+"年"+s_month+"月"+s_day+"日";
            if(!file.exists()){
                file.mkdirs();
            }
            fileName=order_no+"_"+order_tel+"_"+begin_date+suffix+".pdf";
            file1=new File(file,fileName);
        }
        Map map1=new HashMap();
        map.put("file",file1);
        insertPDF(map);

        map1.put("fileName",file1.getName());
        map1.put("fileSrc",file1.getAbsolutePath());
        return map1;
    }


    //获取协议模板的内容

    @RequestMapping("getContractTemplatePdf")
    @ResponseBody
    public Map changeContactTemplate(String user_id,String template_type,String bank_id,String order_id) throws Exception {

        Map returnMap=new HashMap();
        Map map=new HashMap();

        Map map1=contractService.getContactTemplate(template_type);
        Map map2=contractService.getProductFree(order_id);
        Map map3=contractService.getBankInfo(bank_id);
        Map map4=contractService.getOrderInfo(order_id);
        Map map5=contractService.getRegistAddress(order_id);
        Map map6=contractService.getUserInfo(user_id);

        String context="";//协议模板
        String template_name="";//协议模板名
        String borrow_card = "";//身份证
        String borrow_address = "";//地址
        String name = "";//申请人
        String bank = "";//开户行
        String card_name="";//账户名
        String account ="";//账号
        String borrow_money = "";//贷款本金数额
        String contract_amount="";//合同总价
        String order_no="";//合同号
        String periods = "";//产品期数
        String begin_time = "";//合同签订时间
        String repay_date="";//还款日期
        String balance = "";//利息
        String pay_service_fee = "";//支付服务费
        String risk_assessment_fee = ""; //风险评估费
        String judge_service_fee = "";//审核服务费
        String account_manage_fee = "";//账户管理费
        String  card_no="";//绑卡的身份证
        String create_time="";//绑卡时间
        String tel="";//用户手机号
        String order_tel="";//订单手机号
        String realName="";//用户姓名
        String user_time="";//用户注册时间
        String s_year ="";
        String s_month ="";
        String s_day = "";
        String e_year ="";
        String e_month ="";
        String e_day = "";

        if(map1!=null){
            context=String.valueOf(map1.get("content_no_bq"));
            template_name=String.valueOf(map1.get("name"));
            map.put("template_name",template_name);
        }
        if(map2!=null){
            balance =String.valueOf(map2.get("li_xi"));//利息
            pay_service_fee = String.valueOf( map2.get("zhifu_fee"));//支付服务费
            risk_assessment_fee = String.valueOf( map2.get("fengxian_fee")); //风险评估费
            judge_service_fee = String.valueOf(map2.get("shenhe_fee"));//审核服务费
            account_manage_fee = String.valueOf( map2.get("zhanghu_fee"));//账户管理费

        }
        if(map3!=null){
            account=String.valueOf(map3.get("bank_no"));
            bank=String.valueOf(map3.get("bank_attribution"));
            card_no=String.valueOf(map3.get("card_no"));
            card_name=String.valueOf(map3.get("card_name"));
            create_time=String.valueOf(map3.get("create_time"));
        }
        if(map4!=null){
            borrow_money = String.valueOf( map4.get("applay_money"));//贷款本金数额
            order_tel = String.valueOf(map4.get("tel"));//订单手机号
            begin_time = String.valueOf(map4.get("creat_time"));//合同签订时间..
            repay_date=String.valueOf(map4.get("repay_date"));//还款时间
            periods=String.valueOf(map4.get("periods"));//贷款期限
            borrow_card=String.valueOf(map4.get("card"));//身份证号码
            name=String.valueOf(map4.get("customer_name"));
            order_no=String.valueOf(map4.get("order_no"));//合同号
            s_year = begin_time.substring(0, 4);
            s_month = begin_time.substring(4, 6);
            s_day = begin_time.substring(6, 8);
            e_year = repay_date.substring(0, 4);
            e_month = repay_date.substring(4, 6);
            e_day = repay_date.substring(6, 8);
            map.put("order_no",order_no);
            //map.put("cardNo",borrow_card);
        }
        if(map5!=null){
            borrow_address=String.valueOf(map5.get("card_register_address"));
        }
        if(map6!=null){
            tel=String.valueOf(map6.get("tel"));
            realName=String.valueOf(map6.get("person_name"));
            borrow_card=String.valueOf(map6.get("card"));
            user_time=String.valueOf(map6.get("creat_time"));
            map.put("tel",tel);
        }

        if("0".equals(template_type)){//借款协议
            context=context.replace("$borrow_day",periods);//贷款期限
            context=context.replace("$borrow_money", borrow_money);//贷款本金数额
            context=context.replace("$s_year", s_year); //合同签订年、月、日
            context=context.replace("$s_month", s_month);
            context=context.replace("$s_date", s_day);
            context=context.replace("$e_year", e_year);//借款截止年、月、日
            context=context.replace("$e_month", e_month);
            context=context.replace("$e_date", e_day);
            context=context.replace("$borrow_all_money", contract_amount);//还款总金额
            context=context.replace("$balance", balance);//利息
            context=context.replace("$pay_service_fee", pay_service_fee);//支付服务费
            context=context.replace("$danger_alance_fee", risk_assessment_fee);//风险评估费
            context=context.replace("$judge_service_fee", judge_service_fee);//审核服务费
            context=context.replace("$account_manage_fee", account_manage_fee);//账户管理费
            context=context.replace("$borrow_user", name);//申请人
            context=context.replace("$borrow_card", borrow_card);//身份证
            context=context.replace("$borrow_bank", bank);//开户行
            context=context.replace("$bank_account", account);//账号
            context=context.replace("$borrow_address", borrow_address);//地址
            context=context.replace("$lt","<");
            context=context.replace("$gt",">");
            map.put("order_tel",order_tel);
            map.put("user_time",user_time);
            map.put("begin_time",begin_time);
            map.put("suffix","_JKXY");
            map.put("context",context);
        }else if("3".equals(template_type)){//绑卡协议/扣款协议
            context=context.replace("$borrow_card", card_no);
            context=context.replace("$borrow_bank", bank);//开户行
            context=context.replace("$account_name", card_name);//账户名称
            context=context.replace("$bank_account", account);//账号
            context=context.replace("$borrow_user", name);//申请人
            s_year = create_time.substring(0, 4);
            s_month = create_time.substring(4, 6);
            s_day = create_time.substring(6, 8);
            context=context.replace("$s_year", s_year);
            context=context.replace("$s_month", s_month);
            context=context.replace("$s_day", s_day);
            map.put("user_time",user_time);
            map.put("creat_time",create_time);
            map.put("suffix","_KKXY");
            map.put("card_no",card_no);
            map.put("context",context);
        }else if("1".equals(template_type)){//信用查询协议
            context=context.replace("$borrow_user", realName);//申请人
          context=context.replace("$borrow_card", borrow_card);//身份证
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String time=sdf.format(new Date());
            s_year = time.substring(0, 4);
            s_month = time.substring(4, 6);
            s_day = time.substring(6, 8);
            context= context.replace("$s_year",s_year);
            context=context.replace("$s_month",s_month);
            context=context.replace("$s_day", s_day);
            map.put("user_time",user_time);
            map.put("suffix","_SQXY");
            map.put("context",context);
        }else if("2".equals(template_type)){
            map.put("user_time",user_time);
            map.put("suffix","_FWXY")
;            map.put("context",context);
        }else{
            returnMap.put("mag","获取协议模板失败！");
        }
        returnMap.put("data",createPackage(map));
        return returnMap;
    }
    //动态获取下拉选
    @PostMapping("platformType")
    @ResponseBody
    public Response platformType(){
        Response response = new Response();
        List list=dictService.getDetailList("平台类型");
        if (list!=null&&list.size()>0){
            response.setData(list);
        }else {
            return null;
        }
        return response;
    }

}