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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>同盾信息明细</title>
    <link rel="stylesheet" href="${ctx}/resources/css/Pre-loan.css${version}">
</head>
<body>
<div class="main">
    <div class="header">
        <h3>同盾贷前报告</h3>
        <div class="advise checkResult">
            <p>扫描建议: <span>100分，申请用户检测出高危风险，建议拒绝</span></p>
            <p>共发现 <span>19</span> 条异常信息</p>
        </div>
    </div>
    <div class="container">
        <div class="accountInfo">
            <div class="title"><h5>账户基本信息</h5></div>
            <div class="details">
                <ul class="content clearfix">
                    <li class="fl clearfix">
                        <span>姓名</span>
                        <input type="text" readonly class="custName">
                    </li>
                    <li class="fl clearfix">
                        <span>身份证号码</span>
                        <input type="text" readonly class="custIc">
                    </li>
                    <li class="fl clearfix">
                        <span>手机号码</span>
                        <input type="text" readonly class="custMobile">
                    </li>
                    <li class="fl clearfix">
                        <span>邮箱地址</span>
                        <input type="text" readonly class="email">
                    </li>
                    <li class="fl clearfix">
                        <span>报告编号</span>
                        <input type="text" readonly class="applicationId">
                    </li>
                    <li class="fl clearfix">
                        <span>报告时间</span>
                        <input type="text" readonly class="reportTime">
                    </li>
                </ul>
            </div>
        </div>
        <div class="paymentRecords riskItems" style="display:none">
            <div class="title"><h5>个人基本信息核查</h5></div>
            <div class="details">
                <table class="table">
                    <tr class="thead">
                        <td>检查项目</td>
                        <td>风险等级</td>
                        <td>备注</td>
                    </tr>
                    <tbody class="result">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="courtDetails" style="display:none">
            <div class="title"><h5>附件:法院详情</h5></div>
            <div class="details">
                <div class="loseFaithList">
                    <div class="listTitle"><h5>身份证命中法院失信名单</h5></div>
                    <div class="listDetails court">
                    </div>
                </div>
            </div>
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
            resultId: '${param.resultId}',
            customerId: '${param.customerId}'
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
                    console.log(data);
                    if (!data || data.code !== 0) {
                        return;
                    }
                    var result = data.data;
                    $('.custName').val(result['custName']);
                    $('.custIc').val(result['custIc']);
                    $('.custMobile').val(result['custMobile']);
                    $('.email').val(result['email']);
                    var apiResult = result['apiResult'];
                    if (apiResult != null) {
                        $('.applicationId').val(apiResult['application_id']);
                        $('.reportTime').val(preLoanObject.methods.toDate(apiResult['report_time']));
                        $('.checkResult').html(preLoanObject.methods.finalDecision(apiResult));
                        var riskItems = result['apiResult']['risk_items'];
                        if (riskItems && riskItems.length > 0) {
                            $('.riskItems').removeAttr('style');
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
                        preLoanObject.template.tableContentHtml += '<tr>' +
                            '<td class="tLeft">' + riskItem["item_name"] + '</td>' +
                            '<td>' + riskLevel + '</td>' +
                            '<td class="tLeft"><ul>' + preLoanObject.template.remarksHtml +
                            '</ul></td>' +
                            '</tr>';
                    }
                    $(preLoanObject.tableEl).append(preLoanObject.template.tableContentHtml);
                    if (preLoanObject.data.courtDetails.length > 0) {
                        $('.courtDetails').removeAttr('style');
                        preLoanObject.methods.courtHandle(preLoanObject.data.courtDetails);
                    }
                })

            },
            /**
             * 客户行为检测
             */
            frequencyHandle: function () {
                if (preLoanObject.data.itemDetail.hasOwnProperty('frequency_detail_list')) {
                    var frequencyDetail = preLoanObject.data.itemDetail['frequency_detail_list'];
                    for (var frequency in frequencyDetail) {
                        preLoanObject.template.remarksHtml += '<li style="display:block;font-weight:normal;width:100%">' + frequencyDetail[frequency]["detail"] + '</li>';
                    }
                }
            },
            /**
             * 多平台借贷申请检测
             */
            platformHandle: function () {
                if (preLoanObject.data.itemDetail.hasOwnProperty('platform_detail')) {
                    for (var platform in preLoanObject.data.itemDetail['platform_detail']) {
                        preLoanObject.template.remarksHtml += '<li style="display:block;font-weight:normal;width:100%">' + preLoanObject.data.itemDetail['platform_detail'][platform] + '</li>';
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
                        preLoanObject.template.remarksHtml += '<li >' + text + '</li>';
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
                                preLoanObject.template.remarksHtml += '<li >' + text + '</li>';
                            }
                            //如果是灰名单
                        } else if (name.type === 'grey_list') {
                            var text = name['fraud_type'] + ':' + name['hit_type_displayname']
                                + ' 描述:' + name['description'];
                            preLoanObject.template.remarksHtml += '<li >' + text + '</li>';

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
                    preLoanObject.template.courtHtml += '<ul class="content detailsContent">' +
                        '<li class="clearfix">' +
                        '<span>被执行人姓名</span>' +
                        '<input type="text" value="' + courtDetail["name"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>性别</span>' +
                        '<input type="text" value="' + courtDetail["gender"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>年龄</span>' +
                        '<input type="text" value="' + courtDetail["age"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>身份证号码</span>' +
                        '<input type="text" value="' + courtDetail["id_number"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>执行法院</span>' +
                        '<input type="text" value="' + courtDetail["court_name"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>省份</span>' +
                        '<input type="text" value="' + courtDetail["province"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>执行依据文号</span>' +
                        '<input type="text" value="' + courtDetail["case_number"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>立案时间</span>' +
                        '<input type="text" value="' + courtDetail["filing_time"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>执行依据文号</span>' +
                        '<input type="text" value="' + courtDetail["execution_base"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>做出执行依据单位</span>' +
                        '<input type="text" value="' + courtDetail["execution_department"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>生效法律文书确定的义务</span>' +
                        '<input type="text" value="' + courtDetail["duty"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>被执行人的履行情况</span>' +
                        '<input type="text" value="' + courtDetail["situation"] + '"/>' +
                        '</li>' +
                        '<li class="clearfix">' +
                        '<span>失信被执行人行为具体情形</span>' +
                        '<input type="text" value="' + courtDetail["discredit_detail"] + '"/>' +
                        '</li>' +
                        '</ul>';
                    if (courtItem !== 0) {
                        styleName = 'margin-top:20px';
                    }
                }
                $('.court').append(preLoanObject.template.courtHtml);
            },
            /**
             * 检查结果
             */
            finalDecision: function (data) {
                var finalScore = data['final_score'];
                var finalDecision = data['final_decision'];
                var length = data['risk_items'] ? data['risk_items'].length : 0;
                switch (finalDecision) {
                    case 'Accept':
                        return ' <p>扫描建议:<span>' + finalScore + '分, 申请用户检测出低风险，建议通过</span></p> <p>共发现 <span>' + length + '</span>条异常信息</p>';
                    case 'Review':
                        return '<p>扫描建议:<span>' + finalScore + '分, 申请用户检测出中风险，建议审核</span></p> <p>共发现' + length + '</span>条异常信息</p>';
                    case 'Reject':
                        return '<p>扫描建议:<span>' + finalScore + '分, 申请用户检测出高风险，建议拒绝</span></p> <p>共发现' + length + '</span>条异常信息</p>';
                }
            },
            //时间戳转换成日期
            toDate: function (str) {
                if(str) {
                    //零填充
                    var getZf = function (num) {
                        if (parseInt(num) < 10) {
                            num = '0' + num;
                        }
                        return num;
                    };
                    var oDate = new Date(str),
                        oYear = oDate.getFullYear(),
                        oMonth = oDate.getMonth() + 1,
                        oDay = oDate.getDate(),
                        oHour = oDate.getHours(),
                        oMin = oDate.getMinutes(),
                        oSen = oDate.getSeconds(),
                        oTime = oYear + '-' + getZf(oMonth) + '-' + getZf(oDay) + ' ' + getZf(oHour) + ':' + getZf(oMin) + ':' + getZf(oSen);//最后拼接时间
                    return oTime;
                }
            }
        }
    }

</script>