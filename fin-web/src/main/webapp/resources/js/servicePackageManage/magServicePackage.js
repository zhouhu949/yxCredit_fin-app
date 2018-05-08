var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        debugger
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            if($("#type").val()!=null && $("#type").val()!=''){
                param.type = $("#type").val();
            }
            if ($("#pname").val()!=null && $("#pname").val()!=''){
                param.pname = $("#pname").val();
            }
            if ($("#mgstate").val()!=null && $("#mgstate").val()!=''){
                param.mgstate = $("#mgstate").val();
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
    showType();
})
$(function (){
    if(g_userManage.tableCustomer){
        g_userManage.tableCustomer.ajax.reload();
    }else{
        g_userManage.tableCustomer = $('#fee_list').dataTable($.extend({
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
                debugger
                Comm.ajaxPost('magServicePackage/magServicePackageList', JSON.stringify(queryFilter), function (result) {
                    /*console.log(result);*/
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
                {"data": "id","orderable" : false,"class":'hidden'},
                {"data": "package_type_name","orderable" : false},
                {"data": "package_name","orderable" : false},
                {"data": "repayment_type","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if (data == 1) {
                            return "前置";
                        } else if (data == 2) {
                            return "月付";
                        } else {
                            return "";
                        }
                    }

                },
                {"data": "period_collection_type","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if (data == "0") {
                            return "随产品期数";
                        } else if (data == "1") {
                            return "自定义期数";
                        } else {
                            return "-";
                        }
                    }

                },
                {"data": "force_collection","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if (data == "0") {
                            return "否";
                        } else if (data == "1") {
                            return "是";
                        } else {
                            return "-";
                        }
                    }

                },
                {"data": "period_collection","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if (data == ""||data == null) {
                            return "-";
                        }else {
                            return data;
                        }
                    }
                },
                {"data": "month","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if (data == ""||data == null) {
                            return "-";
                        }else {
                            return data;
                        }
                    }
                },
                {"data": "amount_collection","orderable" : false},
