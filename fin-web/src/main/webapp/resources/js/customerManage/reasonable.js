/**
 * Created by Administrator on 2017/6/22 0022.
 */
var materialArr=[];
function checkImg(me) {
    $("#showBigImg").attr("src",$(me).attr("src")).parent().show();
}
function showListImg(me) {
    var customerId=$("#customerId").val();
    var isShow=$("#showReasonableImg").css("display");
    if(isShow=="none"){
        if($("#isFirst").val()){
            $("#showReasonableImg").show();
            $(me).html("隐藏清单");
        }else{
            $("#showReasonableImg .ReasonableUl").empty();
            Comm.ajaxPost("customer/customerDetailsSP",customerId,function(result){
                var data = result.data;
                var host=data.host;
                var imageList=data.imageList;//上传资料
                if(imageList){
                    var html=""
                    for(var i=0;i<imageList.length;i++){
                        if(imageList[i].type=="117"){
                            html+='<li style="margin-right: .5em;"><img onclick="imgShow2(this)" src="'+host+imageList[i].src+'" class="showBigImgHover"></li>';
                        }
                    }
                    $("#showReasonableImg .ReasonableUl").append(html);
                    $("#showReasonableImg").show();
                    $(me).html("隐藏清单");
                    $("#isFirst").val(1)
                }
            }, "application/json",null,null,null,false);
        }
    }else{
        $("#showReasonableImg").hide();
        $("#showBigImg").parent().hide();
        $(me).html("查看清单");
    }
}
function addReasonableContent() {
    var keyWord=$("input[name='ReasonableInput']").val().trim();
    if(keyWord==""){
        layer.msg("请输入材料名称！",{time:2000});
        return;
    }else{
        if(materialArr.indexOf(keyWord)==-1){
            var customerId=$("#customerId").val();
            var orderId=$("#orderId").val();
            $("#showReasonableContent").append('<form action="" id="form-'+keyWord+'"><table class="tb_info" style="font-size:12px;margin: .5em 0;cursor: pointer">' +
                '<tbody><tr>' +
                '<td class="ReasonableTableTitle">材料名称：</td>' +
                '<td class="ReasonableTableTitle"><input class="getMsg" type="text" style="text-align: center" name="materialType'+keyWord+'" readonly value="'+keyWord+'"></td>' +
                '<td class="ReasonableTableTitle">装修价格是否合理：</td>' +
                '<td class="ReasonableTableTitle getMsg"><label>' +
                '<input type="radio" name="IsReasonable-'+keyWord+'" value="1" checked>是</label>' +
                '<label>' +
                '<input type="radio" name="IsReasonable-'+keyWord+'" value="0">否</label></td>' +
                '<td class="ReasonableTableTitle">装修材料数量是否合理：</td>' +
                '<td class="ReasonableTableTitle getMsg">' +
                '<label>' +
                '<input type="radio" name="IsCountReasonable-'+keyWord+'" value="1" checked>是</label>' +
                '<label>' +
                '<input type="radio" name="IsCountReasonable-'+keyWord+'" value="0">否</label>' +
                '</td><td class="ReasonableTableTitle">备注：</td><td><label><input class="getMsg" type="text" placeholder="输入备注" name="remark'+keyWord+'"></label></td>' +
                '<td class="ReasonableTableTitle">' +
                '<a href="#####" onclick="showCompare(\''+keyWord+'\')" class="yetUpload">比价</a>&nbsp;&nbsp;' +
                '<input type="file" name="file" class="waitUpload" style="width:180px;display:inline-block;" onchange="javascript:submitImg(this,\''+keyWord+'\');">' +
                '&nbsp;&nbsp;<a class="waitUpload imgUpload" onclick="imgShow1(this)">待上传</a>&nbsp;&nbsp;' +
                '<a href="#####" onclick="deleteReasonable(this,\''+keyWord+'\')" class="waitUpload">删除</a></td></tr></tbody></table>' +
                '<input type="hidden" name="customerId" value="'+customerId+'">' +
                '<input type="hidden" name="orderId" value="'+orderId+'">' +
                '<input type="hidden" name="materialPriceResp" value="">' +
                '<input type="hidden" name="materialCountResp" value="">' +
                '<input type="hidden" name="decorationCountResp" value="">' +
                '<input type="hidden" name="materialName" value="">' +
                '<input type="hidden" name="remark" value="">' +
                '<input type="hidden" name="saveId" value="" class="getMsg">' +
                '<input type="hidden" name="fileName" value="" class="getMsg">' +
                '</form>');
            $("#showReasonableContent").show();
            $("#showReasonableJudge").show().css("display","inline-block");
            $("input[name=ReasonableInput]").val("");
            materialArr.push(keyWord);
        }else{
            layer.msg("该材料已经添加成功!",{time:2000});
            $("input[name=ReasonableInput]").val("");
        }

    }
}
function showCompare(keyWord) {
    window.open('https://search.jd.com/Search?keyword='+keyWord+'&enc=utf-8');
}
function deleteReasonable(me,key) {
    var form=$("#form-"+key);
    var id= $(form).find("input[name='saveId']").val();//保存id
    var fileName = 'fintecher_file/'+$(me).prev().attr('filename');
    if(id){
        Comm.ajaxPost('customerAudit/deleteFile',"fileName="+fileName,function (result) {
            if(result.code==0){
                $(me).parent("td").parent("tr").parents(".tb_info ").parent("form").remove();
                materialArr.splice(materialArr.indexOf(key),key.length);
                if($("#showReasonableContent tbody").find("tr").length==0){
                    $("#showReasonableJudge").hide();
                    $("input[name=ReasonableInput]").val("");
                }
            }
        });
    }else{
        $(me).parent("td").parent("tr").parents(".tb_info ").parent("form").remove();
        materialArr.splice(materialArr.indexOf(key),key.length);
        if($("#showReasonableContent tbody").find("tr").length==0){
            $("#showReasonableJudge").hide();
            $("input[name=ReasonableInput]").val("");
        }
    }

   /* if(id){
        Comm.ajaxPost('customerAudit/deleteCustomerResp',"id="+id,function (result) {
            debugger
            if(result.code==0){
                layer.msg(result.msg,{time:2000},function () {
                    $(me).parent("td").parent("tr").parents(".tb_info ").parent("form").remove();
                    materialArr.splice(materialArr.indexOf(key),key.length);
                    if($("#showReasonableContent tbody").find("tr").length==0){
                        $("#showReasonableJudge").hide();
                        $("input[name=ReasonableInput]").val("");
                    }
                });
            }
        });
    }else{
        $(me).parent("td").parent("tr").parents(".tb_info ").parent("form").remove();
        materialArr.splice(materialArr.indexOf(key),key.length);
        if($("#showReasonableContent tbody").find("tr").length==0){
            $("#showReasonableJudge").hide();
            $("input[name=ReasonableInput]").val("");
        }
    }*/
}
function submitImg(me,keyWord) {
    var form=$("#form-"+keyWord);
    // var materialPriceResp=$("input[name='IsReasonable-"+keyWord+"']:checked").val();
    // if(!materialPriceResp){
    //     layer.msg("请选择装修价格是否合理",{time:2000});
    //     return;
    // }
    // $(form).find("input[name='materialPriceResp']").val(materialPriceResp);//价格
    // var materialCountResp=$("input[name='IsCountReasonable-"+keyWord+"']:checked").val();
    // if(!materialCountResp){
    //     layer.msg("请选择装修材料数量是否合理",{time:2000});
    //     return;
    // }
    // $(form).find("input[name='materialCountResp']").val(materialCountResp);//数量
    //
    // if($("input[name='ListIsReasonable']").attr("checked")){
    //     var decorationCountResp="1"
    // }else{
    //     var decorationCountResp="0"
    // }
    // // if(decorationCountResp=="0"){
    // //     layer.msg("请选择装修清单是否合理",{time:2000});
    // //     return;
    // // }
    // $(form).find("input[name='decorationCountResp']").val(decorationCountResp);//总计
    //
    // var materialName=$("input[name='materialType"+keyWord+"']").val();
    // $(form).find("input[name='materialName']").val(materialName);//材料名称
    //
    // var remark=$("input[name='remark"+keyWord+"']").val();
    // $(form).find("input[name='remark']").val(remark);//备注

    var file=$("input[name='file']")[0];
    if(file.files &&file.files[0]){
        var type=file.files[0].type;
        if(type!="image/jpeg"&&type!="image/png"&&type!="image/tiff"&&type!="image/bmp"){
            layer.msg("您上传的图片格式不正确，请重新选择!");
            return;
        }
    }else{
        layer.msg("请上传图片!");
        $(form).find(".imgUpload").html("待上传").removeClass("yetUpload").addClass("waitUpload");
        return;
    }
    $(form).ajaxSubmit({
        type: "POST",
        url: "addCustomerResp",
        beforeSend: function () {
          layer.load(1, {
                shade: [0.1, '#fff']
            });
        },
        success: function (msg) {
            layer.closeAll('loading');
            var msg = eval('(' + msg + ')');
            if(msg.code==0&&msg.data){
                layer.msg("上传成功!",{time:2000},function(){
                    $(form).find(".imgUpload").html("预览图片");
                    $(form).find(".imgUpload").attr("fileName",msg.data.fileName);
                    $(form).find("input[name='saveId']").val(msg.data.id);//保存id
                    $(form).find("input[name='fileName']").val(msg.data.fileName);//文件名
                    $(me).attr('disabled', true);
                });
            }
        }
    });
}
//查看商户
function checkMerchant(me) {
    var id=$("#merchantId").val();
    layer.open({
        type:1,
        title:'商户信息',
        area: ['100%','100%'],
        content :$('#MerMsg'),
        success: function(layero, index){
            Comm.ajaxPost('decoration/getById',id,function(data){
                if(data.code==1){
                    layer.msg(data.msg,{time:2000});
                    return;
                }
                var data = data.data.merchantDecoration;
                if(data==null){
                    layer.msg("无此商户信息！",{time:2000});
                    return;
                }
                $('#merchantName').val(data.merchantName);
                $("input[name='merPlace']").val(data.proName+"/"+data.cityName);
                $('#address').val(data.address);
                $('#merchantHolder').val(data.merchantHolder);
                $('#position').val(data.position);
                $('#accountBank').val(data.accountBank);
                $('#publicAccount').val(data.publicAccount);
                $('#principal').val(data.principal);
                $('#principalPosition').val(data.principalPosition);
                $('#principalTel').val(data.principalTel);

                $('#channelPerson').val(data.channelPerson);
                $('#personTel').val(data.personTel);
                $('#merchantScale').val(data.merchantScale);
                $('#designerCount').val(data.designerCount);
                $('#servicerCount').val(data.servicerCount);
                $("#level option[value='"+data.level+"']").attr("selected", "selected");//客户级别
                $('#price').val(data.price);
                $('#turnoverCount').val(data.turnoverCount);
                $('#channel').val(data.channel);
                $('#team').val(data.team);
                $('#department').val(data.department);
                $('#cusPriceMax').val(data.cusPriceMax);
                /*$('#branchCount').val(data.branchCount);
                $('#highSeas').val(data.highSeas);
                $('#personDepartment').val(data.personDepartment);
                $('#industry').val(data.industry);*/
            },"application/json")
        }
    });
}
//图片预览方法二
function imgShow1(me){
    if(!$(me).attr("fileName")){
        layer.msg("请先上传图片!");
        return;
    }
    var src=_ctx+"/fintecher_file/"+$(me).attr("fileName");
    window.open(src,"图片预览");
    // $("#imgDisplay img").attr("src",src);
    // layer.open({
    //     type :1,
    //     title: false,
    //     maxmin : true,
    //     shadeClose : true,
    //     offset: '100px',
    //     area : [ '60%', '60%' ],
    //     content : $('#imgDisplay'),
    //     success : function(index, layero) {
    //         $("#imgDisplay img").attr("src",$(me).attr("src"));
    //     },
    // })
}
//图片预览方法三
function imgShow2(me){
    var src=_ctx+"/fintecher_file/"+$(me).attr("src");
    window.open($(me).attr("src"),"图片预览");
    // $("#imgDisplay img").attr("src",src);
    // layer.open({
    //     type :1,
    //     title: false,
    //     maxmin : true,
    //     shadeClose : true,
    //     offset: '100px',
    //     area : [ '60%', '60%' ],
    //     content : $('#imgDisplay'),
    //     success : function(index, layero) {
    //         $("#imgDisplay img").attr("src",$(me).attr("src"));
    //     },
    // })
}