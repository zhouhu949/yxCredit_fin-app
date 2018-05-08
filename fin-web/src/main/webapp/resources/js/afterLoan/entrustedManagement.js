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
            param.cusName = $("#custName").val();
            param.custTel = $("#custTel").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    g_userManage.tableUser = $('#afterLoanTable').dataTable($.extend({
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
            Comm.ajaxPost('afterLoan/listEntrusted', JSON.stringify(queryFilter), function (result) {
                console.log(result);
                //封装返回数据
                var returnData = {};
                var resData = result.data;

                returnData.draw = data.draw;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                console.log(returnData.data);
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
            {
                "className" : "childBox",
                'class':'hidden',
                "orderable" : false,
                "data" : null,
                "width" : "20px",
                "searchable":false,
                "render" : function(data, type, row, meta) {
                    return '<input id="'+data.customerId+'" type="checkbox" value="'+data.customerId+'" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "entrust_date","orderable" : false,
                "render": function (data, type, row, meta) {
                    return formatTime(data);
                }
            },
            {"data": "person_name","orderable" : false},
            {"data": "tel","orderable" : false},
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""},
            //{"data": null,"orderable" : false}
            {"data": null ,"searchable":false,"orderable" : false,defaultContent:""}
        ],
        //处理序号用
        "columnDefs": [
            {
                "searchable": false,
                "orderable": false,
                "targets": 1
            }
        ],
        "createdRow": function(row, data, index, settings, json) {
            if(data.entrust_sign=="0" ||data.entrust_sign==""){
                var collectionbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="collection(\''+data.customerId+'\',\'1\')"> 标记</a>');
                $("td", row).eq(5).append(collectionbtn);

                $("td", row).eq(6).append('未完成');
            }else if(data.entrust_sign=="1") {
                $("td", row).eq(5).append('已标记');
                var collectionbtn = $('<a href="##" style="text-decoration:none;color: #307ecc;" class="tabel_btn_style" onclick="collection(\''+data.customerId+'\',\'2\')">委外催收中</a>');
                $("td", row).eq(6).append(collectionbtn);
            }else if(data.entrust_sign=="2"){
                $("td", row).eq(5).append('已标记');
                $("td", row).eq(6).append('委外完成');
            }
        },
        "initComplete" : function(settings,json) {
            $("#btn_search").click(function() {
                $("#cus_order").hide();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
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


//时间转换
function formatTime(t){
    var time = t.replace(/\s/g, "");//去掉所有空格
    time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
        time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    return time;
}

function sltReductionOnchange(){
    debugger
    if ($("#sltReduction").val()=="0") {
        $("#reductionAmount").removeAttr("readonly");
    }else {
        $("#reductionAmount").attr("readonly", "readonly");    //去除readonly属性
    }
}

function  collection(customerId,entrust_sign) {
    debugger
    var param={};
    param.customerId = customerId;
    param.entrust_sign=entrust_sign;
    Comm.ajaxPost('afterLoan/updateEntrustedState',JSON.stringify(param),function(data){
        layer.msg(data.msg,{time:1000},function () {
            g_userManage.tableUser.ajax.reload();
            //layer.close(setUpLayer);
        });
    }, "application/json");
}