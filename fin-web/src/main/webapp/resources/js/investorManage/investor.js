/**
 * Created by Administrator on 2017/5/2 0002.
 */
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.investorName = $("[name='investName']").val();
            param.tel = $("[name='mobile']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};
//初始化表格数据
$(function (){
    //获取权限集合
   /* Comm.ajaxPost(
        'user/getPermission',"{}",
        function(data){
            var data=data.data;
            var html='';
            for(var i=0;i<data.length;i++){
                if(data[i] =="/user/add"){
                    $("#addBtn").show();
                }
                if(data[i] =="/user/edit"){
                    $("#updateBtn").show();
                }
                if(data[i] =="/user/delete"){
                    $("#deleteBtn").show();
                }
                if(data[i] =="/user/resetPwd"){
                    $("#resetBtn").show();
                }
                if(data[i] =="/userRole/add"){
                    $("#assignBtn").show();
                }
            }

        },"application/json"
    );*/
    g_userManage.tableUser = $('#User_list').dataTable($.extend({
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
            Comm.ajaxPost('investor/getList',JSON.stringify(queryFilter),function(result){
                console.log(result)
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
                "searchable":false,"orderable" : false
            },
            {"data":'investorName',"searchable":false,"orderable" : false},
            {"data":'acount',"searchable":false,"orderable" : false},
            {"data":'tel',"searchable":false,"orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.status==1){
                        return '有效'
                    }else{
                        return '无效'
                    }
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            },
            {"data":'id','class':'hidden',"searchable":false,"orderable" : false}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnEdit = $('<a onclick="updateUser(0,\''+data.id+'\')" href="##" class="tabel_btn_style" style="color: #307ecc">修改</a>');
            var btnDel = $('<a onclick="deleteInvestor(\''+data.id+'\')" href="##" class="tabel_btn_style" style="color: #307ecc">  删除</a>');
            $('td', row).eq(5).append(btnEdit).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    $("#userCheckBox_All").attr("checked",false);
                });
            });
            //全选
            $("#userCheckBox_All").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#User_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#User_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //重置
            $("#btn_search_reset").click(function() {
                $('input[name="investName"]').val("");
                $('input[name="mobile"]').val("");
                g_userManage.fuzzySearch = false;
                g_userManage.tableUser.ajax.reload(function(){
                    $("#userCheckBox_All").attr("checked",false);
                });
            });
            //单选行
            $("#User_list tbody").delegate( 'tr','click',function(e){
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
});
//添加、编辑用户信息
function updateUser(sign,id) {
    var paramt ={};
    paramt.investorId = id;
    if(sign==0){
        Comm.ajaxPost('investor/detail',JSON.stringify(paramt),function(data){
            layer.closeAll();
            var investor=data.data;
            var organStatus;
            $('input[name="user_account"]').val(investor.investorName);
            $('input[name="user_trueName"]').val(investor.acount);
            $('input[name="user_mobile"]').val(investor.tel);
            if(investor.status==1){
                $("#qiyong").attr('selected','selected');
            }else{
                $("#jinyong").attr('selected','selected');
            }
            layer.open({
                type : 1,
                title : '修改用户',
                area : [ '576px', '370px' ],
                content : $('#Add_user_style'),
                btn : [ '保存', '取消' ],
                yes : function(index, layero) {
                    if ($('input[name="user_account"]').val() == "") {
                        layer.msg("投资人名称不能为空",{time:2000});
                        return;
                    }
                    if ($('input[name="user_mobile"]').val() == "") {
                        layer.msg("手机号码不能为空",{time:2000});
                        return;
                    }
                    var investorName=$('input[name="user_account"]').val();
                    var tel=$('input[name="user_mobile"]').val();
                    var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                    if(!mobileReg.test(tel)){
                        layer.msg("手机号码格式不正确",{time:2000});
                        return;
                    }
                    var acount=$('input[name="user_trueName"]').val();
                    var isLock=$("#isLock").val();
                    var investor={
                        investorName:investorName,
                        tel:tel,
                        acount:acount,
                        id:id,
                        status:parseInt(isLock),
                    };
                    Comm.ajaxPost(
                        'investor/edit',JSON.stringify(investor),
                        function(data){
                            layer.closeAll();
                            layer.msg(data.msg,{time:1000},function () {
                                $('#User_list').dataTable().fnDraw(false);
                            });
                        }, "application/json"
                    );
                }
            });
        },"application/json","","","",false);
    }else{
        $("#organ").attr('disabled',false);
        $("#isEdit").show();
        $('input[name="user_account"]').val("");
        $('input[name="user_password"]').val("");
        $('input[name="user_mobile"]').val("");
        $('input[name="user_email"]').val("");
        $('input[name="user_trueName"]').val("");
        $("#remark").val("");
        $("#qiyong").attr('selected','selected');
        $("#organ").attr('disabled',false);
        layer.open({
            type : 1,
            title : '添加投资人',
            area : [ '576px', '370px' ],
            content : $('#Add_user_style'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                if ($('input[name="user_account"]').val() == "") {
                    layer.msg("投资人不能为空",{time:2000});
                    return;
                }
                if ($('input[name="user_mobile"]').val() == "") {
                    layer.msg("手机号码不能为空",{time:2000});
                    return;
                }
                var investorName=$('input[name="user_account"]').val();//投资人
                var tel=$('input[name="user_mobile"]').val();//手机号
                var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                if(!mobileReg.test(tel)){
                    layer.msg("手机号码格式不正确",{time:2000});
                    return;
                }
                var acount=$('input[name="user_trueName"]').val();//账号
                var isLock=$("#isLock").val();//状态
                var organId=$('#deptPid').val();
                var investor={
                    investorName:investorName,
                    tel:tel,
                    acount:acount,
                    status:parseInt(isLock),
                };
                Comm.ajaxPost(
                    'investor/add',JSON.stringify(investor),
                    function(data){
                        layer.closeAll();
                        layer.msg(data.msg,{time:1000},function () {
                            g_userManage.tableUser.ajax.reload(function(){
                            });
                        });
                    },"application/json"
                );
            }
        });
    }

}
//启用或停止
/*function updateState(state) {
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
 layer.msg(data.msg, {time: 1000}, function () {
 g_userManage.fuzzySearch = true;
 g_userManage.tableUser.ajax.reload(function(){
 contentChange();//点击东西过长显示省略号
 });
 });
 }, "application/json"
 );

 }*/
//删除投资人信息
function deleteInvestor(id){
    layer.confirm('是否删除投资人？', {
        btn : [ '确定', '取消' ]
    }, function() {
        var param ={};
        param.id = id;//投资人id
        Comm.ajaxPost(
            'investor/delete',JSON.stringify(param),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    g_userManage.tableUser.ajax.reload(function(){
                        //$("#userCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json");
    });
}
//重置用户密码
function resetPwd(userId) {
    var userIds = new Array();
    userIds.push(userId);

    // var selectArray = $("#User_list tbody input:checked");
    // if(!selectArray || selectArray.length==0){
    //     layer.msg("请选择用户",{time:2000});
    //     return;
    // }
    // var userIds = new Array();
    // $.each(selectArray,function(i,e){
    //     var val = $(this).val();
    //     userIds.push(val);
    // });
    // if(userIds.lenght==0){
    //     return;
    // }
    layer.confirm('是否重置密码，重置后原密码将失效？', {
        btn : [ '重置', '取消' ]
    }, function() {
        Comm.ajaxPost(
            'user/resetPwd', JSON.stringify(userIds),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    g_userManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json"
        );
    });
}
//角色分配
function asignRole(userId) {
    Comm.ajaxPost('role/getRoleMap',JSON.stringify(userId),function(response){
        $("#multiselect option").remove();
        $("#multiselect_to option").remove();
        var roleIds = response.data.roleIds;
        $.each(response.data.roleList,function(i,roleMap){
            var roleId = roleMap.roleId;
            if(roleIds.indexOf(roleId)>=0){
                $("#multiselect_to").prepend("<option value='"+roleId+"'>"+roleMap.role_name+"</option>");
            }else{
                $("#multiselect").prepend("<option value='"+roleId+"'>"+roleMap.role_name+"</option>");
            }
        });
    },"application/json");
    layer.open({
        type : 1,
        title : "角色分配",
        maxmin : true,
        area : [ '576px', '468px' ],
        content : $('#asignRole'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var roleIds = [];
            $("#multiselect_to option").each(function(i,e){
                var selectVal = $(this).val();
                roleIds.push(selectVal);
            });
            var param = {"userId":userId,"roleIds":roleIds};
            Comm.ajaxPost('userRole/add',param, function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    g_userManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox_All").attr("checked",false);
                    });
                });
            })
        }});
}
//角色-单个按钮功能
function rightAll(){
    var options=$("#multiselect option");
    $("#multiselect_to").prepend(options);
}
function rightSelected(){
    var options=$("#multiselect option:selected");
    $("#multiselect_to").append(options);
    $("#multiselect_to option:selected").attr('selected',false);
}
function leftSelected(){
    var options=$("#multiselect_to option:selected");
    $("#multiselect").append(options);
    $("#multiselect option:selected").attr('selected',false);
}
function leftAll(){
    var options=$("#multiselect_to option");
    $("#multiselect").prepend(options);
}

//点击所属部门，加载树
var showDeptZtree = function(){
    //打开部门ztree弹框
    var deptTree = layer.open({
        title:"部门",
        type: 1,
        maxWidth:'auto',
        area:['300px','300px'],
        skin: 'layer_normal',
        shift: 5,
        offset:'150px',
        btn : [ '保存', '取消' ],
        shadeClose:false,
        content: $("#jstree").show(),
        success: function(layero, index){
            getDepartmentZtree();
            $('#jstree').delegate('span.text', 'click', function () {
                var id = $(this).attr('title');
                var deptName = $(this).text();
                $('#deptPname').val(deptName);
                $('#deptPid').val(id);
            })
        },
        yes: function () {
            layer.close(deptTree);
        }
    });
};
