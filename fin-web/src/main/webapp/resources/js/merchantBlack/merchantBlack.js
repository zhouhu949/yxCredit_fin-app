/**
 * Created by Administrator on 2017/12/5.
 */
//获取全部区域限额设置信息
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
        param.blackTypeId = $("#search_state").val();
        param.content = $.trim($("#content").val());
        param.state=$("#state").val();
        // console.log(param);
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//显示商户黑名单配置列表
$(function (){
    g_userManage.tableUser = $('#afterLoanTable').dataTable($.extend({
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
            Comm.ajaxPost('merchantBlack/showMerchantBlackList', JSON.stringify(queryFilter), function (result) {
                //封装返回数据
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
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data": "blackTypeId","orderable" : false,"render": function (data, type, row, meta) {
                if(data=="1"){
                    return "商户名称";
                }else if(data=="2"){
                    return "营业执照注册号";
                }else if(data=="3"){
                    return "法人身份证号"
                }
                else {
                    return "";
                }
            }},
            {"data": "content","orderable" : false},
            {"data": "state","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data=="1"){
                        return "已启用";
                    }else if(data=="0"){
                        return "已停用";
                    }else {
                        return "";
                    }
                }
            },
            {"data": "createTime","orderable" : false,
                "render":function (data, type, row, meta) {
                    return getTime(data);
                }
            },
            {"data": "null", "orderable": false, "defaultContent":""}
        ],"createdRow": function ( row, data, index,settings,json ) {
            var btnStartOrStop="";
            if(data.state=="1"){
                btnStartOrStop=$('<a class="tabel_btn_style" onclick="(startOrStop(\''+data.id+'\',\''+0+'\'))"> 停用 </a>');//停用就是把状态改成0
            }else{
                btnStartOrStop=$('<a class="tabel_btn_style" onclick="(startOrStop(\''+data.id+'\',\''+1+'\'))">   启用 </a>');//启用就是把状态改成1
            };
            var btnChange=$('<a style="margin-right: 20px;" class="tabel_btn_style" onclick="(eiftBlack(\''+data.id+'\'))"> 修改</a>');
            var btnDel=$('<a class="tabel_btn_style" onclick="(deleteOne(\''+data.id+'\'))">删除 </a>');
            $("td", row).eq(5).append(btnStartOrStop);
            $("td", row).eq(5).append(btnChange);
            $("td", row).eq(5).append(btnDel);

        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //添加商户黑名单设置
            $("#btn_add_black").on("click",function(){
                layer.open({
                    type : 1,
                    title : '添加商户黑名单设置',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '500px', '400px' ],
                    content : $('#addBlack').show(),
                    btn : [ '保存', '取消' ],
                    success : function(index, layero) {
                       $("#add_type").val('');//类型
                       $("#add_content").val('');//内容
                       $("#add_state").val(' ');//状态
                    },
                    yes:function(index,layero){
                        var param={};
                        //传入后台参数
                        param.blackTypeId=$("#add_type").val();//类型
                        param.content=$.trim($("#add_content").val());//内容
                        param.state=$.trim($("#add_state").val());//状态
                        //console.log(param);
                        //在此处做字符串验证
                        var reg = new RegExp("^[0-9]*$");
                        if( param.blackTypeId==''){
                            layer.msg("黑名单类型为必选项",{time:2000});return
                        }
                        if(param.content==''){
                            layer.msg("内容为必填项",{time:2000});return
                        }
                        if(param.state==""){
                            layer.msg("是否启用为必选项~",{time:2000});return
                        }
                        Comm.ajaxPost('merchantBlack/addMerchantBlack',JSON.stringify(param), function (data) {
                                if(data.code==0){
                                    layer.msg(data.msg,{time:2000},function(){
                                        layer.closeAll();
                                        //添加之后刷新页面
                                        g_userManage.tableUser.ajax.reload();
                                    })
                                }
                            },"application/json"
                        );
                    }
                });
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
    }).draw();
});
//格式时间
function getTime(inputTime) {
    var y,m,d,h,mi,s;
    if(inputTime) {
        y = inputTime.slice(0,4);
        m = inputTime.slice(4,6);
        d = inputTime.slice(6,8);
        h = inputTime.slice(8,10);
        mi = inputTime.slice(10,12);
        s = inputTime.slice(12);
        return y+'-'+m+'-'+d+" "+h+":"+mi+":"+s;
    }
}
//停用或启用
function startOrStop(id,state) {
    var param = {
        id: id,
        state:state
    }
    Comm.ajaxPost(
        'merchantBlack/startOrstopState', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json"
    );
}
//修改区域限额
function eiftBlack(id){
    layer.open({
        type : 1,
        title : '修改商户黑名单配置窗口',
        maxmin : true,
        shadeClose : false,
        area : [ '500px', '400px' ],
        content : $('#editBlack').show(),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            //先查询并展示出来信息以供修改
            Comm.ajaxPost('merchantBlack/getOneMerchantBlackById',JSON.stringify(param), function (data) {
                    //console.log(data);
                    if(data.code==0){
                        $('#edit_type').val(data.data.blackTypeId);
                        $('#edit_content').val(data.data.content);
                        $('#edit_state').val(data.data.state);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            var param={};
            //传入后台参数
            param.id=id;
            param.blackTypeId=$("#change_province").val();
            param.content=$.trim($("#edit_content").val());
            param.state=$.trim($("#edit_state").val());
            //console.log(param);
            //在此处做字符串验证
            var reg = new RegExp("^[0-9]*$");
            if( param.blackTypeId==''){
                layer.msg("类型为必选项~",{time:2000});return
            }
            if(param.content==''){
                layer.msg("内容不能为空~",{time:2000});return
            }
            if(param.state==""){
                layer.msg("是否启用为必选项~",{time:2000});return
            }
            Comm.ajaxPost('merchantBlack/editOneMerchantBlackById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:2000},function(){
                            layer.closeAll();
                            //更改之后刷新页面
                            g_userManage.tableUser.ajax.reload();
                        })
                    }
                },"application/json"
            );
        }
    });

}
//删除方法
function deleteOne(id){
    console.log(12138);
    var param={};
    //传入后台参数
    param.id=id;
    Comm.ajaxPost('merchantBlack/deleteOneById',JSON.stringify(param), function (data) {
        if(data.code==0){
            layer.msg(data.msg,{time:2000},function(){
                layer.closeAll();
                //更改之后刷新页面
                g_userManage.tableUser.ajax.reload();
            })
        }
    },"application/json");
}
//查询重置
function resetSearch(){
    $("#search_state").val('');
    $("#content").val('');
    $("#state").val('');
    g_userManage.tableUser.ajax.reload();
}