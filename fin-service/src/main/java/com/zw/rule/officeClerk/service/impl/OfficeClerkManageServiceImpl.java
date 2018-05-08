package com.zw.rule.officeClerk.service.impl;

import com.zw.rule.customer.po.Order;
import com.zw.rule.mapper.OfficeClerkManager.OfficeClerkManageMapper;
import com.zw.rule.merchant.Merchant;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.officeClerkEntity.MerchantSalemanRel;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.officeClerkEntity.SalesmanImgRel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/28.
 * 办单员管理服务层具体方法
 */
@Service("officeClerkManageService")
public class OfficeClerkManageServiceImpl implements OfficeClerkManageService {
    /**
     * 查询所有的办单员信息
     * @return List<OfficeClerkManager>
     */
    @Resource
    private OfficeClerkManageMapper mapper;
    public List<OfficeClerkManager> getAllOCManager(Map map){
        //System.out.print(mapper.selectAllManagers());
        return mapper.selectAllManagers(map);
    }

    /**
     * 添加一个办单员服务层实现方法
     */
    public int addOfficeClerkManager(OfficeClerkManager manager){
        return(mapper.addOfficeClerkManager(manager));
    }

    /**
     * 获取单个办单员信息
     */
    public OfficeClerkManager getOneClerkManagerById(String id){
        OfficeClerkManager manager=mapper.selectOneManagerById(id);
        System.out.print(manager);
        return manager;
    }

    /**
     * 编辑办单员个人信息
     * @param manager
     * @return
     */
    public int editOfficeClerkManager(OfficeClerkManager manager){
        int i=mapper.updateOfficeClerkManagerById(manager);
        return (i);
    }

    /**
     * 更改办单员冻结情况信息
     * @param map
     * @return
     */
    public int changeOfficeClerkState(Map map){
        return (mapper.updateOfficeClerkManagerState(map));
    }

    /**
     *重置密码
     * @param map
     * @return
     */
    public int resertPasswd(Map map){
       return (mapper.resertPasswdById(map));
    }

    /**
     * 获取所有的办单员电话号码
     * @return
     */
    public List<String> getAllOfficeClerkTel(){
        return mapper.getAllTels();
    }

    /**
     * 查询所有办单员关联的商户(门下的门店)
     */
    public List<Merchant> getAllSalesmanMerchant(String salesmanId){
        return mapper.getAllMerchantsBySalesmanId(salesmanId);
    }

    /**
     * 获取所有可以被办单员关联到门下的商户(去除他之前已经关联过的门店)
     * @param salesmanId
     * @return
     */
    public List<Merchant> getAllCanBeManagerMerchants(String salesmanId){
        return mapper.selectAllCanBeManagerMerchants(salesmanId);
    }

    /**
     * 绑定商户给办单员 其实就是关联表加一条数据
     * @param map
     * @return
     */
    public int addMerchantToSalesman(Map map){
        MerchantSalemanRel merchantSalemanRel=new MerchantSalemanRel();
        String id= UUID.randomUUID().toString();
        String employeeId=map.get("salesmanId").toString();
        String merchantId=map.get("merchantId").toString();
        merchantSalemanRel.setId(id);
        merchantSalemanRel.setEmployeeId(employeeId);
        merchantSalemanRel.setMerchantId(merchantId);
        merchantSalemanRel.setState("0");
        return  mapper.insertMerchantToSalesman(merchantSalemanRel);
    }

    /**
     * 解除商户跟办单员的绑定(其实就是将这条数据的关联状态改成1)
     * @param map
     * @return
     */
    public int removeMerchantFromSalesman(Map map){
       return mapper.deleteMerchantFromSalesman(map);
    }

    /**
     * 办单员再次绑定该商户(绑定之前曾经绑定过但是又解除绑定的商户)
     * @param map
     * @return
     */
    public int addSameMerchantToSalesmanAgain(Map map){
            return mapper.insertSameMerchantToSalesmanAgain(map);
    }

    /**
     *查询办单员记录总数
     * @return
     */
    public int getNumFromSalesman(){
        return mapper.getCountFromSalesman();
    }
    //根据办单员id来查询办单员办的订单
    public List<Order> getOrdersFromSalesman(Map map){
        return mapper.getOrdersBySalesmanId(map);
    }

    /**
     *  给办单员添加关联图片(办单员身份证正面反面 手持等)
     * @param salesmanImgRel
     * @return
     */
    public int addImgToSalesman(SalesmanImgRel salesmanImgRel){
        return mapper.insertImagetoSalesman(salesmanImgRel);
    }

    /**
     * 根据办单员的id查询办单员的照片
     * @param salesmanId
     * @return
     */
    public List<SalesmanImgRel> getSalesmanImages(String salesmanId){
        return  mapper.getImagesListBySalesId(salesmanId);
    }

    /**
     * 更改办单员的图片地址
     * 参数是一个map 存的是办单员id 图片地址 图片类型
     * @param map
     * @return
     */
    public int changeSalesmanImg(Map map){
        return mapper.updateSalesmanImages(map);
    }




    /**
     * 查找身份证号码在数据库中的条数
     * @param idcard
     * @return
     */
    public int getIdcardCount(String idcard) {
        return mapper.selectIdcardCount(idcard);
    }

    /**
     * 根据身份证号来查询办单员实体
     * @param idcard
     * @return
     */
    public List<OfficeClerkManager> getSalesmanByIdcard(String idcard){
        return mapper.getSalesmanByIdcard(idcard);
    }

    /**
     * 根据手机号查询办单员实体
      * @param tel
     * @return
     */
    public List<OfficeClerkManager> getSalesmansByTel(String tel) {
        return mapper.getSalesmanByTel(tel);
    }

    /**
     * 根据办单员账号来查询办单员实体类
     * @param employeeNum
     * @return
     */
    public List<OfficeClerkManager> getSalesmansByEmployeeNum(String employeeNum) {
        return mapper.getSalesmanByEmployeeNum(employeeNum);
    }

    /**
     * 根据手机号是否存在与办单员账号 来查询办单员实体
     * @param tel
     * @return
     */
    public List<OfficeClerkManager> getSalesmansByTelWhereEmployeeNum(String tel) {
        return mapper.getSalesmanByTelWhereEmployeeNum(tel);
    }

    /**
     * 根据办单员账号是否存在于办单员手机号来查询办单员实体类
     * @param employeeNum
     * @return
     */
    public List<OfficeClerkManager> getSalesmansByEmployeeNumWhereTel(String employeeNum) {
        return mapper.getSalesmanByEmployeeNumWhereTel(employeeNum);
    }

    /**
     * 根据部门id查询部门实体类
     * @param
     * @return
     */
    public Branch getBranch(String  id){
        return  mapper.selectBranckById(id);
    }

    /**
     * 根据部门id判断是否为公司
     * （递归查询）
     * @return
     */
    public Branch getGongSiName(Branch branch){
        if("1".equals(branch.getType())){
        return branch;
    }else{
        return getGongSiName(getBranch(branch.getPid()));
    }
}
}
