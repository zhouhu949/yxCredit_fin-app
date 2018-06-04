var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        // if (g_userManage.fuzzySearch) {
        //     param.name = $("input[name='contractName']").val();
        //     var beginTime = $("#beginTime").val();
        //     if(beginTime != null && beginTime != ''){
        //         param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
        //     }
        //     var endTime = $("#endTime").val();
        //     if(endTime != null && endTime != ''){
        //         param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
        //     }
        // }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    apendSelect();
    // var beginTime = {
    //     elem: '#beginTime',
    //     format: 'YYYY-MM-DD hh:mm:ss',
    //     min: '1999-01-01 00:00:00',
    //     max: laydate.now(),
    //     istime: true,
    //     istoday: false,
    //     choose: function(datas){
    //         endTime.min = datas; //开始日选好后，重置结束日的最小日期
    //         endTime.start = datas //将结束日的初始值设定为开始日
    //     }
    // };
    // var endTime = {
    //     elem: '#endTime',
    //     format: 'YYYY-MM-DD hh:mm:ss',
    //     min: '1999-01-01 00:00:00',
    //     max: laydate.now(),
    //     istime: true,
    //     istoday: false,
    //     choose: function(datas){
    //         beginTime.max = datas; //结束日选好后，重置开始日的最大日期
    //     }
    // };
    // laydate(beginTime);
    // laydate(endTime);
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
                Comm.ajaxPost('contract/config', JSON.stringify(queryFilter), function (result) {
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
                {"data": "name","orderable" : false},
                {"data": "template_type","orderable" : false},
                {"data": "createTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {"data": "state","orderable" : false,
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

                var btnConfig=$('<span style="color: #307ecc;" onclick="config(\''+data.id+'\')">配置</span>');
                var btnQyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+1+'\')">启用</span>');
                var btnTyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+2+'\')">停用</span>');
                var btndel=$('<span style="color: #307ecc;" onclick="deleteContact(\''+data.id+'\',\''+data.state+'\')">删除</span>');
                if(data.state==1){//启用
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
                    $("input[name='contractName']").val("");
                    $("#beginTime").val("");
                    $("#endTime").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
                $("#btn_add").click(function() {
                    layer.open({
                        type : 1,
                        title : '添加模板',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '400', '400' ],
                        content : $('#Add_procedure_style'),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            $('#name').val('');
                            $('#state').val('');
                            $('#type').val('');
                        },
                        yes:function(index,layero){
                            debugger
                            var param={};
                            param.name=$('#name').val();
                            param.state = $('#state').val();
                            param.template_type = $('#type').val();
                            if(param.name==''){
                                layer.msg("模板名称不能为空！",{time:1000});return
                            }
                            if(param.state==''){
                                layer.msg("模板状态必选！",{time:1000});return
                            }
                            if(param.template_type==''){
                                layer.msg("模板类型必选！",{time:1000});return
                            }
                            param.platformType=$('#platformType').val();
                            Comm.ajaxPost('contract/add',JSON.stringify(param), function (data) {
                                    if(data.code==0){
                                        layer.msg(data.msg,{time:1000},function(){
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
        state: state
    }
    Comm.ajaxPost(
        'contract/updateState', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableOrder.ajax.reload();
            });
        }, "application/json"
    );

}

//下载合同文件
function downContact(src) {
    window.open(_ctx+src);
}
// 删除合同
function deleteContact(id,state) {
    var param = {};
    param.id = id;
    if(state == 1){
        layer.msg("启用模板不可删除！",{time:1000});
        return
    }
    Comm.ajaxPost(
        'contract/deleteContract',JSON.stringify(param),
        function (data) {
            layer.msg(data.msg,{time:1000},function(){
                g_userManage.tableOrder.ajax.reload();
            });
        }, "application/json");
}


// 模板配置
function config(id) {
    layer.open({
        type : 1,
        title : '配置模板',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#template_config'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            Comm.ajaxPost('contract/getById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger
                        $('#template_name').val(data.data.name);
                        $('#template_type').val(data.data.template_type);
                        $('#template_contented').val(data.data.content);
                        $('#platformType').val(data.data.platformType);
                        ue.setContent(data.data.content);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            debugger
            var param={};
            param.id=id;
            param.name = $('#template_name').val();
            param.template_type = $('#template_type').val();
            param.content = ue.getContent();
            param.content_no_bq = ue.getPlainTxt();
            if(param.name==''){
                layer.msg("模板名称不可为空！",{time:1000});return
            }
            if(param.template_type==''){
                layer.msg("模板类型必选！",{time:1000});return
            }
            if(param.content==''){
                layer.msg("模板内容不可为空！",{time:1000});return
            }
            debugger
            param.platformType=$('#platformType').val();
            Comm.ajaxPost('contract/update',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:1000},function(){
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
    Comm.ajaxPost('contract/apendSelect',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#type').append(btndel);$('#template_type').append(btndel);
                }
            }
        },"application/json"
    );
}

//动态加载下拉框内容
function platformType() {
    Comm.ajaxPost('activity/platformType',null, function (data) {
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