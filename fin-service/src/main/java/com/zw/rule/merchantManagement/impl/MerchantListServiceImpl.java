package com.zw.rule.merchantManagement.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.mapper.merchant.MerchantListMapper;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.merchant.MerchantAccountRel;
import com.zw.rule.merchantManagement.MerchantGrade;
import com.zw.rule.merchantManagement.MerchantListService;
import com.zw.rule.officeClerkEntity.MerchantSalemanRel;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/25.
 */
@Service
public class MerchantListServiceImpl implements MerchantListService {
    @Resource
    private MerchantListMapper mapper;

    /**
     * 获取全部的商户信息返回 商户集合
     *
     * @param map
     * @return
     */
    public List<Merchant> getAllMerchants(Map map) {
        return mapper.selectAllMerchants(map);
    }

    /**
     * 添加商户
     * @param map
     * @return
     */
    @Transactional
    public int addMerchant(Map map) {
        map.put("id", UUID.randomUUID().toString());
        map.put("state", "0");//初始状态设置为未激活(0)
        map.put("creatTime", DateUtils.getCurrentTime().toString());//
//        map.put("checkState",0);//审核状态 审核状态 0-待审核(默认) 1-审核中 2-审核通过 3-审核拒绝
        map.put("fanQiZhaState",0);//反欺诈状态 0-未发起(默认) 1-反欺诈进行中(反欺诈进行中无法不能审核操作) 2-反欺诈结束'
        //处理图片-->图片类型：0-身份证账面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像
        //0-身份证正面
        List<String> idcardZhengmianList = (List<String>) map.get("idcardZhengmian");
        addImgsToMerchant(idcardZhengmianList, map.get("id").toString(), "0");
        //1-身份证反面
        List<String> idcardFanmianList = (List<String>) map.get("idcardFanmian");
        addImgsToMerchant(idcardFanmianList, map.get("id").toString(), "1");
        //2-营业执照
        List<String> yinyezhizhaoList = (List<String>) map.get("yinyezhizhao");
        addImgsToMerchant(yinyezhizhaoList, map.get("id").toString(), "2");
        //3-门头logo照
        List<String> touxiangLogoList = (List<String>) map.get("touxiangLogo");
        addImgsToMerchant(touxiangLogoList, map.get("id").toString(), "3");
        //4-室内照
        List<String> shineiList = (List<String>) map.get("shinei");
        addImgsToMerchant(shineiList, map.get("id").toString(), "4");
        //5-收银台照
        List<String> shouyintaiList = (List<String>) map.get("shouyintai");
        addImgsToMerchant(shouyintaiList, map.get("id").toString(), "5");
        //6-街景照
        List<String> jiejingList = (List<String>) map.get("jiejing");
        addImgsToMerchant(jiejingList, map.get("id").toString(), "6");
        //7-法人银行卡照
        List<String> farenyinhangkaList = (List<String>) map.get("farenyinhangka");
        addImgsToMerchant(farenyinhangkaList, map.get("id").toString(), "7");
        //8-收款委托书
        List<String> shoukuanweituoshuList = (List<String>) map.get("shoukuanweituoshu");
        addImgsToMerchant(shoukuanweituoshuList, map.get("id").toString(), "8");
        //法人账户信息
        MerchantAccountRel faren = new MerchantAccountRel();
        faren.setId(UUID.randomUUID().toString());//id
        faren.setMerchantId(map.get("id").toString());//商户id
        faren.setName(map.get("person_name").toString());//法人姓名
        faren.setTel(map.get("person_tel").toString());//法人电话
        faren.setIdcard(map.get("person_idcard").toString());//法人身份证号
        faren.setAccount(map.get("persion_bank_account").toString());//法人账号(银行卡号)
        faren.setProvinceId(map.get("persion_account_province").toString());//法人银行卡省份id
        faren.setProvinceName(map.get("persion_account_province_name").toString());//法人银行卡省份名
        faren.setCityId(map.get("persion_account_city").toString());//法人银行卡城市id
        faren.setCityName(map.get("persion_account_city_name").toString());//法人银行卡城市名
        faren.setBankName(map.get("persion_opening_bank").toString());//法人开户行全名
        faren.setType("0");//账号类型： 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
        faren.setBankHead(map.get("add_persion_opening_bank_head").toString());//银行名：如-建设银行，浦发银行
        faren.setBankHeadId(map.get("add_persion_opening_bank_head_id").toString());//银行id
        //商户信息表内存一条商户对私主账号的id（默认为法人账户）
        map.put("mer_private_account_id", faren.getId());
        //添加商户基本信息(文字)
        int m0 = mapper.insertOnerMerchant(map);
        //添加法人账户信息
        int m1 = mapper.insertAccountToMerchant(faren);

        //收款委托人信息
        MerchantAccountRel bailor = new MerchantAccountRel();
        bailor.setId(UUID.randomUUID().toString());
        bailor.setMerchantId(map.get("id").toString());
        bailor.setName(map.get("money_bailor_name").toString());
        bailor.setTel(map.get("money_bailor_tel").toString());
        bailor.setIdcard(map.get("money_bailor_idcard").toString());
        bailor.setAccount(map.get("money_bailor_account").toString());
        bailor.setProvinceId(map.get("bailor_account_province").toString());
        bailor.setProvinceName(map.get("bailor_account_province_name").toString());
        bailor.setCityId(map.get("bailor_account_city").toString());
        bailor.setCityName(map.get("bailor_account_city_name").toString());
        bailor.setBankName(map.get("money_bailor_oppen_bank").toString());
        bailor.setType("1");//账号类型： 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
        bailor.setBankHead(map.get("add_money_bailor_oppen_bank_head").toString());//银行名
        bailor.setBankHeadId(map.get("add_money_bailor_oppen_bank_head_id").toString());//银行id
        int m2 = mapper.insertAccountToMerchant(bailor);

        //对公账户
        MerchantAccountRel pubAccount = new MerchantAccountRel();
        pubAccount.setId(UUID.randomUUID().toString());
        pubAccount.setMerchantId(map.get("id").toString());
        pubAccount.setName(map.get("public_account_name").toString());
        pubAccount.setAccount(map.get("public_bank_account").toString());
        pubAccount.setProvinceId(map.get("public_bank_province_id").toString());
        pubAccount.setProvinceName(map.get("public_bank_province_name").toString());
        pubAccount.setCityId(map.get("public_bank_city_id").toString());
        pubAccount.setCityName(map.get("public_bank_city_name").toString());
        pubAccount.setBankName(map.get("public_opening_bank").toString());
        pubAccount.setType("2");//账号类型： 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
        pubAccount.setBankHead(map.get("add_public_opening_bank_head").toString());//银行名
        pubAccount.setBankHeadId(map.get("add_public_opening_bank_head_id").toString());//银行id
        int m3 = mapper.insertAccountToMerchant(pubAccount);

        return m0 + m1 + m2 + m3;//文字信息插入数量+法人账户插入数量+收款委托人插入数量+对公账户插入
    }

