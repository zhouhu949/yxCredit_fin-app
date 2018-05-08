var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.orderType='2'; //商品订单状态
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();
        }

        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    if(g_userManage.tableOrder){
        g_userManage.tableOrder.ajax.reload();
    }else{
        g_userManage.tableOrder = $('#order_list').dataTable($.extend({
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
                Comm.ajaxPost('settleCustomer/overtimeOrderList', JSON.stringify(queryFilter), function (result) {
                    debugger
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
                {"data": "orderNo","orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "productTypeName","orderable" : false},
                {"data": "productNameName","orderable" : false},
                {"data": null, "searchable": false, "orderable": false,
                    "render": function (data, type, row, meta) {
                        return data.merchandiseBrand+data.merchandiseModel;
                    }
                },
                {"data": "amount","orderable" : false},
                {"data": "predictPrice","orderable" : false},
                {"data": "applayMoney","orderable" : false},
                {"data": "periods","orderable" : false},
                {"data": "commodityState","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data.toString()=="18"){
                            return "等待发货";
                        }else if(data.toString()=="17"){
                            return "等待收取首付款";
                        }
                    }
                },
                {"data": "alterTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return  formatTime(data);
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var btnDel = $('<a class="tabel_btn_style" onclick="orderDetail(\''+data.id+'\',\''+data.customerId+'\',\''+data.state+'\',\''+data.antifraudState+'\',\''+data.tache+'\')">查看</a>');
                var btnClose = $('<a class="tabel_btn_style" onclick="closeOrder(\''+data.id+'\')">关闭订单</a>');
                $('td', row).eq(14).append(btnDel).append("&nbsp;").append(btnClose);
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //搜索重置
                $("#btn_search_reset").click(function() {
                    $("#customerName").val("");
                    g_userManage.fuzzySearch = true;
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

function closeOrder(orderId)
{
    Comm.ajaxPost('settleCustomer/closeOrder', orderId, function (data) {
        layer.msg(((data.data && data.data.msg) ? data.data.msg : "关闭失败"),{time:2000},function(){
            g_userManage.fuzzySearch = true;
            g_userManage.tableOrder.ajax.reload();
        });
        }, "application/json"
    );
}

//打开查看页面
function orderDetail(orderId,customerId){
    var url = "/finalAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}
