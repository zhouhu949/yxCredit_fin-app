
//规则集左边查询条件
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var engineId= $("input[name='engineId']").val();
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {

        }
        param.status = 1;
        param.type = 2;
        param.engineId = engineId;
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;

    }
};
//规则集右边查询条件
var g_userDetailManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var engineId= $("input[name='engineId']").val();
        var parentIds=$("#parentIds").val();
        //自行处理查询参数
        param.fuzzySearch = g_userDetailManage.fuzzySearch;
        if (g_userDetailManage.fuzzySearch) {

        }
        param.status = 1;
        param.engineId = engineId;
        paramFilter.param = param;
        param.parentIds = parentIds;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;

    }
};
//初始化规则集
function getRulInt(){
    var isShowRules=$("#isShowRules").val();
    if(!isShowRules){
        g_userManage.tableUser = $('#ruls_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "iDisplayLength" : 5,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('knowledge/tree/getTreeDataForEngine',JSON.stringify(queryFilter),function(result){
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json")

            },
            "order": [],
            "columns" :[
                {
                    "data":null,
                    "searchable":false,"orderable" : false,
                    "class":"hidden"
                },
                {
                    "className" : "childBox",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" >'
                    }
                },
                {"data": "name","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    g_scoreManage.fuzzySearch = true;
                    g_scoreManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    $('input[name="Parameter_search"]').val("");
                    g_scoreManage.fuzzySearch = true;
                    g_scoreManage.tableUser.ajax.reload();
                });
                $("#ruls_list tbody").delegate( 'tr input','change',function(e){
                    var selectArray = $("#ruls_list tbody input:checked");
                    if(selectArray.length>0){
                        for(var i=0;i<selectArray.length;i++){
                            $(selectArray[i]).attr('checked',false);
                        }
                    }
                    $(this).attr('checked',"checked");
                    $("#parentIds").val($(this).attr("value"));
                    getRulsInt();
                    $("#myTabContent").hide();
                });
                $("#ruls_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#ruls_list tbody input:checked");
                        if(selectArray.length>0){
                            for(var i=0;i<selectArray.length;i++){
                                $(selectArray[i]).attr('checked',false);
                                $(input).attr('isChecked','false');
                            }
                        }
                        if(isChecked=='false'){
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','true');
                        }else{
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','false');
                        }
                        $("#parentIds").val($(input).attr("value"));
                        getRulsInt();
                        $("#myTabContent").hide();
                        $("#ruls_detail_list_wrapper .bottom").show();
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
        $("#isShowRules").val(true);
    }else{
        g_userManage.tableUser.ajax.reload();
    }
}
//初始化规则集详情
function getRulsInt(){
    var isShowRules=$("#isShowRules_detail").val();
    if(!isShowRules){
        g_userDetailManage.tableUser = $('#ruls_detail_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "iDisplayLength" : 5,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {
                var queryFilter = g_userDetailManage.getQueryCondition(data);
                Comm.ajaxPost('knowledge/rule/getRuleDataForEngine',JSON.stringify(queryFilter),function(result){
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json")

            },
            "order": [],
            "columns" :[
                {
                    "data":null,
                    "searchable":false,"orderable" : false,
                    "class":"hidden"
                },
                {
                    "className" : "childBox",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" priority="'+data.priority+'" cname="'+data.name+'" dataParentId="'+data.parentId+'" code="'+data.code+'">'
                    }
                },
                {"data": "name","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    g_userDetailManage.fuzzySearch = true;
                    g_userDetailManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    g_userDetailManage.fuzzySearch = true;
                    g_userDetailManage.tableUser.ajax.reload();
                });
                $("#ruls_detail_list tbody").delegate( 'tr input','change',function(e){
                    getDetails();
                });
                $("#ruls_detail_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        if(isChecked=='false'){
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','true');
                        }else{
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','false');
                        }
                        getDetails();
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userDetailManage.tableUser.on("order.dt search.dt", function() {
            g_userDetailManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowRules_detail").val(true);
    }else{
        g_userDetailManage.tableUser.ajax.reload();
    }
}
//展示初始化数据
function lookOrOperate(nodeId) {
    layer.open({
        type : 1,
        title : '规则集详情',
        maxmin : true,
        shadeClose : false,
        area : [ '750px', '700px' ],
        content : $('.page-content'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var _param = getParam();
            var _url;
            if(_param.deny_rules!=''  || _param.addOrSubRules!=''){
                if(node_2.id !=0 ){
                    _url ="decision_flow/update";
                }else{
                    _url ="decision_flow/save";
                }
                Comm.ajaxPost(_url,JSON.stringify(_param),function(data){
                    layer.closeAll();
                    layer.msg(data.msg,{time:1000});
                    if(data.code == 0){
                        node_2.id = data.data;
                    }
                },"application/json")
            }
        },
        success:function () {
            clearRule();
            if(nodeId!=0){
                var _param = {"nodeId":nodeId};
                Comm.ajaxPost("decision_flow/getNode",_param,function (result) {
                    var data=result.data;
                    if(data!=null){
                        var jsonStr = $.parseJSON(data.nodeJson);
                        var addOrSubRules = null ,deny_rules = null;
                        if(jsonStr.addOrSubRules!=''){
                            addOrSubRules = $.parseJSON(jsonStr.addOrSubRules)
                        }
                        if(jsonStr.deny_rules!=''){
                            deny_rules = $.parseJSON(jsonStr.deny_rules);
                        }
                        initRender(deny_rules,addOrSubRules);
                    }else{
                        getRulInt();
                    }
                })
            }else{
                getRulInt();
            }

        }
    });
}
//显示底部数据源
function getDetails(){
    var inputChecked=$("#ruls_detail_list tbody tr input:checked");
    if(inputChecked.length>0){
        $("#refuse-rule").find("span").remove();
        $(".c-policy-priority").hide();
        for(var i=0;i<inputChecked.length;i++){
            var priority=$(inputChecked[i]).attr("priority");
            $("#priority-"+priority).children(".right-priority").append("<span class='l_back' dataTitle='"+$(inputChecked[i]).attr("cname")+"' dataId='"+$(inputChecked[i]).attr("value")+"' dataParentId='"+$(inputChecked[i]).attr("dataParentId")+"' dataPriority='"+$(inputChecked[i]).attr("priority")+"' dataCode='"+$(inputChecked[i]).attr("code")+"'>"+$(inputChecked[i]).attr("cname")+"</span>");
            $("#priority-"+priority).show();
        }
        $("#myTabContent").show();
    }else{
        $("#myTabContent").hide();
    }

}

$("#preview").click(function(){
    openWin();
});

function openWin(){
    var param = getParam();
    $("#previewForm input[name=id]").val(node_2.id);
    $("#previewForm input[name=ruleJson]").val(JSON.stringify(param));
    window.open("about:blank","newWin","");
    $("#previewForm").submit();
}
function getParam(){
    var _param = new Object();
    var nodeId = node_2.id;
    var initEngineVersionId =$("input[name=initEngineVersionId]").val();
    var node_dataIdDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_dataId');
    var node_dataId=$(node_dataIdDom).val();

    var node_urlDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_url');
    var node_url=$(node_urlDom).val();

    var node_xDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_x');
    var node_x=$(node_xDom).val();

    var node_yDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_y');
    var node_y=$(node_yDom).val();

    var node_nameDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_name');
    var node_name=$(node_nameDom).val();

    var node_codeDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_code');
    var node_code=$(node_codeDom).val();

    var node_typeDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_type');
    var node_type=$(node_typeDom).val();

    var node_orderDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_order');
    var node_order=$(node_orderDom).val();
    _param.initEngineVersionId = initEngineVersionId;
    _param.id = nodeId;
    _param.nodeX = node_x;
    _param.nodeY = node_y;
    _param.nodeName = node_name;
    _param.nodeCode = node_code;
    _param.nodeType = node_type;
    _param.nodeOrder = node_order;
    _param.params ='{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'"}';
    _param.deny_rules = getDenyRulesJson();
    _param.addOrSubRules =  getAddOrSubJson();
    return  _param;
}
//拼接拒绝规则Json
function getDenyRulesJson(){
    var isSerial;
    if($("#c-refuse-rule").find('.l_back').length > 0) {
        isSerial = 1;
    }else{
        isSerial = 0;
    }
    var slectedRuleIdsForDenyRules = "";
    if($("#refuse-rule").find('.right-priority .l_back').length>0){
        slectedRuleIdsForDenyRules = '{"isSerial":'+isSerial;
        slectedRuleIdsForDenyRules +=',"rules":[';
        $("#refuse-rule").find('.right-priority .l_back').each(function(index,element){
            slectedRuleIdsForDenyRules +='{"id":'+$(element).attr("dataId")+','
            slectedRuleIdsForDenyRules +='"parentId":'+$(element).attr("dataParentId")+',';
            slectedRuleIdsForDenyRules +='"priority":'+$(element).attr("dataPriority")+',';
            slectedRuleIdsForDenyRules +='"code":"'+$(element).attr("dataCode")+'",';
            slectedRuleIdsForDenyRules +='"name":"'+$(element).attr("dataTitle")+'"}'+',';
        });
        slectedRuleIdsForDenyRules = slectedRuleIdsForDenyRules.substring(0,slectedRuleIdsForDenyRules.length-1);
        slectedRuleIdsForDenyRules +="]}"
    }
    return slectedRuleIdsForDenyRules;
}
//拼接加减分规则json
function getAddOrSubJson(){
    var threshold = $("#threshold").val();
    var slectedRuleIdsForAddRules = "";
    if($("#addorSub-rule").find('.l_back').length>0){
        var slectedRuleIdsForAddRules = '{"threshold":"'+threshold;
        slectedRuleIdsForAddRules +='","rules":[';
        $("#addorSub-rule").find('.l_back').each(function(index,element){
            slectedRuleIdsForAddRules +='{"id":'+$(element).attr("dataId")+',';
            slectedRuleIdsForAddRules +='"parentId":'+$(element).attr("dataParentId")+',';
            slectedRuleIdsForAddRules +='"priority":'+$(element).attr("dataPriority")+',';
            slectedRuleIdsForAddRules +='"code":"'+$(element).attr("dataCode")+'",';
            slectedRuleIdsForAddRules +='"name":"'+$(element).attr("dataTitle")+'"}'+",";
        })
        slectedRuleIdsForAddRules = slectedRuleIdsForAddRules.substring(0,slectedRuleIdsForAddRules.length-1);
        slectedRuleIdsForAddRules +="]}"
    }
    return slectedRuleIdsForAddRules;
}
$("#myTabContent").on("click",".l_checkbox",function(){
    if($(this).hasClass("l_back")==false){
        $(this).addClass("l_back");
    }else{
        $(this).removeClass("l_back");
    }
});
function initRender(deny_ruless,addOrSubRules) {
    var deny_rules=deny_ruless.rules;
    var addOrSubRules=addOrSubRules;
    if(deny_rules){
        $("#refuse-rule").find("span").remove();
        $(".c-policy-priority").hide();
        if(deny_ruless.isSerial==1){
            $("#c-refuse-rule .l_checkbox").addClass("l_back");
        }
        for(var i=0;i<deny_rules.length;i++){
            var priority=deny_rules[i].priority;
            $("#priority-"+priority).children(".right-priority").append("<span class='l_back' dataTitle='"+deny_rules[i].name+"' dataId='"+deny_rules[i].id+"' dataParentId='"+deny_rules[i].parentId+"' dataPriority='"+deny_rules[i].priority+"' dataCode='"+deny_rules[i].code+"'>"+deny_rules[i].name+"</span>");
            $("#priority-"+priority).show();
        }
        $("#myTabContent").show();
        getRulInt();
    }
    if(addOrSubRules){
        console.log(addOrSubRules);
    }

}
function clearRule() {
    $("#ruls_list tbody").children().remove();
    $("#ruls_detail_list tbody").children().remove();
    $("#ruls_detail_list_wrapper .bottom").hide();
    $("#c-refuse-rule .l_checkbox").removeClass("l_back");
}
