package com.zw.rule.mapper.product;

import com.zw.rule.product.Red;
import com.zw.rule.product.RedInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
public interface RedMapper {
     List<Map> getRedList(Red red);
     int addRed(Red red);
     int updateRed(Red red);
     List<Red> getList(Map map);
     List<RedInfo> getRedInfoList(Map map);
}
