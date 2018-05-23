<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/contractorManage/whiteList.js${version}"></script>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/js/jquery.form.min.js"></script>
    <title>白名单信息</title>
    <style>
        #organ{outline: none!important;appearance:none; -moz-appearance:none; -webkit-appearance:none;height:28px;line-height:19px;}
        #email{margin-left:0px!important;}
        #isLock{height:28px;}
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <li>
                        <label class="label_name">总包商名</label>
                        <label>
                            <select name="proSeriesName" style="height: 30px;" id="userId">
                                <option value="">请选择</option>
                            </select>
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
                    <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="updateWhite(1)" id="Add_user_btn">添加白名单</button>
                </ul>
                <%--<ul class="search_content clearfix">--%>
                    <%----%>
                    <%--<button type="button" class="btn btn-primary deleteBtn" id="deleteBtn" onclick="deleteUser()">删除</button>--%>
                    <%--<button type="button" class="btn btn-primary resetBtn" id="resetBtn" onclick="resetPwd()">重置密码</button>--%>
                <%--</ul>--%>
                <div id="Add_user_style" style="display: none">
                    <div class="addCommon clearfix">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">所属总包商</label>
                                <label>
                                    <select name="contractorList" style="height: 30px;" id="contractorId">
                                        <option value="">请选择</option>
                                    </select>
                                </label>
                            </li>

                            <li>
                                <label class="label_name">姓名</label>
                                <label for="realName">
                                    <input name="real_name"  type="text"  id="realName"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">身份证号</label>
                                <label for="card">
                                    <input name="card"  type="text" id="card" maxlength="11"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">手机号</label>
                                <label for="telphone">
                                    <input name="tel_phone"  type="text" id="telphone" maxlength="11"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">合同状态</label>
                                <label>
                                    <select name="contract_status" size="1" id="contractStatus">
                                        <option value=1 id="you">有</option>
                                        <option value=0 id="wu">无</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>

                            <li>
                                <label class="label_name">工资发放</label>
                                <label>
                                    <select name="pay_type" size="1" id="payType">
                                        <option value=1 id="xj">现金</option>
                                        <option value=2 id="dk">打卡</option>
                                        <option value=3 id="sy">均有</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>

                            <li><label class="lf">合同时间</label>
                                <label>
                                    <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                                    <span class="date-icon"><i class="icon-calendar"></i></span>
                                </label>
                                <span class="line-cut">--</span>
                            </li>
                            <li style="width:200px;">
                                <label>
                                    <input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>
                                    <span class="date-icon"><i class="icon-calendar"></i></span>
                                </label>
                            </li>


                            <li>
                                <label class="label_name">职务</label>
                                <label>
                                    <select name="job" size="1" id="job">
                                        <option value=1 id="bgt">包工头</option>
                                        <option value=2 id="nmg">农民工</option>
                                        <option value=3 id="lsg">临时工</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li>
                                <label class="label_name">工种</label>
                                <label for="jobType">
                                    <input name="job_type"  type="text"  id="jobType"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">发薪日</label>
                                <label for="latestPayday">
                                    <input name="latest_payday"  type="text"  id="latestPayday"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>

                            <li>
                                <label class="label_name">应发工资</label>
                                <label for="latestPay">
                                    <input name="latest_pay"  type="text"  id="latestPay"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li style="width:524px;">
                                <form id="whiteListImgForm" method="post" enctype="multipart/form-data" >
                                    <label class="label_name">发薪证明（图片）</label>
                                    <div id="localImag" style="padding-left:3px;"><img id="preview" src="" width="100" height="20" style="display: block; width: 100px; height: 20px;"></div>
                                    <input type="file" name="file" id="file" style="width:100px;" onchange="javascript:setImagePreview1();" >
                                </form>
                            </li>
                            <li>
                                <label class="label_name">最低工资</label>
                                <label for="localMonthlyMinWage">
                                    <input name="local_monthly_min_wage"  type="text"  id="localMonthlyMinWage"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>

                        </ul>
                    </div>
                </div>

                <div id="asignRole" style="height:100%;display: none;">
                    <div class="col-xs-5" style="height: 100%;" >
                        <select id="multiselect" style="height: 100%;"  class="form-control" size="8" multiple="multiple">
                            <option style="text-align:center;"></option>
                        </select>
                    </div>
                    <div class="col-xs-2" style="margin: 20% 0;">
                        <button type="button" id="multiselect_rightAll" class="btn btn-block" onclick="rightAll()"><i class="glyphicon glyphicon-forward"></i></button>
                        <button type="button" id="multiselect_rightSelected" class="btn btn-block" onclick="rightSelected()"><i class="glyphicon glyphicon-chevron-right"></i></button>
                        <button type="button" id="multiselect_leftSelected" class="btn btn-block" onclick="leftSelected()"><i class="glyphicon glyphicon-chevron-left"></i></button>
                        <button type="button" id="multiselect_leftAll" class="btn btn-block" onclick="leftAll()"><i class="glyphicon glyphicon-backward"></i></button>
                    </div>
                    <div class="col-xs-5" style="height: 100%;" >
                        <select  style="height: 100%;"  id="multiselect_to" class="form-control" size="8" multiple="multiple">
                            <option  style="text-align:center;"></option>
                        </select>
                    </div>
                </div>

            </div>
        </div>
        <div class="Manager_style">
            <div class="User_list">
                <table style="cursor:pointer;" id="White_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <%--<th>--%>
                            <%--<input name="userCheckBox_All" id="userCheckBox_All" type="checkbox"  class="ace" isChecked="false" />--%>
                            <%--<span class="lbl" style="cursor:pointer;"></span>--%>
                        <%--</th>--%>
                        <th>所属总包商</th>
                        <th>姓名</th>
                        <th>身份证号</th>
                        <th>手机号</th>
                        <th>职务</th>
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