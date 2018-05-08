    var path=$("#getPath").val();
    var map = new Map();
    var field_isOutput_array,field_isInput_array,sc_array;
    var show_value;
    var hide=false;
    var variableType; //点击input框时，区分输入输出变量
    var engineId =$("input[name='engineId']").val();
    var nodeid;
    //获取输入、输出变量数组
    function getField(nodeId){
        debugger
        $("#myTabContent1 .c-field-condition").children().remove();
        $(".l_before_input").val("");
        $(".c-field-condition").find("select").children().remove();
        nodeid=nodeId;
        var param={
            "processId":process_id,
            "nodeId":nodeId
        };
        Comm.ajaxPost('decision_flow/getJsonData',JSON.stringify(param),function (result) {
            debugger
            try{
                field_isInput_array = JSON.parse(result.data.data).jcList;
            }catch (e){
                layer.alert("报文格式有误");
                return;
            }

        },"application/json","","","","",false);
        Comm.ajaxPost('decision_flow/getJsonData',JSON.stringify(param),function (result) {
            debugger
            try{
                field_isOutput_array = JSON.parse(result.data.data).jcList;
            }catch (e){
                layer.alert("报文格式有误");
                return;
            }
        },"application/json","","","","",false);
        tagShow();
        init_option();
        if(field_isInput_array!=null){
            debugger
            for (var i = 0; i < field_isInput_array.length; i++) {
                var key = "\@"+field_isInput_array[i].cnName+"\@";
                var value = "\#{"+field_isInput_array[i].enName+"\}";
                map.put(key,value);
            }
        }
    }

    function settingFormula(array){
        $.fn.atwho.debug = true;
        var at_config = {
            at: "@",
            data: array,
            headerTpl: '<div class="atwho-header">Field List<small>↑&nbsp;↓&nbsp;</small></div>',
            limit: 200
        }
        $inputor = $('#inputor').atwho(at_config);
        $(document).on("focus",".inputor",function() {
            $(".inputor").removeAttr("id");
            $(this).attr("id","inputor");
            $inputor = $('#inputor').atwho(at_config);
        });
    }

    function setFomulaField(){
        var array = new Array();
        $("#d-option input").each(function(index,element){
            array.push({"name":$(element).val()})
        })
        settingFormula(array);
    }


    //决策选项点击查看和编辑时执行操作
    function lookOrOperateForDecisionOption(nodeId){
        var _param = {"nodeId":nodeId};
        if(node_2.id!=0){
            Comm.ajaxPost("decision_flow/getNode",_param,function(result){
                var data=result.data;
                if(data!=null){
                    var jsonStr = $.parseJSON(data.nodeJson);
                    if(jsonStr !=null && jsonStr!=''){
                        inputInit(jsonStr);
                        outputInit(jsonStr);
                        if(jsonStr.inputs == 1){
                            if(jsonStr.condition_type == 1){
                                conditionInit_1(jsonStr)
                            }else{
                                $("#decision_region").removeClass("active");
                                $("#decision_region_tag").removeClass("selected_mark")
                                $("#equation_editing_tag").addClass("selected_mark")
                                $("#equation_editing").addClass("active");
                                outputChange(jsonStr.conditions,jsonStr.condition_type);
                                setFomulaField();
                            }
                        }else if(jsonStr.inputs == 2){
                            if(jsonStr.condition_type == 1){
                                conditionInit_2(jsonStr);
                            }else{
                                $("#decision_regionTwo").removeClass("active");
                                $("#equation_editingTwo").addClass("active");
                                $("#decision_regionTwo_tag").removeClass("selected_mark")
                                $("#equation_editingTwo_tag").addClass("selected_mark")
                                outputChange(jsonStr.conditions,jsonStr.condition_type);
                                setFomulaField()
                            }
                        }else{
                            $("#d-option-1").addClass("hidden");
                            $("#d-option-2").addClass("hidden");
                            $("#d-option-3").removeClass("hidden");
                            outputChange(jsonStr.conditions,jsonStr.condition_type);
                            setFomulaField()
                        }
                    }
                }
            })
        }
    }


    $("#d-option").delegate("input.l_before_input","click",function () {
        variableType = 1;
        showOptionPopup(this,1);
    })

    function showOptionPopup(me,sing) {
        var index1=layer.open({
            type : 1,
            title : '输入参数',
            maxmin : true,
            shadeClose : false,
            area : [ '750px', '450px' ],
            content : $('#option_popups'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                var isShowOne=$("#showOne").css("display");
                var isShowTwo=$("#showTwo").css("display");
                var obj = $("#showOne").find("input:checked");
                if(isShowOne=="block"&&obj.length>0){
                    var val=$("#showFields1").html().split("/");
                    var valuetype=$("#valueType1").html();
                    var valuescope=$("#hiddenRestrainScope1").html();
                    var fieldcode=$("#enName1").html();
                    $(me).attr("value",val[1]);
                    $(me).attr("dataid",val[0]);
                    $(me).attr("valuetype",valuetype);
                    $(me).attr("valuescope",valuescope);
                    $(me).attr("fieldname",val[1]);
                    $(me).attr("fieldcode",fieldcode);
                    $(me).addClass("selected");
                    var id = $(obj).attr("value");
                    var valueType = $(obj).attr("valuetype");
                    var valueScope = $(obj).attr("restrainscope");
                    var fieldName = $(obj).attr("cname");
                    var fieldKey = $(obj).attr("enname");
                    // var selectedObj = $(document).find("#ser-value .selected");
                    // $(selectedObj).attr("dataId",$(obj).attr("value"))
                    // $(selectedObj).attr("valueType",$(obj).attr("valuetype"))
                    // $(selectedObj).attr("valueScope",$(obj).attr("restrainscope"))
                    // $(selectedObj).attr("fieldName",$(obj).attr("cname"))
                    // $(selectedObj).attr("fieldCode",$(obj).attr("enname"))
                    // $(selectedObj).attr("value",$(obj).attr("cname"))
                    if(variableType == 1){
                            var  conditionType = getCondition_type();
                            var len = $("#d-option input").length;
                            if( len ==1 && conditionType == 1){
                                var inputHtml = '<option dataid='+id+' valueType= '+valueType+' valueScope ='+valueScope+' value='+fieldKey+' >'+fieldName+'</opttion>';
                                $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition").each(function(index,element){
                                    $(element).find("select[name=field_code]").html("").html(inputHtml);
                                    $(element).find("select[name=field_code]").attr("title",fieldName);
                                    var field = $(element).find("select[name=field_code]").find("option:selected").get(0);
                                    render_option(field);
                                });
                            }
                            if(conditionType == 2 || len >= 3){
                                setFomulaField();
                            }

                            if( len == 2 &&  conditionType == 1){
                                var input_valueType = $("#d-option").find("input:eq(0)").attr("valueType");
                                var input_valuescope = $("#d-option").find("input:eq(0)").attr("valuescope");
                                var input_fieldname = $("#d-option").find("input:eq(0)").attr("fieldname");
                                $(".c-variate-a").text("").text(input_fieldname);
                                if(input_fieldname_1!=''){
                                    $(".c-variate-a").text("").text(input_fieldname);
                                }
                                $("#d-option-2 .c-option-a").find('input,select').replaceWith(getInputOption(input_valueType,input_valuescope))

                                var input_valueType_1 = $("#d-option").find("input:eq(1)").attr("valueType");
                                var input_valuescope_1 = $("#d-option").find("input:eq(1)").attr("valuescope");
                                var input_fieldname_1 = $("#d-option").find("input:eq(1)").attr("fieldname");
                                if(input_fieldname_1!=''){
                                    $(".c-variate-b").text("").text(input_fieldname_1);
                                }
                                $("#des-b").find('input,select').replaceWith(getInputOption(input_valueType_1,input_valuescope_1))
                            }
                        }else{
                        outputChange(null,null);
                    }
                }
                if(isShowTwo=="block"&&obj.length>0){
                    var val=$("#showFields1").html().split("/");
                    var valuetype=$("#valueType1").html();
                    var valuescope=$("#hiddenRestrainScope1").html();
                    var fieldcode=$("#enName1").html();
                    $(me).attr("value",val[1]);
                    $(me).attr("dataid",val[0]);
                    $(me).attr("valuetype",valuetype);
                    $(me).attr("valuescope",valuescope);
                    $(me).attr("fieldname",val[1]);
                    $(me).attr("fieldcode",fieldcode);
                }
                layer.close(index1);
            },
            success:function(){
                $("#showFields1").html("/");
                $("#valueType1").html("");
                $("#hiddenRestrainScope1").html("");
                $("#enName1").html("");
                $("#showFields2").html("/");
                $("#valueType2").html("");
                $("#hiddenRestrainScope2").html("");
                $("#enName2").html("");
                getFields();
            }
        });
    }

    $("#d-option-out input").click(function(){
        variableType = 2;
        showOptionPopup(this,2);
    });

    $("#option_popup #option_sc").click(function(){
        $(this).addClass("active");
        $("#option_popup #option_fd").removeClass("active");
        showOptionFieldsList()
    })

    $("#option_popup #option_fd").click(function(){
        $(this).addClass("active");
        $("#option_popup #option_sc").removeClass("active");
        showOptionFieldsList()
    })

    //选择字段
    function showOptionFieldsList(){
        var op_type,isoutput;
        if($("#option_popup #option_sc").hasClass("active")){
            op_type = 1;
        }else{
            op_type = 2;
        }
        if(variableType == 1){
            isOutput = 0
        }else{
            isOutput = 1
        }
        var _param = new Object();
        var engineId = $("input[name='engineId']").val();
        _param.opType = op_type;
        _param.nodeId = node_2.id;
        _param.engineId = engineId;
        _param.isOutput = isOutput;
        var _url ="${ctx}/decision_flow/getFieldOrScorecardForOption";
        $.ajax({
            url:_url,
            type:"POST",
            data:_param,
            async:false,
            success:function(data){
                $("#option_popup_content").html('').html(data);
                autoCenter($("#option_popup"));
                autoCenter($("#option_dialog"));
            }
        })
    }

    function  init_option(){
        debugger
        var output_valueType = $("#d-option-out input").attr("valueType");
        var output_valuescope = $("#d-option-out input").attr("valuescope");
        fillData(output_valueType,output_valuescope);
        $("#decision_region .c-field-condition").append(newData);
        var field = $("#decision_region  .c-field-condition .c-optional-rules").eq(0).find('select[name=field_code] option:first').get(0);
        render_option(field);
    }

    //切换条件区域内容
    function tagShow(){
        debugger
        var len = $("#d-option").find('input').length;
        //清空数组
        if(len == 1){  //一个输入变量时
            $("#d-option-1").removeClass("hidden");
            $("#d-option-2").addClass("hidden");
            $("#d-option-3").addClass("hidden");
            $("#decision_region_tag").addClass("selected_mark");
            $("#equation_editing_tag").removeClass("selected_mark");
            $("#decision_region").addClass("active");
            $("#equation_editing").removeClass("active");
        }else if(len == 2){ //两个输入变量时
            $("#d-option-1").addClass("hidden");
            $("#d-option-3").addClass("hidden");
            $("#d-option-2").removeClass("hidden").show();
            if($("#des-b .c-option-d").length <= 0){
                addOption_B();
            }
            if($(".c-option-a .c-front-portion").length <= 0){
                addOption_A();
            }
            $("#decision_regionTwo_tag").addClass("selected_mark");
            $("#equation_editingTwo_tag").removeClass("selected_mark");
            $("#decision_regionTwo").addClass("active");
            $("#equation_editingTwo").removeClass("active");
        }else{ //三或三个以上输入变量时
            $("#d-option-1").addClass("hidden");
            $("#d-option-2").addClass("hidden");
            $("#d-option-3").removeClass("hidden").show();
        }
        outputChange(null,null);
        hide=false;
    }

    //删除输入变量
    $("#delete_option").click(function(){
        $("#d-option .l_selects .select_option:last-child").remove();
        tagShow();
    })

    //添加输入变量
    $("#add_option").unbind('click').click(function(){
        var str ='<div class="select_option" style="width:130px; float:left; margin-top:10px;"><input  data="" class="l_before_input input_variable"  style="width:124px;background-position:105px 0px;">'+
            '</div>';
        $(".l_selects").append(str);
        $(".l_selects .select_option:last-child input").html('').html(getOption());
        $("#d-option input").click(function(){
            variableType = 1;
            $("#ser-value").find('input').removeClass("selected");
            $("#option_popup").show();
            $(this).addClass("selected")
            $("#option_popup #option_fd").click();
            var text = $(this).val();
            $(".c-createusers-content input[type=radio][fieldName='"+text+"']").click();
        });
        tagShow();
    })


    $("#decision_region_tag").click(function(){
        $(this).addClass("selected_mark")
        $("#equation_editing_tag").removeClass("selected_mark");
        outputChange(null,null);
    })

    $("#equation_editing_tag").click(function(){
        $(this).addClass("selected_mark")
        $("#decision_region_tag").removeClass("selected_mark");
        outputChange(null,null);
    })

    $("#decision_regionTwo_tag").click(function(){
        $(this).addClass("selected_mark")
        $("#equation_editingTwo_tag").removeClass("selected_mark");
        outputChange(null,null);
    })

    $("#equation_editingTwo_tag").click(function(){
        $(this).addClass("selected_mark");
        $("#decision_regionTwo_tag").removeClass("selected_mark");
        outputChange(null,null);
    })


    $(".shows").click(function(){
        if(hide==false){
            $(this).parent().next().show();
            $(this).parent().parent().css("height","265px");
            hide = true;
        }else{
            $(this).parent().next().hide();
            $(this).parent().parent().css("height","220px");
            hide = false;
        }
    })

    function outputChange(show_value,condition_type){
        if(condition_type == null){
            condition_type = getCondition_type();
        }
        var len = $("#d-option").find('input').length;
        var output_valueType = $("#d-option-out input").attr("valueType");
        var output_valuescope = $("#d-option-out input").attr("valuescope");
        if(condition_type == 2){
            setFomulaField()
            if(len == 1 ){
                $("#d-option-1").find('.includeFormula').html('').html(addFormulas(output_valueType,output_valuescope,show_value));
            }else if(len == 2){
                $("#d-option-2").find('.includeFormula').html('').html(addFormulas(output_valueType,output_valuescope,show_value));
            }else if(len >= 3){
                $("#d-option-3").find('.includeFormula').html('').html(addFormulas(output_valueType,output_valuescope,show_value));
            }
            setFomulaField();
        }else{
            if(len == 1){
                varChange(output_valuescope,output_valueType);
            }else if(len == 2){
                $(".c-variate-a").text("").text($("#d-option").find('input').eq(0).val());
                $(".c-variate-b").text("").text($("#d-option").find('input').eq(1).val());
                $("#d-option-2 .c-option-c").find('input,select').replaceWith(getOutputOption())
            }
        }
    }




    //公式部分添加输出
    function addFormulas(output_valueType,output_valuescope,show_value){
        var newFormulas="";
        if(output_valueType == 3){
            var array =  output_valuescope.split(",");
            for (var i = 0; i < array.length; i++) {
                var subArray = array[i].split(":")
                newFormulas +='<div class="formulas" style="overflow:hidden; height: 32px; float:left; margin-top:20px;">'+
                    '<div class="leftOption" style="width: 200px; height:30px; float:left;">'+
                    '<input type="text" data = "'+subArray[1]+'" value="'+subArray[0]+'" name = "result" style="width: 100px; font-size:12px; margin-left:20px;margin-right:10px;height:30px;border: 1px solid #e6e6e6;border-radius: 4px;text-align: center"/>'+
                    '<span style="margin-top:35px;margin-left:14px;">=</span>'+
                    '</div>'+
                    '<div class="textArea" style="width: 270px; height: 30px; float:left;">';
                if(show_value!=null){
                    for (var j = 0; j < show_value.length; j++) {
                        if(subArray[1] == show_value[j].result){
                            newFormulas +='<textarea id="inputor" name="formula" class="inputor" style="width: 260px; height: 30px; line-height:28px; resize:none;">'+show_value[j].formula_show+'</textarea>';
                        }
                    }
                }else{
                    newFormulas +='<textarea id="inputor" name="formula" class="inputor" style="width: 260px; height: 30px; line-height:28px; resize:none;"></textarea>';
                }
                newFormulas +='</div></div>';
            }
        }else{
            newFormulas +='<div class="formulas" style="overflow:hidden; height: 32px; float:left; margin-top:20px;">'+
                '<input type="hidden" value="" name = "result" style="width: 100px; margin-left:20px;margin-right:10px;height:30px;border: 1px solid #e6e6e6;border-radius: 4px;text-align: center"/>';
            if(show_value!=null){
                newFormulas +='<textarea id="inputor" name="formula" class="inputor" style=" margin-left:20px; width: 550px; height:170px;border:none; line-height:25px; resize:none;">'+show_value[0].formula_show+'</textarea>';
            }else{
                newFormulas +='<textarea id="inputor" name="formula" class="inputor" style=" margin-left:20px; width: 550px; height:170px;border:none; line-height:25px; resize:none;"></textarea>';
            }
            newFormulas +='</div>';
        }
        return newFormulas;
    }

    //公式
    function fieldsetting(){
        var text = subtext3
        layer.closeAll('page');
        layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px', '400px'], //宽高
            content:'<div class="field-bounced-content">'+
            '<div class="field-bounced-section">'+
            '</div>'+
            '<div class="field-bounced-table">'+
            '<table>'+
            '<tr>'+
            '<td>区间</td>'+
            '<td>值</td>'+
            '<td>操作</td>'+
            '</tr>'+
            '</table>'+
            '</div>'+
            '</div>'+
            '<span id="checkSpan" style="margin:10px 0 0 23px;color:red;display: inline-block;"></span>'+
            '<div align="center" style="margin-top:30px;" class="areaButton">'+
            '<button id="interval_sumbit">确定</button>'+
            '<button id="interval_rencel" style="margin-left:20px;">取消</button>'+
            '</div>'
        });

        var valueType,valueScope,fieldCode,intervalJson;
        $("#d-option input").each(function(index,element){
            var fieldName = $(element).val()
            if(text == fieldName){
                valueType = $(element).attr("valueType");
                valueScope = $(element).attr("valueScope");
                fieldCode = $(element).attr("value")
                intervalJson = $(element).attr("data")
            }
        })

        if(intervalJson == ""){
            $(".field-bounced-content").append(addInterval_1(valueType,valueScope,fieldCode));
        }else{
            $(".field-bounced-content").append(addInterval(valueType,valueScope,fieldCode,intervalJson));
        }

        //字段编辑动态
        $(document).unbind('click').on("click",".add_interval",function(e){
            e.stopPropagation();
            $(".field-bounced-content").append(addInterval_1(valueType,valueScope,fieldCode));
        })

        $(document).on("click",".delete_interval",function(e){
            e.stopPropagation();
            $(e.target).parent().parent().remove();
        })

        var formula_json = new Array();


        //字段编辑弹窗确定时执行
        $("#interval_sumbit").click(function(){
            var intervalArray = new Array();
            $(".field-bounced-content").find(".field-bounced-first").each(function(index,element){
                var content ='{'
                if($(element).find('select[name=segment] option:selected').prop("outerHTML") != undefined){
                    content += '"segment":"'+$(element).find('select[name=segment] option:selected').val();
                    intervalArray.push($(element).find('select[name=segment] option:selected').text())
                    content += '","segmentKey":"'+$(element).find('select[name=segment] option:selected').text();
                }else{
                    content += '"segment":"'+$(element).find('input[name=segment]').val();
                    intervalArray.push($(element).find('input[name=segment]').val())
                }
                content +='","value":"'+$(element).find('input[name=value]').val();
                content +='"}'
                formula_json.push(content);
            })
            intervalArray=intervalArray.sort();
            for(var i=0;i<intervalArray.length;i++){
                if (intervalArray[i] == intervalArray[i+1]){
                    $("#checkSpan").text("").text("区间有重叠,请核准!");
                    return ;
                }
            }

            if(valueType == 1){
                var flag = false;
                $.ajax({
                    url:"${ctx}/common/validate/section",
                    async:false,
                    type:"POST",
                    data :{"sections":intervalArray},
                    dataType:'json',
                    success:function(data){
                        if(data.result == -1){
                            $("#checkSpan").text("").text(data.msg);
                            flag = true
                        }
                    }
                })
                if(flag){
                    return;
                }
            }


            $("#d-option input").each(function(index,element){
                if($(element).val() == text){
                    $(element).attr("data","");
                    $(element).attr("data",formula_json)
                }
            })
            layer.closeAll('page');
        })

        //字段编辑弹窗点击取消时执行
        $("#interval_rencel").click(function(){
            layer.closeAll('page');
        })
    }

    function addInterval(valueType,valueScope,fieldCode,intervalJson){
        var jsonStr = "["+intervalJson+"]";
        var jsonArray = JSON.parse(jsonStr)
        var str ="";
        if(jsonArray.length>0){
            for (var i = 0; i < jsonArray.length; i++) {
                str +='<div class="field-bounced-first">'+
                    '<div class="c-bounced-grid">';
                if(valueType == 3){
                    str +='<select name="segment" class="l_before" style="width:80px;height:22px;margin: 5px 0 0 5px">';
                    var array =  valueScope.split(",");
                    for (var j = 0; j < array.length; j++) {
                        var subArray = array[j].split(":")
                        if(jsonArray[i].segment == subArray[1]){
                            str +='<option selected="selected" value="'+subArray[1]+'">'+subArray[0]+'</option>'
                        }else{
                            str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>'
                        }

                    }
                    str +='<select>'
                }else{
                    str +='<input class="c-inputOne" name="segment" value="'+jsonArray[i].segment+'" type="text"/>';
                }
                str+='</div>'+
                    '<div class="c-bounced-grid">'+
                    '<input class="c-inputThree" name="value" value="'+jsonArray[i].value+'" type="text"/>'+
                    '</div>'+
                    '<div class="c-bounced-grid">'+
                    '<img src='+path+'/resources/images/rules/add.png class="add_interval"/>'+
                    '<img src='+path+'/resources/images/rules/delete.png style="margin-left: 16px;"  class="delete_interval"/>'+
                    '</div>'+
                    '</div>';
            }
        }
        return str;
    }


    function addInterval_1(valueType,valueScope,fieldCode){
        var str=""
        str +='<div class="field-bounced-first">'+
            '<div class="c-bounced-grid">';
        if(valueType == 3){
            str +='<select name="segment" class="l_before" style="width:80px;height:22px;margin: 5px 0 0 5px">';
            var array =  valueScope.split(",");
            for (var j = 0; j < array.length; j++) {
                var subArray = array[j].split(":")
                str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>'
            }
            str +='<select>'
        }else{
            str +='<input class="c-inputOne" name="segment" value="" type="text"/>';
        }
        str+='</div>'+
            '<div class="c-bounced-grid">'+
            '<input class="c-inputThree" name="value" value="" type="text"/>'+
            '</div>'+
            '<div class="c-bounced-grid">'+
            '<img src='+path+'/resources/images/rules/add.png class="add_interval"/>'+
            '<img src='+path+'/resources/images/rules/delete.png style="margin-left: 16px;"  class="delete_interval"/>'+
            '</div>'+
            '</div>';
        return str;
    }

    //公式区域的添加与删除
    var heights=150;

    //删除
    $(document).on("click",".formulaRemove",function(){
        $(this).parents(".formulas").remove();
    });

    function varChange(valuescope,valueType){
        var str="";
        if(valueType == 3){
            var array =  valuescope.split(",");
            for (var i = 0; i < array.length; i++) {
                var subArray = array[i].split(":")
                str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
            }
            var obj = $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return select[name=result]")
            if(obj.length > 0){
                $(obj).html('').html(str);
            }else{
                var html= '<select class="l_before" name="result" style="width:60px">'+
                    str+
                    '</select>';
                $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return input[name=result]").replaceWith(html);
            }
        }else{
            var obj = $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return input[name=result]")
            if(obj.length > 0){
                $(obj).val();
            }else{
                var html = '<input type="text" name="result">'
                $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return select[name=result]").replaceWith(html);
            }
        }
        $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition select[name=field_code]").html('').html(getOption());
        var field =  $("#decision_region .c-field-condition").find(".c-inclusion:eq(0)").find(".c-optional-rules").find(".c-repetition:last-child").find('select[name=field_code] option:selected').get(0);
        render_option(field);
    }

    function addOption_A(){
        var input_valueType = $("#d-option").find("input:eq(0)").attr("valueType");
        var input_valuescope = $("#d-option").find("input:eq(0)").attr("valuescope")
        var str='<div class="c-front-portion" style="padding-top:5px;">'+
            '<img src='+path+'/resources/images/rules/add.png  class="l_op_a_addData"/>'+
            '<img src='+path+'/resources/images/rules/delete.png style="margin:0 3px 0 10px;"  class="l_op_a_delData"/>'+
            getInputOption(input_valueType,input_valuescope)+'</div>';
        $("#d-option-2 .c-option-a").append(str)
        var len = $(".c-option-d").length;
        var str1 ="";
        var output_option = getOutputOption();
        str1 +='<div class="c-queen-portion" style="padding-left:78px;">';
        for (var int = len; int > 0; int--) {
            str1  +='<div class="c-option-e">'+
                '<div class="turn-down">'+
                output_option+
                '</div>'+
                '</div>';
        }
        str1 += '</div>';
        $("#d-option-2 .c-option-c").append(str1);
    }


    function getInputOption(input_valueType,input_valuescope){
        var input_option="";
        if(input_valueType != 3){
            input_option +='<input type="text" name="input"/>'
        }else{
            var array =  input_valuescope.split(",");
            input_option += '<select name="input" class="l_before_option">';
            for (var i = 0; i < array.length; i++) {
                var subArray = array[i].split(":")
                input_option +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
            }
            input_option +='</select>';
        }
        return input_option;
    }


    function getOutputOption(){
        var output_valueType = $("#d-option-out").find("input:eq(0)").attr("valueType");
        var output_valuescope = $("#d-option-out").find("input:eq(0)").attr("valuescope")
        var out_option = "";
        if(output_valueType != 3){
            out_option +='<input type="text" name="result"  />'
        }else{
            var array =  output_valuescope.split(",");
            out_option +='<select name="result" class="l_before_option">'
            for (var i = 0; i < array.length; i++) {
                var subArray = array[i].split(":")
                out_option +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
            }
            out_option +="</select>"
        }
        return out_option;
    }


    $("#d-option-2").on('click','.c-option-a .l_op_a_addData',function(){
        addOption_A();
    })

    $("#d-option-2").on('click','.c-option-a .l_op_a_delData',function(){
        var index = $(this).parent().index();
        $(this).parent().remove();
        $("#d-option-2 .c-option-c").find('.c-queen-portion').eq(index).remove();
    })


    $("#d-option-2").on('click','#des-b .l_op_b_addData',function(){
        addOption_B()
        var output_valueType = $("#d-option-out input").attr("valueType");
        var output_valuescope = $("#d-option-out input").attr("valuescope");
        //$("#d-option-2 .c-option-c").find('input,select').replaceWith(getOutputOption())
    })

    function addOption_B(){
        var input_valueType = $("#d-option").find("input:eq(1)").attr("valueType");
        var input_valuescope = $("#d-option").find("input:eq(1)").attr("valuescope");
        var str = '<div class="c-option-d">'+
            '<div class="c-section-right" style="padding-left:5px; font-family: sans-serif; font-size:14px;">'+
            getInputOption(input_valueType,input_valuescope)+
            '</div>'+
            '</div>';
        $("#des-b").append(str);

        var str1 ='<div class="c-option-e">'+
            '<div class="turn-down">'+
            getOutputOption()+
            '</div>'+
            '</div>';
        $(".c-option-c .c-queen-portion").append(str1);
    }

    $("#d-option-2").on('click','#des-b .l_op_b_delData',function(){
        $("#des-b").find('.c-option-d:last-child').remove();
        $(".c-option-c .c-queen-portion").each(function(index,element){
            $(element).find('.c-option-e:last-child').remove();
        });
    })

    //取消
    $("#op_close").unbind('click').on('click',function(e){
        dialog.hide();
        $(".bigCircle").hide();
        var inputs  = $("#d-option").find("select").length;
        var condition_type;
        if(inputs == 1){
            if(!$("#decision_region_tag").hasClass("selected_mark")){

            }else{
                $("#d-option-1 .c-field-condition").html('');
                init_option();
            }
        }
    })

    //确定提交
    $("#op_submit").click(function(){
        var initEngineVersionId =$("input[name=initEngineVersionId]").val();
        var nodeId = node_2.id;
        var _param = new Object();
        _param.initEngineVersionId =initEngineVersionId;
        _param.id =nodeId;
        _param.nodeX = node_x;
        _param.nodeY = node_y;
        _param.nodeName = node_name;
        _param.nodeCode = node_code;
        _param.nodeType = node_type;
        _param.nodeOrder = node_order;
        var inputs  = $("#d-option").find("input").length;
        var condition_type = getCondition_type();
        var input ="[";
        $("#d-option").find("input").each(function(index,element){
            var obj = $(this);
            input += '{"field_id":'+$(obj).attr("dataId");
            var code = $(obj).attr("fieldCode");
            if(code.indexOf("SC_") > -1){
                code = code+"_score";
            }
            input += ',"field_code":"'+code;
            input += '","field_name":"'+$(obj).attr("value");
            if(condition_type == 2){
                input +='","segemnts":['+$(element).attr("data")+']'
            }else{
                input += '"';
            }
            input += ',"field_scope":"'+$(obj).attr("valueScope");
            input += '","field_type":'+$(obj).attr("valueType")+'}';
            input +=","
        })
        input = input.substring(0,input.length -1);
        input +="]"
        var outObj = $("#d-option-out").find('input');
        var output = '{"field_id":'+$(outObj).attr("dataId")+',"field_code":"'+$(outObj).attr("fieldCode")+'","field_name":"'+$(outObj).attr("value")+'","field_type":'+$(outObj).attr("valueType")+',"field_scope":"'+$(outObj).attr("valueScope")+'"}'
        var str = "["
        if(inputs == 1){
            if(condition_type == 1){
                str += assembledJson_1();
            }else{
                str += getFormulaJson("d-option-1");
            }
        }else if(inputs == 2){
            if(condition_type == 1){
                str += assembledJson_2();
            }else{
                str += getFormulaJson("d-option-2");
            }
        }else{
            str += getFormulaJson("d-option-3");
        }
        str +="]"
        _param.params = '{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'"}';
        _param.nodeJson ='{"inputs":'+inputs+',"condition_type":'+condition_type+',"input":'+input+',"output":'+output+',"conditions":'+str+'}';
        console.log(_param);
        var _url;
        if(nodeId !=0 ){
            _url ="${ctx}/decision_flow/update";
        }else{
            _url ="${ctx}/decision_flow/save";
        }
        $.post(_url,_param,function(data){
            if(data.result == '1'){
                node_2.id = data.nodeId;
                dialog.hide();
                $(".bigCircle").hide();
            }
        })
    })

    function getCondition_type(){
        var inputs  = $("#d-option").find("input").length;
        var condition_type;
        if(inputs == 1){
            if($("#decision_region_tag").hasClass("selected_mark")){
                condition_type = 1; //公式编辑
            }else{
                condition_type = 2 ;  //决策区域
            }
        }else if(inputs == 2){
            if($("#decision_regionTwo_tag").hasClass("selected_mark")){
                condition_type = 1; //公式编辑
            }else{
                condition_type = 2 ;  //决策区域
            }
        }else if(inputs == 3){
            condition_type = 2; //公式编辑
        }
        return condition_type;
    }

    //公式编辑json拼接
    function getFormulaJson(obj){
        var str="";
        $("#"+obj).find(".includeFormula .formulas").each(function(index,element){
            if($("#d-option-out").find("input").attr("valueType") == 3){
                str += '{"result":"'+$(element).find("input[name=result]").attr("data")

                str += '","resultKey":"'+$(element).find("input[name=result]").val();
            }else{
                str += '{"result":"'+$(element).find("input[name=result]").val()
            }
            var formula = $(element).find(".inputor").val();
            str += '","formula":"'+replaceField(formula.replace(" ",""));
            str += '","formula_show":"'+formula;
            str += '"},'
        })
        str = str.substring(0,str.length -1);
        return str;
    }


    function replaceField(str){
        console.log(str);
        var reg=/@[a-zA-Z0-9_\u4e00-\u9fa5()（）-]+@/;
        var patt = new RegExp(reg, 'g');
        var arr0=str.match(patt);
        if(arr0 !=null && arr0.length>0){
            for (var i=0;i<arr0.length;i++) {
                str=str.replace(reg,map.get(arr0[i]));
            }
        }
        return str;
    }


    //输入变量只有一个时的json拼装
    function assembledJson_1(){
        var str="";
        $("#decision_region .c-field-condition .c-inclusion").each(function(index_1,element_1){
            var substr ="["
            $(element_1).find(".c-optional-rules .c-repetition").each(function(index,element){
                var code = $(element).find('select[name=field_code] option:selected').val();
                if(code.indexOf("SC_") > -1){
                    code = code+"_score";
                }
                var content  = '{"field_code":"'+code;
                content  += '","operator":"'+$(element).find('select[name=operator] option:selected').val();
                if($(element).find('select[name=field_code] option:selected').attr("valueType") == 3){
                    content  += '","result":"'+$(element).find('select[name=result] option:selected').val();
                    content  += '","resultKey":"'+$(element).find('select[name=result] option:selected').text();
                }else{
                    content  += '","result":"'+$(element).find('input[name=result]').val();
                    content  += '","resultKey":"'+$(element).find('input[name=result]').val();
                }
                if(index == 0){
                    content  += '"}';
                }else{
                    content  += '","sign":"'+$(element).prev().find('select[name=sign] option:selected').val()+'"}';
                }
                console.log(content);
                substr +=content+","
            })
            substr = substr.substring(0,substr.length -1);
            substr = substr +"]"
            var subContent=""
            if($("#d-option-out").find('input').attr("valueType") == 3){
                var result=$(element_1).find('.c-operation-inclusion select[name="result"] option:selected').val();
                var resultKey=$(element_1).find('.c-operation-inclusion select[name="result"] option:selected').text();
                subContent = '{"result":"'+result+'","resultKey":"'+resultKey+'","formula":'+substr+'}'
            }else{
                var result = $(element_1).find('.c-operation-inclusion input[name="result"]').val();
                var resultKey=$(element_1).find('.c-operation-inclusion input[name="result"]').val();
                subContent = '{"result":"'+result+'","resultKey":"'+resultKey+'","formula":'+substr+'}'
            }
            str +=subContent+","
        })
        str = str.substring(0,str.length -1);
        return str;
    }

    //输入变量有两个时的json拼装
    function assembledJson_2(){
        var input_code_1 = $("#d-option").find("input:eq(0)").attr("fieldCode");
        if(input_code_1.indexOf("SC_") > -1){
            input_code_1 = input_code_1+"_score";
        }
        var input_valueType_1 = $("#d-option").find("input:eq(0)").attr("valueType");
        var input_code_2 = $("#d-option").find("input:eq(1)").attr("fieldCode");
        if(input_code_2.indexOf("SC_") > -1){
            input_code_2 = input_code_2+"_score";
        }
        var input_valueType_2 = $("#d-option").find("input:eq(1)").attr("valueType");
        var output_valueType = $("#d-option-out").find("input:eq(0)").attr("valueType");
        var str = "";
        $("#d-option-2").find(".c-option-a .c-front-portion").each(function(index,element){
            $("#des-b .c-option-d").each(function(index_1,element_1){
                if(output_valueType == 3){
                    str += '{"result":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("select[name=result] option:selected").val();
                    str += '","resultKey":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("select[name=result] option:selected").text()
                }else{
                    str += '{"result":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("input[name=result]").val();
                    str += '","resultKey":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("input[name=result]").val()
                }
                str += '","index":"'+index+','+index_1
                str += '","formula":{"field_code1":"'+input_code_1;
                str += '","expression1":"'

                if(input_valueType_1 == 3){
                    str += $(element).find("select[name='input'] option:selected").val()
                    str += '","expression1Key":"'+$(element).find("select[name='input'] option:selected").text()
                }else{
                    str += $(element).find("input[name='input']").val()
                }
                str +='","field_code2":"'+input_code_2;
                str += '","expression2":"'
                if(input_valueType_2 == 3){
                    str += $(element_1).find("select[name='input'] option:selected").val()
                    str += '","expression2Key":"'+$(element).find("select[name='input'] option:selected").text()
                }else{
                    str += $(element_1).find("input[name='input']").val()
                }
                str +='"}},'
            });
        })
        str = str.substring(0,str.length -1);
        return str;
    }



    //输入变量回显初始化
    function inputInit(jsonStr){
        var inputArray = jsonStr.input;
        for (var i = 0; i < inputArray.length; i++) {
            var segemnts="";
            if(jsonStr.condition_type == 2){
                segemnts = JSON.stringify(inputArray[i].segemnts);
                segemnts = segemnts.substring(1,segemnts.length-1)
            }

            var  code = inputArray[i].field_code;
            if(code.indexOf("_score") > -1){
                code = code.substring(0,code.lastIndexOf("_score"));
            }

            if(i == 0){
                var selectedObj =  $("#d-option").find('input:eq(0)')
                $(selectedObj).attr("data",segemnts);
                $(selectedObj).attr("dataId",inputArray[i].field_id);
                $(selectedObj).attr("valueType",inputArray[i].field_type);
                $(selectedObj).attr("valueScope",inputArray[i].field_scope);
                $(selectedObj).attr("fieldName",inputArray[i].field_name);
                $(selectedObj).attr("fieldCode",code);
                $(selectedObj).attr("value",inputArray[i].field_name);
            }else{
                var str = '<div class="select_option" style="width:130px; float:left; margin-top:10px;">'+
                    '<input fieldName="'+inputArray[i].field_name+'"  dataId="'+inputArray[i].field_id+'" valueType="'+inputArray[i].field_type+'" value="'+inputArray[i].field_name+'" valueScope="'+inputArray[i].field_scope+'" fieldCode="'+code+'" class="l_before_input input_variable"';
                if(segemnts !=''){
                    str +='data='+segemnts
                }else{
                    str +='data=""';
                }
                str +='  style="width:124px;background-position:100px 0px;">'+
                    '</input></div>';
                $(".l_selects").append(str);
                $("#d-option input").click(function(){
                    variableType = 1;
                    $("#ser-value").find('input').removeClass("selected");
                    $("#option_popup").show()
                    $(this).addClass("selected")
                    $("#option_popup #option_fd").click();
                    var text = $(this).val();
                    $(".c-createusers-content input[type=radio][fieldName='"+text+"']").click();
                });
            }
        }
        tagShow();
    }

    //输出变量回显初始化
    function outputInit(jsonStr){
        var obj =jsonStr.output;
        var selectedObj = $("#d-option-out").find('input:eq(0)');
        $(selectedObj).attr("dataId",obj.field_id);
        $(selectedObj).attr("valueType",obj.field_type);
        $(selectedObj).attr("valueScope",obj.field_scope);
        $(selectedObj).attr("fieldName",obj.field_name)
        $(selectedObj).attr("fieldCode",obj.field_code)
        $(selectedObj).attr("value",obj.field_name)
    }

    //当输入变量只有一个时条件区域回显初始化
    function conditionInit_1(jsonStr){
        $("#decision_region .c-field-condition .c-inclusion:eq(0)").find("select").html('').find('input').val('');
        $("#decision_region .c-field-condition .c-inclusion:gt(0)").remove();
        var inputArray = jsonStr.input;
        var output  = jsonStr.output;
        var conditions = jsonStr.conditions;
        var inputhtml = '<option dataid="'+inputArray[0].field_id+'" valueType= "'+inputArray[0].field_type+'" valueScope ="'+inputArray[0].field_scope+'" value="'+inputArray[0].field_code+'" >'+inputArray[0].field_name+'</opttion>';
        for (var i = 0; i < conditions.length; i++) {
            var obj = $("#decision_region .c-field-condition .c-inclusion:last-child");
            if(output.field_type == 3){
                var obj_1 =$(obj).parent().parent('.c-repetition').find('select[name ="result"]');
                if($(obj_1).prop("outerHTML") == undefined){
                    $(obj).find('.c-operation-inclusion').find('input[name ="result"]').replaceWith(function(){
                        var str=""
                        var array =  output.field_scope.split(",");
                        for (var i = 0; i < array.length; i++) {
                            var subArray = array[i].split(":")
                            str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
                        }
                        return '<select class="l_before" name="result" style="width:60px" value="1">'+str+'</select>'})
                }
                $(obj).find(".c-operation-inclusion select[name=result] option[value="+conditions[i].result+"]").attr("selected","selected");
            }else{
                $(obj).find(".c-operation-inclusion input[name=result]").val(conditions[i].result);
            }
            if(i != conditions.length-1){
                $(obj).find(".c-operation-inclusion .l_addData").click();
            }
            var fieldArray = conditions[i].formula;
            for (var int2 = 0; int2 < fieldArray.length; int2++) {
                var subObj = $(obj).find(".c-optional-rules .c-repetition:last-child");
                $(subObj).find('select[name=field_code]').html('').html(inputhtml);
                render_option($(subObj).find('select[name=field_code] option:eq(0)').get(0))
                $(subObj).find('select[name=operator] option[value="'+fieldArray[int2].operator+'"]').attr("selected", "selected");
                if(inputArray[0].field_type == 3){
                    $(subObj).find('select[name=result] option[value="'+fieldArray[int2].result+'"]').attr("selected", "selected");
                } else{
                    $(subObj).find('input[name=result]').val(fieldArray[int2].result);
                }
                if(int2 != fieldArray.length-1){
                    $(subObj).find('.l_addData2').click();
                }
                if(int2 != 0){
                    $(subObj).prev().find('select[name=sign] option[value="'+fieldArray[int2].sign+'"]').attr("selected", "selected");
                }
            }
        }
    }

    //当输入变量有两个时条件区域回显初始化
    function conditionInit_2(jsonStr){
        var val;
        var output = jsonStr.output;
        var input = jsonStr.input;
        var conditions = jsonStr.conditions;
        $(".c-variate-a").text("").text(input[0].field_name);
        $(".c-variate-b").text("").text(input[1].field_name);
        for (var i = 0; i < conditions.length; i++) {
            var obj = conditions[i];
            var index = obj.index;
            var formula = obj.formula;
            var array = index.split(",");

            var input_1 = $("#d-option-2").find(".c-option-a .c-front-portion:eq("+parseInt(array[0])+")");
            if($(input_1).length == 0){
                $("#d-option-2 .c-option-a .c-front-portion:first-child .l_op_a_addData").click();
                input_1 = $("#d-option-2").find(".c-option-a .c-front-portion:eq("+parseInt(array[0])+")");
            }
            if(input[0].field_type == 3 ){
                $(input_1).find('select option[value='+formula.expression1+']').attr('selected','selected');
            }else{
                $(input_1).find("input").val(formula.expression1);
            }

            var input_2 = $("#des-b .c-option-d:eq("+parseInt(array[1])+")");
            if($(input_2).length == 0){
                $("#des-b .l_op_b_addData").click();
                input_2 = $("#des-b .c-option-d:eq("+parseInt(array[1])+")")
            }

            if(input[1].field_type == 3 ){
                $(input_2).find("select option[value="+formula.expression2+"]").attr("selected","selected");
            }else{
                $(input_2).find("input").val(formula.expression2);
            }

            if(output.field_type == 3){
                $(".c-option-c .c-queen-portion:eq("+parseInt(array[0])+")").find(".c-option-e:eq("+parseInt(array[1])+")").find("select option[value="+obj.result+"]").attr("selected","selected");
            }else{
                $(".c-option-c .c-queen-portion:eq("+parseInt(array[0])+")").find(".c-option-e:eq("+parseInt(array[1])+")").find("input").val(obj.result);
            }

            if(obj.index == '0,0'){
                val =obj.result
            }
        }

        if(output.field_type == 3){
            $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find('input,select').replaceWith(getOutputOption())
            $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find("select option[value="+val+"]").attr("selected","selected");
        }else{
            $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find('input,select').replaceWith(getOutputOption())
            $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find("input").val(val);
        }
    }


    //字段输入数据查询条件
    var g_fieldManage = {
        tableUser : null,
        currentItem : null,
        fuzzySearch : false,
        getQueryCondition : function(data) {
            var paramFilter = {};
            var page = {};
            var param = {};
            param.engineId = $("input[name='engineId']").val();
            param.opType=2;
            if(variableType==1){
                param.isOutput=0;
            }else{
                param.isOutput=1;
            }
            param.nodeId=nodeid;
            //自行处理查询参数
            param.fuzzySearch = g_fieldManage.fuzzySearch;
            if (g_fieldManage.fuzzySearch) {

            }

            paramFilter.param = param;
            page.firstIndex = data.start == null ? 0 : data.start;
            page.pageSize = data.length  == null ? 10 : data.length;
            paramFilter.page = page;
            return paramFilter;
        }
    };
    //评分输入卡数据查询条件
    var g_scoresssManage = {
        tableUser : null,
        currentItem : null,
        fuzzySearch : false,
        getQueryCondition : function(data) {
            var paramFilter = {};
            var page = {};
            var param = {};
            param.engineId = $("input[name='engineId']").val();
            param.opType=1;
            if(variableType==1){
                param.isOutput=0;
            }else{
                param.isOutput=1;
            }
            param.nodeId=nodeid;
            //自行处理查询参数
            param.fuzzySearch = g_scoresssManage.fuzzySearch;
            if (g_scoresssManage.fuzzySearch) {

            }
            paramFilter.param = param;
            page.firstIndex = data.start == null ? 0 : data.start;
            page.pageSize = data.length  == null ? 10 : data.length;
            paramFilter.page = page;
            return paramFilter;
        }
    };

    //字段输入数据
    function getFields(){
        var isShowOptionFileds1=$("#isShowOptionFileds1").val();
        if(isShowOptionFileds1){
            g_fieldManage.tableUser.ajax.reload();
        }else{
            g_fieldManage.tableUser = $('#optionFields_list1').dataTable($.extend({
                'iDeferLoading':true,
                "bAutoWidth" : false,
                "Processing": true,
                "ServerSide": true,
                "sPaginationType": "full_numbers",
                "bPaginate": true,
                "bLengthChange": false,
                "iDisplayLength" : 5,
                "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                "ajax" : function(data, callback, settings) {
                    var queryFilter = g_fieldManage.getQueryCondition(data);
                    Comm.ajaxPost('decision_flow/getFieldOrScorecardForOption',JSON.stringify(queryFilter),function(result){
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
                    {
                        "className" : "childBox",
                        "orderable" : false,
                        "data" : null,
                        "width" : "60px",
                        "searchable":false,
                        "render" : function(data, type, row, meta) {
                                return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.cnName+'" enName="'+data.enName+'" valueType="'+data.valueType+'" restrainScope="'+data.restrainScope+'">'
                        }
                    },
                    {"data": "cnName","orderable" : false,"searchable":false}
                ],
                "createdRow": function ( row, data, index,settings,json ) {
                },
                "initComplete" : function(settings,json) {
                    $("#btn_search_score").click(function() {
                        g_fieldManage.fuzzySearch = true;
                        g_fieldManage.tableUser.ajax.reload();
                    });
                    $("#btn_search_reset_score").click(function() {
                        $('input[name="Parameter_search"]').val("");
                        g_fieldManage.fuzzySearch = true;
                        g_fieldManage.tableUser.ajax.reload();
                    });
                    $("#optionFields_list1 tbody").delegate( 'tr input','click',function(e){
                        var isChecked=$(this).attr('isChecked');
                            var selectArray = $("#optionFields_list1 tbody input:checked");
                            if(selectArray.length>0){
                                for(var i=0;i<selectArray.length;i++){
                                    $(selectArray[i]).attr('checked',false);
                                    $(this).attr('isChecked','false');
                                }
                            }
                            if(isChecked=='false'){
                                if($(this).attr('checked')){
                                    $(this).attr('checked',false);
                                }else{
                                    $(this).attr('checked','checked');
                                }
                                $(this).attr('isChecked','true');
                            }else{
                                if($(this).attr('checked')){
                                    $(this).attr('checked',false);
                                }else{
                                    $(this).attr('checked','checked');
                                }
                                $(this).attr('isChecked','false');
                            }
                            var fieldsId= $(this).val();
                            var fieldsHtml=$(this).parent().next().html();
                            $("#showFields1").html(fieldsId+'/'+fieldsHtml);
                            $("#hiddenFields1").html(fieldsId+'/'+fieldsHtml);
                            $("#valueType1").html($(this).attr('valueType'));
                            $("#hiddenRestrainScope1").html($(this).attr('restrainScope'));
                            $("#enName1").html($(this).attr('enName'));
                    });
                    $("#optionFields_list1 tbody").delegate( 'tr','click',function(e){
                        var target=e.target;
                        if(target.nodeName=='TD'){
                            var input=target.parentNode.children[1].children[0];
                            var isChecked=$(input).attr('isChecked');
                            var selectArray = $("#optionFields_list1 tbody input:checked");
                            if(selectArray.length>0){
                                for(var i=0;i<selectArray.length;i++){
                                    $(selectArray[i]).attr('checked',false);
                                    $(input).attr('isChecked','false');
                                }
                            }
                            if(isChecked=='false'){
                                if($(input).attr('checked')){
                                    $(input).attr('checked',false);
                                }else{
                                    $(input).attr('checked','checked');
                                }
                                $(input).attr('isChecked','true');
                            }else{
                                if($(input).attr('checked')){
                                    $(input).attr('checked',false);
                                }else{
                                    $(input).attr('checked','checked');
                                }
                                $(input).attr('isChecked','false');
                            }
                            var fieldsId= $(input).val();
                            var fieldsHtml=$(input).parent().next().html();
                            $("#showFields1").html(fieldsId+'/'+fieldsHtml);
                            $("#hiddenFields1").html(fieldsId+'/'+fieldsHtml);

                            $("#valueType1").html($(input).attr('valueType'));
                            $("#hiddenRestrainScope1").html($(input).attr('restrainScope'));
                            $("#enName1").html($(input).attr('enName'));
                        }
                    });
                }
            }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
            g_fieldManage.tableUser.on("order.dt search.dt", function() {
                g_fieldManage.tableUser.column(0, {
                    search : "applied",
                    order : "applied"
                }).nodes().each(function(cell, i) {
                    cell.innerHTML = i + 1
                })
            }).draw();
            $("#isShowOptionFileds1").val(1);
        }
    }
    //评分输入卡数据
    function getscores(){
        var isShowOptionFileds2=$("#isShowOptionFileds2").val();
        if(isShowOptionFileds2){
            g_scoresssManage.tableUser.ajax.reload();
        }else{
            g_scoresssManage.tableUser = $('#optionFields_list2').dataTable($.extend({
                'iDeferLoading':true,
                "bAutoWidth" : false,
                "Processing": true,
                "ServerSide": true,
                "sPaginationType": "full_numbers",
                "bPaginate": true,
                "bLengthChange": false,
                "iDisplayLength" : 5,
                "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                "ajax" : function(data, callback, settings) {
                    var queryFilter = g_scoresssManage.getQueryCondition(data);
                    Comm.ajaxPost('decision_flow/getFieldOrScorecardForOption',JSON.stringify(queryFilter),function(result){
                        var returnData = {};
                        var resData = result.data.list;
                        var resPage = result.data;
                        returnData.draw = data.draw;
                        returnData.recordsTotal = resPage.total;
                        returnData.recordsFiltered = resPage.total;
                        if(resData){
                            sc_array = resData;
                            if(sc_array!=null){
                                for (var i = 0; i < sc_array.length; i++) {
                                    var key = "\@"+sc_array[i].name+"\@";
                                    var value = "\#{"+sc_array[i].code+"\}";
                                    map.put(key,value);
                                }
                            }
                            returnData.data = resData;
                            callback(returnData);
                        }
                    },"application/json")

                },
                "order": [],
                "columns" :[
                    {
                        "data":null,
                        "searchable":false,"orderable" : false,
                        "class":"hidden"
                    },
                    {
                        "className" : "childBox",
                        "orderable" : false,
                        "data" : null,
                        "width" : "60px",
                        "searchable":false,
                        "render" : function(data, type, row, meta) {
                                return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.name+'" enName="'+data.enName+'">'
                        }
                    },
                    {"data": "cnName","orderable" : false,"searchable":false}
                ],
                "createdRow": function ( row, data, index,settings,json ) {
                },
                "initComplete" : function(settings,json) {
                    $("#btn_search_score").click(function() {
                        g_scoresssManage.fuzzySearch = true;
                        g_scoresssManage.tableUser.ajax.reload();
                    });
                    $("#btn_search_reset_score").click(function() {
                        $('input[name="Parameter_search"]').val("");
                        g_scoresssManage.fuzzySearch = true;
                        g_scoresssManage.tableUser.ajax.reload();
                    });
                    $("#optionFields_list2 tbody").delegate( 'tr input','click',function(e){
                        var isChecked=$(this).attr('isChecked');
                            var selectArray = $("#optionFields_list2 tbody input:checked");
                            if(selectArray.length>0){
                                for(var i=0;i<selectArray.length;i++){
                                    $(selectArray[i]).attr('checked',false);
                                    $(this).attr('isChecked','false');
                                }
                            }
                            if(isChecked=='false'){
                                if($(this).attr('checked')){
                                    $(this).attr('checked',false);
                                }else{
                                    $(this).attr('checked','checked');
                                }
                                $(this).attr('isChecked','true');
                            }else{
                                if($(this).attr('checked')){
                                    $(this).attr('checked',false);
                                }else{
                                    $(this).attr('checked','checked');
                                }
                                $(this).attr('isChecked','false');
                            }
                            var fieldsId= $(this).val();
                            var fieldsHtml=$(this).parent().next().html();
                            $("#showFields2").html(fieldsId+'/'+fieldsHtml);
                            $("#hiddenFields2").html(fieldsId+'/'+fieldsHtml);
                    });
                    $("#optionFields_list2 tbody").delegate( 'tr','click',function(e){
                        var target=e.target;
                        if(target.nodeName=='TD'){
                            var input=target.parentNode.children[1].children[0];
                            var isChecked=$(input).attr('isChecked');
                            var selectArray = $("#optionFields_list2 tbody input:checked");
                            if(selectArray.length>0){
                                for(var i=0;i<selectArray.length;i++){
                                    $(selectArray[i]).attr('checked',false);
                                    $(input).attr('isChecked','false');
                                }
                            }
                            if(isChecked=='false'){
                                if($(input).attr('checked')){
                                    $(input).attr('checked',false);
                                }else{
                                    $(input).attr('checked','checked');
                                }
                                $(input).attr('isChecked','true');
                            }else{
                                if($(input).attr('checked')){
                                    $(input).attr('checked',false);
                                }else{
                                    $(input).attr('checked','checked');
                                }
                                $(input).attr('isChecked','false');
                            }
                            var fieldsId= $(input).val();
                            var fieldsHtml=$(input).parent().next().html();
                            $("#showFields2").html(fieldsId+'/'+fieldsHtml);
                            $("#hiddenFields2").html(fieldsId+'/'+fieldsHtml);
                        }
                    });
                }
            }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
            g_scoresssManage.tableUser.on("order.dt search.dt", function() {
                g_scoresssManage.tableUser.column(0, {
                    search : "applied",
                    order : "applied"
                }).nodes().each(function(cell, i) {
                    cell.innerHTML = i + 1
                })
            }).draw();
            $("#isShowOptionFileds2").val(1);
        }
    }



