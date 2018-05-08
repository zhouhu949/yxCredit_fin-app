/**
 * Created by Win7 on 2017/5/4.
 */
//获取放款表信息
var g_userManage_loan = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        debugger
        var paramFilter = {};
        var param = {};
        var page = {};
        // 自行处理查询参数
        param.fuzzySearch = g_userManage_loan.fuzzySearch;
        if (g_userManage_loan.fuzzySearch) {
            param.personName = $("#search").val();
        }
        param.personName = $("input[name='search']").val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//页面初始化
$(function () {
    g_userManage_loan.tableUser = $('#Res_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "iDisplayLength": 7,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
            "ajax" : function(data, callback, settings) {
                debugger
                var queryFilter = g_userManage_loan.getQueryCondition(data);
                Comm.ajaxPost("loanManageController/getLoanManage", JSON.stringify(queryFilter), function (result) {
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
                {"data":'orderNo',"searchable":false,"orderable" : false},
                {"data":'personName',"searchable":false,"orderable" : false},

                {"data":'TEL',"searchable":false,"orderable" : false},
                {"data":'card',"searchable":false,"orderable" : false},
                {"data":'amount',"searchable":false,"orderable" : false},
                {"data":'state',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                    if (data=='1') {
                        return "放款成功";
                    } else {
                        return "";
                    }
                }},
                {"data":'loan_time',"searchable":false,"orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }},
                {"data":'manageFee',"searchable":false,"orderable" : false},
                {"data":'quickTrialFee',"searchable":false,"orderable" : false},
                {"data":'payable_amount',"searchable":false,"orderable" : false},
                {"data":'expiration_date',"searchable":false,"orderable" : false},
                {"data":'derate_amount',"searchable":false,"orderable" : false},
                {"data":'agent_amount',"searchable":false,"orderable" : false},
                {"data":'penalty',"searchable":false,"orderable" : false},
                {
                    "data": "null", "orderable": false, "width": "100px",
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var btnLookDel = $('<a class="tabel_btn_style" onclick="LookDel(event)">查看明细&nbsp;</a>');
                var btnAddDel = $('<a class="tabel_btn_style" onclick="addDel(event)">添加明细&nbsp;</a>');
                var btnDelete = $('<a class="tabel_btn_style_dele" onclick="deleteById(event)">删除&nbsp;</a>');
                var btnEdit = $('<a class="tabel_btn_style_dele" onclick="openDict(event)">修改</a>');
                $("td", row).eq(17).append(btnLookDel).append(btnAddDel).append(btnDelete).append(btnEdit);
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
                    g_userManage_loan.fuzzySearch = true;
                    g_userManage_loan.tableUser.ajax.reload();
                    $("#paramSearch").val("");
                });
                //搜索重置
                $("#btn_search_reset").click(function() {
                    $('input[name="search"]').val("");
                    g_userManage_loan.fuzzySearch = false;
                    g_userManage_loan.tableUser.ajax.reload();
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage_loan.tableUser.on("order.dt search.dt", function() {
        g_userManage_loan.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
})


var layerIndex;
//编辑的赋值
function openDict(e){
    $('#Code').attr("disabled",true);
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
        "iDisplayLength": 25,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "info": false,//页脚信息显示
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userDetileManage.getQueryCondition(menuId,data);
            Comm.ajaxPost("loanManageController/getRepayment", JSON.stringify(queryFilter), function (result) {
                debugger
                console.log(result);
                var returnData = {};
                var resData = result.data.list;
                var resPage = result.data;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.total;
                returnData.recordsFiltered = resPage.total;
                returnData.data = resData;
                callback(returnData);
            }, "application/json","","","",false)
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
            {"data":'payCount',"searchable":false,"orderable" : false},
            {"data":'fee',"searchable":false,"orderable" : false},

            {"data":'repaymentAmount',"searchable":false,"orderable" : false},
            {"data":'managefee',"searchable":false,"orderable" : false},
            {"data":'quicktrialfee',"searchable":false,"orderable" : false},
            {"data":'payTime',"searchable":false,"orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }},
            {"data":'state',"searchable":false,"orderable" : false, "render":function (data, type, row, meta) {
                if (data=='1') {
                    return "未还";
                }
                else if (data=='2') {
                    return "已还";
                }else if (data=='3'){
                    return "逾期";
                } else {
                    return "";
                }
            }}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
        /*  var btnAdd = $('<a class="tabel_btn_style" onclick="deleteById(event)">删除&nbsp;</a>');
            var btnEdit = $('<a class="tabel_btn_style" onclick="openEditDel(event)">修改</a>');
            return $("td", row).eq(7).append(btnAdd).append(btnEdit);*/
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
            var remark=$("#remark").val();
            var dict={
                code:nameCode,
                name:name,
                isCatagory:"N",
                remark:remark,
                parentId:selectType
            };
            Comm.ajaxPost(
                'dict/add',JSON.stringify(dict),
                function(data){
                    if(data.code==1){
                        layer.msg(data.msg,{time:1000});
                    }
                    layer.msg(data.msg,{time:1000},function(){
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
            var dict={
                id:checkId,
                code:nameCode,
                name:name,
                isCatagory:"N",
                remark:remark,
                parentId:selectType
            };
            Comm.ajaxPost(
                'dict/edit',JSON.stringify(dict),
                function(data){
                    if(data.code==1){
                        layer.msg(data.msg,{time:1000});
                    }
                    layer.msg(data.msg,{time:1000},function(){
                        layer.close(layerIndex);
                        g_userManage_loan.tableUser.draw();//刷新数据
                        $("#Res_list_detail").show();
                        g_userDetileManage.tableUser.draw();
                    });
                }, "application/json"
            );
        }
    });
}



