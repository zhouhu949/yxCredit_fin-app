package com.zw.rule.mapper.servicePackage;

import com.zw.rule.servicePackage.po.ServicePackageOrder;
import com.zw.rule.servicePackage.po.ServicePackageRepayment;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/8.
 */
public interface ServicePackageOrderMapper {
    //获取订单服务包
    List<ServicePackageOrder> getServicePackageOrderList(Map map);

    List<ServicePackageRepayment> getServicePackageRepayment(Map map);

    int updatePackState(Map map);
}
