//页面初始化datatable赋值
$(function () {
    g_userManage.tableUser = $('#Data_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('datamanage/listmanage/list', JSON.stringify(queryFilter), function (result) {
                var returnData = {};
                var resData = result.data.pageInfo;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            }, "application/json")
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {
                'data':'id',
                'class': 'hidden', "searchable": false, "orderable": false
            },
            {
                "className": "childBox",
                "orderable": false,
                "data": null,
                "width": "60px",
                "searchable": false,
                "render": function (data, type, row, meta) {
                    return '<input type="checkbox"  value="' + data.id + '" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "listName", "orderable": false, "width": "150px"},
            {"data": "queryFieldCn", "orderable": false, "width": "250px"},
            {
                'data':'queryFieldCn',
                'class': 'hidden', "searchable": false, "orderable": false
            },
            {"data": "listDesc", "orderable": false, "width": "150px"},
            {
                "data": null,
                "searchable": false,
                "orderable": false,
                "width": "100px",
                "render": function (data, type, row, meta) {
                    if (data.dataSource == 1) {
                        return '外部'
                    } else if (data.dataSource == 2) {
                        return '内部'
                    } else if (data.dataSource == 0) {
                        return '待选'
                    }
                }
            },
            {
                "data": null,
                "searchable": false,
                "orderable": false,
                "width": "100px",
                "render": function (data, type, row, meta) {
                    if (data.status == 1) {
                        return '启用'
                    } else if (data.status == 0) {
                        return '停用'
                    } else if (data.status == -1) {
                        return '删除'
                    }
                }
            },
            {"data": "nickName", "orderable": false, "width": "200px"},
            {
                "data": null, "orderable": false, "width": "200px",
                "render": function (data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            {
                "data": "null", "orderable": false, "width": "100px",
                "render": function (data, type, row, meta) {
                    var img='<img class="operate" src="' + _ctx + '/resources/images/operate.png"/>';
                    return img;
                }
            },
        ],
        "createdRow": function (row, data, index, settings, json) {
        },
        "initComplete": function (settings, json) {
            contentChange(); //点击东西过长显示省略号
            //操作
            $("#Data_list").on("click",".operate",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                addORdetail(0,id);
            });
            //单选行选择
            $("#Data_list tbody").delegate( 'tr','click',function(e){
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
            //双击进入详情页面
            $("#Data_list tbody").on("dblclick",'tr',function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                layer.open({
                    type: 2,
                    title:'详情页面',
                    area: ['100%', '100%'],
                    offset: '0px',
                    fix: true, //不固定
                    maxmin: false,
                    content: 'whiteDetail?listDbId='+id+'&listType=b',
                });
            })
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function () {
        g_userManage.tableUser.column(0, {
            search: "applied",
            order: "applied"
        }).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});
var g_userManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.searchKey = $("#search").val();
            /* param.roleName = $("#name").val();*/
        }
        param.listType = "b";
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;

        paramFilter.page = page;

        return paramFilter;
    }
};
//点击东西过长显示省略号
function contentChange(){
    //点击东西过长显示省略号
    var trlist=$("#Data_list tbody tr");
    if(trlist.length>=1&&($(trlist).children("td").html()!=="表中数据为空")){
        for(var i=0;i<trlist.length;i++){
            var html=$(trlist[i]).children(".childBox").next().next().html();
            var htmlLength=html.length;
            if(htmlLength>=20){
                html=html.substring(0,20)+"...";
                $(trlist[i]).children(".childBox").next().next().html(html);
                $(trlist[i]).children(".childBox").next().next().mouseenter(function(){
                    $(this).html($(this).next().html());
                })
                $(trlist[i]).children(".childBox").next().next().mouseleave(function(){
                    var trlist=$("#Data_list tbody tr");
                    for(var i=0;i<trlist.length;i++){
                        var html=$(trlist[i]).children(".childBox").next().next().html();
                        var htmlLength=html.length;
                        if(htmlLength>=20){
                            html=html.substring(0,20)+"...";
                            $(trlist[i]).children(".childBox").next().next().html(html);
                        }
                    }
                })
            }
        }
    }
}
//删除全部
function selectAll(me) {
    var isChecked = me.getAttribute('isChecked');
    if (isChecked == 'false') {
        var inputlist = $("#Data_list tbody input");
        for (var i = 0; i < inputlist.length; i++) {
            $(inputlist[i]).attr('checked', true);
            me.setAttribute('isChecked', 'true')
        }
    } else {
        var inputlist = $("#Data_list tbody input:checked");
        for (var i = 0; i < inputlist.length; i++) {
            $(inputlist[i]).attr('checked', false);
            me.setAttribute('isChecked', 'false')
        }
    }
}
//删除某一项
function deleteBlack() {
    var selectArray = $("#Data_list tbody input:checked");
    if (!selectArray || (selectArray.length <= 0)) {
        layer.msg("请选择一个用户", {time: 2000});
        return;
    }
    var param = {
        Ids: null,
        status: null
    }
    var roleIds = new Array();
    $.each(selectArray, function (i, e) {
        var val = $(this).val();
        roleIds.push(val);
    });
    if (roleIds.length == 0) {
        return;
    }
    param.Ids = roleIds;
    param.status = -1;
    layer.confirm('是否删除黑名单？', {
        btn: ['确定', '取消']
    }, function () {
        Comm.ajaxPost(
            'datamanage/listmanage/updateStatus', JSON.stringify(param),
            function (data) {
                layer.msg(data.msg, {time: 2000}, function () {
                    layer.closeAll();
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableUser.ajax.reload(function(){
                        contentChange(); //点击东西过长显示省略号
                    });
                });
            }, "application/json"
        );
    });
}
//启用或停止
function updateState(state) {
    var selectArray = $("#Data_list tbody input:checked");
    if (!selectArray || (selectArray.length <= 0)) {
        layer.msg("请选择一个用户", {time: 2000});
        return;
    }
    var param = {
        Ids: null,
        status: null
    }
    var roleIds = new Array();
    $.each(selectArray, function (i, e) {
        var val = $(this).val();
        roleIds.push(val);
    });
    if (roleIds.length == 0) {
        return;
    }

    param.Ids = roleIds;
    param.status = state;
    Comm.ajaxPost(
        'datamanage/listmanage/updateStatus', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    contentChange();//点击东西过长显示省略号
                });
            });
        }, "application/json"
    );

}

