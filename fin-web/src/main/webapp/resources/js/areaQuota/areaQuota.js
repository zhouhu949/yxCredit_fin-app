/**
 * Created by Administrator on 2017/12/5.
 */
//获取全部区域限额设置信息
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
            param.province = $("#search_province").val();
            param.city = $("#search_city").val();
            param.state=$("#state").val();
        // console.log(param);
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//显示区域限额设置列表
$(function (){
    //动态加载省份下拉选内容
    provinceSelAdd($("#search_province"));
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
            Comm.ajaxPost('areaQuota/showAreaQuotaList', JSON.stringify(queryFilter), function (result) {
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
            {"data": "provinceName","orderable" : false},
            {"data": "cityName","orderable" : false},
            {"data": "state","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data=="1"){
                        return "已启用";
                    }else if(data=="0"){
                        return "已停用";
                    }else {
                        return "";
                    }
                }
            },
            {"data": "numberDay","orderable" : false},
            {"data": "numberWeek","orderable" : false},
            {"data": "numberMonth","orderable" : false},
            {"data": "quotaDay","orderable" : false},
            {"data": "quotaWeek","orderable" : false},
            {"data": "quotaMonth","orderable" : false},
            {"data": "singleQuota","orderable" : false},
            {"data": "null", "orderable": false, "defaultContent":""},
            {"data": "null", "orderable": false, "defaultContent":""}
        ],"createdRow": function ( row, data, index,settings,json ) {
            var btnStartOrStop="";
            if(data.state=="1"){
                btnStartOrStop=$('<a class="tabel_btn_style" onclick="(startOrStop(\''+data.id+'\',\''+0+'\'))">停用</a>');//停用就是把状态改成0
            }else{
                btnStartOrStop=$('<a class="tabel_btn_style" onclick="(startOrStop(\''+data.id+'\',\''+1+'\'))">启用</a>');//启用就是把状态改成1
            };
            var btnChange=$('<a class="tabel_btn_style" onclick="(changeAreaQuota(\''+data.id+'\'))">修改</a>');
            $("td", row).eq(11).append(btnStartOrStop);
            $("td", row).eq(12).append(btnChange);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //添加区域限额
            $("#btn_add_quota").on("click",function(){
                provinceSelAdd($("#add_province"));
                layer.open({
                    type : 1,
                    title : '添加区域限额',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '450px', '450px' ],
                    content : $('#addGrade').show(),
                    btn : [ '保存', '取消' ],
                    success : function(index, layero) {
                        $('#add_province').val('');
                        $("#add_city").val('');
                        $("#add_state").val('');
                        $("#number_day").val('');
                        $("#number_week").val('');
                        $("#number_month").val('');
                        $("#quota_day").val('');
                        $("#quota_week").val("");
                        $("#quota_month").val('');
                        $("#single_quota").val('');
                    },
                    yes:function(index,layero){
                        var param={};
                        //传入后台参数
                        param.provinceId=$("#add_province").val();
                        param.provinceName=$("#add_province").find("option:selected").text();
                        param.cityId=$("#add_city").val();
                        param.cityName=$("#add_city").find("option:selected").text();
                        param.state=$.trim($("#add_state").val());
                        param.numberDay=$.trim($("#number_day").val());
                        param.numberWeek=$.trim($("#number_week").val());
                        param.numberMonth=$.trim($("#number_month").val());
                        param.quotaDay=$.trim($("#quota_day").val());
                        param.quotaWeek=$.trim($("#quota_week").val());
                        param.quotaMonth=$.trim($("#quota_month").val());
                        param.singleQuota=$.trim($("#single_quota").val());
                        //console.log(param);
                        //在此处做字符串验证
                        var reg = /^[1-9]\d*$/;
                        if( param.provinceId==''){
                            layer.msg("省份为必选项",{time:1000});return
                        }
                        if(param.cityId==''){
                            layer.msg("城市为必选项",{time:1000});return
                        }
                        if(param.state==""){
                            layer.msg("是否启用为必选项~",{time:1000});return
                        }
                        if(param.numberDay=="" || !reg.test(param.numberDay)){
                            layer.msg("每日进件限额不能为空并且输入要为正整数",{time:1000});return
                        }
                        if(param.numberWeek==""|| !reg.test(param.numberWeek)){
                            layer.msg("每周进件限额不能为空并且输入要为正整数",{time:1000});return
                        }
                        if(param.numberMonth==""|| !reg.test(param.numberMonth)){
                            layer.msg("每月进件限额不能为空并且输入要为正整数",{time:1000});return
                        }
                        var reg1=/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
                        if(param.quotaDay==''|| !reg1.test(param.quotaDay)){
                            layer.msg("日限额未必填项且必须为正数",{time:1000});return
                        }
                        if(param.quotaWeek==''|| !reg1.test(param.quotaWeek)){
                            layer.msg("周限额未必填项且必须为正数",{time:1000});return
                        }
                        if(param.quotaMonth==''|| !reg1.test(param.quotaMonth)){
                            layer.msg("月限额为必填项且必须为正数",{time:1000});return
                        }
                        if(param.singleQuota==''|| !reg1.test(param.singleQuota)){
                            layer.msg("单笔限额为必填项且必须为正数",{time:1000});return
                        }
                        Comm.ajaxPost('areaQuota/addQuota',JSON.stringify(param), function (data) {
                                if(data.code==0){
                                    layer.msg(data.msg,{time:1000},function(){
                                        layer.closeAll();
                                        //添加之后刷新页面
                                        g_userManage.tableUser.ajax.reload();
                                    })
                                }
                            },"application/json"
                        );
                    }
                });
            })


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
//动态加载省份下拉选的公共方法
function provinceSelAdd(selProvinceContent) {
    for(i=0;i<cityData3.length;i++){
        var opt='<option value="'+cityData3[i].value+'">'+cityData3[i].text+'</option>';
        // $('#change_province').append(opt);
        selProvinceContent.append(opt);
    }
}
//动态加载城市下拉选的公共方法(由省份下拉选触发)
function citySelAdd(value,selCityContent) {
    //先清空内容
    // $('#change_city').html("");
    selCityContent.html("");
    selCityContent.append('<option value="">'+"请选择"+'</option>');
    for(i=0;i<cityData3.length;i++){
        if(cityData3[i].value==value){
            var city=cityData3[i].children;
            for(j=0;j<city.length;j++){
                var opt='<option value="'+city[j].value+'">'+city[j].text+'</option>';
                selCityContent.append(opt);
            }
        }
    }
}
/*根据省份来加载城市(仅适用于修改弹出框)*/
function getcities(){
    //先获取省份选择的是啥
    var val=$("#change_province").val();
    //先清空内容
    $('#change_city').html("");
    $('#change_city').append('<option value="">'+"请选择"+'</option>');
    for(i=0;i<cityData3.length;i++){
        if(cityData3[i].value==val){
            var city=cityData3[i].children;
            for(j=0;j<city.length;j++){
                var opt='<option value="'+city[j].value+'">'+city[j].text+'</option>';
                $('#change_city').append(opt);
            }
        }
    }
}
//修改区域限额
function changeAreaQuota(id){
    //动态加载省份
    provinceSelAdd($("#change_province"))
    layer.open({
        type : 1,
        title : '修改区域限额窗口',
        maxmin : true,
        shadeClose : false,
        area : [ '450px', '450px' ],
        content : $('#changeQuota').show(),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            //先查询并展示出来信息以供修改
            Comm.ajaxPost('areaQuota/showOneQuota',JSON.stringify(param), function (data) {
                    //console.log(data);
                    if(data.code==0){
                        $('#change_province').val(data.data.provinceId);
                        //动态加载城市
                        getcities();
                        $("#change_city").val(data.data.cityId);
                        $("#change_state").val(data.data.state);
                        $("#change_day_number").val(data.data.numberDay);
                        $("#change_week_number").val(data.data.numberWeek);
                        $("#change_month_number").val(data.data.numberMonth);
                        $("#change_day_quota").val(data.data.quotaDay);
                        $("#change_week_quota").val(data.data.quotaWeek);
                        $("#change_month_quota").val(data.data.quotaMonth);
                        $("#change_single_quota").val(data.data.singleQuota);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            var param={};
            //传入后台参数
            param.id=id;
            param.provinceId=$("#change_province").val();
            param.provinceName=$("#change_province").find("option:selected").text();
            param.cityId=$("#change_city").val();
            param.cityName=$("#change_city").find("option:selected").text();
            param.state=$.trim($("#change_state").val());
            param.numberDay=$.trim($("#change_day_number").val());
            param.numberWeek=$.trim($("#change_week_number").val());
            param.numberMonth=$.trim($("#change_month_number").val());
            param.quotaDay=$.trim($("#change_day_quota").val());
            param.quotaWeek=$.trim($("#change_week_quota").val());
            param.quotaMonth=$.trim($("#change_month_quota").val());
            param.singleQuota=$.trim($("#change_single_quota").val());
            //console.log(param);
            //在此处做字符串验证
            var reg = /^[1-9]\d*$/;
            if( param.provinceId==''){
                layer.msg("省份为必选项",{time:1000});return
            }
            if(param.cityId==''){
                layer.msg("城市为必选项",{time:1000});return
            }
            if(param.numberDay==""|| !reg.test(param.numberDay)){
                layer.msg("每日进件限额不能为空并且输入要为正整数",{time:1000});return
            }
            if(param.numberWeek==""|| !reg.test(param.numberWeek)){
                layer.msg("每周进件限额不能为空并且输入要为正整数",{time:1000});return
            }
            if(param.numberMonth==""|| !reg.test(param.numberMonth)){
                layer.msg("每月进件限额不能为空并且输入要为正整数",{time:1000});return
            }
            var reg1=/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            if(param.quotaDay==''|| !reg1.test(param.quotaDay)){
                layer.msg("日限额未必填项且必须是正数",{time:1000});return
            }
            if(param.quotaWeek==''|| !reg1.test(param.quotaWeek)){
                layer.msg("周限额未必填项且必须是正数",{time:1000});return
            }
            if(param.quotaMonth==''|| !reg1.test(param.quotaMonth)){
                layer.msg("月限额为必填项且必须是正数",{time:1000});return
            }
            if(param.singleQuota==''|| !reg1.test(param.singleQuota)){
                layer.msg("单笔限额为必填项且必须是正数",{time:1000});return
            }
            Comm.ajaxPost('areaQuota/changeOneQuota',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:1000},function(){
                            layer.closeAll();
                            //添加之后刷新页面
                            g_userManage.tableUser.ajax.reload();
                        })
                    }
                },"application/json"
            );
        }
    });

}

//停用或启用
function startOrStop(id,state) {
    var param = {
        id: id,
        state:state
    }
    Comm.ajaxPost(
        'areaQuota/startOrStopQuota', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json"
    );
}
//查询重置
function resetSearch(){
    $("#state").val("");
    $("#search_city").val("");
    $("#search_province").val("");
    g_userManage.tableUser.ajax.reload();
}