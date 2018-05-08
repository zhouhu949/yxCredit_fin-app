<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/js/reportManage/reportManage.js${version}"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/areaQuota/city.data-3.js${version}"></script>
    <title>逾期</title>
    <style type="text/css">
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #sign_list{font-size:13px;}
        .checkShow,.uploadContact{color:#05c1bc}
        .checkShow{margin-right: 8px;}

        #divFrom input{border:none;background-color: #fff!important;text-align: left;}
        .unit{position: relative; top:1px;}
        /*合理性样式*/
        #showReasonable input[name=ReasonableInput],#showReasonable input[name=ReasonableInput_fitment],#showReasonable input[name=ReasonableInput_fitment_money]{border:1px solid #ddd}
        .ReasonableUl li{
            display: inline-block;
            width: 10%;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
        }
        .ReasonableUl li:hover{
            display: inline-block;
            width: 25%;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
        }
        .ReasonableUl li img{
            width: 100%;
            cursor: pointer;
        }
        .answerRemark,#elecRemark,#customerRemark,#giveAmount{width: 100%;position: relative;left:-10px;}
        #customerRemark,#elecRemark,#advice{width: 96%;margin-left: 20px;}
        .imagediv{
            width:150px;
            height:150px;
            background-color:white;
            text-align: center;
            float: left;
            position: relative;
            margin-right: 1em;
        }
        .addMaterial{
            width:150px;
            height:150px;
            vertical-align: middle;
        }
        .picShow{
            position:absolute;
            z-index:100;
            opacity:0;
            filter:alpha(opacity=0);
            height:150px;
            width:150px;
            readonly:true;
        }
        #firstCredit,#passRemark,#cheatRemark{width:95%;float:left;margin-left:0px;margin-left:4px;}
        #cheatRemark{width: 99%;}
        .telRecord{height: 37px;  text-align: left;  padding-left: 35px!important;}
        #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#answerBody td,#answerBody1 td{border:none!important;border-right:1px solid #ccc}
        #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}
        .answerTh th{text-align: center; font-weight: normal;border:none!important;}
        .answerTh thead{border:1px solid #ccc;border-top:none;background: #DFF1D9}
        .cencus{height: 40px;width:100%;line-height:40px;float:left;border-right:1px solid #ccc;border-left:1px solid #ccc;background-color: white; text-align: left;
            padding-left: 29px;
        }
        .MerInput{
            margin:.5em 0;
        }
        #MerMsg tr td{
            text-align: right;
            padding-left:0.2em;
        }
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
        /*显示图片*/
        #imageCard .imgShow,#cheatImg .imgShow,#phoneImg .imgShow{width:40px;height: 40px;float:left;margin-right:1em;}
        #imageCard .imgShowTd{padding-left: 1em;text-align:left}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        .align{width:110px;}
        #divFrom td{text-align: center;}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
        #divFrom .telRecord{text-align: left;font-weight: bold;}
        .picShow{
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 30px;
            width: 70px;
            top:1px;
            left:99px;
        }
        #contract_list{font-size: 13px;}
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
        .checkBoxLabel{
            margin-right: 10px;
        }
        .predictPrice , .salesmanName, .salesmanTel,.yhze,.orderCapital,.yufukuan,.payCount,.sureOrderTime,.orderState
        ,.yufukuanState,.deliveryState,.loanState,.jsState,.fahuoTime,.monthRate,.dayForDueDate,.businessType{
            display: none;
        }
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul >
                <li >
                    <label class="checkBoxLabel" style="margin-top: auto">选择列：</label>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>一级渠道</span>--%>
                    <%--<input type="checkbox" value="province" name="dx">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>二级渠道</span>--%>
                    <%--<input type="checkbox" value="city" name="dx">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>区域</span>--%>
                    <%--<input type="checkbox" value="district" name="dx">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>商户名称</span>--%>
                    <%--<input type="checkbox" value="merchantName" name="dx">--%>
                    <%--</label >--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>订单编号</span>--%>
                    <%--<input type="checkbox" value="">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>姓名</span>--%>
                    <%--<input type="checkbox" value="二级渠道">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>手机号</span>--%>
                    <%--<input type="checkbox" value="二级渠道">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>身份证号</span>--%>
                    <%--<input type="checkbox" value="">--%>
                    <%--</label>--%>
                    <%--<label class="checkBoxLabel">--%>
                    <%--&lt;%&ndash;<input name="s_content" type="text" class="text_add"/>&ndash;%&gt;--%>
                    <%--<span>商品名称</span>--%>
                    <%--<input type="checkbox" value="">--%>
                    <%--</label>--%>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>商品首付</span>
                        <input type="checkbox" value="predictPrice" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>办单员</span>
                        <input type="checkbox" value="salesmanName" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>办单员手机号</span>
                        <input type="checkbox" value="salesmanTel" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>应还总额</span>
                        <input type="checkbox" value="yhze" name="dx">
                    </label>
                    <%--</li>--%>
                    <%--<li style="margin-left: 70px;">--%>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>订单本金</span>
                        <input type="checkbox" value="orderCapital" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>预付款</span>
                        <input type="checkbox" value="yufukuan" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>期数</span>
                        <input type="checkbox" value="payCount" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>确认订单时间</span>
                        <input type="checkbox" value="sureOrderTime" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>订单状态</span>
                        <input type="checkbox" value="orderState" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>预付款状态</span>
                        <input type="checkbox" value="yufukuanState" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>发货状态</span>
                        <input type="checkbox" value="deliveryState" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>放款状态</span>
                        <input type="checkbox" value="loanState" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>结算状态</span>
                        <input type="checkbox" value="jsState" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>发货时间</span>
                        <input type="checkbox" value="fahuoTime" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>月息</span>
                        <input type="checkbox" value="monthRate" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>离还款日天数</span>
                        <input type="checkbox" value="dayForDueDate" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <%--<input name="s_content" type="text" class="text_add"/>--%>
                        <span>行业类型</span>
                        <input type="checkbox" value="businessType" name="dx">
                    </label>
                </li>
            </ul>
            <br>
            <div >
                <ul class="search_content clearfix" >
                    <li><label class="lf">商户名称</label>
                        <label>
                            <input name="customerName" type="text" id="merchantName" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">客户姓名</label>
                        <label>
                            <input name="customerName" type="text" id="customerName" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">手机号</label>
                        <label>
                            <input name="customerName" type="text" id="customerTel" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">身份证号</label>
                        <label>
                            <input name="customerName" type="text" id="customerCard" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">订单时间</label>
                        <label>
                            <input readonly="true" placeholder="开始" class="eg-date" id="orderBeginTime" type="text"/>
                            <span class="date-icon"><i class="icon-calendar"></i></span>
                        </label>
                    </li><span class="line-cut">--</span>
                    <li style="width:200px;">
                        <label>
                            <input readonly="true" placeholder="结束" class="eg-date" id="orderEndTime" type="text"/>
                            <span class="date-icon"><i class="icon-calendar"></i></span>
                        </label>
                    </li>
                    <li><label class="lf">订单本金</label>
                        <label>
                            <input name="customerName" type="text" id="orderCapitalStart" class="text_add"/>
                        </label>
                    </li><span class="line-cut">----</span>
                    <li style="margin-left: 45px;">
                        <label style="margin-left: -12px;">
                            <input name="customerName" type="text" id="orderCapitalEnd" class="text_add"/>
                        </label>
                    </li>
                    <li style="margin-left: -70px;"><label class="lf">订单编号</label>
                        <label>
                            <input name="customerName" type="text" id="orderNo" class="text_add"/>
                        </label>
                    </li>
                    <li style="width:500px; margin-left: 21px;">
                        <label class="lf">省</label>
                        <label>
                            <select id="province" style="min-width:90px;height: 28px; display: inline" onchange="citySelAdd(this.options[this.options.selectedIndex].value,$('#city'));">
                                <option value="">-请选择省份-</option>
                            </select>
                        </label>
                        <label class="lf">市</label>
                        <label>
                            <select id="city" style="min-width:90px;height: 28px;display: inline" onchange="areaSelAdd(this.options[this.options.selectedIndex].value,$('#district'));">
                                <option value="">-请选择市-</option>
                            </select>
                        </label>
                        <label class="lf">区&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <label>
                            <select id="district" style="min-width:90px;height: 28px;display: inline">
                                <option value="">-请选择区-</option>
                            </select>
                        </label>
                    </li>
                </ul>
                <%--<ul class="search_content clearfix">--%>
                <%--<li style="margin-right: 20px;"><label class="lf">订单状态：</label>--%>
                <%--<label class="if">--%>
                <%--&lt;%&ndash;<input name="customerName" type="text" id="orderState" class="text_add"/>&ndash;%&gt;--%>
                <%--<input type="checkbox" ><label class="if">已结算</label>--%>
                <%--<input type="checkbox" ><label class="if">未结算</label>--%>
                <%--<input type="checkbox" ><label class="if">已取消</label>--%>
                <%--</label>--%>
                <%--</li>--%>
                <%--<li style="margin-right: 20px;"><label class="lf">结算状态：</label>--%>
                <%--<label class="if">--%>
                <%--&lt;%&ndash;<input name="customerName" type="text" id="orderState" class="text_add"/>&ndash;%&gt;--%>
                <%--<input type="checkbox" ><span >已结算</span>--%>
                <%--<input type="checkbox" ><span >未结算</span>--%>
                <%--<input type="checkbox" ><span >已取消</span>--%>
                <%--</label>--%>
                <%--</li>--%>
                <%--<li><label class="lf">发货状态：</label>--%>
                <%--<label class="if">--%>
                <%--&lt;%&ndash;<input name="customerName" type="text" id="orderState" class="text_add"/>&ndash;%&gt;--%>
                <%--<input type="checkbox" ><span class="if">已结算</span>--%>
                <%--<input type="checkbox" ><span class="if">未结算</span>--%>
                <%--<input type="checkbox" ><span class="if">已取消</span>--%>
                <%--</label>--%>
                <%--</li>--%>

                <%--</ul>--%>
            </div>
            <ul>
                <li style="width:300px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button id="btn_excel" type="button" class="btn btn-primary queryBtn" onclick="toExcel()">导出excel</button>
                </li>
            </ul>
        </div>
        <div class="Manager_style" style="overflow:scroll;">
            <div class="order_list" style="width: 2500px;">
                <table style="cursor:pointer;" id="sign_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>一级渠道</th>
                        <th>二级渠道</th>
                        <th>区域</th>
                        <th>商户名称</th>
                        <th>订单编号</th>
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>身份证号</th>
                        <th>商品名称</th>
                        <th class="predictPrice">商品首付</th>
                        <th class="salesmanName">办单员</th>
                        <th>办单员手机号</th>
                        <th>应还总额</th>
                        <th>订单本金</th>
                        <th>预付款</th>
                        <th>期数</th>
                        <th>确认订单时间</th>
                        <th>订单状态</th>
                        <th>预付款状态</th>
                        <th>发货状态</th>
                        <th>放款状态</th>
                        <th>结算状态</th>
                        <th>发货时间</th>
                        <th>月息</th>
                        <th>离还款日天数</th>
                        <th>行业类型</th>
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

