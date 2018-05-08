
var process_id=$("#process_id").val();
var node_order;//节点编号（唯一）
var node_code;//节点唯一标识
var node_x;//节点x坐标
var node_y;//节点y坐标
var node_name;//节点名称
var node_type;//节点类型
var node_excType;
var node_url;//节点图片路径
var node_dataId;//节点类型标识编号
var node_2;//右键点击节点后的节点变量
var c=0;
var id;
var sceneX=0,sceneY=0;//画布位移（初始为0）
var order_count = 0;//节点编号变量
var canvas = document.getElementById('canvas');
var stage = new JTopo.Stage(canvas);
var groupJson=new Object();
var initEngineVersionId =$("input[name=initEngineVersionId]").val();
var sanBoxJsonObj=[];
var sanLine;//沙盒验证变量
var groupLine;//分群验证变量
//点击放大
$("#stageEnlarge").on("click",function(){
	stage.zoomOut(0.85);
});
//点击缩小
$("#stageNarrow").on("click",function(){
	stage.zoomIn(0.85);
});
//显示工具栏
showJTopoToobar(stage);
var scene = new JTopo.Scene(stage);
var nodes = [{
	node: {
		dataId: "0",
		text: "决策节点",
		type: 100,
		excType:3,
		url: _ctx+ "/resources/images/decision/决策选项.png"
	}
}];
var pageX;
var pageY;
var canvasX;
var canvasY;
var differenceX;
var differenceY;
var drag = false;
var sanbox;
var l_lineText;
var nextNode;
var moveDrag=false;

//定义方法删除数组制指定元素
Array.prototype.removeByValue = function(val) {
	for(var i=0; i<this.length; i++) {
		if(this[i] == val) {
			this.splice(i, 1);
			break;
		}
	}
};

function editNode(flag) {
	debugger
	var processId = process_id;
	var nodeId = $("#nodeId").val();
	if(nodeId!=0&&nodeId!=null&&nodeId!=undefined&&nodeId.length>0){
		if(flag==1){//修改
			layer.open({
				type : 1,
				title : "修改节点",
				area : [ '300px', '260px' ],
				content : $('#addNodes'),
				btn : [ '保存', '取消' ],
				success: function () {
					var process = {
						nodeId : nodeId
					}
					Comm.ajaxPost(
						'decision_flow/getNode',JSON.stringify(process),
						function(result){
							var data = result.data;
							if(data!=null){
								if(data.excType==0){//自动节点
									$("#urlInput").addClass("labelStyle_hide");
									$("#executeClass").removeClass("labelStyle_hide");
									$("#engineIds").removeClass("labelStyle_hide");
								}else{//人工节点
									$("#executeClass").addClass("labelStyle_hide");
									$("#engineIds").addClass("labelStyle_hide");
									$("#urlInput").removeClass("labelStyle_hide");
									$("#urlDel").removeClass("labelStyle_hide");
								}
								$('#selectType').val(data.excType);
								$('#className').val(data.className);
								$('#pageUrl').val(data.pageUrl);
								$('#handUrl').val(data.handUrl);
								$('#engineId').val(data.engineId);
								$('#text').val(data.nodeName);
								$('#icon').val(data.nodeUrl);
							}
						},"application/json"
					);
				},
				yes : function(index, layero) {
					if ($('#orger_name').val() == "") {
						layer.msg("名称不能为空",{time:2000});
						return;
					}
					debugger
					var executeClass=$('#className').val();
					var urlInput=$('#pageUrl').val();
					var handUrl = $('#handUrl').val();
					var text=$('#text').val();
					var url="/resources/images/decision/createDiy.png";//$('#icon').val();
					var type=$("#selectType option:selected").val();//节点类型 自动节点或人工节点
					var engineId = $('#engineId').val();
					var process={
						nodeId : nodeId,
						excType:type,
						className:executeClass,
						pageUrl:urlInput,
						handUrl:handUrl,
						nodeX : null,
						nodeY : null,
						engineId:engineId,
						nodeName:text,
						nodeUrl:"/resources/images/decision/createDiy.png",
					};
					Comm.ajaxPost(
						'decision_flow/updateNode',JSON.stringify(process),
						function(data){
							layer.closeAll();
							layer.msg(data.msg,{time:2000},function () {
								$("#nodeId").val('');
								choseNodes(1);
								init(1);
							});
						},"application/json"
					);
				}
			});
		}else if(flag==2){//删除
			layer.open({
				type : 1,
				title : "删除节点",
				area : [ '300px', '260px' ],
				content : "确认删除吗？",
				btn : [ '确认', '取消' ],
				success: function () {
				},
				yes : function(index, layero) {
					debugger
					var process={
						nodeId : nodeId,
						processId : processId
					};
					Comm.ajaxPost(
						'decision_flow/delNode',JSON.stringify(process),
						function(data){
							layer.closeAll();
							debugger
							layer.msg(data.msg,{time:2000},function () {
								$("#nodeId").val('');
								choseNodes(1);
								init(1);
							});
						},"application/json"
					);
				}
			});
		}else {
			return;
		}
	}else if(nodeId==0&&nodeId.length>0){
		layer.msg("初始决策节点不可操作");
	}else {
		layer.msg("请先选中节点");
	}
}

