<%--
  魔盒数据展示
  User: 陈清玉
  Date: 2018/5/20
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>运营商</title>
    <script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js${version}"></script>
    <%--<link rel="stylesheet" href="${ctx}/resources/css/moheyys.css">--%>
    <script src="${ctx}/resources/js/Operator_jrl.js"></script>
    <style>
        html,body, div, ul, li, ol, h1, h2, h3, h4, h5, h6, input, textarea, select, p, dl, dt, dd, a, img, button, form, table, th, tr, td, tbody, article, aside, details, figcaption, figure, footer, header, menu, nav, section {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "PingFang-SC-Medium";
        }
        html,body{
            height: 100%;
        }
        fieldset, img, input, button, textarea {
            border: none;
            outline-style: none;
        }
        ul, ol {
            list-style: none;
        }

        h1, h2, h3, h4, h5, h6 {
            text-decoration: none;
            font-weight: normal;

        }
        .clearfix:before, .clearfix:after {
            content: "";
            display: table;
        }
        .clearfix:after {
            clear: both;
        }
        .fl {
            float: left;
        }
        .fr {
            float: right;
        }

        .main {
            width: 1200px;
            margin: 0 auto;
            padding-bottom: 30px;
            background-color: #f3f3f3;
        }
        .header h3 {
            font-size: 30px;
            line-height: 50px;
            text-align: center;
        }
        .container {
            font-size: 14px;
            color: #333;
        }
        .title {
            color: #0d9bdc;
            font-size: 16px;
            background-color: #d1d1f3;
            padding: 6px 20px;
        }
        .title h5 {
            border-left: 4px solid #0d9bdc;
            text-indent: 6px;
            font-weight: 700;
        }
        .details {
            padding: 10px 20px;
        }
        .content li {
            width: 50%;
        }
        .content li span,
        .content li input {
            display: block;
            line-height: 30px;
            text-indent: 6px;
            float: left;
        }
        .content li span {
            width: 40%;
            background-color: #d1d1f3;
            border-bottom: 1px solid #fff;
        }
        .content li input {
            width: 60%;
            background-color: #fff;
            border-bottom: 1px solid #eee;
        }
        .serialNumber {
            font-weight: 700;
            margin-bottom: 10px;
        }
        .table {
            background-color: #fff;
            border-collapse: collapse;
            text-align: center;
            width: 100%;
        }
        .table td {
            border: 1px solid #eee;
            line-height: 30px;
        }
        .table .thead {
            background-color: #d1d1f3;
        }
        .table .thead td {
            border: 1px solid #fff;
        }
        .btnPage {
            position: relative;
            height: 40px;
        }
        .btnPage .lastPage,
        .btnPage .nextPage {
            position: absolute;
            width: 60px;
            line-height: 30px;
            text-align: center;
            border-radius: 5px;
            background-color: #fff;
            right: 0px;
            top: 10px;
        }
        .btnPage .lastPage {
            right: 70px;
        }
        .recordsTime {
            margin-bottom: 10px;
        }
        .recordsTime li {
            font-weight: 700;
            margin-right: 30px;
            line-height: 30px;
        }
        .recordsTime .now {
            color: #0d9bdc;
            border-bottom: 2px solid #0d9bdc;
        }
        .recordsTime li:hover {
            color: #0d9bdc;
            border-bottom: 2px solid #0d9bdc;
        }
        .noRecord {
            line-height: 100px;
            text-align: center;
            color: #999;
            padding: 10px 20px;
            background-color: #fff;
        }
    </style>
