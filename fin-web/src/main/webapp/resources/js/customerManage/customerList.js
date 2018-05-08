var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        debugger
        if (g_userManage.fuzzySearch) {
            param.personName = $("input[name='personName']").val();
            param.tel=$.trim($("input[name='tel']").val());
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
var g_userManage_quota = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        param.customerId=$("#hdloanId").val();
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
    if(g_userManage.tableCustomer){
        g_userManage.tableCustomer.ajax.reload();
    }else{
        g_userManage.tableCustomer = $('#customer_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "destroy":true,//解决dataTable重新加载问题
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('customer/listO', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    debugger
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
                {"data": "customerId","orderable" : false},
                {"data": "personName","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "tel","orderable" : false},
                { "data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                // {"data": "current_quota_ratio","orderable" : false},
                {"data": "submitCount","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if(data==null){
                            return "0";
                        }else{
                            return data;
                        }
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var btnDel = $('<a class="tabel_btn_style" onclick="showDetail(\''+data.customerId+'\',\''+data.card+'\',\''+data.tel+'\',\''+data.occupationType+'\')">查看 </a>');
                var resetPwd=$('<a class="tabel_btn_style" onclick="resetPwd(\''+data.customerId+'\')"> 重置密码 </a>');
                var changeTel=$('<a class="tabel_btn_style" onclick="changeTel(\''+data.customerId+'\',\''+data.tel+'\')"> 更改手机号 </a>');
                var changeBankCard=$('<a class="tabel_btn_style" onclick="changeBankCard(\''+data.userId+'\',\''+data.personName+'\')"> 更改银行卡 </a>');
                var quotaSee = $('<a class="tabel_btn_style" onclick="quotaSee(\''+data.customerId+'\')">  提额明细</a>');
                var pro_quota_proportion= parseInt(data.pro_quota_proportion)+100;
                if (data.pro_quota_proportion==""){
                    pro_quota_proportion="0";
                }
                var spanQuota = $('<span >'+pro_quota_proportion+'</span>');
                // if(data.current_quota_ratio||data.current_quota_ratio==""){
                //     $('td', row).eq(6).append(spanQuota);
                // }
                $('td', row).eq(7).append(btnDel).append(resetPwd).append(changeTel).append(changeBankCard);
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableCustomer.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='personName']").val("");
                    $("#beginTime").val("");
                    $("#endTime").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableCustomer.ajax.reload();
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableCustomer.on("order.dt search.dt", function() {
            g_userManage.tableCustomer.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
});

//更改客户手机号
function changeTel(customerId,tel) {
    // console.log(userId);
    layerAdd = layer.open({
        type : 1,
        title : "更改手机号",
        shadeClose : true, //点击遮罩关闭层
        area : [ '550px', '120px' ],
        content : $('#changeTelDiv').show(),
        btn : [ '保存', '取消' ],
        success :function () {
            $('#oldTel').val('');
            $('#oldTel').val(tel);
            $('#oldTel').attr("disabled",true);
            $('#newTel').val('');
        },
        yes:function(index, layero){
            var oldTel=$('#oldTel').val();
            var newTel = $('#newTel').val();
            if(oldTel == newTel){
                layer.msg('新旧手机号一致',{time:2000});
                return false;
            }
            if (!newTel) {
                layer.msg('请填写新手机号!',{time:2000});
                return false;
            }
            if(!(/^1[3|7|4|5|8][0-9]\d{4,8}$/).test(newTel)){
                layer.msg("号码格式不正确",{time:2000});
                return false;
            }
            var param={};
            param.newTel=newTel;
            param.customerId=customerId;
            Comm.ajaxPost('customer/changeTel',JSON.stringify(param),function(data){
                if(data.code =='0'){
                    layer.msg(data.msg,{time:2000},function(){
                        layer.closeAll();
                        g_userManage.tableCustomer.ajax.reload();
                    });
                }else{
                    layer.msg(data.msg,{time:2000});
                }

                }, "application/json"
            );
        }
    });
}

function changeBankCard(userId, customerName)
{
    layer.confirm('确定允许客户'+customerName+'更换银行卡？', {icon: 3, title:'操作提示'}, function(index){
        Comm.ajaxPost('customer/sendBindingCardMsg',JSON.stringify({userId:userId}),function(data){
                layer.msg(data.msg,{time:2000});
            }, "application/json");
    })
}


function showDetail(id,card,tel,occupationType){
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
        title : '查看客户资料',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : $('#container'),
        success : function(index, layero) {
            var param = {};
            param.customerId = id;
            param.type = occupationType;
            param.orderId = "";
            Comm.ajaxPost("customer/customerDetails",JSON.stringify(param),function(result){
                debugger
                var data = result.data;
                if (data){
                    var person=data.personList[0];//个人信息
                    var deviceInfo = data.customerDeviceInfo;//设备信息
                    var linkmanList=data.linkmanList;//联系人信息
                    var occupationList = data.occupationList[0];//职业信息
                    var customerContact = data.customerContact;//客户的通讯信息
                    var customer = data.customer;//客户信息
                    if(customer){
                        //if(customer.callRecordUrl&&customer.callRecordUrl!=''){
                            var btn='<a class="tabel_btn_style" href="'+_ctx+'/customer/getTelephoneRecord?customerId='+customer.id+'" target="_blank"> 话单下载 </a>';
                            $("#callRecordUrl").html(btn);
                        //}else {
                        //    $("#callRecordUrl").html('未生成话单');
                        //}
                    }
                }
                //银行卡信息
                if (data.bankCard)
                {
                    $("#bankCard").html(data.bankCard.bank_card);
                    $("#accountBank").html(data.bankCard.account_bank);
                }

                //个人信息
                var marry = "";
                if(person){
                    debugger
                    $("#realname").html(person.realname);//姓名
                    $("#sex").html(person.sex_name);//姓别
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

                    $("#cusCard").html(card);
                    debugger
                    var datetime = new Date();
                    var year=datetime.getFullYear();
                    var cardYear = card.substr(6,4);
                    $("#age").html(year-cardYear);


                    $("#house").html(person.houseproperty);

                    $("#cusTel").html(tel);


                    $("#bankTel").html(person.tel);
                    $("#bankNo").html(person.bank_card);
                    $("#withPerson").html(person.withPerson);
                    $('#tdRefereeId').text(person.APEX2);//推荐人编号
                    $('#tdReferee').text(person.APEX1);//推荐人名称
                    $("#applyCensus").text(person.card_register_address);//户籍
                    $("#tdBirth").text(person.birth);//出生日期
                    $("#applyMerry").text(person.marry_name);//婚姻状况
                    $("#zcardSrcBase64").attr('src',person.zcardSrcBase64);
                    $("#fcardSrcBase64").attr('src',person.fcardSrcBase64);
                    $("#face_src").attr('src',person.face_src);
                }
                //公司信息
                if(occupationList){
                    $("#unitName").text(occupationList.companyName);//单位名称
                    $("#unitPro").text(occupationList.companyProperty);//单位性质
                    $("#unitTel").text(occupationList.companyCode+occupationList.companyPhone);//单位电话
                    var address=occupationList.companyAddress.replace(/\//g, '');
                    $("#unitAddr").text(occupationList.provinceName+occupationList.cityName+occupationList.districtName);//单位地址city_name, district_name
                    $("#unitDepart").text(occupationList.department);//部门
                    $("#unitGrade").text(occupationList.posLevel);//职级
                    $("#tdDetailed").text(occupationList.companyAddress);//单位地址
                }
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
                //联系人信息
                var html = '';
                var html1 = '';
                $("#relation").empty();
                for(var i=0;i<linkmanList.length;i++){
                    var rel = linkmanList[i].mainSign;
                    if(rel=='0'){//直系
                        var yesno = "";
                        html=html+ '<tr>'+
                            '<td width="10%" >关系</td>'+
                            '<td width="23%">'+linkmanList[i].relationshipName+'</td>'+
                            '<td width="10%" >名称</td>'+
                            '<td width="23%">'+linkmanList[i].linkName+'</td>'+
                            '<td width="10%" >联系方式</td>'+
                            '<td width="23%">'+linkmanList[i].contact+'</td>'+
                            '</tr>';

                    }else {
                        var yesno = "";
                        html1= html1+ '<tr>'+
                            '<td width="10%" >关系</td>'+
                            '<td width="23%">'+linkmanList[i].relationshipName+'</td>'+
                            '<td width="10%" >名称</td>'+
                            '<td width="23%">'+linkmanList[i].linkName+'</td>'+
                            '<td width="10%" >联系方式</td>'+
                            '<td width="23%">'+linkmanList[i].contact+'</td>'+
                            '</tr>';

                    }
                }
                debugger
                html='<tr><td colspan="6"  style="text-align: left">直系联系人</td></tr>'+html;
                html1='<tr><td colspan="6" style="text-align: left">其他联系人</td></tr>'+html1;
                $("#relation").html('');//直系
                $("#relation1").html('');//直系
                $("#relation").append(html);//直系
                $("#relation1").append(html1);//直系
            }, "application/json");
        }
    });
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
//格式时间
function getTime(inputTime) {
    var y,m,d;
    if(inputTime) {
        y = inputTime.slice(0,4);
        m = inputTime.slice(4,6);
        d = inputTime.slice(6,8);
        return y+'年'+m+'月'+d+'日';
    }
}

function  quotaSee(customerId) {
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        shadeClose : false,
        area : [ '80%', '60%' ],
        content : $('#quotaLoan').show(),
        success : function(index, layero) {
            if(!$("#hdloanId").val()){
                $("#hdloanId").val(customerId);
                g_userManage_quota.tableUser = $('#quotaLoanTable').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        debugger
                        var queryFilter = g_userManage_quota.getQueryCondition(data);
                        Comm.ajaxPost('customer/quotaList', JSON.stringify(queryFilter), function (result) {
                            debugger
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
                            "data": null,
                            "defaultContent":"",
                            "orderable" : false,
                            "width" : "30px"
                        },
                        {"data": "order_no","orderable" : false},
                        {"data": "apply_money","orderable" : false},
                        {"data": "periods","orderable" : false},
                        {"data": "current_quota_ratio","orderable" : false},
                        {"data": "loan_time","orderable" : false,
                            "render": function (data, type, row, meta) {
                                return formatTime(data);
                            }
                        },//loanId
                        {"data": "remark","orderable" : false},
                        // {"data":'remark',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                        //     if (data=='0') {
                        //         return "还款提额";
                        //     }
                        //     else if (data=='1') {
                        //         return "逾期减额";
                        //     }
                        // }}

                        //{"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
                    ],
                    "createdRow": function(row, data, index, settings, json) {

                    },
                    "initComplete" : function(settings,json) {
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_userManage_quota.tableUser.on("order.dt search.dt", function() {
                    g_userManage_quota.tableUser.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
            }else {
                $("#hdloanId").val(customerId);
                g_userManage_quota.tableUser.ajax.reload();
            }
        }
    })
}

//添加催收记录
function resetPwd(customerId) {
    layerAdd = layer.open({
        type : 1,
        title : "重置密码",
        shadeClose : true, //点击遮罩关闭层
        area : [ '550px', '120px' ],
        content : $('#updateWpd').show(),
        btn : [ '保存', '取消' ],
        success :function () {
            $('#lblpwd1').val("");
            $('#lblpwd2').val("");
        },
        yes:function(index, layero){
            var lblpwd1 = $('#lblpwd1').val();
            var lblpwd2 = $('#lblpwd2').val();
            if (!lblpwd1) {
                layer.msg('请填写新密码!',{time:2000});
                return;
            }
            if (!lblpwd2) {
                layer.msg('请填写确认密码!',{time:2000});
                return;
            }
            if(lblpwd1!=lblpwd2){
                layer.msg('新密码与确认密码不一致!',{time:2000});
                return;
            }
            if(lblpwd2.length<6){
                layer.msg('新密码与确认密码长度不少于6位!',{time:2000});
                return;
            }
            var param={};

            param.lblpwd1 = lblpwd1;
            param.lblpwd2 = lblpwd2;
            param.customerId=customerId;
            Comm.ajaxPost('customer/updatePwd',JSON.stringify(param),function(data){
                    layer.msg(data.msg,{time:2000},function(){
                        layer.close(layerAdd);
                    });
                }, "application/json"
            );
        }
    });
}