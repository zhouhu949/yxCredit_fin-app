<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="c-policy-dialog dialog rules-div" style="display: none">
            <input type="hidden" name="rule-node-id" value="">
			<%--<div class="c-policy-head move_part" >--%>

			<%--</div>--%>
			<div class="c-policy-content">
				<%--<div id="myTab" class="policy-TabControl-head" role="tablist">--%>
				    <%--<div class="c-change-left">--%>
						<%--<img id="prev" src="${ctx}/resources/images/decision/left-arrow.jpg"/>--%>
					<%--</div>--%>
					<%--<div class="cats" id="cats"  style="width:442px; float:left;margin-left:3px;">--%>
					<%--</div>--%>
					<%--<div class="c-change-right">--%>
						<%--<img id="next" src="${ctx}/resources/images/decision/right-arrow.png"/>--%>
					<%--</div>--%>
					<%--<div class="c-change-select">--%>
					     <%--<input type="text" id="searchRuleKey" />--%>
					     <%--<img id="searchRuleBtn" src="${ctx}/resources/img/engine/search.png">--%>
					<%--</div>--%>
				<%--</div>--%>
			
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane active" id="bulletin">
						<div class=""></div>
					</div>
					<div class="" id="rule">
						<div class="c-policy-radio">
							<div  id="rules"  style="width:585px; float:left;">
							</div>
						</div>
						<div class="c-selected-preview"  id="preview-rule">
							<div class="c-policy-preview">
								<div class="c-policy-preview-span">
									<span>已选规则</span>
									<div class="preview-span" id ="preview">预览</div>
								</div>
									<div class="c-tier-one">
										<div class="c-reject-lfet">
											<div class="c-refuse-rule">拒绝规则</div>
											<div class="c-refuse-rule" id="c-refuse-rule">
												<div class="l_checkbox" style="margin:0px 3px 0 0;"></div>
												<span>串行</span>
											</div>
										</div>
										<div class="c-reject-right" id="refuse-rule">
										   <div class="c-policy-priority">
												<div class="left-priority" dataTitle="priority_0">优先级0</div>
												<div class="right-priority priority_0">	
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;display:block">
												<div class="left-priority" dataTitle="priority_1">优先级1</div>
												<div class="right-priority priority_1">
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_2">优先级2</div>
												<div class="right-priority priority_2">
												</div>
											</div>
										    <div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_3">优先级3</div>
												<div class="right-priority priority_3">
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_4">优先级4</div>
												<div class="right-priority priority_4">
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_5">优先级5</div>
												<div class="right-priority priority_5">
												</div>
											</div>
										   <div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_6">优先级6</div>
												<div class="right-priority priority_6">
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_7">优先级7</div>
												<div class="right-priority priority_7">
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_8">优先级8</div>
												<div class="right-priority priority_8">
												</div>
											</div>
											<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;">
												<div class="left-priority" dataTitle="priority_9">优先级9</div>
												<div class="right-priority priority_9">
												</div>
											</div>
										</div>
									</div>
									<div class="c-tier-two ">
										<div class="c-subtract-left">
											<div class="c-deduction">加减分规则</div>
											<div class="c-deduction">
												<span>阈值</span>
												<input id="threshold" value="" />
											</div>
										</div>
										<div class="c-subtract-right" id="addorSub-rule">											
										</div>
									</div>
								<!-- </div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<%--<div align="center" class="rule_sumbit">--%>
			    <%--<button class="button" id="rule-sumbit">确定</button>--%>
				<%--<button class="button  cancel-button" id="rule-close" >取消</button>--%>
			<%--</div>--%>

			
			<%--<form id="previewForm" method="post" target="newWin" action="${ctx}/decision_flow/previewRule">--%>
			   <%--<input type="hidden" name ="id" >--%>
			   <%--<input type = "hidden" name ="ruleJson">--%>
			<%--</form>--%>

            <div class="Manager_style add_user_info search_style">
                <input type="hidden" id="isShowRules">
                <ul class="search_content clearfix">
                    <li style="margin-left:1.5em">
                        <label class="lf">名称</label>
                        <label>
                            <input name="mobile"  type="text" class="text_add"/>
                        </label>
                    </li>
                    <li style="float:right">
                        <button id="btn_search_rules"  type="button" class="btn btn-primary queryBtn">查询</button>
                        <button id="btn_search_reset_rules"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                    </li>
                </ul>
                <table style="cursor:pointer;display: none" id="ruls_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th></th>
                            <th>选择</th>
                            <th>名称</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
