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
    <script src="${ctx}/resources/js/customerManage/customerFinalPage.js${version}"></script>
    <title>客户审核</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
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
        .align{width:110px;}
        #divFrom td{text-align:center}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
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
        .ly_positionBar .position {
            padding-left: 30px;
            font-size: 16px;
            font-style: italic;
        }
        #answerList table td{text-align: left}
        #answerList table tr{height: 60px;border:1px solid #4a4f56;}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="orderId" id="orderId" value="${order.orderId}"/>
    <input type="hidden" name="orderState" id="orderState" value="${order.orderState}"/>
    <input type="hidden" name="customerId" id="customerId" value="${order.customerId}"/>

    <input type="hidden" name="userId" id="userId" value="${order.userId}"/>
    <input type="hidden" name="state" id="state" value="${order.state}"/>
    <input type="hidden" name="salesmanId" id="salesmanId" value="${order.empId}"/>
    <input type="hidden" name="orderId" id="taskNodeId" value=""/>
    <div class="commonManager" id="commonManager">

        <%--查看--%>
        <div id="container" class="of-auto_H" style="padding:20px;">

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
                                <td class="align" width="10%">产品名称:</td>
                                <td id="productName"width="23%">${order.productName}</td>
                                <td class="align" width="10%">产品利率:</td>
                                <td  width="23%" id="rate">${order.rate}</td>
                            </tr>
                            <tr>
                                <td class="align">申请金额:</td>
                                <td id="applayMoney">${order.applayMoney}</td>
                                <td class="align">申请期限:</td>
                                <td id="periods">${order.periods}</td>
                                <td class="align">申请时间:</td>
                                <td id="applayTime"></td>
                            </tr>
                            <tr>
                                <td class="align">开户银行:</td>
                                <td id="bankName">${order.bankName}</td>
                                <td class="align">开户支行:</td>
                                <td id="bankSubbranch">${order.bankSubbranch}</td>
                                <td class="align">银行卡号:</td>
                                <td id="cardNumber">${order.cardNumber}</td>
                            </tr>

                            </tbody>
                        </table>


                    </div>
                    <%--申请人信息--%>
                        <div class="paperBlockfree" style="margin-top:20px">
                        <div class="block_hd" style="float:left;" onclick="shrink(this)">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">客户信息</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="align" width="10%">姓&emsp;&emsp;名:</td>
                                <td id="personName" width="23%">${customer.personName}</td>
                                <td class="align" width="10%">性&emsp;&emsp;别:</td>
                                <td width="23%" id="sexName">${customer.sexName}</td>
                                <td class="align" width="10%">年&emsp;&emsp;龄:</td>
                                <td id="age" width="23%">${customer.age}</td>
                            </tr>
                            <tr>
                                <td class="align">出生日期:</td>
                                <td id="birth">${customer.birth}</td>
                                <td class="align">手机号码:</td>
                                <td id="tel">${customer.tel}</td>
                                <td class="align">证件号码:</td>
                                <td id="card">${customer.card}</td>
                            </tr>
                            <tr>
                                <td class="align">婚姻状况:</td>
                                <td id="marital">${customer.maritalStatus}</td>
                                <td class="align">子女状况:</td>
                                <td id="children">${customer.childrenStatus}</td>
                                <td class="align">所属总包商:</td>
                                <td id="contractorName">${customer.contractorName}</td>
                            </tr>
                            <tr>
                                <td>户籍居住地址：</td>
                                <td id="cardRegisterAddress" colspan="5">${customer.companyAddress}</td>

                            </tr>
                            <tr>

                                <td>工作居住地址：</td>
                                <td id="residenceAddress" colspan="5">${customer.nowaddress}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                        <%--联系人--%>
                        <div class="paperBlockfree">
                            <div class="block_hd" style="float:left;">
                                <s class="ico icon-file-text-alt"></s><span class="bl_tit">联系人信息</span>
                            </div>
                            <table class="tb_info" id="relation" style="font-size:12px;">

                            </table>

                        </div>

                        <%--风控审核信息--%>
                        <div class="paperBlockfree">
                            <div class="block_hd" style="float:left;">
                                <s class="ico icon-file-text-alt"></s><span class="bl_tit">风控审核信息</span>
                            </div>
                            <table class="tb_info" id="apiResult" style="font-size:12px;">

                            </table>

                        </div>

                      <%--  <c:if test="${order.orderState==3}">--%>
                            <%--  人工审核信息--%>
                            <div class="paperBlockfree" id="artificial" style="display: none">
                                <div class="block_hd" style="float:left;">
                                    <s class="ico icon-file-text-alt"></s><span class="bl_tit">人工审核信息</span>
                                </div>
                                <table class="tb_info" id="riskManagement" style="font-size:12px;">
                                    <tr>
                                        <td class="align" width="10%">审批金额:</td>
                                        <td id="amount" width="23%"></td>
                                        <td class="align" width="10%">审核结果:</td>
                                        <td id="operationResult" width="23%"></td>

                                    </tr>
                                    <tr>
                                        <td class="align" width="10%">审核人员:</td>
                                        <td id="empName" width="23%"></td>
                                        <td class="align" width="10%">审核时间:</td>
                                        <td id="operationTime" width="23%"></td>
                                    </tr>
                                    <tr>
                                        <td class="align" width="10%">审核意见:</td>
                                        <td id="description" width="23%" colspan="3"></td>
                                    </tr>
                                </table>
                            </div>

                        <%--</c:if>--%>

                       <%-- <c:if test="${order.orderState==5}">--%>

                            <%--放款审核信息--%>
                            <div class="paperBlockfree" id="Loan" style="display: none">
                                <div class="block_hd" style="float:left;">
                                    <s class="ico icon-file-text-alt"></s><span class="bl_tit">放款审核信息</span>
                                </div>
                                <table class="tb_info" id="loanAudit" style="font-size:12px;">
                                    <tr>
                                        <td class="align" width="10%">银行名称:</td>
                                        <td id="loanBankName" width="23%">${order.bankName}</td>
                                        <td class="align" width="10%">银行卡号:</td>
                                        <td id="loanCardNumber" width="23%">${order.cardNumber}</td>
                                        <td class="align" width="10%">放款金额:</td>
                                        <td id="loanAmount" width="23%"></td>
                                    </tr>
                                    <tr>
                                        <td class="align" width="10%">放款时间:</td>
                                        <td id="loanTime" width="23%" colspan="2"></td>
                                        <td class="align" width="10%">放款状态:</td>
                                        <td id="loanState" width="23%" colspan="2"></td>
                                    </tr>
                                </table>
                            </div>
                        <%--</c:if>--%>



                </div>
            </div>
        </div>
    </div>



    <div id="showImg" style="display: none">
        <img id="seeImg" style="height: 600px;width: 600px" src="">
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