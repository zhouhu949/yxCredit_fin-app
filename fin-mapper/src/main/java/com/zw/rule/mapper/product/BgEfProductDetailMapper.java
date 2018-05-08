package com.zw.rule.mapper.product;


import com.zw.rule.product.BgEfProductDetail;

import java.util.List;
import java.util.Map;

public interface BgEfProductDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(BgEfProductDetail record);

    int insertSelective(BgEfProductDetail record);

    BgEfProductDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BgEfProductDetail record);

    int updateByPrimaryKey(BgEfProductDetail record);

    List<Map> getBgData(String CrmOrderId);
}