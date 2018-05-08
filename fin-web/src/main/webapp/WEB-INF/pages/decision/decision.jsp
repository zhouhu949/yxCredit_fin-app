<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="time" value="<%=new Date().getTime()%>"/>
<c:set var="version" value="?v=${time}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>决策流</title>
		<link rel="stylesheet" href="${ctx}/resources/css/decision/decision-leftMenu.css">
		<link rel="stylesheet" href="${ctx}/resources/css/decision/rulesDetails.css">
		<link rel="stylesheet" href="${ctx}/resources/css/decision/commonality.css">
		<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
	</head>
	<body>
    <textarea id="jtopo_textfield" style="width: 60px; z-index:10000; position: absolute; display: none;" onkeydown="if(event.keyCode==13)this.blur();" value=""></textarea>
	<input type="hidden" name="initEngineVersionId" value="${initEngineVersionId}">
	<input type="hidden" name="processId" value="${processId}">
	<input type="hidden" id="getCtx" value="${ctx}">
    <object id="getScene" style="display: none"></object>
	<object id="groupJson" style="display: none"></object>
    <object id="node_2" style="display: none"></object>
	<input type="hidden" id="node_x" value="">
	<input type="hidden" id="node_y" value="">
	<input type="hidden" id="node_name" value="">
	<input type="hidden" id="node_type" value="">
	<input type="hidden" id="node_code" value="">
	<input type="hidden" id="node_order" value="">
    <input type="hidden" id="node_dataId" value="">
    <input type="hidden" id="node_url" value="">
    <div class="outs">
			<div class="out-content">
				<div class="left-decisionMenus">
					<div class="l-leftTopMenu">
						<span class="l_title">
							决策流
						</span>
						<span class="l_descript">
							请配置引擎专属的决策流
						</span>
					</div>
					<div class="l_decision_options">
						<ul id="l_decisions">
							<li dataId="0">
                                 <img src="${ctx}/resources/images/decision/blackName.png" />
								<div class="fs2">
		                           <span class="icon-blackName">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_black_list left_Name">
									黑名单
								</span>
							</li>
							<li dataId="1">
                                <img src="${ctx}/resources/images/decision/whiteName.png" />
								<div class="fs2">
		                           <span class="icon-whiteName">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_white_list left_Name">
									白名单
								</span>
							</li>
							<li dataId="2">
                                <img src="${ctx}/resources/images/decision/riverRate.png" />
								<div class="fs2">
		                           <span class="icon-riverRate">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_river_rate left_Name">
									沙盒比例
								</span>
							</li>
							<li dataId="3">
                                <img src="${ctx}/resources/images/decision/userGroup.png" />
								<div class="fs2">
		                           <span class="icon-userGroup">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span><span class="path4"></span>
		                              <span class="path5"></span><span class="path6"></span><span class="path7"></span><span class="path8"></span>
		                            </span>
					            </div>
								<span class="l_users_group left_Name">
									客户分群
								</span>
							</li>
							<li dataId="4">
                                <img src="${ctx}/resources/images/decision/ruleGroup.png" />
								<div class="fs2">
		                           <span class="icon-ruleGroup">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>

		                            </span>
					            </div>
								<span class="l_rules_group left_Name">
									规则集
								</span>
							</li>
							<li dataId="5">
                                <img src="${ctx}/resources/images/decision/scoreLevel.png" />
								<div class="fs2">
		                           <span class="icon-scoreLevel">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_score_level left_Name">
									评分卡
								</span>
							</li>
							<%-- <li dataId="6">
								<img src="${ctx}/resource/images/decision/crediteLevel.png" />
								<div class="fs2">
		                           <span class="icon-crediteLevel">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_credit_level left_Name">
									信用评级
								</span>
							</li> --%>
							<li dataId="7">
								 <img src="${ctx}/resources/images/decision/dcisionOption.png" />
								<div class="fs2">
		                           <span class="icon-dcisionOption">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_decisions_options left_Name">
									决策选项
								</span>
							</li>
							<%-- <li dataId="8">
								<img src="${ctx}/resource/images/decision/limitCompute.png" />
								<div class="fs2">
		                           <span class="icon-limitCompute">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_limit_compute left_Name">
									额度计算
								</span>
							</li> --%>
							<%-- <li dataId="9">
								<img src="${ctx}/resource/images/decision/formAnalyse.png" />
								<div class="fs2">
		                           <span class="icon-formAnalyse">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_forms_analyse left_Name">
									报表分析
								</span>
							</li> --%>
							<%-- <li dataId="10">
								<img src="${ctx}/resource/images/decision/diy.png"" />
								<div class="fs2">
		                           <span class="icon-diy">
		                             <span class="path1"></span><span class="path2"></span><span class="path3"></span>
		                            </span>
					            </div>
								<span class="l_diy left_Name">
									自定义按钮
								</span>
							</li> --%>
						</ul>
					</div>
				</div>
				<div class="l_rights">
					<div class="rightTopMenu">
						<div class="left_txts">
							决策流程布局图
						</div>
						<div class="l_right_buttons">
							<div class="l_left_buttons">
								<button style="width: 80px;" id="clickWrite" class="btn btn-primary queryBtn">数据填写</button>
								<button id="versionSave" class="btn btn-primary queryBtn">保存</button>
								 <%--<%--%>
							     <%--temp_menu_key=String.format("1121_%s",engine_id);  //部署--%>
							     <%--if  (map_menu.get(temp_menu_key)!=null) {--%>
							  	 <%--%>--%>
								<button id="arrange" class="stopArrange btn btn-primary queryBtn">停止部署</button>
								<%--<%}%>--%>
								<button id="clearAll" class="btn btn-danger queryBtn">清空</button>
								<button id="adds" class="btn btn-primary queryBtn">新建</button>
							</div>
							<%-- <div class="l_input">
								<input type="text" placeholder="搜索" name="search" id="search" value="">
								<img id="search_btn" src="${ctx}/resource/images/engine/search.png">
							</div> --%>
						</div>
					</div>
					<div class="rightOperateArea" style="background:url(${ctx}/resources/images/decision/decisionBcg.jpg)">

					<img alt='' src='${ctx}/resources/images/decision/cha.png' style="display:none; z-index:10001; position:absolute;" id="lineDel" />
						<canvas id="canvas" width="1033px" height="450px">
						</canvas>
						<div class="l_through_line">
							<!--<span>
								连线
							</span>-->
							<span id="areaSelect" class="disSelect">
								区域
							</span>
						</div>

						<div class="l_pictures_operate">
							<div id="stageDel">
							    <div class="fs0">
                                    <img src="${ctx}/resources/images/decision/del.png" title="删除" />
			                         <span class="icon-dels" style="color:#398dee"></span>
						        </div>
							</div>
							<div id="stageSearch">
							     <div class="fs0">
                                     <img src="${ctx}/resources/images/decision/search.png" title="搜索" />
                                     <span class="icon-search" style="color:#398dee"></span>
						         </div>
							 </div>
							<div id="stageEnlarge">
							     <div class="fs0">
                                     <img src="${ctx}/resources/images/decision/fangda.png" title="放大" />
                                     <span class="icon-fangda" style="color:#398dee"></span>
						         </div>
							</div>
							<div id="stageNarrow">
							      <div class=" fs0">
                                      <img src="${ctx}/resources/images/decision/suoxiao.png" title="缩小" />
			                         <span class="icon-suoxiao" style="color:#398dee"></span>
						          </div>
							 </div>
						</div>
						<!--<div class="testImg">
							<img src="../${ctx}/resource/images/decision/黑名单.png" style="position: absolute;"/>
						</div>-->
						<div class="c-hide-prompt">
						    <div class="c-prompt-content-add">

						    </div>
						    <button class="c-prompt-button"></button>
					    </div>
					</div>
					<div class="l_version">
						<div class="l_run_version" style="overflow:auto;">
							 <!-- 主版本所在区域 -->
						</div>
						<div class="l_stop_version" style="overflow: auto;">
							<div class="l_right_versionTxt">
								<!-- <div class="l_left_versionTxt">
									<span class="stop_point">
								    </span>
									<span>正在运行 &nbsp;V 10.1</span>
								</div> -->
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	<!-- 右键菜单 -->
	<div class="bigCircle" style="display: none; position: absolute;">
		<div class="operate hovers" title="编辑">
			<img src="${ctx}/resources/images/decision/operate.png" class="shows"/>
			<img src="${ctx}/resources/images/decision/operateWhite.png" style="display: none;"  class="hides" />
		</div>
		<div class="look hovers" title="查看" dataId=''>
			<img src="${ctx}/resources/images/decision/look.png" class="shows" />
			<img src="${ctx}/resources/images/decision/lookWhite.png" style="display: none;" class="hides" />
		</div>
		<div class="copy hovers" title="复制">
			<img src="${ctx}/resources/images/decision/copy.png" class="shows" />
			<img src="${ctx}/resources/images/decision/copyWhite.png" style="display: none;" class="hides" />
		</div>
		<div class="delete hovers" title="删除">
			<img src="${ctx}/resources/images/decision/delete.png" class="shows" />
			<img src="${ctx}/resources/images/decision/deleteWhite.png" style="display: none;" class="hides" />
		</div>
		<div class="alpha">
		</div>
		<div class="blue" class="hideMenu">
			<img src="${ctx}/resources/images/decision/dele.png" style="margin:0"/>
		</div>
	</div>
	</body>
	<script  type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/decision/decision-leftMenu.js${version}" charset="utf-8"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/lib/jtopo/jtopo-0.4.8-min.js" charset="utf-8"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/lib/jtopo/toolbar.js"  charset="utf-8"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/procedureMange/decision_content_all.js${version}"  charset="utf-8"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/common/common.js"></script>
	<script  type="text/javascript" src="${ctx}/resources/assets/layer/layer.js"></script>
    <script>
		var _version = "${time}";
    	//决策流验证框动画
    	var clicks=true;
    	var content_one;
         $(".c-prompt-button").on("click",function(){
        	  var heightOut=0;
        	  for(var i=0;i<$(".c-prompt-content").length;i++){
        		   heightOut=heightOut+$(".c-prompt-content").eq(i).outerHeight();
        	  }
        	 if(clicks){
        		 content_one=$(".c-prompt-content").eq(0).outerHeight();
        		 $(".c-prompt-content").animate({height:"0"},function(){
        			 $(".c-prompt-content").hide();
        			 clicks=false;
        		 });
        	 }else{
        		  $(".c-prompt-content").animate({height:"31"},function(){
        			  $(".c-prompt-content").eq(0).animate({
             			 height:content_one
             		 });
        		  });
        		 $(".c-prompt-content").show();
        		 clicks=true;
        	 }
         });
    </script>