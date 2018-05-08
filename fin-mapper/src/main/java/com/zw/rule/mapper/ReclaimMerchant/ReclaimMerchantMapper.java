package com.zw.rule.mapper.ReclaimMerchant;

import com.zw.rule.reclaimMerchant.ReclaimMerchant;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/12/20.
 */
public interface ReclaimMerchantMapper {
    //获取全部的商户信息
    List<ReclaimMerchant> getMerchantList(Map map);
//    //添加商户信息
    int addMerchant(ReclaimMerchant merchant);
    int updateMerchant(ReclaimMerchant merchant);

    ReclaimMerchant getById(String id);
    int updateStatus(ReclaimMerchant merchant);

    int deleteMerchant(String id);

    List<ReclaimMerchant> getMerchantListByDepId(Map map);

}
