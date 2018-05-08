package com.zw.rule.mapper.reportMapper;

import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.reportManage.Sorderdetail;

import java.util.Map;
import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 * 报表持久层映射器
 */
public interface ReportManageMapper {
//    List<Sorderdetail> selectReportAll(Map map);
    //根据userId获取用户的公司id
    String selectCompanyIdByUserId(String userId);
    //根据当前的部门id获取机构实体
    Branch selectBranchById(String id);
    //获取公司顶级父节点id
    String selectMostParsentId();
    //获取二级公司的id集合
    List<String> selectSecondIds(String topId);
    //获取子公司名称
    List<String> selectSonCompanyNames(String nowCompanyId);
    //获取当前登录人的公司的订单个数
    List<String> selectNowCompanyOrderCount(Map map);

    //销售订单报表查询离现在最近的那一条还款记录
    String selectDayForPayTime(Map map);
    //销售订单报表专用查询
    List<Map> selectReportAllToExcel(Map map);
    //逾期报表查询全部
    List<Map> selectYuQiReportToExcel(Map map);
    //根据customerId查询联系人信息
    List<Map> getLinkManByCustomerId(String customerID);

    //获取代扣次数
    List<Map> getNumOfDeducting(Map map);

}
