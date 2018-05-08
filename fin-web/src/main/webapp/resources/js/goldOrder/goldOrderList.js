var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.orderType='1'; //现金订单状态
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
    debugger
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
                debugger
                Comm.ajaxPost('goldCustomerAudit/getSubmitList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "id","orderable" : false},
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
                {"data": "amount","orderable" : false},
                {"data": "price","orderable" : false},
                {"data": "weight","orderable" : false},
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
                $('td', row).eq(13).append(btnDel);
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
    var url = "/goldCustomerAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}
// 展示客户图片
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
//展示审核人员上传的图片
function showOrderImgList(imgList){
    var html39 = "";
    var html40 ="";
    var FanQizha="";
    var PaiKe="";
    for(var i=0;i<imgList.length;i++) {
        if (imgList[i].type == "39") {//审核人员上传资料
            //var src = _ctx + imgList[i].src;
            /* html39 += '<div class="getFanQiZha businessPic">';
             html39 += '<input type="hidden"  class="imgHidden" name="picid" value="' + imgList[i].id + '">';
             html39 += '<form action="" enctype="multipart/form-data">';
             html39 += '<input type="hidden" value="' + merchantId + '">';
             html39 += '<input type="hidden" name="id" value="">';
             html39 += '<input type="hidden" name="type" value="39">';
             html39 += '<input type="hidden" name="businessType" value="1">';
             html39 += '<div class="imagediv">';
             html39 += '<img class="addMaterial" onclick="imgShow(this)" src="' + src + '">';
             html39 += '</form>';
             html39 += '</div>';*/
            var time=getFirstTime(imgList[i].creatTime);
            html39+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>初审审核人员资料</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
        if(imgList[i].type== "40"){//审核人员上传资料
            /* var src=_ctx+imgList[i].src;
             html40+='<div class="getFanQiZha businessPic">';
             html40+='<input type="hidden"  class="imgHidden" name="picid" value="'+imgList[i].id+'">';
             html40+='<form action="" enctype="multipart/form-data">';
             html40+='<input type="hidden" value="'+merchantId+'">';
             html40+='<input type="hidden" name="id" value="">';
             html40+='<input type="hidden" name="type" value="40">';
             html40+='<input type="hidden" name="businessType" value="1">';
             html40+='<div class="imagediv">';
             html40+='<img class="addMaterial" onclick="imgShow(this)" src="'+src+'">';
             html40+='</form>';
             html40+='</div>';*/
            var time=getFirstTime(imgList[i].creatTime);
            html40+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>终审审核人员资料</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
        if(imgList[i].type== "8"){//拍客资料
            var time=getFirstTime(imgList[i].creatTime);
            PaiKe+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height: 100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>拍客资料</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
        if(imgList[i].type== "7"){//反欺诈资料
            var time=getFirstTime(imgList[i].creatTime);
            FanQizha+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height: 100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>反欺诈资料</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
    }
    $("#zszl ul").empty();
    $("#zszl ul").append(html40);
    $("#cszl ul").empty();
    $("#cszl ul").append(html39);
    $("#fanqzContainer ul").empty();
    $("#fanqzContainer ul").append(FanQizha);
    $("#paikeContainer ul").empty();
    $("#paikeContainer ul").append(PaiKe);
}
//图片预览方法二
function imgShow(me){
    window.open($(me).attr("src"),"图片预览");
}
//格式时间
function getFirstTime(inputTime) {
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