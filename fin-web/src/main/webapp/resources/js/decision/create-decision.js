            var _ctx=$("#getCtx").val();
			var node_order;//节点编号（唯一）
            var node_code;//节点唯一标识
			var node_x;//节点x坐标
			var node_y;//节点y坐标
			var node_name;//节点名称
			var node_type;//节点类型
			var node_url;//节点图片路径
			var node_dataId;//节点类型标识编号
			var node_2;//右键点击节点后的节点变量
			var c=0;
			var id;
			var sceneX=0,sceneY=0;//画布位移（初始为0）
			var order_count = 0;//节点编号变量
			var canvas = document.getElementById('canvas');
			var stage = new JTopo.Stage(canvas);
			var sanboxLine=[];
			var groupJson=new Object();
			var initEngineVersionId =$("input[name=initEngineVersionId]").val();
			var scoreCardId;
			var sanBoxJsonObj=[];
			var sanLine;//沙盒验证变量
			var groupLine;//分群验证变量
			//点击放大
			$("#stageEnlarge").on("click",function(){
				stage.zoomOut(0.85);
				console.log(scene.selectedElements);
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
					text: "黑名单",
					type: 5,
					url: _ctx+ "/resources/images/decision/createBlackName.png"
				}
			}, {
				node: {
					dataId: "1",
					text: "白名单",
					type: 6,
					url:  _ctx+"/resources/images/decision/createWhiteName.png"
				}
			}, {
				node: {
					dataId: "2",
					text: "沙盒比例",
					type: 7,
					url: _ctx+"/resources/images/decision/createRiverRate.png"
				}
			}, {
				node: {
					dataId: "3",
					text: "客户分群",
					type: 3,
					url: _ctx+"/resources/images/decision/createUserGroup.png"
				}
			}, {
				node: {
					dataId: "4",
					text: "规则集",
					type: 2,
					url: _ctx+"/resources/images/decision/createRuleGroup.png"
				}
			}, {
				node: {
					dataId: "5",
					text: "评分卡",
					type: 4,
					url: _ctx+"/resources/images/decision/createScoreLevel.png"
				}
			}, {
				node: {
					dataId: "6",
					text: "信用评级",
					type: 8,
					url: _ctx+"/resources/images/decision/createCrediteLevel.png"
				}
			}, {
				node: {
					dataId: "7",
					text: "决策选项",
					type: 9,
					url: _ctx+"/resources/images/decision/createDcisionOption.png"
				}
			}, {
				node: {
					dataId: "8",
					text: "额度计算",
					type: 10,
					url: _ctx+"/resources/images/decision/createLimitCompute.png"
				}
			}, {
				node: {
					dataId: "9",
					text: "报表分析",
					type: 11,
					url: _ctx+"/resources/images/decision/createFormAnalyse.png"
				}
			}, {
				node: {
					dataId: "10",
					text: "自定义按钮",
					type: 12,
					url: _ctx+"/resources/images/decision/createDiy.png"
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
				
			$(document).on("mousedown", "#l_decisions li", function(e) {
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
					createNode(dataId, text, url, x, y,type,1);
					$(".l_click").removeClass("l_click");
					drag = false;
				} else {
					drag = false;
					$(".testImg").remove();
				}

			});
			//定义一个方法创建相应节点
			function createNode(dataId, text, url, x, y,type) {
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
				if(order_count == 1){
					node_1.node_code = "ND_START";
				}else{
					node_1.node_code = "ND_"+order_count;
				}
				node_1.node_order = order_count;
				node_1.node_json=new Array();
				node_1.next_node =new Array();
				scene.add(node_1);
				node_1.id=0;
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
					dataId=event.target.dataId;
					$(".look").attr("dataId",dataId);
					node_2=event.target;
					$("#node_2").val(node_2);
					moveAfterX = event.target.x;
					moveAfterY = event.target.y;
				    if(event.target.id != 0 && ( moveBeforeX != moveAfterX || moveBeforeY !=moveAfterY)){
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
			var tempNodeA = new JTopo.Node('tempA');
			tempNodeA.setSize(1, 1);
			var tempNodeZ = new JTopo.Node('tempZ');
			tempNodeZ.setSize(1, 1);
			var link = new JTopo.Link(tempNodeA, tempNodeZ);
			link = writeLink(link)
			
			scene.mouseup(function(e) {
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
						if(endNode.inLinks!=null&&endNode.inLinks.length>0){
							scene.remove(link);
							beginNode=null;
						}
						else{
						   if(beginNode.next_node.length>0){//判断两节点之间是否已连线
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
								var l = new JTopo.Link(beginNode, endNode);
								l = writeLink(l)
								handleSanBoxAndSanGroup(beginNode,endNode,l,link)
								beginNode=null;
							}
						}else {//开始节点的下一结点为空
							l = new JTopo.Link(beginNode, endNode);
							l= writeLink(l);
							handleSanBoxAndSanGroup(beginNode,endNode,l,link);
							beginNode = null;
						}
					}
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
				if(e.text == '开始' && e.node_code == 'ND_START' && e.node_order == 1||e.elementType=='link'){
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
					//沙盒比例
					if(beginNode.dataId==2){
						if (sanbox!=null) sanbox=sanbox.replace(":","");
						nodeObj.sandbox=sanbox;
						nodeObj.proportion=l_lineText;
						nodeObj.nextNode=nextNode;
						if (typeof(nodeObj.sandbox) =="undefined") {
							;
						}else {
							 for (var i = 0; i < sanBoxJsonObj.length; i++) {
								 if(sanBoxJsonObj[i].sandbox == sanbox){
									 sanBoxJsonObj[i].nextNode = nextNode;
								 }
							 }
						    _param.node_json_1 =JSON.stringify(sanBoxJsonObj);
						}
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
						$("#lineDel").hide();
                        // var nodeId=selink.nodeZ.id;
                        // var nodeZ_code=selink.nodeZ.node_code;
                        // var param={'currentNodeId':removeId,'preNodeId':lastId};
                        // Comm.ajaxPost('decision_flow/removeLink',JSON.stringify(param),function(result){
                        //     selink.nodeA.next_node.removeByValue(nodeZ_code);
                        //     layer.msg("删除成功！");
                        //     scene.remove(l);
                        //     scene.remove(selink);
                        //     $("#lineDel").hide();
                        //     beginNode=null;
                        // },"application/json")
					})
				});
				l.fontColor='0,0,0';
				return l;
		 }
           
		 
       	function handleSanBoxAndSanGroup(beginNode,endNode,l,link){
    	    
		   if(beginNode.id!=0&&endNode.id!=0){//如果开始节点与结束节点都已经保存
				if(endNode.text=='开始'){//如果结束节点连的是开始则取消本次连线
					scene.remove(l);
				} else if(beginNode.dataId==2){//沙盒比例
					sanBox(beginNode,endNode,l); 
				} else if(beginNode.dataId==3){//客户分群
						groups(beginNode, endNode,l);
				} else{//结束节点连的不是开始
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
				}	
		   }else if(endNode.id == 0 && endNode.dataId==7){//连接节点为决策选项
		  	   decisionOption(beginNode, endNode);
		  	   scene.remove(link);
		  	   scene.add(l);
		   }else{
		    	 layer.msg("请先保存节点！",{time:1000});
		    	 scene.remove(link);
		    	 scene.remove(l);
		   }  
		   beginNode = null;
     }
		function groups(beginNode, endNode,l) { //客户分群
			var groupJson_1;
			var param={"nodeId":beginNode.id};
            Comm.ajaxPost("decision_flow/getNode",param,function(result){
                var data=result.data;
                if(data.engineNode!=null){
                    groupJson_1 = JSON.parse(data.engineNode.nodeJson);
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
            });
			if(endNode.id != 0) {
				$(".c-swarms").css("display", "block");
				autoCenter($(".c-swarms"));
			}
			
			//点击确定操作(选择分组)
			$(".l_selGroupSure").unbind("click").on("click", function() {
				var versionId=$("input[name=initEngineVersionId]").val();
				var sel_group = $("input[name=groups]:checked").next().find("b").html();
				l.text = '分组' + sel_group;
				nextNode = endNode.node_code;
				beginNode.next_node.push(nextNode)
				var conditionsLen = groupJson_1.conditions.length;
				for(var i = 0; i < conditionsLen; i++) {
					if(groupJson_1.conditions[i].group_name == l.text) {
						groupJson_1.conditions[i].nextNode = nextNode;
					}
				}
				console.log(groupJson_1);
				var _param = new Object();
				var html = $(".c_content").html();
				var groupSelHtml=$(".c-group-content").html();
				_param.initEngineVersionId = versionId;
				_param.id = beginNode.id;
				_param.nodeX = beginNode.x;
				_param.nodeY = beginNode.y;
				_param.nodeName = beginNode.text;
				_param.nextNodes =beginNode.next_node.join(",");
				_param.nodeCode = beginNode.node_code;
				_param.nodeType = beginNode.type;
				_param.nodeOrder = beginNode.node_order;
				_param.nodeJson = JSON.stringify(groupJson_1);
				var object = {
					dataId: node_dataId,
					url: node_url,
					type: node_type,
					html: html,
					groupSelHtml:groupSelHtml
				};
				object = JSON.stringify(object);
                Comm.ajaxPost("decision_flow/update",object,function(result){
                    var data=result.data;
                    	if(data.result == '1') {
                    		layer.msg("更新成功!",{time:1000});
                    		$(".c-swarms").hide();
                    		l.nodeA.node_json[0] = groupJson;
                    		$("input[name=groups]:checked").attr("disabled", "true");
                        }
                },"application/json");

				scene.remove(link);
				scene.add(l);
				var outlinks=beginNode.outLinks.length;
				var conditions_group=$(this).parents(".dialog").find("input").length;
				if(conditions_group!=outlinks){
					groupLine=false;
				}else{
					groupLine=true;
				}
				beginNode = null;
			});
			
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
					content : $('.sanbox-popup'),
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
			$("#getScene").val(scene);
			$("#groupJson").val(groupJson);