</head>
<body>
<div class="main">
    <div class="header">
        <h3>运营商报告</h3>
    </div>
    <div class="container">
        <div class="accountInfo">
            <div class="title"><h5>账户信息</h5></div>
            <div class="details">
                <ul class="content clearfix">
                    <li class="fl clearfix">
                        <span>姓名</span>
                        <input type="text" readonly  id="operatorUserName">
                    </li>
                    <li class="fl clearfix">
                        <span>身份证号码</span>
                        <input type="text" readonly id="operatorCertNum">
                    </li>
                    <li class="fl clearfix">
                        <span>手机号码</span>
                        <input type="text" readonly id="operatorUserNumber">
                    </li>
                    <li class="fl clearfix">
                        <span>账户余额</span>
                        <input type="text" readonly id="operatorAccountBalance">
                    </li>
                    <li class="fl clearfix">
                        <span>当前话费</span>
                        <input type="text" readonly id="operatorCurrentFee">
                    </li>
                    <li class="fl clearfix">
                        <span>账户状态</span>
                        <input type="text" readonly id="operatorMobileStatus">
                    </li>
                    <li class="fl clearfix">
                        <span>账户星级</span>
                        <input type="text" readonly id="operatorCreditLevel">
                    </li>
                    <li class="fl clearfix">
                        <span>入网时间</span>
                        <input type="text" readonly id="operatorNetTime">
                    </li>
                    <li class="fl clearfix">
                        <span>实名制信息</span>
                        <input type="text" readonly id="operatorRealInfo">
                    </li>
                    <li class="fl clearfix">
                        <span>积分</span>
                        <input type="text" readonly id="operatorCreditPoint">
                    </li>
                </ul>
            </div>
        </div>
        <div class="paymentRecords">
            <div class="title"><h5>缴费记录</h5></div>
            <div class="details">
                <span id="operatorPaymentRecordTable"></span>
                <div class="btnPage" id="operatorPaymentRecordUl"></div>
            </div>
        </div>
        <div class="callRecords">
            <div class="title"><h5>通话记录</h5></div>
            <div class="details">
                <ul class="recordsTime clearfix">
                    <li role="tab" class="now fl" onclick="operatorCallCycleTab('1','0')" data-reactid="176"><span id="operatorCallCycle0"></span></li>
                    <li role="tab" class="fl" onclick="operatorCallCycleTab('1','1')" data-reactid="177"><span id="operatorCallCycle1"></span></li>
                    <li role="tab" class="fl" onclick="operatorCallCycleTab('1','2')" data-reactid="178"><span id="operatorCallCycle2"></span></li>
                    <li role="tab" class="fl" onclick="operatorCallCycleTab('1','3')" data-reactid="179"><span id="operatorCallCycle3"></span></li>
                    <li role="tab" class="fl" onclick="operatorCallCycleTab('1','4')" data-reactid="180"><span id="operatorCallCycle4"></span></li>
                    <li role="tab" class="fl" onclick="operatorCallCycleTab('1','5')" data-reactid="181"><span id="operatorCallCycle5"></span></li>
                </ul>
                <div class="recordTable">
                    <span id="operatorCallCycleTabTable"></span>
                    <div class="btnPage" id="operatorCallInfoRecordUl"></div>
                </div>
                <!-- <div class="noRecord">暂无记录</div> -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(function(){
        loadOperator();
    })
    //保留两位小数
    function operatorDecimal(value){
        var newValue = parseFloat(value) / 100;
        return newValue.toFixed(2);
    }

    //加载运营商
    function loadOperator(){
        $.ajax({
            type: "GET",
            url: "../apiResult/tongDun/${param.resultId}/${param.customerId}",
            dataType:"json",
            success: function (data) {
                //基本信息
                var baseInfo = data.data;
                $("#operatorUserName").val(baseInfo.custName);//姓名
                $("#operatorUserSex").val(baseInfo.user_sex);//性别
                $("#operatorUserNumber").val(baseInfo.custMobile);//手机号码
                $("#operatorCertNum").val(baseInfo.custIc);//身份证号码
                $("#operatorCertAddr").val(baseInfo.cert_addr);//联系地址
                $("#operatorUserContactNo").val(baseInfo.user_contact_no);//联系电话
                $("#operatorUserEmail").val(baseInfo.user_email);//邮箱地址

                var apiResult = data.data.apiResult;
                //账户信息
                var accountInfo = apiResult.account_info;
                $("#operatorAccountBalance").val(operatorDecimal(accountInfo.account_balance));//账户余额。整形数字精确到分
                $("#operatorCurrentFee").val(operatorDecimal(accountInfo.current_fee));//当前话费。整形数字精确到分
                $("#operatorCreditLevel").val(accountInfo.credit_level);//帐户级别
                $("#operatorNetTime").val(accountInfo.net_time);//入网时间
                $("#operatorNetAge").val(accountInfo.net_age);//网龄
                $("#operatorRealInfo").val(accountInfo.real_info);//实名制信息
                $("#operatorCreditPoint").val(accountInfo.credit_point);//积分。整形数字
                $("#operatorBrandName").val(accountInfo.brand_name);//品牌
                $("#operatorPayType").val(accountInfo.pay_type);//缴费类型
                $("#operatorMobileStatus").val(accountInfo.mobile_status);//账户状态。正常、欠费、停机、销户、未激活、未知


                //缴费记录
                paymentInfo = apiResult.payment_info;
                operatorPaymentInfoRecordTable(1,paymentInfo);
                operatorPaymentInfoRecord = 1;
                operatorUlPageFun(paymentInfo,"operatorPaymentRecordUl","PaymentInfoRecord");


                //通话记录
                operatorCallInfo = apiResult.call_info;
                //最近六个月的通话记录
                $("#operatorCallCycle0").html(operatorCallInfo[0].call_cycle);
                $("#operatorCallCycle1").html(operatorCallInfo[1].call_cycle);
                $("#operatorCallCycle2").html(operatorCallInfo[2].call_cycle);
                $("#operatorCallCycle3").html(operatorCallInfo[3].call_cycle);
                $("#operatorCallCycle4").html(operatorCallInfo[4].call_cycle);
                $("#operatorCallCycle5").html(operatorCallInfo[5].call_cycle);

                operatorCallInfoCallRecordIndex = 0;
                operatorCallInfoCallRecordIndexPage = 1;
                operatorCallCycleTab(1,operatorCallInfoCallRecordIndex);
                operatorUlPageFun(operatorCallInfo[operatorCallInfoCallRecordIndex].call_record,"operatorCallInfoRecordUl","CallInfoRecord");



            },
            error: function (XMLHttpRequest, textStatus, thrownError) {
                layer.msg("查询运营商数据失败");
            }

        });
    }
    //运营商分页
    function operatorUlPageFun(data,ulId,recordType)
    {
        $("#"+ulId).html(" ");
        var row = data.length;
        var newRow = 1;
        if(row>10){
            newRow = row % 10 ==0? row/10 : (row/10)+1;
        }
        var ul=$("#"+ulId);
        ul.append("<span title='上一页' class='lastPage' data-reactid ='395' onclick='operator"+recordType+"PreviousPage()'>上一页</span>");//动态生成
        // for(var i=1;i<=newRow;i++){
        //     ul.append("<li class='ant-pagination-item ant-pagination-item-1 ant-pagination-item-active' data-reactid ='395' onclick='operator"+recordType+"Page("+i+")'>"+i+"</li>");//动态生成
        // }
        ul.append("<span  class='nextPage' data-reactid ='395' onclick='operator"+recordType+"NextPage()'>下一步</span>");//动态生成

    }


    /*****************************************缴费记录*********************************************/