function edit(nodeId) {
	$("#nodeId").val(nodeId);
}
$(document).on("mousedown", "#l_decisions li", function(e) {
	debugger
	e.preventDefault();
	$(".l_decision_options li").removeClass("selected");
	$(this).addClass("selected");
	drag = true;
	$(".l_decision_options li").removeClass("l_click");
	$(this).addClass("l_click");
	pageX = e.pageX;
	pageY = e.pageY;
	canvasX = $("#canvas").offset().left;
	canvasY = $("#canvas").offset().top;
	differenceX = pageX - canvasX;
	differenceY = pageY - canvasY;
	var index = $(".l_click").attr("dataId");
	var src = nodes[index].node.url;
	var newNode = $('<div class="testImg" style="position: absolute;width:45px;height:45px;">' +
		'<img src="' + src + '" />' +
		'</div>');
	$(".rightOperateArea").append(newNode);
	newNode.hide();
});
$(document).on("mousemove", function(e) {
	e.preventDefault();
	if(drag) {
		$(".testImg").show();
		pageX = e.pageX;
		pageY = e.pageY;
		differenceX = pageX - canvasX;
		differenceY = pageY - canvasY;
		$(".testImg").css({
			left: differenceX - 22 + "px",
			top: differenceY - 22 + "px",
			opacity: 0.5
		});
	}

});
$(document).on("mouseup", function(e) {
	e.preventDefault();
	if(drag && differenceX > 0 && differenceY > 0) {
		$(".testImg").remove();
		var dataId = $(".l_click").attr("dataId");
		var text = nodes[dataId].node.text;
		var url = nodes[dataId].node.url;
		var x = differenceX - 22-sceneX;
		var y = differenceY - 22-sceneY;
		var type=nodes[dataId].node.type;
		var excType=nodes[dataId].node.excType;
		createNode(dataId, text, url, x, y,type,excType);
		$(".l_click").removeClass("l_click");
		drag = false;
		$("#nodeId").val('');
	} else {
		drag = false;
		$(".testImg").remove();
	}

});
//获取当前流程下的最大node_order并加1
function  getNodeOrder() {
	var _param_ = {};
	var data;
	_param_.processId = process_id;
	Comm.ajaxPost("decision_flow/getMaxNodeOrder",JSON.stringify(_param_),function(result){
		if(result.code==0){
			data=result.data;
		}
	},"application/json","","","",false);
	return data;
}

//获取当前流程下的最大node_type并加1
function  getNodeType() {
	var _param_ = {};
	var data;
	_param_.processId = process_id;
	Comm.ajaxPost("decision_flow/getMaxNodeType",JSON.stringify(_param_),function(result){
		if(result.code==0){
			data=result.data;
		}
	},"application/json","","","",false);
	return data;
}
//定义一个方法创建相应节点
function createNode(dataId, text, url, x, y,type,excType) {
	order_count++;
	var node_1 = new JTopo.Node(text);
	//点击框选
	var areas=false;
	$("#areaSelect").on("click",function(){
		if(areas==false){
			node_1.showSelected=true;
			stage.mode="select";
			areas=true;
		}else{
			stage.mode = "normal";
			node_1.showSelected=false;
			areas=false;
		}
	})
	node_1.showSelected = false; // 不显示选中矩形
	node_1.setLocation(x, y);
	node_1.setSize(45, 45);
	node_1.fontColor = "48,48,48";
	node_1.borderRadius = 22.5;
	node_1.font = "12px";
	node_1.dataId=dataId;
	node_1.setImage(url);
	node_1.type=type;
	node_1.url=url;
	node_1.excType=excType;
	var _node_max_order=getNodeOrder();
	if(node_1.type == 1){
		node_1.node_code = "ND_START";
	}else{
		node_1.node_code = "ND_"+_node_max_order;
	}
	node_1.node_order = _node_max_order;
	node_1.node_json=new Array();
	node_1.next_node =new Array();
	scene.add(node_1);
	//获取节点坐标
	//右键菜单部分
	var nodel;
	function handler(event) {
		if(event.button == 2) { // 右键
			// 当前位置弹出菜单（div）
			$(".bigCircle").css({
				top: event.pageY - 75 + "px",
				left: event.pageX - 75 + "px"
			}).show();
			//获取节点坐标
			node_2=event.target;
			node_x=event.target.x;
			node_y=event.target.y;
			node_name=event.target.text;
			node_type=event.target.type;
			node_excType=event.target.excType;
			node_code = event.target.node_code;
			node_order = event.target.node_order;
			node_url=event.target.url;
			node_dataId=event.target.dataId;

			$("#node_2").val(node_2);
			$("#node_x").val(node_x);
			$("#node_y").val(node_y);
			$("#node_name").val(node_name);
			$("#node_type").val(node_type);
			$("#node_code").val(node_code);
			$("#node_order").val(node_order);
			$("#node_excType").val(node_excType);
			$("#node_url").val(node_url);
			$("#node_dataId").val(node_dataId);

		}
	}
	var moveBeforeX,moveBeforeY,moveAfterX,moveAfterY;
	node_1.addEventListener('mousedrag', function(event) {
		moveDrag=true;
	});
	node_1.addEventListener('mousedown', function(event) {
		moveBeforeX = event.target.x;
		moveBeforeY = event.target.y;
	});

	node_1.addEventListener('mouseup', function(event) {
		handler(event);
		type=event.target.type;
		$(".look").attr("type",type);
		node_2=event.target;
		$("#node_2").val(node_2);
		moveAfterX = event.target.x;
		moveAfterY = event.target.y;
		if(event.target.id != undefined && ( moveBeforeX != moveAfterX || moveBeforeY !=moveAfterY)){
			updatePropertyForMove(node_2);
		}
	});
	stage.click(function(event) {
		if(event.button == 0) { // 右键
			// 关闭弹出菜单（div）
			$(".bigCircle").hide();
		}
	});
	return node_1;
}
//连线
var l;//新的连线
var beginNode = null;
debugger
var tempNodeA = new JTopo.Node('tempA');
tempNodeA.setSize(1, 1);
var tempNodeZ = new JTopo.Node('tempZ');
tempNodeZ.setSize(1, 1);
var link = new JTopo.Link(tempNodeA, tempNodeZ);
link = writeLink(link)




