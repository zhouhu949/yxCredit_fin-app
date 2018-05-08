package com.zw.rule.surety;

/**
 * Created by Administrator on 2017/12/12.
 * 担保人实体类
 */
public class Surety {
    private String id;//id
    private String name;//担保人姓名
    private String tel;//担保人电话
    private String idcard;//担保人身份证好
    private String relation;//担保人关系
    private String remark;//担保人备注

    /*非本表字段关联查出来的*/
    private String createTime;//担保人单表订单关联表担保记录创建时间
    private String relationName;//担保人关系名称

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Surety() {
    }

    @Override
    public String toString() {
        return "Surety{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", idcard='" + idcard + '\'' +
                ", relation='" + relation + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
