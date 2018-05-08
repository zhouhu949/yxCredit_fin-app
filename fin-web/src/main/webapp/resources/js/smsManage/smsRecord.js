var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            debugger
            param.sms_tel = $("input[name='sms_tel']").val();
            param.beginTime = $("#beginTime").val().replace(/\ /g,"").replace(/\:/g,"").replace(/\-/g,"");
            param.endTime = $("#endTime").val().replace(/\ /g,"").replace(/\:/g,"").replace(/\-/g,"");
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
    if(g_userManage.tableOrder){
        g_userManage.tableOrder.ajax.reload();
    }else{
        g_userManage.tableOrder = $('#sign_list').dataTable($.extend({
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
                Comm.ajaxPost('sms/smsRecordList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "id" ,"searchable":false,"orderable" : false,"class":"hidden"},
                {"data": "tel","orderable" : false},
                {"data": "content","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return substr(data);
                    }
                },
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],"createdRow": function ( row, data, index,settings,json ) {

                var btnSee=$('<span style="color: #307ecc;" onclick="config(\''+data.id+'\')">查看</span>');
                $("td", row).eq(5).append(btnSee).append(' ');
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='sms_name']").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableOrder.ajax.reload();
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

// 模板配置
function config(id) {
    layer.open({
        type : 1,
        title : '短信配置',
        maxmin : true,
        shadeClose : false,
        area : [ '400', '400' ],
        content : $('#Add_procedure_style'),
        success : function(index, layero) {
            var param = {};
            param.id = id;
            $('input[name="sms_rule"]').attr("checked",false);
            Comm.ajaxPost('sms/getRecordById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger
                        $('#sms_tel').val(data.data.tel);
                        $('#create_time').val(formatTime(data.data.creatTime));
                        $('#sms_content').val(data.data.content);
                    }
                },"application/json"
            );
        }
    });
}



//时间转换
function formatTime(t){
    debugger
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}

//内容字符串截取
function substr(str){
    debugger
    if(str.length>10){
        str=str.substring(0,9)+"..."
    }
    return str;
}
