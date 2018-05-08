package com.zw.rule.mapper.repayment;

import com.zw.rule.repayment.po.MagRepaymentExtension;

import java.util.List;
import java.util.Map;

/**
 * 展期数据接口
 */
public interface MagRepaymentExtensionMapper {
    /**
     * 插入展期
     * @param record 展期信息
     * @return 插入结果
     */
    int insert(MagRepaymentExtension record);

    /**
     * 根据id获取展期
     * @param id 展期id
     * @return 展期详情
     */
    MagRepaymentExtension selectById(String id);

    /**
     * 查询展期
     * @param map 参数
     * @return 展期信息
     */
    List<MagRepaymentExtension>  getExtensionList(Map map);

    /**
     * 查询订单展期
     * @param map 参数
     * @return 展期信息
     */
    List<Map<String, Object>>  getOrderExtensions(Map map);

    /**
     * 删除展期
     * @param id 展期id
     * @return 删除结果
     */
    int deleteById(String id);
}