// 折线
function newFoldLink(nodeA, nodeZ, text){
	var link = new JTopo.FoldLink(nodeA, nodeZ, text);
	link.direction = 'horizontal';
	link.arrowsRadius = '10';
	link.bundleOffset = '60'; // 折线拐角处的长度
	link.bundleGap = '20'; // 线条之间的间隔
	link.textOffsetY = '3'; // 文本偏移量（向下3个像素）
	link.lineWidth='0.5';
	link.fontColor='0,11,0';
	link.strokeColor='87,87,87';
	link.dashedPattern = '5';
	scene.add(link);
	link.addEventListener("click", function(event){
		selink=link;
		var nodeA_x=link.nodeA.x;
		var nodeZ_x=link.nodeZ.x;
		var nodeA_y=link.nodeA.y;
		var nodeZ_y=link.nodeZ.y;
		var imgX=nodeA_x+(nodeZ_x-nodeA_x)/2;
		var imgY=nodeA_y+(nodeZ_y-nodeA_y)/2;
		$("#lineDel").css({
			display:"block",
			left:imgX+sceneX,
			top:imgY+sceneY
		});
		removeId=link.nodeZ.id;
		lastId=link.nodeA.id;
		$("#lineDel").unbind('click').on("click",function(){
			var nodeId=selink.nodeZ.id;
			var nodeZ_code=selink.nodeZ.node_code;
			if(!nodeId){
				$("#lineDel").hide();
				return;
			}
			var param={'currentNodeId':removeId,'preNodeId':lastId};
			Comm.ajaxPost('decision_flow/removeLink',JSON.stringify(param),function(result){
				selink.nodeA.next_node.removeByValue(nodeZ_code);
				layer.msg("删除成功！");
				scene.remove(link);
				scene.remove(selink);
				$("#lineDel").hide();
				beginNode=null;
			},"application/json")
		})
	});
	return link;
}


