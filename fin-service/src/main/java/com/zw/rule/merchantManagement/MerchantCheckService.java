package com.zw.rule.merchantManagement;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchant.MerchantBasicInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/8.
 * 商户审核服务层接口
 */
public interface MerchantCheckService {
    //商户审核列表
    public List<Merchant> getCheckMerchantList(Map map);
    //审核商户的方法 就是更改商户状态(审核通过 state改成2 审核拒绝 state改成3)
    public int changeMerchantCheckState(Map map);
    //审批意见
    boolean writeSuggestion(Map map);
    //更改反欺诈状态
    int changeFanQiZhaState(Map map);
    //查询审批意见
    ProcessApproveRecord getSuggestion(Map map);
    //审核商户方法集成
    boolean auditOneMerchantSer(Map map);

}
