package com.zw.rule.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class CreditReviewResponse implements Serializable {
    private static final long serialVersionUID = 1257606101862047897L;

    /**
     * "数尊塔返回码[D00000：操作成功,D00001:验签失败,请联系管理员,D00002:必填参数缺失，请联系管理员"
     D00003:规则引擎不存在，请联系管理员,D00004:系统错误，请联系管理员,D00005:网络错误，请重试,"
     + "D00006:规则引擎获取数据失败，请重试,D00007:异步处理中],"
     + "erp返回码[A000:必填参数不能为空,A012:找不到对应的引擎id,A009:调用外部接口返回结果为空,9999:系统异常]")
     */
    private String responseCode;

    private String responseMsg ;

    /**
     * 结果
     */
    private String result;

    /**
     * 返回节点所需字段
     */
    private Object data;

    /**
     * 引擎Id
     */
    private Integer engineId;

    /**
     * 进件编号
     */
    private String applicationId;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getEngineId() {
        return engineId;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        return "CreditResponse [responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", result=" + result
                + ", data=" + data + ", engineId=" + engineId + ", applicationId=" + applicationId + "]";
    }
}
