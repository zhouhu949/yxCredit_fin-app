/**
 * Created by Win7 on 2017/5/4.
 */
//页面初始化
$(function () {
    var menuId = "0"
    queryList(menuId);
})
/****************************************************大类*****************************************************************/
function queryList(id){
    g_userManage.tableUser = $('#Res_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "iDisplayLength": 4,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(id,data);
            Comm.ajaxPost("dict/listDict", JSON.stringify(queryFilter), function (result) {
                console.log(result);
                var returnData = {};
                var resData = result.data.list;
                var resPage = result.data;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.total;
                returnData.recordsFiltered = resPage.total;
                returnData.data = resData;
                callback(returnData);
            }, "application/json")
        },
        "order": [],
        "columns" :[
            {
                'data':null,
                'class':'hidden',"searchable":false,"orderable" : false,
            },
            {
                "className" : "childBox",
                'class':'hidden',
                "orderable" : false,
                "data" : null,
                "width" : "20px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input id="'+data.id+'" type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data":'code',"searchable":false,"orderable" : false},
            {"data":'name',"searchable":false,"orderable" : false},
            {
                "data":null,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            {
                "data":null,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.updateTime);
                }
            },
            {"data":'remark',"searchable":false,"orderable" : false},
            {
                "data": "null", "orderable": false, "width": "100px",
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnLookDel = $('<a href="##" class="LookDel" style="text-decoration: none;color: #307ecc;"onclick="LookDel(event)">查看明细&nbsp;</a>');
            var btnAddDel = $('<a href="##" class="addDel" style="text-decoration: none;color: #307ecc;"onclick="addDel(event)">添加明细&nbsp;</a>');
            var btnDelete = $('<a href="##" class="delete" style="text-decoration: none;color: #307ecc;" onclick="deleteById(event)">删除&nbsp;</a>');
            var btnEdit = $('<a href="##" class="edit" style="text-decoration: none;color: #307ecc;" onclick="openDict(event)">修改</a>');
            $("td", row).eq(7).append(btnLookDel).append(btnAddDel).append(btnDelete).append(btnEdit);
        },
        "initComplete" : function(settings,json) {
            //点击一行显示明细
            $("#Res_list").delegate( 'tbody tr td:not(:last-child)','click',function(e){
                $("#Res_list_detail").show();
                $("#tile").show();
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
                queryListDetile(id);
            });
            //搜索
            $("#paramSearch").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
                $("#paramSearch").val("");
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
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(id,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        // 自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.searchKey = $("#search").val();
        }
        param.searchKey = $("input[name='search']").val();
        param.parentId=id;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 4 : data.length;
        if (page.pageSize==-1)
        {
            page.pageSize=4;
        }
        paramFilter.page = page;
        return paramFilter;
    }
};
//查询重置方法
function paramSearchReset() {
    $("#Res_list_detail").hide();
    $("#tile").hide();
    $('input[name="search"]').val("");
    g_userManage.fuzzySearch = false;
    g_userManage.tableUser.ajax.reload();
}
var layerIndex;
//编辑的赋值
function openDict(e){
    var target = e.target || window.event.target;
    var checkId = $(target).parents("tr").children().eq(1).children("input").val();
    $('#nameCode').val("");
    $("#nameDict").val("");
    $('input[name="isCatagory"]:checked').attr("checked",false);
    $("#remarkdel").val("");
        titleName = "编辑";
        Comm.ajaxGet(
            "dict/detail?id="+checkId,
            "",
            function(data){
                var dict = data.data;
                var isCatagory=dict.isCatagory;
                if(isCatagory=='Y'){
                    $('#isCatagoryY').attr('checked','checked');
                }else{
                    $('#isCatagoryN').attr('checked','checked');
                }
                $('input[name="name_code"]').val(dict.code);
                $('input[name="name_dict"]').val(dict.name);
                $('textarea[name="remark"]').val(dict.remark);
                $("#parentId").val(dict.parentId);
                editDict(checkId);
            }
        );
}
//修改大类
function editDict(checkId){
    var titleName =  "修改";
    layerIndex = layer.open({
        type : 1,
        title : titleName,
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '600px', '' ],
        content : $('#Add_Dict'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            assortSelect(titleName,checkId);
        },
        yes:function(index, layero){
            var code=$("#Code").val();
            var name=$("#dictName").val();
            var remark=$("#remark").val();
            var dict={
                id:checkId,
                code:code,
                name:name,
                isCatagory:"Y",
                remark:remark,
                parentId:"0"
            };
            if(name==''){
                layer.msg("字典名称不可为空！",{time:2000});return
            }
            if(code==''){
                layer.msg("字典code不可为空！",{time:2000});return
            }
            Comm.ajaxPost(
                'dict/edit',JSON.stringify(dict),
                function(data){
                    if(data.code==1){
                        layer.msg(data.msg,{time:2000});
                    }
                    layer.msg(data.msg,{time:2000},function(){
                        layer.close(layerIndex);
                        g_userManage.tableUser.draw();//刷新数据
                    });
                }, "application/json"
            );
        }
    });
}
//删除字典
function deleteById(e){
    layer.msg("删除功能不予开放，请通知系统管理员！");
    // var target = e.target || window.event.target;
    // var checkId = $(target).parents("tr").children().eq(1).children("input").val();//获取点击的id
    // var param = {};
    // param.id = checkId;
    // Comm.ajaxPost('dict/delete',JSON.stringify(param),
    //     function(data){
    //         layer.msg(data.msg,{time:2000});
    //         g_userManage.tableUser.draw();//刷新数据
    //         g_userDetileManage.tableUser.draw();//刷新数据
    //     },"application/json"
    // );
}
//新增大类
function addDict(){
    $('#Code').val("");
    $("#dictName").val("");
    $('input[name="isCatagory"]:checked').attr("checked",false);
    $("#remark").val("");
    layerIndex = layer.open({
        type : 1,
        title : "添加",
        shadeClose : true, //点击遮罩关闭层
        area : [ '550px', '340px' ],
        content : $('#Add_Dict'),
        btn : [ '保存', '取消' ],
        yes:function(index, layero){
            var nameCode=$('input[name="name_code"]').val();
            var name=$("#dictName").val();
            var remark=$('textarea[name="remark"]').val();
            var param={
                code:nameCode,
                name:name,
                type:"",
                value:"",
                isCatagory:"Y",
                remark:remark,
                parentId:"0"
            };
            if(name==''){
                layer.msg("字典名称不可为空！",{time:2000});return
            }
            if(nameCode==''){
                layer.msg("字典code不可为空！",{time:2000});return
            }
            debugger
            Comm.ajaxPost(
                'dict/add',JSON.stringify(param),
                function(data){
                    if(data.code==1){
                        layer.msg(data.msg,{time:2000});
                    }
                    layer.msg(data.msg,{time:2000},function(){
                        layer.close(layerIndex);
                        var id=$("#id").val();
                        g_userManage.tableUser.draw();
                    });
                }, "application/json"
            );
        }
    });
}
//编辑和新增页面给值
function assortSelect(titleName,menuId){
    Comm.ajaxGet(
        "dict/getCatagory",
        '',
        function(data){
            var dataList=data.data;
            if(titleName=="编辑"){
                var html="";
                for(var i=0;i<dataList.length;i++){
                    if(menuId==dataList[i].id){
                        html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                    }else{
                        html+="<option value='"+dataList[i].id+"'>"+dataList[i].name+"</option>"
                    }
                }
                $('select[name="selectType"]').html(html);
            }
            else if(titleName=="添加"){
                var html="";
                for(var i=0;i<dataList.length;i++){
                    if(menuId==dataList[i].id){
                        html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                    }else{
                        html+="<option value='"+dataList[i].id+"'>"+dataList[i].name+"</option>"
                    }
                }
                $('#selectType').html(html);
            }
        }
    )
}