    /**
     * 给商户关联图片的功能方法pro
     * @param imgList
     * @param merchantId
     * @param imgType
     */
    public void addImgsToMerchant(List imgList, String merchantId, String imgType) {//传入图片地址的集合，商户id，图片类型
        for (int i = 0; i < imgList.size(); i++) {
            if (!imgList.get(i).toString().equals("")) {
                Map<String, String> imgMap = new HashMap<String, String>();
                //关联表id
                imgMap.put("id", UUID.randomUUID().toString());
                imgMap.put("merchantId", merchantId);
                imgMap.put("imgUrl", imgList.get(i).toString());
                imgMap.put("imgType", imgType);
                mapper.insertImgsTomerchant(imgMap);
            }
        }
    }

    /**
     * 根据商户id获取单个商户的基本信息
     *
     * @param map
     * @return
     */
    public Map getMerchantById(Map map) {
        Map<String, Object> merchantMap = new HashedMap();
        //商户基本信息
        merchantMap.put("merchantBasicInformation", mapper.selectMerchantBasicInformation(map));
        //该商户的所有账户信息
        merchantMap.put("merchantAccounts", mapper.selectMerchantAccounts(map));
        //该商户的所有关联图片
        merchantMap.put("merchantImgs", mapper.selectMerchantImgs(map));
        return merchantMap;
    }

