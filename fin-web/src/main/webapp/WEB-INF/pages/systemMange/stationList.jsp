<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css${version}" />
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.checkbox.js${version}"></script>
    <script src="${ctx}/resources/js/systemMange/stationList.js${version}"></script>
    <title>岗位管理</title>
    <style>
        #processDiv .page_jump{display: none;}
        #userDiv .page_jump{display: none;}
    </style>
</head>
<body>
<div class="page-content">
    <input id="search_stationId" type="hidden"/>
    <div id="jsTreeCheckBox" style="display: none;text-align: left !important;width: 280px;"></div>
    <div id="processDiv" style="display: none;padding: 1em;">
        <table style="cursor:pointer;" id="station_process" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th></th>
                    <th>id</th>
                    <th>processId</th>
                    <th>nodeId</th>
                    <th>岗位名称</th>
                    <th>流程名称</th>
                    <th>流程节点名称</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <div id="userDiv" style="display: none;padding: 1em;">
        <div style="margin-bottom: 10px;">
            <input name="account" placeholder="(账号)" type="text" class="text_add"/>
            <input name="trueName" placeholder="(姓名)" type="text" class="text_add"/>
            <input name="mobile" placeholder="(手机号码)" type="text" class="text_add"/>
            <button id="user_btn_search"  type="button" class="btn btn-primary queryBtn" style="  margin-bottom: .5em;">查询</button>
            <button id="user_btn_search_reset"  type="button" class="btn btn-primary queryBtn" style="margin-bottom: .5em;">查询重置</button>
        </div>
        <table style="cursor:pointer;" id="user_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th></th>
                <th>
                    <input name="userCheckBox_All" id="userCheckBox_All" type="checkbox"  class="ace" isChecked="false" />
                    <span class="lbl" style="cursor:pointer;"></span>
                </th>
                <th>帐号</th>
                <th>姓名</th>
                <th>手机号</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <button type="button" class="btn btn-primary addBtn" id="addBtn" onclick="updateOrger(1,null)">添加岗位</button>
                <%--<button type="button" class="btn btn-primary deleteBtn" id="deleteBtn" onclick="updateStatusRus(-1)">删除</button>--%>
                <%--<button type="button" class="btn btn-primary resetBtn" id="resetBtn" onclick="updateStatusRus(1)">启用</button>--%>
                <%--<button type="button" class="btn btn-primary assignBtn" id="assignBtn" onclick="updateStatusRus(0)">停用</button>--%>
                <input type="text" id="search_name" placeholder="岗位名称" style="vertical-align: middle"/>
                <button id="btn_search" type="button" class="btn btn-primary queryBtn" style="top:0">查询</button>
                <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn" style="top:0">查询重置</button>
            </ul>
            <div id="Add_orger_style" style="display: none">
                <div class="addCommon clearfix">
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">岗位名称</label>
                            <label for="stationName">
                                <input name="stationName" type="text" id="stationName" maxlength="10"/>
                                <i style="color: #F60;">*</i>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">岗位描述</label>
                            <label for="stationDesc">
                                <input  name="stationDesc" type="text" id="stationDesc" maxlength="30"/>
                                <i style="color: #F60;">*</i>
                            </label>
                        </li>
                        <li style="text-align: left">
                            <input name="process" type="hidden"/>
                            <label id="addOver" style="display: none;font-size:15px;color: #307ecc;">
                                <i style="color: #F60;">*</i>流程已添加<i style="color: #F60;">*</i>
                            </label>
                            <button id="add_process" class="btn addBtn" onclick="showProcessCheckBoxTree(1,null)" style="display: none;margin-left:1.5em">添加流程</button>
                            <button id="edit_process" class="btn updateBtn" style="display: none;margin-left:1.5em">编辑流程</button>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="Manager_style">
            <div class="Organization_list">
                <table style="cursor:pointer;" id="orger_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <%--<th>--%>
                            <%--<input name="orgerCheckBox_All" id="orgerCheckBox_All" type="checkbox"  class="ace" isChecked="false" />--%>
                            <%--<span class="lbl" style="cursor:pointer;"></span>--%>
                        <%--</th>--%>
                        <th>岗位名称</th>
                        <th>岗位描述</th>
                        <th>状态</th>
                        <th>最后更新时间</th>
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