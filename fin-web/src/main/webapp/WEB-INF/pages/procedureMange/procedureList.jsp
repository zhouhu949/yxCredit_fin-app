<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/procedureMange/procedure.js${version}"></script>
    <title>流程管理</title>
    <style>
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <li><label class="lf">流程名称</label>
                        <label>
                            <input name="procedure_name" type="text" class="text_add"/>
                        </label>
                    </li>
                    <button id="btn_search"  type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button id="addBtn" class="btn btn-primary queryBtn" type="button" onclick="updateProcedure(1)">添加流程</button>
                </ul>
                <%--<ul class="search_content clearfix">--%>
                <%--<button id="addBtn" class="btn btn-primary addBtn1 " type="button" onclick="updateProcedure(1)">添加流程</button>--%>
                <%--</ul>--%>
                <div id="Add_procedure_style" style="display: none">
                    <div class="addCommon clearfix">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">流程编码</label>
                                <label>
                                    <input name="orger_code" type="text" value="procedure_0001" id="orger_code" readonly/>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">流程名称</label>
                                <label for="orger_name">
                                    <input name="orger_name" type="text"  id="orger_name"/>
                                </label>
                            </li>
                            <%--<li>--%>
                            <%--<label class="label_name">绑定产品</label>--%>
                            <%--<label>--%>
                            <%--<input id="proName" proId="" type="text" onclick="openFieldsList();">--%>
                            <%--</label>--%>
                            <%--</li>--%>
                            <li>
                                <label class="label_name">流程描述</label>
                                <label for="remark" style="margin-top: 16px;">
                                    <textarea name="remark"  type="text" style="width:163px;height: 30px" id="remark" rows="4" cols="25" maxlength="150"></textarea>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">流程报文</label>
                                <label for="remark" style="margin-top: 16px;">
                                    <textarea name="remark"  type="text" style="width:170px;height:120px;" id="message" rows="4" cols="25" maxlength="150"></textarea>
                                </label>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </form>
        <div class="Manager_style">
            <div class="Organization_list">
                <table style="cursor:pointer;" id="procedure_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th style="display:none">ID</th>
                        <th>流程ID</th>
                        <th>流程编码</th>
                        <th>流程名称</th>
                        <th>流程描述</th>
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
<div class="c-createusers-dialog selWord dialog group-dialog" id="" style="display: none">
    <input type="hidden" value="" id="showFieldsPlace">
    <div style="padding-top: 8px;" id="fieldsStyle">
        <ul style="float: right" id="ziduanchaxun">
            <input type="hidden" id="isShowFileds">
            <label>产品名称：</label>
            <input type="text" name="Parameter_search" id="Parameter_search" placeholder="搜索" style="vertical-align: middle"/>
            <button type="button" class="btn btn-primary queryBtn" style="top:0" id="btn_searchr">查询</button>
            <button type="button" class="btn btn-primary queryBtn" style="top:0" id="btn_search_resetr">查询重置</button>
        </ul>
    </div>
    <hr>
    <table style="text-align: center;cursor: pointer;margin-top: 20px" id="swarmFields_list" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th></th>
            <th><input type="radio" name="proId" value=""></th>
            <th>产品名称</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
</body>
</html>