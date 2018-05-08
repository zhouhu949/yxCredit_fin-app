/**
 * Created by Administrator on 2017/12/5.商户审核js
 */
//查询订单的对象
var g_orderManageShow = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_orderManageShow.fuzzySearch;
        param.merchantId=$("#merchantId").val();
        if (g_orderManageShow.fuzzySearch) {
            //姓名
            param.customName=$.trim($("#search_customName").val());
            //手机号
            param.customTel= $.trim($("#search_custom_tel").val());
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//获取全部商户对象
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
            //省
            param.provinces_id=$("#province_search").val();
            //市
            param.city_id=$("#city_search").val();
            //区
            param.distric_id=$("#district_search").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//商品管理的对象
var g_merchantdiseManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.merchantId=$("#merchantId").val();
        param.fuzzySearch = g_merchantdiseManage.fuzzySearch;
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
//获取全部为被冻结且之前没有绑定过的办单员的对象
var g_salesmanManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_salesmanManage.fuzzySearch;
        param.merchantId=$("#merchant_id_biding_salesman").val();
        if (g_salesmanManage.fuzzySearch) {
            //办单员姓名
            param.salesmanName=$.trim($("#salesman_name").val());
            //办单员手机号
            param.salesmanTel=$.trim($("#salesman_tel").val());
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//获取该商户下的办单员的对象
var g_salesmanManageShow = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_salesmanManage.fuzzySearch;
        param.merchantId=$("#merchant_id_biding_salesman_show").val();
        if (g_salesmanManageShow.fuzzySearch) {
            //办单员姓名
            param.salesmanName=$.trim($("#salesman_name_show").val());
            //手机号
            param.salesmanTel= $.trim($("#salesman_tel_show").val());
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
    //搜索框省市区下拉选
    provinceSelAdd($("#province_search"));//搜索
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
    //账户管理银行下拉选
    addBankOpt($("#add_public_opening_bank_manage_head"));
    addBankOpt($("#add_money_bailor_oppen_bank_manage_head"));
    addBankOpt($("#add_persion_opening_bank_manage_head"));
    //动态加载企业类型
    getMerchantType();
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
            Comm.ajaxPost('enterMerchantManage/enterMerchantManageList', JSON.stringify(queryFilter), function (result) {
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
            {"data": "gradeName","orderable" : false},//商户等级
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
            {"data": "state","orderable" : false,"render": function (data, type, row, meta) {//商户状态：0-未激活 1-激活
                if(data=='1'){
                    return '已激活'
                }if(data=='0'){
                    return '未激活'
                }
            }},
            {"data": "null", "orderable": false, "defaultContent":""}//操作
        ],"createdRow": function ( row, data, index,settings,json ) {
            var btn_show_merchant=$('<a class="tabel_btn_style" onclick="(showOneMerchant(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">查看商户 </a>');//查看商户
            var btn_show_order=$('<a class="tabel_btn_style" onclick="(showMerchantOrders(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">查看订单 </a>');//查看订单
            var btn_goods_manage=$('<a class="tabel_btn_style" onclick="(goodsManage(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\',\''+data.state+'\'))">商品管理 </a>');//查看订单
            if(data.state=='1'){
               var btn_jihuoOrDongjie=$('<a class="tabel_btn_style" onclick="(jihuoOrDongjie(\''+data.id+'\',\''+0+'\',\''+data.checkState+'\'))">冻结 </a>');//冻结就是状态改成0
            }
            if(data.state=='0'){
               var btn_jihuoOrDongjie=$('<a class="tabel_btn_style" onclick="(jihuoOrDongjie(\''+data.id+'\',\''+1+'\',\''+data.checkState+'\'))">激活 </a>');//激活就是将状态改成1
            }
            var btn_account_manager=$('<a class="tabel_btn_style" onclick="(accountManager(\''+data.id+'\'))">账户管理 </a>');
            var btn_biding=$('<a class="tabel_btn_style" onclick="(bidingSalesmans(\''+data.id+'\',\''+data.state+'\'))">绑定办单员 </a>');
            var btn_showSalesman=$('<a class="tabel_btn_style" onclick="(showSalesman(\''+data.id+'\',\''+data.state+'\'))">办单员 </a>');
            var btn_editGrade=$('<a class="tabel_btn_style" onclick="(editMerGrade(\''+data.id+'\',\''+data.merGradeId+'\',\''+data.state+'\'))">修改商户分级 </a>');
            $("td", row).eq(10).append(btn_show_merchant).append(btn_show_order).append(btn_goods_manage)
                .append(btn_jihuoOrDongjie).append(btn_account_manager).append(btn_biding).append(btn_showSalesman).append(btn_editGrade);
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
    // if(checkState !='0' || checkState !='1' ){
    //     $('#merchantCheck_information_div').css("display",'none');
    // }
    //按钮隐藏
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
                Comm.ajaxPost('merchantListController/getMerchant', JSON.stringify(param), function (data) {
                        var merchantBasic=data.data.merchantBasicInformation;//商户基本信息
                        var merchantAccounts=data.data.merchantAccounts;//商户账户信息 三个账户
                        var merchantImgs=data.data.merchantImgs;//商户关联图片
                        // console.log(merchantBasic);
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
                        //门店地址
                        $("#province").val(merchantBasic.provincesId);//省
                        citySelAdd(merchantBasic.provincesId,$("#city"));
                        $("#city").val(merchantBasic.cityId);//市
                        areaSelAdd(merchantBasic.cityId,$("#area"));
                        $("#area").val(merchantBasic.districId);//区
                        $('#add_merchant_tel').val(merchantBasic.merTel);//门店电话
                        $('#add_merchant_detail_address').val(merchantBasic.merDetailAddress);//详细地址
                        $('#add_merchant_email').val(merchantBasic.merEmail);//商户邮箱
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
                        }
                        // $("#add_persion_opening_bank_head").val();

                        //设置优先级图片和input的优先级(专用于右键保存图片)
                        $("#div_add_merchant").find("img").css({"z-index":"10"});
                        $("#div_add_merchant").find("input").css({"z-index":"-1"});

                        //设置整个网页只读
                        $("#div_add_merchant").find("input").attr("disabled",true);
                        $("#div_add_merchant").find("select").attr("disabled",true);
                        $("#div_add_merchant").find("textarea").attr("disabled",true);

                    }, "application/json"
                );
            }
            else if(checkState == '2' || checkState == '3'){
                var param={};
                param.nodeId='3';
                param.merchantId=merchantId;
                Comm.ajaxPost('merchantListController/getMerchant', JSON.stringify(param), function (data) {
                        var merchantBasic=data.data.merchantBasicInformation;//商户基本信息
                        var merchantAccounts=data.data.merchantAccounts;//商户账户信息 三个账户
                        var merchantImgs=data.data.merchantImgs;//商户关联图片
                        // console.log(merchantBasic);
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
                        //门店地址
                        $("#province").val(merchantBasic.provincesId);//省
                        citySelAdd(merchantBasic.provincesId,$("#city"));
                        $("#city").val(merchantBasic.cityId);//市
                        areaSelAdd(merchantBasic.cityId,$("#area"));
                        $("#area").val(merchantBasic.districId);//区
                        $('#add_merchant_tel').val(merchantBasic.merTel);//门店电话
                        $('#add_merchant_detail_address').val(merchantBasic.merDetailAddress);//详细地址
                        $('#add_merchant_email').val(merchantBasic.merEmail);//商户邮箱
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
                                debugger
                                if(data.data != null){
                                    $("#remarksContent_fanqizha").html(data.data.approveSuggestion);
                                }
                                //alert(data.data.approveSuggestion);
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
                    $("#div_add_merchant").find("input").attr("disabled",true);
                    $("#div_add_merchant").find("select").attr("disabled",true);
                    $("#div_add_merchant").find("textarea").attr("disabled",true);

                    }, "application/json"
                );

            }
        }
    });
    //设置整个网页只读
    $("#div_add_merchant").find("input").attr("disabled",true);
    $("#div_add_merchant").find("select").attr("disabled",true);
    $("#div_add_merchant").find("textarea").attr("disabled",true);
}

