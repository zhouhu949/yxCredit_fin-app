package com.zw.rule.mapper.product;

import com.zw.rule.product.Fee;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
public interface FeeMapper {
    Fee getFeeByProductName(Map map);
}
