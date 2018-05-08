package com.zw.rule.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cfca.util.pki.PKIException;
import com.cfca.util.pki.api.CertUtil;
import com.cfca.util.pki.api.KeyUtil;
import com.cfca.util.pki.api.SignatureUtil;
import com.cfca.util.pki.cert.X509Cert;
import com.cfca.util.pki.cipher.JCrypto;
import com.cfca.util.pki.cipher.JKey;
import com.yeepay.g3.facade.yop.ca.dto.DigitalEnvelopeDTO;
import com.yeepay.g3.facade.yop.ca.enums.CertTypeEnum;
import com.yeepay.g3.frame.yop.ca.DigitalEnvelopeUtils;
import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.utils.InternalConfig;
import com.zw.base.util.CallbackUtils;
import com.zw.base.util.DateUtils;
import com.zw.base.util.TraceLoggerUtil;
import com.zw.yiBao.YbWithdrawsApi;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/11/17.
 */
public class WithdrawalsApi {

    private static com.cfca.util.pki.cipher.Session tempsession = null;
    //生成打款批次号
    public static String getbatchNo() throws Exception {
        Random r = new Random();
        String date = DateUtils.getDateString(new Date());
        return date += r.nextInt(10)+"";
    }

//    public static void main(String [] args){
//        try{
//        Map map=new HashedMap();
//        WithdrawalsApi.transferParamResolver(map);
//        }catch (Exception e){}
//    }


