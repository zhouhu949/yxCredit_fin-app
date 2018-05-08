package com.zw.rule.servicePackage.service.impl;

import com.zw.rule.mapper.servicePackage.ServicePackageMapper;
import com.zw.rule.servicePackage.po.ServicePackage;
import com.zw.rule.servicePackage.service.ServicePackageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service
public class ServicePackageServiceImpl implements ServicePackageService{
    @Resource
    private ServicePackageMapper servicePackageMapper;
    //获取服务包列表
    @Override
    public List<ServicePackage> getList(Map map) {
        return servicePackageMapper.getList(map);
    }

    //获取服务包类型
    @Override
    public List<ServicePackage>  getMagList(){
        return servicePackageMapper.getMagList();
    }

    public int updateServicePackage(ServicePackage servicePackage){
        return servicePackageMapper.updateServicePackage(servicePackage);
    }

    @Override
    public int insertServicePackage(ServicePackage servicePackage) {
        return servicePackageMapper.insertServicePackage(servicePackage);
    }

}
