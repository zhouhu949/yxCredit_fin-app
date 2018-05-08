var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.cusName = $("#custName").val();
            param.custTel = $("#custTel").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userColl = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var loanId = $('#loanId').val();
        param.loanId = loanId;
        //自行处理查询参数
        param.fuzzySearch = g_userColl.fuzzySearch;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userPlan = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var loanId = $('#loanId').val();
        param.loanId = loanId;
        //自行处理查询参数
        param.fuzzySearch = g_userColl.fuzzySearch;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userProgress = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var loanId = $('#loanId').val();
        param.loanId = loanId;
        //自行处理查询参数
        param.fuzzySearch = g_userProgress.fuzzySearch;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
var g_userList = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userList.fuzzySearch;
        if (g_userList.fuzzySearch) {
            param.account = $("[name='account']").val();
            param.trueName = $("[name='trueName']").val();
            param.tel = $("[name='mobile']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};
$(function (){
    g_userManage.tableUser = $('#afterLoanTable').dataTable($.extend({
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
            Comm.ajaxPost('afterLoan/listApproval', JSON.stringify(queryFilter), function (result) {
                console.log(result);
                //封装返回数据
                var returnData = {};
                var resData = result.data;

                returnData.draw = data.draw;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                console.log(returnData.data);
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {
                "className" : "childBox",
                'class':'hidden',
                "orderable" : false,
                "data" : null,
                "width" : "20px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input id="'+data.customerId+'" type="checkbox" value="'+data.customerId+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "order_no","orderable" : false},
            {"data": "pay_count","orderable" : false},
            {"data": "derate_date","orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data": "person_name","orderable" : false},
            {"data": "tel","orderable" : false},
            {"data": "amount","orderable" : false},
            {"data": "fee","orderable" : false},
            {"data": "penalty","orderable" : false,"render":function (data, type, row, meta) {
                console.log(row);
                return parseFloat(row.penalty) + parseFloat(row.default_interest);
            }},
            {"data": "overdue_days","orderable" : false},
            {"data": "derate_amount","orderable" : false},
            //1未还，2已还，3逾期 4.还款确认中',
            {"data": "mrState","orderable" : false,
                "render": function (data, type, row, meta) {
                    //减免环节 0电审环节；1外访环节；2委外环节；
                    if(data.toString()=="1"){
                        return "未还";
                    }else if (data.toString()=="2"){
                        return "已还";
                    }else if (data.toString()=="3"){
                        return "逾期";
                    }else if (data.toString()=="4"){
                        return "还款确认中";
                    }
                }
            },
            {"data": "approval_state","orderable" : false,
                "render": function (data, type, row, meta) {
                    //审批状态 0拒绝；1同意；
                    if(data.toString()==""){
                        return "未审批";
                    }
                    else if(data.toString()=="0"){
                        return "拒绝";
                    }else if (data.toString()=="1"){
                        return "同意";
                    }
                }
            },
            //{"data": null,"orderable" : false}
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
        ],
        //处理序号用
        "columnDefs": [
            {
                "searchable": false,
                "orderable": false,
                "targets": 1
            }
        ],
        "createdRow": function(row, data, index, settings, json) {
            var approval = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="costReduction(\''+data.orderId+'\',\''+data.repayment_id+'\',\''+data.derate_amount+'\',\''+data.id+'\',\''+data.mrState+'\')"> 审批 </a>');
            var btnSee = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="costReductionSee(\''+data.orderId+'\',\''+data.customerId+'\',\''+data.derate_amount+'\',\''+data.id+'\',\''+data.repayment_id+'\')"> 查看 </a>');
            var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="getCollection(event)">催收记录 </a>');
            if(data.state==1||data.state==2||data.state==3){
                //$("td", row).eq(9).append(btn);
                $("td", row).eq(14).append(btnSee);
            }else {
                $("td", row).eq(14).append(approval);//.append(btn);
            }

        },
        "initComplete" : function(settings,json) {
            // $("#afterLoanTable").delegate( 'tbody tr','click',function(e){
            //     $("#cus_order").show();
            //     var target=e.target;
            //     if(target.nodeName=='TD'){
            //         var input=target.parentNode.children[1].children[0];
            //         var isChecked=$(input).attr('isChecked');
            //         if(isChecked=='false'){
            //             if($(input).attr('checked')){
            //                 $(input).attr('checked',false);
            //             }else{
            //                 $(input).attr('checked','checked');
            //             }
            //             $(input).attr('isChecked','true');
            //         }else{
            //             if($(input).attr('checked')){
            //                 $(input).attr('checked',false);
            //             }else{
            //                 $(input).attr('checked','checked');
            //             }
            //             $(input).attr('isChecked','false');
            //         }
            //     }
            //     target = e.target||window.event.target;
            //     var id = $(target).parents("tr").children().eq(1).children("input").val();
            //     queryCusOrderList(id);
            // });
            //搜索
            $("#btn_search").click(function() {
                $("#cus_order").hide();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //搜索重置
            $("#btn_search_reset").click(function() {
                $("#custName").val("");
                $("#custTel").val("");
                g_userManage.fuzzySearch = false;
                g_userManage.tableUser.ajax.reload();
            });

        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function() {
        g_userManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});
//催收记录
function getCollection(e) {
    debugger
    var target = e.target||window.event.target;
    var loanId = $(target).parent('td').parent('tr').children('td').eq(15).html();
    $('#loanId').val(loanId);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#collContainer').show(),
        success : function(index, layero) {
            collTable(loanId);
        }
    })
}
//催收记录table初始化
function collTable() {
    if(g_userColl.tableUser) {
        g_userColl.tableUser.ajax.reload();
    }else{
        g_userColl.tableUser = $('#collRecordTable').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userColl.getQueryCondition(data);
                Comm.ajaxPost('collection/list', JSON.stringify(queryFilter), function (result) {
                    console.log(result);
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data;

                    returnData.draw = data.draw;
                    returnData.recordsTotal = resData.total;
                    returnData.recordsFiltered = resData.total;
                    returnData.data = resData.list;
                    console.log(returnData.data);
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },
                {"data": "tel","orderable" : false},
                {"data": "callTime","orderable" : false},
                {"data": 'relationship',"orderable" : false},
                {"data": 'remark',"orderable" : false},
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                },
                {"data": "id" ,'class':'hidden',"searchable":false,"orderable" : false}
            ],
            //处理序号用
            "columnDefs": [
                {
                    "searchable": false,
                    "orderable": false,
                    "targets": 1
                }
            ],
            "createdRow": function(row, data, index, settings, json) {
                var btn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="order_detail" onclick="deleteColl(\''+data.id+'\')">删除</a>');
                $("td", row).eq(5).append(btn);

                //时间格式转换
                var payTime = data.payTime;
                if (!payTime) {
                    payTime = '';
                }else{
                    payTime = payTime.substr(0,4)+"-"+payTime.substr(4,2)+"-"+payTime.substr(6,2);
                }
                $('td', row).eq(8).html(payTime);


            },
            "initComplete" : function(settings,json) {


            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userColl.tableUser.on("order.dt search.dt", function() {
            g_userColl.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw()
    }
}
//添加催收记录
function addColl() {
    var loanId = $('#loanId').val();
    layerAdd = layer.open({
        type : 1,
        title : "添加催收记录",
        shadeClose : true, //点击遮罩关闭层
        area : [ '550px', '340px' ],
        content : $('#Add_coll').show(),
        btn : [ '保存', '取消' ],
        success :function () {
            $('#collForm')[0].reset();
        },
        yes:function(index, layero){
            var tel = $('#callTel').val();
            if (!tel) {
                layer.msg('请填写拨打电话',{time:1000});
                return;
            }
            var callTime = $('#callTime').val();
            if (!callTime) {
                layer.msg('请填写拨打时间',{time:1000});
                return;
            }
            var relationship = $('#relationship').val();
            if (!relationship) {
                layer.msg('请填写与客关系',{time:1000});
                return;
            }
            var remark= $('#remark').val();
            if (!remark) {
                layer.msg('请填写催收备注',{time:1000});
                return;
            }
            var magCollectionRecord={};
            magCollectionRecord.loanId = loanId;
            magCollectionRecord.tel = tel;
            magCollectionRecord.callTime = callTime;
            magCollectionRecord.relationship = relationship;
            magCollectionRecord.remark = remark;

            Comm.ajaxPost('collection/add',JSON.stringify(magCollectionRecord),function(data){
                    layer.msg(data.msg,{time:1000},function(){
                        layer.close(layerAdd);
                        window.$('#collRecordTable').dataTable().fnDraw(false);
                    });
                }, "application/json"
            );
        }
    });
}
//删除催收记录
function deleteColl(id) {
    layer.confirm('确定要删除该记录吗？', {icon: 3, title:'操作提示'}, function(index){
        Comm.ajaxPost('collection/delete',{id:id},function(data){
                layer.msg(data.msg,{time:1000},function(){
                    window.$('#collRecordTable').dataTable().fnDraw(false);
                });
            }
        );
    })
}
//返回
function backColl() {
    layer.closeAll();
}

//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
        time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}
var g_CustomerOrderManage = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(customerId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.customerId=customerId;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};

//查看订单
function showDetail(id,card,tel,customerId){
    $("#loanInfo").show();
    $("#imgInfo").hide();
    var occupationType = $("#occupationType").val();
    if(occupationType == 1){//工薪者
        $("#wageEarner").show();
        $("#wageEarnerType").html("工薪者")
    }else if(occupationType == 2){//经营者
        $("#operator").show();
        $("#operatorType").html("经营者")
    }else if(occupationType == 3){//自由职业者
        $("#professional").show();
        $("#professionalType").html("自由职业者")
    }else if(occupationType == 4){//在读学生
        $("#readingStu").show();
        $("#readingStuType").html("在读学生")
    }else if(occupationType == 5){//退休人员
        $("#retiree").show();
        $("#retireeType").html("退休人员")
    }
    layer.open({
        type : 1,
        title : '客户详细信息',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#container'),
        success : function(index, layero) {
            var param = {};
            param.customerId = customerId;
            param.type = occupationType;
            param.orderId = id;
            Comm.ajaxPost("loanClient/customerDetailsSP",JSON.stringify(param),function(result){
                debugger
                var data = result.data;
                var person=data.personList[0];//个人信息
                var incomeList = data.incomeList[0];//收入状况
                var linkmanList=data.linkmanList;//联系人信息
                //var occupationList = data.occupationList[0];//职业信息
                var productDetailList = data.productDetailList[0];//产品详细信息
                var customerContact = data.customerContact;//客户的通讯信息
                var imgList = data.imgList;//图片信息
                var order = data.order;
                var host = data.host;//图片地址
                if(order){
                    $("#product_type_name").html(order.productTypeName);
                    $("#periods").html(productDetailList.periods);
                    $("#product_name_name").html(order.productNameName);
                    $("#multipleRate").html(productDetailList.multipleRate+"%/年");
                    var payment = "";
                    if(productDetailList.payment == "1"){
                        payment="月付息，到期还本";
                    }else if(productDetailList.payment == "2"){
                        payment="到期一次还本付息";
                    }else if(productDetailList.payment == "3"){
                        payment="等额本息";
                    }else if(productDetailList.payment == "4"){
                        payment="分期等额";
                    }else if(productDetailList.payment == "5"){
                        payment="等额本金";
                    }
                    $("#repayType").html(payment);
                    $("#applay_money").html(order.credit);
                    $("#vehiclePrice").html(order.periods);
                    $("#actualLimit").html(productDetailList.actualLowerLimit+"~"+productDetailList.actualUpperLimit);
                    $("#interestRemark").html(productDetailList.interestRemark);
                    $("#mortgageDate").html(order.periods);
                }
                //个人信息
                var marry ="";
                if(person){
                    $("#liveProvinces").html(person.liveProvinces);
                    $("#liveCity").html(person.liveCity);
                    $("#realname").html(person.realname);//姓名
                    $("#sex").html(person.sex_name);//姓别
                    $("#oldName").html(person.oldname);//曾用名
                    if(person.marry =="1"){
                        marry = "已婚";
                    }else if(person.marry =="2"){
                        marry = "未婚";
                    }else if(person.marry =="3"){
                        marry = "离异";
                    }else if(person.marry =="4"){
                        marry = "丧偶";
                    }
                    $("#marry").html(marry);//婚姻状况
                    $("#education").html(person.educational_name);
                    $("#school").html(person.school);
                    //$("#weixin").html(customerContact.wechat);//微信号码
                    //$("#qq").html(customerContact.qq);//扣扣号
                    //$("#email").html(customerContact.email);//邮箱

                    $("#cusCard").html(card);
                    $("#cardTime").html();

                    $("#card_address").html(person.cardAddress);
                    $("#cityTime").html(person.citylivetime);
                    var address = person.liveProvinces+person.liveCity+person.liveDistric+person.nowaddress;
                    $("#nowaddress").html(address);//
                    $("#addresslivetime").html(person.addresslivetime);

                    $("#house_remark").html(card);
                    $("#house").html(person.houseproperty);
                    $("#house_property").html(person.houseproperty);

                    $("#house_address").html(person.house_address);
                    $("#register_time").html(person.register_time);
                    $("#cusTel").html(tel);
                }
                //收入状况
                if(incomeList){
                    $("#annualIncome").html();
                    $("#month_income").html(incomeList.monthIncome);
                    $("#month_average_expense").html(incomeList.monthAverageexpense);
                    $("#support_num").html(incomeList.supportNum);
                }
                // //职业信息
                // if(occupationList){
                //     if(occupationType == 1){//工薪者
                //         $("#company").html(occupationList.company);
                //         var earnerPcd = occupationList.provinces+""+occupationList.city+""+occupationList.distric;
                //         $("#earnerPcd").html(earnerPcd);
                //         $("#company_address").html(earnerPcd+occupationList.companyAddress);
                //         $("#company_fixedphone").html(occupationList.companyFixedphone);
                //         $("#entering_the_company").html(getTime(occupationList.enteringTheCompany));
                //         $("#basic_monthly_pay").html(occupationList.basicMonthlyPay);
                //     }else if(occupationType == 2){//经营者
                //         var managerPcd = occupationList.provinces+""+occupationList.city+""+occupationList.distric;
                //         $("#enterprise").html(occupationList.enterprise);
                //         $("#management_years").html(occupationList.managementYears);
                //         $("#management_address").html(occupationList.managementAddress);
                //         $("#year_income").html(occupationList.yearIncome);
                //         $("#managerPcd").html(managerPcd);
                //     }else if(occupationType == 3){//自由职业者
                //         $("#source_income").html(occupationList.sourceIncome);
                //         $("#income_month").html(occupationList.averageIncomeMonth);
                //         $("#work_years").html(occupationList.currentWorkYears);
                //         $("#certificate_name").html(occupationList.certificateName);
                //     }else if(occupationType == 4){//在读学生
                //         $("#school_name").html(occupationList.schoolName);
                //         $("#school_address").html(occupationList.schoolAddress);
                //         $("#stu_department").html(occupationList.stu_department);
                //         $("#stu_class").html(occupationList.stuClass);
                //     }else if(occupationType == 5){//退休人员
                //         $("#former_company").html(occupationList.formerCompany);
                //         $("#pension_month").html(occupationList.pensionMonth);
                //         $("#retire_years").html(occupationList.retireYears);
                //         $("#other_source_income").html(occupationList.otherSourceIncome);
                //     }
                // }
                //联系人信息
                var html = "";
                var html1 = "";
                $("#relation").empty();
                for(var i=0;i<linkmanList.length;i++){
                    var rel = linkmanList[i].mainSign;
                    if(rel=='1'){//直系
                        var yesno = "";
                        if(linkmanList[i].knownLoan=='1')yesno="是";
                        if(linkmanList[i].knownLoan=='0')yesno="否";
                        html= '<tr>'+
                            '<td>'+linkmanList[i].linkName+'</td>'+
                            '<td>'+linkmanList[i].relationshipName+'</td>'+
                            '<td>'+linkmanList[i].contact+'</td>'+
                            '<td>'+linkmanList[i].workCompany+'</td>'+
                            '<td>'+yesno+'</td>'+
                            '</tr>';
                        $("#relation").append(html);//直系
                    }
                }
                //图片信息
                var html9 = "";
                var imgName ="";
                // var url = "http://192.168.102.14:8080/";
                for(var i=0;i<imgList.length;i++){
                    if(imgList[i].type == "9"&&imgList[i].isfront=="1"){
                        imgName="身份证正面"
                    }else if(imgList[i].type == "9"&&imgList[i].isfront=="2"){
                        imgName="身份证反面"
                    }else if(imgList[i].type == "9"&&imgList[i].isfront=="3"){
                        imgName="手持身份证"
                    }
                    html9+='<li style="float:left;margin-right: 10px"><div style="overflow: hidden;width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto">' +
                        '<img style="width: 100%;height:100%" src="'+host+imgList[i].src+'" class="imgShow" onclick="imgShow(this)">' +
                        '</div><p style="text-align: center">'+imgName+'</p></li>';
                }
                $("#personInfo").empty();
                $("#personInfo").append(html9);
            }, "application/json");
        }
    });
}

//费用减免
function  costReduction(orderId,repaymentId,derateAmount,derateId,mrState) {
    if(mrState=='2'||mrState=='4'){
        var msg='当前计划表还款确认中无法审批！';
        if (mrState=='2'){
            msg='当前计划表已还无法审批！';
        }
        layer.msg(msg,{time:2000},function () {
        });
        return;
    }
    var setUpLayer = layer.open({
        type : 1,
        title : '减免金额审批',
        shadeClose : false,
        area : ['740px','450px'],
        content : $('#settleDiv'),
        btn : [ '提交', '取消' ],
        success:function (index, layero) {
            var param = {};
            param.id = derateId;
            Comm.ajaxPost('afterLoan/getDerateDetails', JSON.stringify(param), function (result) {
                console.log(result);
                //封装返回数据
                debugger
                var returnData = {};
                var resData = result.data;
                $("#lblPersonName").html(resData.person_name);
                $("#lblTel").html(resData.tel);
                $("#lblPayCount").html(resData.pay_count);
                $("#lblFee").html(resData.fee);
                // $("#lblManageFee").html(resData.manageFee);
                // $("#lblQuickTrialFee").html(resData.quickTrialFee);
                $("#lblRepaymentAmount").html(resData.repayment_amount);
                $("#lblAmount").html(resData.amount);
                $("#lblPenalty").html(parseFloat(resData.penalty)+parseFloat(resData.default_interest));
                $("#lblOverdueDays").html(resData.overdue_days);
                $("#lblTotal").html(parseFloat(resData.repayment_amount ) + parseFloat(resData.penalty));
                $("#reductionAmount").val(derateAmount);
                debugger
                if(resData.order_type=='2'){
                    $("#servicePackageAmountTdTab").show();
                    $("#servicePackageAmountTdLab").show();
                    $("#effectiveDataTr").show();
                    var tds = $("#lblFeeTdLab");
                    tds.attr("colspan", "");
                    $("#lblTotal").html((parseFloat(resData.sunAmount) + parseFloat(resData.totalFine)).toFixed(2));
                    $("#lblPenalty").html(resData.totalFine);
                    if(resData.servicePackageAmount==""){
                        $("#servicePackageAmount").html("0");
                    }else {
                        $("#servicePackageAmount").html(resData.servicePackageAmount);
                    }
                    $("#effectiveDataLbl").html(formatTime(resData.effectiveData))
                }else {
                    $("#servicePackageAmountTdTab").hide();
                    $("#servicePackageAmountTdLab").hide();
                    $("#effectiveDataTr").hide();
                    var tds = $("#lblFeeTdLab");
                    tds.attr("colspan", "3");
                }
                $("#agree1").removeAttr("disabled");
                $("#agree").removeAttr("disabled");
            },"application/json");
        },
        yes : function(index, layero) {
            debugger
            var param = {};
            param.derateId = derateId;
            if ($("#agree").attr("checked")){
                param.approvalState="1";
            }else {
                param.approvalState="0";
            }
            param.repaymentId=repaymentId;
            param.derateAmount=$("#reductionAmount").val();
            Comm.ajaxPost('afterLoan/updateApproval',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.close(setUpLayer);
                    g_userManage.tableUser.ajax.reload();
                });
            }, "application/json");
        }
    });
}



//费用减免查看
function  costReductionSee(orderId,customerId,derateAmount,derateId,repayment_id) {
    debugger;
    var setUpLayer = layer.open({
        type : 1,
        title : '减免金额审批',
        shadeClose : false,
        area : ['740px','450px'],
        content : $('#settleDiv'),
        success:function (index, layero) {
            var param = {};
            param.id = derateId;
            Comm.ajaxPost('afterLoan/getDerateDetails', JSON.stringify(param), function (result) {
                console.log(result);
                //封装返回数据
                debugger
                var returnData = {};
                var resData = result.data;
                $("#lblPersonName").html(resData.person_name);
                $("#lblTel").html(resData.tel);
                $("#lblPayCount").html(resData.pay_count);
                $("#lblFee").html(resData.fee);
                // $("#lblManageFee").html(resData.manageFee);
                // $("#lblQuickTrialFee").html(resData.quickTrialFee);
                $("#lblRepaymentAmount").html(resData.repayment_amount);
                $("#lblAmount").html(resData.amount);
                $("#lblPenalty").html(parseFloat(resData.penalty) + (resData.default_interest?parseFloat(resData.default_interest):0) );
                $("#lblOverdueDays").html(resData.overdue_days);
                $("#lblTotal").html((parseFloat(resData.sunAmount ) + parseFloat(resData.penalty) + parseFloat(resData.default_interest)).toFixed(2) );//本金利息服务包+逾期手续费+逾期罚息
                $("#lblAmount").html(resData.repayment_amount);
                $("#reductionAmount").val(derateAmount);
                $("#effectiveDataLbl").html(formatTime(resData.effectiveData));
                if (resData.approval_state=='1'){
                    $("#agree").attr("checked","checked");
                    $("#agree").attr("disabled","disabled");
                    $("#agree1").attr("disabled","disabled");
                }else {
                    $("#agree1").attr("checked","checked");
                    $("#agree").attr("disabled","disabled");
                    $("#agree1").attr("disabled","disabled");
                }
            },"application/json");
        },
        yes : function(index, layero) {
            debugger
            var param = {};
            param.derateId = derateId;
            if ($("#agree").attr("checked")){
                param.approvalState="1";
            }else {
                param.approvalState="0";
            }
            Comm.ajaxPost('afterLoan/updateApproval',JSON.stringify(param),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.close(setUpLayer);
                    g_userManage.tableUser.ajax.reload();
                });
            }, "application/json");
        }

    });
}
