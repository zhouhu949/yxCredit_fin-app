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
    g_contractorManage.tableUser = $('#ContractorOrder_list').dataTable($.extend({
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
            Comm.ajaxPost('contractorManage/contractorOrderListPage',JSON.stringify(queryFilter),function(result){
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
            {"data":'orderNo',"searchable":false,"orderable" : false},
            {"data":'contractorName',"searchable":false,"orderable" : false},
            {"data":'realName',"searchable":false,"orderable" : false},
            {"data":'card',"searchable":false,"orderable" : false},
            {"data":'applayMoney',"searchable":false,"orderable" : false},
            {"data":'loanAmount',"searchable":false,"orderable" : false},
            {"data":'serviceFee',"searchable":false,"orderable" : false},

            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return getOrderState(data.orderState);
                }
            },
            {
                "data": "null", "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="getContractorOrder(\''+data.id+'\')">查看</a>');
            $('td', row).eq(9).append(btnDel);
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

function getContractorOrder(id){

}

/**
 * 0未提交;1借款申请;2自动化审批通过;3自动化审批拒绝;4自动化审批异常转人工;
 * 5人工审批通过;6人工审批拒绝;7合同确认;8放款成功;9结清;10关闭
 */
function getOrderState(paramValue){
    var param=Number(paramValue);
    var paramStr;
    switch (param) {
        case 1:
            paramStr="借款申请";
            break;
        case 2:
            paramStr="自动化审批通过";
            break;
        case 3:
            paramStr="自动化审批拒绝";
            break;
        case 4:
            paramStr="自动化审批异常转人工";
            break;
        case 5:
            paramStr="人工审批通过";
            break;
        case 6:
            paramStr="人工审批拒绝";
            break;
        case 7:
            paramStr="合同确认";
            break;
        case 8:
            paramStr="放款成功";
            break;
        case 9:
            paramStr="结清";
            break;
        case 10:
            paramStr="关闭";
            break;
        default:
            paramStr=param;
            break;
    }
    return paramStr;
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





