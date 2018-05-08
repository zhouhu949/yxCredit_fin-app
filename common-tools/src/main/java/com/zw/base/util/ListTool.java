package com.zw.base.util;


import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 功能说明:用来处理List
 * 典型用法：
 * 特殊用法：
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：ListTool
 * Copyright zzl-apt
 */
public class ListTool {
	
	/**
	 * 功能说明：判断空
	 * panye  ListTool
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static boolean isNullOrEmpty(List list){
		if(list == null || list.size()==0 ){
			return true;
		}
		return false;
	}
	/**
	 * 功能说明：判断空
	 * panye  ListTool
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static List getList(List list,int page,int size){
		int start = (page-1)*size;
		int end = start+size;
		int total = list.size();
		if(start>total){
			return new ArrayList();
		}
		if(end>total){
			return list.subList(start, total);
		}
		return list.subList(start, end);
		
	
	}
	/**
	 * 功能说明：判断非空
	 * panye  ListTool
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static boolean isNotNullOrEmpty(List list){
		return !isNullOrEmpty(list);
	}
	/**
	 * 功能说明：获得总数值
	 * panye  ListTool
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static double getSumDouble(List<Map> list ,String key){
		//用来返回的总值
		double sum=0d;
		//若为空则直接返回
		if(isNullOrEmpty(list)){return sum;}
		for(Map map:list){
			double temp = 0d;
			try{
				temp = Double.parseDouble(map.get(key).toString());
			}catch (Exception e) {
				continue;
			}
			sum += temp;
		}
		sum = NumberTool.parseDouble(new DecimalFormat("0.00").format(sum));
		return sum;	
	}
	/**
	 * 功能说明：获得对应list中的对象
	 * panye  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static JSONObject getJsonByKey(List<JSONObject> list , String key, String value){
		for(JSONObject json:list){
			String realValue = json.getString(key);
			if(realValue.equals(value)){
				return json;
			}
		}
		return null;
		
	}
}
