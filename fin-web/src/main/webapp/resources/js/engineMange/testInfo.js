//页面初始化datatable赋值
$(function () {
    g_userManage.tableUser = $('#testInfo_list').dataTable($.extend({
        'iDeferLoading': true,
        "bAutoWidth": false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('engine/engineTestList', JSON.stringify(queryFilter), function (result) {
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
            {"data": "verId", "orderable": false, "width": "180px"},
            {"data": "version", "orderable": false, "width": "250px"},

            {"data": "engineName", "orderable": false, "width": "300px"},
            {"data": "engineDesc", "orderable": false, "width": "300px"},
            {"data": "latestTime", "orderable": false, "width": "300px"},

            {
                "data": "null", "orderable": false, "width": "300px",
                "render": function (data, type, row, meta) {
                    var operate='<span id="productBatch">批量生成</span><span id="leadBatch">批量导入</span>';
                    return operate;
                }
            },


        ],
        "createdRow": function (row, data, index, settings, json) {
        },
        "initComplete": function (settings, json) {
            $("#testInfo_list").on("click","#leadBatch",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                batchLead(id);
            });
            $("#testInfo_list").on("click","#productBatch",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                batchProduct(id);
            });
            $(".testImg").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    $("#searchTest").val("");
                });
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
});
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
function batchProduct(id){
    $("#nullCtRatio").val("");
    $("#elseCtRatio").val("");
    $("#rowCt").val("");
    var productLayer=layer.open({
        type : 1,
        title : "批量生成",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '400px', '300px' ],
        offset: '140px',
        content: $('#produce_detail_style'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){

        },
        yes : function(index, layero) {
            var param={};
            param.versionId=id;
            param.nullCtRatio=$("#nullCtRatio").val();
            param.elseCtRatio=$("#elseCtRatio").val();
            param.rowCt=$("#rowCt").val();
            var engineId=$("#engineId").val();
            param.engineId = engineId;
            Comm.ajaxPost('engine/createSampleData', JSON.stringify(param), function (result) {
                if ((result.msg == "")||(result.msg =="null")||(result.msg ==null)) {
                   layer.msg("您的引擎尚未配置字段",{time:2000});
                    return;
                }else{
                    layer.msg("数据已经生成,稍后会自动导出下载",{time:2000},function(){
                        window.open(_ctx+"/engine/downEngineTestData?fileName="+result.msg,"引擎测试样本数据下载窗口");
                    });
                }

            }, "application/json")
            layer.close(productLayer)
        },
        cancel: function(index, layero){
            layer.close(productLayer)
        }
    });
}
function handleFile() {
    $("#fileName").val($("#file").val());
}
function batchLead(id){
    $("#fileName").val("");
    var leadLayer=layer.open({
        type : 1,
        title : "批量导入",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '500px', '200px' ],
        offset: '200px',
        content: $('#Lead_detail_style'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            if( $("#fileName").val()==""){
                layer.msg("请选择文件",{time: 1000},function(){
                });
                return;
            }
            var formData = new FormData();
            formData.append("file",$('#file')[0].files[0]);
            var fileName = $('#file')[0].files[0].name;
            Comm.ajaxPost('engine/engineTestDataUpload?versionId='+id+'&fileName='+fileName,formData,function (data) {
                debugger
                if (data.data > 0) {
                   layer.msg("成功执行【"+data.data+"】条数据测试",{time:2000});
                }
            },false,false,false);
            layer.close(leadLayer)
        },
        cancel: function(index, layero){
            layer.close(leadLayer)
        }
    });
}




