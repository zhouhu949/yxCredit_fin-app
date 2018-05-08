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
    <script src="${ctx}/resources/js/customerManage/intentionalCustomerList.js${version}"></script>
    <title>客户管理</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #userDiv .page_jump{display: none}
        #followDiv .page_jump{display: none}
        #addCustomer li{width:400px;}
        #addCustomer input,textarea{width:250px;margin-left:0;}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="empId" value="${userId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="personName" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">创建时间</label>
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
                    <button id="btn_search" type="button" class="btn btn-primary">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary ">查询重置</button>
                </li>
                <li style="width:155px;">
                    <button type="button" class="btn btn-primary addBtn" onclick="updateCustomer(null)">添加意向客户</button>
                </li>
                <%--<li style="width:155px;padding-top: 10px;">
                    <span class="onlyMe"><input type="checkbox" id="onlyMe"/>只看自己的客户</span>
                </li>--%>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="customer_list">
                <table style="cursor:pointer;" id="customer_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>客户id</th>
                        <th>客户名称</th>
                        <th>身份证号码</th>
                        <th>手机号码</th>
                        <th>归属公司</th>
                        <th>归属部门</th>
                        <th>归属人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%--添加意向客户弹框--%>
        <div id="addCustomer" style="display:none;">
            <input type="hidden" id="customerId">
            <div class="addCommon clearfix">
                <ul>
                    <li>
                        <label class="label_name">业务员<i style="color: #F60;">*</i></label>
                        <label>
                            <input type="text" id="empName" onclick="showUser()" readonly/>
                        </label>
                    </li>
                    <li>
                        <label class="label_name">客户姓名<i style="color: #F60;">*</i></label>
                        <label>
                            <input type="text" id="custName" maxlength="10"/>
                        </label>
                    </li>
                    <li>
                        <label class="label_name">手机号<i style="color: #F60;">*</i></label>
                        <label>
                            <input type="text" id="tel" maxlength="11"/>
                        </label>
                    </li>
                    <li>
                        <label class="label_name">身份证<i id="star" style="color: #F60;display: none;">*</i></label>
                        <label>
                            <input type="text" id="idCard" placeholder="请认真填写，否则实名认证可能不通过!" maxlength="18"/>
                        </label>
                    </li>
                    <li>
                        <label class="label_name">备注</label>
                        <label>
                            <textarea id="remark" style="height: 100px;resize:none;"></textarea>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
        <%--选择业务员--%>
        <div id="userDiv" style="display:none;padding: 1em;">
            <div style="margin-bottom: 10px;float: left;">
                <input name="userName" type="text" placeholder="业务员名称" class="text_add"/>
                <button id="search" type="button" class="btn btn-primary">查询</button>
            </div>
            <table style="cursor:pointer;" id="user_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th></th>
                    <th>工号</th>
                    <th>姓名</th>
                    <%--<th>部门</th>--%>
                    <th>公司</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <%--跟进列表--%>
        <div id="followDiv" style="display: none;padding: 1em;">
            <button type="button" class="btn btn-primary addBtn" style="float: left;margin-bottom: 10px;" onclick="addFollow()">新增跟进</button>
            <table style="cursor:pointer;" id="follow_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>id</th>
                    <th>跟进形式</th>
                    <th>跟进日期</th>
                    <th>创建时间</th>
                    <th>跟进内容</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <%--添加跟进记录--%>
        <div id="addFollow" style="display: none;">
            <div class="addCommon clearfix">
                <ul>
                    <li>
                        <label class="label_name">跟进日期<i style="color: #F60;">*</i></label>
                        <label>
                            <input class="eg-date" id="followDate" type="text" onclick=" laydate()" readonly/>
                        </label>
                    </li>
                    <li>
                        <label class="label_name">跟进形式<i style="color: #F60;">*</i></label>
                        <label>
                            <select style="height: 28px;" id="type">
                                <option value="1">电话</option>
                                <option value="2">拜访</option>
                                <option value="3">活动</option>
                            </select>
                        </label>
                    </li>
                    <li style="width: 350px;">
                        <label class="label_name">跟进内容<i style="color: #F60;">*</i></label>
                        <label>
                            <textarea id="followContent" style="height: 100px;resize:none;"></textarea>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
        <%--查看跟进内容--%>
        <div id="showFollowContent" style="display: none;padding: 1em;">
            <span id="followContentShow"></span>
        </div>
    </div>
</div>
</body>
</html>