//缴费记录上一页
    function operatorPaymentInfoRecordPreviousPage(){
        if(operatorPaymentInfoRecord==1){
            return;
        }
        operatorPaymentInfoRecord = operatorPaymentInfoRecord - 1;
        operatorPaymentInfoRecordPage(operatorPaymentInfoRecord);
    }
    //缴费记录跳转页
    function operatorPaymentInfoRecordPage(page){
        operatorPaymentInfoRecord = page;
        operatorPaymentInfoRecordTable(page,paymentInfo);
    }
    //缴费记录下一页
    function operatorPaymentInfoRecordNextPage(){
        if(operatorPaymentInfoRecord == pageCount(paymentInfo)){
            return;
        }
        operatorPaymentInfoRecord = operatorPaymentInfoRecord + 1;
        operatorPaymentInfoRecordPage(operatorPaymentInfoRecord);
    }


    //缴费记录列表
    function operatorPaymentInfoRecordTable(pageSize, data) {
        var index = (pageSize - 1) * 10;
        var row = data.length>10?pageSize * 10:data.length;
        $("#operatorPaymentRecordTable").html(" ");
        var table = $("<table data-toggle='table' class='table'>");
        table.appendTo($("#operatorPaymentRecordTable"));
        var tr0 = $("<tr  class='thead'></tr>").appendTo(table);
        $("<td style='border-bottom:1px solid #ccc;height: 30px'>日期</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>金额</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>缴费方式</td>").appendTo(tr0);
        for (var i = index; i < row; i++) {
            var item = data[i];
            if (item) {
                var tr = $("<tr class='table_math' style='text-align: center;'></tr>").appendTo(table);
                $("<td  style='border-bottom:1px solid #ccc;height: 30px'>"+item.pay_date+"</td>").appendTo(tr);
                $("<td style='border-bottom:1px solid #ccc;height: 30px'>"+operatorDecimal(item.pay_fee)+"</td>").appendTo(tr);
                $("<td  style='border-bottom:1px solid #ccc;height: 30px'>"+item.pay_type+"</td>").appendTo(tr);
            }
        }
        $("#operatorPaymentRecordTable").append("</table>");
    }

    /*********************************************通话记录**********************************************************/

