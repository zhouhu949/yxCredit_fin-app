package com.zw.rule.finalOrderAudit.service;


import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.transaction.po.TransactionDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * 功能说明：信贷类型/产品表service层接口
 * @author wangmin 2017-06-13
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：
 */
public interface IFinalOrderAuditService {

    /**
     * 功能说明：得到产品系列列表
     * wangmin  2017-6-13
     * @return
     */
    public List getFinalOrderList(String orderId) throws Exception;

    /**
     * 功能说明：得到资源池终审订单列表
     * wangmin  2017-6-13
     * @return
     */
    public List getOrderList(ParamFilter paramFilter) throws Exception;

    /**
     * 功能说明：得到资源池终审订单列表
     * wangmin  2017-6-23
     * @return
     */
    boolean updateReceiveId(String orderId,String userId);

    /**
     * 功能说明：审核终审订单
     * wangmin  2017-6-23
     * @return
     */
    int auditOrder(String orderId,String end_credit,String state);

    /**
     * 功能说明：根据订单号查询当前用户的设备号
     * wangmin  2017-6-23
     * @return
     */
    Map getUserInfoByorderId(String orderId);

    /**
     * 功能说明：装修合理性判断
     * wangmin  2017-6-13
     * @return
     */
    public List decorationList(String orderId) throws Exception;

    /**
     * 功能说明：电核信息
     * wangmin  2017-6-13
     * @return
     */
    public List examineList(String orderId) throws Exception;

    /**
     * 功能说明：通话记录
     * wangmin  2017-6-13
     * @return
     */
    public List investiList(String orderId) throws Exception;

    /**
     * 功能说明：反欺诈资料
     * wangmin  2017-6-13
     * @return
     */
    public List imageList(String orderId) throws Exception;

    /**
     * 功能说明：评估结果
     * wangmin  2017-6-13
     * @return
     */
    public List approveList(String orderId) throws Exception;

    /**
     * 功能说明：保存终审审核人员上传的图片资料
     * wangmin  2017-6-13
     * @return
     */
    boolean saveImg(Map map) throws Exception;

    public List queryOrders(Map map)throws Exception;

    public List queryOrdersGold(Map map)throws Exception;

    public List withdrawalList(ParamFilter paramFilter)throws Exception;

    //黄金回购放款操作
    String confirmationLoan(Map map)throws Exception;

    //商品贷给商户放款(线下放款)
    String confirmationMerchantXianXia(Map map)throws Exception;

    //商品贷给商户放款
    String confirmationMerchant(Map map)throws Exception;

    //放款回调
    void ybWithdrawalCallbackInfo(Map map)throws Exception;

    //扣款回调
    void bindCardPayCallback(Map map);

    //宝付放款回调
    boolean bfWithdrawalCallback(HttpServletRequest req, Map map);

    //发起扣款
    String costDebit(Map map);
    //获取放款明细
    List<TransactionDetails> transactionDetailsList(Map map);
}

