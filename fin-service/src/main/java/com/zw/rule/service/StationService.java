package com.zw.rule.service;

import com.alibaba.fastjson.JSONArray;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Station;
import com.zw.rule.po.StationProcess;
import com.zw.rule.po.StationUser;
import com.zw.rule.po.User;

import java.util.List;
import java.util.Map;
public interface StationService {
    List<Station> getAllStation(ParamFilter param);
    //查询所有岗位(无参数)
    List<Station> getAllStationNoParam();

    List<Station> getAllValidStation();

    Station findById(String id);

    Boolean createStation(Map map, User currentUser);

    Boolean updateStation(Map map, User currentUser);

    Boolean updateStatus(Map map);

    List<Station> validateStationOnly(Map map);

    int countStation(Map map);

    Response getProcess();

    Response getProNodeByProId(Long processId);

    List<StationProcess> getProcessByStationId(ParamFilter param);

    Boolean createStationProcess(JSONArray jsonArray, String stationId);

    Boolean createStationUser(Map map);

    List<StationUser> selectAllSU(ParamFilter param);

    List selectSUByStationId(String stationId);
}