    //易宝提现接口
    public  static Map transferParamResolver(Map map) throws Exception{
        //商户密钥
        String hmacKey=map.get("hmacKey").toString();
        String mer_id = map.get("mer_id").toString();  //商户编号
        String host = map.get("host").toString(); //提现的url
        //先查询开户信息
        //Map accountMap=getOpenAccountInfo(userInfo.getCustomer_id());
        String account_Name =(String)  map.get("count_name"); //账户名称
        String branch_Bank_Name =(String)  map.get("account_branch_bank"); //开户支行名称
        String bank_Code = (String) map.get("account_bank_id"); //银行编号
        String province = (String) map.get("account_province"); //开户省
        String city = (String) map.get("account_city"); //开户市
        String batch_no = getbatchNo(); //生成打款批次号
        Map returnMap = new HashMap();
        String groupId=map.get("groupId").toString(); //总公司商户编号
        String cnaps=""; //联行号

        String account_Number =(String) map.get("bank_card"); //账户号
        String bank_Name = (String) map.get("account_bank"); //银行名称
        String order_id=(String)map.get("orderId");
        String account=(String)map.get("account");
        String xml="<data>\n" +
                "\t<cmd>TransferSingle</cmd>\n" +
                "\t<version>1.1</version>\n" +
                "\t<mer_Id>"+mer_id+"</mer_Id>\n" +
                "\t<group_Id>"+groupId+"</group_Id>\n" +
                "\t<batch_No>"+batch_no+"</batch_No>\n" +
                "\t<order_Id>"+order_id+"</order_Id>\n" +
                "\t<bank_Code>"+bank_Code+"</bank_Code>\n" +
                "\t<cnaps>"+cnaps+"</cnaps>\n" +
                "\t<bank_Name>"+bank_Name+"</bank_Name>\n" +
                "\t<branch_Bank_Name>"+branch_Bank_Name+"</branch_Bank_Name>\n" +
                "\t<amount>"+account+"</amount>\n" +
                "\t<account_Name>"+account_Name+"</account_Name>\n" +
                "\t<account_Number>"+account_Number+"</account_Number>\n" +
                "\t<province>"+province+"</province>\n" +
                "\t<city>"+city+"</city>\n" +
                "\t<fee_Type>SOURCE</fee_Type>\n" +
                "\t<payee_Email></payee_Email>\n" +
                "\t<payee_Mobile></payee_Mobile>\n" +
                "\t<leave_Word></leave_Word>\n" +
                "\t<abstractInfo></abstractInfo>\n" +
                "\t<remarksInfo></remarksInfo>\n" +
                "\t<urgency>0</urgency>\n" +
                "\t<hmac></hmac>\n" +
                "</data>";
        //调用体现接口，发送报文
        Map result = new LinkedHashMap();
        Map xmlMap = new LinkedHashMap();
        Map xmlBackMap = new LinkedHashMap();
        String retCValue ="";//系统返回码
        String bank_Status = "";//银行状态码
        String error_Msg ="";//错误信息
        String r1_Code ="";//打款状态码
        String s = "cmd,mer_Id,batch_No,order_Id,amount,account_Number,hmacKey";
        String[] digestValues = s.split(",");//request.getParameter("digest").split(",");
        String ss = "cmd,ret_Code,mer_Id,batch_No,total_Amt,total_Num,r1_Code,hmacKey";
        String[] backDigestValues =ss.split(",");
        //第一步:将请求的数据和商户自己的密钥拼成一个字符串,
        Document document = null;
        try {
            document= DocumentHelper.parseText(xml);
        } catch (Exception e) {
        }
        Element rootEle = document.getRootElement();
        String cmdValue = rootEle.elementText("cmd");
        List list = rootEle.elements();
        for(int i=0;i<list.size();i++){
            Element ele = (Element)list.get(i);
            String eleName = ele.getName();
            if(!eleName.equals("list")){
                xmlMap.put(eleName,ele.getText().trim());
            }else{
                continue;
            }
        }

        String hmacStr="";
        for(int i=0;i<digestValues.length;i++){
            if(digestValues[i].equals("hmacKey")){
                hmacStr = hmacStr+hmacKey;
                continue;
            }
            hmacStr = hmacStr + xmlMap.get(digestValues[i]);
        }
        System.out.println("签名之前的源数据为---||" + hmacStr+"||");

        //下面用数字证书进行签名
        String ALGORITHM = SignatureUtil.SHA1_RSA;
        JCrypto jcrypto =null;
        if(tempsession==null){
            try {
                //初始化加密库，获得会话session
                //多线程的应用可以共享一个session,不需要重复,只需初始化一次
                //初始化加密库并获得session。
                //系统退出后要jcrypto.finalize()，释放加密库
                jcrypto = JCrypto.getInstance();
                jcrypto.initialize(JCrypto.JSOFT_LIB, null);
                tempsession = jcrypto.openSession(JCrypto.JSOFT_LIB);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        JKey jkey = null;
        SignatureUtil signUtil =null;
        X509Cert cert  =null;
        String pfxPath = map.get("pfxPath").toString();
        try {
            jkey = KeyUtil.getPriKey(pfxPath, "123456");
        } catch (PKIException e1) {
            e1.printStackTrace();
        }
        try {
            cert = CertUtil.getCert(pfxPath, "123456");
        } catch (PKIException e1) {
            e1.printStackTrace();
        }
        System.out.println(cert.getSubject());
        X509Cert[] cs=new X509Cert[1];
        cs[0]=cert;
        String signMessage ="";
        try{
            // 第二步:对请求的串进行MD5对数据进行签名
            String yphs;
            yphs = new YbWithdrawsApi().hmacSign(hmacStr);
            signUtil = new SignatureUtil();
            byte[] b64SignData;
            // 第三步:对MD5签名之后数据调用CFCA提供的api方法用商户自己的数字证书进行签名
            b64SignData = signUtil.p7SignMessage(true, yphs.getBytes(),ALGORITHM, jkey, cs, tempsession);
            if(jcrypto!=null){
                jcrypto.finalize (com.cfca.util.pki.cipher.JCrypto.JSOFT_LIB,null);
            }
            signMessage = new String(b64SignData, "UTF-8");
        }catch(Exception e){
        }
        TraceLoggerUtil.info("经过md5和数字证书签名之后的数据为--->" + signMessage);
        Element r=rootEle.element("hmac");
        r.setText(signMessage);
        result.put("xml",xml);
        document.setXMLEncoding("GBK");
        TraceLoggerUtil.info("完整xml请求报文--->" + document.asXML());

        //第四步:发送https请求
        String responseMsg = CallbackUtils.httpRequest(host, document.asXML(), "POST", "gbk","text/xml ;charset=gbk", false);
        System.out.println(
                "<html><body><textarea rows=\"23\" cols=\"120\" name=\"xml\" id=\"xml\">" +
                        responseMsg +
                        "</textarea></body></html>");
        TraceLoggerUtil.info("服务器响应xml报文--->" + responseMsg);
        try {
            document = DocumentHelper.parseText(responseMsg);
        } catch (Exception e) {
        }
        rootEle = document.getRootElement();
        cmdValue = rootEle.elementText("hmac");
        //第五步:对服务器响应报文进行验证签名
        boolean sigerCertFlag = false;
        if(cmdValue!=null){
            try {
                sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
            } catch (PKIException e1) {
                e1.printStackTrace();
            }
            String backmd5hmac = xmlBackMap.get("hmac") + "";
            if(sigerCertFlag){
                TraceLoggerUtil.info("证书验签成功--->" + "证书验签成功");
                try {
                    backmd5hmac = new String(signUtil.getSignedContent());
                } catch (PKIException e1) {
                    e1.printStackTrace();
                }
                TraceLoggerUtil.info("证书验签获得的MD5签名数据为--->" + backmd5hmac);
                try {
                    TraceLoggerUtil.info("证书验签获得的证书dn为--->" + new String(signUtil.getSigerCert()[0].getSubject()));
                } catch (PKIException e1) {
                    e1.printStackTrace();
                }
                //第六步.将验签出来的结果数据与自己针对响应数据做MD5签名之后的数据进行比较是否相等
                Document backDocument = null;
                try {
                    backDocument = DocumentHelper.parseText(responseMsg);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Element backRootEle = backDocument.getRootElement();
                List backlist = backRootEle.elements();
                for(int i = 0; i < backlist.size(); i++){
                    Element ele = (Element)backlist.get(i);
                    String eleName = ele.getName();
                    if(!eleName.equals("list")){
                        xmlBackMap.put(eleName, ele.getText().trim());
                    }else{
                        continue;
                    }
                }
                String backHmacStr="";
                for(int i = 0; i < backDigestValues.length;i++){
                    if(backDigestValues[i].equals("hmacKey")){
                        backHmacStr = backHmacStr + hmacKey;
                        continue;
                    }
                    String tempStr = (String)xmlBackMap.get(backDigestValues[i]);
                    backHmacStr = backHmacStr + ((tempStr == null) ? "" : tempStr);
                }
                String newmd5hmac = new YbWithdrawsApi().hmacSign(backHmacStr);
                TraceLoggerUtil.info("提交返回源数据为--->" + backHmacStr);
                TraceLoggerUtil.info("经过md5签名后的验证返回hmac为--->" + newmd5hmac);
                TraceLoggerUtil.info("提交返回的hmac为--->" + backmd5hmac);
                if(newmd5hmac.equals(backmd5hmac)){
                    System.out.println("md5验签成功");
                    //第七步:判断该证书DN是否为易宝
                    try {
                        if(signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0){
                            TraceLoggerUtil.info("证书DN是易宝的--->" + "证书DN是易宝的");
                            retCValue = rootEle.elementText("ret_Code");//系统返回吗
                            bank_Status = rootEle.elementText("bank_Status");//银行状态码
                            error_Msg = rootEle.elementText("error_Msg");//错误信息
                            r1_Code = rootEle.elementText("r1_Code");//打款状态
                            returnMap.put("retCValue",retCValue);
                            returnMap.put("bank_Status",bank_Status);
                            returnMap.put("error_Msg",error_Msg);
                            returnMap.put("r1_Code",r1_Code);
                            returnMap.put("responseMsg",responseMsg);
                            returnMap.put("batch_No",batch_no);
                        }else{
                            TraceLoggerUtil.info("证书DN不是易宝的--->" + "证书DN不是易宝的");
                        }
                    } catch (PKIException e) {
                        e.printStackTrace();
                    }
                }else{
                    TraceLoggerUtil.info("md5验签失败--->" + "md5验签失败");
                }
            }else{
                TraceLoggerUtil.info("证书验签失败--->" + "证书验签失败");
            }
        }
        return returnMap;
    }

    /**
     * 秒付提现回调接口
     * @param map
     * @return
     */
    public static Map ybWithdrawalCallbackInfo(Map map) {
        String hmacKey=map.get("hmacKey").toString();
        String responseMsg=map.get("responseMsg").toString();
        String pfxPath=map.get("pfxPath").toString();
        Map result = new LinkedHashMap();
        Map xmlMap = new LinkedHashMap();
        Map xmlBackMap = new LinkedHashMap();
        System.out.println(
                "<html><body><textarea rows=\"23\" cols=\"120\" name=\"xml\" id=\"xml\">" +
                        responseMsg +
                        "</textarea></body></html>");
        TraceLoggerUtil.info("服务器响应xml报文--->" + responseMsg);

        Document document = null;
        try {
            document = DocumentHelper.parseText(responseMsg);
        } catch (Exception e) {
        }
        Element rootEle = document.getRootElement();
        String cmdValue = rootEle.elementText("hmac");

        //对服务器响应报文进行验证签名
        String ALGORITHM = SignatureUtil.SHA1_RSA;
        JCrypto jcrypto =null;
        if(tempsession==null){
            try {
                //初始化加密库，获得会话session
                //多线程的应用可以共享一个session,不需要重复,只需初始化一次
                //初始化加密库并获得session。
                //系统退出后要jcrypto.finalize()，释放加密库
                jcrypto = JCrypto.getInstance();
                jcrypto.initialize(JCrypto.JSOFT_LIB, null);
                tempsession = jcrypto.openSession(JCrypto.JSOFT_LIB);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        JKey jkey = null;
        try {
            jkey = KeyUtil.getPriKey(pfxPath, "123456");
        } catch (PKIException e) {
            e.printStackTrace();
        }
        X509Cert cert = null;
        try {
            cert = CertUtil.getCert(pfxPath, "123456");
        } catch (PKIException e) {
            e.printStackTrace();
        }
        X509Cert[] cs=new X509Cert[1];
        cs[0]=cert;
        boolean sigerCertFlag = false;
        SignatureUtil signUtil = new SignatureUtil();;
        if(cmdValue!=null){
            try {
                sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
            } catch (PKIException e) {
                e.printStackTrace();
            }
            String backmd5hmac = xmlBackMap.get("hmac") + "";
            TraceLoggerUtil.info("------------------>" + backmd5hmac);
            if(sigerCertFlag){
                TraceLoggerUtil.info("证书验签成功");
                try {
                    backmd5hmac = new String(signUtil.getSignedContent());
                    TraceLoggerUtil.info("证书验签获得的MD5签名数据为----"+ backmd5hmac);
                    TraceLoggerUtil.info("证书验签获得的证书dn为----"+ new String(signUtil.getSigerCert()[0].getSubject()));
                } catch (PKIException e) {
                    e.printStackTrace();
                }
                //将验签出来的结果数据与自己针对响应数据做MD5签名之后的数据进行比较是否相等
                Document backDocument = null;
                try {
                    backDocument = DocumentHelper.parseText(responseMsg);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Element backRootEle = backDocument.getRootElement();
                List backlist = backRootEle.elements();
                for(int i = 0; i < backlist.size(); i++){
                    Element ele = (Element)backlist.get(i);
                    String eleName = ele.getName();
                    if(!eleName.equals("list")){
                        xmlBackMap.put(eleName, ele.getText().trim());
                    }else{
                        continue;
                    }
                }
                String backHmacStr="";
                String[] backDigestValues = "cmd,mer_Id,batch_No,order_Id,status,message,hmacKey".split(",");
                for(int i = 0; i < backDigestValues.length;i++){
                    if(backDigestValues[i].equals("hmacKey")){
                        backHmacStr = backHmacStr + hmacKey;
                        continue;
                    }
                    String tempStr = (String)xmlBackMap.get(backDigestValues[i]);
                    backHmacStr = backHmacStr + ((tempStr == null) ? "" : tempStr);
                }
                String newmd5hmac =new YbWithdrawsApi().hmacSign(backHmacStr);
                TraceLoggerUtil.info("提交返回源数据为----"+ backHmacStr);
                TraceLoggerUtil.info("经过md5签名后的验证返回hmac为----"+ newmd5hmac);
                TraceLoggerUtil.info("提交返回的hmac为----"+ backmd5hmac);
                if(newmd5hmac.equals(backmd5hmac)){
                    TraceLoggerUtil.info("md5验签成功----");
                    //判断该证书DN是否为易宝
                    try {
                        if(signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0){
                            TraceLoggerUtil.info("证书DN是易宝的----");
                            result.put("orderId",xmlBackMap.get("order_Id").toString());
                            result.put("status",xmlBackMap.get("status").toString());
                            result.put("batch_No",xmlBackMap.get("batch_No").toString());
                        }else{
                            TraceLoggerUtil.info("证书DN不是易宝的----");
                        }
                    } catch (PKIException e) {
                        e.printStackTrace();
                    }
                }else{
                    TraceLoggerUtil.info("md5验签失败----");
                }
            }else{
                TraceLoggerUtil.info("证书验签失败----");
            }
        }

        return result;
    }

    /**
     //     * 扣款请求
     //     * @param cardCheckMap
     //     * @return
     //     * @throws Exception
     //     */
    public static Map bindCardPayQuery(Map cardCheckMap) throws Exception {
        String unibindcardpayUri = cardCheckMap.get("unibindcardpayUri").toString();//统一绑卡支付uri
        String host = cardCheckMap.get("recordcallbackurl").toString(); //回调地址
        String requestno = UUID.randomUUID().toString();
        String appKey = cardCheckMap.get("appKey").toString();
        String secretKey = cardCheckMap.get("secretKey").toString();
        String serverRoot = cardCheckMap.get("serverRoot").toString();
        String callbackurl = host + "/finalAudit/ybWithdrawalCallback"; //回调地址
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requesttime = sdf.format(date);//请求时间格式2017-11-01 18:19:20
        Map<String, String> ybmap = new HashMap<String, String>();
        ybmap.put("merchantno", cardCheckMap.get("mer_id").toString());//商户编号
        ybmap.put("requestno", requestno);//业务请号
        ybmap.put("issms", "false");//是否存在短验
        ybmap.put("identityid", cardCheckMap.get("card_num").toString());//商户生成的唯一标识
        ybmap.put("identitytype", "ID_CARD");//商户生成的唯一标识类型
        ybmap.put("idcardno", cardCheckMap.get("card_num").toString());//用户标识
        ybmap.put("idcardtype", "ID");//用户标识类型
        ybmap.put("cardno", cardCheckMap.get("bank_card").toString());//银行卡号
        ybmap.put("username", cardCheckMap.get("count_name").toString());//用户姓名
        ybmap.put("phone", cardCheckMap.get("tel").toString());//手机
        ybmap.put("amount", cardCheckMap.get("amount").toString());//还款金额
        ybmap.put("authtype", "COMMON_FOUR");//建议短信发送类型
        ybmap.put("issms", "false");//是否短验
        ybmap.put("productname", "秒付金服");//商品名称
        ybmap.put("callbackurl", callbackurl);//回调地址
        ybmap.put("requesttime", requesttime);//请求时间
        ybmap.put("terminalno", "SQKKSCENE10");//终端号标识码
        ybmap.put("remark", "");//备注
        ybmap.put("extinfos", "");//扩展信息
        ybmap.put("dividecallbackurl", "");//分账结果通知地址
        ybmap.put("dividejstr", "");//分账信息
        YopRequest yoprequest  =  new YopRequest(appKey,secretKey,serverRoot);
        Map<String,String> result  =  new HashMap<String,String>();
        Set<Map.Entry<String,String>> entry		=	ybmap.entrySet();
        for(Map.Entry<String,String> s:entry){
            yoprequest.addParam(s.getKey(), s.getValue());
        }
        System.out.println("yoprequest:"+yoprequest.getParams());
        //向YOP发请求
        YopResponse yopresponse = YopClient3.postRsa(unibindcardpayUri, yoprequest);
        TraceLoggerUtil.info("请求YOP之后结果----"+yopresponse.toString());
        TraceLoggerUtil.info("请求YOP之后结果----"+yopresponse.getStringResult());

        //成功则进行相关处理
        if ("SUCCESS".equals(yopresponse.getState())) {
            Map resultMap=(Map)yopresponse.getResult();
            if ("PROCESSING".equals(resultMap.get("status").toString())) {
                result = parseResponse(yopresponse.getStringResult());
                System.out.println("yopresponse.getStringResult: "+result);
                return result;
            }else {
                if(yopresponse.getError() != null){
                    result.put("errorcode",yopresponse.getError().getCode());
                    result.put("errormsg",yopresponse.getError().getMessage());
                    TraceLoggerUtil.info("系统处理异常结果----"+result);
                    return result;
                }
            }
        }else {
            if(yopresponse.getError() != null){
                result.put("errorcode",yopresponse.getError().getCode());
                result.put("errormsg",yopresponse.getError().getMessage());
                TraceLoggerUtil.info("系统处理异常结果----"+result);
                return result;
            }
        }
        return result;
    }

    //将获取到的yopresponse转换成json格式
    public static Map<String, String> parseResponse(String yopresponse){

        Map<String,String> jsonMap  = new HashMap<>();
        jsonMap	= JSON.parseObject(yopresponse,
                new TypeReference<TreeMap<String,String>>() {});
        System.out.println("将结果yopresponse转化为map格式之后: "+jsonMap);
        return jsonMap;
    }

    /*
    * 扣款回调
    * */
    public static Map bindCardPayCallback(Map cardCheckMap) throws Exception {
        String response=cardCheckMap.get("responseMsg").toString();
        TraceLoggerUtil.info("扣款回调----"+response);
        Map<String, String> jsonMap = new HashMap<>();
        try {
            //开始解密;
            DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
            dto.setCipherText(response);
            //InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
            PrivateKey privateKey = InternalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
            TraceLoggerUtil.info("privateKey----"+privateKey);
            PublicKey publicKey = InternalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
            TraceLoggerUtil.info("publicKey----"+publicKey);

            dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
            System.out.println("-------:" + dto.getPlainText());
            jsonMap = parseResponse(dto.getPlainText());
            System.out.println(jsonMap);
        } catch (Exception e) {
            jsonMap=null;
            TraceLoggerUtil.info("回调解密失败----"+"回调解密失败");
        }
        return jsonMap;
    }
}

