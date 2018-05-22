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
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>运营商</title>
    <script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js${version}"></script>
    <link rel="stylesheet" href="${ctx}/resources/css/moheyys.css">
    <script src="${ctx}/resources/js/Operator_jrl.js"></script>
</head>
<body>
<!--运营商信息表-->
<div class="" data-reactid="42">
    <div class=" markdown" style="padding: 12px; overflow: auto;"
         data-reactid="43">
        <h4 class="tableTitle" data-reactid="44">用户信息</h4>
        <div class="" data-reactid="45">
            <div class="ant-row " style="margin-left: -8px; margin-right: -8px;"
                 data-reactid="46">
                <div class="ant-row tableRow ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="47">
                    <p>姓名</p>
                </div>
                <div class="ant-row tableRow ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="49">
                    <p>
                        <span id="operatorUserName"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="55">
                    <p>手机号码</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="57">
                    <p>
                        <span id="operatorUserNumber"></span>
                    </p>
                </div>
            </div>
            <div class="ant-row " style="margin-left: -8px; margin-right: -8px;"
                 data-reactid="59">
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="60">
                    <p>身份证号码</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="62">
                    <p>
                        <span id="operatorCertNum"></span>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class=" markdown" style="padding: 12px; overflow: auto;"
         data-reactid="43">
        <h4 class="tableTitle" data-reactid="44">账户信息</h4>
        <div class="gutter-example" data-reactid="45">
            <div class="ant-row " style="margin-left: -8px; margin-right: -8px;"
                 data-reactid="46">
                <div class="ant-row tableRow ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="47">
                    <p>账户余额（元）</p>
                </div>
                <div class="ant-row tableRow ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="49">
                    <p>
                        <span id="operatorAccountBalance"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="51">
                    <p>当前话费</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="53">
                    <p>
                        <span id="operatorCurrentFee"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="55">
                    <p>账户星级</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="57">
                    <p>
                        <span id="operatorCreditLevel"></span>
                    </p>
                </div>
            </div>
            <div class="ant-row " style="margin-left: -8px; margin-right: -8px;"
                 data-reactid="59">
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="60">
                    <p>账户状态</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="62">
                    <p>
                        <span id="operatorMobileStatus"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="64">
                    <p>入网时间</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="66">
                    <p>
                        <span id="operatorNetTime"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="68">
                    <p>网龄</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="70">
                    <p>
                        <span id="operatorNetAge"></span>
                    </p>
                </div>
            </div>
            <div class="ant-row " style="margin-left: -8px; margin-right: -8px;"
                 data-reactid="72">
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="73">
                    <p>实名制信息</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="75">
                    <p>
                        <span id="operatorRealInfo"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="73">
                    <p>积分</p>
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="75">
                    <p>
                        <span id="operatorCreditPoint"></span>
                    </p>
                </div>
                <div class="ant-row tableRow  ant-col-3"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="73">
                </div>
                <div class="ant-row tableRow  ant-col-5"
                     style="padding-left: 8px; padding-right: 8px;" data-reactid="75">
                </div>
            </div>
        </div>
    </div>
    <div class="markdown" style="padding: 12px; overflow: auto;"
         data-reactid="43">
        <h4 class="tableTitle" data-reactid="44" style="margin-bottom: 20px;">缴费记录</h4>
        <div class="gutter-example" data-reactid="45">
            <div class="ant-row " style="margin-left: -8px; margin-right: -8px;"
                 data-reactid="46">
                <span id="operatorPaymentRecordTable"></span>
            </div>
        </div>
        <ul class="ant-pagination ant-table-pagination" data-reactid="158" unselectable="unselectable" id="operatorPaymentRecordUl">

        </ul>
    </div>
    <div class="ant-tabs-nav " data-reactid="43" id="box">
        <h4 class="tableTitle" data-reactid="44" style="padding-left: 20px; width: 100%">通话记录</h4>
        <div class="ant-tabs-ink-bar" data-reactid="173" style="height: 50px;">
            <div class="ant-row1 " style="margin-left: -8px; margin-right: -8px;" data-reactid="175">
                <div role="tab" class="ant-tabs-tab" onclick="operatorCallCycleTab('1','0')" data-reactid="176"><span id="operatorCallCycle0"></span></div>
                <div role="tab" class="ant-tabs-tab" onclick="operatorCallCycleTab('1','1')" data-reactid="177"><span id="operatorCallCycle1"></span></div>
                <div role="tab" class="ant-tabs-tab" onclick="operatorCallCycleTab('1','2')" data-reactid="178"><span id="operatorCallCycle2"></span></div>
                <div role="tab" class="ant-tabs-tab" onclick="operatorCallCycleTab('1','3')" data-reactid="179"><span id="operatorCallCycle3"></span></div>
                <div role="tab" class="ant-tabs-tab" onclick="operatorCallCycleTab('1','4')" data-reactid="180"><span id="operatorCallCycle4"></span></div>
                <div role="tab" class="ant-tabs-tab" onclick="operatorCallCycleTab('1','5')" data-reactid="181"><span id="operatorCallCycle5"></span></div>
            </div>
        </div>
        <div id="tabCon">
            <div id="tabCon_0">
                <span id="operatorCallCycleTabTable"></span>
            </div>
        </div>
    </div>
