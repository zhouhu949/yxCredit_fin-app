package com.zw.rule.mapper.product;

import com.zw.rule.product.ProYield;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
public interface ProYieldMapper {
     List<Map> getQuato(ProYield proYield);
     List<Map> getQuatoList();
     int addQuato (ProYield proYield);
     int updateQuato(ProYield proYield);
     List<ProYield> getList(Map map);
}
