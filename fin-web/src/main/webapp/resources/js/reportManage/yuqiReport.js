var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
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
            param.customerName=$.trim($("#customerName").val());//姓名
            param.customerTel=$.trim($("#customerTel").val());//手机号
            param.customerCard=$.trim($("#customerCard").val());//身份证号
            param.yuqiDays=$.trim($("#yuqiDays").val());//逾期天数
            param.dqyhTimeStart=$.trim($("#dqyhTimeStart").val()).replace(/[^0-9]/ig,"");//到期应还时间开始
            param.dqyhTimeEnd=$.trim($("#dqyhTimeEnd").val()).replace(/[^0-9]/ig,"");//到期应还时间结束
            param.yuqiMoneyStart=$.trim($("#yuqiMoneyStart").val());//逾期金额开始
            param.yuqiMoneyEnd=$.trim($("#yuqiMoneyEnd").val());//逾期金额结束
            param.province=$("#province").val();//省份
            param.city=$("#city").val();//城市
            param.district=$("#district").val();//区
            param.merchantName=$("#merchantName").val();//商户名称
            param.merchantNo=$("#merchantNo").val();//商户编号
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
        elem: '#dqyhTimeStart',
        format: 'YYYY-MM-DD ',
        min: '1960-01-01 ',
        max: '2050-01-01 ',
        istime: true,
        istoday: false,
    };
    laydate(beginTime);
    var endTime={
        elem: '#dqyhTimeEnd',
        format: 'YYYY-MM-DD',
        min: '1960-01-01',
        max: '2050-01-01',
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
                Comm.ajaxPost('reportManage/getYuQiReports', JSON.stringify(queryFilter), function (result) {
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
                    //多选框绑定单击事件
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
                {"data": "", "orderable": false,"defaultContent":"- -"},//渠道编号
                {"data": "province","orderable" : false},//一级渠道
                {"data": "city","orderable" : false},//二级渠道
                {"data": "district","orderable" : false},//区域
                {"data": "merchant_id","orderable" : false},//商户编号
                {"data": "merchantName","orderable" : false},//商户名称
                {"data": "orderNo","orderable" : false},//订单编号
                {"data": "orderDate","orderable" : false},//订单日期
                {"data": "customerName","orderable" : false},//客户姓名
                {"data": "card","orderable" : false},//身份证号
                {"data": "customerTel","orderable" : false},//手机号
                {"data": "merchantdiseName","orderable" : false},//购买商品
                //以上都是必选项
                {"data": "applayMoney","sClass":"applayMoney","orderable" : false},//申请金额
                {"data": "orderTotalMoney","sClass":"orderTotalMoney","orderable" : false},//订单总金额
                {"data": "syyhzje","sClass":"syyhzje","orderable" : false},//剩余应还总金额
                {"data": "yuqiDays","sClass":"yuqiDays","orderable" : false},//逾期天数
                {"data": "yqfx","sClass":"yqfx","orderable" : false},//逾期罚息
                // {"data": "yhbx","sClass":"yhbx","orderable" : false},//应还本息
                {"data": "payCount","sClass":"payCount","orderable" : false},//分期数
                {"data": "pay_time","sClass":"pay_time","orderable" : false},//应还日期
                {"data": "dqyhzje","sClass":"dqyhzje","orderable" : false,"render":function (data, type, row, meta) {return data;}},//到期应还总金额
                {"data": "salesman","sClass":"salesman","orderable" : false,"render":function (data,type,row,meta) {return data;}},//办单员
                {"data": "hkqs","sClass":"hkqs","orderable" : false,"render":function (data,type,row,meta) {return data;}},//还款期数
                {"data": "lsyq","sClass":"lsyq","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//历史逾期
                {"data": "hjszd","sClass":"hjszd","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//户籍所在地
                {"data": "provinceWork","sClass":"provinceWork","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//省份
                {"data": "cityWork","sClass":"cityWork","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//城市
                {"data": "nowaddress","sClass":"nowaddress","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//现居住地址
                {"data": "companyName","sClass":"companyName","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//工作单位
                {"data": "companyPhone","sClass":"companyPhone","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//电话
                {"data": "link0","sClass":"link0","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//电话1
                {"data": "link1","sClass":"link1","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//电话2
                {"data": "link2","sClass":"link2","orderable" : false ,"render":function (data,type,row,meta) {return data;}},//电话3
                {"data": "link3","sClass":"link3","orderable" : false ,"render":function (data,type,row,meta) {return data;}}//电话4
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

//获取复选框所有选中的值并展示选中的值，设置显示
function showCol(){
    //获取所有选中的值
    var list = new Array();
    var checks=$("input[name='dx']:checkbox:checked");
    for(var i =0;i<checks.length;i++){
        list[i]=checks[i].value;
    }
    console.log(list);
    for(var i=0;i<list.length;i++){//.orderTotalMoney , .yhzje, .yuqiDays,.yqfx,.yhbx,.payCount,.pay_time,.dqyhzje,.salesman,.hkqs,.lsyq
        if(list[i] == 'orderTotalMoney'){
            $('.orderTotalMoney').css("display","table-cell");//订单总金额
        }else if(list[i] =='applayMoney'){
            $('.applayMoney').css("display","table-cell");//申请金额
        }else if(list[i] =='syyhzje'){
            $('.syyhzje').css("display","table-cell");//剩余应还总金额
        }else if(list[i] =='yuqiDays'){
            $('.yuqiDays').css("display","table-cell");//逾期天数
        }else if(list[i] =='yqfx'){
            $('.yqfx').css("display","table-cell");//逾期罚息
        }else if(list[i] =='yhbx'){
            $('.yhbx').css("display","table-cell");//应还本息
        }else if(list[i] =='payCount'){
            $('.payCount').css("display","table-cell");//分期数
        }else if(list[i] =='pay_time'){
            $('.pay_time').css("display","table-cell");//应还日期
        }else if(list[i] =='dqyhzje'){
            $('.dqyhzje').css("display","table-cell");//到期应还总金额
        }else if(list[i] =='salesman'){
            $('.salesman').css("display","table-cell");//办单员
        }else if(list[i] =='hkqs'){
            $('.hkqs').css("display","table-cell");//还款期数
        }else if(list[i] =='lsyq'){
            $('.lsyq').css("display","table-cell");//历史逾期
        }else if(list[i] =='hjszd'){
            $('.hjszd').css("display","table-cell");//户籍所在地
        }else if(list[i] =='provinceWork'){
            $('.provinceWork').css("display","table-cell");//省
        }else if(list[i] =='cityWork'){
            $('.cityWork').css("display","table-cell");//市
        }else if(list[i] =='nowaddress'){
            $('.nowaddress').css("display","table-cell");//现居住地址
        }else if(list[i] =='companyName'){
            $('.companyName').css("display","table-cell");//工作单位
        }else if(list[i] =='companyPhone'){
            $('.companyPhone').css("display","table-cell");//电话
        }else if(list[i] == 'link0'){
            $('.link0').css("display","table-cell");//联系人1
        }else if(list[i] == 'link1'){
            $('.link1').css("display","table-cell");//联系人2
        }else if(list[i] == 'link2'){
            $('.link2').css("display","table-cell");//联系人3
        }else if(list[i] == 'link3'){
            $('.link3').css("display","table-cell");//联系人4
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
    /**
     {"data": "", "orderable": false,"defaultContent":"- -"},//渠道编号
     {"data": "province","orderable" : false},//一级渠道
     {"data": "city","orderable" : false},//二级渠道
     {"data": "district","orderable" : false},//区域
     {"data": "merchant_id","orderable" : false},//商户编号
     {"data": "merchantName","orderable" : false},//商户名称
     {"data": "orderNo","orderable" : false},//订单编号
     {"data": "orderDate","orderable" : false},//订单日期
     {"data": "customerName","orderable" : false},//客户姓名
     {"data": "card","orderable" : false},//身份证号
     {"data": "customerTel","orderable" : false},//手机号
     {"data": "merchantdiseName","orderable" : false},//购买商品
     * @type {{}}
     */
    var param = {};//所有筛选条件
    param.customerName=$.trim($("#customerName").val());//客户姓名
    param.customerTel=$.trim($("#customerTel").val());//手机号
    param.customerCard=$.trim($("#customerCard").val());//身份证号
    param.yuqiDays=$.trim($("#yuqiDays").val());//逾期天数
    param.dqyhTimeStart=$.trim($("#dqyhTimeStart").val()).replace(/[^0-9]/ig,"");//到期应还开始时间
    param.orderEndTime=$.trim($("#dqyhTimeEnd").val()).replace(/[^0-9]/ig,"");//到期应还结束时间
    param.yuqiMoneyStart=$.trim($("#yuqiMoneyStart").val());//逾期金额开始
    param.yuqiMoneyEnd=$.trim($("#yuqiMoneyEnd").val());//逾期金额结束
    param.province=$("#province").val();//省份
    param.city=$("#city").val();//城市
    param.district=$("#district").val();//区
    param.merchantName=$.trim($("#merchantName").val());//商户名
    param.merchantNo=$.trim($("#merchantNo").val());//商户编号



    var list = new Array();//复选框选中的值(也就是要打印的字段集合)
    var checks=$("input[name='dx']:checkbox:checked");
    for(var i =0;i<checks.length;i++){
        list[i]=checks[i].value;
    }
    param.list=list;
    var salesOrder=null;//查询参数的map
    window.location.href = "yuqiToExcel?salesOrder="+ encodeURI(JSON.stringify(param));
    return;
}
//查询重置
$(function () {
    $('#btn_search_reset').click(function () {
        g_userManage
        $("#customerName").val('');//姓名
        $("#customerTel").val('');//手机号
        $("#customerCard").val('');//身份证号
        $("#yuqiDays").val('');//逾期天数
        $("#dqyhTimeStart").val('');//到期应还时间开始
        $("#dqyhTimeEnd").val('');//到期应还时间结束
        $("#yuqiMoneyStart").val('');//逾期金额开始
        $("#yuqiMoneyEnd").val('');//逾期金额结束
        $("#province").val('');//省份
        $("#city").val('');//城市
        $("#district").val('');//区
        $("#merchantName").val('');//商户名称
        $("#merchantNo").val('');//商户编号
        g_userManage.fuzzySearch = false;
        g_userManage.tableCustomer.ajax.reload();
    })
})
