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


    var personList,deviceInfo,linkmanList,jobList,imageList,host,ruleResult,servicePackageOrderList,answerList,answerScore;
    var customer;
    var paramMap = {};
    paramMap.customerId = $("#customerId").val();
    paramMap.type = '1';
    paramMap.orderId = orderId;
    Comm.ajaxPost("customer/customerDetailsSP",JSON.stringify(paramMap),function(result){
        var data = result.data;
        personList=data.personList[0];//个人信息
        linkmanList=data.linkmanList;//联系人信息
        jobList = data.occupationList[0];//单位信息
        deviceInfo = data.customerDeviceInfo;//设备信息
        ruleResult=data.ruleResult;//风控返回结果
        host = data.hostUrl;//url
        imageList=data.imgList;//上传资料
        customer = data.customer;//客户信息
        answerList=data.answerList;//答案信息
        answerScore=data.answerScore;//答题得分
        servicePackageOrderList = data.servicePackageOrderList;//服务包
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
    if(servicePackageOrderList){
        debugger
        var servicePackageQZ="";
        var servicePackageTQ="";
        var servicePackageQT="";
        for (var i=0;i<servicePackageOrderList.length;i++){
            var servicePackageOrder=servicePackageOrderList[i];
            if(servicePackageOrder.type=='1'){
                if (servicePackageQZ==""){
                    servicePackageQZ='<br /><table style="width: 100%"><tr><td colspan="3" style="text-align: left">前置服务包</td></tr><tr><td style="width: 33%">还款包类型</td><td style="width: 33%">还款包名称</td><td style="width: 33%">价格</td></tr>';
                    servicePackageQZ+='<tr><td>'+servicePackageOrder.packageTypeName+'</td><td>'+servicePackageOrder.packageName+'</td><td>'+servicePackageOrder.amountCollection+'</td></tr>';
                }else {
                    servicePackageQZ+='<tr><td>'+servicePackageOrder.packageTypeName+'</td><td>'+servicePackageOrder.packageName+'</td><td>'+servicePackageOrder.amountCollection+'</td></tr>';
                }
            }else if(servicePackageOrder.type=='2'){
                if (servicePackageTQ==""){
                    servicePackageTQ='<br /><table style="width: 100%"><tr><td colspan="5" style="text-align: left">月付还款包</td></tr><tr><td style="width: 20%">还款包类型</td><td style="width: 20%">还款包名称</td><td style="width: 20%">价格</td><td style="width: 20%">几期后允许提前还款</td><td style="width: 20%">收取期数</td></tr>';
                    servicePackageTQ+='<tr><td>'+servicePackageOrder.packageTypeName+'</td><td>'+servicePackageOrder.packageName+'</td><td>'+servicePackageOrder.amountCollection+'</td><td>'+servicePackageOrder.month+'</td><td>--</td></tr>';
                }else {
                    servicePackageTQ+='<tr><td>'+servicePackageOrder.packageTypeName+'</td><td>'+servicePackageOrder.packageName+'</td><td>'+servicePackageOrder.amountCollection+'</td><td>'+servicePackageOrder.month+'</td><td>--</td></tr>';
                }
            }else if(servicePackageOrder.type=='3'){
                if (servicePackageQT==""){
                    servicePackageQT='<br /><table style="width: 100%"><tr><td colspan="5" style="text-align: left">其他还款包</td></tr><tr><td style="width: 20%">还款包类型</td><td style="width: 20%">还款包名称</td><td style="width: 20%">价格</td><td style="width: 20%">几期后允许提前还款</td><td style="width: 20%">收取期数</td></tr>';
                    servicePackageQT+='<tr><td>'+servicePackageOrder.packageTypeName+'</td><td>'+servicePackageOrder.packageName+'</td><td>'+servicePackageOrder.amountCollection+'</td><td>'+servicePackageOrder.month+'</td><td>'+servicePackageOrder.periodCollection+'</td></tr>';
                }else {
                    servicePackageQT+='<tr><td>'+servicePackageOrder.packageTypeName+'</td><td>'+servicePackageOrder.packageName+'</td><td>'+servicePackageOrder.amountCollection+'</td><td>'+servicePackageOrder.month+'</td><td>'+servicePackageOrder.periodCollection+'</td></tr>';
                }
            }
        }
        if(servicePackageQZ!=""){
            servicePackageQZ+='</table>';
        }
        if(servicePackageTQ!=""){
            servicePackageTQ+='</table>';
        }
        if(servicePackageQT!=""){
            servicePackageQT+='</table>';
        }
        $("#servicePackageOrderListDiv").html(servicePackageQZ+servicePackageTQ+servicePackageQT);

    }
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
            if(ruleResult[i].engineId=='286L'){
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
            }else  if(ruleResult[i].engineId=='287L') {
                ruleResultHtml=ruleResultHtml+'<tr><td width="10%">规则名称：</td><td width="23%">'+ruleResult[i].ruleName+'</td><td width="10%">审核结果：</td><td colspan="3">'+ruleResult[i].state+'</td></tr>';
            }else  if(ruleResult[i].engineId=='288L') {
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
                if(ruleResult[i].engineId=='287L') {
                    var ruleDetailed="";
                    //增信通
                    var riskItemsZXT=ruleResult[i].hitRule[0];
                    if (riskItemsZXT&&riskItemsZXT.length>0){
                        //ruleDetailed=ruleDetailed+'<td >增信通</td><td colspan="4">拒绝</td></tr>'
                        if(riskItemsZXT[0]&&riskItemsZXT[0].length>0){
                            for (var j=0;j<riskItemsZXT[0].length;j++){
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
                    //91规则
                    var riskItems91=ruleResult[i].hitRule[2];
                    if(riskItems91&&riskItems91.length>0){
                        if(ruleDetailed.length>0){
                            ruleDetailed=ruleDetailed+'<tr><td rowspan="'+riskItems91.length+'">91贷款明细：</td>';
                        }else {
                            ruleDetailed=ruleDetailed+'<td rowspan="'+riskItems91.length+'">91贷款明细：</td>';
                        }
                        ruleDetailed=ruleDetailed+'<td  colspan="5"><table>';
                        for (var j=0;j<riskItems91.length;j++){
                            rows=rows+1;
                            if (j==0){
                                ruleDetailed=ruleDetailed+'<tr><td >借款类型</td><td >借款状态</td><td >合同金额</td><td ><td >合同日期</td><td >批贷期数</td><td >还款状态</td><td >欠款金额</td></tr>';
                                ruleDetailed=ruleDetailed+'<tr><td >'+riskItems91[j][0]+'</td><td >'+riskItems91[j][1]+'</td><td >'+riskItems91[j][2]+'</td><td >'+riskItems91[j][3]+'</td><td >'+riskItems91[j][4]+'</td><td >'+riskItems91[j][5]+'</td><td >'+riskItems91[j][6]+'</td></tr>';
                            }else {
                                ruleDetailed=ruleDetailed+'<tr><td >'+riskItems91[j][0]+'</td><td >'+riskItems91[j][1]+'</td><td >'+riskItems91[j][2]+'</td><td >'+riskItems91[j][3]+'</td><td >'+riskItems91[j][4]+'</td><td >'+riskItems91[j][5]+'</td><td >'+riskItems91[j][6]+'</td></tr>';
                            }

                        }
                        ruleDetailed=ruleDetailed+'</table></td>';
                    }else {
                        rows=rows+1;
                    }
                    //ruleResultHtml=ruleResultHtml+'<tr><td rowspan="'+rows+'">命中详情：</td>'+ruleDetailed;
                    ruleResultHtml=ruleResultHtml+ruleDetailed;
                }else {
                    if (ruleResult[i].hitRule&&ruleResult[i].hitRule.length>0&&ruleResult[i].engineId!='286L'){
                        ruleResultHtml=ruleResultHtml+'<tr><td rowspan="'+ruleResult[i].hitRule.length+'">命中详情：</td>';
                        for(var j=0;j<ruleResult[i].hitRule.length;j++){
                            if(j==0){
                                ruleResultHtml=ruleResultHtml+'<td colspan="3">'+ruleResult[i].hitRule[j]+'</td><td colspan="2">命中</td></tr>';
                            }else {
                                ruleResultHtml=ruleResultHtml+'<tr><td colspan="3">'+ruleResult[i].hitRule[j]+'</td><td colspan="2">命中</td></tr>';
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
    if(imageList){
        debugger
        var  holdImg="";//手持身份证
        var  groupImg="";//客户合影
        var  autographImg="";//手签图片
        var  contractImg="";//合同图片
        var  goodsImg="";//提货图片
        for (var i=0;i<imageList.length;i++){
            var image=imageList[i];
            //手持身份证
            if(image.type=='1'){
                holdImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow"></div><p>手持身份证</p><p class="hideTime" style="margin:1em;"></p></li>';
            }
            //客户合影
            if(image.type=='2'){
                groupImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow" ></div><p>客户合影</p><p class="hideTime" style="margin:1em;"></p></li>';
            }
            //手签图片
            if(image.type=='5'){
                autographImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 95%;" src="'+image.imgUrl+'" class="imgShow" ></div><p>手签图片</p><p class="hideTime" style="margin:1em;"></p></li>';
            }
            //合同图片
            if(image.type=='4'){
                contractImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow" ></div><p>合同图片</p><p class="hideTime" style="margin:1em;"></p></li>';
            }
            //提货图片
            if(image.type=='3'){
                goodsImg+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+image.imgUrl+'" class="imgShow" ></div><p>提货图片</p><p class="hideTime" style="margin:1em;"></p></li>';
            }
        }
        $("#holdImg").html(holdImg);
        $("#groupImg").html(groupImg);
        $("#autographImg").html(autographImg);
        if(contractImg!=""){
            $("#contractImg").html(contractImg);
            $("#trContractImg").show();
        }
        if(goodsImg!=""){
            $("#goodsImg").html(goodsImg);
            $("#trGoodsImg").show();
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
        $("#unitTel").text(jobList.companyCode+"-"+jobList.companyPhone);//单位电话
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

    //答案信息
    if(answerList){
        var tabValue,choiceList;
        tabValue+='<tr><td width="8%"  style="text-align: center">可信程度(0-100)</td><td style="text-align: center" colspan="2">'+answerScore+'</td></tr>';
        tabValue+='<tr><td width="8%"  style="text-align: center">序号</td><td width="82%" style="text-align: center">题目内容</td><td width="10%" style="text-align: center">正确答案</td></tr>';
        for (var i=0;i<answerList.length;i++){
            debugger
            var obj=answerList[i];
            choiceList=JSON.parse(obj.choiceList);
            tabValue+='<tr><td width="8%" rowspan="2" style="text-align: center">'+(i+1)+'</td><td width="82%">'+obj.questionTitle+'</td><td rowspan="2">'+obj.correctAnswer+'</td></tr>';
            if (choiceList[0].type=='image'){
                tabValue+='<tr><td width="23%">';
                for (var j=0;j<choiceList.length;j++){
                    tabValue+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  disabled="disabled"  value="'+choiceList[j].code+'" id="radio'+choiceList[j].code+(i+1)+'"  name="radio'+(i+1)+'">'+choiceList[j].code+'、<img style="width: 100px;height: 100px" onclick="showImg(this)" src="'+choiceList[j].value+'"/>';
                }
                tabValue+='</td></tr>';
            }else {
                tabValue+='<tr><td width="23%">';
                for (var j=0;j<choiceList.length;j++){
                    tabValue+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  disabled="disabled"    value="'+choiceList[j].code+'" id="radio'+choiceList[j].code+(i+1)+ '"  name="radio'+(i+1)+'">'+choiceList[j].code+'、'+choiceList[j].value;
                }
                tabValue+='</td></tr>';
            }
        }
        $("#answerCount").val(answerList.length);
        debugger
        $("#answerTable").html(tabValue);
        $("#answerList").show();
        for (var i=0;i<answerList.length;i++){
            var obj=answerList[i];
            var choiceCodeList=obj.choiceCode.split('/');
            for (var j=0;j<choiceCodeList.length;j++){
                var  idName='radio'+choiceCodeList[j]+(i+1);
                //$('#radio'+choiceCodeList[j].code+(i+1)).attr("checked",'true');radioA2
                $('#'+idName).attr("checked",'true');
            }
        }
    }
    //联系人信息
    if(linkmanList){
        var ZXContacts="";
        var QTContacts="";
        for(var i=0;i<linkmanList.length;i++){
            var rel=linkmanList[i].mainSign;//关系
            if(rel=="0"){//直系
                debugger
                if(ZXContacts==''){
                    ZXContacts='<tr> <td colspan="10" class="telRecord">直系联系人</td></tr>';
                    ZXContacts+=' <tr> <td width="10%">关系：</td> <td width="23%">'+linkmanList[i].relationshipName+'</td> <td class="relInfo" width="10%">姓名：</td> <td width="23%">'+linkmanList[i].linkName+'</td> <td width="10%">联系电话：</td> <td width="23%" >'+linkmanList[i].contact+'</td> </tr>';
                }else {
                    ZXContacts+=' <tr> <td width="10%">关系：</td> <td width="23%">'+linkmanList[i].relationshipName+'</td> <td class="relInfo" width="10%">姓名：</td> <td width="23%">'+linkmanList[i].linkName+'</td> <td width="10%">联系电话：</td> <td width="23%" >'+linkmanList[i].contact+'</td> </tr>';
                }
            }else{//同事
                if(QTContacts==''){
                    QTContacts='<tr> <td colspan="10" class="telRecord">其他联系人</td></tr>';
                    QTContacts+=' <tr> <td width="10%">关系：</td> <td width="23%">'+linkmanList[i].relationshipName+'</td> <td class="relInfo" width="10%">姓名：</td> <td width="23%">'+linkmanList[i].linkName+'</td> <td width="10%">联系电话：</td> <td width="23%" >'+linkmanList[i].contact+'</td> </tr>';
                }else {
                    QTContacts+=' <tr> <td width="10%">关系：</td> <td width="23%">'+linkmanList[i].relationshipName+'</td> <td class="relInfo" width="10%">姓名：</td> <td width="23%">'+linkmanList[i].linkName+'</td> <td width="10%">联系电话：</td> <td width="23%" >'+linkmanList[i].contact+'</td> </tr>';
                }
            }
        }
        debugger
        $("#ZXContacts").html(ZXContacts);
        $("#QTContacts").html(QTContacts);
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