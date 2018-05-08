<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/28
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
    <script src="${ctx}/resources/js/servicePackageManage/magServicePackage.js${version}"></script>
    <title>费率管理</title>
</head>

<body>
<div class="page-content">
    <input type="hidden" name="empId" value="${userId}"/>
    <div class="commonManager">

        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li style="width: 200px;">
                    <label class="1f" style="width: 35px;" >类型</label>
                    <label >
                        <select id="type" style="min-width:90px;height: 28px;" >
                            <option value="">请选择</option>
                        </select>
                    </label>
                </li>
                <li><label class="lf">服务包名称</label>
                    <label style="width: 130px;">
                        <input id="pname" type="text" placeholder="请输入服务包名称" style="width: 130px;">
                    </label>
                </li>
                <li style="width: 200px;">
                    <label class="1f"  >是否启用</label>
                    <label >
                        <select id="mgstate" style="min-width:90px;height: 28px;">
                            <option value="">请选择</option>
                            <option value="1">启用</option>
                            <option value="2">停用</option>
                        </select>
                    </label>
                </li>
                <li style="width:300px;">
                    <button id="b_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button id="btn_add" type="button" class="btn btn-primary queryBtn">添加</button>
                </li>
            </ul>
        </div>

        <div class="Manager_style">
            <input type="hidden" value="" id="customerId" />
            <div class="fee_list">
                <table style="cursor:pointer;" id="fee_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th></th>
                        <th>服务包类型</th>
                        <th>服务包名称</th>
                        <th>付款方式</th>
                        <th>收取类型</th>
                        <th>是否强制收取</th>
                        <th>收取期数</th>
                        <th>几期后可提前还款</th>
                        <th>收取金额</th>
                        <%--<th>启用/停用</th>--%>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%--服务包款包--%>
        <div id="editDetail" style="display: none">
            <div class="addCommon clearfix">
                <ul class="clearfix">
                    <li style="width:250px;" id = "fwb1f" >
                        <label class="1f"  style="width:112px;">服务包类型</label>
                        <label style="width:128px;">
                            <select id="package_id"  style="width:128px;size: 14px;" onchange="packageOnchange()">

                            </select>
                        </label>
                    </li>
                    <li style="width:250px;" id="aa">
                        <label class="lf"  style="width:106px;">服务包名称</label>
                        <label style="width:128px;">
                            <input type="text"  name="package_name" id="package_name" style="width:128px;">
                        </label>
                    </li>
                    <li style="width:250px;" id="jqhktqhk1f">
                        <label  class="1f"  style="width:112px;">几期后可提前还款</label>
                        <label style="width:128px;">
                            <input type="text"   id="month" style="width:128px;">
                        </label>
                    </li>
                    <li style="width:250px;" id="qzsq1f">
                        <label class="lf"  style="width:112px;">是否强制收</label>
                        <label style="width:130px;">
                            <input type="radio" id="apex1" name="apex1" value="1"  checked="checked" /><span>  是    </span>
                            <input type="radio" id="apex2" name="apex1" value="0" /><span>  否</span>
                        </label>
                    </li>
                    <li style="width:250px;" id="sqje1f">
                        <label class="1f"  style="width:112px;">收取金额</label>
                        <label style="width:128px;">
                            <input type="text"  name="amount_collection" id="amount_collection" style="width:128px; margin-left:5px;" placeholder="元/月">
                        </label>
                    </li>
                    <li style="width:250px;" id="sqqs1f">
                        <label class="lf"  style="width:107px;">收取期数</label>
                        <label style="width:128px;">
                            <select id="collection_period" onchange="collection_periodOnchange()"  style="width:128px;size: 14px;">
                                <option value="1">自定义期数</option>
                                <option value="0">随产品期数</option>
                            </select>
                        </label>
                    </li>
                    <li style="width:250px;" id="zdyqs">
                        <label class="lf"  style="width:107px;">自定义期数</label>
                        <label style="width:128px;">
                            <input type="text"   id="zdyqsTxt" style="width:128px;">
                        </label>
                    </li>


                    <li style="width:250px;" id="sfqy1f">
                        <label class="1f"  style="width:113px;">是否启用</label>
                        <label style="width:128px;">
                            <select id="state"  style="width:128px;">
                                <option value="1" >启用</option>
                                <option value="2">停用</option>
                            </select>
                        </label>
                    </li>

                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
