var process_id = $("#process_id").val();
var node_2;
var groupJson = {};
var count = 1;
groupJson.conditions = new Array();//客户分群条件json
init();
//清空画布
// $("#clearAll").on("click", function () {
//     var versionId = $("input[name=initEngineVersionId]").val();
//     var param = {'versionId': versionId};
//     Comm.ajaxPost("engineVersion/clear", param,
//         function (data) {
//             $(".bigCircle").hide();
//             scene.clear();
//             init();
//             layer.msg(data.msg, {time: 1000});
//         })
// });

$(".l_decision_options").on("click", "ul li", function () {
    $(".l_decision_options li").removeClass("selected");
    $(this).addClass("selected");
});
//删除
$(".delete").on("click", function () {

    var type = ($(".look").attr("type"));
    var id = node_2.id;
    var _param;
    if (node_2.id == undefined ) {//节点未保存|| (node_2.inLinks != null && node_2.inLinks.length == 0)
        scene.remove(node_2)
        $(".bigCircle").hide();
    } else {//节点已保存
        _param = {"nodeId": id}
        if (type == 1) {
            layer.msg("开始节点不能删除", {time: 1000});
            $(".bigCircle").hide();
            return;
        } else {
            if(node_2.inLinks!=null&&node_2.inLinks!=undefined){
                if(node_2.inLinks.length>0||node_2.outLinks.length>0){
                    layer.msg("请先删除相关连线", {time: 1000});
                    return;
                }
                else{
                    deleteNode(_param);
                }
            }else{
                deleteNode(_param);
            }
        }
    }
})

//编辑
$(".operate").on("click", function () {
    var type = node_2.type;
    var excType = node_2.excType;
    if(type!=1&&type!=undefined&&type!=null&&excType!=3){
        groupedit(node_2.id,node_2.type, 'Y');
        $(".bigCircle").hide();
    }else if(excType==3){
        jcEdit(node_2.id,node_2.type, 'Y');
        $(".bigCircle").hide();
    }else{
        layer.msg("开始节点不能编辑", {time: 1000});
    }
    // switch (type) {
    //     case '5':
    //         console.log("黑名单");
    //         blackInit(node_2.id, 'Y');
    //         $(".bigCircle").hide();
    //         break;
    //     case '6':
    //         console.log("白名单");
    //         whiteInit(node_2.id, 'Y');
    //         $(".bigCircle").hide();
    //         break;
    //     case '7':
    //         console.log("沙盒比例");
    //         sandboxedit();
    //         $(".bigCircle").hide();
    //         break;
    //     case '3':
    //         console.log("客户分群");
    //
    //         groupedit(node_2.id,node_2.type, 'Y');
    //         $(".bigCircle").hide();
    //         break;
    //     case '2':
    //         console.log("规则集");
    //         lookOrOperate(node_2.id, "G");
    //         $(".bigCircle").hide();
    //         break;
    //     case '4':
    //         console.log("评分卡");
    //         getpage()
    //         $(".bigCircle").hide();
    //         break;
    //     case '8':
    //         console.log("信用评级");
    //         $(".scoreCard").css("display", "block");
    //         autoCenter($(".scoreCard"));
    //         $(".bigCircle").hide();
    //         break;
    //     case '9':
    //         console.log("决策选项");
    //         tagOption(node_2.id);
    //         $(".bigCircle").hide();
    //         //决策选项输入区间的验证
    //         var section = /^(\(|\[)([1-9][0-9]*|0{1})(\.([0-9]+))?\,([1-9][0-9]*|0{1})(\.([0-9]+))?(\)|\])$/;
    //         $(".l_inputs").on("focus", function () {
    //             $(this).addClass("l_foc")
    //         })
    //         $(".l_inputs").on("blur", function () {
    //             var sectionContent = $(".l_foc").val();
    //             var sectionContent = $(this).val();
    //             if (section.test(sectionContent) == false) {
    //                 layer.msg("您的输入不符合要求，请重新输入", {time: 1000});
    //                 $(this).val('');
    //             }
    //         })
    //         break;
    //     // case '10':
    //     //     console.log("额度计算");
    //     //     $(".scoreCard").css("display", "block");
    //     //     autoCenter($(".scoreCard"));
    //     //     $(".bigCircle").hide();
    //     //     break;
    //     // case '11':
    //     //     console.log("报表分析");
    //     //     lookOrOperate(node_2.id,"F");
    //     //     $(".bigCircle").hide();
    //     //     break;
    //     // case '12':
    //     //     console.log("自定义按钮");
    //     //     lookOrOperate(node_2.id,"F");
    //     //     $(".bigCircle").hide();
    //     //     break;
    //     // case '11':
    //     //     console.log("报表分析");
    //     //     lookOrOperate(node_2.id,"F");
    //     //     $(".bigCircle").hide();
    //     //     break;
    //     case '14':
    //         console.log("人工节点");
    //         lookOrOperate(node_2.id, "F");
    //         $(".bigCircle").hide();
    //         break;
    //     case '14':
    //         console.log("自动流程节点");
    //         lookOrOperate(node_2.id, "F");
    //         $(".bigCircle").hide();
    //         break;
    //     default:
    //         break;
    // }
})

//关闭菜单
$(".blue").on("click", function () {
    $(".bigCircle").hide();
});
//右键菜单
$(".hovers").hover(function () {
    $(this).css("background", "#398DEE");
    $(this).find(".shows").hide();
    $(this).find(".hides").show();
}, function () {
    $(this).css("background", "#FFFFFF");
    $(this).find(".shows").show();
    $(this).find(".hides").hide();
});


//决策节点
function jcEdit(nodeId,nodeType,isEdit) {

    if(nodeId!=0&&nodeId!=undefined){
        var processNode = {"nodeId": nodeId};
    }else{
        var processNode = {"processId":process_id,"nodeType":nodeType};
    }
    Comm.ajaxPost("decision_flow/getNode", JSON.stringify(processNode), function (result) {

        var data = result.data;
        if (data != null && data.params != null&&data.nodeJson != null) {
            var htmlObj = JSON.parse(data.params);
            var html = htmlObj.html;
            var groupSelHtml = htmlObj.groupSelHtml;
            if (html != null && !html == "") {
                $(".c_content").empty();
                $(".c_content").append(html);
                $(".c-group-content").empty();
                $(".c-group-content").append(groupSelHtml);
            }
            showJc(nodeId,nodeType);
        } else {
            $(".c_content").empty();
            JcInit(nodeId,nodeType);
        }
    }, "application/json")
}

//决策初始化
function JcInit(nodeId,nodeType) {

    var initCount = 0;
    $(".c-swarm-content").empty();
    for (var i = 0; i < 1; i++) {
        initCount++;
        var newGroup = '<div class="c-swarm-interior">' +
            '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
            '<option data="0" value="待选">待选</option><option data="1" value="通过">通过</option><option data="2" value="拒绝">拒绝</option></select></div>';
        $(".c-swarm-content").append(newGroup);
    }
    showJc(nodeId,nodeType);
}
//显示决策管理
function showJc(nodeId,nodeType) {

    var processNode = {};
    if(nodeId!=0&&nodeId!=undefined){
        var processNode = {"nodeId": nodeId};
    }else{
        var processNode = {"processId":process_id,"nodeType":nodeType};
    }
    var sta;
    Comm.ajaxPost("decision_flow/getNode", JSON.stringify(processNode), function (result) {

        var data = result.data;
        if(data!=null){
            sta = JSON.parse(data.nodeJson);
        }
    }, "application/json","","","",false);
    if(sta){
        $('#jcStatus').val(sta.conditions[0].status);
    }
    $(".c-swarm-iexternal").show();
    $(".c-positon-img").show();
    layer.open({
        type: 1,
        title: '编辑节点',
        maxmin: true,
        shadeClose: false,
        area: ['780px', '300px'],
        content: $('.c-jc-dialog'),
        btn: ['保存', '取消'],
        yes: function (index, layero) {

            //点击确定操作(设置分组)
            groupJson.fields = new Array();
            var nodeId = node_2.id;
            var param = new Object();
            var html = $(".c_content").html();
            param.processId = process_id;
            param.id = nodeId;
            param.nodeId = nodeId;
            param.nodeX = node_x;
            param.nodeY = node_y;
            param.nodeType = node_type;
            param.nodeName = node_name;
            param.nodeCode = node_code;
            param.nodeOrder = node_order;
            param.excType = node_2.excType;
            var object = {dataId: node_dataId, url: node_url, type: node_type, html: html};
            object = JSON.stringify(object);
            param.params = object;
            groupJson.conditions = [{
                group_name:'通过',
                nextNode:'',
                status:$('#jcStatus').val()
            },{
                group_name:'拒绝',
                nextNode:'',
                status:$('#jcStatus').val()
            }];
            if (nodeId != undefined) {
                param.nodeType = null;
                Comm.ajaxPost("decision_flow/getNode", JSON.stringify(param), function (result) {

                    var data = result.data;
                    if(data!=null){
                        groupJson_1 = JSON.parse(data.nodeJson);
                        var conditions=groupJson.conditions;//当前条件
                        var conditions_1=groupJson_1.conditions;//原先条件
                        conditions.nextNode = conditions_1.nextNode;
                        groupJson.conditions = conditions;
                        param.nodeJson=JSON.stringify(groupJson);
                        Comm.ajaxPost("decision_flow/update",JSON.stringify(param),function (result) {
                            layer.closeAll();
                            if(result.code==0){
                                layer.msg(result.msg,{time:1000});
                                choseNodes(1);
                            }
                        },"application/json")
                    }
                }, "application/json");
            } else {

                param.nodeJson = JSON.stringify(groupJson);
                Comm.ajaxPost("decision_flow/save", JSON.stringify(param), function (data) {
                    layer.closeAll();
                    if (data.code == 0) {
                        layer.msg(data.msg, {time: 1000});
                        node_2.id = data.data.nodeId;
                        choseNodes(1);
                    }
                }, "application/json");
            }
        }
    });
}

//客户分群
function groupedit(nodeId,nodeType,isEdit) {

    if(nodeId!=0&&nodeId!=undefined){
        var processNode = {"nodeId": nodeId};
    }else{
        var processNode = {"processId":process_id,"nodeType":nodeType};
    }
    Comm.ajaxPost("decision_flow/getNode", JSON.stringify(processNode), function (result) {

        var data = result.data;
        if (data != null && data.params != null&&data.nodeJson != null) {
            var htmlObj = JSON.parse(data.params);
            var html = htmlObj.html;
            var groupSelHtml = htmlObj.groupSelHtml;
            if (html != null && !html == "") {
                $(".c_content").empty();
                $(".c_content").append(html);
                $(".c-group-content").empty();
                $(".c-group-content").append(groupSelHtml);
            }
            showSwarm();
        } else {
            $(".c_content").empty();
            groupInit();
        }
    }, "application/json")

}

