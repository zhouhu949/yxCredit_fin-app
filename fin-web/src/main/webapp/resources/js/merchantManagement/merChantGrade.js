/**
 * Created by Administrator on 2017/12/5.
 */
//获取全部商户级别信息
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
            param.grade = $("#grade").val();
            param.state = $("#state").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//动态加载商户级别下拉选
function getAppend(){
    var param = {};
    Comm.ajaxPost('merchantGrade/apendSelected', JSON.stringify(param), function (data) {
            for(i=0;i<data.data.length;i++){
                var opt='<option value="' + data.data[i].code + '">' + data.data[i].name + '</option>';
                $("#edit_grade").append(opt);
                $("#change_grade").append(opt);
                $("#grade").append(opt);

            }

        }, "application/json"
    );
}


//显示商户级别列表
$(function (){
    getAppend();
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
            Comm.ajaxPost('merchantGrade/getAllGrades', JSON.stringify(queryFilter), function (result) {
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
            {"data": "grade","orderable" : false},
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
            var btnChange=$('<a class="tabel_btn_style" onclick="(changeMerchanteGrade(\''+data.id+'\'))">修改</a>');
            $("td", row).eq(10).append(btnStartOrStop);
            $("td", row).eq(11).append(btnChange);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                $("#cus_order").hide();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //添加商户分级
            $("#btn_add_grade").on("click",function(){
                layer.open({
                    type : 1,
                    title : '添加商户级别',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '450px', '400px' ],
                    content : $('#addGrade').show(),
                    btn : [ '保存', '取消' ],
                    success : function(index, layero) {
                        $('#edit_grade').val('');
                        $("#edit_state").val('');
                        $("#number_day").val('');
                        $("#number_week").val('');
                        $("#number_month").val('');
                        $("#quota_day").val('');
                        $("#quota_week").val("");
                        $("#quota_month").val('');
                    },
                    yes:function(index,layero){
                        var param={};
                        //传入后台参数
                        param.grade=$.trim($('#edit_grade').val());
                        param.state=$.trim($("#edit_state").val());
                        param.numberDay=$.trim($("#number_day").val());
                        param.numberWeek=$.trim($("#number_week").val());
                        param.numberMonth=$.trim($("#number_month").val());
                        param.quotaDay=$.trim($("#quota_day").val());
                        param.quotaWeek=$.trim($("#quota_week").val());
                        param.quotaMonth=$.trim($("#quota_month").val());
                        param.singleQuota=$.trim($("#single_quota").val());
                       // console.log(param);
                        //在此处做字符串验证，先简单做之后再改
                        var reg = /^[1-9]\d*$/;
                        if(param.grade==''){
                            layer.msg("商户级别为必选项",{time:2000});return
                        }
                        if(param.state==''){
                            layer.msg("是否启用为必选项",{time:2000});return
                        }
                        if(param.numberDay=="" || !reg.test(param.numberDay)){
                            layer.msg("每日进件限额不能为空并且输入要为正整数",{time:2000});return
                        }
                        if(param.numberWeek==""|| !reg.test(param.numberWeek)){
                            layer.msg("每周进件限额不能为空并且输入要为正整数",{time:2000});return
                        }
                        if(param.numberMonth==""|| !reg.test(param.numberMonth)){
                            layer.msg("每月进件限额不能为空并且输入要为正整数",{time:2000});return
                        }
                        if(param.quotaDay==''|| !reg.test(param.quotaDay)){
                            layer.msg("日限额未必填项并且输入要为正整数",{time:2000});return
                        }
                        if(param.quotaWeek==''|| !reg.test(param.quotaWeek)){
                            layer.msg("周限额未必填项并且要为正整数",{time:2000});return
                        }
                        if(param.quotaMonth==''|| !reg.test(param.quotaMonth)){
                            layer.msg("月限额为必填项并且要为正整数",{time:2000});return
                        }
                        if(param.singleQuota==''|| !reg.test(param.singleQuota)){
                            layer.msg("单笔限额为必填项并且要为正整数",{time:2000});return
                        }
                        Comm.ajaxPost('merchantGrade/addmerchantGrade',JSON.stringify(param), function (data) {
                                if(data.code==0){
                                    layer.msg(data.msg,{time:2000},function(){
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
//停用或启用
function startOrStop(id,state) {
    var param = {
        id: id,
        state:state
    }
    Comm.ajaxPost(
        'merchantGrade/startOrStopGrade', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json"
    );
}
//修改商户等级配置信息
function changeMerchanteGrade(id) {
    layer.open({
        type : 1,
        title : '编辑商户分级设置',
        maxmin : true,
        shadeClose : false,
        area : [ '450px', '400px'  ],
        content : $('#changeGrade'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            //先查询并展示出来信息以供修改
            Comm.ajaxPost('merchantGrade/getOneGradebyId',JSON.stringify(param), function (data) {
                   // console.log(data);
                    if(data.code==0){
                        $('#change_grade').val(data.data.grade);
                        $('#change_state').val(data.data.state);
                        $('#change_number_day').val(data.data.numberDay);
                        $('#change_number_week').val(data.data.numberWeek);
                        $('#change_number_month').val(data.data.numberMonth);
                        $('#change_quota_day').val(data.data.quotaDay);
                        $('#change_quota_week').val(data.data.quotaWeek);
                        $('#change_quota_month').val(data.data.quotaMonth);
                        $('#change_single_quota').val(data.data.singleQuota);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            var param={};
            param.id=id;
            param.grade = $('#change_grade').val();
            param.state=$('#change_state').val();
            param.numberDay =  $.trim($('#change_number_day').val());
            param.numberWeek = $.trim($('#change_number_week').val());
            param.numberMonth = $.trim($('#change_number_month').val());
            param.quotaDay = $.trim($('#change_quota_day').val());
            param.quotaWeek =$.trim($('#change_quota_week').val());
            param.quotaMonth =$.trim($('#change_quota_month').val());
            param.singleQuota =$.trim($('#change_single_quota').val());

            //在这里做了简单的字符串验证
            var reg = /^[1-9]\d*$/;
            if(param.grade==''){
                layer.msg("商户级别为必选项",{time:2000});return
            }
            if(param.state==''){
                layer.msg("是否启用为必选项",{time:2000});return
            }
            if(param.numberDay==""|| !reg.test(param.numberDay)){
                layer.msg("每日进件限额不能为空并且输入要为正整数",{time:2000});return
            }
            if(param.numberWeek==""|| !reg.test(param.numberWeek)){
                layer.msg("每周进件限额不能为空并且输入要为正整数",{time:2000});return
            }
            if(param.numberMonth==""|| !reg.test(param.numberMonth)){
                layer.msg("每月进件限额不能为空并且输入要为正整数",{time:2000});return
            }
            var reg1=/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            if(param.quotaDay==''|| !reg1.test(param.quotaDay)){
                layer.msg("日限额为必填项并且输入要为正数",{time:2000});return
            }
            if(param.quotaWeek==''|| !reg1.test(param.quotaWeek)){
                layer.msg("周限额为必填项并且输入要为正数",{time:2000});return
            }
            if(param.quotaMonth==''|| !reg1.test(param.quotaMonth)){
                layer.msg("月限额为必填项并且输入要为正数",{time:2000});return
            }
            if(param.singleQuota==''|| !reg1.test(param.singleQuota)){
                layer.msg("单笔限额为必填项并且输入要为正数",{time:2000});return
            }
            Comm.ajaxPost('merchantGrade/changeOneGradebyId',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:2000},function(){
                            layer.closeAll();
                            //更改单个办单员信息之后刷新页面
                            g_userManage.tableUser.ajax.reload();
                        })
                    }
                },"application/json"
            );
        }
    });
}
//查询重置
function reset() {
    $("#state").val("");
    $("#grade").val("");
    g_userManage.tableUser.ajax.reload();
}