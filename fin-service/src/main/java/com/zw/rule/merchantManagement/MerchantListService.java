package com.zw.rule.merchantManagement;

import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchant.MerchantAccountRel;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/25.
 */
public interface MerchantListService {
    List<Merchant> getAllMerchants(Map map);

    //添加商户
    int addMerchant(Map map);

    //提交审核
    int addMerchantToCheck(Map map);

    //根据商户id获取单个商户的所有信息
    Map getMerchantById(Map map);

    //修改商户信息(基本信息，图片信息)
    int editMerchantById(Map map);

    //修改商户账户信息(法人 委托人 公共)
    int editAccountToMerchant(Map map);

    //激活或者冻结
    int setActivateOrFreeze(Map map);

    //查询所有的商户等级
    List<MerchantGrade> getAllMerchantGrade();

    //修改商户等级
    int changeMerchantGrade(Map map);

    //查询所有办单员
    List<OfficeClerkManager> getAllSalesman(Map map);

    //绑定给商户绑定办单员
    int bidingSalesmanToMerchant(Map map);

    //查询该商户下的所有办单员（包括曾经绑定过的）
    List<OfficeClerkManager> getAllMerchantSalesman(Map map);

    //解除绑定或者再次绑定
    int bidingOrCalcelSalesman(Map map);
}

