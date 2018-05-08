var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.receiveId=userId;
        param.state = '5';
        param.tache = '1';

        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();//客户名称
            param.card = $("input[name='card']").val();//身份证号
            param.tel = $("input[name='tel']").val();//手机号码
            param.orderNo = $("input[name='orderNo']").val();//订单编号
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
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: '2099-06-16 23:59:59',
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
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {"data": null ,"searchable":false,"orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "orderNo","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "productNameName","orderable" : false},
                {"data": "periods","orderable" : false},
                {"data": "state","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data==0) return "未提交订单";
                        if(data==1) return "无效订单";
                        if(data==2) return "已提交订单";
                        if(data==3) return "自动化审核";
                        // if(data==4) return "接受申请贷款";
                        if(data==4) return "人工初审";
                        if(data==5) return "人工终审";
                        if(data==6) return "签约";
                        if(data==7) return "放款申请";
                        if(data=="7.5") return "放款初审";
                        if(data==8) return "放款审核";
                        if(data==9) return "还款中";
                        else return "";
                    }
                },
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {
                var btn=$('<a class="tabel_btn_style" style="text-decoration: none;color: #307ecc;" onclick="auditOrder(\''+data.id+'\',\''+data.customerId+'\',0)">审核</a>');
                return $("td", row).eq(8).append(btn);
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
function auditOrder(orderId,customerId,sing){
    $("#customerId").val(customerId);
    $("#orderId").val(orderId);
    layer.open({
        type : 1,
        title : '审核订单及客户资料',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#container'),
        btn : [ '取消' ],
        success : function(index, layero) {
            $(".trFirst").nextAll().remove();
            $("form[id^=form-]").remove();
            var html='<div><input type="hidden" class="imgHidden"><form action="" enctype="multipart/form-data"><input type="hidden" name="id" value=""><div class="imagediv"><input type="file" name="pic" class="picShow" onchange="setImagePreview(this)"><img class="addMaterial" src="../resources/images/photoadd.png"></div></form><input type="hidden" class="imgHidden" value="1"><input type="hidden" class="imgHidden" value="40"></div>';
            $("#yyzz").empty().append(html);
            Comm.ajaxPost("customerAudit/getOrderById",orderId,function(result){
                var orderData = result.data;
                var score = orderData.scoreCard;
                if(score){
                    score=Number(score);
                    if(score>=500){
                        $("#scoreCard").text(orderData.scoreCard);//信用评分
                        $('#totalscore').html(orderData.scoreCard);//评分卡评分
                        $("#isPass").html("通过");//信用评分状态
                        $("#checkResult").html("通过");//审核结果
                        $("#rulesResult").html("通过");//规则集结果
                        $("#scoreResult").html("通过");//评分卡结果
                    }else if(score=="-1"){
                        $("#scoreCard").text("");//信用评分
                        $("#isPass").html("拒绝");//信用评分状态
                        $("#checkResult").html("拒绝");//审核结果
                        $("#rulesResult").html("拒绝");//规则集结果
                        $("#scoreResult").html("拒绝");//评分卡结果
                    }else if(score<500){
                        $("#scoreCard").text(orderData.scoreCard);//信用评分
                        $('#totalscore').html(orderData.scoreCard);//评分卡评分
                        $("#isPass").html("建议拒绝");//信用评分状态
                        $("#checkResult").html("建议拒绝");//审核结果
                        $("#rulesResult").html("建议拒绝");//规则集结果
                        $("#scoreResult").html("建议拒绝");//评分卡结果
                    }
                }else{
                    $("#scoreCard").text("0");//信用评分
                    $('#totalscore').html("0");//评分卡评分
                    $("#isPass").html("建议拒绝");//信用评分状态
                    $("#checkResult").html("建议拒绝");//审核结果
                    $("#rulesResult").html("建议拒绝");//规则集结果
                    $("#scoreResult").html("建议拒绝");//评分卡结果
                }
                // if(score) {
                //     score = JSON.parse(score);
                //     var inlists = score.data[0].resultJson[0].inFields;
                //     var html='';
                //     var totalScore=0;
                //     var phoneTimeScore=0;
                //     for (var i=0;i<inlists.length;i++) {
                //         if(inlists[i].fieldName=="手机在网时长"){
                //             phoneTimeScore=inlists[i].calcValue;
                //         }else{
                //             html+='<tr>';
                //             html+='<td>'+inlists[i].fieldName+'</td>';
                //             html+='<td>'+inlists[i].calcValue+'</td>';
                //             html+='</tr>';
                //         }
                //     }
                //     html+='<tr rowspan="3">';
                //     html+='<td>手机号码实名是否一致</td>';
                //     html+='<td>30</td>';
                //     html+='</tr>';
                //     html+='<tr>';
                //     html+='<td>手机在网时长</td>';
                //     html+='<td>'+phoneTimeScore+'</td>';
                //     html+='</tr>';
                //     html+='<tr>';
                //     html+='<td>鹏元学历</td>';
                //     html+='<td>10</td>';
                //     html+='</tr>';
                //     $('#scoreCardBody').empty();
                //     $('#scoreCardBody').append(html);
                //     $('#totalscore').html(score.score);
                //     //getNothing(customerId);
                // }
                $("#scoreCard").text(score.score);//信用评分
                $("#precredit").text(orderData.precredit);//预授信金额
                //初审结果
                $('#giveAmount').val(orderData.firstCredit);//授信金额
                $('#paymentRatio').val(orderData.paymentRatio);//付款比例
                //客户情况简述备注
                $('#customerRemark').val(orderData.customerRemark);
                //合理性判断
                $("#merchantId").val(orderData.merchantId);//商户id
                $("#merchantNameHidden").val(orderData.merchantName);//商户名称
                $("input[name=ReasonableInput_fitment]").val(orderData.merchantName);//商户名称
                $("input[name=ReasonableInput_fitment_money]").val(orderData.predictPrice);//预授信金额
                $("#provincesId").val(orderData.provincesId)//额度匹配省id
                $("#cityId").val(orderData.cityId)//额度匹配市id
                //贷款明细
                $("#proType").val(orderData.productTypeName);//产品类型
                $("#proApproval").val(orderData.manager);//当前审批人
                $("#proLoan").val(orderData.orderNo);//贷款号
                $("#proState").val("借款申请");//状态(审核的是借款申请的订单的表)
                $("#proTel").val(orderData.tel);//手机号码
                $("#proApplytime").val(formatTime(orderData.creatTime));//申请时间
                $("#proSum").val(orderData.applayMoney);//申请金额
                $("#proDeadline").val(orderData.periods);//申请期限
                $("#proSource").val(orderData.bak);//GSP定位地址
                $("#phoneGps").empty().html(orderData.bak);//手机GPS定位
                var personList,linkmanList,jobList,propertyList,imageList,host,orderImageList;
                Comm.ajaxPost("customer/customerDetailsSP",orderData.customerId,function(result){
                    var data = result.data;
                    host = data.host;//上下文
                    $('#host').val(host);
                    personList=data.personList[0];//个人信息
                    linkmanList=data.linkmanList;//联系人信息
                    jobList=data.jobList[0];//工作信息
                    propertyList=data.propertyList[0];//房产信息
                    imageList=data.imageList;//上传资料
                    showCumIng(imageList,host);
                }, "application/json",null,null,null,false);
                //查询该订单下的审核人员上传的图片信息
                Comm.ajaxPost("customerAudit/imgDetails",orderData.id,function(result){
                    var data = result.data;
                    console.log(data);
                    orderImageList=data.imageList;//上传资料
                    showOrderImgList(orderImageList);
                }, "application/json");

                Comm.ajaxPost("customer/findOne",orderData.customerId,function(result){
                    var customer = result.data;
                    if(customer && customer.cardType){
                        $("#bank_card").val(customer.cardType);//银行卡号
                    }
                }, "application/json");
                //申请人信息
                $("#applyName").val(orderData.customerName);//姓名
                $("#applyIdcard").val(orderData.card);//身份证号码
                if(personList){
                    $("#applyMerry").val(personList.marry_name);//婚姻状况
                    $("#applyCensus").val(personList.residence);//户籍
                    $("#applyAddr").val(personList.live_address);//当前住址

                    //$("#bank_card").val(personList.bank_card);//银行卡号
                    /*$("#account_city").val(personList.account_city);//开户地址
                    $("#account_bank").val(personList.account_bank);//开户银行*/

                    $("#education").val(personList.educational_name);//学历
                }
                //待装修房产信息
                if(propertyList){
                    $("#limitName").val(propertyList.cellName);
                    $("#houseArea").val(propertyList.proArea);//房产面积
                    $("#houseFrame").val(propertyList.structure);//户型结构
                    $("#housePurcha").val(propertyList.method);//购买方式
                    $("#houseAddr").val(propertyList.proAddress);//房产地址
                    $("#houseBank").val(propertyList.mortgageBank);//按揭银行
                    $("#houseSum").val(propertyList.amount);//月供金额
                    $("#houseDone").val(propertyList.year + "年" + propertyList.month + "月");// 已按揭___年___月
                }
                amountBtn();
                //单位信息
                if(jobList){
                    $("#unitName").val(jobList.companyName);//单位名称
                    $("#unitPro").val(jobList.companyProperty);//单位性质
                    $("#unitTel").val(jobList.companyPhone);//单位电话
                    var unitAddr=jobList.companyAddress+jobList.address;
                    $("#unitAddr").val(unitAddr.replace(/\//g, ''));//单位地址
                    $("#unitDepart").val(jobList.department);//部门
                    $("#unitGrade").val(jobList.posLevel);//职级
                    if(jobList.hiredate){
                        hiredate=jobList.hiredate.substring(0,4)+"-"+jobList.hiredate.substring(4,6)+"-"+jobList.hiredate.substring(6,8)
                    }else{
                        hiredate='';
                    }
                    $("#unitYear").val(hiredate);//现单位入职时间/注册时间
                    $("#unitDeliverty").val(jobList.payType);//工资发放形式
                    $("#unitIncome").val(jobList.monthIncome);//月收入
                    $("#unitDay").val(jobList.monthPayday);//发薪日
                    $("#unitSocial").val(jobList.fundSocialsec);//是否缴纳公积金及社保
                }
                //联系人信息
                if(linkmanList){
                    for(var i=0;i<linkmanList.length;i++){
                        var rel=linkmanList[i].mainSign;//关系
                        if(rel=="0"){//直系
                            $("#directName").val(linkmanList[i].linkName);//姓名(直系)
                            $("#directRel").val(linkmanList[i].relationshipName);//关系名称
                            $("#directUnit").val(linkmanList[i].workCompany);//工作单位
                            $("#directTel").val(linkmanList[i].contact);//联系电话
                            $("#knownLoanName").html(linkmanList[i].knownLoanName);//是否知晓此借款
                        }else{//同事
                            if(rel=="1"){
                                $("#workerName").val(linkmanList[i].linkName);//姓名(同事)
                                $("#workerDepart").val(linkmanList[i].department);//部门？
                                $("#workerPost").val(linkmanList[i].job);//职务？
                                $("#workerTel").val(linkmanList[i].contact);//联系电话
                            }
                        }
                    }
                }
                //反欺诈备注和状态
                $("#cheatRemark").text(orderData.antifraudRemark);
                $("#photoRemark").text(orderData.photoRemark);//拍客备注
                if(orderData.antifraudState==0 || orderData.antifraudState=="") $("#cheatState").html("未核查");
                if(orderData.antifraudState==1) $("#cheatState").html("通过");
                if(orderData.antifraudState==2) $("#cheatState").html("拒绝");
            }, "application/json");
            Comm.ajaxPost("finalAudit/finallist",'orderId='+orderId,function(result){
                //客户银行卡信息
                var accountInfo = result.data.AccountInfo;
                if(accountInfo){
                    if(accountInfo.bankCard){
                        $("#bankCard").val(accountInfo.bankCard);
                    }
                    if(accountInfo.accountProvince&&accountInfo.accountCity){
                        $("#accountAddress").val(accountInfo.accountProvince+accountInfo.accountCity);
                    }
                    if(accountInfo.accountBank){
                        $("#caccountBank").val(accountInfo.accountBank);
                    }
                }
                //装修合理性
                var decorationList = result.data.decorationList;
                console.log(decorationList);
                for(var i=0;i<decorationList.length;i++){
                    $("#showReasonableContent").show();
                    var materialName=decorationList[i].materialName;
                    var materialPriceResp=decorationList[i].materialPriceResp;//价格装修是否合理
                    var materialCountResp=decorationList[i].materialCountResp;//数量装修是否合理
                    var decorationImg=decorationList[i].decorationImg;//比价图片

                    var host=$('#host').val();
                    var html=''
                    html+='<form action="" id="form-'+materialName+'"><table class="tb_info" style="font-size:12px;margin: .5em 0;cursor: pointer">';
                    html+= '<tbody><tr>';
                    html+='<td class="ReasonableTableTitle">材料名称：</td>';
                    html+='<td class="ReasonableTableTitle"><input class="getMsg" type="text" style="text-align: center" name="materialType'+materialName+'" readonly value="'+materialName+'"></td>';
                    html+='<td class="ReasonableTableTitle">装修价格是否合理：</td>';
                    html+='<td class="ReasonableTableTitle getMsg"><label>';
                    if (materialPriceResp=='0'){
                        html+='<input type="radio" name="IsReasonable-'+materialName+'" value="1" disabled>是</label>';
                        html+='<label>';
                        html+='<input type="radio" name="IsReasonable-'+materialName+'" value="0"  checked>否</label></td>';
                    }else {
                        html+='<input type="radio" name="IsReasonable-'+materialName+'" value="1" checked>是</label>';
                        html+='<label>';
                        html+='<input type="radio" name="IsReasonable-'+materialName+'" value="0" disabled>否</label></td>';
                    }
                    html+='<td class="ReasonableTableTitle">装修材料数量是否合理：</td>';
                    html+='<td class="ReasonableTableTitle getMsg">';
                    html+='<label>';
                    if (materialCountResp=='0'){
                        html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="1" disabled>是</label>';
                        html+='<label>';
                        html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="0" checked>否</label>';
                    }else {
                        html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="1" checked>是</label>';
                        html+='<label>';
                        html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="0" disabled>否</label>';
                    }
                    html+='</td><td class="ReasonableTableTitle">备注：</td><td><label><input class="getMsg" type="text" placeholder="输入备注" name="remark'+materialName+'" value="'+decorationList[i].remark+'"></label></td>';
                    html+='<td class="ReasonableTableTitle">';
                    /* html+='<a href="#####" onclick="showCompare(\''+materialName+'\')" class="yetUpload">比价</a>&nbsp;&nbsp;' ;
                     html+='<input type="file" name="file" class="waitUpload" style="width:180px;display:inline-block;" onchange="javascript:submitImg(this,\''+materialName+'\');">';
                     html+='&nbsp;&nbsp;<a class="waitUpload imgUpload">待上传</a>&nbsp;&nbsp;';
                     html+='<a href="#####" onclick="deleteReasonable(this,\''+materialName+'\')" class="waitUpload">删除</a>';*/
                    if(decorationImg){
                        html+='<img width="50" height="40" src="'+_ctx+"/fintecher_file/"+decorationImg+'" onclick="imgShow(this)"/>';
                        html+='&nbsp;&nbsp;<a class="waitUpload imgUpload" onclick="imgShow1(this)" filename="'+decorationImg+'">预览</a>';
                        html+='&nbsp;&nbsp;<a href="#####" onclick="finaldeleteReasonable(this,\''+materialName+'\')" class="waitUpload">删除</a>';

                    }else{
                        html+='<a href="#####" onclick="showCompare(\''+materialName+'\')" class="yetUpload">比价</a>&nbsp;&nbsp;' ;
                        html+='<input type="file" name="file" class="waitUpload" style="width:140px;display:inline-block;" onchange="javascript:submitImg(this,\''+materialName+'\');">';
                        html+='&nbsp;&nbsp;<a class="waitUpload imgUpload" onclick="imgShow1(this)">待上传</a>&nbsp;&nbsp;';
                        html+='<a href="#####" onclick="deleteReasonable(this,\''+materialName+'\')" class="waitUpload">删除</a>';
                    }
                    html+='</td></tr></tbody></table><input type="hidden" name="customerId" value="'+customerId+'">' ;
                    html+='<input type="hidden" name="orderId" value="'+orderId+'">' ;
                    html+='<input type="hidden" name="materialPriceResp" value="">' ;
                    html+='<input type="hidden" name="materialCountResp" value="">' ;
                    html+='<input type="hidden" name="decorationCountResp" value="">' ;
                    html+='<input type="hidden" name="materialName" value="">' ;
                    html+='<input type="hidden" name="remark" value="">' ;
                    html+='<input type="hidden" name="saveId" value="'+decorationList[i].id+'" class="getMsg">';
                    html+='<input type="hidden" name="fileName" value="'+decorationImg+'" class="getMsg">';
                    html+='</form>';
                    $("#showReasonableContent").prepend(html);

                }

                //电核信息
                var examineList = result.data.examineList[0];
                if(examineList){
                    $("input[name='checkInfo'][value='"+examineList.loan+"']").attr("checked", "checked");
                    $("input[name='checkCondi'][value='"+examineList.job+"']").attr("checked", "checked");
                    $("input[name='checkHouse'][value='"+examineList.house+"']").attr("checked", "checked");
                    $("input[name='checkLoan'][value='"+examineList.liabilities+"']").attr("checked", "checked");
                    $("input[name='checkGlide'][value='"+examineList.flow+"']").attr("checked", "checked");
                    $('#elecRemark').val(examineList.remark);
                }


                //电话
                var investiList = result.data.investiList;
                if(investiList){
                    $("#recordList1").empty();
                    $("#recordList2").empty();
                    $("#recordList3").empty();
                    $("#recordList4").empty();
                    $("#recordList5").empty();
                    $("#recordList6").empty();
                    var list1=new Array(),list2=new Array(),list3=new Array(),list4=new Array(),list5=new Array(),list6=new Array();
                    for(var i=0;i<investiList.length;i++){
                        if(investiList[i].type=='1') list1.push(investiList[i]);
                        if(investiList[i].type=='2') list2.push(investiList[i]);
                        if(investiList[i].type=='3') list3.push(investiList[i]);
                        if(investiList[i].type=='4') list4.push(investiList[i]);
                        if(investiList[i].type=='5') list5.push(investiList[i]);
                        if(investiList[i].type=='6') list6.push(investiList[i]);
                    }
                    if(list1.length==0)addBtn(1,null);
                    if(list2.length==0)addBtn(2,null);
                    if(list3.length==0)addBtn(3,null);
                    if(list4.length==0)addBtn(4,null);
                    if(list5.length==0)addBtn(5,null);
                    if(list6.length==0)addBtn(6,null);
                    for(var i=0;i<investiList.length;i++){
                        var answerState=investiList[i].answerState;
                        var answerTime = formatTime(investiList[i].answerTime);
                        var html='<tr><td class="getMsg">'+answerTime+'</td><td>';
                        if(answerState=="0"){
                            html+='<select name="" class="listenState getMsg"><option value="0" selected>无人接听</option><option value="1">接通</option><option value="2">其它</option></select>';
                        }else if(answerState=="1"){
                            html+='<select name="" class="listenState getMsg"><option value="0">无人接听</option><option value="1" selected>接通</option><option value="2">其它</option></select>';
                        }else if(answerState=="2"){
                            html+='<select name="" class="listenState getMsg"><option value="0">无人接听</option><option value="1">接通</option><option value="2" selected>其它</option></select>';
                        }
                        html+='</td><td><input maxlength="13" type="text" class="answerTel getMsg" style="border:1px solid #ccc;" value="'+investiList[i].answerTel+'"></td>';
                        html+='<td><textarea maxlength="1000" class="answerRemark getMsg" cols="30" rows="2">'+investiList[i].remark+'</textarea></td>';
                        var nothing = '<td><input type="hidden" class="getMsg" fileName="">'+
                            '<form enctype="multipart/form-data" style="margin-left:9px;">'+
                            '<input type="file" name="file" class="waitUpload" style="width:140px;display:inline-block;" onchange="submitAudio(this);">'+
                            '<a href="#####" onclick="deletetTelrecord(this)" class="waitUpload" style="display:none;color:red;font-size: 20px;">×</a>'+
                            '</form></td>';
                        //下载
                        if(investiList[i].soundSrc==""){
                            html+=nothing;
                            html+='<td><button  type="button" class="btn btn-primary deleteBtn"  class="deleteBtn" onclick="answerdelBtn(this)">删除</button></td>';
                        }else{
                            Comm.ajaxPost('customerAudit/isExists',"fileName="+investiList[i].soundSrc,function (result) {
                                if(result.data==0){
                                    html+=nothing;
                                    html+='<td><button  type="button" class="btn btn-primary deleteBtn"  class="deleteBtn" onclick="answerdelBtn(this)">删除</button></td>';
                                }else if(result.data==1){
                                    html+='<td align="center"><input type="hidden" class="getMsg" fileName="'+investiList[i].soundSrc+'">';
                                    html+='<a type="button" class="btn btn-primary deleteBtn"class="deleteBtn"href="downloadSound?fileName='+investiList[i].soundSrc+'">下载</a></td><td></td>';
                                }
                            },null,null,null,null,false);
                        }
                        html+='</tr>';
                        if(investiList[i].type=='1'){//本人电话拨打记录
                            $("#recordList1").prepend(html);
                        }else if(investiList[i].type=='2'){//网络调查
                            $("#recordList2").prepend(html);
                        }else if(investiList[i].type=='3'){//114调查
                            $("#recordList3").prepend(html);
                        }else if(investiList[i].type=='4'){//进件单固
                            $("#recordList4").prepend(html);
                        }else if(investiList[i].type=='5'){//直系联系人通话
                            $("#recordList5").prepend(html);
                        }else if(investiList[i].type=='6'){//同事联系人通话
                            $("#recordList6").prepend(html);
                        }
                    }
                }
                //把第一个删除设为清空
                //$("form[id^='form-']")
                var divList=$("[id^='recordList']");
                for(var i=0;i<divList.length;i++){
                    $(divList[i]).find("button:first").html("清空").removeAttr("onclick").attr("onclick","answerclearBtn(this)");
                }
                //初审意见
                var approveList = result.data.approveList;
                if(approveList){
                    for(var i=0;i<approveList.length;i++){
                        if(approveList[i].state == '5'){
                            $('#advice').text(approveList[i].approveSuggestion);
                        }
                    }
                }
            });
        }
    });
}
//展示风险评估结果
function getNothing(customerId) {
    Comm.ajaxPost("customer/getRisk",{'customerId':customerId},function (result) {
        if(result.code=="0"&&result.data){
            var phoneResk=result.data.phoneResk;
            var resk=result.data.resk;
            if(phoneResk){
                if(phoneResk.onlineTime){
                    $("#phoneTime").html(phoneResk.onlineTime);//手机使用时长
                }else{
                    $("#phoneTime").html("");
                }
                if(phoneResk.bizResponseCode=="1"){//查得 手机号实名验证
                    if(phoneResk.pyIsNameMatchOperato==1){
                        $("#phoneNoCheck").html("一致");
                    }else if(phoneResk.pyIsNameMatchOperato==0){
                        $("#phoneNoCheck").html("不一致");
                    }else if(phoneResk.pyIsNameMatchOperato==-1){
                        $("#phoneNoCheck").html("无此记录");
                    }
                }
            }
        }
    })
}
//点击风险评估到风险评估页面
function crediet(cardId){
    layer.open({
        type : 1,
        title : '风险评估',
        area : [ '100%', '100%' ],
        content : $('#auditFinds'),
        btn : [ '取消' ],
        success : function(index, layero) {
            Comm.ajaxPost("customer/riskdetails","cardNum="+cardId,function(result) {
                var data=result.data;
                var refuse=data.refuse;
                var ruleRisk=data.ruleRisk;
                var scoreCard=data.scoreCard;
                //结果
                if(refuse){
                    $("#first_rule").empty().html(refuse.first_rule!=""?refuse.first_rule:"通过");
                    $("#second_rule").empty().html(refuse.second_rule!=""?refuse.second_rule:"通过");
                    $("#third_rule").empty().html(refuse.third_rule!=""?refuse.third_rule:"通过");
                    $("#fourth_rule").empty().html(refuse.fourth_rule!=""?refuse.fourth_rule:"通过");
                    $("#fifth_rule").empty().html(refuse.fifth_rule!=""?refuse.fifth_rule:"通过");
                    $("#sixth_rule").empty().html(refuse.sixth_rule!=""?refuse.sixth_rule:"通过");
                }else{
                    $("#first_rule").empty().html("通过");
                    $("#second_rule").empty().html("通过");
                    $("#third_rule").empty().html("通过");
                    $("#fourth_rule").empty().html("通过");
                    $("#fifth_rule").empty().html("通过");
                    $("#sixth_rule").empty().html("通过");
                }
                //规则
                if(ruleRisk){
                    $("#htrl").empty().html(ruleRisk.htrl!=""?ruleRisk.htrl:"一致");//活体人脸识别
                    $("#siys").empty().html(ruleRisk.siys!=""?ruleRisk.siys:"一致");//四要素识别
                    $("#phoneReal").empty().html(ruleRisk.phoneReal!=""?ruleRisk.phoneReal:"一致");//手机号实名验证
                    $("#phoneTime").empty().html(ruleRisk.phoneTime!=""?ruleRisk.phoneTime:"36-72");//手机号使用时间

                    $("#pyIfo").empty().html(ruleRisk.pyIfo!=""?ruleRisk.pyIfo:"姓名和身份证号码一致");//鹏元身份信息
                    $("#pyEdu").empty().html(ruleRisk.pyEdu!=""?ruleRisk.pyEdu:"未授权");//鹏元教育信息
                    $("#pyDistress").empty().html(ruleRisk.pyDistress!=""?ruleRisk.pyDistress:"0");//鹏元催欠公告信息条数
                    $("#pyTax").empty().html(ruleRisk.pyTax!=""?ruleRisk.pyTax:"0");//鹏元税务行政执法信息条数
                    $("#pyJudcase").empty().html(ruleRisk.pyJudcase!=""?ruleRisk.pyJudcase:"0");//鹏元司法案例信息条数
                    $("#pyJuddishonesty").empty().html(ruleRisk.pyJuddishonest!=""?ruleRisk.pyJuddishonesty:"0");//鹏元司法失信信息条数
                    $("#pyJudenfor").empty().html(ruleRisk.pyJudenfor!=""?ruleRisk.pyJudenfor:"0");//鹏元司法执行信息条数
                    $("#pyNetloanover").empty().html(ruleRisk.pyNetloanover!=""?ruleRisk.pyNetloanover:"0");//鹏元网贷逾期信息条数

                    $("#tdBhit").empty().html(ruleRisk.tdBhit!=""?ruleRisk.tdBhit:"0");//同盾黑名单命中数量
                    $("#tdLoanproxy").empty().html(ruleRisk.tdLoanproxy!=""?ruleRisk.tdLoanproxy:"否");//同盾借款设备代理识别
                    $("#tdLoantool").empty().html(ruleRisk.tdLoantool!=""?ruleRisk.tdLoantool:"否");//同盾借款设备作弊工具识别
                    $("#tdExtplatform").empty().html(ruleRisk.tdExtplatform!=""?ruleRisk.tdExtplatform:"0");//同盾外部贷款平台个数


                    $("#tdidCriminal").empty().html(ruleRisk.tdidCriminal!=""?ruleRisk.tdidCriminal:"否");//同盾身份证命中法院犯罪通缉名单
                    $("#tdidRisk").empty().html(ruleRisk.tdidRisk!=""?ruleRisk.tdidRisk:"否");//同盾身份证命中高风险关注名单
                    $("#tdidTaxes").empty().html(ruleRisk.tdidTaxes!=""?ruleRisk.tdidTaxes:"否");//同盾身份证命中欠税名单
                    $("#tdidCredit").empty().html(ruleRisk.tdidCredit!=""?ruleRisk.tdidCredit:"否");//同盾身份证命中信贷逾期名单


                    $("#tdphoneRisk").empty().html(ruleRisk.tdphoneRisk!=""?ruleRisk.tdphoneRisk:"否");//同盾手机号命中高风险关注名单
                    $("#tdphoneArrears").empty().html(ruleRisk.tdphoneArrears!=""?ruleRisk.tdphoneArrears:"否");//同盾手机号命中欠款公司法人代表名单
                    $("#tdphoneCommunication").empty().html(ruleRisk.tdphoneCommunication!=""?ruleRisk.tdphoneCommunication:"否");//同盾手机号命中通讯小号库
                    $("#tdphoneCredit").empty().html(ruleRisk.tdphoneCredit!=""?ruleRisk.tdphoneCredit:"否");//同盾手机号命中信贷逾期名单
                    $("#tdphoneFalsenum").empty().html(ruleRisk.tdphoneFalsenum!=""?ruleRisk.tdphoneFalsenum:"否");//同盾手机号命中虚假号码库
                    $("#tdphoneFraudnum").empty().html(ruleRisk.tdphoneFraudnum!=""?ruleRisk.tdphoneFraudnum:"否");//同盾手机号命中诈骗号码库
                    $("#tdcontphoneRisk").empty().html(ruleRisk.tdcontphoneRisk!=""?ruleRisk.tdcontphoneRisk:"否");//同盾联系人手机号命中高风险关注名单
                    $("#tdcontphoneCommunication").empty().html(ruleRisk.tdcontphoneCommunication!=""?ruleRisk.tdcontphoneCommunication:"否");//同盾联系人手机号命中通讯小号库
                    $("#tdcontphoneCredit").empty().html(ruleRisk.tdcontphoneCredit!=""?ruleRisk.tdcontphoneCredit:"否");//同盾联系人手机号命中信贷逾期名单
                    $("#tdcontphoneFalsenum").empty().html(ruleRisk.tdcontphoneFalsenum!=""?ruleRisk.tdcontphoneFalsenum:"否");//同盾联系人手机号命中虚假号码库
                    $("#tdcontphoneFraudnum").empty().html(ruleRisk.tdcontphoneFraudnum!=""?ruleRisk.tdcontphoneFraudnum:"否");//同盾联系人手机号命中诈骗号码库
                    $("#tddgFalsenum").empty().html(ruleRisk.tddgFalsenum!=""?ruleRisk.tddgFalsenum:"否");//同盾单固命中虚假号码库
                    $("#tddgFraudnum").empty().html(ruleRisk.tddgFraudnum!=""?ruleRisk.tddgFraudnum:"否");//同盾单固命中诈骗号码库
                    $("#tddgAgencynum").empty().html(ruleRisk.tddgAgencynum!=""?ruleRisk.tddgAgencynum:"否");//同盾单固命中中介号码库

                    $("#shzxLoan").empty().html(ruleRisk.shzxLoan!=""?ruleRisk.shzxLoan:"0");//上海资信查询所有贷款笔数
                    $("#shzxOutloan").empty().html(ruleRisk.shzxOutloan!=""?ruleRisk.shzxOutloan:"0");//上海资信查询未结清贷款数量
                    $("#shzxOverdue").empty().html(ruleRisk.shzxOverdue!=""?ruleRisk.shzxOverdue:"0");//上海咨询查询逾期数量
                    $("#shzxLinecredit").empty().html(ruleRisk.shzxLinecredit!=""?ruleRisk.shzxLinecredit:"0");//上海资信查询共享授信额度总和
                    $("#shzxMonth").empty().html(ruleRisk.shzxMonth!=""?ruleRisk.shzxMonth:"0");//上海资信查询月供

                    $("#accumulation").empty().html(ruleRisk.accumulation!=""?ruleRisk.accumulation:"未授权");//公积金授权
                    $("#social").empty().html(ruleRisk.social!=""?ruleRisk.social:"未授权");//社保授权
                    $("#alipayRealname").empty().html(ruleRisk.alipayRealname!=""?ruleRisk.alipayRealname:"未授权");//支付宝实名验证
                    $("#sesameSeeds").empty().html(ruleRisk.sesameSeeds!=""?ruleRisk.sesameSeeds:"未授权");//芝麻分

                }else{
                    $("#htrl").empty().html("一致");//活体人脸识别
                    $("#siys").empty().html("一致");//四要素识别
                    $("#phoneReal").empty().html("一致");//手机号实名验证
                    $("#phoneTime").empty().html("36-72");//手机号使用时间


                    $("#pyIfo").empty().html("姓名和身份证号码一致");//鹏元身份信息
                    $("#pyEdu").empty().html("未授权");//鹏元教育信息
                    $("#pyDistress").empty().html("0");//鹏元催欠公告信息条数
                    $("#pyTax").empty().html("0");//鹏元税务行政执法信息条数
                    $("#pyJudcase").empty().html("0");//鹏元司法案例信息条数
                    $("#pyJuddishonesty").empty().html("0");//鹏元司法失信信息条数
                    $("#pyJudenfor").empty().html("0");//鹏元司法执行信息条数
                    $("#pyNetloanover").empty().html("0");//鹏元网贷逾期信息条数

                    $("#tdBhit").empty().html("0");//同盾黑名单命中数量
                    $("#tdLoanproxy").empty().html("否");//同盾借款设备代理识别
                    $("#tdLoantool").empty().html("否");//同盾借款设备作弊工具识别
                    $("#tdExtplatform").empty().html("0");//同盾外部贷款平台个数


                    $("#tdidCriminal").empty().html("否");//同盾身份证命中法院犯罪通缉名单
                    $("#tdidRisk").empty().html("否");//同盾身份证命中高风险关注名单
                    $("#tdidTaxes").empty().html("否");//同盾身份证命中欠税名单
                    $("#tdidCredit").empty().html("否");//同盾身份证命中信贷逾期名单


                    $("#tdphoneRisk").empty().html("否");//同盾手机号命中高风险关注名单
                    $("#tdphoneArrears").empty().html("否");//同盾手机号命中欠款公司法人代表名单
                    $("#tdphoneCommunication").empty().html("否");//同盾手机号命中通讯小号库
                    $("#tdphoneCredit").empty().html("否");//同盾手机号命中信贷逾期名单
                    $("#tdphoneFalsenum").empty().html("否");//同盾手机号命中虚假号码库
                    $("#tdphoneFraudnum").empty().html("否");//同盾手机号命中诈骗号码库
                    $("#tdcontphoneRisk").empty().html("否");//同盾联系人手机号命中高风险关注名单
                    $("#tdcontphoneCommunication").empty().html("否");//同盾联系人手机号命中通讯小号库
                    $("#tdcontphoneCredit").empty().html("否");//同盾联系人手机号命中信贷逾期名单
                    $("#tdcontphoneFalsenum").empty().html("否");//同盾联系人手机号命中虚假号码库
                    $("#tdcontphoneFraudnum").empty().html("否");//同盾联系人手机号命中诈骗号码库
                    $("#tddgFalsenum").empty().html("否");//同盾单固命中虚假号码库
                    $("#tddgFraudnum").empty().html("否");//同盾单固命中诈骗号码库
                    $("#tddgAgencynum").empty().html("否");//同盾单固命中中介号码库

                    $("#shzxLoan").empty().html("0");//上海资信查询所有贷款笔数
                    $("#shzxOutloan").empty().html("0");//上海资信查询未结清贷款数量
                    $("#shzxOverdue").empty().html("0");//上海咨询查询逾期数量
                    $("#shzxLinecredit").empty().html("0");//上海资信查询共享授信额度总和
                    $("#shzxMonth").empty().html("0");//上海资信查询月供

                    $("#accumulation").empty().html("未授权");//公积金授权
                    $("#social").empty().html("未授权");//社保授权
                    $("#alipayRealname").empty().html("未授权");//支付宝实名验证
                    $("#sesameSeeds").empty().html("未授权");//芝麻分
                }
                //评分卡
                if(scoreCard){
                    $("#sex").empty().html(scoreCard.sex!=""?scoreCard.sex:"0");//性别
                    $("#age").empty().html(scoreCard.age!=""?scoreCard.age:"0");//年龄
                    $("#marry").empty().html(scoreCard.marry!=""?scoreCard.marry:"0");//婚姻状况
                    $("#hjszqy").empty().html(scoreCard.hjszqy!=""?scoreCard.hjszqy:"0");//户籍所在区域
                    $("#dqjzqk").empty().html(scoreCard.dqjzqk!=""?scoreCard.dqjzqk:"0");//当前居住情况

                    $("#xdwgznx").empty().html(scoreCard.xdwgznx!=""?scoreCard.xdwgznx:"0");//现单位工作年限
                    $("#dwxz").empty().html(scoreCard.dwxz!=""?scoreCard.dwxz:"0");//单位性质
                    $("#sfjngjjjsb").empty().html(scoreCard.sfjngjjjsb!=""?scoreCard.sfjngjjjsb:"0");//是否缴纳公积金及社保
                    $("#rank").empty().html(scoreCard.rank!=""?scoreCard.rank:"0");//职级
                    $("#gzffxs").empty().html(scoreCard.gzffxs!=""?scoreCard.gzffxs:"0");//工资发放形式


                    $("#gmfs").empty().html(scoreCard.gmfs!=""?scoreCard.gmfs:"0");//购买方式
                    $("#yaj").empty().html(scoreCard.yaj!=""?scoreCard.yaj:"0");//已按揭


                    $("#pyxl").empty().html(scoreCard.pyxl!=""?scoreCard.pyxl:"0");//学历（APP自填）
                    $("#sjhmsmsfyz").empty().html(scoreCard.sjhmsmsfyz!=""?scoreCard.sjhmsmsfyz:"0");//手机号码实名验证
                    $("#azthrj").empty().html(scoreCard.azthrj!=""?scoreCard.azthrj:"0");//APP安装同行软件
                    $("#shzxwjqdksl").empty().html(scoreCard.shzxwjqdksl!=""?scoreCard.shzxwjqdksl:"0");//上海资信未结清贷款数量
                    $("#tdwbdkptgs").empty().html(scoreCard.tdwbdkptgs!=""?scoreCard.tdwbdkptgs:"0");//同盾外部贷款平台个数
                    $("#sqgjj").empty().html(scoreCard.sqgjj!=""?scoreCard.sqgjj:"0");//授权公积金
                    $("#sqtxl").empty().html(scoreCard.sqtxl!=""?scoreCard.sqtxl:"0");//授权通讯录
                    $("#sqthjl").empty().html(scoreCard.sqthjl!=""?scoreCard.sqthjl:"0");//授权通话记录
                    $("#zmf").empty().html(scoreCard.zmf!=""?scoreCard.zmf:"0");//芝麻分
                    $("#dw").empty().html(scoreCard.dw!=""?scoreCard.dw:"0");//GPS定位
                    $("#sjhsysc").empty().html(scoreCard.sjhsysc!=""?scoreCard.sjhsysc:"0");//手机号使用时长

                    $("#djzc").empty().html(scoreCard.djzc!=""?scoreCard.djzc:"0");//点击注册/利息细则
                    $("#gzdwmcxg").empty().html(scoreCard.gzdwmcxg!=""?scoreCard.gzdwmcxg:"0");//工作单位名称修改
                    $("#gzdwdhxg").empty().html(scoreCard.gzdwdhxg!=""?scoreCard.gzdwdhxg:"0");//工作单位电话修改
                    $("#lxrdhxg").empty().html(scoreCard.lxrdhxg!=""?scoreCard.lxrdhxg:"0");//联系人电话修改
                    $("#xbxldwcs").empty().html(scoreCard.xbxldwcs!=""?scoreCard.xbxldwcs:"0");//性别+学历+单位+城市
                }else{
                    $("#sex").empty().html("0");//性别
                    $("#age").empty().html("0");//年龄
                    $("#marry").empty().html("0");//婚姻状况
                    $("#hjszqy").empty().html("0");//户籍所在区域
                    $("#dqjzqk").empty().html("0");//当前居住情况

                    $("#xdwgznx").empty().html("0");//现单位工作年限
                    $("#dwxz").empty().html("0");//单位性质
                    $("#sfjngjjjsb").empty().html("0");//是否缴纳公积金及社保
                    $("#rank").empty().html("0");//职级
                    $("#gzffxs").empty().html("0");//工资发放形式


                    $("#gmfs").empty().html("0");//购买方式
                    $("#yaj").empty().html("0");//已按揭


                    $("#pyxl").empty().html("0");//学历（APP自填）
                    $("#sjhmsmsfyz").empty().html("0");//手机号码实名验证
                    $("#azthrj").empty().html("0");//APP安装同行软件
                    $("#shzxwjqdksl").empty().html("0");//上海资信未结清贷款数量
                    $("#tdwbdkptgs").empty().html("0");//同盾外部贷款平台个数
                    $("#sqgjj").empty().html("0");//授权公积金
                    $("#sqtxl").empty().html("0");//授权通讯录
                    $("#sqthjl").empty().html("0");//授权通话记录
                    $("#zmf").empty().html("0");//芝麻分
                    $("#dw").empty().html("0");//GPS定位
                    $("#sjhsysc").empty().html("0");//手机号使用时长

                    $("#djzc").empty().html("0");//点击注册/利息细则
                    $("#gzdwmcxg").empty().html("0");//工作单位名称修改
                    $("#gzdwdhxg").empty().html("0");//工作单位电话修改
                    $("#lxrdhxg").empty().html("0");//联系人电话修改
                    $("#xbxldwcs").empty().html("0");//性别+学历+单位+城市
                }
            })
        }
    })
}
function formatTime(t){
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}
//拨打记录添加
function addBtn(flag,me){
    $(me).parents("table").find(".delLeft").removeClass("delLeft");
    var html='<tr>';
    html+='<td class="getMsg">'+getFirstTime()+'</td><td>'
    html+='<select name="" class="listenState getMsg"> <option value="0">无人接听</option><option value="1">接通</option> <option value="2">其它</option></select>';
    html+='</td><td><input type="text" class="answerTel getMsg" style="border:1px solid #ccc;"></td>';
    html+='<td><textarea maxlength="1000" class="answerRemark getMsg" cols="30" rows="2"></textarea></td>';
    html+='<td class="ReasonableTableTitle">';
    html+='<input type="hidden" class="getMsg" fileName="">';
    html+='<form action="" enctype="multipart/form-data" style="margin-left:9px;">';
    html+='<input type="file" name="file" class="waitUpload" style="width:140px;display:inline-block;" onchange="javascript:submitAudio(this);">';
    html+='<a href="#####" onclick="deletetTelrecord(this)" class="waitUpload" style="display:none;color:red;font-size: 20px;">×</a>';
    html+='</form>';
    html+='</td>';
    html+='<td>';
    html+='<button type="button" class="btn btn-primary deleteBtn"  class="deleteBtn" onclick="answerdelBtn(this)">删除</button>';
    html+='</td>';
    html+='</tr>';
    $("#recordList"+flag).append(html);
}
//额度匹配
function amountBtn(){
    var param={};
    param.cellName=$("#limitName").val();
    // param.provincesId=$("#provincesId").val();
    // param.cityId=$("#cityId").val();暂时不用省市id
    Comm.ajaxPost("customerAudit/getMatchingPrice",JSON.stringify(param),function(data){
        console.log(data.data.length)
        if(data.data.length<=0){
            $("#limitPrice").val("45000");//小区平均房价
            $("#limitDeco").val("2100");//小区平均装修价格
        }else{
            if(data.data[0].averageHousePrice){
                $("#limitPrice").val(data.data[0].averageHousePrice);//小区平均房价
            }else{
                $("#limitPrice").val("45000");//小区平均房价
            }
            if(data.data[0].averageRenovationPrice){
                $("#limitDeco").val(data.data[0].averageRenovationPrice  );//小区平均装修价格
            }else{
                $("#limitDeco").val("2100");//小区平均装修价格
            }
            $("#cmId").val(data.data[0].id);
        }
    }, "application/json");
}
//客户电核信息
function elecButton(){
    var loan=$('input[name="checkInfo"]:checked').next().val();//借款信息核实
    var job=$('input[name="checkCondi"]:checked').next().val();//工作情况核实
    var house=$('input[name="checkHouse"]:checked').next().val();//房产情况核实
    var liabilities=$('input[name="checkLoan"]:checked').next().val();//负债信息核实
    var flow=$('input[name="checkGlide"]:checked').next().val();//流水核查
    var remark=$("#elecRemark").val();//备注
    var param={};
    param.orderId=$("#orderId").val();//订单id
    param.customerId= $("#customerId").val();//客户id
    param.loan=loan;//借款信息核实
    param.job=job;//工作情况核实
    param.house=house;//房产情况核实
    param.liabilities=liabilities;//负债信息核实
    param.flow=flow;//流水核查
    param.remark=remark;//备注
    Comm.ajaxPost("customerAudit/addCustomerExamine",JSON.stringify(param),function(data){

    }, "application/json");
}
//通信记录
function callBtn(me,type){
    var answerContent=$(me).parent().parent().next().find(".answerContent").val()
    if(answerContent==""||answerContent==null){
        layer.msg("接听内容不能为空",{time:2000});
        return;
    }
    var param={};
    param.orderId=$("#orderId").val();//订单id
    param.customerId= $("#customerId").val();//客户id
    param.answerContent=$(me).parent().parent().next().find(".answerContent").val();//接听内容
    param.answerState=$(me).parent().parent().find(".listenState option:selected").val();//接听状态
    param.type=type;//调查类型
    param.remark=$(me).parent().parent().next().next().find(".answerRemark").val();//备注
    Comm.ajaxPost("customerAudit/addInvestigation",JSON.stringify(param),function(data){
        layer.msg(data.msg,{time:2000},function(){
            $("input[name='contactId']").val(data.data);
        });
    }, "application/json");
}
//删除通讯录
function answerdelBtn(me){
    $(me).parent().parent().remove();
    var fileName=$(me).parent().prev().find('input').attr("fileName");
    console.log(fileName);
    if(fileName!==""&&fileName!=undefined&&fileName!=null){
        if(fileName){
            Comm.ajaxPost('customerAudit/deleteFile',"fileName="+fileName,function (result) {
                console.log(result);
                if(result.code==0){
                    layer.msg("删除成功",{time:2000},function () {
                        $(me).parent().prev().children('form').empty().append('<input type="file" name="file" class="waitUpload getMsg" filename="" style="width:140px;display:inline-block;" onchange="javascript:submitAudio(this);"><a href="#####" onclick="deletetTelrecord(this)" class="waitUpload" style="display:none;color:red;font-size: 20px;">×</a>')
                    });
                }
            });
        }
    }
}
//发欺诈资料
$('#addV3Product').ajaxSubmit({
    type: "POST",
    url: "addProductSeries",
    success: function (data) {
        data = eval('('+data+')');
        layer.msg(data.msg,{time:2000},function(){
            layer.closeAll();
            var id=$("#v3ParentId").val();
            clickPrdType(id)
        });
    },
    error: function (XMLHttpRequest, textStatus, thrownError) {
    }
});
//收缩目录
function shrink(me){
    $(me).next().slideToggle();
}
//清空
function answerclearBtn(me){
    $(me).parent().prev().prev().prev().children().val("");
    $(me).parent().prev().prev().children().val("");
    var fileName=$(me).parent().prev().find('input').attr("fileName");
    if(fileName!==""&&fileName!=undefined&&fileName!=null){
        if(fileName){
            Comm.ajaxPost('customerAudit/deleteFile',"fileName="+fileName,function (result) {
                var html="";
                html+='<input type="hidden" class="getMsg" fileName="">';
                html+='<form action="" enctype="multipart/form-data" style="margin-left:9px;">'
                html+='<input type="file" name="file" class="waitUpload" style="width:140px;display:inline-block;margin-left:0px" onchange="javascript:submitAudio(this);">'
                html+='<a href="#####" onclick="deletetTelrecord(this)" class="waitUpload" style="color:red;font-size: 20px;position: relative;top: 2px;display: none;">×</a>'
                html+='</form>'
                if(result.code==0){
                    layer.msg("删除成功",{time:2000},function () {
                        $(me).parent().prev().empty().append(html);
                    });
                }
            });
        }
    }
}
//备注
function remarkBtn(){
    var param={};
    param.customerRemark=$("#customerRemark").val();//备注
    param.id=$("#orderId").val();//订单id
    Comm.ajaxPost("customerAudit/addCustomerRemark",JSON.stringify(param),function(data){
        layer.msg(data.msg,{time:2000});
    }, "application/json");
}
//通过
function thoughButton(){
    var though=layer.open({
        type : 1,
        title : "输入授信金额",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '600px', '' ],
        offset: '100px',
        content : $('#Though_Sum'),
        btn : [ '保存', '取消' ],
        yes: function (index, layero) {
            var param={};
            param.firstCredit=$('#firstCredit').val();//初审授信额度
            param.approveSuggestion=$("#passRemark").val();//审批意见
            param.state=5;
            param.tache=1;
            param.handlerId=$("#userId").val();
            param.handlerName=$("#handName").val();
            param.orderId=$("#orderId").val();//订单id
            param.customerId= $("#customerId").val();//客户id
            Comm.ajaxPost("customerAudit/audit",JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:2000});
            }, "application/json");
        }
    });
    /* var param={};
     param.*/
}

