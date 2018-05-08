package com.zw.rule.reportManage.service;

import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.reportManage.Sorderdetail;

import java.util.*;

/**
 * Created by Administrator on 2018/3/5.
 * 报表功能服务层接口
 */
public interface ReportManageService {
    String getCompanyIdByUserId(String userId);
    //根据当前的部门id获取机构实体
    Branch getBranchById(String id);
    //查询顶级父节点id
    String getMostParsentId();
    //获取二级节点id
    List<String> getSecondIds(String topId);
    //获取当前登录人公司订单个数
    List<Map> getNowCompanyOrderCount(Map map);
    //代扣统计service接口
    List<Map> getNumOfDeducting(Map map);

    //销售订单报表专用查询
    List <Map> getReportAllToExcel(Map map);
    //逾期报表查询
    List<Map> getYuQiReportToExcel(Map map);

}
