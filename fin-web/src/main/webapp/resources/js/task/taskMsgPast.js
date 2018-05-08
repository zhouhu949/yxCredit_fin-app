var g_userManage = {
    tableCommit : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.nodeStateList = "3,4";
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    g_userManage.tableCommit = $("#commit_list").dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "iDisplayLength" : 10,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var paramFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost("taskMsg/getCommitTask", JSON.stringify(paramFilter), function (result) {
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
            {"data": "orderId","orderable" : false},
            {"data": "customerName","orderable" : false},
            {"data": "productName","orderable" : false},
            {"data": "processName","orderable" : false},
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
                    return json2TimeStamp(data);
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="operate(\''+data.customerId+'\',\''+data.relId+'\',\''+data.handUrl+'\')">查看</a>');
            $('td', row).eq(9).append(btnDel);
        },
        "initComplete" : function(settings,json) {
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
//可以被转寄的用户列表
function showUser(url,paramMap) {
    var showLayer = layer.open({
        type : 1,
        title : '转寄任务',
        maxmin : true,
        shadeClose : false,
        area : [ '650px','600px' ],
        content : $('#userDiv'),
        btn : [ '保存', '取消' ],
        success:function () {
            if(g_userManage.tableUser){
                g_userManage.tableUser.ajax.reload();
            }else{
                g_userManage.tableUser = $("#user_list").dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {
                        var paramFilter = g_userManage.getQueryCondition(data);
                        paramFilter.param =paramMap;
                        Comm.ajaxPost(url, JSON.stringify(paramFilter), function (result) {
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
                    "columns": [
                        {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"className" : "childBox","orderable" : false,"data" : null,"width" : "50px","searchable":false,
                            "render" : function(data, type, row, meta) {
                                return '<input type="checkbox" value="'+data.userId+'" style="cursor:pointer;" isChecked="false">'
                            }
                        },
                        {"data": "account","orderable" : false},
                        {"data": "userName","orderable" : false},
                        {"data": "orgName","orderable" : false},
                    ],
                    "initComplete" : function(settings,json) {
                        //单选行
                        $("#user_list tbody").delegate( 'tr','click',function(e){
                            var target=e.target;
                            if(target.nodeName=='TD'){
                                var input=target.parentNode.children[1].children[0];
                                var isChecked=$(input).attr('isChecked');
                                var selectArray = $("#user_list tbody input:checked");
                                if(selectArray.length>0){
                                    for(var i=0;i<selectArray.length;i++){
                                        $(selectArray[i]).attr('checked',false);
                                        $(input).attr('isChecked','false');
                                    }
                                }
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
                                $("input[name=userId]").val($(input).val());
                            }
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
            }
        },
        yes : function(index, layero) {
            var userId = $("input[name=userId]").val();
            if(userId != null && userId != ''){
                var param = {
                    taskNodeId:paramMap.id,
                    userId:userId
                }
                Comm.ajaxPost("taskMsg/updateTaskNodeByUser", JSON.stringify(param), function (result) {
                    layer.msg(result.msg,{time:1000},function () {
                        layer.close(showLayer);
                        $('#commit_list').dataTable().fnDraw(false);
                    });
                },"application/json");
            }else{
                layer.msg("尚未选择用户！",{time:1000});
            }
        }
    });

}