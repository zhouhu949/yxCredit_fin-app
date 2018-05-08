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
    <script src="${ctx}/resources/js/customerManage/customerDetail.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>
    <title>客户审核</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
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
            background: #05c1bc;
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
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
        }
        .ReasonableUl li:hover{
            display: inline-block;
            width: 25%;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
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
        #firstCredit,#passRemark,#cheatRemark,#photoRemark{width:95%;float:left;margin-left:0px;margin-left:4px;}
        #cheatRemark,#photoRemark{width: 99%;}
        .telRecord{height: 37px;  text-align: left;  padding-left: 35px!important;}
        #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#recordList5 td,#recordList6 td,#answerBody td,#answerBody1 td{border-right:1px solid #ccc}
        #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#answerBody tr,#recordList5 tr,#recordList6 tr,#answerBody1 tr{border:1px solid #ccc;}
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
        #divFrom td{text-align: center;}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
        #divFrom .telRecord{text-align: left;font-weight: bold;}
        textarea{resize: none;}
        #showNewImg{
            text-align: left;
        }
        #showNewImg ul li,#cheatPu ul li,#phonePu ul li,#checkTd ul li{
            display: inline-block;
            width:25%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
        }
        #loanDetail tr{height: 37px;}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="leftStatus" value="${leftStatus}"/>
    <input type="hidden" name="userId" id="userId" value="${userId}"/>
    <input type="hidden" name="handName" id="handName" value="${nickName}"/>
    <input type="hidden" id="host">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="customerName" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">身份证号</label>
                    <label>
                        <input name="card" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">手机号码</label>
                    <label>
                        <input name="tel" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">订单编号</label>
                    <label>
                        <input name="orderNo" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">创建时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li><span class="line-cut">--</span>
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
            <div class="order_list">
                <table style="cursor:pointer;" id="order_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>客户名称</th>
                        <th>订单编号</th>
                        <th>手机号码</th>
                        <th>产品名称</th>
                        <th>分期期数</th>
                        <th>状态</th>
                        <th>是否通过</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%-- 通过输入授信金额--%>
        <div id="Though_Sum" style="display: none">
            <div class="commonManager ">
                <div class="addCommon">
                    <ul class="clearfix">
                        <li style="width:100%;">
                            <label class="label_name" style="width:15%;">初审授信额度</label>
                            <label style="width:80%;">
                                <input name="firstCredit" id ="firstCredit" type="text" value="" class="text_add" />
                            </label>
                        </li>
                    </ul>
                    <div class="Remark" style="padding-top: 20px;">
                        <label class="label_name" style="width:15%;">审批意见</label>
                        <label style="width:80%;">
                            <textarea name="remark" id ="passRemark" cols="" rows="" style=" height: 100px; padding: 5px;"></textarea>
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <%--查看--%>
        <div id="container" class="of-auto_H" style="padding:20px;display:none;">
            <!-- 隐藏列 -->
            <!-- orderID, -->
            <!-- customerId, -->
            <input type="hidden" id="orderId">
            <input type="hidden" id="customerId">
            <input type="hidden" id="merchantId">
            <input type="hidden" id="merchantNameHidden">
            <!-- 隐藏formId表单编号 -->
            <div id="divFrom">
                <div class="paddingBox xdproadd" style="width:1200px">
                    <%--贷款明细--%>
                    <div class="paperBlockfree" id="pictureLoad">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">贷款明细</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody  id="loanDetail">
                            <tr>
                                <td class="align">产品类型:</td>
                                <td id="proType"></td>
                                <td class="align">当前审批人：</td>
                                <td id="proApproval"></td>
                                <td class="align">贷款号：</td>
                                <td id="proLoan"></td>
                            </tr>
                            <tr>
                                <td class="align">状态：</td>
                                <td id="proState"></td>
                                <td class="align">手机号码：</td>
                                <td id="proTel"></td>
                                <td class="align">申请时间：</td>
                                <td id="proApplytime"></td>
                            </tr>
                            <tr>
                                <td class="align">申请金额：</td>
                                <td id="proSum"></td>
                                <td class="align">申请期限：</td>
                                <td colspan="4" id="proDeadline"></td>
                            </tr>
                            <tr>
                                <td class="align">定位地址：</td>
                                <td id="BAK" colspan="6"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--申请人信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">申请人信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">姓名:</td>
                                <td id=""><input type="text" name="applyName" id="applyName" readonly></td>
                                <td class="align">身份证号码:</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyIdcard" id="applyIdcard" readonly></td>
                                <td class="align">婚姻状况:</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyMerry" id="applyMerry" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">户籍：</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyCensus"  id="applyCensus" readonly></td>
                                <td class="align">当前住址：</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyAddr"  id="applyAddr" readonly></td>
                                <td class="align">学历</td>
                                <td id="education"></td>
                                <td style="display:none;" class="align">银行卡号：</td>
                                <td style="display:none;"><input type="text" class="inpText info-inpW1" name="bank_card"  id="bank_card" readonly></td>
                            </tr>
                            <tr style="display:none;">
                                <td class="align">开户银行：</td>
                                <td><input type="text" class="inpText info-inpW1" name="account_bank"  id="account_bank" readonly></td>
                                <td class="align">开户地址：</td>
                                <td colspan="3"><input type="text" class="inpText info-inpW1" name="account_city"  id="account_city" readonly width="90%"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--待装修房产信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">待装修房产信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">房产面积：</td>
                                <td><input type="text" name="houseArea" id="houseArea" readonly></td>
                                <td class="align">户型结构：</td>
                                <td><input type="text" class="inpText info-inpW1" name="houseFrame" id="houseFrame" readonly></td>
                                <td class="align">购买方式：</td>
                                <td><input type="text" class="inpText info-inpW1" name="housePurcha" id="housePurcha" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">房产地址：</td>
                                <td colspan="6"><input type="text" class="inpText info-inpW1" name="houseAddr"  id="houseAddr" readonly width="90%"></td>
                            </tr>
                            <tr>
                                <td class="align">按揭银行：</td>
                                <td><input type="text" class="inpText info-inpW1" name="houseBank"  id="houseBank" readonly></td>
                                <td class="align">月供金额：</td>
                                <td><input type="text" class="inpText info-inpW1" name="houseSum"  id="houseSum" readonly></td>
                                <td class="align">已按揭：</td>
                                <td><input type="text" class="inpText info-inpW1" name="houseDone"  id="houseDone" readonly></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--额度匹配自己输入--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">额度匹配</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <input type="hidden" id="cmId">
                                <td class="align">小区名称：</td>
                                <td style="width: 287px;">
                                    <input type="text" name="limitName" id="limitName" style="border:1px solid #ccc" readonly>
                                </td>
                                <td class="align">小区平均房价：</td>
                                <td><input type="text" class="inpText info-inpW1" name="limitPrice" id="limitPrice" readonly><span class="unit">元/m2</span></td>
                                <td class="align">小区平均装修价格：</td>
                                <td><input type="text" class="inpText info-inpW1" name="limitDeco" id="limitDeco" readonly><span class="unit"></span>元/m2</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--装修合理性判断--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="text-align:left;clear: both" onclick="shrink()">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">装修合理性判断</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;" id="showReasonable">
                            <tbody>
                            <tr style="background: #DFF1D9;line-height: 30px;">
                                <td colspan="8" class="telRecord">装修合理性判断
                                    <button class="btn btn-primary addBtn" onclick="showListImg(this)" style="margin-right:20px;float: right">查看清单</button>
                                    <label  style="margin-right:20px;float: right">
                                        <input disabled type="radio" name="ListIsReasonable" id="ListIsReasonable_is" value="1" checked>是
                                        <input disabled type="radio" name="ListIsReasonable" id="ListIsReasonable_no" value="0">否
                                    </label>
                                    <label style="margin-right:20px;float: right">
                                        装修清单是否合理
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>装修公司：</td>
                                <td>
                                    <input type="text" name="ReasonableInput_fitment" style="text-align: center;cursor: pointer;" readonly />
                                    <span  onclick="checkMerchant()" style="cursor: pointer;float:right;" class="btn btn-primary queryBtn">查看商户信息</span>
                                </td>
                                <td>预估装修金额：</td>
                                <td colspan="3">
                                    <input type="text" name="ReasonableInput_fitment_money" style="text-align: center;width: 100%;margin-left:0" readonly>
                                </td>
                                <%--<td>材料名称：</td>
                                <td>
                                    <input type="text" name="ReasonableInput" style="text-align: left" readonly>
                                </td>--%>
                            </tr>
                            </tbody>
                        </table>
                        <div  style="text-align: left">
                            <div id="showReasonableImg" style="display: none">
                                <ul class="ReasonableUl">
                                </ul>
                            </div>
                            <div id="showReasonableContent" style="display:none"></div>
                            <div id="showReasonableUpload"></div>
                        </div>
                    </div>
                    <%--电核信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">电核信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr style="height:37px;">
                                <td class="align">借款信息核实：</td>
                                <td>
                                    <input type="radio" name="checkInfo" style="margin-left:4em;float:left;"  value="1"><span style="float:left;">是</span>
                                    <input type="radio" name="checkInfo" style="margin-left:4em;float:left;" value="0"><span style="float:left;">否</span>
                                </td>
                                <td class="align">工作情况核实：</td>
                                <td>
                                    <input type="radio" name="checkCondi" style="margin-left:4em;float:left;"  value="1"><span style="float:left;">是</span>
                                    <input type="radio" name="checkCondi" style="margin-left:4em;float:left;" value="0"><span style="float:left;">否</span>
                                </td>
                                <td class="align">房产情况核实：</td>
                                <td colspan="2">
                                    <input type="radio" name="checkHouse" style="margin-left:4em;float:left;"  value="1"><span style="float:left;">是</span>
                                    <input type="radio" name="checkHouse" style="margin-left:4em;float:left;" value="0"><span style="float:left;">否</span>
                                </td>
                            </tr>
                            <tr style="height:37px;">
                                <td class="align">负债信息核实：</td>
                                <td>
                                    <input type="radio" name="checkLoan" style="margin-left:4em;float:left;"  value="1"><span style="float:left;">是</span>
                                    <input type="radio" name="checkLoan" style="margin-left:4em;float:left;" value="0"><span style="float:left;">否</span>
                                </td>
                                <td class="align">流水核查：</td>
                                <td colspan="4">
                                    <input type="radio" name="checkGlide" style="margin-left:4em;float:left;"  value="1"><span style="float:left;">是</span>
                                    <input type="radio" name="checkGlide" style="margin-left:4em;float:left;" value="0"><span style="float:left;">否</span>
                                </td>
                            </tr>
                            <tr style="height:37px;">
                                <td class="align">备注：</td>
                                <td colspan="5">
                                    <textarea name="" id="elecRemark" cols="30" rows="2" readonly></textarea>
                                </td>
                                <%--<td>
                                    <button type="button" class="btn btn-primary addBtn" id="elecButton" onclick="elecButton()" style="margin-left:2em;">确认</button>
                                </td>--%>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%-- 本人电话拨打记录--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">本人电话拨打记录</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <thead>
                            <tr style="background: #DFF1D9;line-height: 30px;">
                                <td colspan="10" class="telRecord">
                                    本人电话拨打记录
                                </td>
                            </tr>
                            <tr style="border: 1px solid #ccc; border-top:none;" >
                                <th style="width :20%;text-align: center;">接听时间</th>
                                <th style="width :20%;text-align: center;">接听状态</th>
                                <th style="width :20%;text-align: center;">接听电话</th>
                                <th style="width :30%;text-align: center;">备注</th>
                                <th style="width :10%;text-align: center;">音频文件</th>
                            </tr>
                            </thead>
                            <tbody id="recordList1" class="recordArray">
                            </tbody>
                        </table>
                    </div>
                    <%--客户情况简述备注--%>
                    <div class="paperBlockfree" style="margin-top:20px;height: 200px;">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">客户情况简述备注</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;height: 85%">
                            <tbody>
                            <tr>
                                <td class="align">
                                    备注：
                                </td>
                                <td><textarea type="text"  id="customerRemark" readonly rows="8"></textarea></td>
                                <%-- <td>
                                     <button type="button" class="btn btn-primary addBtn"  onclick="remarkBtn()" style="margin-left:2em;">确认</button>
                                 </td>--%>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--单位信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">单位信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">单位名称：</td>
                                <td><input type="text" name="unitName" id="unitName" readonly></td>
                                <td class="align">单位性质：</td>
                                <td><input type="text" class="inpText info-inpW1" name="unitPro" id="unitPro" readonly></td>
                                <td class="align">单位电话：</td>
                                <td colspan="4"><input type="text" class="inpText info-inpW1" name="unitTel" id="unitTel" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">单位地址：</td>
                                <td colspan="7"><input type="text" class="inpText info-inpW1" name="unitAddr"  id="unitAddr" readonly style="width:90%"></td>
                            </tr>
                            <tr>
                                <td class="align">部门：</td>
                                <td><input type="text" class="inpText info-inpW1" name="unitDepart"  id="unitDepart" readonly></td>
                                <td class="align">职级：</td>
                                <td><input type="text" class="inpText info-inpW1" name="unitGrade"  id="unitGrade" readonly></td>
                                <td class="align">现单位入职时间/注册时间：</td>
                                <td colspan="4"><input type="text" class="inpText info-inpW1" name="unitYear"  id="unitYear" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">工资发放形式：</td>
                                <td><input type="text" class="inpText info-inpW1" name="unitDeliverty"  id="unitDeliverty" readonly></td>
                                <td class="align">月收入：</td>
                                <td><input type="text" class="inpText info-inpW1" name="unitIncome"  id="unitIncome" readonly></td>
                                <td class="align">发薪日：</td>
                                <td colspan="4"><input type="text" class="inpText info-inpW1" name="unitDay"  id="unitDay" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">是否缴纳公积金及社保：</td>
                                <td colspan="7"><input type="text" class="inpText info-inpW1" name="unitSocial"  id="unitSocial" readonly></td>
                            </tr>

                            <%-- 网络调查--%>
                            <table class="tb_info " style="font-size:12px;">
                                <thead>
                                <tr style="background: #DFF1D9;line-height: 30px;">
                                    <td colspan="10" class="telRecord">
                                        网络调查
                                    </td>
                                </tr>
                                <tr style="border: 1px solid #ccc; border-top:none;" >
                                    <th style="width :20%;text-align: center;">接听时间</th>
                                    <th style="width :20%;text-align: center;">接听状态</th>
                                    <th style="width :20%;text-align: center;">接听电话</th>
                                    <th style="width :30%;text-align: center;">备注</th>
                                    <th style="width :10%;text-align: center;">音频文件</th>
                                </tr>
                                </thead>
                                <tbody id="recordList2" class="recordArray">
                                </tbody>
                            </table>
                            <%--114调查--%>
                            <div class="cencus">
                                114调查
                            </div>
                            <table class="tb_info " style="font-size:12px;">
                                <thead>
                                <tr style="background: #DFF1D9;line-height: 30px;">
                                    <td colspan="10" class="telRecord">
                                        114调查
                                    </td>
                                </tr>
                                <tr style="border: 1px solid #ccc; border-top:none;" >
                                    <th style="width :20%;text-align: center;">接听时间</th>
                                    <th style="width :20%;text-align: center;">接听状态</th>
                                    <th style="width :20%;text-align: center;">接听电话</th>
                                    <th style="width :30%;text-align: center;">备注</th>
                                    <th style="width :10%;text-align: center;">音频文件</th>
                                </tr>
                                </thead>
                                <tbody id="recordList3" class="recordArray">
                                </tbody>
                            </table>
                            <%-- 进件单固--%>
                            <table class="tb_info " style="font-size:12px;">
                                <thead>
                                <tr style="background: #DFF1D9;line-height: 30px;">
                                    <td colspan="10" class="telRecord">
                                        进件单固
                                    </td>
                                </tr>
                                <tr style="border: 1px solid #ccc; border-top:none;" >
                                    <th style="width :20%;text-align: center;">接听时间</th>
                                    <th style="width :20%;text-align: center;">接听状态</th>
                                    <th style="width :20%;text-align: center;">接听电话</th>
                                    <th style="width :30%;text-align: center;">备注</th>
                                    <th style="width :10%;text-align: center;">音频文件</th>
                                </tr>
                                </thead>
                                <tbody id="recordList4" class="recordArray">
                                </tbody>
                            </table>
                        </table>
                    </div>
                    <%--联系人信息--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">联系人信息</span>
                        </div>
                        <%--直系联系人--%>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td colspan="10" class="telRecord">直系联系人</td>
                            </tr>
                            <tr>
                                <td class="relInfo">姓名：</td>
                                <td style="text-align: right;width:100px"><input style="width:90%" type="text" name="directName" id="directName" readonly></td>
                                <td>关系：</td>
                                <td style="width:100px"><input type="text" class="inpText info-inpW1" style="width:90%" name="directRel" id="directRel" readonly></td>
                                <td>工作单位：</td>
                                <td style=""><input type="text" class="inpText info-inpW1" style="width:90%" name="directUnit" id="directUnit" readonly></td>
                                <td>联系电话：</td>
                                <td style="text-align: right;width:120px" ><input type="text" name="directTel" style="width:90%" id="directTel" readonly></td>
                                <td>是否知晓此次贷款申请：</td>
                                <td style="text-align: center;width:65px;" id="knownLoanName"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="tb_info " style="font-size:12px;">
                            <thead>
                            <tr style="border: 1px solid #ccc; border-top:none;background: #DFF1D9;" >
                                <th style="width :20%;text-align: center;">接听时间</th>
                                <th style="width :20%;text-align: center;">接听状态</th>
                                <th style="width :20%;text-align: center;">接听电话</th>
                                <th style="width :30%;text-align: center;">备注</th>
                                <th style="width :10%;text-align: center;">音频文件</th>
                            </tr>
                            </thead>
                            <tbody id="recordList5" class="recordArray">
                            </tbody>
                        </table>
                        <%--同事联系人--%>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td colspan="8" class="telRecord" style="border-top:none;">其他联系人</td>
                            </tr>
                            <tr>
                                <td class="relInfo align">姓名：</td>
                                <td><input type="text" name="directName" id="workerName" readonly></td>
                                <td class="align">部门：</td>
                                <td><input type="text" class="inpText info-inpW1"  id="workerDepart" name="directRel"  readonly></td>
                                <td class="align">职务：</td>
                                <td><input type="text" class="inpText info-inpW1"  id="workerPost" name="directUnit" readonly></td>
                                <td class="align">联系电话：</td>
                                <td><input type="text" name="directTel" id="workerTel"  readonly></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="tb_info " style="font-size:12px;">
                            <thead>
                            <tr style="border: 1px solid #ccc; border-top:none;background: #DFF1D9;" >
                                <th style="width :20%;text-align: center;">接听时间</th>
                                <th style="width :20%;text-align: center;">接听状态</th>
                                <th style="width :20%;text-align: center;">接听电话</th>
                                <th style="width :30%;text-align: center;">备注</th>
                                <th style="width :10%;text-align: center;">音频文件</th>
                            </tr>
                            </thead>
                            <tbody id="recordList6" class="recordArray">
                            </tbody>
                        </table>
                    </div>
                    <%--证明文件--%>
                    <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                            <div class="block_hd" style="float:left;">
                                <s class="ico icon-file-text-alt"></s><span class="bl_tit">上传资料</span>
                            </div>
                            <table class="tb_info" style="font-size:12px;">
                                <thead>
                                <tr>
                                    <th style="background: #DEF0D8;height:43px">图片资料</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="tdTitle align" id="showNewImg" style="text-align: left"><ul></ul></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    <%--反欺诈资料--%>
                    <div class="paperBlockfree" id="antiFraud1">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">反欺诈资料</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">状态:</td>
                                <td id="cheatState"></td>
                                <td class="align">备注：</td>
                                <td colspan="3">
                                    <textarea name="" id="cheatRemark" cols="30" rows="2" readonly></textarea>
                                </td>
                            </tr>
                            <tr style="height: 37px;" id="cheatImg">
                                <td class="align">
                                    反欺诈资料预览：
                                </td>
                                <td colspan="3" class="imgShowTd" id="cheatPu">
                                    <ul style="text-align: left;"></ul>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--反欺诈资料-未通过--%>
                    <%--<div class="paperBlockfree" id="antiFraud0">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">反欺诈资料</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">状态:</td>
                                <td id="antiFraud02"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>--%>
                    <%--拍客资料--%>
                    <div class="paperBlockfree" style="overflow:hidden" id="custPhone">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">拍客资料</span>
                        </div>
                        <%-- <div style="height:145px;margin-top:3em;">
                             <div class="getFanQiZha PaiKe">
                                 <input type="hidden" class="imgHidden">&lt;%&ndash;删除的图片id及名称&ndash;%&gt;
                                 <form action="" enctype="multipart/form-data">
                                     <input type="hidden" name="orderId">
                                     <input type="hidden" name="customerId">
                                     <input type="hidden" name="type" value="7">
                                     <div class="imagediv">
                                         <input type="file"  name="pic" class="picShow" onchange="setImagePreview(this,1)"/>
                                         <img class="addMaterial" src="../resources/images/photoadd.png" />
                                     </div>
                                 </form>
                                 <input type="hidden" class="imgHidden" value="1">
                                 <input type="hidden" class="imgHidden" value="8">
                             </div>
                         </div>--%>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">状态:</td>
                                <td>通过</td>
                                <td class="align">备注：</td>
                                <td colspan="3">
                                    <textarea name="" id="photoRemark" cols="30" rows="2" readonly></textarea>
                                </td>
                            </tr>
                            <tr style="height: 37px;" id="phoneImg">
                                <td class="align">
                                    拍客资料预览：
                                </td>
                                <td colspan="3" class="imgShowTd" id="phonePu">
                                    <ul style="text-align: left;"></ul>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--自动化审批结果--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">自动化审批结果</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">审批状态:</td>
                                <td style="width: 287px;" id="isPass">通过</td>
                                <td class="align">
                                    信用评分：
                                </td>
                                <td style="text-align: center;" id="scoreCard"></td>
                            </tr>
                            <tr>
                                <td class="align">
                                    预授信金额:
                                </td>
                                <td style="text-align: center;"  id="precredit"></td>
                                <td colspan="2">
                                    <span  onclick="crediet(document.getElementById('cardNum').getAttribute('value'))" style="cursor: pointer;float:left;" class="btn btn-primary queryBtn">风险评估结果</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--审核人员上传资料--%>
                   <%-- <div class="paperBlockfree" style="">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">审核人员资料</span>
                        </div>
                        <div style="height:145px;margin:30px 0 20px 0;" id="yyzz">

                        </div>
                    </div>--%>
                    <div class="paperBlockfree" style="position: relative;margin-top:20px;" id = "finlZs">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">审核人员资料</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;">
                            <thead>
                            <tr>
                                <th style="background: #DEF0D8;height:43px">图片资料</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="tdTitle align"  style="text-align: left" id="checkTd">
                                    <ul id="yyzz"></ul>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--评估结果--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">初审结果</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align">审批状态:</td>
                                <td style="width:287px;">
                                    <select name="" id="checkState" disabled="disabled">
                                        <option value="0">拒绝</option>
                                        <option value="1" selected>通过</option>
                                    </select>
                                </td>
                                <td class="align" >
                                    授信金额：
                                </td>
                                <td><input readonly type="text" id="giveAmount" style="width:100%;border:1px solid #ccc"></td>
                                <td class="align">付款比例:</td>
                                <td style="width:258px;text-align: left;" id="paymentRatio"></td>
                            </tr>
                            <tr>
                                <td class="align">审批意见：</td>
                                <td colspan="5">
                                    <textarea name="" id="advice" cols="30" rows="3" disabled="disabled"></textarea>
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
<div id="MerMsg" style="display: none;padding:20px;">
    <form id="add_mer_form" method="post"  style="margin-bottom: 20px">
        <table class='inputGroup_v space_lg'>
            <tr>
                <td width='80'>商户名称：</td>
                <td>
                    <input class="inpText inpMax MerInput" name="merchantName" id="merchantName" readonly/>
                </td>
                <td class="v-top">商户地址：</td>
                <td>
                    <input type="text" class="inpText inpMax MerInput"  name="merPlace" value="" readonly width="90%">
                </td>
            </tr>
            <tr>
                <td class="v-top">详细地址：</td>
                <td colspan="3">
                    <input class="inpText inpMax MerInput" readonly type='text' style='width:100%;margin-left: 0' name="address" id="address"  datatype="*" nullmsg="请输入地址！" maxlength="120"/>
                </td>
            </tr>
            <tr>
                <td>商户所有人：</td>
                <td><input class="inpText inpMax MerInput" name="merchantHolder" id="merchantHolder" readonly/></td>
                <td>职位：</td>
                <td><input class="inpText inpMax MerInput" name="position" id="position" readonly /></td>
            </tr>
            <tr>
                <td>开户行：</td>
                <td><input class="inpText inpMax MerInput"  name="accountBank" id="accountBank" readonly/></td>
                <td>对公账户：</td>
                <td><input class="inpText inpMax MerInput" name="publicAccount" id="publicAccount" readonly/></td>
            </tr>
            <tr>
                <td>负责人：</td>
                <td><input class="inpText inpMax MerInput" name="principal" id="principal" maxlength="20" readonly/></td>
                <td>负责人职位：</td>
                <td><input class="inpText inpMax MerInput" name="principalPosition" id="principalPosition" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>负责人电话：</td>
                <td><input class="inpText inpMax MerInput" name="principalTel" id="principalTel" maxlength="20"  readonly/></td>
                <td>渠道关键人：</td>
                <td><input class="inpText inpMax MerInput" name="channelPerson" id="channelPerson" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>关键人电话：</td>
                <td><input class="inpText inpMax MerInput" name="personTel" id="personTel" maxlength="20"  readonly/></td>
                <td>商户规模：</td>
                <td><input class="inpText inpMax MerInput" name="merchantScale" id="merchantScale" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>设计师数量：</td>
                <td><input class="inpText inpMax MerInput" name="designerCount" id="designerCount" maxlength="20"  readonly/></td>
                <td>业务人员数量：</td>
                <td><input class="inpText inpMax MerInput" name="servicerCount" id="servicerCount" maxlength="20"  readonly/></td>

            </tr>
            <tr>
                <td>客户级别：</td>
                <td>
                    <select class="inpText inpMax MerInput" name="level" id="level" style="width:154px;" disabled>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </td>
                <td>月客单价：</td>
                <td><input class="inpText inpMax MerInput" name="price" id="price" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>月成交单数：</td>
                <td><input class="inpText inpMax MerInput" name="turnoverCount" id="turnoverCount" maxlength="20"  readonly/></td>
                <td>获客渠道：</td>
                <td><input class="inpText inpMax MerInput" name="channel" id="channel" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>团队：</td>
                <td><input class="inpText inpMax MerInput" name="team" id="team" maxlength="20"  readonly/></td>
                <td>部门：</td>
                <td><input class="inpText inpMax MerInput" name="department" id="department" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>客户单价上限：</td>
                <td><input class="inpText inpMax MerInput" name="department" id="cusPriceMax" maxlength="20"  readonly/></td>
            </tr>
            <%--<tr>
                <td>分公司数：</td>
                <td><input class="inpText inpMax MerInput" name="branchCount" id="branchCount" maxlength="20"  readonly/></td>
                <td>所属公海：</td>
                <td><input class="inpText inpMax MerInput" name="highSeas" id="highSeas" maxlength="20"  readonly/></td>
            </tr>
            <tr>
                <td>所属部门：</td>
                <td><input class="inpText inpMax MerInput" name="personDepartment" id="personDepartment" maxlength="20"  readonly/></td>
                <td>行业：</td>
                <td><input class="inpText inpMax MerInput" name="industry" id="industry" maxlength="20"  readonly/></td>
            </tr>--%>
        </table>
    </form>
