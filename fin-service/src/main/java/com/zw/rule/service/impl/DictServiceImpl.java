package com.zw.rule.service.impl;

import com.google.common.base.Strings;
import com.zw.base.util.GeneratePrimaryKeyUtils;
import com.zw.rule.mapper.system.DictDao;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Dict;
import com.zw.rule.pojo.JSTree;
import com.zw.rule.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictDao dictDao;
    @Override
    public void add(Map map) {
        String parentId = (String)map.get("parentId");
        Dict dict = new Dict();
        dict.setCode((String) map.get("code"));
        dict.setName((String) map.get("name"));
        dict.setIsCatagory((String) map.get("isCatagory"));
        dict.setRemark((String) map.get("remark"));
        dict.setType(map.get("type").toString());
        dict.setParentId(parentId);
        dict.setCreateBy(map.get("createName").toString());
        dict.setUpdateBy("");
        dict.setValue(map.get("value").toString());
        checkNotNull(dict,"字典信息不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getCode()),"字典Code不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getName()),"字典名称不能为空");
        checkArgument(!Strings.isNullOrEmpty(dict.getIsCatagory()),"字典类型不能为空");
        dictDao.save(dict);
    }

    @Override
    public List<Dict> getList(ParamFilter queryFilter) {
        return dictDao.find("getList", queryFilter.getParam());
    }

    /**
     * 跟新字典
     * @param dict
     */
    @Override
    public void update(Dict dict) {
        dictDao.update("updateById",dict);
    }

    /**
     * 获取树的节点
     * @return
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public List<JSTree> getTree() {
        return dictDao.findMap("getTree");
    }
    /**
     * 根据父id查询字典信息
     * @param parentId
     * @return
     */

    @Override
    public List<Dict> getListByParentId(String parentId) {
        List<Dict> dictList = new LinkedList<>();
        List<Dict> subList = dictDao.find("getListByParentId",parentId);
        Dict dict = dictDao.findUnique("getById",parentId);
        dictList.add(dict);
        if(subList!=null && subList.size()>0){
            dictList.addAll(subList);
        }
        return dictList;
    }

    /**
     * 获取是否为大类
     * @return
     */
    @Override
    public List<Dict> getCatagory() {
        return dictDao.find("getCatagory");
    }
    /**
     * 删除字典
     * @param id
     */
    @Override
    public void delete(String id) {
        dictDao.delete("delete", id);
    }
    //根据id获取字典信息
	@Override
	public Dict getById(String id) {
		return dictDao.findUnique("getById",id);
	}

    @Override
    public List findByDictId(Map map) {
        return dictDao.find("findByDictId",map);
    }

    /**
     * 根据name,父id得到value集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<Dict> findByDictName(Map map){
        return dictDao.find("findByDictName",map);
    }
    /**
     * 根据code 父id得到value集合
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<Dict> findByDictCode(Map map){
        return dictDao.find("findByDictCode",map);
    }

    /**
     * 根据字典大类获取明细
     * @param dictName
     * @return
     */
    @Override
    public List<Dict> getDetailList(String dictName){
        return dictDao.find("getDetailList",dictName);
    }

    /**
     * 根据字典大类code获取明细
     * @param code
     * @return
     */
    @Override
    public List<Dict> getDetailListByCode(String code){
        return dictDao.find("getDetailListByCode",code);
    }
    /**
     * 根据字典name获取明细
     * @param name
     * @return
     */
    @Override
    public List<Dict> getListByName(String name) {
        return dictDao.find("getListByName",name);
    }
    /**
     * 根据字典code获取明细
     * @param code
     * @return
     */
    @Override
    public List<Dict> getListByCode(String code) {
        return dictDao.find("getListByCode",code);
    }


    /**
     * 根据字典code获取明细
     * @param code
     * @return
     */
    @Override
    public String  getNameByCode(String code) {
        Dict dict =  dictDao.findUnique("getNameByCode",code);
        String name = dict.getName();
        if(name==null){
            return null;
        }
        return name;
    }

    @Override
    public String getDetilNameByCode(String code, String parentId) {
        Map map = new HashMap();
        map.put("code",code);
        map.put("parent_id",parentId);
        Dict dict =  dictDao.findUnique("getDetailNameByCode",map);
        String name = dict.getName();
        if(name==null){
            return null;
        }
        return name;
    }

    /**
     * 根据字典大类获取明细
     * @param dictName
     * @return
     */
    @Override
    public List<Map<String,Object>> selectAppDictList(String dictName){
        return dictDao.findMap("selectAppDictList",dictName);
    }
}
