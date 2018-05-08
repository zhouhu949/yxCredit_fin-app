var g_userManage = {
    tableUser:null,
    tableCommit : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userManage.fuzzySearch) {
            var customerName = $("input[name='customerName']").val();
            if(customerName!=null && customerName != ''){
                param.customerName = customerName;
            }
            var orderId = $("input[name='orderId']").val();
            if(orderId!=null && orderId != ''){
                param.orderId = orderId;
            }
            var selectType = $("#selectType").val();
            if(selectType!=null && selectType != ''&& selectType != '-1'){
                param.selectType = selectType;
            }
            var beginTime = $("#beginTime").val();
            if(beginTime != null && beginTime != ''){
                // param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
                param.beginTime = beginTime;
            }
            var endTime = $("#endTime").val();
            if(endTime != null && endTime != ''){
                // param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
                param.endTime = endTime;
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
    g_userManage.tableCommit = $("#commit_list").dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        // "iDisplayLength" : 10,
        "ajax" : function(data, callback, settings) {
            var paramFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost("taskMsg/getMonitor", JSON.stringify(paramFilter), function (result) {
                //封装返回数据
                var resData = result.data;
                var resPage = result.page;
                var returnData = {
                    draw:data.draw,
                    recordsTotal:resPage.resultCount,
                    recordsFiltered:resPage.resultCount,
                    data:resData
                };
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {"data": null ,"searchable":false,"orderable" : false},
            {
                "className" : "childBox",
                'class':'hidden',
                "orderable" : false,
                "data" : null,
                "width" : "20px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input id="'+data.taskId+'" type="checkbox" value="'+data.taskId+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "orderId","orderable" : false},
            {"data": "customerName","orderable" : false},
            {"data": "productName","orderable" : false},
            {"data": "processName","orderable" : false},
            {"data": "processNodeName","orderable" : false},
            {"data" : "taskState","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==0)return '待处理';
                    if(data==1)return '处理中';
                    if(data==2)return '挂起';
                    if(data==3)return '拒绝';
                    if(data==4)return '通过';
                    else return "";
                }
            },
            {"data" : "taskNodeState","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==1)return '处理中';
                    if(data==2)return '挂起';
                    if(data==3)return '拒绝';
                    if(data==4)return '通过';
                    else return "";
                }
            },
            {"data" : "createTimeTask","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            {"data" : "createTimeTaskNode","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            {"data" : "nodeAlterTime","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data == null){
                        return null;
                    }
                    return json2TimeStamp(data);
                }
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
        },
        "initComplete" : function(settings,json) {
            $("#commit_list").delegate( 'tbody tr td ','click',function(e){
                $("#cus_order").show();
                var target=e.target;
                if(target.nodeName=='TD'){
                    var input=target.parentNode.children[1].children[0];
                    var isChecked=$(input).attr('isChecked');
                    if(isChecked=='false'){
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','true');
                    }else{
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','false');
                    }
                }
                target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                console.log(id);
                queryCusOrderList(id);
            });
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableCommit.ajax.reload();
                $("#cus_order").hide();
            });
            //重置
            $("#btn_search_reset").click(function() {
                $("input[name='customerName']").val("");
                $("input[name='orderId']").val("");
                $("#selectType").val(-1);
                $("#beginTime").val("");
                $("#endTime").val("");
                g_userManage.fuzzySearch = false;
                g_userManage.tableCommit.ajax.reload();
                $("#cus_order").hide();
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableCommit.on("order.dt search.dt", function() {
        g_userManage.tableCommit.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
});


function queryCusOrderList(taskId){
    g_userManage.tableCusOrder = $('#order_list').dataTable($.extend({
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
            var queryFilter = g_customerListManage.getQueryCondition(taskId,data);
            Comm.ajaxPost('taskMsg/getTaskDetail', JSON.stringify(queryFilter), function (result) {
                //封装返回数据
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            },"application/json","","","",false);
        },
        "order": [],
        "columns": [
            {"data": null ,"searchable":false,"orderable" : false},
            {"data": "orderId","orderable" : false},
            {"data": "customerName","orderable" : false},
            {"data": "productName","orderable" : false},
            {"data": "processNodeName","orderable" : false},
            {"data" : "taskNodeState","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==1)return '处理中';
                    if(data==2)return '挂起';
                    if(data==3)return '拒绝';
                    if(data==4)return '通过';
                    else return "";
                }
            },
            {"data" : "createTimeTaskNode","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            {"data" : "commitTimeTaskNode","searchable":false,"orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data == null){
                        return null;
                    }
                    return json2TimeStamp(data);
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="operate(\''+data.customerId+'\',\''+data.orderId+'\',\''+data.handUrl+'\')">查看</a>');
            $('td', row).eq(8).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            $("#commit_list").delegate( 'tbody tr td:not(:last-child)','click',function(e){
                $("#cus_order").show();
            });
            /*  //搜索
             $("#btn_search").click(function() {
             g_userManage.fuzzySearch = true;
             g_userManage.tableCusOrder.ajax.reload();
             });
             //重置
             $("#btn_search_reset").click(function() {
             $("input[name='personName']").val("");
             $("#beginTime").val("");
             $("#endTime").val("");
             g_userManage.fuzzySearch = false;
             g_userManage.tableCusOrder.ajax.reload();
             });*/
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableCusOrder.on("order.dt search.dt", function() {
        g_userManage.tableCusOrder.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
}

var g_customerListManage = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(taskId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.taskId=taskId;
        param.nodeStateList = "3,4";
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};

//查看
function operate(customerId,relId,handUrl) {
    layer.open({
        type : 2,
        area : ["100%", "100%" ],
        fix : false,
        maxmin : true,
        content : "/settleCustomer/details?orderId="+relId+"&customerId="+customerId,
    })
}