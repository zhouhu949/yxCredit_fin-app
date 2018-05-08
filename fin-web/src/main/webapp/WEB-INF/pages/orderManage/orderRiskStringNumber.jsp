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
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/orderManage/orderRiskStringNumber.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>
    <title>商品串号码管理</title>
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
                <li><label class="lf">商品串号码</label>
                    <label>
                        <input id="merchandiseCode" type="text" class="text_add"/>
                    </label>
                </li>
                <li style="width:150px">
                    <label class="lf">状态</label>
                    <label>
                        <select name="condition" size="1" id="isLock" style="width:100px;    margin-left: 10px;">
                            <%--1借款申请;2自动化审批通过;3自动化审批拒绝;4自动化审批异常转人工;5人工审批通过;6人工审批拒绝;7合同确认;8放款成功;9结清'--%>
                            <option value="">请选择</option>
                            <option value="1">借款申请</option>
                            <option value="2">自动化审批通过</option>
                            <option value="3">自动化审批拒绝</option>
                            <option value="4">自动化审批异常转人工</option>
                            <option value="5">人工审批通过</option>
                            <option value="6">人工审批拒绝</option>
                            <option value="-7">已退回</option>
                            <option value="-1">待确认合同</option>
                            <option value="-2">合同确认</option>
                            <option value="-3">待付预付款</option>
                            <option value="-4">待发货</option>
                            <option value="-5">已发货</option>
                            <option value="-6">还款中</option>
                            <!--<option value="8">放款成功</option>-->
                            <option value="9">结清</option>
                            <option value="10">已关闭</option>
                        </select>
                    </label>
                </li>
                <li><label class="lf">创建时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li><span class="line-cut"></span>
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
                        <th>申请时间</th>
                        <th>产品类型</th>
                        <th>产品名称</th>
                        <th>商品名称</th>
                        <th>商品金额</th>
                        <th>首付金额</th>
                        <th>申请金额</th>
                        <th>申请期限</th>
                        <th>状态</th>
                        <th>商品串号码</th>
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
</body>
</html>