function getTime(time) {
    var arr1=time.split(" ");
    var y=arr1[0].split("-");
    var t=arr1[1].split(":");
    return newTime=y[0]+y[1]+y[2]+t[0]+t[1]+t[2];
}
$(function(){
    $("#checkState").change(function(){
        if($("#checkState option:selected").val()==0){
            $("#checkState").parent().next().hide();
            $("#checkState").parent().next().next().hide();
        }else{
            $("#checkState").parent().next().show();
            $("#checkState").parent().next().next().show();
        }
    })
    $("#finalcheckState").change(function(){
        if($("#finalcheckState option:selected").val()==0){
            $("#finalcheckState").parent().next().hide();
            $("#finalcheckState").parent().next().next().hide();
        }else{
            $("#finalcheckState").parent().next().show();
            $("#finalcheckState").parent().next().next().show();
        }
    })
})

$().ready(function () {
    $(".showBigImg").hover(function(){
        $(this).parent().next().children("img").attr("src",$(this).attr("src"));
        $(this).parent().next().show();
    },function(){
        $(this).parent().next().hide();
    });
})
// 展示客户图片
function showCumIng(imgList,host) {

    var html="";
    for(var i=0;i<imgList.length;i++){
        if(imgList[i].type== "9"){//身份证
            var time=formatTime(imgList[i].creatTime);
            if(imgList[i].isfront=="0"){
                html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>身份证正面</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
            }else{
                html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>身份证反面</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "114"){//手持身份证明
            var time=formatTime(imgList[i].creatTime);
            if(imgList[i].isfront=="0"){
                html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>手持身份证明正面</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
            }else{
                html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>手持身份证明反面</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "112"){//工作证明
            var time=formatTime(imgList[i].creatTime);
            html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>工作证明</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
        if(imgList[i].type== "115"){//房产证明
            var time=formatTime(imgList[i].creatTime);
            html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>房产证明</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
        if(imgList[i].type== "116"){//装修合同
            var time=formatTime(imgList[i].creatTime);
            html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>装修合同</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
        if(imgList[i].type== "117"){//装修清单
            var time=formatTime(imgList[i].creatTime);
            html+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>装修清单</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
    }
    $("#showNewImg ul").empty();
    $("#showNewImg ul").append(html);
}
//展示审核人员上传的图片
function showOrderImgList(imgList){
    var html39 = "";
    var html40='';
    var FanQizha="";
    var PaiKe="";
    for(var i=0;i<imgList.length;i++) {
        if (imgList[i].type == "39") {
            var time=formatTime(imgList[i].creatTime);
            html39+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height:100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>初审审核材料</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
        if(imgList[i].type== "8"){//拍客资料
            var time=formatTime(imgList[i].creatTime);
            PaiKe+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height:100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>拍客资料</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
        if(imgList[i].type== "7"){//反欺诈资料
            var time=formatTime(imgList[i].creatTime);
            FanQizha+='<li><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height:100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>反欺诈资料</p><p style="margin:1em;" class="hideTime">'+time+'</p></li>';
        }
        if(imgList[i].type == "40"){//终审待处理 之前保存的终审人员上传图片
            html40+='<div class="getFanQiZha"><input type="hidden" class="imgHidden" value="'+imgList[i].src+'"><form action="" enctype="multipart/form-data"><input type="hidden" name="id" value="'+imgList[i].customerId+'"><div class="imagediv"><input type="file" name="pic" class="picShow" onchange="setImagePreview(this)"><img class="addMaterial" src="'+_ctx+imgList[i].src+'" onclick="imgShow(this)"><span class="closeImg" onclick="closeDelete(this)">×</span></div></form><input type="hidden" class="imgHidden" value="1"><input type="hidden" class="imgHidden" value="40"></div>';
        }
    }
    $("#cszl").empty();
    $("#cszl").append(html39);
    $("#fanqzContainer ul").empty();
    $("#fanqzContainer ul").append(FanQizha);
    $("#paikeContainer ul").empty();
    $("#paikeContainer ul").append(PaiKe);
    $('#yyzz').prepend(html40);
}
// 展示客户图片
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
//图片预览
function imgShow(me){
    window.open($(me).attr("src"),"图片预览");
}

//总体保存
function saveAll(flag){
    var orderId = $("#orderId").val();
    var customerId = $("#customerId").val();
    //额度匹配
    var cmParam={};
    var cmId = $("#cmId").val();
    if(cmId!=null && cmId!=''){
        cmParam.cmId=cmId;
    }
    cmParam.cellName=$("#limitName").val();
    cmParam.limitPrice=$("#limitPrice").val();
    cmParam.limitDeco=$("#limitDeco").val();
    //电核信息
    var ceParam={};
    var loan=$('input[name="checkInfo"]:checked').val();//借款信息核实
    var job=$('input[name="checkCondi"]:checked').val();//工作情况核实
    var house=$('input[name="checkHouse"]:checked').val();//房产情况核实
    var liabilities=$('input[name="checkLoan"]:checked').val();//负债信息核实
    var flow=$('input[name="checkGlide"]:checked').val();//流水核查
    var remark=$("#elecRemark").val();//备注
    ceParam.loan=loan;//借款信息核实
    ceParam.job=job;//工作情况核实
    ceParam.house=house;//房产情况核实
    ceParam.liabilities=liabilities;//负债信息核实
    ceParam.flow=flow;//流水核查
    ceParam.remark=remark;//备注
    //客户情况简述备注
    var customerRemark=$("#customerRemark").val();
    //客户通信调查
    var optionPersonTelListTr=$("#recordList1 tr");//个人通话
    var cinParam="[";
    $(optionPersonTelListTr).each(function (index,elem) {
        cinParam+="{\"type\":1,";
        $(elem).find(".getMsg").each(function(n,dom){
            if(n==0){
                var times=getTime($(dom).html());
                cinParam+="\"answerTime\":\""+times+"\"";
            }else if(n==1){
                cinParam+=",\"answerState\":\""+$(dom).val()+"\"";
            }else if(n==2){
                cinParam+=",\"answerTel\":\""+$(dom).val()+"\"";
            }else if(n==3){
                cinParam+=",\"remark\":\""+$(dom).val()+"\"";
            }else if(n==4){
                cinParam+=",\"soundSrc\":\""+$(dom).attr("fileName")+"\"";
            }
        })
        cinParam+="},";
    })

    var optionUnitNetTelListTr=$("#recordList2 tr");//单位-网络
    var UnitNetString=cinParam;
    $(optionUnitNetTelListTr).each(function (index,elem) {
        UnitNetString+="{\"type\":2,";;
        $(elem).find(".getMsg").each(function(n,dom){
            if(n==0){
                var times=getTime($(dom).html());
                UnitNetString+="\"answerTime\":\""+times+"\"";
            }else if(n==1){
                UnitNetString+=",\"answerState\":\""+$(dom).val()+"\"";
            }else if(n==2){
                UnitNetString+=",\"answerTel\":\""+$(dom).val()+"\"";
            }else if(n==3){
                UnitNetString+=",\"remark\":\""+$(dom).val()+"\"";
            }else if(n==4){
                UnitNetString+=",\"soundSrc\":\""+$(dom).attr("fileName")+"\"";
            }
        })
        UnitNetString+="},";
    });

    var optionUnitFourTelListTr=$("#recordList3 tr");//单位-114
    var UnitFourString=UnitNetString;
    $(optionUnitFourTelListTr).each(function (index,elem) {
        UnitFourString+="{\"type\":3,";
        $(elem).find(".getMsg").each(function(n,dom){
            if(n==0){
                var times=getTime($(dom).html());
                UnitFourString+="\"answerTime\":\""+times+"\"";
            }else if(n==1){
                UnitFourString+=",\"answerState\":\""+$(dom).val()+"\"";
            }else if(n==2){
                UnitFourString+=",\"answerTel\":\""+$(dom).val()+"\"";
            }else if(n==3){
                UnitFourString+=",\"remark\":\""+$(dom).val()+"\"";
            }else if(n==4){
                UnitFourString+=",\"soundSrc\":\""+$(dom).attr("fileName")+"\"";
            }
        })
        UnitFourString+="},";
    })

    var optionUnitPhoneTelListTr=$("#recordList4 tr");//单位-固话
    var UnitPhoneString=UnitFourString;
    $(optionUnitPhoneTelListTr).each(function (index,elem) {
        UnitPhoneString+="{\"type\":4,";
        $(elem).find(".getMsg").each(function(n,dom){
            if(n==0){
                var times=getTime($(dom).html());
                UnitPhoneString+="\"answerTime\":\""+times+"\"";
            }else if(n==1){
                UnitPhoneString+=",\"answerState\":\""+$(dom).val()+"\"";
            }else if(n==2){
                UnitPhoneString+=",\"answerTel\":\""+$(dom).val()+"\"";
            }else if(n==3){
                UnitPhoneString+=",\"remark\":\""+$(dom).val()+"\"";
            }else if(n==4){
                UnitPhoneString+=",\"soundSrc\":\""+$(dom).attr("fileName")+"\"";
            }
        })
        UnitPhoneString+="},";
    })

    var optionUnitFamilyTelListTr=$("#recordList5 tr");//联系人-家庭
    var UnitFamilyString=UnitPhoneString;
    $(optionUnitFamilyTelListTr).each(function (index,elem) {
        UnitFamilyString+="{\"type\":5,";
        $(elem).find(".getMsg").each(function(n,dom){
            if(n==0){
                var times=getTime($(dom).html());
                UnitFamilyString+="\"answerTime\":\""+times+"\"";
            }else if(n==1){
                UnitFamilyString+=",\"answerState\":\""+$(dom).val()+"\"";
            }else if(n==2){
                UnitFamilyString+=",\"answerTel\":\""+$(dom).val()+"\"";
            }else if(n==3){
                UnitFamilyString+=",\"remark\":\""+$(dom).val()+"\"";
            }else if(n==4){
                UnitFamilyString+=",\"soundSrc\":\""+$(dom).attr("fileName")+"\"";
            }
        })
        UnitFamilyString+="},";
    })

    var optionUnitOtherTelListTr=$("#recordList6 tr");//联系人-其他
    var UnitOtherString=UnitFamilyString;
    $(optionUnitOtherTelListTr).each(function (index,elem) {
        UnitOtherString+="{\"type\":6,";
        $(elem).find(".getMsg").each(function(n,dom){
            if(n==0){
                var times=getTime($(dom).html());
                UnitOtherString+="\"answerTime\":\""+times+"\"";
            }else if(n==1){
                UnitOtherString+=",\"answerState\":\""+$(dom).val()+"\"";
            }else if(n==2){
                UnitOtherString+=",\"answerTel\":\""+$(dom).val()+"\"";
            }else if(n==3){
                UnitOtherString+=",\"remark\":\""+$(dom).val()+"\"";
            }else if(n==4){
                UnitOtherString+=",\"soundSrc\":\""+$(dom).attr("fileName")+"\"";
            }
        })
        UnitOtherString+="},";
    })
    if(UnitOtherString.length > 1){
        UnitOtherString = UnitOtherString.substring(0,UnitOtherString.length-1);
    }
    UnitOtherString+="]";
    //合理性判断
    var optionUnitReasonableTelListTr=$("form[id^='form-']");//合理性判断
    if(optionUnitReasonableTelListTr.length>=1){
        var creParam="[";
        var decorationCountResp=$("input[name='ListIsReasonable']:checked").val();
        $(optionUnitReasonableTelListTr).each(function (index,elem) {
            creParam+="{";
            $(elem).find(".getMsg").each(function(n,dom){
                if(n==0){
                    creParam+="\"materialName\":\""+$(dom).val()+"\"";
                }else if(n==1){
                    creParam+=",\"materialPriceResp\":\""+$(dom).find("input[name^='IsReasonable-']:checked").val()+"\"";
                }else if(n==2){
                    creParam+=",\"materialCountResp\":\""+$(dom).find("input[name^='IsCountReasonable-']:checked").val()+"\"";
                }else if(n==3){
                    creParam+=",\"remark\":\""+$(dom).val()+"\"";
                }else if(n==4){
                    creParam+=",\"id\":\""+$(dom).val()+"\"";
                }else if(n==5){
                    creParam+=",\"decorationImg\":\""+$(dom).val()+"\"";
                }
            })
            creParam+=",\"decorationCountResp\":\""+decorationCountResp+"\"},";
        });
        creParam = creParam.substring(0,creParam.length-1);
        creParam+="]";
    }
    //评估结果
    var orderParam={};
    // orderParam.state="5";//人工初审
    // orderParam.tache=$("#checkState option:selected").val();
    // orderParam.firstCredit=$('#giveAmount').val();//初审授信额度
    orderParam.paymentRatio = $("#paymentRatio").val();//付款比例
    //审批记录
    var approveParam={};
    approveParam.approveSuggestion=$("#advice").val();//审批意见
    approveParam.handlerId=$("#userId").val();
    approveParam.handlerName=$("#handName").val();
    //审核人员上传资料
    var imageParam="[";
    var optionUnitQiZhaList=$("#yyzz .getFanQiZha");//审核人员上传资料
    $(optionUnitQiZhaList).each(function (index,elem) {
        imageParam+="{";
        $(elem).find(".imgHidden").each(function(n,dom){
            if(n==0){
                imageParam+="\"src\":\""+$(dom).val()+"\"";
            }else if(n==1){
                imageParam+=",\"businessType\":\""+$(dom).val()+"\"";
            }else if(n==2){
                imageParam+=",\"type\":\""+$(dom).val()+"\"";
            }
        });
        imageParam+="},";
    })
    if(imageParam.length > 1){
        imageParam = imageParam.substring(0,imageParam.length-1);
    }
    imageParam+="]";
    var end_credit = $('#finalgiveAmount').val();
    var end_isShow = $('#finalgiveAmount').parent().css("display");
    if(flag){
        if(!end_credit&&end_isShow=="table-cell"){
            layer.msg("请输入终审授信金额",{time:2000});
            return;
        }else if(end_isShow=="none"){
            end_credit="0";
        }
        var reg=/^([1-9][0-9]*|0)(\.[0-9]+)?$/;
        var regTest=reg.test(end_credit);
        if(!regTest){
            layer.msg("授信金额格式不正确!",{time:2000});
            return;
        }
    }
    var message = $('#finaladvice').val();
    var through = $('#finalcheckState').val();
    var param={};
    param.orderId=orderId;
    param.customerId=customerId;
    param.cmParam=cmParam;//额度匹配
    param.ceParam=ceParam;  //电核信息
    param.customerRemark=customerRemark;//客户情况简述备注
    param.cinParam=UnitOtherString;//客户通信调查
    param.creParam=creParam; //合理性判断
    param.orderParam=orderParam; //评估结果
    param.approveParam=approveParam;//审批记录
    param.imageParam=imageParam;//审核人员上传资料
    param.end_credit=end_credit;
    param.message=message;
    param.state=through;
    param.flag=flag;
    Comm.ajaxPost("finalAudit/auditOrder",JSON.stringify(param),function(data){
        var msg;
        if(flag) msg="审批成功！";
        else msg="保存成功！";
        if(data.code==0){
            layer.msg(msg,{time:2000},function(){
                if(flag){
                    layer.closeAll();
                    g_userManage.tableOrder.ajax.reload();
                }
            });
        }else {
            layer.msg("系统或网络异常,请稍候重试！");
        }
    }, "application/json");
}
/*上传音频*/
function submitAudio(me) {
    $(me).parent("form").ajaxSubmit({
        type: "POST",
        url: "uploadSound",
        dataType:"json",
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff']
            });
        },
        success: function (data) {
            layer.closeAll('loading');
            layer.msg(data.msg,{time:2000},function(){
                console.log(data);
                $(me).parent().prev().attr("fileName",data.data);
                $(me).next().show();
                $(me).attr("disabled",true);
            });
        }
    });
}
//删除通话记录的录音上传
function deletetTelrecord(me) {
    var id=$(me).parent().prev().attr("fileName");
    if(id){
        Comm.ajaxPost('customerAudit/deleteFile',"fileName="+id,function (result) {
            console.log(result);
            if(result.code==0){
                layer.msg("删除成功",{time:2000},function () {
                    $(me).parent().empty().append('<input type="file" name="file" class="waitUpload" style="width:140px;display:inline-block;" onchange="javascript:submitAudio(this);"><a href="#####" onclick="deletetTelrecord(this)" class="waitUpload" style="display:none;color:red;font-size: 20px;">×</a>')
                });
            }
        });
    }
}
//装修合理性 删除初审上传的材料
function finaldeleteReasonable(me,key) {
    layer.confirm('确认删除该条装修材料？', {icon: 3, title:'操作提示'}, function(index){
        layer.close(index);
        var form = $("#form-" + key);
        var id = $(form).find("input[name='saveId']").val();//保存id
        var fileName = 'fintecher_file/' + $(me).prev().attr('filename');
        if (id) {
            Comm.ajaxPost('customerAudit/deleteFile', "fileName=" + fileName, function (result) {
                if (result.code == 0) {
                    $(me).parent("td").parent("tr").parents(".tb_info ").parent("form").remove();
                    materialArr.splice(materialArr.indexOf(key), key.length);
                    if ($("#showReasonableContent tbody").find("tr").length == 0) {
                        $("#showReasonableJudge").hide();
                        $("input[name=ReasonableInput]").val("");
                    }
                }
            });
        } else {
            $(me).parent("td").parent("tr").parents(".tb_info ").parent("form").remove();
            materialArr.splice(materialArr.indexOf(key), key.length);
            if ($("#showReasonableContent tbody").find("tr").length == 0) {
                $("#showReasonableJudge").hide();
                $("input[name=ReasonableInput]").val("");
            }
        }
    })
}
