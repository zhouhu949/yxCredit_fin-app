/**
 * Created by Administrator on 2017/5/2 0002.
 */
var g_contractorManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_contractorManage.fuzzySearch;
        if (g_contractorManage.fuzzySearch) {
            param.contractorName = $("[name='contractorName']").val();
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
    Comm.ajaxPost(
        'user/getPermission',"{}",
        function(data){
            var data=data.data;
            var html='';
            for(var i=0;i<data.length;i++){
                if(data[i] =="/contractorManage/addContractor"){
                    $("#addBtn").show();
                }
                if(data[i] =="/contractorManage/updateContractor"){
                    $("#updateBtn").show();
                }
               /* if(data[i] =="/user/contractorDetail"){
                    $("#deleteBtn").show();
                }
                if(data[i] =="/user/resetPwd"){
                    $("#resetBtn").show();
                }
                if(data[i] =="/userRole/add"){
                    $("#assignBtn").show();
                }*/
            }

        },"application/json"
    );
    g_contractorManage.tableUser = $('#Contractor_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_contractorManage.getQueryCondition(data);
            Comm.ajaxPost('contractorManage/contractorListPage',JSON.stringify(queryFilter),function(result){
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
            {"data":'contractorName',"searchable":false,"orderable" : false},
            {"data":'linkman',"searchable":false,"orderable" : false},
            {"data":'linkmanPhone',"searchable":false,"orderable" : false},
            {"data":'credit',"searchable":false,"orderable" : false},

            {
                "data":"admissionDate",
                "searchable":false,
                "orderable" : false

            },

            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.state==1){
                        return '启用'
                    }else{
                        return '停用'
                    }
                }
            },

            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="updateContractor(0,\''+data.id+'\')">修改</a>&nbsp;<a class="tabel_btn_style" onclick="asignRole(\''+data.id+'\')">绑定用户</a>');
            $('td', row).eq(7).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_contractorManage.fuzzySearch = true;
                g_contractorManage.tableUser.ajax.reload(function(){
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
                $('input[name="account"]').val("");
                $('input[name="trueName"]').val("");
                $('input[name="mobile"]').val("");
                g_contractorManage.fuzzySearch = true;
                g_contractorManage.tableUser.ajax.reload(function(){
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
    g_contractorManage.tableUser.on("order.dt search.dt", function() {
        g_contractorManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});

function uploadFile() {
    $.ajax({
        type : "POST",
        url : "findContractorByRoleId",
        data : {
            Name : $("#file").val(),
        },
        async : false,
        dataType : 'json',
        success : function(data) {
            var html="";
            html+="<option value=''>请选择</option>";
            $.each(data.data,function(index,result) {
                html+="<option value="+result.id+">"+result.contractorName+"</option>"
            });
            $("select[name='contractorList']").html(html);

        }
    });
}



//下面用于图片上传预览功能
function setImagePreview1() {
    var docObj=document.getElementById("file");

    var imgObjPreview=document.getElementById("preview");
    if(docObj.files &&docObj.files[0])
    {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '272px';
        imgObjPreview.style.height = '180px';
        //imgObjPreview.src = docObj.files[0].getAsDataURL();

        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }
    else
    {
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag");
        //必须设置初始大小
        localImagId.style.width = "272px";
        localImagId.style.height = "180px";
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        try{
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        }
        catch(e)
        {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
    /*if($("#preview").attr("src")!=""){
     var html="<span>fdfasdf</span>";
     $("#pictureLoad").append(html);
     }*/
}

//添加、编辑用户信息
function updateContractor(sign,id) {
    if(sign==0){
        $('#organ').html("");
        Comm.ajaxPost('contractorManage/contractorDetail',id,function(data){
            layer.closeAll();
            var contractor = data.data;
            $("#contractorName").val(contractor.contractorName);
            $("#linkman").val(contractor.linkman);
            $("#linkmanPhone").val(contractor.linkmanPhone);
            $("#credit").val(contractor.credit);
          //  $("#organ").attr('disabled',true);
            if(contractor.state==1){
                $("#qiyong").attr('selected','selected');
            }else{
                $("#jinyong").attr('selected','selected');
            }
           // $("#remark").val(user.remark);
            $("#isEdit").hide();
            layer.open({
                type : 1,
                title : '修改总包商',
                area : [ '576px', '370px' ],
                content : $('#Add_user_style'),
                btn : [ '保存', '取消' ],
                yes : function(index, layero) {
                    if ($('input[name="contractor_name"]').val() == "") {
                        layer.msg("总包商名不能为空",{time:2000});
                        return;
                    }
                    if ($('input[name="contractor_linkman"]').val() == "") {
                        layer.msg("联系人不能为空",{time:2000});
                        return;
                    }
                    if ($('input[name="contractor_mobile"]').val() == "") {
                        layer.msg("联系方式不能为空",{time:2000});
                        return;
                    }
                    var contractorName=$('input[name="contractor_name"]').val();
                    var linkman=$('input[name="contractor_linkman"]').val();
                    var linkmanPhone=$('input[name="contractor_mobile"]').val();
                    var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                    if(!mobileReg.test(linkmanPhone)){
                        layer.msg("手机号码格式不正确",{time:2000});
                        return;
                    }
                    var credit=$('input[name="contractor_credit"]').val();
                    var state=$("#state").val();
                    var userId=$("#userId").val();
                    alert($("#preview").attr("src"));
                    console.log(state);
                    var user={
                        id : id,
                        contractorName:contractorName,
                        linkman:linkman,
                        linkmanPhone:linkmanPhone,
                        credit:credit,
                        userId:userId,
                        licenceAttachment : $("#file").val(),
                        state:parseInt(state)
                    };
                    Comm.ajaxPost(
                        'contractorManage/updateContractor',JSON.stringify(user),
                        function(data){
                            layer.closeAll();
                            layer.msg(data.msg,{time:2000},function () {
                                $('#Contractor_list').dataTable().fnDraw(false);
                            });
                        }, "application/json"
                    );
                }
            });
        },"application/json","","","",false);
    }else{
        //$("#organ").attr('disabled',false);
        $("#isEdit").show();
        $('input[name="contractor_name"]').val("");
        $('input[name="contractor_linkman"]').val("");
        $('input[name="contractor_mobile"]').val("");
        $('input[name="contractor_credit"]').val("");
      //  $("#remark").val("");
        $("#qiyong").attr('selected','selected');
        $("#organ").attr('disabled',false);
        layer.open({
            type : 1,
            title : '添加总包商',
            area : [ '576px', '370px' ],
            content : $('#Add_user_style'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                if ($('input[name="contractor_name"]').val() == "") {
                    layer.msg("总包商不能为空",{time:2000});
                    return;
                }
                if ($('input[name="contractor_mobile"]').val() == "") {
                    layer.msg("手机号码不能为空",{time:2000});
                    return;
                }
                var contractorName=$('input[name="contractor_name"]').val();
                var linkman=$('input[name="contractor_linkman"]').val();
                var linkmanPhone=$('input[name="contractor_mobile"]').val();
                var mobileReg=/^1(3|4|5|7|8)\d{9}$/;//(1[34578])\\d{9}$
                if(!mobileReg.test(linkmanPhone)){
                    layer.msg("手机号码格式不正确~",{time:2000});
                    return;
                }
                var credit=$('input[name="contractor_credit"]').val();
                var state = $("#state option:selected").val();
                var userId=$("#userId").val();
                var contractor={
                    contractorName:contractorName,
                    linkmanPhone:linkmanPhone,
                    linkman:linkman,
                    credit:credit,
                    userId:userId,
                    state:parseInt(state)
                };
                Comm.ajaxPost(
                    'contractorManage/addContractor',JSON.stringify(contractor),
                    function(data){
                        layer.closeAll();
                        layer.msg(data.msg,{time:2000},function () {
                            g_contractorManage.tableUser.ajax.reload(function(){
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
                g_contractorManage.fuzzySearch = true;
                g_contractorManage.tableUser.ajax.reload(function(){
                    contentChange();//点击东西过长显示省略号
                });
            });
        }, "application/json"
    );

}*/
//删除用户信息
function deleteUser(userId){
    var userIds = new Array();
    userIds.push(userId);
    layer.confirm('是否删除用户？', {
        btn : [ '确定', '取消' ]
    }, function() {
        Comm.ajaxPost(
            'user/delete', JSON.stringify(userIds),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:2000},function () {
                    g_contractorManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json");
    });
}

//绑定用户
function asignRole(contractorId) {
    Comm.ajaxPost('contractorManage/findUserByMenuUrl',"",function(data){
        var html="";
        $.each(data.data,function(index,result){
            html+="<tr>"
                +"<td><input name=\"userCheckBox\" id=\"userCheckBox\" type=\"checkbox\"  class=\"ace\" value=\""+result.userId+"\" onclick='checkSelect(\"userCheckBox\", \"allSelectCheckBox\", \"allSelectId\")'>" +
                "<span class=\"lbl\" style=\"cursor:pointer;\"></span></td>"
                +"<td>"+result.account+"</td>"
                +"<td>"+result.tel+"</td>"
                +"</tr>";
        });
        $("#User_list").html(html);

    },"application/json");
    layer.open({
        type : 1,
        title : "绑定用户",
        maxmin : true,
        area : [ '576px', '468px' ],
        content : $('#asignRole'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var userArr = new Array();
            $("[name=userCheckBox]").each(function(){
                if ($(this).prop("checked") == true) {
                    userArr.push($(this).attr("value"));
                }
            });
            var param = {"id":contractorId,"userStr": userArr.join(",")};
            Comm.ajaxPost('contractorManage/bindingUser',JSON.stringify(param), function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:2000},function () {
                    g_contractorManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox").attr("checked",false);
                    });
                });
            },"application/json")
        }});
}

/**
 * 是否选中全选按钮
 * @param childName: 子复选框的统一名称
 * @param allSelectName: 全选复选框的名称
 * @param allSelectId: 全复选框的Id
 */
function checkSelect(childName, allSelectName, allSelectId) {
    var flag = 1;
    $("input[type='checkbox'][name='" + childName + "']").each(function(){
        if ($(this).prop("checked") == false) {
            flag = flag & 0;
        } else {
            flag = flag & 1;
        }
    });
    if (flag == 0) {
        $("input[name='" + allSelectName + "'][id='" + allSelectId + "']").removeAttr("checked");
    } else {
        $("input[name='" + allSelectName + "'][id='" + allSelectId + "']").prop("checked", true);
    }
}

/**
 * 全选复选框的响应函数
 * @param allSelectName: 全选复选框名称
 * @param allSelectId: 全选复选框Id
 * @param childCheckBoxName: 子复选框统一名称
 */
function selectAll(allSelectName, allSelectId, childCheckBoxName) {
    if ($("input[name=" + allSelectName + "][id='" + allSelectId + "']").prop("checked")) {
        $("[name='" + childCheckBoxName + "']").prop("checked",true);
    } else {
        $("[name='" + childCheckBoxName + "']").removeAttr("checked");
    }
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
