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
        param.name =$.trim($("#coupon_name").val());
        param.state=$("#state").val();
        console.log(param);
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//推广渠道列表展示
$(function (){
    g_userManage.tableUser = $('#sign_list').dataTable($.extend({
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
            Comm.ajaxPost('cuponRecommendController/getRecommendList', JSON.stringify(queryFilter), function(result) {
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
            {"data": "name","orderable" : false},
            {"data": "code","orderable" : false},
            {"data":"remarks","orderable" : false},
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
            {"data": "null", "orderable": false, "defaultContent":""},
        ],"createdRow": function ( row, data, index,settings,json ) {
            //二维码按钮
            var btnQrCode=$('<a style="margin-right: 20px;" class="tabel_btn_style" onclick="(showQrCode(\''+data.name+'\',\''+data.code+'\'))">二维码&nbsp;&nbsp;</a>');

            var btnStartOrStop="";
            if(data.state=="1"){
                btnStartOrStop=$('<a class="tabel_btn_style" onclick="(startOrStop(\''+data.id+'\',\''+0+'\'))">停用</a>');//停用就是把状态改成0
            }else{
                btnStartOrStop=$('<a class="tabel_btn_style" onclick="(startOrStop(\''+data.id+'\',\''+1+'\'))">启用</a>');//启用就是把状态改成1
            };
            var btnChange=$('<a style="margin-right: 20px;" class="tabel_btn_style" onclick="(eiftRecommend(\''+data.id+'\'))">&nbsp;&nbsp;修改</a>');
            var btnDel=$('<a class="tabel_btn_style" onclick="(deleteOne(\''+data.id+'\'))">删除</a>');
            $("td", row).eq(5).append(btnQrCode);
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
            //重置
            $("#btn_search_reset").click(function() {
                $("#coupon_name").val("");
                $('#state').val("");
                g_userManage.fuzzySearch = false;
                g_userManage.tableUser.ajax.reload();
            });
            //添加推广渠道
            $("#btn_add").on("click",function(){
                layer.open({
                    type : 1,
                    title : '添加推广渠道',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '350px', '350px' ],
                    content : $('#add_recommend').show(),
                    btn : [ '保存', '取消' ],
                    success : function(index, layero) {
                        $("#add_name").val('');//名称
                        $("#add_remarks").val('');//描述
                        $("#add_code").val('');//代码
                    },
                    yes:function(index,layero){
                        var param={};
                        //传入后台参数
                        param.name=$.trim($("#add_name").val());//类型
                        param.remarks=$.trim($.trim($("#add_remarks").val()));//备注
                        param.code=$.trim($("#add_code").val());//代码
                        //在此处做字符串验证

                        // var reg = new RegExp("^[0-9]*$");
                        if( param.name==''){
                             layer.msg("名称不能为空~",{time:2000});return
                         }
                        if( param.remarks==''){
                            layer.msg("描述不能为空",{time:2000});return
                        }
                        if( param.code==''){
                            layer.msg("代码不能为空",{time:2000});return
                        }
                        if( param.code.length > 64){
                            layer.msg("代码长度不能超过64~",{time:2000});return
                        }
                        if(escape(param.code).indexOf("%u") !=-1){
                            layer.msg("代码中不能出现汉字~",{time:2000});return
                        }

                        Comm.ajaxPost('cuponRecommendController/addRecommend',JSON.stringify(param), function (data) {
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
// function getTime(inputTime) {
//     var y,m,d,h,mi,s;
//     if(inputTime) {
//         y = inputTime.slice(0,4);
//         m = inputTime.slice(4,6);
//         d = inputTime.slice(6,8);
//         h = inputTime.slice(8,10);
//         mi = inputTime.slice(10,12);
//         s = inputTime.slice(12);
//         return y+'-'+m+'-'+d+" "+h+":"+mi+":"+s;
//     }
// }
//停用或启用
function startOrStop(id,state) {
    var param = {
        id: id,
        state:state
    }
    Comm.ajaxPost(
        'cuponRecommendController/startOrstopState', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json"
    );
}

//显示二维码
function showQrCode(name, code){
    layer.open({
        type : 1,
        title : name ? ('二维码-' + name) : '二维码',
        maxmin : true,
        shadeClose : false,
        area : [ '270px', '305px' ],
        content : $('#recommend_qrcode').show(),
        success : function(index, layero) {
            Comm.ajaxPost('cuponRecommendController/showQRCode',JSON.stringify({code:code}), function (data) {
                    if(data.code==0){
                        $("#qrcode_img").attr("src", data.data);
                    }
                    else
                    {
                        var msg = data.msg ? data.msg : "获取二维码失败";
                        alert(msg);
                    }
                },"application/json"
            );
        }
    });
}

//修改商户推广渠道
function eiftRecommend(id){
    layer.open({
        type : 1,
        title : '修改推广渠道',
        maxmin : true,
        shadeClose : false,
        area : [ '450px', '250px' ],
        content : $('#edit_recommend').show(),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            //先查询并展示出来信息以供修改
            Comm.ajaxPost('cuponRecommendController/showOneRecommend',JSON.stringify(param), function (data) {
                    console.log(data);
                    if(data.code==0){
                        $("#edit_name").val(data.data.name);//名称
                        $("#edit_remarks").val(data.data.remarks);//描述
                        $("#edit_code").val(data.data.code);//代码
                        //设置文本框不可编辑
                        $("#edit_code").attr("disabled",true);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            var param={};
            //传入后台参数
            param.id=id;
            param.name=$.trim($("#edit_name").val());
            param.remarks=$.trim($("#edit_remarks").val());
            console.log(param);
            //在此处做字符串验证
            if( param.name==''){
                layer.msg("名称不能为空~",{time:2000});return
            }
            if(param.remarks==''){
                layer.msg("备注不能为空~",{time:2000});return
            }
            Comm.ajaxPost('cuponRecommendController/editRecommend',JSON.stringify(param), function (data) {
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
    Comm.ajaxPost('cuponRecommendController/deleteOneById',JSON.stringify(param), function (data) {
        if(data.code==0){
            layer.msg(data.msg,{time:2000},function(){
                layer.closeAll();
                //更改之后刷新页面
                g_userManage.tableUser.ajax.reload();
            })
        }
    },"application/json");
}