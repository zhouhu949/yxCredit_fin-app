var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.type = 1;
        param.state = '2';//待人工审核 3
        param.orderType = '1';//黄金订单 3
        if(leftStatus=="in"){
            param.receiveId = $("input[name='userId']").val();//f907fd9c-f2e7-49d7-8323-f18230766317
        }
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();//客户名称
            param.condition=$('#isLock').val(); //获取订单状态
           /* param.card = $("input[name='card']").val();//身份证号
            param.tel = $("input[name='tel']").val();//手机号码*/
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
    alert();
    var table;
        table = [
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
                    if(data==2){
                        return "待审核";
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
                Comm.ajaxPost('goldCustomerAudit/examineList', JSON.stringify(queryFilter), function (result) {
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
                var btn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="auditOrder(\''+data.id+'\',\''+data.customerId+'\')">审核 </a>');
                return $("td", row).eq(11).append(btn);
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
                    $("input[name='card']").val("");
                    $("input[name='tel']").val("");
                    $("input[name='orderNo']").val("");
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
//发起自动审核
function  automation(orderId) {
    Comm.ajaxPost('goldCustomerAudit/automation',orderId,function(data){
        layer.msg(data.msg,{time:1000},function () {
            g_userManage.tableOrder.ajax.reload();
            layer.close(setUpLayer);
        });
    }, "application/json");
}

//打开查看页面
function auditOrder(orderId,customerId){
    var url = "/goldCustomerAudit/details?orderId="+orderId+"&customerId="+customerId;
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
function shrinkLoad(me){
    $(me).next().next().slideToggle();
}

//时间去掉空格
function getTime(time) {
    var arr1 = time.split(" ");
    var y = arr1[0].split("-");
    var t = arr1[1].split(":");
    return newTime = y[0] + y[1] + y[2] + t[0] + t[1] + t[2];
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



