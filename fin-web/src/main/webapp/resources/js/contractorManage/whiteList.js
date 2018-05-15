/**
 * Created by Administrator on 2017/5/2 0002.
 */
var g_whiteListManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_whiteListManage.fuzzySearch;
        if (g_whiteListManage.fuzzySearch) {
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
    var beginTime = {
        elem: '#beginTime',
        /*format: 'YYYY-MM-DD hh:mm:ss',*/
        format: 'YYYY-MM-DD',
        max: laydate.now(),
        istime: true,
        istoday: false,
       /* choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }*/
    };
    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD',
        min: laydate.now(),
        istime: true,
        istoday: false,
       /* choose: function(datas){
            beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }*/
    };
    var latestPayday = {
        elem: '#latestPayday',
        format: 'YYYY-MM-DD',
       /* min: laydate.now(),*/
        istime: true,
        istoday: false,
        /* choose: function(datas){
             beginTime.max = datas; //结束日选好后，重置开始日的最大日期
         }*/
    };
    laydate(beginTime);
    laydate(endTime);
    laydate(latestPayday);


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
            }

        },"application/json"
    );
    g_whiteListManage.tableUser = $('#White_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_whiteListManage.getQueryCondition(data);
            Comm.ajaxPost('contractorManage/whiteListPage',JSON.stringify(queryFilter),function(result){
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
            {"data":'realName',"searchable":false,"orderable" : false},
            {"data":'card',"searchable":false,"orderable" : false},
            {"data":'telphone',"searchable":false,"orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.job==1){
                        return '包工头'
                    }else if(data.job==2){
                        return '农民工'
                    } else if(data.job==3){
                        return '临时工'
                    }
                }
            },


            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.whiteStatus==1){
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
            var btnDel = $('<a class="tabel_btn_style" onclick="updateWhite(0,\''+data.id+'\')">修改</a>');
            $('td', row).eq(7).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_whiteListManage.fuzzySearch = true;
                g_whiteListManage.tableUser.ajax.reload(function(){
                    $("#userCheckBox_All").attr("checked",false);
                });
            });
            loadContractorType1();
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
                g_whiteListManage.fuzzySearch = true;
                g_whiteListManage.tableUser.ajax.reload(function(){
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
    g_whiteListManage.tableUser.on("order.dt search.dt", function() {
        g_whiteListManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});



//当前用户下总包商下拉框
function loadContractorType(){
    $.ajax({
        type : "POST",
        url : "findContractorByRoleId",
        data : {
            parentClassId : 1,
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

//当前用户下总包商下拉框
function loadContractorType1(){
    $.ajax({
        type : "POST",
        url : "findContractorByRoleId",
        data : {
            parentClassId : 1,
        },
        async : false,
        dataType : 'json',
        success : function(data) {
            var html="";
            html+="<option value=''>请选择</option>";
            $.each(data.data,function(index,result) {
                html+="<option value="+result.id+">"+result.contractorName+"</option>"
            });
            $("select[name='proSeriesName']").html(html);

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

//添加、编辑白名单信息
function updateWhite(sign,id) {
    loadContractorType();
    if(sign==0){
        Comm.ajaxPost('contractorManage/whiteListDetail',id,function(data){
            layer.closeAll();
            debugger
            var whiteList = data.data;
            $("#realName").val(whiteList.realName);
            $("#card").val(whiteList.card);
            $("#telphone").val(whiteList.telphone);
            $("#jobType").val(whiteList.jobType);
            $("#latestPay").val(whiteList.latestPay);
            $("#contractorId option[value=" + whiteList.contractorId + "]").attr("selected","selected");
            $("#job option[value=" + whiteList.job + "]").attr("selected","selected");
            $("#payType option[value=" + whiteList.payType + "]").attr("selected","selected");
            $("#beginTime").val(whiteList.contractStartDate);
            $("#endTime").val(whiteList.contractEndDate);
            $("#latestPayday").val(whiteList.latestPayday);
            $("#localMonthlyMinWage").val(whiteList.localMonthlyMinWage);
          //  $("#organ").attr('disabled',true);
            /*if(whiteList.state==1){
                $("#qiyong").attr('selected','selected');
            }else{
                $("#jinyong").attr('selected','selected');
            }*/
           // $("#remark").val(user.remark);
            layer.open({
                type : 1,
                title : '修改白名单',
                area : [ '576px', '370px' ],
                content : $('#Add_user_style'),
                btn : [ '保存', '取消' ],
                yes : function(index, layero) {
                    /*if ($('input[name="contractor_name"]').val() == "") {
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
                    }*/
                    var realName=$('input[name="real_name"]').val();
                    var card=$('input[name="card"]').val();
                    var latestPay=$('input[name="latest_pay"]').val();
                    var telphone=$('input[name="tel_phone"]').val();
                    var jobType=$('input[name="job_type"]').val();
                    var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                    if(!mobileReg.test(telphone)){
                        layer.msg("手机号码格式不正确",{time:2000});
                        return;
                    }
                    var contractStatus=$("#contractStatus").val();
                    var localMonthlyMinWage=$("#localMonthlyMinWage").val();
                    var contractorId = $("#contractorId option:selected").val();
                    var job = $("#job option:selected").val();
                    var payType = $("#payType option:selected").val();

                    var beginTime = $("#beginTime").val();
                    if(beginTime != null && beginTime != ''){
                        beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
                    }
                    var endTime = $("#endTime").val();
                    if(endTime != null && endTime != ''){
                        endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
                    }
                    var latestPayday = $("#latestPayday").val();
                    if(latestPayday != null && latestPayday != ''){
                        latestPayday = latestPayday.replace(/[^0-9]/ig,"");//字符串去除非数字
                    }

                    var user={
                        id : id,
                        realName:realName,
                        card:card,
                        telphone :telphone,
                        jobType :jobType,
                        contractStatus:contractStatus,
                        contractorId:contractorId,
                        job:job,
                        payType:payType,
                        latestPay:latestPay,
                        contractStartDate:beginTime,
                        contractEndDate:endTime,
                        latestPayday:latestPayday,
                        localMonthlyMinWage:localMonthlyMinWage
                    };
                    Comm.ajaxPost(
                        'contractorManage/updateWhiteList',JSON.stringify(user),
                        function(data){
                            layer.closeAll();
                            layer.msg(data.msg,{time:2000},function () {
                                g_whiteListManage.tableUser.ajax.reload(function(){
                                });
                            });
                        }, "application/json"
                    );
                }
            });
        },"application/json","","","",false);
    }else{
        $("#isEdit").show();
        $('input[name="real_name"]').val("");
        $('input[name="card"]').val("");
        $('input[name="tel_phone"]').val("");
        $('input[name="contractor_credit"]').val("");
        $('input[name="local_monthly_min_wage"]').val("");
        $("#wu").attr('selected','selected');
        $("#bgt").attr('selected','selected');
        $("#xj").attr('selected','selected');
        layer.open({
            type : 1,
            title : '添加白名单',
            area : [ '600px', '370px' ],
            content : $('#Add_user_style'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                if ($('input[name="real_name"]').val() == "") {
                    layer.msg("姓名不能为空",{time:2000});
                    return;
                }
                if ($('input[name="tel_phone"]').val() == "") {
                    layer.msg("手机号码不能为空",{time:2000});
                    return;
                }
                var realName=$('input[name="real_name"]').val();
                var card=$('input[name="card"]').val();
                var jobType=$('input[name="job_type"]').val();
                var telphone=$('input[name="tel_phone"]').val();
                var mobileReg=/^1(3|4|5|7|8)\d{9}$/;//(1[34578])\\d{9}$
                if(!mobileReg.test(telphone)){
                    layer.msg("手机号码格式不正确~",{time:2000});
                    return;
                }
                var contractStatus=$("#contractStatus").val();
                var localMonthlyMinWage=$("#localMonthlyMinWage").val();
                var contractorId = $("#contractorId option:selected").val();
                var latestPay=$('input[name="latest_pay"]').val();
                var job = $("#job option:selected").val();
                var payType = $("#payType option:selected").val();

                var beginTime = $("#beginTime").val();
                if(beginTime != null && beginTime != ''){
                    beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
                }
                var endTime = $("#endTime").val();
                if(endTime != null && endTime != ''){
                    endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
                }
                var latestPayday = $("#latestPayday").val();
                if(latestPayday != null && latestPayday != ''){
                    latestPayday = latestPayday.replace(/[^0-9]/ig,"");//字符串去除非数字
                }

                var contractor={
                    realName: realName,
                    card:card,
                    telphone:telphone,
                    jobType:jobType,
                    contractStatus:contractStatus,
                    contractorId:contractorId,
                    job:job,
                    payType:payType,
                    latestPay:latestPay,
                    contractStartDate:beginTime,
                    contractEndDate:endTime,
                    latestPayday:latestPayday,
                    localMonthlyMinWage:localMonthlyMinWage

                };
                Comm.ajaxPost(
                    'contractorManage/addWhiteList',JSON.stringify(contractor),
                    function(data){
                        layer.closeAll();
                        layer.msg(data.msg,{time:2000},function () {
                            g_whiteListManage.tableUser.ajax.reload(function(){
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
                g_whiteListManage.fuzzySearch = true;
                g_whiteListManage.tableUser.ajax.reload(function(){
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
    // var selectArray = $("#User_list tbody input:checked");
    // if(!selectArray || selectArray.length==0){
    //     layer.msg("请选择用户");
    //     return;
    // }
    // var userIds = new Array();
    // $.each(selectArray,function(i,e){
    //     var val = $(this).val();
    //     userIds.push(val);
    // });
    // if(userIds.length==0){
    //     return;
    // }
    layer.confirm('是否删除用户？', {
        btn : [ '确定', '取消' ]
    }, function() {
        Comm.ajaxPost(
            'user/delete', JSON.stringify(userIds),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:2000},function () {
                    g_whiteListManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json");
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
                layer.msg(data.msg,{time:2000},function () {
                    g_whiteListManage.tableUser.ajax.reload(function(){
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
