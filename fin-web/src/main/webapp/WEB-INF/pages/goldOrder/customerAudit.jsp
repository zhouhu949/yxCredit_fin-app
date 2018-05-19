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
    <script src="${ctx}/resources/js/goldOrder/customerAudit.js${version}"></script>
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
            background: #05c1bc;
            color: #fff;
            border-radius: 2px;
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
        .addButton{margin-right:20px;}
        .audioShow{
            position:absolute;
            z-index:100;
            opacity:0;
            filter:alpha(opacity=0);
            height:30px;
            width:48px;
            readonly:true;
            right: 26px;
            top: 13px;
        }
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
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="leftStatus" value="${leftStatus}"/>
    <input type="hidden" name="userId" id="userId" value="${userId}"/>
    <input type="hidden" name="handName" id="handName" value="${nickName}"/>
    <input type="hidden" name="taskNodeId" id="taskNodeId" value="${param.taskNodeId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="customerName" type="text" class="text_add"/>
                    </label>
                </li>
                <%--<li style="width:150px">
                    <label class="lf">状态</label>
                    <label>
                        <select name="condition" size="1" id="isLock" style="width:100px;    margin-left: 10px;">
                            <option value="">请选择</option>
                            <option value="1">待申请</option>
                            <option value="2">审核中</option>
                            <option value="3">待签约</option>
                            <option value="4">待放款</option>
                            <option value="5">待还款</option>
                            <option value="6">已结清</option>
                            <option value="7">已取消</option>
                            <option value="8">申请失败</option>
                            <option value="9">已放弃</option>
                        </select>
                    </label>
                </li>--%>

               <%-- <li><label class="lf">身份证号</label>
                    <label>
                        <input name="card" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">手机号码</label>
                    <label>
                        <input name="tel" type="text" class="text_add"/>
                    </label>
                </li>--%>
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
                        <th>身份证号</th>
                        <th>手机号码</th>
                        <th>产品名称</th>
                        <th>申请金额</th>
                        <th>申请期限</th>
                        <th>申请时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>
<script>
    function setImagePreview(me){
        //var docObj=document.getElementById("file");
        var docObj=me;
        //var imgObjPreview=document.getElementById("tx1");
        var imgObjPreview=me.nextElementSibling;
        if(docObj.files && docObj.files[0]){
            //火狐下，直接设img属性
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
            $(me).parent().append("<span class='closeImg' onclick='closeDelete(this)'>×</span>");
            var html="";
            html+='<div><input type="hidden" class="imgHidden">';
            html+='<form action="" enctype="multipart/form-data">';
            html+='<input type="hidden" name="id" value=""/>';
            html+='<div class="imagediv">';
            html+='<input type="file"  name="pic" class="picShow" onchange="setImagePreview(this)"/>';
            html+='<img  class="addMaterial" src="../resources/images/photoadd.png" />';
            html+='</div>';
            html+='</form>';
            html+='<input type="hidden" class="imgHidden" value="1">';
            html+='<input type="hidden" class="imgHidden" value="39"></div>';
            $(me).parent().parent().parent().parent().append(html);
            $("input[name='orderId']").val($("#orderId").val());//order
            $("input[name='customerId']").val($("#customerId").val());
            $(me).parent().parent().ajaxSubmit({
                type: "POST",
                url: "uploadFile",
                success: function (data) {
                    data = eval('('+data+')');
                    $(me).parent().parent().prev().val(data.data);
                    $(me).hide();
                    $(me).next("img").attr("onclick","imgShow(this)");
                    $(me).parent().parent().parent().addClass("getFanQiZha");
                    layer.msg(data.msg,{time:1000});
                },
                error: function (XMLHttpRequest, textStatus, thrownError) {
                }
            });
        }else{

        }
        return true;
    }
    function closeDelete(me){
        var id=$(me).parent().parent().prev().val();
        console.log(id);
        Comm.ajaxPost("customerAudit/deleteFile","fileName="+id,function(data){
            layer.msg("删除成功",{time:1000},function () {
                $(me).parent().parent().parent().remove();
            });
        });
    }
    var leftStatus = $("input[name='leftStatus']").val();
    leftStatus="in";
   var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
//        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    laydate(beginTime);


</script>
</body>
</html>