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
        param.loanId=$("#hdloanId").val();
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
        debugger
        if($("#custName").val()){
            param.personName = $("#custName").val();
        }
        if ($("#isoverdue").val()&&$("#isoverdue").val()!=""){
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
            Comm.ajaxPost("goldPayment/getLoanManage", JSON.stringify(queryFilter), function (result) {
                console.log(result);
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
            {"data":'order_id',"searchable":false,"orderable" : false},
            {"data":'personName',"searchable":false,"orderable" : false},
            {"data":'TEL',"searchable":false,"orderable" : false},
            {"data":'amount',"searchable":false,"orderable" : false},
            {"data":'createtime',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data":'payable_amount',"searchable":false,"orderable" : false},
            {"data":'expiration_date',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data":'derate_amount',"searchable":false,"orderable" : false},
            {"data":'returned_amount',"searchable":false,"orderable" : false},
            {"data":'unpaid_amount',"searchable":false,"orderable" : false},
            // {"data":'agent_amount',"searchable":false,"orderable" : false},
            {"data":'red_amount',"searchable":false,"orderable" : false},
            {"data": null, "searchable": false, "orderable": false,
                "render": function (data, type, row, meta) {
                    if(data.penalty==''){
                        return "0";
                    }else{
                        return data.penalty;
                    }
                }
            },
            //{"data":'penalty',"searchable":false,"orderable" : false},
            {"data": null, "searchable": false, "orderable": false,
                "render": function (data, type, row, meta) {
                    if(data.overdueCount>0){
                        return "逾期";
                    }else{
                        return "未逾期";
                    }
                }
            },
            {"data": null, "searchable": false, "orderable": false,
                "render": function (data, type, row, meta) {
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
                    }else if(data.toString()=="10"){
                        return "关闭";
                    }
                }
            },
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getRepaymentList(\''+data.id+'\')">还款明细 </a>');
            //var btnSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\''+data.id+'\')">提前结清 </a>');
            // var btncs = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getCollection(\''+data.id+'\')">催收记录 </a>');
            // var collectionbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="collection(\''+data.cust_id+'\')"> 电催分配</a>');
            // $("td", row).eq(12).append(btn).append(btncs).append(collectionbtn);
            // var btnCostReduction = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="costReduction(\''+data.order_id+'\',\''+data.customerId+'\')">费用减免</a>');
            // if (data.settle_state == '0'&&data.order_state!='9') {
            //     btnSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\''+data.id+'\')">提前结清 </a>');
            // } else  if (data.settle_state == '1'&&data.order_state!='9') {
            //     btnSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="Settle(\''+data.id+'\')">编辑结清 </a>');
            // }else {
            //     if(data.settle_state != '0') {
            //         btnSettle= $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="seeSettle(\''+data.id+'\')">查看结清 </a>');}
            // }
            $("td", row).eq(15).append(btn);
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
                $("#isoverdue").val("");
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

//获取还款明细
function  getRepaymentList(loanId) {
    layer.open({
        type : 1,
        title : false,
        shadeClose : false,
        area : [ '90%', '70%' ],
        content : $('#afterLoan').show(),
        success : function(index, layero) {
            if(!$("#hdloanId").val()){
            $("#hdloanId").val(loanId);
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
            Comm.ajaxPost('goldPayment/getRepaymentDerateList', JSON.stringify(queryFilter), function (result) {
                //封装返回数据
                var returnData = {};
                var resData = result.data;
                returnData.draw = data.draw;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                console.log(returnData.data);
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
            },//loanId
            {"data": "amount","orderable" : false},
            {"data": "fee","orderable" : false},
            {"data": "repaymentAmount","orderable" : false},
            {"data": "redAmount","orderable" : false},
            {"data":'state',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                if (data=='1') {
                    return "未还";
                }
                else if (data=='2') {
                    return "已还";
                }else if (data=='3'){
                    return "逾期";
                } else {
                    return "";
                }
            }},
            {"data": "overdueDays","orderable" : false},
            {"data": 'penalty',"orderable" : false},
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
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
            //{"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
        ],
        "createdRow": function(row, data, index, settings, json) {
            var btnCostReduction = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="costReduction(\''+data.orderId+'\',\''+data.id+'\')">费用减免</a>');
            // var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getCollection(event)">催收记录 </a>');
            // var planbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getPlanlist(\''+data.loan_id+'\')"> 还款计划</a>');
            // var collectionbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="collection(\''+data.customerId+'\')"> 电催分配</a>');
            if (data.state=="2"||data.state=="1"){
                btnCostReduction = $('<a class="tabel_btn_style_disabled" disable="ture">费用减免&nbsp;</a>');;
            }
             $("td", row).eq(13).append(btnCostReduction)
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
        $("#hdloanId").val(loanId);
        g_userManage.tableUser.ajax.reload();
    }
        }
    })
}


//催收记录
function getCollection(e) {
    var target = e.target||window.event.target;
    var loanId = $(target).parent('td').parent('tr').children('td').eq(15).html();
    $('#loanId').val(loanId);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#collContainer').show(),
        success : function(index, layero) {
            collTable(loanId);
        }
    })
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
                    console.log(result);
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data;

                    returnData.draw = data.draw;
                    returnData.recordsTotal = resData.total;
                    returnData.recordsFiltered = resData.total;
                    returnData.data = resData.list;
                    console.log(returnData.data);
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
                    layer.msg(data.msg,{time:2000},function(){
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
                layer.msg(data.msg,{time:2000},function(){
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
// function planTable() {
//     if(g_userPlan.tableUser) {
//         g_userPlan.tableUser.ajax.reload();
//     }else{
//         g_userPlan.tableUser = $('#planListTable').dataTable($.extend({
//             'iDeferLoading':true,
//             "bAutoWidth" : false,
//             "Processing": true,
//             "ServerSide": true,
//             "sPaginationType": "full_numbers",
//             "bPaginate": true,
//             "bLengthChange": false,
//             "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
//             "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
//                 var queryFilter = g_userPlan.getQueryCondition(data);
//                 Comm.ajaxPost('repayment/getList', JSON.stringify(queryFilter), function (result) {
//                     console.log(result);
//                     //封装返回数据
//                     var returnData = {};
//                     var resData = result.data;
//
//                     returnData.draw = data.draw;
//                     returnData.recordsTotal = resData.total;
//                     returnData.recordsFiltered = resData.total;
//                     returnData.data = resData.list;
//                     console.log(returnData.data);
//                     callback(returnData);
//                 },"application/json");
//             },
//             "order": [],
//             "columns": [
//                 {
//                     "className" : "cell-operation",
//                     "data": null,
//                     "defaultContent":"",
//                     "orderable" : false,
//                     "width" : "30px"
//                 },
//                 {"data": null,"orderable" : false},
//                 {"data": "repaymentAmount","orderable" : false},
//                 {"data": 'fee',"orderable" : false},
//                 {"data": null,"orderable" : false},
//                 {"data": 'state',"orderable" : false},
//
//             ],
//             //处理序号用
//             "columnDefs": [
//                 {
//                     "searchable": false,
//                     "orderable": false,
//                     "targets": 1
//                 }
//             ],
//             "createdRow": function(row, data, index, settings, json) {
//
//                 // //时间格式转换
//                 // var payTime = data.payTime;
//                 // if (!payTime) {
//                 //     payTime = '';
//                 // }else{
//                 //     payTime = payTime.substr(0,4)+"-"+payTime.substr(4,2)+"-"+payTime.substr(6,2);
//                 // }
//                 // $('td', row).eq(1).html(payTime);
//                 //
//                 // var monthPay = (parseFloat(data.repaymentAmount) + parseFloat(data.fee)).toFixed(2);
//                 // $('td', row).eq(4).html(monthPay);
//                 //
//                 // var state = data.state;
//                 // if (!state) {
//                 //     state = '';
//                 // }else if(state =='1'){
//                 //     state = '未还'
//                 // }else if(state =='2'){
//                 //     state = '以还'
//                 // }else if(state =='3'){
//                 //     state = '逾期'
//                 // }
//                 // $('td', row).eq(5).html(state);
//             },
//             "initComplete" : function(settings,json) {
//
//             },
//             "fnDrawCallback": function(){
//                 var api = this.api();
//                 var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
//                 api.column(0).nodes().each(function(cell, i) {
//                     cell.innerHTML = startIndex + i + 1;
//                 });
//             }
//         }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
//         g_userPlan.tableUser.on("order.dt search.dt", function() {
//             g_userPlan.tableUser.column(0, {
//                 search : "applied",
//                 order : "applied"
//             }).nodes().each(function(cell, i) {
//                 console.log(cell.length);
//                 cell.innerHTML = (i + 1)
//             })
//         }).draw()
//     }
// }

//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
    //+ " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}


//分配电催-选择用户
// function collection(customerId) {
//     var setUpLayer = layer.open({
//             type : 1,
//             title : '选择',
//             shadeClose : false,
//             area : ['730px','400px'],
//             content : $('#userDiv'),
//             btn : [ '保存', '取消' ],
//             success:function (index, layero) {
//                 if(!g_userList.tableUser) {
//                     g_userList.tableUser = $('#user_list').dataTable($.extend({
//                         'iDeferLoading': true,
//                         "bAutoWidth": false,
//                         "Processing": true,
//                         "ServerSide": true,
//                         "sPaginationType": "full_numbers",
//                         "bPaginate": true,
//                         "bLengthChange": false,
//                         "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
//                         "ajax": function (data, callback, settings) {
//                             var queryFilter = g_userList.getQueryCondition(data);
//                             Comm.ajaxPost('user/list', JSON.stringify(queryFilter), function (result) {
//                                 var returnData = {};
//                                 var resData = result.data;
//                                 var resPage = result.page;
//                                 returnData.draw = data.draw;
//                                 returnData.recordsTotal = resPage.resultCount;
//                                 returnData.recordsFiltered = resPage.resultCount;
//                                 returnData.data = resData;
//                                 callback(returnData);
//                             }, "application/json")
//                         },
//                         "order": [],
//                         "columns": [
//                             {'data': null, 'class': 'hidden', "searchable": false, "orderable": false},
//                             {
//                                 "className": "childBox_setUp",
//                                 "orderable": false,
//                                 "data": null,
//                                 "width": "60px",
//                                 "searchable": false,
//                                 "render": function (data, type, row, meta) {
//                                     return '<input type="checkbox" value="' + data.userId + '" style="cursor:pointer;" isChecked="false">'
//                                 }
//                             },
//                             {"data": 'account', "searchable": false, "orderable": false},
//                             {"data": 'trueName', "searchable": false, "orderable": false},
//                             {"data": 'tel', "searchable": false, "orderable": false},
//                             {
//                                 "data": null,
//                                 "searchable": false,
//                                 "orderable": false,
//                                 "render": function (data, type, row, meta) {
//                                     if (data.status == 1) {
//                                         return '启用'
//                                     } else {
//                                         return '停用'
//                                     }
//                                 }
//                             },
//                         ],
//                         "initComplete": function (settings, json) {
//                             //搜索
//                             $("#user_btn_search").click(function () {
//                                 g_userList.fuzzySearch = true;
//                                 g_userList.tableUser.ajax.reload(function () {
//                                     $("#userCheckBox_All").attr("checked", false);
//                                 });
//                             });
//                             //重置
//                             $("#user_btn_search_reset").click(function () {
//                                 $('input[name="account"]').val("");
//                                 $('input[name="trueName"]').val("");
//                                 $('input[name="mobile"]').val("");
//                                 g_userList.fuzzySearch = false;
//                                 g_userList.tableUser.ajax.reload(function () {
//                                     $("#userCheckBox_All").attr("checked", false);
//                                 });
//                             });
//                             //全选
//                             $("#userCheckBox_All").click(function (J) {
//                                 if (!$(this).prop("checked")) {
//                                     $("#user_list tbody tr").find("input[type='checkbox']").prop("checked", false)
//                                 } else {
//                                     $("#user_list tbody tr").find("input[type='checkbox']").prop("checked", true)
//                                 }
//                             });
//                             //单选行
//                             $("#user_list tbody").delegate('tr', 'click', function (e) {
//                                 var target = e.target;
//                                 if (target.nodeName == 'TD') {
//                                     var input = target.parentNode.children[1].children[0];
//                                     var isChecked = $(input).attr('isChecked');
//                                     if (isChecked == 'false') {
//                                         if ($(input).attr('checked')) {
//                                             $(input).attr('checked', false);
//                                         } else {
//                                             $(input).attr('checked', 'checked');
//                                         }
//                                         $(input).attr('isChecked', 'true');
//                                     } else {
//                                         if ($(input).attr('checked')) {
//                                             $(input).attr('checked', false);
//                                         } else {
//                                             $(input).attr('checked', 'checked');
//                                         }
//                                         $(input).attr('isChecked', 'false');
//                                     }
//                                 }
//                             });
//                         }
//                     }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
//                     g_userList.tableUser.on("order.dt search.dt", function () {
//                         g_userList.tableUser.column(0, {
//                             search: "applied",
//                             order: "applied"
//                         }).nodes().each(function (cell, i) {
//                             cell.innerHTML = i + 1
//                         })
//                     }).draw();
//                     g_userList.userSearchFlag = false;
//                 }
//             },
//             yes : function(index, layero) {
//                 var selectArray = $("#user_list tbody input:checked");
//                 if(!selectArray || selectArray.length!=1){
//                     layer.msg("请选择一个用户！");
//                     return;
//                 }
//                 var userIds = new Array();
//             $.each(selectArray,function(i,e){
//                 var val = $(this).val();
//                 userIds.push(val);
//             });
//             console.log(userIds)
//             if(userIds.length>0){
//                 var addParam = {
//                     customerId:customerId,
//                     userId:userIds[0]
//                 }
//                 Comm.ajaxPost('afterLoan/setCollection',JSON.stringify(addParam),function(data){
//                     layer.msg(data.msg,{time:2000},function () {
//                         layer.close(setUpLayer);
//                     });
//                 }, "application/json");
//             }
//         }
//     });
// }

//费用减免
function  costReduction(orderId,id) {
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
        btn : [ '提交', '取消' ],
        success:function (index, layero) {
            var param = {};
            param.orderId = orderId;
            param.isoverdue="1";
            param.derateState="2";
            param.id=id;
            Comm.ajaxPost('goldPayment/getAfterLoanDetails', JSON.stringify(param), function (result) {
                console.log(result);
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
                $("#lblRepaymentAmount").html(resData.repayment_amount);
                $("#lblAmount").html(resData.amount);
                $("#lblPenalty").html(resData.penalty);
                $("#lblOverdueDays").html(resData.overdue_days);
                $("#lblTotal").html(parseInt(resData.repayment_amount ) + parseInt(resData.penalty));
                debugger
                if (derate_state!="")
                {
                    $("#reductionAmount").val(derate_amount);
                    //$("#sltReduction").val("0");
                    $("#reductionAmount").attr("disabled","disabled");
                }
                if(approval_state=="1"){
                    $("#lbl1").html("已通过！");
                }else if(approval_state=="0"){
                    if(derate_state=="0"){
                    $("#lbl1").html("已提交！");
                    }
                    else {
                        $("#lbl1").html("未通过！");
                        $("#reductionAmount").removeAttr("disabled");
                    }
                }
            },"application/json");
        },
        yes : function(index, layero) {
            var param = {};
            param.orderId = orderId;
            param.repayment_id=id;
            debugger
            //电催完成状态
            if(derate_state!="1"){
                if(derate_state=="0"){
                    layer.msg("减免金额审核中！",{time:2000});
                    return;
                }
                var val = $("#reductionAmount").val();
                var lblTotal=parseInt($("#lblTotal").html());
                var ival = parseInt(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN

                if (isNaN(ival)) {
                    layer.msg("减免金额格式错误！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }

                if (ival>lblTotal){
                    layer.msg("减免金额不能大于还款总额！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }

                if (lblTotal<ival){
                    layer.msg("减免金额不能大于还款金额！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }
                param.reductionAmount = val;
                param.derate_link='0';
                param.derate_state='0';
            }else {
                var val = $("#reductionAmount").val();
                var ival = parseInt(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN
                if (isNaN(ival)) {
                    layer.msg("减免金额格式错误！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }
                var lblTotal=parseInt($("#lblTotal").html());
                if (ival>lblTotal){
                    layer.msg("减免金额不能大于还款总额！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }
                if(approval_state=="1"){
                    layer.msg("减免金额已审核完成！",{time:2000});
                    return;
                }
                param.reductionAmount = val;
                param.derate_state="0";
                param.derate_id=derate_id;
            }
            Comm.ajaxPost('goldPayment/orderSettle',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:2000},function () {
                    layer.close(setUpLayer);
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
            Comm.ajaxPost('goldPayment/getAfterLoanDetails', JSON.stringify(param), function (result) {
                console.log(result);
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
                $("#lblRepaymentAmount").html(resData.repayment_amount);
                $("#lblAmount").html(resData.amount);
                $("#lblPenalty").html(resData.penalty);
                $("#lblOverdueDays").html(resData.overdue_days);
                $("#lblTotal").html(parseInt(resData.repayment_amount ) + parseInt(resData.penalty));
                debugger
                if (derate_state!="")
                {
                    $("#reductionAmount").val(derate_amount);
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
                    layer.msg("减免金额审核中！",{time:2000});
                    return;
                }
                var val = $("#reductionAmount").val();
                var ival = parseInt(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN
                if (isNaN(ival)) {
                    layer.msg("减免金额格式错误！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }

                var lblTotal=parseInt($("#lblTotal").html());
                if (lblTotal<ival){
                    layer.msg("减免金额不能大于还款金额！",{time:2000});
                    //alert("减免金额格式错误！");
                    return;
                }
                param.reductionAmount = val;
                param.derate_link='0';
                param.derate_state='0';
            }else {
                layer.msg("减免金额已审核完成！",{time:2000});
                return;
            }
            Comm.ajaxPost('goldPayment/orderSettle',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:2000},function () {
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