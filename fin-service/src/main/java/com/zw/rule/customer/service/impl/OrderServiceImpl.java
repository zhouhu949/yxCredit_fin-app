package com.zw.rule.customer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.UploadFile;
import com.zw.base.util.*;
import com.zw.constants.CommonConstants;
import com.zw.jiguang.JiGuangUtils;
import com.zw.rule.SendMessage.service.SendMessageFactory;
import com.zw.rule.api.sms.service.ISmsApiService;
import com.zw.rule.api.sortmsg.MsgRequest;
import com.zw.rule.approveRecord.po.OrderOperationRecord;
import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.OrderService;
import com.zw.rule.mapper.approveRecord.ProcessApproveRecordMapper;
import com.zw.rule.mapper.customer.*;
import com.zw.rule.mapper.orderLog.OrderLogMapper;
import com.zw.rule.mapper.servicePackage.ServicePackageOrderMapper;
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.mapper.task.TaskMsgMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.orderLog.po.MagOrderLogs;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.po.User;
import com.zw.rule.service.AppDictService;
import com.zw.rule.task.po.ProcessTaskNode;
import com.zw.rule.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/21
 ********************************************************/
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private TaskMsgMapper taskMsgMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private ProcessApproveRecordMapper processApproveRecordMapper;
    @Resource
    private CustomerInvestigationMapper customerInvestigationMapper;
    @Resource
    private CustomerRespMapper customerRespMapper;
    @Resource
    private CustomerExamineMapper customerExamineMapper;
    @Resource
    private CustomerMatchingMapper customerMatchingMapper;
    @Resource
    private CustomerImageMapper customerImageMapper;
    @Resource
    private AppDictService dictService;
    @Resource
    private AppMessageMapper messageMapper;
    @Resource
    private AppUserMapper userMapper;

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    @Resource
    private OrderLogMapper orderLogMapper;

    @Autowired
    private SendMessageFactory sendMessageFactory;

    @Autowired
    private ServicePackageOrderMapper servicePackageOrderMapper;

    @Autowired
    private ISmsApiService smsApiService;

    @Override
    public Order selectById(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 初审or终审
     * @param map
     * @return
     */
    @Override
    public Boolean firstOrEndAudit(Map map) {
        Order order = new Order();
        order.setId(map.get("orderId").toString());
        order.setState(map.get("state").toString());
//        if(map.containsKey("tache")){
//            order.setTache(map.get("tache").toString());
//        }
//        if(map.containsKey("firstCredit")){
//            order.setFirstCredit(map.get("firstCredit").toString());
//        }
//        if(map.containsKey("endCredit")){
//            order.setFirstCredit(map.get("endCredit").toString());
//        }
        int num = orderMapper.firstOrEndAudit(order);
        return num>0;
    }
    @Override
    public Boolean updateReceiveId(Order order) {
        order.setAlterTime(DateUtils.getDateString(new Date()));
        int num = orderMapper.updateReceiveId(order);
        return num>0;
    }

    @Override
    public List<Order> getAlreadyOrders(ParamFilter paramFilter) {
//        int resultCount = orderMapper.countAlreadyOrders(paramFilter.getParam());
//        paramFilter.getPage().setResultCount(resultCount);
        return orderMapper.getAlreadyOrders(paramFilter);
    }

    @Override
    public List getOrderDel(String id) {
        return orderMapper.getOrderDel(id);
    }
    @Override
    public Map getTaskId(String orderId) {
        return orderMapper.getTaskId(orderId);
    }

    public void updateTask(String taskNodeId,Long userId){
//        ProcessTask processTask = new ProcessTask();
//        processTask.setId(taskNodeId);
//        processTask.setNodeState(1);//状态改为处理中
        ProcessTaskNode processTaskNode = new ProcessTaskNode();
        processTaskNode.setId(taskNodeId);
        processTaskNode.setOperateUser(userId);
        taskMsgMapper.updateNodeState(processTaskNode);
//        taskMsgMapper.updateTaskState(processTask);
    }

    @Transactional
    @Override
    public Response updateOrder(Map map) {
        Response response = orderPushAndSend(map);
        try {
            if(!CommonConstants.FAIL.equals(response.getCode())) {
                if("finalMoney".equals(map.get("proveType").toString())) {
                    Map<String, Object> orderMap = orderStateFinal(map);
                    orderMapper.updateOrderStatus(orderMap);
                    confirmationFinal(map);
                    //更新客户信息
                    Customer customer = new Customer();
                    String contractAmount = map.get("contractAmount").toString();//订单合同金额
                    String surplusContractAmount = map.get("surplusContractAmount").toString();//客户剩余合同金额
                    String customerId = map.get("customerId").toString();
                    customer.setId(customerId);
                    customer.setSurplusContractAmount(new BigDecimal(surplusContractAmount).subtract(new BigDecimal(contractAmount)));
                    customerMapper.updateByPrimaryKeySelective(customer);
                } else {
                    orderMapper.updateOrderStatus(map);
                    addApproveRecord(map);
                }
            }
        }catch (Exception e){
            return Response.error();
        }
        return response;
    }

    @Override
    public List getLoanCustomerList(Map map) {
        return orderMapper.getLoanCustomerList(map);
    }

    private Map<String, Object> orderStateFinal(Map map){
        Map<String, Object> orderMap = new HashMap<String, Object>();
        String orderId = map.get("id").toString();
        orderMap.put("id", orderId);
        orderMap.put("alterTime",map.get("alterTime").toString());
        orderMap.put("orderState",map.get("orderState"));
        String contractAmountStr = map.get("contractAmount").toString();//合同金额
        BigDecimal contractAmount = new BigDecimal(contractAmountStr);
        Map serviceFeeMap = orderMapper.getServiceFeeList(orderId);
        Map contractorMap = orderMapper.getContractorList(orderId);
        if(null != serviceFeeMap && null != contractorMap) {
            BigDecimal dateRate = new BigDecimal(serviceFeeMap.get("dateRate").toString());//日利率
            String periods = contractorMap.get("periods").toString();//期限（日）
            //还款金额//应还金额＝放款金额＋（日利息＋居间服务费率）＊合同金额＊借款期限（日）
            /*String repayMoney = (((serviceFeeRate.add(dateRate.divide(new BigDecimal(100)))).multiply(contractAmount).multiply(new BigDecimal(periods))).add(contractAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();*/
            //还款金额//应还金额＝放款金额＋日利息＊合同金额＊借款期限（日）
            String repayMoney = (((dateRate.divide(new BigDecimal(100))).multiply(contractAmount).multiply(new BigDecimal(periods))).add(contractAmount)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            orderMap.put("repayMoney",repayMoney);//还款金额
            Date repayDate = DateUtils.getDateAfter(new Date(),Integer.parseInt(periods));
            orderMap.put("repayDate", DateUtils.formatDate(repayDate,DateUtils.STYLE_2));

        }
        return orderMap;
    }

    /**
     * 放款操作日志
     * @param map
     * @return
     * @throws Exception
     */
    private Boolean confirmationFinal(Map map) {
        OrderOperationRecord orderOperationRecord = new OrderOperationRecord();
        String uuid = GeneratePrimaryKeyUtils.getUUIDKey();
        orderOperationRecord.setId(uuid);
        orderOperationRecord.setOperationTime(map.get("alterTime").toString());
        orderOperationRecord.setDescription("已放款");
        orderOperationRecord.setOperationNode(Integer.parseInt(Constants.ORDER_AUDIT_LOAN_STATE));//放款审核
        orderOperationRecord.setOperationResult(7);//放款
        orderOperationRecord.setEmpId(map.get("handlerId").toString());
        orderOperationRecord.setEmpName(map.get("handlerName").toString());
        orderOperationRecord.setOrderId(map.get("id").toString());//订单id
        orderOperationRecord.setAmount(new BigDecimal(map.get("contractAmount").toString()));
        orderOperationRecord.setStatus(1);//1有效，0无效
        int num = processApproveRecordMapper.insertOrderOperRecord(orderOperationRecord);
        return true;
    }


    /**
     * 订单推送/短信
     * 订单状态【1.待申请、2.审核中、3.待签约、4.待放款、5.待还款、6.已结清、7.已取消、8.审批拒绝，9：已放弃】
     */
    private Response orderPushAndSend(Map map){
        //根据客户id获取订单手机号
        try {
            Customer customer = customerMapper.selectByPrimaryKey(map.get("customerId").toString());
            if(null != customer) {
                String orderState = map.get("orderState").toString();
                AppMessage message = new AppMessage();
                message.setOrderId(map.get("id").toString());
                String currentDate = DateUtils.getNowDate();
                String uuid = GeneratePrimaryKeyUtils.getUUIDKey();
                message.setId(uuid);
                message.setAlterTime(currentDate);
                message.setCreatTime(currentDate);
                message.setUserId(customer.getUserId());

                message.setState(Constants.MESSAGE_UNREAD_STATE);//未读
                message.setPushState(Constants.MESSAGE_PUSH_FAILURE_STATE);
                message.setOrderState(orderState);
                message.setMsgType("1");
                message.setUpdateState("1");
                String registration_id="1111";
                String messageTitle = "";
                String messageContent = "";
                switch (orderState){
                    case Constants.ORDER_AUDIT_PASS_STATE://审核通过
                        messageContent = dictService.getDictInfo("消息内容","SPTG");
                        messageTitle = "审批通过";
                        break;
                    case  Constants.ORDER_AUDIT_FAILURE_STATE://审核拒绝
                        messageContent = dictService.getDictInfo("消息内容","SPJJ");
                        messageContent = messageContent + map.get("approveSuggestion").toString();
                        messageTitle = "审批拒绝";
                        break;
                    case  Constants.ORDER_AUDIT_LOAN_STATE://放款通过
                        messageContent = dictService.getDictInfo("消息内容","FKCG");
                        messageTitle = "放款成功";
                        break;
                }
                messageContent = TemplateUtils.getContent(messageContent, map);
                //激光推送
                if(JiGuangUtils.alias(messageTitle,messageContent,registration_id)){
                    message.setPushState(Constants.MESSAGE_PUSH_SUCCESS_STATE);
                }
               MsgRequest msgRequest = new MsgRequest();
                msgRequest.setContent(messageContent);
                msgRequest.setPhone(customer.getTel());
                //发送手机短信
                String result = smsApiService.sendSms(msgRequest);
                if(StringUtils.isBlank(result)){
                    return Response.error();
                }
                Response response = JSONObject.parseObject(result, Response.class);
                if(Constants.SMS_FAILURE_STATE.equals(response.getRes_code())) {
                    return Response.error(CommonConstants.FAIL,response.getRes_msg());
                }
                message.setTitle(messageTitle);
                message.setContent(messageContent);
                messageMapper.insert(message);
            } else {
                return Response.error(CommonConstants.FAIL,"客户不存在");
            }
        } catch (Exception e) {
            return Response.error();
        }
        return Response.ok("操作成功",null);
    }

    /**
     * 更新审核操作日志
     * @param map
     * @return
     */
    private Boolean addApproveRecord(Map map) {
        OrderOperationRecord orderOperationRecord = new OrderOperationRecord();
        String uuid = UUID.randomUUID().toString();
        orderOperationRecord.setId(uuid);
        orderOperationRecord.setDescription(map.get("approveSuggestion").toString());//备注
        orderOperationRecord.setOperationTime(map.get("examineTime").toString());
        orderOperationRecord.setOperationNode(3);//人工审核
        orderOperationRecord.setOperationResult(3);//拒绝
        if(map.get("proveType").toString().equals("pass")) {
            orderOperationRecord.setOperationResult(2);//通过
            orderOperationRecord.setAmount(new BigDecimal(map.get("loanAmount").toString()));//审批额度
        }
        orderOperationRecord.setEmpId(map.get("handlerId").toString());
        orderOperationRecord.setEmpName(map.get("handlerName").toString());
        orderOperationRecord.setOrderId(map.get("id").toString());//订单id
        orderOperationRecord.setStatus(1);//1有效，0无效
        int num = processApproveRecordMapper.insertOrderOperRecord(orderOperationRecord);
        if(0 > num) {
            return false;
        }
        return true;
    }
    @Override
    public Boolean addOrderLogs(Map map){
        MagOrderLogs magOrderLogs=new MagOrderLogs();
        magOrderLogs.setId(UUID.randomUUID().toString());
        magOrderLogs.setOperatorId(map.get("handlerId").toString());
        magOrderLogs.setOperatorName(map.get("handlerName").toString());
        magOrderLogs.setOperatorType("1");
        magOrderLogs.setOrderId(map.get("orderId").toString());
        magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));
        magOrderLogs.setTache(map.get("tache").toString());
        magOrderLogs.setChangeValue(map.get("changeValue").toString());
        magOrderLogs.setState(map.get("state").toString());
        magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
        magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
        orderLogMapper.addOrderLogs(magOrderLogs);
        return true;
    }
    @Override
    public Boolean updateState(Order order) {
        int num = orderMapper.updateState(order);
        return num>0;
    }

    @Override
    public int insertOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public List getOrderList(Map map) {
        return orderMapper.getOrderList(map);
    }

    @Override
    public List getOrderListSP(Map map) {
        return orderMapper.getOrderListSP(map);
    }


    //查询所有黄金订单列表
    @Override
    public List getGoldSubmitList(Map map){
        return orderMapper.getGoldSubmitList(map);
    }

    //查询所有黄金发货列表
    @Override
    public List getGoldLogisticsList(Map map){
        return orderMapper.getGoldLogisticsList(map);
    }
    @Override
    public Boolean antifraud(Map map) {
        int num = 0;
        String orderId = map.get("orderId").toString();
        //上传的资料--批量
        //num += batchImage(map);
        Order order = new Order();
        order.setId(orderId);
//        order.setAntifraudState(map.get("antifraudState").toString());
//        order.setAntifraudRemark(map.get("antifraudRemark").toString());
//        order.setPhotoRemark(map.get("photoRemark").toString());
        num += orderMapper.updateAntifraudState(order);
        return num>0;
    }

    @Override
    public void orderFinalAudit(Map map) {
        String orderId = map.get("orderId").toString();
        String customerId = map.get("customerId").toString();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        //小区额度匹配--唯一
        Map cmParam = (Map)map.get("cmParam");//小区额度匹配
        CustomerMatching customerMatching = new CustomerMatching();
        if(cmParam.containsKey("cmId")){//是否有额度匹配id
            String cmId1 = cmParam.get("cmId").toString();
            //删除对应的小区
            customerMatchingMapper.deleteByPrimaryKey(cmId1);
        }
        if(!cmParam.get("cellName").toString().equals("")){
            String cmId = UUID.randomUUID().toString();
            customerMatching.setId(cmId);
            customerMatching.setCellName(cmParam.get("cellName").toString());
            customerMatching.setAverageHousePrice(cmParam.get("limitPrice").toString());
            customerMatching.setAverageRenovationPrice(cmParam.get("limitDeco").toString());
            //customerMatching.setProvincesId(cmParam.get("provincessId").toString());
            //customerMatching.setCityId(cmParam.get("cityId").toString());暂时不用省市id
            customerMatchingMapper.insertSelective(customerMatching);
        }

        //装修合理性判断--批量
        //删除原先的装修合理性数据
        JSONArray creArray = null;
        if(map.containsKey("creParam")){
            creArray= jsonObject.getJSONArray("creParam");//装修合理性判断
        }
        customerRespMapper.deleteByOrderId(orderId);
        List<CustomerResp> creList = new ArrayList<CustomerResp>();
        if(creArray!=null && !creArray.isEmpty()){
            for(int i=0;i<creArray.size();i++){
                CustomerResp customerResp = new CustomerResp();
                Map creMap =(Map) creArray.get(i);
                String id = UUID.randomUUID().toString();
                customerResp.setId(id);
                customerResp.setOrderId(orderId);
                customerResp.setCustomerId(customerId);
                customerResp.setMaterialPriceResp((String)creMap.get("materialPriceResp"));
                customerResp.setMaterialCountResp((String)creMap.get("materialCountResp"));
                customerResp.setDecorationCountResp((String)creMap.get("decorationCountResp"));
                customerResp.setMaterialName((String)creMap.get("materialName"));
                customerResp.setDecorationImg((String)creMap.get("decorationImg"));
                customerResp.setRemark((String)creMap.get("remark"));
                String time = DateUtils.getDateString(new Date());
                customerResp.setCreatTime(time);
                creList.add(customerResp);
            }
            customerRespMapper.batchInsert(creList);
        }

        //更新电核信息--唯一
        Map ceParam = (Map)map.get("ceParam");//电核信息
        CustomerExamine customerExamine = new CustomerExamine();
        customerExamine.setCreatTime(DateUtils.getDateString(new Date()));
        customerExamine.setOrderId(orderId);
        customerExamine.setCustomerId(customerId);
        customerExamine.setFlow(ceParam.get("flow").toString());
        customerExamine.setHouse(ceParam.get("house").toString());
        customerExamine.setLiabilities(ceParam.get("liabilities").toString());
        customerExamine.setJob(ceParam.get("job").toString());
        customerExamine.setLoan(ceParam.get("loan").toString());
        customerExamine.setRemark(ceParam.get("remark").toString());
        customerExamineMapper.updateByOrderId(customerExamine);

        //客户通信调查--批量
        //删除客户通信数据
        JSONArray cinArray= jsonObject.getJSONArray("cinParam");//客户通信调查 1.本人电话拨打记录 2.网络调查  3.114调查 4.进件单固 5.直系联系人通话记录 6.同事联系人
        customerInvestigationMapper.deleteByOrderId(orderId);
        List cinList = new ArrayList();
        if(cinArray!=null && !cinArray.isEmpty()){
            for(int i=0;i<cinArray.size();i++){
                CustomerInvestigation customerInvestigation = new CustomerInvestigation();
                String cinId = UUID.randomUUID().toString();
                customerInvestigation.setId(cinId);
                customerInvestigation.setCustomerId(customerId);
                customerInvestigation.setOrderId(orderId);
                Map map1 = (Map)cinArray.get(i);
                if(!map1.get("answerTel").toString().equals("")){
                    customerInvestigation.setAnswerTime(map1.get("answerTime").toString());
                    customerInvestigation.setAnswerTel(map1.get("answerTel").toString());
                    customerInvestigation.setAnswerState(map1.get("answerState").toString());
                    customerInvestigation.setType(map1.get("type").toString());
                    customerInvestigation.setSoundSrc((String)map1.get("soundSrc"));
                    customerInvestigation.setRemark(map1.get("remark").toString());
                    cinList.add(customerInvestigation);
                }
            }
            if(cinList.size()>0){
                customerInvestigationMapper.batchInsert(cinList);
            }
        }

        //客户情况简述备注--唯一
        Order order1 = new Order();
        order1.setId(orderId);
       // order1.setCustomerRemark(map.get("customerRemark").toString());
        orderMapper.addCustomerRemark(order1);

        //付款比例
        if((Boolean)map.get("flag")){
            Map orderParam = (Map)map.get("orderParam");//对订单进行修改状态等
            Order order2 = new Order();
            order2.setId(orderId);
            //order2.setPaymentRatio((String)orderParam.get("paymentRatio"));
            orderMapper.firstOrEndAudit(order2);
        }

        //上传的资料--批量
        JSONArray imageArray= jsonObject.getJSONArray("imageParam");//审核人员资料
        customerImageMapper.deleteByOrderIdAndType(orderId,"40");
        batchImage(imageArray,orderId,customerId);
    }

    @Override
    public List getMatchingPrice(CustomerMatching customerMatching) {
        List list = customerMatchingMapper.getMatchingPrice(customerMatching);
        return list;
    }

    /**
     * 一次性提交所有初审数据
     * @param map
     * {
     *     flag {true 审批，flase 保存}
     *     orderId  订单id
     *     customerId 客户id
     *     cmParam  小区额度匹配
     *     creParam  装修合理性判断
     *     ceParam   电核信息
     *     cinParam   客户通信调查
     *     imageParam   上传资料(不需要了在反欺诈审核里做)
     *     customerRemark  客户情况简述备注
     *     orderParam  对订单进行修改状态等
     *     {
     *         state  状态 5人工初审
     *         tache 1通过，0拒绝
     *         firstCredit  初审授信额度  tache为1才有
     *         paymentRatio 付款比例
     *     }
     *     approveParam  审批记录
     *     {
     *         approveSuggestion 审批意见
     *         handlerId   处理人id
     *         handlerName  处理人名称
     *     }
     * }
     *
     * @return
     */
    @Transactional
    @Override
    public Boolean submitOrderAudit(Map map) throws Exception{
        int num = 0;
        String orderId = map.get("orderId").toString();
        String customerId = map.get("customerId").toString();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        //小区额度匹配--唯一
        Map cmParam = (Map)map.get("cmParam");//小区额度匹配
        CustomerMatching customerMatching = new CustomerMatching();
        if(cmParam.containsKey("cmId")){//是否有额度匹配id
            String cmId1 = cmParam.get("cmId").toString();
            num += customerMatchingMapper.deleteByPrimaryKey(cmId1);
        }
        if(!cmParam.get("cellName").toString().equals("")){
            String cmId = UUID.randomUUID().toString();
            customerMatching.setId(cmId);
            customerMatching.setCellName(cmParam.get("cellName").toString());
            customerMatching.setAverageHousePrice(cmParam.get("limitPrice").toString());
            customerMatching.setAverageRenovationPrice(cmParam.get("limitDeco").toString());
            //customerMatching.setProvincesId(cmParam.get("provincessId").toString());
            //customerMatching.setCityId(cmParam.get("cityId").toString());暂时不用省市id
            num += customerMatchingMapper.insertSelective(customerMatching);
        }

        //装修合理性判断--批量
        JSONArray creArray= jsonObject.getJSONArray("creParam");//装修合理性判断
        customerRespMapper.deleteByOrderId(orderId);
        List<CustomerResp> creList = new ArrayList<CustomerResp>();
        if(creArray!=null && !creArray.isEmpty()){
            for(int i=0;i<creArray.size();i++){
                CustomerResp customerResp = new CustomerResp();
                Map creMap =(Map) creArray.get(i);
                String id = UUID.randomUUID().toString();
                customerResp.setId(id);
                customerResp.setOrderId(orderId);
                customerResp.setCustomerId(customerId);
                customerResp.setMaterialPriceResp((String)creMap.get("materialPriceResp"));
                customerResp.setMaterialCountResp((String)creMap.get("materialCountResp"));
                customerResp.setDecorationCountResp((String)creMap.get("decorationCountResp"));
                customerResp.setMaterialName((String)creMap.get("materialName"));
                customerResp.setDecorationImg((String)creMap.get("decorationImg"));
                customerResp.setRemark((String)creMap.get("remark"));
                String time = DateUtils.getDateString(new Date());
                customerResp.setCreatTime(time);
                creList.add(customerResp);
            }
            num += customerRespMapper.batchInsert(creList);
        }

        //电核信息--唯一
        Map ceParam = (Map)map.get("ceParam");//电核信息
        CustomerExamine customerExamine = new CustomerExamine();
        customerExamine.setCreatTime(DateUtils.getDateString(new Date()));
        customerExamine.setOrderId(orderId);
        customerExamine.setCustomerId(customerId);
        customerExamine.setFlow(ceParam.get("flow").toString());
        customerExamine.setHouse(ceParam.get("house").toString());
        customerExamine.setLiabilities(ceParam.get("liabilities").toString());
        customerExamine.setJob(ceParam.get("job").toString());
        customerExamine.setLoan(ceParam.get("loan").toString());
        customerExamine.setRemark(ceParam.get("remark").toString());
        List examines = customerExamineMapper.findByOrderId(orderId);
        if(examines.size()>0){
            customerExamineMapper.updateByOrderId(customerExamine);
        }else{
            String ceId = UUID.randomUUID().toString();
            customerExamine.setId(ceId);
            num += customerExamineMapper.insertSelective(customerExamine);
        }

        //客户通信调查--批量
        JSONArray cinArray= jsonObject.getJSONArray("cinParam");//客户通信调查 1.本人电话拨打记录 2.网络调查  3.114调查 4.进件单固 5.直系联系人通话记录 6.同事联系人
        customerInvestigationMapper.deleteByOrderId(orderId);
        List cinList = new ArrayList();
        if(cinArray!=null && !cinArray.isEmpty()){
            for(int i=0;i<cinArray.size();i++){
                CustomerInvestigation customerInvestigation = new CustomerInvestigation();
                String cinId = UUID.randomUUID().toString();
                customerInvestigation.setId(cinId);
                customerInvestigation.setCustomerId(customerId);
                customerInvestigation.setOrderId(orderId);
                Map map1 = (Map)cinArray.get(i);
                if(!map1.get("answerTel").toString().equals("")){
                    customerInvestigation.setAnswerTime(map1.get("answerTime").toString());
                    customerInvestigation.setAnswerTel(map1.get("answerTel").toString());
                    customerInvestigation.setAnswerState(map1.get("answerState").toString());
                    customerInvestigation.setType(map1.get("type").toString());
                    customerInvestigation.setSoundSrc(map1.get("soundSrc").toString());
                    customerInvestigation.setRemark(map1.get("remark").toString());
                    cinList.add(customerInvestigation);
                }
            }
            if(cinList.size()>0){
                num += customerInvestigationMapper.batchInsert(cinList);
            }
        }

        //客户情况简述备注--唯一
        Order order1 = new Order();
        order1.setId(orderId);
        //order1.setCustomerRemark(map.get("customerRemark").toString());
        num += orderMapper.addCustomerRemark(order1);

       //上传的资料--批量
        JSONArray imageArray= jsonObject.getJSONArray("imageParam");//审核人员资料
        customerImageMapper.deleteByOrderIdAndType(orderId,"39");
        num += batchImage(imageArray,orderId,customerId);

        //审核通过or拒绝
        if((Boolean) map.get("flag")){
            Map orderParam = (Map)map.get("orderParam");//对订单进行修改状态等
            Order order = new Order();
            order.setId(orderId);
            String state = orderParam.get("state").toString();
            String tache = orderParam.get("tache").toString();
            if(state!=null && !"".equals(state)){
                order.setAlterTime(DateUtils.getDateString(new Date()));
                order.setState(orderParam.get("state").toString());
               // order.setTache(orderParam.get("tache").toString());
                if(tache.equals("1") && state.equals("5")){
                    //通过下有授信额度
                //    order.setFirstCredit(orderParam.get("firstCredit").toString());
                }
            }
            //order.setPaymentRatio((String)orderParam.get("paymentRatio"));
            num += orderMapper.firstOrEndAudit(order);
            //审批记录表
            if(num>0){
                Map approveParam = (Map)map.get("approveParam");//审批记录
                approveParam.put("orderId",orderId);
                approveParam.put("customerId",customerId);
                approveParam.put("type","1");//1客户id，2商户id
                approveParam.put("state",orderParam.get("state"));
                approveParam.put("result",orderParam.get("tache"));
                addApproveRecord(approveParam);
            }
        }

        return num>0;
    }

    //上传的资料--批量
    public int batchImage(JSONArray imageArray,String orderId,String customerId){
        int num = 0;
        List imageList = new ArrayList();
        if(imageArray!=null && !imageArray.isEmpty()){
            for(int i=0;i<imageArray.size();i++){
                CustomerImage customerImage = new CustomerImage();
                String imageId = UUID.randomUUID().toString();
                customerImage.setId(imageId);
                customerImage.setCreatTime(DateUtils.getDateString(new Date()));
                customerImage.setAlterTime(DateUtils.getDateString(new Date()));
                customerImage.setOrderId(orderId);
                customerImage.setCustomerId(customerId);
                Map map1 = (Map)imageArray.get(i);
                if(!map1.get("src").toString().equals("")){
                    customerImage.setType(map1.get("type").toString());
                    customerImage.setSrc(map1.get("src").toString());
                    customerImage.setBusinessType(map1.get("businessType").toString());
                    imageList.add(customerImage);
                }
            }
            if(imageList.size()>0){
                num += customerImageMapper.batchInsert(imageList);
            }
        }
        return num;
    }

    /**
     * 批量上传反欺诈资料
     * @param request{orderId,customerId,fileList}
     * @throws Exception
     */
    @Override
    public void batchUploadAntiFraud(HttpServletRequest request) throws Exception {
        String fileName="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("/");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFile(request,root+ File.separator + "customerAntifraud", id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        String orderId = allMap.get("orderId").toString();
        String customerId = allMap.get("customerId").toString();
        if (!list.isEmpty()) {
            List imageList = new ArrayList();
            for(int i=0;i<list.size();i++){
                Map<String, Object> fileMap = list.get(i);
                fileName = "/customerAntifraud/"+fileMap.get("Name");
                CustomerImage customerImage = new CustomerImage();
                String imageId = UUID.randomUUID().toString();
                customerImage.setId(imageId);
                customerImage.setOrderId(orderId);
                customerImage.setCustomerId(customerId);
                customerImage.setCreatTime(DateUtils.getDateString(new Date()));
                customerImage.setAlterTime(DateUtils.getDateString(new Date()));
                customerImage.setBusinessType("0");//客户
                customerImage.setType("7");//反欺诈
                customerImage.setSrc(fileName);
                imageList.add(customerImage);
            }
            if(imageList.size()>0){
                customerImageMapper.batchInsert(imageList);
            }
        }
    }

    public Map<String, Object> backOrder(String orderId, User user){
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("msg", "退回成功");
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null == order)
        {
            resMap.put("msg", "订单不存在");
            return resMap;
        }
        //修改订单状态（state为1，commodity_state为21）
        Map<String, Object> orderparam = new HashMap<String, Object>();
        orderparam.put("id", orderId);
        orderparam.put("state", "0");
        orderparam.put("commodityState", "21");
        orderMapper.updateOrderStatus(orderparam);

        //记录订单日志
        MagOrderLogs magOrderLogs=new MagOrderLogs();
        magOrderLogs.setId(UUID.randomUUID().toString());
        magOrderLogs.setOperatorId(String.valueOf(user.getUserId()));
        magOrderLogs.setOperatorName(user.getTrueName());
        magOrderLogs.setOperatorType("1");
        magOrderLogs.setOrderId(order.getId());
        magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));
        magOrderLogs.setTache("退回订单");
        magOrderLogs.setChangeValue("退回订单");
        magOrderLogs.setState(order.getState());
        magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
        magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
        orderLogMapper.addOrderLogs(magOrderLogs);

        //发送消息
        Integer appType = Integer.valueOf(order.getOrderType());
        Map<String, Object> sendRes = sendMessageFactory.sendMessage(appType, "2", order.getId(), null);
        if (null == sendRes || 0 != (Integer)sendRes.get("code"))
        {
            resMap.put("msg", "发送消息失败");
        }
        return resMap;
    }

    @Override
    public List findByOrderId(String orderId) {
        return customerImageMapper.findByOrderId(orderId);
    }

    @Override
    public List findByCustId(String customerId) {
        return customerImageMapper.getCustomerImage(customerId);
    }

    @Override
    public List<Order> getStandardList(ParamFilter paramFilter){
        return orderMapper.getStandardOrders(paramFilter);
    }

    public String orderMsgDealWith(String order_id,String order_state,String user_id,String channel) throws  Exception{
        return null;
    }
    //获取订单权限
    @Override
    public Map  getJurisdiction(Map map){
        if ("admin".equals(map.get("account"))){
            map.put("admin",map.get("account"));
        }else {
            SysDepartment sysDepartment=sysDepartmentMapper.findById((Long) map.get("orgid"));
            //Pid=0是总公司
            if (sysDepartment.getPid()==0){
                map.put("headquarters",sysDepartment.getId());
            }else {
                map.put("branch",sysDepartment.getId());
            }
        }
        return  map;
    }
    /**
     * 获取审核意见表意见
     * @param map
     * @return
     */
    public List<ProcessApproveRecord> getApproveSuggestion(Map map) {
        return orderMapper.selectProByOrderIdAndNode(map);
    }

    public List<Order> getOvertimePayOrder(Map<String, Object> map)
    {
        List<Order> list = new ArrayList<Order>();
        //获取超时时间
        String over = "";
        try {
            over = dictService.getDictInfo("状态超时","cssj");
        } catch (Exception e) {
            TraceLoggerUtil.error("获取超时时间", e);
        }
        if (null == over || "".equals(over.trim()))
        {
            over = "72";
        }
        int overTime = Integer.valueOf(over) * 60 * 60 * 1000;
        Date d = new Date(new Date().getTime() - overTime);
        //查询state状态为5、commodity_state状态为18的超时订单
        map.put("state", "5");
        map.put("commodityState", "18");
        map.put("orderType", "2");
        map.put("editEndTime", DateUtils.formatDate(d, DateUtils.STYLE_10));
        List<Order> payList = orderMapper.getOverTimeOrders(map);
        list.addAll(payList);

        //查询state状态为5、commodity_state状态为17并且有服务包 的超时订单
        map.put("commodityState", "17");
        map.put("hasBeforePack", "1");
        List<Order> packList = orderMapper.getOverTimeOrders(map);
        list.addAll(packList);
        return list;
    }

    @Transactional
    public Map<String, Object> closeOrder(String orderId, User user)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Order orderDetail = orderMapper.selectByPrimaryKey(orderId);
        //修改订单状态
        Order order = new Order();
        order.setId(orderId);
        order.setState("10");
        order.setAlterTime(DateUtils.formatDate(DateUtils.STYLE_10));
        orderMapper.updateByPrimaryKeySelective(order);

        //已付款成功的服务包设置为退款
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("state", "4");
        param.put("date", DateUtils.formatDate(DateUtils.STYLE_10));
        param.put("orderId", orderId);
        servicePackageOrderMapper.updatePackState(param);
        //记录日志
        MagOrderLogs magOrderLogs=new MagOrderLogs();
        magOrderLogs.setId(UUID.randomUUID().toString());
        magOrderLogs.setOperatorId(String.valueOf(user.getUserId()));
        magOrderLogs.setOperatorName(user.getTrueName());
        magOrderLogs.setOperatorType("1");
        magOrderLogs.setOrderId(orderId);
        magOrderLogs.setCreatTimeLog(DateUtils.getDateString(new Date()));
        magOrderLogs.setTache("订单关闭");
        magOrderLogs.setChangeValue("订单关闭");
        magOrderLogs.setState(orderDetail.getState());
        magOrderLogs.setCreatTime(DateUtils.getDateString(new Date()));
        magOrderLogs.setAlterTime(DateUtils.getDateString(new Date()));
        orderLogMapper.addOrderLogs(magOrderLogs);
        //发送app消息
        Integer appType = Integer.valueOf((null == order.getOrderType() || "".equals(order.getOrderType()) ? "2" : order.getOrderType()));
        Map<String, Object> sendRes = sendMessageFactory.sendMessage(appType, "3", orderId, null);
        if (null == sendRes || 0 != (Integer)sendRes.get("code"))
        {
            map.put("msg", "发送消息失败");
            return map;
        }
        map.put("msg", "关闭订单成功");
        return map;
    }

    @Override
    public List getSubmitList(Map map){

        return orderMapper.getSubmitList(map);
    }

    /**********************************************碧友信*****************************************************/

    /**
     * 查询所有订单
     * @author 仙海峰
     * @param map
     * @return
     */
    @Override
    public List getAllOrderList(Map map){

        return orderMapper.getAllOrderList(map);
    }

    /**
     * 根据订单ID 获取订单信息和银行卡信息
     * @author 仙海峰
     * @param orderId
     * @return
     */
    @Override
    public Map getOrderAndBank(String orderId) {
        return orderMapper.getOrderAndBank(orderId);
    }

    @Override
    public List findWindControlAuditList(Map map) {
        return orderMapper.findWindControlAuditList(map);
    }
}
