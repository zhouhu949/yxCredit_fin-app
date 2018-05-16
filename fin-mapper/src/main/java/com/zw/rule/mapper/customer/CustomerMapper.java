package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.AppUser;
import com.zw.rule.customer.po.Customer;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);



    //客户订单关联查得到金额和注册时间之类的
    List<Map> getCustomerAndOrder(ParamFilter paramFilter);

    //获取客户提额明细
    List<Map> getQuotaList(ParamFilter paramFilter);

    //修改客户密码
    int updatePwd(Map map);

    int countCustomers(Map map);

    //客户装修信息--通过客户id从order表查看
    List<Map> getCustomerRenovation(String customerId);

    //商户合作的客户信息
    List<Customer> selectByMerId(ParamFilter paramFilter);
    //商户合作的客户信息数量
    int getCustomerCount(ParamFilter paramFilter);

    Map getCustomerInfo(String userId);
    //客户交接
    int change(Customer customer);

    int changeAll(List<Customer> list);

    //进行客户和订单的信息查询,查询还款信息
    List<Map> getCustInfo(String crmOrderId);

    //获取客户列表 待匹配、筹资中
    List<Customer> getFinancialCustomerList(ParamFilter paramFilter);
    //获取待放款客户
    List<Customer>getCustomerList(Map map);
    //获取已结清客户信息
    List<Customer>getCustomerByState(Map map);

    //通过客户Id获取客户
    Customer getCustomerByUserId(String userId);

    int updateCallRecordUrl(Customer record);

    Map getDepartmentInfo();
    //更改客户电话号码
    int updateTelByCustomerId(Map map);
    //更改用户表电话号码
    int updateTelByUserId(Map map);
    //根据tel查询app用户
   List<AppUser> getAppUserByTel(Map map);
    //根据客户表id查询用户id
    String selectUserIdFromCustomer(Map map);
    //查询现金分期总公司（秒付金服）
    Map getXJCompany();

    /****************************************碧友信*******************************************/

    /**
     *获取客户列表
     * @param paramFilter
     * @return
     */
    List<Customer> getCustomers(ParamFilter paramFilter);

    /**
     *根据ID获取客户信息
     * @param id
     * @return
     */
    Map getCustomerById(String id);


}