var mapObj = new Map();
var mapObj_1 = new Map();
var lineIn;
function init(flag) {
    if(flag!=1){
        choseNodes();
    }
    lineIn = [];
    var id = process_id;
    var param = new Object();
    param.processId = id;
    Comm.ajaxPost('decision_flow/getNodeList', JSON.stringify(param), function (result) {

        var data = result.data;
        scene.clear();
        mapObj_1.clear();
        mapObj.clear();
        var array = data.processNodeList;
        for (var b = 0; b < array.length; b++) {
            mapObj_1.put(array[b].nodeCode, array[b]);
        }
        ;
        lineIn = lineIn.concat(array);
        var array_1 = new Array();
        order_count = data.maxOrder + 1;
        for (var i = 0; i < array.length; i++) {
            var i_node = $.parseJSON(array[i].params);
            var node = createNode(Number(i_node.dataId), array[i].nodeName, i_node.url, Number(array[i].nodeX), Number(array[i].nodeY), i_node.type,Number(array[i].excType));
            node.id = array[i].nodeId;
            node.node_order = array[i].nodeOrder;
            node.node_code = array[i].nodeCode;
            if (array[i].nextNodes != null && array[i].nextNodes != undefined) {
                if (array[i].nextNodes.indexOf(",") > -1) {
                    var ary = array[i].nextNodes.split(",");
                    for (var s = 0; s < ary.length; s++) {
                        var element = mapObj_1.get(ary[s]);
                        lineIn.removeByValue(element);
                        node.next_node.push(ary[s]);
                    }
                } else if (array[i].nextNodes != '') {
                    var element = mapObj_1.get(array[i].nextNodes);
                    lineIn.removeByValue(element);
                    node.next_node.push(array[i].nextNodes);
                }
            }
            array_1.push(node);
            var nodeString = node.node_code;
            mapObj.put(nodeString, node);
        }
        desLink();
    }, "application/json");
}
function desLink() {

    for (var i = 0; i < lineIn.length; i++) {

        var beginNode = mapObj.get(lineIn[i].nodeCode);
        var nextNodes = beginNode.next_node;
        if (nextNodes.length > 0) {
            for (var item = 0; item < nextNodes.length; item++) {
                var endNode = mapObj.get(beginNode.next_node[item]);
                if (endNode != undefined&&endNode.node_code != beginNode.node_code) {
                    drawline(beginNode, endNode, mapObj);
                }
            }
        }
    }
}
//连线的回显
function drawline(beginNode, endNode, mapObj) {

    if(beginNode.excType!=3){
        var link = new JTopo.Link(beginNode, endNode);
        link.arrowsRadius = 10;
        link.strokeColor = '0,0,0';
        link.dashedPattern = 2;
        link.lineWidth = '0.5';
        link.fontColor = '0,0,0';
        scene.add(link);
    }else{
        var link = newFoldLink(beginNode,endNode);
        scene.add(link);
    }
    var param;
    if (beginNode.type != 1 && beginNode.type != undefined) {
        param = {"nodeId": beginNode.id};
        Comm.ajaxPost('decision_flow/getNode', JSON.stringify(param), function (result) {
            var datas = result.data;
            var nodeJsonObj = JSON.parse(datas.nodeJson);
            if (datas != null) {
                var conditions = nodeJsonObj.conditions;
                for (var i = 0; i < conditions.length; i++) {
                    if (conditions[i].nextNode == link.nodeZ.node_code) {
                        link.text = conditions[i].group_name;
                    }
                }
                var html = JSON.parse(datas.params);
                html = html.html;
                groupSelHtml = html.groupSelHtml;
                $(".c_content").empty();
                $(".c_content").append(html);
            }
        }, "application/json", "", "", "", false);
    }
    if (endNode.next_node != undefined && endNode.next_node.length > 0) {

        for (var i = 0; i < endNode.next_node.length; i++) {
            var nodeCode;
            if(beginNode.excType==3){
                param = {"nodeId":beginNode.id};
                Comm.ajaxPost('decision_flow/getNode', JSON.stringify(param), function (result) {
                    var datas = result.data;
                    var nodeJsonObj = JSON.parse(datas.nodeJson);
                    if (datas != null) {
                        var conditions = nodeJsonObj.conditions;
                        for (var i = 0; i < conditions.length; i++) {
                            if (conditions[i].group_name == '通过') {
                                nodeCode = conditions[i].nextNode;
                            }
                        }
                    }
                }, "application/json", "", "", "", false);
                if(endNode.node_code == nodeCode) {//结束节点的node_code和决策节点连的是通过的节点node_code一样的情况下才继续划线
                    drawline(endNode, mapObj.get(endNode.next_node[i]), mapObj);
                }
            }else{
                drawline(endNode, mapObj.get(endNode.next_node[i]), mapObj);
            }
        }
    }
    link.addEventListener("click", function (event) {
        selink = link;
        var nodeA_x = link.nodeA.x;
        var nodeZ_x = link.nodeZ.x;
        var nodeA_y = link.nodeA.y;
        var nodeZ_y = link.nodeZ.y;
        var imgX = nodeA_x + (nodeZ_x - nodeA_x) / 2;
        var imgY = nodeA_y + (nodeZ_y - nodeA_y) / 2;
        $("#lineDel").css({
            display: "block",
            left: imgX + sceneX,
            top: imgY + sceneY
        });
        removeId = link.nodeZ.id;
        lastId = link.nodeA.id;
        $("#lineDel").unbind('click').on("click", function () {
            var nodeId = selink.nodeZ.id;
            var nodeZ_code = selink.nodeZ.node_code;
            var param = {'currentNodeId': removeId, 'preNodeId': lastId};
            Comm.ajaxPost('decision_flow/removeLink', JSON.stringify(param), function (result) {
                selink.nodeA.next_node.removeByValue(nodeZ_code);
                layer.msg("删除成功！", {time: 1000});
                scene.remove(link);
                scene.remove(selink);
                $("#lineDel").hide();
                beginNode = null;
            }, "application/json")
        })
    }, true);
}

function jcline(beginNode, endNode, mapObj) {

    var link = newFoldLink(beginNode,endNode);
    scene.add(link);
    var param;
    if (beginNode.type != 1 && beginNode.type != undefined) {
        param = {"nodeId": beginNode.id};
        Comm.ajaxPost('decision_flow/getNode', JSON.stringify(param), function (result) {
            var datas = result.data;
            var nodeJsonObj = JSON.parse(datas.nodeJson);
            if (datas != null) {
                var conditions = nodeJsonObj.conditions;
                for (var i = 0; i < conditions.length; i++) {
                    if (conditions[i].nextNode == link.nodeZ.node_code) {
                        link.text = conditions[i].group_name;
                    }
                }
                var html = JSON.parse(datas.params);
                html = html.html;
                groupSelHtml = html.groupSelHtml;
                $(".c_content").empty();
                $(".c_content").append(html);
            }
        }, "application/json", "", "", "", false);
    }
    link.addEventListener("click", function (event) {
        selink = link;
        var nodeA_x = link.nodeA.x;
        var nodeZ_x = link.nodeZ.x;
        var nodeA_y = link.nodeA.y;
        var nodeZ_y = link.nodeZ.y;
        var imgX = nodeA_x + (nodeZ_x - nodeA_x) / 2;
        var imgY = nodeA_y + (nodeZ_y - nodeA_y) / 2;
        $("#lineDel").css({
            display: "block",
            left: imgX + sceneX,
            top: imgY + sceneY
        });
        removeId = link.nodeZ.id;
        lastId = link.nodeA.id;
        $("#lineDel").unbind('click').on("click", function () {
            var nodeId = selink.nodeZ.id;
            var nodeZ_code = selink.nodeZ.node_code;
            var param = {'currentNodeId': removeId, 'preNodeId': lastId};
            Comm.ajaxPost('decision_flow/removeLink', JSON.stringify(param), function (result) {
                selink.nodeA.next_node.removeByValue(nodeZ_code);
                layer.msg("删除成功！", {time: 1000});
                scene.remove(link);
                scene.remove(selink);
                $("#lineDel").hide();
                beginNode = null;
            }, "application/json")
        })
    }, true);
}

function  getNodeList() {
    var _param_ = {};
    var data;
    var list = new Array();
    _param_.id = process_id;
    Comm.ajaxPost("decision_flow/getNodeByProcessIds",JSON.stringify(_param_),function(result){

        var lists = result.data;
        for(var i=0;i<lists.length;i++) {
            if (lists[i].nodeCode != "ND_START"&&lists[i].nodeJson!=null) {

                list.push(lists[i]);
            }
        }
    },"application/json","","","",false);
    data = list.length+1;
    return data;
}


//删除节点
var removeId;
var lastId;
function deleteNode(_param) {

    var _param_1 = new Object();
    removeId = node_2.id;
    if (node_2.inLinks == null || (node_2.inLinks != null && node_2.inLinks.length == 0)) {
        lastId = -1;
    } else {
        lastId = node_2.inLinks[0].nodeA.id;
    }
    _param_1.currentNodeId = removeId;
    _param_1.preNodeId = lastId;
    Comm.ajaxPost("decision_flow/removeNode", JSON.stringify(_param_1), function (reslut) {
        if (node_2.inLinks != null && node_2.inLinks.length > 0) {
            node_2.inLinks[0].nodeA.next_node.removeByValue(node_2.node_code);
        }
        layer.msg(reslut.msg, {time: 1000});
        scene.remove(node_2);
        choseNodes(1);
        $(".bigCircle").hide();
    }, "application/json")
}

