var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        debugger
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        param.orderId=$("#hdOrderId").val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//交易明细记录展示查询对象
var g_showJYDetail = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        debugger
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        param.orderId=$("#showJYDetailOrderId").val();
        param.loanId=$("#showJYDetailId").val();

        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userColl = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var loanId = $('#loanId').val();
        param.loanId = loanId;
        //自行处理查询参数
        param.fuzzySearch = g_userColl.fuzzySearch;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userPlan = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var loanId = $('#loanId').val();
        param.loanId = loanId;
        //自行处理查询参数
        param.fuzzySearch = g_userColl.fuzzySearch;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userProgress = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var loanId = $('#loanId').val();
        param.loanId = loanId;
        //自行处理查询参数
        param.fuzzySearch = g_userProgress.fuzzySearch;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userList = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userList.fuzzySearch;
        if (g_userList.fuzzySearch) {
            param.account = $("[name='account']").val();
            param.trueName = $("[name='trueName']").val();
            param.tel = $("[name='mobile']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};

//获取放款表信息
var g_userManage_loan = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        // 自行处理查询参数
        param.fuzzySearch = g_userManage_loan.fuzzySearch;
        if($("#custName").val()){
            param.personName = $("#custName").val();
        }
        if ($("#isoverdue").val()){
            param.isoverdue = $("#isoverdue").val();
        }
        var beginTime = $("#beginTime").val();
        if(beginTime != null && beginTime != ''){
            param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
        }
        var endTime = $("#endTime").val();
        if(endTime != null && endTime != ''){
            param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//页面初始化
$(function () {
    g_userManage_loan.tableUser = $('#Res_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage_loan.getQueryCondition(data);
            Comm.ajaxPost("loanManageController/getLoanManage", JSON.stringify(queryFilter), function (result) {
                debugger
                // console.log(result);
                var returnData = {};
                var resData = result.data.list;
                var resPage = result.data;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.total;
                returnData.recordsFiltered = resPage.total;
                returnData.data = resData;
                callback(returnData);
            }, "application/json")
        },
        "order": [],
        "columns" :[
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data":'orderNo',"searchable":false,"orderable" : false},
            {"data":'merchantName',"searchable":false,"orderable" : false},
            {"data":'customerName',"searchable":false,"orderable" : false},
            {"data":'tel',"searchable":false,"orderable" : false},
            {"data":'applayMoney',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'fee',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'amountCollection',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'periods',"searchable":false,"orderable" : false},
            {"data":'expirationDate',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? formatTime(data) : "";
                }
            },
            {"data":'realAmount',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'realFee',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'payCollection',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'alreadyPayCount',"searchable":false,"orderable" : false},
            {"data":'derateAmount',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'redAmount',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'totalFine',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data": null, "searchable": false, "orderable": false,
                "render": function (data, type, row, meta) {
                    if(data.overdueCount>0){
                        return "逾期";
                    }else {
                        return "未逾期";
                    }
                }
            },
            {"data":'settleAmount',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":'settleFee',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return data ? parseFloat(data).toFixed(2) : "0.00";
                }},
            {"data":null,"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data.order_state=="9")
                    {
                        return data.alterTime ? formatTime(data.alterTime) : "";
                    }
                    else
                    {
                        return "";
                    }
                }
            },
            {"data": null, "searchable": false, "orderable": false,
                "render": function (data, type, row, meta) {
                    if (data.settle_state == '1') {
                        if (data.settleType == 0)
                            return '提前结清中'
                        else if (data.settleType == 1)
                            return '非正常结清中'
                    }else  if (data.settle_state == '2') {
                        if (data.settleType == 0)
                            return '提前结清'
                        else if (data.settleType == 1)
                            return '非正常结清'
                    }
                    if(data.order_state=="0"){
                        return "未提交";
                    }else if(data.order_state=="1"){
                        return "借款申请";
                    }else if(data.order_state=="2"){
                        return "自动化审批通过";
                    }else if(data.order_state=="3"){
                        return "自动化审批拒绝";
                    }else if(data.order_state=="4"){
                        return "自动化审批异常";
                    }else if(data.order_state=="5"){
                        return "人工审批通过";
                    }else if(data.order_state=="6"){
                        return "人工审批拒绝";
                    }else if(data.order_state=="7"){
                        return "合同确认";
                    }else if(data.order_state=="8"){
                        return "放款成功";
                    }else if(data.order_state=="9"){
                        return "结清";
                    }else if(data.order_state=="10"){
                        return "关闭";
                    }else if(data.order_state.toString()=="-1"){
                        return "待确认合同";
                    }else if(data.order_state.toString()=="-2"){
                        return "合同确认";
                    }else if(data.order_state.toString()=="-3"){
                        return "待付预付款";
                    }else if(data.order_state.toString()=="-4"){
                        return "待发货";
                    }else if(data.order_state.toString()=="-5"){
                        return "已发货";
                    }else if(data.order_state.toString()=="-6"){
                        return "还款中";
                    }else if(data.order_state.toString()=="-7"){
                        return "退回";
                    }
                }
            },
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            //交易明细展示
            var btnJYDetailList = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="showJYDetail(\'' + data.id + '\',\'' + data.order_id + '\')">支付流水 </a>');
            var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getRepaymentList(\''+data.order_id+'\',\''+data.settle_state+'\')">还款明细 </a>');
            var btnnormalSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\''+data.id+'\',\'0\')">正常结清 </a>');
            var btnSettle=  $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\''+data.id+'\',\'1\')">非正常结清 </a>');
            if (data.settle_state == '0'&&data.order_state!='9') {
                btnSettle = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\''+data.id+'\',\'1\')">非正常结清 </a>');
                btnnormalSettle = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\'' + data.id + '\',\'0\')">正常结清 </a>');
            } //是否有提前结清信息
            else  if (data.settle_state == '1'&&data.order_state!='9') {
                //正常结清
                if(data.settleType == "0" && data.msrState=="1") {
                    btnnormalSettle = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\'' + data.id + '\',\'0\')">编辑正常结清 </a>');
                }
                else
                {
                    btnnormalSettle = "";
                }
                //非正常结清
                if(data.settleType == "1" && data.msrState=="1") {

                    btnSettle = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\'' + data.id + '\',\'1\')">编辑非正常结清 </a>');
                }
                else
                {
                    btnSettle = "";
                }
            }else {
                btnSettle="";
                btnnormalSettle = "";
            }
            /**else {
                if(data.settle_state != '0') {
                    if(data.msr2State=="2") {
                    btnSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="seeSettle(\''+data.id+'\',\'1\')">查看非正常结清 </a>');
                    }else {
                        btnSettle="";
                    }
                    if(data.msr1State=="2") {
                        btnnormalSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="seeSettle(\''+data.id+'\',\'0\')">查看正常结清 </a>');
                    }else {
                        btnnormalSettle="";
                    }
                }
            }*/
            $("td", row).eq(22).append(btn).append(btnnormalSettle).append(btnSettle).append(btnJYDetailList);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage_loan.fuzzySearch = true;
                g_userManage_loan.tableUser.ajax.reload();
                $("#paramSearch").val("");
            });
            //搜索重置
            $("#btn_search_reset").click(function() {
                $("#custName").val("");
                $("#isoverdue").val("-1");
                g_userManage_loan.fuzzySearch = false;
                g_userManage_loan.tableUser.ajax.reload();
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage_loan.tableUser.on("order.dt search.dt", function() {
        g_userManage_loan.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
})

//提前结清还款情况下的还款明细
function getRepaymentSettle(orderId)
{
    var paramFilter1 = {};
    var page1 = {};
    var param1 = {};
    //自行处理查询参数
    param1.fuzzySearch = false;
    param1.orderId=orderId;
    paramFilter1.param = param1;
    page1.firstIndex = 0;
    page1.pageSize = 10;
    paramFilter1.page = page1;

    Comm.ajaxPost('afterLoan/getRepaymentDerateListSP', JSON.stringify(paramFilter1), function (result) {
        var resData = result.data.list;
        var magRepaymentAllHtml='<tr><td colspan="17" style="text-align: left">还款明细</td></tr><tr><th>序号</th><th>期数</th><th>还款时间</th><th>应还金额</th><th>本金</th><th>利息</th><th>服务包</th><th>红包抵扣金额</th><th>状态</th><th>逾期天数</th><th>逾期手续费</th><th>逾期罚息</th><th>减免金额</th><th>减免状态</th><th>展期天数</th><th>展期费用</th><th>还款方式</th></tr>';
        var allMoney = 0;
        if (resData)
        {
            for (var i = 0; i < resData.length; i++)
            {
                if (resData[i]["state"] != 2)
                {
                    continue;
                }
                if (!resData[i]["actualAmount"])
                {
                    allMoney += parseFloat(resData[i]["amount"]);
                    continue;
                }
                var state = "";
                if (resData[i]["state"]=='1') {
                    state = "未还";
                }
                else if (resData[i]["state"]=='2') {
                    state = "已还";
                }else if (resData[i]["state"]=='3'){
                    state = "逾期";
                }else if (resData[i]["state"]=='4'){
                    state = "还款确认中";
                }
                var derateState = "";
                if (resData[i]["derateState"]== "") {
                    derateState = "暂无减免";
                }
                else if (resData[i]["derateState"]=="1") {
                    derateState = "待减免";
                }else if (resData[i]["derateState"]=="3"){
                    derateState = "已减免";
                }
                var huankuanType = "";
                if(resData[i]["huankuanType"] == '0'){
                    huankuanType = "线下";
                }else if(resData[i]["huankuanType"] == '1'){
                    huankuanType = "线上";
                }else{
                    if(resData[i]["settleType"] == '1' || resData[i]["settleType"]=='2'){
                        huankuanType = "线上";
                    }else{
                        huankuanType =  "";
                    }
                }

                magRepaymentAllHtml += '<tr><td>'+(i+1)+'</td><td>'+resData[i]["payCount"]+'</td><td>'+formatTime(resData[i]["payTime"])+'</td><td>'+resData[i]["sunAmount"]+'</td>' +
                '<td>'+resData[i]["amount"]+'</td><td>'+resData[i]["fee"]+'</td><td>'+(resData[i]["servicePackageAmount"] ? resData[i]["servicePackageAmount"] : 0)+'</td><td>'+(resData[i]["redAmount"] ? resData[i]["redAmount"] : 0)+'</td>' +
                '<td>'+state+'</td><td>'+resData[i]["overdueDays"]+'</td><td>'+(resData[i]["penalty"] ? resData[i]["penalty"] : 0)+'</td><td>'+(resData[i]["default_interest"] ? resData[i]["default_interest"] : 0)+'</td><td>'+(resData[i]["derateAmount"] ? resData[i]["derateAmount"] : 0)+'</td>' +
                '<td>'+derateState+'</td><td>'+(resData[i].extensionDay ? resData[i].extensionDay : 0)+'</td><td>'+(resData[i].extensionAmount ? resData[i].extensionAmount : 0)+'</td><td>'+huankuanType+'</td></tr>';
            }
        }

        Comm.ajaxPost('afterLoan/getRepaymentSettleList',orderId, function (data) {
            var settleType = data.data.settle.settleType;
            if(settleType=='0'){
                magRepaymentAllHtml+='<tr><td colspan="17" style="text-align: left">结清处理</td></tr><tr><td>结清状态：</td><td style="color: #03b4b6 ">正常结清</td><td>应还本金：</td><td colspan="2">'+allMoney.toFixed(2)+'元</td><td >提前结清费用：</td> <td colspan="2" style="text-align: left">'+data.data.settle.settleFee+'</td><td >实还金额：</td> <td colspan="2" style="text-align: left">'+data.data.details[0].amount+'</td><td >还款时间：</td> <td colspan="5" style="text-align: left">'+formatTime(data.data.details[0].creatTime)+'</td></tr>';
            }//非正常结清
            else {
                magRepaymentAllHtml+='<tr><td colspan="17" style="text-align: left">结清处理</td></tr><tr><td >结清状态：</td><td  style="color: red">非正常结清</td><td >结清金额：</td><td colspan="2">'+data.data.settle.settleAmount+'</td><td >实还金额：</td> <td colspan="2" style="text-align: left">'+data.data.details[0].amount+'</td><td>还款时间：</td><td colspan="8">'+formatTime(data.data.details[0].creatTime)+'</td></tr>';
            }
            $("#repaymentAllList").html(magRepaymentAllHtml);
            var  layerAdd=layer.open({
                type : 1,
                title : false,
                shadeClose : false,
                area : [ '90%', '70%' ],
                content : $('#Settle').show(),
                success : function(index, layero) {
                }
            })
        },"application/json");

    },"application/json");
}

//获取还款明细
function  getRepaymentList(orderId,seState) {
    if (seState == "2")
    {
        //提前结清还款情况下的还款历史
        getRepaymentSettle(orderId);
        return;
    }
    layer.open({
        type : 1,
        title : false,
        shadeClose : false,
        area : [ '90%', '70%' ],
        content : $('#afterLoan').show(),
        success : function(index, layero) {
            if(!$("#hdOrderId").val()){
            $("#hdOrderId").val(orderId);
            g_userManage.tableUser = $('#afterLoanTable').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            debugger
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('afterLoan/getRepaymentDerateListSP', JSON.stringify(queryFilter), function (result) {
                //封装返回数据
                var returnData = {};
                var resData = result.data;
                returnData.draw = data.draw;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                // console.log(returnData.data);
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data": "id","orderable" : false},
            {"data": "payCount","orderable" : false},
            {"data": "payTime","orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data": "sunAmount","orderable" : false},
            {"data": "amount","orderable" : false},
            {"data": "fee","orderable" : false},
            //{"data": "servicePackageAmount","orderable" : false},
            {"data": "servicePackageAmount","orderable" : false,
                "render": function (data, type, row, meta) {
                    debugger
                    if (data==null){
                        return "0";
                    }else {
                        return data;
                    }
                }
            },
            {"data": "redAmount","orderable" : false},
            {"data":'state',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                if (data=='1') {
                    return "未还";
                }
                else if (data=='2') {
                    return "已还";
                }else if (data=='3'){
                    return "逾期";
                }else if (data=='4'){
                    return "还款确认中";
                } else {
                    return "";
                }
            }},
            {"data": "overdueDays","orderable" : false},
            //{"data": 'totalFine',"orderable" : false},
            {"data": null, "searchable": false, "orderable": false,
                "render": function (data, type, row, meta) {
                    return data.penalty;
                }
            },
            {"data": "default_interest","orderable" : false, "render":function (data, type, row, meta) {
                if (data) {
                    return data;
                } else {
                    return "0";
                }
            }},//逾期罚息
            {"data":'derateAmount',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                if (data=='') {
                    return "0";
                } else {
                    return data;
                }
            }},
            {"data":'derateState',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                if (data== "") {
                    return "暂无减免";
                }
                else if (data=="1") {
                    return "待减免";
                }else if (data=="3"){
                    return "已减免";
                } else {
                    return "";
                }
            }},

            {"data": "huankuanType" ,"searchable":false,"orderable" : false,defaultContent:"","render":function (data, type, row, meta) {//还款形式：0-线下 1-线上
                console.log();
                if(data == '0'){
                    return "线下";
                }else if(data == '1'){
                    return "线上";
                }else{
                    if(row.settleType == '1' || row.settleType=='2'){
                        return "线上";
                    }else{
                        return  "";
                    }
                }
            }},
            {"data": "actual_amount","orderable" : false, "render":function (data, type, row, meta) {
                if (data) {
                    return parseFloat(data).toFixed(2);
                } else {
                    return "0";
                }
            }},//实际还款金额
            {"data": "extensionDay","orderable" : false, "render":function (data, type, row, meta) {
                if (data) {
                    return data;
                } else {
                    return "0";
                }
            }},//展期天数
            {"data": "extensionAmount","orderable" : false, "render":function (data, type, row, meta) {
                if (data) {
                    return data;
                } else {
                    return "0";
                }
            }},//展期费用
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""},
        ],
        "createdRow": function(row, data, index, settings, json) {
            var btnXianXia ='';
            var bntExtend = $('<a class="tabel_btn_style_disabled" disable="ture">&nbsp;展期&nbsp;</a>');
            if (data.state=="1" && seState == '0'){
                bntExtend = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="repayMentExtend(\''+data.orderId+'\',\''+data.id+'\',\''+data.payCount+'\')">&nbsp;展期&nbsp;</a>');
            }
            var btnCostReduction = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="costReduction(\''+data.orderId+'\',\''+data.id+'\')">费用减免</a>');
            // var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getCollection(event)">催收记录 </a>');
            // var planbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getPlanlist(\''+data.loan_id+'\')"> 还款计划</a>');
            // var collectionbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="collection(\''+data.customerId+'\')"> 电催分配</a>');
            if (data.state=="2"||data.state=="1"||data.state=="4"){
                btnCostReduction = $('<a class="tabel_btn_style_disabled" disable="ture">费用减免&nbsp;</a>');
            }
            //根据还款状态判断线下还款按钮的展示状态(当期和之前已逾期的可以进行线下还款操作)
            // if(data.state=='1'||data.state=='3'){
            debugger;
            var now = getNowFormatDate();
            var payTimeMon=(data.payTime).substring(0,6);
            // console.log(data);
            debugger;
            if(seState == '0'){
                // if(data.state=='3' || (payTimeMon　==now && data.state=='1') || (payTimeMon < now && data.state=='1')|| (data.state=='1' && parseFloat(payTimeMon)==(parseFloat(now)+1))){
                if(data.state=='3' || data.state=='1'){//逾期的和未还的都可以操作线下还款来还款
                    btnXianXia='<a class="tabel_btn_style"  onclick="payForXianxia(\''+data.orderId+'\',\''+data.id+'\',\''+data.amount+'\',\''+data.fee+'\',\''+data.servicePackageAmount+'\',\''+data.penalty+'\',\''+data.state+'\',\''+data.payCount+'\',\''+data.default_interest+'\',\''+data.derateAmount+'\',\''+data.magDerateId+'\')">线下还款</a>';
                }                                                                    // 订单id              还款id             本金               利息                    服务包                           逾期利息                                                  逾期罚息                      本期减免                   减免表id
            }
            $("td", row).eq(19).append(btnXianXia);
            $("td", row).eq(19).append(bntExtend);
            $("td", row).eq(19).append(btnCostReduction);
        },
        "initComplete" : function(settings,json) {
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableUser.on("order.dt search.dt", function() {
            g_userManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }else {
           $("#hdOrderId").val(orderId);
           g_userManage.tableUser.ajax.reload();
    }
        }
    })
}
//页面初始化完成绑定逾期费用输入框改变事件
$(function () {
    $("#penalty_xianxia").bind('input propertychange', function() {
        debugger
        var amount=$("#amount").val();//本金
        var fee=$("#fee").val();//利息
        var servicePackageAmount = $("#servicePackageAmount_xianxia").val();//服务包
        var default_interest = $("#default_interest").val();//逾期罚息
        if(isNaN($.trim($("#penalty_xianxia").val()))){
            $("#warn").css("display","inline");
            return false;
        }else{
            $("#warn").css("display","none");
        }
        var penalty = $.trim($("#penalty_xianxia").val());
        penalty=(!penalty)||penalty=='null'||isNaN(penalty)?'0':penalty;
        var  default_interest =$.trim($("#default_interest").val());
        default_interest=(!default_interest)||default_interest=='null'||isNaN(default_interest)?'0':default_interest;
        var derateAmount=$("#derateAmount").val();
        derateAmount=(!derateAmount)||derateAmount=='null'||isNaN(derateAmount)?'0':derateAmount;
        var allMoney = parseFloat(amount)+parseFloat(fee)+parseFloat(servicePackageAmount)+parseFloat(penalty)+parseFloat(default_interest)-derateAmount;
        $("#xianxiaMoney").html(allMoney.toFixed(2));
    });
})

//单期线下还款           订单id     还款id      本金   利息      服务包          逾期利息 还款状态  期数      逾期罚息           本期减免    减免表id
function payForXianxia(orderId,repaymentId,amount,fee,servicePackageAmount,penalty,state,payCount,default_interest,derateAmount,magDerateId){
    // //此处判断改期之前是否还有未还的或者逾期的还款分期 发送ajax请求
    // Comm.ajaxPost('afterLoan/getBeforeNotPaied',JSON.stringify(param), function (data) {
    //     layer.msg(data.msg,{time:2000});
    //     if(data.code=="0"){
    //         layer.close(xianxiaLayer);
    //         g_userManage.tableUser.ajax.reload();
    //     }
    // },"application/json","","","",false);
    var xianxiaLayer=layer.open({
        type : 1,
        title : '确认线下还款',
        maxmin : true,
        shadeClose : false,
        area : [ '400px', '300px' ],
        content : $('#sure_xianxia'),
        btn : [ '确认', '取消' ],
        success : function(index, layero) {
            //清空输入框
            $("#xianxiaMoney").html('');
            $("#amount").val('');
            $("#fee").val('');
            $("#servicePackageAmount_xianxia").val('');
            $("#penalty_xianxia").val('');
            $("#default_interest").val('');
            $("#derateAmount").val('');

            debugger;
            $("#xianxiaMoney").html((parseFloat(amount)+parseFloat(fee)+parseFloat((!servicePackageAmount||servicePackageAmount=='null')?'0':servicePackageAmount)+parseFloat((!penalty||penalty=='null')?'0':penalty)+parseFloat((!default_interest||default_interest=='null')?'0':default_interest) - parseFloat((!derateAmount||derateAmount=='null')?'0':derateAmount)).toFixed(2));
            $("#amount").val(amount);//本金
            $("#fee").val(fee);//利息
            $("#servicePackageAmount_xianxia").val((!servicePackageAmount||servicePackageAmount=='null')?'0':servicePackageAmount);//服务包

            if(state == 3){
                $("#yuqiLi").css("display","block");
                $("#yuqiFaXi").css("display","block");
                $('#jianMian').css("display","block");
                $("#penalty_xianxia").val((!penalty||penalty=='null')?'0':penalty);//逾期费用
                $("#default_interest").val((!default_interest||default_interest=='null')?'0':default_interest);//逾期罚息
                $('#derateAmount').val(!derateAmount?'0':derateAmount);//当期减免
            }else{
                $("#yuqiLi").css("display","none");
                $("#yuqiFaXi").css("display","none");
                $('#jianMian').css("display","none");
            }
        },
        yes:function(index,layero){
            var param={};
            param.orderId=orderId;
            param.repaymentId=repaymentId;
            param.payCount=payCount;
            param.magDerateId = magDerateId;
            // param.sumAmount=sumAmount;
            param.sumAmount=$("#xianxiaMoney").html();
            //js验证
            if(isNaN($("#penalty_xianxia").val())){
                layer.msg("逾期费用输入有误，请输入正确数字",{time:2000});
                return false;
            }
            if(isNaN(param.sumAmount)){
                layer.msg("输入信息有误，请输入正确数字",{time:2000});
                return false;
            }
            Comm.ajaxPost('afterLoan/getXianxiaMoney',JSON.stringify(param), function (data) {
                layer.msg(data.msg,{time:2000});
                if(data.code=="0"){
                    layer.close(xianxiaLayer);
                    g_userManage.tableUser.ajax.reload();
                }
                },"application/json"
            );
        }
    });
}

//获取当前时间
function getNowFormatDate() {//YYYYMM
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year+month;
    return currentdate;
}

//提前结清
function  Settle(loanId,type) {
    // debugger
    var settleRecordId="";
    var settleState="";
    var normal="";
    var param = {};
    param.loanId=loanId;
    param.type=type;
    Comm.ajaxPost('loanManageController/getSettleData', JSON.stringify(param), function (result) {
        debugger
        if(result.code=="0"){
            debugger
            //封装返回数据
            var resData = result.data;
            console.log(resData);
            var magRepaymentAllList=resData.magRepaymentAllList;
            var settleRecordAmount=0;//减免金额
            var allAmount=0;//所有待还金额
            var periodAllAmount=0;//本期所有待还金额
            debugger
            var settleRecord=resData.settleRecord;
            var magRepaymentAllHtml='<tr><td colspan="10" style="text-align: left">还款明细</td></tr><tr><th style="width: 5%">期数</th><th style="width: 9%">应还总额</th><th style="width: 9%">应还本金</th><th style="width: 9%">应还利息</th><th style="width: 9%">减免金额</th><th style="width: 9%">逾期手续费</th><th style="width: 9%">逾期罚息</th><th style="width: 9%">逾期天数</th><th style="width: 9%">服务包金额</th><th style="width: 9%">状态</th></tr>';
            for(var i=0;i<magRepaymentAllList.length;i++){
                var magRepayment=magRepaymentAllList[i];
                //var servicePackageList=magRepayment.servicePackageList;
                // var servicePackageAmount=0;
                // for(var j=0;j<servicePackageList.length;j++){
                //     servicePackageAmount+=parseFloat(servicePackageList[j].amount);
                // }
                if(magRepayment.servicePackageAmount==""){
                    magRepayment.servicePackageAmount=0;
                }
                var state=magRepayment.state;
                periodAllAmount=0.0;
                periodAllAmount=parseFloat(magRepayment.repaymentAmount)+parseFloat(magRepayment.servicePackageAmount);
                if(state=='2'){
                    state='已还';
                }else if(state=='1'){
                    state='未还';
                    allAmount=parseFloat(allAmount)+parseFloat(magRepayment.amount)+parseFloat(magRepayment.servicePackageAmount);
                }else if(state=='3'){
                    if(resData.normal=='0'){
                        layer.msg("有逾期未还款，不能提前还清",{time:2000});
                        return;
                    }
                    state='逾期';
                }else   if(state=='4'){
                    state='还款确认中';
                }
                if(magRepayment.servicePackageAmount==""){
                    magRepayment.servicePackageAmount=0;
                }
                    magRepaymentAllHtml+='<tr><td >'+magRepayment.payCount+'</td><td>'+periodAllAmount.toFixed(2)+'</td><td>'+magRepayment.amount+'</td><td>'+magRepayment.fee+'</td><td>'+magRepayment.derateAmount+'</td><td>'+magRepayment.penalty+'</td><td>' + (magRepayment.defaultInterest?magRepayment.defaultInterest:0) + '</td><td>'+magRepayment.overdueDays+'</td><td>'+magRepayment.servicePackageAmount+'</td><td>'+state+'</td></tr>';
            }
            settleState=resData.loanManage.settleState;

            normal=resData.normal;
            //是否已经有结清数据
            if(settleState=='1'){
                debugger
                if(settleRecord ==null || settleRecord =='' ){
                    layer.msg("暂无数据",{time:2000});
                    return false;
                }
                settleRecordId=settleRecord.id;
                settleRecordAmount=parseFloat(settleRecord.settleAmount);
                //正常结清
                if(resData.normal=='0'){
                    magRepaymentAllHtml+='<tr><td colspan="10" style="text-align: left">提前结清处理</td></tr><tr><td>结清状态：</td><td colspan="1" style="color: #03b4b6 ">正常结清</td><td>待还总金额：</td><td id="realRepaymentMoney">'+(allAmount + parseFloat(settleRecord.settleFee)).toFixed(2) +'元</td><td >提前结清费用：</td> <td colspan="5" style="text-align: left"><input name="trueName" placeholder="元"  id="allAmount"  oninput="changRealMoney('+allAmount.toFixed(2)+')"  type="text" class="text_add" placeholder="元" maxlength="20" value="'+settleRecord.settleFee+'"/></td></tr>';
                }//非正常结清
                else {
                    magRepaymentAllHtml+='<tr><td colspan="10" style="text-align: left">结清处理</td></tr><tr><td >结清状态：</td><td  style="color: red">非正常结清</td><td >待还总金额：</td><td id="realRepaymentMoney">'+settleRecordAmount+'元</td><td >结清金额：</td><td ><input name="trueName" placeholder="元"  id="allAmount"  oninput="changRealMoney(0)"  type="text" class="text_add" placeholder="元" maxlength="20" value="'+settleRecordAmount+'"/></td><td>有效时间：</td><td colspan="3"><input readonly="true" placeholder="有效时间" class="eg-date" id="beginTime" value="'+formatTime(settleRecord.effectiveTime)+' 23:59:59" type="text"/></td></tr>';
                }
            }else {
                //正常结清
                if(resData.normal=='0'){
                    magRepaymentAllHtml+='<tr><td colspan="10" style="text-align: left">提前结清处理</td></tr><tr><td>结清状态：</td><td colspan="1" style="color: #03b4b6 ">正常结清</td><td>待还总金额：</td><td id="realRepaymentMoney">'+allAmount.toFixed(2)+'元</td><td >提前结清费用：</td><td  colspan="5"  style="text-align: left"><input name="trueName" placeholder="元"  id="allAmount"  oninput="changRealMoney('+allAmount.toFixed(2)+')" type="text" class="text_add" placeholder="元" maxlength="20" value="0"/></td></tr>';
                }//非正常结清
                else {
                    magRepaymentAllHtml+='<tr><td colspan="10" style="text-align: left">结清处理</td></tr><tr><td >结清状态：</td><td  style="color: red">非正常结清</td><td >待还总金额：</td><td id="realRepaymentMoney"></td><td>结清金额：</td><td><input name="trueName" id="allAmount"  oninput="changRealMoney(0)" style="width: 75%" type="text" placeholder="元" class="text_add" maxlength="20"/></td><td>有效时间：</td><td colspan="3"><input readonly="true" placeholder="有效时间" class="eg-date" id="beginTime"  type="text"/></td></tr>';
                }
            }
            $("#repaymentAllList").html(magRepaymentAllHtml);
            if(resData.normal!='0'){
                var beginTime = {
                    elem: '#beginTime',
                    format: 'YYYY-MM-DD hh:mm:ss',
                    //min: '1999-01-01 00:00:00',
                    min: laydate.now(),
                    // istime: true,
                    istoday: false,
                    choose: function(value){
                        if (value.indexOf(":") == -1)
                        {
                            $("#beginTime").val(value + " 23:59:59");
                        }
                        else
                        {
                            $("#beginTime").val(value.split(" ")[0]+ " 23:59:59");
                        }
                    }
                };
                laydate(beginTime);
            }
            var  layerAdd=layer.open({
                type : 1,
                title : '提前结清',
                shadeClose : false,
                area : [ '80%', '70%' ],
                content : $('#Settle').show(),
                btn : [ '保存', '取消' ],
                success : function(index, layero) {
                },
                yes:function(index, layero){
                    debugger
                    var param={};
                    if(normal=='1'){
                        param.beginTime=$("#beginTime").val().replace(/[^0-9]/ig,"");//字符串去除非数字;
                        param.allAmount = $("#allAmount").val().trim();
                        if (isNaN(param.allAmount)||param.allAmount=="" || param.allAmount<=0) {
                            layer.msg("结清金额格式错误！",{time:2000});
                            return;
                        }
                        if ($("#beginTime").val()==""){
                            layer.msg("请选择有效时间！",{time:2000});
                            return;
                        }
                    }else {
                        param.settleFee=$("#allAmount").val().trim();
                        param.allAmount=$("#allAmount").val().trim();
                        if (isNaN(param.allAmount) || param.allAmount<=0) {
                            layer.msg("结清金额格式错误！",{time:2000});
                            return;
                        }
                    }
                    param.normal=normal;//0正常结清 1非正常结清
                    param.loanId=loanId;
                    param.settleRecordId=settleRecordId;
                    param.type="0";//添加
                    if(settleRecordId!=""){
                        param.type="1";//修改
                    }
                    Comm.ajaxPost('loanManageController/addSettleData',JSON.stringify(param),function(data){
                            layer.msg(data.msg,{time:1000},function(){
                                if(data.code=="0"){
                                    layer.close(layerAdd);
                                    g_userManage_loan.tableUser.ajax.reload();
                                }
                            });
                        }, "application/json"
                    );
                }
            })
        }else {
            layer.msg(result.msg,{time:2000});
            return;
        }
    },"application/json");
}

//改变提前结清的待还金额
function changRealMoney(allMoney)
{
    if ($("#allAmount").val() && isNaN(parseFloat($("#allAmount").val())))
    {
        layer.msg("请输入正确的金额",{time:1000},function(){
            $("#allAmount").val("");
            $("#realRepaymentMoney").text(allMoney + "元");
        })
        return;
    }
    var money = allMoney + ($("#allAmount").val() ? parseFloat($("#allAmount").val()) : 0);
    $("#realRepaymentMoney").text(money.toFixed(2) + "元");
}

//查看提前结清
function  seeSettle(loanId,type) {
    debugger
    var settleRecordId="";
    var settleState="";
    var normal="";
    var param = {};
    param.loanId=loanId;
    param.type=type;

    Comm.ajaxPost('loanManageController/getSettleData', JSON.stringify(param), function (result) {
        if(result.code=="0"){
            debugger
            //封装返回数据
            var resData = result.data;
            var magRepaymentAllList=resData.magRepaymentAllList;
            var settleRecordAmount=0;//减免金额
            var allAmount=0;//所有待还金额
            var periodAllAmount=0;//本期所有待还金额
            var settleRecord=resData.settleRecord;
            var magRepaymentAllHtml='<tr><td colspan="9" style="text-align: left">还款明细</td></tr><tr><th style="width: 8%">期数</th><th style="width: 11.5%">应还总额</th><th style="width: 11.5%">应还本金</th><th style="width: 11.5%">应还利息</th><th style="width: 11.5%">减免金额</th><th style="width: 11.5%">逾期利息</th><th style="width: 11.5%">逾期天数</th><th style="width: 11.5%">服务包金额</th><th style="width: 11.5%">状态</th></tr>';
            for(var i=0;i<magRepaymentAllList.length;i++){
                var magRepayment=magRepaymentAllList[i];
                //var servicePackageList=magRepayment.servicePackageList;
                // var servicePackageAmount=0;
                // for(var j=0;j<servicePackageList.length;j++){
                //     servicePackageAmount+=parseFloat(servicePackageList[j].amount);
                // }
                var state=magRepayment.state;
                var settleType=magRepayment.settleType;
                periodAllAmount=0.0;
                if(state=='2'){
                    state='已还';
                }else if(state=='1'){
                    state='未还';
                }else if(state=='3'){
                    state='逾期';
                }else if(state=='4'){
                    state='还款确认中';
                }
                debugger
                if(magRepayment.servicePackageAmount==""){
                    magRepayment.servicePackageAmount=0;
                }
                if(settleType=="1"||settleType=="2"){
                    periodAllAmount=parseFloat(magRepayment.amount)+parseFloat(magRepayment.fee)+parseFloat(magRepayment.totalFine)+parseFloat(magRepayment.servicePackageAmount);
                }
                if (state == '未还' || state == '逾期')
                {
                    var currNotPay = parseFloat(magRepayment.amount)+parseFloat(magRepayment.fee)+parseFloat(magRepayment.totalFine)+parseFloat(magRepayment.servicePackageAmount);
                    allAmount += currNotPay;
                }
                magRepaymentAllHtml+='<tr><td >'+magRepayment.payCount+'</td><td>'+periodAllAmount.toFixed(2)+'</td><td>'+magRepayment.amount+'</td><td>'+magRepayment.fee+'</td><td>'+magRepayment.derateAmount+'</td><td>'+magRepayment.totalFine+'</td><td>'+magRepayment.overdueDays+'</td><td>'+magRepayment.servicePackageAmount+'</td><td>'+state+'</td></tr>';
            }
            settleState=resData.loanManage.settleState;
            normal=resData.normal;
            //是否已经有结清数据
            if(settleState=='2'){
                settleRecordId=settleRecord.id;
                settleRecordAmount=parseFloat(settleRecord.settleAmount);
                //正常结清
                debugger
                if(resData.normal=='0'){
                    magRepaymentAllHtml+='<tr><td colspan="9" style="text-align: left">提前结清处理</td></tr><tr><td>结清状态：</td><td colspan="1" style="color: #03b4b6 ">正常结清</td><td>待还总金额：</td><td >'+allAmount.toFixed(2)+'元</td><td >提前结清费用：</td> <td colspan="4" style="text-align: left"><input name="trueName" placeholder="元"  id="allAmount" disabled="disabled"   type="text" class="text_add" placeholder="元" maxlength="20" value="'+settleRecord.settleFee+'"/></td></tr>';
                }//非正常结清
                else {
                    magRepaymentAllHtml+='<tr><td colspan="9" style="text-align: left">结清处理</td></tr><tr><td >结清状态：</td><td  style="color: red">非正常结清</td><td >待还总金额：</td><td >'+allAmount.toFixed(2)+'元</td><td >结清金额：</td><td ><input name="trueName" placeholder="元"  id="allAmount"  type="text" class="text_add" placeholder="元" maxlength="20" value="'+settleRecordAmount+'" disabled="disabled" /></td><td>有效时间：</td><td colspan="2"><input readonly="true" placeholder="有效时间" class="eg-date" id="beginTime" value="'+formatTime(settleRecord.effectiveTime)+'" type="text" disabled="disabled" /></td></tr>';
                }
            }
            $("#repaymentAllList").html(magRepaymentAllHtml);
            // if(resData.normal!='0'){
            //     var beginTime = {
            //         elem: '#beginTime',
            //         format: 'YYYY-MM-DD hh:mm:ss',
            //         //min: '1999-01-01 00:00:00',
            //         min: laydate.now(),
            //         istime: true,
            //         istoday: false
            //     };
            //     laydate(beginTime);
            // }
            var  layerAdd=layer.open({
                type : 1,
                title : '提前结清',
                shadeClose : false,
                area : [ '80%', '70%' ],
                content : $('#Settle').show(),
                success : function(index, layero) {
                }
            })
        }else {
            layer.msg(result.msg,{time:2000});
            return;
        }
    },"application/json");
}
//催收记录table初始化
function collTable() {
    if(g_userColl.tableUser) {
        g_userColl.tableUser.ajax.reload();
    }else{
        g_userColl.tableUser = $('#collRecordTable').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userColl.getQueryCondition(data);
                Comm.ajaxPost('collection/list', JSON.stringify(queryFilter), function (result) {
                    // console.log(result);
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data;

                    returnData.draw = data.draw;
                    returnData.recordsTotal = resData.total;
                    returnData.recordsFiltered = resData.total;
                    returnData.data = resData.list;
                    // console.log(returnData.data);
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },
                {"data": "tel","orderable" : false},
                {"data": "callTime","orderable" : false},
                {"data": 'relationship',"orderable" : false},
                {"data": 'remark',"orderable" : false},
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                },
                {"data": "id" ,'class':'hidden',"searchable":false,"orderable" : false}
            ],
            //处理序号用
            "columnDefs": [
                {
                    "searchable": false,
                    "orderable": false,
                    "targets": 1
                }
            ],
            "createdRow": function(row, data, index, settings, json) {
                var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="order_detail" onclick="deleteColl(\''+data.id+'\')">删除</a>');
                $("td", row).eq(5).append(btn);

                //时间格式转换
                var payTime = data.payTime;
                if (!payTime) {
                    payTime = '';
                }else{
                    payTime = payTime.substr(0,4)+"-"+payTime.substr(4,2)+"-"+payTime.substr(6,2);
                }
                $('td', row).eq(8).html(payTime);


            },
            "initComplete" : function(settings,json) {


            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userColl.tableUser.on("order.dt search.dt", function() {
            g_userColl.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw()
    }
}
//添加催收记录
function addColl() {
    var loanId = $('#loanId').val();
    layerAdd = layer.open({
        type : 1,
        title : "添加催收记录",
        shadeClose : true, //点击遮罩关闭层
        area : [ '550px', '340px' ],
        content : $('#Add_coll').show(),
        btn : [ '保存', '取消' ],
        success :function () {
            $('#collForm')[0].reset();
        },
        yes:function(index, layero){
            var magCollectionRecord={};
            magCollectionRecord.loanId = loanId;
            magCollectionRecord.tel = tel;
            magCollectionRecord.callTime = callTime;
            magCollectionRecord.relationship = relationship;
            magCollectionRecord.remark = remark;

            Comm.ajaxPost('collection/add',JSON.stringify(magCollectionRecord),function(data){
                    layer.msg(data.msg,{time:1000},function(){
                        layer.close(layerAdd);
                        window.$('#collRecordTable').dataTable().fnDraw(false);
                    });
                }, "application/json"
            );
        }
    });
}
//删除催收记录
function deleteColl(id) {
    layer.confirm('确定要删除该记录吗？', {icon: 3, title:'操作提示'}, function(index){
        Comm.ajaxPost('collection/delete',{id:id},function(data){
                layer.msg(data.msg,{time:1000},function(){
                    window.$('#collRecordTable').dataTable().fnDraw(false);
                });
            }
        );
    })
}
//返回
function backColl() {
    layer.closeAll();
}

//查看还款计划
function getPlanlist(e) {
    //var target = e.target||window.event.target;
    //var loanId = $(target).parent('td').parent('tr').children('td').eq(6).html();
    $('#loanId').val(e);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#planList').show(),
        success : function(index, layero) {
           planTable();
        }
    })
}
//还款计划table初始化
function planTable() {
    if(g_userPlan.tableUser) {
        g_userPlan.tableUser.ajax.reload();
    }else{
        g_userPlan.tableUser = $('#planListTable').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userPlan.getQueryCondition(data);
                Comm.ajaxPost('repayment/getListSP', JSON.stringify(queryFilter), function (result) {
                    // console.log(result);
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data;

                    returnData.draw = data.draw;
                    returnData.recordsTotal = resData.total;
                    returnData.recordsFiltered = resData.total;
                    returnData.data = resData.list;
                    // console.log(returnData.data);
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },
                {"data": null,"orderable" : false},
                {"data": "repaymentAmount","orderable" : false},
                {"data": 'fee',"orderable" : false},
                {"data": null,"orderable" : false},
                {"data": 'state',"orderable" : false},

            ],
            //处理序号用
            "columnDefs": [
                {
                    "searchable": false,
                    "orderable": false,
                    "targets": 1
                }
            ],
            "createdRow": function(row, data, index, settings, json) {

                // //时间格式转换
                // var payTime = data.payTime;
                // if (!payTime) {
                //     payTime = '';
                // }else{
                //     payTime = payTime.substr(0,4)+"-"+payTime.substr(4,2)+"-"+payTime.substr(6,2);
                // }
                // $('td', row).eq(1).html(payTime);
                //
                // var monthPay = (parseFloat(data.repaymentAmount) + parseFloat(data.fee)).toFixed(2);
                // $('td', row).eq(4).html(monthPay);
                //
                // var state = data.state;
                // if (!state) {
                //     state = '';
                // }else if(state =='1'){
                //     state = '未还'
                // }else if(state =='2'){
                //     state = '以还'
                // }else if(state =='3'){
                //     state = '逾期'
                // }
                // $('td', row).eq(5).html(state);
            },
            "initComplete" : function(settings,json) {

            },
            "fnDrawCallback": function(){
                var api = this.api();
                var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                api.column(0).nodes().each(function(cell, i) {
                    cell.innerHTML = startIndex + i + 1;
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userPlan.tableUser.on("order.dt search.dt", function() {
            g_userPlan.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                // console.log(cell.length);
                cell.innerHTML = (i + 1)
            })
        }).draw()
    }
}

//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
    //+ " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}
//时间转换
function formatTime1(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}

//分配电催-选择用户
function collection(customerId) {
    var setUpLayer = layer.open({
            type : 1,
            title : '选择',
            shadeClose : false,
            area : ['730px','400px'],
            content : $('#userDiv'),
            btn : [ '保存', '取消' ],
            success:function (index, layero) {
                if(!g_userList.tableUser) {
                    g_userList.tableUser = $('#user_list').dataTable($.extend({
                        'iDeferLoading': true,
                        "bAutoWidth": false,
                        "Processing": true,
                        "ServerSide": true,
                        "sPaginationType": "full_numbers",
                        "bPaginate": true,
                        "bLengthChange": false,
                        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                        "ajax": function (data, callback, settings) {
                            var queryFilter = g_userList.getQueryCondition(data);
                            Comm.ajaxPost('user/list', JSON.stringify(queryFilter), function (result) {
                                var returnData = {};
                                var resData = result.data;
                                var resPage = result.page;
                                returnData.draw = data.draw;
                                returnData.recordsTotal = resPage.resultCount;
                                returnData.recordsFiltered = resPage.resultCount;
                                returnData.data = resData;
                                callback(returnData);
                            }, "application/json")
                        },
                        "order": [],
                        "columns": [
                            {'data': null, 'class': 'hidden', "searchable": false, "orderable": false},
                            {
                                "className": "childBox_setUp",
                                "orderable": false,
                                "data": null,
                                "width": "60px",
                                "searchable": false,
                                "render": function (data, type, row, meta) {
                                    return '<input type="checkbox" value="' + data.userId + '" style="cursor:pointer;" isChecked="false">'
                                }
                            },
                            {"data": 'account', "searchable": false, "orderable": false},
                            {"data": 'trueName', "searchable": false, "orderable": false},
                            {"data": 'tel', "searchable": false, "orderable": false},
                            {
                                "data": null,
                                "searchable": false,
                                "orderable": false,
                                "render": function (data, type, row, meta) {
                                    if (data.status == 1) {
                                        return '启用'
                                    } else {
                                        return '停用'
                                    }
                                }
                            },
                        ],
                        "initComplete": function (settings, json) {
                            //搜索
                            $("#user_btn_search").click(function () {
                                g_userList.fuzzySearch = true;
                                g_userList.tableUser.ajax.reload(function () {
                                    $("#userCheckBox_All").attr("checked", false);
                                });
                            });
                            //重置
                            $("#user_btn_search_reset").click(function () {
                                $('input[name="account"]').val("");
                                $('input[name="trueName"]').val("");
                                $('input[name="mobile"]').val("");
                                g_userList.fuzzySearch = false;
                                g_userList.tableUser.ajax.reload(function () {
                                    $("#userCheckBox_All").attr("checked", false);
                                });
                            });
                            //全选
                            $("#userCheckBox_All").click(function (J) {
                                if (!$(this).prop("checked")) {
                                    $("#user_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                                } else {
                                    $("#user_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                                }
                            });
                            //单选行
                            $("#user_list tbody").delegate('tr', 'click', function (e) {
                                var target = e.target;
                                if (target.nodeName == 'TD') {
                                    var input = target.parentNode.children[1].children[0];
                                    var isChecked = $(input).attr('isChecked');
                                    if (isChecked == 'false') {
                                        if ($(input).attr('checked')) {
                                            $(input).attr('checked', false);
                                        } else {
                                            $(input).attr('checked', 'checked');
                                        }
                                        $(input).attr('isChecked', 'true');
                                    } else {
                                        if ($(input).attr('checked')) {
                                            $(input).attr('checked', false);
                                        } else {
                                            $(input).attr('checked', 'checked');
                                        }
                                        $(input).attr('isChecked', 'false');
                                    }
                                }
                            });
                        }
                    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                    g_userList.tableUser.on("order.dt search.dt", function () {
                        g_userList.tableUser.column(0, {
                            search: "applied",
                            order: "applied"
                        }).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1
                        })
                    }).draw();
                    g_userList.userSearchFlag = false;
                }
            },
            yes : function(index, layero) {
                var selectArray = $("#user_list tbody input:checked");
                if(!selectArray || selectArray.length!=1){
                    layer.msg("请选择一个用户！");
                    return;
                }
                var userIds = new Array();
            $.each(selectArray,function(i,e){
                var val = $(this).val();
                userIds.push(val);
            });
            // console.log(userIds)
            if(userIds.length>0){
                var addParam = {
                    customerId:customerId,
                    userId:userIds[0]
                }
                Comm.ajaxPost('afterLoan/setCollection',JSON.stringify(addParam),function(data){
                    layer.msg(data.msg,{time:1000},function () {
                        layer.close(setUpLayer);
                    });
                }, "application/json");
            }
        }
    });
}

//费用减免
function  costReduction(orderId,id) {
    debugger
    //清空无用信息
    $("#reductionAmount").val("");
    $("#lbl1").html("");
    $("#lblEffectiveData").val("");
    $("#reductionAmount").removeAttr("disabled");
    $("#lblEffectiveData").removeAttr("disabled");

    var  derate_id="";
    var  derate_amount="";
    var  derate_state="";
    var  approval_state="";
    var  state = '';
    var setUpLayer = layer.open({
        type : 1,
        title : '费用减免',
        shadeClose : false,
        area : ['740px','350px'],
        content : $('#settleDiv'),
        btn : [ '提交', '取消' ],
        success:function (index, layero) {
            var param = {};
            param.orderId = orderId;
            param.isoverdue="1";
            param.derateState="2";
            param.id=id;
            Comm.ajaxPost('afterLoan/getAfterLoanDetails', JSON.stringify(param), function (result) {
                debugger
                var resData = result.data;
                derate_id=resData.derate_id;//减免表id
                derate_amount=resData.derate_amount;//减免金额
                derate_state=resData.derate_state;//状态 0未完成；1完成;2无效;3已减免
                approval_state=resData.approval_state;//审批状态 0拒绝；1同意
                $("#lblPersonName").html(resData.person_name);//用户姓名
                $("#lblTel").html(resData.tel);//用户电话
                $("#lblPayCount").html(resData.pay_count);//期数
                $("#lblFee").html(resData.fee);//利息
                debugger
                if(resData.servicePackageAmount==""){
                    $("#servicePackageAmount").html("0");
                }else {
                    $("#servicePackageAmount").html(resData.servicePackageAmount);//服务包金额
                }
                $("#lblRepaymentAmount").html(parseFloat(resData.sunAmount).toFixed(2));//本金利息服务包的钱 应还款额
                $("#lblAmount").html(parseFloat(resData.amount).toFixed(2));//本金
                $("#lblPenalty").html(parseFloat(resData.totalFine).toFixed(2));//逾期手续费+逾期罚息 = 逾期费
                $("#lblOverdueDays").html(resData.overdue_days);//逾期天数
                $("#lblTotal").html((parseFloat(resData.sunAmount) + parseFloat(resData.totalFine)).toFixed(2));//还款总额 = 本金利息服务包+逾期费
                if(resData.normal!='0'){
                    var beginTime = {
                        elem: '#lblEffectiveData',
                        format: 'YYYY-MM-DD hh:mm:ss',
                        //min: '1999-01-01 00:00:00',
                        min: laydate.now(),
                        // istime: true,
                        istoday: false,
                        choose: function(value, date, endDate){
                            if (value.indexOf(":") == -1)
                            {
                                $("#lblEffectiveData").val(value + " 23:59:59");
                            }
                            else
                            {
                                $("#lblEffectiveData").val(value.split(" ")[0]+ " 23:59:59");
                            }
                        }
                    };
                    laydate(beginTime);

                    if (resData.effectiveData)
                    {
                        $("#lblEffectiveData").val(formatTime(resData.effectiveData) + " 23:59:59");
                    }
                }
                debugger
                //状态 0未完成；1完成;2无效;3已减免
                if (derate_state =="0"){//未完成
                    $("#lbl1").html("已提交！");
                    $("#reductionAmount").val(parseFloat(derate_amount).toFixed(2));
                    $('#lblEffectiveData').val(formatTime1(resData.effectiveData));
                    $("#reductionAmount").attr("disabled","disabled");
                    $("#lblEffectiveData").attr("disabled","disabled");
                }else if(derate_state =="2"){//无效
                    $("#lbl1").html("已失效！");
                    $("#reductionAmount").val(parseFloat(derate_amount).toFixed(2));
                    $('#lblEffectiveData').val(formatTime1(resData.effectiveData));
                    $("#reductionAmount").attr("disabled","disabled");
                    $("#lblEffectiveData").attr("disabled","disabled");
                }else if(derate_state =="3"){//已减免
                    $("#lbl1").html("已减免！");
                    $("#reductionAmount").val(parseFloat(derate_amount).toFixed(2));
                    $('#lblEffectiveData').val(formatTime1(resData.effectiveData));
                    $("#reductionAmount").attr("disabled","disabled");
                    $("#lblEffectiveData").attr("disabled","disabled");
                }else if(derate_state == '1'){//减免审核完成
                    //审批状态 0拒绝；1同意
                    if(approval_state=="1"){//审批通过 无法更改信息
                        $("#lbl1").html("已通过！");
                        $("#reductionAmount").val(parseFloat(derate_amount).toFixed(2));
                        $('#lblEffectiveData').val(formatTime1(resData.effectiveData));
                        $("#reductionAmount").attr("disabled","disabled");
                        $("#lblEffectiveData").attr("disabled","disabled");
                    }else if(approval_state=="0"){//审批拒绝 可以更改
                        $("#lbl1").html("未通过！");
                        $("#reductionAmount").val(parseFloat(derate_amount).toFixed(2));
                        $('#lblEffectiveData').val(formatTime1(resData.effectiveData));
                        $("#reductionAmount").removeAttr("disabled");
                        $("#lblEffectiveData").removeAttr("disabled");
                    }
                }
                // if(approval_state=="1"){ //审批状态 0拒绝；1同意
                //     $("#lbl1").html("已通过！");
                //     $("#reductionAmount").attr("disabled","disabled");
                //     $("#lblEffectiveData").attr("disabled","disabled");
                // }else if(approval_state=="0"){//审批状态 0拒绝；1同意
                //     if(derate_state=="0"){//状态 0未完成；1完成;2无效;3已减免',
                //         $("#lbl1").html("已提交！");
                //         $("#reductionAmount").attr("disabled","disabled");
                //         $("#lblEffectiveData").attr("disabled","disabled");
                //     }else if(derate_state == '1'){//未通过可以再次更改提交
                //         $("#lbl1").html("未通过！");
                //         $("#reductionAmount").removeAttr("disabled");
                //         $("#lblEffectiveData").removeAttr("disabled");
                //     }
                // }
            },"application/json");
        },
        yes : function(index, layero) {
                var param = {};
                param.orderId = orderId;
                param.repayment_id=id;
                if ($("#lblEffectiveData").val()==""){
                    layer.msg("请选择有效时间！",{time:2000});
                    return;
                }
                var val = $("#reductionAmount").val();
                var ival = parseFloat(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN

                if (isNaN(ival) || ival <= 0) {
                    layer.msg("减免金额格式错误！",{time:2000});
                    return;
                }
                var lblTotal=parseFloat($("#lblTotal").html());
                if (ival>lblTotal){
                    layer.msg("减免金额不能大于还款总额！",{time:2000});
                    return;
                }
                if(approval_state=="1"){
                    layer.msg("减免金额已审核通过！",{time:1000});
                    return;
                }
                if(derate_state=="0"){
                    layer.msg("减免已提交！能重复提交！",{time:1000});
                    return;
                }
                if(derate_state=="2" ){
                    layer.msg("减免已失效无法提交！",{time:1000});
                    return;
                }
                if(derate_state=="3" ){
                    layer.msg("减免已完成无法提交！",{time:1000});
                    return;
                }
                param.reductionAmount = val;
                param.derate_state="0";
                param.derate_id=derate_id;
                param.effective_data=$("#lblEffectiveData").val().replace(/[^0-9]/ig,"");//字符串去除非数字;
            Comm.ajaxPost('afterLoan/orderSettle',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.close(setUpLayer);
                    g_userManage.tableUser.ajax.reload();
                });
            }, "application/json");
        }
    });
}

//展期
function  repayMentExtend(orderId,id,payCount) {
    $("#extensionAmount").val("");
    $("#extensionDay").val("");
    var extensionLayer = layer.open({
        type : 1,
        title : '展期',
        shadeClose : false,
        area : ['370px','200px'],
        content : $('#extensionDiv'),
        btn : [ '提交', '取消' ],
        success:function (index, layero) {
        },
        yes : function(index, layero) {
            var param = {};
            param.order_id = orderId;
            param.repayment_id = id;
            param.pay_count = payCount;

            var extensionAmountVal = $("#extensionAmount").val();
            var extensionDayVal = $("#extensionDay").val();
            if (extensionAmountVal==""){
                layer.msg("请填写展期费用！",{time:2000});
                return;
            }
            if (extensionDayVal==""){
                layer.msg("请填写展期天数！",{time:2000});
                return;
            }

            var iextensionAmountVal = parseFloat(extensionAmountVal);//如果变量字符类型的数则转换为float类型 如果不是则ival为NaN
            if (isNaN(iextensionAmountVal) || iextensionAmountVal <= 0) {
                layer.msg("展期费用格式错误！",{time:2000});
                return;
            }
            if (!(/(^[1-9]\d*$)/.test(extensionDayVal))) {
                layer.msg("展期天数格式错误！",{time:2000});
                return;
            }

            param.extension_amount = extensionAmountVal;
            param.extension_day = extensionDayVal;
            Comm.ajaxPost('afterLoan/repaymentExtension',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.close(extensionLayer);
                    g_userManage.tableUser.ajax.reload();
                });
            }, "application/json");
        }
    });
}


//费用减免查看
function  costReductionSee(orderId,id) {
    var  derate_id="";
    var  derate_amount="";
    var  derate_state="";
    var  approval_state="";
    var setUpLayer = layer.open({
        type : 1,
        title : '费用减免',
        shadeClose : false,
        area : ['740px','350px'],
        content : $('#settleDiv'),
        success:function (index, layero) {
            var param = {};
            param.orderId = orderId;
            param.isoverdue="1";
            param.derateState="2";
            param.id=id;
            Comm.ajaxPost('afterLoan/getAfterLoanDetails', JSON.stringify(param), function (result) {
                // console.log(result);
                //封装返回数据
                debugger
                var returnData = {};
                var resData = result.data;
                derate_id=resData.derate_id;
                derate_amount=resData.derate_amount;
                derate_state=resData.derate_state;
                approval_state=resData.approval_state;
                $("#lblPersonName").html(resData.person_name);
                $("#lblTel").html(resData.tel);
                $("#lblPayCount").html(resData.pay_count);
                $("#lblFee").html(resData.fee);
                // $("#lblManageFee").html(resData.manageFee);
                // $("#lblQuickTrialFee").html(resData.quickTrialFee);
                $("#lblRepaymentAmount").html(parseFloat(resData.repayment_amount).toFixed(2));
                $("#lblAmount").html(parseFloat(resData.amount).toFixed(2));
                $("#lblPenalty").html(parseFloat(resData.penalty).toFixed(2));
                $("#lblOverdueDays").html(resData.overdue_days);
                $("#lblTotal").html((parseFloat(resData.repayment_amount ) + parseFloat(resData.penalty)).toFixed(2));
                debugger
                if (derate_state!="")
                {
                    $("#reductionAmount").val(parseFloat(derate_amount).toFixed(2));
                    //$("#sltReduction").val("0");
                    $("#reductionAmount").attr("disabled","disabled");
                }
                if(approval_state=="1"){
                    $("#lbl1").html("已通过！");
                }else if(approval_state=="0"){
                    $("#lbl1").html("未通过！");
                }
            },"application/json");
        },
        yes : function(index, layero) {
            var param = {};
            param.orderId = orderId;
            param.repayment_id=id;
            //电催完成状态
            //param.electronic_examination_state="2";
            if(derate_state!="1"){
                if(derate_state=="0"){
                    layer.msg("减免金额审核中！",{time:1000});
                    return;
                }
                var val = $("#reductionAmount").val();
                var ival = parseFloat(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN
                if (isNaN(ival)) {
                    layer.msg("减免金额格式错误！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }

                var lblTotal=parseFloat($("#lblTotal").html());
                if (lblTotal<ival){
                    layer.msg("减免金额不能大于还款金额！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }
                param.reductionAmount = val;
                param.derate_link='0';
                param.derate_state='0';
            }else {
                layer.msg("减免金额已审核完成！",{time:1000});
                return;
            }
            Comm.ajaxPost('afterLoan/orderSettle',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.close(setUpLayer);
                });
            }, "application/json");
        }
    });
}



function sltReductionOnchange(){
    debugger
    if ($("#sltReduction").val()=="0") {
        $("#reductionAmount").removeAttr("readonly");
    }else {
        $("#reductionAmount").attr("readonly", "readonly");    //去除readonly属性
    }
}
//交易明细展示
function  showJYDetail(id,orderId) {
    debugger
    $('#showJYDetailId').val(id);
    $('#showJYDetailOrderId').val(orderId);
    var showJYDetailLayer = layer.open({
        type : 1,
        title : '交易明细展示',
        shadeClose : false,
        area : ['80%','80%'],
        content : $('#showJYDetail'),
        success:function (index, layero) {


                if(g_showJYDetail.tableUser) {
                    g_showJYDetail.tableUser.ajax.reload();
                }else{
                    g_showJYDetail.tableUser = $('#withdrawal_list').dataTable($.extend({
                        'iDeferLoading':true,
                        "bAutoWidth" : false,
                        "Processing": true,
                        "ServerSide": true,
                        "sPaginationType": "full_numbers",
                        "bPaginate": true,
                        "bLengthChange": false,
                        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                            var queryFilter = g_showJYDetail.getQueryCondition(data);
                            Comm.ajaxPost('loanManageController/getJYDetailList', JSON.stringify(queryFilter), function (result) {
                                // console.log(result);
                                //封装返回数据
                                var returnData = {};
                                var resData = result.data;

                                returnData.draw = data.draw;
                                returnData.recordsTotal = resData.total;
                                returnData.recordsFiltered = resData.total;
                                returnData.data = resData.list;
                                // console.log(returnData.data);
                                callback(returnData);
                            },"application/json");
                        },
                        "order": [],
                        "columns": [
                            {
                                "className" : "cell-operation",
                                "data": null,
                                "defaultContent":"",
                                "orderable" : false,
                                "width" : "30px"
                            },
                            {"data": "creat_time","orderable" : false,"render":function (data) {
                                return formatTime1(data);
                            }},//交易时间
                            {"data": "batch_no","orderable" : false},//交易流水
                            {"data": "bank_name","orderable" : false},//银行名称
                            {"data": "bank_card","orderable" : false},//银行卡号
                            {"data": "amount","orderable" : false},//交易金额
                            {"data": "baofu_code","orderable" : false},//交易状态码
                            {"data": "state","orderable" : false,"render":function (data) {
                                //0待查询；1交易成功，2交易失败,4交易异常
                                if(data == '0'){
                                    return "待查询"
                                }else if(data == '1'){
                                    return "交易成功"
                                }else if(data == '2'){
                                    return "交易失败"
                                }else if(data == '4'){
                                    return "交易异常"
                                }
                            }},//交易状态
                            {"data": "huankuanType","orderable" : false,"render":function (data) {
                                if(data =='0'){
                                    return"线下";
                                }else if(data == '1'){
                                    return"线上";
                                }else{
                                    return'';
                                }
                            }},//交易方式
                            {"data": "des","orderable" : false},//交易描述
                        ],
                        "columnDefs": [
                            {
                                "searchable": false,
                                "orderable": false,
                                "targets": 1
                            }
                        ],
                        "createdRow": function(row, data, index, settings, json) {
                        },
                        "initComplete" : function(settings,json) {

                        },
                        "fnDrawCallback": function(){
                            var api = this.api();
                            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
                            api.column(0).nodes().each(function(cell, i) {
                                cell.innerHTML = startIndex + i + 1;
                            });
                        }
                    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                    g_showJYDetail.tableUser.on("order.dt search.dt", function() {
                        g_showJYDetail.tableUser.column(0, {
                            search : "applied",
                            order : "applied"
                        }).nodes().each(function(cell, i) {
                            // console.log(cell.length);
                            cell.innerHTML = (i + 1)
                        })
                    }).draw()
                }




        },
        yes : function(index, layero) {
        }
    });
}