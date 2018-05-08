var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.userId='1';
        param.state = '8';//人工终审
        /* if(leftStatus=="in"){
         param.receiveId = $("input[name='userId']").val();
         }*/
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();
            var beginTime = $("#beginTime").val();
            if(beginTime != null && beginTime != ''){
                param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
            var endTime = $("#endTime").val();
            if(endTime != null && endTime != ''){
                param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
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
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
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
                Comm.ajaxPost('standard/list', JSON.stringify(queryFilter), function (result) {
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
                {"data": "orderSubmissionTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {"data": "provinces","orderable" : false},
                {"data": "city","orderable" : false},
                {"data": "productTypeName","orderable" : false},
                {"data": "productNameName","orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "card","orderable" : false},
                // {"data": "orderNo","orderable" : false},
                {"data": "tel","orderable" : false},
                // {"data": "periods","orderable" : false,"width":"80px"},
                // {"data": "state","orderable" : false,
                //     "render": function (data, type, row, meta) {
                //         if(data==6){
                //             return "合规审核";
                //         }
                //     }
                // },
                // {"data": "creatTime","orderable" : false,
                //     "render": function (data, type, row, meta) {
                //         return formatTime(data);
                //     }
                // },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {

                var btn=$('<span style="color: #307ecc;" onclick="auditOrder(\''+data.id+'\',\''+data.customerId+'\')">审核</span>');
                return $("td", row).eq(9).append(btn);
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
                    $("input[name='personName']").val("");
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




//时间转换
function formatTime(t){
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}


//获取当前时间
function getFirstTime() {
    var time;
    var newData=new Date();
    var N=newData.getFullYear();
    var s=newData.getMonth()+1;
    var Y=s<=9?"0"+s:s;
    var r=newData.getDate();
    var D=r<=9?"0"+r:r;
    var h=newData.getHours();
    var H=h<=9?"0"+h:h;
    var m=newData.getMinutes();
    var M=m<=9?"0"+m:m;
    var a=newData.getSeconds();
    var S=a<=9?"0"+a:a;
    return time=N+"-"+Y+"-"+D+" "+H+":"+M+":"+S;
}
$().ready(function () {
    var time=getFirstTime();
    $("tbody[id^='recordList'] tr:first-child td:first-child").html(time);
})


//打开查看页面
function auditOrder(orderId,customerId){
    var url = "/standard/details?orderId="+orderId+"&customerId="+customerId;
    debugger
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:url
    });
}
//审核修改状态
function clickNextButton(val) {
    if(val=="12"){
    layerIndex = layer.open({
        type : 1,
        title : "产品信息",
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '500px', '' ],
        content : $('#preQuotaDialog'),
        btn : [ '提交', '取消' ],
        yes:function(index, layero){
            var paramFilter = {};
            var page = {};
            var param = {};
            param.orderId=$("#orderId").val();
            param.state = val;//合规检查通过
            param.taskNodeId = $("#taskNodeId");
            paramFilter.param = param;
            param.userId = $("#userId").val();
            Comm.ajaxPost('standard/updateState', JSON.stringify(paramFilter), function (result) {
                debugger
                layer.msg(result.msg,{time:2000},function () {

                });
                var index = parent.layer.getFrameIndex(window.name);
                parent.g_userManage.tableOrder.ajax.reload();
                parent.layer.close(index);
            },"application/json");
        }
    })
    }else {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.orderId=$("#orderId").val();
        param.state = val;//合规检查通过
        paramFilter.param = param;
        param.taskNodeId = taskNodeId;
        param.userId = $("#userId").val();
        Comm.ajaxPost('standard/updateState', JSON.stringify(paramFilter), function (result) {
            debugger
            layer.msg(result.msg,{time:2000},function () {

            });
        },"application/json");
    }
}