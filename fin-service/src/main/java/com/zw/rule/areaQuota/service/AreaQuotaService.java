package com.zw.rule.areaQuota.service;

import com.zw.rule.areaQuota.AreaQuota;

import java.util.List;
import java.util.Map;

/**
 * 区域限额设置服务层接口
 * Created by Administrator on 2017/12/6.
 */
public interface AreaQuotaService {
    //获取全部区域限额信息
    public List<AreaQuota> getAllQuota(Map map);
    //添加区域限额
    public int addAreaQuota (AreaQuota areaQuota);
    //根据id查询单个区域限额
    public AreaQuota getOneAreaQuotaByID(String id);
    //更改区域限额
    public int updateAreaQuotaById(AreaQuota areaQuota);
    //更改区域限额状态
    public int changeAreQuotaState(Map map);
}
