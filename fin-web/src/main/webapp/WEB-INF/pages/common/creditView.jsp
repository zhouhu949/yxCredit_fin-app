<%--
  征信报告数据展示
  User: luochaofang
  Date: 2018/5/20
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>个人征信信息明细</title>
    <link rel="stylesheet" href="${ctx}/resources/css/creditReport/base.css">
    <link rel="stylesheet" href="${ctx}/resources/css/creditReport/index.css">
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        td{
            text-align: 45px;
        }
        table{
            border-collapse: collapse;
            /* border-bottom: 30px; */
            margin-bottom: 10px;

        }
        .table_header{
            margin-left: 10px;

        }
        .table_header th{
            width: 1340px;
            height: 45px;
            font-size: 16px;
            font-weight: 600;
            line-height: 40px;
            background-color:rgb(223, 217, 217);
            color: #000;
            padding-left: 20px;

        }
        .table_header td{
            width: 50px;
            height: 45px;
            line-height: 30px;
            border: 1px solid #000;
            padding-left: 20px;
            font-size: 16px;

        }
        .table_header td:first-child{
            background-color: #eee;
            width: 15%;
        }
        .table_header td:nth-child(3){
            background-color:#eee ;
            width: 15%;
        }
        .table_footer .table_math{
            background-color: #eee;
        }
        .table_footer td:first-child{
            width: 15%;
        }
        .table_footer td:nth-child(3){
            width: 25%;
        }
        .tbody{
            border: 1px solid #eee;
            width: 1340px;
            height: 200px;
        }
        .table_content td{
            border-bottom: 1px solid #000;
            padding-left: 10px;
            height: 45px;
            line-height: 45px;
        }
        .table_content th{
            height: 45px;
            background-color: #eee;

        }
        .content{
            width: 1340px;
            height: 100%;
            border: 1px solid #eee;

        }
        .content .content_h{
            border: 1px solid #eee;
            height: 100px;
            padding-left: 20px;
            color: rgb(34, 65, 241);
            padding-top: 10px;

        }
        .content .content_h p{
            padding-top: 10px;
        }
        h1{
            text-align: center;
            margin:30px 0;;
        }
    </style>



</head>
<body style="padding-left:20px;">
<h1>征信数据</h1>
<table data-toggle="table" style="width:1340px;border:1px solid #eee;">
    <thead class="table_header" >
    <tr>
        <th colspan="4" style="text-align:left;">基本信息</th>
    </tr>
    <tr>
        <td>姓&nbsp;名：</td>
        <td id="name"></td>
        <td>报告编号：</td>
        <td id="reportCode"></td>
    </tr>
    <tr>
        <td>证件类型：</td>
        <td id="cardType"></td>
        <td>查询时间</td>
        <td id="applyTime"></td>
    </tr>
    <tr>
        <td>证件号码</td>
        <td id="cardNo"></td>
        <td>报告时间</td>
        <td id="reportTime"></td>
    </tr>
    <tr>
        <td>婚姻状况</td>
        <td id="marriedStatus"></td>
        <td>手机号码</td>
        <td id="custMobile"></td>
    </tr>
    </thead>
</table>

<table data-toggle="table" style="width:1340px;border:1px solid #eee;">
    <thead class="table_footer table_header" >
    <tr>
        <th colspan="4" style="text-align:left;">信用卡详情</th>
    </tr>
    <tr class="table_math" style="text-align: center;">
        <td>流水号</td>
        <td>卡片类型</td>
        <td>发放时间</td>
        <td>发放银行</td>
        <td>账户类型</td>
        <td>信用卡账单/销毁日期</td>
        <td>额度</td>
        <td>已用额度</td>
        <td>信用卡状态 (销户、未激活、使用中、呆账)</td>
        <td>是否逾期</td>
        <td>是否逾期90天</td>
        <td>最近5年内逾期月数</td>
        <td>最近5年内逾期90天的月数</td>
        <td>创建时间</td>
        <td>最后更新日期</td>
    </tr>
    </thead>
    <tbody id="creditDetails">
    </tbody>
