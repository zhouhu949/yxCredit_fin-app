<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/js/reportManage/yuqiReport.js${version}"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/areaQuota/city.data-3.js${version}"></script>
    <title>逾期报表</title>
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
        .orderTotalMoney , .syyhzje, .yuqiDays,.yqfx,.yhbx,.payCount,.pay_time,.dqyhzje,.salesman
        ,.hkqs,.lsyq,.hjszd,.provinceWork,.cityWork,.nowaddress,.companyName,.companyPhone,.applayMoney,.link0,.link1,.link2,.link3{
            display: none;
        }
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul style="">
                <li style="">
                    <label class="checkBoxLabel" style="margin-top: auto">选择列：</label>
                    <label class="checkBoxLabel">
                        <span>申请金额</span>
                        <input type="checkbox" value="applayMoney" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>订单总金额</span>
                        <input type="checkbox" value="orderTotalMoney" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>剩余应还总金额</span>
                        <input type="checkbox" value="syyhzje" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>逾期天数</span>
                        <input type="checkbox" value="yuqiDays" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>逾期罚息</span>
                        <input type="checkbox" value="yqfx" name="dx">
                    </label>
                    <%--<label class="checkBoxLabel">--%>
                        <%--<span>应还本息</span>--%>
                        <%--<input type="checkbox" value="yhbx" name="dx">--%>
                    <%--</label>--%>
                    <label class="checkBoxLabel">
                        <span>分期数</span>
                        <input type="checkbox" value="payCount" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>应还日期</span>
                        <input type="checkbox" value="pay_time" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>到期应还总金额</span>
                        <input type="checkbox" value="dqyhzje" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>办单员</span>
                        <input type="checkbox" value="salesman" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>还款期数</span>
                        <input type="checkbox" value="hkqs" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>历史逾期</span>
                        <input type="checkbox" value="lsyq" name="dx">
                    </label>


                    <label class="checkBoxLabel">
                        <span>户籍所在地</span>
                        <input type="checkbox" value="hjszd" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>省份</span>
                        <input type="checkbox" value="provinceWork" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>城市</span>
                        <input type="checkbox" value="cityWork" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>现居住地址</span>
                        <input type="checkbox" value="nowaddress" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>工作单位</span>
                        <input type="checkbox" value="companyName" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>电话</span>
                        <input type="checkbox" value="companyPhone" name="dx">
                    </label>
                    <br>
                    <label class="checkBoxLabel">
                        <span>联系人1</span>
                        <input type="checkbox" value="link0" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>联系人2</span>
                        <input type="checkbox" value="link1" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>联系人3</span>
                        <input type="checkbox" value="link2" name="dx">
                    </label>
                    <label class="checkBoxLabel">
                        <span>联系人4</span>
                        <input type="checkbox" value="link3" name="dx">
                    </label>
                </li>
            </ul>
            <div style="">
            <ul class="search_content clearfix" >
                <li><label class="lf">姓名</label>
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
                <li><label class="lf">逾期天数</label>
                    <label>
                        <input name="customerName" type="text" id="yuqiDays" class="text_add"/>
                    </label>
                </li>
                <li style="width:300px;"><label class="lf">到期应还时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="dqyhTimeStart" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li>

                <li style="width:300px;">
                    <label>
                        <input readonly="true" placeholder="结束" class="eg-date" id="dqyhTimeEnd" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li>
                <li><label class="lf">逾期金额</label>
                    <label>
                        <input name="customerName" type="text" id="yuqiMoneyStart" class="text_add"/>
                    </label>
                </li><span class="line-cut">-</span>
                <li style="">
                    <label style="">
                        <input name="customerName" type="text" id="yuqiMoneyEnd" class="text_add"/>
                    </label>
                </li>
                <li style="width:500px;margin-left: -20px;">
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
                <li style=""><label class="lf">商户名称</label>
                    <label>
                        <input name="customerName" type="text" id="merchantName" class="text_add"/>
                    </label>
                </li>
                <li style=""><label class="lf">商户编号</label>
                <label>
                    <input name="customerName" type="text" id="merchantNo" class="text_add"/>
                </label>
            </li>
            </ul>
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
                        <th>渠道编号</th>
                        <th>一级渠道</th>
                        <th>二级渠道</th>
                        <th>区域</th>
                        <th>商户编号</th>
                        <th>商户名称</th>
                        <th>订单编号</th>
                        <th>订单日期</th>
                        <th>客户姓名</th>
                        <th>身份证号</th>
                        <th>手机号</th>
                        <th>购买商品</th>
                        <%--以上为必须字段--%>
                        <th>申请金额</th>
                        <th>订单总金额</th>
                        <th>剩余应还总金额</th>
                        <th>逾期天数</th>
                        <th>逾期罚息</th>
                        <%--<th>应还本息</th>--%>
                        <th>分期数</th>
                        <th>应还日期</th>
                        <th>到期应还总金额</th>
                        <th>办单员</th>
                        <th>还款期数</th>
                        <th>历史逾期</th>

                        <th>户籍所在地</th>
                        <th>省份</th>
                        <th>城市</th>
                        <th>现居住地址</th>
                        <th>工作单位</th>
                        <th>电话</th>
                        <th>联系人1</th>
                        <th>联系人2</th>
                        <th>联系人3</th>
                        <th>联系人4</th>
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