/****************************************************明细*****************************************************************/
function queryListDetile(menuId){
    g_userDetileManage.tableUser = $('#Res_list_detail').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "info": false,//页脚信息显示
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userDetileManage.getQueryCondition(menuId,data);
            Comm.ajaxPost("dict/listDict", JSON.stringify(queryFilter), function (result) {
                var returnData = {};
                var resData = result.data;
                returnData.data = resData;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            }, "application/json",null,null,null,false)
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
                "width" : "20px",
                'class':'hidden',
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input id="'+data.id+'" type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data":'code',"searchable":false,"orderable" : false},
            {"data":'name',"searchable":false,"orderable" : false},
            {"data":'value',"searchable":false,"orderable" : false},
            {
                "data":null,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            {
                "data":null,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            {"data":'remark',"searchable":false,"orderable" : false},
            {
                "data": "null", "orderable": false, "width": "100px",
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnAdd = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="" onclick="deleteById(event)">删除&nbsp;</a>');
            var btnEdit = $('<a href="##" class="edit" style="text-decoration:none;color: #307ecc;" onclick="openEditDel(event)">修改</a>');
            return $("td", row).eq(8).append(btnAdd).append(btnEdit);
        },
        "initComplete" : function(settings,json) {
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userDetileManage.tableUser.on("order.dt search.dt", function() {
        g_userDetileManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
}
var g_userDetileManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(menuId,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.parentId=menuId;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//查看明细
function LookDel(e){
    $("#Res_list_detail").show();
    $("#tile").show();
    var target = e.target || window.event.target;
    var checkId = $(target).parents("tr").children().eq(1).children("input").val();//获取点击的id
    queryListDetile(checkId);
}
//新增明细
function addDel(e){
    $('#nameCode').val("");
    $("#nameDict").val("");
    $('input[name="isCatagory"]:checked').attr("checked",false);
    $("#remarkdel").val("");
    var titleName = "添加";
    var target = e.target || window.event.target;
    var checkId = $(target).parents("tr").children().eq(1).children("input").val();//获取点击的id
    layerIndex = layer.open({
        type : 1,
        title : titleName,
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '600px', '' ],
        content : $('#Add_Dict_Del'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            assortSelect(titleName,checkId);
        },
        yes:function(index, layero){
            var selectType=$('select[name="selectType"]').val();
            var nameCode=$("#nameCode").val();
            var name=$("#nameDict").val();
            var remark=$("#remarkdel").val();
            var value = $('input[name="value"]').val();
            var dict={
                code:nameCode,
                name:name,
                isCatagory:"N",
                remark:remark,
                value:value,
                type:"",
                parentId:selectType
            };
            if(nameCode==''){
                layer.msg("字典Code不可为空！",{time:2000});return
            }
            if(name==''){
                layer.msg("字典名称不可为空！",{time:2000});return
            }
            debugger
            Comm.ajaxPost(
                'dict/add',JSON.stringify(dict),
                function(data){
                    if(data.code==1){
                        layer.msg(data.msg,{time:2000});
                    }
                    layer.msg(data.msg,{time:2000},function(){
                        layer.close(layerIndex);
                        $("#Res_list_detail").show();
                        $("#tile").show();
                        queryListDetile(selectType);//刷新数据
                    });
                }, "application/json"
            );
        }
    });

}
//打开修改明细页面
function openEditDel(e){
    var target = e.target || window.event.target;
    var checkId = $(target).parents("tr").children().eq(1).children("input").val();
    $('#nameCode').val("");
    $("#nameDict").val("");
    $('input[name="isCatagory"]:checked').attr("checked",false);
    $("#remarkdel").val("");
    var titleName = "编辑";
    Comm.ajaxGet(
        "dict/detail?id="+checkId,
        "",
        function(data){
            var dict = data.data;
            var isCatagory=dict.isCatagory;
            if(isCatagory=='Y'){
                $('#isCatagory_Y').attr('checked','checked');
            }else{
                $('#isCatagory_N').attr('checked','checked');
            }
            $('input[name="name_code"]').val(dict.code);
            $('input[name="name_dict"]').val(dict.name);
            $('input[name="value"]').val(dict.value);
            $('textarea[name="remark"]').val(dict.remark);
            $("#parentId").val(dict.parentId);
            var parentId = dict.parentId;
            editDictDel(checkId,parentId);
        }
    );
}
//修改明细
function editDictDel(checkId,parentId){
    var titleName =  "编辑";
    layerIndex = layer.open({
        type : 1,
        title : titleName,
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '600px', '' ],
        content : $('#Add_Dict_Del'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            assortSelect(titleName,parentId);
        },
        yes:function(index, layero){
            var selectType=$('select[name="selectType"]').val();
            var nameCode=$("#nameCode").val();
            var name=$("#nameDict").val();
            var remark=$("#remarkdel").val();
            var value = $('input[name="value"]').val();
            var dict={
                id:checkId,
                code:nameCode,
                name:name,
                isCatagory:"N",
                remark:remark,
                parentId:selectType,
                value:value
            };

            if(nameCode==''){
                layer.msg("字典Code不可为空！",{time:2000});return
            }
            if(name==''){
                layer.msg("字典名称不可为空！",{time:2000});return
            }
            if(value ===''){
                layer.msg("字典Value不可为空！",{time:2000});return
            }
            Comm.ajaxPost(
                'dict/edit',JSON.stringify(dict),
                function(data){
                    if(data.code==1){
                        layer.msg(data.msg,{time:2000});
                    }
                    layer.msg(data.msg,{time:2000},function(){
                        layer.close(layerIndex);
                        g_userManage.tableUser.draw();//刷新数据
                        $("#Res_list_detail").show();
                        g_userDetileManage.tableUser.draw();
                    });
                }, "application/json"
            );
        }
    });
}