//编辑和添加
function addORdetail(sign,id) {
    var id=id;
    //编辑和添加之前先清空数据
    $("#listName").val("");
    $("#dataSource").val("0");
    $("#listAttr").val("");
    $("#listDesc").val("");
    $("#maybe").attr("checked",false);
    $("#draw").attr("checked",false);
    $("#dim").attr("checked",false);
    $("#precise").attr("checked",false);
    $("#maintenIncre").html("<span class='blackadd' onclick='whiteLayer()'></span>");
    $("#searchIncre").html("<span class='whiteadd' onclick='whitesubLayer()'></span>");
    if (sign == 0) {//编辑
        var id=id;
        var param = {
            id: null,
            listType: null
        };
        param.id = id;
        param.listType = "b";//名单类型
        Comm.ajaxPost(
            'datamanage/listmanage/edit', JSON.stringify(param),
            function (data) {
                if(data.code==0){
                    layer.closeAll();
                    var listDb = data.data.listDb;//列表字段
                    var fieldList=data.data.fieldList;
                    var queryFieldList=data.data.queryFieldList;
                    var editSave=layer.open({
                        type : 1,
                        title : "编辑用户",
                        maxmin : true,
                        shadeClose : true, //点击遮罩关闭层
                        area : [ '600px', '' ],
                        offset: '50px',
                        content : $('#Add_data_style'),
                        yes: function (index, layero) {
                            save(id,editSave);
                        }
                    });
                    $("#queryFilde").val(listDb.queryField);
                    $("#listName").val(listDb.listName);
                    $("#dataSource").val(listDb.dataSource);
                    $("#listAttr").val(listDb.listAttribute);
                    $("#listDesc").val(listDb.listDesc);
                    if(listDb.queryType==0){
                        $("#maybe").attr("checked",true);
                    }else{
                        $("#draw").attr("checked",true);
                    }
                    if(listDb.matchType==0){
                        $("#dim").attr("checked",true);
                    }else{
                        $("#precise").attr("checked",true);
                    }
                    var html="";
                    for(var i=0;i<fieldList.length;i++){
                        html+="<b class='queryStyle'>"+fieldList[i].cnName+"</b><input type='hidden' value='"+fieldList[i].id+"'/>";
                    }
                    $("#maintenIncre").html(html);
                    var keyhtml="";
                    for(var i=0;i<queryFieldList.length;i++){
                        keyhtml+="<b class='queryStyle'>"+queryFieldList[i].cnName+"</b><input type='hidden' value='"+queryFieldList[i].id+"'/>";
                    }
                    $("#searchIncre").html(keyhtml);
                }else{
                    layer.msg(data.msg,{time:1000});
                }
            }, "application/json"
        );
    } else if (sign == 1) {//添加
        var addVal=layer.open({
            type: 1,
            title: '添加黑名单',
            maxmin: true,
            shadeClose: true,
            area: ['600px', ''],
            offset: '80px',
            content: $('#Add_data_style'),
            /* btn: ['保存', '取消'],*/
            yes: function (index, layero) {
                var queryType=$('input[name="queryType"]:checked').val();
                var matchType=$('input[name="matchType"]:checked').val();
                //维护字段
                var imputVal=$(".valName input");
                var maintenance=""
                for(var i=0;i<imputVal.length;i++){
                    if(imputVal[i].value==""){
                        maintenance+=imputVal[i].value
                    }else{
                        maintenance+=imputVal[i].value+",";
                    }
                }
                maintenance=maintenance.substr(0,maintenance.length-1);
                //查询主键
                var affliinputVal=$(".afflivalName input");
                var queryField=""
                for(var i=0;i<affliinputVal.length;i++){
                    if(imputVal[i].value==""){
                        queryField+=imputVal[i].value;
                    }else{
                        queryField+=imputVal[i].value+",";
                    }
                }
                queryField=queryField.substr(0,queryField.length-1);
                var role = {
                    listType:null,
                    listName:null,
                    dataSource:null,
                    listAttribute:null,
                    listDesc:null,
                    queryType:null,
                    maintenance:null,
                    queryField:null,
                    matchType:null,
                    status:1
                };
                role.listType = 'b';
                role.listName=$("#listName").val();
                role.dataSource=$("#dataSource").val();
                role.listAttribute=$("#listAttr").val();
                role.listDesc=$("#listDesc").val();
                role.queryType=queryType;
                role.maintenance=maintenance;
                role.queryField=queryField;
                role.matchType=matchType;
                if($("#listName").val()==""){
                    layer.msg("名单名称不能为空",{time: 1000});
                    return;
                }
                if(maintenance==""){
                    layer.msg("维护字段不能为空",{time: 1000});
                    return;
                }
                if(queryField==""){
                    layer.msg("查询主键不能为空",{time: 1000});
                    return;
                }
                if($('input[name="matchType"]:checked').length==0){
                    layer.msg("匹配模式至少选一个",{time: 2000});
                    return;
                }
                if($('input[name="queryType"]:checked').length==0){
                    layer.msg("and or至少选一个",{time: 2000});
                    return;
                }
                Comm.ajaxPost(
                    'datamanage/listmanage/add', JSON.stringify(role),
                    function (data) {
                        if(data.code==0){
                            layer.msg(data.msg,{time:1000},function(){
                                layer.close(addVal);
                                g_userManage.tableUser.ajax.reload(function(){
                                    contentChange();//点击东西过长显示省略号
                                });
                            });
                        }else{
                            layer.msg(data.msg,{time:1000});
                        }
                    }, "application/json"
                );
            }
        });
    }
}
//编辑修改后赋值
function save(id,editSave){
    var role ={};
    role.listType = "b";
    role.id = id;
    //维护字段
    var imputVal=$("#maintenIncre input");
    var maintenance=""
    for(var i=0;i<imputVal.length;i++){
        if(imputVal[i].value==""){
            maintenance+=imputVal[i].value
        }else{
            maintenance+=imputVal[i].value+",";
        }
    }
    maintenance=maintenance.substr(0,maintenance.length-1);
    //查询主键
    var affliinputVal=$("#searchIncre input");
    var queryField=""
    for(var i=0;i<affliinputVal.length;i++){
        if(imputVal[i].value==""){
            queryField+=imputVal[i].value;
        }else{
            queryField+=imputVal[i].value+",";
        }
    }
    var queryType=$('input[name="queryType"]:checked').val();
    var matchType=$('input[name="matchType"]:checked').val();
    queryField=queryField.substr(0,queryField.length-1);
    role.listName = $("#listName").val();//白名单名称
    role.dataSource =  $("#dataSource").val();//数据来源
    role.listAttribute =  $("#listAttr").val();//名单类型
    role.listDesc =  $("#listDesc").val();//名单描述
    role.maintenance=maintenance;//维护字段
    role.queryField=queryField;//查询主键
    role.queryType = queryType;
    role.matchType = matchType;


    role.queryField =  $("#queryFilde").val();
    if($("#listName").val()==""){
        layer.msg("名单名称不能为空",{time: 1000});
        return;
    }
    if($('input[name="matchType"]:checked').length==0){
        layer.msg("匹配模式至少选一个",{time: 2000});
        return;
    }
    if($('input[name="queryType"]:checked').length==0){
        layer.msg("and or至少选一个",{time: 2000});
        return;
    }
    Comm.ajaxPost('datamanage/listmanage/update',JSON.stringify(role),function(data){
        if(data.code==0){
            layer.msg(data.msg, {time: 1000}, function () {
                layer.close(editSave);
                g_userManage.tableUser.ajax.reload(function(){
                    contentChange();//点击东西过长显示省略号
                });
            });
        }else{
            layer.msg(data.msg,{time:1000});
        }
    },"application/json")
}

