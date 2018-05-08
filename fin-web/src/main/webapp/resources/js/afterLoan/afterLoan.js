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
            if($("#custName").val()!=""){
                param.cusName = $("#custName").val();
            }
            if ($("#isoverdue").val()!=""){
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
        }
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
$(function (){
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            //endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        //max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(beginTime);
    laydate(endTime);
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
            Comm.ajaxPost('afterLoan/list', JSON.stringify(queryFilter), function (result) {
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
            {"data": "person_name","orderable" : false},
            {"data": "tel","orderable" : false},
            {"data": 'pay_count',"orderable" : false},
            {"data": "pay_time","orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data": "state","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data.toString()=="3"){
                        return "已逾期";
                    }else {
                        return "未逾期";
                    }
                }
            },
            //{"data": null,"orderable" : false}
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
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
            var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getCollection(event)">催收记录 </a>');
            var planbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getPlanlist(\''+data.loan_id+'\')"> 还款计划</a>');
            var collectionbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="collection(\''+data.loan_id+'\')"> 催收分配</a>');
            // var probtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getPlanprogess(event)"> 放款进度</a>');
            // var warningbtn = $('<span>&nbsp;</span><a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="checkWarning(\''+data.loanId+'\')">查看预警</a>');
            $("td", row).eq(6).append(btn).append(planbtn).append(collectionbtn);
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
//查看预警
function checkWarning(loanId) {
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#warningInfo'),
        success : function(index, layero) {
            warningInfo(loanId);
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
            var tel = $('#callTel').val();
            if (!tel) {
                layer.msg('请填写拨打电话',{time:1000});
                return;
            }
            var callTime = $('#callTime').val();
            if (!callTime) {
                layer.msg('请填写拨打时间',{time:1000});
                return;
            }
            var relationship = $('#relationship').val();
            if (!relationship) {
                layer.msg('请填写与客关系',{time:1000});
                return;
            }
            var remark= $('#remark').val();
            if (!remark) {
                layer.msg('请填写催收备注',{time:1000});
                return;
            }
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

//催收
function collection(e) {
    debugger
    $('#loanId').val(e);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        shadeClose : false,
        area : [ '50%', '50%' ],
        content : $('#collection').show(),
        success : function(index, layero) {
            //planTable();
        }
    })
}

//查看还款计划
function getPlanlist(e) {
    debugger
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
                debugger
                Comm.ajaxPost('repayment/getList', JSON.stringify(queryFilter), function (result) {
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

                //时间格式转换
                var payTime = data.payTime;
                if (!payTime) {
                    payTime = '';
                }else{
                    payTime = payTime.substr(0,4)+"-"+payTime.substr(4,2)+"-"+payTime.substr(6,2);
                }
                $('td', row).eq(1).html(payTime);

                var monthPay = (parseFloat(data.repaymentAmount) + parseFloat(data.fee)).toFixed(2);
                $('td', row).eq(4).html(monthPay);

                var state = data.state;
                if (!state) {
                    state = '';
                }else if(state =='1'){
                    state = '未还'
                }else if(state =='2'){
                    state = '以还'
                }else if(state =='3'){
                    state = '逾期'
                }
                $('td', row).eq(5).html(state);
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
                console.log(cell.length);
                cell.innerHTML = (i + 1)
            })
        }).draw()
    }
}

//放款进度table初始化
function progressTable() {
    if(g_userProgress.tableUser) {
        g_userProgress.tableUser.ajax.reload();
    }else{
        g_userProgress.tableUser = $('#loanDetailListTable').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userProgress.getQueryCondition(data);
                Comm.ajaxPost('afterLoan/loandetaillist', JSON.stringify(queryFilter), function (result) {
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
                {"data": 'userName',"orderable" : false},
                {"data": "orderNo","orderable" : false},
                {"data": 'loanTime',"orderable" : false},
                {"data": 'loanAmount',"orderable" : false},
                {"data": 'percent',"orderable" : false},
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
                var loanTime = data.loanTime;
                if (!loanTime) {
                    loanTime = '';
                }else{
                    loanTime = loanTime.substr(0,4)+"-"+loanTime.substr(4,2)+"-"+loanTime.substr(6,2);
                }
                $('td', row).eq(3).html(loanTime);
            },
            "initComplete" : function(settings,json) {


            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userProgress.tableUser.on("order.dt search.dt", function() {
            g_userProgress.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw()
    }
}

function warningInfo(loanId) {
    Comm.ajaxPost('afterLoan/warningInfo',{"loanId":loanId}, function (result) {
        console.log(result);
        //封装返回数据
        var returnData = {};
        var resData = result.data;
        if(resData != null){
            $('#addCredit').empty().html(resData.addCredit);
            $('#black').empty().html(resData.black);
            $('#changeAddress').empty().html(resData.changeAddress);
            $('#dtcredit').empty().html(resData.dtcredit);
            $('#liabilities').empty().html(resData.liabilities);
        }else{
            $('#addCredit').empty();
            $('#black').empty();
            $('#changeAddress').empty();
            $('#dtcredit').empty();
            $('#liabilities').empty();
        }
    });
}
//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
            time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}
