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
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/customerAudit.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>
    <title>客户审核</title>
</head>
<style>
    #answerList table td{text-align: left}
    #answerList table tr{height: 60px;border:1px solid #4a4f56;}
    /* css注释：只对table td标签设置红色边框样式 */
</style>
<body>
<div class="page-content">
    <input type="hidden" name="leftStatus" value="${leftStatus}"/>
    <input type="hidden" name="userId" id="userId" value="${userId}"/>
    <input type="hidden" name="handName" id="handName" value="${nickName}"/>
    <input type="hidden" name="taskNodeId" id="taskNodeId" value="${param.taskNodeId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">客户名称</label>
                    <label>
                        <input name="customerName" type="text" class="text_add"/>
                    </label>
                </li>
               <%-- <li><label class="lf">身份证号</label>
                    <label>
                        <input name="card" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">手机号码</label>
                    <label>
                        <input name="tel" type="text" class="text_add"/>
                    </label>
                </li>--%>
               <%-- <li >
                    <label class="lf">审核状态</label>
                    <label>
                        <select name="condition" size="1" id="isLock" style="width:163px;margin-left: 10px;height: 28px" >
                            <option value="1">待审核</option>
                            <option value="2">已审核</option>
                        </select>
                    </label>
                </li>--%>
                <li><label class="lf">申请时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                <%--</li><span class="line-cut">--</span>--%>
                <li style="width:200px;">
                    <label>
                        <input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li>
                <li style="width:155px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
                <%-- <li style="width:155px;padding-top: 10px;">
                     <span class="onlyMe"><input type="checkbox" id="onlyMe"/>只看自己的客户</span>
                 </li>--%>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="order_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单编号</th>
                        <th>客户名称</th>
                        <th>身份证号</th>
                        <th>手机号码</th>
                        <th>产品名称</th>
                        <th>申请金额</th>
                        <th>申请期限</th>
                        <th>申请时间</th>
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
<div style="display: none;" id="imgDisplay">
    <img src="" alt="">
</div>
<div id="answerList" style="display: none">
    <input type="hidden" id="answerCount">
    <table  style="font-size:12px;width: 90%;">
        <tbody id="answerTable">
        </tbody>
    </table>
</div>
<div id="showImg" style="display: none">
    <img id="seeImg" style="height: 600px;width: 600px" src="">
</div>

</body>
</html>