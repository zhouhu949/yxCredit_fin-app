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
    <script src="${ctx}/resources/js/standard/headquartersReview.js${version}"></script>
    <title>客户审核</title>
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
        .s_floatToolA {
            position: fixed;
            left: 1250px;
            top: 135px;
        }
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
        #showNewImg ul li,#cheatPu ul li,#phonePu ul li{
            display: inline-block;
            width:25%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
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
        .s_floatToolA {
            position: fixed;
            left: 1150px;
            top: 210px;
        }
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
    <input type="hidden" name="orderId" id="taskNodeId" value=""/>
    <div class="commonManager" id="commonManager">
        <%-- 通过输入授信金额--%>
        <%--查看--%>
        <div id="container" class="of-auto_H" style="padding:20px;">
            <!-- 隐藏列 -->
            <!-- 隐藏formId表单编号 -->
            <div class="ly_positionBar">
                <span class="position">客户资料 </span>
            </div>
            <div>
                <button id="btnMaterail" type="button" class="btn btn-primary queryBtn">进件资料</button>
                <button id="btn_load" type="button" class="btn btn-primary queryBtn">上传材料</button>
            </div>
            <div id="divFrom">
                <div class="paddingBox xdproadd" style="width:1200px">
                    <%--借款申请信息--%>
                    <div class="paperBlockfree" id="pictureLoad">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">借款申请信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">申请省份：</td>
                                <td><input type="text" name="provinces" value="${customerLive.provinces}" readonly></td>
                                <td class="align">申请城市：</td>
                                <td><input type="text" class="inpText info-inpW1" value="${customerLive.city}" name="city"readonly></td>
                                <td class="align">希望期数：</td>
                                <td><input type="text" class="inpText info-inpW1" value=""  readonly></td>
                            </tr>
                            <tr>
                                <td class="align">申请金额：</td>
                                <td><input type="text" class="inpText info-inpW1" name="precredit"  value="${order.precredit}" readonly></td>
                                <td class="align">希望的还款方式：</td>
                                <td><input type="text" class="inpText info-inpW1"  value="" readonly></td>
                                <td class="align">月供能力：</td>
                                <td><input type="text" class="inpText info-inpW1"   value="0元" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">可接受每周最高还款额：</td>
                                <td><input type="text" class="inpText info-inpW1" readonly></td>
                                <td class="align">借款用途：</td>
                                <td ><input type="text" class="inpText info-inpW1"  value="购物" readonly></td>
                                <td class="align">预计借款期限：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="periods" value="${order.periods}天" readonly style="width:90%"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--产品信息--%>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">产品信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">产品系列:</td>
                                <td><input type="text" name="applyName" value="${order.productTypeName}" readonly></td>
                                <td class="align">产品名称:</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyIdcard" value="${order.productNameName}" readonly></td>
                                <td class="align">产品期数:</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyMerry" value="${productDetail.periods}" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">产品利率：</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyCensus"  value="${productDetail.multipleRate}%年" readonly></td>
                                <td class="align">还款方式：</td>
                                <td><input type="text" class="inpText" value="<c:if test="${productDetail.payment eq 1}" >月付息，到期还本</c:if><c:if test="${productDetail.payment eq 2}" >到期一次还本付息</c:if><c:if test="${productDetail.payment eq 3}" >等额本息</c:if><c:if test="${productDetail.payment eq 4}" >分期等额</c:if><c:if test="${productDetail.payment eq 5}" >等额本金</c:if>" readonly style="width:90%"></td>
                                <td class="align">授信范围</td>
                                <td id="education">${productDetail.actualLowerLimit}~${productDetail.actualUpperLimit}</td>

                            </tr>
                            <tr>
                                <td class="align">备注：</td>
                                <td colspan="6"><input type="text" class="inpText info-inpW1" name="bank_card" value="${productDetail.interestRemark}" readonly></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <%--您的个人资料--%>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">您的个人资料</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">姓名：</td>
                                <td><input type="text" name="realName" value="${person.realname}" readonly></td>
                                <td class="align">曾用名：</td>
                                <td><input type="text" class="inpText info-inpW1" value="${person.oldname}"  readonly></td>
                                <td class="align">性别：</td>
                                <td><input type="text" class="inpText info-inpW1" name="sex_name" value="${person.sex_name}" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">婚姻状况：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="marry_name"  value="${person.marry_name}" readonly style="width: 90%"></td>
                                <td class="align">教育程度：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="educational_name"  value="${person.educational_name}" readonly style="width: 90%"></td>
                                <td class="align">毕业院校：</td>
                                <td ><input type="text" class="inpText info-inpW1"   readonly style="width: 90%"></td>
                            </tr>
                            <tr>
                                <td class="align">手机（主）：</td>
                                <td><input type="text" class="inpText info-inpW1" name="tel"  value="${customerContact.tel}" readonly></td>
                                <td class="align">密码：</td>
                                <td colspan="3"><input type="text" class="inpText info-inpW1" name="serverpwd"  value="${customerContact.serverpwd}" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">微信号：</td>
                                <td><input type="text" class="inpText info-inpW1" name="wechat" value="${customerContact.wechat}" readonly></td>
                                <td class="align">QQ：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="qq" value="${customerContact.qq}" readonly></td>
                                <td class="align">*电子邮箱：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="email" value="${customerContact.email}" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">*身份证号：</td>
                                <td><input type="text" class="inpText info-inpW1" name="houseBank"  readonly></td>
                                <td class="align">*证件有效期：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseSum"  readonly></td>
                                <td class="align">*发证机关：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseSum"  readonly></td>
                            </tr>
                            <tr>
                                <td class="align">*身份证地址：</td>
                                <td colspan="3"><input type="text" class="inpText info-inpW1" name="houseBank"  readonly></td>
                                <td class="align">*本市生活时长：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseSum"  readonly></td>
                            </tr>
                            <tr>
                                <td class="align">*现居住地址：</td>
                                <td colspan="5"><input type="text"  name="nowaddress" value="${customerLive.provinces} ${customerLive.city} ${customerLive.distric} ${customerLive.nowaddress}" readonly style="width:90%"></td>
                            </tr>
                            <tr>
                                <td class="align">*现居地生活时长：</td>
                                <td><input type="text" class="inpText info-inpW1" name="addresslivetime" value="${customerLive.addresslivetime}"   readonly></td>
                                <td class="align">居住地家庭座机：</td>
                                <td colspan="3"><input type="text" class="inpText info-inpW1"  readonly></td>
                            </tr>
                            <tr>
                                <td class="align">*本市房产状况：</td>
                                <td><input type="text" class="inpText info-inpW1"   readonly></td>
                                <td class="align">*房产归属：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseproperty" value="${customerLive.houseproperty}"  readonly></td>
                                <td class="align">*房产性质：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseSum"  readonly></td>
                            </tr>
                            <tr>
                                <td class="align">*房产地址：</td>
                                <td colspan="3"><input type="text" class="inpText info-inpW1" name="houseBank"  readonly></td>
                                <td class="align">*已购年限：<input type="text" class="inpText info-inpW1" name="houseSum"  readonly></td>
                                <td >年</td>
                            </tr>
                            <tr>
                                <td class="align">电商账号：</td>
                                <td colspan="3"><input type="text" class="inpText info-inpW1" name="houseBank"  readonly></td>
                                <td class="align">电商密码：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseSum"  readonly></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--职业资料--%>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">职业资料</span>
                        </div>
                        <table class="tb_info">
                            <tbody><tr id="zhiyeguishu">
                                <td style="border-bottom:0;"><span class="red" id="professionSpan">*</span>职业归属：</td>
                                <td colspan="5" style="border-bottom:0;">
                                    <input type="radio" id="professionR1" name="professionAscription" value="1" <c:if test="${customer.occupationType eq 1}" >checked="checked"</c:if>disabled="disabled"/>工薪者
                                    <input type="radio" id="professionR2" name="professionAscription" value="2" <c:if test="${customer.occupationType eq 2}" >checked="checked"</c:if> disabled="disabled"/>经营者
                                    <input type="radio" id="professionR3" name="professionAscription" value="3" <c:if test="${customer.occupationType eq 3}" >checked="checked"</c:if> disabled="disabled"/>自由职业者
                                    <input type="radio" id="professionR4" name="professionAscription" value="4" <c:if test="${customer.occupationType eq 4}" >checked="checked"</c:if> disabled="disabled"/>在读学生
                                    <input type="radio" id="professionR5" name="professionAscription" value="5" <c:if test="${customer.occupationType eq 5}" >checked="checked"</c:if> disabled="disabled"/>退休人员
                                </td>
                            </tr>
                            </tbody></table>

                        <div id="consumerFinance">
                            <!-- 引用crmOrderApply 的工薪者结束 -->
                            <table class="tb_info e-empTypeInfo" id="e-empTypeInfo_1" style="<c:if test="${customer.occupationType !=  1}" >display: none;</c:if>">
                                <tbody><tr>
                                    <td class="tbTitW1_5"><span class="red" id="paperBlockfreeSP1">*</span>单位名称：</td>
                                    <td><span style="">${customerEarne.company}</span></td>
                                    <td class="tbTitW1_5"><span class="red" id="paperBlockfreeSP2">*</span>部门：</td>
                                    <td><span style="">${customerEarne.branch}</span> </td>
                                    <td class="tbTitW1_5"><span class="red" id="paperBlockfreeSP3">*</span>职务：</td>
                                    <td><span style="">${customerEarne.profession}</span> </td>
                                </tr>
                                <tr>
                                    <td><span class="red" id="paperBlockfreeSP4">*</span>单位性质：</td>
                                    <td><span style="">${customerEarne.companyProperty} </span></td>
                                    <td><span class="red" id="paperBlockfreeSP5">*</span>单位规模：</td>
                                    <td><span style="">${customerEarne.companySize} </span></td>
                                    <td><span class="red" id="paperBlockfreeSP6">*</span>职级：</td>
                                    <td><span style="">${customerEarne.professionGrade} </span></td>
                                </tr>
                                <tr>
                                    <td><span class="red" id="paperBlockfreeSP7">*</span>单位地址：</td>
                                    <td colspan="5"><ul class="group_line">
                                        <li class="gr_item info-selW2"><span style="">${customerEarne.provinces}</span></li>
                                        <li class="gr_item info-selW2"><span style="">${customerEarne.city} </span></li>
                                        <li class="gr_item info-selW2"><span style=""> ${customerEarne.distric}</span></li>
                                        <li class="gr_item"><span style="">${customerEarne.companyAddress}</span></li>
                                    </ul></td>
                                </tr>
                                <tr>
                                    <td><span class="red" id="paperBlockfreeSP8">*</span>单位固定电话：</td>
                                    <td colspan="3"><span class="hideclass" style="display: none;">区号</span><span style=""> </span><span class="hideclass" style="display: none;">电话</span> <span style=""> </span> <span class="hideclass" style="display: none;">分机</span><span style=""> </span></td>
                                    <td>现单位工作年限:</td>
                                    <td><span style="">${customerEarne.workYears} </span> 年</td>
                                </tr>
                                <!-- 20160602 begin -->
                                <tr>
                                    <td><span class="red" id="paperBlockfreeSP10"></span>基本月薪:</td>
                                    <td><span style="">${customerEarne.basicMonthlyPay}</span> 元 </td>
                                    <td><span class="red" id="paperBlockfreeSP11"></span>每月发薪日:</td>
                                    <td><span style=""> ${customerEarne.payDay}</span>  </td>
                                    <td><span class="red" id="paperBlockfreeSP12"></span>发薪方式:</td>
                                    <td>
                                        <span style="">  ${customerEarne.payType}</span>
                                    </td>
                                </tr><!-- 20160602 end -->

                                </tbody></table>

                            <!--工薪者end-->
                            <!--经营者start-->
                            <table class="tb_info e-empTypeInfo" id="e-empTypeInfo_2" style="<c:if test="${customer.occupationType !=  2}" >display: none;</c:if>">
                                <tbody><tr>
                                    <td class="tbTitW3">企业名称：</td>
                                    <td><span style=""> ${customerManager.enterpris}</span></td>
                                    <td class="tbTitW3"><span class="red">*</span>身份：</td>
                                    <td><span style="">  ${customerManager.identity}</span>
                                    </td>
                                    <td>股份占比：</td>
                                    <td><span style=""> ${customerManager.stockPercent} </span>%</td>
                                </tr>
                                <tr>
                                    <td class="tbTitW4"><span class="red">*</span>企业经营年限（以实际日期为准）：</td>
                                    <td><span style=""> ${customerManager.managementYears} </span>年</td>
                                    <td><span class="red">*</span>注册资本：</td>
                                    <td><span style=""> ${customerManager.capital} </span>万元</td>
                                    <td><span class="red">*</span>在职员工：</td>
                                    <td><span style=""> ${customerManager.currentEmployees} </span><span style=""> </span>人</td>
                                </tr>
                                <tr>
                                    <td>所属行业</td>
                                    <td><span style=""> </span></td>
                                    <td><span class="red">*</span>经营地归属：</td>
                                    <td colspan="3"><span style=""> ${customerManager.mangementPlace} </span>
                                        <span id="leaseYear" style="display: none">年租金:<span style=""> ${customerManager.yearRent} </span>万元</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>经营地址：</td>

                                    <td colspan="5"><span style="">${customerManager.provinces} ${customerManager.city} ${customerManager.distric} ${customerManager.managementAddress}</span></td>
                                </tr>
                                <tr>
                                    <td><span class="red">*</span>过去一年营业收入</td>
                                    <td colspan="2"><span style=""> ${customerManager.yearIncome} </span>万元</td>
                                    <td><span class="red">*</span>过去一年利润：</td>
                                    <td colspan="2"><span style=""> ${customerManager.yearProfit} </span>万元</td>
                                </tr>
                                </tbody></table>
                            <!--经营者end-->
                            <!-- 自由职业者start -->
                            <table class="tb_info e-empTypeInfo" id="e-empTypeInfo_3" style="<c:if test="${customer.occupationType !=  3}" >display: none;</c:if>">
                                <tbody><tr>
                                    <td class="tbTitW3">收入来源：</td>
                                    <td><span style="">${customerFreelance.sourceIncome} </span></td>
                                    <td class="tbTitW4">月均收入金额：</td>
                                    <td><span style=""> ${customerFreelance.averageIncomeMonth}</span>元</td>
                                </tr>
                                <tr>
                                    <td class="tbTitW3">已从事此项工作年限(年)：</td>
                                    <td><span style=""> ${customerFreelance.currentWorkYears}</span></td>
                                    <td class="tbTitW4">相关资格证书：</td>
                                    <td><span style=""> ${customerFreelance.certificateName}</span></td>
                                </tr>
                                </tbody></table>
                            <!-- 自由职业者end -->
                            <!-- 在读学生start -->
                            <table class="tb_info e-empTypeInfo" id="e-empTypeInfo_4" style="<c:if test="${customer.occupationType !=  4}" >display: none;</c:if>">
                                <tbody><tr>
                                    <td class="tbTitW3">学校名称：</td>
                                    <td><span style="">${customerStu.schoolName} </span></td>
                                    <td class="tbTitW4">就读专业：</td>
                                    <td><span style=""></span></td>
                                </tr>
                                <tr>
                                    <td class="tbTitW3">院系：</td>
                                    <td><span style="">${customerStu.departments} </span></td>
                                    <td class="tbTitW4">班级：</td>
                                    <td><span style="">${customerStu.theClass} </span></td>
                                </tr>
                                </tbody></table>
                            <!-- 在读学生end -->
                            <!-- 退休人员start -->
                            <table class="tb_info e-empTypeInfo" id="e-empTypeInfo_5" style="<c:if test="${customer.occupationType !=  5}" >display: none;</c:if>">
                                <tbody><tr>
                                    <td class="tbTitW3">原单位名称：</td>
                                    <td><span style=""><c:if test="${customerRetire.formerCompany eq null}">不详</c:if><c:if test="${customerRetire.formerCompany != null}">${customerRetire.formerCompany}</c:if></span></td>
                                    <td class="tbTitW4">原单位地址：</td>
                                    <td><span style="">${customerRetire.provinces} ${customerRetire.city} ${customerRetire.distric} ${customerRetire.formerCompanyAddress} </span></td>
                                </tr>
                                <tr>
                                    <td class="tbTitW3">月退休金金额：</td>
                                    <td><span style=""> ${customerRetire.pensionMonth}</span>元</td>
                                    <td class="tbTitW4">每月其他收入：</td>
                                    <td><span style="">${customerRetire.monthOtherIncome} </span>元,来源:<span style="">${customerRetire.otherSourceIncome} </span></td>
                                </tr>
                                <tr>
                                    <td class="tbTitW3">已退休年限：</td>
                                    <td colspan="3"><span style="">${customerRetire.retireYears} </span>年</td>
                                </tr>
                                </tbody></table>
                            <!-- 退休人员end -->
                        </div>
                    </div>
                    <%--联系人资料--%>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">联系人资料<span class="red" style="padding-left: 10px">提示：联系人的手机号和固定电话请至少填写一个。</span></span>
                        </div>
                        <table class="tb_info ">

                            <!-- 配偶信息 -->
                            <tbody><tr>
                                <td colspan="6" style="background-color: rgba(242, 242, 242, 1);">直系亲属联系人</td>
                            </tr>
                            <c:forEach var="linkMans" items="${linkmanList}" varStatus="st">
                                <tr>
                                    <td><span class="red" id="mateNamered" style="display: none;">*</span>直系亲属联系人${st.index}：</td>
                                    <td><span style=""> ${linkMans.linkName}</span>
                                    </td>
                                    <td>关系：${linkMans.relationshipName}</td>
                                    <td></td>
                                    <td><span class="red" id="mateIsKnowred" style="display: none;">*</span>是否知晓此项借款：</td>
                                    <td><span style=""></span></td>
                                </tr>
                                <tr>
                                    <td><span class="red" id="phonered" style="display: none;">*</span>手机号：</td>
                                    <td><span style="">${linkMans.contact} </span>
                                    </td>
                                    <td>固定电话：</td>
                                    <td colspan="3"><span class="hideclass" style="display: none;">区号</span><span style=""> </span> <span class="hideclass" style="display: none;">电话</span><span style=""> </span> <span class="hideclass" style="display: none;">分机</span><span style=""> </span></td>
                                </tr>
                            </c:forEach>
                            </tbody></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="s_floatToolA" style="left: 1269px; display: block;" id="buttonsApplay">
    <div id="reviewDiv">
        <div class="mb10" id="order_apply_sub_next" style="">
            <span class="btn_pad_s2 btn_color1"  onclick="clickNextButton(12)">通过</span>
        </div>
        <div class="mb10" id="order_apply_sub_dead" style="">
            <span class="btn_pad_s2 btn_color1"  onclick="clickNextButton(4)">拒绝</span>
        </div>
    </div>