//客户分群
//添加条件
var cP = 0;
$("body").on("click", ".addCondition", function () {
    var newSelect = $('<div class="c-select-one c-add" style="">' +
        '<select  class="l_before l_relations l_relation datas" style="width:69px;margin-right:3px;background-position: 50px 0px;">' +
        '<option data="&&" value="且">且</option>' +
        '<option data="||" value="或">或</option>' +
        '</select>' +
        '</div>');
    // if($(this).parents(".c-swarm-interior").find(".l_relation").length==0){
    //     $(this).parents(".c-swarm-interior").find(".c-contains-outside").after(newSelect);
    // }else{
    //     $(this).parents(".c-swarm-interior").find(".l_relation").last().parent().after(newSelect);
    // };
    $(this).parent().prev(".c-swarm-interior-left").find(".c-contains-outside").find(".c-contained-within:last-child").append(newSelect);
    var newCondition = $('<div class="c-contained-within">' +
        '<div class="c-select-two">' +
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选" />' +
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
        '<option data="0" value="待选">待选</option></select>' +
        '</div>' +
        '<div class="c-swarm-name">' +
        '<input type="text" class="datas" value=""/>' +
        '</div>' +
        '<div class="c-select-one">' +
        '<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">' +
        '<option data="0" value="待选">待选</option><option data="0" value="且">且</option><option data="||" value="或">或</option>' +
        '</select>' +
        '</div>' +
        '<div class="c-select-two">' +
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选">' +
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
        '<option data="0" value="待选">待选</option>' +
        '</select>' +
        '</div>' +
        '<div class="c-swarm-name">' +
        '<input type="text" class="datas" value=""/>' +
        '</div>' +
        '</div>');
    $(this).parents(".c-swarm-interior").find(".c-contains-outside").append(newCondition);
});
//客户分群输入框赋值
$(document).on("blur", "input,select", function () {
    $(this).attr("value", $(this).val());
})
//客户分群初始化
function groupInit() {
    var initCount = 0;
    $(".c-swarm-content").empty();
    for (var i = 0; i < 1; i++) {
        initCount++;
        var newGroup = '<div class="c-swarm-interior">' +
            '<div class="c-swarm-interior-left">' +
            '<div class="c-swarm-name c-title">分组<b class="datas groupNum">' + initCount + '</b></div>' +
            '<div class="c-contains-outside">' +
            '<div class="c-contained-within">' +
            '<div class="c-select-two">' +
            '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" />' +
            '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
            '<option data="0" value="待选">待选</option></select>' +
            '</div>' +
            '<div class="c-swarm-name">' +
            '<input type="text" class="datas" value=""/>' +
            '</div>' +
            '<div class="c-select-one">' +
            '<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">' +
            '<option data="0" value="待选">待选</option><option data="&&" value="且">且</option><option data="||" value="或">或</option>' +
            '</select>' +
            '</div>' +
            '<div class="c-select-two">' +
            '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text"/>' +
            '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
            '<option data="0" value="待选">待选</option>' +
            '</select>' +
            '</div>' +
            '<div class="c-swarm-name">' +
            '<input type="text" class="datas" value=""/>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="c-swarm-name  c-positon-img"><span class="addCondition"></span><span style="margin-left: 14px;" class="delCondition"></span>' +
            '</div></div></div>';
        $(".c-swarm-content").append(newGroup);
    }
    showSwarm();
}
//显示分群管理
function showSwarm() {

    var param = {};
    param.processId = process_id;
    $(".c-swarm-iexternal").show();
    $(".c-positon-img").show();
    layer.open({
        type: 1,
        title: '编辑节点',
        maxmin: true,
        shadeClose: false,
        area: ['780px', '300px'],
        content: $('.c-swarm-dialog'),
        btn: ['保存', '取消'],
        yes: function (index, layero) {

            //点击确定操作(设置分组)
            groupJson.fields = new Array();
            //分组数
            var foriegn = $(".dialog").find(".c-swarm-name").find("b").length;
            groupJson.conditions = [];
            for (var i = 0; i < foriegn; i++) {
                var formulas = [];
                //条件有几行
                var relationLen = $(".dialog").find(".c-contains-outside").eq(i).find(".c-contained-within").length;//每组条件行数
                for (var j = 0; j < relationLen; j++) {
                    //每行有几个文本框
                    for (var k = 0; k < 8; k++) {
                        switch (k) {
                            case 0:
                                var field_code1 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).val();
                                // if(field_code1.length<1){
                                //     layer.msg("请选择字段",{time:1000});
                                //     return;
                                // }
                                var field_code = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).val();
                                var field_name = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).val();
                                var field_id = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).attr("fieldId");
                                var field_type = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).attr("field_type");
                                var fields = {
                                    field_code: field_code,
                                    field_name: field_name,
                                    field_id: field_id,
                                    field_type: field_type
                                };
                                var push = true;
                                if (groupJson.fields.length > 0) {
                                    for (var n = 0; n < groupJson.fields.length; n++) {
                                        if (groupJson.fields[n].field_id == fields.field_id) {
                                            push = false;
                                        }
                                    }
                                    if (push) {
                                        groupJson.fields.push(fields);
                                    }
                                } else {
                                    groupJson.fields.push(fields);
                                }
                                formulas[j] = {field_code1: field_code1};
                                continue;
                            case 1:
                                var operator1 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find("select.datas").find("option:selected").attr("value");
                                formulas[j] = {field_code1: field_code1, operator1: operator1};
                                continue;
                            case 2:
                                if ($(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(2).find("option").length > 0) {
                                    var value1 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(2).find("option:selected").attr("data");
                                    // if(value1.length<1){
                                    //     layer.msg("请输入值",{time:1000});
                                    //     return;
                                    // }
                                } else {
                                    var value1 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(2).val();
                                    // if(value1.length<1){
                                    //     layer.msg("请输入值",{time:1000});
                                    //     return;
                                    // }
                                }
                                formulas[j] = {field_code1: field_code1, operator1: operator1, value1: value1};
                                continue;
                            case 3:
                                var a = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(3).find("option:selected").attr("data");
                                if ($(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(3).find("option:selected").attr("data") == '0') {
                                    var relative_operator = '';
                                    formulas[j] = {
                                        field_code1: field_code1,
                                        operator1: operator1,
                                        value1: value1,
                                        relative_operator: ''
                                    };
                                    k = 8;
                                    j = relationLen;
                                    break;
                                } else {
                                    var relative_operator = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(3).find('option:selected').attr("data");
                                    formulas[j] = {
                                        field_code1: field_code1,
                                        operator1: operator1,
                                        value1: value1,
                                        relative_operator: relative_operator
                                    };
                                    continue;
                                }

                            case 4:
                                var field_code2 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).val();
                                var field_code = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).val();
                                var field_name = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).val();
                                var field_id = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).attr("fieldId");
                                var field_type = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).attr("field_type");
                                var fields = {
                                    field_code: field_code,
                                    field_name: field_name,
                                    field_id: field_id,
                                    field_type: field_type
                                };
                                var push = true;
                                if (groupJson.fields.length > 0) {
                                    for (var m = 0; m < groupJson.fields.length; m++) {
                                        if (groupJson.fields[m].field_id == fields.field_id) {
                                            push = false;
                                        }
                                    }
                                    if (push) {
                                        groupJson.fields.push(fields);
                                    }

                                } else {
                                    groupJson.fields.push(fields);
                                }
                                formulas[j] = {
                                    field_code1: field_code1,
                                    operator1: operator1,
                                    value1: value1,
                                    relative_operator: relative_operator,
                                    field_code2: field_code2
                                };
                                continue;
                            case 5:
                                var operator2 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(5).find("option:selected").attr("value");
                                formulas[j] = {
                                    field_code1: field_code1,
                                    operator1: operator1,
                                    value1: value1,
                                    relative_operator: relative_operator,
                                    field_code2: field_code2,
                                    operator2: operator2
                                };
                                continue;
                            case 6:
                                if ($(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(6).siblings("select").find("option").length > 0) {
                                    var value2 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(6).siblings("select").find("option:selected").attr("data");
                                } else {
                                    var value2 = $(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(6).val();
                                }

                                formulas[j] = {
                                    field_code1: field_code1,
                                    operator1: operator1,
                                    value1: value1,
                                    relative_operator: relative_operator,
                                    field_code2: field_code2,
                                    operator2: operator2,
                                    value2: value2
                                };
                                continue;
                            case 7:
                                if (j == relationLen - 1) {
                                    continue;
                                } else {
                                    var sign = $(".c-swarm-interior-left").eq(i).find(".l_relation").eq(j).find("option:selected").attr("data");
                                    formulas[j] = {
                                        field_code1: field_code1,
                                        operator1: operator1,
                                        value1: value1,
                                        relative_operator: relative_operator,
                                        field_code2: field_code2,
                                        operator2: operator2,
                                        value2: value2,
                                        sign: sign
                                    };
                                    console.log(formulas);
                                    continue;
                                }

                            default:
                                break;
                        }
                    }
                    groupJson.conditions[i] = {
                        group_name: "分组" + $(".c-swarm-name").find("b").eq(i).html(),
                        nextNode: '',
                        formulas: formulas
                    };
                }
            }
            var nodeId = node_2.id;
            var param = new Object();
            var html = $(".c_content").html();
            var groupSelHtml = $(".c-group-content").html();
            param.processId = process_id;
            param.id = nodeId;
            param.nodeId = nodeId;
            param.nodeX = node_x;
            param.nodeY = node_y;
            param.nodeName = node_name;
            param.nodeCode = node_code;
            param.nodeType = node_type;
            param.nodeOrder = node_order;
            param.excType = node_2.excType;
            var object = {dataId: node_dataId, url: node_url, type: node_type, html: html, groupSelHtml: groupSelHtml};
            object = JSON.stringify(object);
            param.params = object;
            if (nodeId != undefined) {
                param.nodeType = null;
                Comm.ajaxPost("decision_flow/getNode", JSON.stringify(param), function (result) {
                    var data = result.data;
                    if(data!=null){
                        groupJson_1 = JSON.parse(data.nodeJson);
                        var conditions=groupJson.conditions;
                        var conditions_1=groupJson_1.conditions;
                        for(var i=0;i<conditions.length;i++){
                            for (var j = 0; j < conditions_1.length; j++) {
                                if(conditions[i].group_name == conditions_1[j].group_name){
                                    conditions[i].nextNode = conditions_1[j].nextNode;
                                    break;
                                }
                            }
                        }
                        groupJson.conditions = conditions;
                        param.nodeJson=JSON.stringify(groupJson);
                        Comm.ajaxPost("decision_flow/update",JSON.stringify(param),function (result) {
                            layer.closeAll();
                            if(result.code==0){
                                layer.msg(result.msg,{time:1000});
                                choseNodes(1);
                            }
                        },"application/json")
                    }
                }, "application/json");
            } else {
                Comm.ajaxPost("decision_flow/getNode", JSON.stringify(param), function (result) {
                    var data = result.data;
                    if(data!=null){
                        debugger
                        param.pageUrl = data.pageUrl;
                        param.handUrl = data.handUrl;
                        param.className = data.className;
                        param.nodeJson = JSON.stringify(groupJson);
                        Comm.ajaxPost("decision_flow/save", JSON.stringify(param), function (data) {
                            layer.closeAll();
                            if (data.code == 0) {
                                layer.msg(data.msg, {time: 1000});
                                node_2.id = data.data.nodeId;
                                node_2.type = data.data.nodeType;
                                node_2.node_code = data.data.nodeCode;
                                node_2.node_order = data.data.nodeOrder;
                                choseNodes(1);
                            }
                        }, "application/json");
                    }
                }, "application/json");

                // param.nodeJson = JSON.stringify(groupJson);
                // Comm.ajaxPost("decision_flow/save", JSON.stringify(param), function (data) {
                //     layer.closeAll();
                //     if (data.code == 0) {
                //         layer.msg(data.msg, {time: 1000});
                //         node_2.id = data.data.nodeId;
                //         node_2.type = data.data.nodeType;
                //         node_2.node_code = data.data.nodeCode;
                //         node_2.node_order = data.data.nodeOrder;
                //         choseNodes(1);
                //     }
                // }, "application/json");
            }
        }
    });
}