</div>
	<script type="text/javascript" charset="utf-8">
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

				}
				param.status = 1;
				param.type = 2;
				param.engineId = ${engineId};
				paramFilter.param = param;

				page.firstIndex = data.start == null ? 0 : data.start;
				page.pageSize = data.length  == null ? 10 : data.length;
				paramFilter.page = page;

				return paramFilter;

			}
		};
	$("#cats").on("click",".c-policy-cat",function(){
		$("#cats").find(".c-policy-cat").removeClass("c-font-color");
		$(this).addClass("c-font-color");
	}); 
	$(".rules-div").on("click",".l_checkbox",function(){
		if($(this).hasClass("l_back")==false){
			$(this).addClass("l_back");
		}else{
			$(this).removeClass("l_back");
		}		
	});	
	
	$("#preview").click(function(){
		openWin();
	})
	
	function openWin(){
		   var param = getParam();
		   $("#previewForm input[name=id]").val(node_2.id);
		   $("#previewForm input[name=ruleJson]").val(JSON.stringify(param));
		   window.open("about:blank","newWin","");
		   $("#previewForm").submit();
	}
	
	var count = 1;
	$("#prev").unbind('click').on('click',function(e){
		  count--;
		  getTreeData(null)
	})

	 $("#next").unbind('click').on('click',function(e){
		if($("#cats").find(".c-policy-cat").length > 0){
			 count++;
			 getTreeData(null)
		}
	})

	$("#rule-close").unbind('click').on('click',function(e){
		dialog.hide();
		$(".bigCircle").hide();
		$("#cats").find(".c-policy-cat .selected-tag").removeClass('selected-tag');
	})
	
    $("#rule-back").unbind('click').on('click',function(e){
		dialog.hide();
		$(".bigCircle").hide();
		$("#cats").find(".c-policy-cat .selected-tag").removeClass('selected-tag');
	})

	//确认提交
	$("#rule-sumbit").unbind('click').on('click',function(e){
		var _param = getParam();	
	 	var _url;
	 	if(_param.deny_rules!=''  || _param.addOrSubRules!=''){
	 		if(node_2.id !=0 ){
		  		 _url ="${ctx}/decision_flow/update";
		  	}else{
		  		 _url ="${ctx}/decision_flow/save";
		  	}
		  	$.post(_url,_param,function(data){
		  		if(data.result == '1'){
		  			node_2.id = data.nodeId;		
		  		 }
		  	})
	 	}
		dialog.hide();
  		$(".bigCircle").hide();
		clearRule();
	 })
	 
	function getParam(){
		 var _param = new Object();
		 $("#cats").find(".c-policy-cat .selected-tag").removeClass('selected-tag');
	  	 var nodeId = node_2.id;
		 var initEngineVersionId =$("input[name=initEngineVersionId]").val();	
	 	_param.initEngineVersionId = initEngineVersionId;
	 	_param.id = nodeId;
	 	_param.nodeX = node_x;
	 	_param.nodeY = node_y;
	 	_param.nodeName = node_name;
	 	_param.nodeCode = node_code;
	 	_param.nodeType = node_type;
	 	_param.nodeOrder = node_order;
	 	_param.params ='{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'"}';
	 	_param.deny_rules = getDenyRulesJson();
	 	_param.addOrSubRules =  getAddOrSubJson();
	 	return  _param;
	}

	//拼接加减分规则json
	function getAddOrSubJson(){
		var threshold = $("#threshold").val();
		var slectedRuleIdsForAddRules = "";
		if($("#addorSub-rule").find('.l_back').length>0){
			var slectedRuleIdsForAddRules = '{"threshold":"'+threshold;
			slectedRuleIdsForAddRules +='","rules":[';
		 	$("#addorSub-rule").find('.l_back').each(function(index,element){
		  		slectedRuleIdsForAddRules +='{"id":'+$(element).attr("dataId")+',';
		  		slectedRuleIdsForAddRules +='"parentId":'+$(element).attr("dataParentId")+',';
		  		slectedRuleIdsForAddRules +='"priority":'+$(element).attr("dataPriority")+',';
		  		slectedRuleIdsForAddRules +='"code":"'+$(element).attr("dataCode")+'",';
		  		slectedRuleIdsForAddRules +='"name":"'+$(element).attr("dataTitle")+'"}'+",";
		  	})
		  	slectedRuleIdsForAddRules = slectedRuleIdsForAddRules.substring(0,slectedRuleIdsForAddRules.length-1);
		  	slectedRuleIdsForAddRules +="]}"
		}
		return slectedRuleIdsForAddRules;
	}
	
	
	//拼接拒绝规则Json
	function getDenyRulesJson(){
		var isSerial;
	  	if($("#c-refuse-rule").find('.l_back').length > 0) {
	  		isSerial = 1;
	  	}else{
	  		isSerial = 0;
	  	}
	  	var slectedRuleIdsForDenyRules = "";
	  	if($("#refuse-rule").find('.right-priority .l_back').length>0){
	  		slectedRuleIdsForDenyRules = '{"isSerial":'+isSerial;
	  		slectedRuleIdsForDenyRules +=',"rules":[';
	  		 $("#refuse-rule").find('.right-priority .l_back').each(function(index,element){
	   			 slectedRuleIdsForDenyRules +='{"id":'+$(element).attr("dataId")+',' 
	   			 slectedRuleIdsForDenyRules +='"parentId":'+$(element).attr("dataParentId")+',';
	   			 slectedRuleIdsForDenyRules +='"priority":'+$(element).attr("dataPriority")+',';
	   			 slectedRuleIdsForDenyRules +='"code":"'+$(element).attr("dataCode")+'",';
	   			 slectedRuleIdsForDenyRules +='"name":"'+$(element).attr("dataTitle")+'"}'+','; 
	         });
			slectedRuleIdsForDenyRules = slectedRuleIdsForDenyRules.substring(0,slectedRuleIdsForDenyRules.length-1);
			slectedRuleIdsForDenyRules +="]}"
	  	}
	  	return slectedRuleIdsForDenyRules;
	}
	 
	    
   function getTreeData(jsonStr){
		  preview_rule();  
    	  var selectedRule;
    	  if(jsonStr != null){
              selectedRule = $.parseJSON(jsonStr.selectedRule)
    	   }
    	   var isShowRules=$("#isShowRules").val();
           if(!isShowRules){
               g_userManage.tableUser = $('#ruls_list').dataTable($.extend({
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
                       Comm.ajaxPost('knowledge/tree/getTreeDataForEngine',JSON.stringify(queryFilter),function(result){
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
                           "data":null,
                           "searchable":false,"orderable" : false,
                           "class":"hidden"
                       },
                       {"data": "name","orderable" : false,"searchable":false}
                   ],
                   "createdRow": function ( row, data, index,settings,json ) {
                   },
                   "initComplete" : function(settings,json) {
                   }
               }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
               g_userManage.tableUser.on("order.dt search.dt", function() {
                   g_userManage.tableUser.column(0, {
                       search : "applied",
                       order : "applied"
                   }).nodes().each(function(cell, i) {
                       cell.innerHTML = i + 1
                   })
               }).draw();
               $("#isShowRules").val(true);
           }else{
               g_userManage.tableUser.ajax.reload();
           }
       render(selectedRule);
	}

    function preview_rule(){
  	  if($("#rule").find("#refuse-rule .l_back,#addorSub-rule .l_back").length>0){
			  $("#preview-rule").removeClass("hidden");
		  }else{
			  $("#preview-rule").addClass("hidden");
	      }    
    }
	
    $("#searchRuleBtn").click(function(){
    	var ruleName = $("#searchRuleKey").val();
    	var _url="${ctx}/knowledge/rule/getRuleDataForEngine"
		var _param = new Object();
    	var ids = new Array();
		_param.status = 1;
		_param.engineId = ${engineId};
		_param.is_select = 1;
		_param.key="ruleName";
		_param.value =ruleName
		$.post(_url,_param,function(data){
			addContentForTag(data,ids);
			$("#cats").find(".c-policy-cat").removeClass("c-font-color");
		})   
    })
	
	function render(selectedRule){
		 $(".rule-tag").unbind('click').on('click',function(e){
			  var ids = new Array();
			  if(selectedRule!=undefined && selectedRule.length > 0){
				  for (var i = 0; i < selectedRule.length; i++) {
					  console.log(selectedRule[i].id)
					  ids.push(selectedRule[i].id); 
				  } 
			  }
			  $("#cats").find(".c-policy-cat .selected-tag").removeClass("selected-tag");
			  $(e.target).addClass("selected-tag");
			  $(e.target).parent().addClass("c-font-color");
			  $(e.target).attr("disabled","disabled");
			  var id = $(e.target).attr("dataId");
			  var _url="${ctx}/knowledge/rule/getRuleDataForEngine"
			  var _param = new Object();
			  _param.status = 1;
			  _param.parentIds = id;
			  _param.engineId = ${engineId};
			  $.post(_url,_param,function(data){   
				  addContentForTag(data,ids);
			  })
		  })
		showRules();
	 }
    function showRules(){
		layer.open({
			type : 1,
			title : '规则集',
			maxmin : true,
			shadeClose : false,
			area : [ '600px', '468px' ],
			content : $('.c-policy-dialog'),
			btn : [ '保存', '取消' ],
			yes : function(index, layero) {

			}
		});
	}
    
    function  addContentForTag(data,ids){
  	  var ruleArray = data.rlist;
	  var ruleStr = "";
	  if(ruleArray.length > 0){
			  for (var i = 0; i < ruleArray.length; i++) {
				    var count = 0;
				    $("#myTabContent").find('div .l_back').each(function(index,element){
				 	    if($(element).attr("dataTitle") == ruleArray[i].name) {
				 	    	count++;
				 	    }
				    })	
				    
				    var l_back="";
  				if(count > 0){
  					l_back="l_back"
				}
  				
  				if(ids.length > 0 && $.inArray(parseInt(ruleArray[i].id), ids) > -1){
  					l_back="l_back"
  				}
  				
				    ruleStr  +='<div class="c-every-radio">'+
				'<div class="l_checkbox '+l_back+' rules"  dataRuleType="'+ruleArray[i].ruleType+'" dataPriority="'+ruleArray[i].priority+'" dataParentId="'+ruleArray[i].parentId+'" dataTitle="'+ruleArray[i].name+'" dataCode="'+ruleArray[i].code+'"  dataId ="'+ruleArray[i].id+'" style="margin-top:0px;"></div>'+
				    '<span title="'+ruleArray[i].name+'">'+ruleArray[i].name+'</span>'+
			    '</div>';	
				}
		  }
	  $("#rules").html('').append(ruleStr)
	  render_1();
    }
    
    

	function render_1(){
	  	  $(".rules").unbind('click').on('click',function(e){
	  		debugger;
  		    var dataId = $(e.target).attr("dataId");
		    var dataTitle = $(e.target).attr("dataTitle");
		    var dataParentId = $(e.target).attr("dataParentId");
		    var dataPriority = $(e.target).attr("dataPriority");
		    var dataRuleType = $(e.target).attr("dataRuleType");
		    var dataCode = $(e.target).attr("dataCode");
		    
	  		if(!$(e.target).hasClass("l_back")){
	  			 if(parseInt(dataRuleType) == 0){
				    	for (var i = 0; i <= 9; i++) {
					    	if(parseInt(dataPriority) == i){
					    		var  strHtml='<div class="c-priority-radio">'+
									'<div class="l_back" dataCode="'+dataCode+'" dataPriority="'+dataPriority+'" dataParentId="'+dataParentId+'" dataTitle="'+dataTitle+'" dataId ="'+dataId+'" style="margin-top:0px;"></div>'+
									'<span>'+dataTitle+'</span>'+
							    '</div>';
					    		$(".priority_"+i).append(strHtml);
					    	}
						} 
				    	updatePriority();
				    }else{
				    	 var  strHtml_1='<div class="c-priority-radio">'+
							'<div class="l_back" dataCode="'+dataCode+'" dataPriority="'+dataPriority+'" dataParentId="'+dataParentId+'" dataTitle="'+dataTitle+'" dataId ="'+dataId+'" style="margin-top:0px;"></div>'+
							'<span>'+dataTitle+'</span>'+
					        '</div>';
					     $("#addorSub-rule").append(strHtml_1); 
				    }
	  		}else{
	  			$("#refuse-rule").find(".l_back").each(function(index,element){
	  				if($(element).attr("dataId") == dataId){
	  					$(element).parent().remove();
	  				}	
	  			})	
	  			$("#addorSub-rule").find(".l_back").each(function(index,element){
	  				if($(element).attr("dataId") == dataId){
	  					$(element).parent().remove();
	  				}	
	  			})
	      }
	  	  upadteShow();
	  	  preview_rule();
	  	  updatePriority();
	     })
	}
	

	function updatePriority(){
		$("#refuse-rule .right-priority").each(function(index,element){
				if($(element).find(".l_back").length == 0){
					$(element).parent().hide().addClass("hdf")
				}else{
					$(element).parent().show().removeClass("hdf");
				}
	    })	    
		 if($("#refuse-rule").find(".hdf").length == 9){
			$(".c-policy-priority").css("height","40px");
			$(".left-priority").css({
				height:"40px"
			});
			$(".right-priority").css({
				height:"40px"
			});
			$(".c-reject-lfet").css({
				marginTop:"0px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 8){
			$(".c-policy-priority").css("height","40px");
			$(".left-priority").css({
				height:"40px"
			});
			$(".right-priority").css({
				height:"40px"
			});
			$(".c-reject-lfet").css({
				marginTop:"19px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 7){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"25px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 6){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"40px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 5){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"55px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 4){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"70px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 3){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"85px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 2){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"120px"
			});
		}else if($("#refuse-rule").find(".hdf").length == 1){
			$(".c-policy-priority").css("height","29px");
			$(".left-priority").css({
				height:"29px",
			});
			$(".right-priority").css({
				height:"29px",
			});
			$(".c-reject-lfet").css({
				marginTop:"135px"
			});
		}
	}

	function upadteShow(){
		  if($("#refuse-rule").find(".l_back").length ==0){
			  $(".c-tier-one").hide();
		  }else{
			  $(".c-tier-one").show();
		  }
		  if($("#addorSub-rule").find(".l_back").length == 0){
			  $(".c-tier-two").hide();
		  }else{
			  $(".c-tier-two").show();
		  }
	}
	
	function initRender(deny_rules,addOrSubRules){
		if(deny_rules!=null){
			var denyRuleArray = deny_rules.rules;
			 if(deny_rules.isSerial == '1'){
			        $("#c-refuse-rule .l_checkbox").addClass('l_back');
		     } 
			 
			 if(denyRuleArray!=null && denyRuleArray.length>0){
				 for (var s = 0; s <= 9; s++) { 
					   	for (var int2 = 0; int2 < denyRuleArray.length; int2++) {
				    		if(parseInt(denyRuleArray[int2].priority) == s){
					    		var  strHtml='<div class="c-priority-radio">'+
									'<div class="l_checkbox l_back" dataCode="'+denyRuleArray[int2].code+'" dataPriority="'+denyRuleArray[int2].priority+'" dataParentId="'+denyRuleArray[int2].parentId+'" dataTitle="'+denyRuleArray[int2].name+'" dataId ="'+denyRuleArray[int2].id+'" style="margin-top:2px;"></div>'+
									'<span>'+denyRuleArray[int2].name+'</span>'+
							    '</div>';
					    		$(".priority_"+s).append(strHtml);
					    	}
						}
			    } 
			 }
		}

		if(addOrSubRules != null){
		    var asArray = addOrSubRules.rules;
			$("#threshold").val(addOrSubRules.threshold);
			if(asArray!=null && asArray.length>0){
				for (var int3 = 0; int3 < asArray.length; int3++) {
					var  strHtml='<div class="c-priority-radio">'+
					'<div class="l_checkbox l_back" dataCode="'+asArray[int3].code+'" dataPriority="'+asArray[int3].priority+'" dataParentId="'+asArray[int3].parentId+'" dataTitle="'+asArray[int3].name+'" dataId ="'+asArray[int3].id+'" style="margin-top:2px;"></div>'+
					'<span>'+asArray[int3].name+'</span>'+
			       '</div>';
				    $("#addorSub-rule").append(strHtml); 
				}
			}
		}
		upadteShow();
	    preview_rule();  
	    updatePriority();
	}
	
	function clearRule(){
		$("#rules").find('.l_back').removeClass("l_back");
		$("#rule").find("#refuse-rule .l_back,#addorSub-rule .l_back").parent().remove();
		$("#rule").find("#c-refuse-rule .l_checkbox").removeClass("l_back")
		$("#threshold").val("")
	}
	
	function lookOrOperate(nodeId){
        debugger
		clearRule();
		var _param = {"nodeId":node_2.id};
		if(node_2.id!=0){
			Comm.ajaxPost("decision_flow/getNode",_param,function (data) {
				debugger
				if(data.engineNode!=null){
					console.log(data)
					var jsonStr = $.parseJSON(data.engineNode.nodeJson);
					var addOrSubRules = null ,deny_rules = null;
					if(jsonStr.addOrSubRules!=''){
						addOrSubRules = $.parseJSON(jsonStr.addOrSubRules)
					}
					if(jsonStr.deny_rules!=''){
						deny_rules = $.parseJSON(jsonStr.deny_rules);
					}
					getTreeData(jsonStr);
					initRender(deny_rules,addOrSubRules)
				}else{
					getTreeData(null);
				}
			})
		}else{
			getTreeData(null);
		}
	}
</script>	
<style>
 .hidden{
   display:none;
 }
</style>	