</div>
<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>
<div id="preQuotaDialog" style=" display: none" class="layui-layer-wrap">
    <form class="calculateQuotaDialogForm" action="">
        <div class="paddingBox" >
            <table class="nobor">
                <tbody><tr>
                    <td colspan="6"><hr style="height:1px;border:none;border-top:1px solid #555555;"></td>
                </tr>
                <tr>
                    <td>产品序号：</td>
                    <td>
                        <span id="preQuotaDialog_cpNumber">${order.productType}</span>
                    </td>
                    <td>产品名称：</td>
                    <td>
                        <span id="preQuotaDialog_cptName">${order.productTypeName}</span>
                    </td>

                    <td width="80">产品期数：</td>
                    <td>
                        <span id="preQuotaDialog_periods">${productDetail.periods}</span>
                    </td>
                </tr>
                <tr>
                    <td>合同利率：</td>
                    <td>
                        <span id="preQuotaDialog_contractRate">${productDetail.contractRate}%</span>
                    </td>

                    <td style="">综合利率：</td>
                    <td>
                        <span id="preQuotaDialog_multipleRate">${productDetail.multipleRate}%</span>
                    </td>

                    <td>还款方式：</td>
                    <td>
                        <span id="preQuotaDialog_payment"><c:if test="${productDetail.payment eq 1}" >月付息，到期还本</c:if><c:if test="${productDetail.payment eq 2}" >到期一次还本付息</c:if><c:if test="${productDetail.payment eq 3}" >等额本息</c:if><c:if test="${productDetail.payment eq 4}" >分期等额</c:if><c:if test="${productDetail.payment eq 5}" >等额本金</c:if></span>
                        <input id="preQuotaDialog_payment_id" style="display:none;">
                    </td>
                </tr>
                <tr>
                    <td>授信范围：</td>
                    <td>
                        <span class="" id="preQuotaDialog_actualLimit">${productDetail.actualLowerLimit}~${productDetail.actualUpperLimit}</span>
                    </td>
                </tr>

                <tr>
                    <td colspan="6"><hr style="height:1px;border:none;border-top:1px solid #555555;"></td>
                </tr>
                <tr>
                    <td>审核备注:</td>
                    <td colspan="5"><textarea id="preQuotaDialog_remark" style="width:380px"></textarea></td>
                </tr>
                </tbody></table>
        </div>
    </form>
