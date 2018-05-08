package com.zw.rule.engine.po;

import java.io.Serializable;
import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月12日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
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
public class EngineVersion implements Serializable, Comparable<EngineVersion> {
    private static final long serialVersionUID = -7665759126432601671L;
    private Long verId;
    private Long engineId;
    private Integer version;
    private Integer subVer;
    private Integer bootState;
    private Integer status;
    private Integer layout;
    private Long userId;
    private String createTime;
    private Long latestUser;
    private String latestTime;
    private List<EngineNode> engineNodeList;
    private String engineName;
    private String engineDesc;

    public EngineVersion() {
    }

    public Long getVerId() {
        return this.verId;
    }

    public void setVerId(Long verId) {
        this.verId = verId;
    }

    public Long getEngineId() {
        return this.engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
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

    public List<EngineNode> getEngineNodeList() {
        return this.engineNodeList;
    }

    public void setEngineNodeList(List<EngineNode> engineNodeList) {
        this.engineNodeList = engineNodeList;
    }

    public Integer getSubVer() {
        return this.subVer;
    }

    public void setSubVer(Integer subVer) {
        this.subVer = subVer;
    }

    public String getEngineName() {
        return this.engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getEngineDesc() {
        return this.engineDesc;
    }

    public void setEngineDesc(String engineDesc) {
        this.engineDesc = engineDesc;
    }

    public int compareTo(EngineVersion o) {
        return this.version != o.getVersion()?this.version.intValue() - o.getVersion().intValue():(this.subVer != o.getSubVer()?this.subVer.intValue() - o.getSubVer().intValue():this.version.intValue() - o.getVersion().intValue());
    }

    @Override
    public String toString() {
        return "EngineVersion{" +
                "verId=" + verId +
                ", engineId=" + engineId +
                ", version=" + version +
                ", engineName='" + engineName + '\'' +
                ", engineDesc='" + engineDesc + '\'' +
                '}';
    }
}

