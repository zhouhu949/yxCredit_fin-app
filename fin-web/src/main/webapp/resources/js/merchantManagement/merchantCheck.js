/**
 * Created by Administrator on 2017/12/5.商户审核js
 */
//查询反欺诈专员的对象
var g_autiFraudManageShow = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_autiFraudManageShow.fuzzySearch;
        param.merchantId=$("#merchantId").val();
        if (g_autiFraudManageShow.fuzzySearch) {
            //姓名
            param.trueName=$.trim($("#autiFraudManName").val());
            //手机号
            param.tel= $.trim($("#autiFraudManTel").val());
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//获取全部商户级别信息
var g_userManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            //商户名
            param.merchantName = $.trim($("#search_merchantName").val());
            //申请人
            param.applyName = $.trim($("#search_apply_name").val());
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//显示商户列表
$(function (){
    // laydate(add_license_date);
    g_userManage.tableUser = $('#sign_list').dataTable($.extend({
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
            Comm.ajaxPost('merchantCheck/checkMerchantsList', JSON.stringify(queryFilter), function (result) {
                // console.log(result);
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
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            // {"data": "id","orderable" : false},//商户编号
            {"data": "merName","orderable" : false},//商户名称
            {"data": "mainBusiness","orderable" : false},//主营业务
            {"data": "provinceCityiDstric","orderable" : false},//地址 省市区拼接成的字符串
            {"data": "address","orderable" : false},//详细地址
            {"data": "applyName","orderable" : false},//申请人姓名
            {"data": "checkState","orderable" : false,"render": function (data, type, row, meta) {////审核状态 0-待审核(默认) 1-审核中 2-审核通过 3-审核拒绝
                 if(data=='1'){
                    return '审核中'
                }if(data=='2'){
                    return '审核通过'
                }if(data=='3'){
                    return '审核拒绝'
                }
            }},
            {"data": "fanQIZhaState","orderable" : false,"render": function (data, type, row, meta) {////反欺诈状态 0-未发起(默认) 1-反欺诈进行中(反欺诈进行中不能审核操作) 2-反欺诈结束',
                 if(data=='1'){
                    return '反欺诈处理中'
                }if(data=='2'){
                    return '反欺诈结束'
                }if(data=='0'){
                    return '未发起反欺诈'
                }
            }},
            {"data": "null", "orderable": false, "defaultContent":""}//操作
        ],"createdRow": function ( row, data, index,settings,json ) {
            if(data.checkState !=1 ){
                var btn_show=$('<a class="tabel_btn_style" onclick="(showOneMerchant(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">查看 </a>');//查看
                $("td",row).eq(8).append(btn_show);
            }else {
                var btn_show=$('<a class="tabel_btn_style" onclick="(showOneMerchant(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">查看 </a>');//查看
                var btn_check=$('<a class="tabel_btn_style" onclick="(checkOneMerchant(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))"> 审核 </a>');//编辑
                //var btn_fanQiZha=$('<a class="tabel_btn_style" onclick="(goFanQiZha(\''+data.id+'\'))"> 发起反欺诈 </a>');//编辑
                $("td", row).eq(8).append(btn_show).append(btn_check);//.append(btn_fanQiZha);
            }
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
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
    }).draw();
});
//查看商户的方法
function showOneMerchant(merchantId,fanQIZhaState,checkState){
    //反欺诈状态结束就显示该商户的反欺诈信息
    if(fanQIZhaState == '2'){
        $("#imgs_fanqizha").css("display",'block');
    }else{
        $("#imgs_fanqizha").css("display",'none');
    }
    $("#shenhe").css("display","none");
    $("#fanqizha").css("display","none");
    //动态加载省份下拉选
    provinceSelAdd($("#province"));
    provinceSelAdd($("#faren_province"));
    provinceSelAdd($("#weituo_province"));
    provinceSelAdd($("#public_province"));
    //动态加载账户管理内的省市区下拉选
    provinceSelAdd($("#faren_province_manage"));
    provinceSelAdd($("#weituo_province_manage"));
    provinceSelAdd($("#public_province_manage"));
    //银行下拉选
    addBankOpt($("#add_persion_opening_bank_head"));
    addBankOpt($("#add_money_bailor_oppen_bank_head"));
    addBankOpt($("#add_public_opening_bank_head"));
    //动态加载企业类型
    getMerchantType();
    //清除模板之前存在的内容
    clearModel();
    $("#merchantCheck_information").html("");
    $("#remarksContent_fanqizha").html("");
    layer.open({
        type : 1,
        title : '查看商户信息',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%'  ],
        content : $('#div_add_merchant'),
        success : function(index, layero) {
            if(checkState == '0' || checkState == '1'){
                var param={};
                param.merchantId=merchantId;
                Comm.ajaxPost('merchantListController/getMerchant', JSON.stringify(param),
                    function (data) {
                        var merchantBasic=data.data.merchantBasicInformation;//商户基本信息
                        var merchantAccounts=data.data.merchantAccounts;//商户账户信息 三个账户
                        var merchantImgs=data.data.merchantImgs;//商户关联图片
                        debugger
                        console.log(merchantBasic);
                        // console.log(merchantAccounts);
                        // console.log(merchantImgs);
                        //商户基本信息merchantBasic
                        $('#add_mer_name').val(merchantBasic.merName);//公司名称
                        $('#add_license_number').val(merchantBasic.licenseNumber);//营业执照注册号
                        $('#add_main_business').val(merchantBasic.mainBusiness);//主营业务
                        $('#add_type').val(merchantBasic.type);//企业类型
                        $('#add_license_date').val(merchantBasic.licenseDate);//营业执照日期
                        $('#add_registered_capital').val(merchantBasic.registeredCapital);//注册资本
                        $('#add_employees_number').val(merchantBasic.employeesNumber);//员工人数
                        $('#add_merchant_email').val(merchantBasic.merEmail);//门店邮箱地址
                        //门店地址
                        $("#province").val(merchantBasic.provincesId);//省
                        citySelAdd(merchantBasic.provincesId,$("#city"));
                        $("#city").val(merchantBasic.cityId);//市
                        areaSelAdd(merchantBasic.cityId,$("#area"));
                        $("#area").val(merchantBasic.districId);//区
                        $('#add_merchant_tel').val(merchantBasic.merTel);//门店电话
                        $('#add_merchant_detail_address').val(merchantBasic.merDetailAddress);//详细地址
                        //申请人信息
                        $('#add_apply_name').val(merchantBasic.applyName);//申请人姓名
                        $('#add_apply_tel').val(merchantBasic.applyTel);//申请人电话
                        $('#add_apply_idcard').val(merchantBasic.applyCard);//申请人身份证号码
                        //备注说明
                        $("#remarksContent").val(merchantBasic.merDes);
                        //账户信息
                        for(var i=0;i<merchantAccounts.length;i++){
                            //0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
                            if(merchantAccounts[i].type == '0'){
                                //法人信息
                                $('#add_person_name').val(merchantAccounts[i].name);//法人姓名
                                $('#add_person_tel').val(merchantAccounts[i].tel);//法人电话
                                $('#add_persion_card').val(merchantAccounts[i].idcard);//法人身份证号
                                $('#add_persion_bank_account').val(merchantAccounts[i].account);//法人银行卡号
                                $("#faren_province").val(merchantAccounts[i].provinceId);//法人银行卡开户省份id
                                citySelAdd(merchantAccounts[i].provinceId,$("#faren_city"));
                                $("#faren_city").val(merchantAccounts[i].cityId); //法人银行卡开户城市id
                                $('#add_persion_opening_bank').val(merchantAccounts[i].bankName);//法人银行卡开户行
                                $("#add_persion_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                            }else if(merchantAccounts[i].type == '1'){
                                //收款委托人
                                $("#add_money_bailor_name").val(merchantAccounts[i].name)//收款委托人姓名
                                $("#add_money_bailor_tel").val(merchantAccounts[i].tel);//收款委托人电话
                                $("#add_money_bailor_idcard").val(merchantAccounts[i].idcard);//收款委托人身份证号
                                $("#add_money_bailor_account").val(merchantAccounts[i].account);//收款委托人银行卡号
                                $("#weituo_province").val(merchantAccounts[i].provinceId);//省
                                citySelAdd(merchantAccounts[i].provinceId,$("#weituo_city"));
                                $("#weituo_city").val(merchantAccounts[i].cityId);//市
                                $("#add_money_bailor_oppen_bank").val(merchantAccounts[i].bankName);//收款委托人开户行
                                $("#add_money_bailor_oppen_bank_head").val(merchantAccounts[i].bankHeadId);
                            }else if(merchantAccounts[i].type == '2'){
                                //对公账号
                                $('#add_public_account_name').val(merchantAccounts[i].name);//对公账号名称
                                $('#add_public_bank_account').val(merchantAccounts[i].account);//对公账号
                                $("#public_province").val(merchantAccounts[i].provinceId);
                                citySelAdd(merchantAccounts[i].provinceId,$("#public_city"));
                                $("#public_city").val(merchantAccounts[i].cityId);
                                $('#add_public_opening_bank').val(merchantAccounts[i].bankName);//对公开户行
                                $("#add_public_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                            }
                        }
                        //审核信息
                        if(checkState =='0'){
                            $("#merchantCheck_information").html("还未发起审核");
                        }else if(checkState=='1'){
                            $("#merchantCheck_information").html("审核进行中");
                        }
                        //反欺诈备注
                        var paramSug={};
                        paramSug.relId=merchantId;
                        paramSug.type='2';
                        paramSug.nodeId='4';
                        Comm.ajaxPost('merchantCheck/getMerchantSuggestion', JSON.stringify(paramSug),
                            function (data) {
                                $("#remarksContent_fanqizha").html(data.data.approveSuggestion);
                                // alert(data.data.approveSuggestion);
                            },"application/json");
                        $("#remarksContent_fanqizha").html();
                        //图片信息 0-身份证正面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像
                        for(var i=0;i<merchantImgs.length;i++){
                            if(merchantImgs[i].imgType =='0'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_zhengmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_zhengmian",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                                $("#"+'td_idcard_zhengmian').find("[type='file']").attr("disabled",true);
                            }else if(merchantImgs[i].imgType =='1'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_fanmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_fanmian",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='2'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_yinyezhizhao').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_yinyezhizhao",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='3'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_touxiang_logo').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_touxiang_logo",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='4'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shinei').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shinei",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='5'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shouyintai').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shouyintai",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='6'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_jiejing').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_jiejing",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='7'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_farenyinhangka').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_farenyinhangka",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='8'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shoukuanweituoshu').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shoukuanweituoshu",merchantImgs[i].imgUrl,1);
                            }
                            /**************************反欺诈图片********************************/
                            if(merchantImgs[i].imgType =='10'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_zhengmian_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_zhengmian_fqz",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                                $("#"+'td_idcard_zhengmian_fqz').find("[type='file']").attr("disabled",true);
                            }else if(merchantImgs[i].imgType =='11'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_fanmian_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_fanmian_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='12'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_yinyezhizhao_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_yinyezhizhao_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='13'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_touxiang_logo_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_touxiang_logo_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='14'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shinei_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shinei_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='15'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shouyintai_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shouyintai_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='16'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_jiejing_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_jiejing_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='17'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_farenyinhangka_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_farenyinhangka_fqz",merchantImgs[i].imgUrl,1);
                            }

                            //设置优先级图片和input的优先级(专用于右键保存图片)
                            $("#div_add_merchant").find("img").css({"z-index":"10"});
                            $("#div_add_merchant").find("input").css({"z-index":"-1"});

                            //设置只读
                            $("#div_add_merchant").find("input").attr("disabled","disabled");
                            $("#div_add_merchant").find("select").attr("disabled","disabled");
                            $("#div_add_merchant").find("textarea").attr("disabled","disabled");

                        }
                        // $("#add_persion_opening_bank_head").val();
                    }, "application/json"
                );
            }
            else if(checkState == '2' || checkState == '3'){
                var param={};
                param.nodeId='3';//代表商户审核的时候插入的审核意见
                param.merchantId=merchantId;
                Comm.ajaxPost('merchantListController/getMerchant', JSON.stringify(param),
                    function (data) {
                        var merchantBasic=data.data.merchantBasicInformation;//商户基本信息
                        var merchantAccounts=data.data.merchantAccounts;//商户账户信息 三个账户
                        var merchantImgs=data.data.merchantImgs;//商户关联图片
                        console.log(merchantBasic);
                        // console.log(merchantAccounts);
                        // console.log(merchantImgs);
                        //商户基本信息merchantBasic
                        $('#add_mer_name').val(merchantBasic.merName);//公司名称
                        $('#add_license_number').val(merchantBasic.licenseNumber);//营业执照注册号
                        $('#add_main_business').val(merchantBasic.mainBusiness);//主营业务
                        $('#add_type').val(merchantBasic.type);//企业类型
                        $('#add_license_date').val(merchantBasic.licenseDate);//营业执照日期
                        $('#add_registered_capital').val(merchantBasic.registeredCapital);//注册资本
                        $('#add_employees_number').val(merchantBasic.employeesNumber);//员工人数
                        $('#add_merchant_email').val(merchantBasic.merEmail);//门店邮箱
                        //门店地址
                        $("#province").val(merchantBasic.provincesId);//省
                        citySelAdd(merchantBasic.provincesId,$("#city"));
                        $("#city").val(merchantBasic.cityId);//市
                        areaSelAdd(merchantBasic.cityId,$("#area"));
                        $("#area").val(merchantBasic.districId);//区
                        $('#add_merchant_tel').val(merchantBasic.merTel);//门店电话
                        $('#add_merchant_detail_address').val(merchantBasic.merDetailAddress);//详细地址
                        //申请人信息
                        $('#add_apply_name').val(merchantBasic.applyName);//申请人姓名
                        $('#add_apply_tel').val(merchantBasic.applyTel);//申请人电话
                        $('#add_apply_idcard').val(merchantBasic.applyCard);//申请人身份证号码
                        //备注说明
                        $("#remarksContent").val(merchantBasic.merDes);
                        //账户信息
                        for(var i=0;i<merchantAccounts.length;i++){
                            //0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
                            if(merchantAccounts[i].type == '0'){
                                //法人信息
                                $('#add_person_name').val(merchantAccounts[i].name);//法人姓名
                                $('#add_person_tel').val(merchantAccounts[i].tel);//法人电话
                                $('#add_persion_card').val(merchantAccounts[i].idcard);//法人身份证号
                                $('#add_persion_bank_account').val(merchantAccounts[i].account);//法人银行卡号
                                $("#faren_province").val(merchantAccounts[i].provinceId);//法人银行卡开户省份id
                                citySelAdd(merchantAccounts[i].provinceId,$("#faren_city"));
                                $("#faren_city").val(merchantAccounts[i].cityId); //法人银行卡开户城市id
                                $('#add_persion_opening_bank').val(merchantAccounts[i].bankName);//法人银行卡开户行
                                $("#add_persion_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                            }else if(merchantAccounts[i].type == '1'){
                                //收款委托人
                                $("#add_money_bailor_name").val(merchantAccounts[i].name)//收款委托人姓名
                                $("#add_money_bailor_tel").val(merchantAccounts[i].tel);//收款委托人电话
                                $("#add_money_bailor_idcard").val(merchantAccounts[i].idcard);//收款委托人身份证号
                                $("#add_money_bailor_account").val(merchantAccounts[i].account);//收款委托人银行卡号
                                $("#weituo_province").val(merchantAccounts[i].provinceId);//省
                                citySelAdd(merchantAccounts[i].provinceId,$("#weituo_city"));
                                $("#weituo_city").val(merchantAccounts[i].cityId);//市
                                $("#add_money_bailor_oppen_bank").val(merchantAccounts[i].bankName);//收款委托人开户行
                                $("#add_money_bailor_oppen_bank_head").val(merchantAccounts[i].bankHeadId);
                            }else if(merchantAccounts[i].type == '2'){
                                //对公账号
                                $('#add_public_account_name').val(merchantAccounts[i].name);//对公账号名称
                                $('#add_public_bank_account').val(merchantAccounts[i].account);//对公账号
                                $("#public_province").val(merchantAccounts[i].provinceId);
                                citySelAdd(merchantAccounts[i].provinceId,$("#public_city"));
                                $("#public_city").val(merchantAccounts[i].cityId);
                                $('#add_public_opening_bank').val(merchantAccounts[i].bankName);//对公开户行
                                $("#add_public_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                            }
                        }

                        //审核信息
                        if(checkState =='2'){
                            // $("#merchantCheck_information").html("审核结果：审核通过"+merchantBasic.suggestion);
                            $("#merchantCheck_information").html('<span style="color: red;">审核结果：审核通过</span><br>审核意见：'+merchantBasic.suggestion);
                        }else if(checkState =='3'){
                            $("#merchantCheck_information").html('<span style="color: red;">审核结果：审核拒绝</span><br>审核意见：'+merchantBasic.suggestion);
                        }
                        //反欺诈备注
                        var paramSug={};
                        paramSug.relId=merchantId;
                        paramSug.type='2';
                        paramSug.nodeId='4';
                        Comm.ajaxPost('merchantCheck/getMerchantSuggestion', JSON.stringify(paramSug),
                            function (data) {
                                $("#remarksContent_fanqizha").html(data.data.approveSuggestion);
                                // alert(data.data.approveSuggestion);
                            },"application/json");

                        //图片信息 0-身份证正面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像
                        for(var i=0;i<merchantImgs.length;i++){
                            if(merchantImgs[i].imgType =='0'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_zhengmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_zhengmian",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                                $("#"+'td_idcard_zhengmian').find("[type='file']").attr("disabled",true);
                            }else if(merchantImgs[i].imgType =='1'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_fanmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_fanmian",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='2'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_yinyezhizhao').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_yinyezhizhao",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='3'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_touxiang_logo').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_touxiang_logo",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='4'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shinei').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shinei",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='5'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shouyintai').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shouyintai",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='6'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_jiejing').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_jiejing",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='7'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_farenyinhangka').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_farenyinhangka",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='8'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shoukuanweituoshu').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shoukuanweituoshu",merchantImgs[i].imgUrl,1);
                            }

                            /**************************反欺诈图片********************************/
                            if(merchantImgs[i].imgType =='10'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_zhengmian_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_zhengmian_fqz",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                                $("#"+'td_idcard_zhengmian_fqz').find("[type='file']").attr("disabled",true);
                            }else if(merchantImgs[i].imgType =='11'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_idcard_fanmian_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_idcard_fanmian_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='12'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_yinyezhizhao_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_yinyezhizhao_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='13'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_touxiang_logo_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_touxiang_logo_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='14'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shinei_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shinei_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='15'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_shouyintai_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_shouyintai_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='16'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_jiejing_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_jiejing_fqz",merchantImgs[i].imgUrl,1);
                            }else if(merchantImgs[i].imgType =='17'){
                                //先删除之前存在的添加样式的图片
                                $("#"+'td_farenyinhangka_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                                //再添加该图片div
                                appendImgDivToTd("td_farenyinhangka_fqz",merchantImgs[i].imgUrl,1);
                            }
                        }
                        // $("#add_persion_opening_bank_head").val();

                        //设置优先级图片和input的优先级(专用于右键保存图片)
                        $("#div_add_merchant").find("img").css({"z-index":"10"});
                        $("#div_add_merchant").find("input").css({"z-index":"-1"});

                        //设置整个网页只读
                        $("#div_add_merchant").find("input").attr("disabled","disabled");
                        $("#div_add_merchant").find("select").attr("disabled","disabled");
                        $("#div_add_merchant").find("textarea").attr("disabled","disabled");

                    }, "application/json"
                );
            }

            //设置优先级图片和input的优先级(专用于右键保存图片)
            $("#div_add_merchant").find("img").css({"z-index":"10"});
            $("#div_add_merchant").find("input").css({"z-index":"-1"});

            //设置整个网页只读
            $("#div_add_merchant").find("input").attr("disabled","disabled");
            $("#div_add_merchant").find("select").attr("disabled","disabled");
            $("#div_add_merchant").find("textarea").attr("disabled","disabled");

        }
    });
}
//审核按钮方法
function checkOneMerchant(merchantId,fanQIZhaState,checkState){
    //显示按钮
    $("#shenhe").css("display","inline");
    $("#fanqizha").css("display","inline");
    if(fanQIZhaState=='1'){
        layer.msg("该商户已被发起发欺诈，无法审核",{time:1000});return
    }else{
    $("#merchantId").val('');
    $("#fanQIZhaState").val('');
    $("#merchantId").val(merchantId);
    $("#fanQIZhaState").val(fanQIZhaState);
    //动态加载省份下拉选
    provinceSelAdd($("#province"));
    provinceSelAdd($("#faren_province"));
    provinceSelAdd($("#weituo_province"));
    provinceSelAdd($("#public_province"));
    //动态加载账户管理内的省市区下拉选
    provinceSelAdd($("#faren_province_manage"));
    provinceSelAdd($("#weituo_province_manage"));
    provinceSelAdd($("#public_province_manage"));
    //银行下拉选
    addBankOpt($("#add_persion_opening_bank_head"));
    addBankOpt($("#add_money_bailor_oppen_bank_head"));
    addBankOpt($("#add_public_opening_bank_head"));
    //动态加载企业类型
    getMerchantType();
    //动态加载商户分级
    addGrade();
    //清除模板之前存在的内容
    clearModel();
    layer.open({
        type : 1,
        title : '审核商户',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%'  ],
        content : $('#div_add_merchant'),
        success : function(index, layero) {
            var param={};
            param.merchantId=merchantId;
            Comm.ajaxPost('merchantListController/getMerchant', JSON.stringify(param),
                function (data) {
                    var merchantBasic=data.data.merchantBasicInformation;//商户基本信息
                    var merchantAccounts=data.data.merchantAccounts;//商户账户信息 三个账户
                    var merchantImgs=data.data.merchantImgs;//商户关联图片
                    // console.log(merchantImgs);
                    //商户基本信息merchantBasic
                    $('#add_mer_name').val(merchantBasic.merName);//公司名称
                    $('#add_license_number').val(merchantBasic.licenseNumber);//营业执照注册号
                    $('#add_main_business').val(merchantBasic.mainBusiness);//主营业务
                    $('#add_type').val(merchantBasic.type);//企业类型
                    $('#add_license_date').val(merchantBasic.licenseDate);//营业执照日期
                    $('#add_registered_capital').val(merchantBasic.registeredCapital);//注册资本
                    $('#add_employees_number').val(merchantBasic.employeesNumber);//员工人数
                    $('#add_merchant_email').val(merchantBasic.merEmail);//门店邮箱
                    //门店地址
                    $("#province").val(merchantBasic.provincesId);//省
                    citySelAdd(merchantBasic.provincesId,$("#city"));
                    $("#city").val(merchantBasic.cityId);//市
                    areaSelAdd(merchantBasic.cityId,$("#area"));
                    $("#area").val(merchantBasic.districId);//区
                    $('#add_merchant_tel').val(merchantBasic.merTel);//门店电话
                    $('#add_merchant_detail_address').val(merchantBasic.merDetailAddress);//详细地址
                    //申请人信息
                    $('#add_apply_name').val(merchantBasic.applyName);//申请人姓名
                    $('#add_apply_tel').val(merchantBasic.applyTel);//申请人电话
                    $('#add_apply_idcard').val(merchantBasic.applyCard);//申请人身份证号码
                    //备注说明
                    $("#remarksContent").val(merchantBasic.merDes);
                    //账户信息
                    for(var i=0;i<merchantAccounts.length;i++){
                        //0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
                        if(merchantAccounts[i].type == '0'){
                            //法人信息
                            $('#add_person_name').val(merchantAccounts[i].name);//法人姓名
                            $('#add_person_tel').val(merchantAccounts[i].tel);//法人电话
                            $('#add_persion_card').val(merchantAccounts[i].idcard);//法人身份证号
                            $('#add_persion_bank_account').val(merchantAccounts[i].account);//法人银行卡号
                            $("#faren_province").val(merchantAccounts[i].provinceId);//法人银行卡开户省份id
                            citySelAdd(merchantAccounts[i].provinceId,$("#faren_city"));
                            $("#faren_city").val(merchantAccounts[i].cityId); //法人银行卡开户城市id
                            $('#add_persion_opening_bank').val(merchantAccounts[i].bankName);//法人银行卡开户行
                            $("#add_persion_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                        }else if(merchantAccounts[i].type == '1'){
                            //收款委托人
                            $("#add_money_bailor_name").val(merchantAccounts[i].name)//收款委托人姓名
                            $("#add_money_bailor_tel").val(merchantAccounts[i].tel);//收款委托人电话
                            $("#add_money_bailor_idcard").val(merchantAccounts[i].idcard);//收款委托人身份证号
                            $("#add_money_bailor_account").val(merchantAccounts[i].account);//收款委托人银行卡号
                            $("#weituo_province").val(merchantAccounts[i].provinceId);//省
                            citySelAdd(merchantAccounts[i].provinceId,$("#weituo_city"));
                            $("#weituo_city").val(merchantAccounts[i].cityId);//市
                            $("#add_money_bailor_oppen_bank").val(merchantAccounts[i].bankName);//收款委托人开户行
                            $("#add_money_bailor_oppen_bank_head").val(merchantAccounts[i].bankHeadId);
                        }else if(merchantAccounts[i].type == '2'){
                            //对公账号
                            $('#add_public_account_name').val(merchantAccounts[i].name);//对公账号名称
                            $('#add_public_bank_account').val(merchantAccounts[i].account);//对公账号
                            $("#public_province").val(merchantAccounts[i].provinceId);
                            citySelAdd(merchantAccounts[i].provinceId,$("#public_city"));
                            $("#public_city").val(merchantAccounts[i].cityId);
                            $('#add_public_opening_bank').val(merchantAccounts[i].bankName);//对公开户行
                            $("#add_public_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                        }
                    }

                    //反欺诈备注
                    var paramSug={};
                    paramSug.relId=merchantId;
                    paramSug.type='2';
                    paramSug.nodeId='4';
                    Comm.ajaxPost('merchantCheck/getMerchantSuggestion', JSON.stringify(paramSug),
                        function (data) {
                            $("#remarksContent_fanqizha").html(data.data.approveSuggestion);
                            //alert(data.data.approveSuggestion);
                        },"application/json");
                    //审核信息
                        $("#merchantCheck_information").html('审核进行中');
                    //图片信息 0-身份证正面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像
                    for(var i=0;i<merchantImgs.length;i++){
                        if(merchantImgs[i].imgType =='0'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_idcard_zhengmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_idcard_zhengmian",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                            $("#"+'td_idcard_zhengmian').find("[type='file']").attr("disabled",true);
                        }else if(merchantImgs[i].imgType =='1'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_idcard_fanmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_idcard_fanmian",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='2'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_yinyezhizhao').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_yinyezhizhao",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='3'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_touxiang_logo').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_touxiang_logo",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='4'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_shinei').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_shinei",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='5'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_shouyintai').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_shouyintai",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='6'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_jiejing').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_jiejing",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='7'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_farenyinhangka').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_farenyinhangka",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='8'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_shoukuanweituoshu').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_shoukuanweituoshu",merchantImgs[i].imgUrl,1);
                        }
                        /**************************反欺诈图片********************************/
                        if(merchantImgs[i].imgType =='10'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_idcard_zhengmian_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_idcard_zhengmian_fqz",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                            $("#"+'td_idcard_zhengmian_fqz').find("[type='file']").attr("disabled",true);
                        }else if(merchantImgs[i].imgType =='11'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_idcard_fanmian_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_idcard_fanmian_fqz",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='12'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_yinyezhizhao_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_yinyezhizhao_fqz",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='13'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_touxiang_logo_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_touxiang_logo_fqz",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='14'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_shinei_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_shinei_fqz",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='15'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_shouyintai_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_shouyintai_fqz",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='16'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_jiejing_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_jiejing_fqz",merchantImgs[i].imgUrl,1);
                        }else if(merchantImgs[i].imgType =='17'){
                            //先删除之前存在的添加样式的图片
                            $("#"+'td_farenyinhangka_fqz').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                            //再添加该图片div
                            appendImgDivToTd("td_farenyinhangka_fqz",merchantImgs[i].imgUrl,1);
                        }
                    }

                    //设置优先级图片和input的优先级(专用于右键保存图片)
                    $("#div_add_merchant").find("img").css({"z-index":"10"});
                    $("#div_add_merchant").find("input").css({"z-index":"-1"});

                    //设置整个网页只读
                    $("#div_add_merchant").find("input").attr("disabled",true);
                    $("#div_add_merchant").find("select").attr("disabled",true);
                    $("#div_add_merchant").find("textarea").attr("disabled",true);

                    //审核商户的方法
                    $("#shenhe").unbind();
                    $("#shenhe").click(function () {
                        // console.log(merchantId);
                            layer.open({
                                type : 1,
                                title : '确认审核',
                                maxmin : true,
                                shadeClose : false,
                                area : [ '50%', '50%'  ],
                                content : $('#sureCheck').show(),
                                btn : [ '保存', '取消' ],
                                success : function(index, layero) {
                                    //清空节点内容
                                    $('#checkResult').val('');
                                    $('#merchant_grade').val('');
                                    $('#check_merchant_suggestion').val('');
                                    //审批通过展示商户等级选择，审批拒绝后隐藏商户等级选择
                                    $("#checkResult").blur(function () {
                                        if($("#checkResult").val()=='') {
                                            $('#merchant_grade_li').css("display","block");
                                        }else if($("#checkResult").val()=='2'){
                                            $('#merchant_grade_li').css("display","block");
                                        }else if($("#checkResult").val()=='3'){
                                            $('#merchant_grade_li').css("display","none");
                                        }
                                    });
                                },
                                yes:function(index,layero){
                                    var param = {};
                                    param.merchantId=merchantId;//商户id
                                    param.checkResult=$('#checkResult').val();//审核结果
                                    param.suggestion=$.trim($("#check_merchant_suggestion").val());//商户审批意见
                                    if( param.checkResult == ''){
                                        layer.msg("请选择审核结果",{time:1000});return
                                    }
                                    if(param.suggestion == ''){
                                        layer.msg("请填写审批意见",{time:1000});return
                                    }
                                    if(param.suggestion.length>90){
                                        layer.msg("审批意见不能超过90个字",{time:1000});return
                                    }
                                    //审核成功
                                    if(param.checkResult == '2'){
                                        param.merchantGrade=$('#merchant_grade').val();//商户等级
                                        if(param.merchantGrade == ''){
                                            layer.msg("请选择商户等级",{time:1000});return
                                        }
                                        Comm.ajaxPost('merchantCheck/checkOneMerchant', JSON.stringify(param), function (data) {
                                            layer.msg(data.msg, {time: 1000}, function () {
                                                layer.closeAll();
                                                g_userManage.fuzzySearch = true;
                                                g_userManage.tableUser.ajax.reload();
                                            });
                                        },"application/json");
                                    //审核拒绝
                                    }else if(param.checkResult == '3'){
                                        param.merchantGrade="";//商户等级设置成空字符串
                                        Comm.ajaxPost('merchantCheck/checkOneMerchant', JSON.stringify(param), function (data) {
                                            layer.msg(data.msg, {time: 1000}, function () {
                                                layer.closeAll();
                                                g_userManage.fuzzySearch = true;
                                                g_userManage.tableUser.ajax.reload();
                                            });
                                        },"application/json");
                                    }
                                }
                            });
                    });
                    // //反欺诈方法 此处做一次判断 反欺诈状态为0的才可以进行反欺诈
                    if(fanQIZhaState =='0'){
                        $("#fanqizha").css("display",'inline');
                        $("#imgs_fanqizha").css("display",'none');
                    }else if(fanQIZhaState =='1'){
                        $("#fanqizha").css("display",'none');
                        $("#imgs_fanqizha").css("display",'none');
                    }else if(fanQIZhaState =='2'){
                        $("#fanqizha").css("display",'none');
                        $("#imgs_fanqizha").css("display",'block');
                    }
                    $("#fanqizha").unbind();
                    $("#fanqizha").click(function () {
                        layer.open({
                            type : 1,
                            title : '反欺诈分配',
                            maxmin : true,
                            shadeClose : false,
                            area : [ '50%', '60%'  ],
                            content : $('#bidingSalesman'),
                            btn : [ '保存', '取消' ],
                            success : function(index, layero) {
                                //清空搜索框
                                $("#autiFraudManTel").val('');
                                $("#autiFraudManName").val('');
                                $("#des_fanQiZha").val('');
                                 var initFlag=$("#initFlag").val();
                                //先查询所有反欺诈专员
                                if(!initFlag){
                                // console.log(merchantId);
                                    g_autiFraudManageShow.tableUser = $('#autiFraudMan_list').dataTable($.extend({
                                        'iDeferLoading':true,
                                        "bAutoWidth" : false,
                                        "Processing": true,
                                        "ServerSide": true,
                                        "sPaginationType": "full_numbers",
                                        "bPaginate": true,
                                        "bLengthChange": false,
                                        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                                        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                                            var queryFilter = g_autiFraudManageShow.getQueryCondition(data);
                                            Comm.ajaxPost('merchantCheck/getAllAutiFraudMan', JSON.stringify(queryFilter), function (result) {
                                                 // console.log(result);
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
                                            {
                                                "className" : "cell-operation",
                                                "data": null,
                                                "defaultContent":"",
                                                "orderable" : false,
                                                "width" : "30px"
                                            },
                                            {"data": "trueName","orderable" : false},//姓名
                                            {"data": "tel","orderable" : false}//手机号
                                            // {"data": "null", "orderable": false, "defaultContent":""}//操作
                                        ],"createdRow": function ( row, data, index,settings,json ) {
                                            var btn_show=$('<input name="autiFraudxxxxxxx" type="radio" value= '+data.userId+' />');
                                            $("td", row).eq(0).append(btn_show);
                                        },
                                        "initComplete" : function(settings,json) {
                                            //搜索
                                            $("#search_autiFraud").click(function() {
                                                g_autiFraudManageShow.fuzzySearch = true;
                                                g_autiFraudManageShow.tableUser.ajax.reload();
                                            });
                                        }
                                    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                                    g_autiFraudManageShow.tableUser.on("order.dt search.dt", function() {
                                        // g_salesmanManage.tableUser.column(0, {
                                        //     search : "applied",
                                        //     order : "applied"
                                        // }).nodes().each(function(cell, i) {
                                        //     cell.innerHTML = i + 1
                                        // })
                                    }).draw();
                                    $("#initFlag").val(true);
                                }else{
                                    g_autiFraudManageShow.tableUser.ajax.reload();
                                }
                            },
                            yes: function (index,layero) {
                                var param={};
                                //获取选中的反欺诈专员
                                param.autiFraudId=$("input[name='autiFraudxxxxxxx']:checked").val();
                                //商户id
                                param.merchantId=merchantId;
                                // //获取反欺诈备注
                                // param.desFanQiZha=$("#des_fanQiZha").val();
                                //访问商户反欺诈专员分配controller
                                    Comm.ajaxPost('merchantCheck/merchantAutiFraudAllot', JSON.stringify(param), function (data) {
                                        layer.msg(data.msg, {time: 1000}, function () {
                                            layer.closeAll();
                                            g_autiFraudManageShow.fuzzySearch = true;
                                            g_autiFraudManageShow.tableUser.ajax.reload();
                                            g_userManage.fuzzySearch = true;
                                            g_userManage.tableUser.ajax.reload();

                                        });
                                    }, "application/json");
                            }
                        });
                    });
                }, "application/json"
            );
        }
    });
}
}

//清除复用模板之前存在的value 之前存在的输入信息
function clearModel() {
    //添加弹出窗口打开之后立即清除输入框以及隐藏域的内容
    $('#add_mer_name').val('');//公司名称
    $('#add_license_number').val('');//营业执照注册号
    $('#add_main_business').val('');//主营业务
    $('#add_type').val('');//企业类型
    $('#add_license_date').val('');//营业执照日期
    $('#add_registered_capital').val('');//注册资本
    $('#add_employees_number').val('');//员工人数
    // $('#add_mer_address').val('');//门店地址省市区
    //门店地址
    $("#province").val('');//省
    $("#city").val('')//市
    $("#area").val('')//区
    $('#add_merchant_tel').val('');//门店电话
    $('#add_merchant_detail_address').val('');//详细地址

    //申请人信息
    $('#add_apply_name').val('');//申请人姓名
    $('#add_apply_tel').val('');//申请人电话
    $('#add_apply_idcard').val('');//申请人身份证号码

    //法人信息
    $('#add_person_name').val('');//法人姓名
    $('#add_person_tel').val('');//法人电话
    $('#add_persion_card').val('');//法人身份证号
    $('#add_persion_bank_account').val('');//法人银行卡号
    $('#faren_province').val("");//省
    $('#faren_city').val("");//市
    $('#add_persion_opening_bank').val("");//银行全名

    //收款委托人
    $("#add_money_bailor_name").val('')//收款委托人姓名
    $("#add_money_bailor_tel").val('');//收款委托人电话
    $("#add_money_bailor_idcard").val('');//收款委托人身份证号
    $("#add_money_bailor_account").val('');//收款委托人银行卡号
    $('#weituo_province').val("");//省
    $('#weituo_city').val("");//市
    $('#add_money_bailor_oppen_bank').val("");//银行全名
    //对公账号
    $('#add_public_account_name').val('');//对公账号名称
    $('#add_public_bank_account').val('');//对公账号
    $('#public_province').val("");//省
    $('#public_city').val("");//市
    $('#add_public_opening_bank').val("");//银行全名

    //备注说明
    $("#remarksContent").val('');
    //反欺诈备注
    $("#fanQiZhaBeiiZhu").val('');
    //清空图片信息 藏有图片信息的隐藏域清空
    //法人身份证正面
    clearImgAndHidden("td_idcard_zhengmian");
    //法人身份证反面
    clearImgAndHidden("td_idcard_fanmian");
    //营业执照
    clearImgAndHidden("td_yinyezhizhao");
    //门头logo照
    clearImgAndHidden("td_touxiang_logo");
    //室内照
    clearImgAndHidden("td_shinei");
    //收银台
    clearImgAndHidden("td_shouyintai");
    //街景
    clearImgAndHidden("td_jiejing");
    //法人银行卡
    clearImgAndHidden("td_farenyinhangka");
    //收款委托书
    clearImgAndHidden("td_shoukuanweituoshu");
    /***********反欺诈影像清空****************/
    //法人身份证正面
    clearImgAndHidden("td_idcard_zhengmian_fqz");
    //法人身份证反面
    clearImgAndHidden("td_idcard_fanmian_fqz");
    //营业执照
    clearImgAndHidden("td_yinyezhizhao_fqz");
    //门头logo照
    clearImgAndHidden("td_touxiang_logo_fqz");
    //室内照
    clearImgAndHidden("td_shinei_fqz");
    //收银台
    clearImgAndHidden("td_shouyintai_fqz");
    //街景
    clearImgAndHidden("td_jiejing_fqz");
    //法人银行卡
    clearImgAndHidden("td_farenyinhangka_fqz");
    //收款委托书
    clearImgAndHidden("td_shoukuanweituoshu_fqz");
}
//给td添加图片的方法，传入参数是该td的id，图片地址，x代表着是查看还是编辑
function appendImgDivToTd(tdId,src,x) {//1代表查看，2代表编辑
    if(x == 1){
        var div_img=
            '<div>' +
            '<input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">'+
            '<input type="hidden" class="imgHidden"  name="src" value="\''+src+'\'"/>'+
            '<input type="hidden" class="imgHidden" name="originalName" value="">'+
            '<input type="hidden" class="imgHidden" name="isfront" value="0">'+
            '<form action="" enctype="multipart/form-data">'+
            '<div class="imagediv">'+
            '<span style="display: none; color: red;" class="closeImg" onclick="closeDelete(this,\''+tdId+'\')">X</span>'+
            '<input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,\''+tdId+'\')"/>'+              //编辑还是查看都不允许直接在原图片上面更改，只能删除后再改
            '<img class="addMaterial"  src=\''+src+'\' />'+
            '</div>'+
            '</form>'+
            '</div>' ;
    }else{
        var div_img=
            '<div>' +
            '<input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">'+
            '<input type="hidden" class="imgHidden"  name="src" value=\''+src+'\'/>'+
            '<input type="hidden" class="imgHidden" name="originalName" value="">'+
            '<input type="hidden" class="imgHidden" name="isfront" value="0">'+
            '<form action="" enctype="multipart/form-data">'+
            '<div class="imagediv">'+
            '<span style="display: inline-block; color: red; " class="closeImg" onclick="closeDelete(this,\''+tdId+'\')">X</span>'+
            '<input type="file"  name="pic" class="picShow" disabled onchange="setImagePreview1(this,\''+tdId+'\')"/>'+     //编辑还是查看都不允许直接在原图片上面更改，只能删除后再改
            '<img class="addMaterial"  src=\''+src+'\' />'+
            '</div>'+
            '</form>'+
            '</div>' ;
    }
    $("#"+tdId).append(div_img);
}
//格式化时间
function getTime(inputTime) {
    var y,m,d,h,mi,s;
    if(inputTime) {
        y = inputTime.slice(0,4);
        m = inputTime.slice(4,6);
        d = inputTime.slice(6,8);
        h = inputTime.slice(8,10);
        mi = inputTime.slice(10,12);
        s = inputTime.slice(12);
        return y+'-'+m+'-'+d;
    }
}
//根据td的id来清理图片隐藏域存着的信息
function clearImgAndHidden(tdId) {
    var listDiv= $("#"+tdId).children();
    for (i=0;i<listDiv.length;i++){
        listDiv.eq(i).children().eq(1).val('');
    }
    //清除之前选择的图片(直接将该td的内容设置成这样)
    $("#"+tdId).html(
        '<div>' +
        '<input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">'+
        '<input type="hidden" class="imgHidden" name="src" value=""/>'+
        '<input type="hidden" class="imgHidden" name="originalName" value="">'+
        '<input type="hidden" class="imgHidden" name="isfront" value="0">'+
        '<form action="" enctype="multipart/form-data">'+
        '<div class="imagediv">'+
        '<span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,\''+tdId+'\')">X</span>'+
        '<input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,\''+tdId+'\')"/>'+
        '<img class="addMaterial"  src="../resources/images/photoadd.png" />'+
        '</div>'+
        '</form>' +
        '</div>'
    );
}
//动态加载省份下拉选的公共方法
function provinceSelAdd(selProvinceContent) {
    //先清空内容
    selProvinceContent.html("");
    selProvinceContent.append('<option value="">'+"请选择"+'</option>');
    for(i=0;i<cityData3.length;i++){
        var opt='<option value="'+cityData3[i].value+'">'+cityData3[i].text+'</option>';
        // $('#change_province').append(opt);
        selProvinceContent.append(opt);
    }
}
//动态加载城市下拉选的公共方法(由省份下拉选触发)
function citySelAdd(value,selCityContent) {
    //先清空内容
    selCityContent.html("");
    selCityContent.append('<option value="">'+"请选择"+'</option>');
    for(var i=0;i<cityData3.length;i++){
        if(cityData3[i].value==value){
            var city=cityData3[i].children;
            for(j=0;j<city.length;j++){
                var opt='<option value="'+city[j].value+'">'+city[j].text+'</option>';
                selCityContent.append(opt);
            }
        }
    }
}
//动态加载区下拉选的公共方法(由城市下拉选触发)
function areaSelAdd(value,selAreaContent) {
    //先清空内容
    selAreaContent.html("");
    selAreaContent.append('<option value="">'+"请选择"+'</option>');
    for(i=0;i<cityData3.length;i++){
        var city=cityData3[i].children;
        for(j=0;j<city.length;j++){
            var area=city[j].children;
            if(city[j].value == value){
                for(k=0;k<area.length;k++) {
                    var opt = '<option value="' + area[k].value + '">' + area[k].text + '</option>';
                    selAreaContent.append(opt);
                }
            }
        }
    }
}
//动态加载银行下拉选
function addBankOpt(content){
    //先格式化该节点，再添加内容
    content.html('<option val="">请选择</option>');
    var param={};
    Comm.ajaxPost('merchantListController/getBanksHeads', JSON.stringify(param), function (data) {
        var bankList=data.data;
        for (var i=0;i<bankList.length;i++){
            var opt='<option value="'+bankList[i].id+'">'+bankList[i].name+'</option>';
            content.append(opt);
        }
    }, "application/json");
}
//动态加载企业类型
function getMerchantType(){
    $("#add_type").html('');
    $("#add_type").append(' <option value="">请选择</option>');
    Comm.ajaxPost('merchantListController/getMerchantType', JSON.stringify({}), function (data) {
            var list=data.data;
            for(i=0;i<list.length;i++){
                var opt='<option value="'+list[i].id+'">'+list[i].name+'</option>';
                $("#add_type").append(opt);
            }
        }, "application/json"
    );
}
//查询重置
function searchReset(){
    $("#search_apply_name").val("");
    $("#search_merchantName").val("");
    g_userManage.tableUser.ajax.reload();
}

//动态加载商户分级
function addGrade(){
    var queryFilter = g_userManage.getQueryCondition({});
Comm.ajaxPost('merchantListController/showAllMerchantGrade', JSON.stringify(queryFilter), function (data) {
    //清空下拉选节点
    $("#merchant_grade").html('<option value="" style="font-size: 10px;">请选择商户级别</option>');
    var list=data.data;
    for(var i=0;i<list.length;i++){
        var opt = $('<option class="tabel_btn_style" value=' + list[i].id + '> '+list[i].grade+' </option>');
        $("#merchant_grade").append(opt);
    }
    // $("#merchant_grade").val(merGradeId);
}, "application/json");
}