package com.zw.rule.quato.service.impl;


import com.zw.rule.product.Fee;
import com.zw.rule.product.ProYield;

import java.util.List;
import java.util.Map;

/**
 *
 * 功能说明：信贷类型/产品表service层接口
 * @author wangmin 2017-06-13
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：
 */
public interface IQuatoService {

    /**
     * 功能说明：查询费率
     * gaozhidong  2017-7-17
     * @return
     */
    List  getQuato(ProYield proYield);
    List  getQuatoList();
    int addQuato(ProYield proYield);
    int updateQuato(ProYield proYield);
    List<ProYield>  getList(Map map);
}

