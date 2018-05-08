var g_userManage = {
    tableOrder : null,
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.personName=null;
        param.beginTime=null;
        param.endTime=null;
        paramFilter.param = param;
        param.parentId = $("#parentIdHtml").val();
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {

            if($("#mtype").val()!=null && $("#mtype").val()!=''){
                param.mtype = $("#mtype").val();
            }
            if ($("#mname").val()!=null && $("#mname").val()!=''){
                param.mname = $("#mname").val();
            }
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
        g_userManage.tableOrder = $('#Res_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "destroy":true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('commodity/getCommodityList', JSON.stringify(queryFilter), function (result) {
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
                {"data": null ,"searchable":false,"orderable" : false},
                {"data": "id" ,"searchable":false,"orderable" : false,"class":"hidden"},
                {"data": "merchandiseName","orderable" : false},
             /*   {"data": "apex1",
                    "orderable" : false,
                    "render" : function(data, type, row, meta) {
                        if(data==1){
                            return '<lable class="label label-sm label-success arrowed arrowed-in">按钮</lable>'
                        }else{
                            return '<lable class="label label-sm label-warning arrowed arrowed-right">菜单</lable>'
                        }
                    }
                },*/
                {"data": "type","orderable" : false,
                    "render" : function(data, type, row, meta) {
                        if(data==1) {
                            return "商品类型";
                        }else if(data==2) {
                            return "品牌名称";
                        }else if(data==3) {
                            return "具体型号";
                        }else if(data==4){
                            return "具体版本";
                        }else{
                            return "";
                        }
                    }
                },
              /*  {"data": "state","orderable" : false,
                    "render" : function(data, type, row, meta) {
                        if(data==1) {
                            return "是";
                        }
                        if(data==0){
                            return "否";
                        }
                    }
                },*/
             /*   {"data": "imgUrl","orderable" : false},*/
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
               /* {"data": "alterTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },*/
                {"data": "descri","orderable" : false},
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {
                var btnQyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+1+'\')">启用</span>');
                var btnTyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+0+'\')">停用</span>');
              /*  var btnlook=$('<span style="color: #307ecc;" onclick="editDetail(\'1\',\''+data.id+'\')">查看</span>');
                var btnConfig=$('<span style="color: #307ecc;" onclick="editDetail(\'2\',\''+data.id+'\')">修改</span>');
                var btndel=$('<span style="color: #307ecc;" onclick="deleteContact(\''+data.id+'\',\''+data.state+'\')">删除</span>');*/
               if(data.state==1){//启用
                    $("td", row).eq(6).append(btnTyong).append(' ');
                }else {
                    $("td", row).eq(6).append(btnQyong).append(' ');
                }
            },
            "initComplete" : function(settings,json) {
                 //搜索
                 $("#b_search").click(function() {
                     $("#parentIdHtml").val('');
                     g_userManage.fuzzySearch = true;
                     g_userManage.tableOrder.ajax.reload();
                 });
                 //重置
                 $("#btn_search_reset").click(function() {
                     $("#mtype").val("");
                     $("#mname").val("");
                     g_userManage.fuzzySearch = false;
                     g_userManage.tableOrder.ajax.reload();
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
        //tableUser.draw( false );//刷新数据
    }
});
function queryList(cId) {
    $("#parentIdHtml").val(cId);
    $("#mtype").val('');
    $("#mname").val('');
    g_userManage.tableOrder.ajax.reload();
}
//时间转换
function formatTime(t){
    
    var time = t.replace(/\s/g,"");//去掉所有空格
    if(t.length>=14) {
        time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
            time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    }
    return time;
}
//启用或停止
function updateState(id,state) {
    var param = {
        id: id,
        state: state
    }
    Comm.ajaxPost('commodity/updateCommodityState', JSON.stringify(param), function (data) {
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableOrder.ajax.reload();
            });
        }, "application/json"
    );
}
function  editDetail(type,id) {
    if (type == '1') {
        layerIndex = layer.open({
            type: 1,
            title: "查看服务包",
            maxmin: true,
            shadeClose: false,
            area: ['550px', '400px'],
            content: $('#menu-edit'),
            success: function () {
                $("#aaa").hide();
                var param = {};
                param.id = id;
                Comm.ajaxPost('commodity/getCommodity1', JSON.stringify(param), function (result) {
                    console.log(result);
                    var detailData = result.data;
                    $("input[name='merchandiseName']").val(detailData.merchandiseName);
                    var oRadio = document.getElementsByName("state");
                    for(var i=0;i<oRadio.length;i++) //循环
                    {
                        if(oRadio[i].value==detailData.state) //比较值
                        {
                            oRadio[i].checked=true; //修改选中状态
                            break; //停止循环
                        }
                    }
                    $('.addMaterial').attr('src','../resources/images/photoadd.png');
                    $('#img').attr('src',detailData.imgUrl);
                    $("#apex1").val(detailData.apex1);
                    $("#imgUrl").val(detailData.imgUrl);
                    /*$("input[name='state']").val(detailData.state);*/
                    $("input[name='descri']").val(detailData.descri);
                    $("input[name='apex3']").val(detailData.apex3);
                    //实现只读
                    $("input[name='merchandiseType']").val(detailData.merchandiseType);
                    $("input[name='state']").attr("disabled","disabled");
                    $("#type").val(detailData.type);
                    $("#merchandiseName").attr("disabled","disabled");
                    $("#apex1").attr("disabled","disabled");
                    $("#type").attr("disabled","disabled");
                    $("input[name='descri']").attr("disabled","disabled");
                    $("input[name='pic']").attr("disabled","disabled");
                }, "application/json");
            },
        })
    } else if (type == '2'){
        layerIndex = layer.open({
            type: 1,
            title: "修改商品",
            maxmin: true,
            shadeClose: false,
            area: ['550px', '400px'],
            content: $('#menu-edit'),
            btn: ['提交', '取消'],
            success: function () {
                $("#aaa").hide();
                var param = {};
                param.id = id;
                Comm.ajaxPost('commodity/getCommodity1', JSON.stringify(param), function (result) {
                    console.log(result);
                    var detailData = result.data;
                    $("input[name='merchandiseName']").val(detailData.merchandiseName);
                    var oRadio = document.getElementsByName("state");
                    for(var i=0;i<oRadio.length;i++) //循环
                    {
                        if(oRadio[i].value==detailData.state) //比较值
                        {
                            oRadio[i].checked=true; //修改选中状态
                            break; //停止循环
                        }
                    }
                    $('.addMaterial').attr('src','../resources/images/photoadd.png');
                    $('#img').attr('src',detailData.imgUrl);
                    $("#apex1").val(detailData.apex1);
                    $("#imgUrl").val(detailData.imgUrl);
                    /*$("input[name='state']").val(detailData.state);*/
                    $("input[name='descri']").val(detailData.descri);
                    $("input[name='apex3']").val(detailData.apex3);
                    $("input[name='merchandiseType']").val(detailData.merchandiseType);
                    $("#type").val(detailData.type);
                    $('#activity_img_fileName').val(detailData.imgUrl);

                    //取消只读
                    $("#merchandiseName").removeAttr("disabled");
                    $("input[name='state']").removeAttr("disabled");
                    $("#apex1").removeAttr("disabled");
                    $("#type").removeAttr("disabled");
                    $("input[name='descri']").removeAttr("disabled");
                    $("input[name='pic']").removeAttr("disabled");
                }, "application/json");
            },
            yes: function (index, layero) {
                var commodity = {};
                commodity.id = id;
                //param.id = $('#id').val();
                commodity.merchandiseName = $("input[name='merchandiseName']").val();
                commodity.imgUrl = $("input[name='imgUrl']").val();
                commodity.apex3 = $("input[name='apex3']").val();
                commodity.state = $("input[name='state']:checked").val();
                commodity.descri = $("input[name='descri']").val();
                commodity.apex1 = $("#apex1").val();
                //menu.icon = $("input[name='icon']").val();
                commodity.merchandiseType = $("input[name='merchandiseType']").val();
                commodity.type = $("#type").val();
                if($("#type").val()==1){
                    commodity.merchandiseType = "商品类型";
                }else if($("#type").val()==2){
                    commodity.merchandiseType = "品牌名称";
                } else if($("#type").val()==3){
                    commodity.merchandiseType = "具体型号";
                }else if($("#type").val()==4){
                    commodity.merchandiseType = "具体版本";
                }
                if($("input[name='merchandiseName']").val()==""){
                    layer.alert("菜单名称不能为空！",{icon: 2, title:'操作提示'});
                    return
                }
                if($("input[name='state']:checked").val()==""||$("input[name='state']:checked").val()==null){
                    layer.alert("请选择是否显示！",{icon: 2, title:'操作提示'});
                    return
                }
                if($("#type").val()==0){
                    layer.alert("商品类型不能为空！",{icon: 2, title:'操作提示'});
                    return
                }
                Comm.ajaxPost('commodity/updateCommodity', JSON.stringify(commodity), function (result) {
                    layer.msg(result.msg, {time: 2000}, function () {
                        if (result.code == "0") {
                            layer.closeAll();
                            g_userManage.tableOrder.ajax.reload();
                            $.jstree.reference("#jstree").refresh();
                        }
                    });
                }, "application/json");
            }
        })
    }
}
// 删除
function deleteContact(id,state) {
    var param = {};
    param.id = id;
    if(state==1){
        layer.msg("启用状态下不可删除！",{time:2000});return
    }
    Comm.ajaxPost(
        'commodity/deleteCommodity',JSON.stringify(param),
        function (data) {
            layer.msg(data.msg,{icon:1,offset:'200px',time:2000},function(){
                $.jstree.reference("#jstree").refresh();
            });
            g_userManage.tableOrder.ajax.reload();
        }, "application/json");
}
