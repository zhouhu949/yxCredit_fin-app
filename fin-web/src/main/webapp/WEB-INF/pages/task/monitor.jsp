<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/task/monitor.js${version}"></script>
    <title>流程任务</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        #userDiv .page_jump{display: none;}
    </style>
</head>
<body>
<div class="page-content">
    <input name="userId" type="hidden" value=""/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">订单号</label>
                    <label>
                        <input name="orderId" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="customerName" type="text" class="text_add"/>
                    </label>
                </li>
                <li style="width: 160px;"><label class="lf">任务状态</label>
                    <label style=" margin-left: 15px;margin-top: 4px;" >
                        <select name="selectType" id="selectType" style="height: 28px;">
                            <option value="-1">请选择</option>
                            <option value="1">处理中</option>
                            <option value="3">拒绝</option>
                            <option value="4">通过</option>
                        </select>
                    </label>
                </li>
                <li style="width: 280px;"><label class="lf">任务创建时间</label>
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
                <%--<li style="width:155px;padding-top: 10px;">
                    <span class="onlyMe"><input type="checkbox" id="onlyMe"/>只看自己的客户</span>
                </li>--%>
            </ul>
        </div>
        <div id="taskDiv">
            <table id="commit_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>
                        <input name="selectAll" id="selectAll" type="checkbox"  class="ace" isChecked="false" />
                        <span class="lbl" style="cursor:pointer;"></span>
                    </th>
                    <th>订单号</th>
                    <th>客户名称</th>
                    <th>产品名称</th>
                    <th>流程名称</th>
                    <th>任务流程节点</th>
                    <th>任务流程状态</th>
                    <th>任务流程节点状态</th>
                    <th>任务创建时间</th>
                    <th>任务当前节点创建时间</th>
                    <th>任务当前节点处理时间</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <%--已处理详情--%>
        <div id="cus_order" style="display: none">
            <span style="font-size: 20px;margin: 10px 0px 0px 10px">已处理任务</span>
            <table style="cursor:pointer;" id="order_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>订单号</th>
                    <th>客户名称</th>
                    <th>产品名称</th>
                    <th>任务所处节点</th>
                    <th>任务所处节点状态</th>
                    <th>任务节点创建时间</th>
                    <th>任务节点提交时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>