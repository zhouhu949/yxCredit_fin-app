package com.zw.rule.mapper.magServicePackage;



import com.zw.rule.magServicePackage.po.MagServicePackage;
import com.zw.rule.servicePackage.po.ServicePackage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface MagServicePackageMapper {
    List<Map> getList(Map param);
    List<MagServicePackage> getPackageType(Map param);

    int updateMagServicePackage(MagServicePackage magServicePackage);

    int insertMagServicePackage(MagServicePackage magServicePackage);

    //int delMagServicePackage(String id);

    int updateState(Map param);
}
