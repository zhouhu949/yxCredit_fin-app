$().ready(function(){
    var orderId = $('#orderId').val();
    $("#YHJE").text((parseFloat($("#tdFee").text())+parseFloat($("#applayMoney").text())).toFixed(2));
    var paramMap = {};
    paramMap.customerId = $("#customerId").val();
    paramMap.type = '1';
    paramMap.orderId = orderId;
    paramMap.salesmanId=$("#salesmanId").val();
    Comm.ajaxPost("customer/customerDetails",JSON.stringify(paramMap),function(result){
        var data = result.data;
        console.log(data);
        if(data){
            var linkmanList=data.linkmanList;//联系人信息
            //var customer = data.customer;//客户信息
            var apiResultList= data.apiResultList;//风控审核信息
            var orderAndbank= data.orderAndbank;//订单信息和银行卡信息
        }

        //订单信息和银行卡信息
        if(orderAndbank){
            console.log(orderAndbank);
            $("#orderId").val(orderAndbank.orderId)
            $("#orderNo").html(orderAndbank.orderNo);//订单编号
            $("#productName").html(orderAndbank.productName);//产品名称
            $("#rate").text(orderAndbank.rate);//利率
            $("#applayMoney").text(orderAndbank.applayMoney);//申请金额
            $("#periods").text(orderAndbank.periods);//申请期限
            $("#applayTime").text(orderAndbank.applayTime);//申请时间
            $("#bankName").text(orderAndbank.applayTime);//开户行
            $("#bankSubbranch").text(orderAndbank.applayTime);//支行
            $("#cardNumber").text(orderAndbank.cardNumber);//银行卡号


        }

        //风控信息审核列表
        var html = '';
        $("#apiResult").empty();
        for(var i=0;i<apiResultList.length;i++){
            html=html+ '<tr>'+
                '<td width="10%" >规则名称：</td>'+
                '<td width="23%">'+apiResultList[i].sourceName+'</td>'+
                '<td width="10%" >审核结果：</td>'+
                '<td width="23%">'+apiResultList[i].message+'</td>'+
                '<td width="10%" >报告浏览：</td>'+
                '<td width="23%"> <a href="./tongDunView?orderId=${orderId}" target="_blank">查看报告</a></td>'+
                '</tr>';

        }
        $("#apiResult").append(html);

        //联系人信息
        var html = '';
        $("#relation").empty();
        for(var i=0;i<linkmanList.length;i++){
            var rel = linkmanList[i].mainSign;
            var yesno = "";
            html=html+ '<tr>'+
                '<td width="10%" >关&emsp;&emsp;系：</td>'+
                '<td width="23%">'+linkmanList[i].relationshipName+'</td>'+
                '<td width="10%" >名&emsp;&emsp;称：</td>'+
                '<td width="23%">'+linkmanList[i].linkName+'</td>'+
                '<td width="10%" >联系方式：</td>'+
                '<td width="23%">'+linkmanList[i].contact+'</td>'+
                '</tr>';

        }
        $("#relation").append(html);//直系
    }, "application/json",null,null,null,false);
});


//格式时间
function getTime(inputTime) {
    var y,m,d;
    if(inputTime) {
        y = inputTime.slice(0,4);
        m = inputTime.slice(4,6);
        d = inputTime.slice(6,8);
        return y+'年'+m+'月'+d+'日 '+inputTime.slice(8,10)+":"+inputTime.slice(10,12)+":"+inputTime.slice(12,14);
    }
}
//格式时间
function getTime1(inputTime) {
    var y,m,d;
    if(inputTime) {
        y = inputTime.slice(0,4);
        m = inputTime.slice(4,6);
        d = inputTime.slice(6,8);
        return y+'年'+m+'月'+d+'日';
    }
}
function clickNextButton(){
    layerIndex = layer.open({
        type : 1,
        title : "通过",
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '500px', '260px' ],
        content : $('#preQuotaDialog'),
        btn : [ '提交', '取消' ],
        yes:function(index, layero){
            var reguser = /^(([0-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
            var user = $("#predictPrice").val();
            if (reguser.test(user) == false) {
                if (user!='0'){
                    layer.alert("审批额度格式有误！", {icon: 2, title: '操作提示'});
                    return
                }
            }
            var predictPrice=parseFloat(user);
            var orderId = $("#orderId").val();
            var approveSuggestion = $("#approveSuggestion").val();
            var customerId = $("#newCustomerId").val();
            Comm.ajaxPost('customerAudit/approvedSP',JSON.stringify({id:orderId,approveSuggestion:approveSuggestion,customerId:customerId,predictPrice:predictPrice}) , function (result) {
                layer.msg(result.msg,{time:2000},function(){
                    // layer.closeAll();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.g_userManage.tableOrder.ajax.reload();
                    parent.layer.close(index)
                })
            },"application/json");
        }
    })

}
function reasonClick(){
    layerIndex = layer.open({
        type : 1,
        title : "拒绝",
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '500px', '' ],
        content : $('#approvalRefused'),
        btn : [ '提交', '取消' ],
        yes:function(index, layero){
            var orderId = $("#orderId").val();
            var approveSuggestion = $("#remark").val();
            if(! approveSuggestion){
                layer.msg("拒绝原因不能为空");
                return;
            }
            var customerId = $("#customerId").val();
            Comm.ajaxPost('customerAudit/approvalRefusedSP',JSON.stringify({id:orderId,approveSuggestion:approveSuggestion,customerId:customerId}) , function (result) {
                layer.msg(result.msg,{time:2000},function(){
                    // layer.closeAll();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.g_userManage.tableOrder.ajax.reload();
                    parent.layer.close(index)
                })
            },"application/json");
        }
    })
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

//收缩目录
function shrink(me){
    $(me).nextAll().slideToggle();
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