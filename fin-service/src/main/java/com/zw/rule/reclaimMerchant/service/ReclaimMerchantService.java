package com.zw.rule.reclaimMerchant.service;


import com.zw.rule.reclaimMerchant.ReclaimMerchant;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/12/20.
 */
public interface ReclaimMerchantService {
    //获取全部的商户信息
    List<ReclaimMerchant> getMerchantList(Map map);

    //添加商户信息
   int addMerchant(ReclaimMerchant merchant);

    int updateMerchant(ReclaimMerchant merchant);

    ReclaimMerchant getById(String id);
    int updateStatus(ReclaimMerchant merchant);
    int deleteMerchant(String id);
    /*用于验证数据库是够存在该公司*/
    List<ReclaimMerchant> getMerchantListByDepId(Map map);
}
