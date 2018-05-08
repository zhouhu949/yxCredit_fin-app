package com.zw.rule.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class CreditResponse implements Serializable {

    private static final long serialVersionUID = 1257606101862047897L;

//    @ApiModelProperty(value="返回码[D00000：操作成功,D00001:验签失败,请联系管理员,D00002:必填参数缺失，请联系管理员"
//            + ",D00003:规则引擎不存在，请联系管理员,D00004:系统错误，请联系管理员,D00005:网络错误，请重试,"
//            + "D00006:规则引擎获取数据失败，请重试,D00007:异步处理中]",required=true)
//    private String responseCode = ApiConstants.DC_STATUS_SUCCESS;
//
////    @ApiModelProperty(value="返回信息",required=true)
//    private String responseMsg = ApiConstants.statusMap.get(ApiConstants.DC_STATUS_SUCCESS);

    /**
     * 结果
     */
//    @ApiModelProperty(value="返回结果[通过、拒绝、人工审核]",required=true)
    private String result;

    /**
     * 分数
     */
//    @ApiModelProperty(value="分数",required=true)
    private String score;

    /**
     * 返回节点所需字段
     */
//    @ApiModelProperty(value="返回数据[参考数尊塔文档]",required=true)
    private Object data;

    /**
     * 引擎Id
     */
//    @ApiModelProperty(value = "引擎Id", required = true, dataType = "Long")
    private Long engineId;

    /**
     * 进件编号
     */
//    @ApiModelProperty(value = "进件编号", required = true, notes = "进件编号")
    private String applicationId;

    /**
     * 流水号,作为业务系统、数据中心数据调用唯一标识。
     */
//    @ApiModelProperty(value="流水号：唯一标识，每次调用不一样", required=true, dataType="String")
    private String pid;

    /**
     *
     */
//    @ApiModelProperty(value = "调用时间", hidden = true)
    private Date callTime = new Date();

    private long costTime;

//    public String getResponseCode() {
//        return responseCode;
//    }
//
//    public void setResponseCode(String responseCode) {
//        this.responseCode = responseCode;
//    }
//
//    public String getResponseMsg() {
//        return responseMsg;
//    }
//
//    public void setResponseMsg(String responseMsg) {
//        this.responseMsg = responseMsg;
//    }

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

    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    @Override
    public String toString() {
        return "CreditResponse [result=" + result
                + ", score=" + score + ", data=" + data + ", engineId=" + engineId + ", applicationId=" + applicationId
                + ", pid=" + pid + ", callTime=" + callTime + ", costTime=" + costTime + "]";
    }
}