</table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">购房贷款详情</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>贷款类型</td>
            <td>贷款时间</td>
            <td>贷款银行</td>
            <td>贷款金额</td>
            <td>到期时间</td>
            <td>还款时间</td>
            <td>贷款余额</td>
            <td>是否逾期</td>
            <td>是否逾期90天</td>
            <td>最近5年内逾期月数</td>
            <td>最近5年内逾期90天的月数</td>
            <td>逾期金额</td>
            <td>房贷状态(结清，未结清)</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="houseloanDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">查询记录</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>查询人员</td>
            <td>查询原因</td>
            <td>查询时间</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="queryInfos">
        </tbody>
    </table>
    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">行政处罚</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>处罚机构</td>
            <td>文书编号</td>
            <td>处罚内容</td>
            <td>是否行政复议</td>
            <td>处罚金额</td>
            <td>行政复议结果</td>
            <td>处罚生效时间</td>
            <td>处罚截止时间</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="punishDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">欠税记录</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>主管税务机关</td>
            <td>欠税统计时间</td>
            <td>欠税金额</td>
            <td>纳税人识别号</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="taxUnpaidDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">电信欠费记录</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>电信运营商</td>
            <td>业务类型</td>
            <td>记账年月</td>
            <td>业务开通时间</td>
            <td>欠费金额</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="teleArrearsDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">强制执行记录</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>执行法院</td>
            <td>案号</td>
            <td>执行案由</td>
            <td>结案方式</td>
            <td>立案时间</td>
            <td>案件状态</td>
            <td>申请执行标的</td>
            <td>申请执行标的</td>
            <td>申请执行标的金额</td>
            <td>已执行标的金额</td>
            <td>结案时间</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="enforceDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">民事判决记录</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>执行法院</td>
            <td>案号</td>
            <td>执行案由</td>
            <td>结案方式</td>
            <td>立案时间</td>
            <td>判决/调解结果</td>
            <td>诉讼标的</td>
            <td>判决/调解生效时间</td>
            <td>诉讼标的金额</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="civilJudgementDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">资产处置信息</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>处置时间</td>
            <td>接收单位</td>
            <td>接收单位</td>
            <td>债权金额</td>
            <td>最近一次还款日期</td>
            <td>余额</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="disposalDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">保证人代偿信息</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>代偿日期</td>
            <td>代偿单位</td>
            <td>累计代偿金额</td>
            <td>余额</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="compensationDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">担保详情</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>担保日期</td>
            <td>被担保人姓名</td>
            <td>被担保人证件类型</td>
            <td>被担保人证件号</td>
            <td>单位</td>
            <td>担保类型</td>
            <td>担保合同金额</td>
            <td>担保合同金额</td>
            <td>统计截止日期</td>
            <td>担保余额</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="guaranteedDetails">
        </tbody>
    </table>

    <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
        <thead class="table_footer table_header" >
        <tr>
            <th colspan="4" style="text-align:left;">其他贷款详情</th>
        </tr>
        <tr class="table_math" style="text-align: center;">
            <td>流水号</td>
            <td>贷款类型</td>
            <td>贷款时间</td>
            <td>贷款银行</td>
            <td>贷款金额</td>
            <td>到期时间</td>
            <td>还款时间</td>
            <td>贷款余额</td>
            <td>是否逾期</td>
            <td>是否逾期90天</td>
            <td>最近5年逾期月数</td>
            <td>最近5年内逾期90天的月数</td>
            <td>逾期金额</td>
            <td>贷款状态(结清，未结清)</td>
            <td>创建时间</td>
            <td>最后更新日期</td>
        </tr>
        </thead>
        <tbody id="otherloanDetails">
        </tbody>
    </table>

