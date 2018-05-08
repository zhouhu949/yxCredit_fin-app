package com.zw.rule.mapper.servicePackage;

import com.zw.rule.servicePackage.po.ServicePackage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */
public interface ServicePackageMapper {

    List<ServicePackage> getList(Map param);

    List<ServicePackage> getMagList();

    int updateServicePackage(ServicePackage servicePackage);

    int insertServicePackage(ServicePackage servicePackage);
}
