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
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel=$('<a href="##" class="operate" style="color:#007eff">修改</a>');
            var btnCK=$('<a href="##" class="btnCK" style="color:#007eff">进入流程</a>');
            // var btnDel = $('<img class="operate" src="'+_ctx+'/resources/images/operate.png" onclick="updateProcedure(0,'+data.id+')"/>');
            // var btnCK = $('<button  id="btnCK" onclick="goProcessNode('+data.id+')">进入流程</button>');
            $('td', row).eq(6).append(btnDel);
            $('td', row).eq(6).append("&nbsp&nbsp&nbsp;");
            $('td', row).eq(6).append(btnCK);
        },
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
                var sign=0;
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                if(id && sign==0) {
                    layer.open({
                        type: 1,
                        title: '修改信息',
                        area: ['400px', '400px'],
                        content: $('#Add_procedure_style'),
                        btn: ['保存', '取消'],
                        yes: function (index, layero) {
                            if ($('#orger_name').val() == "") {
                                layer.msg("流程名称不能为空", {time: 2000});
                                return;
                            }
                            var name = $('#orger_name').val();
                            var desc = $('#remark').val();
                            var message = $('#message').val();
                            var engine = {
                                id: id,
                                name: name,
                                desc: desc,
                                message:message
                            };
                            Comm.ajaxPost(
                                'process/update', JSON.stringify(engine),
                                function (data) {
                                    layer.closeAll();
                                    layer.msg(data.msg, {time: 1000}, function () {
                                        $('#orger_list').dataTable().fnDraw(false);
                                        g_proceduceManage.tableUser.ajax.reload();
                                    });
                                }, "application/json");
                        },
                        success: function () {
                            Comm.ajaxPost('process/initupdate', "id=" + id, function (data) {
                                debugger
                                var eng = data.data;
                                if(eng!=null){
                                    $('#orger_code').val(eng.code);
                                    $('#orger_name').val(eng.name);
                                    $('#remark').val(eng.desc);
                                    $('#message').val(eng.message);
                                    // $('#proName').val(eng.proName);
                                }else {
                                    return;
                                }

                            });
                        }
                    });
                }else{
                    $('#orger_name').val("");
                    $('#remark').val("");
                    var codeUUID;
                    Comm.ajaxGet(
                        'process/getUUID',{},
                        function(data){
                            codeUUID = data.data.uuid;
                            $("#orger_code").val(codeUUID);
                        });

                    layer.open({
                        type : 1,
                        title : '添加流程',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '600px', '500px' ],
                        offset: '150px',
                        content : $('#Add_procedure_style'),
                        btn : [ '保存', '取消' ],
                        yes : function(index, layero) {
                            if ($('#orger_name').val() == "") {
                                layer.msg("流程名称不能为空",{time:2000});
                                return;
                            }
                            var name=$('#orger_name').val();
                            var desc=$('#remark').val();
                            var message=$('#message').val();
                            var process={
                                code:codeUUID,
                                name:name,
                                desc:desc,
                                message:message
                            };
                            Comm.ajaxPost(
                                'process/update',JSON.stringify(process),
                                function(data){
                                    debugger
                                    layer.closeAll();
                                    layer.msg(data.msg,{time:1000},function () {
                                        g_proceduceManage.tableUser.ajax.reload();
                                    });
                                },"application/json"
                            );
                        }
                    });
                }
            });
            $("#procedure_list").on("click", ".btnCK",function(e){
                var target = e.target || window.event.target;
                var processId = $(target).parents("tr").children().eq(1).html();
                window.location=_ctx+"/process/toprocess?id="+processId+"&parentId=1&flag=1";
            });
            // $("#procedure_list tbody").delegate( 'tr','dblclick',function(e){
            //     var target=e.target;
            //     var id;//流程ID
            //     if(target.nodeName=='TD'){
            //         var tr=target.parentNode.children[1];
            //         id = $(tr).html();
            //     }
            //     window.location=_ctx+"/process/toprocess?id="+id+"&parentId=1&flag=1";
            // });
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

