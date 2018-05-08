package com.zw.rule.magServicePackage.Service;

/*
     *
     * Created by Administrator on 2017/12/6.
     */


import com.zw.rule.magServicePackage.po.MagServicePackage;
import com.zw.rule.servicePackage.po.ServicePackage;

import java.util.List;
import java.util.Map;

public interface MagServicePackageService {
    //获取服务包列表
    List<Map> getList(Map param);


    //获取包类型

    List<MagServicePackage> getPackageType(Map param);

    //更新服务包
    int updateMagServicePackage(MagServicePackage magServicePackage);

    //插入
    int insertMagServicePackage(MagServicePackage magServicePackage);

    //int delServicePackage(String id);
    //更新状态
    int updateState(Map param);
}
