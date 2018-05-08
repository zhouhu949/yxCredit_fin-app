package com.zw.rule.mapper.system;

import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Station;
import com.zw.rule.po.User;
import com.zw.rule.process.po.Process;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/12
 ********************************************************/
public interface StationMapper {
    List<Station> getAllStation(ParamFilter param);
    //获取所有岗位(无参数)
    List<Station> getAll();

    List<Station> getAllValidStation();

    Station findById(String id);

    int createStation(Station station);

    int updateStation(Station station);

    int updateStatus(Map<String, Object> map);

    List<Station> validateStationOnly(Station station);

    int countStation(Map map);

    List<Map> getProcess();

    List<Map> getProNodeByProId(@Param("processId") Long processId);

    List<String> getStationByUPN(@Param("userId") Long userId,@Param("processId") Long processId,@Param("processNodeId") Long processNodeId);
}
