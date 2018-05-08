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
            param.order_no = $("#orderNo").val()
            param.customer_name = $("#custName").val();
            param.tel = $("#custTel").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    g_userManage.tableUser = $('#extensionTable').dataTable($.extend({
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
            Comm.ajaxPost('afterLoan/listExtension', JSON.stringify(queryFilter), function (result) {
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
            {"data": "order_no","orderable" : false},
            {"data": "customer_name","orderable" : false},
            {"data": "tel","orderable" : false},
            {"data": "pay_count","orderable" : false},
            {"data": "pay_time","orderable" : false,
                "render": function (data, type, row, meta) {
                return formatTime(data);
            }},
            {"data": "extension_day","orderable" : false},
            {"data": "extension_amount","orderable" : false},
            {"data": "account","orderable" : false},
            {"data": "operate_time","orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data, true);
                }
            }
        ],
        //处理序号用
        "columnDefs": [
            {
                "searchable": false,
                "orderable": false,
                "targets": 1
            }
        ],
        "initComplete" : function(settings,json) {
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            $("#btn_search_reset").click(function() {
                $('#orderNo').val("");
                $('#custName').val("");
                $('#custTel').val("");
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


//时间转换
function formatTime(t, timeFlag){
    var time = t.replace(/\s/g, "");//去掉所有空格
    var day = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8)

    if (timeFlag)
    {
        day += " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    }
    return day;
}

function sltReductionOnchange(){
    debugger
    if ($("#sltReduction").val()=="0") {
        $("#reductionAmount").removeAttr("readonly");
    }else {
        $("#reductionAmount").attr("readonly", "readonly");    //去除readonly属性
    }
}
