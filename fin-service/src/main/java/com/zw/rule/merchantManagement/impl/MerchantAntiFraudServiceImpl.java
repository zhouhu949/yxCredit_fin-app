package com.zw.rule.merchantManagement.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.mapper.merchant.MerchantAntiFraudMapper;
import com.zw.rule.mapper.merchant.MerchantListMapper;
import com.zw.rule.mapper.surety.SuretyCheckMapper;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchantManagement.MerchantAntiFraudService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/1/9.
 */
@Service
public class MerchantAntiFraudServiceImpl implements MerchantAntiFraudService {
    @Resource
    private MerchantAntiFraudMapper mapper;
    //给商户添加图片用到的mapper
    @Resource
    private MerchantListMapper mapperAddImgs;
    //记录审核备注的mapper
    @Resource
    private SuretyCheckMapper mapperOther;
    /**
     * 获取所有反欺诈的商户
     * @param map
     * @return
     */
    public List<Merchant> getAntiFraudMerchants(Map map){
        return mapper.selectAllAntifraudMerchants(map);
    }

    /**
     * 添加反欺诈图片给商户
     * @param map
     * @return
     */
    public int addAntiFraudImgsToMerchant(Map map) {
        //处理图片-->图片类型：0-身份证账面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像
        //0-身份证正面
        List<String> idcardZhengmianList = (List<String>) map.get("idcardZhengmian");
        addImgsToMerchant(idcardZhengmianList, map.get("merchantId").toString(), "10");//10-身份证正面(反欺诈上传)
        //1-身份证反面
        List<String> idcardFanmianList = (List<String>) map.get("idcardFanmian");
        addImgsToMerchant(idcardFanmianList, map.get("merchantId").toString(), "11");//11-身份证反面(反欺诈上传)
        //2-营业执照
        List<String> yinyezhizhaoList = (List<String>) map.get("yinyezhizhao");
        addImgsToMerchant(yinyezhizhaoList, map.get("merchantId").toString(), "12");//12营业执照(反欺诈上传)
        //3-门头logo照
        List<String> touxiangLogoList = (List<String>) map.get("touxiangLogo");
        addImgsToMerchant(touxiangLogoList, map.get("merchantId").toString(), "13");//13-门头logo照(反欺诈上传)
        //4-室内照
        List<String> shineiList = (List<String>) map.get("shinei");
        addImgsToMerchant(shineiList, map.get("merchantId").toString(), "14");//14-室内照(反欺诈上传)
        //5-收银台照
        List<String> shouyintaiList = (List<String>) map.get("shouyintai");
        addImgsToMerchant(shouyintaiList, map.get("merchantId").toString(), "15");//15-收银台照
        //6-街景照
        List<String> jiejingList = (List<String>) map.get("jiejing");
        addImgsToMerchant(jiejingList, map.get("merchantId").toString(), "16");//16-街景照(反欺诈上传)
        //7-法人银行卡照
        List<String> farenyinhangkaList = (List<String>) map.get("farenyinhangka");
        addImgsToMerchant(farenyinhangkaList, map.get("merchantId").toString(), "17");//17-法人银行卡照(反欺诈上传)
        return 1;
    }

    //给商户关联图片的功能方法
    public void addImgsToMerchant(List imgList, String merchantId, String imgType) {//传入图片地址的集合，商户id，图片类型
        for (int i = 0; i < imgList.size(); i++) {
            if (!("").equals(imgList.get(i).toString())) {
                Map<String, String> imgMap = new HashMap<String, String>();
                //关联表id
                imgMap.put("id", UUID.randomUUID().toString());
                imgMap.put("merchantId", merchantId);
                imgMap.put("imgUrl", imgList.get(i).toString());
                imgMap.put("imgType", imgType);
                mapperAddImgs.insertImgsTomerchant(imgMap);
            }
        }
    }
    /**
     * 审核商户后记录商户审批意见，审批人等
     * @param map
     * @return
     */
    public boolean writeSuggestion(Map map) {
        ProcessApproveRecord processApproveRecord = new ProcessApproveRecord();
        String uuid = UUID.randomUUID().toString();
        processApproveRecord.setId(uuid);
        processApproveRecord.setCreateTime(DateUtils.getDateString(new Date()));
        processApproveRecord.setCommitTime(DateUtils.getDateString(new Date()));
//        processApproveRecord.setOrderId(map.get("id").toString());
        processApproveRecord.setRelId(map.get("merchantId").toString());
        processApproveRecord.setType(map.get("type").toString());//1客户id，2商户id
//        processApproveRecord.setState(map.get("state").toString());
//        processApproveRecord.setResult(map.get("result").toString());//结果 1通过  0拒绝
        processApproveRecord.setApproveSuggestion(map.get("suggestion").toString());//审批意见
        processApproveRecord.setHandlerId(map.get("handlerId").toString());//处理人id
        processApproveRecord.setHandlerName(map.get("handlerName").toString());//处理人姓名
        processApproveRecord.setNodeId("4");//总部审核1；审核担保人担保订单2;审核商户的时候生成3 ；反欺诈处理生成4；   记录在哪里生成的审核意见
        int num = mapperOther.insertSelective(processApproveRecord);
        return num>0;
    }

    /**
     * 更该商户反欺诈状态
     * @param map
     * @return
     */
    public int changMerchantFanQiZhaState(Map map) {
        return mapper.updateMerchantFanQiZhaState(map);
    }

}
