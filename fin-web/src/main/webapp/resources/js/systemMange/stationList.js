var g_userManage = {
    tableUser : null,
    tableStation : null,
    tableProcess:null,
    currentItem : null,
    stationIdFlag:false,
    stationNameFlag : false,
    userSearchFlag : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.stationNameFlag) {
            param.stationName = $("#search_name").val();
        }
        if (g_userManage.stationIdFlag) {
            param.stationId = $("#search_stationId").val();
        }
        if (g_userManage.userSearchFlag) {
            param.account = $("[name='account']").val();
            param.trueName = $("[name='trueName']").val();
            param.tel = $("[name='mobile']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    if(g_userManage.tableStation){
        g_userManage.tableStation.ajax.reload();
        return;
    }
    g_userManage.tableStation = $('#orger_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('station/list', JSON.stringify(queryFilter), function (result) {
                //封装返回数据
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;

                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {
                'data':null,
                "searchable":false,"orderable" : false
            },
            // {
            //     "className" : "childBox",
            //     "orderable" : false,
            //     "data" : null,
            //     "searchable":false,
            //     "render" : function(data, type, row, meta) {
            //         return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false">'
            //     }
            // },
            {"data": "stationName","orderable" : false},
            {"data": "stationDesc","orderable" : false},
            {"data" : "status","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==1) return '启用';
                    else return '停用';
                }
            },
            {"data" : "updateTime","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            // {
            //     "data": "null", "orderable": false, "orderable" : false,
            //     "render": function (data, type, row, meta) {
            //         var str='<a href="##" style="text-decoration: none;color: #307ecc;" id="edit">编辑&nbsp;</a>';
            //         str +='<a href="##" style="text-decoration: none;color: #307ecc;" id="setUp">岗位设置&nbsp;</a>';
            //         str +='<a href="##" style="text-decoration: none;color: #307ecc;" id="process">查看岗位流程</a>';
            //         return str;
            //     }
            // },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="updateOrger(0,\''+data.id+'\')">编辑</a>&nbsp;<a class="tabel_btn_style" onclick="updateStatusRus(1,\''+data.id+'\')">启用</a>&nbsp;<a class="tabel_btn_style" onclick="updateStatusRus(0,\''+data.id+'\')">停用</a>&nbsp;<a class="tabel_btn_style" onclick="setUpStation(\''+data.id+'\')">岗位设置</a>&nbsp;<a class="tabel_btn_style_dele" onclick="checkProess(\''+data.id+'\')">查看岗位流程</a>&nbsp;<a class="tabel_btn_style_dele" onclick="updateStatusRus(-1,\''+data.id+'\')">删除</a>');
            $('td', row).eq(5).append(btnDel);

        },
        "initComplete" : function(settings,json) {
            //编辑
            $("#orger_list").on("click","#edit",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                updateOrger(0,id);
            });
            //岗位设置
            $("#orger_list").on("click","#setUp",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                setUpStation(id);
            });
            //查看岗位流程
            $("#orger_list").on("click","#process",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                g_userManage.stationIdFlag = true;
                $("#search_stationId").val(id);
                showStationProcessList();
            });
            //全选
            $("#orgerCheckBox_All").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#orger_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#orger_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //搜索
            $("#btn_search").click(function() {
                g_userManage.stationNameFlag = true;
                g_userManage.tableStation.ajax.reload();
            });
            //重置
            $("#btn_search_reset").click(function() {
                $("#search_name").val("");
                g_userManage.stationNameFlag = false;
                g_userManage.tableStation.ajax.reload();
            });
            //单选行
            $("#orger_list tbody").delegate( 'tr','click',function(e){
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
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableStation.on("order.dt search.dt", function() {
        g_userManage.tableStation.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
    g_userManage.stationNameFlag = false;
});
function checkProess(id) {
    g_userManage.stationIdFlag = true;
    $("#search_stationId").val(id);
    showStationProcessList();
}
//添加、编辑岗位信息
function updateOrger(sign,id) {
    $("#addOver").hide();
    $("#add_process").hide();
    $("#edit_process").hide();
    $("#edit_process").unbind();
    $('input[name="stationName"]').val("");
    $('input[name="stationDesc"]').val("");
    $('input[name="process"]').val("");
    if(id && sign==0){
        Comm.ajaxPost('station/detail',id,function(result){
                var data=result.data;
                var editLayer = layer.open({
                    type : 1,
                    title : '修改岗位信息',
                    area : [ '360px', '230px' ],
                    content : $('#Add_orger_style'),
                    btn : [ '保存', '取消' ],
                    success:function () {
                        $("#edit_process").show();
                        $('input[name="stationName"]').val(data.stationName);
                        $('input[name="stationDesc"]').val(data.stationDesc);
                        $("#edit_process").click(function(e){
                            showProcessCheckBoxTree(0,id);
                        });
                    },
                    yes : function(index, layero) {
                        if ($('input[name="stationName"]').val() == "") {
                            layer.msg("岗位名称不能为空",{time:2000});
                            return;
                        }
                        var stationName=$('input[name="stationName"]').val();
                        var stationDesc=$('input[name="stationDesc"]').val();
                        var process=$('input[name="process"]').val();
                        var paramMap={
                            id:id,
                            stationName:stationName,
                            stationDesc:stationDesc,
                            process:process
                        };
                        Comm.ajaxPost('station/edit',JSON.stringify(paramMap),
                            function(data){
                                layer.msg(data.msg,{time:2000},function () {
                                    layer.close(editLayer);
                                    $('#orger_list').dataTable().fnDraw(false);
                                });
                            }, "application/json"
                        );
                    }
                });
            },"application/json");
    }else{
        var addLayer = layer.open({
            type : 1,
            title : '添加岗位信息',
            area : [ '360px', '230px' ],
            content : $('#Add_orger_style'),
            btn : [ '保存', '取消' ],
            success : function(index, layero) {
                $("#add_process").show();
            },
            yes : function(index, layero) {
                if ($('input[name="stationName"]').val() == "") {
                    layer.msg("岗位名称不能为空",{time:2000});
                    return;
                }
                if ($('input[name="process"]').val() == "") {
                    layer.msg("岗位流程不能为空",{time:2000});
                    return;
                }
                var stationName=$('input[name="stationName"]').val();
                var stationDesc=$('input[name="stationDesc"]').val();
                var process=$('input[name="process"]').val();
                var paramMap={
                    stationName:stationName,
                    stationDesc:stationDesc,
                    process:process
                };
                Comm.ajaxPost('station/add',JSON.stringify(paramMap),
                    function(data){
                        layer.msg(data.msg,{time:2000},function () {
                            layer.close(addLayer);
                            g_userManage.tableStation.ajax.reload();
                        });
                    },"application/json"
                );
            }
        });
    }
}
//删除,启用,停用
function updateStatusRus(status,id){
    // var selectArray = $("#orger_list tbody input:checked");
    // if(!selectArray || selectArray.length==0){
    //     layer.msg("请至少选择一个岗位！");
    //     return;
    // }
    var ids = new Array();
    ids.push(id);
    // $.each(selectArray,function(i,e){
    //     var val = $(this).val();
    //     ids.push(val);
    // });
    // if(ids.length==0)return;
    var param = {
        status:status,
        idList:ids
    };
    var msg = "";
    if(status==1) msg = "是否确定启用？";
    if(status==0) msg = "是否确定停用？";
    if(status==-1) msg = "是否确定删除？";
    layer.confirm(msg,{btn : [ '确定', '取消' ]}, function() {
        Comm.ajaxPost('station/updateStatus', JSON.stringify(param),function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:2000},function () {
                    $("#orger_list").dataTable().fnDraw(false);
                });
            },"application/json");
    });
}
//岗位设置-展现用户列表-选择用户
function setUpStation(stationId) {
    var setUpLayer = layer.open({
        type : 1,
        title : '岗位设置',
        shadeClose : false,
        area : ['730px','400px'],
        content : $('#userDiv'),
        btn : [ '保存', '取消' ],
        success:function (index, layero) {
            if(g_userManage.tableUser){
                g_userManage.tableUser.ajax.reload(function(){
                    Comm.ajaxPost('station/getSUByStationId', stationId, function (result) {
                        var dataArr = result.data;
                        $(".childBox_setUp input").each(function () {
                            for(var i=0;i<dataArr.length;i++){
                                if($(this).val() == dataArr[i].userId){
                                    $(this).attr("checked",true);
                                }
                            }
                        })
                    },"application/json");
                });
            }else{
                g_userManage.tableUser = $('#user_list').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {
                        var queryFilter = g_userManage.getQueryCondition(data);
                        Comm.ajaxPost('user/list',JSON.stringify(queryFilter),function(result){
                            var returnData = {};
                            var resData = result.data;
                            var resPage = result.page;
                            returnData.draw = data.draw;
                            returnData.recordsTotal = resPage.resultCount;
                            returnData.recordsFiltered = resPage.resultCount;
                            returnData.data = resData;
                            callback(returnData);
                        },"application/json")

                    },
                    "order": [],
                    "columns" :[
                        {'data':null,'class':'hidden',"searchable":false,"orderable" : false},
                        {
                            "className" : "childBox_setUp",
                            "orderable" : false,
                            "data" : null,
                            "width" : "60px",
                            "searchable":false,
                            "render" : function(data, type, row, meta) {
                                return '<input type="checkbox" value="'+data.userId+'" style="cursor:pointer;" isChecked="false">'
                            }
                        },
                        {"data":'account',"searchable":false,"orderable" : false},
                        {"data":'trueName',"searchable":false,"orderable" : false},
                        {"data":'tel',"searchable":false,"orderable" : false},
                        {
                            "data" : null,
                            "searchable":false,
                            "orderable" : false,
                            "render" : function(data, type, row, meta) {
                                if(data.status==1){
                                    return '启用'
                                }else{
                                    return '停用'
                                }
                            }
                        },
                    ],
                    "initComplete" : function(settings,json) {
                        Comm.ajaxPost('station/getSUByStationId', stationId, function (result) {
                            var dataArr = result.data;
                            $(".childBox_setUp input").each(function () {
                                for(var i=0;i<dataArr.length;i++){
                                    if($(this).val() == dataArr[i].userId){
                                        $(this).attr("checked",true);
                                    }
                                }
                            })
                        },"application/json");
                        //搜索
                        $("#user_btn_search").click(function() {
                            g_userManage.userSearchFlag = true;
                            g_userManage.tableUser.ajax.reload(function(){
                                $("#userCheckBox_All").attr("checked",false);
                            });
                        });
                        //重置
                        $("#user_btn_search_reset").click(function() {
                            $('input[name="account"]').val("");
                            $('input[name="trueName"]').val("");
                            $('input[name="mobile"]').val("");
                            g_userManage.userSearchFlag = false;
                            g_userManage.tableUser.ajax.reload(function(){
                                $("#userCheckBox_All").attr("checked",false);
                            });
                        });
                        //全选
                        $("#userCheckBox_All").click(function(J) {
                            if (!$(this).prop("checked")) {
                                $("#user_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                            } else {
                                $("#user_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                            }
                        });
                        //单选行
                        $("#user_list tbody").delegate( 'tr','click',function(e){
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
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_userManage.tableUser.on("order.dt search.dt", function() {
                    g_userManage.tableUser.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
                g_userManage.userSearchFlag = false;
            }
        },
        yes : function(index, layero) {
            var selectArray = $("#user_list tbody input:checked");
            if(!selectArray || selectArray.length==0){
                layer.msg("请至少选择一个用户！");
                return;
            }
            var userIds = new Array();
            $.each(selectArray,function(i,e){
                var val = $(this).val();
                userIds.push(val);
            });
            console.log(userIds)
            if(userIds.length>0){
                var addParam = {
                    stationId:stationId,
                    userIds:userIds.join(",")
                }
                Comm.ajaxPost('station/setUpStation',JSON.stringify(addParam),function(data){
                    layer.msg(data.msg,{time:2000},function () {
                        layer.close(setUpLayer);
                    });
                }, "application/json");
            }
        }
    });
}
//岗位流程列表
function showStationProcessList() {
    var processLayer = layer.open({
        type : 1,
        title : '岗位流程',
        shadeClose : false,
        area : ['650px','400px'],
        content : $('#processDiv'),
        success:function (index, layero) {
            if(g_userManage.tableProcess){
                g_userManage.tableProcess.ajax.reload();
            }else{
                g_userManage.tableProcess = $('#station_process').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {
                        var queryFilter = g_userManage.getQueryCondition(data);
                        Comm.ajaxPost('station/getProcessByStationId', JSON.stringify(queryFilter), function (result) {
                            //封装返回数据
                            var returnData = {};
                            var resData = result.data;
                            var resPage = result.page;

                            returnData.draw = data.draw;
                            returnData.recordsTotal = resPage.resultCount;
                            returnData.recordsFiltered = resPage.resultCount;
                            returnData.data = resData;
                            callback(returnData);
                        },"application/json");
                    },
                    "order": [],
                    "columns": [
                        {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},//序号
                        {"data": "id" ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"data": "processId" ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"data": "nodeId" ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"data": "stationName","orderable" : false},
                        {"data": "processName","orderable" : false},
                        {"data": "nodeName","orderable" : false},
                    ],
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_userManage.tableProcess.on("order.dt search.dt", function() {
                    g_userManage.tableProcess.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
                g_userManage.stationIdFlag = false;
            }
        }
    });
}
/*************************************** 添加岗位流程jstreeCheckBox ******************************************/
var jsTreeCheckBox;
function showProcessCheckBoxTree(sign,id) {
    var addLayer = layer.open({
        type : 1,
        title : '岗位添加流程',
        shadeClose : false,
        offset:["20px"],
        area : ['300px','500px'],
        content : $('#jsTreeCheckBox'),
        btn : [ '保存', '取消' ],
        success:function (index, layero) {
            //这个是关键，如果不清空实例，jstree不会重新生成
            if(jsTreeCheckBox){
                $('#jsTreeCheckBox').data('jstree', false).empty();
                jsTreeCheckBox.unbind();
            }
            jsTreeCheckBox = $('#jsTreeCheckBox').jstree({
                "core" : {
                    "animation" : 0,
                    "check_callback" : true,
                    "themes" : { "stripes" : true },
                    'data' : function (obj, callback) {
                        var array = new Array();
                        Comm.ajaxGet("station/getProcess", null, function (result) {
                            var data= result.data;
                            console.log(data)
                            for(var i=0 ; i<data.length; i++){
                                var arrayChild = new Array();
                                Comm.ajaxGet("station/getProNodeByProId", "proId="+data[i].id, function (res) {
                                    var dataChild= res.data;
                                    for(var j=0 ; j<dataChild.length; j++){
                                        var arrChild = {
                                            "id":dataChild[j].node_id,
                                            "text":dataChild[j].node_name,
                                            "icon":false
                                        }
                                        arrayChild.push(arrChild);
                                    }
                                },null,false);
                                var arr = {
                                    "id":data[i].id,
                                    "text":data[i].name,
                                    "state": {"opened": true},
                                    "children" : arrayChild,
                                    "icon":false
                                }
                                array.push(arr);
                            }
                        },null,false);
                        callback.call(this, array);
                    }
                },
                "checkbox" : {
                    "keep_selected_style" : false,
                    "real_checkboxes" : true
                },
                "plugins" : [ "checkbox" ],
            });
            jsTreeCheckBox.bind('loaded.jstree', function(obj, e){
                //$('#jsTreeCheckBox').jstree().open_all();
                if(sign==0){
                    var inst = e.instance;
                    var queryFilter = {param:{stationId:id}};
                    Comm.ajaxPost('station/getProcessByStationId', JSON.stringify(queryFilter), function (result) {
                        var dataArr = result.data;
                        for(var i=0;i<dataArr.length;i++){
                            var obj = inst.get_node(dataArr[i].nodeId);
                            inst.select_node(obj);
                        }
                    },"application/json");
                }
            });
        },
        yes : function(index, layero) {
            var jsonArr = new Array();
            var processIds = new Array();
            var node_ids=$("#jsTreeCheckBox").jstree("get_all_checked");//取得所有选中,半选中的节点id，返回数组
            var str="[";
            var objStr="";
            if(node_ids.length>0){
                for(var i=0;i<node_ids.length;i++){
                    var node1 = $('#jsTreeCheckBox').jstree("get_node", node_ids[i]);//根据节点id得到节点对象
                    if(node1.parent=="#"){
                        processIds.push(node1.id);
                    }
                }
                for(var j=0;j<processIds.length;j++){
                    var nodeIds = new Array();
                    for(var i=0;i<node_ids.length;i++){
                        var node2 = $('#jsTreeCheckBox').jstree("get_node", node_ids[i]);//根据节点id得到节点对象
                        if(node2.parent == processIds[j]){
                            nodeIds.push(node2.id);
                        }
                    }
                    if(j==0){
                        objStr+="{\'processId\':"+"\'"+processIds[j]+"\'"+",\'nodeId\':"+"\'"+nodeIds.join(",")+"\'"+"}";
                    }else{
                        objStr+=",{\'processId\':"+"\'"+processIds[j]+"\'"+",\'nodeId\':"+"\'"+nodeIds.join(",")+"\'"+"}";
                    }
                }
                str += objStr+"]";
                $('input[name="process"]').val(str);
                $("#addOver").show();
                if(sign==0)$("#edit_process").hide();
                else $("#add_process").hide();
                layer.close(addLayer);
            }else{
                layer.msg("尚未选择流程！",{time:2000});
            }
        }
    });
    //改变jstree的方法，为了得到半选中的节点
    $('#jsTreeCheckBox').jstree(true).get_all_checked = function(full) {
        var tmp=new Array;
        for(var i in this._model.data){
            if(this.is_undetermined(i)||this.is_checked(i)){tmp.push(full?this._model.data[i]:i);}
        }
        return tmp;
    };
}