/*<th>序号</th>
 <th></th>
 <th>服务包类型</th>
 <th>服务包名称</th>
 <th>付款方式</th>
 <th>收取期数</th>
 <th>几个月后允许提前还款</th>
 <th>收取金额</th>
 <th>启用/停用</th>
 <th>操作</th>*/
                {
                    "data": "state", "orderable": false,
                    "render": function (data, type, row, meta) {
                        if (data == 1) {
                            return "启用";
                        } else if (data == 2) {
                            return "停用";
                        } else {
                            return "";
                        }
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],

            "createdRow": function ( row, data, index,settings,json ) {
                var btnQyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+1+'\')">启用</span>');
                var btnTyong=$('<span style="color: #307ecc;" onclick="updateState(\''+data.id+'\',\''+2+'\')">停用</span>');
                var btnUpdate = $('<span style="color: #307ecc;" onclick="editDetail(\'2\',\''+data.id+'\')">修改 </span>&nbsp; ');
                if(data.state==1){//启用
                    $("td", row).eq(11).append(btnTyong).append(' ').append(btnUpdate);
                }else {
                    $("td", row).eq(11).append(btnQyong).append(' ').append(btnUpdate);
                }
               /* $('td', row).eq(9).append(btnUpdate);*/


            },"initComplete" : function(settings,json) {
                //搜索
                $("#b_search").click(function () {
                    debugger
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableCustomer.ajax.reload();
                });
                $("#btn_add").click(function() {
                    layer.open({
                        type : 1,
                        title : '添加服务包',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '600px', '300px' ],
                        content : $('#editDetail').show(),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            $("#package_id").removeAttr("disabled");
                         /*   $('#id').val();*/
                            $('#package_id').val('');
                            $('#package_name').val('');
                            $('#month').val('');
                            $('#period_collection').val('');
                            $('#repayment_type').val('');
                            $('#amount_collection').val('');
                            $('#state').val('');
                            $("#apex1").attr("checked",true);
                            $("#package_id").val(1);
                            $("#collection_period").val(1);
                            $("#zdyqsTxt").removeAttr("disabled");
                            $("#zdyqs").hide();
                            $("#zdyqsTxt").val("");
                            $("#apex1").attr("checked","checked");

                            $("#fwb1f").show();
                            $("#aa").show();
                            $("#jqhktqhk1f").show();
                            $("#sqje1f").show();
                            $("#sfqy1f").show();
                            $("#sqqs1f").hide();
                            $("#qzsq1f").hide();
                        },

                        yes:function(index,layero){
                            var param={};
                            /*param.id=id;*/
                            debugger
                            param.id = $('#id').val();
                            param.packageId = $('#package_id').val();
                            param.packageName = $('#package_name').val();
                            param.repaymentType = $('#repayment_type').val();
                            param.amountCollection = $('#amount_collection').val();
                            param.state= $('#state').val();
                            var value=$("#package_id").val();
                            var valuePeriod=$("#collection_period").val();
                            if(value=="1"){
                                param.month = $('#month').val();
                                if(!parseInt(param.month)){
                                    layer.alert("几期后可提前还款格式有误！",{icon: 2, title:'操作提示'});
                                    return
                                }
                            }else  if (value=="2"){
                                if (valuePeriod=='1'){
                                    param.periodCollectionType="1";
                                    param.periodCollection = $('#zdyqsTxt').val();
                                    if(!parseInt(param.periodCollection)){
                                        layer.alert("自定义期数格式有误！",{icon: 2, title:'操作提示'});
                                        return
                                    }
                                }else {
                                    param.periodCollectionType="0";
                                }
                                param.month = $('#month').val();
                                if(!parseInt(param.month)){
                                    layer.alert("几期后可提前还款格式有误！",{icon: 2, title:'操作提示'});
                                    return
                                }
                            }else  if (value=="3"){
                                if($("#apex1").attr("checked")) {
                                    param.forceCollection="1";
                                }else {
                                    param.forceCollection="0";
                                }
                                debugger
                                if (valuePeriod=='1'){
                                    param.periodCollectionType="1";
                                    param.periodCollection = $('#zdyqsTxt').val();
                                    if(!parseInt(param.periodCollection)){
                                        layer.alert("自定义期数格式有误！",{icon: 2, title:'操作提示'});
                                        return
                                    }
                                }else {
                                    param.periodCollectionType="0";
                                }
                            }
                            if(!parseInt(param.amountCollection)){
                                layer.alert("收取金额格式有误！",{icon: 2, title:'操作提示'});
                                return
                            }
                            if($("#package_id").val()==""){
                                layer.alert("服务包类型不能为空！",{icon: 2, title:'操作提示'});
                                return
                            }
                            if($("#package_name").val()==""){
                                layer.alert("服务包名称不能为空！",{icon: 2, title:'操作提示'});
                                return
                            }
                            if($("#amount_collection").val()==""){
                                layer.alert("收取金额不能为空！",{icon: 2, title:'操作提示'});
                                return
                            }
                            if($("#state").val()==""){
                                layer.alert("是否启用不能为空！",{icon: 2, title:'操作提示'});
                                return
                            }
                            debugger
                            Comm.ajaxPost('magServicePackage/add',JSON.stringify(param), function (data) {
                                    if(data.code==0){
                                        layer.msg(data.msg,{time:1000},function(){
                                            layer.closeAll();
                                            g_userManage.tableCustomer.ajax.reload();
                                        })
                                    }
                                },"application/json"
                            );
                        }
                    })
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableCustomer.on("order.dt search.dt", function() {
            g_userManage.tableCustomer.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
});




//搜索下拉框
function  showType() {
    debugger
    title : "类型",
    $("#type").val("");
    Comm.ajaxPost('magServicePackage/magList', null, function (result) {
        debugger
        var str = null;
        var resData = result.data;
        for (var i = 0; i < resData.length; i++) {
            str += "<option value=" + resData[i].id + ">" + resData[i].package_type_name + "</option><br>";
        };
        debugger
        $("#type").append(str);
        $("#package_id").append(str);
        }, "application/json"
    );
}


//修改服务包名称
function  editDetail(type,id) {
    if (type=='1'){
    }else {

        layerIndex = layer.open({
            type : 1,
            title : "修改服务包",
            maxmin : true,
            shadeClose : false,
            area : [ '600px', '300px'  ],
            content : $('#editDetail'),
            btn : [ '提交', '取消' ],

            success: function () {
                var param = {};
                param.id=id;
                //$('#id').val(id);
                Comm.ajaxPost('magServicePackage/getMagServicePackage', JSON.stringify(param), function (result) {
                    console.log(result);
                    debugger
                    var resData = result.data;
                    $("#package_id").attr("disabled","disabled");
                    $("#package_id").val(resData.package_id);
                    $("#package_name").val(resData.package_name);
                    $("#month").val(resData.month);
                    $("#amount_collection").val(resData.amount_collection);
                    $("#period_collection").val(resData.period_collection);
                    $("#state").val(resData.state);
                    $("#apex1").attr("checked","checked");
                    //$('input[name="apex1"]').attr("checked",false);

                    if(resData.package_id=="1"){
                        $("#jqhktqhk1f").show();
                        $("#qzsq1f").hide();
                        $("#sqqs1f").hide();
                        $("#zdyqs").hide();
                    }else  if (resData.package_id=="2"){
                        $("#collection_period").val(resData.period_collection_type);
                        if (resData.period_collection_type=='0'){
                            $("#zdyqsTxt").attr("disabled","disabled");
                        }else {
                            $("#zdyqsTxt").removeAttr("disabled");
                            $("#zdyqsTxt").val(resData.period_collection);
                        }
                        $("#jqhktqhk1f").show();
                        $("#qzsq1f").hide();
                        $("#sqqs1f").show();
                        $("#zdyqs").show();
                    }else  if (resData.package_id=="3"){
                        $("#collection_period").val(resData.period_collection_type);
                        if (resData.period_collection_type=='0'){
                            $("#zdyqsTxt").attr("disabled","disabled");
                        }else {
                            $("#zdyqsTxt").removeAttr("disabled");
                            $("#zdyqsTxt").val(resData.period_collection);
                        }
                        if(resData.force_collection=="1"){
                            $("#apex1").attr("checked","checked");
                        }else {
                            $("#apex2").attr("checked","checked");
                        }
                        $("#jqhktqhk1f").hide();
                        $("#qzsq1f").show();
                        $("#sqqs1f").show();
                        $("#zdyqs").show();
                    }

                },"application/json");
            },

            yes:function(index, layero){
                var param={};
                param.id=id;
                debugger
                //param.id = $('#id').val();
                param.packageId = $('#package_id').val();
                param.packageName = $('#package_name').val();
                param.repaymentType = $('#repayment_type').val();
                param.amountCollection = $('#amount_collection').val();
                param.state= $('#state').val();
                var value=$("#package_id").val();
                var valuePeriod=$("#collection_period").val();
                if(value=="1"){
                    param.month = $('#month').val();
                    if(!parseInt(param.month)){
                        layer.alert("几期后可提前还款格式有误！",{icon: 2, title:'操作提示'});
                        return
                    }
                }else  if (value=="2"){
                    if (valuePeriod=='1'){
                        param.periodCollectionType="1";
                        param.periodCollection = $('#zdyqsTxt').val();
                        if(!parseInt(param.periodCollection)){
                            layer.alert("自定义期数格式有误！",{icon: 2, title:'操作提示'});
                            return
                        }
                    }else {
                        param.periodCollectionType="0";
                    }
                    param.month = $('#month').val();
                    if(!parseInt(param.month)){
                        layer.alert("几期后可提前还款格式有误！",{icon: 2, title:'操作提示'});
                        return
                    }
                }else  if (value=="3"){
                    if($("#apex1").attr("checked")) {
                        param.forceCollection="1";
                    }else {
                        param.forceCollection="0";
                    }
                    debugger
                    if (valuePeriod=='1'){
                        param.periodCollectionType="1";
                        param.periodCollection = $('#zdyqsTxt').val();
                        if(!parseInt(param.periodCollection)){
                            layer.alert("自定义期数格式有误！",{icon: 2, title:'操作提示'});
                            return
                        }
                    }else {
                        param.periodCollectionType="0";
                    }
                }
                if(isNaN(param.amountCollection)){
                    layer.alert("收取金额格式有误！",{icon: 2, title:'操作提示'});
                    return
                }
                if($("#package_id").val()==""){
                    layer.alert("服务包类型不能为空！",{icon: 2, title:'操作提示'});
                    return
                }
                if($("#package_name").val()==""){
                    layer.alert("服务包名称不能为空！",{icon: 2, title:'操作提示'});
                    return
                }
                if($("#amount_collection").val()==""){
                    layer.alert("收取金额不能为空！",{icon: 2, title:'操作提示'});
                    return
                }
                if($("#state").val()==""){
                    layer.alert("是否启用不能为空！",{icon: 2, title:'操作提示'});
                    return
                }

                Comm.ajaxPost('magServicePackage/updateMagServicePackage', JSON.stringify(param), function (result) {
                    layer.msg(result.msg,{time:1000},function () {
                        if (result.code=="0"){
                            layer.closeAll();
                            g_userManage.tableCustomer.ajax.reload();
                        }
                    });
                },"application/json");
            }
        })
    }
}
//启用或停止
function updateState(id,state) {
    var param = {
        id: id,
        state: state
    }
    debugger
    Comm.ajaxPost('magServicePackage/updatePackageState', JSON.stringify(param), function (data) {
        debugger
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableCustomer.ajax.reload();
            });
        }, "application/json"
    );
}


function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}
function  packageOnchange() {
    var value=$("#package_id ").val();
    if(value=="1"){
        $("#jqhktqhk1f").show();
        $("#qzsq1f").hide();
        $("#sqqs1f").hide();
        $("#zdyqsTxt").val("");
        $("#zdyqs").hide();
    }else  if (value=="2"){
        $("#jqhktqhk1f").show();
        $("#qzsq1f").hide();
        $("#sqqs1f").show();
        $("#zdyqs").show();
    }else  if (value=="3"){
        $("#jqhktqhk1f").hide();
        $("#month").val();
        $("#qzsq1f").show();
        $("#sqqs1f").show();
        $("#zdyqs").show();
    }

}

function  collection_periodOnchange() {
    var value=$("#collection_period").val();
    if (value=='1'){
        $("#zdyqsTxt").removeAttr("disabled");
    }else {
        $("#zdyqsTxt").attr("disabled","disabled");
        $("#zdyqsTxt").val("");
    }
}

//查询重置
$(function(){
    $("#btn_search_reset").click(function () {
        $("#type").val('');
        $("#pname").val('');
        $("#mgstate").val('');
        g_userManage.tableCustomer.ajax.reload();
    });
})