</div>
<%--风险评估--%>
<input type="hidden" id="cardNum" value="">
<div id="auditFinds" style="display: none">
    <div class="block_hd" style="margin-bottom: 10px; text-align: left">
        <s class="ico icon-file-text-alt"></s><span class="bl_tit" style="font-size: 18px;margin-left:6px;">风险评估结果</span>
    </div>
    <p>审核结果:<span id="checkResult">通过</span></p>
    <%--规则集--%>
    <div class="rules">
        <p>规则集:<span id="rulesResult">通过</span></p>
        <table class="findsTable">
            <thead>
            <tr>
                <th>规则集名称</th>
                <th>规则名称</th>
                <th>结果</th>
            </tr>
            </thead>
            <tbody>
            <%--身份核验--%>
            <tr>
                <td rowspan="5">身份核验</td>
                <td>活体人脸识别</td>
                <td id="htrl"></td>
            </tr>
            <tr>
                <td>四要素识别</td>
                <td id="siys"></td>
            </tr>
            <tr>
                <td>手机号实名验证</td>
                <td id="phoneReal"></td>
            </tr>
            <tr>
                <td>手机号使用时间</td>
                <td id="phoneTime"></td>
            </tr>
            <tr>
                <td>手机GPS定位</td>
                <td id="phoneGps"></td>
            </tr>
            <%--鹏元信息--%>
            <tr>
                <td rowspan="8">鹏元信息</td>
                <td>鹏元身份信息</td>
                <td id="pyIfo"></td>
            </tr>
            <tr>
                <td>鹏元教育信息</td>
                <td id="pyEdu"></td>
            </tr>
            <tr>
                <td>鹏元催欠公告信息条数</td>
                <td id="pyDistress"></td>
            </tr>
            <tr>
                <td>鹏元税务行政执法信息条数</td>
                <td id="pyTax"></td>
            </tr>
            <tr>
                <td>鹏元司法案例信息条数</td>
                <td id="pyJudcase"></td>
            </tr>
            <tr>
                <td>鹏元司法失信信息条数</td>
                <td id="pyJuddishonesty"></td>
            </tr>
            <tr>
                <td>鹏元司法执行信息条数</td>
                <td id="pyJudenfor"></td>
            </tr>
            <tr>
                <td>鹏元网贷逾期信息条数</td>
                <td id="pyNetloanover"></td>
            </tr>
            <%--<tr>--%>
            <%--<td>鹏元银行账户名及流水核实</td>--%>
            <%--<td>无</td>--%>
            <%--</tr>--%>
            <%--同盾外部--%>
            <tr>
                <td rowspan="4">同盾外部</td>
                <td>同盾黑名单命中数量</td>
                <td id="tdBhit"></td>
            </tr>
            <tr>
                <td>同盾借款设备代理识别</td>
                <td id="tdLoanproxy"></td>
            </tr>
            <tr>
                <td>同盾借款设备作弊工具识别</td>
                <td id="tdLoantool"></td>
            </tr>
            <tr>
                <td>同盾外部贷款平台个数</td>
                <td id="tdExtplatform"></td>
            </tr>

            <%--同盾身份证--%>
            <tr>
                <td rowspan="4">同盾身份证</td>
                <%--<td>同盾身份证命中法院失信名单</td>--%>
                <%--<td>否</td>--%>
                <td>同盾身份证命中法院犯罪通缉名单</td>
                <td id="tdidCriminal"></td>
            </tr>
            <tr>
                <td>同盾身份证命中高风险关注名单</td>
                <td id="tdidRisk"></td>
            </tr>
            <tr>
                <td>同盾身份证命中欠税名单</td>
                <td id="tdidTaxes"></td>
            </tr>
            <tr>
                <td>同盾身份证命中信贷逾期名单</td>
                <td id="tdidCredit"></td>
            </tr>
            <%--<tr>--%>
            <%--<td>同盾身份证命中法院执行名单</td>--%>
            <%--<td>否</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>同盾身份证命中欠款公司法人代表名单</td>--%>
            <%--<td>否</td>--%>
            <%--</tr>--%>
            <%--同盾手机号--%>
            <tr>
                <td rowspan="14">同盾手机号</td>
                <td>同盾手机号命中高风险关注名单</td>
                <td id="tdphoneRisk"></td>
            </tr>
            <tr>
                <td>同盾手机号命中欠款公司法人代表名单</td>
                <td id="tdphoneArrears"></td>
            </tr>
            <tr>
                <td>同盾手机号命中通讯小号库</td>
                <td id="tdphoneCommunication"></td>
            </tr>
            <tr>
                <td>同盾手机号命中信贷逾期名单</td>
                <td id="tdphoneCredit"></td>
            </tr>
            <tr>
                <td>同盾手机号命中虚假号码库</td>
                <td id="tdphoneFalsenum"></td>
            </tr>
            <tr>
                <td>同盾手机号命中诈骗号码库</td>
                <td id="tdphoneFraudnum"></td>
            </tr>
            <tr>
                <td>同盾联系人手机号命中高风险关注名单</td>
                <td id="tdcontphoneRisk"></td>
            </tr>
            <tr>
                <td>同盾联系人手机号命中通讯小号库</td>
                <td id="tdcontphoneCommunication"></td>
            </tr>
            <tr>
                <td>同盾联系人手机号命中信贷逾期名单</td>
                <td id="tdcontphoneCredit"></td>
            </tr>
            <tr>
                <td>同盾联系人手机号命中虚假号码库</td>
                <td id="tdcontphoneFalsenum"></td>
            </tr>
            <tr>
                <td>同盾联系人手机号命中诈骗号码库</td>
                <td id="tdcontphoneFraudnum"></td>
            </tr>
            <tr>
                <td>同盾单固命中虚假号码库</td>
                <td id="tddgFalsenum"></td>
            </tr>
            <tr>
                <td>同盾单固命中诈骗号码库</td>
                <td id="tddgFraudnum"></td>
            </tr>
            <tr>
                <td>同盾单固命中中介号码库</td>
                <td id="tddgAgencynum"></td>
            </tr>

            <%--上海资信--%>
            <tr>
                <td rowspan="5">上海资信</td>
                <td>上海资信查询所有贷款笔数</td>
                <td id="shzxLoan"></td>
            </tr>
            <tr>
                <td>上海资信查询未结清贷款数量</td>
                <td id="shzxOutloan"></td>
            </tr>
            <tr>
                <td>上海咨询查询逾期数量</td>
                <td id="shzxOverdue"></td>
            </tr>
            <tr>
                <td>上海资信查询共享授信额度总和</td>
                <td id="shzxLinecredit"></td>
            </tr>
            <tr>
                <td>上海资信查询月供</td>
                <td id="shzxMonth"></td>
            </tr>

            <%--公积金、社保--%>
            <tr>
                <td rowspan="2">公积金、社保</td>
                <td>公积金授权</td>
                <td id="accumulation"></td>
            </tr>
            <tr>
                <td>社保授权</td>
                <td id="social"></td>
            </tr>

            <%--支付宝、淘宝交易信息--%>
            <tr>
                <td rowspan="2">支付宝</td>
                <td>支付宝实名验证</td>
                <td id="alipayRealname"></td>
            </tr>
            <%--<tr>--%>
            <%--<td>支付宝交易信息</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <tr>
                <td>芝麻分</td>
                <td id="sesameSeeds"></td>
            </tr>
            <%--<tr>--%>
            <%--<td>淘宝授权</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>淘宝收货地址及收货联系人手机号</td>--%>
            <%--<td>2000</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>支付水、电、宽带等费用交易记录</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>充值详情</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>

            <%--&lt;%&ndash;手机号信息&ndash;%&gt;--%>
            <%--<tr>--%>
            <%--<td rowspan="6">手机号信息</td>--%>
            <%--<td>手机号实名验证</td>--%>
            <%--<td>一致</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机号使用时间</td>--%>
            <%--<td>3年</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机号运营商通话记录</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>来往通话号码标识（贷款公司、信用卡机构等等）</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机号注册同行贷款APP个数</td>--%>
            <%--<td>0</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>身份证号注册关联手机号码</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>

            <%--&lt;%&ndash;信用卡授权&ndash;%&gt;--%>
            <%--<tr>--%>
            <%--<td rowspan="2">信用卡授权</td>--%>
            <%--<td>信用卡实名验证</td>--%>
            <%--<td>是</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>信用卡近半年交易记录</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>

            <%--&lt;%&ndash;设备信息&ndash;%&gt;--%>
            <%--<tr>--%>
            <%--<td rowspan="9">设备信息</td>--%>
            <%--<td>手机通讯录</td>--%>
            <%--<td>是</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>通讯录匹配同行贷款公司个数</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机短信命中逾期、催缴、还款等高风险字段</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机安装同行APP软件个数</td>--%>
            <%--<td>0</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机安装炒股软件个数</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机近3个月访问APP次数</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机近3个月浏览网址记录</td>--%>
            <%--<td>0</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机设备ID命中黑名单</td>--%>
            <%--<td>否</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>手机申请GPS定位</td>--%>
            <%--<td></td>--%>
            <%--</tr>--%>
            </tbody>
        </table>
    </div>
    <%--评分卡--%>
    <div class="scoreCard">
        <p style="width: 800px">评分卡:<span id="scoreResult">通过</span><span style="float: right;margin-right: 50px">总得分:<i id="totalscore"></i></span></p>
        <table class="findsTable">
            <thead>
            <tr>
                <th>规则集名称</th>
                <th>规则名称</th>
                <th>得分</th>
            </tr>
            </thead>
            <tbody id="scoreCardBody">
            <%--基本情况--%>
            <tr>
                <td rowspan="5">基本情况</td>
                <td>性别</td>
                <td id="sex"></td>
            </tr>
            <tr>
                <td>年龄</td>
                <td id="age"></td>
            </tr>
            <tr>
                <td>婚姻状况</td>
                <td id="marry"></td>
            </tr>
            <tr>
                <td>户籍所在区域</td>
                <td id="hjszqy"></td>
            </tr>
            <tr>
                <td>当前居住情况</td>
                <td id="dqjzqk"></td>
            </tr>
            <%--工作信息--%>
            <tr>
                <td rowspan="5">工作信息</td>
                <td>现单位工作年限</td>
                <td id="xdwgznx"></td>
            </tr>
            <tr>
                <td>单位性质</td>
                <td id="dwxz"></td>
            </tr>
            <tr>
                <td>是否缴纳公积金及社保</td>
                <td id="sfjngjjjsb"></td>
            </tr>
            <tr>
                <td>职级</td>
                <td id="rank"></td>
            </tr>
            <tr>
                <td>工资发放形式</td>
                <td id="gzffxs"></td>
            </tr>
            <%--资产情况--%>
            <tr>
                <td rowspan="2">资产情况</td>
                <td>购买方式</td>
                <td id="gmfs"></td>
            </tr>
            <tr>
                <td>已按揭</td>
                <td id="yaj"></td>
            </tr>
            <%--外部数据--%>
            <tr>
                <td rowspan="11">外部数据</td>
                <td>学历（APP自填）</td>
                <td id="pyxl"></td>
            </tr>
            <tr>
                <td>手机号码实名验证</td>
                <td id="sjhmsmsfyz"></td>
            </tr>
            <tr>
                <td>APP安装同行软件</td>
                <td id="azthrj"></td>
            </tr>
            <tr>
                <td>上海资信未结清贷款数量</td>
                <td id="shzxwjqdksl"></td>
            </tr>
            <tr>
                <td>同盾外部贷款平台个数</td>
                <td id="tdwbdkptgs"></td>
            </tr>
            <tr>
                <td>授权公积金</td>
                <td id="sqgjj"></td>
            </tr>
            <tr>
                <td>授权通讯录</td>
                <td id="sqtxl"></td>
            </tr>
            <tr>
                <td>授权通话记录</td>
                <td id="sqthjl"></td>
            </tr>
            <tr>
                <td>芝麻分</td>
                <td id="zmf"></td>
            </tr>
            <tr>
                <td>GPS定位</td>
                <td id="dw"></td>
            </tr>
            <tr>
                <td>手机号使用时长</td>
                <td id="sjhsysc"></td>
            </tr>
            <%--业务埋点--%>
            <tr>
                <td rowspan="4">业务埋点</td>
                <td>点击注册/利息细则</td>
                <td id="djzc"></td>
            </tr>
            <tr>
                <td>工作单位名称修改</td>
                <td id="gzdwmcxg"></td>
            </tr>
            <tr>
                <td>工作单位电话修改</td>
                <td id="gzdwdhxg"></td>
            </tr>
            <tr>
                <td>联系人电话修改</td>
                <td id="lxrdhxg"></td>
            </tr>
            <%--组合--%>
            <tr>
                <td>组合</td>
                <td>性别+学历+单位+城市</td>
                <td id="xbxldwcs"></td>
            </tr>
            <%--拒绝规则--%>
            <tr>
                <td rowspan="6">拒绝规则</td>
                <td>APP安装同行软件≥2 & 上海资信未结清贷款数量≥2 & GPS定位与工作地or居住地均不相符</td>
                <td id="first_rule"></td>
            </tr>
            <tr>
                <td>APP安装同行软件≥2 & 上海资信未结清贷款数量≥2 & 同盾外部贷款平台个数≥10</td>
                <td id="second_rule"></td>
            </tr>
            <tr>
                <td>芝麻分500分以下 & 同盾外部贷款平台个数≥10</td>
                <td id="third_rule"></td>
            </tr>
            <tr>
                <td>芝麻分500分以下 &  GPS定位与工作地or居住地均不相符</td>
                <td id="fourth_rule"></td>
            </tr>
            <tr>
                <td>芝麻分500分以下 &  手机号使用不足1月</td>
                <td id="fifth_rule"></td>
            </tr>
            <tr>
                <td>工作单位名称修改≥2次&工作单位电话修改≥2次&联系人电话修改≥2次</td>
                <td id="sixth_rule"></td>
            </tr>
            </tbody>
        </table>
    </div>
    <%--&lt;%&ndash;黑名单&ndash;%&gt;--%>
    <%--<div class="blackList">--%>
    <%--<p>黑名单</p>--%>
    <%--<table class="findsTable">--%>
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th>黑名单名称</th>--%>
    <%--<th>类型</th>--%>
    <%--<th>名称</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>
    <%--<tr>--%>
    <%--<td colspan="3" style="text-align: center">未命中</td>--%>
    <%--</tr>--%>
    <%--</tbody>--%>
    <%--</table>--%>
    <%--</div>--%>
    <%--&lt;%&ndash;白名单&ndash;%&gt;--%>
    <%--<div class="whiteList">--%>
    <%--<p>白名单</p>--%>
    <%--<table class="findsTable">--%>
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th>白名单名称</th>--%>
    <%--<th>类型</th>--%>
    <%--<th>名称</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>
    <%--<tr>--%>
    <%--<td colspan="3" style="text-align: center">未命中</td>--%>
    <%--</tr>--%>
    <%--</tbody>--%>
    <%--</table>--%>
    <%--</div>--%>
</div>
<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>
</body>
</html>