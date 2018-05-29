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
  <%--  <link rel="stylesheet" href="${ctx}/resources/css/creditReport/base.css">--%>
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
        .noRecord {
            line-height: 100px;
            text-align: center;
            color: #999;
            padding: 10px 20px;
            background-color: #fff;}



    </style>
</head>
<body>
<div class="main">
    <div class="header">
        <h3>人行征信报告</h3>
    </div>
    <div class="container">
        <div class="personalInfo">
            <div class="title"><h5>个人信息</h5></div>
            <div class="details">
                <ul class="content clearfix">
                    <li class="fl clearfix">
                        <span>姓名</span>
                        <input type="text" id="name" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>证件类型</span>
                        <input type="text" id="cardType" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>身份证号码</span>
                        <input type="text" id="cardNo" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>婚姻状态</span>
                        <input type="text" id="marriedStatus" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>信用卡数量</span>
                        <input type="text" id="creditQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>正在使用的信用卡数量</span>
                        <input type="text" id="usingCreditQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>发生逾期的信用卡数量</span>
                        <input type="text" id="odCreditQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>购房贷款数量</span>
                        <input type="text" id="houseloanQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>未结清的购房贷款数量</span>
                        <input type="text" id="usingHouseloanQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>逾期的购房贷款数量</span>
                        <input type="text" id="odHouseloanQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>为他人担保的购房贷款</span>
                        <input type="text" id="guaranteeHouseloanQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>其他借款总额</span>
                        <input type="text" id="otherloanQty" readonly>
                    </li>
                    <li class="fl clearfix">
                        <span>报告编号</span>
                        <input type="text" id="reportCode" readonly >
                    </li>
                    <li class="fl clearfix">
                        <span>报告时间</span>
                        <input type="text" id="reportTime" readonly>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
   <%-- <table data-toggle="table" style="width:1340px;border:1px solid #eee;">
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
    </table>--%>

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
                    var apiResult = result['apiResult'];//获取结果集合
                    var reportInfo = apiResult.reportInfo;//获取报告信息
                    var creditDetails = apiResult.creditDetails;//获取信用卡详情
                    var houseloanDetails = apiResult.houseloanDetails;//购房贷款详情
                    var queryInfos = apiResult.queryInfos;//查询记录
                    var punishDetails = apiResult.punishDetails;//行政处罚
                    var taxUnpaidDetails = apiResult.taxUnpaidDetails;//欠税记录
                    $('#reportCode').val(apiResult.reportCode == null? "":apiResult.reportCode);
                    $('#cardType').val(reportInfo.cardType);
                    $('#reportTime').val(reportInfo.reportTime);
                    $('#cardNo').val(reportInfo.cardNo);
                    $('#name').val(reportInfo.name);
                    $('#marriedStatus').val(reportInfo.marriedStatus);
                    $('#otherloanQty').val(reportInfo.otherloanQty);
                    $('#guaranteeHouseloanQty').val(reportInfo.guaranteeHouseloanQty);
                    $('#odHouseloanQty').val(reportInfo.odHouseloanQty);
                    $('#usingHouseloanQty').val(reportInfo.usingHouseloanQty);
                    $('#houseloanQty').val(reportInfo.houseloanQty);
                    $('#guaranteeCreditQty').val(reportInfo.guaranteeCreditQty);
                    $('#odCreditQty').val(reportInfo.odCreditQty);
                    $('#usingCreditQty').val(reportInfo.usingCreditQty);
                    $('#creditQty').val(reportInfo.creditQty);
                    $('#cardType').val(reportInfo.cardType);

                    var creditDetailHtml="";
                    if(creditDetails.length > 0) {
                        creditDetailHtml+='<div class="creditCard">';
                        creditDetailHtml +='<div class="title"><h5>信用卡详情</h5></div>';
                        for(var i in creditDetails){
                            var creditBean = creditDetails[i];
                            creditDetailHtml +='<div class="details">';
                            creditDetailHtml +='<div class="serialNumber">流水号: <span>'+(creditBean.reportCode == null ?"":creditBean.reportCode)+'</span></div>';
                            creditDetailHtml +='<ul class="content clearfix">';
                            creditDetailHtml +='<li class="fl clearfix"><span>卡片类型</span><input type="text" readonly value=\''+creditBean.cardType+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"><span>发放时间</span><input type="text" readonly value=\''+creditBean.creditDate+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"><span>发放银行</span><input type="text" readonly value=\''+creditBean.creditBank+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"><span>账户类型</span><input type="text" readonly value=\''+creditBean.creditType+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"><span>信用卡账单/销毁日期</span><input type="text" readonly value=\''+creditBean.creditMonth+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"><span>额度</span><input type="text" readonly value=\''+creditBean.creditLimit+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>已用额度</span><input type="text" readonly value=\''+creditBean.creditUsedLimit+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>信用卡状态 (销户、未激活、使用中、呆账)</span><input type="text" readonly value=\''+creditBean.creditStatus+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>是否逾期</span><input type="text" readonly value=\''+(creditBean.isOd === false ?"否":"是")+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>是否逾期90天</span><input type="text" readonly value=\''+(creditBean.isOd90 === false ?"否":"是")+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>最近5年内逾期月数</span><input type="text" readonly value=\''+(creditBean.odMonth === null ?"":creditBean.odMonth)+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>最近5年内逾期90天的月数</span><input type="text" readonly value=\''+(creditBean.od90Month == null ?"":creditBean.od90Month)+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>创建时间</span><input type="text" readonly value=\''+(creditBean.createTime === null ?"": formatDateTime(creditBean.createTime))+'\'></li>';
                            creditDetailHtml +='<li class="fl clearfix"> <span>最后更新日期</span><input type="text" readonly value=\''+(creditBean.updateTime === null ?"":formatDateTime(creditBean.updateTime))+'\'></li>';
                            creditDetailHtml +='</ul>';
                            creditDetailHtml +='</div>';
                        }
                        creditDetailHtml +='</div>';
                    }


                    //购房详情
                    var houseloanDetailsHtml="";
                    if(houseloanDetails.length > 0) {
                        houseloanDetailsHtml+='<div class="creditCard1">';
                        houseloanDetailsHtml +='<div class="title"><h5>购房贷款详情</h5></div>';
                        for(var i in houseloanDetails){
                            var houseloanBean = houseloanDetails[i];
                            houseloanDetailsHtml +='<div class="details">';
                            houseloanDetailsHtml +='<div class="serialNumber">流水号: <span>'+(houseloanBean.reportCode == null ?"":houseloanBean.reportCode)+'</span></div>';
                            houseloanDetailsHtml +='<ul class="content clearfix">';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>贷款类型</span><input type="text" readonly value=\''+houseloanBean.houseloanType+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>贷款时间</span><input type="text" readonly value=\''+houseloanBean.houseloanDate+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>贷款银行</span><input type="text" readonly value=\''+houseloanBean.houseloanBank+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>贷款金额</span><input type="text" readonly value=\''+houseloanBean.houseloanMoney+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>到期时间</span><input type="text" readonly value=\''+houseloanBean.houseloanEndDate+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>还款时间</span><input type="text" readonly value=\''+houseloanBean.houseloanMonth+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>贷款余额</span><input type="text" readonly value=\''+houseloanBean.houseloanBalance+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>是否逾期</span><input type="text" readonly value=\''+(houseloanBean.isOd === false ?"否":"是")+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>是否逾期90天</span><input type="text" readonly value=\''+(houseloanBean.isOd90 === false ?"否":"是")+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>最近5年内逾期月数</span><input type="text" readonly value=\''+(houseloanBean.odMonth === null ?"":houseloanBean.odMonth)+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>最近5年内逾期90天的月数</span><input type="text" readonly value=\''+(houseloanBean.od90Month === null ?"":houseloanBean.od90Month)+'\'></li>';
                         /*   houseloanDetailsHtml +='<li class="fl clearfix"> <span>房贷状态(结清，未结清)</span><input type="text" readonly value=\''+(houseloanBean.houseloanStatus === null ?"":houseloanBean.houseloanStatus)+'\'></li>';*/
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>创建时间</span><input type="text" readonly value=\''+(houseloanBean.createTime === null ?"": formatDateTime(houseloanBean.createTime))+'\'></li>';
                            houseloanDetailsHtml +='<li class="fl clearfix"> <span>最后更新日期</span><input type="text" readonly value=\''+(houseloanBean.updateTime === null ?"":formatDateTime(houseloanBean.updateTime))+'\'></li>';
                            houseloanDetailsHtml +='</ul>';
                            houseloanDetailsHtml +=' </div>';

                        }
                        houseloanDetailsHtml +='</div>';
                    }

                    var queryInfosDetailsHtml="";
                    if(queryInfos.length > 0) {
                        queryInfosDetailsHtml+='<div class="creditCard">';
                        queryInfosDetailsHtml +='<div class="title"><h5>查询记录</h5></div>';
                        for(var i in queryInfos){
                            var queryInfosDetail = queryInfos[i];
                            queryInfosDetailsHtml +='<div class="details">';
                            queryInfosDetailsHtml +='<div class="serialNumber">流水号: <span>'+(queryInfosDetail.reportCode == null ?"":queryInfosDetail.reportCode)+'</span></div>';
                            queryInfosDetailsHtml +='<ul class="content clearfix">';
                            queryInfosDetailsHtml +='<li class="fl clearfix"> <span>查询人员</span><input type="text" readonly value=\''+queryInfosDetail.queryer+'\'></li>';
                            queryInfosDetailsHtml +='<li class="fl clearfix"> <span>查询原因</span><input type="text" readonly value=\''+queryInfosDetail.queryReason+'\'></li>';
                            queryInfosDetailsHtml +='<li class="fl clearfix"> <span>查询时间</span><input type="text" readonly value=\''+queryInfosDetail.queryTime+'\'></li>';
                            queryInfosDetailsHtml +='<li class="fl clearfix"> <span>创建时间</span><input type="text" readonly value=\''+(queryInfosDetail.createTime === null ?"": formatDateTime(queryInfosDetail.createTime))+'\'></li>';
                            queryInfosDetailsHtml +='<li class="fl clearfix"> <span>最后更新日期</span><input type="text" readonly value=\''+(queryInfosDetail.updateTime === null ?"":formatDateTime(queryInfosDetail.updateTime))+'\'></li>';
                            queryInfosDetailsHtml +='</ul>';
                            queryInfosDetailsHtml +=' </div>';
                        }
                        queryInfosDetailsHtml +='</div>';
                    }

                    var punishDetailsHtml="";
                    if(punishDetails.length > 0) {
                        punishDetailsHtml+='<div class="creditCard">';
                        punishDetailsHtml +='<div class="title"><h5>行政处罚</h5></div>';
                        for(var i in punishDetails){
                            var punishDetailsBean = punishDetails[i];
                            punishDetailsHtml +='<div class="details">';
                            punishDetailsHtml +='<div class="serialNumber">流水号: <span>'+(punishDetailsBean.reportCode == null ?"":punishDetailsBean.reportCode)+'</span></div>';
                            punishDetailsHtml +='<ul class="content clearfix">';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>处罚机构</span><input type="text" readonly value=\''+punishDetailsBean.institution+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>文书编号</span><input type="text" readonly value=\''+punishDetailsBean.docNum+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>处罚内容</span><input type="text" readonly value=\''+punishDetailsBean.punishContent+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>是否行政复议</span><input type="text" readonly value=\''+punishDetailsBean.isReview+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>处罚金额</span><input type="text" readonly value=\''+punishDetailsBean.punishSum+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>行政复议结果</span><input type="text" readonly value=\''+punishDetailsBean.reviewResult+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>处罚生效时间</span><input type="text" readonly value=\''+punishDetailsBean.effectiveTime+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>处罚截止时间</span><input type="text" readonly value=\''+punishDetailsBean.endTime+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>创建时间</span><input type="text" readonly value=\''+(punishDetailsBean.createTime === null ?"": formatDateTime(queryInfosDetail.createTime))+'\'></li>';
                            punishDetailsHtml +='<li class="fl clearfix"> <span>最后更新日期</span><input type="text" readonly value=\''+(punishDetailsBean.updateTime === null ?"":formatDateTime(queryInfosDetail.updateTime))+'\'></li>';
                            punishDetailsHtml +='</ul>';
                            punishDetailsHtml +=' </div>';
                        }
                        punishDetailsHtml +=' </div>';
                    }
                    $(".details").append(creditDetailHtml+houseloanDetailsHtml+queryInfosDetailsHtml+punishDetailsHtml);
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

