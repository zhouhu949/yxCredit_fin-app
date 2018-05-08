var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            param.message_name = $("input[name='message_name']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    apendSelect();
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
                Comm.ajaxPost('message/messageList', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
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
                {"data": "id" ,"searchable":false,"orderable" : false,"class":"hidden"},
                {"data": "message_name","orderable" : false},
                {"data": "message_type","orderable" : false},
                {"data": "message_state","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data==1){
                            return "启用";
                        }else if(data==2){
                            return "停用";
                        }else {
                            return "";
                        }
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {

                var btnConfig=$('<span style="color: #307ecc;" onclick="config(\''+data.id+'\')">修改</span>');
                var btnQyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+1+'\')">启用</span>');
                var btnTyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+2+'\')">停用</span>');
                var btndel=$('<span style="color: #307ecc;" onclick="deleteContact(\''+data.id+'\',\''+data.message_state+'\')">删除</span>');
                debugger
                if(data.message_state==1){//启用
                    $("td", row).eq(5).append(btnConfig).append(' ').append(btnTyong).append(' ').append(btndel);
                }else {
                    $("td", row).eq(5).append(btnConfig).append(' ').append(btnQyong).append(' ').append(btndel);
                }
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='message_name']").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
                $("#btn_add").click(function() {
                    layer.open({
                        type : 1,
                        title : '添加消息模板',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '600', '400' ],
                        content : $('#Add_procedure_style'),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            $('#message_name').val('');
                            $('#message_content').val('');
                            $('#message_state').val('1');
                            $('#message_type').val('');
                            // $('#message_desc').val('');
                        },
                        yes:function(index,layero){
                            debugger
                            var param={};
                            param.message_name=$('#message_name').val();
                            param.message_state = $('#message_state').val();
                            param.message_content = $('#message_content').val();
                            param.message_type = $('#message_type').val();
                            if(param.message_type==''){
                                layer.msg("消息类型必选！",{time:2000});return
                            }
                            if(param.message_name==''){
                                layer.msg("消息标题不可为空！",{time:2000});return
                            }
                            if(param.message_content==''){
                                layer.msg("消息内容不可为空！",{time:2000});return
                            }
                            if(param.message_state==''){
                                layer.msg("消息状态必选！",{time:2000});return
                            }
                            Comm.ajaxPost('message/add',JSON.stringify(param), function (data) {
                                    if(data.code==0){
                                        layer.msg(data.msg,{time:2000},function(){
                                            layer.closeAll();
                                            g_userManage.tableOrder.ajax.reload();
                                        })
                                    }
                                },"application/json"
                            );
                        }
                    });
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

//启用或停止
function updateState(id,state) {
    var param = {
        id: id,
        message_state: state
    }
    Comm.ajaxPost(
        'message/updateState', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableOrder.ajax.reload();
            });
        }, "application/json"
    );

}

// 删除
function deleteContact(id,state) {
    var param = {};
    param.id = id;
    if(state==1){
        layer.msg("启用状态下不可删除！",{time:2000});return
    }
    Comm.ajaxPost(
        'message/del',JSON.stringify(param),
        function (data) {
            layer.msg(data.msg,{time:2000},function(){
                g_userManage.tableOrder.ajax.reload();
            });
        }, "application/json");
}


// 模板配置
function config(id) {
    layer.open({
        type : 1,
        title : '消息配置',
        maxmin : true,
        shadeClose : false,
        area : [ '600', '400'  ],
        content : $('#Add_procedure_style'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            Comm.ajaxPost('message/getById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger
                        $('#message_name').val(data.data.message_name);
                        $('#message_content').val(data.data.message_content);
                        $('#message_state').val(data.data.message_state);
                        $('#message_type').val(data.data.message_type);
                        // $('#message_desc').val(data.data.message_desc);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            debugger
            var param={};
            param.id=id;
            param.message_name = $('#message_name').val();
            param.message_state = $('#message_state').val();
            param.message_content = $('#message_content').val();
            param.message_type = $('#message_type').val();
            if(param.message_type==''){
                layer.msg("消息类型必选！",{time:2000});return
            }
            if(param.message_name==''){
                layer.msg("消息标题不可为空！",{time:2000});return
            }
            if(param.message_content==''){
                layer.msg("消息内容不可为空！",{time:2000});return
            }
            if(param.message_state==''){
                layer.msg("消息状态必选！",{time:2000});return
            }
            Comm.ajaxPost('message/update',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:2000},function(){
                            layer.closeAll();
                            g_userManage.tableOrder.ajax.reload();
                        })
                    }
                },"application/json"
            );
        }
    });
}


//时间转换
function formatTime(t){
    debugger
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}

//动态加载下拉框内容
function apendSelect() {
    debugger
    Comm.ajaxPost('message/apendSelect',null, function (data) {
            debugger
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#message_type').append(btndel);
                }
            }
        },"application/json"
    );
}
