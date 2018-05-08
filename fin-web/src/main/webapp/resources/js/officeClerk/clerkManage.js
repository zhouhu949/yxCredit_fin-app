var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        /*参数注释*/
        param.realname=$.trim($("#search_name").val());
        param.tel=$.trim($("#search_tel").val());
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    //加载完成后给那三个隐藏的下拉选加上东西
    getPost();
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD ',
        min: '1960-01-01 ',
        max: laydate.now(),
        istime: true,
        istoday: false,
    };
    laydate(beginTime);
    var edit_manager_dateBath={
        elem: '#edit_manager_dateBath',
        format: 'YYYY-MM-DD',
        min: '1960-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
    }
    laydate(edit_manager_dateBath);
    if(g_userManage.tableCustomer){
        g_userManage.tableCustomer.ajax.reload();
    }else{
        g_userManage.tableCustomer = $('#sign_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "destroy":true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('officeClerk/getAllManagers', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                }, "application/json");
            },
            "order": [],
            "columns": [
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },
                {"data": "realname","orderable" : false},
                {"data": "branch","orderable" : false},
                {"data": "post","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "idcard", "orderable": false},//身份证号
                {"data": "employeeNum", "orderable": false},//工号也就是账号
                {"data": "activationState", "orderable": false,"render":function (data, type, row, meta) {
                    if(data=="1"){
                        return "已激活";
                    }
                    if(data=="0"){
                        return "冻结中";
                    }
                }},//办单员状态
                {"data": "null", "orderable": false, "defaultContent":""}//操作
            ],
             "createdRow": function ( row, data, index,settings,json ) {
                 //查看详细信息按钮
                 var btnFindInformation=$('<a class="tabel_btn_style" onclick="config(\''+data.id+'\')"> 查看 </a>');
                 //编辑个人信息按钮
                 var btnEditInformation=$('<a class="tabel_btn_style" onclick="configEditManager(\''+data.id+'\')"> 编辑</a>');
                 //查看和修改分管商户按钮
                 var btnFindSogo=$('<a class="tabel_btn_style" onclick="showSalesmanMerchant(\''+data.id+'\')"> 商户明细 </a>');
                 var btnAddSoga=$('<a class="tabel_btn_style" onclick="addMerchantToSalesman(\''+data.id+'\',\''+data.activationState+'\')"> 添加商户 </a>');
                 //查看订单
                 var btnFindOrder=$('<a class="tabel_btn_style" onclick="showSalesmanOrders(\''+data.id+'\',\''+data.realname+'\')"> 订单查看 </a>');
                 //重置密码
                 var btnResertPasswd=$('<a class="tabel_btn_style" onclick="resertPasswd(\''+data.id+'\')"> 密码重置 </a>');
                 // var btnChangePasswd=$('<a class="tabel_btn_style" onclick="alert(222)">修改</a>');
                 //激活冻结按钮
                 if(data.activationState=="0"){
                     var btnDongjie=$('<a class="tabel_btn_style" onclick="changeState(\''+data.id+'\',\''+1+'\')"> 激活</a>');//激活就是把状态改成1
                 }else{
                     var btnDongjie=$('<a class="tabel_btn_style" onclick="changeState(\''+data.id+'\',\''+0+'\')">冻结</a>');//冻结就是把状态改成0
                 }
                 //添加按钮
                 $("td", row).eq(8).append(btnFindInformation).append(' ').append(btnEditInformation).append(' ').append(btnFindSogo).append(' ').append(btnAddSoga)
                     .append(btnFindOrder).append(' ').append(btnResertPasswd).append('').append(btnDongjie);
            },
            "initComplete" : function(settings,json) {
                $("#customer_list").delegate( 'tbody tr td:not(:last-child)','click',function(e){
                    $("#cus_order").show();
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
                    $("#customerId").val(id);
                    //queryCusOrderList();
                });
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableCustomer.ajax.reload();
                });
                //添加办单员
                $("#btn_add_clerkmanager").click(function() {
                    layer.open({
                        type : 1,
                        title : '添加办单员模板',
                        maxmin : true,
                        shadeClose : false,
                        area : [ '80%', '90%' ],
                        content : $('#div_add_clerkmanager'),
                        btn : [ '保存', '取消' ],
                        success : function(index, layero) {
                            //清空输入框
                            $('#office_clerk_name').val('');
                            $("#office_clerk_sex").val('');
                            $("#office_clerk_tel").val('');
                            $("#office_clerk_birth").val('');
                            $("#idcard").val('');
                            $("#idcard_address").val('');
                            $("#now_address").val("");
                            $("#deptPname").val('');
                            $("#psot").val('');
                            $("#beginTime").val("");
                            $("#employee_num").val('');//办单员账号(办单员工号)
                            //存图片地址的隐藏域要清空
                            $("#cardImageZhengMian").val("");  //身份证正面照片
                            $("#cardImageFanMian").val("");    //身份证反面照片
                            $("#cardImageShouChi").val("");    //手持身份证照片
                            $("#cardImageQiTa").val("");       //其他照片
                            //图片先设置成默认的+
                            $("#show_fanmian_img").attr("src","../resources/images/photoadd.png");
                            $("#show_zhengmian_img").attr("src","../resources/images/photoadd.png");
                            $("#show_shouchi_img").attr("src","../resources/images/photoadd.png");
                            $("#show_qita_img").attr("src","../resources/images/photoadd.png");

                        },
                        yes:function(index,layero){
                            var param={};
                            //传入后台参数
                            param.realname=$.trim($('#office_clerk_name').val());
                            param.sex=$.trim($("#office_clerk_sex").val());
                            param.tel=$.trim($("#office_clerk_tel").val());
                            param.dateBath=$.trim($("#beginTime").val());
                            param.idcard=$.trim($("#idcard").val());
                            param.idcardAddress=$.trim($("#idcard_address").val());
                            param.nowAddress=$.trim($("#now_address").val());
                            //部门deptPid
                            param.branch=$.trim($("#deptPid").val());
                            param.post=$.trim($("#psot").val());
                            param.employeeNum=$.trim($("#employee_num").val());//办单员工号(办单员账号)

                            //图片阿里云地址信息
                            param.cardImageZhengMian=$("#cardImageZhengMian").val();//身份证正面照片
                            param.cardImageFanMian=$("#cardImageFanMian").val();    //身份证反面照片
                            param.cardImageShouChi=$("#cardImageShouChi").val();    //手持身份证照片
                            param.cardImageQiTa=$("#cardImageQiTa").val();          //其他照片
                            console.log(param);
                            //在此处做字符串验证，先简单做之后再改
                            if(param.realname==''){
                                layer.msg("姓名不能为空",{time:2000});return
                            }
                            if(param.sex==''){
                                layer.msg("性别不能为空",{time:2000});return
                            }
                            if(!(/^1[3|7|4|5|8][0-9]\d{4,8}$/).test(param.tel)){
                                layer.msg("电话号码为空或电话号码格式不正确",{time:2000});return
                            }
                            if(param.dateBath==''||param.dateBath==null){
                                layer.msg("出生日期为必填项",{time:2000});return
                            }
                            if(!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(param.idcard))){
                                layer.msg("身份证号码未输入或者格式不正确",{time:2000});return
                            }
                            if(param.idcardAddress==''){
                                layer.msg("身份证地址不能为空",{time:2000});return
                            }
                            if(param.nowAddress==''){
                                layer.msg("现居住地址不能为空",{time:2000});return
                            }
                            if(param.branch==''){
                                layer.msg("所在部门不能为空",{time:2000});return
                            }
                            if(param.post==''){
                                layer.msg("岗位不能为空",{time:2000});return
                            }
                            if(param.employeeNum==''){
                                layer.msg("账号不能为空",{time:2000});return
                            }
                            if($("#cardImageZhengMian").val()==''){
                                layer.msg("身份证正面照片不能为空~",{time:2000});return
                            }
                            if($("#cardImageFanMian").val()==''){
                                layer.msg("身份证反面照片不能为空~",{time:2000});return
                            }
                            if($("#cardImageShouChi").val()==''){
                                layer.msg("手持身份证照片不能为空~",{time:2000});return
                            }
                            Comm.ajaxPost('officeClerk/addOfficeClerkManager',JSON.stringify(param), function (data) {
                                    if(data.code==0){
                                        layer.msg(data.msg,{time:2000},function(){
                                            layer.closeAll();
                                            //添加之后刷新页面
                                            g_userManage.tableCustomer.ajax.reload();
                                        })
                                    }
                                    if(data.code==1){
                                        layer.msg(data.msg,{time:2000});
                                    }
                                },"application/json"
                            );
                        }
                    });
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableCustomer.on("order.dt search.dt", function() {
            g_userManage.tableCustomer.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
});
//查看办单员个人信息函数模板
function config(id) {
    getSalesmanImgs(id);
    layer.open({
        type : 1,
        title : '办单员个人信息',
        maxmin : true,
        shadeClose : false,
        area : ['80%', '90%'],
        content : $('#show_manager'),
        success : function(index, layero) {
            var param = {};
            param.id = id;
            $('input[name="sms_rule"]').attr("checked",false);
            Comm.ajaxPost('officeClerk/getOneManagerById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        //console.log(data);
                        $("#realname").val(data.data.realname);
                        $("#sex_name").val(data.data.sexName);
                        $("#tel").val(data.data.tel);
                        $("#date_bath").val(data.data.dateBath);
                        $("#idcard_show").val(data.data.idcard);
                        $("#idcard_address_show").val(data.data.idcardAddress);
                        $("#now_address_show").val(data.data.nowAddress);
                        $("#branch_show").val(data.data.branch);
                        $("#chakan_post_show").val(data.data.postId);
                        //工号也就是账号
                        $("#employee_num_show").val(data.data.employeeNum);
                        if(data.data.activationState =="0"){
                            $("#activation_state").val("未激活");
                        }else {
                            $("#activation_state").val("已激活");
                        }
                    }
                },"application/json"
            );
        }
    });
}
/*在查看办单员弹窗的时候，查询办单员和图片的关联表数据，取出图片地址*/
function  getSalesmanImgs(id) {
    var param={};
    param.salesmanId=id;
    $("#zhengmian").attr("src","../resources/images/photoadd.png");
    $("#fanmian").attr("src","../resources/images/photoadd.png");
    $("#shouchi").attr("src","../resources/images/photoadd.png");
    $("#qita").attr("src","../resources/images/photoadd.png");
    Comm.ajaxPost('officeClerk/getSalesmanImgsController',JSON.stringify(param), function (data) {
        var obj;
        for(i=0;i<data.data.length;i++){
            obj=(data.data)[i];
            if(obj.imgType=='0'){
                $("#zhengmian").attr("src",obj.imgUrl);
            }
            if(obj.imgType=='1'){
                $("#fanmian").attr("src",obj.imgUrl);
            }
            if(obj.imgType=='2'){
                $("#shouchi").attr("src",obj.imgUrl);
            }
            if(obj.imgType=='3'){
                $("#qita").attr("src",obj.imgUrl);
            }
        }
    },"application/json");
}
/*在编辑办单员弹窗的时候，查询办单员和图片的关联表数据，取出图片地址*/
function  getEditSalesmanImgs(id) {
    var param={};
    param.salesmanId=id;
        //存图片地址的隐藏域要清空
        $("#cardImageZhengMian").val("");  //身份证正面照片
        $("#cardImageFanMian").val("");    //身份证反面照片
        $("#cardImageShouChi").val("");    //手持身份证照片
        $("#cardImageQiTa").val("");       //其他照片
        //图片先设置成默认的+
        $("#edit_zhengmian").attr("src","../resources/images/photoadd.png");
        $("#edit_fanmian").attr("src","../resources/images/photoadd.png");
        $("#edit_shouchi").attr("src","../resources/images/photoadd.png");
        $("#edit_qita").attr("src","../resources/images/photoadd.png");
    Comm.ajaxPost('officeClerk/getSalesmanImgsController',JSON.stringify(param), function (data) {
        var obj;
        for(i=0;i<data.data.length;i++){
            obj=(data.data)[i];
            if(obj.imgType=='0'){
                $("#edit_zhengmian").attr("src",obj.imgUrl);
                $("#edit_cardImageZhengMian").val(obj.imgUrl)//此时向该隐藏域填入正面照片的阿里云地址
            }
            if(obj.imgType=='1'){
                $("#edit_fanmian").attr("src",obj.imgUrl);
                $("#edit_cardImageFanMian").val(obj.imgUrl)//此时向该隐藏域填入反面照片的阿里云地址
            }
            if(obj.imgType=='2'){
                $("#edit_shouchi").attr("src",obj.imgUrl);
                $("#edit_cardImageShouChi").val(obj.imgUrl)//此时向该隐藏域填入手持照片的阿里云地址
            }
            if(obj.imgType=='3'){
                $("#edit_qita").attr("src",obj.imgUrl);
                $("#edit_cardImageQiTa").val(obj.imgUrl)//此时向该隐藏域填入其他照片的阿里云地址
            }
        }
    },"application/json");
}
// 编辑办单员详细信息模板
function configEditManager(id) {
    getEditSalesmanImgs(id);
    layer.open({
        type : 1,
        title : '编辑办单员模板',
        maxmin : true,
        shadeClose : false,
        area : [ '80%', '90%'  ],
        content : $('#edit_manager'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;
            //先查询并展示出来信息以供修改
            Comm.ajaxPost('officeClerk/getOneManagerById',JSON.stringify(param), function (data) {
                if(data.code==0){
                        $('#edit_realname').val(data.data.realname);
                        $('#edit_sex').val(data.data.sex);
                        $('#edit_tel').val(data.data.tel);
                        $('#edit_manager_dateBath').val(data.data.dateBath);
                        $('#edit_idcard_show').val(data.data.idcard);
                        $('#edit_idcard_address_show').val(data.data.idcardAddress);
                        $('#edit_now_address_show').val(data.data.nowAddress);
                        $('#edit_deptPname').val(data.data.branch);//部门
                        //在此处添加办单员部门id
                        $('#edit_deptPid').val(data.data.branchId)
                        $('#edit_post_show').val(data.data.postId);//岗位
                        //工号(账号)
                        $('#edit_employee_num_show').val(data.data.employeeNum);
                        $('#edit_employee_num_show').attr("readonly","readonly");
                        //假如是测试办单员,那么手机号和姓名是无法更改的
                        if(data.data.id == 'c6891e91-6ecf-403d-8fe8-c20a63249686'){
                            $('#edit_realname').attr("disabled",true);
                            $('#edit_tel').attr("disabled",true);
                        }else{
                            $("#edit_realname").attr("disabled",false);
                            $("#edit_tel").attr("disabled",false);
                        }
                }
                },"application/json"
            );
        },
        yes:function(index,layero){
            var param={};
            param.id=id;
            param.realname = $('#edit_realname').val();
            param.sex=$('#edit_sex').val();
            param.tel = $('#edit_tel').val();
            param.dateBath = $('#edit_manager_dateBath').val();
            param.idcard = $('#edit_idcard_show').val();
            param.idcardAddress =$('#edit_idcard_address_show').val();
            param.nowAddress = $('#edit_now_address_show').val();
            param.branch =$('#edit_deptPid').val();//岗位
            param.post =$('#edit_post_show').val();//部门
            //工号也是账号
            param.employeeNum =$('#edit_employee_num_show').val();
            param.activationState = $('#edit_activation_state').val();

            //图片阿里云地址信息
            param.zhengmian=$("#edit_cardImageZhengMian").val();//身份证正面照片
            param.fanmian=$("#edit_cardImageFanMian").val();    //身份证反面照片
            param.shouchi=$("#edit_cardImageShouChi").val();    //手持身份证照片
            param.qita=$("#edit_cardImageQiTa").val();          //其他照片

            //在这里做字符串验证
            if(param.realname==''){
                layer.msg("姓名不能为空",{time:2000});return
            }
            if(param.sex==''){
                layer.msg("性别不能为空",{time:2000});return
            }
            if(!(/^1[3|4|5|8][0-9]\d{4,8}$/).test(param.tel)){
                layer.msg("电话号码为空或电话号码格式不正确",{time:2000});return
            }
            if(param.dateBath==''||param.dateBath==null){
                layer.msg("出生日期为必填项",{time:2000});return
            }
            if(!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(param.idcard))){
                layer.msg("身份证号码未输入或者格式不正确",{time:2000});return
            }
            if(param.idcardAddress==''){
                layer.msg("身份证地址不能为空",{time:2000});return
            }
            if(param.nowAddress==''){
                layer.msg("现居住地址不能为空",{time:2000});return
            }
            if(param.branch==''){
                layer.msg("所在部门不能为空",{time:2000});return
            }
            if(param.post==''){
                layer.msg("岗位不能为空",{time:2000});return
            }
            if(param.employeeNum==''){
                layer.msg("工号不能为空",{time:2000});return
            }
            Comm.ajaxPost('officeClerk/updateOfficeClerkManagerById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:2000},function(){
                            layer.closeAll();
                            //更改单个办单员信息之后刷新页面
                            g_userManage.tableCustomer.ajax.reload();
                        })
                    }
                if(data.code==1){
                    layer.msg(data.msg,{time:2000});
                }
                },"application/json"
            );
        }
    });
}
//冻结解冻按钮changeState
function changeState(id,state) {
    var param = {
        id: id,
        activationState:state
    }
    Comm.ajaxPost(
        'officeClerk/changeStateById', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableCustomer.ajax.reload();
            });
        }, "application/json"
    );

}
//重置密码
function resertPasswd(id) {
    var param = {
        id: id
    }
    Comm.ajaxPost(
        'officeClerk/resertPasswdById', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableCustomer.ajax.reload();
            });
        }, "application/json"
    );

}
//查询办单员担保的商户的对象/
var g_salesmanMerchant = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.salesmanId=$("#salesmanId").val();
        // param.employeeNum=$("#employeeNum").val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//查看办单员关联商户 传入办单员id
