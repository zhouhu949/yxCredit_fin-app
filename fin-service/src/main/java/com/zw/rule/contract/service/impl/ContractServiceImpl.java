package com.zw.rule.contract.service.impl;

import com.zw.rule.contract.ContractService;
import com.zw.rule.contract.po.Contract;
import com.zw.rule.contract.po.MagOrderContract;
import com.zw.rule.mapper.contract.ContractMapper;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    private  ContractMapper contractMapper;
    @Resource
    private OrderMapper orderMapper;

//    //上传合同
//    @Override
//    public List uploadContractFile(HttpServletRequest request) throws Exception {
//        Contract contract = new Contract();
//        //存储服务器路径文件名称+后缀名
//        String fileName="";
//        //存储服务器路径文件名称
//        String id = UUID.randomUUID().toString();
//        //获取根目录
//        String root = request.getSession().getServletContext().getRealPath("/");
//        //捕获前台传来的图片，并用uuid命名，防止重复
//        Map<String, Object> allMap = UploadFile.getFileContract(request,root+ File.separator + "firstAudit", id);
//        //上传合同文件的名称
//        contract.setFileName((String) allMap.get("fileName"));
//        //上传合同文件的后缀名
//        contract.setFileSuffix((String) allMap.get("uploadFileSuffix"));
//        //客户id
//        contract.setCustomerId((String) allMap.get("customerId"));
//        //订单id
//        contract.setOrderId((String) allMap.get("orderId"));
//        //状态  0正常 1删除
//        contract.setState("0");
//        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
//        List imageList = new ArrayList();
//        //当前台有文件时，给文件名称变量赋值
//        if (!list.isEmpty()) {
//            Map<String, Object> fileMap = list.get(0);
//            fileName = "/firstAudit/"+(String) fileMap.get("Name");
//            imageList.add(fileName);
//            contract.setSrc(fileName);
//
//            String uuid = UUID.randomUUID().toString();
//            contract.setId(uuid);
//            SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
//            contract.setCreateTime(sdf.format(new Date()));
//            contract.setBusinessType("1");
//            /*插入数据库*/
//            contractMapper.addContract(contract);
//        }
//        return imageList;
//    }

    //获取订单集合
    @Override
    public List getOrders(Map map)
    {
        return contractMapper.getOrderList(map);
    }

    public List getContractByOrderId(Map map){
        return contractMapper.getContractByOrderId(map);
    }

    public List searchContract(Map map){
        return contractMapper.searchContract(map);
    }

    public int  updateContract(Contract contract){
        return contractMapper.updateContract(contract);
    }

    public Contract getById(String id){
        return contractMapper.getById(id);
    }

    //获取终审订单集合
    @Override
    public List getOrdersList(ParamFilter queryFilter){
        return orderMapper.getContractOrders(queryFilter);
    }
    //删除合同
    @Override
    public int deleteContract(String  id)
    {
        return contractMapper.deleteContract(id);
    }

    public int updateState(Map map){
        return contractMapper.updateState(map);
    }

    public int addContract(Contract contract){
        return contractMapper.addContract(contract);
    }

    //签订合同
    @Override
    public boolean signContract(String id)
    {
        if (contractMapper.signContract(id)>0){
            return true;
        }else {
            return false;
        }
    }
    //插入合同
    @Override
    public int createContract(MagOrderContract magOrderContract){
        return contractMapper.createContract(magOrderContract);
    }

    public List<Map> getMagCustomerAccountByCustomerId(String customerId){
        return contractMapper.getMagCustomerAccountByCustomerId(customerId);
    }

    public List<Map> getInfoByproductId(Map map){
        return contractMapper.getInfoByproductId(map);
    }

    //获取协议模板
    @Override
    public Map getContactTemplate(Map map){

        return contractMapper.getContactTemplate(map);
    }

    public Map getProductFree(String order_id) {
        return contractMapper.getProductFree(order_id);
    }

    @Override
    public Map getContactTemplate(String template_type) {
        return contractMapper.getContectTemplate(template_type);
    }

    @Override
    public Map getOrderInfo(String order_id) {
        return contractMapper.getOrderInfo(order_id);
    }

    @Override
    public Map getBankInfo(String bank_id) {
        return contractMapper.getBankInfo(bank_id);
    }

    @Override
    public Map getRegistAddress(String order_id) {
        return contractMapper.getRegistAddress(order_id);
    }

    public Map getUserInfo(String user_id){
        return contractMapper.getUserInfo(user_id);
    }
}
