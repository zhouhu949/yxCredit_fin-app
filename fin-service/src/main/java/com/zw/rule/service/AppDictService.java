package com.zw.rule.service;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Dict;
import com.zw.rule.po.MagDict;
import com.zw.rule.po.MagDictDetail;
import com.zw.rule.pojo.JSTree;

import java.util.List;
import java.util.Map;

public interface AppDictService {

	void add(Map map);

	void addDetail(Map map);

	List<MagDict> getList(Map map);

	List<MagDictDetail> getListDictDetail(Map map);

	void update(Map map);

	void updateDetail(Map map);

//	List<JSTree> getTree();
//
//	List<Dict> getListByParentId(String parentId);
//
	List<MagDict> getCatagory();
//
	void delete(String id);

	void deleteDetail(String id);

	MagDict getById(String id);

	MagDictDetail getByDetailId(String id);
	List<String> findByDictId(Map map);//根据id,父id查询code

	List<String> findByDictDetailId(Map map);//根据id,父id查询code
	List<MagDict> findByDictName(Map map);//根据字典名称查询
	List<MagDict> findByDictCode(Map map);//根据字典Code查询

	List<MagDictDetail> findDetailByDictName(Map map);//根据父id和名称查询是否已存在

	List<MagDictDetail> findDetailByDictCode(Map map);//根据父id和code查询是否已存在

	/**
	 * 根据key和code获取对应的name
	 * @param
	 * @param
	 * @return
	 * @throws Exception
	 */
	String getDictInfo(String key, String code) throws  Exception;

	/**
	 * 根据key和name得到code
	 * @param
	 * @return
	 * @throws Exception
	 */
	String getDictCode(String key,String name) throws  Exception;

//	List<Dict> getDetailList(String dictName);//根据字典大类查询明细
//	List<Dict> getListByName(String name);//根据name查询
//	List<Dict> getListByCode(String code);//根据code查询
//
//	/**
//	 * 小窝-根据code查询
//	 * @param code
//	 * @return
//	 */
//	String  getNameByCode(String code);
//
//
//	/**
//	 * 小窝-根据code查询详情名称
//	 * @param code
//	 * @return
//	 */
//	String  getDetilNameByCode(String code, String parentId);
}
