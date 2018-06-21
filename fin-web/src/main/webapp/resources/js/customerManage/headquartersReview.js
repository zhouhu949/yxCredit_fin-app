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
            var orderAndbank= data.order;//订单信息和银行卡信息
            var imageList = data.imgList;//上传资料
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

            $("#applayTime").text(formatTime(orderAndbank.applayTime));//申请时间
            $("#bankName").text(orderAndbank.bankName);//开户行
            $("#bankSubbranch").text(orderAndbank.bankSubbranch);//支行
            $("#cardNumber").text(orderAndbank.cardNumber);//银行卡号


        }

        if(imageList){
            var  showImg="";
            if(imageList.length > 0) {
                if(imageList.length < 5){
                    showImg +=" <tr>";
                    showImg +="<td class=\"tdTitle align\" id=\"showNewImg1\" style=\"text-align: left\" colspan=\"3\">\n" +
                        "<ul style=\"text-align: left;\">";
                    for (var i=0; i<imageList.length; i++){
                        var image=imageList[i];
                        showImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto" class="imgbox"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow"></div><p class="hideTime" style="margin:1em;"></p></li>';
                    }
                    showImg +="</ul></td>";
                    showImg +="</tr>";
                }
                if(imageList.length > 4){
                    showImg +=" <tr>";
                    showImg +="<td class=\"tdTitle align\" id=\"showNewImg1\" style=\"text-align: left\" colspan=\"3\">\n" +
                        "<ul style=\"text-align: left;\">";
                    for (var i=0; i < 4; i++){
                        var image=imageList[i];
                        showImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto" class="imgbox"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow"></div><p class="hideTime" style="margin:1em;"></p></li>';
                    }
                    showImg +="</ul></td>";
                    showImg +="</tr>";
                    showImg +=" <tr>";
                    showImg +="<td class=\"tdTitle align\" id=\"showNewImg2\" style=\"text-align: left\" colspan=\"3\"><ul style=\"text-align: left;\">";
                    for (var i= 4; i<imageList.length; i++){
                        var image=imageList[i];
                            showImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto" class="imgbox"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow" ></div><p class="hideTime" style="margin:1em;"></p></li>';
                    }
                    showImg +="</ul></td>";
                    showImg +="</tr>";
                }

            }
            $("#yxtup").html(showImg);
            PostbirdImgGlass.init({
                domSelector:".imgbox img",
                animation:true
            });
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
                '<td width="23%"> <a href="./tongDunView?resultId='+apiResultList[i].apiResultId+'&customerId='+ paramMap.customerId +'&sourceCode='+ apiResultList[i].sourceCode +'" target="_blank" style="color: #f1a02f">查看报告</a></td>'+
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
            var numReg = /[1-9]+[0-9]*/;
            var predictPrice = $("#predictPrice").val();
            var applayMoney = $("#applayMoney").text();
            if (!numReg.test(predictPrice)) {
                layer.alert("审批金额格式有误！", {icon: 2, title: '操作提示'});
                return
            }
            if(Number(applayMoney) < Number(predictPrice)) {
                layer.alert("审批金额不能超过申请金额！", {icon: 2, title: '操作提示'});
                return
            }

            var paramMap = {};
            paramMap.loanAmount = predictPrice;
            paramMap.applayMoney = applayMoney;
            paramMap.periods = $("#periods").text();
            paramMap.id = $("#orderId").val();
            paramMap.approveSuggestion = $("#approveSuggestion").val();
            paramMap.customerId = $("#customerId").val();
            Comm.ajaxPost('customerAudit/approvedSP',JSON.stringify(paramMap) , function (result) {
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
            var approveSuggestion = $("#remark").val();
            if(!approveSuggestion){
                layer.msg("拒绝原因不能为空");
                return;
            }
            var paramMap = {};
            paramMap.applayMoney = $("#applayMoney").text();
            paramMap.periods = $("#periods").text();
            paramMap.id = $("#orderId").val();
            paramMap.customerId = $("#customerId").val();
            paramMap.approveSuggestion = approveSuggestion;

            Comm.ajaxPost('customerAudit/approvalRefusedSP',JSON.stringify(paramMap) , function (result) {
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