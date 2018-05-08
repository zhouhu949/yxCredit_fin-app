package com.zw.rule.mapper.system;

import com.zw.rule.po.MagDict;
import com.zw.rule.po.MagDictDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface AppDictMapper {

    List<MagDict> getMagDictList(Map map);//获取大类字典列表

    List<MagDict> findByDictName(Map map);//根据名称查询是否已存在

    List<MagDict> findByDictCode(Map map);//根据code查询是否已存在

    int insert(MagDict dict);//新增大类

    int delete(String id);//删除大类

    MagDict getById(String id);//编辑获取详情

    List<String> findByDictId(Map map);

    List<MagDict> getCatagory();

    void update(MagDict magDict);//更新字典大类



}
