package com.zw.rule.collectionRecord.service.impl;


import com.zw.base.util.DateUtils;
import com.zw.rule.collection.po.Collection;
import com.zw.rule.collectionRecord.po.MagCollectionRecord;
import com.zw.rule.collectionRecord.po.MagWarning;
import com.zw.rule.collectionRecord.service.CollectionRecordService;
import com.zw.rule.mapper.collectionRecord.MagCollectionRecordMapper;
import com.zw.rule.mapper.collectionRecord.MagDerateMapper;
import com.zw.rule.mapper.collectionRecord.MagWarningMapper;
import com.zw.rule.mapper.repayment.RepaymentMapper;
import com.zw.rule.mapper.servicePackage.ServicePackageOrderMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月12日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
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
public class CollectionRecordServiceImpl implements CollectionRecordService {
    private static final Logger logger = Logger.getLogger(CollectionRecordServiceImpl.class);

    @Autowired
    private MagCollectionRecordMapper magCollectionRecordMapper;
    @Autowired
    private RepaymentMapper repaymentMapper;
    @Autowired
    private MagDerateMapper magDerateMapper;
    @Autowired
    private MagWarningMapper magWarningMapper;
    @Autowired
    private ServicePackageOrderMapper servicePackageOrderMapper;

    @Override
    public int addCollectionRecord(MagCollectionRecord magCollectionRecord) {
        magCollectionRecord.setId(UUID.randomUUID().toString());
        magCollectionRecord.setCreateTime(DateUtils.getCurrentTime(DateUtils.STYLE_5));
        return magCollectionRecordMapper.insertSelective(magCollectionRecord);
    }

    @Override
    public int updateCollectionRecord(MagCollectionRecord magCollectionRecord) {
        return magCollectionRecordMapper.updateByPrimaryKeySelective(magCollectionRecord);
    }

