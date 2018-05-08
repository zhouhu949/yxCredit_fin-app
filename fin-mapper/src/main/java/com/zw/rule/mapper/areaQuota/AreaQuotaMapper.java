package com.zw.rule.mapper.areaQuota;

import com.zw.rule.areaQuota.AreaQuota;

import java.util.List;
import java.util.Map;

/**
 * 区域限额设置持久层映射器
 * Created by Administrator on 2017/12/6.
 */
public interface AreaQuotaMapper {
    /**
     * 查询所有的区域限额设置
     * @return
     */
    public List<AreaQuota> selectAllQuota(Map map);
    //添加区域限额
    public int insertAreaQuota(AreaQuota areaQuota);
    //根据id查找区域限制
    public AreaQuota selectOneAreaQuotaById(String id);
    //根据id更改区域限额
    public int changeAreaQuotaById(AreaQuota areaQuota);
    //根据id更改区域限额状态
    public int changeState(Map map);

}
