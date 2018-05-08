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
        param.orderType='1';
        param.withdrawalState="0";
        if (g_userManage.fuzzySearch) {
            if ($("#isLock").val()!='-1'){
                param.withdrawalState=$("#isLock").val();
            }
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
            {"data": null ,"searchable":false,"orderable" : false},
            {
                "className" : "childBox",
                'class':'hidden',
                "orderable" : false,
                "data" : null,
                "width" : "20px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input id="'+data.id+'" type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "id","orderable" : false},
            {"data": "customerName","orderable" : false},
            {"data": "card","orderable" : false},
            {"data": "tel","orderable" : false},
            {"data": "creatTime","orderable" : false,
                "render": function (data, type, row, meta) {
                    return  formatTime(data);
                }
            },
            {"data": "productTypeName","orderable" : false},
            {"data": "productNameName","orderable" : false},
            {"data": "amount","orderable" : false},
            {"data": "periods","orderable" : false},
        /*    {"data": "state","orderable" : false},*/
            {"data": "merName","orderable" : false},
            {"data": "reclaimAmount","orderable" : false},
            {"data": "rebatesState","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data==0) return "<span style='text-decoration: none;color:#307ecc;'> 未申请</span>";
                    if(data==1) return "<span style='text-decoration: none;color:#307ecc;'> 已申请</span>";
                    if(data==2) return "<span style='text-decoration: none;color:#307ecc;'> 审核通过</span>";
                }
            },
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
                Comm.ajaxPost('goldCustomerAudit/orderList', JSON.stringify(queryFilter), function (result) {
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
                var loanBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderLoan(\''+data.id+'\')"> 放款明细</a>');
                var seeBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderSee(\''+data.id+'\',\''+data.customerId+'\')"> 查看</a>');
                $("td", row).eq(15).append(seeBtn).append(loanBtn);
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

//打开查看页面
function orderSee(orderId,customerId){
    var url = "/goldCustomerAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
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
        //param.isoverdue="1";
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//客户放款记录
function orderLoan(customerId){
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : '放款明细',
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
            var queryFilter = g_CustomerOrderManage.getQueryCondition(customerId,data);
            debugger
            Comm.ajaxPost('finalAudit/withdrawalList', JSON.stringify(queryFilter), function (result) {
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
            {"data": "createTime","orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data": "merName","orderable" : false},
            {"data": "withdrawalAmount","orderable" : false},
            {"data": "bankCard","orderable" : false},
            {"data": "accountBank","orderable" : false},
            {"data": "type","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data==0) return "红包放款";
                    else return "借款金额放款";
                }
            },
            {"data": "state","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data==0) return "待放款";
                    else if (data==1) return "已放款";
                    else if (data==2) return "放款中";
                    else if (data==3) return "放款失败";
                    else return "";
                }
            },
            // {"data": "alterTime","orderable" : false,
            //     "render": function (data, type, row, meta) {
            //         if(data==""){
            //             return "";
            //         }else {
            //             return formatTime(data);
            //         }
            //     }
            // },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var loanBtn = $('<a class="tabel_btn_style_disabled" disable="ture">确认放款&nbsp;</a>');
            if (data.state=="0"||data.state=="3"){
                loanBtn= $('<a class="tabel_btn_style" onclick="confirmationLoan(\''+data.id+'\',\''+data.orderId+'\')">确认放款&nbsp;</a>');
            }
            $('td', row).eq(8).append(loanBtn);
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


function  confirmationLoan(withdrawalsId,orderId) {
    debugger
    Comm.ajaxPost('finalAudit/confirmationLoan','withdrawalsId='+withdrawalsId,function(data){
        layer.msg(data.msg,{time:2000},function(){
            layer.closeAll();
        })
    });
}


