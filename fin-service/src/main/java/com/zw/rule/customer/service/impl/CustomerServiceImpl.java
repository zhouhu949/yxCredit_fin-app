package com.zw.rule.customer.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.base.util.MD5Utils;
import com.zw.rule.answer.po.Answer;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.mapper.answerMapper.AnswerMapper;
import com.zw.rule.mapper.customer.*;
import com.zw.rule.mapper.servicePackage.ServicePackageOrderMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.servicePackage.po.ServicePackageOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          小窝
 *Filename：
 *          客户业务逻辑
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/20
 ********************************************************/
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustomerDeviceInfoMapper customerDeviceInfoMapper;
    @Resource
    private CustomerImageMapper customerImageMapper;
    @Resource
    private CustomerLinkmanMapper customerLinkmanMapper;
    @Resource
    private CustomerPersonMapper customerPersonMapper;
    @Resource
    private CustomerPropertyMapper customerPropertyMapper;
    @Resource
    private CustomerExpendMapper customerExpendMapper;
    @Resource
    private FollowUpMapper followUpMapper;
    @Resource
    private CustomerStuMapper customerStuMapper;
    @Resource
    private CustomerFreelanceMapper customerFreelanceMapper;
    @Resource
    private CustomerManagerMapper customerManagerMapper;
    @Resource
    private CustomerEarnerMapper customerEarnerMapper;
    @Resource
    private CustomerJobMapper customerJobMapper;
    @Resource
    private CustomerRetireMapper customerRetireMapper;
    @Resource
    private MagCustomerLiveMapper customerLiveMapper;
    @Resource
    private MagCustomerContactMapper customerContactMapper;
    @Resource
    private ServicePackageOrderMapper servicePackageOrderMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public List getCustomerList(ParamFilter paramFilter) {
//        int resultCount = customerMapper.countCustomers(paramFilter.getParam());
//        paramFilter.getPage().setResultCount(resultCount);
        List customers = customerMapper.getCustomers(paramFilter);
        return customers;
    }

    @Override
    public List<Map> getCustomerAndOrder(ParamFilter paramFilter) {
        return customerMapper.getCustomerAndOrder(paramFilter);
    }

    @Override
    public List<Map> getQuotaList(ParamFilter paramFilter) {
        return customerMapper.getQuotaList(paramFilter);
    }


    @Override
    public int updatePwd(Map param) {
        try {
            param.put("passwd", MD5Utils.GetMD5Code(param.get("lblpwd1").toString()));
        }catch (Exception e){
            return  0;
        }
        return customerMapper.updatePwd(param);
    }

    @Override
    public List getCustomerImage(String customerId) {
        return customerImageMapper.getCustomerImage(customerId);
    }

    @Override
    public List getCustomerImageOrderId(String orderId) {
        return customerImageMapper.getCustomerImageOrderId(orderId);
    }
    @Override
    public List getCustomerJob(String customerId) {
        return customerJobMapper.getCustomerJob(customerId);
    }

    @Override
    public List getCustomerLinkMan(String customerId) {
        return customerLinkmanMapper.getCustomerLinkMan(customerId);
    }

    @Override
    public List getCustomerPerson(String customerId) {
        return customerPersonMapper.getCustomerPerson(customerId);
    }

    @Override
    public List getCustomerOccupation(String customerId,String type) {
        if("1".equals(type)){//工薪者
            return customerEarnerMapper.getCustomerEarnerList(customerId);
        }else if("2".equals(type)){//经营者
            return customerManagerMapper.getCustomerManagerList(customerId);
        }else if("3".equals(type)){//自由职业者
            return customerFreelanceMapper.getCustomerFreelanceList(customerId);
        }else if("4".equals(type)){//在读学生
            return customerStuMapper.getCustomerStuList(customerId);
        }else if("5".equals(type)){//退休人员
            return customerRetireMapper.getCustomerRetireList(customerId);
        }
        return null;
    }
    @Override
    public List getCustomerOccupation(String customerId) {
        return customerJobMapper.getCustomerJob(customerId);
    }

    //获取信问结果
    @Override
    public List<Answer> getAnswerList(Map map){
        return answerMapper.getAnswerList(map);
    }

    public Order selectByPrimaryKey(String orderId){
        return orderMapper.getOrderDel(orderId).get(0);
    }

    @Override
    public List getCustomerProperty(String customerId) {
        return customerPropertyMapper.getCustomerProperty(customerId);
    }

    @Override
    public List<CustomerExpend> getCustomerIncome(String customerId) {
        return customerExpendMapper.getCustomerExpend(customerId);
    }

    @Override
    public List getCustomerRenovation(String customerId) {
        return customerMapper.getCustomerRenovation(customerId);
    }

    @Override
    public Boolean addCustomer(Customer customer) {
        String uuid = UUID.randomUUID().toString();
        customer.setId(uuid);
        customer.setCreatTime(DateUtils.getDateString(new Date()));
        int num = customerMapper.insertSelective(customer);
        return num>0;
    }

    @Override
    public Customer findOne(String customerId) {
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        return customer;
    }

    @Override
    public Boolean updateCustomer(Customer customer) {
        int num = customerMapper.updateByPrimaryKeySelective(customer);
        return num>0;
    }

    @Override
    public List<FollowUp> getFollow(Map map) {
        return followUpMapper.getFollowUp(map);
    }

    @Override
    public Boolean addFollow(FollowUp followUp) {
        String uuid = UUID.randomUUID().toString();
        followUp.setId(uuid);
        followUp.setCreateTime(DateUtils.getDateString(new Date()));
        int num = followUpMapper.insertSelective(followUp);
        return num>0;
    }

    @Override
    public Map getCustomerInfo(String userId) {
        return customerMapper.getCustomerInfo(userId);
    }

    @Override
    public List<Map> getCustInfo(String crmOrderId) {
        return customerMapper.getCustInfo(crmOrderId);
    }
    @Override
    public List getCustomerLists(Map map){
        List customers = customerMapper.getCustomerList(map);
        return customers;
    }

    public CustomerEarner getJobEarnerInfo(String customerId){
        CustomerEarner customerEarner = customerEarnerMapper.getCustomerEarner(customerId);
        return customerEarner;
    }
    public CustomerManager getJobManagerInfo(String customerId){
        CustomerManager customerManager = customerManagerMapper.getCustomerManager(customerId);
        return customerManager;
    }
    public CustomerFreelance getJobFreelanceInfo(String customerId){
        CustomerFreelance customerFreelance = customerFreelanceMapper.getCustomerFreelance(customerId);
        return customerFreelance;
    }
    public CustomerStu getJobStuInfo(String customerId){
        CustomerStu customerStu = customerStuMapper.getCustomerStu(customerId);
        return customerStu;
    }
    public CustomerRetire getJobRetireInfo(String customerId){
        CustomerRetire customerRetire = customerRetireMapper.getCustomerRetire(customerId);
        return customerRetire;
    }
    public MagCustomerLive getCustomerLive(String customerId){
        MagCustomerLive customerLive = customerLiveMapper.selectByCustomerId(customerId);
        return customerLive;
    }

    public CustomerDeviceInfo getCustomerDeviceInfo(String orderId){
        CustomerDeviceInfo customerDeviceInfo = customerDeviceInfoMapper.selectByOrderId(orderId);
        return customerDeviceInfo;
    }
    public Customer getCustomer(String customerId){
        return customerMapper.selectByPrimaryKey(customerId);
    }

    public MagCustomerContact getContact(String customerId){
        return customerContactMapper.selectByCustomerId(customerId);
    }

    @Override
    public int updateByPrimaryKey(Customer customer){
        return customerMapper.updateCallRecordUrl(customer);
    }

    @Override
    public  Map getDepartmentInfo(){
        return customerMapper.getDepartmentInfo();
    }

    //获取订单服务包
    @Override
    public List<ServicePackageOrder> getServicePackageOrderList(Map map){
        return  servicePackageOrderMapper.getServicePackageOrderList(map);
    }

    /**
     * 更改客户电话号码(同时更改用户表和客户表)
     * @param map
     * @return
     */
    public boolean changeCustomerUserTel(Map map) {
        List<AppUser> user = customerMapper.getAppUserByTel(map);
        if(user.size()>0 ){
            return false;
        }else{
            int i= customerMapper .updateTelByCustomerId(map);
            int j= customerMapper.updateTelByUserId(map);
            return i+j>1;
        }
    }

    /**
     * 获取现金分期的公司(秒付金服)
     * @return
     */
    public Map getXJCompany(){
        return customerMapper.getXJCompany();
    }
}
