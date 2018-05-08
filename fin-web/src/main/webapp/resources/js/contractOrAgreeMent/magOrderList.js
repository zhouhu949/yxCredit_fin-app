var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            debugger
            param.customerName = $("input[name='customer_name']").val();
            param.card = $("input[name='customer_card']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_contractManage = {
    tableContract : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        paramFilter.param = param;
        return paramFilter;
    }
};
$(function (){
    if(g_userManage.tableOrder){
        g_userManage.tableOrder.ajax.reload();
    }else{
        g_userManage.tableOrder = $('#sign_list').dataTable($.extend({
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
                Comm.ajaxPost('contract/orderList', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {"data": null ,"searchable":false,"orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "productNameName","orderable" : false},
                {"data": "orderNo","orderable" : false},
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {

                var btnDownLoad=$('<span style="color: #307ecc;" onclick="downLoad(\''+data.id+'\')">下载合同</span>');
                $("td", row).eq(6).append(btnDownLoad);
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='customer_name']").val("");
                    $("input[name='customer_card']").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableOrder.on("order.dt search.dt", function() {
            g_userManage.tableOrder.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
});
//下载合同文件
function downLoad(contract_id) {

    layer.open({
        type : 1,
        title : '下载列表',
        maxmin : true,
        shadeClose : false,
        area : [ '700', '500' ],
        content : $('#contractList'),
        success : function(index, layero) {
            g_contractManage.tableContract = $('#contract_list').dataTable($.extend({
                'iDeferLoading':true,
                "bAutoWidth" : false,
                "Processing": true,
                "ServerSide": true,
                "searching":false, //去掉搜索框
                "sPaginationType": "full_numbers",
                "bPaginate": true,
                "bLengthChange": false,
                "destroy":true,
                "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                    var queryFilter = g_contractManage.getQueryCondition(data);
                    queryFilter.param.id = contract_id;
                    Comm.ajaxPost('contract/getContractByOrderId', JSON.stringify(queryFilter), function (result) {
                        //封装返回数据
                        console.log(result);
                        var returnData = {};
                        var resData = result.data.list;
                        var resPage = result.data;
                        returnData.draw = data.draw;
                        returnData.recordsTotal = resPage.total;
                        returnData.recordsFiltered = resPage.total;
                        returnData.data = resData;
                        callback(returnData);
                    },"application/json");
                },
                "order": [],
                "columns": [
                    {"data": null ,"searchable":false,"orderable" : false},
                    {"data": "contract_no" ,"orderable" : false},//合同编号
                    {"data": "createTime","orderable" : false,
                        "render": function (data, type, row, meta) {
                            return json2TimeStamp(data);
                        }
                    },
                    {"data": "contract_name" ,"orderable" : false},
                    {
                        "data": "null", "orderable": false,
                        "defaultContent":""
                    }
                ],"createdRow": function ( row, data, index,settings,json ) {

                    var btnDownLoad=$('<span style="color: #307ecc;" onclick="downloadContract(\''+data.contract_src+'\')">下载</span>');
                    $("td", row).eq(4).append(btnDownLoad);
                },
                "initComplete" : function(settings,json) {
                }
            }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
            g_contractManage.tableContract.on("order.dt search.dt", function() {
                g_contractManage.tableContract.column(0, {
                    search : "applied",
                    order : "applied"
                }).nodes().each(function(cell, i) {
                    cell.innerHTML = i + 1
                })
            }).draw();
        },
        yes:function(index,layero){
        }
    });
}

//时间转换
function formatTime(t){
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}

//下载合同
function downloadContract(src){
    var headerUrl = '';
    window.open(src);
    // Comm.ajaxPost('contract/downLoad',null,function (result) {
    //     debugger
    //     headerUrl = result.data;
    //     window.open(headerUrl+src);
    // },"application/json");
}
