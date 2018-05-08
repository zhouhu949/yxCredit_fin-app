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
/****************************************************准客户列表*****************************************************************/
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
            Comm.ajaxPost("repayManage/getCust", JSON.stringify(queryFilter), function (result) {
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
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data":'id',class:"hidden","searchable":false,"orderable" : false},
            {"data":'person_name',"searchable":false,"orderable" : false},
            {
                "className" : "TEL",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
            },
            {
                "className" : "CARD_NUM",
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
            $('td', row).eq(9).html(creatTime);
            var customerId = data.id;
            var addProduct = $('<a href="##" onclick="lookdetail(this)" style="text-decoration: none;color: #307ecc;">新增产品&nbsp;</a>');
            $("td", row).eq(10).append(addProduct);

            var tel = data.tel;
            //var id = data.id;
            var changeTel =tel.substring(0,3)+"****"+tel.substring(7,11);
            var card_num = data.card;
            var changeNum = card_num.substring(0,3)+"****"+card_num.substring(14,18);
            var emp_id = data.emp_id;

            if(emp_id==userId){
                $('td', row).eq(4).append(tel);
                $('td', row).eq(3).append(card_num);
            }else{
                $('td', row).eq(4).append(changeTel);
                $('td', row).eq(3).append(changeNum);
            }


        },
        "initComplete" : function(settings,json) {
            //操作
           /* $("#Role_list").on("click",".LookDel",function(e){

            });*/
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
                var id = $(target).parent("tr").children().eq(1).html();
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
    }).draw()
}
function lookdetail(e) {
    var target = e.target||window.event.target;
    var id = $(target).parent().parent('tr').children('td').eq(1).html();
    addProduct(id);
}
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(id,data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        param.state='1';
        // 自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.personName = $("#personName").val();
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
function addProduct(id) {
    layer.open({
            type : 2,
            title : "新增产品",
            area : ["350px", "250px" ],
            offset : "200px",
            fix : false,
            maxmin : true,
            content : ["/percust/productSel?customerId="
            + id],
        })
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
            Comm.ajaxPost("percust/orderList", JSON.stringify(queryFilter), function (result) {
                console.log(result);
                var returnData = {};
                var resData = result.data;
                returnData.data = resData;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            }, "application/json")
        },
        "order": [],
        "columns" :[
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data":'id',class:"hidden","searchable":false,"orderable" : false},
            {"data":'orderNo',"searchable":false,"orderable" : false},
            {"data":'productNameName',"searchable":false,"orderable" : false},
            {"data":'periods',"searchable":false,"orderable" : false},
            {"data":'rate',"searchable":false,"orderable" : false},
            {"data":'repayType',"searchable":false,"orderable" : false},
            {"data":'credit',"searchable":false,"orderable" : false},
            {"data":'contractAmount',"searchable":false,"orderable" : false},
            {"data":'stateName',"searchable":false,"orderable" : false},
            {"data":'tacheName',"searchable":false,"orderable" : false},
            {"data": "creatTime","orderable" : false,"width":"140px"},
            {
                "data": "null", "orderable": false, "width": "100px",
                "defaultContent":""
            },
            {"data":'state',class:"hidden","searchable":false,"orderable" : false},
            {"data":'tache',class:"hidden","searchable":false,"orderable" : false},
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            //时间格式转换
            var creatTime = data.creatTime;
            if (!creatTime) {
                creatTime = '';
            }else{
                creatTime = creatTime.substr(0,4)+"-"+creatTime.substr(4,2)+"-"+creatTime.substr(6,2)+" "+creatTime.substr(8,2)+":"+creatTime.substr(10,2)+":"+creatTime.substr(12,2);
            }
            $('td', row).eq(11).html(creatTime);

            var btnAdd = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="" onclick="auditDetail(event)">审批详细&nbsp;</a>');
            //var btnEdit = $('<a href="##" class="edit" style="text-decoration:none;color: #307ecc;" onclick="orderDetail(event)">查看订单</a>');
            return $("td", row).eq(12).append(btnAdd);
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
        param.customerId=menuId;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};

function auditDetail(e) {
    var target = e.target || window.event.target;
    var id = $(target).parent().parent('tr').children('td').eq(1).html();
    layer.open({
        type : 2,
        title : "流程跟踪详细",
        area : [
            "580px",
            "420px" ],
        offset : "200px",
        fix : false,
        maxmin : true,
        content : ["/percust/approveRecordList?orderId="
        + id],
    })
}


