<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/task/taskMsgPast.js${version}"></script>
    <title>流程任务</title>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div id="taskDiv">
            <table id="commit_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>订单号</th>
                    <th>客户名称</th>
                    <th>产品名称</th>
                    <th>任务流程</th>
                    <th>任务流程节点</th>
                    <th>任务流程节点状态</th>
                    <th>任务节点创建时间</th>
                    <th>任务节点处理时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>