    @Override
    public MagCollectionRecord getCollectionRecordById(String id) {
        return magCollectionRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List getCollectionRecordByLoanId(Map map) {
        return magCollectionRecordMapper.getCollectionRecordByLoanId(map);
    }

    @Override
    public int deleteCollectionRecordById(String id) {
        return magCollectionRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List getAfterLoanList(ParamFilter paramFilter) {
        return repaymentMapper.getRepaymentList(paramFilter.getParam());
    }

    @Override
    public List getRepaymentDerateList(ParamFilter paramFilter) {
        return repaymentMapper.getRepaymentDerateList(paramFilter.getParam());
    }

    @Override
    public List getRepaymentDerateListSP(ParamFilter paramFilter) {
//        List<Map> magRepaymentAllList=repaymentMapper.getRepaymentDerateListSP(paramFilter.getParam());
//        //获取服务包明细
//        for (int i=0;i<magRepaymentAllList.size();i++){
//            Map<String,Object>mapPackageRepayment=new HashedMap();
//            mapPackageRepayment.put("repaymentId",magRepaymentAllList.get(i).get("id"));
//            magRepaymentAllList.get(i).put("servicePackageList",servicePackageOrderMapper.getServicePackageRepayment(mapPackageRepayment));
//        }
        return repaymentMapper.getRepaymentDerateListSP(paramFilter.getParam());
    }
    @Override
    public List getLoandetaillist(String loanId) {
        return repaymentMapper.getLoandetaillist(loanId);
    }

    @Override
    public MagWarning getWarningByLoanId(String loanId) {
        return magWarningMapper.getWarningByLoanId(loanId);
    }

    @Override
    public int setCollection(Map addParam) {
        Collection collection=new Collection();
        collection.setId(UUID.randomUUID().toString());
        collection.setCustomerId(addParam.get("customerId").toString());
        collection.setElectricExaminationPersonnel(addParam.get("userId").toString());
        collection.setEntranceExaminationDate(DateUtils.getCurrentTime(DateUtils.STYLE_5));
        collection.setElectronicExaminationState("1");
        return magCollectionRecordMapper.insertCollection(collection);
    }

    @Override
    public int updateCollectionExternal(Map addParam) {
        Collection collection=new Collection();
        collection.setElectronicExaminationState("2");
        collection.setElectricExaminationComplete(DateUtils.getCurrentTime(DateUtils.STYLE_5));
        collection.setCustomerId(addParam.get("customerId").toString());
        collection.setExternalAuditorPersonnel(addParam.get("userId").toString());
        collection.setExternalAuditorDate(DateUtils.getCurrentTime(DateUtils.STYLE_5));
        collection.setExternalAuditorState("1");
        return magCollectionRecordMapper.updateCollectionExternal(collection);
    }

    @Override
    public int updateEntrusted(Map param) {
        Collection collection=new Collection();
        if ("1".equals(param.get("entrust_state").toString())){
            collection.setEntrustState("1");
            collection.setEntrustDate(DateUtils.getCurrentTime(DateUtils.STYLE_5));
            collection.setCustomerId(param.get("customerId").toString());
            collection.setExternalAuditorState("2");
            collection.setExternalAuditorComplete(DateUtils.getCurrentTime(DateUtils.STYLE_5));
        }
//        else if ("1".equals(param.get("entrust_state").toString())){
//            collection.setEntrustState("1");
//            collection.setCustomerId(param.get("customerId").toString());
//        }else if ("2".equals(param.get("entrust_state").toString())){
//            collection.setEntrustState("2");
//            collection.setCustomerId(param.get("customerId").toString());
//            collection.setEntrustComplete(DateUtils.getCurrentTime(DateUtils.STYLE_5));
//            collection.setState("2");
//        }
        return magCollectionRecordMapper.updateEntrusted(collection);
    }

    @Override
    public int updateEntrustedState(Map param){
        return magCollectionRecordMapper.updateEntrustedState(param);
    }

    @Override
    public List getApprovalList(Map param){
        return magCollectionRecordMapper.getApprovalList(param);
    }

    @Override
    public int updateApproval(Map param){
        if ("1".equals(param.get("approvalState"))){
            //更新还款计划表
            Map<String,Object> map=new HashedMap();
            map.put("id",param.get("repaymentId"));
            map.put("derateAmount",param.get("derateAmount"));
            //repaymentMapper.updtaeRepayByorderId(map);
        }
        return magCollectionRecordMapper.updateApproval(param);
    }

    @Override
    public int updateApprovalHJD(Map param){
        if ("1".equals(param.get("approvalState"))){
            //更新还款计划表
            Map<String,Object> map=new HashedMap();
            map.put("id",param.get("repaymentId"));
            map.put("derateAmount",param.get("derateAmount"));
            //repaymentMapper.updtaeRepayByorderId(map);
        }
        return magCollectionRecordMapper.updateApprovalHJD(param);
    }
    @Override
    public List getTelephoneList(Map param){
        return magCollectionRecordMapper.getTelephoneList(param);
    }

    @Override
    public List getAfterLoanDetails(Map param) {
        return repaymentMapper.getAfterLoanDetails(param);
    }
    @Override
    public List getAfterLoanDetailsHJD(Map param) {
        return repaymentMapper.getAfterLoanDetailsHJD(param);
    }
    @Override
    public int orderSettle(Map addParam) {
        int count=0;
        addParam.put("complete",DateUtils.getCurrentTime(DateUtils.STYLE_5));
        addParam.put("state","1");
        //更新催收记录表
        count=magCollectionRecordMapper.updateCollection(addParam);
        //更新还款计划表
        count+=magCollectionRecordMapper.updateRepayment(addParam);
        return  count;
    }

    @Override
    public int addDerate(Map addParam) {
        addParam.put("id", UUID.randomUUID().toString());//减免id
        addParam.put("derate_date",DateUtils.getCurrentTime(DateUtils.STYLE_10));//减免申请时间
        addParam.put("state","0");//状态 0未完成；1完成
        return magCollectionRecordMapper.addDerate(addParam);
    }


    @Override
    public int addDerateHJD(Map addParam) {
        addParam.put("id", UUID.randomUUID().toString());//减免id
        addParam.put("derate_date",DateUtils.getCurrentTime(DateUtils.STYLE_5));//减免申请时间
        addParam.put("state","0");//状态 0未完成；1完成
        return magCollectionRecordMapper.addDerateHJD(addParam);
    }

    @Override
    public int updateDerate(Map updateParam) {
        return magCollectionRecordMapper.updateDerate(updateParam);
    }

    /**
     * 根据id查询还款记录信息（单条）
     * @param map
     * @return
     */
    public Map getRepaymentById(Map map){
    return repaymentMapper.selectRepaymentById(map);
    }

    /**
     * 线下还款服务层(方法整合)
     * @param param
     */
    @Transactional
    public int payForXianXiaImpl(Map param){
        String sumAmount=param.get("sumAmount").toString();//应还款总额
        String orderId=param.get("orderId").toString();//订单id
        String repaymentId = param.get("repaymentId").toString();//还款表id
        //1.更新还款表状态
        param.put("actualTime", DateUtils.getCurrentTime());//当前系统时间(实际还款时间)
        int i0=changePayState(param);

        //2.插入交易记录表mag_transaction_details
        Map mapTransaction =new HashMap();
       // transNo="TID"+System.currentTimeMillis(); //唯一流水号//
        mapTransaction.put("id", UUID.randomUUID().toString());//id
        mapTransaction.put("transNo","TID"+System.currentTimeMillis());
        mapTransaction.put("amount", sumAmount);//应还总额
        mapTransaction.put("repayment_id", repaymentId);//还款表id
        mapTransaction.put("order_id", orderId);//订单id
        mapTransaction.put("creat_time", DateUtils.getCurrentTime());//创建时间
        mapTransaction.put("source","1");//(0黄金袋，1商品贷)
        mapTransaction.put("des","单期线下第"+param.get("payCount")+"期还款");
        int i1=addTransaction(mapTransaction);

        //3.查询该订单下所有的还款记录，如果没有未还的且没有还款确认中的 就更新订单状态为已结清
        List<Map> listNotPays=getAllNotPayRepayments(param);
        if(listNotPays.size() == 0){
            //更改订单表状态并记录更新时间
            param.put("alterTime",DateUtils.getCurrentTime());
            changeOrderState(param);

        }
        //4.更改减免表状态
        int i2=changeDerateState(param);
        return 1;
    }

    /**
     * 更改还款表状态
     * @return
     */
    public int changePayState(Map map){
        return repaymentMapper.updateRepaymentState(map);
    }

    /**
     * 查询该订单下所有未还款的还款记录
     * @param map
     * @return
     */
    public  List<Map> getAllNotPayRepayments(Map map){
        return repaymentMapper.getAllNotPayRepayments(map);
    }

    /**
     * 更该订单表状态
     * @param map
     * @return
     */
    public int changeOrderState(Map map){
        return repaymentMapper.updateOrderState(map);
    }

    /**
     * 添加还款记录明细mag_transaction_details
     * @param map
     * @return
     */
    public int addTransaction(Map map){
       return repaymentMapper.insertTransaction(map);
    }

    /**
     * 更改减免表状态
     * @param map
     * @return
     */
    public int changeDerateState(Map map){
        return repaymentMapper.changeDerateState(map);
    }

    /**
     * 获取减免详情
     * @param map 查询条件
     * @return 结果
     */
    public Map getMagDerateDetail(Map map)
    {
        return magDerateMapper.getMagDerateDetail(map);
    }
}

