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
    <script src="${ctx}/resources/js/goldOrder/overdueQuery.js${version}"></script>
    <%--<script src="${ctx}/resources/js/finalOrder/orderUntreated.js${version}"></script>
    <script src="${ctx}/resources/js/customerManage/reasonable.js${version}"></script>--%>
    <title>还款查询</title>
    <style>
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

        #plan-wrapper li span{
            display: inline-block;
            height: 40px;
            line-height: 40px;
            font-size: 16px;
            text-align: center;
        }
        #warningInfo table tr{
           border:1px solid #ddd
        }
        #warningInfo table th,#warningInfo table td{
            text-align: center;
            border:1px solid #ddd;
            padding: .5em;
        }
        #repaymentAllList th {
            border:1px solid #4a4f56;
            text-align: center;
        }
        #repaymentAllList td {
            border:1px solid #4a4f56;
            text-align: center;
        }
        #repaymentAllList tr{
            height: 30px;
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
                <%--<li><label class="lf">还款日期:</label>--%>
                    <%--<label>--%>
                        <%--<input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>--%>
                        <%--<span class="date-icon"><i class="icon-calendar"></i></span>--%>
                    <%--</label>--%>
                <%--</li><span class="line-cut">--</span>--%>
                <%--<li style="width:200px;">--%>
                    <%--<label>--%>
                        <%--<input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>--%>
                        <%--<span class="date-icon"><i class="icon-calendar"></i></span>--%>
                    <%--</label>--%>
                <%--</li>--%>
                <li style="width:200px"><label class="lf">是否逾期:</label>
                    <label>
                        <select name="isoverdue" id="isoverdue" type="text" class="text_add" style="min-width: 120px;height:30px;margin-left: .5em">
                            <option value="">请选择</option>
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </label>
                </li>
                <li style="line-height: 20px">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="order_list">

                <table style="text-align: center;font-size: 13px" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th >订单编号</th>
                        <th >客户名称</th>
                        <th >手机号码</th>
                        <th >放款金额</th>
                        <th>创建时间</th>
                        <th>应还金额</th>
                        <th>到期时间</th>
                        <th>减免金额</th>
                        <th>已还金额</th>
                        <th>待还金额</th>
                        <th>红包抵扣金额</th>
                        <%--<th>代账金额</th>--%>
                        <th>逾期费用</th>
                        <th>逾期状态</th>
                        <th>订单状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div style="display: none;" id="afterLoan">
                <br />
                <input type="hidden"  id="hdloanId" value=""/>
                <table style="cursor:pointer;" id="afterLoanTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th >还款编号</th>
                        <th>本期期数</th>
                        <th>本期还款时间</th>
                        <th>本期本金</th>
                        <th>本期利息</th>
                        <th>本期应还金额</th>
                        <th>本期红包抵扣金额</th>
                        <th>本期状态</th>
                        <th>本期逾期天数</th>
                        <th>本期逾期费用</th>
                        <th>本期减免金额</th>
                        <th>本期减免状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                </div>
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
        <%--催收--%>
        <div id="collection" style="display: none;padding: 0 10px;width: 1000px">
            <div class="block_hd" style="margin:10px 0;text-align: left">
                <s class="ico icon-file-text-alt"></s><span class="bl_tit" style="font-size: 18px;margin-left:6px;">还款计划</span>
                <button type="button" class="btn btn-primary" style="float: right" onclick="backColl()">返回</button>
            </div>
            <table style="cursor:pointer;" id="collectionTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
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
        <%--费用减免--%>
        <div id="settleDiv" style="display: none;padding: 1em;">
            <table  cellpadding="0" cellspacing="0" class="table  table-bordered ">
                <tr><td colspan="4" style="text-align: left">客户信息</td></tr>
                <tr>
                    <td style="text-align: right;width: 20%">客户名称：</td>
                    <td style="text-align: left;width: 30%"><label id="lblPersonName"></label></td>
                    <td style="text-align: right;width: 20%">联系电话</td>
                    <td style="text-align: left;width: 30%"><label id="lblTel"></label></td>
                </tr>
                <tr><td colspan="4" style="text-align: left">还款明细</td></tr>
                <tr>
                    <td style="text-align: right">期数：</td>
                    <td style="text-align: left"><label id="lblPayCount"></label></td>
                    <td style="text-align: right">本金：</td>
                    <td style="text-align: left"><label id="lblAmount"></label>元</td>
                </tr>
                <tr>
                    <td style="text-align: right">利息：</td>
                    <td style="text-align: left"><label id="lblFee"></label>元</td>
                    <td style="text-align: right">应还款额：</td>
                    <td   style="text-align: left"><label id="lblRepaymentAmount"></label>元


                </tr>
                <%--<tr>--%>
                    <%--<td style="text-align: right">平台管理费</td>--%>
                    <%--<td style="text-align: left"><label id="lblManageFee"></label>元</td>--%>
                    <%--<td style="text-align: right">快速审批费：</td>--%>
                    <%--<td style="text-align: left"><label id="lblQuickTrialFee"></label>元</td>--%>

                <%--</tr>--%>
                <tr>
                    <td style="text-align: right">逾期费用：</td>
                    <td style="text-align: left"><label id="lblPenalty"></label>元</td>
                    <td style="text-align: right">逾期天数：</td>
                    <td style="text-align: left"><label id="lblOverdueDays"></label>天</td>
                </tr>
                <tr>
                    <td style="text-align: right">还款总额：</td>
                    <td style="text-align: left"><label id="lblTotal"></label>元</td>
                    <td style="text-align: right">减免金额：</td>
                    <td   style="text-align: left">
                        <input name="reductionAmount" id="reductionAmount" type="text"  style="width: 60px" />元  <label id="lbl1" style="color:red;font-size:10px;"></label>
                    </td>
                </tr>
            </table>
        </div>

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
        <!--提前结清-->
        <div style="display: none;" id="Settle">
            <table class="tb_info" style="font-size:12px;width: 99.8%;text-align: center" id="repaymentAllList" >
            </table>
            <br/>
            <br/>
        </div>
    </div>
</div>
<script>
</script>
</body>
</html>