var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.orderType='1'; //现金订单状态
        param.deliveryState='3'; //发货状态
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();
            var beginTime = $("#beginTime").val();
            // if(beginTime != null && beginTime != ''){
            //     param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            // }
            // var endTime = $("#endTime").val();
            // if(endTime != null && endTime != ''){
            //     param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            // }
        }
        param.state = '1';
        param.type = '2';

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
    //     max: '2099-06-16 23:59:59',
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
    //apendSelect();
    if(g_userManage.tableOrder){
        g_userManage.tableOrder.ajax.reload();
    }else{
        g_userManage.tableOrder = $('#order_list').dataTable($.extend({
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
                debugger
                Comm.ajaxPost('goldCustomerAudit/getGoldLogisticsList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "id","orderable" : false},
                {"data": "customer_name","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "product_type_name","orderable" : false},
                {"data": "product_name_name","orderable" : false},
                {"data": "amount","orderable" : false},
                {"data": "price","orderable" : false},
                {"data": "weight","orderable" : false},
                {"data": "periods","orderable" : false},
                {"data": "delivery_state","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data=="1"){
                            return "待发货";
                        }else if(data=="2"){
                            return "已发货";
                        }else if(data=="3"){
                            return "已确认";
                        }
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var btnDel = $('<a class="tabel_btn_style" onclick="addLogistics(\''+data.id+'\')">发货</a>');
                if (data.delivery_state=="2"){
                    btnDel = $('<a class="tabel_btn_style" onclick="updateLogistics(\''+data.id+'\')">编辑</a>');
                }
                if (data.delivery_state=="3"){
                    btnDel = $('<a class="tabel_btn_style" onclick="seeLogistics(\''+data.id+'\')">查看</a>');
                }
                $('td', row).eq(12).append(btnDel);
            },
            "initComplete" : function(settings,json) {
                //全选
                $("#checkBox_All").click(function(J) {
                    if (!$(this).prop("checked")) {
                        $("#order_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                    } else {
                        $("#order_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                    }
                });
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='customerName']").val("");
                    $('#isLock').val("");
                    $("#beginTime").val("");
                    $("#endTime").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
                });
                //只看自己
                $("#onlyMe").click(function() {
                    if($("#onlyMe").attr("checked")){
                        g_userManage.fuzzySearch = true;
                    }else{
                        g_userManage.fuzzySearch = false;
                    }
                    g_userManage.tableOrder.ajax.reload();
                });
                //单选行
                $("#order_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        if(isChecked=='false'){
                            if($(input).attr('checked')){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','true');
                        }else{
                            if($(input).attr('checked')){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','false');
                        }
                    }
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
//发货详情
function  addLogistics(orderId) {
    layer.open({
        type : 1,
        title : '发货详情',
        shadeClose : false,
        area : ['740px','330px'],
        content : $('#logisticsDiv'),
        btn : [ '提交', '取消' ],
        success:function (index, layero) {
            Comm.ajaxPost('goldCustomerAudit/getlogistics', orderId, function (result) {
                var returnData = {};
                var resData = result.data;
                $("#lblReceiveAddress").html(resData[0].receive_address);
                $("#lblPersonName").html(resData[0].customer_name);
                $("#lblTel").html(resData[0].tel);
                $("#purchasePrice").val("");
                $("#billNo").val("");
                $("#company").val("-1");
                $("#company").removeAttr("disabled");
                $("#billNo").removeAttr("disabled");
                $("#purchasePrice").removeAttr("disabled");
            },"application/json");
        },
        yes : function(index, layero) {
            var param = {};
            param.orderId = orderId;
            param.companyName=$("#company").find("option:selected").text();
            param.companyId=$("#company").val();
            param.billNo=$("#billNo").val();
            param.purchasePrice=$("#purchasePrice").val();
            param.deliveryState='2';
            if(isNaN(param.purchasePrice)){
                layer.msg("采购价格格式有误！",{time:1000});
                return;
            }
            if(param.companyId=="-1"){
                layer.msg("请选择物流公司！",{time:1000});
                return;
            }

            if(param.billNo==""){
                layer.msg("请填写物流单号！",{time:1000});
                return;
            }
            Comm.ajaxPost('goldCustomerAudit/addLogistics',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:2000},function(){
                    layer.closeAll();
                })
            }, "application/json");

        }
    });
}

//编辑发货详情
function  updateLogistics(orderId) {
    var id='';
    layer.open({
        type : 1,
        title : '编辑发货详情',
        shadeClose : false,
        area : ['740px','330px'],
        content : $('#logisticsDiv'),
        btn : [ '提交', '取消' ],
        success:function (index, layero) {
            Comm.ajaxPost('goldCustomerAudit/getlogistics', orderId, function (result) {
                debugger
                var returnData = {};
                var resData = result.data;
                $("#lblReceiveAddress").html(resData[0].receive_address);
                $("#lblPersonName").html(resData[0].customer_name);
                $("#lblTel").html(resData[0].tel);
                $("#purchasePrice").val(resData[0].purchase_price);
                $("#billNo").val(resData[0].bill_no);
                $("#company").val(resData[0].company_id);
                $("#company").removeAttr("disabled");
                $("#billNo").removeAttr("disabled");
                $("#purchasePrice").removeAttr("disabled");
                id=resData[0].id
            },"application/json");
        },
        yes : function(index, layero) {
            debugger
            var param = {};
            param.orderId = orderId;
            param.companyName=$("#company").find("option:selected").text();
            param.companyId=$("#company").val();
            param.billNo=$("#billNo").val();
            param.purchasePrice=$("#purchasePrice").val();
            param.deliveryState='2';
            param.id=id;
            if(isNaN(param.purchasePrice)){
                layer.msg("采购价格格式有误！",{time:1000});
                return;
            }
            if(param.companyId=="-1"){
                layer.msg("请选择物流公司！",{time:1000});
                return;
            }

            if(param.billNo==""){
                layer.msg("请填写物流单号！",{time:1000});
                return;
            }
            Comm.ajaxPost('goldCustomerAudit/updateLogistics',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:2000},function(){
                    layer.closeAll();
                })
            }, "application/json");

        }
    });
}
//格式时间
function getFirstTime(inputTime) {
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

//查看发货详情
function  seeLogistics(orderId) {
    var id='';
    layer.open({
        type : 1,
        title : '查看发货详情',
        shadeClose : false,
        area : ['740px','330px'],
        content : $('#logisticsDiv'),
        success:function (index, layero) {
            Comm.ajaxPost('goldCustomerAudit/getlogistics', orderId, function (result) {
                debugger
                var returnData = {};
                var resData = result.data;
                $("#lblReceiveAddress").html(resData[0].receive_address);
                $("#lblPersonName").html(resData[0].customer_name);
                $("#lblTel").html(resData[0].tel);
                $("#purchasePrice").val(resData[0].purchase_price);
                $("#billNo").val(resData[0].bill_no);
                $("#company").val(resData[0].company_id);
                id=resData[0].id
                $("#company").attr("disabled","disabled");
                $("#billNo").attr("disabled","disabled");
                $("#purchasePrice").attr("disabled","disabled");
            },"application/json");
        }
    });
}
//格式时间
function getFirstTime(inputTime) {
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
//动态加载下拉框内容
function apendSelect() {
    Comm.ajaxPost('goldCustomerAudit/apendSelected', null, function (data) {
            if (data.code == 0) {
                var map = [];
                map = data.data;
                for (var i = 0; i < map.length; i++) {
                    var btndel = '<option value="' + map[i].code + '">' + map[i].name + '</option>';
                    if(map[i].code!=1){
                        $('#company').append(btndel);
                    }

                }
            }
        }, "application/json"
    );
}
