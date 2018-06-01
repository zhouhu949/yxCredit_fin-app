var g_userManage = {
    tableOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            param.content = $("input[name='s_content']").val();
            param.type = $('#s_type').val();
            param.customerName=$('#customerName').val();
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
$(function (){
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD',
        min: '1999-01-01',
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
        format: 'YYYY-MM-DD',
        min: '1999-01-01',
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
            "destroy":true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('suggestion/list', JSON.stringify(queryFilter), function (result) {
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
                {"data": null ,"searchable":false,"orderable" : false},
                {"data": "customerName","orderable" : false},
                {"data": "content","orderable" : false},
                {"data": "tel","orderable" : false}
                // ,
                // {"data": "type","orderable" : false}
            ],
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableOrder.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='s_content']").val("");
                    $("#s_type").val("");
                    $("#beginTime").val("");
                    $("#endTime").val("");
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




//时间转换
function formatTime(t){
    var time = t.replace(/\s/g,"");//去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+
        time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
    return time;
}


/*
//动态加载下拉框内容
function apendSelect() {
    Comm.ajaxPost('suggestion/apendSelect',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#s_type').append(btndel);
                }
            }
        },"application/json"
    );
}
*/

//获取当前时间
function getFirstTime() {
    var time;
    var newData=new Date();
    var N=newData.getFullYear();
    var s=newData.getMonth()+1;
    var Y=s<=9?"0"+s:s;
    var r=newData.getDate();
    var D=r<=9?"0"+r:r;
    var h=newData.getHours();
    var H=h<=9?"0"+h:h;
    var m=newData.getMinutes();
    var M=m<=9?"0"+m:m;
    var a=newData.getSeconds();
    var S=a<=9?"0"+a:a;
    return time=N+"-"+Y+"-"+D+" "+H+":"+M+":"+S;
}
$().ready(function () {
    var time=getFirstTime();
    //apendSelect();
})