//通话记录上一页
    function operatorCallInfoRecordPreviousPage(){
        if(operatorCallInfoCallRecordIndexPage == 1){
            return;
        }
        operatorCallInfoRecordPage(operatorCallInfoCallRecordIndexPage);
        operatorCallInfoCallRecordIndexPage = operatorCallInfoCallRecordIndexPage - 1;

    }
    //通话记录跳转页
    function operatorCallInfoRecordPage(page){
        operatorCallInfoCallRecordIndexPage = page;
        operatorCallCycleTab(page,operatorCallInfoCallRecordIndex);
    }


    //通话记录下一页
    function operatorCallInfoRecordNextPage(){
        if(operatorCallInfoCallRecordIndexPage == pageCount(operatorCallInfo[operatorCallInfoCallRecordIndex].call_record)){
            return;
        }
        operatorCallInfoCallRecordIndexPage = operatorCallInfoCallRecordIndexPage + 1;
        operatorCallInfoRecordPage(operatorCallInfoCallRecordIndexPage);
    }

    function operatorCallCycleColor(index){
        for(var i=0;i<6;i++){
            $("#operatorCallCycle"+i).parent().removeClass("now");
        }
        $("#operatorCallCycle"+index).parent().addClass("now");
    }


    function operatorCallCycleTab(pageSize, arrIndex){
        operatorCallCycleColor(arrIndex);
        operatorCallInfoCallRecordIndex = arrIndex;
        $("#operatorCallInfoRecordUl").html("");
        operatorUlPageFun(operatorCallInfo[operatorCallInfoCallRecordIndex].call_record,"operatorCallInfoRecordUl","CallInfoRecord");
        operatorCallInfoTable(operatorCallInfo[operatorCallInfoCallRecordIndex].call_record,pageSize);
    }


    //通话记录列表
    function operatorCallInfoTable(data,pageSize){
        var index = (pageSize - 1) * 10;
        var row = data.length>10?pageSize * 10:data.length;
        $("#operatorCallCycleTabTable").html("");
        var table = $("<table class='table'>");
        table.appendTo($("#operatorCallCycleTabTable"));
        var tr0 = $("<tr  class='thead'></tr>").appendTo(table);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>起始时间</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>通话地点</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>呼叫类型</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>对方号码</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>通话时长</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>费用小计</td>").appendTo(tr0);
        for (var i = index; i < row; i++) {
            var item = data[i];
            if(item) {
                var tr = $("<tr class='table_math' style='text-align: center;'></tr>").appendTo(table);
                $("<td  style='border-bottom: 1px solid #ccc;height: 30px'>" + item.call_start_time + "</td>").appendTo(tr);
                $("<td style='border-bottom: 1px solid #ccc;height: 30px'>" + item.call_address + "</td>").appendTo(tr);
                $("<td  style='border-bottom: 1px solid #ccc;height: 30px'>" + item.call_land_type + "</td>").appendTo(tr);
                $("<td style='border-bottom: 1px solid #ccc;height: 30px'>" + item.call_other_number + "</td>").appendTo(tr);
                $("<td  style='border-bottom: 1px solid #ccc;height: 30px'>" + item.call_time + "</td>").appendTo(tr);
                $("<td style='border-bottom: 1px solid #ccc;height: 30px'>" + operatorDecimal(item.call_cost) + "</td>").appendTo(tr);
            }
        }
        $("#operatorCallCycleTabTable").append("</table>");
    }
    function pageCount(data){
        if(undefined != data.length && data.length > 0){
            var num = data.length;
            var page = 1;
            if(num>10)
            {
                page = num % 10 ==0? num/10 : parseInt(num/10)+1;
            }
            return page
        }
        return 0;
    }
</script>

