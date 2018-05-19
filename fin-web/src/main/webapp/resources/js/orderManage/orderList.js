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
                {"data": null ,"searchable":false,"orderable" : false},//序号
                {"data": "orderNo","orderable" : false},//订单编号
                {"data": "customerName","orderable" : false},//客户姓名
                {"data": "card","orderable" : false},//身份证号码
                {"data": "tel","orderable" : false},//电话号码
                {"data": "productName","orderable" : false},//产品名称
                {"data": "applayMoney","orderable" : false},//申请金额
                {"data": "periods","orderable" : false},//申请期限
                {"data": "applayTime","orderable" : false },//申请时间
                //订单状态
                {"data": "orderState","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data==1){
                            return "待申请";
                        }else if(data==2){
                            return "审核中";
                        }else if(data==3){
                            return "待签约";
                        }else if(data==4){
                            return "待放款";
                        }else if(data==5){
                            return "待还款";
                        }else if(data==6){
                            return "已结清";
                        }else if(data==7){
                            return "已结清";
                        }else if(data==8){
                            return "申请失败";
                        }else if(data==9){
                            return "已放弃";
                        }else {
                            return "未知状态";
                        }
                    }
                },
                // { "data": "orderSubmissionTime","orderable" : false,
                //     "render": function (data, type, row, meta) {
                //         return  formatTime(data);
                //     }
                // },
                // {"data": "provinces","orderable" : false},
                // {"data": "city","orderable" : false},

             /*   {"data": null, "searchable": false, "orderable": false,
                    "render": function (data, type, row, meta) {
                        return data.merchandiseBrand+data.merchandiseModel;
                    }
                },*/

               /* {"data": "applayTime","orderable" : false},*/
               /* {"data": "applayMoney","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return data ? parseFloat(data).toFixed(2) : "";
                    }},
                {"data": "periods","orderable" : false},*/


                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var btnDel = $('<a class="tabel_btn_style" onclick="orderDetail(\''+data.orderId+'\',\''+data.customerId+'\')">查看订单</a>');
                $('td', row).eq(10).append(btnDel)
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
    alert(customerId);
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