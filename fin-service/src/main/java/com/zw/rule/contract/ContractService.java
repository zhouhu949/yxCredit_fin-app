package com.zw.rule.contract;

import com.zw.rule.contract.po.Contract;
import com.zw.rule.contract.po.MagOrderContract;
import com.zw.rule.mybatis.ParamFilter;


import java.util.List;
import java.util.Map;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@合同管理@<br>
 * <strong>Create on : 2017年06月27日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:gaozhidong <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public interface ContractService {
//    //上传合同,返回合同名称
//    List uploadContractFile(HttpServletRequest request)throws Exception;
//    //获取订单的合同集合
//    List<Contract> getContractById(String orderId) ;
    //获取订单集合
    List getOrders(Map map) ;

    List getContractByOrderId(Map map);

    List searchContract(Map map);

    int updateContract(Contract contract);

    Contract getById(String id);

    //获取终审订单集合
    List getOrdersList(ParamFilter queryFilter) ;
    //删除合同
    int deleteContract(String  id);

    //模板启停
    int updateState(Map map);

    //添加合同
    int addContract(Contract contract);

    //签订合同
    boolean signContract(String  id);
    //插入合同
    int createContract(MagOrderContract magOrderContract);

    List<Map> getMagCustomerAccountByCustomerId(String customerId);

    List<Map> getInfoByproductId(Map map);

    //获取协议模板
    Map getContactTemplate(Map map);

    Map getProductFree(String order_id);
    Map getContactTemplate(String template_type);
    Map getOrderInfo(String order_id);
    Map getBankInfo(String bank_id);
    Map getRegistAddress(String order_id);
    Map getUserInfo(String user_id);

}
