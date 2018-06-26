var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.type = 1;
        param.commodityState="20";//串号码提交
        param.orderType='2';
        param.isLock=$("#isLock").val();//处理状态
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();//客户名称
            param.tel = $("input[name='tel']").val();//手机号码
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
    var table;
        table = [
            {"data": null ,"searchable":false,"orderable" : false},//序号
            {"data": "orderNo","orderable" : false},//订单编号
            {"data": "customerName","orderable" : false},//客户姓名
            {"data": "card","orderable" : false},//身份证号码
            {"data": "tel","orderable" : false},//电话号码
            {"data": "productName","orderable" : false},//产品名称
            {"data": "loanAmount","orderable" : false},//批复金额
            {"data": "periods","orderable" : false},//申请期限
            {"data": "examineTime","orderable" : false ,//审批时间
                "render":function (data, type, row, meta) {
                    if(data !=null && data !=''){
                        return formatTime(data);
                    }else {
                        return '';
                    }
                }},//申请时间
            //订单状态
            {"data": "orderState","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data==4){
                        return "待放款";
                    }else {
                        return "";
                    }
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ]
    var beginTime = {
         elem: '#beginTime',
         format: 'YYYY-MM-DD',
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
        format: 'YYYY-MM-DD',
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
                // debugger
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('finalAudit/orderList', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    // debugger
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
            "columns": table,
            "createdRow": function ( row, data, index,settings,json ) {
                var seeBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderSee(\''+data.orderId+'\',\''+data.customerId+'\')"> 查看 </a>');
                var loanBtn = $('<a class="tabel_btn_style" onclick="confirmationLoan(\''+data.orderId+'\',\''+data.customerId+'\',\''+
                    data.contractAmount+'\',\''+data.surplusContractAmount+'\',\''+data.applayMoney+'\',\''+data.periods+'\')">确认放款 </a>');
                return $("td", row).eq(10).append(loanBtn).append(seeBtn);
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
                    $("#cus_order").hide();
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='customerName']").val("");
                    $("input[name='card']").val("");
                    $("input[name='tel']").val("");
                    $("input[name='orderNo']").val("");
                    $("#beginTime").val("");
                    $("#endTime").val("");
                    $("#isLock").val("");
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
    }
});

//打开查看页面
function orderSee(orderId,customerId){
    var url = "/finalAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}



//收缩目录
function shrink(me){
    $(me).nextAll().slideToggle();
}

//时间去掉空格
function getTime(time) {
    var arr1 = time.split(" ");
    var y = arr1[0].split("-");
    var t = arr1[1].split(":");
    return newTime = y[0] + y[1] + y[2] + t[0] + t[1] + t[2];
}

var g_CustomerOrderManage = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(customerId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.orderId=customerId;
        param.type="0";
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};



function formatTime(time){
    //去掉所有空格
    //var time=t.replace(/\s/g,"");
    if(time!=null){
        time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
            time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
        return time;
    }else{
        return null;
    }

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
//图片预览方法二
function imgShow(me){
    window.open($(me).attr("src"),"图片预览");
}


function  orderDebit(orderId) {
    Comm.ajaxPost('finalAudit/costDebit','orderId='+orderId,function(data){
        layer.msg(data.msg,{time:2000},function(){
            g_userManage.tableOrder.ajax.reload();
        })
    });
}

function  confirmationLoan(orderId, customerId, contractAmount, surplusContractAmount, applayMoney, periods) {
    layer.open({
        type : 1,
        title : '确认放款方式',
        maxmin : true,
        shadeClose : false,
        area : [ '350px', '180px'  ],
        content : $('#checkConfirmationLoanStyle'),
        btn : [ '确认', '取消' ],
        yes:function(index,layero){
            var pattern = /^([1-9]{1})(\d{15}|\d{16}|\d{17}|\d{18})$/;
            var payBackUser = $("#payBackUser").val()//还款用户
            var payBackCard = $("#payBackCard").val()//还款卡号
            if(!payBackUser){
                layer.msg("还款用户不能为空",{time:2000});
                return;
            }
            if(!payBackCard){
                layer.msg("还款卡号不能为空",{time:2000});
                return;
            }
            if(!pattern.test(payBackCard)){
                layer.msg("还款卡号不合法",{time:2000});
                return;
            }
            layer.confirm('确认放款?', function(index) {
                layer.close(index);
                var param={};
                param.id = orderId;
                param.surplusContractAmount = surplusContractAmount;
                param.contractAmount = contractAmount;
                param.customerId = customerId;
                param.periods = periods;
                param.applayMoney = applayMoney;
                param.payBackUser = payBackUser;
                param.payBackCard = payBackCard;
                Comm.ajaxPost('finalAudit/confirmationMerchant',JSON.stringify(param),function(data){
                    layer.msg(data.msg,{time:2000},function(){
                        layer.closeAll();
                        g_userManage.tableOrder.ajax.reload();
                    })
                },"application/json");
            });
        }
    });

}