scene.mouseup(function(e) {
	debugger
	if(e.target==null||e.target instanceof JTopo.Node){
		$("#lineDel").hide();
	}
	if(e.button == 2) {
		scene.remove(link);
		beginNode=null;
		return;
	}
	if(e.target != null && e.target instanceof JTopo.Node) {//如果点击区域非空且点击的 为jtopo节点
		if(beginNode == null) {//开始节点为空
			beginNode = e.target;
			if(moveDrag==false){
				scene.add(link);
			}else{
				moveDrag=false;
				beginNode=null;
				return;
			}
			tempNodeA.setLocation(e.x, e.y);
			tempNodeZ.setLocation(e.x, e.y);
		} else if(beginNode !== e.target) {//如果点击的不是开始节点
			var endNode = e.target;
			var links=true;
			var fors=false;
			var beginNodeCode=beginNode.node_code;
			var endNodeCode=endNode.node_code;
			// if(endNode.inLinks!=null&&endNode.inLinks.length>0){
			// 	scene.remove(link);
			// 	beginNode=null;
			// }
			// else{
			if(false){//判断两节点之间是否已连线
				links=true;
				var beginNodeLen=beginNode.next_node.length;
				var endNodeLen=endNode.next_node.length;
				for(var i=0;i<beginNodeLen;i++){
					for(var j=0;j<endNodeLen;j++){
						var beginId=beginNode.next_node[i];//开始节点的下一结点
						var endId=endNode.next_node[j];//结束节点的下一结点
						if(beginId==endNodeCode||endId==beginNodeCode){//如果开始节点的下一结点等于结束节点或者结束节点的下一节点等于开始节点
							var ls=link;
							scene.remove(ls);
							beginNodeLen=i;
							endNodeLen=j;
							links=false;
							beginNode=null;
							break;
						}

					}
					fors=true;
				}
				if(links&&fors){
					if(beginNode.excType!=3){
						var l = new JTopo.Link(beginNode, endNode);
						l = writeLink(l)
						handleSanBoxAndSanGroup(beginNode,endNode,l,link)
						beginNode=null;
					}else if(beginNode.excType==3){
						var l = newFoldLink(beginNode,endNode);
						handleSanBoxAndSanGroup(beginNode,endNode,l,link);
						beginNode = null;
					}
				}
			}else {//开始节点的下一结点为空
				if(beginNode.excType!=3) {
					if(endNode.outLinks==null){
						l = new JTopo.Link(beginNode, endNode);
						l = writeLink(l);
						handleSanBoxAndSanGroup(beginNode, endNode, l, link);
						beginNode = null;
					}else if(endNode.outLinks.length<=0||(endNode.outLinks.length>0&&endNode.inLinks.length<=0)){
						l = new JTopo.Link(beginNode, endNode);
						l = writeLink(l);
						handleSanBoxAndSanGroup(beginNode, endNode, l, link);
						beginNode = null;
					}else if(endNode.excType==3){
						l = new JTopo.Link(beginNode, endNode);
						l = writeLink(l);
						handleSanBoxAndSanGroup(beginNode, endNode, l, link);
						beginNode = null;
					}
				}else{
					var l = newFoldLink(beginNode,endNode);
					handleSanBoxAndSanGroup(beginNode,endNode,l,link);
					beginNode = null;
				}
			}
			// }
		} else {
			beginNode = null;
		}
	} else {
		scene.remove(link);
		beginNode=null;
	}
});
scene.mousedown(function(e) {
	if(e.target == null || e.target === beginNode || e.target === link) {
		scene.remove(link);
		beginNode=null;
	}
});
scene.mousemove(function(e) {
	tempNodeZ.setLocation(e.x, e.y);
});
//节点重命名
var oldName;
var textfield = $("#jtopo_textfield");
var renameNode;
scene.dbclick(function(event){
	if(event.target == null) return;
	var e = event.target;
	if(e.text == '开始' && e.node_code == 'ND_START' && e.node_order == 1||e.elementType=='link'||e.id ==undefined){
		return;
	}
	renameNode=event.target;
	oldName=e.text;
	textfield.css({
		top: event.pageY,
		left: event.pageX - e.width / 2
	}).show()
	textfield.val(e.text).focus().select();
	e.text = " ";
	textfield[0].JTopoNode = e;
});

$("#jtopo_textfield").blur(function() {
	if(textfield[0].JTopoNode.text.length==0){
		oldName=textfield.hide().val();
		nodeName=oldName;
		var nodeId=renameNode.id;

	}else{
		textfield[0].JTopoNode.text = textfield.hide().val();
		var nodeName=textfield[0].JTopoNode.text;
		var nodeId=renameNode.id;
	}

	//保存重命名节点
	if(nodeId==0){
		return;
	}else{
		var param={nodeId:nodeId,nodeName:nodeName};
		Comm.ajaxPost("decision_flow/renameNode",JSON.stringify(param),function(data){
			layer.msg("更新成功！");
			choseNodes(1);
		},"application/json")
	}




});