    /**
     * 修改商户信息(其中调用了修改商户信息，商户关联的账户信息，商户关联的图片信息)
     *
     * @param map
     * @return
     */
    public int editMerchantById(Map map) {
        map.put("alterTime", DateUtils.getCurrentTime().toString());
        //修改商户的文字信息
        int m0 = mapper.updateOnerMerchantById(map);
        int m1 = editImgsToMerchant(map);
        return m0 + m1;
    }

    /**
     * 修改商户关联图片信息
     *
     * @param map
     * @return
     */
    public int editImgsToMerchant(Map map) {//就是先删除之前存在的关联记录 再次重新添加数据
        //1.先删除之前关联的所有图片
        int i = mapper.deleteMerchantImgsById(map.get("merchantId").toString());
        //2.重新添加图片关联
        //0-身份证正面
        List<String> idcardZhengmianList = (List<String>) map.get("idcardZhengmian");
        addImgsToMerchant(idcardZhengmianList, map.get("merchantId").toString(), "0");
        //1-身份证反面
        List<String> idcardFanmianList = (List<String>) map.get("idcardFanmian");
        addImgsToMerchant(idcardFanmianList, map.get("merchantId").toString(), "1");
        //2-营业执照
        List<String> yinyezhizhaoList = (List<String>) map.get("yinyezhizhao");
        addImgsToMerchant(yinyezhizhaoList, map.get("merchantId").toString(), "2");
        //3-门头logo照
        List<String> touxiangLogoList = (List<String>) map.get("touxiangLogo");
        addImgsToMerchant(touxiangLogoList, map.get("merchantId").toString(), "3");
        //4-室内照
        List<String> shineiList = (List<String>) map.get("shinei");
        addImgsToMerchant(shineiList, map.get("merchantId").toString(), "4");
        //5-收银台照
        List<String> shouyintaiList = (List<String>) map.get("shouyintai");
        addImgsToMerchant(shouyintaiList, map.get("merchantId").toString(), "5");
        //6-街景照
        List<String> jiejingList = (List<String>) map.get("jiejing");
        addImgsToMerchant(jiejingList, map.get("merchantId").toString(), "6");
        //7-法人银行卡照
        List<String> farenyinhangkaList = (List<String>) map.get("farenyinhangka");
        addImgsToMerchant(farenyinhangkaList, map.get("merchantId").toString(), "7");
        //8-收款委托书
        List<String> shoukuanweituoshuList = (List<String>) map.get("shoukuanweituoshu");
        addImgsToMerchant(shoukuanweituoshuList, map.get("merchantId").toString(), "8");
        return 1;
    }

