<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/26
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>征信报告</title>
    <%@include file="../common/taglibs.jsp" %>
    <%@include file="../common/importDate.jsp" %>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <style>
        .tableList {
            text-align: center;
        }

        .tableList th {
            text-align: center;
            border: 1px solid #d0d5d8;
            background-color: #f1f4f6;
            height: 32px;
            font-size: 14px;
        }

        .tableList td {
            text-align: center;
            border: 1px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }

        .tablecusTomer td {
            text-align: center;
            border: 1px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }

        .tableListBorder td {
            text-align: center;
            border: 0px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }
    </style>
    <script>
        //格式时间
        function timestampToTime(timestamp) {
            var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
            Y = date.getFullYear() + '-';
            M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
            D = date.getDate() + ' ';
            h = date.getHours() + ':';
            m = date.getMinutes() + ':';
            s = date.getSeconds();
            return Y+M+D+h+m+s;
        }
        //合同金额
        function formatBorrowAmount(borrowAmount){
//            return borrowAmount*1000;
            if(borrowAmount == -7){
                return "0-1000"
            }else if(borrowAmount == -6){
                return "1000-2000"
            }else if(borrowAmount == -5){
                return "2000-3000"
            }else if(borrowAmount == -4){
                return "3000-4000"
            }else if(borrowAmount == -3){
                return "4000-6000"
            }else if(borrowAmount == -2){
                return "6000-8000"
            }else if(borrowAmount == -1){
                return "8000-10000"
            }else if(borrowAmount == 0){
                return "未知"
            }else if(borrowAmount == 1){
                return "10000 - 20000"
            }else{
                return (borrowAmount-1)*20000 +'-'+ borrowAmount*20000 ;
            }
        }

        //欠款金额
        function formatArrearsAmount(arrearsAmount){
            if(arrearsAmount !='' && arrearsAmount !=null){
                return '<span style="color: red;">'+arrearsAmount/100000+'</span>';
            }else{
                return "";
            }

        }

        //还款状态
        function FormatRepayState(repayState,node){
            if(repayState == 0){
                return "未知";
            }else if(repayState == 1){
                return "正常";
            }else if(repayState == 2){
//                console.log($(node).html());
//                $(node).prev().css("color","red");
                return "<span style='color: red;'>M1</span>";
            }else if(repayState == 3){
                return "<span style='color: red;'>M2</span>";
            }else if(repayState == 4){
                return "<span style='color: red;'>M3</span>";
            }else if(repayState == 5){
                return "<span style='color: red;'>M4</span>";
            }else if(repayState == 6){
                return "<span style='color: red;'>M5</span>";
            }else if(repayState == 7){
                return "<span style='color: red;'>M6</span>";
            }else if(repayState == 8){
                return "<span style='color: red;'>M6+</span>";
            }else if(repayState == 9){
                return "已还清";
            }
        }

        //借款类型
        function FormatBorrowType(borrowType){
            if(borrowType == 0){
                return "未知";
            }else if(borrowType == 1){
                return "个人信贷";
            }else if(borrowType == 2){
                return "个人抵押";
            }else if(borrowType == 3){
                return "企业信贷";
            }else if(borrowType == 4){
                return "企业抵押";
            }else{
                return"未知";
            }
        }

        //借款状态
        function formatBorrowState(borrowState){
            if(borrowState == 0){
                return "未知";
            }else if(borrowState == 1){
                return "<span style='color: red'>拒贷</span>";
            }else if(borrowState == 2){
                return "批贷已放款";
            }else if(borrowState == 3){
                return "待放款";
            }else if(borrowState == 4){
                return "借款人放弃申请";
            }else if(borrowState == 5){
                return "审核中";
            }else if(borrowState == 6){
                return "待放款";
            }
        }
        //页面初始化
        $(function () {
           var customerJson=${customerJson};
           var ninetyOneRule =customerJson.customer.ninetyOneRule;
            if(ninetyOneRule){
                var loanInfo= JSON.parse(ninetyOneRule).loanInfo;
                console.log(loanInfo);
                if(loanInfo.length>0){
                    for(var i =0 ;i<loanInfo.length;i++){
                        /**
                         * "companyCode":"P2P554684",   公司代码
                         "borrowAmount":-6,           合同金额
                         "contractDate":1498665600000,合同日期(未批贷时为借款日期)
                         "arrearsAmount":0,           欠款金额
                         "repayState":9,              还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
                         "borrowType":1,              借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
                         "borrowState":2,             借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3同6意义相同）
                         "loanPeriod":1               批贷期数 （实际期数）
                         */
                        $("#91ZXBody").append(
                                '<tr>' +
                                '<td> '+ i +'</td>' +
                                '<td> '+loanInfo[i].companyCode+'</td>' +
                                '<td> '+formatBorrowAmount(loanInfo[i].borrowAmount)+'</td>' +
                                '<td> '+timestampToTime(loanInfo[i].contractDate)+'</td>' +
                                '<td> '+formatArrearsAmount(loanInfo[i].arrearsAmount)+'</td>' +
                                '<td> <span>'+FormatRepayState(loanInfo[i].repayState,this)+'</span></td>' +
                                '<td> '+FormatBorrowType(loanInfo[i].borrowType)+'</td>' +
                                '<td> '+formatBorrowState(loanInfo[i].borrowState)+'</td>' +
                                '<td> '+loanInfo[i].loanPeriod+'</td>' +
                                '</tr>'
                        );
                    }
                }else{
                    $("#91ZXBody").append('' +
                            '<tr>' +
                            '<td colspan="9" style="color: red;">' +
                            '----暂无数据----' +
                            '</td>' +
                            '</tr>');
                }
            }else{
                $("#91ZXBody").append('' +
                        '<tr>' +
                        '<td colspan="9" style="color: red;">' +
                        '----暂无数据----' +
                        '</td>' +
                        '</tr>');
            }


        })
    </script>

