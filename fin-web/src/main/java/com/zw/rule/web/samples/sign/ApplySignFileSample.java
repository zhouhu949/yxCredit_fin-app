package com.zw.rule.web.samples.sign;

import com.alibaba.fastjson.JSON;
import com.junziqian.api.bean.Signatory;
import com.junziqian.api.common.DealType;
import com.junziqian.api.common.IdentityType;
import com.junziqian.api.request.ApplySignFileRequest;
import com.junziqian.api.response.ApplySignResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;
import org.ebaoquan.rop.thirdparty.com.alibaba.fastjson.JSONArray;
import org.ebaoquan.rop.thirdparty.com.alibaba.fastjson.JSONObject;
import org.ebaoquan.rop.thirdparty.com.google.common.collect.Sets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by wlinguo on 2016-05-16.
 */
public class ApplySignFileSample extends JunziqianClientInit {
    public static String getJunziqian(Map map) throws IOException {
        ApplySignFileRequest.Builder builder = new ApplySignFileRequest.Builder();
        builder.withContractName("买卖协议"); // 合同名称，必填
        builder.withContractAmount(Double.parseDouble(map.get("contractAmount").toString())); // 合同金额
        //1、本地文件方式
        builder.withFile(map.get("pdfPath").toString());
        builder.withServerCa(1);
        builder.withDealType(DealType.AUTH_SIGN);
        /**
         * 0 只需第一次认证过，后面不用认证
         * 1 每次签约都要认证
         */
        HashSet<Signatory> signatories = Sets.newHashSet();
        Signatory signatory = new Signatory();
        signatory.setFullName(map.get("cusName").toString()); //姓名
        signatory.setSignatoryIdentityType(IdentityType.IDCARD); //证件类型
        signatory.setIdentityCard(map.get("cusCard").toString()); //证件号码
        signatory.setMobile(map.get("tel").toString());
        signatory.setServerCaAuto(1);//0 手动签， 1 自动签
        JSONArray chapteJsonArray=new JSONArray();
        
        JSONObject pageJson=new JSONObject();
        pageJson.put("page", 0);
        JSONArray chaptes=new JSONArray();
        pageJson.put("chaptes", chaptes);
        chapteJsonArray.add(pageJson);
        
        JSONObject chapte=new JSONObject();
        chapte.put("offsetX", 0.25);
        chapte.put("offsetY", 0.27);
        chaptes.add(chapte);

        JSONObject pageJson1=new JSONObject();
        pageJson1.put("page", 2);
        JSONArray chaptes1=new JSONArray();
        pageJson1.put("chaptes", chaptes1);
        chapteJsonArray.add(pageJson1);

        JSONObject chapte1=new JSONObject();
        chapte1.put("offsetX", 0.18);
        chapte1.put("offsetY", 0.52);
        chaptes1.add(chapte1);

        System.out.println(JSON.toJSONString(chapteJsonArray));


        signatory.withChapteJson(chapteJsonArray);
        signatories.add(signatory);

        builder.withSignatories(signatories); // 添加签约人
        String serviceUrl = map.get("services_url").toString();
        String appSecrete = map.get("appSecrete").toString();
        String appKey = map.get("appKey").toString();
        ApplySignResponse response = getClient(serviceUrl,appKey,appSecrete).applySignFile(builder.build());
        LogUtils.logResponse(response);
        return response.getApplyNo();
    }
}
