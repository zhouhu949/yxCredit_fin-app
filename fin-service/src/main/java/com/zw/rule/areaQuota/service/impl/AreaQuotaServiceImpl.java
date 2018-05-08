package com.zw.rule.areaQuota.service.impl;

import com.zw.rule.areaQuota.AreaQuota;
import com.zw.rule.areaQuota.service.AreaQuotaService;
import com.zw.rule.mapper.areaQuota.AreaQuotaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */
@Service
public class AreaQuotaServiceImpl implements AreaQuotaService {
    @Autowired
    private AreaQuotaMapper mapper;
    //获取全部区域限额信息
    public List<AreaQuota> getAllQuota(Map map){
        return mapper.selectAllQuota(map);
    }
    //添加区域限额
    public int addAreaQuota (AreaQuota areaQuota){
        return mapper.insertAreaQuota(areaQuota);
    }
    //根据id查找区域限额
    public AreaQuota getOneAreaQuotaByID(String id){
        return mapper.selectOneAreaQuotaById(id);
    }
    //更改区域限额
    public int updateAreaQuotaById(AreaQuota areaQuota){
        return mapper.changeAreaQuotaById(areaQuota);
    }
    //更改区域限额状态
    public int changeAreQuotaState(Map map){
        return mapper.changeState(map);
    }
}
