<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.c-white-name div.showName{
		text-align: left;
		margin:1.5em;
		font-size:1em;
	}
	.c-white-price{
		text-align: left;
		margin:1.5em;
		padding-bottom:1em;
		border-bottom: 1px dashed #ddd;
	}
</style>
<div class="c-white-dialog whitelist dialog" style="display: none;">
	<div class="c-white-head move_part">
	<input type="hidden" class="hide" name="canEdit" value="${canEdit}">
	<input type="hidden" class="hide" name="nodeId" value="${nodeId}">
	<input type="hidden" class="hide" name="innerNodeListDb" value="${innerNodeListDb}">
	<input type="hidden" class="hide" name="outerNodeListDb" value="${outerNodeListDb}">
	</div>
	<div class="c-white-content whitelist">
		<div class="c-white-interior">
			
		</div>
		<hr>
		<div class="c-white-iexternal">

		</div>
	</div>
</div>
<script type="text/javascript">
var nodeId;
function whiteInit(nodeId,isEdit){
	console.log("白名单的节点nodeId: ",nodeId);
	$("input[name=nodeId]").val(nodeId);
	var _arrInnerNodeListDb = new Array();
	var _arrOuterNodeListDb = new Array();
	// 同步请求节点已保存的黑白名单库信息
	if(nodeId>0){
		var paramMap={"nodeId":nodeId,"listType":"w"};
		Comm.ajaxPost("bwListNode/findBlackList",JSON.stringify(paramMap),function(result){
			var data=result.data;
			_arrInnerNodeListDb = data.innerNodeListDb;
			_arrOuterNodeListDb = data.outerNodeListDb;
		},"application/json","","","",false)
	}
    
    if(isEdit=='Y'){ //编辑（新增和已保存的回显）时显示已经保存了的已勾选的名单和其它所有可用的名单列表
		var paramMap={"nostatusdeId":nodeId,"listType":"w","status":"1"};
		Comm.ajaxPost("datamanage/listmanage/findListDbByUser",JSON.stringify(paramMap),function(result){
			var data=result.data;
			//内部白名单列表
			var innerArray = data.insideListDbs;
			var str = '<div class="c-white-name"><div class="showName">内部白名单:</div></div><input type="hidden" class="hide" name="innerDbs" value="">';
			if(innerArray.length>0){

				for(var i=0; i<innerArray.length; i++){
					var flag = 0;
					for(var j=0; j<_arrInnerNodeListDb.length ;j++){
						if(innerArray[i].id==_arrInnerNodeListDb[j].id){
							flag = 1;
						}
					}
					str += '<div class="c-white-frame ';
					if(flag==1){
						str += 'l_back ';
					}
					str += '" msg="'+innerArray[i].id+'"><input type="checkbox" isChecked="false" class="checkThis"></div>'
							+ '<div class="c-white-price">'+innerArray[i].listName+'</div>';
				}
			}
			$(".c-white-interior").html("").append(str);
			//外部白名单列表
			var outerArray = data.outsideListDbs;
			var strO = '<div class="c-white-name"><div  class="showName">外部白名单:</div></div><input type="hidden" class="hide" name="outerDbs" value="">';
			if(outerArray.length>0){
				for(var i=0; i<outerArray.length; i++){
					var flag = 0;
					for(var j=0; j<_arrOuterNodeListDb.length ;j++){
						if(outerArray[i].id==_arrOuterNodeListDb[j].id){
							flag = 1;
						}
					}
					strO += '<div class="c-white-frame ';
					if(flag==1){
						strO += 'l_back ';
					}
					strO += '" msg="'+outerArray[i].id+'"><input type="checkbox" isChecked="false" class="checkThis"></div>'
							+ '<div class="c-white-price">'+outerArray[i].listName+'</div>';
				}
			}
			$(".c-white-iexternal").html("").append(strO);
			var divList= $(".c-white-frame");
			$(divList).each(function(index,elem){
				if($(this).hasClass("l_back")){
					$(this).find("input").attr("checked","checked").attr("isChecked","true");
				}
			})
		},"application/json")
    }else if(isEdit=='N'){//黑名单查看时显示编辑时已经勾选并保存的数据
    	var str = '<div class="c-white-name"><div  class="showName">内部白名单:</div></div><input type="hidden" class="hide" name="innerDbs" value="">';
		if(_arrInnerNodeListDb.length>0){
			for(var i=0; i<_arrInnerNodeListDb.length; i++){
				str += '<div class="c-white-frame l_back';
				str += '" msg="'+_arrInnerNodeListDb[i].id+'"><input type="checkbox" isChecked="true" checked class="checkThis"></div>'
			     + '<div class="c-white-price">'+_arrInnerNodeListDb[i].listName+'</div>';
			}
		}
	    $(".c-white-interior").html("").append(str);
		
		//外部白名单列表
		var strO = '<div class="c-white-name"><div  class="showName">外部白名单:</div></div><input type="hidden" class="hide" name="outerDbs" value="">';
		if(_arrOuterNodeListDb.length>0){
			for(var i=0; i<_arrOuterNodeListDb.length; i++){
				strO += '<div class="c-white-frame l_back';
				strO += '" msg="'+_arrOuterNodeListDb[i].id+'"><input type="checkbox" isChecked="true" checked class="checkThis"></div>'
			     + '<div class="c-white-price">'+_arrOuterNodeListDb[i].listName+'</div>';
			}
		}
	    $(".c-white-iexternal").html("").append(strO);
    }
	openEditWhite(isEdit);
}
function openEditWhite(isEdit){
	var title="选择白名单";
	if(isEdit=="N"){
		title="查看白名单"
	}
	layer.open({
		type : 1,
		title : title,
		maxmin : true,
		shadeClose : false,
		area : [ '576px', '468px' ],
		content : $('.c-white-dialog'),
		btn : [ '保存', '取消' ],
		yes : function(index, layero) {
			var inputList=$(".c-white-content").find("input[type='checkbox']");
			var arr=[];
			$(inputList).each(function(index,element){
				if($(this).attr("checked")){
					arr.push("1");
				}
			});
			if(arr.length>0&&isEdit=="Y"){
				//处理内部名单库
				var innerArray = new Array();
				$(".c-white-interior").find(".c-white-frame").each(function(index,element){
					if($(this).hasClass("l_back")){
						var innerSelId = $(this).attr("msg");
						innerArray.push(innerSelId);
					}
				});
				$("input[name= 'innerDbs']").val(innerArray);

				//处理外部名单库
				var outerArray = new Array();
				$(".c-white-iexternal").find(".c-white-frame").each(function(index,element){
					if($(this).hasClass("l_back")){
						var outerSelId = $(this).attr("msg");
						outerArray.push(outerSelId);
					}
				});
				$("input[name= 'outerDbs']").val(outerArray);

				var _innerDbs = $("input[name= 'innerDbs']").val();
				var _outerDbs = $("input[name= 'outerDbs']").val();

				nodeId = $("input[name=nodeId]").val();
				if(nodeId==null||nodeId==''){
					nodeId = 0;
				}
				if(nodeId !=0 ){
					_url = "bwListNode/update";
				}else{
					_url = "bwListNode/create";
				}
				var node_dataIdDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_dataId');
				var node_dataId=$(node_dataIdDom).val();
				var node_urlDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_url');
				var node_url=$(node_urlDom).val();
				var node_typeDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_type');
				var node_type=$(node_typeDom).val();
				var node_orderDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_order');
				var node_order=$(node_orderDom).val();
				var node_codeDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_code');
				var node_code=$(node_codeDom).val();
				var node_xDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_x');
				var node_x=$(node_xDom).val();
				var node_yDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_y');
				var node_y=$(node_yDom).val();
				var node_nameDom=document.getElementById('iframejsp').contentWindow.document.getElementById('node_name');
				var node_name=$(node_nameDom).val();

				var params = '{"dataId":'+node_dataId+',"url":"'+node_url+'","type":'+node_type+'}';
				var versionId = $("input[name=initEngineVersionId]").val();
				var _params = {"nodeId":nodeId,"insideListdbs":_innerDbs,"outsideListdbs":_outerDbs
					,"node_dataId":node_dataId
					,"verId":versionId
					,"node_url":node_url
					,"node_order":node_order
					,"node_code":node_code
					,"node_x":node_x
					,"node_y":node_y
					,"node_name":node_name
					,"node_type":node_type
					,"nodeJson":''
					,"params":params};
				Comm.ajaxPost(_url,JSON.stringify(_params),
						function(result){
							debugger
							layer.closeAll();
							var data=result.data;
							var type=typeof(data);
							layer.msg("保存成功！",{time:1000});
							if(type=="number"){
								node_2.id = result.data;
								$("input[name=nodeId]").val(result.data);
							}else{
								node_2.id = result.data.nodeId;
								$("input[name=nodeId]").val(result.data.nodeId);
							}
						},"application/json"
				);
			}else{
				layer.closeAll();
			}
		}
	});
}
$(".c-white-content").on("click",".c-white-price",function(){
	var msg=$(this).prev(".c-white-frame").attr("msg");
	var isChecked=$(this).prev(".c-white-frame").find("input").attr("isChecked");
	if(isChecked=="false"){
		$(this).prev(".c-white-frame").find("input").attr("checked","checked");
		$(this).prev(".c-white-frame").find("input").attr("isChecked","true");
		$(this).prev(".c-white-frame").addClass("l_back");
	}else{
		$(this).prev(".c-white-frame").find("input").attr("checked",false);
		$(this).prev(".c-white-frame").find("input").attr("isChecked","false");
		$(this).prev(".c-white-frame").removeClass("l_back");
	}
})
$(".c-white-content").on("click",".checkThis",function(){
	var isChecked=$(this).attr("isChecked");
	if(isChecked=="false"){
		$(this).parent().addClass("l_back");
		$(this).attr("isChecked","true");
		$(this).attr("checked","checked");
	}else{
		$(this).parent().removeClass("l_back");
		$(this).attr("isChecked","false");
		$(this).attr("checked",false);
	}
})

</script>