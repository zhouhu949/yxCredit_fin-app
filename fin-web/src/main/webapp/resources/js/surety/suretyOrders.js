//查询所有被担保且被审核之后的订单展示页面
//获取所有被担保且审核之后(包括通过和未通过)的订单
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
        param.customerName=$.trim($("#search_name").val()); //客户姓名
        param.tel=$.trim($("#search_tel").val());   //客户手机号
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//所有被担保且审核过的(不论审核结果)订单展示
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
            Comm.ajaxPost('suretyCheckController/getSuretyAndCheckOrdersList', JSON.stringify(queryFilter), function(result) {
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
            {"data":"tel","orderable" : false},
            {"data": "amount","orderable" : false},//商品金额
            // {"data":"predictPrice","orderable" : false},
            {"data": "applyFenQiLimit", "orderable": false},//申请分期额度  (商品金额-首付金额)
            {"data": "periods", "orderable": false},//期数
            {"data": "creatTime", "orderable": false},//申请时间
            {"data": "productTypeName", "orderable": false},//秒付产品
            {"data": "checkResult", "orderable": false,"render":function (data, type, row, meta) {
                if(data=="0"){
                    return "审核拒绝";
                }else if(data=="1"){
                    return "审核通过";
                }
            }},//审批状态
            {"data": "", "orderable": false,"defaultContent":""}
        ],"createdRow": function ( row, data, index,settings,json ) {
            var btn_caozuo=$('<a style="margin-right: 20px;" class="tabel_btn_style" onclick="showDetal' +
                '(\''+data.id+'\',\''+data.customerId+'\',\''+data.relId+'\',\''+data.checkManName+'\',\''+data.checkManId+'\',\''+data.checkResult+'\',\''+data.checkId+'\')">详情</a>');
            $("td", row).eq(10).append(btn_caozuo);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
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
// 打开查看页面
function showDetal(orderId,customerId,relId,checkManName,checkManId,checkResult,checkId){
    debugger
    var url = "/suretyCheckController/suretyCheckOrderDetailShow?orderId="+orderId+"&customerId="+customerId+"&relId="+relId+
        "&checkManName="+checkManName+"&checkManId="+checkManId+"&checkResult="+checkResult+"&checkId="+checkId;
    layer.open({
        type : 2,
        title : '查看审核后的订单',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}
//查询重置
$(function(){
    $("#btn_search_reset").click(function () {
        $("#search_name").val('');
        $("#search_tel").val('');
        g_userManage.tableUser.ajax.reload();
    })
})