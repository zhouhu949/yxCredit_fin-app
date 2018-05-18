<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file = "../common/taglibs.jsp"%>
    <%@ include file = "../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <script src="${ctx}/resources/js/smsManage/sms.js${version}"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>

    <title>短信列表</title>
    <style type="text/css">
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #sign_list{font-size:13px;}
        .checkShow,.uploadContact{color:#05c1bc}
        .checkShow{margin-right: 8px;}

        .commonManager .addCommon li {
            line-height: 52px;
            width: 300px
        }

        .lf{
            margin-left: 10px;
        }
        .commonManager .addCommon li select {
            width: 160px;
            height: 28.89px;
            margin-left:10px;
        }
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
        .search_style .search_content li {
            width: 250px;
            float: left;
            height: 32px;
            line-height: 32px;
        }
        .search_style .search_content li .btn_search {
            height: 32px;
            background: #2a8bcc !important;
            background-image: -webkit-gradient(linear, left 0, left 100%, from(#3b98d6),
            to(#197ec1)) !important;
            background-image: -moz-linear-gradient(top, #3b98d6 0, #197ec1 100%) !important;
            background-image: linear-gradient(to bottom, #3b98d6 0, #197ec1 100%) !important;
            background-repeat: repeat-x !important;
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff3b98d6',
            endColorstr='#ff197ec1', GradientType=0) !important;
            line-height: 32px;
            border: 0;
            width: 60px;
            text-align: center;
            color: #FFF;
            border-radius: 3px;
            -moz-border-radius: 3px;
            -webkit-border-radius: 3px;
        }
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">短信标题</label>
                    <label>
                        <input name="sms_name" type="text" class="text_add"/>
                    </label>
                </li>
                <li style="width:300px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button id="btn_add" type="button" class="btn btn-primary queryBtn">添加</button>
                </li>
            </ul>
        </div>
        <div id="Add_procedure_style" style="display: none" >
            <div class="addCommon clearfix">
                <div>
                    <input  id="contract_id" type="hidden" />
                    <ul>
                        <li>
                            <label class="lf">短信标题</label>
                            <label>
                                <input id="sms_name" type="text" class="text_add"/>
                            </label>
                        </li>
                        <li ><label class="lf" >平台类型</label>
                            <label style="width: 173px;">
                                <select id="platform_type" style="min-width:60px;height: 28px;">
                                    <%--<option value="">请选择</option>--%>
                                    <%--<option value="0">金棒棒</option>--%>
                                    <option value="1">商品贷</option>
                                </select>
                            </label>
                        </li>
                        <li><label class="lf">短信状态</label>
                            <label style="width: 173px">
                                <select id="sms_state" style="min-width:60px;height: 28px;">
                                    <option value="">请选择</option>
                                    <option value="1">启用</option>
                                    <option value="2">停用</option>
                                </select>
                            </label>
                        </li>
                        <li style="width: 300px">
                            <label class="lf">短信内容</label>
                            <label style="width: 200px">
                                <textarea id="sms_content" style="width: 200px;height: 150px;margin-left: 24px;margin-top: 12px;"></textarea>
                            </label>
                        </li>
                        <li style="width: 301px;height: 110px; margin-top: 100px;">
                            <label class="lf">使用规则</label>
                            <form action="" method="get" style="width: 300px; height: 100px;margin-top: 20px;">
                                <li style="margin-left: 80px;margin-top: -40px;">
                                    <label><input name="sms_rule" type="checkbox" value="0" />注册 </label>
                                    <label><input name="sms_rule" type="checkbox" value="1" />忘记密码 </label>
                                    <label><input name="sms_rule" type="checkbox" value="2" />找回密码 </label>
                                    <label><input name="sms_rule" type="checkbox" value="3" />验证码 </label>
                                </li>
                                <li style="margin-left: 14px;margin-top: -10px;">
                                    <label><input name="sms_rule" type="checkbox" value="4" />放款 </label>
                                    <label><input name="sms_rule" type="checkbox" value="5" />还款成功 </label>
                                    <label><input name="sms_rule" type="checkbox" value="6" />还款失败 </label>
                                    <label><input name="sms_rule" type="checkbox" value="7" />发送物流 </label>
                                </li>
                                <li style="margin-left: 14px;margin-top: -10px;">
                                    <label><input name="sms_rule" type="checkbox" value="8" />更改物流 </label>
                                    <label><input name="sms_rule" type="checkbox" value="9" />还款提醒 </label>
                                    <label><input name="sms_rule" type="checkbox" value="10" />逾期提醒 </label>
                                </li>

                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="sign_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th></th>
                        <th>短信标题</th>
                        <th>短信环节</th>
                        <th>状态</th>
                        <th>操作</th>
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

