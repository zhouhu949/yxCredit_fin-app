/**
 * Created by Administrator on 2017/5/22 0022.
 */
var start2;
var end2;
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
            param.startDate = start2;
            param.endDate = end2;
        }else{
            //获取时间
            var endDate = new Date();
            var endStr = endDate.getFullYear() + "-";
            var startStr = endDate.getFullYear() + "-";
            endStr += (endDate.getMonth()+1) + "-";
            startStr += (endDate.getMonth()) + "-";
            endStr += (endDate.getDate());
            startStr += endDate.getDate();
            $('#reservation').val(startStr+' - '+endStr);
            var timeStr=$('#reservation').val();
            timeStr=timeStr.split("-");
            var start=timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
            var end=timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
            param.startDate = start;
            param.endDate = end;
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        var engineId=$("#engineId").val();
        param.engineId = engineId;

        paramFilter.page = page;

        return paramFilter;
    }
};
$(function (){
    g_userManage.tableUser = $('#resulList_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('engine/results',JSON.stringify(queryFilter),function(result){
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            },"application/json")
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {
                'data':'id',
                'class':'hidden',"searchable":false,"orderable" : false
            },

            {"data": "id","orderable" : false,"width":"150px"},
            {"data": "engineCode","orderable" : false,"width" : "250px"},
            {"data": "engineName","orderable" : false, "width" : "300px"},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "width" : "300px",
                "render" : function(data, type, row, meta) {
                    if(data.result==''||data.result==null){
                        return '- -'
                    }else{
                        return data.result;
                    }
                }
            },
            {
                "className" : "childBox",
                "orderable" : false,
                "data" : null,
                "width" : "60px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<span class="look">查看</span>'
                }
            },
        ],
        "createdRow": function ( row, data, index,settings,json ) {

        },

        "initComplete" : function(settings,json) {//回调函数
            $("#resulList_list").on("click",".look",function(e){
                var target = e.target||window.event.target;
                var id=$(target).parent().prev().prev().prev().prev().html();
                window.open(_ctx+"/engine/lookOver?resultSetId="+id);
            });
            $('#reservation').daterangepicker(null, function(start, end, label) {
                var timeStr=$('#reservation').val();
                timeStr=timeStr.split("-");
                start2=timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
                end2=timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            })
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
$(function(){
    //数据填写
    $("#dataWrite").click(function(){
        $("#resulList_list_wrapper").show();
        $("#Fill_data").show();
        $("#batchList").hide();
        $("#testSearch").hide();
    })
    //批量测试
    var tableCheck=false;
    $("#batchTest").click(function(){
        $("#resulList_list_wrapper").hide();
        $("#Fill_data").hide();
        $("#batchList").show();
        $("#testSearch").show(function(){
            if(tableCheck==false){
                batchTable();
                tableCheck==true;
            }else{
                g_userManage.tableUser.ajax.reload();
            }
        });
    })
})
function batchTable(){
        var g_userManage = {
        tableUser: null,
        currentItem: null,
        fuzzySearch: false,
        getQueryCondition: function (data) {
            var paramFilter = {};
            var page = {};
            var param = {};

            //自行处理查询参数
            param.fuzzySearch = g_userManage.fuzzySearch;
            if (g_userManage.fuzzySearch) {
                param.searchKey = $("#searchTest").val();
            }
            var engineId=$("#engineId").val();
            param.engineId = engineId;

            paramFilter.param = param;

            page.firstIndex = data.start == null ? 0 : data.start;
            page.pageSize = data.length == null ? 10 : data.length;

            paramFilter.page = page;

            return paramFilter;
        }
    };
        g_userManage.tableUser = $('#batchTable').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "bDestroy":true,
            "bRetrieve": true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('engine/engineBatchTestList', JSON.stringify(queryFilter), function (result) {
                    var returnData = {};
                    var resData = result.data;
                    returnData.recordsTotal = resData.total;
                    returnData.recordsFiltered = resData.total;
                    returnData.data = resData.list;
                    callback(returnData);
                }, "application/json")
            },
            "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
            "columns": [
                {
                    'data':null,
                    'class': 'hidden', "searchable": false, "orderable": false
                },
                {"data": "batchNo", "orderable": false, "width": "150px", "className" : "batchNo"},
                {"data": "engineId", "orderable": false, "width": "250px"},

                {"data": "engineName", "orderable": false, "width": "150px"},
                {
                    "data": null, "orderable": false, "width": "200px",
                    "render": function (data, type, row, meta) {
                        return json2TimeStamp(data.startTime);
                    }
                },
                {
                    "data": null, "orderable": false, "width": "200px",
                    "render": function (data, type, row, meta) {
                        return json2TimeStamp(data.costTime);
                    }
                },
                {
                    "className" : "lookTest",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<span class="look">查看</span>'
                    }
                },
                {
                    "className" : "lookLead",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<span class="look">导出</span>'
                    }
                }
            ],
            "createdRow": function (row, data, index, settings, json) {
            },
            "initComplete": function (settings, json) {
                $("#batchTable").on("click",".lookTest",function(e){
                    var target = e.target||window.event.target;
                    var batchNo = $(target).parents("tr").children().eq(1).html();
                    var engineId=$("#engineId").val();//引擎id
                    $(target).parents("#batchList").hide();
                    //$("#batchList").hide();
                    $("#checkList").show(function(){
                        $("#checkList span").click(function(){
                            $("#checkList").hide();
                            $("#batchList").show();
                        })
                        $("#dataWrite").click(function(){
                            $("#checkList").hide();
                        })
                        $("#batchTest").click(function(){
                            $("#checkList").hide();
                        })
                        var g_userManage = {
                            tableUser: null,
                            currentItem: null,
                            fuzzySearch: false,
                            getQueryCondition: function (data) {
                                var paramFilter = {};
                                var page = {};
                                var param = {};

                                //自行处理查询参数
                                param.fuzzySearch = g_userManage.fuzzySearch;
                                if (g_userManage.fuzzySearch) {
                                    param.searchKey = $("#searchTest").val();
                                }
                                var engineId=$("#engineId").val();
                                param.engineId = engineId;

                                paramFilter.param = param;
                                param.batchNo=batchNo;

                                page.firstIndex = data.start == null ? 0 : data.start;
                                page.pageSize = data.length == null ? 10 : data.length;

                                paramFilter.page = page;

                                return paramFilter;
                            }
                        };
                        g_userManage.tableUser = $('#checkTable').dataTable($.extend({
                            'iDeferLoading':true,
                            "bAutoWidth" : false,
                            "Processing": true,
                            "ServerSide": true,
                            "sPaginationType": "full_numbers",
                            "bPaginate": true,
                            "bLengthChange": false,
                            "bDestroy":true,
                            "bRetrieve": true,
                            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                            "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                                var queryFilter = g_userManage.getQueryCondition(data);
                                Comm.ajaxPost('engine/engineBatchTestResultList', JSON.stringify(queryFilter), function (result) {
                                    var returnData = {};
                                    var resData = result.data;
                                    returnData.recordsTotal = resData.total;
                                    returnData.recordsFiltered = resData.total;
                                    returnData.data = resData.list;
                                    callback(returnData);
                                }, "application/json")
                            },
                            "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
                            "columns": [
                                {
                                    'data':null,
                                    'class': 'hidden', "searchable": false, "orderable": false
                                },
                                {"data": "id", "orderable": false, "width": "150px"},
                                {"data": "engineName", "orderable": false, "width": "250px"},

                                {"data": "result", "orderable": false, "width": "150px"},
                                {"data": "scorecardscore", "orderable": false, "width": "150px"},
                                {
                                    "className" : "detaillook",
                                    "orderable" : false,
                                    "data" : null,
                                    "width" : "60px",
                                    "searchable":false,
                                    "render" : function(data, type, row, meta) {
                                        return '<b>查看</b>'
                                    }
                                },
                                {
                                    "className" : "detailLead",
                                    "orderable" : false,
                                    "data" : null,
                                    "width" : "60px",
                                    "searchable":false,
                                    "render" : function(data, type, row, meta) {
                                        return '<b>导出</b>'
                                    }
                                }
                            ],
                            "createdRow": function (row, data, index, settings, json) {
                            },
                            "initComplete": function (settings, json) {
                                $("#checkTable").on("click",".lookTest",function(e){
                                    var target = e.target||window.event.target;
                                    var batchNo=$(target).prev().prev().prev().prev().prev().html();
                                    $("#testSearch span").show();
                                });
                                $("#checkTable").on("click",".detaillook",function(e){
                                    var target = e.target||window.event.target;
                                    var id = $(target).parents("tr").children().eq(1).html();
                                    window.open(_ctx+"/engine/lookOver?resultSetId="+id);

                                });
                                $("#checkTable").on("click",".detailLead",function(e){
                                    var target = e.target||window.event.target;
                                    var id = $(target).parents("tr").children().eq(1).html();
                                    window.open(_ctx+"/engine/downloadPDFEngineTestResultData?resultSetId="+id);

                                });
                                $(".testImg").click(function() {
                                    g_userManage.fuzzySearch = true;
                                    g_userManage.tableUser.ajax.reload();
                                });
                            }
                        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                        g_userManage.tableUser.on("order.dt search.dt", function () {
                            g_userManage.tableUser.column(0, {
                                search: "applied",
                                order: "applied"
                            }).nodes().each(function (cell, i) {
                                cell.innerHTML = i + 1
                            })
                        }).draw();
                    });

                });
                $("#batchTable").on("click",".lookLead",function(e){
                    var target = e.target||window.event.target;
                    var batchNo = $(target).parents("tr").children().eq(1).html();
                    window.open(_ctx+"/engine/downloadEngineTestResultData?batchNo="+batchNo);
                });
                $(".testImg").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableUser.ajax.reload();
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableUser.on("order.dt search.dt", function () {
            g_userManage.tableUser.column(0, {
                search: "applied",
                order: "applied"
            }).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw()
    }









