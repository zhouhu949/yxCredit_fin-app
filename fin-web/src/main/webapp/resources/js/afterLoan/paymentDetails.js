var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.type = 1;
        param.state = '7';//待人工审核 3
        param.withdrawState="0";//提现操作
        param.orderType='1';
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();//客户名称
            param.tel = $("input[name='tel']").val();//手机号码
            // var beginTime = $("#beginTime").val();
            // if(beginTime != null && beginTime != ''){
            //     param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            // }
            // var endTime = $("#endTime").val();
            // if(endTime != null && endTime != ''){
            //     param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            // }
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
        {"data": null ,"searchable":false,"orderable" : false},
        {"data": "order_no","orderable" : false},
        {"data": "customer_name","orderable" : false},
        {"data": "tel","orderable" : false},
        {"data": "pay_time","orderable" : false,
            "render": function (data, type, row, meta) {
                return  formatTime(data);
            }
        },
        {"data": "periods","orderable" : false},
        {"data": "pay_count","orderable" : false},
        {"data": "repayment_amount","orderable" : false},
        //'状态0无效,1未还，2已还，3逾期 4.还款确认中',
        {"data": "state","orderable" : false,
            "render": function (data, type, row, meta) {
                if(data==1) return "待还";
                if(data==2) return "已还";
                if(data==3) return "逾期";
                if(data==4) return "还款确认中";
            }
        },
        {"data": "mlSettleState","orderable" : false,
            "render": function (data, type, row, meta) {
                if(data=='') return "未提前结清";
                if(data==0) return "未提前结清";
                if(data==1) return "提前结清中";
                if(data==2) return "已提前结清";
            }
        },
        {"data": "paymentState","orderable" : false,
            "render": function (data, type, row, meta) {
                if(data=='') return "<span style='text-decoration: none;color:#307ecc;'> 未扣款</span>";
                if(data==0) return "<span style='text-decoration: none;color:#307ecc;'> 扣款中</span>";
                if(data==1) return "<span style='text-decoration: none;color:#307ecc;'> 扣款成功</span>";
                if(data==2) return "<span style='text-decoration: none;color:red;'> 扣款失败</span>";
            }
        },
        {
            "data": "null", "orderable": false,
            "defaultContent":""
        }
    ]
    // var beginTime = {
    //     elem: '#beginTime',
    //     format: 'YYYY-MM-DD hh:mm:ss',
    //     min: '1999-01-01 00:00:00',
    //     max: laydate.now(),
    //     istime: true,
    //     istoday: false,
    //     choose: function(datas){
    //         endTime.min = datas; //开始日选好后，重置结束日的最小日期
    //         endTime.start = datas //将结束日的初始值设定为开始日
    //     }
    // };
    //
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
                debugger
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('SPPayment/paymentList', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    debugger
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    console.log(returnData);
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": table,
            "createdRow": function ( row, data, index,settings,json ) {
                var debitBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderDebit(\''+data.repayment_id+'\')">发起扣款 </a>');
                var loanBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderLoan(\''+data.repayment_id+'\')"> 扣款明细</a>');
                if((data.paymentState=="2")&&((data.mlSettleState=="")||(data.mlSettleState=="0"))){
                    return $("td", row).eq(11).append(debitBtn).append(loanBtn);
                }else {
                    return $("td", row).eq(11).append(loanBtn);
                }
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
    getQueryCondition : function(repaymentId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.repaymentId=repaymentId;
        param.type="2";
        //param.isoverdue="1";
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//客户放款记录
function orderLoan(repaymentId){
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : '扣款明细',
        shadeClose : false,
        area : [ '80%', '60%' ],
        content : $("#cus_order"),
        success : function(index, layero) {
            g_CustomerOrderManage.tableCusOrder = $('#withdrawal_list').dataTable($.extend({
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
                    var queryFilter = g_CustomerOrderManage.getQueryCondition(repaymentId,data);
                    debugger
                    Comm.ajaxPost('SPPayment/transactionDetailsList', JSON.stringify(queryFilter), function (result) {
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
                    {"data": "creatTime","orderable" : false,
                        "render": function (data, type, row, meta) {
                            return formatTime1(data);
                        }
                    },
                    {"data": "batchNo","orderable" : false},
                    {"data": "amount","orderable" : false},
                    {"data": "baofuCode","orderable" : false},
                    // {"data": "type","orderable" : false,
                    //     "render": function (data, type, row, meta) {
                    //         if(data==0) return "红包放款";
                    //         else return "借款金额放款";
                    //     }
                    // },
                    {"data": "state","orderable" : false,
                        //0待查询；1交易成功，2交易失败,4交易异常
                        "render": function (data, type, row, meta) {
                            if(data==0) return "待查询";
                            else if (data==1) return "交易成功";
                            else if (data==2) return "交易失败";
                            else if (data==4) return "交易异常";
                            else return "";
                        }
                    },
                    {"data": "baofuMag","orderable" : false}
                    // {"data": "alterTime","orderable" : false,
                    //     "render": function (data, type, row, meta) {
                    //         if(data==""){
                    //             return "";
                    //         }else {
                    //             return formatTime(data);
                    //         }
                    //     }
                    // },
                    // {
                    //     "data": "null", "orderable": false,
                    //     "defaultContent":""
                    // }
                ],
                "createdRow": function ( row, data, index,settings,json ) {
                },
                "initComplete" : function(settings,json) {
                    $("#customer_list").delegate( 'tbody tr','click',function(e){
                        $("#cus_order").show();
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
            }).draw();}
    })
}



function formatTime(time){
    //去掉所有空格
    //var time=t.replace(/\s/g,"");
    if(time!=null){
        time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);//+" "+ time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
        return time;
    }else{
        return null;
    }

}
function formatTime1(time){
    //去掉所有空格
    //var time=t.replace(/\s/g,"");
    if(time!=null){
        time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+ time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
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


function  orderDebit(repaymentId) {
    Comm.ajaxPost('SPPayment/costDebit','repaymentId='+repaymentId,function(data){
        layer.msg(data.msg,{time:2000},function(){
            g_userManage.tableOrder.ajax.reload();
        })
    });
}



