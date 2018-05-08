var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            param.sms_name = $("input[name='sms_name']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){

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
                Comm.ajaxPost('sms/smsList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "sms_name","orderable" : false},
                {"data": "sms_rule","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data=='0'){
                            return '注册';
                        }else if (data=='1'){
                            return '忘记密码';
                        }else if (data=='2'){
                            return '找回密码';
                        }else if (data=='3'){
                            return '验证码';
                        }else if (data=='4'){
                            return '放款';
                        }else if (data=='5'){
                            return '还款成功';
                        }else if (data=='6'){
                            return '还款失败';
                        }else if (data=='7'){
                            return '发送物流';
                        }else if (data=='8'){
                            return '更改物流';
                        }else if (data=='9'){
                            return '还款提醒';
                        }else if (data=='10'){
                            return '逾期提醒';
                        }
                    }},
                {"data": "sms_state","orderable" : false,
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
                var btndel=$('<span style="color: #307ecc;" onclick="deleteContact(\''+data.id+'\',\''+data.sms_state+'\')">删除</span>');
                debugger
                if(data.sms_state==1){//启用
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
                    $("input[name='sms_name']").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
                $("#btn_add").click(function() {
                    layer.open({
                        type : 1,
                        title : '添加短信模板',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '400', '400' ],
                        content : $('#Add_procedure_style'),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            $('#sms_name').val('');
                            $('#sms_content').val('');
                            $('#sms_state').val('');
                            $('input[name="sms_rule"]').attr("checked",false);
                        },
                        yes:function(index,layero){
                            debugger
                            var param={};
                            param.sms_name=$('#sms_name').val();
                            param.sms_state = $('#sms_state').val();
                            param.sms_content = $('#sms_content').val();
                            param.platform_type = $('#platform_type').val();
                            var chk_value =[];
                            $('input[name="sms_rule"]:checked').each(function(){
                                chk_value.push($(this).val());
                            });
                            param.sms_rule = chk_value.toString();
                            if(param.sms_name==''){
                                layer.msg("短信标题不可为空！",{time:2000});return
                            }
                            if(param.sms_state==''){
                                layer.msg("短信状态必选！",{time:2000});return
                            }
                            if(param.sms_content==''){
                                layer.msg("短信内容不可为空！",{time:2000});return
                            }
                            if(param.sms_rule==''){
                                layer.msg("短信使用规则至少选一个！",{time:2000});return
                            }
                            Comm.ajaxPost('sms/add',JSON.stringify(param), function (data) {
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
        sms_state: state
    }
    Comm.ajaxPost(
        'sms/updateState', JSON.stringify(param),
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
        'sms/del',JSON.stringify(param),
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
        title : '短信配置',
        maxmin : true,
        shadeClose : false,
        area : [ '400', '400' ],
        content : $('#Add_procedure_style'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            $('input[name="sms_rule"]').attr("checked",false);
            Comm.ajaxPost('sms/getById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger
                        $('#sms_name').val(data.data.sms_name);
                        $('#sms_content').val(data.data.sms_content);
                        $('#sms_state').val(data.data.sms_state);
                        $('#platform_type').val(data.data.platform_type);

                        var boxObj = $("input:checkbox[name='sms_rule']"); //获取所有的复选框值
                        var expresslist = data.data.sms_rule;
                        var express = expresslist.split(',');
                        $.each(express, function(index, expressId){
                            boxObj.each(function () {
                                if($(this).val() == expressId) {
                                    $(this).attr("checked",true);
                                }
                            });
                        });
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            debugger
            var param={};
            param.id=id;
            param.sms_name = $('#sms_name').val();
            param.sms_state = $('#sms_state').val();
            param.sms_content = $('#sms_content').val();
            param.platform_type = $('#platform_type').val();
            var chk_value =[];
            $('input[name="sms_rule"]:checked').each(function(){
                chk_value.push($(this).val());
            });
            param.sms_rule = chk_value.toString();
            if(param.sms_name==''){
                layer.msg("短信标题不可为空！",{time:2000});return
            }
            if(param.sms_state==''){
                layer.msg("短信状态必选！",{time:2000});return
            }
            if(param.sms_content==''){
                layer.msg("短信内容不可为空！",{time:2000});return
            }
            if(param.sms_rule==''){
                layer.msg("短信使用规则至少选一个！",{time:2000});return
            }
            Comm.ajaxPost('sms/update',JSON.stringify(param), function (data) {
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
