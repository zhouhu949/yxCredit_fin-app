/**
 * Created by Administrator on 2017/6/13 0013.
 */
//是否显示验证码
function isShowCode(confName){
    Comm.ajaxPost("safe/find","confName="+confName,function(data){
        if(data.code==0){
            if(data.data=="是"){
                $("#isShow_is").attr("checked","checked");
            }else{
                $("#isShow_no").attr("checked","checked");
            }
        }
    });
    layer.open({
        type : 1,
        title : '是否显示验证码',
        area : [ '200px', '200px' ],
        content : $('#isShowCode'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var name = $("input[type='radio']:checked").val();
            Comm.ajaxPost("safe/add","name="+name+"&confName=是否需要验证码",function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000});
                g_userManage.tableUser.ajax.reload();
            });
        }
    });
}
//密码错误次数
function pwdNum(confName) {
    Comm.ajaxPost("safe/find","confName="+confName,function(data){
        if(data.code==0){
            $('#pwdNum_input').val(data.data);
        }
    });
    layer.open({
        type : 1,
        title : '设置密码错误次数',
        area : [ '200px', '200px' ],
        content : $('#pwdNum'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var name = $('#pwdNum_input').val();
            var reg = /^(?:[1-9]\d*|0)$/;
            if(name!="" && reg.test(name)){
                Comm.ajaxPost("safe/add","name="+name+"&confName="+confName,function(data){
                    layer.closeAll();
                    layer.msg(data.msg,{time:1000});
                    g_userManage.tableUser.ajax.reload();
                });
            }else{
                $('#pwdNum_input').val("");
                layer.msg("请输入正确的数字",{time:1000});
            }
        }
    });
}

function edit(e){
    var target = e.target || window.event.target;
    var confName = $(target).parents("tr").children().eq(1).text();
    if(confName=="是否需要验证码"){
        isShowCode(confName);
    }else if(confName=="错误次数"){
        pwdNum(confName);
    }
}

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
        param.confName = $("[name='confName']").val();
     }
     paramFilter.param = param;

     page.firstIndex = data.start == null ? 0 : data.start;
     page.pageSize = data.length  == null ? 10 : data.length;
     paramFilter.page = page;

     return paramFilter;
     }
};

$(function (){
    g_userManage.tableUser = $('#safe_list').dataTable($.extend({
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
            Comm.ajaxPost('safe/getList',JSON.stringify(queryFilter),function(result){
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            },"application/json")

        },
        "order": [],
        "columns" :[
            {'data':"id", "searchable":false,"orderable" : false},
            {"data":'confName',"searchable":false,"orderable" : false},
            {"data":'name',"searchable":false,"orderable" : false},
            {"data":'description',"searchable":false,"orderable" : false},
            {
                "data":'alterTime',
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==null){
                        return "";
                    }else{
                        return json2TimeStamp(data);
                    }
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnEdit = $('<a href="##" class="edit" style="text-decoration: none;color: #307ecc;" onclick="edit(event)">修改</a>');
            $("td", row).eq(5).append(btnEdit);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //重置
            $("#btn_search_reset").click(function() {
                $('input[name="confName"]').val("");
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