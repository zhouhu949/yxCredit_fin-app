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

        .commonManager .addCommon .content_ul li {
            width: 46%;
        }
        .commonManager .addCommon .content_ul li .label_name{
            width: 70px;
            text-align: center;
        }
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
                            <select name="proSeriesName" style="height: 30px;" id="searchContractor">
                                <option value="">请选择</option>
                            </select>
                        </label>
                    </li>
                    <li><label class="lf">姓名</label>
                        <label>
                            <input name="trueName" type="text" class="text_add" id="searchName"/>
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
                <div id="Add_user_style" style="display: none">
                    <div class="addCommon clearfix">
                        <ul class="clearfix content_ul">
                            <li>
                                <label class="label_name">所属总包商</label>
                                <label>
                                    <select name="contractorList" style="height: 30px;" id="contractorId">
                                        <option value="">请选择</option>
                                    </select>
                                </label>
                            </li>

                            <li>
                                <label class="label_name">姓名 <i style="color: #F60;">*</i></label>
                                <label for="realName">
                                    <input name="real_name"  type="text"  id="realName"/>

                                </label>
                            </li>
                            <li>
                                <label class="label_name">身份证号<i style="color: #F60;">*</i></label>
                                <label for="card">
                                    <input name="card"  type="text" id="card" maxlength="18"/>

                                </label>
                            </li>
                            <li>
                                <label class="label_name">手机号 <i style="color: #F60;">*</i></label>
                                <label for="telphone">
                                    <input name="tel_phone"  type="number" id="telphone" maxlength="11"/>

                                </label>
                            </li>
                            <li>
                                <label class="label_name">合同状态 <i style="color: #F60;">*</i></label>
                                <label>
                                    <select name="contract_status" size="1" id="contractStatus">
                                        <option value=1 id="you">有</option>
                                        <option value=0 id="wu">无</option>
                                    </select>
                                </label>

                            </li>

                            <li>
                                <label class="label_name">工资发放<i style="color: #F60;">*</i></label>
                                <label>
                                    <select name="pay_type" size="1" id="payType">
                                        <option value=1 id="xj">现金</option>
                                        <option value=2 id="dk">打卡</option>
                                        <option value=3 id="sy">均有</option>
                                    </select>
                                </label>

                            </li>

                            <li style="width:59%;"><label class="lf" style="width:70px; text-align:left;">合同时间</label>
                                <label style="margin-left: -12px;">
                                    <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                                    <span class="date-icon"><i class="icon-calendar"></i></span>
                                </label>
                                <span class="line-cut" style="margin-left:37px">--</span>
                            </li>
                            <li style="width:200px;margin-left:10px;">
                                <label>
                                    <input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>
                                    <span class="date-icon"><i class="icon-calendar"></i></span>
                                </label>
                            </li>


                            <li>
                                <label class="label_name">职务 <i style="color: #F60;">*</i></label>
                                <label>
                                    <select name="job" size="1" id="job">
                                        <option value=1 id="bgt">包工头</option>
                                        <option value=2 id="nmg">农民工</option>
                                        <option value=3 id="lsg">临时工</option>
                                    </select>
                                </label>

                            </li>
                            <li>
                                <label class="label_name">工种 <i style="color: #F60;">*</i></label>
                                <label for="jobType">
                                    <input name="job_type"  type="text"  id="jobType"/>

                                </label>
                            </li>
                            <li>
                                <label class="label_name">发薪日 <i style="color: #F60;">*</i></label>
                                <label for="latestPayday">
                                    <input name="latest_payday"  type="number"  id="latestPayday"/>

                                </label>
                            </li>

                            <li>
                                <label class="label_name">应发工资<i style="color: #F60;">*</i></label>
                                <label for="latestPay">
                                    <input name="latest_pay"  type="number"  id="latestPay"/>

                                </label>
                            </li>
                            <li>
                                <label class="label_name">最低工资 <i style="color: #F60;">*</i></label>
                                <label for="localMonthlyMinWage">
                                    <input name="local_monthly_min_wage"  type="number"  id="localMonthlyMinWage"/>

                                </label>
                            </li>
                            <li style="width:524px; height:100%;">
                                <form id="whiteListImgForm" method="post" enctype="multipart/form-data" >
                                    <label class="label_name" style="width:120px;">发薪证明（图片）</label>
                                    <div id="localImag" style="padding-left: 5px;"><img id="preview" src="" style="display: block; width: 162px; height: 110px;"></div>
                                    <input type="file" name="file" id="file" style="width:64px; height:35px; margin-left:120px;" onchange="javascript:setImagePreview1();" >
                                </form>
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