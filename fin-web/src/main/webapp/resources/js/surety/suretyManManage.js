/**
 * Created by Administrator on 2017/12/5.
 */
//获取全部担保人信息
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
        param.name=$.trim($("#search_name").val()); //担保人姓名
        param.tel=$.trim($("#search_tel").val());   //担保人手机号
        //param.idcard=$.trim($("#content").val());
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//担保人列表展示
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
            Comm.ajaxPost('surety/getSuretyList', JSON.stringify(queryFilter), function(result) {
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
            {"data": "tel","orderable" : false},
            {"data":"idcard","orderable" : false},
            {"data": "relation","orderable" : false},
            {"data":"remark","orderable" : false},
            {"data": "null", "orderable": false, "defaultContent":""}
        ],"createdRow": function ( row, data, index,settings,json ) {
            var btnshowOrder=$('<a style="margin-right: 20px;" class="tabel_btn_style" onclick="searchSuretyOrder(\''+data.id+'\',\''+data.name+'\')">查看</a>');
            var btnDanbao=$('<a class="tabel_btn_style" onclick="(danBao(\''+data.id+'\'))">担保</a>');
            $("td", row).eq(6).append(btnshowOrder).append(btnDanbao);
            // $("td", row).eq(7).append(btnDanbao);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //添加担保人
            $("#btn_add").on("click",function(){
                addRelationOption();
                layer.open({
                    type : 1,
                    title : '添加担保人',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '350px', '400px' ],
                    content : $('#add_surety').show(),
                    btn : [ '保存', '取消' ],
                    success : function(index, layero) {
                        $("#add_relation").val('');//担保人关系
                        $("#add_name").val('');//担保人姓名
                        $("#tel").val('');//担保人电话
                        $("#add_idcard").val('');//担保人身份证号
                    },
                    yes:function(index,layero){
                        var param={};
                        //传入后台参数
                        param.relation=$("#add_relation").val();//关系
                        param.name=$.trim($.trim($("#add_name").val()));//担保人姓名
                        param.tel=$.trim($("#add_tel").val());//担保人电话
                        param.idcard=$.trim($("#add_idcard").val());//担保人身份证号
                        param.remark=$.trim($("#add_remark").val());//担保人备注
                        //在此处做字符串验证
                        var regTel=(/^1[3|4|7|5|8][0-9]\d{4,8}$/)
                        var regIdcard=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                        if( param.relation==''){
                            layer.msg("担保人关系不能为空~",{time:2000});return
                        }
                        if( param.name==''){
                            layer.msg("担保人姓名不能为空",{time:2000});return
                        }
                        if( param.tel==''){
                            layer.msg("担保人手机号不能为空",{time:2000});return
                        }
                        if( !regTel.test(param.tel)){
                            layer.msg("担保人手机号格式不正确~",{time:2000});return
                        }
                        if( param.idcard==''){
                            layer.msg("担保人身份证号不能为空",{time:2000});return
                        }
                        if( !regIdcard.test(param.idcard)){
                            layer.msg("担保人身份证号格式不正确~",{time:2000});return
                        }
                        console.log(param);
                        Comm.ajaxPost('surety/addSurety',JSON.stringify(param), function (data) {
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
//动态加载下拉选getSuretyRelationList
function addRelationOption(){
    Comm.ajaxPost(
        'surety/getSuretyRelationList', JSON.stringify(),
        function (data) {
            console.log(data.data);
            for(i=0;i<data.data.length;i++){
                var opt=$('<option style="font-size: 10px;" value='+data.data[i].code+'>'+data.data[i].name+'</option>')
                $("#add_relation").append(opt);
            }

        }, "application/json"
    );
}
//******查看担保的订单的对象*********/
var g_CustomerOrderManage = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(suretyId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.suretyId=$("#suretyId").val();
        param.customerName=$.trim($("#customerName").val());
        param.tel=$.trim($("#tel").val());
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/******可以被担保的订单查询的对象************/
var g_CustomerOrderManageDanBao = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(suretyId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.suretyId=$("#suretyId_danbao").val();
        param.name=$.trim($("#danbao_content_surety_order_name").val());
        param.tel=$.trim($("#danbao_content_surety_order_tel").val());
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//担保按钮触发的方法
function danBao(id) {
    //设置搜索框为空danbao_content_surety_order_name
    $("#danbao_content_surety_order_name").val("");
    $("#danbao_content_surety_order_tel").val("");
    $("#suretyId_danbao").val(id)
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+'选择订单担保',
        shadeClose : false,
        area : [ '1100px', '800px' ],
        content : $("#danbao_surety_order_list_div"),
        success : function(index, layero) {
            getCanBeSuretyOrders(id);
        }
    })
}
//查询可以被担保的订单方法
function getCanBeSuretyOrders(id){
    if(!g_CustomerOrderManageDanBao.tableCusOrder) {
        g_CustomerOrderManageDanBao.tableCusOrder = $('#danbao_surety_order_list').dataTable($.extend({
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
                var queryFilter = g_CustomerOrderManageDanBao.getQueryCondition(id,data);
                Comm.ajaxPost('surety/getSuretyCanOrder', JSON.stringify(queryFilter), function (result) {
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
                {"data": "orderNo","orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data==""){
                            return "";
                        }else {
                            return getTime(data);
                        }
                    }
                },
                {"data": "productNameName","orderable" : false},
                {"data": "state","orderable" : false,
                    "render":function(data, type, row, meta){
                        if(data=="0"){
                            return "未提交";
                        }else if(data=="1"){
                            return "借款申请";
                        }else if(data=="2"){
                            return "自动化审批通过";
                        }else if(data=="3"){
                            return "自动化审批拒绝";
                        }else if(data=="4"){
                            return "自动化审批异常";
                        }else if(data=="5"){
                            return "人工审批通过";
                        }else if(data=="6"){
                            return "人工审批拒绝";
                        }else if(data=="7"){
                            return "合同确认";
                        }else if(data=="8"){
                            return "放款成功";
                        }else if(data=="9"){
                            return "结清";
                        }else if(data=="10"){
                            return "关闭";
                        }
                    }},
                {"data": "null", "orderable": false, "defaultContent":""}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                //添加担保按钮
                var BtnDanbao=$('<a class="tabel_btn_style" onclick="(danbaoOneOrder(\''+data.id+'\',\''+data.state+'\'))">担保</a>');
                $("td", row).eq(8).append(BtnDanbao);
            },
            "initComplete" : function(settings,json) {
                //查询按钮触发
                $("#btn_search_order_danbao").click(function(){
                    g_CustomerOrderManageDanBao.tableCusOrder.ajax.reload();
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_CustomerOrderManageDanBao.tableCusOrder.on("order.dt search.dt", function() {
            g_CustomerOrderManageDanBao.tableCusOrder.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }else {
        g_CustomerOrderManageDanBao.tableCusOrder.ajax.reload();
    }
}
//格式化时间
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
//查看按钮触发的方法
function searchSuretyOrder(id,name){
    //设置搜索框为空
    $("#customerName").val("");
    $("#tel").val("");
    //将担保人id放到隐藏域里面
    $("#suretyId").val(id)
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+name+'担保的订单',
        shadeClose : false,
        area : [ '1100px', '800px' ],
        content : $("#surety_order_list_div"),
        success : function(index, layero) {
            getOrders(id);
    }
    })
}
//查询担保人担保的订单方法
function  getOrders(id) {
    if(!g_CustomerOrderManage.tableCusOrder) {
        g_CustomerOrderManage.tableCusOrder = $('#surety_order_list').dataTable($.extend({
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
                var queryFilter = g_CustomerOrderManage.getQueryCondition(id,data);
                Comm.ajaxPost('surety/getSuretyOrder', JSON.stringify(queryFilter), function (result) {
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
                {"data": "orderNo","orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data==""){
                            return "";
                        }else {
                            return getTime(data);
                        }
                    }
                },
                {"data": "productNameName","orderable" : false},
                {"data": "state","orderable" : false,
                    "render":function(data, type, row, meta){
                        if(data=="0"){
                            return "未提交";
                        }else if(data=="1"){
                            return "借款申请";
                        }else if(data=="2"){
                            return "自动化审批通过";
                        }else if(data=="3"){
                            return "自动化审批拒绝";
                        }else if(data=="4"){
                            return "自动化审批异常";
                        }else if(data=="5"){
                            return "人工审批通过";
                        }else if(data=="6"){
                            return "人工审批拒绝";
                        }else if(data=="7"){
                            return "合同确认";
                        }else if(data=="8"){
                            return "放款成功";
                        }else if(data=="9"){
                            return "结清";
                        }else if(data=="10"){
                            return "关闭";
                        }
                    }}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                //绑定函数在此处
                // //查询按钮触发
                $("#btn_search_order").click(function(){
                    g_CustomerOrderManage.tableCusOrder.ajax.reload();
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_CustomerOrderManage.tableCusOrder.on("order.dt search.dt", function() {
            g_CustomerOrderManage.tableCusOrder.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }else {
        g_CustomerOrderManage.tableCusOrder.ajax.reload();
    }
}
//担保人担保单个订单的方法
function danbaoOneOrder(orderId,state){
        debugger
        var param={};
        param.suretyId = $("#suretyId_danbao").val();
        param.orderId=orderId;
        param.state=state;
        console.log(param)
        Comm.ajaxPost('surety/suretyAssureOrder',JSON.stringify(param), function (data) {
                if(data.code==0){
                    layer.msg(data.msg,{time:2000},function(){
                       // layer.closeAll();
                        //担保完成之后刷新页面
                        g_CustomerOrderManageDanBao.tableCusOrder.ajax.reload();
                    })
                }
            },"application/json"
        );
    }
//查询重置
$(function(){
    $("#btn_search_reset").on("click",function () {
        $("#search_name").val("");
        $("#search_tel").val("");
        g_userManage.tableUser.ajax.reload();
    });
    $("#btn_search_order_reset").click(function(){
        $("#customerName").val("");
        $("#tel").val("");
        g_CustomerOrderManage.tableCusOrder.ajax.reload();

    });
    $("#btn_search_order_danbao_reset").click(function(){
        $("#danbao_content_surety_order_name").val("");
        $("#danbao_content_surety_order_tel").val("");
        g_CustomerOrderManageDanBao.tableCusOrder.ajax.reload();
    });

})


