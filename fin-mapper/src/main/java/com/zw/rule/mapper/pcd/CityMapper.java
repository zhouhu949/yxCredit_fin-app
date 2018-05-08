package com.zw.rule.mapper.pcd;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.pcd.po.City;

import java.util.List;
import java.util.Map;

/**
 * Created by shengkf on 2017/6/14.
 */
public interface CityMapper extends BaseMapper<City> {
    public List<City> getCity(Map map) throws Exception;
    public City getCityById(String id) throws Exception;
}
