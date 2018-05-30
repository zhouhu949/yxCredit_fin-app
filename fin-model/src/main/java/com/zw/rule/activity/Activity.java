package com.zw.rule.activity;

/**
 * Created by Administrator on 2017/9/16.
 */
public class Activity {


    private String id; //主键id
    private String host;
    private String activity_title; //活动标题
    private String activity_content;//活动描述
    private String activity_type;//活动类型
    private String activity_url;//活动图片连接
    private String activity_img_url;//活动图片地址
    private String activity_img_addr;//图片位置(1，弹框，2轮播图，3启动图)
    private String activity_state;//活动状态1：已上架，2：未上架
    private String activity_time;//活动期限
    private String create_time;//创建时间
    private String alter_time;//修改时间
    private String release_time;//活动发布时间
    private String platform_type;//平台类型 0现金贷，1商品贷
    private String priority;//优先级（1.位置1，2.位置2，3.位置3）

    public String getPlatform_type() {
        return platform_type;
    }

    public void setPlatform_type(String platform_type) {
        this.platform_type = platform_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) {
        this.activity_content = activity_content;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getActivity_url() {
        return activity_url;
    }

    public void setActivity_url(String activity_url) {
        this.activity_url = activity_url;
    }

    public String getActivity_state() {
        return activity_state;
    }

    public void setActivity_state(String activity_state) {
        this.activity_state = activity_state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAlter_time() {
        return alter_time;
    }

    public void setAlter_time(String alter_time) {
        this.alter_time = alter_time;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getActivity_time() {
        return activity_time;
    }

    public void setActivity_time(String activity_time) {
        this.activity_time = activity_time;
    }

    public String getActivity_img_url() {
        return activity_img_url;
    }

    public void setActivity_img_url(String activity_img_url) {
        this.activity_img_url = activity_img_url;
    }

    public String getActivity_img_addr() {
        return activity_img_addr;
    }

    public void setActivity_img_addr(String activity_img_addr) {
        this.activity_img_addr = activity_img_addr;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