//layer选择产品弹框
function openFieldsList() {
    var layerOne = layer.open({
        type: 1,
        title: '选择产品',
        maxmin: true,
        shadeClose: false,
        area: ['600px', '450px'],
        content: $(".group-dialog"),
        btn: ['保存', '取消'],
        success: function bindPro(){
            debugger
            var isShowFileds=$("#isShowFileds").val();
            if(isShowFileds){
                // g_proceduceManage.tableUser.ajax.reload();
            }else{
                g_proceduceManage.tableUser = $('#swarmFields_list').dataTable($.extend({
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
                        var queryFilter = g_proceduceManage.getQueryCondition(data);
                        Comm.ajaxPost('decision_flow/getProList',JSON.stringify(queryFilter),function(result){
                            debugger
                            var returnData = {};
                            var resData = result.data.list;
                            var resPage = result.data;
                            returnData.draw = data.draw;
                            returnData.recordsTotal = resPage.total;
                            returnData.recordsFiltered = resPage.total;
                            returnData.data = resData;
                            callback(returnData);
                        },"application/json")

                    },
                    "order": [],
                    "columns" :[
                        {"data": null,"class":"hidden","orderable" : false,"searchable":false},
                        {
                            "className" : "childBox",
                            "orderable" : false,
                            "data" : null,
                            "width" : "60px",
                            "searchable":false,
                            "render" : function(data, type, row, meta) {
                                return '<input type="radio" code="'+data.pro_name+'" name="pro" value="'+data.id+'" style="cursor:pointer;" isChecked="false">'
                            }
                        },
                        {"data": "pro_name","orderable" : false,"searchable":false}
                    ],
                    "createdRow": function ( row, data, index,settings,json ) {
                    },
                    "initComplete" : function(settings,json) {
                        $("#btn_searchr").click(function() {
                            g_proceduceManage.fuzzySearch = true;
                            g_proceduceManage.tableUser.ajax.reload();
                        });
                        $("#btn_search_resetr").click(function() {
                            $('#ziduanchaxun input[name="Parameter_search"]').val("");
                            g_proceduceManage.fuzzySearch = true;
                            g_proceduceManage.tableUser.ajax.reload();
                        });
                        $("#swarmFields_list tbody").delegate( 'tr input','change',function(e){
                            var isChecked=$(this).attr('isChecked');
                            var selectArray = $("#swarmFields_list tbody input:checked");
                            if(selectArray.length>0){
                                for(var i=0;i<selectArray.length;i++){
                                    $(selectArray[i]).attr('checked',false);
                                    $(this).attr('isChecked','false');
                                }
                            }
                            if(isChecked=='false'){
                                if($(this).attr('checked')){
                                    $(this).attr('checked',false);
                                }else{
                                    $(this).attr('checked','checked');
                                }
                                $(this).attr('isChecked','true');
                            }else{
                                if($(this).attr('checked')){
                                    $(this).attr('checked',false);
                                }else{
                                    $(this).attr('checked','checked');
                                }
                                $(this).attr('isChecked','false');
                            }
                        });
                        $("#swarmFields_list tbody").delegate( 'tr','click',function(e){
                            var target=e.target;
                            if(target.nodeName=='TD'){
                                if(!target.parentNode.children[1]){
                                    return;
                                }
                                var input=target.parentNode.children[1].children[0];
                                var isChecked=$(input).attr('isChecked');
                                var selectArray = $("#swarmFields_list tbody input:checked");
                                if(selectArray.length>0){
                                    for(var i=0;i<selectArray.length;i++){
                                        $(selectArray[i]).attr('checked',false);
                                        $(input).attr('isChecked','false');
                                    }
                                }
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
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_proceduceManage.tableUser.on("order.dt search.dt", function() {
                    g_proceduceManage.tableUser.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
                $("#isShowFileds").val(1);
            }
            // openFieldsList();
        },
        yes: function (index, layero) {
            debugger
            layer.close(layerOne);
            var proId = $("input[name='pro']:checked").val();
            var proName = $("input[name='pro']:checked").attr("code");
            $("#proName").val(proName);
            $("#proName").attr("proId",proId);
        }
    });
}
//layer弹出流程弹框
function updateProcedure(sign) {
    $('#orger_name').val("");
    $('#remark').val("");
    $('#message').val("");
    $('#proName').val("");
    var codeUUID;
    Comm.ajaxGet(
        'process/getUUID',{},
        function(data){
            codeUUID = data.data.uuid;
            $("#orger_code").val(codeUUID);
        });

    layer.open({
        type : 1,
        title : '添加流程',
        maxmin : true,
        shadeClose : false,
        area : [ '400px', '400px' ],
        offset: '150px',
        content : $('#Add_procedure_style'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            if ($('#orger_name').val() == "") {
                layer.msg("流程名称不能为空",{time:2000});
                return;
            }
            if ($('#proName').val() == "") {
                layer.msg("需要绑定产品", {time: 2000});
                return;
            }
            debugger
            var name=$('#orger_name').val();
            var desc=$('#remark').val();
            var message=$('#message').val();
            var proId = $('#proName').attr("proId");
            var process={
                code:codeUUID,
                name:name,
                desc:desc,
                message:message,
                relId:proId
            };
            Comm.ajaxPost(
                'process/update',JSON.stringify(process),
                function(data){
                    layer.closeAll();
                    layer.msg(data.msg,{time:1000},function () {
                        // window.location.reload();
                        g_proceduceManage.tableUser.ajax.reload();
                    });
                },"application/json"
            );
        }
    });
}