function showSalesmanMerchant(id){
    $("#salesmanId").val(id);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : "分管门店",
        shadeClose : false,
        area : [ '1100px', '800px' ],
        content : $("#salesmanMerchantList"),
        success : function(index, layero) {
            getSalesmanMerchantList()
        }
    })
}
//获取办单员关联的商户
function getSalesmanMerchantList() {
    if(!g_salesmanMerchant.tableCusOrder) {
        g_salesmanMerchant.tableCusOrder = $('#meichant_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "destroy":true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_salesmanMerchant.getQueryCondition(data);
                Comm.ajaxPost('officeClerk/getSalesmanMerchants', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },
                {"data": "id","orderable" : false},//商户编号
                {"data": "merName","orderable" : false},//商户简称
                {"data": "mainBusiness", "orderable": false},//主营业务 ，先设置成这样，最后需要改动
                {"data": "merDetailAddress","orderable" : false},//营业地址
                {"data": "applyName","orderable" : false},//申请人,这里填写的是法人的名字
                {"data": "state","orderable" : false,       //商户状态
                        "render": function (data, type, row, meta) {
                            if(data=="1"){
                                return "发布中";
                            }else if(data=="0") {
                                return "冻结中";
                            }else{
                                return '';
                            }
                        }
                    },
                {"data": "relState", "orderable": false,
                    "render": function (data, type, row, meta) {
                        if(data=="1"){
                            return "已解绑";
                        }else if(data=="0") {
                            return "绑定中";
                        }
                    }
                },//关联状态
                {"data": "null", "orderable": false, "defaultContent":""}//操作
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                //解绑和重新绑定按钮
               if(data.relState =="0"){
                   var btnRemovebinding=$('<a class="tabel_btn_style" onclick="btnRemovebinding(\''+data.id+'\')"> 解除绑定 </a>');
                   $("td", row).eq(8).append(btnRemovebinding);
               }else if(data.relState =="1"){
                   var btnBindingAgain=$('<a class="tabel_btn_style" onclick="btnBindingAgain(\''+data.id+'\')"> 再次绑定 </a>');
                   $("td", row).eq(8).append(btnBindingAgain);
               }else {
                   $("td", row).eq(8).append($("<label>"+data.relState+"</label>"))
               }

            },
            "initComplete" : function(settings,json) {
                //绑定函数在此处
                // // //查询按钮触发
                // $("#btn_search_order").click(function(){
                //     g_salesmanMerchant.tableCusOrder.ajax.reload();
                // });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_salesmanMerchant.tableCusOrder.on("order.dt search.dt", function() {
            g_salesmanMerchant.tableCusOrder.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }else {
        g_salesmanMerchant.tableCusOrder.ajax.reload();
    }
}
//解除绑定按钮(办单员商户解除绑定)
function btnRemovebinding(id){
    var param = {
        //办单员的id
        salesmanId:$("#salesmanId").val(),
        //商户的id
        merchantId: id
    }
    Comm.ajaxPost(
        'officeClerk/removebindingSalesmanMerchant', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                //layer.closeAll();
                g_salesmanMerchant.tableCusOrder.ajax.reload();
            });
        }, "application/json"
    );
}
//再次绑定按钮
function btnBindingAgain(id){
    var param = {
        //办单员的id
        salesmanId:$("#salesmanId").val(),
        //商户的id
        merchantId: id
    }
    Comm.ajaxPost(
        'officeClerk/salesmanRelationMerchantAgain', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                //layer.closeAll();
                g_salesmanMerchant.tableCusOrder.ajax.reload();
            });
        }, "application/json"
    );
}
//给办单员添加商户
function  addMerchantToSalesman(id,activationState) {
    if(activationState == '0'){
        layer.msg("办单员冻结中，无法操作",{time:1500});
        return false;
    }
    $("#salesmanId_merchant").val(id);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : "添加门店",
        shadeClose : false,
        area : [ '1100px', '800px' ],
        content : $("#salesmanCanWorkMerchantList"),
        success : function(index, layero) {
            addMerchantToSalesmanList()
        }
    })
}
//查询可以被办单员管理的门店对象
var g_canBesalesmanMerchant = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.salesmanId=$("#salesmanId_merchant").val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
//获取可以被办单员管理的商户
function addMerchantToSalesmanList() {
    if(!g_canBesalesmanMerchant.tableCusOrder) {
        g_canBesalesmanMerchant.tableCusOrder = $('#officeClerkManager_meichant_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "destroy":true,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_canBesalesmanMerchant.getQueryCondition(data);
                Comm.ajaxPost('officeClerk/getAllCanBeSalesmanManagerMerchants', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json");
            },
            "order": [],
            "columns": [
                {
                    "className" : "cell-operation",
                    "data": null,
                    "defaultContent":"",
                    "orderable" : false,
                    "width" : "30px"
                },
                {"data": "id","orderable" : false},//商户编号
                {"data": "merName","orderable" : false},//商户简称
                {"data": "mainBusiness", "orderable": false},//主营业务 ，先设置成这样，最后需要改动
                {"data": "merDetailAddress","orderable" : false},//营业地址
                {"data": "applyName","orderable" : false},//申请人,这里填写的是法人的名字
                {"data": "state","orderable" : false,       //状态
                    "render": function (data, type, row, meta) {
                        if(data=="1"){
                            return "发布中";
                        }else if(data=="0") {
                            return "冻结中";
                        }
                    }
                },
                {"data": "null", "orderable": false, "defaultContent":""}//操作
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                //给办单员绑定的按钮
                var btnBinding=$('<a class="tabel_btn_style" onclick="binding(\''+data.id+'\')"> 绑定 </a>');
                $("td", row).eq(7).append(btnBinding);
            },
            "initComplete" : function(settings,json) {
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_canBesalesmanMerchant.tableCusOrder.on("order.dt search.dt", function() {
            g_canBesalesmanMerchant.tableCusOrder.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }else {
        g_canBesalesmanMerchant.tableCusOrder.ajax.reload();
    }
}
//绑定商户给办单员
function  binding(id) {
    var param = {
        //办单员的id
        salesmanId:$("#salesmanId_merchant").val(),
        //商户的id
        merchantId: id
    }
    Comm.ajaxPost(
        'officeClerk/salesmanRelationMerchant', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                //layer.closeAll();
                g_canBesalesmanMerchant.tableCusOrder.ajax.reload();
            });
        }, "application/json"
    );
}
//添加模块 点击所属部门，加载树
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
//修改模块 点击所属部门，加载菜单树
var editShowDeptZtree = function(){
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
                $('#edit_deptPname').val(deptName);
                $('#edit_deptPid').val(id);
            })
        },
        yes: function () {
            layer.close(deptTree);
        }
    });
};
//获取岗位列表动态加载岗位下拉选
function getPost(){
    var paramFilter={};
    Comm.ajaxPost('station/getAllStations', JSON.stringify(paramFilter), function (result) {
        //console.log(result.data);
        for(i=0;i<result.data.length;i++){
        var opt='<option class="tabel_btn_style" style="font-size: 10px;" value="'+(result.data[i]).id+'">'+(result.data[i]).stationName+'</option>';
            //查询办单员岗位的下拉选
            $("#chakan_post_show").append(opt);
            //添加办单员，岗位的下拉选
            $("#psot").append(opt);
            //编辑办单员，岗位的下拉选
            $("#edit_post_show").append(opt);
        }
    });
}
//格式化时间
function getTime(inputTime) {
    var y,m,d,h,mi,s;
    if(inputTime) {
        y = inputTime.slice(0,4);
        m = inputTime.slice(4,6);
        d = inputTime.slice(6,8);
        h = inputTime.slice(8,10);
        mi = inputTime.slice(10,12);
        s = inputTime.slice(12);
        return y+'-'+m+'-'+d+" "+h+":"+mi+":"+s;
    }
}
//查询订单的对象
var g_salesmanOrders = {
    tableCusOrder : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.empId=$("#employeeNum").val();
        param.tel=$.trim($("#search_tel_order").val());
        param.customerName=$.trim($("#search_name_order").val());

        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/***********订单模块方法***********/ //疼爱
function showSalesmanOrders(empId,name){
    $("#employeeNum").val(empId);
    layer.open({
        type : 1,
        title : false,
        maxmin : true,
        title : name+"的订单",
        shadeClose : false,
        area : [ '1100px', '800px' ],
        content : $("#salesmanOrdersList"),
        success : function(index, layero) {
            if(!g_salesmanOrders.tableCusOrder) {
                g_salesmanOrders.tableCusOrder = $('#salesmanOrdersListTable').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "destroy":true,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_salesmanOrders.getQueryCondition(data);
                        Comm.ajaxPost('officeClerk/getOrdersFromSalesman', JSON.stringify(queryFilter), function (result) {
                            console.log(result);
                            //封装返回数据
                            var returnData = {};
                            var resData = result.data.list;
                            var resPage = result.data;
                            returnData.draw = data.draw;
                            returnData.recordsTotal = resPage.total;
                            returnData.recordsFiltered = resPage.total;
                            returnData.data = resData;
                            callback(returnData);
                        },"application/json");
                    },
                    "order": [],
                    "columns": [
                        {
                            "className" : "cell-operation",
                            "data": null,
                            "defaultContent":"",
                            "orderable" : false,
                            "width" : "30px"
                        },
                        {"data": "orderNo","orderable" : false},//订单编号
                        {"data": "customerName","orderable" : false},//客户姓名
                        {"data": "tel","orderable" : false},//客户电话号码
                        {"data": "card", "orderable": false},//证件号
                        {"data": "creatTime","orderable" : false,"render":function (data, type, row, meta) {
                            if(data !=null && data !=''){
                                return getTime(data);
                            }else {
                                return '';
                            }

                        }},//创建时间
                        {"data": "productTypeName","orderable" : false},//秒付产品
                        {"data": "state","orderable" : false,"render":function (data, type, row, meta) {
                            if(data=="1"){
                                return "借款申请";
                            }
                            if(data=="2"){
                                return "自动化审批通过";
                            }
                            if(data=="3"){
                                return "自动化审批拒绝";
                            }
                            if(data=="4"){
                                return "自动化审批异常转人工";
                            }
                            if(data=="5"){
                                return "人工审批通过";
                            }
                            if(data=="6"){
                                return "人工审批拒绝";
                            }
                            if(data=="7"){
                                return "合同确认";
                            }
                            if(data=="8"){
                                return "放款成功";
                            }
                            if(data=="9"){
                                return "结清";
                            }
                            if(data=="10"){
                                return "关闭";
                            }
                        }},
                        {"data": "null", "orderable": false, "defaultContent":""}//操作
                    ],
                    "createdRow": function ( row, data, index,settings,json ) {
                            var btn_xiangxixinxi=$('<a class="tabel_btn_style" onclick="showOrder(\''+data.id+'\',\''+data.customerId+'\')"> 详细信息 </a>');
                            $("td", row).eq(8).append(btn_xiangxixinxi);
                    },
                    "initComplete" : function(settings,json) {
                        //绑定函数在此处
                        //查询按钮触发
                        $("#btn_search_order").click(function(){
                            g_salesmanOrders.tableCusOrder.ajax.reload();
                        });
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_salesmanOrders.tableCusOrder.on("order.dt search.dt", function() {
                    g_salesmanOrders.tableCusOrder.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
            }else {
                g_salesmanOrders.tableCusOrder.ajax.reload();
            }
        }
    })
}

//订单弹出窗搜索重置
function searchResetOrder(){
    $("#search_name_order").val("");
    $('#search_tel_order').val("");
}
//订单详细信息
function showOrder(orderId,customerId){
    var url = "/finalAudit/examineDetails?orderId="+orderId+"&customerId="+customerId;
    layer.open({
        type : 2,
        title : '查看订单及客户资料',
        area : [ '100%', '100%' ],
        btn : [ '取消' ],
        content:_ctx+url
    });
}
//搜索重置
function searchReset(){
    $("#search_name").val("");
    $('#search_tel').val("");
}
$(function(){
    $("#btn_reset").click(function () {
        $("#search_name").val('');
        $("#search_tel").val('');
        g_userManage.tableCustomer.ajax.reload();
    });
    $("#btn_reset_order").click(function () {
        $("#search_name_order").val('');
        $("#search_tel_order").val('');
        g_salesmanOrders.tableCusOrder.ajax.reload();
    });
})
