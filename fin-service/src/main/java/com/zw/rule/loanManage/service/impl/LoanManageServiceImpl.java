package com.zw.rule.loanManage.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.core.Response;
import com.zw.rule.loanManage.service.LoanManageService;
import com.zw.rule.loanmange.po.LoanManage;
import com.zw.rule.loanmange.po.SettleRecord;
import com.zw.rule.mapper.loanmange.LoanManageMapper;
import com.zw.rule.mapper.repayment.MagRepaymentMapper;
import com.zw.rule.mapper.servicePackage.ServicePackageOrderMapper;
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.repayment.po.MagRepayment;
import com.zw.rule.servicePackage.po.ServicePackageOrder;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年11月17日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:yaoxuetao <br>
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
public class LoanManageServiceImpl implements LoanManageService {
    @Autowired
    private LoanManageMapper loanManageMapper;

    @Autowired
    private MagRepaymentMapper magRepaymentMapper;

    @Autowired
    private ServicePackageOrderMapper servicePackageOrderMapper;

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;


    @Override
    public List getLoanManage(Map map) {
//        if ("admin".equals(map.get("account"))){
//            map.put("admin",map.get("account"));
//        }else {
//            SysDepartment sysDepartment=sysDepartmentMapper.findById((Long) map.get("orgid"));
//            //Pid=0是总公司
//            if (sysDepartment.getPid()==0){
//                map.put("headquarters",sysDepartment.getId());
//            }else {
//                map.put("branch",sysDepartment.getId());
//            }
//        }
        return loanManageMapper.getLoanManage(map);
    }

    @Override
    public List getRepaymentList(Map map) {
        return loanManageMapper.getRepaymentList(map);
    }

    @Override
    public List getLoanManageHJD(Map map) {
//        if ("admin".equals(map.get("account"))){
//            map.put("admin",map.get("account"));
//        }else {
//            SysDepartment sysDepartment=sysDepartmentMapper.findById((Long) map.get("orgid"));
//            //Pid=0是总公司
//            if (sysDepartment.getPid()==0){
//                map.put("headquarters",sysDepartment.getId());
//            }else {
//                map.put("branch",sysDepartment.getId());
//            }
//        }
        return loanManageMapper.getLoanManageHJD(map);
    }

    //获取结清数据
    @Override
    public Response getSettleData(Map map){
        String loanId=map.get("loanId").toString();
        Response response=new Response();
        String normal=map.get("type").toString();//0正常结清 1非正常结清
        SettleRecord settleRecord=null;
        Map<String,Object>dataMap=new HashedMap();
        //放款数据
        LoanManage loanManage=loanManageMapper.selectByPrimaryKey(loanId);
        //已有结清数据
        if ("1".equals(loanManage.getSettleState())||"2".equals(loanManage.getSettleState())){
            Map<String,Object> mapSettleRecord=new HashedMap();
            mapSettleRecord.put("loanId",loanManage.getId());
            mapSettleRecord.put("type",normal);
            settleRecord=loanManageMapper.getMagSettleRecord(mapSettleRecord);
        }
        Map<String,Object> mapPackage=new HashedMap();
        mapPackage.put("orderId",loanManage.getOrderId());
        //获取服务包信息
        List<ServicePackageOrder> servicePackageOrderList=servicePackageOrderMapper.getServicePackageOrderList(mapPackage);
        int maxMonth = 0;//最大提前还款期数
        if(servicePackageOrderList.size()>0){
            for(int i=0;i<servicePackageOrderList.size();i++){
                String month= servicePackageOrderList.get(i).getMonth();
                if(Integer.parseInt( "".equals(month)||month == null?"0":month ) > maxMonth){
                    maxMonth = Integer.parseInt(servicePackageOrderList.get(i).getMonth());
                }
            }
        }

        Map<String,Object> mapRepayment=new HashedMap();
        mapRepayment.put("orderId",loanManage.getOrderId());
        mapRepayment.put("state","2");
        List<MagRepayment> magRepaymentList=magRepaymentMapper.getRepaymentList(mapRepayment);//还款计划所有已还的

        mapRepayment.put("state",null);
        List<MagRepayment> magRepaymentAllList=magRepaymentMapper.getRepaymentList(mapRepayment);//所有的还款计划(不管已还的还是未还的)
        //判断是否存在还款确认中的还款计划
        List<MagRepayment> sureingRepayment = magRepaymentMapper.getSureingRepayment(map);
        int sureI=sureingRepayment.size();
        if("0".equals(normal)){//正常结清
            if(sureI>0){
                response.setCode(1);
                response.setMsg("存在还款确认中的还款计划");
                return  response;
            }
            if(servicePackageOrderList.size() == 0){//没有服务包不做限制

            }else if (maxMonth > magRepaymentList.size() ){//有服务包的判定服务包提前还款期数到了没有
                response.setCode(1);
                response.setMsg("不符合提前结清条件！");
                return  response;
            }
            if("2".equals(loanManage.getSettleState())){
                response.setCode(1);
                response.setMsg("提前结清已完成！");
                return  response;
            }
        }else {//非正常结清
            //获取所有还款确认中的还款计划
            if(sureI>0){
                response.setCode(1);
                response.setMsg("存在还款确认中的还款计划");
                return  response;
            }
            }

        dataMap.put("loanManage",loanManage);
        dataMap.put("settleRecord",settleRecord);
        dataMap.put("magRepaymentAllList",magRepaymentAllList);
        dataMap.put("normal",normal);
        response.setData(dataMap);
        return  response;
    }


