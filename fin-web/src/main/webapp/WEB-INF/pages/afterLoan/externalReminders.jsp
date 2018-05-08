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
    <script src="${ctx}/resources/js/afterLoan/externalReminders.js${version}"></script>
    <%--<script src="${ctx}/resources/js/finalOrder/orderUntreated.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>--%>
    <title>外访催收</title>
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
                <li><label class="lf">客户名称:</label>
                    <label>
                        <input name="customerName" id="custName" type="text" class="text_add"/>
                    </label>
                </li>
                <li><label class="lf">联系电话:</label>
                    <label>
                        <input name="customerName" id="custTel" type="text" class="text_add"/>
                    </label>
                </li>
                <li style="line-height: 20px">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                </li>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="afterLoanTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th></th>
                        <th>进入外催时间</th>
                        <th>客户姓名</th>
                        <th>联系电话</th>
                        <th>外催状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="Manager_style" style="display: none;padding: 10px;width: 1100px;" id="collContainer">
            <input type="text" id="loanId" style="display: none">
            <div class="block_hd" style="margin-bottom: 10px; text-align: left">
                <s class="ico icon-file-text-alt"></s><span class="bl_tit" style="font-size: 18px;margin-left:6px;">催收记录</span>

                <button id="btn_back" type="button" class="btn btn-primary" style="float: right" onclick="backColl()">返回</button>
                <button id="btn_add" type="button" class="btn btn-primary" style="float: right" onclick="addColl()">增加</button>
            </div>
            <table style="cursor:pointer;" id="collRecordTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>拨打电话</th>
                    <th>拨打时间</th>
                    <th>与客关系</th>
                    <th>催收备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

        <%--//对应的贷款订单--%>
        <div class="Manager_style" id="cus_order" style="display: none">
            <br /><br />
            <span style="font-size: 15px;margin: 10px 0px 0px 10px">对应的贷款订单</span>
            <div class="order_list">
                <table style="cursor:pointer;" id="order_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单编号</th>
                        <%--<th>结算渠道</th>--%>
                        <th>产品名称</th>
                        <%--<th>还款方式</th>--%>
                        <th>期数</th>
                        <th>年利化率(%)</th>
                        <th>授信金额(元)</th>
                        <th>合同金额(元)</th>
                        <th>创建时间</th>
                        <th>操作</th>
                        <th style="display:none">id</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%--新增更进form表单--%>
        <form name="collForm" id="collForm" method="post">
            <div id="Add_coll" style="display: none">
                <div class="commonManager ">
                    <div class="addCommon">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">拨打电话:</label>
                                <label>
                                    <input name="tel" id ="callTel" type="text" value="" />
                                </label>
                            </li>
                            <li>
                                <label class="label_name">拨打时间:</label>
                                <label>
                                    <input name="callTime" type="text" value="" id="callTime" />
                                </label>
                            </li>
                            <li>
                                <label class="label_name">与客关系:</label>
                                <label>
                                    <input name="relationship" type="text" value="" id="relationship" />
                                </label>
                            </li>
                        </ul>
                        <div class="Remark" style="padding-top: 20px;margin-left:5px;">
                            <label class="label_name">催收备注:</label>
                            <label>
                                <textarea name="remark" id ="remark" cols="" rows="" style="width: 430px; height: 100px; padding: 5px;"></textarea>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <%--还款计划--%>
        <div id="planList" style="display: none;padding: 0 10px;width: 1000px">
            <div class="block_hd" style="margin:10px 0;text-align: left">
                <s class="ico icon-file-text-alt"></s><span class="bl_tit" style="font-size: 18px;margin-left:6px;">还款计划</span>
                <button type="button" class="btn btn-primary" style="float: right" onclick="backColl()">返回</button>
            </div>
            <table style="cursor:pointer;" id="planListTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>期数</th>
                    <th>还款日期</th>
                    <th>本金</th>
                    <th>利息</th>
                    <th>月还款金额</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <%--分配外访用户--%>
        <div id="userDiv" style="display: none;padding: 1em;">
            <div style="margin-bottom: 10px;">
                <input name="account" id="account" placeholder="(账号)" type="text" class="text_add"/>
                <input name="trueName" id="trueName" placeholder="(姓名)" type="text" class="text_add"/>
                <input name="mobile" id="mobile" placeholder="(手机号码)" type="text" class="text_add"/>
                <button id="user_btn_search"  type="button" class="btn btn-primary queryBtn" style="  margin-bottom: .5em;">查询</button>
                <button id="user_btn_search_reset"  type="button" class="btn btn-primary queryBtn" style="margin-bottom: .5em;">查询重置</button>
            </div>
            <table style="cursor:pointer;" id="user_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>
                        <input name="userCheckBox_All" id="userCheckBox_All" type="checkbox"  class="ace" isChecked="false" />
                        <span class="lbl" style="cursor:pointer;"></span>
                    </th>
                    <th>帐号</th>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <%--结清操作--%>
        <div id="settleDiv" style="display: none;padding: 1em;">
            <table  cellpadding="0" cellspacing="0" class="table  table-bordered ">
                <tr><td colspan="4" style="text-align: left">客户信息</td></tr>
                <tr>
                    <td style="text-align: right;width: 20%">客户名称：</td>
                    <td style="text-align: left;width: 30%"><label id="lblPersonName"></label></td>
                    <td style="text-align: right;width: 20%">联系电话</td>
                    <td style="text-align: left;width: 30%"><label id="lblTel"></label></td>
                </tr>
                <tr><td colspan="4" style="text-align: left">逾期明细</td></tr>
                <tr>
                    <td style="text-align: right">期数：</td>
                    <td style="text-align: left"><label id="lblPayCount"></label></td>
                    <td style="text-align: right">利息：</td>
                    <td style="text-align: left"><label id="lblFee"></label>元</td>
                </tr>
                <tr>
                    <td style="text-align: right">平台管理费</td>
                    <td style="text-align: left"><label id="lblManageFee"></label>元</td>
                    <td style="text-align: right">快速审批费：</td>
                    <td style="text-align: left"><label id="lblQuickTrialFee"></label>元</td>
                </tr>
                <tr>
                    <td style="text-align: right">是否减免：</td>
                    <td  style="text-align: left">
                        <select name="sltReduction" id="sltReduction" type="text" style="min-width: 120px;height:30px;margin-left: .5em" onchange="sltReductionOnchange()">
                            <option value="">请选择</option>
                            <option value="1">否</option>
                            <option value="0">是</option>
                        </select>
                    </td>
                    <td style="text-align: right">减免金额：</td>
                    <td   style="text-align: left">
                        <input name="reductionAmount" id="reductionAmount" type="text" readonly="readonly" style="width: 60px" />元  <label id="lbl1" style="color:red;font-size:10px;"></label>
                    </td>
                </tr>
                <tr> <td style="text-align: right">还款总额：</td>
                    <td  colspan="3" style="text-align: left"><label id="lblAmount"></label>元
                    </td>
                </tr>
            </table>
        </div>
        <%--查看--%>
        <div id="container" class="of-auto_H" style="padding:20px;display:none;">
            <div class="block_hd" style="margin-right: 1390px">
                <button id="btn_load" type="button"  class="btn btn-primary queryBtn">借款申请信息</button>
                <button id="btn_plus" type="button" class="btn btn-primary queryBtn">上传素材</button>
            </div>
            <div id="loanInfo" style="display: none">
                <div class="paddingBox xdproadd" style="width:920px">
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">借款申请信息</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="tdTitle">申请省份：</td>
                                <td id="liveProvinces"></td>
                                <td class="tdTitle">申请城市：</td>
                                <td id="liveCity"></td>
                                <td class="tdTitle">希望期数：</td>
                                <td id="vehiclePrice"></td>
                            </tr>
                            <tr >
                                <td class="tdTitle">申请金额：</td>
                                <td id="applay_money"></td>
                                <td class="tdTitle">希望的还款方式：</td>
                                <td id="vehicleSecondHand"></td>
                                <td class="tdTitle">月供能力：</td>
                                <td id="">0元</td>
                            </tr>
                            <tr >
                                <td class="tdTitle">可接受每周最高还款额：</td>
                                <td id="vehicleMortgage"></td>
                                <td class="tdTitle">借款用途：</td>
                                <td id="vehicleColour">购物</td>
                                <td class="tdTitle">预计借款期限：</td>
                                <td id="mortgageDate"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">产品信息</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="tdTitle">产品系列：</td>
                                <td id="product_type_name"></td>
                                <td class="tdTitle">产品期数：</td>
                                <td id="periods"></td>
                                <td class="tdTitle">产品名称：</td>
                                <td id="product_name_name"></td>
                            </tr>
                            <tr >
                                <td class="tdTitle">产品利率：</td>
                                <td id="multipleRate"></td>
                                <td class="tdTitle">还款方式：</td>
                                <td id="repayType"></td>
                                <td class="tdTitle">授信范围：</td>
                                <td id="actualLimit"></td>
                            </tr>
                            <tr >
                                <td class="tdTitle">备注：</td>
                                <td colspan="5" id="interestRemark"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">您的个人资料</span>
                        </div>
                        <table class="tb_info " style="font-size:12px;">
                            <tbody>
                            <tr>
                                <td class="tdTitle">姓名：</td>
                                <td id="realname"></td>
                                <td class="tdTitle">性别：</td>
                                <td id="sex"></td>
                                <%--<td class="tdTitle">曾用名：</td>--%>
                                <%--<td id="oldName"></td>--%>
                                <td class="tdTitle">婚姻状况：</td>
                                <td id="marry"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">教育程度：</td>
                                <td id="education"></td>
                                <td class="tdTitle">毕业院校：</td>
                                <td id="school" colspan="3"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">微信号：</td>
                                <td id="weixin"></td>
                                <td class="tdTitle">QQ：</td>
                                <td id="qq"></td>
                                <td class="tdTitle">电子邮箱：</td>
                                <td  id="email"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">手机号码：</td>
                                <td id="cusTel"></td>
                                <%--<td class="tdTitle">证件有效期：</td>--%>
                                <%--<td id="valid_term"></td>--%>
                                <td class="tdTitle">身份证号码：</td>
                                <td id="cusCard"></td>
                                <td class="tdTitle">现居地生活时长(年)：</td>
                                <td id="addresslivetime"></td>
                            </tr>
                            <%--<tr>--%>
                            <%--<td class="tdTitle">户籍：</td>--%>
                            <%--<td colspan="3" id="card_address"></td>--%>
                            <%--<td class="tdTitle">本市生活时长：</td>--%>
                            <%--<td id="citylivetime"></td>--%>
                            <%--</tr>--%>
                            <tr>
                                <td class="tdTitle">房产归属：</td>
                                <td id="house"></td>
                                <td class="tdTitle">电商账号：</td>
                                <td id="222"></td>
                                <td class="tdTitle">电商密码：</td>
                                <td id="111"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">现居住地址：</td>
                                <td colspan="5" id="nowaddress"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">职业信息</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;display: none" id="wageEarner" hidden><%----工薪者---%>
                            <tbody>
                            <tr>
                                <td class="tdTitle">职业归属:</td>
                                <td id ="wageEarnerType"  colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">单位名称:</td>
                                <td id ="company"></td>
                                <td class="tdTitle">公司地址:</td>
                                <td id ="earnerPcd" colspan="3"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">详细地址:</td>
                                <td id ="company_address" colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">工作月收入(元):</td>
                                <td id = "basic_monthly_pay"></td>
                                <td class="tdTitle">单位电话:</td>
                                <td id ="company_fixedphone"></td>
                                <td class="tdTitle">何时进入公司:</td>
                                <td id ="entering_the_company"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="tb_info" style="font-size:12px;display: none" id="operator" hidden><%----经营者---%>
                            <tbody>
                            <tr>
                                <td class="tdTitle">职业归属:</td>
                                <td id ="operatorType"  colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">企业名称:</td>
                                <td id ="enterprise"></td>
                                <td class="tdTitle">企业经营年限(年):</td>
                                <td id ="management_years"></td>
                                <td class="tdTitle">过去一年经营收入(元):</td>
                                <td id = "year_income"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">经营地址:</td>
                                <td id ="managerPcd" colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">详细地址:</td>
                                <td id ="management_address" colspan="5"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="tb_info" style="font-size:12px;display: none" id="professional" hidden><%----自由职业者---%>
                            <tbody>
                            <tr>
                                <td class="tdTitle">职业归属:</td>
                                <td id ="professionalType"  colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">收入来源:</td>
                                <td id ="source_income"></td>
                                <td class="tdTitle">月均收入金额(元):</td>
                                <td id ="income_month"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">已从事此项工作年限(年):</td>
                                <td id ="work_years"></td>
                                <td class="tdTitle">相关职业资格证书:</td>
                                <td id = "certificate_name"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="tb_info" style="font-size:12px;display: none" id="readingStu" hidden><%----在读学生---%>
                            <tbody>
                            <tr>
                                <td class="tdTitle">职业归属:</td>
                                <td id ="readingStuType"  colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">学校名称:</td>
                                <td id ="school_name"></td>
                                <td class="tdTitle">班级:</td>
                                <td id = "stu_class"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">学校地址:</td>
                                <td id = "school_address" colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">院系:</td>
                                <td id ="stu_department" colspan="5"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="tb_info" style="font-size:12px;display: none" id="retiree" ><%----退休人员---%>
                            <tbody>
                            <tr>
                                <td class="tdTitle">职业归属:</td>
                                <td id ="retireeType"  colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">原单位名称:</td>
                                <td id ="former_company"  colspan="5"></td>
                            </tr>
                            <tr>
                                <td class="tdTitle">月退休金额(元):</td>
                                <td id ="pension_month"></td>
                                <td class="tdTitle">其他收入来源:</td>
                                <td id ="other_source_income"></td>
                                <td class="tdTitle">已退休年限(年):</td>
                                <td id = "retire_years"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--<div class="paperBlockfree" style="display: none" id ="income">--%>
                    <%--<div class="block_hd" style="float:left;">--%>
                    <%--<s class="ico icon-file-text-alt"></s><span class="bl_tit">收支状况</span>--%>
                    <%--</div>--%>
                    <%--<table class="tb_info" style="font-size:12px;">--%>
                    <%--<tbody>--%>
                    <%--<tr>--%>
                    <%--<td class="tdTitle">年收入：</td>--%>
                    <%--<td id="annualIncome"></td>--%>
                    <%--<td class="tdTitle">每月工作收入：</td>--%>
                    <%--<td id="month_income"></td>--%>
                    <%--</tr>--%>
                    <%--<tr >--%>
                    <%--<td class="tdTitle">月均支出：</td>--%>
                    <%--<td id="month_average_expense"></td>--%>
                    <%--<td class="tdTitle">供养人数：</td>--%>
                    <%--<td id="support_num"></td>--%>
                    <%--</tr>--%>
                    <%--</tbody>--%>
                    <%--</table>--%>
                    <%--</div>--%>
                    <div class="paperBlockfree">
                        <div class="block_hd" style="float:left;">
                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">联系人信息</span>
                        </div>
                        <table class="tb_info" style="font-size:12px;">
                            <thead>
                            <tr>
                                <th style="text-align: center;">直系联系人</th>
                                <th style="text-align: center;">关系</th>
                                <th style="text-align: center;">联系电话</th>
                                <th style="text-align: center;">工作单位</th>
                                <th style="text-align: center;">是否知晓此次贷款申请</th>
                            </tr>
                            </thead>
                            <tbody id="relation">
                            </tbody>
                        </table>
                        <br>
                    </div>
                </div>
            </div>
            <div class="paperBlockfree" style="width:60%;float: left;display: none" id ="imgInfo">
                <div class="block_hd" style="float:left;" onclick="shrink(this)">
                    <s class="ico icon-file-text-alt"></s><span class="bl_tit">个人基本材料</span>
                </div>
                <table class="tb_info" style="font-size:12px;">
                    <thead>
                    <tr>
                        <th style="background: #DEF0D8;height:43px">图片资料</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="tdTitle align" id="csContainer" style="text-align: left"><ul id="personInfo"></ul></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%--<div id="imgInfo" style="display: none">--%>
            <%--<div class="paddingBox xdproadd" style="width:920px">--%>
            <%--<div class="paperBlockfree">--%>
            <%--<div class="block_hd" style="float:left;">--%>
            <%--<s class="ico icon-file-text-alt"></s><span class="bl_tit">个人基本材料</span>--%>
            <%--</div>--%>
            <%--<table class="tb_info" style="font-size:12px;">--%>
            <%--<tbody>--%>
            <%--<tr class="tb_head t-bold ">--%>
            <%--<td width="15%">材料名称</td>--%>
            <%--<td width="40%">文件名称</td>--%>
            <%--<td width="15%">上传时间</td>--%>
            <%--<td width="15%">操作人</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td><span class="red">*</span>身份证</td>--%>
            <%--<td id="file9"></td>--%>
            <%--<td id="ctime9"></td>--%>
            <%--<td id="empName9"></td>--%>
            <%--</tr>--%>
            <%--</tbody>--%>
            <%--</table>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<script>
</script>
</body>
</html>