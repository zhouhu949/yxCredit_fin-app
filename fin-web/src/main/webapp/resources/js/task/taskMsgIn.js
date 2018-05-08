var g_userManage = {
    tableUser:null,
    tableCommit : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.nodeStateList = "1,2";
        paramFilter.param = param;
        debugger
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
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "iDisplayLength" : 10,
        "ajax" : function(data, callback, settings) {
            var paramFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost("taskMsg/getCommitTask", JSON.stringify(paramFilter), function (result) {
                debugger
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
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            debugger
            var taskId=data.taskId;//任务id
            var url=data.pageUrl;
            var taskNodeId = data.taskNodeId;//任务节点id
            var processId = data.processId;
            //var processNodeId = data.processNodeId;
            var processNode=data.processNodeId;
            var relType=data.relType;
            var relId=data.relId;
            if(data.state==3||data.state==6||data.state==7){
                $("td", row).eq(9).html('<p>手机端处理</p>');
            }else if(data.taskNodeState==2){
                $("td", row).eq(9).html('<a class="tabel_btn_style" onclick="operate(\'unlock\',\''+taskNodeId+'\',null,null)">解挂</a>');
            } else{
                var str = '<a class="tabel_btn_style" onclick="operate(\'forward\',\''+taskNodeId+'\',\''+processId+'\',\''+processNode+'\')">转寄</a>';
                str += '&nbsp;<a class="tabel_btn_style" onclick="operate(\'lock\',\''+taskNodeId+'\',null,null)">挂起</a>';
                str += '&nbsp;<a class="tabel_btn_style" onclick="operate(\'handle\',\''+taskNodeId+'\',\''+processId+'\',\''+processNode+'\',\''+taskId+'\',\''+url+'\',\''+relType+'\',\''+relId+'\')">处理</a>';
                $("td", row).eq(9).html(str);
                // var str = '&nbsp;<a class="tabel_btn_style" onclick="operate(\'handle\',\''+taskNodeId+'\',\''+processId+'\',\''+processNode+'\',\''+taskId+'\',\''+url+'\',\''+relType+'\',\''+relId+'\')">处理</a>';
                // $("td", row).eq(9).html(str);
            }
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
//解挂，挂起，转寄，处理
function operate(stat,taskNodeId,processId,processNode,taskId,url,relType,relId) {
    debugger
    var paramMap = {id:taskNodeId};
    if(stat=="forward" && processId != null && processNode != null){
        paramMap.processId = processId;
        paramMap.processNodeId = processNode;
        showUser("taskMsg/forwardTask",paramMap);
    }else {
        paramMap.state = stat;
        if(stat=="handle"){
            //  window.location=_ctx+url+"?taskId="+taskId+"&taskNodeId="+taskNodeId+"&processNode="+processNode+"&relType="+relType+"&relId="+relId+"&processId="+processId+"&isTaskFlag=1"+"&leftStatus=in";
            layer.open({
                type : 2,
                area : ["100%", "100%" ],
                fix : false,
                maxmin : true,
                content : [_ctx+url+"?taskId="+taskId+"&taskNodeId="+taskNodeId+"&processNode="+processNode+"&relType="+relType+"&relId="+relId+"&processId="+processId+"&isTaskFlag=1"+"&leftStatus=in"],
            })
            return;
        }
        Comm.ajaxPost("taskMsg/updateState", JSON.stringify(paramMap), function (result) {
            layer.msg(result.msg,{time:1000},function () {
                $("#commit_list").dataTable().fnDraw(false);
            });
        },"application/json");
    }
}
//可以被转寄的用户列表
function showUser(url,paramMap) {
    var showLayer = layer.open({
        type : 1,
        title : false,
        shadeClose : true,
        area : [ '650px','200px' ],
        content : $('#userDiv'),
        offset:[ '1px'],
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
                    "iDisplayLength" : 2,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {
                        var paramFilter = g_userManage.getQueryCondition(data);
                        paramFilter.param =paramMap;
                        Comm.ajaxPost(url, JSON.stringify(paramFilter), function (result) {
                            //封装返回数据
                            debugger
                            var resData = result.data;
                            var resPage = result.page;
                            if(resData!=null){
                                var returnData = {
                                    draw:data.draw,
                                    recordsTotal:resPage.resultCount,
                                    recordsFiltered:resPage.resultCount,
                                    data:resData
                                };
                                callback(returnData);
                            }
                        },"application/json");
                    },
                    "columns": [
                        {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"className" : "childBox","orderable" : false,"data" : null,"width" : "50px","searchable":false,
                            "render" : function(data, type, row, meta) {
                                return '<input type="radio" value="'+data.userId+'" style="cursor:pointer;" disabled="disabled" >'
                            }
                        },
                        {"data": "account","orderable" : false},
                        {"data": "userName","orderable" : false},
                        {"data": "orgName","orderable" : false},
                    ],
                    "initComplete" : function(settings,json) {
                        //单选行
                        $("#user_list tbody").delegate( 'tr','click',function(e){
                            debugger
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
                                var a = $(input).val();
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
            debugger
            if(userId != null && userId != ''&&userId!='undefined'){
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