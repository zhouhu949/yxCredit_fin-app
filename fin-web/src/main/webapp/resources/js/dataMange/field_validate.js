//英文名称是否重复校验
function validateFieldEn(){
	var _fieldEn = $("#en_name").val();
	
	_fieldEn = 'f_' + _fieldEn;
	
	var _Id = $("#id").val();
	var _engineId = $("#engineId").val();

	var reg = new RegExp("^[A-Za-z0-9_]+$");
	var flag = false;
	if(_fieldEn == 'f_'){
		//layer.tips("字段名称不能为空",$("#fieldName"), { time: 2000 });
		layer.msg("字段名称不能为空",{time:2000});
		return false;
	}else if(!reg.test(_fieldEn)) {
		//layer.tips("字段名称不合法",$("#fieldName"), { time: 2000 });
		layer.msg("字段名称不合法", {time: 2000});
		return false;
	}else {
		flag = true;
	}
    // else{
	// 	var param = {};
	// 	var paramFilter = {};
	// 	param._fieldEn = _fieldEn;
	// 	param._Id = _Id;
	// 	param._engineId = _engineId;
	// 	paramFilter.param = param;
	// 	Comm.ajaxPost(
	// 		'datamanage/field/fieldEnAjaxValidate', JSON.stringify(paramFilter),
	// 		function(data){
	// 			if(data > 0){
	// 				//layer.tips("字段名称已经存在",$("#fieldName"), { time: 2000 });
	// 				layer.msg("字段名称已经存在",{time:2000});
	// 				return false;
	// 			}else{
	// 				flag = true;
	// 			}
	// 		},"application/json",	null,false
	// 	);
	// }
	return flag;
}

//字段中文名称是否重复校验
function validateFieldCn(){
	var _fieldCn = $("#cn_name").val();
	var _Id = $("#id").val();
	var _engineId = $("#engineId").val();
	var reg = new RegExp("^[\u4e00-\u9fa5()（）A-Za-z0-9_]+$");
	var flag = false;
	if(_fieldCn == ''){
		//layer.tips("字段中文名不能为空",$("#fieldCName"), { time: 2000 });
		layer.msg("字段中文名不能为空",{time:2000});
		return false;
	}else if(!reg.test(_fieldCn)){
    	//layer.tips("字段中文名称不合法",$("#fieldCName"), { time: 2000 });
		layer.msg("字段中文名称不合法",{time:2000});
		return false;
	}
	else {
		flag = true;
	}
	// else{
	// 	var param = {};
	// 	var paramFilter = {};
	// 	param.fieldCn = _fieldCn;
	// 	param.Id = _Id;
	// 	param._engineId = _engineId;
	// 	paramFilter.param = param;
	// 	Comm.ajaxPost(
	// 		'datamanage/field/fieldCnAjaxValidate', JSON.stringify(paramFilter),
	// 		function(data){
	// 			if(data > 0){
	// 				//layer.tips("字段中文名已经存在",$("#fieldCName"), { time: 2000 });
	// 				layer.msg("字段中文名已经存在",{time:2000});
	// 				return false;
	// 			}else{
	// 				flag = true;
	// 			}
	// 		},"application/json",	null,false
	// 	);
	// }
	return flag;
}

//字段类型非空校验
function validateFieldType(){
	var _fieldTypeId = $("select[name='fieldTypeId']").val();
	console.log("_fieldTypeId:",_fieldTypeId);
	var flag = false;
	if(_fieldTypeId == ''||_fieldTypeId == null){
		//layer.tips("字 段 范 围不能为空",$("select[name='fieldTypeId']"), { time: 2000 } );
		layer.msg("字 段 范 围不能为空",{time:2000});
		return false;
	}
	else
		flag = true;
	return flag;
}

function validateValueScope(){
	var _valueScope = $("#restrainScope").val();
	var _selValue = $("#valueType").val();
	
	var flag = false;
	//数值型
	if(_selValue=='1'){
		var reg = new RegExp("^(\\(|\\[)(((-?\\d+\\.\\d+|-?\\d+)?,(-?\\d+\\.\\d+|-?\\d+)+)|((-?\\d+\\.\\d+|-?\\d+)+,(-?\\d+\\.\\d+|-?\\d+)?))(\\)|\\])$");
		if(_valueScope == ''){
			//layer.tips("字 段 范 围不能为空",$("#fieldRange"), { time: 2000 });
			layer.msg("字 段 范 围不能为空",{time:2000});
			return false;
		}else if(!reg.test(_valueScope)){
	    	//layer.tips("字 段 范 围不合法，示例：(1,2]",$("#fieldRange"), { time: 2000 });
			layer.msg("字 段 范 围不合法，示例：(1,2]",{time:2000});
			return false;
		}else{
			flag = true;
		}
	}
	//字符型
    if(_selValue=='2'){
    	flag = true;
	}
    //枚举型  -- 男A:1,女B:2
    if(_selValue=='3'){
    	var reg = new RegExp("^([\u4e00-\u9fa5()\\[\\]（）A-Za-z0-9\_\-]+:[0-9]+(,)?)+$");
    	if(_valueScope == ''){
			//layer.tips("字 段 范 围不能为空",$("#fieldRange"), { time: 2000 });
			layer.msg("字 段 范 围不能为空",{time:2000});
			return false;
		}else if(!reg.test(_valueScope)){
	    	//layer.tips("输入不合法，示例：男:1,女:2",$("#fieldRange"), { time: 2000 });
			layer.msg("输入不合法，示例：男:1,女:2",{time:2000});
			return false;
		}else{
			flag = true;
		}
	}
    return flag;
}

function validateFieldCondition(){
	
	var _condFieldValueType = $("input[name= 'condFieldValueType']").val();
	console.log("_condFieldValueType：",_condFieldValueType);

	var _err = 0;
	if(_condFieldValueType=='1'){//数值型
		$(".field-bounced-first").each(function(index,element){
			var _inputOne = $(this).find(".c-bounced-grid").find("input[name='inputOne']").val();
			var reg = new RegExp("^(\\(|\\[)(((-?\\d+\\.\\d+|-?\\d+)?,(-?\\d+\\.\\d+|-?\\d+)+)|((-?\\d+\\.\\d+|-?\\d+)+,(-?\\d+\\.\\d+|-?\\d+)?))(\\)|\\])$");
			if(_inputOne == ''){
				//layer.tips("区间不能为空",$(this).find(".c-bounced-grid"), { time: 2000 });
				layer.msg("区间不能为空",{time:2000});
				//return false;
				_err = _err + 1;
				return false;
			}
			if(!reg.test(_inputOne)){
				//layer.tips("区间不合法",$(this).find(".c-bounced-grid"), { time: 2000 });
				layer.msg("区间不合法",{time:2000});
				_err = _err + 1;
				return false;
			}
		});
		
		if(_err == 0)
			return true;
		else
			return false;
	}else{ //字符型和枚举型不做校验
		return true;
	}
}
