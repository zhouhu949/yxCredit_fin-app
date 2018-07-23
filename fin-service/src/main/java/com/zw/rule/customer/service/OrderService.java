package com.zw.rule.customer.service;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.BusinessRepayment;
import com.zw.rule.customer.po.CustomerMatching;
import com.zw.rule.customer.po.Order;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderService {
//    List getOrders(ParamFilter paramFilter);
    //查询一条客户订单
    Order selectById(String id);
    //审核后改变状态，通过清空领取人及添加授信额度
    Boolean firstOrEndAudit(Map map);
    //领取订单后改变领取人
    Boolean updateReceiveId(Order order);
    //查询已处理订单列表（前端传状态是初审还是终审）
    List<Order> getAlreadyOrders(ParamFilter paramFilter);
    //一次性提交所有初审数据
    Boolean submitOrderAudit(Map map)throws Exception;
    //额度匹配
    List<CustomerMatching> getMatchingPrice(CustomerMatching customerMatching);

    List getOrderDel(String id);

    Map getTaskId(String orderId);

    void updateTask(String tas,Long userId);

    //获取满足放款信息的数据
    List getLoanCustomerList(Map map);

    //添加logs
    Boolean addOrderLogs(Map map);
    //更新订单状态
    Boolean updateState(Order order);
    //插入一条订单
    int insertOrder(Order order);
    //查询订单列表
    List getOrderList(Map map);

    //查询订单列表
    List getOrderListSP(Map map);


    //查询黄金订单列表
    List getGoldSubmitList(Map map);

    //查询所有黄金发货列表
    List getGoldLogisticsList(Map map);

    //反欺诈审核
    Boolean antifraud(Map map);

    void orderFinalAudit(Map map);

    //批量上传反欺诈资料
    void batchUploadAntiFraud(HttpServletRequest request) throws Exception ;

    //获取订单集合  合规审查
    List<Order> getStandardList(ParamFilter queryFilter) ;

    //跟新订单状态
    Response updateOrder(Map map);

    /**
     * 订单信息
     * @param orderId 订单id
     * @param user 操作人信息
     * @return
     */
    Map<String, Object> backOrder(String orderId, User user);

    //获取图片资料
    List findByOrderId(String orderId);
    List findByCustId(String customerId);

    //订单推送  order_state 1 审批通过 0 拒绝 3 放款成功
    String orderMsgDealWith(String order_id,String order_state,String user_id,String channel) throws  Exception;

    //获取订单权限
    Map  getJurisdiction(Map map);

    //获取审核意见（查询审核意见表process_approve_record）
    List<ProcessApproveRecord> getApproveSuggestion(Map map);

    /**
     * 获取已付款未发货的超时订单
     * @param map 参数
     * @return
     */
    List<Order> getOvertimePayOrder(Map<String, Object> map);

    /**
     *  关闭订单
     * @param orderId 订单id
     * @return
     */
    Map<String, Object> closeOrder(String orderId, User user);

    List getSubmitList(Map map);



    /************************************************碧友信*************************************************/

    /**
     * 获取全部订单信息
     * @param map
     * @return
     */
    List getAllOrderList(Map map);

    /**
     * 根据订单ID 获取订单信息和银行卡信息
     * @author 仙海峰
     * @param orderId
     * @return
     */
    Map getOrderAndBank (String orderId);

    /**
     * 获取风控审核列表
     * @param map
     * @return
     */
    List findWindControlAuditList(Map map);

    /**
     * 获取还款计划信息
     * @param businessRepayment
     * @return
     */
    List<BusinessRepayment> findListRepayMentByOrderNo(BusinessRepayment businessRepayment);

}
