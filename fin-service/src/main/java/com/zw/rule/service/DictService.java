package com.zw.rule.service;

import com.zw.rule.po.Dict;
import com.zw.rule.pojo.JSTree;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

public interface DictService{

	void add(Map map);

	List<Dict> getList(ParamFilter queryFilter);

	void update(Dict dict);

	List<JSTree> getTree();

	List<Dict> getListByParentId(String parentId);

	List<Dict> getCatagory();

	void delete(String id);

	Dict getById(String id);
	List findByDictId(Map map);//根据id,父id查询code
	List<Dict> findByDictName(Map map);//根据父id和字典名称查询
	List<Dict> findByDictCode(Map map);//根据父id和字典Code查询


	List<Dict> getDetailList(String dictName);//根据字典大类查询明细
	List<Dict> getDetailListByCode(String code);//根据字典大类code查询明细
	List<Dict> getListByName(String name);//根据name查询
	List<Dict> getListByCode(String code);//根据code查询

	/**
	 * 小窝-根据code查询
	 * @param code
	 * @return
	 */
	String  getNameByCode(String code);


	/**
	 * 小窝-根据code查询详情名称
	 * @param code
	 * @return
	 */
	String  getDetilNameByCode(String code,String parentId);

	List<Map<String,Object>>  selectAppDictList(String dictName);
}
