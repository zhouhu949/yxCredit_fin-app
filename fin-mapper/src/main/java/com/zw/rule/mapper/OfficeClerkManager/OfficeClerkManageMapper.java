package com.zw.rule.mapper.OfficeClerkManager;

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
 * @Author 邹凯旋
 * 办单员管理mapper映射器
 */
public interface  OfficeClerkManageMapper {
    public List<OfficeClerkManager> selectAllManagers(Map map);
    public int addOfficeClerkManager(OfficeClerkManager manager);
    //查询单个办单员
    public OfficeClerkManager selectOneManagerById(String id);
    //更改单个办单员信息
    public int updateOfficeClerkManagerById(OfficeClerkManager manager);
    //更改办单员冻结信息
    public int  updateOfficeClerkManagerState(Map map);
    //重置办单员密码resertPasswdById
    public int resertPasswdById(Map map);
    //查询所有办单员的手机号
    public List<String> getAllTels();
    //查询所有办单员关联的商户(门下的门店)
    public List<Merchant> getAllMerchantsBySalesmanId(String id);
    //查询所有可以被办单员关联到门下的商户(去除他之前已经关联过的门店)
    public List<Merchant> selectAllCanBeManagerMerchants(String salesmanId);
    //添加商户给办单员(其实就是添加关联表数据)
    public int insertMerchantToSalesman(MerchantSalemanRel merchantSalemanRel);
    //解除商户跟办单员的绑定其实就是将这条数据的关联状态改成1
    public int deleteMerchantFromSalesman(Map map);
    //办单员再次绑定该商户(绑定之前曾经绑定过但是又解除绑定的商户)
    public int insertSameMerchantToSalesmanAgain(Map map);
    //获取办单员表记录总数
    public int getCountFromSalesman();
    //根据办单员id来查询办单员关联的订单
    public List<Order> getOrdersBySalesmanId(Map map);
    //给办单员添加图片，在关联表内插入数据
    public int insertImagetoSalesman(SalesmanImgRel salesmanImgRel);
    //根据办单员的id查询办单员的照片
    public List<SalesmanImgRel> getImagesListBySalesId(String salesmanId);
    //根据办单员的id和图片type来更改办单员上传的图片
    public int updateSalesmanImages(Map map);
    //测试办单员模块 查询测试办单元的订单
    public List<Order> getTestSalesmanOrders();



    //验证身份证号码唯一性
    public int selectIdcardCount(String card);
    //根据身份证号来查询办单员实体集合
    public List<OfficeClerkManager> getSalesmanByIdcard(String idcard);
    //根据手机号查询办单员实体
    public List<OfficeClerkManager> getSalesmanByTel(String tel);
    //根据办单员账号查询办单员实体
    public List<OfficeClerkManager> getSalesmanByEmployeeNum(String EmployeeNum);
    //交叉查询
    public List<OfficeClerkManager> getSalesmanByTelWhereEmployeeNum(String tel);
    public List<OfficeClerkManager> getSalesmanByEmployeeNumWhereTel(String EmployeeNum);


    //查询部门
    public Branch selectBranckById(String  id);


}