</div>
<div id="approvalRefused" style="display: none" class="layui-layer-wrap">
    <form class="calculateQuotaDialogForm" action="">
        <div class="paddingBox" >
            <table class="nobor">
                <tbody>
                <tr>
                    <td>拒绝原因:</td>
                    <td colspan="5"><textarea id="remark" style="height:200px;width:380px"></textarea></td>
                </tr>
                </tbody></table>
        </div>
    </form>
</div>
<div class="paperBlock" id="block" style="display: none;margin:0px">
    <div class="block_hd">
        <s class="ico icon-file-text-alt"></s><span class="bl_tit">个人基本材料</span>
    </div>
    <table class="tb_info ">
        <tbody><tr class="tb_head t-bold ">
            <td width="15%">材料名称</td>
            <td width="30%">文件名称</td>
            <td width="25%">上传时间</td>
        </tr>
        <tr>
            <td><span class="red">*</span>身份证</td>

            <td id="file9">

                <c:forEach var="img" items="${imgList}" varStatus="st">
                    <p class="file-work" style="text-decoration: none;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;">
                        <span>${fn:split(img.src,'/')[2]}</span>
                        <a name="loadFileName" href="${appUrl}${img.src}" target="_Blank">查看</a>&nbsp;&nbsp;
                    </p>
                </c:forEach>
            </td>
            <td id="ctime9">
                <c:forEach var="img" items="${imgList}" varStatus="st">
                    <p class="file-work" style="text-decoration: none;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;">
                        <c:set var="string1" value="${img.creatTime}"/>
                        <c:set var="string2" value="${fn:substring(string1, 0, 4)}" />
                        <c:set var="string3" value="${fn:substring(string1, 4, 6)}" />
                        <c:set var="string4" value="${fn:substring(string1, 6, 8)}" />
                            ${string2}-${string3}-${string4}
                    </p>
                    <%--${fn:substring(${img.creatTime}, <beginIndex>, <endIndex>)}--%>
                    <%--<fmt:parseDate value="${img.creatTime}" var="yearMonth" pattern="yyyy-MM-dd"/>--%>
                </c:forEach>
            </td>
        </tr>
        </tbody></table>
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