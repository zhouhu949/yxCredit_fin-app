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
        param.state = $("#state").val();
        if (g_userManage.fuzzySearch) {
            param.custName = $("#custName").val();
            var btime=$("#btime").val();
            btime = btime.replace(/[\:\s\-]/g,"");
            param.beginTime = btime;

            var etime=$("#etime").val();
            etime = etime.replace(/[\:\s\-]/g,"");
            param.endTime = etime;
            param.orderNo = $("#orderNo").val();;
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    g_userManage.tableUser = $('#loanAudit').dataTable($.extend({
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
            Comm.ajaxPost('loan/getList', JSON.stringify(queryFilter), function (result) {
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
            {"data": "customerName","orderable" : false,"width":"120px"},
            {"data": "orderNo","orderable" : false, "width" : "280px"},
            {"data": null,"orderable" : false,"width" : "120px"},
            {"data": 'state','class':'hidden',"orderable" : false,"width" : "120px"},
            {"data": "amount","orderable" : false,"width":"120px"},
            {"data": "createTime","orderable" : false,"width":"120px"},
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "150px"
            },
            {"data": "id" ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "orderId" ,'class':'hidden',"searchable":false,"orderable" : false}
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
            var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getDetail(event)">审核</a>');
            $("td", row).eq(7).append(btn);

            var state = data.state;
            if(state == "1"){
                state = "待审核";
            }else if(state == "2"){
                state = "已放款";
            }else if(state == "3"){
                state = "拒绝";
            }
            $("td", row).eq(3).html(state);
            //时间格式转换
            var createTime = data.createTime;
            if (!createTime) {
                createTime = '';
            }else{
                createTime = createTime.substr(0,4)+"-"+createTime.substr(4,2)+"-"+createTime.substr(6,2)+" "+createTime.substr(8,2)+":"+createTime.substr(10,2)+":"+createTime.substr(12,2);
            }
            $('td', row).eq(6).html(createTime);
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

//获取订单详情
function getDetail(e) {
    //清空数据
    $('#order_detail tbody tr td:odd').not('#last-td').html('');
    $('#repayDate').val('');
    var target = e.target || window.event.target;
    var orderId = $(target).parent('td').next('td').next('td').html();
    var stateNum = $(target).parent('td').parent('tr').children('td').eq(4).html();
    var loanId = $(target).parent('td').parent('tr').children('td').eq(8).html();
    var cusId = '';
    layer.open({
        type: 1,
        title: "订单详情",
        maxmin: true,
        shadeClose: true,
        area: ['100%', '100%'],
        shift: 5,
        content: $('#order_detail').show(),
        title: false,
        btn: ['保存', '取消'],
        success: function () {
            //计划表隐藏，清空计划
            $('#plan-wrapper li:not(".plan-title")').remove();
            $('#repayPlan').hide();

            Comm.ajaxPost('loan/loanDetail',"orderId="+orderId,function(data){
                console.log(data);
                var resultData = data.data;
                console.log(resultData);
                cusId = resultData.customerId;
                var orderNo = resultData.orderNo;//订单编号
                var periods = resultData.periods;//申请期数
                var creat_time = resultData.creatTime;
                //时间格式转换
                if (!creat_time) {
                    creat_time = '';
                }else{
                    creat_time = creat_time.substr(0,4)+"-"+creat_time.substr(4,2)+"-"+creat_time.substr(6,2)+" "+creat_time.substr(8,2)+":"+creat_time.substr(10,2)+":"+creat_time.substr(12,2);
                }
                var tache = resultData.tache;//环节
                if(tache == "0"){
                    tache = "未接收";
                }else if(tache == "1"){
                    tache = "接收";
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
                }else if(state == "8"){
                    state = "申请放款";
                }else if(state == "9"){
                    state = "放款审核";
                }
                var rate = resultData.rate;//利率（年%）
                var customer_name = resultData.customerName;//客户姓名
                var sex_name = resultData.sexName;//客户性别
                var tel = resultData.tel;//电话
                var card_type = resultData.cardType;//证件类型
                var card =resultData.card;//证件号码
               /* var company = resultData.company;//归属公司
                var branch = resultData.branch;//归属部门*/
                var manager = resultData.manager;//归属人
                var channel = resultData.channel;// 资金渠道
                var credit = resultData.endCredit;//授信额度
                var precredit = resultData.firstCredit;//预授信额度
               var merchandise_type = resultData.productTypeName;//商品类型
                var merchandise_name = resultData.productNameName;//商品名称
                /* var amount = resultData.amount;//商品金额*/
                var merchant_name = resultData.merchantName;//装修公司名称
                var loan_amount = resultData.endCredit;//放款金额
                $("#order_no").html(orderNo);
                $("#periods").html(periods);
                $("#creat_time").html(creat_time);
                $("#tache").html(tache);
                $("#order_state").html(state);
                $("#rate").html(rate);
                $("#customer_name").html(customer_name);
                $("#sex_name").html(sex_name);
                $("#tel").html(tel);
                $("#card_type").html("身份证");
                $("#card").html(card);

                /*$("#company").html(company);
                $("#branch").html(branch);*/
                $("#manager").html(manager);
                $("#channel").html(channel);
                $("#credit").html(credit);
                $("#precredit").html(precredit);
                $("#merchandise_type").html(merchandise_type);
                $("#merchandise_name").html(merchandise_name);
                /*$("#amount").html(amount);*/
                $("#merchant_name").html(merchant_name);
                $("#loan_amount").html(loan_amount);
            });
            //返回
            $('#button1').click(function () {
                layer.closeAll();
            });

            //审核放款
            $('#pass_btn').click(function () {
                var periods = $('#periods').html();//期数
                var amount = $('#loan_amount').html();//放款金额
                var rate = $('#rate').html();//年利率
                var loanTime = $('#repayDate').val().replace(/[\:\s\-]/g,"");
                if(!loanTime){
                    layer.msg('请选择还款日期',{time:1000});
                    return
                }
                layer.confirm('确定审核通过吗？', {icon: 3, title:'操作提示'}, function(index){
                    Comm.ajaxPost('loan/reviewLoan',{cusId:cusId,orderId:orderId,state:2,loanId:loanId,periods:periods,amount:amount,rate:rate,loanTime:loanTime},function(data){
                        layer.msg(data.msg,{time:1000},function () {
                            layer.closeAll();
                            window.$('#loanAudit').dataTable().fnDraw(false);
                        });
                    })
                })
            });

            //计算生成还款计划表
            $('#computed_btn').unbind('click').click(function (e) {
                $('#plan-wrapper li:not(".plan-title")').remove();
                var periods = $('#periods').html();//期数
                var amount = $('#loan_amount').html();//放款金额
                var rate = $('#rate').html();//年利率
                var loanTime = $('#repayDate').val().replace(/[\:\s\-]/g,"");
                if(!loanTime){
                    layer.msg('请选择还款日期',{time:1000});
                    return
                }
                Comm.ajaxPost('loan/repaymentList',{orderId:orderId,loanId:loanId,periods:periods,amount:amount,rate:rate,loanTime:loanTime},function(data){
                    console.log(data);
                    var data = data.data;
                    if (data.length > 0) {
                        $('#repayPlan').show();
                        var html = '';
                        for (var i = 0; i< data.length; i++) {
                            var monthPay = (parseFloat(data[i].repaymentAmount) + parseFloat(data[i].fee)).toFixed(2);

                            var payTime = data[i].payTime;
                            if (!payTime) {
                                payTime = '';
                            }else{
                                payTime = payTime.substr(0,4)+"-"+payTime.substr(4,2)+"-"+payTime.substr(6,2);
                            }
                            var state = data[i].state;
                            if (!state) {
                                state = '';
                            }else if(state == '1'){
                                state = '待放款';
                            }
                            html+='<li>';
                            html+='<span class="period">'+(i+1)+'</span>';
                            html+='<span class="repayDate">'+payTime+'</span>';
                            html+='<span class="corpus">'+data[i].repaymentAmount+'</span>';
                            html+='<span class="interest">'+data[i].fee+'</span>';
                            html+='<span class="monthPay">'+monthPay+'</span>';
                            html+='<span class="state" style="display: none">'+state+'</span>';
                            html+='</li>';
                        }
                        $('#plan-wrapper').append(html);
                    }
                })
            })
        }
    })
}

