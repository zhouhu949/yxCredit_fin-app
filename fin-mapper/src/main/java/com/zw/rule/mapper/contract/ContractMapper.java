package com.zw.rule.mapper.contract;


import com.zw.rule.contract.po.Contract;
import com.zw.rule.contract.po.MagOrderContract;


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
public interface ContractMapper {
    //获取订单的合同集合
    List<Contract> searchContract(Map map);

    int updateContract(Contract contract);

    Contract getById(String id);

    List getOrderList(Map map);

    List getContractByOrderId(Map map);

    int addContract(Contract contract);

    //删除合同信息
    int deleteContract (String id);

    //签订合同
    int signContract (String id);

    int updateState(Map map);

    //创建合同
    int createContract(MagOrderContract magOrderContract);

    List<Map> getMagCustomerAccountByCustomerId(String customerId);

    List<Map> getInfoByproductId(Map map);

    Map getContactTemplate(Map map);

    Map getProductFree(String order_id);

    Map getContectTemplate(String template_type) ;


    Map getOrderInfo(String order_id) ;

    Map getBankInfo(String bank_id) ;

    Map getRegistAddress(String order_id) ;

    Map getUserInfo(String user_id);


}
