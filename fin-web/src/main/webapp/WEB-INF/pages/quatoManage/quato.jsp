<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/15
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/quatoManage/quato.js${version}"></script>
    <title>额度管理</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;text-align: center;}
        .tdTitle{text-align: right!important;width: 120px;font-weight:bold;}
        #container td input{background-color: #fff!important; border:none!important;text-align: center!important;}
        #imageCard {width:40px;height: 40px;float:left;margin-right:1em;}
        .imgShow{max-width: 100%;max-height: 100%;}
        #imageCard .imgShowTd{padding-left: 1em;}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        #showNewImg ul{text-align: left}
        #showNewImg ul li{display: inline-block;width:25%;border:1px solid #ddd;text-align: center;margin:.2em 0;}
        .imgDiv{width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto;}
        .tdleft{text-align : left; }
        .tdright {text-align :right; font-weight:900}
        .layui-layer-btn0{width: 100%!important;}
        .layui-layer-shade{background-color:#f2f3eb !important;}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="empId" value="${userId}"/>
    <input type="hidden" name="hId" id="hId"/>
    <div class="commonManager">
        <%--<div class="Manager_style add_user_info search_style">--%>
            <%--<ul class="search_content clearfix">--%>
                <%--<li style="width:155px;">--%>
                    <%--<button id="btnAdd" type="button" class="btn btn-primary queryBtn" onclick="editDetail('1')">新增</button>--%>
                <%--</li>--%>
            <%--</ul>--%>
        <%--</div>--%>
        <%--<div class="Manager_style">--%>
            <%--<input type="hidden" value="" id="customerId" />--%>
            <%--<div class="fee_list">--%>
                <%--<table style="cursor:pointer;" id="fee_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">--%>
                    <%--<thead>--%>
                    <%--<tr>--%>
                        <%--<th>序号</th>--%>
                        <%--<th></th>--%>
                        <%--<th>额度上限</th>--%>
                        <%--<th>额度比例</th>--%>
                        <%--<th>状态</th>--%>
                        <%--<th>操作</th>--%>
                    <%--</tr>--%>
                    <%--</thead>--%>
                    <%--<tbody>--%>
                    <%--</tbody>--%>
                <%--</table>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--费率信息--%>
        <div id="editDetail" >
            <div class="addCommon clearfix">
                <ul class="clearfix">
                    <li  style="width: 250px">
                        <label class="lf"  style="width: 120px">折扣比例上限(%)</label>
                        <label >
                            <input type="text"  name="pro_quota_limit" id="pro_quota_limit" style="width: 120px" maxlength="8">
                        </label>
                    </li>
                    <li  style="width: 250px">
                        <label class="lf" style="width: 120px">折扣比例(%)</label>
                        <label >
                            <input type="text" name="pro_quota_proportion" id="pro_quota_proportion" style="width: 120px" maxlength="8">
                        </label>
                    </li>
                    <li style="width: 250px">
                        <label class="lf"  style="width: 122px">当前状态</label>
                        <select name="sltReduction" id="sltReduction" type="text" style="width: 120px;height:30px;margin-left: .5em" >
                            <option value="1">启用</option>
                            <option value="0">停用</option>
                        </select>
                    </li>
                </ul>
            </div>
            <%--<div style="width: 530px;text-align: center">--%>
                <%--<input type="hidden" name="hId" id="hId"/>--%>
                <%--<button id="btnAdd" type="button" class="btn btn-primary queryBtn" onclick="updateQuato()">保存</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
</body>
</html>
