package com.zw.rule.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

/**
 * 规则引擎公共输入参数
 * @author gubin
 *
 */
public class RECommonRequest implements Serializable {

    private static final long serialVersionUID = 4361204024378872284L;

    /**
     * Api动作,query
     */
    private String act;

    /**
     * Linux时间戳
     */
    private Long ts;

    /**
     * API调用方生成的随机串: 8-10位随机字符串(a-z,0-9)仅包含小写字母和数字
     */
    private String nonce;

    /**
     * 流水号,作为业务系统、数据中心数据调用唯一标识。
     */
    private String pid;

    /**
     * 用户编号
     */
    private String uid;

    /**
     * 签名: sign签名算法:根据act,ts,nonce,pid,uid,token,按顺序用”,”连接在一起，做md5签名。即sign =
     * md5(act,ts,nonce ,pid,uid,token)
     */
    private String sign;

    /**
     * 授权token:系统唯一生成，一个uid对应一个token
     */
    private String token;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RECommonRequest [act=" + act + ", ts=" + ts + ", nonce=" + nonce + ", pid=" + pid + ", uid=" + uid
                + ", sign=" + sign + ", token=" + token + "]";
    }
}