</head>
<body>
<div style="width: 100%;text-align: center">
    <div style="width: 1100px;margin:0 auto;border: 5px solid #eaeced;border-radius:10px 10px 10px 10px;">
        <!--box-shadow: 2px 4px 6px #f0f9ff-->
        <div style="width: 1000px;margin:0 auto;">
            <div style="height:50px;margin-top: 30px;font-size: 30px;font-weight: bolder">
                <span>征信报告</span>
            </div>
           <%--结果展示--%>
            <div>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">91规则结果集</span>
                    </div>
                </div>
                <div>
                    <table width="100%" class="tablecusTomer">
                        <%-- "companyCode":"P2P554684",   公司代码
                             "borrowAmount":-6,           合同金额
                             "contractDate":1498665600000,合同日期(未批贷时为借款日期)
                             "arrearsAmount":0,           欠款金额
                             "repayState":9,              还款状态 0.未知1.正常2.M1 3.M2 4.M3 5.M4 6.M5 7.M6 8.M6+ 9.已还清
                             "borrowType":1,              借款类型 0.未知1.个人信贷2.个人抵押3.企业信贷4.企业抵押
                             "borrowState":2,             借款状态 0.未知1.拒贷2.批贷已放款4.借款人放弃申请5.审核中6.待放款（3同6意义相同）
                             "loanPeriod":1               批贷期数 （实际期数）
    --%>
                        <tbody id="91ZXBody">
                            <tr>
                                <td>序号</td>
                                <td>公司代码</td>
                                <td>合同金额</td>
                                <td>合同日期</td>
                                <td>欠款金额</td>
                                <td>还款状态</td>
                                <td>借款类型</td>
                                <td>借款状态</td>
                                <td>批贷期数</td>
                            </tr>

                        </tbody>
                        <%--<tr>--%>
                            <%--<td width="10%">公司代码</td>--%>
                            <%--<td width="90%" id="companyCode"></td>--%>
                            <%--&lt;%&ndash;<td width="40%" id="customerNameText"></td>&ndash;%&gt;--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">合同金额</td>--%>
                            <%--<td width="90%" id="borrowAmount"></td>--%>
                            <%--&lt;%&ndash;<td width="40%" id="customerCardText"></td>&ndash;%&gt;--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">合同日期</td>--%>
                            <%--<td width="50%" id="contractDate"></td>--%>
                            <%--&lt;%&ndash;<td width="40%" id="customerTelText"></td>&ndash;%&gt;--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">欠款金额</td>--%>
                            <%--<td width="90%" colspan="2" id="arrearsAmount">--%>
                                <%--&lt;%&ndash;<table id="contactList" class="tableListBorder" width="100%"></table>&ndash;%&gt;--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">还款状态</td>--%>
                            <%--<td width="90%" colspan="2" id="repayState"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">借款类型</td>--%>
                            <%--<td width="90%" colspan="2" id="borrowType"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">借款状态</td>--%>
                            <%--<td width="90%" colspan="2" id="borrowState"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td width="10%">批贷期数</td>--%>
                            <%--<td width="90%" colspan="2" id="loanPeriod"></td>--%>
                        <%--</tr>--%>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>
</body>

</html>
