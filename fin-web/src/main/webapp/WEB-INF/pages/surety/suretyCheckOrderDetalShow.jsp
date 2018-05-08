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
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/surety/suretyCheckOrderDetalShow.js${version}"></script>
    <title>客户审核</title>
    <%--单个担保的订单审核结果详情页面--%>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        .mb10 {
            margin-bottom: 10px !important;
        }
        .inpText {
            border-radius: 2px;
            border: 1px solid #ccc;
            text-indent: 2px;
            line-height: 24px;
            padding-top: 0;
            padding-bottom: 0;
        }
        .btn_pad_s2 {
            padding: 0px 28px;
            line-height: 26px;
            border-radius: 2px;
            text-align: center;
            cursor: pointer;
            display: inline-block;
            border: 0;
            font-size: 14px;
        }
        .btn_color1 {
            background: #1BADF6 ;
            color: #fff;
            border-radius: 2px;
        }
        /*.s_floatToolA {*/
        /*position: fixed;*/
        /*left: 1250px;*/
        /*top: 135px;*/
        /*}*/
        #divFrom input{border:none;background-color: #fff!important;text-align: left;}
        .unit{position: relative; top:1px;}
        /*合理性样式*/
        #showReasonable input[name=ReasonableInput],#showReasonable input[name=ReasonableInput_fitment],#showReasonable input[name=ReasonableInput_fitment_money]{border:1px solid #ddd}
        .ReasonableUl li{
            display: inline-block;
            width: 10%;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
        }
        .ReasonableUl li img{
            width: 100%;
            cursor: pointer;
        }
        .answerRemark,#elecRemark,#customerRemark,#giveAmount{width: 100%;position: relative;left:-10px;}
        #customerRemark,#elecRemark,#advice{width: 96%;margin-left: 20px;}
        .imagediv{
            width:150px;
            height:150px;
            background-color:white;
            text-align: center;
            float: left;
            position: relative;
            margin-right: 1em;
            margin-bottom:1em;
        }
        .addMaterial{
            width:150px;
            height:150px;
            vertical-align: middle;
        }
        .picShow{
            position:absolute;
            z-index:100;
            opacity:0;
            filter:alpha(opacity=0);
            height:150px;
            width:150px;
            readonly:true;
        }
        .closeImg{
            position: absolute;
            top: -10px;
            right: -10px;
            width: 20px;
            height: 20px;
            z-index: 999;
            font-size: 20px;
            /* border: 1px solid; */
            border-radius: 50%;
            background: #000;
            color: #fff;
            line-height: 20px;
            cursor: pointer;
        }
        #firstCredit,#passRemark,#cheatRemark,#photoRemark{width:95%;float:left;margin-left:0px;margin-left:4px;}
        #cheatRemark,#photoRemark{width: 99%;}
        #divFrom td.telRecord{height: 37px;  text-align: left;  padding-left: 27px!important;}
        /* #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#recordList5 td,#recordList6 td,#answerBody td,#answerBody1 td{border:none!important;border-right:1px solid #ccc}
         #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#recordList5 tr,#recordList6 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}*/
        .answerTh th{text-align: center; font-weight: normal;border:none!important;}
        .answerTh thead{border:1px solid #ccc;border-top:none;background: #DFF1D9}
        .cencus{height: 40px;width:100%;line-height:40px;float:left;border-right:1px solid #ccc;border-left:1px solid #ccc;background-color: white; text-align: left;
            padding-left: 29px;
        }
        .MerInput{
            margin:.5em 0;
        }
        #MerMsg tr td{
            text-align: right;
            padding-left:0.2em;
        }
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
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
        #showNewImg{
            text-align: left;
        }
        #showNewImg ul li,#paikeContainer ul li,#fanqzContainer ul li,#cszl ul li,#zszl ul li{
            display: inline-block;
            width:17%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
        }
        #showNewImg1{
            text-align: left;
        }
        #showNewImg1 ul li,#paikeContainer ul li,#fanqzContainer ul li,#cszl ul li,#zszl ul li{
            display: inline-block;
            width:17%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
        }

        #showNewImg2{
            text-align: left;
        }
        #showNewImg2 ul li,#paikeContainer ul li,#fanqzContainer ul li,#cszl ul li,#zszl ul li{
            display: inline-block;
            width:17%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
        }
        #showNewImg>ul>li>div,#paikeContainer>ul>li>div,#fanqzContainer>ul>li>div,#cszl>ul>li>div,#zszl>ul>li>div{
            overflow: hidden;
        }
        #showNewImg1>ul>li>div,#paikeContainer>ul>li>div,#fanqzContainer>ul>li>div,#cszl>ul>li>div,#zszl>ul>li>div{
            overflow: hidden;
        }
        #showNewImg2>ul>li>div,#paikeContainer>ul>li>div,#fanqzContainer>ul>li>div,#cszl>ul>li>div,#zszl>ul>li>div{
            overflow: hidden;
        }
        .addButton{margin-right:20px;}
        .audioShow {
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 30px;
            width: 48px;
            readonly: true;
            right: 26px;
            top: 13px;
        }
        #btnMaterail,#btn_load,#btn_plus{width:130px;}
        .ly_positionBar {
            border-bottom: 1px solid #dcdcdc;
            height: 45px;
            line-height: 45px;
            margin-bottom: 2em;
        }
        .ly_positionBar .position {
            padding-left: 30px;
            font-size: 16px;
            font-style: italic;
        }
        .mb10 {
            margin-bottom: 10px !important;
        }
        .btn_pad_s2 {
            padding: 0px 28px;
            line-height: 26px;
            border-radius: 2px;
            text-align: center;
            cursor: pointer;
            display: inline-block;
            border: 0;
            font-size: 14px;
        }
        /*.s_floatToolA {*/
        /*position: fixed;*/
        /*left: 1150px;*/
        /*top: 210px;*/
        /*}*/
        #A008likui1 {
            padding: 20px;
            padding-bottom: 40px;
        }
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="orderId" id="orderId" value="${order.id}"/>
    <input type="hidden" name="customerId" id="customerId" value="${order.customerId}"/>
    <input type="hidden" name="userId" id="userId" value="${order.userId}"/>
    <input type="hidden" name="state" id="state" value="${order.state}"/>
    <input type="hidden" name="orderId" id="taskNodeId" value=""/>
    <input type="hidden" name="relId" id="relId" value="${relId}"/>
    <div class="commonManager" id="commonManager">
        <%-- 通过输入授信金额--%>
        <%--查看--%>
        <div id="container" class="of-auto_H" style="padding:20px;">
            <!-- 隐藏列 -->
            <!-- 隐藏formId表单编号 -->
            <%--<div class="ly_positionBar">--%>
            <%--<span class="position">客户资料 </span>--%>
            <%--</div>--%>
            <%--<div>--%>
            <%--<button id="btnMaterail" type="button" class="btn btn-primary queryBtn">进件资料</button>--%>
            <%--<button id="btn_load" type="button" class="btn btn-primary queryBtn">上传材料</button>--%>
            <%--</div>--%>
            <div id="divFrom">
                <div class="paddingBox xdproadd" style="width:1200px">
                    <%--贷款明细--%>
                    <div class="paperBlockfree" id="pictureLoad">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">订单信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align" width="10%">订单编号:</td>
                                <td id="proLoan" width="23%">${order.orderNo}</td>
                                <td class="align" width="10%">产品类型:</td>
                                <td id="proType"width="23%">${order.productTypeName}</td>
                                <td class="align" width="10%">订单来源:</td>
                                <td  width="23%" id="tdPlatform">${order.source=="1"?"微信":"APP"}</td>
                            </tr>
                            <tr>
                                <td class="align">商户名称:</td>
                                <td id="merchantName">${order.merchantName}</td>
                                <td class="align">商品名称:</td>
                                <td id="merchandiseName">${order.merchandiseBrand}${order.merchandiseModel}</td>
                                <td class="align">商品金额:</td>
                                <td id="proSum">${order.amount}</td>

                            </tr>
                            <tr>
                                <td class="align">首付金额:</td>
                                <td id="predict_price">${order.predictPrice}</td>
                                <td class="align">申请分期金额:</td>
                                <td id="applayMoney">${order.applayMoney}</td>
                                <td class="align">申请期限:</td>
                                <td id="proDeadline">${order.periods}</td>


                            </tr>
                            <tr>
                                <td class="align">利息:</td>
                                <td id="tdFee">${order.fee}</td>
                                <td class="align">申请时间:</td>
                                <td id="proApplytime">${order.creatTime}</td>
                                <td class="align">到期时间:</td>
                                <td id="tdRepayDate">${order.repayDate}</td>

                            </tr>
                            <tr>
                                <td class="align">推荐人编号:</td>
                                <td id="tdRefereeId"></td>
                                <td class="align">推荐人:</td>
                                <td id="tdReferee"></td>
                            </tr>
                            </tbody>
                        </table>
                        <br />
                        <!--服务包信息-->
                        <div id="servicePackageOrderListDiv">

                        </div>
                    </div>
                    <%--申请人信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">客户信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align" width="10%">姓名:</td>
                                <td id="applyName" width="23%">${order.customerName}</td>
                                <td class="align" width="10%">性别:</td>
                                <td width="23%" id="tdSex"></td>
                                <td class="align" width="10%">联系方式:</td>
                                <td id="proTel" width="23%">${order.tel}</td>
                            </tr>
                            <tr>
                                <td class="align">身份证号码:</td>
                                <td id="applyIdcard">${order.card}</td>
                                <td class="align">户籍:</td>
                                <td id="applyCensus"></td>
                                <td class="align">出生日期:</td>
                                <td id="tdBirth"></td>
                            </tr>
                            <tr>
                                <%--<td class="align">婚姻状况:</td>--%>
                                <%--<td id="applyMerry"></td>--%>
                                <%--<td class="align">学历:</td>--%>
                                <%--<td id="education"></td>--%>
                                <td>公司名称</td><td id="unitName"></td>
                                <%--<td class="align">当前住址:</td>--%>
                                <%--<td id="applyAddr"></td>--%>
                                <td>联系电话</td><td id="unitTel"></td>
                                <td>单位地址</td><td id="unitAddr"></td>
                            </tr>
                            <%--<tr>--%>
                            <%--<td class="align">银行卡号:</td>--%>
                            <%--<td id="bank_card"></td>--%>
                            <%--<td class="align">开户银行:</td>--%>
                            <%--<td id="account_bank"></td>--%>
                            <%--<td class="align">开户地址:</td>--%>
                            <%--<td id="account_city"></td>--%>
                            <%--</tr>--%>
                            <tr>

                                <td>详细地址</td><td id="tdDetailed" colspan="5"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--设备信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">设备信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="tdTitle" width="10%">申请所在省份：</td>
                                <td id="apply_province" width="23%"></td>
                                <td class="tdTitle" width="10%">申请市：</td>
                                <td id="apply_city" width="23%"></td>
                                <td class="tdTitle" width="10%">申请区：</td>
                                <td id="apply_area" width="23%"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">申请地理位置：</td>
                                <td id="apply_address"></td>
                                <td class="tdTitle">IMEI号码：</td>
                                <td  id="imei_number"></td>
                                <td class="tdTitle">操作系统：</td>
                                <td id="operate_system"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">设备类型：</td>
                                <td id="device_type"></td>
                                <td class="tdTitle">手机内存：</td>
                                <td id="tel_memory"></td>
                                <td class="tdTitle">手机型号：</td>
                                <td id="tel_model"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">手机品牌：</td>
                                <td id="tel_brand"></td>
                                <td class="tdTitle">网络类型：</td>
                                <td id="network_type"></td>
                                <td class="tdTitle">wifi名称：</td>
                                <td id="wifi_name"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">wifi ssid：</td>
                                <td id="wifi_ssid"></td>
                                <td class="tdTitle">IP地址：</td>
                                <td id="ip_address"></td>
                                <td class="tdTitle">IP地址所在省份：</td>
                                <td id="ip_province"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">IP地址所在城市：</td>
                                <td id="ip_city"></td>
                                <td class="tdTitle">IP地址所在区：</td>
                                <td id="ip_area"></td>
                                <td class="tdTitle">是否ROOT：</td>
                                <td id="is_root"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">是否越狱：</td>
                                <td id="is_prison"></td>
                                <td class="tdTitle">是否模拟器登录：</td>
                                <td id="is_moni_online"></td>
                                <td class="tdTitle">位置权限：</td>
                                <td id="location_permission"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">联系人信息</span>
                        </div>
                        <%--直系联系人--%>
                        <table class="tb_info " id="ZXContacts" style="font-size:12px;">

                        </table>
                        <%--同事联系人--%>
                        <table class="tb_info " id="QTContacts" style="font-size:12px;">
                        </table>
                    </div>
                    <%--手持身份证照片--%>
                    <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                        <div class="block_hd" style="float:left;" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">影像资料</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;">
                            <tbody>
                            <tr>
                                <%--手持身份证照片--%>
                                <td style="background: #DEF0D8;width: 10%">身份证正反面</td>
                                <td class="tdTitle align" id="showNewImg1" style="text-align: left" colspan="3"><ul style="text-align: left;">
                                    <li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="" class="imgShow" id="zcardSrcBase64" onclick="imgShow(this)"></div><p>身份证正面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>
                                    <li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="" class="imgShow" id="fcardSrcBase64" onclick="imgShow(this)"></div><p>身份证反面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>
                                </ul></td>
                            </tr>

                            <tr>
                                <%--手持身份证照片--%>
                                <td style="background: #DEF0D8;width: 10%">人脸识别照片</td>
                                <td class="tdTitle align" id="showNewImg1" style="text-align: left" colspan="3"><ul style="text-align: left;">
                                    <li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="" class="imgShow" id="face_src" onclick="imgShow(this)"></div><p>人脸识别照片</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>
                                </ul></td>
                            </tr>
                            <tr>
                                <%--手持身份证--%>
                                <td style="background: #DEF0D8;width: 10%">手持身份证</td>
                                <td class="tdTitle align" id="showNewImg1"  style="text-align: left" colspan="3">
                                    <ul style="text-align: left;" id="holdImg">
                                    </ul></td>
                            </tr>
                            <tr>
                                <%--客户合影--%>
                                <td style="background: #DEF0D8;width: 10%">客户合影</td>
                                <td class="tdTitle align" id="showNewImg1"  style="text-align: left" colspan="3">
                                    <ul style="text-align: left;" id="groupImg">
                                    </ul></td>
                            </tr>
                            <tr>
                                <%--手签图片--%>
                                <td style="background: #DEF0D8;width: 10%">手签图片</td>
                                <td class="tdTitle align"  id="showNewImg1" style="text-align: left" colspan="3">
                                    <ul style="text-align: left;" id="autographImg">
                                    </ul></td>
                            </tr>
                            <tr id="trContractImg" style="display: none">
                                <%--合同图片--%>
                                <td style="background: #DEF0D8;width: 10%">合同图片</td>
                                <td class="tdTitle align"  id="showNewImg1" style="text-align: left" colspan="3">
                                    <ul style="text-align: left;" id="contractImg">
                                    </ul></td>
                            </tr>
                            <tr id="trGoodsImg" style="display: none">
                                <%--提货图片--%>
                                <td style="background: #DEF0D8;width: 10%">提货图片</td>
                                <td class="tdTitle align"  id="showNewImg1" style="text-align: left" colspan="3">
                                    <ul style="text-align: left;" id="goodsImg">
                                    </ul></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!--信用问卷-->
                    <div id="answerList" class="paperBlockfree" style="display: none">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">信用问卷</span>
                        </div>
                        <table  style="font-size:12px;width: 100%;" id="answerTable">
                        </table>
                    </div>
                    <%--<div class="paperBlockfree" style="position: relative;margin-top:20px;">--%>
                    <%--<div class="block_hd" style="float:left;">--%>
                    <%--</s><span class="bl_tit">风控审核结果</span>       &nbsp;&nbsp;&nbsp;<span id="Result">审核结果：通过</span>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--风控信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">风控审核结果</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody id="Result">
                            <tr> <td>未找到风控数据！</td></tr>
                            </tbody>
                        </table>
                    </div>
                        <%--担保人信息--%>
                        <div class="paperBlockfree" style="margin-top:20px">
                            <div class="block_hd" style="float:left;" onclick="shrink(this)">
                                <s class="ico icon-file-text-alt"></s><span class="bl_tit">担保人信息</span>
                            </div>
                            <table class="tb_info " style="font-size:12px;">
                                <tbody id="suretyMan">
                                <tr>
                                    <td width="10%">姓名：</td>
                                    <td width="23%">${surety.name}</td>
                                    <td width="10%">电话:</td>
                                    <td width="23%">${surety.tel}</td>
                                    <td width="10%">身份证:</td>
                                    <td width="23%">${surety.idcard}</td>
                                </tr>
                                <tr>
                                    <td width="10%">担保人关系</td>
                                    <td width="23%">${surety.relationName}</td>
                                    <td width="10%">担保时间</td>
                                    <td colspan="3">${suretyTime}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <%--担保审核信息--%><!--首付款 审核人 审核时间 审核结果 审核意见 -->
                        <div class="paperBlockfree" style="margin-top:20px">
                            <div class="block_hd" style="float:left;" onclick="shrink(this)">
                                <s class="ico icon-file-text-alt"></s><span class="bl_tit">担保审核信息</span>
                            </div>
                            <table class="tb_info " style="font-size:12px;">
                                <tbody id="checkMessage">
                                <tr>
                                    <td style="width: 10%">审核结果：</td>
                                    <td style="width: 23%">
                                        <span id="checkResult">
                                             <c:choose>
                                                 <c:when test="${checkResult == '1'}">
                                                     通过
                                                 </c:when>
                                                 <c:otherwise>
                                                     拒绝
                                                 </c:otherwise>
                                             </c:choose>
                                        </span>
                                    </td>
                                    <td style="width: 10%">审核人：</td>
                                    <td style="width: 23%">
                                        <span id="checkMan">${checkManName}</span>
                                    </td>
                                    <td style="width: 10%">审核时间：</td>
                                    <td style="width: 23%">
                                        <span id="">${checkTime}</span>
                                    </td>
                                    <%--<td style="width: 15%">首付款：</td>--%>
                                    <%--<td style="width: 35%">--%>
                                        <%--<span id="shouFuKuan">${order.predictPrice}元</span>--%>
                                    <%--</td>--%>

                                </tr>
                                <%--<tr>--%>
                                    <%----%>
                                <%--</tr>--%>
                                <tr>
                                    <td >审核意见：</td>
                                    <td colspan="5">
                                        <span id="check_suggestion">${approveSuggestion}</span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $().ready(function(){
        $("#btnMaterail").click(function () {
            $("#divFrom").show();
            $("#block").hide();
        });
        $("#btn_load").click(function () {
            $("#block").show();
            $("#divFrom").hide();
        });
    });
</script>
</body>
</html>