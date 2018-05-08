/**
 * Created by wangmin on 2017/5/4.
 */
//页面初始化
$(function () {
    var orderId =$("#orderId").val();
    queryList(orderId);
})
/****************************************************准客户列表*****************************************************************/
function queryList(orderId){
    g_userManage.tableUser = $('#Record_list').dataTable($.extend({
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
            var queryFilter = g_userManage.getQueryCondition(orderId,data);
            Comm.ajaxPost("percust/auditList", JSON.stringify(queryFilter), function (result) {
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
            {"data":'tacheName',"searchable":false,"orderable" : false},
            {"data":'stateName',"searchable":false,"orderable" : false},
            {"data":'credit',"searchable":false,"orderable" : false},
            {"data":'approveSuggestion',"searchable":false,"orderable" : false},
            {"data":'createTime',"searchable":false,"orderable" : false},
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            //时间格式转换
            var creatTime = data.createTime;
            if (!creatTime) {
                creatTime = '';
            }else{
                creatTime = creatTime.substr(0,4)+"-"+creatTime.substr(4,2)+"-"+creatTime.substr(6,2)+" "+creatTime.substr(8,2)+":"+creatTime.substr(10,2)+":"+creatTime.substr(12,2);
            }
            $('td', row).eq(5).html(creatTime);
        },
        "initComplete" : function(settings,json) {
            //点击一行显示明细
            $("#Res_list").delegate( 'tbody tr td:not(:last-child)','click',function(e){
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
}
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(orderId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.order_id = $("#orderId").val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        console.log(page.firstIndex)
        if (page.pageSize==-1)
        {
            page.pageSize=10;
        }
        paramFilter.page = page;
        return paramFilter;
    }
};