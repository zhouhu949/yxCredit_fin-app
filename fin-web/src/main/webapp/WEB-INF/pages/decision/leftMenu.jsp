<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>引擎管理主页</title>
		<style>
			.left-menu{
				position:fixed;
			}
			.img-left{
				text-align: center;
				padding: 1.2em 0em;
			}
			.left-menu ul{
				background: #353F4B;
				margin-left:20px;
				margin:0
			}
			.left-menu ul li{
				width:52px;
				list-style: none;
				position: relative;
			}
			.left-menu .animate a{
				display: inline-block;
				width: 75px;
				text-decoration: none;
				padding:1.2em 0;
				color:#fff;
			}
			.animate{
				display: none;
				position: absolute;
				top:0px;
				left:50px;
				background: #000;
			}
			.liActive{
				background:#212733!important;
			}
            .img-left b{
                cursor: pointer;
            }
		</style>
	</head>
	<body>
		<div class="left-menu" isShow="1">
			<ul>
				<li class="liActive">
					<div class="img-left" id="decisions-id">
						<b  class="a_href" onclick="getHrefshow(this)" url="" jspName="decisions">
						  <img src="${ctx}/resources/images/left-menu/first.png"/>
						  <div class="glyph fs1">
							<div class="clearfix pbs">
	                           <span class="icon-ceshi"></span>
	                        </div>
				          </div>
						</b>
					</div>
					<div class="animate">
						<a href="###">决策流</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="rule-collection-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							 <img src="${ctx}/resources/images/left-menu/second.png" />
							<div class="glyph fs1">
								<div class="clearfix pbs">
		                           <span class="icon-knowledge"></span>
		                        </div>   
				          </div> 
						</b>
					</div>
					<div class="animate">
						<a href="###">知识库</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="fieldmapping-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							<img src="${ctx}/resources/images/left-menu/forth.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
		                           <span class="icon-fields"></span>
		                        </div>   
				            </div>
						</b>
					  </div>
					<div class="animate">
						<a href="###">字段映射</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="batchTest-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							<img src="${ctx}/resources/images/left-menu/third.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
		                           <span class="icon-result"></span>
		                        </div>   
				            </div>
						</b>
					</div>
					<div class="animate">
						<a href="###">结果集</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="measurement-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							<img src="${ctx}/resources/images/left-menu/computer.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
									<span class="icon-ceshi2"></span>
								</div>
							</div>
						</b>
					</div>
					<div class="animate">
						<a href="###">测试信息</a>
					</div>
				</li>
			</ul>
		</div>
	</body>
	<script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".img-left").on("mouseenter", function() {
                $(this).parent().css("background","#212733");
				$(this).parent().find(".animate").show();
			});
			$(".left-menu li").on("mouseleave", function() {
                $(this).css("background","#353F4B");
				$(this).find(".animate").hide();
			});
	        //默认选中头菜单
			var parentId = getQueryString("parentId");
		 	var urls = [
				"${ctx}/decision_flow/decisionsPage?id=${engineId}&flag=0",
				"${ctx}/knowledge/tree?engineId=${engineId}",
				"${ctx}/engineManage/fieldmapping?engineId=${engineId}",
				"${ctx}/engine/toresult?engineId=${engineId}",
				"${ctx}/engine/engineTest?engineId=${engineId}"
		 	];
		 	for(var i=0;i<urls.length;i++){
				var url = urls[i]+"&&parentId="+parentId;
				 $(".a_href").eq(i).attr("url",url);
			}
			function getQueryString(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null;
			}
			//左菜单默认选中
			var left_name = $("#leftjsp_id").val();
			switch(left_name){ 
			case "decisions.jsp": 
					$("#decisions-id").addClass("l_imgSelect");				
					break; 
			case "rule-collection.jsp": 
					$("#rule-collection-id").addClass("l_imgSelect");
					break; 
			case "fieldmapping.jsp": 
					$("#fieldmapping-id").addClass("l_imgSelect");
					break; 
			case "resultList.jsp": 
					$("#batchTest-id").addClass("l_imgSelect");
					break; 
			case "measurement.jsp": 
					$("#measurement-id").addClass("l_imgSelect");
					break; 
			default: 
					break;
			}
		});
		function getHrefshow(me) {
            $(me).parent().parent().addClass("liActive").siblings(".liActive").removeClass("liActive");
            $("#iframejsp").attr("src",$(me).attr("url"));
		}
	</script>
</html>