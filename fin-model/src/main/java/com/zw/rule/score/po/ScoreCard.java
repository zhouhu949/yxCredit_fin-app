package com.zw.rule.score.po;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月07日<br>
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
public class ScoreCard {
    private String idCard;//身份证

    private String sex;//性别

    private String age;//年龄

    private String marry;//婚姻状况

    private String hjszqy;//户籍所在区域

    private String dqjzqk;//当前居住情况

    private String xdwgznx;//现单位工作年限

    private String dwxz;//单位性质

    private String sfjngjjjsb;//是否缴纳公积金及社保

    private String rank;//职级

    private String gzffxs;//工资发放形式

    private String gmfs;//购买方式

    private String yaj;//已按揭

    private String pyxl;//鹏元学历

    private String sjhmsmsfyz;//手机号码实名是否一致

    private String azthrj;//APP安装同行软件

    private String shzxwjqdksl;//上海资信未结清贷款数量

    private String tdwbdkptgs;//同盾外部贷款平台个数

    private String sqgjj;//授权公积金

    private String sqtxl;//授权通讯录

    private String sqthjl;//授权通话记录

    private String zmf;//芝麻分

    private String dw;//GPS定位

    private String sjhsysc;//手机号使用时长

    private String djzc;//点击注册/利息细则

    private String gzdwmcxg;//工作单位名称修改

    private String gzdwdhxg;//工作单位电话修改

    private String lxrdhxg;//联系人电话修改

    private String xbxldwcs;//性别+学历+单位+城市

    private String jjgz;//拒绝规则

    private String total;//总分

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry == null ? null : marry.trim();
    }

    public String getHjszqy() {
        return hjszqy;
    }

    public void setHjszqy(String hjszqy) {
        this.hjszqy = hjszqy == null ? null : hjszqy.trim();
    }

    public String getDqjzqk() {
        return dqjzqk;
    }

    public void setDqjzqk(String dqjzqk) {
        this.dqjzqk = dqjzqk == null ? null : dqjzqk.trim();
    }

    public String getXdwgznx() {
        return xdwgznx;
    }

    public void setXdwgznx(String xdwgznx) {
        this.xdwgznx = xdwgznx == null ? null : xdwgznx.trim();
    }

    public String getDwxz() {
        return dwxz;
    }

    public void setDwxz(String dwxz) {
        this.dwxz = dwxz == null ? null : dwxz.trim();
    }

    public String getSfjngjjjsb() {
        return sfjngjjjsb;
    }

    public void setSfjngjjjsb(String sfjngjjjsb) {
        this.sfjngjjjsb = sfjngjjjsb == null ? null : sfjngjjjsb.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public String getGzffxs() {
        return gzffxs;
    }

    public void setGzffxs(String gzffxs) {
        this.gzffxs = gzffxs == null ? null : gzffxs.trim();
    }

    public String getGmfs() {
        return gmfs;
    }

    public void setGmfs(String gmfs) {
        this.gmfs = gmfs == null ? null : gmfs.trim();
    }

    public String getYaj() {
        return yaj;
    }

    public void setYaj(String yaj) {
        this.yaj = yaj == null ? null : yaj.trim();
    }

    public String getPyxl() {
        return pyxl;
    }

    public void setPyxl(String pyxl) {
        this.pyxl = pyxl == null ? null : pyxl.trim();
    }

    public String getSjhmsmsfyz() {
        return sjhmsmsfyz;
    }

    public void setSjhmsmsfyz(String sjhmsmsfyz) {
        this.sjhmsmsfyz = sjhmsmsfyz == null ? null : sjhmsmsfyz.trim();
    }

    public String getAzthrj() {
        return azthrj;
    }

    public void setAzthrj(String azthrj) {
        this.azthrj = azthrj == null ? null : azthrj.trim();
    }

    public String getShzxwjqdksl() {
        return shzxwjqdksl;
    }

    public void setShzxwjqdksl(String shzxwjqdksl) {
        this.shzxwjqdksl = shzxwjqdksl == null ? null : shzxwjqdksl.trim();
    }

    public String getTdwbdkptgs() {
        return tdwbdkptgs;
    }

    public void setTdwbdkptgs(String tdwbdkptgs) {
        this.tdwbdkptgs = tdwbdkptgs == null ? null : tdwbdkptgs.trim();
    }

    public String getSqgjj() {
        return sqgjj;
    }

    public void setSqgjj(String sqgjj) {
        this.sqgjj = sqgjj == null ? null : sqgjj.trim();
    }

    public String getSqtxl() {
        return sqtxl;
    }

    public void setSqtxl(String sqtxl) {
        this.sqtxl = sqtxl == null ? null : sqtxl.trim();
    }

    public String getSqthjl() {
        return sqthjl;
    }

    public void setSqthjl(String sqthjl) {
        this.sqthjl = sqthjl == null ? null : sqthjl.trim();
    }

    public String getZmf() {
        return zmf;
    }

    public void setZmf(String zmf) {
        this.zmf = zmf == null ? null : zmf.trim();
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public String getSjhsysc() {
        return sjhsysc;
    }

    public void setSjhsysc(String sjhsysc) {
        this.sjhsysc = sjhsysc == null ? null : sjhsysc.trim();
    }

    public String getDjzc() {
        return djzc;
    }

    public void setDjzc(String djzc) {
        this.djzc = djzc == null ? null : djzc.trim();
    }

    public String getGzdwmcxg() {
        return gzdwmcxg;
    }

    public void setGzdwmcxg(String gzdwmcxg) {
        this.gzdwmcxg = gzdwmcxg == null ? null : gzdwmcxg.trim();
    }

    public String getGzdwdhxg() {
        return gzdwdhxg;
    }

    public void setGzdwdhxg(String gzdwdhxg) {
        this.gzdwdhxg = gzdwdhxg == null ? null : gzdwdhxg.trim();
    }

    public String getLxrdhxg() {
        return lxrdhxg;
    }

    public void setLxrdhxg(String lxrdhxg) {
        this.lxrdhxg = lxrdhxg == null ? null : lxrdhxg.trim();
    }

    public String getXbxldwcs() {
        return xbxldwcs;
    }

    public void setXbxldwcs(String xbxldwcs) {
        this.xbxldwcs = xbxldwcs == null ? null : xbxldwcs.trim();
    }

    public String getJjgz() {
        return jjgz;
    }

    public void setJjgz(String jjgz) {
        this.jjgz = jjgz == null ? null : jjgz.trim();
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}