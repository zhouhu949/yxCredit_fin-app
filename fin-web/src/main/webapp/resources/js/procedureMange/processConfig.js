//显示流程列表
var g_proceduceManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_proceduceManage.fuzzySearch;
        if (g_proceduceManage.fuzzySearch) {
            param.searchString = $("[name='procedure_name']").val();
            param.searchKey = $("#ziduanchaxun input[name='Parameter_search']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  = 10 ;
        paramFilter.page = page;

        return paramFilter;
    }
};
//初始化表格数据
$(function (){
    g_proceduceManage.tableUser = $('#procedure_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_proceduceManage.getQueryCondition(data);
            Comm.ajaxPost('process/list', JSON.stringify(queryFilter), function (result) {
                if (result.code==1) {
                    layer.msg(result.msg,{icon:2,offset:'200px',time:1000});
                    return;
                }
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
            {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "id","orderable" : false},
            {"data": "code","orderable" : false},
            {"data": "name","orderable" : false},
            {"data": "desc","orderable" : false},
            {"data" : "updateTime",
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            {"data": "relId" ,"searchable":false,"orderable" : false},
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel=$('<a href="##" class="operate" style="color:#007eff">配置</a><input type="hidden" value="'+data.id+'"/>');
            $('td', row).eq(7).append(btnDel);
        },
        // "createdRow": function ( row, data, index,settings,json ) {
        //     var btnDel=$('<a href="##" class="operate" style="color:#007eff">配置</a><input type="hidden" value="'+data.code+'"/>');
        //     $('td', row).eq(7).append(btnDel);
        // },
        "initComplete" : function(settings,json) {
            $("#btn_search").click(function() {
                g_proceduceManage.fuzzySearch = true;
                g_proceduceManage.tableUser.ajax.reload();
            });
            $("#btn_search_reset").click(function() {
                $("input[name='procedure_name']").val("");
                g_proceduceManage.fuzzySearch = false;
                g_proceduceManage.tableUser.ajax.reload();
            });
            $("#procedure_list").on("click", ".operate",function(e){
                $("#merchantType option[value='0']").attr("selected",true);
                var sign=0;
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                var relId=$(target).parents("tr").children().eq(6).html();
                $("#relId").val(relId);
                $("#processId").val(id);
                    var productLayer=layer.open({
                        type: 1,
                        title: '请选择',
                        area: ['800px', '500px'],
                        content: $('#product'),
                        btn: ['保存', '取消'],
                        success: function () {
                            productTable();
                        },
                        yes:function(index,layero){
                            var param={};
                            debugger
                            param.id=$("#processId").val();
                            if($('#productRadio').attr('checked')){
                                param.relId = $('#productRadio').val();
                            }
                            if(!param.relId){
                                layer.msg("请选择功能码！",{time:2000});
                                return;
                            }
                            Comm.ajaxPost('processConfig/updateProcessRel',JSON.stringify(param), function (data) {
                                    if(data.code==0){
                                        layer.msg(data.msg,{time:2000},function(){
                                            layer.closeAll();
                                            g_proceduceManage.tableUser.ajax.reload();
                                        })
                                    }
                                },"application/json"
                            );
                        }
                    })
            })
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_proceduceManage.tableUser.on("order.dt search.dt", function() {
        g_proceduceManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});

//配置
function productTable(){
    var product = {
        tableUser: null,
        currentItem: null,
        fuzzySearch: false,
        getQueryCondition: function (data) {
            var paramFilter = {};
            var page = {};
            var param = {};

            //自行处理查询参数
            param.fuzzySearch = product.fuzzySearch;
            if (product.fuzzySearch) {
                param.searchKey = $("#product_name").val();
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
    product.tableUser = $('#product_list').dataTable($.extend({
        'iDeferLoading': true,
        "bAutoWidth": false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "destroy":true,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = product.getQueryCondition(data);
            Comm.ajaxPost('processConfig/getProductList', JSON.stringify(queryFilter), function (result) {
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
            {"data": null, 'class': 'hidden', "searchable": false, "orderable": false},
            {
                "className" : "childBox",
                "orderable" : false,
                "data" : null,
                "width" : "60px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input type="radio" value="'+data.code+'" id="productRadio" >'
                }
            },
            {"data": "code","orderable": false,"width": "80px"},
            {"data": "name","orderable": false,"width": "80px"},
            {"data": "desc", "orderable": false, "width": "100px"}
        ],
        "createdRow": function (row, data, index, settings, json) {
        },
        "initComplete": function (settings, json) {
            //单选行
            $("#product_list tbody").delegate( 'tr','click',function(e){
                $("#product_list input").attr("checked",false);
                var target=e.target;
                if(target.nodeName=='TD'){
                    var input=target.parentNode.children[1].children[0];
                    var isChecked=$(input).attr('isChecked');
                    if(isChecked=='false'){
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','true');
                    }else{
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','false');
                    }
                }
            });
            //搜索
            $("#product_list_search").click(function() {
                product.fuzzySearch = true;
                product.tableUser.ajax.reload();
            });
            //重置
            $("#product_list_search_reset").click(function() {
                $('#product_name').val("");
                product.fuzzySearch = true;
                product.tableUser.ajax.reload();
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    product.tableUser.on("order.dt search.dt", function () {
        product.tableUser.column(0, {
            search: "applied",
            order: "applied"
        }).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
}
