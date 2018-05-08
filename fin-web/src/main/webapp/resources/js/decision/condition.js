var path=$("#getPath").val();
var sign ='<option value="and">且</option>'+
     '<option value="or">或</option>';

var  digitalType = '<option value=">">大于</option>'+
	 '<option value="<">小于</option>'+
	 '<option value=">=">大于等于</option>'+
	 '<option value="<=">小于等于</option>'+
	 '<option value="==">等于</option>'+
	 '<option value="!=">不等于</option>';

var  characterType = '<option value="contains">包含</option>'+
	  '<option value="notContains">不包含</option>'+
	  '<option value="equals">等于</option>'+
	  '<option value="notEquals">不等于</option>';

var  enumerationType ='<option value="==">等于</option>'+
	  '<option value="!=">不等于</option>';
    
var newData,newSubData;

function fillData(valueType,valuescope){
    newData ='<div class="c-inclusion" style="margin: 0px 0 0 0px;">'+
                  '<div class="c-operation-inclusion">'+
    			       '<a href="###"><img src="'+path+'/resources/images/rules/add.png" class="l_addData" style="vertical-align: baseline;"/></a>'+
    			       '<a href="###"><img src="'+path+'/resources/images/rules/delete.png" class="l_delData" style="vertical-align: baseline;"/></a>'+
    			       '<div class="c-field-return">'+
    			          '<input type="hidden" name="conditions" />';
    			          if(valueType == 3){
    			        	  newData +='<select class="l_before" name="result" style="width:60px;  font-size: 12px;">';
    			        	 var array =  valuescope.split(",");  
     					  	 for (var i = 0; i < array.length; i++) { 
     					  		var subArray = array[i].split(":")
     					  		newData +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
     						 }
     					  	 newData +='</select>'
    			          }else{
    			        	  newData +='<input type="text" name="result">';
    			          }
    			       newData += '</div>'+
    		    	   '<div class="c-cut-off-rule"></div>'+
    		        '</div>'+
    			    '<div class="c-optional-rules">';
    			 	newData += fillsubData()
		            newData += '</div>'+
		    '</div>';
} 


function fillsubData(){;
	newSubData = '<div class="c-repetition">'+
    			     '<a href="###"><img src="'+path+'/resources/images/rules/add.png" class="l_addData2" style="vertical-align: text-bottom;"/></a>'+
    			     '<a href="###"><img src="'+path+'/resources/images/rules/delete.png" class="l_delData2" style="vertical-align: text-bottom;"/></a>'+
	                 '<select class="l_before" name ="field_code" style="width:110px;margin:10px 5px 0 0;background-position:90px 0px;"></select>'+
 			          '<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>'+
 		        	  '<select class="l_before" name ="result" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>'+
    		      '</div>';	                   
 	return newSubData;
} 


function getOperator(valueType){
    var operator;
    if(valueType == '1' || valueType == '4'){
         operator ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+digitalType+'</select>';
    }else if(valueType == '2'){
   	     operator ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+characterType+'</select>';   
    }else if(valueType == '3'){
   	     operator ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+enumerationType+'</select>';    
    }else{
    	 operator ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>';    
    }     
    return operator;
}



function getFieldValue(valueType,valuescope){
     var result;
     var array =  valuescope.split(",");  
     result = '<select class="l_before" name ="result" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">';
  	 for (var int = 0; int < array.length; int++) { 
  		    var subArray = array[int].split(":")
  			result +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';   
	 }
     result +='</select>';    
    return result;
}


$("#decision_region .c-field-condition").on("change","select[name='field_code']",function(e){
	e.stopPropagation();
	var field = $(this).find("option:selected").get(0);
	console.log(field);
	render_option(field);
})

$("#decision_region .c-field-condition").on("click",".l_addData",function(e){
	e.stopPropagation();
	var output_valueType = $("#d-option-out input").attr("valueType");
 	var output_valuescope = $("#d-option-out input").attr("valuescope");
	fillData(output_valueType,output_valuescope);
	$("#decision_region .c-field-condition").append(newData);
	$("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition select[name=field_code]").html('').html(getOption());
	var field = $("#decision_region .c-field-condition .c-inclusion:last-child").find('select[name=field_code] option:selected').get(0)
	render_option(field);
});


$("#decision_region .c-field-condition").on("click",".l_addData2",function(e){    	
	e.stopPropagation();
	$(this).parents(".c-optional-rules").append(newSubData);
	if($(this).parents(".c-repetition").find('select[name= sign]').length > 0){
		$(this).parents(".c-optional-rules").find(".c-repetition:last-child").prev().append('<select class="l_before" name ="sign" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+sign+'</select>')        	           	
	}else{
		$(this).parents(".c-repetition").append('<select class="l_before" name ="sign" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+sign+'</select>')
	}
    $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition select[name=field_code]").html('').html(getOption());
    var field = $(this).parents(".c-optional-rules").find(".c-repetition:last-child").find('select[name=field_code] option:selected').get(0)
  	render_option(field); 
});


$("#decision_region .c-field-condition").on("click",".l_delData",function(e){
	e.stopPropagation();
	$(this).parents(".c-inclusion").remove();
});

$("#decision_region .c-field-condition").on("click",".l_delData2",function(e){
	e.stopPropagation();
	$(this).parents(".c-repetition").prev().find("select[name=sign]").remove();
	$(this).parents(".c-repetition").remove();
});

function render_option(field){
    var valueType = $(field).attr("valuetype");
    var valueScope = $(field).attr("valuescope");
    $(field).parent().parent('.c-repetition').find('select[name ="operator"]').html('').html(getOperator(valueType,valueScope))
    if(valueType != '3'){
    	$(field).parent().parent('.c-repetition').find('select[name ="result"]').replaceWith(function(){return '<input type="text" name="result" >'} )
    }else{
    	var obj =$(field).parent().parent('.c-repetition').find('select[name ="result"]');
    	if($(obj).prop("outerHTML") == undefined){
    		$(field).parent().parent('.c-repetition').find('input[name ="result"]').replaceWith(function(){return '<select class="l_before" name ="result" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>'} )
    	}
    	$(field).parent().parent('.c-repetition').find('select[name ="result"]').html('').html(getFieldValue(valueType,valueScope))
    }
}

function getOption(){
    var Obj = $("#d-option").find('input:eq(0)')  
    var option="";
	if($(Obj).val()!=''){
	    var dataId =  $(Obj).attr("dataId");
	    var valueType =  $(Obj).attr("valueType");
	    var valueScope = $(Obj).attr("valueScope");
	    var fieldName =  $(Obj).attr("fieldName");
	    var fieldCode =  $(Obj).attr("fieldCode");
	    option = '<option dataid="'+dataId+'" valueType= "'+valueType+'" valueScope ="'+valueScope+'" value="'+fieldCode+'" >'+fieldName+'</opttion>';
	}
	return option
}


      