//画布的位移操作
scene.addEventListener("mousemove", function(event){
	sceneX=scene.translateX;
	sceneY=scene.translateY;
});
function updateNodeForLink(beginNode, endNode){
	var _param = new Object();
	_param.nodeJson=[];
	var nodeObj={};
	if(beginNode.id != 0 ){
		if (beginNode.next_node!=null) {
			beginNode.next_node=beginNode.next_node.unique3();
		}
		_param.nodeId_1 = beginNode.id;
		_param.nextNodes_1 = beginNode.next_node.toString();
		_param.nodeId_2 = endNode.id;
		_param.nextNodes_2 = endNode.next_node.toString();
		Comm.ajaxPost("decision_flow/updateProperty",JSON.stringify(_param),function(data){
			return;
		},"application/json")

	}
}
function updatePropertyForMove(node){
	var param = new Object();
	param.nodeX = node.x;
	param.nodeY = node.y;
	param.nodeId = node.id;
	Comm.ajaxPost("decision_flow/updatePropertyForMove",JSON.stringify(param),function(result){
		return;
	},"application/json")
}
function writeLink(l){
	debugger
	l.arrowsRadius = 10;
	l.strokeColor='87,87,87';
	l.dashedPattern = 2;
	l.lineWidth='0.5';
	l.fontColor='0,0,0';
	l.addEventListener("click", function(event){
		selink=l;
		var nodeA_x=l.nodeA.x;
		var nodeZ_x=l.nodeZ.x;
		var nodeA_y=l.nodeA.y;
		var nodeZ_y=l.nodeZ.y;
		var imgX=nodeA_x+(nodeZ_x-nodeA_x)/2;
		var imgY=nodeA_y+(nodeZ_y-nodeA_y)/2;
		$("#lineDel").css({
			display:"block",
			left:imgX+sceneX,
			top:imgY+sceneY
		});
		removeId=l.nodeZ.id;
		lastId=l.nodeA.id;
		$("#lineDel").unbind('click').on("click",function(){
			var nodeId=selink.nodeZ.id;
			var nodeZ_code=selink.nodeZ.node_code;
			if(!nodeId){
				$("#lineDel").hide();
				return;
			}
			var param={'currentNodeId':removeId,'preNodeId':lastId};
			Comm.ajaxPost('decision_flow/removeLink',JSON.stringify(param),function(result){
				selink.nodeA.next_node.removeByValue(nodeZ_code);
				layer.msg("删除成功！");
				scene.remove(l);
				scene.remove(selink);
				$("#lineDel").hide();
				beginNode=null;
			},"application/json")
		})
	});
	l.fontColor='0,0,0';
	return l;
}
function handleSanBoxAndSanGroup(beginNode,endNode,l,link){
	if(beginNode.id!=0&&endNode.id!=undefined&&endNode.id != null){//如果开始节点与结束节点都已经保存
		if(endNode.text=='开始'){//如果结束节点连的是开始则取消本次连线
			scene.remove(l);
		}else if(beginNode.text=='开始'){
			if(beginNode.next_node.length>0){
				for(var i=0;i<beginNode.next_node.length;i++){
					if(beginNode.next_node[i]==endNode.node_code||endNode.inLinks!=null){
						scene.remove(l);
					}else{
						scene.add(l);
						beginNode.next_node.push(endNode.node_code);
						updateNodeForLink(beginNode,endNode);
						i=beginNode.next_node.length;
					}
				}
			}else{//连线开始节点的next_node为空
				scene.remove(link);
				scene.add(l);
				beginNode.next_node.push(endNode.node_code);
				updateNodeForLink(beginNode,endNode);
			}
		}else{//结束节点连的不是开始
			groups(beginNode, endNode,l);
		}
	}else{
		layer.msg("请先保存节点！",{time:2000});
		scene.remove(link);
		scene.remove(l);
	}
	beginNode = null;
}
//分组弹出  连线选择分组
function groups(beginNode, endNode,l) { //客户分群
	var groupJson_1;
	if(beginNode.excType!=3){
		var processNode={"nodeId":beginNode.id};
		Comm.ajaxPost("decision_flow/getNode",JSON.stringify(processNode),function(result){
			var data=result.data;
			if(data!=null){
				groupJson_1 = JSON.parse(data.nodeJson);
				var newGroup = "";
				for (var i = 0; i < groupJson_1.conditions.length; i++) {
					newGroup +='<div class="c-sanbox-lie-input">';
					if(groupJson_1.conditions[i].nextNode !=undefined && groupJson_1.conditions[i].nextNode !='' ){
						newGroup +='<input style="float: left;margin: 0px 5px 0 0;" type="radio" name="groups" disabled="disabled">';
					}else{
						newGroup +='<input style="float: left;margin: 0px 5px 0 0;" type="radio" name="groups" >';
					}
					var text=groupJson_1.conditions[i].group_name.replace('分组','');
					newGroup +=	'<div class="enter-into-proportion">分组<b class="datas">'+text+'</b> <span style="margin-left:3px;"></span></div>'+
						'</div>';
				}
				$(".c-group-content").empty().append(newGroup);
			}
		},"application/json");
		if(endNode.id != 0&&endNode.id !=undefined) {
			layer.open({
				type : 1,
				title : '选择分组',
				shadeClose : false,
				area : [ '250px', '270px' ],
				content : $(".c-swarms"),
				btn : [ '保存', '取消' ],
				yes:function(index, layero){
					//点击确定操作(选择分组)
					var sel_group = $("input[name=groups]:checked").next().find("b").html();
					l.text = '分组' + sel_group;
					nextNode = endNode.node_code;
					beginNode.next_node.push(nextNode);
					var conditionsLen = groupJson_1.conditions.length;
					for(var i = 0; i < conditionsLen; i++) {
						if(groupJson_1.conditions[i].group_name == l.text) {
							groupJson_1.conditions[i].nextNode = nextNode;
						}
					}
					var _param = new Object();
					_param.nodeId = beginNode.id;
					_param.nodeX = beginNode.x;
					_param.nodeY = beginNode.y;
					_param.nodeName = beginNode.text;
					_param.nextNodes =beginNode.next_node.join(",");
					_param.nodeCode = beginNode.node_code;
					_param.nodeType = beginNode.type;
					_param.nodeOrder = beginNode.node_order;
					_param.nodeJson = JSON.stringify(groupJson_1);
					Comm.ajaxPost("decision_flow/update",JSON.stringify(_param),function(result){
						if(result.code == 0 ) {
							layer.closeAll();
							layer.msg(result.msg,{time:2000});
							l.nodeA.node_json[0] = groupJson;
							$("input[name=groups]:checked").attr("disabled", "true");
						}
					},"application/json");
					scene.remove(link);
					scene.add(l);
					var outlinks=beginNode.outLinks.length;
					var conditions_group=$(".c-swarms").find("input").length;
					if(conditions_group!=outlinks){
						groupLine=false;
					}else{
						groupLine=true;
					}
					beginNode = null;
				},
				cancel:function(){
					scene.remove(l);
					beginNode = null;
				}
			});
		}
	}else {
		var processNode={"nodeId":beginNode.id};
		Comm.ajaxPost("decision_flow/getNode",JSON.stringify(processNode),function(result){
			var data=result.data;
			if(data!=null){
				groupJson_1 = JSON.parse(data.nodeJson);
				var newGroup = "";
				for (var i = 0; i < groupJson_1.conditions.length; i++) {
					newGroup +='<div class="c-sanbox-lie-input">';
					if(groupJson_1.conditions[i].nextNode !=undefined && groupJson_1.conditions[i].nextNode !='' ){
						newGroup +='<input style="float: left;margin: 0px 5px 0 0;" type="radio" name="jsRadio" disabled="disabled">';
					}else{
						newGroup +='<input style="float: left;margin: 0px 5px 0 0;" type="radio" name="jsRadio" >';
					}
					var text=groupJson_1.conditions[i].group_name
					newGroup +=	'<div class="enter-into-proportion">'+'<b class="datas">'+text+'</b> <span style="margin-left:3px;"></span></div>'+
						'</div>';
				}
				$(".c-group-content").empty().append(newGroup);
			}
		},"application/json");
		if(endNode.id != 0&&endNode.id !=undefined) {
			layer.open({
				type : 1,
				title : '选择分组',
				shadeClose : false,
				area : [ '250px', '270px' ],
				content : $(".c-swarms"),
				btn : [ '保存', '取消' ],
				yes:function(index, layero){
					//点击确定操作(选择分组)
					var sel_group = $("input[name=jsRadio]:checked").next().find("b").html();
					l.text = sel_group;
					nextNode = endNode.node_code;
					beginNode.next_node.push(nextNode);
					var conditionsLen = groupJson_1.conditions.length;
					for(var i = 0; i < conditionsLen; i++) {
						if(groupJson_1.conditions[i].group_name == l.text) {
							groupJson_1.conditions[i].nextNode = nextNode;
						}
					}
					var _param = new Object();
					_param.nodeId = beginNode.id;
					_param.nodeX = beginNode.x;
					_param.nodeY = beginNode.y;
					_param.nodeName = beginNode.text;
					_param.nextNodes =beginNode.next_node.join(",");
					_param.nodeCode = beginNode.node_code;
					_param.nodeType = beginNode.type;
					_param.nodeOrder = beginNode.node_order;
					_param.nodeJson = JSON.stringify(groupJson_1);
					Comm.ajaxPost("decision_flow/update",JSON.stringify(_param),function(result){
						if(result.code == 0 ) {
							layer.closeAll();
							layer.msg(result.msg,{time:2000});
							l.nodeA.node_json[0] = groupJson;
							$("input[name=groups]:checked").attr("disabled", "true");
						}
					},"application/json");
					scene.remove(link);
					scene.add(l);
					var outlinks=beginNode.outLinks.length;
					var conditions_group=$(".c-swarms").find("input").length;
					if(conditions_group!=outlinks){
						groupLine=false;
					}else{
						groupLine=true;
					}
					beginNode = null;
				},
				cancel:function(){
					scene.remove(l);
					beginNode = null;
				}
			});
		}
	}

}
function decisionOption(beginNode, endNode) { //决策选项
	if(endNode.id == 0) {
		var initEngineVersionId = $("input[name=initEngineVersionId]").val();
		var _param = new Object();
		_param.initEngineVersionId = initEngineVersionId;
		_param.nodeX = endNode.x;
		_param.nodeY = endNode.y;
		_param.nodeName = endNode.text;
		_param.nodeCode = endNode.node_code;
		_param.nodeType = endNode.type;
		_param.nodeOrder = endNode.node_order;
		_param.params = '{"dataId":"'+ endNode.dataId+'","url":"'+endNode.url+'","type":"'+endNode.type+'" }';
		Comm.ajaxPost("decision_flow/save",JSON.stringify(_param),function(result){
			var data=result.data;
			if(data.result == '1') {
				endNode.id = data.nodeId;
				endNode.node_code = data.nodeCode;
				dialog.hide();
				$(".bigCircle").hide();
			}
		},"application/json");

		beginNode.next_node.push(endNode.node_code);
		updateNodeForLink(beginNode, endNode);
	}
}
function sanBox(beginNode, endNode,l) {//沙盒比例
	var sBNum = 0;
	$(".sanbox-popups").css("display", "block");
	var newEle ="";
	Comm.ajaxPost("decision_flow/getNode",{"nodeId":beginNode.id},function(result){
		var data=result.data;
		if(data!=null){
			var json=data.nodeJson;
			if (json!=null && json!="") {
				sanBoxJsonObj= JSON.parse(data.nodeJson);
				for(var i=0;i<sanBoxJsonObj.length;i++){
					newEle +='<div class="c-sanbox-lie-input">';
					if(sanBoxJsonObj[i].nextNode!=undefined && sanBoxJsonObj[i].nextNode !=''){
						newEle +='<input style="float: left;margin: 0px 5px 0 0;" type="checkbox" name="radios" disabled="disabled">';
						sBNum++;
					}else{
						newEle +='<input style="float: left;margin: 0px 5px 0 0;" type="checkbox" name="radios" >';
					}
					newEle +='<div class="enter-into-proportion">沙盒比例<b>'+sanBoxJsonObj[i].sandbox+':</b><span style="margin-left:7px;">'+sanBoxJsonObj[i].proportion+'%</span></div>'+
						'</div>';
				}
			}
		}
	});
	$(".c-sanboxs").html('').append(newEle);
	layer.open({
		type : 1,
		title : '沙盘比例',
		maxmin : true,
		shadeClose : false,
		area : [ '300px', '270px' ],
		content : $('.sanbox-popups'),
		btn : [ '保存', '取消' ]
	});
	$(".l_sanboxSure").unbind("click").on("click", function() {
		var conditions_san=$(this).parents(".dialog").find(".c-sanbox-lie-input").length;
		if(sBNum <= sanBoxJsonObj.length){
			var lineText = $("input[name=radios]:checked").next().find("span").text();
			l.text = lineText;
			sanbox = $("input[name=radios]:checked").next().find("b").html();
			sanbox =sanbox.replace(":", "");
			l_lineText = (l.text.replace(/%/, ""));
			nextNode = endNode.node_code;
			$(".sanbox-popups").hide();
			$("input[name=radios]:checked").parents(".c-sanbox-lie-input").remove();
			endNode.l_rate = l_lineText;
			scene.remove(link);
			scene.add(l);
			beginNode.next_node.push(endNode.node_code);
			updateNodeForLink(beginNode, endNode);
			var outlinks=beginNode.outLinks.length;
			if(conditions_san!=outlinks){
				sanLine=false;
			}else{
				sanLine=true;
			}
			beginNode = null;

		}
	});
	$(".l_sanboxClose").unbind("click").on("click", function() {
		$(".sanbox-popups").hide();
		scene.remove(l);
		beginNode = null;
	});
}
Array.prototype.unique3 = function(){
	var res = [];
	var json = {};
	for(var i = 0; i < this.length; i++){
		if(!json[this[i]]){
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
};
var data_id=1;
var removeId;
var lastId;
//添加节点按钮
function addNodes(){
	$('#className').val("");
	$('#pageUrl').val("");
	$('#text').val("");
	$('#icon').val("");
	layer.open({
		type : 1,
		title : "添加节点",
		area : [ '300px', '260px' ],
		content : $('#addNodes'),
		btn : [ '保存', '取消' ],
		success: function () {
		},
		yes : function(index, layero) {
			if ($('#orger_name').val() == "") {
				layer.msg("名称不能为空",{time:2000});
				return;
			}
			if ($('#selectType').val() == "0" && $('#className').val() == "") {
					layer.msg("执行类不能为空",{time:2000});
					return;

			}else if($('#selectType').val() == "1" && $('#pageUrl').val() == ""){
					layer.msg("页面路径不能为空",{time:2000});
					return;
			}
			if($('#text').val() == ""){
				layer.msg("节点名称不能为空",{time:2000});
				return;
			}
			debugger
			var executeClass=$('#className').val();
			var urlInput=$('#pageUrl').val();
			var handUrl = $('#handUrl').val();
			var text=$('#text').val();
			var url="/resources/images/decision/createDiy.png";//$('#icon').val();
			var type=$("#selectType option:selected").val();//节点类型 自动节点或人工节点
			var engineId = $('#engineId').val();
			var nodeType=getNodeType();
			var processId=process_id;
			var process={
				nodeType:nodeType,
				excType:type,
				className:executeClass,
				pageUrl:urlInput,
				handUrl:handUrl,
				engineId:engineId,
				nodeName:text,
				nodeUrl:"/resources/images/decision/createDiy.png",
				processId:processId
			};
			Comm.ajaxPost(
				'decision_flow/saveNode',JSON.stringify(process),
				function(data){
					layer.closeAll();
					layer.msg(data.msg,{time:2000},function () {
						choseNodes(nodeType);
					});
				},"application/json"
			);
		}
	});
}
//切换选择类型
$("#selectType").on("change",function () {
	var valType=$(this).find("option:selected").val();
	if(valType==0){//自动节点
		$("#urlInput").addClass("labelStyle_hide");
		$("#executeClass").removeClass("labelStyle_hide");
		$("#engineIds").removeClass("labelStyle_hide");
	}else{//人工节点
		$("#executeClass").addClass("labelStyle_hide");
		$("#engineIds").addClass("labelStyle_hide");
		$("#urlInput").removeClass("labelStyle_hide");
		$("#urlDel").removeClass("labelStyle_hide");
	}
});
//选择节点按钮
var jsTreeCheckBox;
function choseNodes(arg) {
	var id = process_id;
	var param = {};
	param.id = id;
	$("#l_decisions").empty();
	Comm.ajaxPost('decision_flow/getNodeByProcessIds',JSON.stringify(param),function (result) {
		var lists = result.data;
		var list = new Array();
		for(var i=0;i<lists.length;i++) {
			if(lists[i].nodeType==arg&&lists[i].nodeCode != "ND_START"){
				list.push(lists[i]);
			}else {
				if (lists[i].nodeCode != "ND_START") {//&&lists[i].nodeJson!=null
					list.push(lists[i]);
				}
			}
		}
		var nodelist="[";
		htmllist = '<li dataId="0" onclick="edit(0);"> <img src="/resources/images/decision/决策选项.png"/>'
			+ '<div class="fs2">'
			+ '<span class="icon-blackName">'
			+ '<span class="path1"></span><span class="path2"></span><span class="path3"></span>'
			+ '</span> </div> <span class="l_black_list left_Name">'
			+ "决策节点" + "</span></li>"
		$("#l_decisions").append(htmllist);
		for(var i=0;i<list.length;i++) {
			// if (list[i].nodeCode != "ND_START"){
			var par = list[i].params;
			var url;
			if (par) {
				url = JSON.parse(list[i].params).url;
			} else {
				url = list[i].nodeUrl;
			}

			if (i == list.length - 1) {
				nodelist += '{"type":"' + list[i].nodeType +'","excType":"' + list[i].excType + '","text":"' + list
						[i].nodeName + '","url":"' + url + '"}]';
			} else {
				nodelist += '{"type":"' + list[i].nodeType +'","excType":"' + list[i].excType + '","text":"' + list
						[i].nodeName + '","url":"' + url + '"},';
			}


			htmllist = '<li onclick="edit('+list[i].nodeId+');" dataId=' +  (i+1) + '> <img src=' + url + '/>'
				+ '<div class="fs2">'
				+ '<span class="icon-blackName">'
				+ '<span class="path1"></span><span class="path2"></span><span class="path3"></span>'
				+ '</span> </div> <span class="l_black_list left_Name">'
				+ list[i].nodeName + "</span> </li>"
			$("#l_decisions").append(htmllist);
			// }
		}
		var m;
		if(arg!=null&&arg!=undefined){
			nodes = [{
				node: {
					dataId: "0",
					text: "决策节点",
					type: 100,
					excType:3,
					url: _ctx+ "/resources/images/decision/决策选项.png"
				}
			}];
		}

		var n=JSON.stringify(nodes);
		n=n.substring(0,n.length-1);
		if(nodelist=='['){
			nodelist+="]";
		}
		nodelist=JSON.parse(nodelist);

		for(var j=0;j<nodelist.length;j++){
			if(n=="["&&j==0){
				m='{"node":{"dataId":"'+(j+1)+'",'+JSON.stringify
					(nodelist[j]).substring(1,JSON.stringify(nodelist[j]).length)+"}";

			}else{
				m=',{"node":{"dataId":"'+(j+1)+'",'+JSON.stringify
					(nodelist[j]).substring(1,JSON.stringify(nodelist[j]).length)+"}";
			}
			n+=m;
			if(j==nodelist.length-1){
				n+="]";
			}
		}
		if (n=='['||n.length==105){
			n+=']';
		}
		nodes=JSON.parse(n);
		data_id+=nodelist.length;
		$("#chosesNodes").val("");
	},"application/json")
}

function whichButton(e) {
	debugger
	var btnNum = e.button;
	if(btnNum==2){
		alert("右键");
	}
}









