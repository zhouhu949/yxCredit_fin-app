var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.orderType='2'; //商品订单状态
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();
            param.condition=$('#isLock').val(); //获取订单状态
            var beginTime = $("#beginTime").val();
            if(beginTime != null && beginTime != ''){
                param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
            var endTime = $("#endTime").val();
            if(endTime != null && endTime != ''){
                param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
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
    debugger;
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: '2099-06-16 23:59:59',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(beginTime);
    laydate(endTime);
    debugger;
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
                Comm.ajaxPost('customerAudit/getSubmitList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "orderNo","orderable" : false},
                {"data": "merchantName","orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return  formatTime(data);
                    }
                },
                // { "data": "orderSubmissionTime","orderable" : false,
                //     "render": function (data, type, row, meta) {
                //         return  formatTime(data);
                //     }
                // },
                // {"data": "provinces","orderable" : false},
                // {"data": "city","orderable" : false},
                {"data": "productTypeName","orderable" : false},
                {"data": "productNameName","orderable" : false},
                {"data": null, "searchable": false, "orderable": false,
                    "render": function (data, type, row, meta) {
                        return data.merchandiseBrand+data.merchandiseModel;
                    }
                },
                {"data": "amount","orderable" : false},
                {"data": "predictPrice","orderable" : false},
                {"data": "applayMoney","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return data ? parseFloat(data).toFixed(2) : "";
                    }},
                {"data": "periods","orderable" : false},
                {"data": "state","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data.toString()=="0"){
                            return "未提交";
                        }else if(data.toString()=="1"){
                            return "借款申请";
                        }else if(data.toString()=="2"){
                            return "自动化审批通过";
                        }else if(data.toString()=="3"){
                            return "自动化审批拒绝";
                        }else if(data.toString()=="4"){
                            return "自动化审批异常";
                        }else if(data.toString()=="5"){
                            return "人工审批通过";
                        }else if(data.toString()=="6"){
                            return "人工审批拒绝";
                        }else if(data.toString()=="7"){
                            return "合同确认";
                        }else if(data.toString()=="8"){
                            return "放款成功";
                        }else if(data.toString()=="9"){
                            return "结清";
                        }else if(data.toString()=="10"){
                            return "关闭";
                        }else if(data.toString()=="-1"){
                            return "待确认合同";
                        }else if(data.toString()=="-2"){
                            return "合同确认";
                        }else if(data.toString()=="-3"){
                            return "待付预付款";
                        }else if(data.toString()=="-4"){
                            return "待发货";
                        }else if(data.toString()=="-5"){
                            return "已发货";
                        }else if(data.toString()=="-6"){
                            return "还款中";
                        }else if(data.toString()=="-7"){
                            return "退回";
                        }
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var btnDel = $('<a class="tabel_btn_style" onclick="orderDetail(\''+data.id+'\',\''+data.customerId+'\',\''+data.state+'\',\''+data.antifraudState+'\',\''+data.tache+'\')">查看</a>');
                $('td', row).eq(15).append(btnDel);
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
//打开查看页面
function orderDetail(orderId,customerId){
    var url = "/finalAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}

//动态加载下拉框内容
function apendSelect() {
    Comm.ajaxPost('customerAudit/condition', null, function (data) {
            if (data.code == 0) {
                var map = [];
                map = data.data;
                for (var i = 0; i < map.length; i++) {
                    var btndel = '<option value="' + map[i].code + '">' + map[i].name + '</option>';
                    if(map[i].code!=1){
                        $('#isLock').append(btndel);
                    }

                }
            }
        }, "application/json"
    );
}

//查询该订单下的审核人员上传的图片信息
// Comm.ajaxPost("customerAudit/imgDetails",orderData.id,function(result){
//     var data = result.data;
//     showOrderImgList(data.imageList);
// }, "application/json");