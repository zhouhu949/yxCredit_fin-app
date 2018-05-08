<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="time" value="<%=new Date().getTime()%>"/>
<c:set var="version" value="?v=${time}"/>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="${ctx}/resources/assets/atwho/jquery.atwho.css">
	<script src="${ctx}/resources/assets/atwho/jquery.atwho.js"></script>
	<script src="${ctx}/resources/assets/atwho/jquery.caret.js"></script>
    <title>规则集详情</title>
<style>
	#ser-value{
		overflow: hidden;
	}
	.c-variable-add{
		text-align: left;
		margin-left: 1.5em;
		float: left;
	}
	.l_selects{
		margin-left:36px;
	}
	#d-option-out{
		float: left;
		margin-left: 1.5em;
		clear: both;
	}
	#myTab{
		overflow: hidden;
	}
	#myTab .c-decision-switcher{
		float:left;
	}
	#d-option-2,#d-option-3{
		display: none;
	}
	#d-option-1 #myTabContent{
		position: relative;
	}
    .c-operation-inclusion{
        float: left;
        position: relative;
        top: 8px;
    }
    .c-field-return{
        float: right;
    }
    .c-optional-rules{
        text-align: right;
    }
    .c-operational-character,.c-operational-characterTwo{
        display: inline-block;
    }
    .decision-TabControl-head{
        margin:1em auto;
    }
    #myTab1 div.c-decision-switcher:first-child,#myTab2 div.c-decision-switcher:first-child{
        float: left;
        width: 50%;
        cursor: pointer;
    }
    a{
        text-decoration: none!important;
    }
	.c-front-portion-head{
		display: block;
		height: 100px;
		width: 100%;
		border: 1px solid #ddd;
	}
	.c-first-line .c-option-a{
		text-align: left;
		float: left;
	}
	#decision_regionTwo .c-decision_region{
		overflow: hidden;
	}
	#decision_regionTwo .c-first-line{
		float: left;
	}
	#decision_regionTwo .c-next-line{
		float: right;
	}
	#des-b .c-section-left{
		float: left;
	}
	#des-b .c-option-d{
		float: left;
	}
	.c-next-line .c-queen-portion-head{
		overflow: hidden;
		padding: 1em 0;
		border: 1px solid #ddd;
	}
	.c-option-c{
		padding: 1em 0;
		text-align: left;
		border: 1px solid #ddd;
		border-top: 0;
		width: 1001%;
	}
	.c-next-line .c-queen-portion-head{
		width: 1001%;
	}
	.c-queen-portion .c-option-e{
		display: inline-block;
	}
	input[name=result]{
		margin-left: 16px;
	}
	.c-queen-portion{
		padding: 1em 0;
		border-bottom: 1px solid #ddd;
	}
	.c-option-a .c-front-portion{
		padding: 1em;
		padding-top: 5px;
		border: 1px solid #ddd;
		border-top: 0;
	}
	.includeFormula{
		width: 100%!important;
		height: 175px;
		border: 1px solid #ddd;
		margin: 1em auto;
		margin-top:0;
	}
	.c-field-operator-head{
		background: #ddd;
		padding: .5em 0;
	}
	img{
		cursor: pointer;
	}
	.c-operator-hide{
		display: none;
	}
	.c-operational-character,.c-operational-characterTwo{
		margin:0 1em;
	}
</style>
</head>
<body>
    <input type="hidden" id="getPath" value="${ctx}">
	<div id="options">
		<div class="c-decision-variable" id="ser-value">
			<div class="c-variable-content" id ="d-option">
				<div class="c-variable-add" style="margin-top:10px;">
					<span>输入变量</span>
					<img src="${ctx}/resources/images/rules/add.png" id="add_option" />
					<img src="${ctx}/resources/images/rules/delete.png" id ="delete_option" />
				</div>
				<div class="l_selects" style="float:left; width:400px; overflow:hidden;">
					<div class="select_option" style="width:130px; float:left; margin-top:10px;">
						<input  data="" class="l_before_input input_variable"  style="width:124px;background-position:105px 0px;">
					</div>
				</div>
			</div>
			<div class="c-export-variable" id="d-option-out">
				<span>输出变量</span>
					<input name="output" id="output" data="" class="l_before_input input_variable"  style="width:124px;margin-left:81px;">
			</div>
		</div>
		<div class="c-decision_region-content" id="d-option-1" >
			<div id="myTab1" class="decision-TabControl-head" role="tablist">
				<div class="c-decision-switcher">
					<a href="#decision_region" id ="decision_region_tag" class="" role="tab" data-toggle="tab">决策区域</a>
				</div>
				<div class="c-decision-switcher" style="border-right: none;">
					<a href="#equation_editing" id ="equation_editing_tag" class=""  role="tab" data-toggle="tab">公式编辑</a>
				</div>
			</div>
			<div id="myTabContent1" class="tab-content">
				<div class="tab-pane active" id="decision_region" style="max-height:200px;overflow-y:auto;overflow-x:hidden;">
					<div class="c-field-condition">

					</div>
				</div>
				<div class="tab-pane" id="equation_editing" >
					<c:import url="formula.jsp"></c:import>
				</div>
				</div>
			</div>
		</div>
		<div class="c-decision_region-content" id="d-option-2" >
			<div id="myTab2" class="decision-TabControl-head" role="tablist">
				<div class="c-decision-switcher">
					<a href="#decision_regionTwo"  id ="decision_regionTwo_tag" class="" role="tab" data-toggle="tab">决策区域</a>
				</div>
				<div class="c-decision-switcher" style="border-right: none;">
					<a href="#equation_editingTwo" id ="equation_editingTwo_tag" class=""  role="tab" data-toggle="tab">公式编辑</a>
				</div>
			</div>
			<div id="myTabContent2" class="tab-content">
				<div class="tab-pane active" id="decision_regionTwo">
					<div class="c-decision_region">
						<div class="c-first-line">
							<div class="c-front-portion-head">
								<div class="c-variate-a">决策变量A</div>
							</div>
							<div class ="c-option-a"></div>
						</div>
						<div class="c-next-line" style="overflow-x:scroll;max-width:390px;">
							<div class="c-queen-portion-head">
								<div class="c-variate-b">
									<span>决策变量B</span>
								</div>
								<div class="c-section-decision" id="des-b" style="width:1000px;">
									<div class="c-section-left" style="width:80px;">
										<img src="${ctx}/resources/images/rules/add.png"  class ="l_op_b_addData"/>
										<img src="${ctx}/resources/images/rules/delete.png"  class ="l_op_b_delData"/>
									</div>
								</div>
							</div>
							<div class="c-option-c"></div>
						</div>
					  </div>
				   </div>
				 <div class="tab-pane" id="equation_editingTwo">
					<c:import url="formula.jsp"></c:import>
				 </div>
			</div>
		</div>
		<div class="c-decision_region-content" id="d-option-3" >
		   <p style="padding: 10px 0 0 20px;color:#777777;">公式编辑</p>
			  <c:import url="formula.jsp"></c:import>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/resources/js/formula/jquery.atwho.js${version}"></script>
	<script type="text/javascript" src="${ctx}/resources/js/formula/jquery.shuzun.caret.js${version}"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/formula.js${version}"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/decision/condition.js${version}" ></script>
	<script type="text/javascript" src="${ctx}/resources/js/validate/map.js${version}"></script>
    <script type="text/javascript" src="${ctx}/resources/js/decision/decision_options.js${version}"></script>
</body>
</html>