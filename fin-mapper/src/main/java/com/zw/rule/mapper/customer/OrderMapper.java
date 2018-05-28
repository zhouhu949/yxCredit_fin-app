package com.zw.rule.mapper.customer;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.customer.po.Order;
import com.zw.rule.customer.po.Withdrawals;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);
    //查询一条客户订单
    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //查询订单列表（前端传状态是初审还是终审）
    List<Order> getOrders(ParamFilter paramFilter);
    //查询资源池订单数量（前端传状态是初审还是终审）
    int countOrders(Map map);
    //领取订单后改变领取人
    int updateReceiveId(Order order);
    //审核后改变状态，通过清空领取人及添加授信额度
    int firstOrEndAudit(Order order);
    //查询已处理订单列表（前端传状态是初审还是终审）
    List<Order> getAlreadyOrders(ParamFilter paramFilter);
    //查询已处理订单数量（前端传状态是初审还是终审）
    int countAlreadyOrders(Map map);

    //领取订单后改变领取人
    int updateFinalReceiveId(@Param("orderId")String orderId, @Param("userId")String userId,@Param("date")String date);


    List<Order> getOrderDel(String id);

    List<Order> getList(Map map);

    Map getTaskId(String orderId);

    /** 根据订单id获取服务费率*/
    Map getServiceFeeList(String orderId);

    /** 根据订单id获取总包商*/
    Map getContractorList(String orderId);

    List getLoanCustomerList(Map map);

    int addCustomerRemark(Order order);

    int updateState(Order order);

    Map getUserInfoByorderId(String orderId);

    List<Order> getOrderList(Map map);

    List<Order> getOrderListSP(Map map);



    //黄金订单
    List<Order> getGoldSubmitList(Map map);

    //黄金订单
    List<Map> getGoldLogisticsList(Map map);

    //反欺诈审核（改状态，加备注）
    int updateAntifraudState(Order record);

    //查询终审已处理订单列表 合同签约
    List<Order> getContractOrders(ParamFilter paramFilter);

    //查询订单列表 合规审核
    List<Order> getStandardOrders(ParamFilter paramFilter);
    // 借贷还款
    List<Order> getRepayCustomerList(ParamFilter paramFilter);
    //获取客户对应的集合
    List<Order> getFinancialOrderList(ParamFilter paramFilter);
    //获取待放款客户订单
    List<Map> getCustOrderList(Map map);

    //修改对应订单信息
    int updateOrderStatus(Map map);

    /**
     * 获取放款审核列表
     * @param map
     * @return
     */
    List<Order> findLoanSincereList(Map map);

    List<Order> getGoldPayment(Map map);

    List<Order> getWithdrawalsListGold(Map map);

    List<Withdrawals> getOrderWithdrawals(Map map);

    void updateWithdrawals(Map map);

    //获取客户开户信息
    List<Map> getAccount(Map map);
    //获取审核信息(审批意见表process_approve_record)
   List<ProcessApproveRecord> selectProByOrderIdAndNode(Map map);

    List<Order> getOverTimeOrders(Map map);
    //获取所有订单
    List<Order> getSubmitList(Map map);
/************************************************碧友信*************************************************/

    /**
     * 查询所有订单
     * @author 仙海峰
     * @param map
     * @return
     */
    List<Order> getAllOrderList(Map map);

    /**
     * 根据订单ID 获取订单信息和银行卡信息
     * @author 仙海峰
     * @param orderId
     * @return
     */
    Map getOrderAndBank (String orderId);

    /**
     * 分控审核订单列表
     * @author luochaofang
     * @param map
     * @return
     */
    List<Order> findWindControlAuditList(Map map);


    /**
     * 根据订单id查询订单号
     * @param id
     * @return
     */
    String getOrderNoById(@Param("id") String id);
}