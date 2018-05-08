package com.zw.rule.loanManage.service;

import com.zw.rule.core.Response;
import com.zw.rule.loanmange.po.SettleRecord;

import java.util.List;
import java.util.Map;

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
public interface LoanManageService {

    //还款查询
    List  getLoanManage(Map map);
    //还款查询
    List getRepaymentList(Map map);
    //还款查询HJD
    List  getLoanManageHJD(Map map);
    //获取结清数据
    Response getSettleData(Map map);
    //添加提前结清数据
    Response addSettleData(Map map);
    //修改提前结清数据
    Response updateSettleData(Map map);
    //查询提前结清
    SettleRecord getSettleList(Map map);
    //查询该订单下的所有交易记录
    List<Map> getJYDetailList(Map map);

}
