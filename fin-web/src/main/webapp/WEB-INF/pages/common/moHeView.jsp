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
    <link rel="stylesheet" href="${ctx}/resources/css/moheyys.css${version}">
    <script src="${ctx}/resources/js/Operator_jrl.js${version}"></script>
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
                for(var i = 0 ; i< 6 ;i++){
                    var callInfo = operatorCallInfo[i];
                    if(callInfo){
                        //最近六个月的通话记录
                        $("#operatorCallCycle" + i).html(callInfo.call_cycle);
                    }
                }
                operatorCallInfoCallRecordIndex = 0
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
        operatorCallInfoCallRecordIndexPage = operatorCallInfoCallRecordIndexPage - 1;
        operatorCallInfoRecordPage(operatorCallInfoCallRecordIndexPage);

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
        console.log(pageSize);
        console.log(data);
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

