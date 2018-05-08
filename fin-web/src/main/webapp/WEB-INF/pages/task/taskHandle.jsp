<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file ="../common/taglibs.jsp"%>
    <title>任务处理</title>
</head>
<style>
    a{color: red;font-size: 15px;margin: 10px 0 0 250px;}
</style>
<body>
    <div style="margin:40px 0 0 40px;">
        <p>假页面---任务处理</p>
        <br>
        <p>任务节点ID<input type="text" value="${taskNodeId}" readonly/></p>
        <br>
        <p>流程节点ID<input type="text" value="${processNodeId}" readonly/></p>
        <a href="javascript:" onclick="self.location=document.referrer;">返回</a>
    </div>
</body>
</html>