//查看订单的方法
function showMerchantOrders(merchantId,fanQIZhaState,checkState){
    $("#merchantId").val(merchantId);
    $("#search_customName").val('');
    $("#search_custom_tel").val('');
    layer.open({
        type : 1,
        title : '订单列表',
        maxmin : true,
        shadeClose : false,
        area : [ '63%', '60%'  ],
        content : $('#order_list_div'),
        success : function(index, layero) {
            if(g_orderManageShow.tableUser){
                g_orderManageShow.tableUser.ajax.reload();
            }else{
                g_orderManageShow.tableUser = $('#order_list_table').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_orderManageShow.getQueryCondition(data);
                        Comm.ajaxPost('enterMerchantManage/getMerchantOrdersList', JSON.stringify(queryFilter), function (result) {
                            console.log(result);
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
                        {"data": "orderNo","orderable" : false},//订单编号
                        {"data": "customerName","orderable" : false},//姓名
                        {"data": "tel","orderable" : false},//手机号码
                        {"data": "card","orderable" : false},//证件号码
                        {"data": "creatTime","orderable" : false},//申请时间
                        {"data": "productTypeName","orderable" : false},//秒付产品
                        {"data": "state","orderable" : false,"render": function (data, type, row, meta) {////审核状态 0-待审核(默认) 1-审核中 2-审核通过 3-审核拒绝
                            if(data=="0"){
                                return "未提交";
                            }else if(data=="1"){
                                return "借款申请";
                            }else if(data=="2"){
                                return "自动化审批通过";
                            }else if(data=="3"){
                                return "自动化审批拒绝";
                            }else if(data=="4"){
                                return "自动化审批异常";
                            }else if(data=="5"){
                                return "人工审批通过";
                            }else if(data=="6"){
                                return "人工审批拒绝";
                            }else if(data=="7"){
                                return "合同确认";
                            }else if(data=="8"){
                                return "放款成功";
                            }else if(data=="9"){
                                return "结清";
                            }else if(data.toString()=="10"){
                                return "关闭";
                            }
                        }}
                    ],"createdRow": function ( row, data, index,settings,json ) {
                        // var btn_show_merchant=$('<a class="tabel_btn_style" onclick="(showOneMerchant(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">查看商户 </a>');//查看商户
                        // var btn_show_order=$('<a class="tabel_btn_style" onclick="(showMerchantOrders(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">查看订单 </a>');//查看订单
                        // var btn_goods_manage=$('<a class="tabel_btn_style" onclick="(goodsManage(\''+data.id+'\',\''+data.fanQIZhaState+'\',\''+data.checkState+'\'))">商品管理 </a>');//查看订单
                        // $("td", row).eq(10).append(btn_show_merchant).append(btn_show_order).append(btn_goods_manage);

                    },
                    "initComplete" : function(settings,json) {
                        //搜索
                        $("#btn_search_order").click(function() {
                            alert();
                            g_orderManageShow.fuzzySearch = true;
                            g_orderManageShow.tableUser.ajax.reload();
                        });
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_orderManageShow.tableUser.on("order.dt search.dt", function() {
                    g_orderManageShow.tableUser.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
            }

        }
    });
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
    //反欺诈影像
    clearImgAndHidden("fanQiZhaYingXiang");
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
    content.html('<option value="">请选择</option>');
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

//商品管理
function goodsManage(merchantId,fanQIZhaState,checkState,state){
    if(state != '1'){
        layer.msg('该商户还未激活', {time: 1000});
        return false;
    }
    $("#merchantId").val(merchantId);
    layer.open({
        type : 1,
        title : '商品管理',
        maxmin : true,
        shadeClose : false,
        area : [ '63%', '80%'  ],
        content : $('#merchantdise_div'),
        success : function(index, layero) {
            if(g_merchantdiseManage.tableUser){
                g_merchantdiseManage.tableUser.ajax.reload();
            }else{
                g_merchantdiseManage.tableUser = $('#merchantdise_list_table').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_merchantdiseManage.getQueryCondition(data);
                        Comm.ajaxPost('enterMerchantManage/merchantdiseManage', JSON.stringify(queryFilter), function (result) {
                            console.log(result);
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
                        {"data": "name","orderable" : false},//商品名称
                        {"data": "merchandiseName","orderable" : false},//e商品类型
                        {"data": "merchandiseBrandName","orderable" : false},//d品牌名称
                        {"data": "merchandiseModelName","orderable" : false},//c具体型号
                        {"data": "merchandiseVersionName","orderable" : false},//b具体版本
                        {"data": "null", "orderable": false, "defaultContent":""}//操作
                    ],"createdRow": function ( row, data, index,settings,json ) {
                        var btn_del=$('<a class="tabel_btn_style" onclick="(deleteMerchantMerchandise(\''+data.merchantAndMerchantdiseRel+'\',\''+1+'\'))">删除 </a>');//删除商品就是将关联状态改成1 （0:正常，1：失效）merchandiseImgUrl
                        var btn_showMerchantdise=$('<a class="tabel_btn_style" onclick="(showMerchandise(\''+data.name+'\',\''+data.merchandiseName+'\',\''+data.merchandiseBrandName+'\',\''+data.merchandiseModelName+'\',\''+data.merchandiseVersionName+'\',\''+data.merchandiseImgUrl+'\',\''+data.merchantdiseId+'\'))">查看 </a>');
                         $("td", row).eq(6).append(btn_del).append(btn_showMerchantdise);
                    },
                    "initComplete" : function(settings,json) {
                        //搜索
                        $("#btn_search").click(function() {
                            g_merchantdiseManage.fuzzySearch = true;
                            g_merchantdiseManage.tableUser.ajax.reload();
                        });
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_merchantdiseManage.tableUser.on("order.dt search.dt", function() {
                    g_merchantdiseManage.tableUser.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
            }

        }
    });
}

//删除商户下面的商品的方法
function deleteMerchantMerchandise(merchantAndMerchantdiseRel,state){
    var param={};
    param.merchantAndMerchantdiseRel=merchantAndMerchantdiseRel;
    param.state=state;
    Comm.ajaxPost('enterMerchantManage/deleteMerchantMerchandise', JSON.stringify(param), function (data) {
        layer.msg(data.msg, {time: 1000}, function () {
            // layer.closeAll();
            g_merchantdiseManage.fuzzySearch = true;
            g_merchantdiseManage.tableUser.ajax.reload();
        });
    }, "application/json");
}
//查看商户商品
function showMerchandise(a,b,c,d,e,f,g) {
    $("#a").html('');
    $("#b").html('');
    $("#c").html('');
    $("#d").html('');
    $("#e").html('');
    $('#f').html('');
    layer.open({
        type : 1,
        title : '查看商品',
        maxmin : true,
        shadeClose : false,
        area : [ '70%', '80%'  ],
        content : $('#goods_div'),
        success : function(index, layero) {
            $("#a").html(a);
            $("#b").html(b);
            $("#c").html(c);
            $("#d").html(d);
            $("#e").html(e);
            //根据商品id查询图片列表
            var param={};
            param.merchantdise_id=g;
            Comm.ajaxPost('enterMerchantManage/getMerchantdiseImgs', JSON.stringify(param), function (data) {
                var list=data.data;
                debugger
                for(var i=0;i<list.length;i++){
                    var img= $('<img class="addMaterial"  src='+list[i]+' />')
                    $('#f').append(img);
                }
            }, "application/json");
        }
    });

}

//激活或者冻结商户的方法
function jihuoOrDongjie(merchantId,state,checkState){
    if(checkState == '2'){
        var param={};
        param.merchantId=merchantId;
        param.state=state;
        // console.log(merchantId+":"+state);
        Comm.ajaxPost('merchantListController/activateOrFreeze', JSON.stringify(param), function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json");
    }else {
        layer.msg('该商户还未通过审核，无法激活', {time: 1000}, function () {
            layer.closeAll();
            g_userManage.fuzzySearch = true;
            g_userManage.tableUser.ajax.reload();
        });
    }

}

//账户管理按钮触发的函数展示商户的账户信息
function accountManager(merchantId){
    // alert('此按钮弹出窗口展示账户信息，分为对公账户和对私账户,对公账户只有一个，展示该信息，对私账户有两个分为：法人，收款委托人。此账户只能存在一个，不是法人就是收款委托人，不能同时存在，用户可以自主选择哪个账户作为对私账户。');
    //清除模板之前存在的内容
    //法人信息
    $('#add_person_name_manage').val('');//法人姓名
    $('#add_person_tel_manage').val('');//法人电话
    $('#add_persion_card_manage').val('');//法人身份证号
    $('#add_persion_bank_account_manage').val('');//法人银行卡号
    $('#faren_province_manage').val("");//省
    $('#faren_city_manage').val("");//市
    $('#add_persion_opening_bank_manage').val("");//银行全名

    //收款委托人
    $("#add_money_bailor_name_manage").val('')//收款委托人姓名
    $("#add_money_bailor_tel_manage").val('');//收款委托人电话
    $("#add_money_bailor_idcard_manage").val('');//收款委托人身份证号
    $("#add_money_bailor_account_manage").val('');//收款委托人银行卡号
    $('#weituo_province_manage').val("");//省
    $('#weituo_city_manage').val("");//市
    $('#add_money_bailor_oppen_bank_manage').val("");//银行全名
    //对公账号
    $('#add_public_account_name_manage').val('');//对公账号名称
    $('#add_public_bank_account_manage').val('');//对公账号
    $('#public_province_manage').val("");//省
    $('#public_city_manage').val("");//市
    $('#add_public_opening_bank_manage').val("");//银行全名
    //对私主账号
    $("#edit_private_account").val('');

    //账户银行卡银行名：如 建设银行
    $("#add_public_opening_bank_manage_head").val('');
    $("#add_money_bailor_oppen_bank_manage_head").val('');
    $("#add_persion_opening_bank_manage_head").val('');

    console.log(merchantId);
    layer.open({
        type : 1,
        title : '账户管理',
        maxmin : true,
        shadeClose : false,
        area : [ '80%', '80%'  ],
        content : $('#div_merchant_manage'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param={};
            param.merchantId=merchantId;
            //先查询该商户的所有信息并展示
            Comm.ajaxPost('merchantListController/getMerchant', JSON.stringify(param),
                function (data) {
                    var merchantBasic = data.data.merchantBasicInformation;//商户基本信息
                    var merchantAccounts = data.data.merchantAccounts;//商户账户信息 三个账户
                    //清空隐藏域内容
                    $("#faren_id_hidden").val('');
                    $("#weituo_id_hidden").val('');
                    $("#public_id_hidden").val('');
                    console.log(merchantBasic);
                    console.log(merchantAccounts);
                    //动态加载下拉选(账号选择下拉选)
                    $("#edit_private_account").html('<option value="">请选择</option>');
                    for (var i = 0; i < merchantAccounts.length; i++) {
                        if (merchantAccounts[i].type == '0') {
                            var opt_faren = $('<option class="tabel_btn_style" value=' + merchantAccounts[i].id + '> 法人账户 </option>');
                            $("#edit_private_account").append(opt_faren);//隐藏域放入法人账户id
                            $("#faren_id_hidden").val(merchantAccounts[i].id);
                        } else if (merchantAccounts[i].type == '1') {
                            var opt_weituo = $('<option class="tabel_btn_style" value=' + merchantAccounts[i].id + '> 委托收款人 </option>');
                            $("#edit_private_account").append(opt_weituo);
                            $("#weituo_id_hidden").val(merchantAccounts[i].id);//隐藏域放入委托人账户id
                        }else if(merchantAccounts[i].type == '2'){
                            $("#public_id_hidden").val(merchantAccounts[i].id);//隐藏域放入公共账号id
                        }
                    }
                    //动态选择下拉选(动态选择对私主账户是哪个)
                    $("#edit_private_account").val(merchantBasic.merPrivateAccountId);
                    //账户信息
                    for (var i = 0; i < merchantAccounts.length; i++) {
                        //0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
                        if (merchantAccounts[i].type == '0') {
                            //法人信息
                            $('#add_person_name_manage').val(merchantAccounts[i].name);//法人姓名
                            $('#add_person_tel_manage').val(merchantAccounts[i].tel);//法人电话
                            $('#add_persion_card_manage').val(merchantAccounts[i].idcard);//法人身份证号
                            $('#add_persion_bank_account_manage').val(merchantAccounts[i].account);//法人银行卡号
                            $("#faren_province_manage").val(merchantAccounts[i].provinceId);//法人银行卡开户省份id
                            citySelAdd(merchantAccounts[i].provinceId, $("#faren_city_manage"));
                            $("#faren_city_manage").val(merchantAccounts[i].cityId); //法人银行卡开户城市id
                            $('#add_persion_opening_bank_manage').val(merchantAccounts[i].bankName);//法人银行卡开户行
                            $("#add_persion_opening_bank_manage_head").val(merchantAccounts[i].bankHeadId);//开户行总行id
                        } else if (merchantAccounts[i].type == '1') {
                            //收款委托人
                            $("#add_money_bailor_name_manage").val(merchantAccounts[i].name)//收款委托人姓名
                            $("#add_money_bailor_tel_manage").val(merchantAccounts[i].tel);//收款委托人电话
                            $("#add_money_bailor_idcard_manage").val(merchantAccounts[i].idcard);//收款委托人身份证号
                            $("#add_money_bailor_account_manage").val(merchantAccounts[i].account);//收款委托人银行卡号
                            $("#weituo_province_manage").val(merchantAccounts[i].provinceId);//省
                            citySelAdd(merchantAccounts[i].provinceId, $("#weituo_city_manage"));
                            $("#weituo_city_manage").val(merchantAccounts[i].cityId);//市
                            $("#add_money_bailor_oppen_bank_manage").val(merchantAccounts[i].bankName);//收款委托人开户行
                            $("#add_money_bailor_oppen_bank_manage_head").val(merchantAccounts[i].bankHeadId);
                        } else if (merchantAccounts[i].type == '2') {
                            //对公账号
                            $('#add_public_account_name_manage').val(merchantAccounts[i].name);//对公账号名称
                            $('#add_public_bank_account_manage').val(merchantAccounts[i].account);//对公账号
                            $("#public_province_manage").val(merchantAccounts[i].provinceId);
                            citySelAdd(merchantAccounts[i].provinceId, $("#public_city_manage"));
                            $("#public_city_manage").val(merchantAccounts[i].cityId);
                            $('#add_public_opening_bank_manage').val(merchantAccounts[i].bankName);//对公开户行
                            $("#add_public_opening_bank_manage_head").val(merchantAccounts[i].bankHeadId);
                        }
                    }
                    // $("#div_merchant_manage").find("textarea").attr("disabled",false);
                    // //设置账户信息只能读 无法编辑
                    // $("#faren_table").find("input,select").attr("disabled",true);
                    // $("#faren_table").css("background-color","#F4F4F4");
                    //
                    // $("#add_money_bailor_table").find("input,select").attr("disabled",true);
                    // $("#add_money_bailor_table").css("background-color","#F4F4F4");
                    //
                    // $("#add_public_opening_table").find("input,select").attr("disabled",true);
                    // $("#add_public_opening_table").css("background-color","#F4F4F4");
                }, "application/json"
            );
        },
        yes:function(index,layero){
            console.log(merchantId);
            //文字信息
            var param={};
            param.merchantId=merchantId; //商户id
            console.log(param);
            //法人信息 账户信息在编辑页面不允许编辑
            param.personId=$("#faren_id_hidden").val();
            param.person_name=$.trim($('#add_person_name_manage').val());//法人姓名
            param.person_tel=$.trim($('#add_person_tel_manage').val());//法人电话
            param.person_idcard=$.trim($('#add_persion_card_manage').val());//法人身份证号
            param.persion_bank_account=$.trim($('#add_persion_bank_account_manage').val());//法人银行卡号
            param.persion_account_province=$("#faren_province_manage").val();//法人银行卡开户省份id
            param.persion_account_city=$("#faren_city_manage").val();//法人银行卡开户城市id
            param.persion_account_province_name=$.trim($("#faren_province_manage").find("option:selected").text());//省名
            param.persion_account_city_name=$.trim($("#faren_city_manage").find("option:selected").text());//城市名
            param.persion_opening_bank=$.trim($('#add_persion_opening_bank_manage').val());//法人银行卡开户行全名

            //收款委托人
            param.weituoId=$("#weituo_id_hidden").val();
            param.money_bailor_name=$.trim($("#add_money_bailor_name_manage").val())//收款委托人姓名
            param.money_bailor_tel=$.trim($("#add_money_bailor_tel_manage").val());//收款委托人电话
            param.money_bailor_idcard=$.trim($("#add_money_bailor_idcard_manage").val());//收款委托人身份证号
            param.money_bailor_account=$.trim($("#add_money_bailor_account_manage").val());//收款委托人银行卡号
            param.bailor_account_province=$("#weituo_province_manage").val();//收款委托人银行卡省份id
            param.bailor_account_province_name=$.trim($("#weituo_province_manage").find("option:selected").text());//省名
            param.bailor_account_city=$("#weituo_city_manage").val();//收款委托人银行卡城市
            param.bailor_account_city_name=$.trim($("#weituo_city_manage").find("option:selected").text());//城市名
            param.money_bailor_oppen_bank=$.trim($("#add_money_bailor_oppen_bank_manage").val());//收款委托人开户行全名

            //对公账号
            param.publicId=$("#public_id_hidden").val();
            param.public_account_name=$.trim($('#add_public_account_name_manage').val());//对公账号名称
            param.public_bank_account=$.trim($('#add_public_bank_account_manage').val());//对公账号
            param.public_bank_province_id=$.trim($("#public_province_manage").val());
            param.public_bank_city_id=$.trim($("#public_city_manage").val());
            param.public_bank_province_name=$.trim($("#public_province_manage").find("option:selected").text())
            param.public_bank_city_name=$.trim($("#public_city_manage").find("option:selected").text())
            param.public_opening_bank=$.trim($('#add_public_opening_bank_manage').val());//对公开户行全名

            //开户行总行名称 如：建设银行
            param.add_persion_opening_bank_manage_head=$("#add_persion_opening_bank_manage_head").find("option:selected").text();//开户行总行名
            param.add_persion_opening_bank_manage_head_id=$("#add_persion_opening_bank_manage_head").val();//开户行总行名id

            param.add_money_bailor_oppen_bank_manage_head=$("#add_money_bailor_oppen_bank_manage_head").find("option:selected").text();//开户行总行名
            param.add_money_bailor_oppen_bank_manage_head_id=$("#add_money_bailor_oppen_bank_manage_head").val();

            param.add_public_opening_bank_manage_head=$("#add_public_opening_bank_manage_head").find("option:selected").text();//开户行总行名
            param.add_public_opening_bank_manage_head_id=$("#add_public_opening_bank_manage_head").val();

            //对私主账号id
            param.merPrivateAccountId= $("#edit_private_account").val();

            //法人信息
            if(param.person_name==''){
                layer.msg("法人姓名不能为空",{time:1000});
                return false;
            }if(param.person_tel==''){
                layer.msg("法人电话不能为空",{time:1000});
                return false;
            }if(!(/^1[3|4|5|8][0-9]\d{4,8}$/).test(param.person_tel)){
                layer.msg("法人电话号码为空或电话号码格式不正确",{time:1000});return false
            }if(param.person_idcard==''){
                layer.msg("法人身份证号不能为空",{time:1000});
                return  false;
            }if(!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(param.person_idcard))){
                layer.msg("法人身份证号码未输入或者格式不正确",{time:1000});return false
            }
            if(param.persion_bank_account==''){
                layer.msg("法人银行卡号不能为空",{time:1000});
                return false;
            }
            if(isNaN(param.persion_bank_account)){
                layer.msg("法人银行卡号只能输入数字",{time:1000});
                return false;
            }
            if(param.persion_account_province==''){
                layer.msg("法人银行卡开户省份不能为空",{time:1000});
                return false;
            }if(param.persion_account_city==''){
                layer.msg("法人银行卡开户城市不能为空",{time:1000});
                return false;
            }if(param.persion_opening_bank==''){
                layer.msg("法人银行卡开户行名称不能为空",{time:1000});
                return false;
            }if(param.add_persion_opening_bank_manage_head_id==''){
                layer.msg("法人银行名称不能为空",{time:1000});
                return false;
            }
            //收款委托人
            if(param.money_bailor_name ==''){
                layer.msg("收款委托人姓名不能为空",{time:1000});
                return false;
            }if(param.money_bailor_tel ==''){
                layer.msg("收款委托人电话不能为空",{time:1000});
                return false;
            }
            if(!(/^1[3|4|5|8][0-9]\d{4,8}$/).test(param.money_bailor_tel)){
                layer.msg("收款委托人电话号码为空或电话号码格式不正确",{time:1000});return false
            }
            if(param.money_bailor_idcard ==''){
                layer.msg("收款委托人身份证号不能为空",{time:1000});
                return false;
            }if(!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(param.money_bailor_idcard))){
                layer.msg("收款委托人身份证号码未输入或者格式不正确",{time:1000});return false
            }if(param.money_bailor_account ==''){
                layer.msg("收款委托人银行卡号不能为空",{time:1000});
                return false;
            }
            if(isNaN(param.money_bailor_account)){
                layer.msg("收款委托人银行卡号只能输入数字",{time:1000});
                return false;
            }
            if(param.bailor_account_province ==''){
                layer.msg("收款委托人银行卡省不能为空",{time:1000});
                return false;
            }if(param.bailor_account_city_name ==''){
                layer.msg("收款委托人银行卡城市不能为空",{time:1000});
                return false;
            }if(param.money_bailor_oppen_bank ==''){
                layer.msg("收款委托人开户行名称不能为空",{time:1000});
                return false;
            }
            if(param.add_money_bailor_oppen_bank_manage_head_id==''){
                layer.msg("收款委托人委托人银行名称不能为空",{time:1000});
                return false;
            }
            //对公账号
            if(param.public_account_name ==''){
                layer.msg("对公账号名称不能为空",{time:1000});
                return false;
            }
            if(param.public_bank_account ==''){
                layer.msg("对公账号不能为空",{time:1000});
                return false;
            }
            if(isNaN(param.public_bank_account)){
                layer.msg("对公账号只能输入数字",{time:1000});
                return false;
            }
            if(param.public_bank_province_id ==''){
                layer.msg("开户行省份不能为空",{time:1000});
                return false;
            }
            if(param.public_bank_city_id ==''){
                layer.msg("开户行城市不能为空",{time:1000});
                return false;
            }
            if(param.public_opening_bank ==''){
                layer.msg("对公开户行名称不能为空",{time:1000});
                return false;
            }
            //银行名bank_head 如：建设银行，浦发银行
            //法人
            if(param.add_persion_opening_bank_head_id == ''){
                layer.msg("法人银行名不能为空",{time:1000});
                return false;
            }if(param.add_money_bailor_oppen_bank_head_id == ''){
                layer.msg("委托收款人银行名不能为空",{time:1000});
                return false;
            }if(param.add_public_opening_bank_head_id == ''){
                layer.msg("对公账号银行名不能为空",{time:1000});
                return false;
            }if(param.merPrivateAccountId == ''){
                layer.msg("对私主账号不能为空",{time:1000});
                return false;
            }
            if(param.add_persion_opening_bank_manage_head_id == '' ){
                layer.msg("开户行总行不能为空",{time:1000});
                return false;
            }
            if(param.add_money_bailor_oppen_bank_manage_head_id == '' ){
                layer.msg("开户行总行不能为空",{time:1000});
                return false;
            }
            if(param.add_public_opening_bank_manage_head_id == '' ){
                layer.msg("对公账号银行名称",{time:1000});
                return false;
            }
            // param.add_persion_opening_bank_manage_head_id=$("#add_persion_opening_bank_manage_head").val();//开户行总行名id
            //
            //
            // param.add_money_bailor_oppen_bank_manage_head_id=$("#add_money_bailor_oppen_bank_manage_head").val();
            //
            //
            // param.add_public_opening_bank_manage_head_id=$("#add_public_opening_bank_manage_head").val();




            //请求编辑的controller层
            Comm.ajaxPost(
                'merchantListController/editMerchantAccount', JSON.stringify(param),
                function (data) {
                    layer.msg(data.msg, {time: 1000}, function () {
                        layer.closeAll();
                        g_userManage.fuzzySearch = true;
                        g_userManage.tableUser.ajax.reload();
                    });
                }, "application/json"
            );
        }

    });
}

//绑定办单员按钮
function bidingSalesmans(merchantId,state){
    debugger
    if(state == '0'){
        layer.msg('该商户还未激活，无法绑定办单员', {time: 1000});
        return false;
    }
    $("#merchant_id_biding_salesman").val(merchantId);
    console.log(merchantId);
    layer.open({
        type : 1,
        title : '绑定办单员',
        maxmin : true,
        shadeClose : false,
        area : [ '50%', '50%'  ],
        content : $('#bidingSalesman'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            //清空搜索框
            $("#salesman_tel").val('');
            $("#salesman_name").val('');
            var initFlag=$("#initFlag").val();
            //先查询所有办单员
            if(!initFlag){
                g_salesmanManage.tableUser = $('#salesman_list').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_salesmanManage.getQueryCondition(data);
                        Comm.ajaxPost('merchantListController/showCanMerSalesman', JSON.stringify(queryFilter), function (result) {
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
                        {
                            "className" : "cell-operation",
                            "data": null,
                            "defaultContent":"",
                            "orderable" : false,
                            "width" : "30px"
                        },
                        {"data": "realname","orderable" : false},//姓名
                        {"data": "tel","orderable" : false}//手机号
                        // {"data": "null", "orderable": false, "defaultContent":""}//操作
                    ],"createdRow": function ( row, data, index,settings,json ) {
                        var btn_show=$('<input name="xxxxxxxxxx" type="checkbox" value= '+data.id+' />');//查看
                        $("td", row).eq(0).append(btn_show);
                    },
                    "initComplete" : function(settings,json) {
                        //搜索
                        $("#search_salesmanName").click(function() {
                            g_salesmanManage.fuzzySearch = true;
                            g_salesmanManage.tableUser.ajax.reload();
                        });
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_salesmanManage.tableUser.on("order.dt search.dt", function() {
                    // g_salesmanManage.tableUser.column(0, {
                    //     search : "applied",
                    //     order : "applied"
                    // }).nodes().each(function(cell, i) {
                    //     cell.innerHTML = i + 1
                    // })
                }).draw();
                $("#initFlag").val(true);
            }else{
                g_salesmanManage.tableUser.ajax.reload();
            }

        },
        yes: function (index,layero) {
            //存被选中的办单员的id的数组
            var salesmanIds=new Array();
            var groupCheckbox=$("input[name='xxxxxxxxxx']:checked");
            for(i=0;i<groupCheckbox.length;i++){
                salesmanIds[i]= groupCheckbox[i].value;
            }
            var param={};
            param.merchantId=merchantId;
            param.salesmanIds=salesmanIds;
            if(salesmanIds.length<1){
                layer.msg("请选择内容", {time: 1000}, function () {
                    // layer.closeAll();
                    // g_userManage.fuzzySearch = true;
                    // g_userManage.tableUser.ajax.reload();
                });
            }else{
                Comm.ajaxPost('merchantListController/bidingSalesman', JSON.stringify(param), function (data) {
                    layer.msg(data.msg, {time: 1000}, function () {
                        //layer.closeAll();
                        g_salesmanManage.fuzzySearch = true;
                        g_salesmanManage.tableUser.ajax.reload();
                    });
                }, "application/json");
            }
        }
    });
}


//查看该商户的办单员
function showSalesman(merchantId,state){
    if(state == '0'){
        layer.msg('该商户还未激活', {time: 1000});
        return false;
    }
    $("#merchant_id_biding_salesman_show").val(merchantId);
    layer.open({
        type : 1,
        title : '办单员信息',
        maxmin : true,
        shadeClose : false,
        area : [ '50%', '50%'  ],
        content : $('#showMerchantSalesmanList'),
        success : function(index, layero) {
            //先清空搜索框
            $("#salesman_name_show").val("");
            $("#salesman_tel_show").val("");
            //先查询该商户绑定的办单员
            var initFlag1=$("#initFlag1").val();
            if(!initFlag1){
                g_salesmanManageShow.tableUser = $('#salesman_list_show').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_salesmanManageShow.getQueryCondition(data);
                        Comm.ajaxPost('merchantListController/showSalesmanFromMerchant', JSON.stringify(queryFilter), function (result) {
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
                        {
                            "className" : "cell-operation",
                            "data": null,
                            "defaultContent":"",
                            "orderable" : false,
                            "width" : "30px"
                        },
                        {"data": "realname","orderable" : false},//姓名
                        {"data": "tel","orderable" : false},//手机号
                        {"data": "relState","orderable" : false,"render":function (data, type, row, meta) {
                            if(data=='1'){
                                return "已解绑";
                            }
                            if(data=='0'){
                                return "已绑定"
                            }
                        }},//手机号
                        {"data": "null", "orderable": false, "defaultContent":""}//操作
                    ],"createdRow": function ( row, data, index,settings,json ) {
                        var btn_cancleBiding;
                        if(data.relState=='0'){
                            btn_cancleBiding=$('<a class="tabel_btn_style" onclick="(bidingOrCancelSalesman(\''+data.relId+'\',\''+1+'\'))"> 解除绑定 </a>');//解除绑定就是将状态改成1
                        }
                        if(data.relState=='1'){
                            btn_cancleBiding=$('<a class="tabel_btn_style" onclick="(bidingOrCancelSalesman(\''+data.relId+'\',\''+0+'\'))"> 再次绑定 </a>');//再次绑定就是将状态改成0
                        }
                        $("td", row).eq(4).append(btn_cancleBiding);
                    },
                    "initComplete" : function(settings,json) {
                        // 搜索
                        $("#search_salesmanName_show").click(function() {
                            g_salesmanManageShow.fuzzySearch = true;
                            g_salesmanManageShow.tableUser.ajax.reload();
                        });
                        // //添加按钮弹出窗
                        // $("#btn_add_merchant").on("click",function(){
                        //     //只要一点击就立即清除输入框以及隐藏域的内容
                        //     clearModel();
                        //     //设置可编辑
                        //     $("#div_add_merchant").find("input").attr("disabled",false);
                        //     $("#div_add_merchant").find("select").attr("disabled",false);
                        //     $("#div_add_merchant").find("textarea").attr("disabled",false);
                        //
                        //     layer.open({
                        //         type : 1,
                        //         title : '添加商户信息',
                        //         maxmin : true,
                        //         shadeClose : false,
                        //         area : [ '100%', '100%'  ],
                        //         content : $('#div_add_merchant'),
                        //         // btn : [ '保存', '取消' ],
                        //         success : function(index, layero) {
                        //             $('#queding').unbind(); //移除之前绑定的函数，重新绑定按钮函数
                        //             //确定按钮绑定函数
                        //             $("#queding").click(function () {
                        //                 addMerchant();
                        //             });
                        //             $("#quxiao").unbind();
                        //             $("#quxiao").click(function () {
                        //                 layer.closeAll();
                        //                 g_userManage.fuzzySearch = true;
                        //                 g_userManage.tableUser.ajax.reload();
                        //             });
                        //         }
                        //     });
                        // })
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_salesmanManageShow.tableUser.on("order.dt search.dt", function() {
                    g_salesmanManageShow.tableUser.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
                $("#initFlag1").val(true);
            }else{
                g_salesmanManageShow.tableUser.ajax.reload();
            }



        },
    });
}
//办单员再次绑定或者解除绑定
function bidingOrCancelSalesman(relId,relState){//商户办单员关联表id ，要更改的状态
    var param={};
    param.relId=relId;
    param.relState=relState;
    Comm.ajaxPost('merchantListController/bidingOrCancelSalesman', JSON.stringify(param), function (data) {
        layer.msg(data.msg, {time: 1000}, function () {
            //layer.closeAll();
            g_salesmanManageShow.fuzzySearch = true;
            g_salesmanManageShow.tableUser.ajax.reload();
        });
    }, "application/json");
}

//修改商户分级
function editMerGrade(merchantId,merGradeId,state){
    // console.log(merchantId+':'+merGradeId);
    if(state != '1'){
        layer.msg('该商户还未激活，无法修改商户分级', {time: 1000});
        return false;
    }
    layer.open({
        type : 1,
        title : '修改商户分级',
        maxmin : true,
        shadeClose : false,
        area : [ '30%', '30%'  ],
        content : $('#changeMerchantGrade'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var queryFilter = g_userManage.getQueryCondition({});
            Comm.ajaxPost('merchantListController/showAllMerchantGrade', JSON.stringify(queryFilter), function (data) {
                //清空下拉选节点
                $("#merchant_grade").html('<option value="" style="font-size: 10px;">请选择商户级别</option>');
                var list=data.data;
                for(var i=0;i<list.length;i++){
                    var opt = $('<option class="tabel_btn_style" value=' + list[i].id + '> '+list[i].grade+' </option>');
                    $("#merchant_grade").append(opt);
                }
                $("#merchant_grade").val(merGradeId);
            }, "application/json");
        },
        yes: function (index,layero) {
            var param={};
            param.merchantId=merchantId;
            param.merGrade=$('#merchant_grade').val();
            if(param.merGrade =='' || param.merGrade==null){
                layer.msg("商户级别不能为空",{time:1000});
                return;
            }
            Comm.ajaxPost('merchantListController/changeMerGrade', JSON.stringify(param), function (data) {
                layer.msg(data.msg, {time: 1000}, function () {
                    layer.closeAll();
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableUser.ajax.reload();
                });
            }, "application/json");
        }
    });

}

//查询重置
$(function(){
    $("#btn_reset").click(function(){
        $("#search_merchantName").val("");
        $("#search_apply_name").val("");
        g_userManage.tableUser.ajax.reload();
    })
    $("#btn_reset_order").click(function(){
        $("#search_customName").val("");
        $("#search_custom_tel").val("");
        g_orderManageShow.tableUser.ajax.reload();
    })
    //绑定办单员查询重置
    $("#search_reset_biding_sal").click(function(){
        $("#salesman_tel").val('');
        $("#salesman_name").val('');
        g_salesmanManage.tableUser.ajax.reload();
    });
    //展示办单员查询重置
    $("#search_sal_show_reset").click(function(){
        $("#salesman_tel_show").val('');
        $("#salesman_name_show").val('');
        g_salesmanManageShow.tableUser.ajax.reload();
    });
})