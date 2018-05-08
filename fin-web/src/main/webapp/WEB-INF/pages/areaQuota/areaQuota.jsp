<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/areaQuota/areaQuota.js${version}"></script>
    <script src="${ctx}/resources/js/areaQuota/city.data-3.js${version}"></script>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>


    <title>区域限额设置</title>
    <style>
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        .mb10 {
            margin-bottom: 10px !important;
        }
        .inpText {
            border-radius: 2px;
            border: 1px solid #ccc;
            text-indent: 2px;
            line-height: 24px;
            padding-top: 0;
            padding-bottom: 0;
        }
        .btn_pad_s2 {
            padding: 0px 28px;
            line-height: 26px;
            border-radius: 2px;
            text-align: center;
            cursor: pointer;
            display: inline-block;
            border: 0;
            font-size: 14px;
        }
        .btn_color1 {
            background: #05c1bc;
            color: #fff;
            border-radius: 2px;
        }
        .s_floatToolA {
            position: fixed;
            left: 1150px;
            top: 135px;
        }
        #collContainer .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #divFrom input{border:none;background-color: #fff!important;text-align: left;float:left;}
        .addnew img {
            margin-top: 72px;
            position: absolute;
            left: 124px;
        }
        .addnew p{
            position: relative;
            top: 154px;
            left: -36%;
        }
        /*合理性样式*/
        #showReasonable input[name=ReasonableInput]{border:1px solid #ddd}
        .ReasonableUl li{
            display: inline-block;
            width: 24%;
        }
        .ReasonableUl li img{
            width: 100%;
            cursor: pointer;
        }
        #btn_search{
            height: 28px;
            width: 70px;
        }

        .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #plan-wrapper li{
            padding-left: 40px;
            font-size: 0;
            border-bottom: 1px solid #ccc;
        }
        #plan-wrapper li:last-child{
            border-bottom: none;
        }
        #plan-wrapper{
            width: 700px;
            border:1px solid #ccc;
        }

        #plan-wrapper li span{
            display: inline-block;
            height: 40px;
            line-height: 40px;
            font-size: 16px;
            text-align: center;
        }
        #plan-wrapper .period{
            width: 50px;
        }
        #plan-wrapper .repayDate{
            width: 190px;
        }
        #plan-wrapper .corpus{
            width: 80px;
        }
        #plan-wrapper .interest{
            width: 80px;
        }
        #plan-wrapper .monthPay{
            width: 140px;
        }
        #plan-wrapper .state{
            width: 80px;
        }
        #warningInfo table tr{
            border:1px solid #ddd
        }
        #warningInfo table th,#warningInfo table td{
            text-align: center;
            border:1px solid #ddd;
            padding: .5em;
        }
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="userId" value="${userId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">省份：</label>
                    <label>
                        <select id="search_province" type="text" style="min-width: 120px;height:30px;margin-left: .5em" onchange="citySelAdd(this.options[this.options.selectedIndex].value,$('#search_city'))">
                            <option value="">请选择</option>
                        </select>
                    </label>
                </li>
                <li><label class="lf">城市:</label>
                    <label>
                        <select  id="search_city" type="text" style="min-width: 120px;height:30px;margin-left: .5em" >
                            <option value="">请选择</option>
                        </select>
                    </label>
                </li>
                <li><label class="lf">状态:</label>
                    <label>
                        <select name="state" id="state" type="text" style="min-width: 120px;height:30px;margin-left: .5em" >
                            <option value="">请选择</option>
                            <option value="0">停用</option>
                            <option value="1">启用</option>
                        </select>
                    </label>
                </li>
                <li style="line-height: 20px; width:200px">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_reset" class="btn btn-primary queryBtn" onclick="resetSearch()">查询重置</button>
                </li>
                <li>
                    <button id="btn_add_quota" class="btn btn-primary queryBtn" style="margin-bottom: 10px;">添加</button>
                </li>
            </ul>
        </div>
    </div>
    <div class="Manager_style">
        <div class="order_list">
            <table style="cursor:pointer;" id="afterLoanTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>省份</th>
                    <th>城市</th>
                    <th>状态</th>
                    <th>日进件量限制/件</th>
                    <th>周进件量限制/件</th>
                    <th>月进件量限制/件</th>
                    <th>日限额/万元</th>
                    <th>周限额/万元</th>
                    <th>月限额/万元</th>
                    <th>每单限额/元</th>
                    <th>启用/停用</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <%--区域限额添加--%>
    <div id="addGrade" style="display: none" >
        <div class="addCommon clearfix">
            <div>
                <%--<input  id="contract_id" type="hidden" />--%>
                <ul>
                    <li style="margin-left: 50px;margin-bottom: 10px;">
                        <label class="lf">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省份:</label>
                        <label>
                            &nbsp;&nbsp;
                            <select id="add_province" class="if" style="width:150px;height: 28px;" onchange="citySelAdd(this.options[this.options.selectedIndex].value,$('#add_city'))">
                                <option value="" style="font-size: 10px;">请选择</option>
                            </select>
                        </label>
                    </li>
                    <li style="margin-left: 50px;margin-bottom: 10px;">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;城市:</label>
                        <label>
                            &nbsp;&nbsp;
                            <select id="add_city" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                            </select>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否启用:</label>
                        <label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <select id="add_state" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf" >每日进件数量限额:</label>
                        <label>
                            <input id="number_day" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为件"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每周进件数量限额:</label>
                        <label>
                            <input id="number_week" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为件"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每月进件数量限额:</label>
                        <label>
                            <input id="number_month" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为件"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每日申请额度限额:</label>
                        <label>
                            <input id="quota_day" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每周申请额度限额:</label>
                        <label>
                            <input id="quota_week" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每月申请额度限额:</label>
                        <label>
                            <input id="quota_month" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">单笔申请额度限额:</label>
                        <label>
                            <input id="single_quota" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
    </div>
   <%--修改区域限额弹出框--%>
    <div id="changeQuota" style="display: none" >
        <div class="addCommon clearfix">
            <div>
                <%--<input  id="contract_id" type="hidden" />--%>
                <ul>
                    <li style="margin-left: 50px;margin-bottom: 10px;">
                        <label class="lf">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省份:</label>
                        <label>
                            &nbsp;&nbsp;
                            <select id="change_province" class="if" style="width:150px;height: 28px;"
                                    onchange="citySelAdd(this.options[this.options.selectedIndex].value,$('#change_city'))">
                                <option value="" style="font-size: 10px;">请选择</option>
                            </select>
                        </label>
                    </li>
                    <li style="margin-left: 50px;margin-bottom: 10px;">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;城市:</label>
                        <label>
                            &nbsp;&nbsp;
                            <select id="change_city" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                            </select>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否启用:</label>
                        <label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <select id="change_state" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf" >每日进件数量限额:</label>
                        <label>
                            <input id="change_day_number" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为件"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每周进件数量限额:</label>
                        <label>
                            <input id="change_week_number" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为件"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每月进件数量限额:</label>
                        <label>
                            <input id="change_month_number" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为件"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每日申请额度限额:</label>
                        <label>
                            <input id="change_day_quota" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每周申请额度限额:</label>
                        <label>
                            <input id="change_week_quota" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">每月申请额度限额:</label>
                        <label>
                            <input id="change_month_quota" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 10px;">
                        <label class="lf">单笔申请额度限额:</label>
                        <label>
                            <input id="change_single_quota" type="text" class="text_add" style="width:150px;height: 28px;" placeholder="单位为万元"/>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script>
</script>
</body>
</html>