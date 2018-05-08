<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file ="../common/taglibs.jsp"%>
    <title>任务消息</title>
    <style>
        iframe{border: 0; width: 100%;height:240px; background-color: #FFF;}
    </style>
</head>
<body>
    <label style="margin-left:1.5em;font-weight: bold">处理中</label>
    <iframe id="iframe_in"  scrolling="auto" frameborder="0" src="${ctx}/taskMsg/pageIn" ></iframe>
    <label style="margin-left:1.5em;color:#E4393C;font-weight: bold">待处理</label>
    <iframe id="iframe_not"  scrolling="auto" frameborder="0" src="${ctx}/taskMsg/pageNot" ></iframe>
    <label style="margin-left:1.5em;color:#2EDA9F;font-weight: bold">已处理</label>
    <iframe id="iframe_past"  scrolling="auto" frameborder="0" src="${ctx}/taskMsg/monitor" ></iframe>
</body>
</html>