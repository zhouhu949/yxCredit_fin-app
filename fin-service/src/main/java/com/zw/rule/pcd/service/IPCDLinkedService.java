package com.zw.rule.pcd.service;

import com.zw.rule.pcd.po.City;
import com.zw.rule.pcd.po.District;
import com.zw.rule.pcd.po.Province;

import java.util.List;
import java.util.Map;

/**
 * 省市区联动接口
 */
public interface IPCDLinkedService {
    List getPCD(Map map) throws Exception;
    Province getProvinceById(String id) throws Exception;
    City getCityById(String id) throws Exception;
    District getDistrictById(String id) throws Exception;
}
