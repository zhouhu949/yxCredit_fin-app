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
$().ready(function(){
    var orderId = $('#orderId').val();
    $("#tdTotal").text(parseFloat($("#tdFee").text())+parseFloat($("#proSum").text()));
    $("#YHJE").text(parseFloat($("#tdFee").text())+parseFloat($("#proSum").text()));


    var personList,deviceInfo,linkmanList,jobList,imageList,host,ruleResult;
    var customer;
    var paramMap = {};
    paramMap.customerId = $("#customerId").val();
    paramMap.type = '1';
    paramMap.orderId = orderId;
    Comm.ajaxPost("customer/customerDetails",JSON.stringify(paramMap),function(result){
        var data = result.data;
        personList=data.personList[0];//个人信息
        linkmanList=data.linkmanList;//联系人信息
        jobList = data.occupationList[0];//单位信息
        deviceInfo = data.customerDeviceInfo;//设备信息
        ruleResult=data.ruleResult;//风控返回结果
        host = data.hostUrl;//url
        imageList=data.imgList;//上传资料
        customer = data.customer;//客户信息
        //showCumIng(imageList,host);
    }, "application/json",null,null,null,false);
    // if(customer){
    //     if(customer.callRecordUrl&&customer.callRecordUrl!=''){
    //         var btn='<a class="tabel_btn_style" href="'+customer.callRecordUrl+'" target="_blank"> 话单下载 </a>';
    //         $("#callRecordUrl").html(btn);
    //     }else {
    //         $("#callRecordUrl").html('未生成话单');
    //     }
    // }
    if(personList){
        debugger
        $("#tdSex").text(personList.sex_name);//性别
        $("#tdBirth").text(personList.birth);//出生日期
        $("#applyMerry").text(personList.marry_name);//婚姻状况
        $("#applyCensus").text(personList.card_register_address);//户籍
        $("#applyAddr").text(personList.nowaddress);//当前住址
        $('#education').text(personList.educational_name);//学历
        $('#tdRefereeId').text(personList.APEX2);//推荐人编号
        $('#tdReferee').text(personList.APEX1);//推荐人名称
        // $("#bank_card").val(personList.bank_card);//银行卡号
        // $("#account_city").val(personList.account_city);//开户地址
        // $("#account_bank").val(personList.account_bank);//开户银行
        $("#zcardSrcBase64").attr('src',personList.zcardSrcBase64);
        $("#fcardSrcBase64").attr('src',personList.fcardSrcBase64);
        $("#face_src").attr('src',personList.face_src);
        $("#shippingAddress").text(personList.receive_address);
    }
    //获取风控结果
    if(ruleResult){
        var  ruleResultHtml="";
        for(var i=0;i<ruleResult.length;i++){
            if(ruleResultHtml!=""){
                ruleResultHtml=ruleResultHtml+'<tr style="border-right:none;border-left:none;" ><td colspan="6" height="20px" style="border-right:none;border-left:none;" ></td></tr>'
            }
            var state=$("#state").val();
            //ruleResultHtml=ruleResultHtml+'<tr><td colspan="6" style="text-align: left;font-weight:bold;">'+ruleResult[i].ruleName+'</td></tr>';
            if(ruleResult[i].engineId=='281L'){
                debugger
                ruleResultHtml=ruleResultHtml+'<tr><td width="10%">规则名称：</td><td width="23%">'+ruleResult[i].ruleName+'</td><td width="10%">分数：</td><td width="23%" >'+ruleResult[i].riskScore+'</td><td width="10%">审核结果：</td><td >'+ruleResult[i].state+'</td></tr>';
                if (ruleResult[i].hitRule&&ruleResult[i].hitRule.length>0){
                    ruleResultHtml=ruleResultHtml+'<tr><td rowspan="'+ruleResult[i].hitRule.length+'">风险详情：</td>';
                    for(var j=0;j<ruleResult[i].hitRule.length;j++){
                        if(j==0){
                            ruleResultHtml=ruleResultHtml+'<td >'+ruleResult[i].hitRule[j][0]+'</td><td >'+ruleResult[i].hitRule[j][2]+'</td><td colspan="3" >'+ruleResult[i].hitRule[j][1]+'</td></tr>';//<td colspan="2">命中</td>
                        }else {
                            ruleResultHtml=ruleResultHtml+'<tr><td >'+ruleResult[i].hitRule[j][0]+'</td><td >'+ruleResult[i].hitRule[j][2]+'</td><td colspan="3"  >'+ruleResult[i].hitRule[j][1]+'</td></tr>';//<td colspan="2">命中</td>
                        }
                    }
                    //ruleResultHtml=ruleResultHtml+'<tr><td width="10%">拒绝明细</td><td colspan="5">'+ruleResult[i].ruleJson+'</td></tr>';
                }
            }else  if(ruleResult[i].engineId=='282L') {
                ruleResultHtml=ruleResultHtml+
                    '<tr>' +
                        '<td width="10%">规则名称：</td>' +
                        '<td width="23%">'+ruleResult[i].ruleName+'</td>' +
                        '<td width="10%">审核结果：</td>' +
                        '<td colspan="2">'+ruleResult[i].state+'</td>' +
                        '<td>' +
                            // '<a class="tabel_btn_style" href="'+_ctx+'/customer/get91ZX?customerId='+customer.id+'" target="_blank"> 征信报告 </a>' +
                            '<a class="tabel_btn_style" href="'+_ctx+'/customer/getTonDunZX?customerId='+customer.id+'&orderId='+orderId+'" target="_blank"> 征信报告 </a>' +
                        '</td>'+
                    '</tr>';
            }else  if(ruleResult[i].engineId=='285L') {
                if(customer){
                    var  HDHtml="";
                    //if(customer.callRecordUrl&&customer.callRecordUrl!=''){
                        var  customerId=$("#customerId").val();
                        HDHtml='<a class="tabel_btn_style" href="'+_ctx+'/customer/getTelephoneRecord?customerId='+customer.id+'" target="_blank"> 话单下载 </a>';
                        $("#callRecordUrl").html(HDHtml);
                    //}else {
                    //    HDHtml='未生成话单';
                    //}
                }
                ruleResultHtml=ruleResultHtml+'<tr><td width="10%">规则名称：</td><td width="23%">'+ruleResult[i].ruleName+'</td><td width="10%">审核结果：</td><td colspan="2">'+ruleResult[i].state+'</td><td>'+HDHtml+'</td></tr>';
            }
            if (ruleResult[i].state=="拒绝"){
                var rows=0;
                if(ruleResult[i].engineId=='282L') {
                    var ruleDetailed="";
                    //增信通
                    var riskItemsZXT=ruleResult[i].hitRule[0];
                    if (riskItemsZXT&&riskItemsZXT.length>0){
                        //ruleDetailed=ruleDetailed+'<td >增信通</td><td colspan="4">拒绝</td></tr>'

                        debugger
                        if(riskItemsZXT[0]&&riskItemsZXT[0].length>0){
                            for (var j=0;j<riskItemsZXT[0].length;j++){
                                debugger
                                rows=rows+1;
                                if(j==0){
                                    ruleDetailed=ruleDetailed+'<td rowspan="'+riskItemsZXT[0].length+'">逾期</td><td colspan="2">'+riskItemsZXT[0][j][1]+'</td><td  colspan="2">'+riskItemsZXT[0][j][2]+'</td></tr>';
                                }else {
                                    ruleDetailed=ruleDetailed+'<tr><td colspan="2">'+riskItemsZXT[0][j][1]+'</td><td colspan="2">'+riskItemsZXT[0][j][2]+'</td></tr>';
                                }
                            }
                        }
                        if(riskItemsZXT[1]&&riskItemsZXT[1].length>0){
                            for (var j=0;j<riskItemsZXT[1].length;j++){
                                debugger
                                rows=rows+1;
                                if(j==0){
                                    ruleDetailed=ruleDetailed+'<td rowspan="'+riskItemsZXT[1].length+'">欺诈</td><td colspan="2">'+riskItemsZXT[1][j][1]+'</td><td  colspan="2">'+riskItemsZXT[1][j][2]+'</td></tr>';
                                }else {
                                    ruleDetailed=ruleDetailed+'<tr><td colspan="2">'+riskItemsZXT[1][j][1]+'</td><td colspan="2">'+riskItemsZXT[1][j][2]+'</td></tr>';
                                }
                            }
                        }

                        if(ruleDetailed.length>0){
                            ruleDetailed='<td rowspan="'+(riskItemsZXT[0].length+riskItemsZXT[1].length)+'">增信通：</td>'+ruleDetailed;
                        }

                        // for (var j=0;j<riskItemsZXT.length;j++){
                        //     debugger
                        //     rows=rows+1;
                        //     if(j==0){
                        //         ruleDetailed=ruleDetailed+'<td colspan="3">'+riskItemsZXT[j]+'</td><td >命中</td></tr>';
                        //     }else {
                        //         ruleDetailed=ruleDetailed+'<tr><td colspan="3">'+riskItemsZXT[j]+'</td><td >命中</td></tr>';
                        //     }
                        // }
                    }else {
                        rows=rows+1;
                        ruleDetailed=ruleDetailed+'<td >增信通</td><td colspan="4">通过</td></tr>'
                    }
                    //同盾
                    var riskItemsTD=ruleResult[i].hitRule[1];
                    if(riskItemsTD&&riskItemsTD.length>0){
                        if(ruleDetailed.length>0){
                            ruleDetailed=ruleDetailed+'<tr><td rowspan="'+riskItemsTD.length+'">同盾规则：</td>';
                        }else {
                            ruleDetailed=ruleDetailed+'<td rowspan="'+riskItemsTD.length+'">同盾规则：</td>';
                        }
                        for (var j=0;j<riskItemsTD.length;j++){
                            rows=rows+1;
                            if (j==0){
                                ruleDetailed=ruleDetailed+'<td colspan="4">'+riskItemsTD[j]+'</td><td >命中</td></tr>';
                            }else {
                                ruleDetailed=ruleDetailed+'<tr><td colspan="4">'+riskItemsTD[j]+'</td><td >命中</td></tr>';
                            }

                        }
                    }else {
                        rows=rows+1;
                    }
                    //ruleResultHtml=ruleResultHtml+'<tr><td rowspan="'+rows+'">命中详情：</td>'+ruleDetailed;
                    ruleResultHtml=ruleResultHtml+ruleDetailed;
                }else {
                    if (ruleResult[i].hitRule&&ruleResult[i].hitRule.length>0){
                        ruleResultHtml=ruleResultHtml+'<tr><td rowspan="'+ruleResult[i].hitRule.length+'">命中详情：</td>';
                        for(var j=0;j<ruleResult[i].hitRule.length;j++){
                            if(j==0){
                                ruleResultHtml=ruleResultHtml+'<td colspan="3">'+ruleResult[i].hitRule[j]+'</td><td colspan="2">命中</td></tr>';
                            }
                        }
                        //ruleResultHtml=ruleResultHtml+'<tr><td width="10%">拒绝明细</td><td colspan="5">'+ruleResult[i].ruleJson+'</td></tr>';
                    }
                }
            }
        }
        if(ruleResultHtml.length>0){
            $("#Result").html(ruleResultHtml);
        }
    }
    //设备信息
    if(deviceInfo){
        $("#apply_province").html(deviceInfo.apply_province);
        $("#apply_city").html(deviceInfo.apply_city);
        $("#apply_area").html(deviceInfo.apply_area);
        $("#apply_address").html(deviceInfo.apply_address);
        $("#imei_number").html(deviceInfo.imei_number);
        $("#operate_system").html(deviceInfo.operate_system);
        $("#device_type").html(deviceInfo.device_type);
        $("#tel_memory").html(deviceInfo.tel_memory);
        $("#tel_model").html(deviceInfo.tel_model);
        $("#tel_brand").html(deviceInfo.tel_brand);
        $("#network_type").html(deviceInfo.network_type);
        $("#wifi_name").html(deviceInfo.wifi_name);
        $("#wifi_ssid").html(deviceInfo.wifi_ssid);
        $("#ip_address").html(deviceInfo.ip_address);
        $("#ip_province").html(deviceInfo.ip_province);
        $("#ip_city").html(deviceInfo.ip_city);
        $("#ip_area").html(deviceInfo.ip_area);
        $("#is_root").html(deviceInfo.is_root);
        $("#is_prison").html(deviceInfo.is_prison);
        $("#is_moni_online").html(deviceInfo.is_moni_online);
        $("#location_permission").html(deviceInfo.location_permission);
    }
    //单位信息
    if(jobList){
        console.log(jobList)
        $("#unitName").text(jobList.companyName);//单位名称
        $("#unitPro").text(jobList.companyProperty);//单位性质
        $("#unitTel").text(jobList.companyCode+jobList.companyPhone);//单位电话
        var address=jobList.companyAddress.replace(/\//g, '');
        $("#unitAddr").text(jobList.provinceName+jobList.cityName+jobList.districtName);//单位地址city_name, district_name
        $("#unitDepart").text(jobList.department);//部门
        $("#unitGrade").text(jobList.posLevel);//职级
        $("#tdDetailed").text(jobList.companyAddress);//单位地址
        var hiredate= '';
        if(jobList.hiredate){
            hiredate=jobList.hiredate.substring(0,4)+"-"+jobList.enteringTheCompany.substring(4,6)+"-"+jobList.enteringTheCompany.substring(6,8)
        }else{
            hiredate='';
        }
        $("#unitYear").html(hiredate);//现单位入职时间/注册时间
        var payType = '';
        if(jobList.payType==1){
            payType = '银行代发';
        }else {
            payType = '现金';
        }
        $("#unitDeliverty").text(payType);//工资发放形式
        $("#unitIncome").text(jobList.basicMonthlyPay);//月收入
        $("#unitDay").text(jobList.payDay);//发薪日
        var isApex = '';
        if(jobList.apex1){
            if (jobList.apex1=='Y'){
                isApex='是';
            }
            if (jobList.apex1=='N'){
                isApex='否';
            }
        }
        $("#unitSocial").text(isApex);//是否缴纳公积金及社保
    }
    //联系人信息
    if(linkmanList){
        for(var i=0;i<linkmanList.length;i++){
            var rel=linkmanList[i].mainSign;//关系
            if(rel=="0"){//直系
                $("#directName1").val(linkmanList[i].linkName);//姓名(直系)
                $("#directRel1").val(linkmanList[i].relationshipName);//关系名称
                $("#directUnit1").val(linkmanList[i].workCompany);//工作单位
                $("#directTel1").val(linkmanList[i].contact);//联系电话
                var isKnow = linkmanList[i].knownLoanName;
                if (isKnow==1){
                    isKnow = '是';
                }
                else if(isKnow==0){
                    isKnow = '否';
                }
            }else{//同事
                $("#workerName").val(linkmanList[i].linkName);//姓名(同事)
                $("#workeDirectRel").val(linkmanList[i].relationshipName);//部门？
                $("#workerPost").val(linkmanList[i].job);//职务？
                $("#workerTel").val(linkmanList[i].contact);//联系电话
                var isKnow = linkmanList[i].knownLoanName;
                if (isKnow==1){
                    isKnow = '是';
                }
                else if(isKnow==0){
                    isKnow = '否';
                }
            }
        }
    }
    // var taskNodeId = window.parent.document.getElementById("taskNodeId").value;
    // $("#taskNodeId").val(taskNodeId);
    // $("#clickNextButton").click(clickNextButton);
    // $("#reasonClick").click(reasonClick);
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
            // if(amount < owerLimit){
            //     layer.msg("预授信额度小于到手金额范围。");
            //     return;
            // }
            // if(amount > upperLimit){
            //     layer.msg("预授信额度大于到手金额范围。");
            //     return;
            // }
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