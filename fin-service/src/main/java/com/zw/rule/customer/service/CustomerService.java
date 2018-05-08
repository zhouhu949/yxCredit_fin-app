package com.zw.rule.customer.service;

import com.zw.rule.answer.po.Answer;
import com.zw.rule.customer.po.*;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.servicePackage.po.ServicePackageOrder;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    List getCustomerList(ParamFilter paramFilter);

    //客户订单关联查得金额和注册时间之类的
    List<Map> getCustomerAndOrder(ParamFilter paramFilter);

    //客户提额明细
    List<Map> getQuotaList(ParamFilter paramFilter);

    //修改客户密码
    int updatePwd(Map param);

    //客户图片资料
    List<CustomerImage> getCustomerImage(String customerId);
    //订单图片资料
    List getCustomerImageOrderId(String orderId);

    //客户工作信息
    List<CustomerJob> getCustomerJob(String customerId);

    //客户联系人详情
    List<CustomerLinkman> getCustomerLinkMan(String customerId);

    //客户基本信息
    List getCustomerPerson(String customerId);
    //客户职业信息
    List getCustomerOccupation(String customerId,String type);

    //客户职业信息
    List getCustomerOccupation(String customerId);

    //获取信问结果
    List<Answer> getAnswerList(Map map);

    //获取订单信息
    Order selectByPrimaryKey(String orderId);

    //客户房产信息
    List<CustomerProperty> getCustomerProperty(String customerId);
    List<CustomerExpend> getCustomerIncome(String customerId);

    //客户装修信息
    List<CustomerRenovation> getCustomerRenovation(String customerId);

    //添加意向客户
    Boolean addCustomer(Customer customer);

    //查询一条意向客户
    Customer findOne(String customerId);

    //更新意向客户
    Boolean updateCustomer(Customer customer);

    //跟进记录表
    List<FollowUp> getFollow(Map map);

    //添加跟进记录
    Boolean addFollow(FollowUp followUp);

    Map getCustomerInfo(String userId);

    List<Map> getCustInfo(String crmOrderId);
    //获取工薪者
    CustomerEarner getJobEarnerInfo(String customerId);
    //获取经营者
    CustomerManager getJobManagerInfo(String customerId);
    //获取自由职业者
    CustomerFreelance getJobFreelanceInfo(String customerId);
    //获取学生
    CustomerStu getJobStuInfo(String customerId);
    //获取退休工人
    CustomerRetire getJobRetireInfo(String customerId);

    MagCustomerLive getCustomerLive(String customerId);

    CustomerDeviceInfo getCustomerDeviceInfo(String orderId);

    Customer getCustomer(String customerId);

    MagCustomerContact getContact(String customerId);
   //关联订单获取客户
    List getCustomerLists(Map map);

    int updateByPrimaryKey(Customer customer);

    Map getDepartmentInfo();

    //获取订单服务包
    List<ServicePackageOrder> getServicePackageOrderList(Map map);

    //更改客户电话号码(同时更改用户表和客户表)
    boolean changeCustomerUserTel(Map map);

    //查询现金贷的公司
    Map getXJCompany();
}
