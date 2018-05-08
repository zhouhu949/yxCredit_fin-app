package com.zw.rule.pcd.service.impl;

import com.zw.rule.mapper.pcd.CityMapper;
import com.zw.rule.mapper.pcd.DistrictMapper;
import com.zw.rule.mapper.pcd.ProvinceMapper;
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.pcd.po.City;
import com.zw.rule.pcd.po.District;
import com.zw.rule.pcd.po.Province;
import com.zw.rule.pcd.service.IPCDLinkedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 省市区联动接口
 */
@Service
public class PCDLinkedServiceImpl  implements IPCDLinkedService {
    @Resource
    private ProvinceMapper provinceMapper;
    @Resource
    private DistrictMapper districtMapper;
    @Resource
    private CityMapper cityMapper;

    public List getPCD(Map map) throws Exception {
        String flag=map.get("flag").toString();
        List listPCD=null;
        if(flag.equals("province")){
            listPCD=provinceMapper.getProvince();
        }else if(flag.equals("city")){
            listPCD =cityMapper.getCity(map);
        }else if(flag.equals("district")){
            listPCD=districtMapper.getDistrict(map);
        }
        return listPCD;
    }

    /**
     * 根据id查询省名称
     * @param id
     * @return
     * @throws Exception
     */
    public Province getProvinceById(String id) throws Exception {
        Province province_name=provinceMapper.getProvinceById(id);
        return province_name;
    }
    /**
     * 根据id查询市名称
     * @param id
     * @return
     * @throws Exception
     */
    public City getCityById(String id) throws Exception {
        City city_name=cityMapper.getCityById(id);
        return city_name;
    }
    /**
     * 根据id查询区名称
     * @param id
     * @return
     * @throws Exception
     */
    public District getDistrictById(String id) throws Exception {
        District district_name=districtMapper.getDistrictById(id);
        return district_name;
    }
}

