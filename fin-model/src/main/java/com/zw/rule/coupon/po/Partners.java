package com.zw.rule.coupon.po;

/**
 * Created by Administrator on 2017/11/14.
 */
public class Partners {
    private String id;
    private String name;
    private String path;
    private String link;
    private String state;
    private String activity_img_url;
    private String platformType;

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getActivity_img_url() {
        return activity_img_url;
    }

    public void setActivity_img_url(String activity_img_url) {
        this.activity_img_url = activity_img_url;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
