var checkedIds="";//翻页保存选中的id
var g_userManage = {
    tableOrder : null,
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            param.name = $("input[name='coupon_name']").val();
            param.coupon_type = $('#coupon_typed').val();
            param.coupon_state = $('#coupon_stated').val();
        }
        param.customer_name = $('#customer_name').val();
        param.tel = $('#tel').val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    Comm.ajaxPost('coupon/platformType',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#platformType').append(btndel);
                }
            }
        },"application/json"
    );
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
                Comm.ajaxPost('coupon/partnersList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "name","orderable" : false},
                {"data": "path","orderable" : false},
                {"data": "link","orderable" : false},
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {
                var btnConfig=$('<span style="color: #307ecc;" onclick="config(\''+data.id+'\')">修改</span>');
                var btndel=$('<span style="color: #307ecc;" onclick="deleteContact(\''+data.id+'\',\''+data.coupon_state+'\')">删除</span>');
                $("td", row).eq(5).append(btnConfig).append(' ').append(btndel);

            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='coupon_name']").val("");
                    $('#coupon_typed').val("");
                    $('#coupon_stated').val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
                $("#btn_add").click(function() {
                    debugger
                    layer.open({
                        type : 1,
                        title : '添加合作方',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '350px', '400px' ],
                        content : $('#Add_procedure_style'),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            $('#coupon_name').val('');
                            $('#coupon_desc').val('');
                            $('#activity_id').val();
                            $("#addMaterial").attr('src','../resources/images/photoadd.png');
                        },
                        yes:function(index,layero){
                            var param={};
                            param.name=$('#coupon_name').val();
                            param.link = $('#coupon_desc').val();
                            param.path =$('#activity_img_fileName').val();
                            param.state="1"
                            param.platformType=$('#platformType').val();

                            if(param.name==''){
                                layer.msg("名称不能为空！",{time:2000});return
                            }

                            if(param.link==''){
                                layer.msg("链接地址不能为空！",{time:2000});return
                            }
                            var reg = /[\u4E00-\u9FA5]/g;
                            if(reg.test(param.link)){
                                layer.msg("活动链接不能为中文！",{time:2000});return
                            }

                            if(param.link==''){
                                layer.msg("请上传图片！",{time:2000});return
                            }
                            Comm.ajaxPost('coupon/addPartners',JSON.stringify(param), function (data) {
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
        coupon_state: state
    }
    Comm.ajaxPost(
        'coupon/updateState', JSON.stringify(param),
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
    param.state = "0";
    Comm.ajaxPost('coupon/updatePartners',JSON.stringify(param), function (data) {
            if(data.code==0){
                layer.msg("删除成功！",{time:2000},function(){
                    g_userManage.tableOrder.ajax.reload();
                })
            }
        },"application/json"
    );
}


// 配置
function config(id) {
    layer.open({
        type : 1,
        title : '合作方修改',
        maxmin : true,
        shadeClose : false,
        area : [ '350px', '400px' ],
        content : $('#Add_procedure_style'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            Comm.ajaxPost('coupon/getPartnersId',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger
                        $('#coupon_name').val(data.data.name);
                        $('#coupon_desc').val(data.data.link);
                        $('#activity_img_fileName').val(data.data.path);
                        $('.addMaterial').attr('src',data.data.activity_img_url);
                        $('#platformType').val(data.data.platformType);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            var param={};
            param.name=$('#coupon_name').val();
            param.link = $('#coupon_desc').val();
            param.path =$('#activity_img_fileName').val();
            param.id=id;
            param.platformType=$('#platformType').val();
            if(param.name==''){
                layer.msg("名称不能为空！",{time:2000});return
            }

            if(param.link==''){
                layer.msg("链接地址不能为空！",{time:2000});return
            }
            var reg = /[\u4E00-\u9FA5]/g;
            if(reg.test(param.link)){
                layer.msg("活动链接不能为中文！",{time:2000});return
            }

            if(param.link==''){
                layer.msg("请上传图片！",{time:2000});return
            }
            Comm.ajaxPost('coupon/updatePartners',JSON.stringify(param), function (data) {
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
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}

//动态加载下拉框内容
function apendSelect() {
    Comm.ajaxPost('coupon/apendSelect',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#coupon_type').append(btndel);$('#coupon_typed').append(btndel);
                }
            }
        },"application/json"
    );
    Comm.ajaxPost('coupon/apendSelected',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#coupon_state').append(btndel);$('#coupon_stated').append(btndel);
                }
            }
        },"application/json"
    );
    Comm.ajaxPost('coupon/productConfig',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].id+'">'+map[i].pro_name+map[i].periods+'天'+'</option>';
                    $('#productConfig').append(btndel);
                }
            }
        },"application/json"
    );
}


function changeType() {
    var coupon_type = $('#coupon_type').val();
    if(coupon_type==1){//折扣
        $('#couponChangeB').show();
        $('#couponChangeA').hide();
    }else if(coupon_type == 0){//减免
        $('#couponChangeB').hide();
        $('#couponChangeA').show();
    }
}

function checkedBox(id) {
    debugger
    var param = {};
    param.coupon_id = id;
    Comm.ajaxPost('coupon/getCustomer',JSON.stringify(param), function (result) {
        debugger
        var dataArr = result.data;
        $(".childBox_setUp input").each(function () {
            for(var i=0;i<dataArr.length;i++){
                if($(this).val() == dataArr[i].customer_id){
                    $(this).attr("checked",true);
                }
            }
        })
    },"application/json",null,null,null,false);
}
