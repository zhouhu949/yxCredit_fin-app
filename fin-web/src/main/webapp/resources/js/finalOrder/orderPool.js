var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    receiveFlag:false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.state = '5';//人工初审
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
        if(g_userManage.receiveFlag){
            param.receiveId = $("input[name='userId']").val();
            param.id = $("#orderId").val();
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
                Comm.ajaxPost('finalAudit/list', JSON.stringify(queryFilter), function (result) {
                    console.log(result);
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
                {"data": null ,"searchable":false,"orderable" : false,'width':'30px'},
                {"className" : "childBox",class:'hidden',"orderable" : false,"data" : null,"width" : "30px","searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<input type="checkbox" value="'+data.id+'" customerId="'+data.customerId+'" style="cursor:pointer;" isChecked="false">'
                    }
                },
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
                {"data": "null", "searchable":false,"orderable": false,
                    "render": function (data, type, row, meta) {
                        var str='<a style="text-decoration: none;color: #307ecc;" id="operate" class="tabel_btn_style">领取</a>';
                        return str;
                    }
                },
                {"data": "id",class:'hidden',"orderable" : false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {

            },
            "initComplete" : function(settings,json) {
                //领取
                $("#order_list").on("click","#operate",function(e){
                    var target = e.target||window.event.target;
                    var orderId = $(target).parents("tr").children().eq(1).children("input").val();
                    Comm.ajaxPost('finalAudit/receiveOrder','orderId='+orderId,function(data){
                        layer.msg(data.msg,{time:1000},function () {
                            layer.closeAll();
                            window.$('#order_list').dataTable().fnDraw(false);
                        });
                    })
                });

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
function auditOrder(orderId,customerId){
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
            Comm.ajaxPost("customerAudit/getOrderById",orderId,function(result){
                var orderData = result.data;
                console.log(orderData);
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
                $("#proSource").val(orderData.channel);//渠道来源
                var personList,linkmanList,jobList,propertyList,imageList;
                Comm.ajaxPost("customer/customerDetailsSP",orderData.customerId,function(result){
                    var data = result.data;
                    console.log(data)
                    personList=data.personList[0];//个人信息
                    linkmanList=data.linkmanList;//联系人信息
                    jobList=data.jobList[0];//工作信息
                    propertyList=data.propertyList[0];//房产信息
                    imageList=data.imageList;//上传资料
                }, "application/json",null,null,null,false);
                //申请人信息
                $("#applyName").val(orderData.customerName);//姓名
                $("#applyIdcard").val(orderData.card);//身份证号码
                $("#applyMerry").val(personList.marry_name);//婚姻状况
                $("#applyCensus").val(personList.residence);//户籍
                $("#applyAddr").val(personList.live_address);//当前住址
                //待装修房产信息
                $("#houseArea").val(propertyList.proArea);//房产面积
                $("#houseFrame").val(propertyList.structure);//户型结构
                $("#housePurcha").val(propertyList.method);//购买方式
                $("#houseAddr").val(propertyList.proAddress);//房产地址
                $("#houseBank").val(propertyList.mortgageBank);//按揭银行
                $("#houseSum").val(propertyList.amount);//月供金额
                $("#houseDone").val(propertyList.year + "年" + propertyList.month + "月");// 已按揭___年___月
                //单位信息
                $("#unitName").val(jobList.companyName);//单位名称
                $("#unitPro").val(jobList.companyProperty);//单位性质
                $("#unitTel").val(jobList.companyPhone);//单位电话
                $("#unitAddr").val(jobList.companyAddress);//单位地址
                $("#unitDepart").val(jobList.department);//部门
                $("#unitGrade").val(jobList.posLevel);//职级
                $("#unitYear").val(jobList.proArea);//现单位工作年限？
                $("#unitDeliverty").val(jobList.proArea);//是否代发？
                $("#unitIncome").val(jobList.proArea);//月收入？
                $("#unitDay").val(jobList.proArea);//发薪日？
                $("#unitSocial").val(jobList.fundSocialsec);//是否缴纳公积金及社保
                //联系人信息
                for(var i=0;i<linkmanList.length;i++){
                    var rel=linkmanList[i].relationship;//关系
                    if(rel=="0" || rel=="2" || rel=="3" || rel=="4"){
                        $("#directName").val(linkmanList[i].linkName);//姓名(直系)
                        $("#directRel").val(linkmanList[i].relationshipName);//关系名称
                        $("#directUnit").val(linkmanList[i].workCompany);//工作单位
                        $("#directTel").val(linkmanList[i].contact);//联系电话
                    }else{
                        if(rel=="1"){
                            $("#workerName").val(linkmanList[i].linkName);//姓名(同事)
                            $("#workerDepart").val(linkmanList[i].companyName);//部门？
                            $("#workerPost").val(linkmanList[i].companyName);//职务？
                            $("#workerTel").val(linkmanList[i].contact);//联系电话
                        }
                    }
                }
                //证明文件


            }, "application/json");
        }
    });
}

function formatTime(t){
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}

//拨打记录添加
function addBtn(){
    var html='';
    html+='<tr>';
    html+='<td style="text-align: right;">接听状态：</td><td>';
    html+='<select name="" class="listenState"> <option value="0">无人接听</option><option value="1">接通</option> <option value="2">其它</option></select>';
    html+='</td><td style="text-align: right;">调查类型：</td><td>'
    html+='<select name="lookType" class="lookType"> <option value="1">本人电话拨打记录</option><option value="2">网络调查</option><option value="3">114调查</option></select>'
    html+='</td><td>';
    html+='<button  type="button" class="btn btn-primary deleteBtn"  class="deleteBtn" onclick="answerdelBtn(this)">删除</button><button  type="button" class="btn btn-primary assignBtn" onclick="callBtn(this)">保存</button>';
    html+='</td></tr><tr> <td>接听内容:</td> <td colspan="4"><textarea  class="answerContent" cols="30" rows="2"> </textarea>'
    html+='</td></tr><tr><td>备注:</td>';
    html+='<td colspan="4"><textarea  class="answerRemark" cols="30" rows="2"></textarea></td></tr>';
    $("#recordList").append(html);
}
//下面用于图片上传预览功能
function setImagePreview1(avalue) {
    var docObj=document.getElementById("file");

    var imgObjPreview=document.getElementById("preview");
    if(docObj.files &&docObj.files[0])
    {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '272px';
        imgObjPreview.style.height = '180px';
        //imgObjPreview.src = docObj.files[0].getAsDataURL();

        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }
    else
    {
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag");
        //必须设置初始大小
        localImagId.style.width = "272px";
        localImagId.style.height = "180px";
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        try{
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        }
        catch(e)
        {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
    /*if($("#preview").attr("src")!=""){
     var html="<span>fdfasdf</span>";
     $("#pictureLoad").append(html);
     }*/
}
//额度匹配
function amountBtn(){
    console.log($("#limitName").val())
    var param={};
    param.cellName=$("#limitName").val();
    param.provincesId=$("#provincesId").val();
    param.cityId=$("#cityId").val();
    Comm.ajaxPost("customerAudit/getMatchingPrice",JSON.stringify(param),function(data){
        console.log(data);
        $("#limitPrice").val(data.averageHousePrice);//小区平均房价
        $("#limitDeco").val(data.averageRenovationPrice  );//小区平均装修价格
    }, "application/json");
}
//客户电核信息
function elecButton(){
    var loan=$('input[name="checkInfo"]:checked').next().html();//借款信息核实
    var job=$('input[name="checkCondi"]:checked').next().html();//工作情况核实
    var house=$('input[name="checkHouse"]:checked').next().html();//房产情况核实
    var liabilities=$('input[name="checkLoan"]:checked').next().html();//负债信息核实
    var flow=$('input[name="checkGlide"]:checked').next().html();//流水核查
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
        console.log(data);

    }, "application/json");
}
//通信记录
function callBtn(me){
    /* customerAudit/addInvestigation保存客户通信记录
     传参
     orderId   订单id
     customerId    客户id
     answerContent  接听内容
     answerState   接听状态（0无人接听 1接通 2其它）
     type   调查类型（1本人电话拨打记录  2网络调查  3114调查）
     remark
     返回  成功失败*/
    var param={};
    param.orderId=$("#orderId").val();//订单id
    param.customerId= $("#customerId").val();//客户id
    param.answerContent=$(me).parent().parent().next().find(".answerContent").val();//接听内容
    param.answerState=$(me).parent().parent().find(".listenState option:selected").val();//接听状态
    param.type=$(me).parent().parent().find(".lookType option:selected").val();//调查类型
    param.remark=$(me).parent().parent().next().next().find(".answerRemark").val();//备注
    Comm.ajaxPost("customerAudit/addInvestigation",JSON.stringify(param),function(data){
        console.log(data);

    }, "application/json");
}
//删除通讯录
function answerdelBtn(me){
    //简单删除
    $(me).parent().parent().hide();
    $(me).parent().parent().next().hide();
    $(me).parent().parent().next().next().hide();
}
//发欺诈资料
$('#addV3Product').ajaxSubmit({
    type: "POST",
    url: "addProductSeries",
    success: function (data) {
        data = eval('('+data+')');
        layer.msg(data.msg,{time:1000},function(){
            layer.closeAll();
            var id=$("#v3ParentId").val();
            clickPrdType(id)
        });
    },
    error: function (XMLHttpRequest, textStatus, thrownError) {
    }
});

