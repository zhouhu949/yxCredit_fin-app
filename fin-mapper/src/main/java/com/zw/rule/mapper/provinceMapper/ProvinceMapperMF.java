package com.zw.rule.mapper.provinceMapper;

import com.zw.rule.provinceAndCity.Province;

import java.util.List;

/**
 * 查询省份映射器
 * Created by Administrator on 2017/12/6.
 */
public interface ProvinceMapperMF {
    //获取全部的省份信息
    public List<Province> selectAllProvince();
}
