<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <%@include file="../common/taglibs.jsp" %>
    <%@include file="../common/importDate.jsp" %>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/orderManage/orderList.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>
    <title>订单列表</title>
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
                <li style="width:150px">
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
                </li>
                <li><label class="lf">创建时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li>
                <span class="line-cut"></span>
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
                <table style="cursor:pointer;" id="order_list" cellpadding="0" cellspacing="0"
                       class="table table-striped table-bordered table-hover">
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
</body>
</html>