package com.zw.rule.mapper.merchant;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchant.MerchantBasicInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/8.
 */
public interface MerchantCheckMapper {
    //获取商户审核列表
    List<Merchant> selectCheckMerchantList(Map map);
    //审核一个商户的方法(添加审核成功失败数据)
    int  changeMerchantCheckState(Map map);
    //发起发欺诈(更改反欺诈状态)
    int updateFanQiZhaState(Map map);
    //查询审核意见方法
    ProcessApproveRecord selectSuggestion(Map map);
//    //查询商户信息+审批意见
//    MerchantBasicInformation selectMerchantBasicInformation(Map map);

}
