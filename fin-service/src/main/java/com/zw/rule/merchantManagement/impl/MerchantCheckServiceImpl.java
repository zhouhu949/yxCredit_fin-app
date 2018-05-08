package com.zw.rule.merchantManagement.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.core.Response;
import com.zw.rule.mapper.merchant.MerchantCheckMapper;
import com.zw.rule.mapper.merchant.MerchantListMapper;
import com.zw.rule.mapper.surety.SuretyCheckMapper;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchant.MerchantBasicInformation;
import com.zw.rule.merchantManagement.MerchantCheckService;
import com.zw.rule.po.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/1/8.
 */
@Service
public class MerchantCheckServiceImpl implements MerchantCheckService {
    @Resource
    private MerchantCheckMapper mapper;
    @Resource
    private MerchantListMapper listMapper;
    @Resource
    private SuretyCheckMapper mapperOther;
    /**
     * 获取全部商户审核列表
     * @return
     */
    public List<Merchant> getCheckMerchantList(Map map) {
        return mapper.selectCheckMerchantList(map);
    }

    /**
     * 审核商户的service层方法 集成了三个方法
     * （插入意见表意见，更改审核状态，更改商户等级）
     * @param map
     * @return
     */
    @Transactional
    public boolean auditOneMerchantSer(Map map){
        //插入处理建议到建议表
        boolean b=writeSuggestion(map);
        //更改商户审核状态
        int i= changeMerchantCheckState(map);
        //更改商户等级
        Map m= new HashMap<String,String>();
        m.put("merchantId",map.get("merchantId"));
        m.put("merGrade",map.get("merchantGrade"));
        int j= listMapper.updateMerGrade(m);
        return b&&(i+j == 2);
    }
    /**
     * 更改商户审核状态
     * @param map
     * @return
     */
    public int changeMerchantCheckState(Map map) {
        return mapper.changeMerchantCheckState(map);
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
        processApproveRecord.setResult(map.get("result").toString());//结果 1通过  0拒绝
        processApproveRecord.setApproveSuggestion(map.get("suggestion").toString());//审批意见
        processApproveRecord.setHandlerId(map.get("handlerId").toString());//处理人id
        processApproveRecord.setHandlerName(map.get("handlerName").toString());//处理人姓名
        processApproveRecord.setNodeId("3");//总部审核1；审核担保人担保订单2;审核商户的时候生成3    记录在哪里生成的审核意见
        int num = mapperOther.insertSelective(processApproveRecord);
        return num>0;
    }

    /**
     * 更改反欺诈状态
     * @param map
     * @return
     */
    public int changeFanQiZhaState(Map map){
        return  mapper.updateFanQiZhaState(map);
    }

    public ProcessApproveRecord getSuggestion(Map map){
        return mapper.selectSuggestion(map);
    }

}
