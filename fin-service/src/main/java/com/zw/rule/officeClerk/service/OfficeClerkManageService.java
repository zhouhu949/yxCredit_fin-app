package com.zw.rule.officeClerk.service;


import com.zw.rule.customer.po.Order;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.officeClerkEntity.MerchantSalemanRel;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.officeClerkEntity.SalesmanImgRel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 * 办单员管理服务层接口
 */
public interface OfficeClerkManageService {
    //查询所有办单员信息返回值集合
    public List<OfficeClerkManager> getAllOCManager(Map map);
    //添加一个办单员
    public int addOfficeClerkManager(OfficeClerkManager manager);
    //查询单个办单员信息
    public OfficeClerkManager getOneClerkManagerById(String id);
    //修改单个办单员信息
    public int editOfficeClerkManager(OfficeClerkManager manager);
    //更改办单员冻结信息
    public int changeOfficeClerkState(Map map);
    //重置办单员密码resertPasswd
    public int resertPasswd(Map map);
    //获取所有的办单员电话号码
    public List<String> getAllOfficeClerkTel();
    //查询所有办单员关联的商户(门下的门店)
    public List<Merchant> getAllSalesmanMerchant(String salesmanId);
    //获取所有可以被办单员关联到门下的商户(去除他之前已经关联过的门店)
    public List<Merchant> getAllCanBeManagerMerchants(String salesmanId);
    //添加商户给办单员(其实就是添加关联表数据)
    public int addMerchantToSalesman(Map map);
    //解除商户跟办单员的绑定（其实就是将这条数据的关联状态改成1）
    public int removeMerchantFromSalesman(Map map);
    //办单员再次绑定该商户(绑定之前曾经绑定过但是又解除绑定的商户)
    public int addSameMerchantToSalesmanAgain(Map map);
    //查询办单员总数
    public int getNumFromSalesman();
    //根据办单员id来查询办单员办的订单
    public List<Order> getOrdersFromSalesman(Map map);
    //给办单员添加图片
    public int addImgToSalesman(SalesmanImgRel salesmanImgRel);
    //查询办单员的图片
    public List<SalesmanImgRel> getSalesmanImages(String salesmanId);
    //更改办单员图片地址
    public int changeSalesmanImg(Map map);


    //获取身份证号在数据库中的条数
    public  int getIdcardCount(String idcard);
    //根据身份证号来查询办单员实体
    public List<OfficeClerkManager> getSalesmanByIdcard(String idcard);
    //根据手机号查询办单员实体
    public List<OfficeClerkManager> getSalesmansByTel(String tel);
    //根据办单员账号查询办单员实体
    public List<OfficeClerkManager> getSalesmansByEmployeeNum(String EmployeeNum);
    //交叉查询
    public List<OfficeClerkManager> getSalesmansByTelWhereEmployeeNum(String tel);
    public List<OfficeClerkManager> getSalesmansByEmployeeNumWhereTel(String EmployeeNum);

    //根据部门id查询实体类
    public Branch getBranch(String  id);
    //传入部门实体返回公司实体
    Branch getGongSiName(Branch branch);
}
