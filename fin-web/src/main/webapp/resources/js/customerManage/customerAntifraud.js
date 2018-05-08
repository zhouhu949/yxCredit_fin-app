var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            param.customerName = $("input[name='customerName']").val();
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
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('customerAudit/list', JSON.stringify(queryFilter), function (result) {
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
                {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},
                {"data": "orderNo","orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {"data": null,"orderable" : false},
                {"data": "productNameName","orderable" : false},
                {"data": "applayMoney","orderable" : false},
                {"data": "periods","orderable" : false},
                {"data": "null","orderable" : false,
                    "defaultContent":""
                    // "render": function (data, type, row, meta) {
                    //     if(data==0) return "未提交订单";
                    //     if(data==1) return "无效订单";
                    //     if(data==2) return "已提交订单";
                    //     if(data==3) return "自动化审核";
                    //     // if(data==4) return "接受申请贷款";
                    //     if(data==4) return "人工初审";
                    //     if(data==5) return "人工终审";
                    //     if(data==6) return "签约";
                    //     if(data==7) return "放款申请";
                    //     if(data=="7.5") return "放款初审";
                    //     if(data==8) return "放款审核";
                    //     if(data==9) return "还款中";
                    //     else return "";
                    // }
                },
                {"data": "antifraudState","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data == "2") return "拒绝";
                        if(data == "1") return "通过";
                        if(data == "0") return "未核查";
                        else return "";
                    }
                },
                {"data": null, "searchable":false,"orderable": false},
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var addr = $("<span>"+data.provinces+data.city+"</span>");
                $('td', row).eq(6).empty().append(addr);
                if(data.antifraudState==1 || data.antifraudState==2){
                    $("td", row).eq(12).html('<a class="tabel_btn_style" onclick="operate(1,\''+data.id+'\',\''+data.customerId+'\')">已审核</a>');
                }else{
                    $("td", row).eq(12).html('<a class="tabel_btn_style" onclick="operate(0,\''+data.id+'\',\''+data.customerId+'\')">反欺诈审核</a>');
                }

                var str="";
                if(data.state=="0"){
                    str="未提交订单";
                }
                if(data.state=="1"){
                    str="无效订单";
                }
                if(data.state=="2"){
                    str="自动化审核";
                }
                if(data.state=="3"&&data.tache=="0"){
                    str="自动化审核拒绝";
                }
                if(data.state=="3"&&data.tache=="1"){
                    str="接受申请贷款";
                }
                if(data.state=="4"){
                    str="人工初审";
                }
                if(data.state=="5"&&data.tache=="0"){
                    str="人工初审拒绝";
                }
                if(data.state=="5"&&data.tache=="1"){
                    str="人工终审";
                }
                if(data.state=="6"&&data.tache=="0"){
                    str="人工终审拒绝";
                }
                if(data.state=="6"&&data.tache=="1"){
                    str="签约";
                }
                if(data.state=="7"){
                    str="放款申请";
                }
                if(data.state=="7.5"){
                    str="放款初审";
                }
                if(data.state=="8"){
                    str="放款终审";
                }
                if(data.state=="9"){
                    str="还款中";
                }
                $('td', row).eq(10).append(str);
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='personName']").val("");
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
function operate(sign,orderId,customerId){
    $("#limitPrice").val("");//小区平均房价
    $("#limitDeco").val("");//小区平均装修价格
    $("#customerId").val(customerId);
    $("#orderId").val(orderId);
    $("#antifraudRemark").val("");//反欺诈备注
    $("#photoRemark").val("");//拍客备注
    if(sign==1){
        $("#uploadPhoneImg").hide();//上传拍客
        $("#uploadCheatImg").hide();//上传反欺诈
        $("#showPhoneImg").show();//拍客展示
        $("#showCheatImg").show();//反欺诈展示
        $("#savaBtn").hide();//保存按钮
    }
    if(sign==0){
        $("#uploadPhoneImg").show();//上传拍客
        $("#uploadCheatImg").show();//上传反欺诈
        $("#showPhoneImg").hide();//拍客展示
        $("#showCheatImg").hide();//反欺诈展示
        $("#savaBtn").show();//保存按钮
        $("#antifraudState").attr("disabled",false);
        $("#antifraudRemark").attr("readonly",false);
        $("#photoRemark").attr("readonly",false);
    }
    layer.open({
        type : 1,
        title : '反欺诈审核',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#container'),
        btn : [ '取消' ],
        success : function(index, layero) {
            Comm.ajaxPost("customerAudit/getOrderById",orderId,function(result){
                var orderData = result.data;
                //贷款明细
                $("#proType").val(orderData.productTypeName);//产品类型
                $("#proApproval").val(orderData.manager);//当前审批人
                $("#proLoan").val(orderData.orderNo);//贷款号
                $("#proState").val("借款申请");//状态(审核的是借款申请的订单的表)
                $("#proTel").val(orderData.tel);//手机号码
                $("#proApplytime").val(formatTime(orderData.creatTime));//申请时间
                $("#proSum").val(orderData.applayMoney);//申请金额
                $("#proDeadline").val(orderData.periods);//申请期限
                $("#proSource").val(orderData.channel);//渠道来源
                if(sign==1){
                    //反欺诈
                    $("#antifraudState").val(orderData.antifraudState);
                    $("#antifraudState").attr("disabled",true);
                    $("#antifraudRemark").val(orderData.antifraudRemark);
                    $("#antifraudRemark").attr("readonly",true);
                    //拍客
                    //$("#photoState").val(orderData.photoState);暂时写死
                    $("#photoRemark").val(orderData.photoRemark);
                    $("#photoRemark").attr("readonly",true);
                }
                var personList,linkmanList,jobList,propertyList,imageList,host;
                Comm.ajaxPost("customer/customerDetailsSP",customerId,function(result){
                    var data = result.data;
                    host = data.host;//上下文
                    $('#host').val(host);
                    personList=data.personList[0];//个人信息
                    linkmanList=data.linkmanList;//联系人信息
                    jobList=data.jobList[0];//工作信息
                    propertyList=data.propertyList[0];//房产信息
                    imageList=data.imageList;//上传资料,证明文件
                    showCumIng(imageList,host,sign);
                }, "application/json",null,null,null,false);
                Comm.ajaxPost("customerAudit/imgDetails",orderData.id,function(result){
                    var data = result.data;
                    showOrderImgList(data.imageList,sign);
                }, "application/json");
                //申请人信息
                $("#applyName").val(orderData.customerName);//姓名
                $("#applyIdcard").val(orderData.card);//身份证号码
                if(personList){
                    $("#applyMerry").val(personList.marry_name);//婚姻状况
                    $("#applyCensus").val(personList.residence);//户籍
                    $("#applyAddr").val(personList.live_address);//当前住址
                    $("#houseproperty").val(personList.houseproperty);//当前居住情况
                    $("#education").html(personList.educational_name);//学历
                }
                //待装修房产信息
                if(propertyList){
                    $("#houseArea").val(propertyList.proArea);//房产面积
                    $("#housePurcha").val(propertyList.method);//购买方式
                    $("#houseAddr").val(propertyList.proAddress);//房产地址
                    $("#houseBank").val(propertyList.mortgageBank);//按揭银行
                    //额度匹配
                    $("#limitName").val(propertyList.cellName);
                }

                var paramC={};
                paramC.cellName=$("#limitName").val();
                // paramC.provincesId=orderData.provincesId;
                // paramC.cityId=orderData.cityId;暂时不用省市id
                Comm.ajaxPost("customerAudit/getMatchingPrice",JSON.stringify(paramC),function(data){
                    if(data.data.length>0){
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
                    }else{
                        $("#limitPrice").val("45000");//小区平均房价
                        $("#limitDeco").val("2100");//小区平均装修价格
                    }
                }, "application/json",null,null,null,false);
                //合理性判断
                $("#merchantId").val(orderData.merchantId);//商户id
                $("#merchantNameHidden").val(orderData.merchantName);//商户名称
                $("input[name=ReasonableInput_fitment]").val(orderData.merchantName);//商户名称
                $("input[name=ReasonableInput_fitment_money]").val(orderData.predictPrice);//预授信金额
                Comm.ajaxPost("finalAudit/finallist",'orderId='+orderId,function(result){
                    console.log(result);
                    //在塞数据之前把已经填的信息给清空
                    //装修合理性
                    $("#ReasonableInput").val("");
                    $("#showReasonableContent").empty();
                    var decorationList = result.data.decorationList;
                    for(var i=0;i<decorationList.length;i++){
                        $("#showReasonableContent").show()
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
                            html+='<input type="radio" name="IsReasonable-'+materialName+'" value="materialName" disabled="disabled">是</label>';
                            html+='<label>';
                            html+='<input type="radio" name="IsReasonable-'+materialName+'" value="0"  checked>否</label></td>';
                        }else {
                            html+='<input type="radio" name="IsReasonable-'+materialName+'" value="materialName" checked>是</label>';
                            html+='<label>';
                            html+='<input type="radio" name="IsReasonable-'+materialName+'" value="0" disabled="disabled">否</label></td>';
                        }
                        html+='<td class="ReasonableTableTitle">装修材料数量是否合理：</td>';
                        html+='<td class="ReasonableTableTitle getMsg">';
                        html+='<label>';
                        if (materialCountResp=='0'){
                            html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="1" disabled="disabled">是</label>';
                            html+='<label>';
                            html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="0" checked>否</label>';
                        }else {
                            html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="1" checked>是</label>';
                            html+='<label>';
                            html+='<input type="radio" name="IsCountReasonable-'+materialName+'" value="0" disabled="disabled">否</label>';
                        }
                        html+='</td><td class="ReasonableTableTitle">备注：</td><td><label><input class="getMsg" type="text" readonly name="remark'+materialName+'" value="'+decorationList[i].remark+'"></label></td>';
                        /* html+='<td class="ReasonableTableTitle">';
                         html+='<a href="#####" onclick="showCompare(\''+materialName+'\')" class="yetUpload">比价</a>&nbsp;&nbsp;' ;
                         html+='<input type="file" name="file" class="waitUpload" style="width:195px;display:inline-block;" onchange="javascript:submitImg(this,\''+materialName+'\');">';
                         html+='&nbsp;&nbsp;<a class="waitUpload imgUpload">待上传</a>&nbsp;&nbsp;';
                         html+='<a href="#####" onclick="deleteReasonable(this,\''+materialName+'\')" class="waitUpload">删除</a></td>';*/
                        html+='<td class="ReasonableTableTitle">';
                        if(decorationImg){
                            html+='<img width="50" height="40" src="'+_ctx+"/fintecher_file/"+decorationImg+'" onclick="imgShow(this)"/>';
                            html+='&nbsp;&nbsp;<a class="waitUpload imgUpload" onclick="imgShow1(this)" filename="'+decorationImg+'">预览</a>';
                        }else{
                            html+='未上传比价图片';
                        }
                        html+='</td></tr></tbody></table><input type="hidden" name="customerId" value="'+customerId+'">' ;
                        html+='<input type="hidden" name="orderId" value="'+orderId+'">' ;
                        html+='<input type="hidden" name="materialPriceResp" value="">' ;
                        html+='<input type="hidden" name="materialCountResp" value="">' ;
                        html+='<input type="hidden" name="decorationCountResp" value="">' ;
                        html+='<input type="hidden" name="materialName" value="">' ;
                        html+='<input type="hidden" name="remark" value="">' ;
                        html+='<input type="hidden" name="saveId" value="" class="getMsg">';
                        html+='<input type="hidden" name="fileName" value="" class="getMsg">';
                        html+='</form>';
                        $("#showReasonableContent").prepend(html);
                    }
                });

            }, "application/json");
        }
    });
}
//保存图片信息及改反欺诈审核状态
function saveBtn() {
    var orderId = $("#orderId").val();
    var customerId = $("#customerId").val();
    $("input[name='orderId']").val(orderId);
    $("input[name='customerId']").val(customerId);
    //反欺诈资料上传
    $("#uploadCheatImg form").ajaxSubmit({
        type: "POST",
        url: "batchUploadFile",
        success: function (data) {
        },
        error: function (XMLHttpRequest, textStatus, thrownError) {
        }
    });
    var param = {};
    param.orderId = orderId;
    param.customerId = customerId;
    //param.imageParam = imageParam;
    param.antifraudState = $("#antifraudState").val();
    param.antifraudRemark = $("#antifraudRemark").val();
    param.photoRemark = $("#photoRemark").val();
    Comm.ajaxPost("custAntifraud/antifraud",JSON.stringify(param),function(data){
        layer.msg(data.msg,{time:1000},function(){
            layer.closeAll();
            g_userManage.tableOrder.ajax.reload();
        });
    }, "application/json");
}
// 展示客户图片
function showCumIng(imgList,host,sign) {
    var html="";
    for(var i=0;i<imgList.length;i++){
        if(imgList[i].type== "9"){//身份证
            var time=getFirstTime(imgList[i].creatTime);
            if(imgList[i].isfront=="0"){
                html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>身份证正面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }else{
                html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>身份证反面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "114"){//手持身份证明
            var time=getFirstTime(imgList[i].creatTime);
            if(imgList[i].isfront=="0"){
                html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>手持身份证明正面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }else{
                html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>手持身份证明反面</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "112"){//工作证明
            var time=getFirstTime(imgList[i].creatTime);
            html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>工作证明</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
        if(imgList[i].type== "115"){//房产证明
            var time=getFirstTime(imgList[i].creatTime);
            html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>房产证明</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
        if(imgList[i].type== "116"){//装修合同
            var time=getFirstTime(imgList[i].creatTime);
            html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>装修合同</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
        if(imgList[i].type== "117"){//装修清单
            var time=getFirstTime(imgList[i].creatTime);
            html+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>装修清单</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
    }
    $("#showNewImg ul").empty();
    $("#showNewImg ul").append(html);
}
function showOrderImgList(imgList,sign){
    var FanQizha="";
    var PaiKe="";
    for(var i=0;i<imgList.length;i++) {
        if(sign==1){
            if(imgList[i].type== "7"){//反欺诈资料
                $("#showCheatImg").show();
                var time=getFirstTime(imgList[i].creatTime);
                FanQizha+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height: 100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>反欺诈资料</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
            }
        }
        if(imgList[i].type== "8"){//拍客资料
            $("#showPhoneImg").show();
            var time=getFirstTime(imgList[i].creatTime);
            PaiKe+='<li><div style="width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto"><img style="width: 100%;height: 100%" src="'+_ctx+imgList[i].src+'" class="imgShow" onclick="imgShow(this)"></div><p>拍客资料</p><p class="hideTime" style="margin:1em;">'+time+'</p></li>';
        }
    }
    $("#showCheatImg ul").empty();
    $("#showCheatImg ul").append(FanQizha);
    $("#showPhoneImg ul").empty();
    $("#showPhoneImg ul").append(PaiKe);
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
//图片预览
function imgShow(me){
    window.open($(me).attr("src"),"图片预览");
    // $("#imgDisplay img").attr("src",$(me).attr("src"));
    // layer.open({
    //     type :1,
    //     title: false,
    //     maxmin : true,
    //     shadeClose : true,
    //     offset: '100px',
    //     area : [ '60%', '60%' ],
    //     content : $('#imgDisplay'),
    //     success : function(index, layero) {
    //         $("#imgDisplay img").attr("src",$(me).attr("src"));
    //     },
    // })
}
//收缩目录
function shrink(me){
    $(me).next().slideToggle();
}