/**
 * Created by Win7 on 2017/5/4.
 */
//页面初始化
$(function () {
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
    var menuId = "0";
    queryList(menuId);
})
function queryList(id){
    g_userManage.tableUser = $('#Res_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        //"iDisplayLength": 4,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(id,data);
            Comm.ajaxPost("percust/list", JSON.stringify(queryFilter), function (result) {
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
                "className" : "cell-operation",
                "data": null,
                class:"hidden",
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data":'id',class:"hidden","searchable":false,"orderable" : false},
            {"className" : "childBox","orderable" : false,"data" : null,"width" : "30px","searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input type="checkbox" name="checkbox" value="'+data.id+'" style="cursor:pointer;" >'
                }
            },
            {"data":'person_name',"searchable":false,"orderable" : false},
            {
                "className" : "CARD_NUM",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
            },
            {
                "className" : "tel",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
            },
            {"data":'num',"searchable":false,"orderable" : false},
            {"data":'company',"searchable":false,"orderable" : false},
            {"data":'branch',"searchable":false,"orderable" : false},
            {"data":'manager',"searchable":false,"orderable" : false},
            {"data": "creat_time","orderable" : false,"width":"140px"},
            {
                "data": "null", "orderable": false, "width": "100px",
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            //时间格式转换
            var creatTime = data.creat_time;
            if (!creatTime) {
                creatTime = '';
            }else{
                creatTime = creatTime.substr(0,4)+"-"+creatTime.substr(4,2)+"-"+creatTime.substr(6,2)+" "+creatTime.substr(8,2)+":"+creatTime.substr(10,2)+":"+creatTime.substr(12,2);
            }
            $('td', row).eq(10).html(creatTime);
            var customerId = data.id;
            //var addProduct = $('<a href="##" onclick="lookdetail(this)" style="text-decoration: none;color: #307ecc;">新增产品&nbsp;</a>');
            var btnLinkUser = $('<a onclick="LinkUser(\''+data.id+'\',\''+data.empId+'\')" href="##" class="LinkUser" style="color: #307ecc">客户交接</a>');
            $("td", row).eq(11).append(btnLinkUser);

            var tel = data.tel;
            //var id = data.id;
            var changeTel =tel.substring(0,3)+"****"+tel.substring(7,11);
            var card_num = data.card;
            var changeNum = card_num.substring(0,3)+"****"+card_num.substring(14,18);
            var emp_id = data.emp_id;

            if(emp_id==userId){
                $('td', row).eq(5).append(tel);
                $('td', row).eq(4).append(card_num);
            }else{
                $('td', row).eq(5).append(changeTel);
                $('td', row).eq(4).append(changeNum);
            }


        },
        "initComplete" : function(settings,json) {
            //全选
            $("#checkBox_All").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#Res_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#Res_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //单选行
            $("#Res_list tbody").delegate( 'tr','click',function(e){
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
    }).draw()
}
var cusIds = [];
function changeAll(){
    var test = $("input[name='checkbox']:checked");
    var checkBoxValue = "";
    test.each(function(){
        checkBoxValue = $(this).val();
        cusIds.push(checkBoxValue);
    });
    if(cusIds.length<=0){
        layer.msg("请选择客户",{time:1000});
        return;
    }
    LinkUserAll(cusIds);
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
            param.personName = $("#personName").val();
            //param.state='1';
            var beginTime = $("#beginTime").val();
            if(beginTime != null && beginTime != ''){
                param.beginTime = beginTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
            var endTime = $("#endTime").val();
            if(endTime != null && endTime != ''){
                param.endTime = endTime.replace(/[^0-9]/ig,"");//字符串去除非数字
            }
            if($("#onlyMe").attr("checked")){
                param.emp_id = userId;
            }
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        console.log(page.firstIndex)
        if (page.pageSize==-1)
        {
            page.pageSize=10;
        }
        paramFilter.page = page;
        return paramFilter;
    }
};
//查询重置方法
function paramSearchReset() {
    $('input[name="search"]').val("");
    g_userManage.fuzzySearch = false;
    g_userManage.tableUser.ajax.reload();
}
var g_userLinkman = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if (g_userLinkman.fuzzySearch) {
            param.account = $("input[name='account']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
function LinkUser(id,empId) {
    layer.open({
        type : 1,
        title : "关联业务员",
        maxmin : true,
        shadeClose : true,
        area: '[1000px,600px]',
        offset: '75px',
        shift: 5,
        content :$('#addLinkUser').show(),
        btn : [ '保存', '取消' ],
        success: function(layero, index){
            loadLinkUser(empId);
        },
        yes: function(layero, index){
            var selectArray = $("#User_list tbody input:checked");
            if(!selectArray || selectArray.length==0){
                layer.msg("请选择用户");
                return;
            }
            var empName=$('#User_list tbody tr td input:checked').parent('td').parent('tr').children('td').eq(3).html();
            var userIds = new Array();
            $.each(selectArray,function(i,e){
                var val = $(this).val();
                userIds.push(val);
            });
            if(userIds.length==0){
                return;
            }
            if(userIds.length>1){
                layer.msg("只能关联一个用户！");
                return;
            }
            var userid = userIds[0];//用户id
            var result= {};
            result.empId = userid;//用户id
            //result.empName = empName;//用户名称
            result.id = id;//商户id
            Comm.ajaxPost('cusConnect/change',JSON.stringify(result),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.closeAll();
                    window.$('#magMer').dataTable().fnDraw(false);
                });
            },"application/json")
        }
    })

}
function LinkUserAll(cusIds) {
    layer.open({
        type : 1,
        title : "关联业务员",
        maxmin : true,
        shadeClose : true,
        area: '[1000px,600px]',
        offset: '75px',
        shift: 5,
        content :$('#addLinkUser').show(),
        btn : [ '保存', '取消' ],
        success: function(layero, index){
            loadLinkUser();
        },
        yes: function(layero, index){
            var selectArray = $("#User_list tbody input:checked");
            if(!selectArray || selectArray.length==0){
                layer.msg("请选择用户",{time:1000});
                return;
            }
            var empName=$('#User_list tbody tr td input:checked').parent('td').parent('tr').children('td').eq(3).html();
            var userIds = new Array();
            $.each(selectArray,function(i,e){
                var val = $(this).val();
                userIds.push(val);
            });
            if(userIds.length==0){
                return;
            }
            if(userIds.length>1){
                layer.msg("只能关联一个用户！");
                return;
            }
            var userid = userIds[0];//用户id
            var result= {};
            result.empId = userid;//用户id
            //result.empName = empName;//用户名称
            result.cusIds = cusIds;//商户id
            Comm.ajaxPost('cusConnect/changeAll',JSON.stringify(result),function(data){
                layer.msg(data.msg,{time:1000},function () {
                    layer.closeAll();
                    g_userManage.tableUser.ajax.reload();
                });
            },"application/json")
        }
    })

}
function  loadLinkUser(empId) {
    if(g_userLinkman.tableCustomer){
        g_userLinkman.tableCustomer.ajax.reload(function () {
            //此商户已经关联过客户
            if(empId) {
                $('#User_list tbody tr td input[value="'+empId+'"]').attr("checked",true);
            }
        });
    }else{
        g_userLinkman.tableCustomer = $('#User_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userLinkman.getQueryCondition(data);
                Comm.ajaxPost('user/list', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
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
                        return '<input type="checkbox" value="'+data.userId+'" style="cursor:pointer;" >'
                    }
                },
                {"data":'account',"searchable":false,"orderable" : false},
                {"data":'trueName',"searchable":false,"orderable" : false},
                {"data":'organName',"searchable":false,"orderable" : false},
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
            ],
            "initComplete" : function(settings,json) {
                //此商户已经关联过客户
                if(empId) {
                    $('#User_list tbody tr td input[value="'+empId+'"]').attr("checked",true);
                }

                //搜索
                $("#btn_search1").click(function() {
                    g_userLinkman.fuzzySearch = true;
                    g_userLinkman.tableCustomer.ajax.reload();
                });
                //重置
                $("#btn_search_reset").click(function() {
                    $("input[name='account']").val("");
                    g_userLinkman.fuzzySearch = false;
                    g_userLinkman.tableCustomer.ajax.reload();
                });

                $("#User_list").delegate("tbody tr td input", "click", function() {
                    if ($(this).prop("checked")) {
                        $(this).parent().parent().siblings("tr").find("input").prop("checked", false)
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userLinkman.tableCustomer.on("order.dt search.dt", function() {
            g_userLinkman.tableCustomer.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
}


