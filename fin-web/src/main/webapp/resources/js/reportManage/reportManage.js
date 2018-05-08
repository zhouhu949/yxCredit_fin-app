var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        // param.state="12";
        // param.personName=null;
        // param.beginTime=null;
        // param.endTime=null;
        if (g_userManage.fuzzySearch) {
            $("input[name='sProblem']:checked").each(function () {
                    if ($(this).val() == 'provice') {
                        param.provinceCol = 'yes';
                    } else if ($(this).val() == '') {
                        param.cityCol = 'yes';
                    } else if ($(this).val() == 'district') {
                        param.districtCol = 'yes';
                    }

                });
            param.merchantName=$.trim($("#merchantName").val());//商户名
            param.customerName=$.trim($("#customerName").val());//客户姓名
            param.customerTel=$.trim($("#customerTel").val());//手机号
            param.customerCard=$.trim($("#customerCard").val());//身份证号
            param.orderBeginTime=$.trim($("#orderBeginTime").val()).replace(/[^0-9]/ig,"");//订单开始时间
            param.orderEndTime=$.trim($("#orderEndTime").val()).replace(/[^0-9]/ig,"");//订单结束时间
            param.orderCapitalStart=$.trim($("#orderCapitalStart").val());//订单本金开始
            param.orderCapitalEnd=$.trim($("#orderCapitalEnd").val());//订单本金结束
            param.orderNo=$.trim($("#orderNo").val());//订单编号
            param.province=$("#province").val();//省份
            param.city=$("#city").val();//城市
            param.district=$("#district").val();//区
            // param.=$.trim($("#").val());
            // param.=$.trim($("#").val());
            //     param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            //     param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};

$(function (){
    //初始化时间插件
    var beginTime = {
        elem: '#orderBeginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1960-01-01 ',
        max: laydate.now(),
        istime: true,
        istoday: false,
    };
    laydate(beginTime);
    var endTime={
        elem: '#orderEndTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1960-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
    }
    laydate(endTime);
    //动态加载省份下拉选
    provinceSelAdd($("#province"));
    // citySelAdd($("province").val(),$("#city"));
    if(g_userManage.tableCustomer){
        g_userManage.tableCustomer.ajax.reload();
    }else{
        g_userManage.tableCustomer = $('#sign_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "destroy":true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('reportManage/getReportAll', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    // debugger
                    var returnData = {};
                    var resData = result.data.list;
                    // console.log(resData);
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                    //多选框绑定单击时间
                    xuanzhong();
                    //展示选中的列
                    showCol();
                },"application/json");
            },
            "order": [],
            "columns":[
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },//序号
                {"data": "province","orderable" : false},//一级渠道
                {"data": "city","orderable" : false},//二级渠道
                {"data": "district","orderable" : false},//三级渠道
                {"data": "merchantName","orderable" : false},//商户名称
                {"data": "orderNo","orderable" : false},//订单编号
                {"data": "customerName","orderable" : false},//姓名
                {"data": "customerTel","orderable" : false},//手机号
                {"data": "card","orderable" : false},//身份证号
                {"data": "merchantdiseName","orderable" : false},//商品名称
                //以上都是必选项
                {"data": "predictPrice","sClass":"predictPrice","orderable" : false},//首付金额
                {"data": "salesmanName","sClass":"salesmanName","orderable" : false},//办单员
                {"data": "salesmanTel","sClass":"salesmanTel","orderable" : false},//办单员手机号
                {"data": "yhze","sClass":"yhze","orderable" : false},//应还总额
                {"data": "orderCapital","sClass":"orderCapital","orderable" : false},//订单本金
                {"data": "yufukuan","sClass":"yufukuan","orderable" : false},//预付款
                {"data": "payCount","sClass":"payCount","orderable" : false},//期数
                {"data": "sureOrderTime","sClass":"sureOrderTime","orderable" : false,"render":function (data, type, row, meta) {
                    return data;
                }},//确认订单时间
                {"data": "orderState","sClass":"orderState","orderable" : false,"render":function (data,type,row,meta) {
                    return data;
                }},//订单状态
                {"data": "yufukuanState","sClass":"yufukuanState","orderable" : false,"render":function (data,type,row,meta) {
                    return data;
                }},//预付款状态
                {"data": "deliveryState","sClass":"deliveryState","orderable" : false ,"render":function (data,type,row,meta) {
                    return data;
                }},////发货状态
                {"data": "loanState","sClass":"loanState","orderable" : false,"render":function (data,type,row,meta) {
                    return data;
                }},//放款状态 放款状态 1待放款，2放款中，3已放款，4放款失败
                {"data": "jsState","sClass":"jsState","orderable" : false,"render":function (data,type,row,meta) {
                    return data;
                }},//结算状态
                {"data": "fahuoTime","sClass":"fahuoTime","orderable" : false},//发货时间
                {"data": "monthRate","sClass":"monthRate","orderable" : false},//月息
                {"data": "dayForDueDate","sClass":"dayForDueDate","orderable" : false},//离还款日天数
                {"data": "businessType","sClass":"businessType","orderable" : false}//行业类型
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    debugger
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableCustomer.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("#merchantName").val('');//商户名
                    $("#customerName").val('');//客户姓名
                    $("#customerTel").val('');//手机号
                    $("#customerCard").val('');//身份证号
                    $("#orderBeginTime").val('');//订单开始时间
                    $("#orderEndTime").val('');//订单结束时间
                    $("#orderCapitalStart").val('');//订单本金开始
                    $("#orderCapitalEnd").val('');//订单本金结束
                    $("#orderNo").val('');//订单编号
                    $("#province").val('');//省份
                    $("#city").val('');//城市
                    $("#district").val('');//区
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

//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
        time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}

//展示或隐藏列
function showCol(){
    //获取所有选中的值
    var list = new Array();
    var checks=$("input[name='dx']:checkbox:checked");
    for(var i =0;i<checks.length;i++){
        list[i]=checks[i].value;
    }
    // console.log(list);
    for(var i=0;i<list.length;i++){
        if(list[i] == 'predictPrice'){
            $('.predictPrice').css("display","table-cell");//首付金额
        }else if(list[i] =='salesmanName'){
            $('.salesmanName').css("display","table-cell");//办单员
        }else if(list[i] =='salesmanTel'){
            $('.salesmanTel').css("display","table-cell");//办单员手机号
        }else if(list[i] =='yhze'){
            $('.yhze').css("display","table-cell");//应还总额
        }else if(list[i] =='orderCapital'){
            $('.orderCapital').css("display","table-cell");//订单本金
        }else if(list[i] =='yufukuan'){
            $('.yufukuan').css("display","table-cell");//预付款
        }else if(list[i] =='payCount'){
            $('.payCount').css("display","table-cell");//期数
        }else if(list[i] =='sureOrderTime'){
            $('.sureOrderTime').css("display","table-cell");//确认订单时间
        }else if(list[i] =='orderState'){
            $('.orderState').css("display","table-cell");//订单状态
        }else if(list[i] =='yufukuanState'){
            $('.yufukuanState').css("display","table-cell");//预付款状态
        }else if(list[i] =='deliveryState'){
            $('.deliveryState').css("display","table-cell");//发货状态
        }else if(list[i] =='loanState'){
            $('.loanState').css("display","table-cell");//放款状态
        }else if(list[i] =='jsState'){
            $('.jsState').css("display","table-cell");//结算状态
        }else if(list[i] =='fahuoTime'){
            $('.fahuoTime').css("display","table-cell");//发货时间
        }else if(list[i] =='monthRate'){
            $('.monthRate').css("display","table-cell");//月息
        }else if(list[i] =='dayForDueDate'){
            $('.dayForDueDate').css("display","table-cell");//离还款日天数
        }else if(list[i] =='businessType'){
            $('.businessType').css("display","table-cell");//行业类型
        }
    }
}

//给多选框绑定单击事件
function xuanzhong(){
    $("input[name='dx']").click(function () {
        if(!$(this).context.checked){
            $('.'+$(this).context.value).css("display","none");
        }else{
            $('.'+$(this).context.value).css("display","table-cell");
        }
    });
}
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

//发送导出excel请求
function toExcel(){
var param = {};
    param.merchantName=$.trim($("#merchantName").val());//商户名
    param.customerName=$.trim($("#customerName").val());//客户姓名
    param.customerTel=$.trim($("#customerTel").val());//手机号
    param.customerCard=$.trim($("#customerCard").val());//身份证号
    param.orderBeginTime=$.trim($("#orderBeginTime").val()).replace(/[^0-9]/ig,"");//订单开始时间
    param.orderEndTime=$.trim($("#orderEndTime").val()).replace(/[^0-9]/ig,"");//订单结束时间
    param.orderCapitalStart=$.trim($("#orderCapitalStart").val());//订单本金开始
    param.orderCapitalEnd=$.trim($("#orderCapitalEnd").val());//订单本金结束
    param.orderNo=$.trim($("#orderNo").val());//订单编号
    param.province=$("#province").val();//省份
    param.city=$("#city").val();//城市
    param.district=$("#district").val();//区
    var list = new Array();//复选框选中的值(也就是要打印的字段集合)
    var checks=$("input[name='dx']:checkbox:checked");
    for(var i =0;i<checks.length;i++){
        list[i]=checks[i].value;
    }
    param.list=list;
    var salesOrder=null;//查询参数的map
    window.location.href = "ToExcel?salesOrder="+ encodeURI(JSON.stringify(param));
    return;

//     Comm.ajaxPost('reportManage/ToExcel', JSON.stringify(param), function (result) {
//
//     // layer.msg(result.msg,{time:1000},function () {
//     //     if (result.code=="0"){
//     //         layer.closeAll();
//     //         g_userManage.tableCustomer.ajax.reload();
//     //     }
//     // });
// },"application/json");
}
