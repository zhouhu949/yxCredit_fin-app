var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.custName = $("#custName").val();
            param.orderNo = $("#orderNo").val();
            var btime=$("#btime").val();
            btime = btime.replace(/[\:\s\-]/g,"");
            param.beginTime = btime;

            var etime=$("#etime").val();
            etime = etime.replace(/[\:\s\-]/g,"");
            param.endTime = etime;
        }
        param.state = '7.5';
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    g_userManage.tableUser = $('#loanOrder').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('loanOrder/getList', JSON.stringify(queryFilter), function (result) {
                console.log(result);
                //封装返回数据
                var returnData = {};
                var resData = result.data;

                returnData.draw = data.draw;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
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
            {"data": "order_no","orderable" : false},
            {"data": "CUSTOMER_NAME","orderable" : false},
            {"data": "TEL","orderable" : false},
            {"data": "product_name_name","orderable" : false},
            {"data": "PERIODS","orderable" : false},
            {"data": "loan_amount","orderable" : false},
            {"data": "end_credit","orderable" : false},
            {"data": null,"orderable" : false},
            {"data": "MANAGER","orderable" : false},
            {"data": "accountBank","orderable" : false},//开户行
            {"data": "bankCard","orderable" : false},//卡号
            {"data": "CREAT_TIME","orderable" : false},
            {
                "data": null, "orderable": false,
                "defaultContent":""
            },
            {"data": "CUSTOMER_ID" ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "ID" ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "USER_ID" ,'class':'hidden',"searchable":false,"orderable" : false}

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
            //时间格式转换
            var creatTime = data.CREAT_TIME;
            if (!creatTime) {
                creatTime = '';
            }else{
                creatTime = creatTime.substr(0,4)+"-"+creatTime.substr(4,2)+"-"+creatTime.substr(6,2)+" "+creatTime.substr(8,2)+":"+creatTime.substr(10,2)+":"+creatTime.substr(12,2);
            }
            $('td', row).eq(8).html("第一次放款");
            $('td', row).eq(12).html(creatTime);
            var DelBtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="LookDel(event)">审 核</a>');
            $("td", row).eq(13).append(DelBtn);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function() {
        g_userManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});
//打开页面
function LookDel(e) {
    var target = e.target || window.event.target;
    var cust_id = $(target).parent('td').next('td').html();
    var id = $(target).parent('td').next('td').next('td').html();
    var userId = $(target).parent('td').next('td').next('td').next('td').html();
    var order_no = $(target).parent('td').parent('tr').children().eq(1).html();
    var amount =$(target).parent('td').parent('tr').children().eq(6).html();
    $("#orderId").val(id);
    $("#order_no").val(order_no);
    $("#cust_id").val(cust_id);
    $("#endCredit").val(amount);
    $("#userId").val(userId);
    var detail = layer.open({
        type: 1,
        title: "放款审核详情",
        maxmin: true,
        shadeClose: true,
        area: ['100%', '100%'],
        //shift: 5,
        content: $('#order_detail').show(),
        btn: ['保存', '取消'],
        success: function () {
            getOrderDel(id);
        },
    });
}
//申请放款
function submitLoan() {
    var id = $("#orderId").val();
    var order_no = $("#order_no").val();
    var cust_id = $("#cust_id").val();
    var endCredit = $("#endCredit").val();
    var userId = $("#userId").val();
    var result = {};
    result.orderNo = order_no;
    result.orderId = id;
    result.custId = cust_id;
    result.amount = endCredit;
    result.userId = userId;
    result.state = '8';
    Comm.ajaxPost('loanOrder/submitLoan',JSON.stringify(result),function(data){
        layer.msg(data.msg,{time:1000},function () {
            layer.closeAll();
            window.$('#loanOrder').dataTable().fnDraw(false);
        });
    },"application/json")
}
//获取订单的详细信息
function getOrderDel(id){
    Comm.ajaxPost('loanOrder/getOrderDel',id,function(data){
        var resultData = data.data[0];
        var orderNo = resultData.orderNo;//订单编号
        var periods = resultData.periods;//申请期数
        var creat_time = resultData.creatTime;
        if (!creat_time) {
            creat_time = '';
        }else{
            creat_time = creat_time.substr(0,4)+"-"+creat_time.substr(4,2)+"-"+creat_time.substr(6,2)+" "+creat_time.substr(8,2)+":"+creat_time.substr(10,2)+":"+creat_time.substr(12,2);
        }
        var state =resultData.state;//状态
        if(state == "0"){
            state = "未提交订单";
        }else if(state == "1"){
            state = "无效订单";
        }else if(state == "2"){
            state = "已提交订单";
        }else if(state == "3"){
            state = "自动化审核";
        }else if(state == "4"){
            state = "接受申请贷款";
        }else if(state == "5"){
            state = "人工初审";
        }else if(state == "6"){
            state = "人工终审";
        }else if(state == "7"){
            state = "签约";
        }else if(state == "7.5"){
            state = "放款初审待审核";
        }else if(state == "8"){
            state = "放款初审审核通过";
        }else if(state == "9"){
            state = "放款终审审核通过";
        }
        var rate = resultData.rate;//利率（年%）
        var customer_name = resultData.customerName;//客户姓名
        var sex_name = resultData.sexName;//客户性别
        var tel = resultData.tel;//电话
        var card_type = resultData.cardType;//证件类型
        var card =resultData.card;//证件号码
        var loanAmount =resultData.loanAmount;//证件号码
        var endCredit = resultData.endCredit;//放款金额
        // var company = resultData.company;//归属公司
        // var branch = resultData.branch;//归属部门
        // var manager = resultData.manager;//归属人
        // var channel = resultData.channel;// 资金渠道
        // var credit = resultData.credit;//授信额度
        // var precredit = resultData.precredit;//预授信额度
        // var merchandise_type = resultData.merchandiseType;//商品类型
        // var merchandise_name = resultData.merchandiseName;//商品名称
        var amount = resultData.amount;//商品金额
        var merchant_name = resultData.merchantName;//装修公司名称
        $("#order_no").html(orderNo);
        $("#periods").html(periods);
        $("#creat_time").html(creat_time);
        $("#state").html(state);
        $("#rate").html(rate);
        $("#customer_name").html(customer_name);
        //$("#sex_name").html(sex_name);
        $("#tel").html(tel);
        $("#card_type").html("身份证");
        $("#card").html(card);
        $("#loanAmount").html(loanAmount);
        // $("#company").html(company);
        // $("#branch").html(branch);
        // $("#manager").html(manager);
        // $("#channel").html(channel);
        // $("#credit").html(credit);
        // $("#precredit").html(precredit);
        // $("#merchandise_type").html(merchandise_type);
        // $("#merchandise_name").html(merchandise_name);
        $("#endCredit").html(endCredit);
        $("#merchant_name").html(merchant_name);
    },"application/json")
}
//查询重置方法
function paramSearchReset() {
    $("#custName").val("");
    $("#orderNo").val("");
    $("#btime").val("");
    $("#etime").val("");
    g_userManage.fuzzySearch = false;
    window.$('#loanOrder').dataTable().fnDraw(false);
}
/*//返回
function returnDel(){
    layer.closeAll();
}*/
