<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/js/customerManage/approveRecord.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css">
    <title>流程跟踪详细</title>
    <style type="text/css">
        .commonManager table ul li {
            float: left;
            margin: 0 15px 10px 0;
        }
        .commonManager table ul li input, .commonManager table ul li select{
            height: 28px;
            padding: 4px;
            margin: 0;
            border: 1px solid #d5d5d5;
            outline: none;
        }
        #btn_search{
            width: 70px;
            height: 28px;
        }
        #menu-edit .add_menu tr{
            margin-bottom:10px;
        }
        #menu-edit .add_menu td{
            line-height:45px !important;
        }
        #Res_list thead th{
            color:#307ecc;
            font-size: 13px;
        }
        #btn{
            display: inline-block;
        }
        #Res_list_detail thead th{
            color:#307ecc;
            font-size: 13px;
        }
        #Res_list_detail_paginate{
            display: none;
        }
        #Res_list_info{
            margin-left:10px;
        }
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <div id="content" style="width: 100%;float: right;height: 100%;overflow: auto;">
        <div class="Res commonManager" style="padding-left: 20px;padding-right: 20px">
            <div class="table_res_list">
                <input type="hidden" id='orderId' value="${orderId}" name="orderId"/>
                <table style="text-align: center;" id="Record_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>环节</th>
                        <th>审批状态</th>
                        <th>金额</th>
                        <th>拒贷原因</th>
                        <th>时间</th>
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
<script>
    var start = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1099-06-16 23:59:59', //设定最小日期为当前日期
        max: laydate.now(), //最大日期
        istime: true,
        istoday: false,
        choose: function(datas){
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1099-06-16 23:59:59',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate.skin('danlan');  //加载皮肤，参数lib为皮肤名
    laydate(start);
    laydate(end);
</script>
</html>


