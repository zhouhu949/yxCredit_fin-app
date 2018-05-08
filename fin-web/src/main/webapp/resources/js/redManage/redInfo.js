var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch){
            debugger
            param.customerName = $("#customerName").val();//客户名称
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
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            //endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        //max: laydate.now(),
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
        g_userManage.tableCustomer = $('#fee_list').dataTable($.extend({
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
                Comm.ajaxPost('red/redInfoList', JSON.stringify(queryFilter), function (result) {
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
                {"data": "id","orderable" : false},
                {"data": "inviteName","orderable" : false},
                {"data": "inviteTel","orderable" : false},
                {"data": "money","orderable" : false},
                {"data": "createTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {"data": "customerName","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "orderId","orderable" : false},
                {
                    "data": "isWithdraw", "orderable": false,
                    "render": function (data, type, row, meta) {
                        if (data == 1) {
                            return "未提现";
                        } else if (data == 0) {
                            return "已提现";
                        } else {
                            return "";
                        }
                    }
                },
                // {
                //     "data": "null", "orderable": false,
                //     "defaultContent":""
                // }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                // var btnUpdate = $('<a class="tabel_btn_style" onclick="editDetail(\'2\',\''+data.id+'\')">修改 </a>&nbsp; ');
                // var btnDetele = $(' &nbsp; <a class="tabel_btn_style" onclick="deleteDetail(\'1\',\''+data.id+'\')"> 删除</a>');
                // var btnOpen = $(' &nbsp; <a class="tabel_btn_style" onclick="deleteDetail(\'2\',\''+data.id+'\')"> 启用</a>');
                // var btnClose = $(' &nbsp; <a class="tabel_btn_style" onclick="deleteDetail(\'3\',\''+data.id+'\')"> 停用</a>');
                // if(data.status == '0'){
                //     $('td', row).eq(5).append(btnUpdate).append(btnDetele).append(btnOpen);
                // }else{
                //     $('td', row).eq(5).append(btnUpdate).append(btnDetele).append(btnClose);
                // }

            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableCustomer.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("#customerName").val("");
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


//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
        time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}