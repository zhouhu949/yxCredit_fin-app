<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/goldOrder/logisticsList.js${version}"></script>
    <title>发货列表</title>
    <style>
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
            left: 1150px;
            top: 135px;
        }
        #collContainer .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #divFrom input{border:none;background-color: #fff!important;text-align: left;float:left;}
        .addnew img {
            margin-top: 72px;
            position: absolute;
            left: 124px;
        }
        .addnew p{
            position: relative;
            top: 154px;
            left: -36%;
        }
        /*合理性样式*/
        #showReasonable input[name=ReasonableInput]{border:1px solid #ddd}
        .ReasonableUl li{
            display: inline-block;
            width: 24%;
        }
        .ReasonableUl li img{
            width: 100%;
            cursor: pointer;
        }
        #btn_search{
            height: 28px;
            width: 70px;
        }

        .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #plan-wrapper li{
            padding-left: 40px;
            font-size: 0;
            border-bottom: 1px solid #ccc;
        }
        #plan-wrapper li:last-child{
            border-bottom: none;
        }
        #plan-wrapper{
            width: 700px;
            border:1px solid #ccc;
        }

        #plan-wrapper li span{
            display: inline-block;
            height: 40px;
            line-height: 40px;
            font-size: 16px;
            text-align: center;
        }
        #plan-wrapper .period{
            width: 50px;
        }
        #plan-wrapper .repayDate{
            width: 190px;
        }
        #plan-wrapper .corpus{
            width: 80px;
        }
        #plan-wrapper .interest{
            width: 80px;
        }
        #plan-wrapper .monthPay{
            width: 140px;
        }
        #plan-wrapper .state{
            width: 80px;
        }
        #warningInfo table tr{
            border:1px solid #ddd
        }
        #warningInfo table th,#warningInfo table td{
            text-align: center;
            border:1px solid #ddd;
            padding: .5em;
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
                <li style="width:200px">
                    <label class="lf">发货状态</label>
                    <label>
                        <select name="condition" size="1" id="isLock" style="width:100px;    margin-left: 10px;">
                            <%--1借款申请;2自动化审批通过;3自动化审批拒绝;4自动化审批异常转人工;5人工审批通过;6人工审批拒绝;7合同确认;8放款成功;9结清'--%>
                            <option value="0">请选择</option>
                            <option value="1">待发货</option>
                            <option value="2">已发货</option>
                        </select>
                    </label>
                </li>
                <%--<li><label class="lf">创建时间</label>--%>
                    <%--<label>--%>
                        <%--<input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>--%>
                        <%--<span class="date-icon"><i class="icon-calendar"></i></span>--%>
                    <%--</label>--%>
                <%--</li><span class="line-cut"></span>--%>
                <%--<li style="width:200px;">--%>
                    <%--<label>--%>
                        <%--<input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>--%>
                        <%--<span class="date-icon"><i class="icon-calendar"></i></span>--%>
                    <%--</label>--%>
                <%--</li>--%>
                <li style="width:220px;">
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
                        <%--<th>申请时间</th>--%>
                        <th>产品类型</th>
                        <th>产品名称</th>
                        <th>购买金额</th>
                        <th>每克单价</th>
                        <th>克数</th>
                        <th>申请期限</th>
                        <th>发货状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%----%>
        <div id="logisticsDiv" style="display: none;padding: 1em;">
            <table  cellpadding="0" cellspacing="0" class="table  table-bordered ">
                <tr><td colspan="4" style="text-align: left">客户信息</td></tr>
                <tr>
                    <td style="text-align: right;width: 20%">客户名称：</td>
                    <td style="text-align: left;width: 30%"><label id="lblPersonName"></label></td>
                    <td style="text-align: right;width: 20%">联系电话</td>
                    <td style="text-align: left;width: 30%"><label id="lblTel"></label></td>
                </tr>
                <tr>
                    <td style="text-align: right">收货地址：</td>
                    <td style="text-align: left;" colspan="3"><label id="lblReceiveAddress"></label></td>
                </tr>
                <tr><td colspan="4" style="text-align: left">发货信息</td></tr>
                <tr>
                    <td style="text-align: right;">采购价格：</td>
                    <td style="text-align: left;" >
                        <input name="billNo" id="purchasePrice" type="text" class="text_add" style="width: 140px;" MAXLENGTH="8"/><span>元/克</span>
                    </td>
                    <td style="text-align: right;">物流公司：</td>
                    <td style="text-align: left;width: 30%" >
                        <select  size="1" id="company" style="width:100px;    margin-left: 10px;">
                            <option value="-1">请选择</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">物流单号：</td>
                    <td style="text-align: left;"  colspan="3">
                        <input name="billNo" id="billNo" type="text" class="text_add" onkeyup="value=value.replace(/[\W]/g,'')" MAXLENGTH="40"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>