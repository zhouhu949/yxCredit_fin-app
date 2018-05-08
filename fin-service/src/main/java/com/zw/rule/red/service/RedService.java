package com.zw.rule.red.service;


import com.zw.rule.product.Red;

import java.util.List;
import java.util.Map;

/**
 *
 * 功能说明：信贷类型/产品表service层接口red
 * @author wangmin 2017-06-13
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：
 */
public interface RedService {

    /**
     * 功能说明：查询费率
     * gaozhidong  2017-7-17
     * @return
     */
    List  getRedList(Red red);
    int addRed(Red red);
    int updateRed(Red red);
    List<Red>  getList(Map map);
    List  getRedInfoList(Map map);

}

