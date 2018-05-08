package com.zw.rule.api;

import com.yeepay.g3.utils.common.json.JSONObject;
import com.yeepay.g3.utils.common.json.XML;
import com.zw.baoFu.base.TransContent;
import com.zw.baoFu.base.TransHead;
import com.zw.baoFu.base.request.TransReqBF0040001;
import com.zw.baoFu.base.request.TransReqBF0040002;
import com.zw.baoFu.base.response.TransRespBF0040001;
import com.zw.baoFu.base.response.TransRespBF0040002;
import com.zw.baoFu.base.response.TransRespBF40001Async;
import com.zw.baoFu.domain.RequestParams;
import com.zw.baoFu.http.SimpleHttpResponse;
import com.zw.baoFu.rsa.RsaCodingUtil;
import com.zw.baoFu.util.*;
import com.zw.base.util.TraceLoggerUtil;
import org.apache.commons.collections.map.HashedMap;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/12/8.
 */
public  class BaoFuApi {

    private static TransContent<TransReqBF0040001> transContent=null;
    //放款接口
    public static Map confirmationLoan(Map map) throws Exception {
        Map<String,Object> resultMap= new HashedMap();
        String dataType = TransConstant.data_type_xml; // 数据类型 xml/json
        if (transContent==null){
            transContent = new TransContent<TransReqBF0040001>(
                    dataType);
        }
        //唯一流水号
        String Trans_no=map.get("transNo").toString();
        List<TransReqBF0040001> trans_reqDatas = new ArrayList<TransReqBF0040001>();
        TransReqBF0040001 transReqData = new TransReqBF0040001();
        transReqData.setTrans_no(Trans_no);
        transReqData.setTrans_money(map.get("amount").toString());
        transReqData.setTo_acc_name(map.get("count_name")==null?"":map.get("count_name").toString());
        transReqData.setTo_acc_no(map.get("bank_card").toString());
        transReqData.setTo_bank_name(map.get("account_bank").toString());
        transReqData.setTo_pro_name(map.get("account_province")==null?"":map.get("account_province").toString());
        transReqData.setTo_city_name(map.get("account_city")==null?"":map.get("account_city").toString());
        transReqData.setTo_acc_dept(map.get("bankBranch")==null?"":map.get("bankBranch").toString());
        transReqData.setTrans_summary("备注1");
        transReqData.setTrans_card_id(map.get("card_num")==null?"":map.get("card_num").toString());
        transReqData.setTrans_mobile(map.get("tel")==null?"":map.get("tel").toString());
        trans_reqDatas.add(transReqData);
        transContent.setTrans_reqDatas(trans_reqDatas);
        String bean2XmlString = transContent.obj2Str(transContent);
        TraceLoggerUtil.info("报文：" + bean2XmlString);

        String keyStorePath = map.get("pfxPath").toString();
        String keyStorePassword = map.get("keyStorePassword").toString();
        String pub_key = map.get("cerPath").toString();
        String origData = bean2XmlString;
        /**
         * 加密规则：项目编码UTF-8
         * 第一步：BASE64 加密
         * 第二步：商户私钥加密
         */
        origData =  new String(SecurityUtil.Base64Encode(origData));//Base64.encode(origData);
        String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
                keyStorePath, keyStorePassword);

        TraceLoggerUtil.info("----------->【私钥加密-结果】" + encryptData);
        // 发送请求
        String requestUrl = map.get("loanUrl").toString();
        String memberId = map.get("memberId").toString(); // 商户号
        String terminalId = map.get("terminalId").toString(); // 终端号

