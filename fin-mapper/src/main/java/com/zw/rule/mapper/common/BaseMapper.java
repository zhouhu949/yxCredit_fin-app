package com.zw.rule.mapper.common;

/**
 * Created by Administrator on 2017/5/4.
 */
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {
    int deleteByExample(T o);

    /**
     * 删除（根据主键ID删除）
     * @param value
     * @return
     */
    int deleteByPrimaryKey(@Param("value") Long value);

    /**
     * 添加 （匹配有值的字段）
     * @param o
     * @return
     */
    int insertSelective(T o);

    /**
     * 更新（匹配有值的字段）
     * @param o
     * @return
     */
    int updateByPrimaryKeySelective(T o);

    int countByExample(T o);

    List<T> selectByExample(T o);

    /**
     * 查询（根据主键ID查询）
     * @param value
     * @return
     */
    T selectByPrimaryKey(@Param("value") Long value);
}