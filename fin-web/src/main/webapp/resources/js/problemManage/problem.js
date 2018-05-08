var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            param.problem_name = $("input[name='problem_name']").val();
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
                Comm.ajaxPost('problem/problemList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "problem_name","orderable" : false},
                {"data": "problem_type","orderable" : false},
                {"data": "problem_state","orderable" : false,
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
                var btndel=$('<span style="color: #307ecc;" onclick="deleteContact(\''+data.id+'\',\''+data.problem_state+'\')">删除</span>');
                debugger
                if(data.problem_state==1){//启用
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
                    $("input[name='problem_name']").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
                $("#btn_add").click(function() {
                    layer.open({
                        type : 1,
                        title : '添加问题模板',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '400', '500' ],
                        content : $('#Add_procedure_style'),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            $('#problem_name').val('');
                            $('#problem_content').val('');
                            $('#problem_state').val('');
                            $('#problem_type').val('');
                            $('#problem_desc').val('');
                        },
                        yes:function(index,layero){
                            debugger
                            var param={};
                            param.problem_name=$.trim($('#problem_name').val());//问题标题
                            param.problem_state = $.trim($('#problem_state').val());//问题状态
                            param.problem_content = $.trim($('#problem_content').val());//问题答案
                            param.problem_type = $('#problem_type').val();//问题分类
                            param.problem_desc = $.trim($('#problem_desc').val());//问题描述
                            param.platform_type= $.trim($('#platformType').val());//平台分类
                            if(param.problem_type==''){
                                layer.msg("问题分类必选！",{time:2000});return
                            }
                            if(param.problem_state==''){
                                layer.msg("问题状态必选！",{time:2000});return
                            }
                            if(param.problem_name==''){
                                layer.msg("问题标题不可为空！",{time:2000});return
                            }
                            if(param.problem_content==''){
                                layer.msg("问题答案不可为空！",{time:2000});return
                            }
                            Comm.ajaxPost('problem/add',JSON.stringify(param), function (data) {
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
        problem_state: state
    }
    Comm.ajaxPost(
        'problem/updateState', JSON.stringify(param),
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
    // if(state==1){
    //     layer.msg("启用状态下不可删除！",{time:2000});return
    // }
    Comm.ajaxPost('problem/del',JSON.stringify(param),
        function (data) {
            debugger
            layer.msg(data.msg, {time: 2000}, function () {
                g_userManage.tableOrder.ajax.reload();
            });
        }, "application/json");
}


// 模板配置
function config(id) {
    layer.open({
        type : 1,
        title : '问题配置',
        maxmin : true,
        shadeClose : false,
        area : [ '400', '400' ],
        content : $('#Add_procedure_style'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            Comm.ajaxPost('problem/getById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger
                        $('#problem_name').val(data.data.problem_name);
                        $('#problem_content').val(data.data.problem_content);
                        $('#problem_state').val(data.data.problem_state);
                        $('#problem_type').val(data.data.problem_type);
                        $('#problem_desc').val(data.data.problem_desc);
                        $('#platformType').val(data.data.platform_type);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            debugger
            var param={};
            param.id=id;
            param.problem_name = $('#problem_name').val();
            param.problem_state = $('#problem_state').val();
            param.problem_content = $('#problem_content').val();
            param.problem_desc = $('#problem_desc').val();
            param.problem_type = $('#problem_type').val();
            param.platform_type=$('#platformType').val();
            if(param.problem_type==''){
                layer.msg("问题分类必选！",{time:2000});return
            }
            if(param.problem_state==''){
                layer.msg("问题状态必选！",{time:2000});return
            }
            if(param.problem_name==''){
                layer.msg("问题标题不可为空！",{time:2000});return
            }
            if(param.problem_content==''){
                layer.msg("问题答案不可为空！",{time:2000});return
            }
            Comm.ajaxPost('problem/update',JSON.stringify(param), function (data) {
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
    Comm.ajaxPost('problem/apendSelect',null, function (data) {
            debugger
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#problem_type').append(btndel);
                }
            }
        },"application/json"
    );
    Comm.ajaxPost('problem/platformType',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#platformType').append(btndel);
                }
            }
        },"application/json"
    );
}
