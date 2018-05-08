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
            {"data": "orderNo","orderable" : false},
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
            {"data": "merchantName","orderable" : false},
            {"data": "amount","orderable" : false},
            {"data": "predictPrice","orderable" : false},
            {"data": "applayMoney","orderable" : false},
            {"data": "periods","orderable" : false},
        /*    {"data": "state","orderable" : false},*/
            {"data": "loanState","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data==0) return "<span style='text-decoration: none;color:red;'> 待放款</span>";
                    if(data==1) return "<span style='text-decoration: none;color:#307ecc;'> 待放款</span>";
                    if(data==2) return "<span style='text-decoration: none;color:#307ecc;'> 放款处理中</span>";
                    if(data==3) return "<span style='text-decoration: none;color:#307ecc;'> 放款成功</span>";
                    if(data==4) return "<span style='text-decoration: none;color:red;'> 放款失败</span>";
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
                    }else if(data.toString()=="-76"){
                        return "退回";
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
                    console.log(returnData);
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": table,
            "createdRow": function ( row, data, index,settings,json ) {
                //var debitBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderDebit(\''+data.id+'\')">发起扣款 </a>');
                var loanDetailedBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderLoan(\''+data.id+'\')">放款明细</a>');
                var seeBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderSee(\''+data.id+'\',\''+data.customerId+'\')"> 查看 </a>');
                var loanBtn = $('<a class="tabel_btn_style" onclick="confirmationLoan(\''+data.id+'\')">确认放款 </a>');
                if((data.loanState=="1")||(data.loanState=="4")){
                    return $("td", row).eq(16).append(loanBtn).append(loanDetailedBtn).append(seeBtn);
                }else {
                    return $("td", row).eq(16).append(loanDetailedBtn).append(seeBtn);
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
function showCumIng(imgList,host) {
    var html="";
    var html1="";
    var html2="";
    for(var i=0;i<imgList.length;i++){
        if(imgList[i].type== "1"){//身份证
            var time=getFirstTime(imgList[i].creatTime);
            if(imgList[i].isfront=="0"){
                html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>身份证正面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }else{
                html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>身份证反面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "2"){//手持身份证明
            var time=getFirstTime(imgList[i].creatTime);
            if(imgList[i].isfront=="0") {
                html1 += '<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="' + host + imgList[i].src + '" class="imgShow" onclick="imgShow(this)"></div><p>手持身份证明正面</p><p class="hideTime" style="margin:1em;">' + time + '</p></li>';
            }else{
                html1+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>手持身份证明反面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "3"){//工作证明
            var time=getFirstTime(imgList[i].creatTime);
            html2+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>银行卡</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
    }
    $("#showNewImg ul").empty();
    $("#showNewImg ul").append(html);
    $("#showNewImg1 ul").empty();
    $("#showNewImg1 ul").append(html1);
    $("#showNewImg2 ul").empty();
    $("#showNewImg2 ul").append(html2);
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
            // debugger
            Comm.ajaxPost('finalAudit/transactionDetailsList', JSON.stringify(queryFilter), function (result) {
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
                    return formatTime(data);
                }
            },
            {"data": "batchNo","orderable" : false},
            {"data": "bankName","orderable" : false},
            {"data": "bankCard","orderable" : false},
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
            {"data": "fangkuanType","orderable" : false,
                //此字段只用于判断商品贷放款类型：0-线下放款  1-线上放款
                "render": function (data, type, row, meta) {
                    if(data==0) return "线下放款";
                    else if (data==1) return "线上放款";
                    else return "线下放款";
                }
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            // var loanBtn = $('<a class="tabel_btn_style_disabled" disable="ture">确认放款&nbsp;</a>');
            // if (data.state=="0"||data.state=="3"){
            //     loanBtn= $('<a class="tabel_btn_style" onclick="confirmationLoan(\''+data.id+'\',\''+data.orderId+'\')">确认放款&nbsp;</a>');
            // }
            // $('td', row).eq(6).append(loanBtn);
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


function  orderDebit(orderId) {
    Comm.ajaxPost('finalAudit/costDebit','orderId='+orderId,function(data){
        layer.msg(data.msg,{time:2000},function(){
            g_userManage.tableOrder.ajax.reload();
        })
    });
}

function  confirmationLoan(orderId) {
    //弹出选择放款方式弹框checkConfirmationLoanStyle
        layer.open({
            type : 1,
            title : '确认放款方式',
            maxmin : true,
            shadeClose : false,
            area : [ '350px', '180px'  ],
            content : $('#checkConfirmationLoanStyle'),
            btn : [ '确认', '取消' ],
            success : function(index, layero) {
                // var param = {};
                // param.id = id;
                // //先查询并展示出来信息以供修改
                // Comm.ajaxPost('officeClerk/getOneManagerById',JSON.stringify(param), function (data) {
                //
                //     },"application/json"
                // );
            },
            yes:function(index,layero){
                var param={};
                param.orderId=orderId;
                param.fangkuanStyle=fangkuanStyle=$("#fangkuanStyle").val();
                if(param.fangkuanStyle == '' || param.fangkuanStyle == null ){
                    layer.msg("选择不能为空！",{time:2000});return
                }
                Comm.ajaxPost('finalAudit/confirmationMerchant',JSON.stringify(param),function(data){
                    layer.msg(data.msg,{time:2000},function(){
                        layer.closeAll();
                        g_userManage.tableOrder.ajax.reload();
                    })
                },"application/json");
            }
        });
    // debugger
    // Comm.ajaxPost('finalAudit/confirmationMerchant','orderId='+orderId,function(data){
    //     layer.msg(data.msg,{time:2000},function(){
    //         layer.closeAll();
    //         g_userManage.tableOrder.ajax.reload();
    //     })
    // });
}


