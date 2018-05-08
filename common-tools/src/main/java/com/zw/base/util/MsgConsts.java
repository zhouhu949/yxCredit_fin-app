package com.zw.base.util;

/**
 * 功能说明：提示消息常量类
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-06-13
 * 需要注意：
 * 当前类中所有变量为静态，常用常量定义，后期可能会修改为配置文件形式
 * 所有常量命名为大写，多个单词间用'_'线分割，不要使用驼峰命名
 * Copyright zzl-apt
 */
public class MsgConsts {
	
	/** 接口返回状态标识 :responseCode */
	public static final String RESPONSE_CODE = "responseCode";
	
	public static final String RESPONSE_DATA = "responseData";
	
	public static final String STATUS_CODE = "status";
	
	/** 接口返回状态标识 :responseCode */
	public static final String NO_BELONG = "该意向客户已存在，且不属于您！";
	
	public static final String BELONG = "该意向客户已存在，且属于您！";
	
	/** 接口返回信息标识 :info*/
	public static final String RESPONSE_INFO = "info";
	
	/** 用户类型*/
	public static final String RESPONSE_CUSTTYPE = "custType";
	
	/** 成功 :1 */
	public static final String STATUS_YES = "y";

	public static final String SUCCESS_CODE = "1";
	
	public static final String SUCCESS_RESULT = "验证成功！";
	
	public static final String PENGYU_ERR_RESULT = "鹏元实名验证失败！";
	
	public static final String APPLYORDER_EMP_RESULT = "根据申请表ID订单查询结果为空！";
	
	public static final String BLACKLIST_RESULT = "该客户存在黑名单中！";
	
	public static final String MATE_BLACKLIST_RESULT = "该客户配偶存在黑名单中！";
	
	public static final String APPLYORDER_RESULT = "该客户在该产品系列下存在未结清或者被拒订单！";
	
	public static final String MATE_APPLYORDER_RESULT = "该客户配偶存在未结清或者被拒订单！";
	/** 成功 :1 */
	public static final String YES_RESULT = "该客户可以添加！";
	
	public static final String CENTER_CODE = "2";
	
	/** 失败 ：0 */
	public static final String ERROR_CODE = "0";
	
	/**微信成功状态码 0*/
	public static final String WECHAT_SUCCESS_CODE = "0";
	
	/**微信失败状态码 1*/
	public static final String WECHAT_ERROR_CODE = "1";
	
	/**微信失败状态码 2*/
	public static final String WECHAT_NO_CUST_CODE = "2";
	
	/**微信失败状态码 3*/
	public static final String WECHAT_UNEXIST_CODE = "3";
	
	/**微信客户信息表主键key tWechatCustId*/
	public static final String WECHAT_T_WECHAT_CUST_ID = "tWechatCustId";
	

	public static final String STATUS_NO = "n";
	
	/** 3 */
	public static final String EXIST_CODE = "3";
	
	/** 成功 :操作成功 */
	public static final String SUCCESS_INFO = "操作成功";
	
	/** 失败 ：操作失败 */
	public static final String ERROR_INFO = "操作失败";
	
	/** 未查询到结果对象 */
	public static final String NO_SEARCH_RESULT = "未查询到结果对象"; 
	
	/** 员工档案为空 */
	public static final String NO_HRAR_RESULT = "员工档案为空！"; 
	
	/** 员工表信息为空 */
	public static final String NO_EMP_RESULT = "员工表信息为空！"; 
	
	/** 提示用户参数不正确  ：参数不正确*/
	public static final String PARAM_ERROR = "参数不正确，请检查后再提交！";
	
	/** 提示用户参数不正确  ：参数不合法*/
	public static final String PARAM_WRONGFUL = "参数不合法，请检查后再提交！";
	
	/** 提示用户参数不正确  ：身份证号码格式不正确*/
	public static final String PARAM_IC_ERROR = "身份证号码格式不正确，请检查后再提交！";
	
	/** 提示用户参数不正确  ：手机号码格式不正确*/
	public static final String PARAM_MOBILE_ERROR = "手机号码格式不正确，请检查后再提交！";
	
	/** 改用户已被他人领取*/
	public final static String OTHER_OPT = "该用户已被他人领取";
	
	/** 系统异常 */
	public static final String SYS_EXCEPTION = "系统异常,请稍后重试或联系在线客服";
	
	/** 启用： 1*/
	public static final Integer ENABLED = 1;
	
	/** 停用 ：0*/
	public static final Integer DISABLE = 0;
	
	/** 待命: 2 */
	public static final Integer ON_STANDBY = 2;
	
	/** 用户名或密码不正确: 2 */
	public static final String LOGIN_ERROR = "用户名或密码不正确";
	
	/** 理财申请单状态 待命: 1 */
	public static final Integer EF_APPLAY_ON_STANDBY = 1;
	
	/** 理财申请单状态 筹建期: 2 */
	public static final Integer EF_APPLAY_ON_PREPARATION = 2;
	
	/** 理财申请单状态 收益期: 3 */
	public static final Integer EF_APPLAY_ON_INCOME = 3;
	
	/** 信贷申请单状态 收益期: 1:待命 */
	public final static int CRM_APPLAY_STATUS_ONE = 1;    
	
	/** 信贷申请单状态 收益期: 2:审批中 */
	public final static int CRM_APPLAY_STATUS_TWO = 2; 	 
	
	/** 信贷申请单状态 收益期: 3:还款中 */
	public final static int CRM_APPLAY_STATUS_THREE = 3;	
	
	/** 信贷申请单状态 收益期: 4:已结清 */
	public final static int CRM_APPLAY_STATUS_FOUR = 4;	 
	
	/** 信贷申请单状态 收益期: 5:拒绝 */
	public final static int CRM_APPLAY_STATUS_FIVE = 5;	
	
	/** 评审重复操作提示*/
	public final static String CL_REPEAT_OPT = "该订单已被操作";	
	
	/** 评审报告验证提示*/
	public final static String CL_REPORT_VER = "未发现报告,请先填写报告";
	
	/** 评审电话呼叫验证提示*/
	public final static String CL_PHONE_CALLS_VER = "未发现电话呼叫,请先进行电话呼叫";
	
	/** 评审合同上传验证提示*/
	public final static String CL_CONTRACT_UPLOAD_VER = "未发现合同上传文件,请先上传合同文件";
	
	/** 评审音频上传验证提示*/
	public final static String CL_VIDEO_VER = "未发现音频上传文件,请先上传音频文件";
	
	/** 开户验证提示*/
	public final static String CL_OPEN_ACCOUNT_VER = "未开户,请先进行开户";
	
	/** 解冻异常提示*/
	public final static String CL_THAW_EXCEPTION = "解冻异常,请联系系统管理员！";
	
	/** 债权转让提示*/
	public final static String CLAIMS_TRANSFER  = "债权转让异常,请联系系统管理员！";
}
