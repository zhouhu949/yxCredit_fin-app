<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.c-black-name div.showName{
		text-align: left;
		margin:1.5em;
		font-size:1em;
	}
    .c-black-price{
        text-align: left;
        margin:1.5em;
        padding-bottom:1em;
        border-bottom: 1px dashed #ddd;
    }
</style>
<div class="c-black-dialog dialog" style="display:none">
	<div class="c-black-head move_part">
		<input type="hidden" class="hide" name="canEdit" value="${canEdit}">
		<input type="hidden" class="hide" name="nodeId" value="${nodeId}">
		<input type="hidden" class="hide" name="innerNodeListDb" value="${innerNodeListDb}">
		<input type="hidden" class="hide" name="outerNodeListDb" value="${outerNodeListDb}">
	</div>
	<div class="c-black-content">
		<div class="c-black-interior"></div>
        <hr>
		<div class="c-black-iexternal"></div>
	</div>
	<div align="center" style="display: none">
		<button class="" id="blackSave">确定</button>
	</div>
</div>

<script type="text/javascript">
var nodeId;
function blackInit(nodeId,isEdit){
	$(".c-black-interior").html(" ");
	$(".cc-black-iexternal").html(" ");
	$("input[name=nodeId]").val(nodeId);
	// 同步请求节点已保存的黑白名单库信息
    var _arrInnerNodeListDb = new Array();
    var _arrOuterNodeListDb = new Array();
	if(nodeId>0){
		var paramMap={"nodeId":nodeId,"listType":"b"};
		Comm.ajaxPost("bwListNode/findBlackList",JSON.stringify(paramMap),function(result){
			var data=result.data;
			_arrInnerNodeListDb = data.innerNodeListDb;
			_arrOuterNodeListDb = data.outerNodeListDb;
		},"application/json","","","",false)
	}
    if(isEdit=='Y'){ //编辑（新增和已保存的回显）时显示已经保存了的已勾选的名单和其它所有可用的名单列表
		var paramMap={"status":1,"listType":"b"};
		Comm.ajaxPost("datamanage/listmanage/findListDbByUser",JSON.stringify(paramMap),function(result){
			var data=result.data;
			//内部黑名单列表
			var innerArray = data.insideListDbs;
			var str = '<div class="c-black-name"><div class="showName">内部黑名单:</div></div><input type="hidden" class="hide" name="innerDbs" value="">';
			if(innerArray.length>0){
				str += '<div class="c-black-name-right">';
				for(var i=0; i<innerArray.length; i++){
					var flag = 0;
					for(var j=0; j<_arrInnerNodeListDb.length ;j++){
						if(innerArray[i].id==_arrInnerNodeListDb[j].id){
							flag = 1;
						}
					}

					str += '<div class="c-external-wrapper"><div class="c-black-frame ';
					if(flag==1){
						str += 'l_back ';
					}
					str += '" msg="'+innerArray[i].id+'"><input type="checkbox" isChecked="false" class="checkThis"></div></div>'
							+ '<div class="c-black-price">'+innerArray[i].listName+'</div></div>';
					if(i==innerArray.length-1)
						str += '</div>';
				}
			}
			$(".c-black-interior").html("").append(str);

			//外部黑名单列表
			var outerArray = data.outsideListDbs;
			var strO = '<div class="c-black-name"><div class="showName">外部黑名单:</div></div><input type="hidden" class="hide" name="outerDbs" value="">';
			if(outerArray.length>0){
				strO += '<div class="c-black-name-right">';
				for(var i=0; i<outerArray.length; i++){
					var flag = 0;
					for(var j=0; j<_arrOuterNodeListDb.length ;j++){
						if(outerArray[i].id==_arrOuterNodeListDb[j].id){
							flag = 1;
						}
					}
					strO += '<div class="c-external-wrapper"><div class="c-black-frame ';
					if(flag==1){
						strO += 'l_back ';
					}
					strO += '" msg="'+outerArray[i].id+'"><input type="checkbox"  isChecked="false"  class="checkThis"></div></div>'
							+ '<div class="c-black-price">'+outerArray[i].listName+'</div></div>';

					if(i==outerArray.length-1)
						str += '</div>';
				}
			}
			$(".c-black-iexternal").html("").append(strO);
            var divList= $(".c-black-frame");
            $(divList).each(function(index,elem){
                if($(this).hasClass("l_back")){
                    $(this).find("input").attr("checked","checked").attr("isChecked","true");
                }
            })
		},"application/json")
    }else if(isEdit=='N'){//黑名单查看时显示编辑时已经勾选并保存的数据
    	console.log("_arrInnerNodeListDb: ",_arrInnerNodeListDb);
    	console.log("_arrOuterNodeListDb: ",_arrOuterNodeListDb);
    	var str = '<div class="c-black-name"><div class="showName">内部黑名单:</div></div><input type="hidden" class="hide" name="innerDbs" value="">';
		if(_arrInnerNodeListDb.length>0){
			str += '<div class="c-black-name-right">';
			for(var i=0; i<_arrInnerNodeListDb.length; i++){
			
				str += '<div class="c-external-wrapper"><div class="c-black-frame l_back';
				str += '" msg="'+_arrInnerNodeListDb[i].id+'"><input type="checkbox" isChecked="true" checked  class="checkThis"></div>'
			          + '<div class="c-black-price">'+_arrInnerNodeListDb[i].listName+'</div></div>';
			    if(i==_arrInnerNodeListDb.length-1)
			    	str += '</div>';
			}
		}
	    $(".c-black-interior").html("").append(str);
		
		//外部黑名单列表
		var strO = '<div class="c-black-name"><div  class="showName">外部黑名单:</div></div><input type="hidden" class="hide" name="outerDbs" value="">';
		if(_arrOuterNodeListDb.length>0){
			strO += '<div class="c-black-name-right">';
			for(var i=0; i<_arrOuterNodeListDb.length; i++){
				strO += '<div class="c-external-wrapper"><div class="c-black-frame l_back';
				strO += '" msg="'+_arrOuterNodeListDb[i].id+'"><input type="checkbox"  isChecked="true" checked  class="checkThis"></div>'
			          + '<div class="c-black-price">'+_arrOuterNodeListDb[i].listName+'</div></div>';
				if(i==_arrOuterNodeListDb.length-1)
			    	str += '</div>';
			}
		}
	    $(".c-black-iexternal").html("").append(strO);
	    $("#blackSave").removeClass("blackEditSave").addClass("blackLookSave");
    }
	openEditBlack(isEdit);
}
function openEditBlack(isEdit){
    var title="选择黑名单";
    if(isEdit=="N"){
        title="查看黑名单"
    }
	layer.open({
		type : 1,
		title : title,
		maxmin : true,
		shadeClose : false,
		area : [ '576px', '468px' ],
		content : $('.c-black-dialog'),
		btn : [ '保存', '取消' ],
		yes : function(index, layero) {
			debugger
            var inputList=$(".c-black-content").find("input[type='checkbox']");
            var arr=[];
            $(inputList).each(function(index,element){
                if($(this).attr("checked")){
                    arr.push("1");
                }
            });
			if(arr.length>0&&isEdit=="Y"){
				//处理内部名单库
				var innerArray = new Array();
				console.log("1",innerArray);
				$(".c-black-interior").find(".c-black-frame").each(function(index,element){
					if($(this).hasClass("l_back")){
						var innerSelId = $(this).attr("msg");
						innerArray.push(innerSelId);
					}
					console.log("2",innerArray);
				});
				$("input[name= 'innerDbs']").val(innerArray);
				console.log("提交的innerDbs：",$("input[name= 'innerDbs']").val());

				//处理外部名单库
				var outerArray = new Array();
				$(".c-black-iexternal").find(".c-black-frame").each(function(index,element){
					if($(this).hasClass("l_back")){
						var outerSelId = $(this).attr("msg");
						outerArray.push(outerSelId);
					}
				});
				$("input[name= 'outerDbs']").val(outerArray);
				console.log("提交的outerDbs：",$("input[name= 'outerDbs']").val());

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
					,"params":params
				};
				Comm.ajaxPost(_url,JSON.stringify(_params),
					function(result){
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
$(".c-black-content").on("click",".c-black-price",function(){
    var msg=$(this).prev(".c-external-wrapper").find(".c-black-frame").attr("msg");
	var isChecked=$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("isChecked");
	if(isChecked=="false"){
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("checked","checked");
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("isChecked","true");
        $(this).prev(".c-external-wrapper").find(".c-black-frame").addClass("l_back");
	}else{
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("checked",false);
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("isChecked","false");
        $(this).prev(".c-external-wrapper").find(".c-black-frame").removeClass("l_back");
	}
})
$(".c-black-content").on("click",".checkThis",function(){
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