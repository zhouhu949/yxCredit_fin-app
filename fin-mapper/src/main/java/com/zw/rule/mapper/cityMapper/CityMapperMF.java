package com.zw.rule.mapper.cityMapper;

import com.zw.rule.provinceAndCity.City;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */
public interface CityMapperMF {
    public List<City> selectCitiesByPId(int PId);
}
