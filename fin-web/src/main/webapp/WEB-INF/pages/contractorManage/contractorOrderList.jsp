<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/zTree/css/zTreeStyle/zTreeStyle.css" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/contractorManage/contractorOrder.js${version}"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/treeTable/js/jquery.treetable.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/tree.js"></script>
    <title>订单汇总</title>
    <style>
        #organ{outline: none!important;appearance:none; -moz-appearance:none; -webkit-appearance:none;height:28px;line-height:19px;}
        #email{margin-left:0px!important;}
        #isLock{height:28px;}
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <li><label class="lf">总包商名称</label>
                        <label>
                            <input name="contractorName"  type="text" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">姓名</label>
                        <label>
                            <input name="trueName" type="text" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">手机号码</label>
                        <label>
                            <input name="mobile"  type="text" class="text_add"/>
                        </label>
                    </li>
                    <button id="btn_search"  type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="updateUser(1)" id="Add_user_btn">导出</button>
                </ul>
            </div>
        </form>
        </div>
        <div class="Manager_style">
            <div class="User_list">
                <table style="cursor:pointer;" id="ContractorOrder_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <%--<th>--%>
                            <%--<input name="userCheckBox_All" id="userCheckBox_All" type="checkbox"  class="ace" isChecked="false" />--%>
                            <%--<span class="lbl" style="cursor:pointer;"></span>--%>
                        <%--</th>--%>
                        <th>订单号</th>
                        <th>所属总包商</th>
                        <th>姓名</th>
                        <th>身份证号码</th>
                        <th>申请金额</th>
                        <th>批复金额</th>
                        <th>服务费</th>
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