//查询订单的对象
var g_salesmanOrders = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.empId='c6891e91-6ecf-403d-8fe8-c20a63249686';//此处将测试办单员的id写死
        param.customerName=$.trim($('#search_name').val());
        param.tel=$.trim($("#search_tel").val());
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/***********订单模块方法***********/
function showSalesmanOrders(){
            if(!g_salesmanOrders.tableCusOrder) {
                g_salesmanOrders.tableCusOrder = $('#sign_list').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "destroy":true,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {   //ajax配置为function,手动调用异步查询
                        var queryFilter = g_salesmanOrders.getQueryCondition(data);
                        Comm.ajaxPost('officeClerk/getOrdersFromSalesman', JSON.stringify(queryFilter), function (result) {
                            console.log(result);
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
                        {"data": "orderNo","orderable" : false},//订单编号
                        {"data": "customerName","orderable" : false},//客户名称
                        {"data": "tel","orderable" : false},//客户电话号码
                        {"data": "card", "orderable": false},//证件号
                        {"data": "creatTime","orderable" : false,"render":function (data, type, row, meta) {
                            return getTime(data);
                        }},//创建时间
                        {"data": "productTypeName","orderable" : false},//秒付产品
                        {"data": "source","orderable" : false,"render":function (data, type, row, meta) {
                            if(data=="1"){
                                return "微信";
                            }
                            else if(data=="2"){
                                return "APP";
                            }else{
                                return '未知';
                            }
                        }},//来源
                        {"data": "state","orderable" : false,"render":function (data, type, row, meta) {
                            if(data=="1"){
                                return "借款申请";
                            }
                            if(data=="2"){
                                return "自动化审批通过";
                            }
                            if(data=="3"){
                                return "自动化审批拒绝";
                            }
                            if(data=="4"){
                                return "自动化审批异常转人工";
                            }
                            if(data=="5"){
                                return "人工审批通过";
                            }
                            if(data=="6"){
                                return "人工审批拒绝";
                            }
                            if(data=="7"){
                                return "合同确认";
                            }
                            if(data=="8"){
                                return "放款成功";
                            }
                            if(data=="9"){
                                return "结清";
                            }
                            if(data=="10"){
                                return "关闭";
                            }
                        }},
                        {"data": "null", "orderable": false, "defaultContent":""}//操作
                    ],
                    "createdRow": function ( row, data, index,settings,json ) {

                        var btn_xiangxixinxi=$('<a class="tabel_btn_style" onclick="showOrder(\''+data.id+'\',\''+data.customerId+'\')"> 详细信息 </a>');
                        $("td", row).eq(9).append(btn_xiangxixinxi);


                    },
                    "initComplete" : function(settings,json) {
                        //绑定函数在此处
                        // //查询按钮触发
                        $("#btn_search").click(function(){
                            g_salesmanOrders.tableCusOrder.ajax.reload();
                        });
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_salesmanOrders.tableCusOrder.on("order.dt search.dt", function() {
                    g_salesmanOrders.tableCusOrder.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
                
            }else {
                g_salesmanOrders.tableCusOrder.ajax.reload();
            }
}
//页面加载完成时 调用的函数
$(function(){
    showSalesmanOrders();
});
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
function  searchReset() {
    $("#search_name").val('');
    $("#search_tel").val('');
    g_salesmanOrders.tableCusOrder.ajax.reload();

}

//订单详细信息
function showOrder(orderId,customerId){
    var url = "/finalAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}