package com.zw.rule.mapper.pcd;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.pcd.po.District;

import java.util.List;
import java.util.Map;

/**
 * Created by shengkf on 2017/6/14.
 */
public interface DistrictMapper extends BaseMapper<District> {
    public List<District> getDistrict(Map map) throws Exception;
    public District getDistrictById(String id) throws Exception;
}
