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
        return y+'年'+m+'月'+d+'日 ';
    }
}
$().ready(function(){
    var orderId = $('#orderId').val();
    var orderState = $('#orderState').val();
    $("#tdTotal").text(parseFloat($("#tdFee").text())+parseFloat($("#proSum").text()));
    $("#YHJE").text(parseFloat($("#tdFee").text())+parseFloat($("#proSum").text()));
    var artificial = document.getElementById("artificial");
    var Loan = document.getElementById("Loan");
    if(orderState == 3){
        artificial.style.display="block";
    };
    if(orderState == 5){
        artificial.style.display="block";
        Loan.style.display="block";
    };

    var paramMap = {};
    paramMap.customerId = $('#customerId').val();
   // paramMap.type = '1';
    paramMap.orderId = orderId;
    //paramMap.salesmanId = $('#salesmanId').val();
    Comm.ajaxPost("customer/customerDetailsSP",JSON.stringify(paramMap),function(result){
        var data = result.data;
        if(data){
            var linkmanList=data.linkmanList;//联系人信息
            var host = data.hostUrl;//url
            var order = data.order;//订单信息
            var customer = data.customer;//客户信息
            var orderAndbank= data.orderAndbank;//订单信息和银行卡信息
            var apiResultList= data.apiResultList;//风控审核信息
            var orderOperationRecord= data.orderOperationRecord;//订单操作记录审核信息
            var loanRecord= data.loanRecord;//订单操作记录放款信息

        }

        if(order){
            $("#applayTime").text(formatTime(order.applayTime));//审核时间
        }


        //订单操作记录信息
        if(orderOperationRecord){
            var operationResult="";
            var number = orderOperationRecord.operationResult;
            if (number == 1){
                operationResult="提交";
            }else if(number == 2){
                operationResult="通过";
            }else if(number == 3){
                operationResult="拒绝";
            }else if(number == 4){
                operationResult="回退";
            }else if(number == 5){
                operationResult="同意";
            }else if(number == 6){
                operationResult="放弃";
            }else if(number == 7){
                operationResult="放款";
            }

            $("#amount").html(orderOperationRecord.amount);//金额
            $("#operationResult").html(operationResult);//审核结果
            $("#empName").text(orderOperationRecord.empName);//审核人员
            $("#operationTime").text(formatTime(orderOperationRecord.operationTime));//审核时间
            $("#description").text(orderOperationRecord.description);//审核意见

        }

        //订单放款信息
        if(loanRecord){
            var loanState="";
            var number = loanRecord.operationResult;
            if (number == 1){
                loanState="提交";
            }else if(number == 2){
                loanState="通过";
            }else if(number == 3){
                loanState="拒绝";
            }else if(number == 4){
                loanState="回退";
            }else if(number == 5){
                loanState="同意";
            }else if(number == 6){
                loanState="放弃";
            }else if(number == 7){
                loanState="放款";
            }

            $("#loanAmount").html(loanRecord.amount);//放款金额
            $("#loanTime").text(formatTime(loanRecord.operationTime));//放款时间
            $("#loanState").html(loanState);//放款状态


        }


        //数据写入隐藏域
        $("#orderId").val(orderId);

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
        $("#relation").html('');//直系
        $("#relation").append(html);//直系


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
                '<td width="23%"> <a href="#" style="color: #f1a02f">点击查看报告</a></td>'+
                '</tr>';

        }
        $("#apiResult").append(html);


    }, "application/json",null,null,null,false);




});
function clickNextButton(){
    layerIndex = layer.open({
        type : 1,
        title : "产品信息",
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '500px', '' ],
        content : $('#preQuotaDialog'),
        btn : [ '提交', '取消' ],
        yes:function(index, layero){
            var orderId = $("#orderId").val();
            //var amount =parseInt($("#RateOfPayGel").val());
            var approveSuggestion = $("#preQuotaDialog_remark").val();
            var owerLimit=parseInt ($("#LowerLimit").val());
            var upperLimit = $("#UpperLimit").val();
            var taskNodeId =  $("#taskNodeId").val();
            var customerId = $("#customerId").val();
            //Comm.ajaxPost('customerAudit/approved',JSON.stringify({id:orderId,credit:amount,precredit:amount,approveSuggestion:approveSuggestion,customerId:customerId,taskNodeId:taskNodeId})
            Comm.ajaxPost('customerAudit/approved',JSON.stringify({id:orderId,approveSuggestion:approveSuggestion,customerId:customerId,taskNodeId:taskNodeId}) , function (result) {
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
    var orderId = $("#orderId").val();
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
            var userId =  $("#userId").val();
            var taskNodeId =  $("#taskNodeId").val();
            if(! approveSuggestion){
                layer.msg("拒绝原因不能为空");
                return;
            }
            var customerId = $("#customerId").val();
            Comm.ajaxPost('customerAudit/approvalRefused',JSON.stringify({id:orderId,approveSuggestion:approveSuggestion,customerId:customerId,userId:userId,taskNodeId:taskNodeId}) , function (result) {
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