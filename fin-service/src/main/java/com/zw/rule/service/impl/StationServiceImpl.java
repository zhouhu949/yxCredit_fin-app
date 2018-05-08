package com.zw.rule.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.rule.core.Response;
import com.zw.rule.jeval.tools.StringUtil;
import com.zw.rule.mapper.system.StationMapper;
import com.zw.rule.mapper.system.StationProcessMapper;
import com.zw.rule.mapper.system.StationUserMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Station;
import com.zw.rule.po.StationProcess;
import com.zw.rule.po.StationUser;
import com.zw.rule.po.User;
import com.zw.rule.service.StationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *         岗位管理业务逻辑
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/12
 ********************************************************/
@Service
public class StationServiceImpl implements StationService{
    @Resource
    private StationMapper stationMapper;
    @Resource
    private StationProcessMapper stationProcessMapper;
    @Resource
    private StationUserMapper stationUserMapper;

    @Override
    public List<Station> getAllStation(ParamFilter param) {
        int resultCount = countStation(param.getParam());
        param.getPage().setResultCount(resultCount);
        return stationMapper.getAllStation(param);
    }
    //查询所有岗位(无参数)
    public List<Station> getAllStationNoParam(){
        return stationMapper.getAll();
    }
    @Override
    public List<StationUser> selectAllSU(ParamFilter param) {
        int resultCount = stationUserMapper.countAll(param.getParam());
        if(param.getPage() != null){
            param.getPage().setResultCount(resultCount);
        }
        return stationUserMapper.selectAll(param);
    }


    @Override
    public List selectSUByStationId(String stationId){
        return stationUserMapper.selectByStationId(stationId);
    }

    @Override
    public List<Station> getAllValidStation() {
        return stationMapper.getAllValidStation();
    }

    @Override
    public Station findById(String id) {
        return stationMapper.findById(id);
    }

    @Override
    public Boolean createStation(Map map, User currentUser) {
        Station station = new Station();
        String uuid = UUID.randomUUID().toString();
        station.setId(uuid);
        String stationName = map.get("stationName").toString();
        String stationDesc = map.get("stationDesc").toString();
        if(stationName!=null && !"".equals(stationName)){
            station.setStationName(stationName);
        }
        if(stationDesc!=null && !"".equals(stationDesc)){
            station.setStationDesc(stationDesc);
        }
        station.setCreateor(currentUser.getUserId());
        station.setCreateTime(new Date());
        station.setUpdateor(currentUser.getUserId());
        station.setUpdateTime(new Date());

        JSONArray jsonArray = JSONArray.parseArray(map.get("process").toString());
        boolean flag = createStationProcess(jsonArray,station.getId());

        int num = 0;
        if(flag) num = stationMapper.createStation(station);
        return num>0;
    }

    @Override
    public Boolean updateStation(Map map, User currentUser) {
        String stationId = map.get("id").toString();
        String stationName = map.get("stationName").toString();
        String stationDesc = map.get("stationDesc").toString();
        Station station = new Station();
        station.setId(stationId);
        if(stationName!=null && !"".equals(stationName)){
            station.setStationName(stationName);
        }
        if(stationDesc!=null && !"".equals(stationDesc)){
            station.setStationDesc(stationDesc);
        }
        station.setUpdateor(currentUser.getUserId());
        station.setUpdateTime(new Date());
        boolean flag = false;
        int num = 0;
        String process = map.get("process").toString();
        if(process != null && !process.equals("")){
            stationProcessMapper.deleteByStationId(stationId);
            JSONArray jsonArray = JSONArray.parseArray(process);
            flag = createStationProcess(jsonArray,stationId);
            if(flag) num = stationMapper.updateStation(station);
        }else{
            if(!flag) num = stationMapper.updateStation(station);
        }
        return num>0;
    }

    @Override
    public Boolean updateStatus(Map map) {
        int num = stationMapper.updateStatus(map);
        return num>0;
    }

    @Override
    public List<Station> validateStationOnly(Map map) {
        Station station = new Station();
        station.setStationName(map.get("stationName").toString());
        return stationMapper.validateStationOnly(station);
    }

    @Override
    public int countStation(Map map) {
        int num = stationMapper.countStation(map);
        return num;
    }

    @Override
    public Response getProcess(){
        List resList = stationMapper.getProcess();
        return new Response(resList);
    }

    @Override
    public Response getProNodeByProId(Long processId){
        List resList = stationMapper.getProNodeByProId(processId);
        return new Response(resList);
    }

    @Override
    public List<StationProcess> getProcessByStationId(ParamFilter param) {
        int resultCount = stationProcessMapper.countStationProcess(param.getParam());
        if(param.getPage() != null){
            param.getPage().setResultCount(resultCount);
        }
        List list = stationProcessMapper.selectByStationId(param.getParam());
        return list;
    }

    @Override
    public Boolean createStationProcess(JSONArray jsonArray,String stationId) {
        int num = 0;
        ArrayList list = new ArrayList();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            String processId = (String)jsonObject.get("processId");
            String nodeIds = (String)jsonObject.get("nodeId");
            String [] nodeIdArr = nodeIds.split(",");
            for(int j=0;j<nodeIdArr.length;j++){
                StationProcess stationProcess = new StationProcess();
                String uuidP = UUID.randomUUID().toString();
                stationProcess.setId(uuidP);
                stationProcess.setStationId(stationId);
                stationProcess.setProcessId(Long.valueOf(Long.parseLong(processId)));
                stationProcess.setNodeId(Long.valueOf(Long.parseLong(nodeIdArr[j])));
                list.add(stationProcess);
                //num += stationProcessMapper.insert(stationProcess);
            }
        }
        num = stationProcessMapper.batchInsert(list);
        return num>0;
    }

    @Override
    public Boolean createStationUser(Map map) {
        ArrayList list = new ArrayList();
        String stationId = map.get("stationId").toString();
        stationUserMapper.deleteByStationId(stationId);
        int num = 0;
        List<Long> userIds = StringUtil.toLongList(map.get("userIds").toString());
        for(int i=0;i<userIds.size();i++){
            StationUser stationUser = new StationUser();
            String uuid = UUID.randomUUID().toString();
            stationUser.setId(uuid);
            stationUser.setStationId(stationId);
            stationUser.setUserId(userIds.get(i));
            list.add(stationUser);
            //num += stationUserMapper.insert(stationUser);
        }
        num = stationUserMapper.batchInsert(list);
        return num>0;
    }
}
