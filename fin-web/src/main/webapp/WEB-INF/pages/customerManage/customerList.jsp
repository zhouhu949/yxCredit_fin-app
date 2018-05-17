<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/customerList.js${version}"></script>
    <title>客户管理</title>
    <style>
        /*显示图片*/
        #imageCard .imgShow,#cheatImg .imgShow,#phoneImg .imgShow{width:40px;height: 40px;float:left;margin-right:1em;}
        #imageCard .imgShowTd{padding-left: 1em;text-align:left}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        .align{width:110px;}
        #divFrom td{text-align:center}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
        .costomerRemark{text-align: left;border: 1px solid rgb(204, 204, 204);height: 56px;line-height: 42px;overflow: hidden;display: block;}
        .costomerRemark>div:first-child{display: inline-block;height: 56px;width: 102px;border-right: 1px solid #ccc;  text-align: center;  line-height: 50px;}
        .costomerRemark>div:last-child{display: inline-block;width: 90%;margin-left: 6px;}
        .telRecord{text-align: left;}
        textarea{resize: none;}
        #showNewImg1 ul li{display: inline-block;text-align: center}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="empId" value="${userId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="personName" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">手机号码</label>
                    <label>
                        <input name="tel" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">注册时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                    <span class="line-cut">--</span>
                </li>
                <li style="width:200px;">
                    <label>
                        <input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li>
                <li style="width:155px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="customer_list">
                <table style="cursor:pointer;" id="customer_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>客户编号</th>
                        <th>客户名称</th>
                        <th>身份证号码</th>
                        <th>手机号码</th>
                        <th>注册时间</th>
                        <%--<th>提额比例(%)</th>--%>
                        <th>认证状态</th>
                        <th>订单数量</th>
                        <th>操作</th>
                    </tr>

                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>

            <div style="display: none;" id="quotaLoan">
                <br />
                <input type="hidden"  id="hdloanId" value=""/>
                <table style="cursor:pointer;" id="quotaLoanTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th >订单编号</th>
                        <th>申请金额</th>
                        <th>申请期限</th>
                        <th>提额比例</th>
                        <th>放款时间</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%--查看--%>
        <div id="container" class="of-auto_H" style="padding:20px;display:none;">
            <div id="divFrom">
                <div class="paddingBox xdproadd" style="width:920px">
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">个人信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td width="14%">姓&emsp;&emsp;名：</td>
                                <td  id="personName" width="19%"></td>
                                <td width="14%">性&emsp;&emsp;别：</td>
                                <td id="sex" width="19%"></td>
                                <td width="14%">年&emsp;&emsp;龄：</td>
                                <td id="age" width="20%"></td>
                            </tr>
                            <tr>
                                <td >手机号码：</td>
                                <td id="cusTel" ></td>
                                <td >证件号码：</td>
                                <td id="cusCard" ></td>
                                <td >所属总包商：</td>
                                <td id="contractor" ></td>
                            </tr>

                            <tr>
                                <td >婚姻状况：</td>
                                <td id="marry" ></td>
                                <td >子女状况：</td>
                                <td id="childrenStatus" ></td>
                                <td >注册时间：</td>
                                <td id="registrationTime" ></td>

                            </tr>
                            <tr>
                                <td >户籍居住地址：</td>
                                <td id="cardRegisterAddress" colspan="5"></td>

                            </tr>
                            <tr>

                                <td >工作居住地址：</td>
                                <td id="residenceAddress" colspan="5"></td>
                            </tr>
                           <%-- <tr>
                                <td  width="10%">姓名</td>
                                <td  width="23%" id="realname"></td>
                                <td  width="10%">性别</td>
                                <td id="sex" width="23%"></td>
                                <td width="10%">手机号码</td>
                                <td id="cusTel" width="23%"></td>
                            </tr>
                            <tr>
                                <td >身份证号码</td>
                                <td id="cusCard"></td>
                                <td >户籍</td>
                                <td id="applyCensus"></td>
                                <td >出生日期</td>
                                <td  id="tdBirth"></td>
                            </tr>
                            <tr>
                                <td width="10%">婚姻状况</td>
                                <td id="applyMerry" width="23%"></td>
                                <td >学历</td>
                                <td id="education"></td>
                                <td >推荐人</td>
                                <td id="tdReferee"></td>
                            </tr>
                            <tr>
                                <td >推荐码</td>
                                <td  id="tdRefereeId"></td>
                                <td >公司名称</td>
                                <td id="unitName"></td>
                                <td >联系电话</td>
                                <td id="unitTel" ></td>
                            </tr>
                            <tr>
                                <td >公司地址</td>
                                <td id ="unitAddr" ></td>
                                <td >详细地址</td>
                                <td id ="tdDetailed" colspan="2"></td>
                                <td id ="callRecordUrl"></td>
                            </tr>
                            <tr>
                                <td >银行卡号</td>
                                <td id ="bankCard" ></td>
                                <td >开户银行</td>
                                <td id ="accountBank" ></td>
                                <td colspan="2"></td>
                            </tr>--%>
                            </tbody>
                        </table>
                    </div>

                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">认证信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td width="14%">认证状态：</td>
                                <td  id="isIdentity" width="19%"></td>
                                <td width="14%">认证时间：</td>
                                <td id="authenticationTime" width="19%"></td>
                                <td width="14%">银行卡号：</td>
                                <td id="cardNumber" width="20%"></td>
                            </tr>
                            <tr>
                                <td>开户银行：</td>
                                <td id="bankName" ></td>
                                <td >所属省份：</td>
                                <td id="provName" ></td>
                                <td >所属城市：</td>
                                <td id="cityName" ></td>

                            </tr>

                            <tr>
                                <td >开户支行：</td>
                                <td id="bankSubbranch" colspan="5"></td>
                            </tr>


                            </tbody>
                        </table>
                    </div>

                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">联系人信息</span>
                        </div>
                        <table class="tb_info" id="relation" style="font-size:12px;">
                        </table>
                        <br>
                        <table class="tb_info" id="relation1" style="font-size:12px;">
                        </table>
                    </div>
                    <%--&lt;%&ndash;设备信息&ndash;%&gt;--%>
                    <%--<div class="paperBlockfree" style="margin-top:20px">--%>
                        <%--<div class="block_hd" style="float:left;" onclick="shrink(this)">--%>
                            <%--<s class="ico icon-file-text-alt"></s><span class="bl_tit">设备信息</span>--%>
                        <%--</div>--%>
                        <%--<table class="tb_info " style="font-size:12px;">--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle" width="10%">申请所在省份：</td>--%>
                                <%--<td id="apply_province" width="23%"></td>--%>
                                <%--<td class="tdTitle" width="10%">申请市：</td>--%>
                                <%--<td id="apply_city" width="23%"></td>--%>
                                <%--<td class="tdTitle" width="10%">申请区：</td>--%>
                                <%--<td id="apply_area" width="23%"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle">申请地理位置：</td>--%>
                                <%--<td id="apply_address"></td>--%>
                                <%--<td class="tdTitle">IMEI号码：</td>--%>
                                <%--<td  id="imei_number"></td>--%>
                                <%--<td class="tdTitle">操作系统：</td>--%>
                                <%--<td id="operate_system"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle">设备类型：</td>--%>
                                <%--<td id="device_type"></td>--%>
                                <%--<td class="tdTitle">手机内存：</td>--%>
                                <%--<td id="tel_memory"></td>--%>
                                <%--<td class="tdTitle">手机型号：</td>--%>
                                <%--<td id="tel_model"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle">手机品牌：</td>--%>
                                <%--<td id="tel_brand"></td>--%>
                                <%--<td class="tdTitle">网络类型：</td>--%>
                                <%--<td id="network_type"></td>--%>
                                <%--<td class="tdTitle">wifi名称：</td>--%>
                                <%--<td id="wifi_name"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle">wifi ssid：</td>--%>
                                <%--<td id="wifi_ssid"></td>--%>
                                <%--<td class="tdTitle">IP地址：</td>--%>
                                <%--<td id="ip_address"></td>--%>
                                <%--<td class="tdTitle">IP地址所在省份：</td>--%>
                                <%--<td id="ip_province"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle">IP地址所在城市：</td>--%>
                                <%--<td id="ip_city"></td>--%>
                                <%--<td class="tdTitle">IP地址所在区：</td>--%>
                                <%--<td id="ip_area"></td>--%>
                                <%--<td class="tdTitle">是否ROOT：</td>--%>
                                <%--<td id="is_root"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="tdTitle">是否越狱：</td>--%>
                                <%--<td id="is_prison"></td>--%>
                                <%--<td class="tdTitle">是否模拟器登录：</td>--%>
                                <%--<td id="is_moni_online"></td>--%>
                                <%--<td class="tdTitle">位置权限：</td>--%>
                                <%--<td id="location_permission"></td>--%>
                            <%--</tr>--%>
                            <%--</tbody>--%>
                        <%--</table>--%>
                    <%--</div>--%>
                    <%--手持身份证照片--%>
                 <%--   <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">影像资料</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;">
                            <tbody>
                            <tr>
                                &lt;%&ndash;手持身份证照片&ndash;%&gt;
                                <td style="background: #DEF0D8;width: 10%">身份证正反面</td>
                                <td class="tdTitle align" id="showNewImg1" style="text-align: left" colspan="3"><ul style="text-align: left;">
                                    <li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="" class="imgShow" id="zcardSrcBase64" onclick="imgShow(this)"></div><p>身份证正面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>
                                    <li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="" class="imgShow" id="fcardSrcBase64" onclick="imgShow(this)"></div><p>身份证反面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>
                                </ul></td>
                            </tr>

                            <tr>
                                &lt;%&ndash;手持身份证照片&ndash;%&gt;
                                <td style="background: #DEF0D8;width: 10%">人脸识别照片</td>
                                <td class="tdTitle align" id="showNewImg1" style="text-align: left" colspan="3"><ul style="text-align: left;">
                                    <li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="" class="imgShow" id="face_src" onclick="imgShow(this)"></div><p>人脸识别照片</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>
                                </ul></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>--%>
                </div>
            </div>
                </div>
    <%--    <div id="updateWpd" style="display: none">
            <div class="commonManager ">
                <div class="addCommon">
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">新密码:</label>
                            <label>
                                <input name="tel" id ="lblpwd1" type="password" value="" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">确认密码:</label>
                            <label>
                                <input name="tel" id ="lblpwd2" type="password" value="" />
                            </label>
                        </li>
                    </ul>
                </div>
            </div>
        </div>--%>
            </div>
        </div>

    </div>
</div>
<%--<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>--%>

<%--更改手机号弹出窗--%>
<%--<div id="changeTelDiv" style="display: none">
    <div class="commonManager ">
        <div class="addCommon">
            <ul class="clearfix">
                <li>
                    <label class="label_name">旧手机号:</label>
                    <label>
                        <input name="tel" id ="oldTel" type="text" value="" />
                    </label>
                </li>
                <li>
                    <label class="label_name">新手机号:</label>
                    <label>
                        <input name="tel" id ="newTel" type="text" value="" />
                    </label>
                </li>
            </ul>
        </div>
    </div>
</div>--%>


</body>
</html>