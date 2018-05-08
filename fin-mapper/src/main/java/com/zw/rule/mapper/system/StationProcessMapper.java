package com.zw.rule.mapper.system;

import com.zw.rule.po.StationProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StationProcessMapper {

    int deleteByStationId(String stationId);

    int insert(StationProcess record);

    int countStationProcess(Map map);

    List selectByStationId(Map map);

    int batchInsert(List<StationProcess> list);
}