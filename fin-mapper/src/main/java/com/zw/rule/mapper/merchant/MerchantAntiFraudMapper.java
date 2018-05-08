package com.zw.rule.mapper.merchant;

import com.zw.rule.merchant.Merchant;

import java.util.List;
import java.util.Map;

/**
 * Created by zoukaixuan on 2018/1/9.
 */
public interface MerchantAntiFraudMapper {
    //获取所有反欺诈进行中的商户
    List<Merchant> selectAllAntifraudMerchants (Map map);
    //更改商户反欺诈状态
    int updateMerchantFanQiZhaState(Map map);
}