//点+号之后的datatable
function addTable(){
    var g_userManage = {
        tableUser: null,
        currentItem: null,
        fuzzySearch: false,
        getQueryCondition: function (data) {
            var paramFilter = {};
            var page = {};
            var param = {};

            //自行处理查询参数
            param.fuzzySearch = g_userManage.fuzzySearch;
            if (g_userManage.fuzzySearch) {
                param.searchKey = $("[name='searchKey']").val();
            }
            param.listType = "b";
            paramFilter.param = param;

            page.firstIndex = data.start == null ? 0 : data.start;
            page.pageSize = data.length == null ? 10 : data.length;

            paramFilter.page = page;

            return paramFilter;
        }
    };
    g_userManage.tableUser = $('#Param_list').dataTable($.extend({
        'iDeferLoading': true,
        "bAutoWidth": false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('datamanage/listmanage/fieldList', JSON.stringify(queryFilter), function (result) {
                var returnData = {};
                var resData = result.data;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            }, "application/json")
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {
                'data':'id',
                'class': 'hidden', "searchable": false, "orderable": false
            },
            {
                "className": "childBox",
                "orderable": false,
                "data": null,
                "width": "60px",
                "searchable": false,
                "render": function (data, type, row, meta) {
                    return '<input type="radio" class="addField" name="chooseRadio" value="' + data.id + '" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "cnName", "orderable": false, "width": "180px"}
        ],
        "createdRow": function (row, data, index, settings, json) {
        },
        "initComplete": function (settings, json) {
            $(".search_btn").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    contentChange();//点击东西过长显示省略号
                });
            });
            /* $("#Param_list").on("click",".addField",function(e){
             var target = e.target||window.event.target;
             var id = $(target).parents("tr").children().eq(1).children("input").val();
             var val = $(target).parents("tr").children().eq(2).html();
             $("#showCode").html(id);
             $("#showName").html(val);
             });*/

            //单选行选择并赋值
            $("#Param_list tbody").delegate( 'tr','click',function(e){
                var target=e.target;
                if(target.nodeName=='TD'){
                    var input=target.parentNode.children[1].children[0];
                    var isChecked=$(input).attr('isChecked');
                    if(isChecked=='false'){
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                            var id = $(target).parents("tr").children().eq(1).children("input").val();
                            var val = $(target).parents("tr").children().eq(2).html();
                            $("#showCode").html(id);
                            $("#showName").html(val);
                        }
                        $(input).attr('isChecked','true');
                    }else{
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);

                        }else{
                            $(input).attr('checked','checked');
                            var id = $(target).parents("tr").children().eq(1).children("input").val();
                            var val = $(target).parents("tr").children().eq(2).html();
                            $("#showCode").html(id);
                            $("#showName").html(val);
                        }
                        $(input).attr('isChecked','false');
                    }
                }
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function () {
        g_userManage.tableUser.column(0, {
            search: "applied",
            order: "applied"
        }).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
}
//维护字段的layer弹框
var showTable=false;
function whiteLayer(){
    var valName=$("#showName").html();
    var index=layer.open({
        type : 1,
        title : "输入参数",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '600px', '700px' ],
        content: $('#Add_mainSea_style'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            $('input[name="chooseRadio"]').attr("checked",false);
            $("#showCode").html("");
            $("#showName").html("");
            if(showTable==false){
                addTable();
                showTable=true;
            }else{
                $("#Param_list").show();
            }

            $("#maintenIncre").on("click",".deleteField",function(e){
                var target = e.target||window.event.target;
                var val=$(target).prev().val();
                $(target).parent().css("display","none");
                $(target).parent().children("b").html("");
                $(target).parent().children("img").attr("src","");
                $(target).parent().children("input").val("");
                $("#searchIncre").find($('input[value="'+val+'"]')).parent().children("b").html("");
                $("#searchIncre").find($('input[value="'+val+'"]')).parent().children("img").attr("src","");
                $("#searchIncre").find($('input[value="'+val+'"]')).parent().children("input").val("");
                $("#searchIncre").find($('input[value="'+val+'"]')).parent().css("display","none");
                $("#affliValue").find($('input[value="'+val+'"]')).parent().css("display","none").css("margin","0px");
            })
        },
        yes : function(index, layero) {
            var showCode=$("#showCode").html();
            var length = $('#maintenIncre input[value="'+showCode+'"]').length;
            var valName=$("#showName").html();
            if(valName==""){
                layer.close(index);
            }else{
                if(length>=1){
                    layer.msg("选择项重复，请重新选择一个",{time: 2000});
                }else{
                    var valName=$("#showName").html();
                    var showCode=$("#showCode").html();
                    if(valName==""){
                        $(".deleteField").attr("src","");
                        $(".deleteField").parent().css("display","none");
                    }
                    $("<p class='valName'><b class='mainVal'>"+valName+"</b><input type='hidden'  value='" +showCode + "'/><img src='" + _ctx + "/resources/images/blackClear.png' class='deleteField'/></p>").insertBefore($(".blackadd"));
                    layer.close(index);
                }
            }





        },
        cancel: function(index, layero){
            layer.close(index)
        },
    });
}
//查询主键的layer弹框
function whitesubLayer(){
    var index=layer.open({
        type : 1,
        title : "输入参数",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '600px', '300px' ],
        content: $('#Add_affliSea_style'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            $("#chooseCode").html("");
            $("#chooseVal").html("");
            var mainValhtml=$(".valName b");
            var showCodeHtml=$(".valName input");
            var html="";
            for(var i=0;i<mainValhtml.length;i++){
                if(mainValhtml[i].innerHTML==""){
                    html+="";
                }else{
                    html+="<div><input type='radio' name='mainVal' value='"+mainValhtml[i].nextElementSibling.value+"' class='affliVal'/><b>"+mainValhtml[i].innerHTML+"</b></div>";
                }
            }
            $("#affliValue").html(html);
            $("#affliValue").on("click",".affliVal",function(e){
                var target = e.target||window.event.target;
                var code=$(target).val();
                $("#chooseCode").html(code);
                var val=$(target).next().html();
                $("#chooseVal").html(val);
            });
            $("#searchIncre").on("click",".deleteAffli",function(e){
                var target = e.target||window.event.target;
                $(target).parent().css("display","none");
                $(target).parent().children("input").val("");
            })
        },
        yes : function(index, layero) {
            var code=$("#chooseCode").html();
            var length = $('#searchIncre input[value="'+code+'"]').length;
            var chooseVal=$("#chooseVal").html();
            if(chooseVal==""){
                layer.close(index);
            }else{
                if(length>=1){
                    layer.msg("选择项重复，请重新选择一个",{time: 2000});
                }else{
                    var chooseVal=$("#chooseVal").html();
                    var chooseCode=$("#chooseCode").html();
                    if(chooseVal==""){
                        $(".deleteField").attr("src","");
                        $(".deleteField").parent().css("display","none");
                    }
                    $("<p class='afflivalName'><b class='afflimainVal'>"+chooseVal+"</b><input type='hidden'  value='" +chooseCode + "'/><img src='" + _ctx + "/resources/images/blackClear.png' class='deleteAffli'/></p>").insertBefore($(".whiteadd"));
                    layer.close(index);
                }
            }
        },
        cancel: function(index, layero){
            layer.close(index)
        }
    });
}




