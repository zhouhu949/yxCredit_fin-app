<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/procedureMange/processConfig.js${version}"></script>
    <title>流程管理</title>
    <style>
        #product_list{font-size: 13px;}
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
                            <input name="procedure_name" id="procedure_name" type="text" class="text_add"/>
                        </label>
                    </li>
                    <button id="btn_search"  type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                </ul>
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
                        <th>功能码</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%--产品--%>
        <div class="Manager_style" id="product" style="display: none">
            <form id="form" name="form1" method="post" action="">
                <div class="Manager_style add_user_info search_style">
                    <ul class="search_content clearfix" style="margin: 1em;">
                        <li><label class="lf">功能码名称</label>
                            <label>
                                <input name="product_name" id="product_name" type="text" class="text_add">
                            </label>
                        </li>
                        <button id="product_list_search" type="button" class="btn btn-primary queryBtn" style="float:left;">查询</button>
                        <button id="product_list_search_reset" type="button" class="btn btn-primary queryBtn" style="float:left">查询重置</button>
                    </ul>
                </div>
            </form>
            <input type="hidden" id="product_id">
            <input type="hidden" id="processId">
            <input type="hidden" id="relId">
            <div class="Organization_list">
                <table style="cursor:pointer;" id="product_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th style="display:none"></th>
                        <th></th>
                        <th>功能码</th>
                        <th>功能码名称</th>
                        <th>功能码描述</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</div>
<div class="c-createusers-dialog selWord dialog group-dialog" id="" style="display: none">
    <input type="hidden" value="" id="showFieldsPlace">
    <div style="padding-top: 8px;" id="fieldsStyle">
        <ul style="float: right" id="ziduanchaxun">
            <input type="hidden" id="isShowFileds">
            <label>名称：</label>
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
            <th>名称</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
</body>
</html>