$().ready(function(){
    var taskNodeId = window.parent.document.getElementById("taskNodeId").value;
    $("#taskNodeId").val(taskNodeId);
    $("#clickNextButton").click(clickNextButton);
    // $("#reasonClick").click(reasonClick);
});
//审核修改状态
function clickNextButton(val) {
    var taskNodeId = window.parent.document.getElementById("taskNodeId").value;
    if(val=="12"){
        layerIndex = layer.open({
            type : 1,
            title : "产品信息",
            maxmin : true,
            shadeClose : false, //点击遮罩关闭层
            area : [ '500px', '' ],
            content : $('#preQuotaDialog'),
            btn : [ '提交', '取消' ],
            yes:function(index, layero){
                var paramFilter = {};
                var page = {};
                var param = {};
                param.orderId=$("#orderId").val();
                param.state = val;//合规检查通过
                param.taskNodeId = taskNodeId;
                paramFilter.param = param;
                param.userId = $("#userId").val();
                Comm.ajaxPost('standard/updateState', JSON.stringify(paramFilter), function (result) {
                    debugger
                    layer.msg(result.msg,{time:1000},function () {

                    });
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.g_userManage.tableOrder.ajax.reload();
                    parent.layer.close(index);
                },"application/json");
            }
        })
    }else {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.orderId=$("#orderId").val();
        param.state = val;//合规检查通过
        paramFilter.param = param;
        param.taskNodeId = taskNodeId;
        param.userId = $("#userId").val();
        Comm.ajaxPost('standard/updateState', JSON.stringify(paramFilter), function (result) {
            debugger
            layer.msg(result.msg,{time:1000},function () {

            });
            var index = parent.layer.getFrameIndex(window.name);
            parent.g_userManage.tableOrder.ajax.reload();
            parent.layer.close(index);
        },"application/json");
    }
}
