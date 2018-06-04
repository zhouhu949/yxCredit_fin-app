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
        param.orderType = '2';//商品订单
        param.isLock=$("#isLock").val();//处理状态
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();//客户名称
            param.card = $("input[name='card']").val();//身份证号
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
            {"data": "applayMoney","orderable" : false},//申请金额
            {"data": "periods","orderable" : false},//申请期限
            {"data": "applayTime","orderable" : false ,
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
                console.log(data);
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
         format: 'YYYY-MM-DD',
         min: '1999-01-01',
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
         min: '1999-01-01',
         max: laydate.now(),
         istime: true,
        istoday: false,
        choose: function(datas){
             beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
     debugger
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
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('customerAudit/list', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
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
                var btn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="auditOrder(\''+data.orderId+'\',\''+data.customerId+'\')">审核 </a>');
                var seeBtn=$('<a  class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="orderSee(\''+data.orderId+'\',\''+data.customerId+'\')"> 查看 </a>');
                $('td', row).eq(10).append(btn).append(seeBtn);
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
                search :"applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
});
//发起自动审核
function  automation(orderId) {
    Comm.ajaxPost('customerAudit/automation',orderId,function(data){
        layer.msg(data.msg,{time:1000},function () {
            g_userManage.tableOrder.ajax.reload();
            layer.close(setUpLayer);
        });
    }, "application/json");
}

//打开审核页面
function auditOrder(orderId,customerId){
    var url = "/customerAudit/details?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '审核订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}

//退回订单
function backOrder(orderId){
    layer.confirm('确认退回订单?', function(index) {
        layer.close(index);
        var param = {};
        param.orderId = orderId;
        Comm.ajaxPost("customerAudit/backOrder", JSON.stringify(param), function (result) {
            g_userManage.fuzzySearch = false;
            g_userManage.tableOrder.ajax.reload();
            layer.msg(result.msg,{time:2000},function () {});
        }, "application/json");
    });
}

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

//加载信用问卷
function loadAnswer(orderId,customerId) {
    var data;
    var choiceList={};
    var param = {};
    var addParam = [];
    param.orderId = orderId;
    param.customerId = customerId;
    Comm.ajaxPost("customerAudit/getAnswer",JSON.stringify(param),function(result) {
        if (result.code=="0"){
            var tabValue="";
            data=result.data;
            for (var i=0;i<data.length;i++){
                var obj=data[i];
                choiceList=JSON.parse(obj.choiceList);
                tabValue+='<tr><td width="8%" rowspan="2" style="text-align: center;border:1px solid #4a4f56;">'+(i+1)+'</td><td width="98%">'+obj.questionTitle+'</td></tr>';
                debugger
                if (choiceList[0].type=='image'){
                    // tabValue+='<tr><td width="23%"> <input type="radio" checked="checked" value="'+choiceList[0].code+'" name="radio'+(i+1)+'">'+choiceList[0].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[0].value+'"/><input type="radio" value="'+obj.choiceList[1].code+'" name="radio'+(i+1)+'">'+choiceList[1].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[1].value+'"/><input type="radio" value="'+choiceList[2].code+'" name="radio'+(i+1)+'">'+choiceList[2].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[2].value+'"/><input type="radio" value="'+choiceList[3].code+'" name="radio'+(i+1)+'">'+choiceList[3].code+'、<img onclick="showImg(this)" style="width: 100px;height: 100px" src="'+choiceList[3].value+'"/></td></tr>';
                    tabValue+='<tr><td width="23%">';
                    for (var j=0;j<choiceList.length;j++){
                        tabValue+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  value="'+choiceList[j].code+'" id="radio'+choiceList[j].code+(i+1)+'"  name="radio'+(i+1)+'">'+choiceList[j].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[j].value+'"/>';
                    }
                    tabValue+='</td></tr>';
                }else {
                    // tabValue+='<tr><td width="23%"><input type="radio" checked="checked" value="'+choiceList[0].code+'" name="radio'+(i+1)+'">'+choiceList[0].code+'、'+choiceList[0].value+'<input type="radio" value="'+choiceList[1].code+'" name="radio'+(i+1)+'">'+choiceList[1].code+'、'+choiceList[1].value+'<input type="radio" value="'+choiceList[2].code+'" name="radio'+(i+1)+'">'+choiceList[2].code+'、'+choiceList[2].value+'<input type="radio" value="'+choiceList[3].code+'" name="radio'+(i+1)+'">'+choiceList[3].code+'、'+choiceList[3].value+'</td></tr>';
                    tabValue+='<tr><td width="23%">';
                    for (var j=0;j<choiceList.length;j++){
                        tabValue+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  value="'+choiceList[j].code+'" id="radio'+choiceList[j].code+(i+1)+ '"  name="radio'+(i+1)+'">'+choiceList[j].code+'、'+choiceList[j].value;
                    }
                    tabValue+='</td></tr>';
                }
            }
            $("#answerCount").val(data.length);
            $("#answerTable").html(tabValue);
            for (var i=0;i<data.length;i++){
                debugger
                var obj=data[i];
                if(obj.choiceCode){
                    var choiceCodeList=obj.choiceCode.split('/');
                    for (var j=0;j<choiceCodeList.length;j++){
                        var  idName='radio'+choiceCodeList[j]+(i+1);
                        //$('#radio'+choiceCodeList[j].code+(i+1)).attr("checked",'true');radioA2
                        $('#'+idName).attr("checked",'true');
                    }
                }

            }
            layerIndex = layer.open({
                type : 1,
                title : "信用问卷",
                shadeClose : false, //点击遮罩关闭层
                area : [ '80%', '80%' ],
                closeBtn: 1,
                content : $('#answerList').show(),
                btn : [ '保存', '取消' ],
                yes:function(index, layero){
                    addParam=[];
                    for (var i=0;i<data.length;i++){
                        var answerName='radio'+(i+1);
                        //data[i].answerCode=$("input[name="+answerName+"]:checked").val();
                        //data[i].orderId = orderId;
                        var addData={};
                        addData.orderId=orderId;
                        addData.id=data[i].id;
                        addData.questionId=data[i].questionId;
                        addData.examId=data[i].examId;
                        var checkedList=$("input[name="+answerName+"]:checked");
                        if(checkedList.length>0){
                            for(var j=0;j<checkedList.length;j++){
                                if(j==0){
                                    addData.choiceCode=$(checkedList[j]).val();
                                }else {
                                    addData.choiceCode+='/'+$(checkedList[j]).val();
                                }
                            }
                        }else {
                            layer.msg('请选择第'+(i+1)+'题',{time:2000},function () {
                            });
                            return;
                        }
                        addParam.push(addData);
                    }
                    debugger
                    Comm.ajaxPost('customerAudit/addAnswer', JSON.stringify(addParam), function (result) {
                        layer.msg(result.msg,{time:2000},function () {
                            if (result.code=="0"){
                                layer.closeAll();
                                g_userManage.tableOrder.ajax.reload();
                            }
                        });
                    },"application/json");
                }
            })
        }else {
            layer.msg(result.msg,{time:1000},function () {

             })
        }
    },"application/json");
}

//查看加载信用问卷
function seeAnswer(orderId,customerId) {
    var data;
    var choiceList={};
    var param = {};
    var addParam = [];
    param.orderId = orderId;
    param.customerId = customerId;
    Comm.ajaxPost("customerAudit/getAnswer",JSON.stringify(param),function(result) {
        if (result.code=="0"){
            var tabValue="";
            data=result.data;
            for (var i=0;i<data.length;i++){
                var obj=data[i];
                choiceList=JSON.parse(obj.choiceList);
                tabValue+='<tr><td width="8%" rowspan="2" style="text-align: center;border:1px solid #4a4f56;">'+(i+1)+'</td><td width="98%">'+obj.questionTitle+'</td></tr>';
                debugger
                if (choiceList[0].type=='image'){
                    // tabValue+='<tr><td width="23%"> <input type="radio" checked="checked" value="'+choiceList[0].code+'" name="radio'+(i+1)+'">'+choiceList[0].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[0].value+'"/><input type="radio" value="'+obj.choiceList[1].code+'" name="radio'+(i+1)+'">'+choiceList[1].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[1].value+'"/><input type="radio" value="'+choiceList[2].code+'" name="radio'+(i+1)+'">'+choiceList[2].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[2].value+'"/><input type="radio" value="'+choiceList[3].code+'" name="radio'+(i+1)+'">'+choiceList[3].code+'、<img onclick="showImg(this)" style="width: 100px;height: 100px" src="'+choiceList[3].value+'"/></td></tr>';
                    tabValue+='<tr><td width="23%">';
                    for (var j=0;j<choiceList.length;j++){
                        tabValue+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  disabled="disabled"  value="'+choiceList[j].code+'" id="radio'+choiceList[j].code+(i+1)+'"  name="radio'+(i+1)+'">'+choiceList[j].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[j].value+'"/>';
                    }
                    tabValue+='</td></tr>';
                }else {
                    // tabValue+='<tr><td width="23%"><input type="radio" checked="checked" value="'+choiceList[0].code+'" name="radio'+(i+1)+'">'+choiceList[0].code+'、'+choiceList[0].value+'<input type="radio" value="'+choiceList[1].code+'" name="radio'+(i+1)+'">'+choiceList[1].code+'、'+choiceList[1].value+'<input type="radio" value="'+choiceList[2].code+'" name="radio'+(i+1)+'">'+choiceList[2].code+'、'+choiceList[2].value+'<input type="radio" value="'+choiceList[3].code+'" name="radio'+(i+1)+'">'+choiceList[3].code+'、'+choiceList[3].value+'</td></tr>';
                    tabValue+='<tr><td width="23%">';
                    for (var j=0;j<choiceList.length;j++){
                        tabValue+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  disabled="disabled"    value="'+choiceList[j].code+'" id="radio'+choiceList[j].code+(i+1)+ '"  name="radio'+(i+1)+'">'+choiceList[j].code+'、'+choiceList[j].value;
                    }
                    tabValue+='</td></tr>';
                }
            }
            $("#answerCount").val(data.length);
            $("#answerTable").html(tabValue);
            for (var i=0;i<data.length;i++){
                debugger
                var obj=data[i];
                var choiceCodeList=obj.choiceCode.split('/');
                for (var j=0;j<choiceCodeList.length;j++){
                    var  idName='radio'+choiceCodeList[j]+(i+1);
                    //$('#radio'+choiceCodeList[j].code+(i+1)).attr("checked",'true');radioA2
                    $('#'+idName).attr("checked",'true');
                }
            }

            layerIndex = layer.open({
                type : 1,
                title : "信用问卷",
                shadeClose : false, //点击遮罩关闭层
                area : [ '80%', '80%' ],
                closeBtn: 1,
                content : $('#answerList').show(),
            })
        }else {
            layer.msg(result.msg,{time:1000},function () {

            })
        }
    },"application/json");
}
function showImg(t) {
    $("#seeImg").attr('src',$(t)[0].src);
    layer.open({
        type : 1,
        title : "图片查看",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '630px', '650px'  ],
        content : $('#showImg'),})

}