//添加分组
var grouopNum=$(".groupNum").last().html();
$("body").on("click", ".c-swarm-name-add", function () {
    debugger
    grouopNum++;
    var newGroup = $('<div class="c-swarm-interior">' +
        '<div class="c-swarm-interior-left">' +
        '<div class="c-swarm-name c-title">分组<b class="datas groupNum">' + grouopNum + '</b></div>' +
        '<div class="c-contains-outside">' +
        '<div class="c-contained-within">' +
        '<div class="c-select-two">' +
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选"/>' +
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
        '<option data="0" value="待选">待选</option></select>' +
        '</div>' +
        '<div class="c-swarm-name">' +
        '<input type="text" class="datas" value=""/>' +
        '</div>' +
        '<div class="c-select-one">' +
        '<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">' +
        '<option data="0" value="待选">待选</option><option data="&&" value="且">且</option><option data="||" value="1">或</option>' +
        '</select>' +
        '</div>' +
        '<div class="c-select-two">' +
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选"/>' +
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">' +
        '<option data="0" value="待选">待选</option>' +
        '</select>' +
        '</div>' +
        '<div class="c-swarm-name">' +
        '<input type="text" class="datas" value=""/>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '<div class="c-swarm-name c-positon-img"><span class="addCondition"></span><span style="margin-left: 14px;" class="delCondition"></span>' +
        '</div></div></div>');
    $(".c-swarm-interior").last().after(newGroup);
});
//删除条件
var delId;
var group_name;
$("body").on("click", ".delCondition", function (e) {

    delId = node_2.id;
    var group_num = $(this).parents(".c-swarm-interior").find(".c-swarm-name b").html();
    group_name = '分组' + group_num;
    var _url = "";
    var _param = {"engineNodeId": delId, "branch": group_name};
    if (node_2.id != undefined) {
        Comm.ajaxPost("decision_flow/validateBranch", JSON.stringify(_param), function (data) {
            if (data.data.result == 1) {
                layer.msg("请先删除相关连线！", {time: 1000});
            } else {
                if ($(e.target).parents(".c-swarm-interior").find(".l_relation").length == 0 && $(".c-swarm-interior").length > 1) {
                    $(e.target).parents(".c-swarm-interior").remove();
                } else if ($(e.target).parents(".c-swarm-interior").find(".c-contained-within").length > 1) {
                    $(e.target).parents(".c-swarm-interior").find(".l_relation").last().parent().remove();
                    $(e.target).parents(".c-swarm-interior").find(".c-contained-within").last().remove();
                }
            }
        }, "application/json")
    } else {
        if ($(e.target).parents(".c-swarm-interior").find(".l_relation").length == 0 && $(".c-swarm-interior").length > 1) {
            $(e.target).parents(".c-swarm-interior").remove();
        } else if ($(e.target).parents(".c-swarm-interior").find(".c-contained-within").length > 1) {
            $(e.target).parents(".c-swarm-interior").find(".l_relation").last().parent().remove();
            $(e.target).parents(".c-swarm-interior").find(".c-contained-within").last().remove();
        }
    }
});
//失去焦点时改变改变select的value
$(".c-swarm-dialog").on("blur", ".l_before", function () {
    var vals = $(this).find("option:selected").text();
    $(this).find("option:selected").attr("value", vals);
    $(this).find("option").removeAttr("selected");
    $(this).find("option[value='" + vals + "']").attr("selected", "true");
    $(this).val(vals);

})
//选择字段
$(".c-swarm-dialog").on("click", ".c-swarm-input", function () {
    showFieldsList($(this));
    $(".c-swarm-input").removeClass("c-select-input");
    $(this).addClass("c-select-input");
    $(".selWord").show();
})
//选择字段
function showFieldsList(me) {
    var param = {};
    param.processId = process_id;
    Comm.ajaxPost('decision_flow/getJsonData', JSON.stringify(param), function (result) {

        var flag = true;
        var data = result.data.data;
        try {
            data = JSON.parse(data);
        } catch (e) {
            flag = false;
            layer.alert("该报文格式有误！");
        }
        var data1 = data.groupList;

        if (data1 == undefined) {
            flag = false;
        }
        if (flag && data1.length > 0) {
            layer.open({
                type: 1,
                title: "选择参数",
                area: ['500px', '400px'],
                content: $('#groupTree'),
                btn: ['确定', '取消'],
                success: function () {

                    $("#groupTree").empty();
                    var nodelist = "";
                    for (var i = 0; i < data1.length; i++) {
                        nodelist += "<input name='chkItem_csum' class='chkItem' type='radio' id='"+data1[i].valueType+"' value='" + data1[i].name + "' />" + data1[i].name+"<br/>";
                    }
                    $("#groupTree").append(nodelist);
                },
                yes: function (index, layero) {

                    //分群参数赋值
                    $(me).attr("value", $("input[name='chkItem_csum']:checked").val());
                    // var showFieldsPlace=$("#showFieldsPlace").val().split("/");
                    var valueType = $("input[name='chkItem_csum']:checked").attr("id");//$("#hiddenFields").html();
                    // var enName=$("#enName").html();
                    // var restrainScope = $("#hiddenRestrainScope").html();
                    // $(me).attr("value",showFieldsPlace[1]);
                    // $(me).attr("fieldid",showFieldsPlace[0]);
                    // $(me).attr("field_code",enName);
                    // $(me).attr("field_type",valueType);
                    // $(me).attr("valueScope",restrainScope);
                    // $(me).attr("dataValue",showFieldsPlace[0]+"|"+enName);
                    // $(me).attr("value",showFieldsPlace[1]);
                    // $(me).attr("fieldid",showFieldsPlace[0]);
                    // $(me).attr("field_code",enName);
                    $(me).attr("field_type", valueType);
                    // $(me).attr("valueScope",restrainScope);
                    // $(me).attr("dataValue",showFieldsPlace[0]+"|"+enName);
                    // $(me).next().children().remove();
                    var input = '<input type="text" class="datas" value="">';
                    if (valueType == 1) {//数值
                        $(me).parent().next().empty().append(input).show();
                        $(me).parent().next().children("select").hide().remove();
                        $(me).next().empty();
                        $(me).next().append('<option value=">">大于</option><option value="<">小于</option><option value=">=">大于等于</option><option value="<=">小于等于</option><option value="==">等于</option><option value="!=">不等于</option>');
                    } else if (valueType == 2) {//字符
                        $(me).parent().next().empty().append(input).show();
                        $(me).parent().next().children("select").hide().remove();
                        $(me).next().empty();
                        $(me).next().append('<option value="like">like</option><option value="not contains">not like</option><option value="==">等于</option><option value="!=">不等于</option>');
                    }
                    layer.close(index);
                }

            });
        } else {
            layer.alert("该报文格式有误！");
        }

    }, "application/json");

}
//保存字段
var arrLen = -1;
$(".selWord").on("click", ".fieldSave", function () {
    arrLen++;
    var push = true;
    var fieldSel = $("input[name='fieldCn']:checked").next().html();
    var fieldId = $("input[name='fieldCn']:checked").val();
    var field_type = $("input[name='fieldCn']:checked").attr("field_type");
    var field_code = $("input[name='fieldCn']:checked").attr("field_code");
    var valueScope = $("input[name='fieldCn']:checked").attr("valueScope");
    var fields = {field_id: fieldId, field_code: field_code, field_name: fieldSel, field_type: field_type};
    $(".c-select-input").val(fieldSel);
    $(".c-select-input").attr("value", fieldSel);
    $(".c-select-input").attr("fieldId", fieldId);
    $(".c-select-input").attr("field_code", field_code);
    $(".c-select-input").attr("field_type", field_type);
    $(".c-select-input").attr("valueScope", valueScope);
    $(this).parents(".dialog").hide();
    switch (field_type) {
        case '0':

            break;
        case '1':
            var inputs = $('<div class="c-swarm-name"><input type="text" class="datas" value=""></div>');
            $(".c-select-input").parent().next().remove();
            $(".c-select-input").parent().after(inputs);
            var options = $('<option data=">" value="大于">大于</option><option data=">=" value="大于等于">大于等于</option><option data="==" value="等于">等于</option><option data="!=" value="不等于">不等于</option><option data="<" value="小于">小于</option><option data="<=" value="小于等于">小于等于</option>');
            break;
        case '2':
            var inputs = $('<div class="c-swarm-name"><input type="text" class="datas" value=""></div>');
            $(".c-select-input").parent().next().remove();
            $(".c-select-input").parent().after(inputs);
            var options = $('<option data="contains" value="包含">包含</option><option data="notContains" value="不包含">不包含</option><option data="equals" value="等于">等于</option><option data="notEquals" value="不等于">不等于</option>');
            break;
        case '3':
            var options = $('<option data="==" value="等于">等于</option><option data="！=" value="不等于">不等于</option>');
            var option;
            var array = valueScope.split(",");
            for (var j = 0; j < array.length; j++) {
                var subArray = array[j].split(":")
                option += '<option data="' + subArray[1] + '" value="' + subArray[0] + '">' + subArray[0] + '</option>'
            }
            var selects = $('<div class="c-select-one"><select class="l_before datas" style="width:80px;height:29px;background-position: 60px 0px;" value="">' + option + '</select></div>');
            $(".c-select-input").parent().next().remove();
            $(".c-select-input").parent().after(selects);
            break;
        case '4':
            var inputs = $('<div class="c-swarm-name"><input type="text" class="datas" value=""></div>');
            $(".c-select-input").parent().next().remove();
            $(".c-select-input").parent().after(inputs);
            var options = $('<option data=">" value="大于">大于</option><option data=">=" value="大于等于">大于等于</option><option data="==" value="等于">等于</option><option data="!=" value="不等于">不等于</option><option data="<" value="小于">小于</option><option data="<=" value="小于等于">小于等于</option>');
            break;
        default:
            break;

    }

    $(".c-select-input").next().empty().append(options);


});
//分群选择取消按钮
$(".l_selGroupClose").on("click", function () {
    $(this).parents(".dialog").hide();
})

