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
    <script src="${ctx}/resources/js/customerManage/customerFinalAudit.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>
    <title>风控放款审核</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #divFrom input{border:none;background-color: #fff!important;text-align: left;}
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
        .answerTh th{text-align: center; font-weight: normal;border:none!important;}
        .answerTh thead{border:1px solid #ccc;border-top:none;background: #DFF1D9}
        #MerMsg tr td{
            text-align: right;
            padding-left:0.2em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
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
        #divFrom td{text-align:center}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .costomerRemark>div:first-child{display: inline-block;height: 56px;width: 102px;border-right: 1px solid #ccc;  text-align: center;  line-height: 50px;}
        .costomerRemark>div:last-child{display: inline-block;width: 90%;margin-left: 6px;}
        textarea{resize: none;}
        #showNewImg ul li,#paikeContainer ul li,#fanqzContainer ul li,#cszl ul li,#zszl ul li{
            display: inline-block;
            width:17%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
        }
        #showNewImg1 ul li,#paikeContainer ul li,#fanqzContainer ul li,#cszl ul li,#zszl ul li{
            display: inline-block;
            width:17%;
            border:1px solid #ddd;
            text-align: center;
            margin:.2em 0;
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

                <li><label class="lf">手机号码</label>
                    <label>
                        <input name="tel" type="text" class="text_add"/>
                    </label>
                </li>
                <%--<li >
                    <label class="lf">审核状态</label>
                    <label>
                        <select name="condition" size="1" id="isLock" style="width:163px;margin-left: 10px;height: 28px" >
                            <option value="">请选择</option>
                            <option value="1">待放款</option>
                            <option value="2">放款中</option>
                            <option value="3">已放款</option>
                        </select>
                    </label>
                </li>--%>
                <li><label class="lf">审核时间</label>
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
                        <th>订单编号</th>
                        <th>客户名称</th>
                        <th>身份证号</th>
                        <th>手机号码</th>
                        <th>产品名称</th>
                        <th>放款金额</th>
                        <th>申请期限</th>
                        <th>审核时间</th>
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
<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>

<%--选择放款方式弹出窗--%>
<div class="Manager_style" id="checkConfirmationLoanStyle" style="display: none">
    <div class="paddingBox" >
        <table class="nobor" style="margin: 23px 58px;">
            <tbody>
            <tr style="height: 50px;">
                <td>还款用户:</td>
                <td colspan="5" style="text-align: left;"> <input type="text" id="payBackUser" style="width: 170px;"/></td>
            </tr>
            <tr style="height: 5px"></tr>
            <tr>
                <td>还款卡号:</td>
                <td colspan="8" style="text-align: left;"> <input type="text" id="payBackCard"  style="width: 170px;"/></td>
            </tr>
            </tbody></table>
    </div>
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

</script>
</body>
</html>