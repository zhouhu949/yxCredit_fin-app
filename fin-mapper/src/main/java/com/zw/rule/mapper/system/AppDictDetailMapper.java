package com.zw.rule.mapper.system;

import com.zw.rule.po.MagDict;
import com.zw.rule.po.MagDictDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface AppDictDetailMapper {

    List<MagDictDetail> getListDictDetail(Map map);//获取明细字典列表

    List<MagDictDetail> findDetailByDictName(Map map);//根据名称查询是否已存在

    List<MagDictDetail> findDetailByDictCode(Map map);//根据code查询是否已存在

    int insert(MagDictDetail dict);//新增大类

    int delete(String id);

    MagDictDetail getById(String id);

    List<String> findByDictId(Map map);

    int update(MagDictDetail magDictDetail);

    Map getDictInfo(Map map);

    Map getDictCode(Map map);

}
