package com.zw.rule.service.impl;

import com.google.common.base.Strings;
import com.zw.rule.mapper.system.AppDictMapper;
import com.zw.rule.mapper.system.AppDictDetailMapper;
import com.zw.rule.po.MagDict;
import com.zw.rule.po.MagDictDetail;
import com.zw.rule.service.AppDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AppDictServiceImpl implements AppDictService {

    @Resource
    private AppDictMapper appDictMapper;

    @Resource
    private AppDictDetailMapper appDictDetailMapper;
    @Override
    public void add(Map map) {
        MagDict dict = new MagDict();
        dict.setState("1");
        dict.setDescription((String) map.get("description"));
        dict.setId(UUID.randomUUID().toString());
        dict.setCode((String) map.get("code"));
        dict.setName((String) map.get("name"));
        checkNotNull(dict,"字典信息不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getCode()),"字典Code不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getName()),"字典名称不能为空");
        appDictMapper.insert(dict);
    }

    @Override
    public void addDetail(Map map) {
        MagDictDetail dict = new MagDictDetail();
        dict.setState("1");
        dict.setDescription((String) map.get("description"));
        dict.setId(UUID.randomUUID().toString());
        dict.setCode((String) map.get("code"));
        dict.setName((String) map.get("name"));
        dict.setDictId((String) map.get("dictId"));
        dict.setDictName((String) map.get("dictName"));
        checkNotNull(dict,"字典信息不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getCode()),"字典Code不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getName()),"字典名称不能为空");
        appDictDetailMapper.insert(dict);
    }

    @Override
    public List<MagDict> getList(Map map) {
        return appDictMapper.getMagDictList(map);
    }

    @Override
    public List<MagDictDetail> getListDictDetail(Map map) {
        return appDictDetailMapper.getListDictDetail(map);
    }



    /**
     * 更新字典
     * @param map
     */
    @Override
    public void update(Map map) {
        MagDict dict = new MagDict();
        dict.setCode((String) map.get("code"));
        dict.setName((String) map.get("name"));
        dict.setDescription((String)map.get("description"));
        dict.setId((String)map.get("id"));
        appDictMapper.update(dict);
    }

    /**
     * 更新字典明细
     * @param map
     */
    @Override
    public void updateDetail(Map map) {
        MagDictDetail dict = new MagDictDetail();
        dict.setCode((String) map.get("code"));
        dict.setName((String) map.get("name"));
        dict.setDescription((String)map.get("description"));
        dict.setId((String)map.get("id"));
        dict.setDictId((String)map.get("dictId"));
        dict.setDictName((String)map.get("dictName"));
        dict.setState("1");
        appDictDetailMapper.update(dict);
    }

//    /**
//     * 获取树的节点
//     * @return
//     */
//    @SuppressWarnings({"unchecked"})
//    @Override
//    public List<JSTree> getTree() {
//        return dictDao.findMap("getTree");
//    }
//    /**
//     * 根据父id查询字典信息
//     * @param parentId
//     * @return
//     */
//
//    @Override
//    public List<Dict> getListByParentId(String parentId) {
//        List<Dict> dictList = new LinkedList<>();
//        List<Dict> subList = dictDao.find("getListByParentId",parentId);
//        Dict dict = dictDao.findUnique("getById",parentId);
//        dictList.add(dict);
//        if(subList!=null && subList.size()>0){
//            dictList.addAll(subList);
//        }
//        return dictList;
//    }
//
    /**
     * 获取是否为大类
     * @return
     */
    @Override
    public List<MagDict> getCatagory() {
        return appDictMapper.getCatagory();
    }
    /**
     * 删除字典大类
     * @param id
     */
    @Override
    public void delete(String id) {
        appDictMapper.delete(id);
    }
    /**
     * 删除字典明细
     * @param id
     */
    @Override
    public void deleteDetail(String id) {
        appDictDetailMapper.delete(id);
    }

    //根据id获取字典信息
	@Override
	public MagDict getById(String id) {
		return appDictMapper.getById(id);
	}

    //根据id获取字典信息
    @Override
    public MagDictDetail getByDetailId(String id) {
        return appDictDetailMapper.getById(id);
    }

    @Override
    public List<String> findByDictId(Map map) {
        return appDictMapper.findByDictId(map);
    }

    @Override
    public List<String> findByDictDetailId(Map map) {
        return appDictDetailMapper.findByDictId(map);
    }

    /**
     * 根据name得到value集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<MagDict> findByDictName(Map map){
        return appDictMapper.findByDictName(map);
    }
    /**
     * 根据code得到value集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<MagDict> findByDictCode(Map map){
        return appDictMapper.findByDictCode(map);
    }

    @Override
    public List<MagDictDetail> findDetailByDictName(Map map){
        return appDictDetailMapper.findDetailByDictName(map);
    }


    @Override
    public List<MagDictDetail> findDetailByDictCode(Map map){
        return appDictDetailMapper.findDetailByDictCode(map);
    }

    /**
     * 根据key和code获取对应的name
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public String getDictInfo(String key,String code) throws Exception {
        Map map = new HashMap();
        map.put("key",key);
        map.put("code",code);
        Map paramMap = appDictDetailMapper.getDictInfo(map);
        if(paramMap == null){
            return null;
        }
        return paramMap.get("name").toString();
    }

    /**
     * 根据key得到value集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public String getDictCode(String key,String name) throws Exception {
        Map map = new HashMap();
        map.put("key",key);
        map.put("name",name);
        Map paramMap = appDictDetailMapper.getDictCode(map);
        if(paramMap == null){
            return null;
        }
        return paramMap.get("code").toString();
    }


//    /**
//     * 根据字典大类获取明细
//     * @param dictName
//     * @return
//     */
//    @Override
//    public List<Dict> getDetailList(String dictName){
//        return dictDao.find("getDetailList",dictName);
//    }
//    /**
//     * 根据字典name获取明细
//     * @param name
//     * @return
//     */
//    @Override
//    public List<Dict> getListByName(String name) {
//        return dictDao.find("getListByName",name);
//    }
//    /**
//     * 根据字典code获取明细
//     * @param code
//     * @return
//     */
//    @Override
//    public List<Dict> getListByCode(String code) {
//        return dictDao.find("getListByCode",code);
//    }
//
//
//    /**
//     * 根据字典code获取明细
//     * @param code
//     * @return
//     */
//    @Override
//    public String  getNameByCode(String code) {
//        Dict dict =  dictDao.findUnique("getNameByCode",code);
//        String name = dict.getName();
//        if(name==null){
//            return null;
//        }
//        return name;
//    }
//
//    @Override
//    public String getDetilNameByCode(String code, String parentId) {
//        Map map = new HashMap();
//        map.put("code",code);
//        map.put("parent_id",parentId);
//        Dict dict =  dictDao.findUnique("getDetailNameByCode",map);
//        String name = dict.getName();
//        if(name==null){
//            return null;
//        }
//        return name;
//    }
}