</div>
<ul class="ant-pagination ant-table-pagination" data-reactid="158" unselectable="unselectable" id="operatorCallInfoRecordUl">

</ul>
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
                $("#operatorUserName").html(baseInfo.custName);//姓名
                $("#operatorUserSex").html(baseInfo.user_sex);//性别
                $("#operatorUserNumber").html(baseInfo.custMobile);//手机号码
                $("#operatorCertNum").html(baseInfo.custIc);//身份证号码
                $("#operatorCertAddr").html(baseInfo.cert_addr);//联系地址
                $("#operatorUserContactNo").html(baseInfo.user_contact_no);//联系电话
                $("#operatorUserEmail").html(baseInfo.user_email);//邮箱地址

                var apiResult = data.data.apiResult;
                //账户信息
                var accountInfo = apiResult.account_info;
                $("#operatorAccountBalance").html(operatorDecimal(accountInfo.account_balance));//账户余额。整形数字精确到分
                $("#operatorCurrentFee").html(operatorDecimal(accountInfo.current_fee));//当前话费。整形数字精确到分
                $("#operatorCreditLevel").html(accountInfo.credit_level);//帐户级别
                $("#operatorNetTime").html(accountInfo.net_time);//入网时间
                $("#operatorNetAge").html(accountInfo.net_age);//网龄
                $("#operatorRealInfo").html(accountInfo.real_info);//实名制信息
                $("#operatorCreditPoint").html(accountInfo.credit_point);//积分。整形数字
                $("#operatorBrandName").html(accountInfo.brand_name);//品牌
                $("#operatorPayType").html(accountInfo.pay_type);//缴费类型
                $("#operatorMobileStatus").html(accountInfo.mobile_status);//账户状态。正常、欠费、停机、销户、未激活、未知


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
        ul.append("<li title='上一页' class='ant-pagination-item ant-pagination-item-1 ant-pagination-item-active' data-reactid ='395' onclick='operator"+recordType+"PreviousPage()'><</li>");//动态生成
        for(var i=1;i<=newRow;i++){
            ul.append("<li class='ant-pagination-item ant-pagination-item-1 ant-pagination-item-active' data-reactid ='395' onclick='operator"+recordType+"Page("+i+")'>"+i+"</li>");//动态生成
        }
        ul.append("<li  class='ant-pagination-item ant-pagination-item-1 ant-pagination-item-active' data-reactid ='395' onclick='operator"+recordType+"NextPage()'>></li>");//动态生成

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
        var table = $("<table data-toggle='table' style='width:100%;border:1px solid #eee'>");
        table.appendTo($("#operatorPaymentRecordTable"));
        var tr0 = $("<tr  class='table_math' style='text-align: center;'></tr>").appendTo(table);
        $("<td style='border-bottom:1px solid #ccc;height: 30px'>日期</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>金额</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>缴费方式</td>").appendTo(tr0);
        for (var i = index; i < row; i++) {
            var tr = $("<tr class='table_math' style='text-align: center;'></tr>").appendTo(table);
            $("<td  style='border-bottom:1px solid #ccc;height: 30px'>"+data[i].pay_date+"</td>").appendTo(tr);
            $("<td style='border-bottom:1px solid #ccc;height: 30px'>"+operatorDecimal(data[i].pay_fee)+"</td>").appendTo(tr);
            $("<td  style='border-bottom:1px solid #ccc;height: 30px'>"+data[i].pay_type+"</td>").appendTo(tr);
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
            $("#operatorCallCycle"+i).css({"color":"black"});
        }
        $("#operatorCallCycle"+index).css({"color":"blue"});
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
        var table = $("<table data-toggle='table' style='width:100%;border:1px solid #eee'>");
        table.appendTo($("#operatorCallCycleTabTable"));
        var tr0 = $("<tr  class='table_math' style='text-align: center;'></tr>").appendTo(table);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>起始时间</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>通话地点</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>呼叫类型</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>对方号码</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>通话时长</td>").appendTo(tr0);
        $("<td style='border-bottom: 1px solid #ccc;height: 30px'>费用小计</td>").appendTo(tr0);
        for (var i = index; i < row; i++) {
            var tr = $("<tr class='table_math' style='text-align: center;'></tr>").appendTo(table);
            $("<td  style='border-bottom: 1px solid #ccc;height: 30px'>"+data[i].call_start_time+"</td>").appendTo(tr);
            $("<td style='border-bottom: 1px solid #ccc;height: 30px'>"+data[i].call_address+"</td>").appendTo(tr);
            $("<td  style='border-bottom: 1px solid #ccc;height: 30px'>"+data[i].call_land_type+"</td>").appendTo(tr);
            $("<td style='border-bottom: 1px solid #ccc;height: 30px'>"+data[i].call_other_number+"</td>").appendTo(tr);
            $("<td  style='border-bottom: 1px solid #ccc;height: 30px'>"+data[i].call_time+"</td>").appendTo(tr);
            $("<td style='border-bottom: 1px solid #ccc;height: 30px'>"+operatorDecimal(data[i].call_cost)+"</td>").appendTo(tr);
        }
        $("#operatorCallCycleTabTable").append("</table>");
    }

</script>

