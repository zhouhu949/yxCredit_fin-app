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
    <script src="${ctx}/resources/js/customerManage/customerAntifraud.js${version}"></script>
    <title>客户反欺诈</title>
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
            height:180px;
            background-color:white;
            text-align: center;
            float: left;
            position: relative;
            margin-right: 1em;
            left:22%;
            padding-top: 1em;
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
            top: 0px;
            right: -10px;
            width: 20px;
            height: 20px;
            z-index: 999;
            font-size: 20px;
            border-radius: 50%;
            background: #000;
            color: #fff;
            line-height: 20px;
            cursor: pointer;
        }
        #firstCredit,#passRemark,#cheatRemark{width:95%;float:left;margin-left:0px;margin-left:4px;}
        #cheatRemark{width: 99%;}
        .telRecord{height: 37px;  text-align: left;  padding-left: 35px!important;}
        #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#answerBody td,#answerBody1 td{border:none!important;border-right:1px solid #ccc}
        #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}
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
        #imageCard .imgShow,#cheatImg .imgShow,#phoneImg .imgShow,#showPhoneImg .imgShow,#showCheatImg .imgShow{width:40px;height: 100%;float:left;margin-right:1em;}
        #imageCard .imgShowTd{padding-left: 1em;text-align:left}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        .align{width:110px;}
        #divFrom td{text-align: center;}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
        #divFrom .telRecord{text-align: left;font-weight: bold;}
        #antifraudRemark,#photoRemark{width: 96%; margin-left: -4px;resize: none;}
        #showNewImg{  text-align: left;  }
        #showNewImg ul li,#paikeContainer ul li,#fanqzContainer ul li,#uploadCheatImg ul li,#uploadPhoneImg ul li{  display: inline-block;  width:25%;  border:1px solid #ddd;  text-align: center;  margin:.2em 0; }
        #uploadCheatImg ul li,#uploadPhoneImg ul li{
            display: inline-block;  width:24%;  border:1px solid #ddd;  text-align: center;  margin:.2em 0;
        }
        #showPhoneImg,#showCheatImg{
            text-align: left;
            border:1px solid #ddd;
        }
        #showPhoneImg ul li,#showCheatImg ul li{
            display: inline-block;  width:25%;  border:1px solid #ddd;  text-align: center;  margin:.2em 0;
        }
        #showNewImg>ul>li>div{
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="userId" value="${userId}"/>
    <input type="hidden" name="handName" value="${nickName}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="customerName" type="text" class="text_add"/>
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
                    <button id="btn_search" type="button" class="btn btn-primary">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary">查询重置</button>
                </li>
                <%-- <li style="width:155px;padding-top: 10px;">
                     <span class="onlyMe"><input type="checkbox" id="onlyMe"/>只看自己的客户</span>
                 </li>--%>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="order_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单编号</th>
                        <th>客户名称</th>
                        <th>手机号码</th>
                        <th>证件号码</th>
                        <th>申请时间</th>
                        <th>下单归属地</th>
                        <th>申请产品</th>
                        <th>申请金额</th>
                        <th>分期期数</th>
                        <th>当前环节</th>
                        <th>反欺诈状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
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
                            <tbody>
                            <tr>
                                <td class="align">产品类型:</td>
                                <td><input type="text" name="proType" id="proType" readonly></td>
                                <td class="align">当前审批人：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proApproval" id="proApproval" readonly></td>
                                <td class="align">贷款号：</td>
                                <td><input type="text" class="inpText info-inpW1" name="periods" id="proLoan" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">状态：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proState"  id="proState" readonly></td>
                                <td class="align">手机号码：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proTel"  id="proTel" readonly></td>
                                <td class="align">申请时间：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proApplytime"  id="proApplytime" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">申请金额：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proSum"  id="proSum" readonly></td>
                                <td class="align">申请期限：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proDeadline"  id="proDeadline" readonly></td>
                                <td class="align">渠道来源：</td>
                                <td><input type="text" class="inpText info-inpW1" name="proSource"  id="proSource" readonly></td>
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
                                <td><input type="text" name="applyName" id="applyName" readonly></td>
                                <td class="align">身份证号码:</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyIdcard" id="applyIdcard" readonly></td>
                                <td class="align">婚姻状况:</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyMerry" id="applyMerry" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">户籍：</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyCensus"  id="applyCensus" readonly></td>
                                <td class="align">当前居住情况：</td>
                                <td><input type="text" class="inpText info-inpW1" name="applyAddr"  id="houseproperty" readonly></td>
                                <td>学历</td>
                                <td id="education" style="color:rgb(147, 145, 146);"></td>
                            </tr>
                            <tr style="height:37px">
                                <td class="align">当前住址：</td>
                                <td colspan="8"><input type="text" class="inpText info-inpW1" name="applyAddr"  id="applyAddr" readonly style="width:90%"></td>
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
                                <td class="align">购买方式：</td>
                                <td><input type="text" class="inpText info-inpW1" name="housePurcha" id="housePurcha" readonly></td>
                            </tr>
                            <tr>
                                <td class="align">按揭银行：</td>
                                <td><input type="text" class="inpText info-inpW1" name="houseBank"  id="houseBank" readonly></td>
                                <td class="align">房产地址：</td>
                                <td ><input type="text" class="inpText info-inpW1" name="houseAddr"  id="houseAddr" readonly style="width:96%"></td>
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
                                    <label  style="margin-right:20px;float: right">
                                        <input readonly type="radio" name="ListIsReasonable" id="ListIsReasonable_is" value="1" checked>是
                                        <input readonly type="radio" name="ListIsReasonable" id="ListIsReasonable_no" value="0">否
                                    </label>
                                    <label style="margin-right:20px;float: right">
                                        装修清单是否合理
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td>装修公司：</td>
                                <td>
                                    <input type="text" name="ReasonableInput_fitment" style="text-align: center;cursor: pointer" readonly onclick="checkMerchant(this)">
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
                    <%--拍客资料--%>
                    <div class="paperBlockfree" style="overflow:hidden;">
                        <div class="block_hd" style="text-align: left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">拍客资料</span>
                        </div>
                        <%--<div id="uploadPhoneImg" style="height:200px;margin-top:3em;display: none;border:1px solid #ddd;text-align: left;overflow: auto">
                            <ul>
                                <li><div class="PaiKe"  style="overflow: hidden">
                                        <input type="hidden" class="imgHidden">&lt;%&ndash;删除的图片名称&ndash;%&gt;
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
                                    </div></li>
                            </ul>
                        </div>--%>
                        <div id="showPhoneImg" style="display: none;">
                            <ul></ul>
                        </div>
                    </div>
                    <%--拍客备注--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">拍客备注</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td>状态</td>
                                <td>通过</td>
                                <td class="align">备注：</td>
                                <td><textarea type="text" id="photoRemark"></textarea></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--反欺诈资料--%>
                    <div class="paperBlockfree" style="overflow: hidden;">
                        <div class="block_hd" style="text-align: left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">反欺诈资料</span>
                        </div>
                        <div id="uploadCheatImg" style="height:200px;margin-top:3em;display: none;border:1px solid #ddd;text-align: left;overflow: auto">
                                <ul>
                                    <form action="" enctype="multipart/form-data">
                                        <input type="hidden" name="orderId">
                                        <input type="hidden" name="customerId">
                                        <li>
                                            <div class="QiZha" style="overflow: hidden">
                                                <div class="imagediv">
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview(this)"/>
                                                    <img class="addMaterial" src="../resources/images/photoadd.png" />
                                                </div>
                                            </div>
                                        </li>
                                    </form>
                                </ul>
                            </div>
                        <div id="showCheatImg" style="display: none;">
                            <ul></ul>
                        </div>
                    </div>
                    <%--反欺诈备注--%>
                    <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">反欺诈备注</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td>状态</td>
                                <td>
                                    <select id="antifraudState">
                                        <option value="1">通过</option>
                                        <option value="2">拒绝</option>
                                    </select>
                                </td>
                                <td class="align">备注：</td>
                                <td><textarea type="text"  id="antifraudRemark"></textarea></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <button type="button" class="btn btn-primary" id="savaBtn" onclick="saveBtn()">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>
<script>
    function setImagePreview(me){
        var docObj=me;
        var imgObjPreview=me.nextElementSibling;
        if(docObj.files && docObj.files[0]){
            //火狐下，直接设img属性
            console.log(docObj.files[0])
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
            $(me).parent().append("<span class='closeImg' onclick='closeDelete(this)'>×</span>");
            var html="";
            html+='<li><div style="overflow: hidden"><input type="hidden" class="imgHidden">';
            //html+='<form action="" enctype="multipart/form-data">';
            html+='<div class="imagediv">';
            html+='<input type="file"  name="pic" class="picShow" onchange="setImagePreview(this)"/>';
            html+='<img  class="addMaterial" src="../resources/images/photoadd.png" />';
            html+='</div>';
            html+='</div></li>'
            $("#uploadCheatImg form").append(html);
        }
        return true;
    }
    function closeDelete(me){
        var id=$(me).parent().parent().prev().val();
        $(me).parent().parent().parent().remove();
    }
</script>
</body>
</html>