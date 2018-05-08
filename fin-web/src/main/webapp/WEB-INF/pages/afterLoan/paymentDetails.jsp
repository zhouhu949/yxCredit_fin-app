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
    <script src="${ctx}/resources/js/afterLoan/paymentDetails.js${version}"></script>
    <title>放款审核</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}

        .onlyMe input{margin:0;vertical-align:middle;}
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
        #divFrom td.telRecord{height: 37px;  text-align: left;  padding-left: 27px!important;}
        /* #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#recordList5 td,#recordList6 td,#answerBody td,#answerBody1 td{border:none!important;border-right:1px solid #ccc}
         #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#recordList5 tr,#recordList6 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}*/
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
                <%--<li><label class="lf">创建时间</label>--%>
                    <%--<label>--%>
                        <%--<input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>--%>
                        <%--<span class="date-icon"><i class="icon-calendar"></i></span>--%>
                    <%--</label>--%>
                <%--</li><span class="line-cut">--</span>--%>
                <%--<li style="width:200px;">--%>
                    <%--<label>--%>
                        <%--<input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>--%>
                        <%--<span class="date-icon"><i class="icon-calendar"></i></span>--%>
                    <%--</label>--%>
                <%--</li>--%>
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
                        <th>手机号码</th>
                        <th>本期应还时间</th>
                        <th>总期数</th>
                        <th>当前期数</th>
                        <th>本期应还金额</th>
                        <th>本期状态</th>
                        <th>提前结清状态</th>
                        <th>扣款状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

        <%--//对应的贷款订单--%>
        <div class="Manager_style" id="cus_order" style="display: none">
            <div class="order_list">
                <table style="cursor:pointer;" id="withdrawal_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>扣款时间</th>
                        <th>扣款流水号</th>
                        <th>扣款金额</th>
                        <th>扣款返回码</th>
                        <th>扣款状态</th>
                        <th>扣款描述</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>