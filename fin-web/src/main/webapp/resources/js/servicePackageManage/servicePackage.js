var g_userManage = {
    tableCustomer : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //param.state="12";
        param.personName=null;
        param.beginTime=null;
        param.endTime=null;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    if(g_userManage.tableCustomer){
        g_userManage.tableCustomer.ajax.reload();
    }else{
        g_userManage.tableCustomer = $('#fee_list').dataTable($.extend({
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
                debugger
                Comm.ajaxPost('servicePackage/servicePackageList', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    debugger
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
                {"data": "id","orderable" : false,"class":'hidden'},
                {"data": "package_type_name","orderable" : false},
                {"data": "repayment_type","orderable" : false,
                    "render": function (data, type, row, meta) {
                        if (data == 1) {
                            return "前置";
                        } else if (data == 2) {
                            return "月付";
                        } else {
                            return "";
                        }
                    }

                },
                {"data": "info","orderable" : false},
                {"data": "alter_time","orderable" : false,
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
              /*  var btnUpdate = $('<a class="tabel_btn_style" onclick="editDetail(\'2\',\''+data.id+'\')">修改 </a>&nbsp; ')*/
                var btnUpdate=$('<span style="color: #307ecc;" onclick="editDetail(\'2\',\''+data.id+'\')">修改 </span>');
                $('td', row).eq(6).append(btnUpdate);
            },
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
//添加修改费率
function  editDetail(type,id) {
    if (type=='1'){
    }else {
        layerIndex = layer.open({
            type : 1,
            title : "修改服务包名称",
            maxmin : true,
            shadeClose : false,
            area : [ '300px', '160px'  ],
            content : $('#editDetail'),
            btn : [ '提交', '取消' ],
            success: function () {
                var param = {};
                param.id=id;
                Comm.ajaxPost('servicePackage/getServicePackage', JSON.stringify(param), function (result) {
                    debugger
                    var resData = result.data;
                    $("#package_type_name").val(resData.package_type_name);
                },"application/json");
            },
            yes:function(index, layero){
                var package_type_name=$("#package_type_name").val();
                if($("#package_type_name").val()==""){
                    layer.alert("服务包不能为空！",{icon: 2, title:'操作提示'});
                    return
                }
                var param = {};
                param.id=id;
                param.package_type_name = package_type_name;
                Comm.ajaxPost('servicePackage/updateServicePackage', JSON.stringify(param), function (result) {
                    layer.msg(result.msg,{time:1000},function () {
                        if (result.code=="0"){
                            layer.closeAll();
                            g_userManage.tableCustomer.ajax.reload();
                        }
                    });
                },"application/json");
            }
        })
    }
}
//时间转换
function formatTime(t){
    debugger
    var time = t.replace(/\s/g,"");//去掉所有空格
    if(t.length>=14) {
        time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " +
            time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
    }
    return time;
}
