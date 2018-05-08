package com.zw.rule.servicePackage.service;


import com.zw.rule.servicePackage.po.ServicePackage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface ServicePackageService {

    //获取服务包列表
    List<ServicePackage> getList(Map param);

    //获取服务包类型
    List<ServicePackage>  getMagList();

    //更新服务包
    int updateServicePackage(ServicePackage servicePackage);

    int insertServicePackage(ServicePackage servicePackage);
}
