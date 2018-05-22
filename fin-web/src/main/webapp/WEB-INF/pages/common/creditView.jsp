<%--
  同盾报告数据展示
  User: 陈清玉
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
        <td></td>
        <td>报告编号：</td>
        <td></td>
    </tr>
    <tr>
        <td>证件类型：</td>
        <td></td>
        <td>查询时间</td>
        <td></td>
    </tr>
    <tr>
        <td>证件号码</td>
        <td></td>
        <td>报告时间</td>
        <td></td>
    </tr>
    <tr>
        <td>婚姻状况</td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    </thead>
</table>



</body>
</html>
<script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js${version}"></script>
<script>
    $(function () {
        preLoanObject.methods.loadTongDunData();
    })
    /**
     * 同盾报告模块对象
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
            loadTongDunUrl: '../apiResult/tongDun/${param.orderId}/${param.sourceCode}/${param.customerId}'
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
                    $('.custName').text(result['custName']);
                    $('.custIc').text(result['custIc']);
                    $('.custMobile').text(result['custMobile']);
                    $('.email').text(result['email']);

                    var apiResult = result['apiResult'];
                    if(apiResult != null ){
                        $('.checkResult').text(preLoanObject.methods.finalDecision(apiResult));
                        var  riskItems = result['apiResult']['risk_items'];
                        if(riskItems && riskItems.length > 0){
                            $('.tongdunInfo').removeAttr('style');
                        }
                    }
                    for (var i in riskItems) {
                        var riskItem = riskItems[i];
                        preLoanObject.template.remarksHtml = '';
                        preLoanObject.data.itemDetail = riskItem['item_detail'];
                        var riskLevel = preLoanObject.data.risk_level[riskItem.risk_level];
                        if (preLoanObject.data.itemDetail !== undefined) {
                            preLoanObject.methods.frequencyHandle();
                            preLoanObject.methods.platformHandle();
                            preLoanObject.methods.overdueHandle();
                            preLoanObject.methods.nameListHandle();
                        }
                        //生成同盾报告数据
                        preLoanObject.template.tableContentHtml += '<tr class="firstRow-Court">' +
                            '<th class=" tableRow2 ant-col-3">' + riskItem["item_name"] + '</th>' +
                            '<td class="ant-row tableRow2 ant-col-3" style="text-align: center;">' + riskLevel + '</td>' +
                            '<th class="ant-row tableRow ant-col-5">' + preLoanObject.template.remarksHtml +
                            '</th>' +
                            '</tr>';
                    }
                    $(preLoanObject.tableEl).append(preLoanObject.template.tableContentHtml);
                    preLoanObject.methods.courtHandle( preLoanObject.data.courtDetails);
                })

            },
            /**
             * 客户行为检测
             */
            frequencyHandle: function () {
                if (preLoanObject.data.itemDetail.hasOwnProperty('frequency_detail_list')) {
                    var frequencyDetail = preLoanObject.data.itemDetail['frequency_detail_list'];
                    for (var frequency in frequencyDetail) {
                        preLoanObject.template.remarksHtml += '<span style="display:block;font-weight:normal;width:100%">' + frequencyDetail[frequency]["detail"] + '</span>';
                    }
                }
            },
            /**
             * 多平台借贷申请检测
             */
            platformHandle: function () {
                if (preLoanObject.data.itemDetail.hasOwnProperty('platform_detail')) {
                    for (var platform in preLoanObject.data.itemDetail['platform_detail']) {
                        preLoanObject.template.remarksHtml += '<span style="display:block;font-weight:normal;width:100%">' + preLoanObject.data.itemDetail['platform_detail'][platform] + '</span>';
                    }
                }
            },
            /**
             * 关联人信息扫描
             */
            overdueHandle: function () {
                if (preLoanObject.data.itemDetail.hasOwnProperty('overdue_details')) {
                    var overdueDetails = preLoanObject.data.itemDetail['overdue_details'];
                    for (var overdue in overdueDetails) {
                        var overdueDetail = overdueDetails[overdue];
                        var text = '于' + overdueDetail['overdue_time'] + '有' + overdueDetail['overdue_count'] + '笔,逾期金额为（范围）' +
                            overdueDetail['overdue_amount_range'] + '逾期天数（范围）' + overdueDetail['overdue_day_range'];
                        preLoanObject.template.remarksHtml += '<span >' + text + '</span>';
                    }
                }
            },
            /**
             * 模糊名单
             */
            nameListHandle: function () {
                if (preLoanObject.data.itemDetail.hasOwnProperty('namelist_hit_details')) {
                    var nameList = preLoanObject.data.itemDetail['namelist_hit_details'];
                    for (var mameItem in nameList) {
                        var name = nameList[mameItem];
                        //如果是模糊模块类型
                        if (name.type === 'fuzzy_list') {
                            var fuzzs = name['fuzzy_detail_hits'];
                            for (var fuzz in fuzzs) {
                                var fuzzitem = fuzzs[fuzz];
                                var text = '模糊名单 :' + fuzzitem['fraud_type'] + ' 身份证号:' + fuzzitem['fuzzy_id_number']
                                    + ' 姓名:' + fuzzitem['fuzzy_name'];
                                preLoanObject.template.remarksHtml += '<span >' + text + '</span>';
                            }
                         //如果是灰名单
                        } else if (name.type === 'grey_list') {
                            var text = name['fraud_type'] + ':' + name['hit_type_displayname']
                                + ' 描述:' + name['description'];
                            preLoanObject.template.remarksHtml += '<span >' + text + '</span>';

                         //如果是黑名单
                        } else if (name.type === 'black_list') {
                            var courts = name['court_details'];
                            for (var courtItem in courts) {
                                preLoanObject.data.courtDetails.push(courts[courtItem]);
                            }
                        }

                    }
                }
            },
            /**
             * 法院数据处理
             */
            courtHandle: function (obj) {
                for (var courtItem in obj) {
                    var courtDetail = obj[courtItem];
                    var styleName = '';
                    if(courtItem !== 0 ) {
                        styleName ='margin-top:20px';
                    }
                    preLoanObject.template.courtHtml += '<table cellspacing="1"style="border: 1px solid #ccc;'+ styleName +'" cellpadding="0" class="form-detail column-two transparent">'+
                    '<tbody>' +
                    '<tr class="firstRow-Court">' +
                    '<td class=" tableRow2 ant-col-3"  >被执行人姓名</td>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["name"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >性别</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["gender"] +'</td>' +
                    '</tr>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >年龄</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["age"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >身份证号码</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["id_number"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >执行法院</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["court_name"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >省份</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["province"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >执行依据文号</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["case_number"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >立案时间</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["filing_time"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >执行依据文号</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["execution_base"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >做出执行依据单位</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["execution_department"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >生效法律文书确定的义务</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["duty"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3"  >被执行人的履行情况</th>' +
                    '<td class="ant-row tableRow2 ant-col-5 name"style="border-right:none;">'+ courtDetail["situation"] +'</td>' +
                    '</tr>' +
                    '<tr class="firstRow-Court">' +
                    '<th class=" tableRow2 ant-col-3" style="border-bottom: none;">失信被执行人行为具体情形</th>' +
                    '<td class="ant-row tableRow2 ant-col-5" style="border-bottom: none;border-right:none;">'+ courtDetail["discredit_detail"] +'</td>' +
                    '</tr>' +
                    '</tbody>' +
                    '</table>';
                }
                $('.court').append(preLoanObject.template.courtHtml);
            },
            /**
             * 检查结果
             */
            finalDecision:function (data) {
                var finalScore = data['final_score'];
                var finalDecision = data['final_decision'];
                var length = data['risk_items'] ? data['risk_items'].length : 0;
                switch (finalDecision){
                    case 'Accept':
                        return '扫描建议:'+ finalScore +'分, 申请用户检测出低风险，建议通过，共发现'+length +'条异常信息';
                    case 'Review':
                      return  '扫描建议:'+finalScore +'分, 申请用户检测出中风险，建议审核，共发现'+length +'条异常信息';
                    case 'Reject':
                        return  '扫描建议:'+finalScore +'分, 申请用户检测出高风险，建议拒绝，共发现1'+length +'条异常信息';
                }
            }

        }
    }

</script>