</body>
</html>
<script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js${version}"></script>
<script>
    $(function () {
        preLoanObject.methods.loadTongDunData();
    })
    /**
     * 征信报告模块对象
     */
    var preLoanObject = {
        tableEl: '.result',
        requestParameter: {
            orderId:'${param.orderId}',
            customerId:'${param.customerId}',
            sourceCode:'${param.sourceCode}'


        },
        template: {
            remarksHtml: '',
            tableContentHtml: '',
            courtHtml: ''
        },
        urls: {
            //数据加载url
            loadTongDunUrl: '../apiResult/tongDun/${param.resultId}/${param.customerId}'
        },
        data: {
            risk_level: {
                low: "低",
                high: "高",
                medium: "中"
            },
            itemDetail: undefined,
            courtDetails: []
        },
        methods: {
            /**
             * 加载征信数据
             */
            loadTongDunData: function () {
                $.getJSON(preLoanObject.urls.loadTongDunUrl, function (data) {
                    if(!data || data.code !== 0){
                        return;
                    }
                    var result = data.data;
                    debugger
                    $('#name').text(result['custName']);
                   // $('#custIc').text(result['custIc']);
                    $('#custMobile').text(result['custMobile']);

                    var apiResult = result['apiResult'];//获取结果集合
                    var reportInfo = apiResult.reportInfo;//获取报告信息
                    var creditDetails = apiResult.creditDetails;//获取信用卡详情
                    var houseloanDetails = apiResult.houseloanDetails;//购房贷款详情
                    var queryInfos = apiResult.queryInfos;//查询记录
                    var punishDetails = apiResult.punishDetails;//行政处罚
                    var taxUnpaidDetails = apiResult.taxUnpaidDetails;//欠税记录
                    $('#reportCode').text(apiResult.reportCode == null? "":apiResult.reportCode);
                    $('#cardType').text(reportInfo.cardType);
                    $('#cardNo').text(reportInfo.cardNo);
                    $('#reportTime').text(reportInfo.reportTime);
                    $('#marriedStatus').text(reportInfo.marriedStatus);

                    var creditDetailHtml="";
                    for(var i in creditDetails){
                        var creditBean = creditDetails[i];
                        creditDetailHtml +='<tr class="table_math" style="text-align: center;">';
                        creditDetailHtml +='<td>'+ (creditBean.reportCode == null ?"":creditBean.reportCode)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.cardType == null ?"":creditBean.cardType)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditDate == null ?"":creditBean.creditDate)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditBank == null ?"":creditBean.creditBank)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditType == null ?"":creditBean.creditType)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditMonth == null ?"":creditBean.creditMonth)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditLimit == null ?"":creditBean.creditLimit)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditUsedLimit == null ?"":creditBean.creditUsedLimit)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.creditStatus == null ?"":creditBean.creditStatus)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.isOd == false ?"否":"是")+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.isOd90 == false ?"否":"是")+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.odMonth == null ?"":creditBean.odMonth)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.od90Month == null ?"":creditBean.od90Month)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.odLimit == null ?"":creditBean.odLimit)+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.createTime == null ?"":formatDateTime(creditBean.createTime))+'</td>';
                        creditDetailHtml +='<td>'+ (creditBean.updateTime == null ?"":formatDateTime(creditBean.updateTime))+'</td>';
                        creditDetailHtml +='</tr>';
                    }
                    $("#creditDetails").html(creditDetailHtml);

                    var houseloanDetailsHtml="";
                    for(var i in houseloanDetails){
                        var houseloanBean = houseloanDetails[i];
                        houseloanDetailsHtml +='<tr class="table_math" style="text-align: center;">';
                        houseloanDetailsHtml +='<td>'+ houseloanBean.reportCode+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanType == null ?"":houseloanBean.houseloanType)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanDate == null ?"":houseloanBean.houseloanDate)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanBank == null ?"":houseloanBean.houseloanBank)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanMoney == null ?"":houseloanBean.houseloanMoney)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanEndDate == null ?"":houseloanBean.houseloanEndDate)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanMonth == null ?"":houseloanBean.houseloanMonth)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanBalance == null ?"":houseloanBean.houseloanBalance)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.isOd == false ?"否":"是")+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.isOd90 == false ?"否":"是")+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.odMonth == null ?"":houseloanBean.odMonth)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.od90Month == null ?"":houseloanBean.od90Month)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.odLimit == null ?"":houseloanBean.odLimit)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.houseloanStatus == null ?"":houseloanBean.houseloanStatus)+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.createTime == null ?"": formatDateTime(houseloanBean.createTime))+'</td>';
                        houseloanDetailsHtml +='<td>'+ (houseloanBean.updateTime == null ?"":formatDateTime(houseloanBean.updateTime))+'</td>';
                        houseloanDetailsHtml +='</tr>';
                    }
                    $("#houseloanDetails").html(houseloanDetailsHtml);

                    var queryInfosDetailsHtml="";
                    for(var i in queryInfos){
                        var queryInfosDetail = queryInfos[i];
                        queryInfosDetailsHtml +='<tr class="table_math" style="text-align: center;">';
                        queryInfosDetailsHtml +='<td>'+ queryInfosDetail.reportCode+'</td>';
                        queryInfosDetailsHtml +='<td>'+ (queryInfosDetail.queryer == null ?"":queryInfosDetail.queryer)+'</td>';
                        queryInfosDetailsHtml +='<td>'+ (queryInfosDetail.queryReason == null ?"":queryInfosDetail.queryReason)+'</td>';
                        queryInfosDetailsHtml +='<td>'+ (queryInfosDetail.queryTime == null ?"":queryInfosDetail.queryTime)+'</td>';
                        queryInfosDetailsHtml +='<td>'+ (queryInfosDetail.createTime == null ?"": formatDateTime(queryInfosDetail.createTime))+'</td>';
                        queryInfosDetailsHtml +='<td>'+ (queryInfosDetail.updateTime == null ?"":formatDateTime(queryInfosDetail.updateTime))+'</td>';
                        queryInfosDetailsHtml +='</tr>';
                    }
                    $("#queryInfos").html(queryInfosDetailsHtml);


                    var punishDetailsHtml="";
                    for(var i in punishDetails){
                        var punishDetailsBean = punishDetails[i];
                        punishDetailsHtml +='<tr class="table_math" style="text-align: center;">';
                        punishDetailsHtml +='<td>'+ punishDetailsBean.reportCode+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.institution == null ?"": punishDetailsBean.institution)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.docNum == null ?"": punishDetailsBean.docNum)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.punishContent == null ?"": punishDetailsBean.punishContent)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.isReview == null ?"": punishDetailsBean.isReview)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.punishSum == null ?"": punishDetailsBean.punishSum)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.reviewResult == null ?"": punishDetailsBean.reviewResult)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.effectiveTime == null ?"": punishDetailsBean.effectiveTime)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.endTime == null ?"":punishDetailsBean.endTime)+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.createTime == null ?"": formatDateTime(punishDetailsBean.createTime))+'</td>';
                        punishDetailsHtml +='<td>'+ (punishDetailsBean.updateTime == null ?"":formatDateTime(punishDetailsBean.updateTime))+'</td>';
                        punishDetailsHtml +='</tr>';
                    }
                    $("#punishDetails").html(punishDetailsHtml);
                })

            },
        }
    }


    function formatDateTime(timeStamp) {
        var date = new Date();
        date.setTime(timeStamp * 1000);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        return y + '-' + m + '-' + d+' ';
    };
</script>
