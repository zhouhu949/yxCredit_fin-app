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
    <script src="${ctx}/resources/js/merchantBlack/merchantBlack.js${version}"></script>
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
                <li style="width: 400px"><label class="lf">类型：</label>
                    <label>
                        <select id="search_state" style="min-width: 120px;height:30px;margin-left: .5em" >
                            <option value="">请选择</option>
                            <option value="1">商户名称</option>
                            <option value="2">营业执照注册号</option>
                            <option value="3">法人身份证号</option>
                        </select>
                    </label>
                    <label>
                        <input type="text" id="content" placeholder="输入关键字查询" style="height:30px;">
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
                <li style="line-height: 20px; width: 200px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn" onclick="resetSearch()">查询重置</button>
                </li>
                <li style="line-height: 20px; width: 600px;">
                    <button id="btn_add_black" class="btn btn-primary queryBtn">添加</button>
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
                    <th>类型</th>
                    <th>内容</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <%--添加按钮弹出窗--%>
    <div id="addBlack" style="display: none" >
        <div class="addCommon clearfix">
            <div>
                <%--<input  id="contract_id" type="hidden" />--%>
                <ul>
                    <li style="margin-left: 50px;margin-bottom: 25px;margin-top: 30px;">
                        <label class="lf">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型:</label>
                        <label>
                            &nbsp;&nbsp;
                            <select id="add_type" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                                <option value="1">商户名称</option>
                                <option value="2">营业执照注册号</option>
                                <option value="3">法人身份证号</option>
                            </select>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 25px;">
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
                    <li style="margin-left: 50px;margin-bottom: 25px; ">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容:</label>
                        <label>
                            <%--<input id="add_content" type="text" class="text_add" style="width:150px;height: 28px;"/>--%>
                            <textarea id="add_content" class="text_add" style="height: 150px; width: 200px;"></textarea>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <%--修改按钮弹出窗--%>
    <div id="editBlack" style="display: none" >
        <div class="addCommon clearfix">
            <div>
                <%--<input  id="contract_id" type="hidden" />--%>
                <ul>
                    <li style="margin-left: 50px;margin-bottom: 15px;margin-top: 30px;">
                        <label class="lf">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型:</label>
                        <label>
                            &nbsp;&nbsp;
                            <select id="edit_type" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                                <option value="1">商户名称</option>
                                <option value="2">营业执照注册号</option>
                                <option value="3">法人身份证号</option>
                            </select>
                        </label>
                    </li>
                    <li style="margin-left: 30px;margin-bottom: 15px;">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否启用:</label>
                        <label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <select id="edit_state" class="if" style="width:150px;height: 28px;">
                                <option value="">请选择</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            &nbsp;&nbsp; &nbsp;&nbsp;
                        </label>
                    </li>
                    <li style="margin-left: 50px;margin-bottom: 15px;">
                        <label class="lf" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容:</label>
                        <label>
                            <%--<input id="edit_content" type="text" class="text_add" style="width:150px;height: 28px;"/>--%>
                            <textarea id="edit_content" class="text_add" style="height: 150px; width: 200px;"></textarea>
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