        RequestParams params = new RequestParams();
        params.setMemberId(Integer.parseInt(memberId));
        params.setTerminalId(Integer.parseInt(terminalId));
        params.setDataType(dataType);
        params.setDataContent(encryptData);// 加密后数据
        params.setVersion("4.0.0");
        params.setRequestUrl(requestUrl);
        SimpleHttpResponse response = BaofooClient.doRequest(params);
        TraceLoggerUtil.info("宝付请求返回结果：" + response.getEntityString());
        TransContent<TransRespBF0040001> str2Obj = new TransContent<TransRespBF0040001>(dataType);
        String reslut = response.getEntityString();
        /**
         * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
         *
         * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
         *
         * 再次通过BASE64解密：new String(new Base64().decode(reslut))
         *
         * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
         */
        TraceLoggerUtil.info(reslut);
        //明文返回处理可能是报文头参数不正确、或其他的异常导致；
        if (reslut.contains("trans_content")) {
            //明文返回
            //我报文错误处理
            str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(
                    reslut, TransRespBF0040001.class);
            throw new Exception("报文头参数不正确");
        } else {
            //密文返回
            //第一步：公钥解密
            reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
            //第二步BASE64解密
            reslut = SecurityUtil.Base64Decode(reslut);
            TraceLoggerUtil.info(reslut);
            str2Obj = (TransContent<TransRespBF0040001>) str2Obj.str2Obj(
                    reslut, TransRespBF0040001.class);
            // 业务逻辑判断
            TransHead list = str2Obj.getTrans_head();
            TraceLoggerUtil.info(list.getReturn_code()+":"+list.getReturn_msg());
            //将xml转为json
            JSONObject xmlJSONObj = XML.toJSONObject(reslut);
            String returnCode=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_head").getString("return_code");
            String returnMsg=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_head").getString("return_msg");
            //0000代付请求交易成功（交易已受理） 0300 代付交易未明，请发起该笔订单查询 0999代付主机系统繁忙   情况不明需要查询

            resultMap.put("returnCode",returnCode);
            resultMap.put("returnMsg",returnMsg);
        }
        return resultMap;
    }

    //放款查询接口
    public static Map confirmationLoanQuery(Map map) throws Exception {
        Map<String,Object> resultMap= new HashedMap();
        TransContent<TransReqBF0040002> transContent = new TransContent<TransReqBF0040002>(
                TransConstant.data_type_xml);

        List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();
        TransReqBF0040002 transReqData = new TransReqBF0040002();
        transReqData.setTrans_no(map.get("transNo").toString());
        trans_reqDatas.add(transReqData);
        transContent.setTrans_reqDatas(trans_reqDatas);
        String bean2XmlString = transContent.obj2Str(transContent);
        System.out.println("报文：" + bean2XmlString);
        String keyStorePath =  map.get("pfxPath").toString();
        String keyStorePassword = map.get("keyStorePassword").toString();
        String pub_key =map.get("cerPath").toString();
        String origData = bean2XmlString;
        //origData = Base64.encode(origData);
        /**
         * 加密规则：项目编码UTF-8
         * 第一步：BASE64 加密
         * 第二步：商户私钥加密
         */
        origData =  new String(SecurityUtil.Base64Encode(origData));//Base64.encode(origData);
        String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
                keyStorePath, keyStorePassword);

        TraceLoggerUtil.info("----------->放款【私钥加密-结果】" + encryptData);

        // 发送请求
        String requestUrl = map.get("loanQueryUrl").toString();
        String memberId = map.get("memberId").toString(); // 商户号
        String terminalId =map.get("terminalId").toString(); // 终端号
        String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

        RequestParams params = new RequestParams();
        params.setMemberId(Integer.parseInt(memberId));
        params.setTerminalId(Integer.parseInt(terminalId));
        params.setDataType(dataType);
        params.setDataContent(encryptData);// 加密后数据
        params.setVersion("4.0.0");
        params.setRequestUrl(requestUrl);
        SimpleHttpResponse response = BaofooClient.doRequest(params);

        //TraceLoggerUtil.info("放款-------宝付请求返回结果：" + response.getEntityString());

        TransContent<TransRespBF0040002> str2Obj = new TransContent<TransRespBF0040002>(dataType);

        String reslut = response.getEntityString();

        /**
         * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
         *
         * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
         *
         * 再次通过BASE64解密：new String(new Base64().decode(reslut))
         *
         * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
         */
        //System.out.println(reslut);
        TraceLoggerUtil.info("放款宝付请求返回结果未解密："+reslut);
        if (reslut.contains("trans_content")) {
            // 我报文错误处理
            str2Obj = (TransContent<TransRespBF0040002>) str2Obj
                    .str2Obj(reslut,TransRespBF0040002.class);
            //业务逻辑判断
            throw new Exception("报文头参数不正确");
        } else {
            reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
            reslut = SecurityUtil.Base64Decode(reslut);
            str2Obj = (TransContent<TransRespBF0040002>) str2Obj
                    .str2Obj(reslut,TransRespBF0040002.class);
            //业务逻辑判断
        }
        TraceLoggerUtil.info("放款宝付请求返回结果已解密："+reslut);
        //将xml转为json
        JSONObject xmlJSONObj = XML.toJSONObject(reslut);
        String returnCode=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_head").getString("return_code");
        String returnMsg=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_head").getString("return_msg");
        resultMap.put("returnCode",returnCode);
        resultMap.put("returnMsg",returnMsg);
        return resultMap;
    }

    //扣款接口
    public static Map ApiPay(Map map) throws Exception{
        Map<String,Object> resultMap= new HashedMap();
        String pay_code = map.get("account_bank_id").toString();//银行卡编码
        String acc_no = map.get("bank_card").toString();//银行卡卡号
        String id_card = map.get("card_num").toString();//身份证号码
        String id_holder = map.get("count_name").toString();//姓名
        String mobile = map.get("tel").toString();//银行预留手机号
        String  pfxpath = map.get("pfxPath").toString();//商户私钥
        String  cerpath = map.get("cerPath").toString();//宝付公钥
        Map<String,String> HeadPostParam = new HashedMap();
        HeadPostParam.put("version", "4.0.0.0");
        HeadPostParam.put("member_id", map.get("memberId").toString());
        HeadPostParam.put("terminal_id", map.get("terminalId").toString());
        HeadPostParam.put("txn_type", "0431");
        HeadPostParam.put("data_type", "xml");
        HeadPostParam.put("txn_sub_type", "13"); //交易子类（代扣）
        //唯一流水号
        String Trans_no=map.get("debitTransNo").toString();
        String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期
        Map<String,String> XMLArray = new HashMap<String,String>();
        XMLArray.put("txn_sub_type", HeadPostParam.get("txn_sub_type"));
        XMLArray.put("biz_type", "0000");
        XMLArray.put("terminal_id", HeadPostParam.get("terminal_id"));
        XMLArray.put("member_id", HeadPostParam.get("member_id"));
        String  txn_amt = String.valueOf(new BigDecimal(map.get("debitAmount").toString()).multiply(BigDecimal.valueOf(100)).setScale(0));//支付金额转换成分
        XMLArray.put("pay_code", pay_code);
        XMLArray.put("pay_cm", "2");//传默认值
        XMLArray.put("id_card_type", "01");//身份证传固定值。
        XMLArray.put("acc_no", acc_no);
        XMLArray.put("id_card", id_card);
        XMLArray.put("id_holder", id_holder);
        XMLArray.put("mobile", mobile);
        XMLArray.put("valid_date", "");//信用卡有效期
        XMLArray.put("valid_no", "");//信用卡安全码
        XMLArray.put("trans_id", Trans_no);//商户订单号
        XMLArray.put("txn_amt", txn_amt);
        XMLArray.put("trans_serial_no", "TISN"+System.currentTimeMillis());
        XMLArray.put("trade_date", trade_date);
        XMLArray.put("additional_info", "附加信息");
        XMLArray.put("req_reserved", "保留");
        String XmlOrJson = "";
        if(HeadPostParam.get("data_type").equals("xml")){
            Map<Object,Object> ArrayToObj = new HashMap<Object,Object>();
            ArrayToObj.putAll(XMLArray);
            XmlOrJson = MapToXml.Coverter(ArrayToObj, "data_content");
        }
        TraceLoggerUtil.info("请求参数："+XmlOrJson);
        String base64str = SecurityUtil.Base64Encode(XmlOrJson);
        String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,map.get("keyStorePassword").toString());
        HeadPostParam.put("data_content", data_content);//加入请求密文
        String PostString  = HttpUtil.RequestForm(map.get("debitUrl").toString(), HeadPostParam);
        TraceLoggerUtil.info("请求返回："+ PostString);
        PostString = RsaCodingUtil.decryptByPubCerFile(PostString,cerpath);
        if(PostString.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
            TraceLoggerUtil.info("=====检查解密公钥是否正确！");
        }
        PostString = SecurityUtil.Base64Decode(PostString);
        TraceLoggerUtil.info("=====返回查询数据解密结果:"+PostString);
        //将xml转为json
        JSONObject xmlJSONObj = XML.toJSONObject(PostString);
        String returnCode=xmlJSONObj.getJSONObject("result").getString("resp_code");
        String returnMsg=xmlJSONObj.getJSONObject("result").getString("resp_msg");
        resultMap.put("returnCode",returnCode);
        resultMap.put("returnMsg",returnMsg);
        return resultMap;
    }

    //还款回调
    public static Map bfWithdrawalCallback(HttpServletRequest req,String cerPath)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        String member_id=req.getParameter("member_id");
        String terminal_id=req.getParameter("terminal_id");
        String data_type=req.getParameter("data_type");
        String data_content=req.getParameter("data_content");
        if (data_content == null ||"".equals(data_content)) {
            TraceLoggerUtil.info("接受数据不能为空！");
            throw new RuntimeException("接受数据不能为空！");
        }

        TraceLoggerUtil.info("放款回调接收的返回数据：member_id" + member_id+",terminal_id:"+terminal_id+",data_content:"+data_content);
        String pub_key = cerPath;
        TransContent<TransRespBF40001Async> str2Obj = new TransContent<TransRespBF40001Async>(data_type);
        // 密文返回
        // 第一步：公钥解密
        data_content = RsaCodingUtil.decryptByPubCerFile(data_content, pub_key);
        // 第二步BASE64解密
        data_content = SecurityUtil.Base64Decode(data_content);
        TraceLoggerUtil.info("放款回调data_content:"+data_content);
        str2Obj = (TransContent<TransRespBF40001Async>) str2Obj.str2Obj(data_content, TransRespBF40001Async.class);
        // 业务逻辑判断
        System.out.println(str2Obj.getTrans_reqDatas());

        //将xml转为json
        JSONObject xmlJSONObj = XML.toJSONObject(data_content);
        String returnCode=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_reqDatas").getJSONObject("trans_reqData").get("state").toString();
        String returnMsg=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_reqDatas").getJSONObject("trans_reqData").get("trans_remark").toString();
        String transNo=xmlJSONObj.getJSONObject("trans_content").getJSONObject("trans_reqDatas").getJSONObject("trans_reqData").get("trans_no").toString();

        resultMap.put("returnCode",returnCode);
        resultMap.put("returnMsg",returnMsg);
        resultMap.put("transNo",transNo);
        return resultMap;
    }

}
