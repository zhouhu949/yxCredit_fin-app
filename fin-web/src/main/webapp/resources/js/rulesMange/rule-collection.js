$(function (){
    queryList("knowledge/rule/list");
});
var isFirstTerm=1;
var isFirstOut=1;
var isFirstRuleFields=1;
//列表数据查询条件
var g_userManage = {
    tableUser : null,
    tableField: null,
    ruleSearch : false,
    scorecardSearch : false,
    fieldSearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if(engineIdFlag){
            param.engineId = $("input[name=eid]").val();
        }
        if (g_userManage.ruleSearch) {
            param.ruleName = $("input[name='rule_search']").val();
        }
        if (g_userManage.scorecardSearch) {
            param.scorecardName = $("input[name='rule_search']").val();
        }
        if (g_userManage.fieldSearch) {
            param.searchKey = $("input[name='Parameter_search']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};
//查询方法
function btnSearch() {
    g_userManage.ruleSearch = true;
    g_userManage.tableUser.ajax.reload();
}
//查询重置方法
function btnSearchReset() {
    $('input[name="rule_search"]').val("");
    g_userManage.ruleSearch = false;
    g_userManage.tableUser.ajax.reload();
}
//显示列表数据
function queryList(url) {
    var $id;
    if(url.indexOf("rule")>=0){
        $id="#rule_list";
        $("#scorecard_list_wrapper").hide();
    }else{
        $id="#scorecard_list";
        $("#rule_list_wrapper").hide();
    }
    $($id).show();
    g_userManage.tableUser = $($id).dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(data);
            if(parentIds != null){
                queryFilter.param.parentIds = parentIds;
            }
            Comm.ajaxPost(url,JSON.stringify(queryFilter),function(result){
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
                'data':null,
                'class':'hidden',"searchable":false,"orderable" : false
            },
            {
                "className" : "childBox",
                "orderable" : false,
                "data" : null,
                "width" : "60px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data":'code',"searchable":false,"orderable" : false},
            {"data":'name',"searchable":false,"orderable" : false},
            {"data":'desc',"searchable":false,"orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.status==1){
                        return '启用'
                    }else{
                        return '停用'
                    }
                }
            },
            {"data":'authorName',"searchable":false,"orderable" : false},
            {
                "data":null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            {
                "data":null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.updateTime);
                }
            },
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<img class="operate" src="../resources/images/operate.png" onclick="getEditRus(this)"/>');
            $('td', row).eq(9).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            $("#rule_list tbody").delegate( 'tr','click',function(e){
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
    }).draw()
}
//得到待编辑数据的id
function getEditRus(me) {
    var id=me.parentNode.parentNode.childNodes[1].childNodes[0].getAttribute('value');
    updateRus(0,id);
}
//全选功能
function selectAll(me){
    var isChecked=me.getAttribute('isChecked');
    if(isChecked=='false'){
        var inputlist=$("#rule_list tbody input");
        for(var i=0;i<inputlist.length;i++){
            $(inputlist[i]).attr('checked',true);
            me.setAttribute('isChecked','true')
        }
    }else{
        var inputlist=$("#rule_list tbody input:checked");
        for(var i=0;i<inputlist.length;i++){
            $(inputlist[i]).attr('checked',false);
            me.setAttribute('isChecked','false')
        }
    }
}
//添加、编辑规则or评分卡信息
function updateRus(sign,id) {
    var $add_id,save_url,edit_url,update_url;
    if(treeTypeFlag) {
        $add_id = "#add_score_style";
        save_url = "knowledge/scorecard/save";
        edit_url = "knowledge/scorecard/edit";
        update_url = "knowledge/scorecard/update";
    }else {
        $add_id = "#add_rule_style";
        save_url = "knowledge/rule/save";
        edit_url = "knowledge/rule/edit";
        update_url = "knowledge/rule/update";
    }
    //清空输入框数据
    $('#rule_account').val("");
    $('#rule_name').val("");
    $('#Priority').val("");
    $('#rule_describe').val("");
    $("#first_logical_select").val("-1").attr("selected",true);
    $("#inputParameter_one_select").children().remove();
    $("#inputParameter_two_select").children().remove();
    $("#inputParameter_three_select").children().remove();
    $(".add_inputParameter_one_select").children().remove();
    $(".add_inputParameter_two_select").children().remove();
    $(".add_inputParameter_three_select").children().remove();
    $(".ruleInput").val("");
    $(".scoreInput").val("");
    $("#last_logical_select").hide();
    $("#scorecard_code").val("");
    $("#scorecard_name").val("");
    $("#scorecard_desc").val("");
    //清空输入框数据-完

    if(isFirstTerm==0){
        var liLength=$("#TermRuleLi li").size();
        if(liLength==2){
            $("#TermRuleLi").append('<li style="text-align: left;padding-left:2em;width:100%"><span class="iconaddData" onclick="addTermLi(this)"></span><span class="iconclearData" onclick="deleteTermLi(this)"></span><select id ="first_logical_select" onchange="show_logical_select(this)" name="logical"><option value ="-1" selected>空置</option><option value="(">(</option><option value="((">((</option></select>&nbsp;<select id ="inputParameter_one_select" onclick="getInputParameter(this)" id="inputParameter" name="fieldId"></select>&nbsp;<select id ="inputParameter_two_select" name="operator"></select>&nbsp;<select id ="inputParameter_three_select" name="fieldValue"></select></li>');
        }
    }
    if(isFirstOut==0){
        var liLength=$("#OutRuleLi li").size();
        if(liLength==2){
            $("#OutRuleLi").append('<li style="text-align: left;padding-left:2em;width:100%"><span class="iconaddData" onclick="addOutLi(this)"></span><span class="iconclearData"></span><select name="Priority" size="1" style="width: 100px"><option value="0">审批建议</option></select>=<input type="text" style="width: 80px"></li><li style="text-align: left;padding-left:2em;width:100%"><span class="iconaddData" onclick="addOutRuleLi(this)"></span><span class="iconclearData"></span><select name="Priority" size="1" style="width: 100px"><option value="0">信用得分</option></select>=<input type="text" style="width: 80px"></li>');
        }
    }
    if(id && sign==0){
        layer.open({
            type : 1,
            title : '修改',
            maxmin : true,
            shadeClose : false,
            area : [ '600px', '650px' ],
            content : $($add_id),
            btn : [ '保存', '取消' ],
            success : function(index, layero) {
                var param={
                    id:id,
                    engineId:engineId
                };
                Comm.ajaxPost(edit_url,JSON.stringify(param),function(data){
                    console.log(data)
                    var result = data.data;
                    var scorecard = result.scorecard;
                    var rule=result.rule;
                    if(rule){
                        $('input[name="rule_account"]').val(rule.code);
                        $('input[name="rule_name"]').val(rule.name);
                        $('#Priority').val(rule.priority);
                        $('input[name="rule_describe"]').val(rule.desc);
                        $("input[name='score']").val(rule.score);
                        $("#rule_score").val(rule.score);
                        $("#approveSuggest").val(rule.ruleAudit);
                        if(rule.lastLogical){
                            $("#last_logical_select").val(rule.lastLogical).show();
                            if(rule.lastLogical==")"){
                                $("#first_logical_select option[value='(']").attr("selected",true);
                            }
                        }else{
                            $("#last_logical_select").hide();
                        }
                        var ruleFieldList=rule.ruleFieldList;
                        if(ruleFieldList.length>0){
                            for(var i=0;i<ruleFieldList.length;i++){
                                if(ruleFieldList[i].valueType==1){
                                    $("#inputParameter_two_select").append('<option value=">">大于</option><option value="<">小于</option><option value=">=">大于等于</option><option value="<=">小于等于</option><option value="==">等于</option><option value="!=">不等于</option>')
                                }
                                if(ruleFieldList[i].valueType==2){
                                    $("#inputParameter_two_select").append('<option value="like">like</option><option value="not contains">not like</option><option value="==">等于</option><option value="!=">不等于</option>');
                                }
                                if(ruleFieldList[i].valueType==3){
                                    var keyValues=ruleFieldList[i].restrainScope;
                                    if(keyValues){
                                        var restrainScopeArr=keyValues.split(",");
                                        var arr=[];
                                        for(var t=0;t<restrainScopeArr.length;t++){
                                            arr.push(restrainScopeArr[t].split(":"));
                                        }
                                    }
                                    $("#inputParameter_two_select").append('<option value="==">等于</option><option value="!=">不等于</option>');
                                    for(var j=0;j<arr.length;j++){
                                        $("#inputParameter_three_select").append('<option value="'+arr[j][1]+'">'+arr[j][0]+'</option>');
                                    }
                                }
                                if(i==0){

                                    if(ruleFieldList[0].logicalSymbol==""){
                                        $("#TermRuleLi #first_logical_select").val("-1");
                                    }else{
                                        $("#TermRuleLi #first_logical_select").val(ruleFieldList[0].logicalSymbol);
                                    }
                                    $("#inputParameter_one_select").append('<option value="'+ruleFieldList[0].fieldId+'">'+ruleFieldList[0].cnName+'</option>');
                                    $("#inputParameter_two_select").val(ruleFieldList[0].operator);
                                }
                            }
                        }
                    }
                    if(scorecard){
                        $('input[name="scorecard_code"]').val(scorecard.code);
                        $('input[name="scorecard_name"]').val(scorecard.name);
                        $('input[name="scorecard_desc"]').val(scorecard.desc);
                        $("#score_score").val(result.scoreJson.formula);
                        $("#score_PD").val(result.pdJson.formula);
                        $("#score_ODDS").val(result.oddsJson.formula);
                    }
                },"application/json");
            },
            yes : function(index, layero) {
                var paramData,teType,$list;
                if($add_id.indexOf("score")>=0){
                    paramData=confirm_score();
                    teType = 2;
                    $list = "#scorecard_list";
                }else {
                    paramData=confirm_rule();
                    teType = 1;
                    $list="#rule_list";
                }
                paramData.id=id;
                checkRuleIsUse(2,id,teType);
                if(paramData){
                    Comm.ajaxPost(update_url,JSON.stringify(paramData),
                        function(data){
                            layer.closeAll();
                            layer.msg(data.msg,{time:1000},function () {
                                $($list).dataTable().fnDraw(false);
                            });
                        },"application/json"
                    );
                }else{
                    return;
                }
            }
        });
    }else{
        layer.open({
            type : 1,
            title : '新增',
            maxmin : true,
            shadeClose : false,
            area : [ '600px', '650px' ],
            content : $($add_id),
            btn : [ '保存', '取消' ],
            success : function(index, layero) {
                //清除之前操作增加的输入框
                if($add_id.indexOf("score")>=0){
                    var li_size = $("#OutScoreLi li").size();
                    if(li_size>5){
                        for(var i=li_size;i>5;i--){
                            $("#OutScoreLi li:nth-child("+i+")").remove();
                        }
                    }
                }else{
                    var lnone= $("#OutRuleLi li").size();
                    if(lnone>4){
                        for(var i=lnone;i>4;i--){
                            $("#OutRuleLi li:nth-child("+i+")").remove();
                        }
                    }
                    var ln= $("#TermRuleLi li").size();
                    if(ln>3){
                        for(var i=ln;i>3;i--){
                            $("#TermRuleLi li:nth-child("+i+")").remove();
                        }
                    }
                }
            },
            yes : function(index, layero) {
                var paramData;
                if($add_id.indexOf("score")>=0){
                    paramData=confirm_score();
                }else paramData=confirm_rule();
                if(paramData){
                    Comm.ajaxPost(save_url,JSON.stringify(paramData),
                        function(data){
                            layer.closeAll();
                            layer.msg(data.msg,{time:1000},function () {
                                g_userManage.tableUser.ajax.reload();
                            });
                        },"application/json"
                    );
                }else{
                    return;
                }
            }
        });
    }

}
//启用、停用、删除规则或评分卡信息
function updateStatusRus(status){
    var teType,_url,$list,parentId;
    var selectArray;
    if(treeTypeFlag){
        teType = 2;
        _url='knowledge/scorecard/updateStatus';
        selectArray = $("#scorecard_list tbody input:checked");
        $list = "#scorecard_list";
    }
    else {
        teType = 1;
        _url='knowledge/rule/updateStatus';
        selectArray = $("#rule_list tbody input:checked");
        $list = "#rule_list";
    }
    var param={
        status:status,
        idList:null
    };
    if(!selectArray || selectArray.length==0){
        layer.msg("请至少选择一条信息！");
        return;
    }
    var idList = new Array();
    $.each(selectArray,function(i,e){
        var val = $(this).val();
        idList.push(val);
    });
    if(idList.length==0){
        return;
    }
    //不是启用
    if(status!=1)checkRuleIsUse(status,idList,teType);
    if(status==1 && recFlag){//启用回收站里的数据
        param.parentId = 0;
    }
    param.idList=idList;
    var msg = "";
    if(status==1) msg = "是否确定启用？";
    if(status==0) msg = "是否确定停用？";
    if(status==-1) msg = "是否确定删除该信息？";
    layer.confirm(msg,{btn : [ '确定', '取消' ]}, function() {
        Comm.ajaxPost(_url, JSON.stringify(param),function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    $($list).dataTable().fnDraw(false);
                });
            },"application/json"
        );
    });
}
//检查要删除或修改或停用的规则有没有正在被某个决策流使用
function checkRuleIsUse(status,ids,type){
    var getE_url = "knowledge/rule/getEnginesByRuleId";
    var _param = new Object();
    _param.ruleIds = ids;//ruleIds不一定就是规则id，也可是评分卡id
    _param.type = type;//判断是评分卡还是规则集
    if(engineId != null && engineId != ''){
        _param.engineId = engineId;
    }
    var array = new Array();
    if(status == 0 || status == -1 || status == 2) {
        Comm.ajaxPost(getE_url, JSON.stringify(_param),function(data){
            array = data.engineList;
        },"application/json");
        if(type == 1){
            if(status == 0){ //停用
                $(".c-reminder-span").html("停用规则会影响以下引擎")
                $(".c-popup-hint").html("调整以上内容后再停用");
            }
            if(status == -1){  //删除
                $(".c-reminder-span").html("删除规则会影响以下引擎")
                $(".c-popup-hint").html("调整以上内容后再删除");
            }
            if(status == 2){  //编辑
                $(".c-reminder-span").html("修改规则会影响以下引擎")
                $(".c-popup-hint").html("调整以上内容后再进行修改");
            }
        }
        if(type == 2){
            if(status == 0){ //停用
                $(".c-reminder-span").html("停用评分卡会影响以下引擎")
                $(".c-popup-hint").html("调整以上内容后再停用");
            }
            if(status == -1){ //删除
                $(".c-reminder-span").html("删除评分卡会影响以下引擎")
                $(".c-popup-hint").html("调整以上内容后再删除");
            }
            if(status == 2){  //编辑
                $(".c-reminder-span").html("修改评分卡会影响以下引擎")
                $(".c-popup-hint").html("调整以上内容后再进行修改");
            }
        }
        if(array.length > 0){
            $(".c_dialog").show();
            autoCenter($(".c_dialog"));
            var str = "";
            for (var i = 0; i < array.length; i++) {
                str +="";
                str +='<div class="c-popup-redact">'+
                    '<span>'+array[i].name+'</span>'+
                    '<a class="c-popup-a upadte_engine" dataid="'+array[i].id+'" href="#">点击修改</a>'+
                    '</div>';
            }
            $(".c-popup-engine").html('').append(str);
        }
        $(".upadte_engine").click(function(){
            var id = $(this).attr("dataid");
            var _url =_ctx+"/decision_flow?id="+id;
            window.open(_url)
        })
    }
}
//增加条件区域
function addTermLi(me) {
    $('#TermRuleLi').append($('#showAddLi').clone().show());
}
//删除条件区域
function deleteTermLi(me) {
    var targetLi=me.parentNode;
    $(targetLi).remove();
    var liLength=$("#TermRuleLi li").size();
    if(liLength==2){
        isFirstTerm=0;
    }
}
//增加输出区域
function addOutLi(me) {
    var outDiv = '<li style="text-align: left;padding-left:2em;width:100%;" class="c-optional-rules">'+
        '<span class="iconaddData" onclick="addOutLi(this)"></span>'+
        '<span class="iconclearData" onclick="deleteOutLi(this)"></span>'+
        '<select name="fieldId" size="1" style="width: 100px" onclick="getInputParameter(this,1)">'+
        '</select>'+
        '=<select name="fieldValue" size="1" style="width: 100px">'+
        '</select>';
    if(treeTypeFlag){
        outDiv += '<input type="text" style="width: 300px;display: none" class="scoreInput" value="" intervaljson="" onclick="setCursor(this.id)"></li>';
        $('#OutScoreLi').append(outDiv);
    }else {
        outDiv += '<input type="text" style="display: none" class="ruleInput" value="" name="fieldValue"></li>';
        $('#OutRuleLi').append(outDiv);
    }
}
//删除输出区域
function deleteOutLi(me) {
    var targetLi=me.parentNode;
    $(targetLi).remove();
    var liLength=$("#OutRuleLi li").size();
    if(liLength==2){
        isFirstOut=0;
    }
}
//输入参数 获取参数列表
function getInputParameter(me,sing){
    var layerOne=layer.open({
        type : 1,
        title : '输入参数',
        maxmin : true,
        shadeClose : false,
        area : [ '750px', '680px' ],
        content : $('#input_parameter_style'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            layer.close(layerOne);
            var showFieldsPlace=$("#showFieldsPlace").val().split("/");
            var valueType=$("#hiddenFields").html();
            var enName=$("#enName").html();
            $(me).children().remove();
            $(me).next().children().remove();
            $(me).next().next().children().remove();
            $(me).append("<option value='"+showFieldsPlace[0]+"|"+enName+"'>"+showFieldsPlace[1]+"</option>");
            if(sing==0){
                if(valueType==1){//数值
                    $(me).next().append('<option value=">">大于</option><option value="<">小于</option><option value=">=">大于等于</option><option value="<=">小于等于</option><option value="==">等于</option><option value="!=">不等于</option>');
                }else if(valueType==2){//字符
                    $(me).next().append('<option value="like">like</option><option value="not contains">not like</option><option value="==">等于</option><option value="!=">不等于</option>');
                }else if(valueType==3){//枚举
                    var restrainScope=$("#hiddenRestrainScope").html();
                    if(restrainScope){
                        var restrainScopeArr=restrainScope.split(",");
                        var arr=[];
                        for(var i=0;i<restrainScopeArr.length;i++){
                            arr.push(restrainScopeArr[i].split(":"));
                        }
                    }
                    $(me).next().append('<option value="==">等于</option><option value="!=">不等于</option>');
                    for(var j=0;j<restrainScopeArr.length;j++){
                        $(me).next().next().append('<option value="'+arr[j][1]+'">'+arr[j][0]+'</option>');
                    }
                }
            }else if(sing==1){
                if(valueType==3){//枚举
                    $(me).next().show();
                    $(me).next().next().hide();
                    var restrainScope=$("#hiddenRestrainScope").html();
                    if(restrainScope){
                        var restrainScopeArr=restrainScope.split(",");
                        var arr=[];
                        for(var i=0;i<restrainScopeArr.length;i++){
                            arr.push(restrainScopeArr[i].split(":"));
                        }
                    }
                    for(var j=0;j<restrainScopeArr.length;j++){
                        $(me).next().append('<option value="'+arr[j][1]+'">'+arr[j][0]+'</option>');
                    }
                }else{
                    $(me).next().hide();
                    $(me).next().next().show();
                }
            }
        },
        success : function(index, layero) {
            getUrlsTableFields();
        }
    });
}
//查询方法
function paramSearch() {
    g_userManage.fieldSearch = true;
    g_userManage.tableField.ajax.reload();
}
//查询重置方法
function paramSearchReset() {
    $('input[name="Parameter_search"]').val("");
    g_userManage.fieldSearch = false;
    g_userManage.tableField.ajax.reload();
}
//添加修改里的字段列表数据
function getUrlsTableFields(){
    if(isFirstRuleFields==1){
        g_userManage.tableField = $('#Parameter_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('knowledge/rule/getFieldList',JSON.stringify(queryFilter),function(result){
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
                    'data':'valueType',
                    'class':'hidden',"searchable":false,"orderable" : false
                },
                {
                    "className" : "childBox",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<input type="checkbox" value="'+data.id+'" valueType="'+data.valueType+'" restrainScope="'+data.restrainScope+'" enName="'+data.enName+'" style="cursor:pointer;" isChecked="false">'
                    }
                },
                {"data": "cnName","orderable" : false,"searchable":false,},
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#Parameter_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#Parameter_list tbody input:checked");
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
                        var fieldsId= $(input).val();
                        var fieldsHtml=$(input).parent().next().html();
                        $("#hiddenFields").html($(input).attr('valueType'));
                        $("#hiddenRestrainScope").html($(input).attr('restrainScope'));
                        $("#enName").html($(input).attr('enName'));
                        $("#showFields").html(fieldsId+'/'+fieldsHtml);
                        $("#showFieldsPlace").val(fieldsId+'/'+fieldsHtml);
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableField.on("order.dt search.dt", function() {
            g_userManage.tableField.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        isFirstRuleFields=0;
    }else{
        g_userManage.tableField.ajax.reload();
        $("#showFields").html('/');
    }
}
//添加规则-条件区域-下拉框变化
function show_logical_select(me){
    var val=$(me).val();
    if(val=="("){
        $("#last_logical_select").show();
        $("#last_logical_select").val(val);
    }else if(val=="(("){
        $("#last_logical_select").show();
        $("#last_logical_select").val(val);
    }else{
        $("#last_logical_select").hide();
    }
}
//添加修改规则的数据
function confirm_rule(){
    $("input[name = 'fieldContent']").val(getJsonArray("#TermRuleLi .c-optional-rules"));
    $("input[name = 'outcontent']").val(getJsonArray("#OutRuleLi .c-optional-rules"));
    $("input[name = 'score']").val($("#rule_score").val());//信用得分
    $("input[name = 'ruleAudit']").val($("#approveSuggest option:selected").val());
    if(!$("#last_logical_select").is(":hidden")){
        $("input[name = 'lastLogical']").val($("#last_logical_select option:selected").val());
    }else{
        $("input[name = 'lastLogical']").val("");
    }
    $("input[name='parentId']").val(parentId);
    $("input[name='parentIds']").val(parentIds);
    var rules=getRuleArray("#TermRuleLi .c-optional-rules","1");
    var outputrules=getRuleArray("#OutRuleLi .c-optional-rules","2");
    var code=$("input[name = 'rule_account']").val();
    var salience =$("select[name = 'Priority']").val();
    var content= getRules(rules,outputrules,code,salience);
    $("input[name = 'content']").val(content);

    var rule={};
    if($('input[name="rule_account"]').val()==""){
        layer.msg("规则代码不能为空",{time:2000});
        return false;
    }
    if($('input[name="rule_name"]').val()==""){
        layer.msg("规则名称不能为空",{time:2000});
        return false;
    }
    if($('input[name="rule_describe"]').val()==""){
        layer.msg("规则描述不能为空",{time:2000});
        return false;
    }
    if($('#Priority').val()==""){
        layer.msg("规则优先级不能为空",{time:2000});
        return false;
    }
    rule.code=$('input[name="rule_account"]').val();//规则代码
    rule.name=$('input[name="rule_name"]').val();////规则名称
    rule.desc=$('input[name="rule_describe"]').val();//规则描述
    rule.engineId=$('input[name="eid"]').val();
    rule.priority=$('#Priority').val();//规则优先级
    rule.parentId=parentId;//父节点id
    rule.content=content;//规则具体内容
    rule.ruleAudit=$("#approveSuggest").val();//审批建议
    rule.score=$("#rule_score").val();//得分
    if($("#last_logical_select").css("display")!="none"){
        rule.lastLogical=$("#last_logical_select").val();//逻辑关系符
    }else{
        rule.lastLogical=null;//逻辑关系符
    }
    rule.fieldContent=$("input[name = 'fieldContent']").val();
    return rule;
}
function getJsonArray(obj){
    var ln=$(obj).length;
    if(ln>0){
        var str="[";
        var content="";
        for(var i=0;i<ln;i++){
            var namelist=$($(obj)[i]).find('select,input');
            for(var j=0;j<namelist.length;j++){
                var name=$(namelist)[j].getAttribute("name");
                var value=$($(namelist)[j]).val();
                if(name&&value){
                    content+= "\""+name+"\""+":"+"\""+value+"\""+",";
                }
            }
        }
        str=str+"{"+content.substring(0,content.length - 1)+"}]";
        return str;
    }

}
function getRuleArray(obj,type){
    var rule="";
    $(obj).each(function(index,element){
        var valueType = $("#hiddenFields").html();
        var content  = $(this).find('select,input').serializeJson();
        if(content.fieldId){
            if(type=="1"){
                if(content.logical!='-1'){
                    rule=rule+" "+ content.logical;
                }
                if(valueType == '2'){
                    if(content.fieldId.indexOf("|")!=-1){
                        rule=rule+" this[\'"+content.fieldId.split("|")[1]+"\'] " +content.operator+" \""+content.fieldValue +"\" ";
                    }else{
                        rule=rule+" this[\'"+content.fieldId+"\'] " +content.operator+" \""+content.fieldValue +"\" ";
                    }
                }else{
                    if(content.fieldId.indexOf("|")!=-1){
                        rule=rule+" this[\'"+content.fieldId.split("|")[1]+"\'] " +content.operator+" "+content.fieldValue +" ";
                    }else{
                        rule=rule+" this[\'"+content.fieldId+"\'] " +content.operator+" "+content.fieldValue +" ";
                    }
                }

            }else{
                if(content.fieldId.indexOf("|")!=-1){
                    rule += "map.put(\""+content.fieldId.split("|")[1]+"\",\""+content.fieldValue+"\"); \r\n\t ";
                }else{
                    rule += "map.put(\""+content.fieldId+"\",\""+content.fieldValue+"\"); \r\n\t ";
                }

            }
        }
    })
    return rule;
}
function getRules(rule,outputparam,code,salience){
    var ruleAudit=$("select[name='ruleAudit']").val();
    var type=1;
    var code=$("input[name = 'rule_account']").val();
    var name=$("input[name='rule_name']").val();
    if(ruleAudit=='2'){
        type='2';
    }
    var fieldcode="";
    var fieldvalue=$("#scoreValue").val();
    var enginerule = "package com.shuzun.apollo.drools\\r\\n";
    enginerule += "import java.util.Map;\\r\\n";
    enginerule += "import java.util.List;\\r\\n";
    enginerule +="import java.util.ArrayList;\\r\\n";
    enginerule +="import java.util.HashMap;\r\n";
    enginerule += "import com.shuzun.apollo.engine.model.InputParam;\\r\\n";
    enginerule += "import com.shuzun.apollo.engine.model.Result;\\r\\n";
    enginerule += "import com.shuzun.apollo.engine.model.Rule;\\r\\n";
    enginerule += "rule \""+code+"\"\\r\\n";
    enginerule += "salience "+salience+"\\r\\n";
    enginerule += "\\twhen\\r\\n";
    enginerule += "\\t$inputParam : InputParam();\\r\\n";
    enginerule +="Map("+rule+")   from $inputParam.inputParam;";
    enginerule += "\\tthen\\r\\n";
    enginerule += "\\t List<Result>  resultList =$inputParam.getResult();\\r\\n";
    enginerule += "\\t Result result =new Result(); \\r\\n";
    enginerule += "\\t result.setResultType(\""+type+"\"); \\r\\n";
    enginerule += "\\t result.setCode(\""+code+"\"); \\r\\n";
    enginerule += "\\t Map<String, Object> map =new HashMap<>(); \\r\\n";

    if(typeof(fieldvalue)!="undefined"&&null!=fieldvalue&&""!=fieldvalue){

        enginerule += "\\t map.put(\"score\","+fieldvalue+"); \\r\\n";

    }

    enginerule +=outputparam;
    enginerule += "\\t result.setMap(map); \\r\\n";
    enginerule+= " resultList.add(result); \r\n\t"
    enginerule +="\\t $inputParam.setResult(resultList); \\r\\n";
    enginerule += "end\\r\\n";
    return enginerule

}
//添加修改评分卡的数据
function confirm_score(){
    if($('input[name="scorecard_code"]').val()==""){
        layer.msg("评分卡代码不能为空",{time:2000});
        return false;
    }
    if($('input[name="scorecard_name"]').val()==""){
        layer.msg("评分卡名称不能为空",{time:2000});
        return false;
    }
    if($('input[name="scorecard_desc"]').val()==""){
        layer.msg("评分卡描述不能为空",{time:2000});
        return false;
    }
    var str="";
    if($("#OutScoreLi .c-optional-rules").length > 3){
        str +="["
        $("#OutScoreLi .c-optional-rules:gt(2)").each(function(index,element){
            str += getFormulaJson(element);
        })
        str = str.substring(0,str.length -1);
        str = str +"]"
    }
    var scorecard={};
    var score = getFormulaJson($("#OutScoreLi .c-optional-rules:eq(0)"));
    var pd = getFormulaJson($("#OutScoreLi .c-optional-rules:eq(1)"));
    var odds = getFormulaJson($("#OutScoreLi .c-optional-rules:eq(2)"));

    if($("#score_score").val()!=''){
        scorecard.score = score.substring(0,score.length -1)
    }
    if($("#score_PD").val()!=''){
        scorecard.pd = pd.substring(0,pd.length -1)
    }
    if($("#score_ODDS").val()!=''){
        scorecard.odds = odds.substring(0,odds.length -1)
    }

    $("input[name='score']").val($("#score_score").val());//信用得分
    $("input[name='parentId']").val(parentId);
    $("input[name='parentIds']").val(parentIds);

    scorecard.code=$('input[name="scorecard_code"]').val();
    scorecard.name=$('input[name="scorecard_name"]').val();
    scorecard.desc=$('input[name="scorecard_desc"]').val();
    //scorecard.version =$("select[name=version] option:selected").val();//未找到在哪取值
    scorecard.content = str;
    scorecard.engineId=$('input[name="eid"]').val();
    scorecard.parentId=parentId;//父节点id
    return scorecard;
}
function getFormulaJson(element){
    var str ="";
    str += '{"output":{"field_id":'+$(element).find("select[name=fieldId] option:selected").attr("dataid")
    str += ',"field_code":"'+$(element).find("select[name=fieldId] option:selected").val();
    str += '","field_name":"'+$(element).find("select[name=fieldId] option:selected").text();
    str += '","field_type":'+$(element).find("select[name=fieldId] option:selected").attr("valueType");
    str += '},';
    str += '"formula":"' +replaceField($(element).find(".scoreInput").val().replace(" ",""));
    str += '","formula_show":"'+$(element).find(".scoreInput").val().replace(" ","");
    str += '","fields":['+$(element).find(".scoreInput").attr("intervaljson")+']';
    str += '},';
    return str;
}
function replaceField(str){
    var reg=/@[a-zA-Z0-9_\u4e00-\u9fa5()（）-]+@/;
    var patt = new RegExp(reg, 'g');
    var arr0=str.match(patt);
    if(arr0 !=null && arr0.length>0){
        for (var i=0;i<arr0.length;i++) {
            str=str.replace(reg,map.get(arr0[i]));
        }
    }
    return str;
}
/***********************************************公式编辑*******************************************************/
//获取光标位置
(function($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if ('selectionStart' in el) {
            pos = el.selectionStart;
        } else if ('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }
})(jQuery);
var dataId,cursor,cursorId;
//获取光标位置
function setCursor(id) {
    cursorId=id;
    cursor=$("#"+id);
    cursor.focus();
}
//点击公式方法
function character(sign){
    if (cursor!=null){
        dataId=$(sign).attr("dataId");
        var strIndex=cursor.getCursorPosition();
        var content=cursor.val();
        var str1=content.slice(0,strIndex);
        var str2=content.slice(strIndex,content.length);
        //var txtFocus = cursor;
        var txtFocus = document.getElementById(cursorId);
        switch (dataId) {
            case '0': str1=str1+'+'; break;
            case '1': str1=str1+'-'; break;
            case '2': str1=str1+'*'; break;
            case '3': str1=str1+'/'; break;
            case '4': str1=str1+'sqrt()'; break;
            case '5': str1=str1+'ln()'; break;
            case '6': str1=str1+'avg()'; break;
            case '7': str1=str1+'()'; break;
            case '8': str1=str1+'abs()'; break;
            case '9': str1=str1+'max()'; break;
            case '10': str1=str1+'min()'; break;
            case '11': str1=str1+'lg()'; break;
            case '12': str1=str1+'exp()'; break;
            case '13': str1=str1+'ceil()'; break;
            case '14': str1=str1+'floor()'; break;
            case '15': str1=str1+'PI'; break;
            default:break;
        }
        var newContent=str1+str2;
        cursor.val(newContent);
        if(dataId==7){
            var position=str1.length-1;
            txtFocus.setSelectionRange(position,position);//设置光标位置，不适用IE
            cursor.focus();
        }else{
            var position=str1.length;
            txtFocus.setSelectionRange(position,position);
            cursor.focus();
        }
    }
};
/********************************************* @输出出现下拉框 ************************************************/
var gobal_inputor;
$(function(){
    var paramData = {
        engineId:engineId,
        isOutput:0
    }
    var array = new Array();
    Comm.ajaxPost("datamanage/field/getEngineFields",paramData,function(result){
        field_isInput_array = result.data;
        for (var i = 0; i < field_isInput_array.length; i++) {
            var key = "\@"+field_isInput_array[i].field.cnName+"\@";
            var value = "\#{"+field_isInput_array[i].field.enName+"\}";
            map.put(key,value);
            array.push(field_isInput_array[i].field.cnName);
        };
    })
    $.fn.atwho.debug = true;
    var at_config = {
        at: "@",
        data: array,
        headerTpl: '<div class="atwho-header">Field List<small>↑&nbsp;↓&nbsp;</small></div>',
        limit: 200
    }
    $inputor = $('.scoreInput').atwho(at_config);
    $(document).on("focus",".scoreInput",function() {
        $(".scoreInput").removeAttr("id");
        $(this).attr("id","inputor");
        gobal_inputor = $(this);
        $inputor = $('#inputor').atwho(at_config);
    });
});
