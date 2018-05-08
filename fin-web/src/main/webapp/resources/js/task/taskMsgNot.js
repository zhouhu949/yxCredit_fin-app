var g_userManage = {
    tableCommit : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    g_userManage.tableCommit = $("#commit_list").dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "iDisplayLength" : 10,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var paramFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost("taskMsg/getNotCommitTask", JSON.stringify(paramFilter), function (result) {
                //封装返回数据
                var resData = result.data;
                var resPage = result.page;
                var returnData = {
                    draw:data.draw,
                    recordsTotal:resPage.resultCount,
                    recordsFiltered:resPage.resultCount,
                    data:resData
                };
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {"data": null ,"searchable":false,"orderable" : false},
            {"data": "orderId","orderable" : false},
            {"data": "customerName","orderable" : false},
            {"data": "productName","orderable" : false},
            {"data": "processName","orderable" : false},
            {"data": "processNodeName","orderable" : false},
            {"data" : "taskState","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    return '待处理';
                }
            },
            {"data" : "createTimeTask","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="operate(\'receive\',\''+data.taskNodeId+'\',\''+data.processNodeId+'\')">领取</a>');
            $('td', row).eq(8).append(btnDel);
        },
        "initComplete" : function(settings,json) {
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableCommit.on("order.dt search.dt", function() {
        g_userManage.tableCommit.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
});
//领取
function operate(stat,taskNodeId,processNodeId,relId) {
    var paramMap = {id:taskNodeId,state:stat};
    paramMap.processNodeId = processNodeId;
    Comm.ajaxPost("taskMsg/updateState", JSON.stringify(paramMap), function (result) {
        layer.msg(result.msg,{time:1000},function () {
            debugger
            g_userManage.tableCommit.ajax.reload();
            // g_userManage.tableUser.ajax.reload();
            //$("#commit_list").dataTable().fnDraw(false);
            $("#commit_list").dataTable().fnDraw(false);
            /*var taskIn = $('#iframe_in', window.parent.document).children('table');

             console.log(taskIn);*/
            console.log($('#iframe_in', window.parent.document));
            console.log($('#iframe_in', window.parent.document).contents().find("#commit_list"));
            $('#iframe_in').contents().find("#commit_list").dataTable().fnDraw(false);
        });
    },"application/json");
}