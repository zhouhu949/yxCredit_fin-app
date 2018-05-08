package com.zw.rule.magServicePackage.Service.impl;

import com.zw.rule.magServicePackage.po.MagServicePackage;
import com.zw.rule.magServicePackage.Service.MagServicePackageService;
import com.zw.rule.mapper.magServicePackage.MagServicePackageMapper;
import com.zw.rule.mapper.servicePackage.ServicePackageMapper;
import com.zw.rule.servicePackage.po.ServicePackage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */

@Service
public class MagServicePackageServiceImpl implements MagServicePackageService {
    @Resource
    private MagServicePackageMapper magServicePackageMapper;
    @Resource
    private ServicePackageMapper servicePackageMapper;
    //获取服务包列表
    @Override
    public List<Map> getList(Map map) {
        return magServicePackageMapper.getList(map);
    }


//获取包类型


    @Override
    public List<MagServicePackage> getPackageType(Map param) {
        return magServicePackageMapper.getPackageType(param);
    }
    public int updateMagServicePackage(MagServicePackage magServicePackage){
        return magServicePackageMapper.updateMagServicePackage(magServicePackage);
    }
    public int insertMagServicePackage(MagServicePackage magServicePackage){

        return magServicePackageMapper.insertMagServicePackage(magServicePackage);
    }
    public int updateState(Map param){
        return magServicePackageMapper.updateState(param);
    }
}

