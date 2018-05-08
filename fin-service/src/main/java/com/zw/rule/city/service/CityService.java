package com.zw.rule.city.service;

import com.zw.rule.provinceAndCity.City;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */
public interface CityService {
    public List<City> getCitiesByPId(int PId);
}
