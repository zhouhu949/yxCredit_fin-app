package com.zw.rule.process.po;

import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.EngineVersion;

import java.util.List;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class ProcessVersion {
    private static final long serialVersionUID = -7665759126432601671L;
    private Long verId;
    private Long processId;
    private Integer version;
    private Integer subVer;
    private Integer bootState;
    private Integer status;
    private Integer layout;
    private Long userId;
    private String createTime;
    private Long latestUser;
    private String latestTime;
    private List<ProcessNode> processNodeList;
    private String processName;
    private String processDesc;

    public ProcessVersion() {
    }

    public Long getVerId() {
        return this.verId;
    }

    public void setVerId(Long verId) {
        this.verId = verId;
    }

    public Long getEngineId() {
        return this.processId;
    }

    public void setEngineId(Long engineId) {
        this.processId = engineId;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getBootState() {
        return this.bootState;
    }

    public void setBootState(Integer bootState) {
        this.bootState = bootState;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLayout() {
        return this.layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null?null:createTime.trim();
    }

    public Long getLatestUser() {
        return this.latestUser;
    }

    public void setLatestUser(Long latestUser) {
        this.latestUser = latestUser;
    }

    public String getLatestTime() {
        return this.latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime == null?null:latestTime.trim();
    }


    public Integer getSubVer() {
        return this.subVer;
    }

    public void setSubVer(Integer subVer) {
        this.subVer = subVer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ProcessVersion{" +
                "verId=" + verId +
                ", processId=" + processId +
                ", version=" + version +
                ", processName='" + processName + '\'' +
                ", processDesc='" + processDesc + '\'' +
                '}';
    }
}