    /**
     * 修改商户关联账户信息
     *
     * @param map
     * @return
     */
    public int editAccountToMerchant(Map map) {
        //法人账户信息
        MerchantAccountRel faren = new MerchantAccountRel();
        faren.setId(map.get("personId").toString());//id
        faren.setMerchantId(map.get("merchantId").toString());//商户id
        faren.setName(map.get("person_name").toString());//法人姓名
        faren.setTel(map.get("person_tel").toString());//法人电话
        faren.setIdcard(map.get("person_idcard").toString());//法人身份证号
        faren.setAccount(map.get("persion_bank_account").toString());//法人账号(银行卡号)
        faren.setProvinceId(map.get("persion_account_province").toString());//法人银行卡省份id
        faren.setProvinceName(map.get("persion_account_province_name").toString());//法人银行卡省份名
        faren.setCityId(map.get("persion_account_city").toString());//法人银行卡城市id
        faren.setCityName(map.get("persion_account_city_name").toString());//法人银行卡城市名
        faren.setBankName(map.get("persion_opening_bank").toString());//法人开户行全名
        faren.setType("0");//账号类型： 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
        faren.setBankHead(map.get("add_persion_opening_bank_manage_head").toString());//银行总行名
        faren.setBankHeadId(map.get("add_persion_opening_bank_manage_head_id").toString());//银行总行id
        //添加法人账户信息
        int m1 = mapper.updateMerchantAccounts(faren);

        //收款委托人信息
        MerchantAccountRel bailor = new MerchantAccountRel();
        bailor.setId(map.get("weituoId").toString());
        bailor.setMerchantId(map.get("merchantId").toString());
        bailor.setName(map.get("money_bailor_name").toString());
        bailor.setTel(map.get("money_bailor_tel").toString());
        bailor.setIdcard(map.get("money_bailor_idcard").toString());
        bailor.setAccount(map.get("money_bailor_account").toString());
        bailor.setProvinceId(map.get("bailor_account_province").toString());
        bailor.setProvinceName(map.get("bailor_account_province_name").toString());
        bailor.setCityId(map.get("bailor_account_city").toString());
        bailor.setCityName(map.get("bailor_account_city_name").toString());
        bailor.setBankName(map.get("money_bailor_oppen_bank").toString());
        bailor.setType("1");//账号类型： 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
        bailor.setBankHead(map.get("add_money_bailor_oppen_bank_manage_head").toString());//银行总行名
        bailor.setBankHeadId(map.get("add_money_bailor_oppen_bank_manage_head_id").toString());//银行总行id
        int m2 = mapper.updateMerchantAccounts(bailor);

        //对公账户
        MerchantAccountRel pubAccount = new MerchantAccountRel();
        pubAccount.setId(map.get("publicId").toString());
        pubAccount.setMerchantId(map.get("merchantId").toString());
        pubAccount.setName(map.get("public_account_name").toString());
        pubAccount.setAccount(map.get("public_bank_account").toString());
        pubAccount.setProvinceId(map.get("public_bank_province_id").toString());
        pubAccount.setProvinceName(map.get("public_bank_province_name").toString());
        pubAccount.setCityId(map.get("public_bank_city_id").toString());
        pubAccount.setCityName(map.get("public_bank_city_name").toString());
        pubAccount.setBankName(map.get("public_opening_bank").toString());
        pubAccount.setType("2");//账号类型： 0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
        pubAccount.setBankHead(map.get("add_public_opening_bank_manage_head").toString());//银行总行名
        pubAccount.setBankHeadId(map.get("add_public_opening_bank_manage_head_id").toString());//银行总行id
        int m3 = mapper.updateMerchantAccounts(pubAccount);

        //修改商户表中对私主账号字段
        int m4 = mapper.updateMerchantMainPriviteId(map);
        return m1 + m2 + m3 + m4;
    }

    /**
     * 激活或者冻结
     * @param map
     * @return
     */
    public int setActivateOrFreeze(Map map) {
        return mapper.updateActivateOrFreeze(map);
    }

    /**
     * 查询所有的商户等级
     * @return
     */
    public List<MerchantGrade> getAllMerchantGrade(){
        return mapper.selectAllMerchantGrade();
    }

    /**
     * 修改商户等级
     * @param map
     * @return
     */
    public int changeMerchantGrade(Map map){
       return mapper.updateMerGrade(map);
    }

    /**
     * 查询所有未被冻结的办单员
     * @param map
     * @return
     */
    public List<OfficeClerkManager> getAllSalesman(Map map){
        return mapper.selectAllSalesman(map);
    }

    /**
     * 给商户绑定办单员
     * @param map
     * @return
     */
    public int bidingSalesmanToMerchant(Map map) {
        String merchantId=map.get("merchantId").toString();
        List<String> salesmanIds=(List<String>) map.get("salesmanIds");
        for (int i=0 ;i<salesmanIds.size();i++){
            MerchantSalemanRel merchantSalemanRel =new MerchantSalemanRel();
            merchantSalemanRel.setId(UUID.randomUUID().toString());
            merchantSalemanRel.setMerchantId(merchantId);
            merchantSalemanRel.setEmployeeId(salesmanIds.get(i));
            merchantSalemanRel.setState("0");
            mapper.insertBidingSalesmans(merchantSalemanRel);
        }
        return 1;
    }

    /**
     * 查询该商户下的所有办单员（包括曾经绑定过的）
     * @param map
     * @return
     */
    public List<OfficeClerkManager> getAllMerchantSalesman(Map map){
        return mapper.selectAllSalesmanFromMerchant(map);
    }

    /**
     * 解除绑定或者再次绑定(其实就是更改关联表状态)
     * @param map
     * @return
     */
    public int bidingOrCalcelSalesman(Map map) {
        return mapper.changeMerchantSalesmanRelState(map);
    }

    /**
     * 提交审核服务层
     * @param map
     * @return
     */
    public int addMerchantToCheck(Map map){
        return mapper.updateCheckState(map);
    }
}
