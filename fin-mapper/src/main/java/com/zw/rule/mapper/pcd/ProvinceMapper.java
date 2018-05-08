package com.zw.rule.mapper.pcd;


import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.pcd.po.Province;

import java.util.List;
import java.util.Map;

/**
 * Created by shengkf on 2017/6/14.
 */
public interface ProvinceMapper extends BaseMapper<Province> {
    public List<Province> getProvince() throws Exception;
    public Province getProvinceById(String id) throws Exception;
}