Array.prototype.unique3 = function () {
    var res = [];
    var json = {};
    for (var i = 0; i < this.length; i++) {
        if (!json[this[i]]) {
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
};
//定义方法删除数组制指定元素
Array.prototype.removeByValue = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
};

$("#option_fd").on("click", function () {
    var href = $(this).attr("hrefs");
    $(this).addClass("active_active").parent(".c-decisions-switcher").siblings(".c-decisions-switcher").find("a").removeClass("active_active");
    $("#" + href).show().siblings(".Manager_style").hide();
});
$("#option_sc").on("click", function () {
    var href = $(this).attr("hrefs");
    $(this).addClass("active_active").parent(".c-decisions-switcher").siblings(".c-decisions-switcher").find("a").removeClass("active_active");
    $("#" + href).show().siblings(".Manager_style").hide();
    getscores();
});

// function blackInit() {
//     var param = {};
//     param.processId = process_id;
//     Comm.ajaxPost('decision_flow/getJsonData', JSON.stringify(param), function (result) {
//         var flag = true;
//         var data = result.data.data;
//         try {
//             data = JSON.parse(data);
//         } catch (e) {
//             flag = false;
//             layer.alert("该报文格式有误！");
//         }
//         var data1 = data.blackList;
//         if (data1 == undefined) {
//             flag = false;
//         }
//         if (flag && data1.length > 0) {
//             layer.open({
//                 type: 1,
//                 title: "黑名单",
//                 area: ['500px', '400px'],
//                 content: $('#blackTree'),
//                 btn: ['保存', '取消'],
//                 success: function () {
//                     
//                     $("#blackTree").empty();
//                     var nodelist = "";
//                     for (var i = 0; i < data1.length; i++) {
//                         nodelist += "<input name='chkItem' class='chkItem' type='checkbox' />" + data1[i].name + "<br/>";
//                     }
//                     $("#blackTree").append(nodelist);
//                 },
//                 yes: function (index, layero) {
//                     //参数
//                     var params = '{"dataId":' + node_dataId + ',"url":"' + node_url + '","type":' + node_type + '}';
//                     var _params = {
//                         "processId": process_id,
//                         "nodeOrder": node_order,
//                         "nodeName": node_name,
//                         "nodeCode": node_code,
//                         "nodeX": node_x,
//                         "nodeY": node_y,
//                         "nodeType": node_type,
//                         "nodeJson": '',
//                         "params": params
//                     };
//                     Comm.ajaxPost("decision_flow/save", JSON.stringify(_params),
//                         function (result) {
//                             layer.closeAll();
//                             var data = result.data;
//                             var type = typeof(data);
//                             layer.msg("保存成功！", {time: 1000});
//                             if (type == "number") {
//                                 node_2.id = result.data;
//                                 $("input[name=nodeId]").val(result.data);
//                             } else {
//                                 node_2.id = result.data.nodeId;
//                                 $("input[name=nodeId]").val(result.data.nodeId);
//                             }
//                         }, "application/json"
//                     );
//                 }
//
//             });
//         } else {
//             layer.alert("黑名单暂无数据！");
//         }
//     }, "application/json");
// }
//
// function whiteInit() {
//     var param = {};
//     param.processId = process_id;
//     Comm.ajaxPost('decision_flow/getJsonData', JSON.stringify(param), function (result) {
//         
//         var flag = true;
//         var data = result.data.data;
//         try {
//             data = JSON.parse(data);
//         } catch (e) {
//             flag = false;
//             layer.alert("该报文格式有误！");
//         }
//         var data1 = data.whiteList;
//         if (data1 == undefined) {
//             flag = false;
//         }
//         if (flag && data1.length > 0) {
//             layer.open({
//                 type: 1,
//                 title: "白名单",
//                 area: ['500px', '400px'],
//                 content: $('#blackTree'),
//                 btn: ['保存', '取消'],
//                 success: function () {
//                     
//                     $("#blackTree").empty();
//                     var nodelist = "";
//                     for (var i = 0; i < data1.length; i++) {
//                         nodelist += "<input name='chkItem' class='chkItem' type='checkbox' />" + data1[i].name + "<br/>";
//                     }
//                     $("#blackTree").append(nodelist);
//                 },
//                 yes: function (index, layero) {
//                     //参数
//                     var params = '{"dataId":' + node_dataId + ',"url":"' + node_url + '","type":' + node_type + '}';
//                     var _params = {
//                         "processId": process_id,
//                         "nodeOrder": node_order,
//                         "nodeName": node_name,
//                         "nodeCode": node_code,
//                         "nodeX": node_x,
//                         "nodeY": node_y,
//                         "nodeType": node_type,
//                         "nodeJson": '',
//                         "params": params
//                     };
//                     Comm.ajaxPost("decision_flow/save", JSON.stringify(_params),
//                         function (result) {
//                             layer.closeAll();
//                             var data = result.data;
//                             var type = typeof(data);
//                             layer.msg("保存成功！", {time: 1000});
//                             if (type == "number") {
//                                 node_2.id = result.data;
//                                 $("input[name=nodeId]").val(result.data);
//                             } else {
//                                 node_2.id = result.data.nodeId;
//                                 $("input[name=nodeId]").val(result.data.nodeId);
//                             }
//                         }, "application/json"
//                     );
//                 }
//
//             });
//         } else {
//             layer.alert("黑名单暂无数据！");
//         }
//     }, "application/json");
// }
//点击保存操作
// $("#versionSave").on("click", function () {
//     $(".c-prompt-content-add").empty();
//     //获取所选版本号
//     var preVersionId = $("input[name=initEngineVersionId]").val();
//     var param = {'versionId': preVersionId};
//     Comm.ajaxGet("decision_flow/saveVersion", param,
//         function (result) {
//             layer.msg("生成子版本!", {time: 1000});
//             var data = result.data;
//             if ($(".l_stop_version").find(".l_left_versionTxt ").length > 0) {
//                 var pri_lastId = $(".l_stop_version").find(".l_left_versionTxt").last().find("b").html().split(".")[0];//新建版本所属主版本
//                 var child_lastId = parseInt($(".l_stop_version").find(".l_left_versionTxt ").last().find("b").html().split(".")[1]) + 1;//新建子版本号
//             } else {
//                 var pri_lastId = $(".ver_select").find("b").html();//新建版本所属主版本
//                 var child_lastId = 1;
//             }
//             $(".ver_select").removeClass("ver_select");
//             $(".l_stop_version").find(".run_point").removeClass("run_point").addClass("stop_point");
//
//             var newVersionHtml = $('<div class="l_left_versionTxt stopVersion ver_select"><span class="child_point run_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>' + pri_lastId + '.' + child_lastId + '</b></span></span></div>');
//             $(".l_right_versionTxt").append(newVersionHtml);
//
//             $("input[name=initEngineVersionId]").val(data.verId);
//             var newObj = {verId: data.verId, subVer: child_lastId};
//             map_child.get(pri_lastId).push(newObj);
//             child_msg.put(child_lastId, newObj);
//             $("#arrange").html("部署").attr("class", "arranging btn btn-primary");
//             init();
//         })
//     var disSave = [];
//     var allNodes = scene.getDisplayedNodes();
//     for (var i = 0; i < allNodes.length; i++) {
//         if (allNodes[i].type != 1 && (allNodes[i].inLinks == null || (allNodes[i].inLinks != null && allNodes[i].inLinks.length == 0))) {
//             disSave.push(allNodes[i].text);
//         }
//     }
//     var disSaves = disSave.unique3();
//     if (disSave.length != 0) {//判断是否有节点未进行连线
//         $(".c-hide-prompt").show();
//         var newContent = $('<div class="c-prompt-content" style="color:red;"></div>');
//         $(".c-prompt-content-add").append(newContent);
//         $(".c-prompt-content").last().empty().append('您的以下节点未进行连线：' + disSaves.join(','));
//     }
//     //判断沙盒与分群分组与连线是否匹配
//     var types = [3, 7];
//     var preVersionId = $("input[name=initEngineVersionId]").val();
//     var param_type = {types: types, versionId: preVersionId};
//     Comm.ajaxPost("engineVersion/getTypedNodes", param_type,
//         function (result) {
//             var data = result.data;
//             ;
//             if (data.hasOwnProperty("sanbox")) {
//                 if (data.sanbox == 0) {
//                     $(".c-hide-prompt").show();
//                     var newContent = $('<div class="c-prompt-content" style="color:red;"></div>');
//                     $(".c-prompt-content-add").append(newContent);
//                     $(".c-prompt-content").last().empty().append('您所设置的沙盒比例分组数与所连线数量不匹配！');
//                 }
//             }
//             if (data.hasOwnProperty("group")) {
//                 if (data.group == 0) {
//                     $(".c-hide-prompt").show();
//                     var newContent = $('<div class="c-prompt-content" style="color:red;"></div>');
//                     $(".c-prompt-content-add").append(newContent);
//                     $(".c-prompt-content").last().empty().append('您所设置的客户分群分组数与所连线数量不匹配！');
//                 }
//             }
//         })
// })
//复制
// $(".copy").on("click", function () {
//     var dataId = ($(".look").attr("dataId"));
//     var id = node_2.id;
//     var _param;
//     if (dataId == 4) {
//         _param = {"nodeId": id, "type": 1};
//     } else if (dataId == 5) {
//         _param = {"nodeId": id, "type": 2};
//     } else {
//         _param = {"nodeId": id}
//     }
//     copyNode(_param, node_2);
// })
//复制节点
// function copyNode(param, node_2) {
//     if (node_2.type == 1) {
//         layer.msg("开始节点不能复制！", {time: 1000});
//         $(".bigCircle").hide();
//     } else {
//         if (param.nodeId != null && param.nodeId != '') {
//             Comm.ajaxPost('decision_flow/copy', JSON.stringify(param), function (result) {
//                 var data = result.data;
//                 if (result.code == 0) {
//                     var c_node = $.parseJSON(data.params);
//                     var node = data;
//                     $(".bigCircle").hide();
//                     var r_node = createNode(Number(c_node.dataId), node.nodeName, c_node.url, Number(node.nodeX), Number(node.nodeY), c_node.type, 2);
//                     r_node.id = node.nodeId;
//                     r_node.node_order = order_count;
//                     if (order_count == 1) {
//                         r_node.node_code = "ND_START";
//                     } else {
//                         r_node.node_code = "ND_" + order_count;
//                     }
//                     layer.msg("复制成功", {time: 1000});
//                 } else {
//                     layer.msg("复制失败", {time: 1000});
//                 }
//             }, "application/json")
//         } else {
//             $(".bigCircle").hide();
//             createNode(node_2.dataId, node_2.text, node_2.url, node_2.x + 50, node_2.y + 50, node_2.type, 1);
//             layer.msg("复制成功", {time: 1000});
//         }
//     }
// }

//沙盒比例部分
// var riverNum = 2;
// var real_riverNum = 0;
// $(".sanbox-popup").on("click", ".c-remove", function (e) {//沙盒比例删除
//     var delSanBoxId = node_2.id;
//     var sanBox_num = $(this).parents(".c-sanbox-lie").find(".c-enter-proportion").text().replace("沙盒", "").replace(":", "");
//     var _param = {"engineNodeId": delSanBoxId, "branch": sanBox_num};
//     if (node_2.id != 0) {
//         Comm.ajaxPost("decision_flow/validateBranch", JSON.stringify(_param), function (reslut) {
//             var data = reslut.data;
//             if (data.result == 1) {
//                 layer.msg("请先删除相关连线！", {time: 1000});
//             } else {
//                 if ($(e.target).parents(".dialog").find(".c-sanbox-lie").length > 1) {
//                     $(e.target).parents(".c-sanbox-lie").remove();
//                 } else {
//                     return;
//                 }
//             }
//         }, "application/json")
//     } else {
//         if ($(this).parents(".dialog").find(".c-sanbox-lie").length > 1) {
//             $(this).parents(".c-sanbox-lie").remove();
//         } else {
//             return;
//         }
//     }
// });
// $(".sanbox-popup").on("click", ".c-sanbox-img", function () {
//     var sanNUm = $(this).parents(".dialog").find(".c-sanbox-lie").last().find(".c-enter-proportion").text().replace("沙盒", "").replace(":", "");
//     sanNUm++;
//     sanBoxName++;
//     var newRate = $('<div class="c-sanbox-lie">' +
//         '<div class="c-enter-proportion">沙盒' + sanNUm + ':</div>' +
//         '<input index="' + sanBoxName + '" name="sanBox_' + sanBoxName + '" type="text" style="margin-left:4px; " required="" maxlength="2"/>' +
//         '<div id="hidden_' + riverNum + '" style="display:none">0</div>' +
//         '<span style="margin-left:3px;">%</span>' +
//         '<div class="c-sanbox-delete" id="div_del_' + (riverNum) + '">' +
//         '<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>' +
//         '</div>');
//     $(".c-setSanbox").append(newRate);
//     $("#div_del_" + (riverNum - 1)).html('');
//     $("#div_del_" + (riverNum - 1)).html('<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>');
// });
// //沙盒比例初始化
// function initRate() {
//     $(".c-setSanbox").remove();
//     var newSanrate = $('<div class="c-sanbox-content c-setSanbox">' +
//         '<div class="c-sanbox-lie">' +
//         '<div class="c-enter-proportion">沙盒1:</div>' +
//         '<input index="1" name="sanBox_1" type="text" value="" style="margin-left:4px;" required="" maxlength="2" />' +
//         '<div id="hidden_1" style="display:none">0</div>' +
//         '<span style="margin-left:3px;">%</span>' +
//         '<div class="c-sanbox-img">' +
//         '添加' +
//         '</div>' +
//         '</div>' +
//         '<div class="c-sanbox-lie">' +
//         '<div class="c-enter-proportion">沙盒2:</div>' +
//         '<input index="2" name="sanBox_2" type="text"  value="" style="margin-left:4px;" required="" maxlength="2" />' +
//         '<div id="hidden_2" style="display:none">0</div>' +
//         '<span style="margin-left:3px;">%</span>' +
//         '<div class="c-sanbox-delete" id="div_del_2">' +
//         '<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>' +
//         '</div>' +
//         '</div>');
//     $(".sanbox-popup .c-sanbox-head").after(newSanrate);
//     showShaPan();
// }
// //沙盘弹出框
// function showShaPan() {
//     var indexs = layer.open({
//         type: 1,
//         title: '沙盘比例',
//         maxmin: true,
//         shadeClose: false,
//         area: ['300px', '270px'],
//         content: $('.sanbox-popup'),
//         btn: ['保存', '取消'],
//         yes: function (index, layero) {
//             //沙盒比例设置保存操作
//             var sanBoxJson = "[";
//             $(".sanbox-popup .c-sanbox-lie").each(function (index, element) {
//                 var val = $(element).find("input").val();
//                 sanBoxJson += '{"proportion":"' + val;
//                 var text = $(element).find(".c-enter-proportion").text().replace("沙盒", '');
//                 sanBoxJson += '","sandbox":"' + text.replace(":", '');
//                 sanBoxJson += '"},'
//             });
//             if (sanBoxJson.length > 1) {
//                 sanBoxJson = sanBoxJson.substring(0, sanBoxJson.length - 1)
//             }
//             sanBoxJson += "]";
//             var sanBoxJsonArr = JSON.parse(sanBoxJson);
//             var reg = /^([0-9]){1,2}$/;
//             for (var t = 0; t < sanBoxJsonArr.length; t++) {
//                 if (!sanBoxJsonArr[t].proportion) {
//                     layer.msg("请填写后再保存!", {time: 1000});
//                     return;
//                 }
//                 var num = Number(sanBoxJsonArr[t].proportion);
//                 if (!reg.test(num)) {
//                     layer.msg('请填写两位整数数字!');
//                     return;
//                 }
//             }
//             var nodeId = node_2.id;
//             var _param = new Object();
//             _param.processId = process_id;
//             _param.id = nodeId;
//             _param.nodeX = node_x;
//             _param.nodeY = node_y;
//             _param.nodeName = node_name;
//             _param.nodeCode = node_code;
//             _param.nodeType = node_type;
//             _param.nodeOrder = node_order;
//             _param.params = '{"dataId":"' + node_dataId + '","url":"' + node_url + '","type":"' + node_type + '" }';
//             if (nodeId != 0) {
//                 var param = {"nodeId": nodeId};
//                 Comm.ajaxPost("decision_flow/getNode", param, function (result) {
//                     var data = result.data;
//                     var nextNodes = data.nextNodes;
//                     if (data != null) {
//                         var sanboxJson_0 = JSON.parse(sanBoxJson);
//                         var sanboxJson_1 = JSON.parse(data.nodeJson);
//                         for (var i = 0; i < sanboxJson_0.length; i++) {
//                             for (var j = 0; j < sanboxJson_1.length; j++) {
//                                 if (sanboxJson_0[i].sandbox == sanboxJson_1[j].sandbox) {
//                                     sanboxJson_0[i].nextNode = sanboxJson_1[j].nextNode;
//                                     break;
//                                 }
//                             }
//                         }
//                         sanBoxJson = JSON.stringify(sanboxJson_0);
//                         _url = "decision_flow/update";
//                         _param.nextNodes = nextNodes;
//                         _param.nodeJson = sanBoxJson;
//                         Comm.ajaxPost(_url, JSON.stringify(_param), function (data) {
//                             if (data.code == 0) {
//                                 layer.msg(data.msg, {time: 1000});
//                             }
//                         }, "application/json");
//                     }
//                 }, false);
//             } else {
//                 // _url ="/decision_flow/save";
//                 _param.nodeJson = sanBoxJson;
//                 var param = JSON.stringify(_param)
//                 Comm.ajaxPost("decision_flow/save", param, function (data) {
//                     if (data.code == 0) {
//                         layer.msg(data.msg, {time: 1000});
//                         node_2.id = data.data;
//                     }
//                 }, "application/json");
//             }
//             layer.closeAll();
//         }
//     });
// }
// //查看
// $(".look").on("click", function () {
//     var dataId = ($(this).attr("dataId"));
//     switch (dataId) {
//         case '0':
//             console.log("黑名单");
//             blackInit(node_2.id, 'N');
//             $(".bigCircle").hide();
//             break;
//         case '1':
//             console.log("白名单");
//             whiteInit(node_2.id, 'N');
//             $(".bigCircle").hide();
//             break;
//         case '2':
//             console.log("沙盒比例");
//             sandboxedit();
//             $(".bigCircle").hide();
//             break;
//         case '3':
//             console.log("客户分群");
//             groupedit(node_2.id, 0);
//             $(".bigCircle").hide();
//             break;
//         case '4':
//             console.log("规则集");
//             lookOrOperate(node_2.id, "G");
//             $(".bigCircle").hide();
//             break;
//         case '5':
//             console.log("评分卡");
//             getpage();
//             $(".bigCircle").hide();
//             break;
//         case '6':
//             console.log("信用评级");
//             $(".scoreCard").css("display", "block");
//             autoCenter($(".scoreCard"));
//             $(".bigCircle").hide();
//             break;
//         case '7':
//             console.log("决策选项");
//             tagOption(node_2.id);
//             $(".bigCircle").hide();
//             //决策选项输入区间的验证
//             var section = /^(\(|\[)([1-9][0-9]*|0{1})(\.([0-9]+))?\,([1-9][0-9]*|0{1})(\.([0-9]+))?(\)|\])$/;
//             $(".l_inputs").on("focus", function () {
//                 $(this).addClass("l_foc")
//             })
//             $(".l_inputs").on("blur", function () {
//                 var sectionContent = $(".l_foc").val();
//                 if (section.test(sectionContent) == false) {
//                     layer.msg("您的输入不符合要求，请重新输入", {time: 1000});
//                     $(this).val('');
//                 }
//             })
//             break;
//         case '8':
//             console.log("额度计算");
//             $(".scoreCard").css("display", "block");
//             autoCenter($(".scoreCard"));
//             $(".bigCircle").hide();
//             break;
//         case '11':
//             console.log("复杂规则");
//             lookOrOperate(node_2.id, "F");
//             $(".bigCircle").hide();
//             break;
//         default:
//             break;
//     }
// });
// //数据填写弹框
// var infor;
// $("#clickWrite").on("click", function () {
//     $(".write-data-content").empty();
//     var engineMsg = $("input[name=initEngineVersionId]").val();
//     var engineVersion = {
//         verId: engineMsg
//     };
//     var sing;
//     Comm.ajaxPost("engine/engineField", JSON.stringify(engineVersion), function (result) {
//         var data = result.data;
//         $.each(data, function (index, value) {
//             var str = "";
//             infor = data[index];
//             var enumerateStr = infor.restrainScope;
//             var nameArr = enumerateStr.split(",");
//             var t_t = "";
//             for (var i = 0; i < nameArr.length; i++) {
//                 var arr_r = nameArr[i].split(":");
//                 for (var j = 0; j < arr_r.length - 1; j++) {
//                     var tt = "<option value=" + arr_r[1] + ">" + arr_r[0] + "</option>";
//                     t_t += tt;
//                 }
//             }
//             if (infor.valueType == 3) {
//                 str = $('<div class="write-data-every"><span name="' + infor.enName + '" title="' + infor.cnName + '">' + infor.cnName + '</span>' +
//                     '<select class="c-drop-down write-data-input">' + t_t + '</select></div>')
//             } else {
//                 str = $('<div class="write-data-every"><span  name="' + infor.enName + '" title="' + infor.cnName + '">' + infor.cnName + '</span><input type="text" name="test" class="write-data-input"/></div><form id="write_form_id">');
//             }
//             $(".write-data-content").append(str);
//         });
//         var contentDiv = $(".write-data-content").text();
//         if (contentDiv == "") {
//             $(".write-data-content").append("您的引擎没有字段...");
//             sing = 0;
//         } else {
//             sing = 1;
//         }
//     }, "application/json", "", "", "", false);
//     showDataLayer(sing);
// });
//点击子版本
// $(document).on("click", ".l_stop_version .l_left_versionTxt", function () {
//     $("#arrange").html("部署");
//     $("#arrange").attr("class", "arranging btn btn-primary");
//     $(".ver_select").removeClass("ver_select");
//     $(this).addClass("ver_select");
//     $(".l_stop_version").find(".run_point").removeClass("run_point").addClass("stop_point");
//     $(this).find(".stop_point").addClass("run_point").removeClass("stop_point");
//     var display_ver = $(".ver_select").find("b").html().split('.')[0];
//     var child_ver = $(".ver_select").find("b").html().split('.')[1];
//     var verLen = map_child.get(display_ver).length;
//     for (var i = 0; i < verLen; i++) {
//         if (map_child.get(display_ver)[i].subVer == child_ver) {
//             var pri_verId = map_child.get(display_ver)[i].verId;
//             console.log("子版本信息");
//             console.log(map_child.get(display_ver)[i]);
//         }
//     }
//     $("input[name=initEngineVersionId]").val(pri_verId);
//     init();
// });
//点击切换子版本
// $(document).delegate(".l_run_version .l_left_versionTxt", "click", function () {
//     $(".l_right_versionTxt").empty();
//     var versionNum = $(this).find("b").html();
//     var sub_version = map_child.get(versionNum);
//     var ver_state = pri_version.get(versionNum);
//     var presentVersionId = pri_msg.get(versionNum).verId;
//     $("input[name=initEngineVersionId]").val(presentVersionId);
//     if ($(this).hasClass("runVersion")) {
//         $("#arrange").html("停止部署");
//         $("#arrange").attr("class", "stopArrange btn btn-primary");
//         $(".l_left_versionTxt").removeClass("ver_select");
//         $(this).addClass("ver_select");
//         init();
//     } else {
//         $("#arrange").html("部署");
//         $("#arrange").attr("class", "arranging btn btn-primary");
//         $(".l_left_versionTxt").removeClass("ver_select");
//         $(this).addClass("ver_select");
//         init();
//     }
//     $(".run_point").attr("class", "stop_point");
//     $(this).find(".stop_point").attr("class", "run_point");
//     if (sub_version.length > 0) {
//         for (var i = 0; i < sub_version.length; i++) {
//             if (sub_version[i].subVer) {
//                 var newSubVersion = $('<div  class="l_left_versionTxt stopVersion"><span class="child_point stop_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>' + versionNum + '.' + sub_version[i].subVer + '</b></span></span></div>');
//             } else {
//                 var newSubVersion = $('<div  class="l_left_versionTxt stopVersion"><span class="child_point stop_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>' + versionNum + '.' + sub_version[i].subVersion + '</b></span></span></div>');
//             }
//
//             $(".l_right_versionTxt").append(newSubVersion);
//         }
//     }
// });
//数据填写弹出框
// function showDataLayer(sing) {
//     var btn;
//     if (sing == 1) {
//         btn = ['执行', '取消']
//     } else {
//         btn = [];
//     }
//     layer.open({
//         type: 1,
//         title: '数据填写',
//         maxmin: true,
//         shadeClose: false,
//         area: ['350px', '350px'],
//         content: $('.write-data-message'),
//         btn: btn,
//         yes: function (index, layero) {
//             //验证
//             var valObj = ""
//             var valName = ""
//             var mapObj = [];
//             // if($(".ver_select").find(".child_point").length>0){
//             //     var versionNum=$(".ver_select").find("b").html().split(".")[0];
//             // }else{
//             //     var versionNum=$(".ver_select").find("b").html();
//             // }
//             var engineMsg = $("input[name=initEngineVersionId] ").val();
//             for (var i = 0; i < $(".write-data-every").length; i++) {
//                 /*  if(infor.valueType==3){ */
//                 var inputMsg = $(".write-data-input").eq(i).val();
//                 /*  /*  }else{
//                  var inputMsg=$(".write-data-input").eq(i).val();
//                  } */
//                 var nameMsg = $(".write-data-every").eq(i).find("span").attr("name");
//                 valObj = inputMsg;
//                 valName = nameMsg;
//                 mapObj[i] = '{"' + valName + '":"' + valObj + '"}';
//             }
//             mapObj = JSON.stringify(mapObj);
//             Comm.ajaxPost("engine/pageCheck", {versionId: engineMsg, valueScope: mapObj}, function (result) {
//                 layer.closeAll();
//                 var data = result.data;
//                 if (data.id == null || data.id == 0) {
//                     layer.msg("执行失败", {time: 1000});
//                     return;
//                 } else {
//                     $(".write-data-content").empty();
//                     var newTab = window.open('about:blank');
//                     newTab.location.href = _ctx + "/engine/lookOver?resultSetId=" + data.id;//跳转到结果集详情
//                 }
//             })
//         },
//         cancel: function () {
//             $(".write-data-content").empty();
//         }
//     });
// }
//初始化版本部署部分
// function versionAction() {
//     var id = $("input[name='engineId']").val();
//     Comm.ajaxPost('engine/version', id, function (result) {
//             var data = result.data;
//             for (var i = 0; i < data.length; i++) {
//                 var subArr = [];
//                 if (data[i].engineVersion.bootState == 1) {//主版本状态为1
//                     var preVersionId = data[i].engineVersion.verId;
//                     pri_state = 1;
//                     var newVersion = $('<div class="l_left_versionTxt runVersion ver_select"><input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="run_point"></span><span >正在运行 &nbsp;V <b>' + data[i].engineVersion.version + '</b></span></div>');
//                     $(".l_run_version").append(newVersion);
//                     if (data[i].subEngineVersionList.length <= 0) {
//                         $(".l_right_versionTxt").empty();
//                     } else {
//                         var run_ver = $(".ver_select").find('b').html();
//                         for (var j = 0; j < data[i].subEngineVersionList.length; j++) {
//                             var newSubVersion = $('<div class="l_left_versionTxt stopVersion"><input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="stop_point"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>' + run_ver + '.' + data[i].subEngineVersionList[j].subVer + '</b></span></span></div>');
//                             subArr.push(data[i].subEngineVersionList[j]);
//                             $(".l_right_versionTxt").append(newSubVersion);
//                             child_msg.put(data[i].subEngineVersionList[j].subVersion, data[i].subEngineVersionList[j]);
//                         }
//                     }
//                     $("input[name='initEngineVersionId']").val(preVersionId);
//                     init();
//                 } else {//主版本状态为0
//                     var newVersion = $('<div class="l_left_versionTxt stopVersion"><span style="margin-right:10px;" class="stop_point"></span><span>未部署</span><span style="margin-left:20px;">修订版本 &nbsp;V <b>' + data[i].engineVersion.version + '</b></span></div>');
//                     $(".l_run_version").append(newVersion);
//                     if (data[i].subEngineVersionList.length > 0) {
//                         for (var j = 0; j < data[i].subEngineVersionList.length; j++) {
//                             subArr.push(data[i].subEngineVersionList[j]);
//                         }
//                     }
//                 }
//                 map_child.put(data[i].engineVersion.version, subArr);
//                 pri_version.put(data[i].engineVersion.version, data[i].engineVersion.bootState);
//                 pri_msg.put(data[i].engineVersion.version, data[i].engineVersion);
//                 if (i == data.length - 1 && pri_state == 0 && data[i].engineVersion.bootState == 0) {//所有版本状态为0，默认选中第一个版本
//                     if (i != 0) {
//                         if (data[i].subEngineVersionList.length > 0) {
//                             var run_ver = $(".ver_select").find('b').html();
//                             for (var j = 0; j < data[i].subEngineVersionList.length; j++) {
//                                 subArr.push(data[i].subEngineVersionList[j]);
//                                 child_msg.put(data[i].subEngineVersionList[j].subVersion, data[i].subEngineVersionList[j]);
//                             }
//                         }
//                     }
//                     $(".l_run_version").find(".l_left_versionTxt").eq(0).find(".stop_point").attr("class", "run_point");
//                     $(".l_run_version").find(".l_left_versionTxt").eq(0).addClass("ver_select");
//                     if (data[0].subEngineVersionList.length <= 0) {
//                         $(".l_right_versionTxt").empty();
//                     } else {
//                         var run_ver = $(".ver_select").find('b').html();
//                         for (var j = 0; j < data[0].subEngineVersionList.length; j++) {
//                             var newSubVersion = $('<div  class="l_left_versionTxt stopVersion"><input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="stop_point"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>' + run_ver + '.' + data[0].subEngineVersionList[j].subVer + '</b></span></span></div>');
//                             $(".l_right_versionTxt").append(newSubVersion);
//                             if ($(".l_right_versionTxt").find(".l_left_versionTxt").length > 3) {
//                                 $(".l_right_versionTxt").css({
//                                     'overflow': 'auto'
//                                 });
//                             }
//                         }
//                     }
//                     $("#arrange").html("部署").attr("class", "arranging btn btn-primary");
//                     var preVersionIds = data[0].engineVersion.verId;
//                     $("input[name='initEngineVersionId']").val(preVersionIds);
//                     init();
//                 }
//             }
//             console.log('主版本对应的子版本信息');
//             console.log(map_child);
//             console.log('子版本对应的版本信息');
//             console.log(child_msg);
//         }, "application/json"
//     )
// }
//引入评分卡弹框页面
// function getpage() {
//     var isShowScoreFileds = $("#isShowScoreFileds").val();
//     if (isShowScoreFileds) {
//         g_scoreManage.tableUser.ajax.reload();
//     } else {
//         g_scoreManage.tableUser = $('#scoreFields_list').dataTable($.extend({
//             'iDeferLoading': true,
//             "bAutoWidth": false,
//             "Processing": true,
//             "ServerSide": true,
//             "sPaginationType": "full_numbers",
//             "bPaginate": true,
//             "bLengthChange": false,
//             "iDisplayLength": 5,
//             "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
//             "ajax": function (data, callback, settings) {
//                 var queryFilter = {};
//                 
//                 queryFilter.processId = process_id;
//                 Comm.ajaxPost('decision_flow/getJsonData', JSON.stringify(queryFilter), function (result) {
//                     
//                     var data = result.data.data;
//                     try {
//                         data = JSON.parse(data);
//                     } catch (e) {
//                         flag = false;
//                         layer.alert("该报文格式有误！");
//                     }
//                     var data1 = data.scorecardList;
//                     if (data1 == undefined) {
//                         flag = false;
//                         return;
//                     }
//                     var returnData = {};
//                     var resData = data1;
//                     var resPage = result.data;
//                     returnData.draw = data.draw;
//                     returnData.recordsTotal = 1;
//                     returnData.recordsFiltered = 1;
//                     returnData.data = resData;
//                     callback(returnData);
//                 }, "application/json")
//
//             },
//             "order": [],
//             "columns": [
//                 {
//                     "data": null,
//                     "searchable": false, "orderable": false,
//                     "class": "hidden"
//                 },
//                 {
//                     "className": "childBox",
//                     "orderable": false,
//                     "data": null,
//                     "width": "60px",
//                     "searchable": false,
//                     "render": function (data, type, row, meta) {
//                         if (data.checked) {
//                             return '<input type="checkbox" value="' + data.id + '" style="cursor:pointer;" isChecked="false" cName="' + data.name + '" checked="' + data.checked + '">'
//                         } else {
//                             return '<input type="checkbox" value="' + data.id + '" style="cursor:pointer;" isChecked="false" cName="' + data.name + '" >'
//                         }
//
//                     }
//                 },
//                 {
//                     "data": "id",
//                     "searchable": false, "orderable": false
//                 },
//                 {"data": "name", "orderable": false, "searchable": false},
//                 {"data": "desc", "orderable": false, "searchable": false},
//                 {
//                     "data": "createTime",
//                     "searchable": false,
//                     "orderable": false,
//                     "render": function (data, type, row, meta) {
//                         if (data == null) {
//                             return "";
//                         } else return json2TimeStamp(data);
//                     }
//                 }
//             ],
//             "createdRow": function (row, data, index, settings, json) {
//             },
//             "initComplete": function (settings, json) {
//                 $("#btn_search_score").click(function () {
//                     g_scoreManage.fuzzySearch = true;
//                     g_scoreManage.tableUser.ajax.reload();
//                 });
//                 $("#btn_search_reset_score").click(function () {
//                     $('input[name="checkscores"]').val("");
//                     g_scoreManage.fuzzySearch = true;
//                     g_scoreManage.tableUser.ajax.reload();
//                 });
//                 $("#scoreFields_list tbody").delegate('tr', 'click', function (e) {
//                     var target = e.target;
//                     if (target.nodeName == 'TD') {
//                         var input = target.parentNode.children[1].children[0];
//                         var isChecked = $(input).attr('isChecked');
//                         var selectArray = $("#scoreFields_list tbody input:checked");
//                         if (selectArray.length > 0) {
//                             for (var i = 0; i < selectArray.length; i++) {
//                                 $(selectArray[i]).attr('checked', false);
//                                 $(input).attr('isChecked', 'false');
//                             }
//                         }
//                         if (isChecked == 'false') {
//                             if ($(input).attr('checked')) {
//                                 $(input).attr('checked', false);
//                             } else {
//                                 $(input).attr('checked', 'checked');
//                             }
//                             $(input).attr('isChecked', 'true');
//                         } else {
//                             if ($(input).attr('checked')) {
//                                 $(input).attr('checked', false);
//                             } else {
//                                 $(input).attr('checked', 'checked');
//                             }
//                             $(input).attr('isChecked', 'false');
//                         }
//                     }
//                 });
//             }
//         }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
//         g_scoreManage.tableUser.on("order.dt search.dt", function () {
//             g_scoreManage.tableUser.column(0, {
//                 search: "applied",
//                 order: "applied"
//             }).nodes().each(function (cell, i) {
//                 cell.innerHTML = i + 1
//             })
//         }).draw();
//         $("#isShowScoreFileds").val(1);
//     }
//     showScoreFields();
// }
// //layer弹出评分卡
// function showScoreFields() {
//     var indexs = layer.open({
//         type: 1,
//         title: '评分卡列表',
//         maxmin: true,
//         shadeClose: false,
//         area: ['750px', '450px'],
//         content: $('.c-createusers-dialogs'),
//         btn: ['保存', '取消'],
//         yes: function (index, layero) {
//             
//             //评分卡id
//             var card_id = $("#scoreFields_list tbody input:checked").val();
//             //评分卡名称
//             var card_name = $("#scoreFields_list tbody input:checked").attr("cName");
//             //参数
//             var params = '{"dataId":' + node_dataId + ',"url":"' + node_url + '","type":' + node_type + '}';
//             //判断是否选中
//             if (card_id != null && card_id != undefined) {
//                 if (node_2.id == 0) {//保存节点
//                     var param = {
//                         cardId: card_id,
//                         processId: process_id,
//                         nodeName: card_name,
//                         nodeCode: node_code,
//                         nodeType: node_type,
//                         nodeOrder: node_order,
//                         nodeX: node_x,
//                         nodeY: node_y,
//                         nodeJson: card_id,
//                         params: params
//                     };
//                     Comm.ajaxPost("decision_flow/save", JSON.stringify(param), function (result) {
//                         layer.closeAll();
//                         if (result.code == 0) {
//                             layer.msg(result.msg, {time: 1000});
//                             node_2.text = card_name;
//                             node_2.id = result.data;
//                         } else {
//                             layer.msg("保存失败", {time: 1000});
//                         }
//                     }, "application/json")
//                 } else {//修改节点
//                     var processNode = {
//                         id: node_2.id,
//                         cardId: card_id,
//                         processId: process_id,
//                         nodeName: card_name,
//                         nodeCode: node_code,
//                         nodeType: node_type,
//                         nodeOrder: node_order,
//                         nodeX: node_x,
//                         nodeY: node_y,
//                         nodeJson: card_id,
//                         params: params,
//                         type: 1
//                     };
//                     Comm.ajaxPost("decision_flow/update", JSON.stringify(processNode), function (result) {
//                         layer.closeAll();
//                         if (result.code == 0) {
//                             layer.msg(result.msg, {time: 1000});
//                             node_2.text = card_name;
//                         } else {
//                             layer.msg("保存失败", {time: 1000});
//                         }
//                     }, "application/json")
//                 }
//
//             } else {
//                 layer.msg("请选择评分卡", {time: 1000});
//             }
//         }
//     });
// }
//沙盒编辑
// function sandboxedit() {
//     var _param = {"nodeId": node_2.id};
//     var obj = null;
//     riverNum = 0;
//     real_riverNum = 0;
//     if (node_2.id != 0) {
//         Comm.ajaxPost("decision_flow/getNode", _param, function (reslut) {
//             var data = reslut.data;
//             if (data != null) {
//                 var json = data.nodeJson;
//                 if (json != null && json != "") {
//                     obj = JSON.parse(data.nodeJson);
//                 }
//                 try {
//                     $(".c-setSanbox").remove();
//                     var newSanrate = '<div class="c-sanbox-content c-setSanbox">';
//                     for (var i = 0; i < obj.length; i++) {
//                         riverNum++;
//                         real_riverNum++;
//                         sandbox = obj[i].sandbox;
//                         proportion = obj[i].proportion;
//                         if (i == 0) {
//                             newSanrate += '<div class="c-sanbox-lie">' +
//                                 '<div class="c-enter-proportion">沙盒' + sandbox + ':</div>' +
//                                 '<input name="sanBox" type="text" value="' + proportion + '" style="margin-left:4px;" required="" maxlength="2" />' +
//                                 '<div id="hidden_' + (i + 1) + '" style="display:none">1</div>' +
//                                 '<span style="margin-left:3px;">%</span>' +
//                                 '<div class="c-sanbox-img">' +
//                                 '添加' +
//                                 '</div>' +
//                                 '</div>';
//                         } else {
//                             newSanrate += '<div class="c-sanbox-lie">' +
//                                 '<div class="c-enter-proportion">沙盒' + sandbox + ':</div>' +
//                                 '<input name="sanBox" type="text" value="' + proportion + '"  style="margin-left:4px;" required="" maxlength="2"/>' +
//                                 '<div id="hidden_' + (i + 1) + '" style="display:none">1</div>' +
//                                 '<span style="margin-left:2px;">%</span>' +
//                                 '<div class="c-sanbox-delete" id="div_del_' + (i + 1) + '">' +
//                                 '<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>' +
//                                 '</div>' +
//                                 '</div>';
//                         }
//                     }
//                     newSanrate += '</div>';
//                     $(".sanbox-popup .c-sanbox-head").after(newSanrate);
//                     showShaPan();
//                 } catch (exception) {
//                     $(".c-setSanbox").remove();
//                     initRate();
//                     riverNum = 2
//                 } finally {
//                 }
//             } else {
//                 initRate();
//                 riverNum = 2;
//             }
//         })
//     } else {
//         initRate();
//         riverNum = 2;
//     }
// }

//决策选项弹出框
// function tagOption(nodeId) {
//     layer.open({
//         type: 1,
//         title: '决策选项',
//         maxmin: true,
//         shadeClose: false,
//         area: ['750px', '380px'],
//         content: $('.c-decision'),
//         btn: ['保存', '取消'],
//         yes: function (index, layero) {
//             var initEngineVersionId = $("input[name=initEngineVersionId]").val();
//             var nodeId = node_2.id;
//             var _param = new Object();
//
//             _param.initEngineVersionId = initEngineVersionId;
//             _param.id = nodeId;
//             _param.nodeX = node_x;
//             _param.nodeY = node_y;
//             _param.nodeName = node_name;
//             _param.nodeCode = node_code;
//             _param.nodeType = node_type;
//             _param.nodeOrder = node_order;
//             var inputs = $("#d-option").find("input").length;
//             var condition_type = getCondition_type();
//             var input = "[";
//             $("#d-option").find("input").each(function (index, element) {
//                 var obj = $(this);
//                 input += '{"field_id":' + $(obj).attr("dataId");
//                 var code = $(obj).attr("fieldCode");
//                 if (!code) {
//                     layer.msg("请选择后保存！", {time: 1000});
//                     return;
//                 } else {
//                     if (code.indexOf("SC_") > -1) {
//                         code = code + "_score";
//                     }
//                 }
//                 input += ',"field_code":"' + code;
//                 input += '","field_name":"' + $(obj).attr("value");
//                 if (condition_type == 2) {
//                     input += '","segemnts":[' + $(element).attr("data") + ']'
//                 } else {
//                     input += '"';
//                 }
//                 input += ',"field_scope":"' + $(obj).attr("valueScope");
//                 input += '","field_type":' + $(obj).attr("valueType") + '}';
//                 input += ","
//             });
//             input = input.substring(0, input.length - 1);
//             input += "]";
//             if (input.indexOf("undefined") != -1) {
//                 layer.msg("请选择后保存！", {time: 1000});
//                 return;
//             }
//             var outObj = $("#d-option-out").find('input');
//             var output = '{"field_id":' + $(outObj).attr("dataId") + ',"field_code":"' + $(outObj).attr("fieldCode") + '","field_name":"' + $(outObj).attr("value") + '","field_type":' + $(outObj).attr("valueType") + ',"field_scope":"' + $(outObj).attr("valueScope") + '"}'
//             var str = "[";
//             if (inputs == 1) {
//                 if (condition_type == 1) {
//                     str += assembledJson_1();
//                 } else {
//                     str += getFormulaJson("d-option-1");
//                 }
//             } else if (inputs == 2) {
//                 if (condition_type == 1) {
//                     str += assembledJson_2();
//                 } else {
//                     str += getFormulaJson("d-option-2");
//                 }
//             } else {
//                 str += getFormulaJson("d-option-3");
//             }
//             str += "]";
//             if (output.indexOf("undefined") != -1) {
//                 layer.msg("请选择后保存！", {time: 1000});
//                 return;
//             }
//             _param.params = '{"dataId":"' + node_dataId + '","url":"' + node_url + '","type":"' + node_type + '"}';
//             _param.nodeJson = '{"inputs":' + inputs + ',"condition_type":' + condition_type + ',"input":' + input + ',"output":' + output + ',"conditions":' + str + '}';
//             var _url;
//             if (nodeId != 0) {
//                 _url = "decision_flow/update";
//             } else {
//                 _url = "decision_flow/save";
//             }
//             ;
//             Comm.ajaxPost(_url, JSON.stringify(_param), function (result) {
//                 layer.closeAll();
//                 layer.msg(result.msg, {time: 1000});
//                 if (result.code == 0) {
//                     if (result.data) {
//                         node_2.id = result.data;
//                     }
//                 }
//             }, "application/json")
//         },
//         success: function () {
//             getField(nodeId);
//             lookOrOperateForDecisionOption(nodeId);
//         }
//     });
// }

//沙盒比例选择框取消按钮
// $(".l_sanboxClose").on("click", function () {
//     $(this).parents(".dialog").hide();
// });
//区域删除
// $("#stageDel").on("click", function () {
//     var allNodes = scene.selectedElements;
//     var idList = new Array();
//     var array = new Array();
//     allNodes.sort(compare('id'))
//     for (var i = 0; i < allNodes.length; i++) {
//         if (allNodes[i].type == 1) {
//             continue;
//         } else {
//             idList.push(allNodes[i].id);
//             array.push({"subNodeId": allNodes[i].id, "preNodeId": allNodes[i].inLinks[0].nodeA.id});
//         }
//     }
//     var _url = "decision_flow/deleteNodes";
//     var _param = {"idList": idList, "array": JSON.stringify(array)};
//     $.post(_url, _param, function (data) {
//         if (data.result == 1) {
//             for (var i = 0; i < allNodes.length; i++) {
//                 if (allNodes[i].type == 1) {
//                     continue;
//                 } else {
//                     scene.remove(allNodes[i])
//                 }
//             }
//         }
//         layer.msg(data.msg, {time: 1000});
//     })
// })
// function compare(property) {
//     return function (a, b) {
//         var value1 = a[property];
//         var value2 = b[property];
//         return value2 - value1;
//     }
// }
//客户分群数据查询条件
// var g_cumManage = {
//     tableUser: null,
//     currentItem: null,
//     fuzzySearch: false,
//     getQueryCondition: function (data) {
//         var paramFilter = {};
//         var page = {};
//         var param = {};
//         param.engineId = $("input[name='engineId']").val();
//
//         //自行处理查询参数
//         param.fuzzySearch = g_cumManage.fuzzySearch;
//         if (g_cumManage.fuzzySearch) {
//             param.searchKey = $("#ziduanchaxun input[name='Parameter_search']").val();
//         }
//
//         paramFilter.param = param;
//         page.firstIndex = data.start == null ? 0 : data.start;
//         page.pageSize = data.length == null ? 10 : data.length;
//         paramFilter.page = page;
//         return paramFilter;
//     }
// };
//评分卡数据查询条件
// var g_scoreManage = {
//     tableUser: null,
//     currentItem: null,
//     fuzzySearch: false,
//     getQueryCondition: function (data) {
//         var paramFilter = {};
//         var page = {};
//         var param = {};
//         param.engineId = $("input[name='engineId']").val();
//         param.nodeId = node_2.id;
//         param.status = "1";
//         //自行处理查询参数
//         param.fuzzySearch = g_scoreManage.fuzzySearch;
//         if (g_scoreManage.fuzzySearch) {
//             param.scorecardName = $("input[name='checkscores']").val();
//         }
//         paramFilter.param = param;
//         page.firstIndex = data.start == null ? 0 : data.start;
//         page.pageSize = data.length == null ? 10 : data.length;
//         paramFilter.page = page;
//         return paramFilter;
//     }
// };