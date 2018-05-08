package com.zw.rule.merchantManagement;

import com.zw.rule.merchant.Merchant;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/9.
 */
public interface MerchantAntiFraudService {
    //获取所有得反欺诈商户
    List<Merchant> getAntiFraudMerchants(Map map);
    //添加反欺诈图片给商户
    int addAntiFraudImgsToMerchant(Map map);
    //记录备注信息
    boolean writeSuggestion(Map map);
    //更改商户反欺诈状态
    int changMerchantFanQiZhaState(Map map);
}
