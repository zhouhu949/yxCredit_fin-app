/**
 * Created by Administrator on 2018/1/9.商户反欺诈
 */
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
            Comm.ajaxPost('merchantAntiFraud/allAntiFraudMerchantsList', JSON.stringify(queryFilter), function (result) {
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
            {"data": "checkState","orderable" : false,"render":function (data, type, row, meta) {
                if(data=='1'){
                    return '审核中'
                }if(data=='2'){
                    return '审核通过'
                }if(data=='3'){
                    return '审核拒绝'
                }
            }},//审核状态
            {"data": "fanQiZhaManName", "orderable": false},//反欺诈专员
            {"data": "fanQIZhaState", "orderable": false,"render":function (data, type, row, meta) {
                if(data=='1'){
                    return '反欺进行中'
                }if(data=='2'){
                    return '反欺诈结束'}
            }},//反欺诈状态
            {"data": "null", "orderable": false, "defaultContent":""}//操作
        ],"createdRow": function ( row, data, index,settings,json ) {
            if(data.fanQIZhaState == '1'){
                var btn=$('<a class="tabel_btn_style" onclick="(dealAntiFraudMerchant(\''+data.id+'\'))">处理 </a>');//处理
                $("td",row).eq(9).append(btn);
            }else{
                $("td",row).eq(9).append('已处理');
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
//处理反欺诈商户
function dealAntiFraudMerchant(merchantId){
    layer.open({
        type : 1,
        title : '处理反欺诈任务',
        maxmin : true,
        shadeClose : false,
        area : [ '80%', '80%'  ],
        content : $('#delAntiFraudMerchant'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            //反欺诈备注
            $("#fanQiZhaBeiiZhu").val('');
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
            // //收款委托书
            // clearImgAndHidden("td_shoukuanweituoshu");
        },
        yes: function (index,layero) {
            var param={};
            param.merchantId=merchantId;
            //图片信息
            //法人身份证正面 OSS地址 单张图片
            param.idcardZhengmian=getImgUrlsListByTdId("td_idcard_zhengmian");
            //法人身份证反面 OSS地址 单张图片
            param.idcardFanmian=getImgUrlsListByTdId("td_idcard_fanmian");
            //营业执照
            param.yinyezhizhao=getImgUrlsListByTdId("td_yinyezhizhao");
            //门头logo照
            param.touxiangLogo=getImgUrlsListByTdId("td_touxiang_logo");
            //室内照
            param.shinei=getImgUrlsListByTdId("td_shinei");
            //收银台
            param.shouyintai=getImgUrlsListByTdId("td_shouyintai");
            //街景
            param.jiejing=getImgUrlsListByTdId("td_jiejing");
            //法人银行卡
            param.farenyinhangka=getImgUrlsListByTdId("td_farenyinhangka");
            // //收款委托书
            // param.shoukuanweituoshu=getImgUrlsListByTdId("td_shoukuanweituoshu");
            //反欺诈备注
            param.fanQiZhaBeiiZhu=$("#fanQiZhaBeiiZhu").val();
            if(!chenckImgs(param)){
                return ;
            }
            Comm.ajaxPost('merchantAntiFraud/dealAntiFraudMerchant', JSON.stringify(param), function (data) {
                    layer.msg(data.msg, {time: 1000}, function () {
                        layer.closeAll();
                        g_userManage.fuzzySearch = true;
                        g_userManage.tableUser.ajax.reload();
                    });
                    // var merchantBasic=data.data.merchantBasicInformation;//商户基本信息
                    // var merchantAccounts=data.data.merchantAccounts;//商户账户信息 三个账户
                    // var merchantImgs=data.data.merchantImgs;//商户关联图片
                    // // console.log(merchantBasic);
                    // // console.log(merchantAccounts);
                    // console.log(merchantImgs);
                    // //商户基本信息merchantBasic
                    // $('#add_mer_name').val(merchantBasic.merName);//公司名称
                    // $('#add_license_number').val(merchantBasic.licenseNumber);//营业执照注册号
                    // $('#add_main_business').val(merchantBasic.mainBusiness);//主营业务
                    // $('#add_type').val(merchantBasic.type);//企业类型
                    // $('#add_license_date').val(merchantBasic.licenseDate);//营业执照日期
                    // $('#add_registered_capital').val(merchantBasic.registeredCapital);//注册资本
                    // $('#add_employees_number').val(merchantBasic.employeesNumber);//员工人数
                    // //门店地址
                    // $("#province").val(merchantBasic.provincesId);//省
                    // citySelAdd(merchantBasic.provincesId,$("#city"));
                    // $("#city").val(merchantBasic.cityId);//市
                    // areaSelAdd(merchantBasic.cityId,$("#area"));
                    // $("#area").val(merchantBasic.districId);//区
                    // $('#add_merchant_tel').val(merchantBasic.merTel);//门店电话
                    // $('#add_merchant_detail_address').val(merchantBasic.merDetailAddress);//详细地址
                    // //申请人信息
                    // $('#add_apply_name').val(merchantBasic.applyName);//申请人姓名
                    // $('#add_apply_tel').val(merchantBasic.applyTel);//申请人电话
                    // $('#add_apply_idcard').val(merchantBasic.applyCard);//申请人身份证号码
                    // //备注说明
                    // $("#remarksContent").val(merchantBasic.merDes);
                    // //账户信息
                    // for(var i=0;i<merchantAccounts.length;i++){
                    //     //0-法人账号   1-委托收款人账号   2-对公账号  (0,1为私人账号)
                    //     if(merchantAccounts[i].type == '0'){
                    //         //法人信息
                    //         $('#add_person_name').val(merchantAccounts[i].name);//法人姓名
                    //         $('#add_person_tel').val(merchantAccounts[i].tel);//法人电话
                    //         $('#add_persion_card').val(merchantAccounts[i].idcard);//法人身份证号
                    //         $('#add_persion_bank_account').val(merchantAccounts[i].account);//法人银行卡号
                    //         $("#faren_province").val(merchantAccounts[i].provinceId);//法人银行卡开户省份id
                    //         citySelAdd(merchantAccounts[i].provinceId,$("#faren_city"));
                    //         $("#faren_city").val(merchantAccounts[i].cityId); //法人银行卡开户城市id
                    //         $('#add_persion_opening_bank').val(merchantAccounts[i].bankName);//法人银行卡开户行
                    //         $("#add_persion_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                    //     }else if(merchantAccounts[i].type == '1'){
                    //         //收款委托人
                    //         $("#add_money_bailor_name").val(merchantAccounts[i].name)//收款委托人姓名
                    //         $("#add_money_bailor_tel").val(merchantAccounts[i].tel);//收款委托人电话
                    //         $("#add_money_bailor_idcard").val(merchantAccounts[i].idcard);//收款委托人身份证号
                    //         $("#add_money_bailor_account").val(merchantAccounts[i].account);//收款委托人银行卡号
                    //         $("#weituo_province").val(merchantAccounts[i].provinceId);//省
                    //         citySelAdd(merchantAccounts[i].provinceId,$("#weituo_city"));
                    //         $("#weituo_city").val(merchantAccounts[i].cityId);//市
                    //         $("#add_money_bailor_oppen_bank").val(merchantAccounts[i].bankName);//收款委托人开户行
                    //         $("#add_money_bailor_oppen_bank_head").val(merchantAccounts[i].bankHeadId);
                    //     }else if(merchantAccounts[i].type == '2'){
                    //         //对公账号
                    //         $('#add_public_account_name').val(merchantAccounts[i].name);//对公账号名称
                    //         $('#add_public_bank_account').val(merchantAccounts[i].account);//对公账号
                    //         $("#public_province").val(merchantAccounts[i].provinceId);
                    //         citySelAdd(merchantAccounts[i].provinceId,$("#public_city"));
                    //         $("#public_city").val(merchantAccounts[i].cityId);
                    //         $('#add_public_opening_bank').val(merchantAccounts[i].bankName);//对公开户行
                    //         $("#add_public_opening_bank_head").val(merchantAccounts[i].bankHeadId);
                    //     }
                    // }
                    // //图片信息 0-身份证正面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像
                    // for(var i=0;i<merchantImgs.length;i++){
                    //     if(merchantImgs[i].imgType =='0'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_idcard_zhengmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_idcard_zhengmian",merchantImgs[i].imgUrl,1);//1代表着查看 2代表着编辑
                    //         $("#"+'td_idcard_zhengmian').find("[type='file']").attr("disabled",true);
                    //     }else if(merchantImgs[i].imgType =='1'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_idcard_fanmian').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_idcard_fanmian",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='2'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_yinyezhizhao').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_yinyezhizhao",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='3'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_touxiang_logo').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_touxiang_logo",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='4'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_shinei').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_shinei",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='5'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_shouyintai').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_shouyintai",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='6'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_jiejing').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_jiejing",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='7'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_farenyinhangka').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_farenyinhangka",merchantImgs[i].imgUrl,1);
                    //     }else if(merchantImgs[i].imgType =='8'){
                    //         //先删除之前存在的添加样式的图片
                    //         $("#"+'td_shoukuanweituoshu').find("[src='../resources/images/photoadd.png']").parent().parent().parent().remove();
                    //         //再添加该图片div
                    //         appendImgDivToTd("td_shoukuanweituoshu",merchantImgs[i].imgUrl,1);
                    //     }
                    // }
                    // $("#add_persion_opening_bank_head").val();
                }, "application/json"
            );
        }

    });
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
//根据td的id获取下面所有隐藏域的地址
function getImgUrlsListByTdId(tdId){
    var urlList=new Array();
    //改td下面所有div
    var listDiv= $("#"+tdId).children();
    for (i=0;i<listDiv.length;i++){
        var url=listDiv.eq(i).children().eq(1).val();
        urlList[i]=url;
    }
    return urlList;
}
//做图片的js验证
function chenckImgs(param){
    //图片信息
        if(param.idcardZhengmian.length<2){
            layer.msg("法人身份证正面不能为空",{time:1000});
            return false;
        }
       else if(param.idcardFanmian .length<2){
            layer.msg("法人身份证反面不能为空",{time:1000});
            return false;
        }
        else if(param.yinyezhizhao.length <2){
            layer.msg("营业执照照片不能为空",{time:1000});
            return false;
        }
        else if( param.touxiangLogo.length <2){
            layer.msg("头像logo不能为空",{time:1000});
            return false;
        }
        else if(param.shinei.length <2){
            layer.msg("室内照不能为空",{time:1000});
            return false;
        }
        else if(param.shouyintai.length <2){
            layer.msg("收银台照不能为空",{time:1000});
            return false;
        }
        else if(param.jiejing.length <2){
            layer.msg("街景照不能为空",{time:1000});
            return false;
        }
        else if(param.farenyinhangka.length <2){
            layer.msg("法人银行卡不能为空",{time:1000});
            return false;
        }else if(param.fanQiZhaBeiiZhu =='' ){
            layer.msg("备注不能为空",{time:1000});
            return false;
        }
        else {
            return true;
        }
}
$(function(){
    $("#btn_reset").click(function () {
        $("#search_merchantName").val('');
        $("#search_apply_name").val('');
        g_userManage.tableUser.ajax.reload();
    });
})