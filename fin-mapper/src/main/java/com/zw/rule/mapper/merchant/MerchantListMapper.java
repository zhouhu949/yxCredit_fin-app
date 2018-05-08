package com.zw.rule.mapper.merchant;

import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchant.MerchantAccountRel;
import com.zw.rule.merchant.MerchantBasicInformation;
import com.zw.rule.merchant.MerchantImgRel;
import com.zw.rule.merchantManagement.MerchantGrade;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/25.
 */
public interface MerchantListMapper {
    //查询商户列表 返回商户集合
    List<Merchant> selectAllMerchants(Map map);

    //添加一个商户
    int insertOnerMerchant(Map map);

    //提交审核
    int updateCheckState(Map map);

    //添加商户关联的账户信息
    int insertAccountToMerchant(MerchantAccountRel merchantAccountRel);

    //给商户关联图片，其实就是往关联表里面添加数据
    int insertImgsTomerchant(Map map);

    //查询单个商户基本信息,根据商户的id
    MerchantBasicInformation selectMerchantBasicInformation(Map map);

    //根据商户id获取商户的账户信息
    List<MerchantAccountRel> selectMerchantAccounts(Map map);

    //根据商户id查询商户关联的图片信息
    List<MerchantImgRel> selectMerchantImgs(Map map);

    //删除该商户关联的所有图片
    int deleteMerchantImgsById(String merchantId);

    //更该商户基本信息
    int updateOnerMerchantById(Map map);

    //更改商户账户信息
    int updateMerchantAccounts(MerchantAccountRel merchantAccountRel);

    //根据商户id更改对私主账号
    int updateMerchantMainPriviteId(Map map);

    //激活或者冻结
    int updateActivateOrFreeze(Map map);

    //查询所有商户等级
    List<MerchantGrade> selectAllMerchantGrade();

    //修改商户等级
    int updateMerGrade(Map map);

    //查询所有未被冻结的办单员
    List<OfficeClerkManager> selectAllSalesman(Map map);

    //绑定办单员
    int insertBidingSalesmans(com.zw.rule.officeClerkEntity.MerchantSalemanRel merchantSalemanRel);

    //查询该商户下的办单员
    List<OfficeClerkManager> selectAllSalesmanFromMerchant(Map map);

    //解除绑定或者再次绑定
    int changeMerchantSalesmanRelState(Map map);

    //获取商户放款账号
    MerchantAccountRel getLoanAccount(String merPrivateAccountid);

}