    //添加提前结清数据
    @Override
    public Response addSettleData(Map map){
        Response response=new Response();
        LoanManage loanManage=loanManageMapper.selectByPrimaryKey(map.get("loanId").toString());
        if ("1".equals(loanManage.getSettleState())||"2".equals(loanManage.getSettleState())){
            response.setCode(1);
            response.setMsg("已经提交提前结清，请勿重复提交！");
        }else {
            SettleRecord settleRecord =new SettleRecord();
            settleRecord.setId(UUID.randomUUID().toString());
            settleRecord.setOrderId(loanManage.getOrderId());
            settleRecord.setLoanId(loanManage.getId());
            settleRecord.setSettleType(map.get("normal").toString());
            //是不是正常结清
            if("0".equals(map.get("normal").toString())){
                settleRecord.setSettleFee(map.get("settleFee").toString());
                //settleRecord.setSettleAmount(map.get("allAmount").toString());
            }else {
                settleRecord.setSettleAmount(map.get("allAmount").toString());
                settleRecord.setEffectiveTime(map.get("beginTime").toString());
            }
            settleRecord.setState("1");
            settleRecord.setCreateTime(DateUtils.getDateString(new Date()));
            settleRecord.setOperatorId(map.get("handlerId").toString());
            settleRecord.setOperatorName(map.get("handlerName").toString());
            loanManageMapper.addSettleRecord(settleRecord);
            LoanManage loanManageMod=new LoanManage();
            loanManageMod.setId(loanManage.getId());
            //提前结清状态改为提前结清
            loanManageMod.setSettleState("1");
            loanManageMapper.updateByPrimaryKey(loanManageMod);
            response.setMsg("提交成功！");
        }
        return  response;
    }
    //修改提前结清数据
    @Override
    public Response updateSettleData(Map map){
        Response response=new Response();
        LoanManage loanManage=loanManageMapper.selectByPrimaryKey(map.get("loanId").toString());
        if ("2".equals(loanManage.getSettleState())){
            response.setCode(1);
            response.setMsg("提前结清已完成，不能修改！");
        }else {
            Map<String,Object> mapsettleRecord=new HashedMap();
            mapsettleRecord.put("id",map.get("settleRecordId"));
            //是不是正常结清
            if("0".equals(map.get("normal").toString())){
                mapsettleRecord.put("settleFee",map.get("allAmount").toString());
            }else {
                mapsettleRecord.put("settleAmount",map.get("allAmount").toString());
                mapsettleRecord.put("effectiveTime",map.get("beginTime").toString());
            }
            loanManageMapper.updateSettleRecord(mapsettleRecord);
            response.setMsg("提交成功！");
        }
        return  response;
    }

    public SettleRecord getSettleList(Map map)
    {
        return loanManageMapper.getMagSettleRecord(map);
    }

    /**
     * 查询该订单下所有交易记录
     * @param map
     * @return
     */
    public List<Map> getJYDetailList(Map map){
        List<Map> listTransaction=loanManageMapper.getJYDetailList(map);
        return listTransaction;
    }
}

