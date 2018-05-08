var g_userManage = {
    tableUser:null,
    tableCustomer : null,
    tableFollow : null,
    currentItem : null,
    fuzzySearch : false,
    userSearch:false,
    isFollow:false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if(!g_userManage.isFollow){//展开跟进记录不需要传state
            param.state = 0;//状态（0-意向客户，1-准客户，2-发送，3-成功客户）
        }
        if (g_userManage.fuzzySearch) {
            param.personName = $("input[name='personName']").val();
            var beginTime = $("#beginTime").val();
            if(beginTime != null && beginTime != ''){
                param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
            var endTime = $("#endTime").val();
            if(endTime != null && endTime != ''){
                param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
            /*if($("#onlyMe").attr("checked")){
             param.empId = $("input[name='empId']").val();
             }*/
        }
        if(g_userManage.userSearch){
            param.trueName=$("input[name='userName']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(beginTime);
    laydate(endTime);
    g_userManage.isFollow = false;
    g_userManage.fuzzySearch = false;
    if(g_userManage.tableCustomer){
        g_userManage.tableCustomer.ajax.reload();
    }else{
        g_userManage.tableCustomer = $('#customer_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('customer/list', JSON.stringify(queryFilter), function (result) {
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
                {"data": null ,"searchable":false,"orderable" : false},
                {"data": "id" ,'class':'hidden',"searchable":false,"orderable" : false},
                {"data": "personName","orderable" : false},
                {"data": "card","orderable" : false},
                {"data": "tel","orderable" : false},
                {"data": "company","orderable" : false},
                {"data": "branch","orderable" : false},
                {"data": "manager","orderable" : false},
                {"data": "creatTime","orderable" : false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {
                    "data": "null", "orderable": false,
                    "defaultContent":""
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                var str='<a class="tabel_btn_style" onclick="showFollow(\''+data.id+'\',\''+data.personName+'\')">跟进</a>';
                    str+='&nbsp;<a class="tabel_btn_style" onclick="updateCustomer(\''+data.id+'\')">开始进件</a>';
                $('td', row).eq(9).append(str);
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#btn_search").click(function() {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableCustomer.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='personName']").val("");
                    $("#beginTime").val("");
                    $("#endTime").val("");
                    g_userManage.fuzzySearch = false;
                    g_userManage.tableCustomer.ajax.reload();
                });
                //只看自己
                /*$("#onlyMe").click(function() {
                 if($("#onlyMe").attr("checked")){
                 g_userManage.fuzzySearch = true;
                 }else{
                 g_userManage.fuzzySearch = false;
                 }
                 g_userManage.tableOrder.ajax.reload();
                 });*/
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
//添加修改意向客户
function updateCustomer(id){
    var title="";
    if(id==null){
        title="添加意向客户";
        $("#star").hide();
    }
    else {
        title="编辑意向客户";
        $("#star").show();
    }
    var addLayer = layer.open({
        type : 1,
        title : title,
        maxmin : true,
        shadeClose : false,
        area : [ '450px', '400px' ],
        content : $('#addCustomer'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            $("#empName").val("");
            $("#custName").val("");
            $("#tel").val("");
            $("#idCard").val("");
            $("#remark").val("");
            if(id!=null){
                Comm.ajaxPost('customer/findOne', id, function (result) {
                    var cust = result.data;
                    $("#customerId").val(cust.id);
                    $("#empName").attr("empId",cust.empId);
                    $("#empName").attr("empNo",cust.empNumber);
                    $("#empName").val(cust.manager);
                    $("#custName").val(cust.personName);
                    $("#tel").val(cust.tel);
                    $("#idCard").val(cust.card);
                },"application/json");
            }
        },
        yes : function (index,layero) {
            var url = 'customer/addCustomer';
            var empId = $("#empName").attr("empId");
            var empNo = $("#empName").attr("empNo");
            var empName = $("#empName").val();
            if (empName == "") {
                layer.msg("业务员不能为空！",{time:2000});
                return;
            }
            var personName = $("#custName").val();
            if (personName == "") {
                layer.msg("客户名称不能为空！",{time:2000});
                return;
            }
            var tel = $("#tel").val();
            if (tel == "") {
                layer.msg("电话不能为空！",{time:2000});
                return;
            }
            var card = $("#idCard").val();
            var remark = $("#remark").val();
            var param = {
                empId:empId,
                empNumber:empNo,
                manager:empName,
                personName:personName,
                tel:tel,
                card:card,
                remark:remark
            }
            if(id!=null){
                url = 'customer/updateCustomer';
                param.id = $("#customerId").val();
                if (card == "") {
                    layer.msg("身份证不能为空！",{time:2000});
                    return;
                }
            }
            Comm.ajaxPost(url, JSON.stringify(param), function (result) {
                layer.msg(result.msg,{time:1000},function () {
                    layer.close(addLayer);
                    if(id!=null){
                        $('#customer_list').dataTable().fnDraw(false);
                    }else{
                        g_userManage.tableCustomer.ajax.reload();
                    }
                })
            },"application/json");
        }
    });
}
//选择业务员
function showUser() {
    var userLayer = layer.open({
        type : 1,
        title : "选择业务员",
        maxmin : true,
        shadeClose : false,
        area : [ '550px', '650px' ],
        content : $('#userDiv'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            g_userManage.userSearch = false;
            if(g_userManage.tableUser){
                g_userManage.tableUser.ajax.reload();
            }else{
                g_userManage.tableUser = $('#user_list').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_userManage.getQueryCondition(data);
                        Comm.ajaxPost('user/list', JSON.stringify(queryFilter), function (result) {
                            var returnData = {};
                            var resData = result.data;
                            var resPage = result.page;
                            returnData.draw = data.draw;
                            returnData.recordsTotal = resPage.resultCount;
                            returnData.recordsFiltered = resPage.resultCount;
                            returnData.data = resData;
                            callback(returnData);
                        },"application/json");
                    },
                    "order": [],
                    "columns": [
                        {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"className" : "childBox","orderable" : false,"data" : null,"width" : "30px","searchable":false,
                            "render" : function(data, type, row, meta) {
                                return '<input type="checkbox" value="'+data.userId+'" style="cursor:pointer;" isChecked="false">'
                            }
                        },
                        {"data": "empNo","orderable" : false},
                        {"data": "trueName","orderable" : false},
                        {"data": "organName","orderable" : false},
                    ],
                    "initComplete" : function(settings,json) {
                        //搜索
                        $("#search").click(function() {
                            g_userManage.userSearch = true;
                            g_userManage.tableUser.ajax.reload();
                        });
                        $("#user_list tbody").delegate( 'tr','click',function(e){
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
                                var userId = $(input).val();
                                var name = $(target.parentNode.children[3]).text();
                                var no = $(target.parentNode.children[2]).text();
                                $("#empName").val(name);
                                $("#empName").attr("empId",userId);
                                $("#empName").attr("empNo",no);
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
        }
    });
}
//展示跟进记录
function showFollow(id,name) {
    layer.open({
        type: 1,
        title: "客户"+name+"---跟进记录",
        maxmin: true,
        shadeClose: false,
        area: ['600px', '650px'],
        content: $('#followDiv'),
        success: function (index, layero) {
            g_userManage.isFollow = true;
            if(g_userManage.tableFollow){
                g_userManage.tableFollow.ajax.reload();
            }else{
                g_userManage.tableFollow = $('#follow_list').dataTable($.extend({
                    'iDeferLoading':true,
                    "bAutoWidth" : false,
                    "Processing": true,
                    "ServerSide": true,
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bLengthChange": false,
                    "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                    "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                        var queryFilter = g_userManage.getQueryCondition(data);
                        queryFilter.param.relId = id;
                        queryFilter.param.relType = 1;
                        Comm.ajaxPost('customer/followUpList', JSON.stringify(queryFilter), function (result) {
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
                        {"data": null ,"searchable":false,"orderable" : false},
                        {"data": "id" ,'class':'hidden',"searchable":false,"orderable" : false},
                        {"data": "type","orderable" : false},
                        {"data": "followDate","orderable" : false},
                        {"data": "createTime","orderable" : false,
                            "render": function (data, type, row, meta){
                                return formatTime(data);
                            }
                        },
                        {"data": "followContent","orderable" : false,
                            "render": function (data, type, row, meta){
                                return '<a class="tabel_btn_style" onclick="showFollowContent(\''+data+'\')">查看</a>';
                            }
                        },
                    ],
                    "createdRow": function ( row, data, index,settings,json ) {
                    },
                    "initComplete" : function(settings,json) {
                    }
                }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
                g_userManage.tableFollow.on("order.dt search.dt", function() {
                    g_userManage.tableFollow.column(0, {
                        search : "applied",
                        order : "applied"
                    }).nodes().each(function(cell, i) {
                        cell.innerHTML = i + 1
                    })
                }).draw();
            }
        }
    })
}
//添加跟进记录
function addFollow() {
    var followLayer = layer.open({
        type: 1,
        title: "新增跟进",
        maxmin: true,
        shadeClose: false,
        area: ['400px', '350px'],
        content: $('#addFollow'),
        btn : [ '保存', '取消' ],
        success: function (index, layero) {
            $("#followDate").val("");
            $("#followContent").val("");
        },
        yes : function (index,layero) {
            var param = {};
            param.relId = $("#customerId").val();//客户id
            param.relType = 1;
            param.followDate = $("#followDate").val();
            param.type = $("#type").val();
            param.followContent = $("#followContent").val();
            Comm.ajaxPost('customer/addFollowUp', JSON.stringify(param), function (result) {
                layer.msg(result.msg,{time:1000},function () {
                    layer.close(followLayer);
                    g_userManage.tableFollow.ajax.reload();
                })
            },"application/json");
        }
    })
}
//查看跟进内容
function showFollowContent(data) {
    layer.open({
        type: 1,
        title: "跟进内容",
        maxmin: true,
        shadeClose: true,
        offset: '100px',
        area: ['300px', '250px'],
        content: $('#showFollowContent'),
        success: function (index, layero) {
            $("#followContentShow").text(data);
        }
    })
}