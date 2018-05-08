<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/systemMange/stationUser.js${version}"></script>
    <title>岗位设置</title>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div style="margin-bottom: 10px;">
            <input id="search_name" placeholder="岗位名称/用户账号" type="text" class="text_add"/>
            <button id="btn_search"  type="button" class="btn btn-primary queryBtn">查询</button>
            <button id="btn_search_reset"  type="button" class="btn btn-primary queryBtn">查询重置</button>
        </div>
        <div class="Manager_style">
            <div class="User_list">
                <table style="cursor:pointer;" id="user_station" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>用户账号</th>
                        <th>岗位名称</th>
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