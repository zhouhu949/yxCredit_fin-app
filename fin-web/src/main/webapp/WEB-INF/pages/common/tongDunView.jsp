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
    <title>同盾信息明细</title>
    <link rel="stylesheet" href="${ctx}/resources/css/Pre-loan.css">
</head>
<body style="padding-left:20px;">
<h2 style="padding-left:35px;margin:30px 0;" class="checkResult">扫描建议:未扫描出建议</h2>
    <div class="mainBox" data-reactid="42">
        <div class="markdown"  data-reactid="43">
            <h4 class="tableTitle" data-reactid="44" style="margin:10px 0;">账户基本信息</h4>
        </div>
        <div class="shadowBox">
            <table cellspacing="1" cellpadding="0" class="form-detail column-two transparent">
                <tbody>
                <tr class="firstRow">
                    <th class="ant-row tableRow1 ant-col-3" style="text-align: left;">
                        姓名
                    </th>
                    <td class="ant-row tableRow1 ant-col-5 custName"style="text-align: left;">
                    </td>
                    <th class="ant-row tableRow1 ant-col-3" style="text-align: left;">
                        身份证
                    </th>
                    <td class="ant-row tableRow1 ant-col-5 custIc"style="text-align: left;">
                    </td>
                </tr>
                <tr class="">
                    <th class="ant-row tableRow1 ant-col-3"style="text-align: left;">
                        手机号码
                    </th>
                    <td class="ant-row tableRow1 ant-col-5 custMobile"style="text-align: left;">
                    </td>
                    <th class="ant-row tableRow1 ant-col-3 "style="text-align: left;" >
                    </th>
                    <td class="ant-row tableRow1 ant-col-5 email"style="text-align: left;">
                    </td>
                </tr>

                </tbody>
            </table>
            <div class="markdown tongdunInfo"  data-reactid="43" style="display:none">
                <h4 class="tableTitle" data-reactid="44" style="margin:10px 0;">个人基本信息核查</h4>
                <table cellspacing="1"style="border: 1px solid #ccc;" cellpadding="0" class="form-detail column-two transparent">
                    <tr class="firstRow">
                        <th class="ant-row tableRow ant-col-3" style=" font-size: 14px;   font-weight: 600; text-align: center; background-color: rgb(255, 255, 255);">
                            检查项目
                        </th>
                        <th class="ant-row tableRow ant-col-5"style="white-space: nowrap;font-size:14px;font-weight: 600;text-align: center;">
                            风险等级
                        </td>
                        <th class="ant-row tableRow ant-col-3" style=" font-size: 14px;    font-weight: 600; text-align:center; background-color: rgb(255, 255, 255);">
                            备注
                        </th>
                    </tr>
                    <tbody class="result">
                    </tbody>
                </table>
            </div>
            <h3 class="tongdunInfo" style="display:none">附件：法院详情</h3>
            <div class="markdown court" data-reactid="43">

            </div>
        </div>
    </div>
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
            resultId:'${param.resultId}',
            customerId:'${param.customerId}'
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
             * 加载同盾数据
             */
            loadTongDunData: function () {
                $.getJSON(preLoanObject.urls.loadTongDunUrl, function (data) {
                    if(!data || data.code !== 0){
                        return;
                    }
                    var result = data.data;
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
