package com.zw.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Pattern;

/**
 * 
 * 功能说明:json工具类
 * 典型用法：
 * 特殊用法：
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-7-6
 * Copyright zzl-apt
 */
public class JsonTool {
	/**
	 * 
	 * 功能说明：获得json的总值	
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static double getSum(JSONObject json, String[] keys){
		double sum = 0d;
		if(keys == null || keys.length == 0){
			return sum;
		}
		for(String key : keys){
			try{
			sum += json.getDouble(key);
			}catch (Exception e) {
				continue;
			}
		}
		return sum;
	}
	/**
	 * 
	 * 功能说明：获得解析String
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static  JSONObject parseString(String str){
		JSONObject resultJson = new JSONObject();
		String[] strArray = str.split(Pattern.quote("|"));
		if(strArray == null || strArray.length==0){
			return resultJson;
		}
		for(String keyValue:strArray){
			if(keyValue.length() >0){
				String[] KV= keyValue.split(Pattern.quote(":"));
				if(KV.length >1){
					resultJson.put(KV[0], KV[1]);
				}else{
					resultJson.put(KV[0], "");
				}
			}
		}
		return resultJson;
	}
	/**
	 * 
	 * 功能说明：格式化json的数值
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static void format(JSONObject json){
		if(json !=null){
			for(Object key:json.keySet()){
				if(key.toString().contains("Money")||key.toString().contains("money")){
					String str = key.toString();
					try{
						double value = json.getDouble(str);
						json.put(str, NumberTool.format(value));
					}catch (Exception e) {
						continue;
					}
				}
			}
		}
	}
	/**
	 * 
	 * 功能说明：格式化json的数值
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：yuanhao
	 * 修改内容：
	 * 修改注意点：
	 */
	public static void formatNumber(JSONObject json){
		if(json !=null){
			for(Object key:json.keySet()){
				String str = key.toString();
				if(key.equals("responseCode") || key.equals("status") )continue;
				try{
					double value = json.getDouble(str);
					json.put(str, NumberTool.format(value));
				}catch (Exception e) {
					continue;
				}
			}
		}
	}
	/**
	 * 
	 * 功能说明：格式化json的数值 remove 中的不预处理
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：yuanhao
	 * 修改内容：
	 * 修改注意点：
	 */
	public static void format(JSONObject json,JSONObject remove){
		if(json !=null){
			for(Object key:json.keySet()){
				String str = key.toString();
				if(remove.containsKey(key.toString())){
					continue;
				}
				try{
					double value = json.getDouble(str);
					json.put(str, NumberTool.format(value));
				}catch (Exception e) {
					continue;
				}
			}
		}
	}
	/**
	 * 
	 * 功能说明：解析jsonArray的并获得其中一个的值
	 * wangmin  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static double  getSum(JSONArray jsonArray , String key) {
		double sum=0d;
		if(jsonArray == null ||jsonArray.size() ==0){
			return 0;
		}
		for(Object json:jsonArray){
			JSONObject j = (JSONObject) json;
			sum = ArithUtil.add(sum, j.getDouble(key));
		}
		return sum;
	}
	/**
	 * 
	 * 功能说明：checkIsArray 判断JSON类型是否为数组
	 * wangmin  2017-7-6
	 * @param object 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
	 * @return true:是数组，false:不是数组
	 * 最后修改时间：最后修改时间
	 * 修改人：
	 * 修改内容：
	 * 修改注意点：
	 * @throws Exception
	 */
	public static Boolean checkIsArray(Object object){
		
		Boolean isTrue = false;
		
		// 对象为空的情况下,返回false
		if(ChkUtil.isEmpty(object)){ 
			return false;
        } 
		// 截取对象第一个字符串信息
		String str = object.toString();
        final char[] strChar = str.substring(0, 1).toCharArray(); 
        final char firstChar = strChar[0]; 
         
        // 判断第一个字符串信息
        if(firstChar == '['){ 
        	isTrue = true;
        } 
		return isTrue;
	}
}
