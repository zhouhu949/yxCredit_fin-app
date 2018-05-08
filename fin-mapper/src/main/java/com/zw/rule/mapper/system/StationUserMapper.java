package com.zw.rule.mapper.system;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.StationUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StationUserMapper {
    int insert(StationUser record);

    int deleteByStationId(String stationId);

    List selectAll(ParamFilter param);

    int countAll(Map map);

    List selectByStationId(@Param("stationId") String stationId);

    int batchInsert(List<StationUser> list);
}