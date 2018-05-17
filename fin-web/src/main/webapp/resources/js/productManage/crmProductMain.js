/**
 * Created by Win7 on 2017/6/13.
 */
var Allhtml;
$(function(){
    loadPrdType();
    $(".addDetail").hide();
    setSelect("1","province");
    // console.log(_ctx);
    apendSelect()
})
//var flag=false;
//产品系列
function loadPrdType(id,flag){
    Comm.ajaxPost(
        'product/getProductSeries',"",
        function (data) {
            var result = data.data.data;
            var resultLength=result.length-1;
            var html="";
            for ( var i = 0; i < result.length; i++) {
                html+="<tr><td style='width:103px;' onclick=\"clickPrdType(\'"+result[i].id+"\',null,\'"+result[i].pro_series_type+"\')\">";//onclick=\"clickPrdType(\'"+result[i].id+"\')\">"+result[i].pro_name+
                html+="<span style='cursor: pointer;'>"+result[i].pro_name+"</span></td>";
                html+=" <td align='right' style='margin-right: 55px;'>";
                if(result[i].status=="1"||result[i].status=="2"){
                    html+='<a href="javascript:void(0);" onclick=\'updataProStatus(\"'+result[i].id+'\",1,this,0)\'>停用</a><input type="hidden" value="'+result[i].parent_id+'">';
                    html+='<a href="javascript:void(0);"  onclick=\'showAddPrdTypeDiv1(\"'+result[i].id+'\",this)\' style="margin-left:3px;">修改</a>';
                }else{
                    html+='<a href="javascript:void(0);" onclick=\'updataProStatus(\"'+result[i].id+'\",1,this,1)\'>启用</a><input type="hidden" value="'+result[i].parent_id+'">';
                }
                html+="</td>";
                html+="</tr>";
            }
            $("#prdTypeTmpl").html(html);
            if(!id){
                $("#showFirst").val(result[resultLength].id);
                $("#pro_series_type").val(result[resultLength].pro_series_type);
                clickPrdType($("#showFirst").val(), null, $("#pro_series_type").val());
            }else{
                $("#showFirst").val(id);
                if(flag==0){
                    clickPrdType($("#showFirst").val(),0);
                }else{
                    clickPrdType($("#showFirst").val());
                }
            }
        }
    );
}
//点击产品类型弹出产品名称
function clickPrdType(id,flag,pro_series_type) {
    $("#pro_series_type").val(pro_series_type);
    debugger
    $("#v3ParentId").val(id);
    $("#prdQueueTmpl").html("");
    Comm.ajaxPost(
        'product/getProductType', 'id='+id,function(data) {
            var result = data.data.data;
            if(data.data!="false"){
                var html="";
                if(flag&&flag!=0){
                    var product_type = '';
                    for ( var i = 0; i < result.length; i++) {
                        html+='<tr><td>';
                        html+="<span style='cursor: pointer;width: 150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;' id='"+result[i].id+"'  onclick=\"renderPrd(\'"+result[i].id+"\',false,\'"+result[i].type+"\')\">"+result[i].pro_name+"</span></td>";
                        html+='<td align="right" style="margin-right: 55px;">';
                        if(result[i].status=="1"||result[i].status=="2"){
                            html+='<a href="javascript:void(0);" onclick=\'updataProStatus(\"'+result[i].id+'\",2,this,0)\'>停用</a><input type="hidden" value="'+result[i].parent_id+'">';
                            html+='<a href="javascript:void(0);" onclick=\'showAddPrdTypeDiv2(\"'+result[i].id+'\",this)\'>修改</a><input type="hidden" value="'+result[i].parent_id+'">';
                        }else{
                            html+='<a href="javascript:void(0);" onclick=\'updataProStatus(\"'+result[i].id+'\",2,this,1)\'>启用</a><input type="hidden" value="'+result[i].parent_id+'">';
                        }
                        html+='</td></tr>';
                        if(result[i].id==flag){
                            product_type = result[i].type;
                        }
                    }
                    renderPrd(flag,false,product_type);
                }else{
                    for ( var i = 0; i < result.length; i++) {
                        html+='<tr><td>';
                        html+="<span style='cursor: pointer;width: 150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;' id='"+result[i].id+"'   onclick=\"renderPrd(\'"+result[i].id+"\',false,\'"+result[i].type+"\')\">"+result[i].pro_name+"</span></td>";
                        html+='<td align="right" style="margin-right: 55px;">';
                        if(result[i].status=="1"||result[i].status=="2"){
                            html+='<a href="javascript:void(0);" onclick=\'updataProStatus(\"'+result[i].id+'\",2,this,0)\'>停用</a><input type="hidden" value="'+result[i].parent_id+'">';
                            html+='<a href="javascript:void(0);" onclick=\'showAddPrdTypeDiv2(\"'+result[i].id+'\",this)\'>修改</a><input type="hidden" value="'+result[i].parent_id+'">';
                        }else{
                            html+='<a href="javascript:void(0);" onclick=\'updataProStatus(\"'+result[i].id+'\",2,this,1)\'>启用</a><input type="hidden" value="'+result[i].parent_id+'">';
                        }
                        html+='</td></tr>';
                        renderPrd(result[i].id,true,result[i].type);
                    }
                }
                $("#prdQueueTmpl").html(html);
            }else{
                if(flag==0){
                    $("#prdListTmpl").html("");
                }else{
                    $("#prdListTmpl").html("");
                    layer.msg(data.msg,{time:1000});
                }
            }
        }
    );
}
//显示详情页
function renderPrd(id,flag,type){
    debugger
    $('#product_type').val(type);
    var c = $('#product_type').val();
    var addhtml="";
    $("#prdListTmpl").html("");
    Allhtml="";
    Comm.ajaxPost(
        'product/getProductDetail', "crmProductId="+id,
        function (data) {
            debugger
            var result=data.data.data;
            var productType=data.data.productType;
            if(productType!=null){
                $("#secondParent").val(productType.id);
                $("#cpNumber1").val(productType.pro_number);
                $("#cpName1").val(productType.pro_name);
            }
            var parent_id=$("#secondParent").val();
            if(data.data!="false"){
                addhtml+="<li style='height:338px; border:1px #ddd dashed;' class='addDetail'>";
                addhtml+="<a class='addnew' href='javascript:void(0);'><img src='../resources/images/photoadd.png'  onclick=\"showFormView('',\'"+productType.id+"\',1,0,this,\'"+type+"\')\"/>"
                addhtml+="<p>新增产品期数</p></a>";
                addhtml+="</li>";
                for ( var i = 0; i < result.length; i++) {
                    if(result[i].status=="0"){
                        Allhtml+="<li class='nofb'>";
                    }else if(result[i].status=="2"||result[i].status=="1"){
                        Allhtml+="<li class='fbon'>";
                    }
                    Allhtml+="<div class='lcprxh'>产品序号："+productType.pro_number+"</div>";
                    Allhtml+="<div class='lcprname'>";
                    Allhtml+="<input type='hidden'  value='"+result[i].id+"' detailview='flase' product='"+result[i].productId+"' isview='false'>";
                    Allhtml+="<h3 style='width: 190px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden'>"+productType.pro_name+"</h3>";

                    Allhtml+="<p>综合利率：</p>";
                    Allhtml+="<p style='height:32px;'><lable class='ransomDetailLable' title='"+result[i].multipleRate+"'>"+result[i].multipleRate+"</lable></p></div>";

                    Allhtml+="</div><div class='lcprdetail'><table><tbody><tr><td width='100'></td>";
                    Allhtml+="<td width='180' align='right'></td></tr>";//
                    Allhtml+="<tr><td>期限</td> <td align='right'>"+result[i].productTermMin+" 至  "+result[i].productTermMax+"</td></tr>";///
                    Allhtml+="<tr><td>还款方式</td><td align='right'>一次性还本付息</td></tr>";////
                    Allhtml+="<tr><td></td><td align='right'></td></tr>";////
                    if(result[i].status=="0"){
                        Allhtml+="<tr><td colspan='2'>&nbsp;</td></tr></tr>";
                        Allhtml+="</tbody></table></div><div class='lcprbtn'>";
                        Allhtml+="<a href='javascript:void(0);' class='fl fulleditbtn detailEdit' onclick=\"showFormView(\'"+result[i].id+"\',\'"+productType.id+"\',2,1,this,\'"+type+"\')\">全局编辑</a><input type='hidden' value='"+productType.id+"'/>";
                        Allhtml+="<a href='javascript:void(0);' class='fr' onclick=\"updateStatus(\'"+result[i].id+"\',2,this)\">发布</a><input type='hidden' value='"+productType.id+"'/>";

                    }else if(result[i].status=="2"||result[i].status=="1"){
                        Allhtml+="<tr><td>停用/启用</td> <td align='right'>";
                        if(result[i].status=="2"){
                            Allhtml+="<div class='switchs' onclick='switchs(this)'></div><input type='hidden' value='"+productType.id+"'/></td></tr>";
                        }else{
                            Allhtml+="<div class='switchs switchOn' onclick='switchs(this)'></div><input type='hidden' value='"+productType.id+"'/></td></tr>";
                        }
                        Allhtml+="</tbody></table></div><div class='lcprbtn'>";
                        Allhtml+="<a href='javascript:void(0);' class='view detailView' onclick=\"showFormView(\'"+result[i].id+"\',\'"+productType.id+"\',0,'',this,\'"+type+"\')\">查看</a>"
                    }
                    Allhtml+="</div></li>";
                }
            }else{
                addhtml+="<li style='height:338px; border:1px #ddd dashed;' class='addDetail'>";
                addhtml+="<a class='addnew' href='javascript:void(0);'><img src='../resources/images/photoadd.png'  onclick=\"showFormView('',\'"+id+"\',1,0,this,\'"+type+"\')\"/>"//
                addhtml+="<p>新增产品期数</p></a>";
                addhtml+="</li>";
            }
            if(flag==true){
                $("#prdListTmpl").html(Allhtml);
            }else{
                $("#prdListTmpl").html(addhtml+Allhtml);
            }
        }
    );
}
//第三列的启动/停用
function switchs(me){
    var obj = $(me);
    //弹框提示用户是否改变状态
    layer.confirm('你确定要改变状态？', {icon: 3, title:'操作提示'}, function(index){
        if(index != null || index != ""){
            //获取当前的class属性值(页面改变状态)
            var selectClass = obj.toggleClass("switchOn")[0].className;
            //产品状态
            var status;
            //通过判断calss属性来决定停用或者是启用
            if("switchs" == selectClass){
                status = "2";	//停用
            }else{
                status = "1";	//启用
            }
            //获取该父节点input的值
            var productId = obj.parent().parent().parent().parent().parent().prev().find("input").val();
            //修改产品状态为启用
            updateStatus(productId, status,me);
        }
        layer.close(index);
    });
}
function updateStatus(productId, status,me){
    var type = $('#product_type').val();
    Comm.ajaxPost("product/updateProductStatus","id="+productId+"&status="+status,function(data){
        if(data.code=="0"){
            //根据参数后台加载产品集合
            //操作提示
            layer.msg(data.msg,{time:1000},function(){
                //if(status=="2"){
                var proId=$(me).next().val();
                renderPrd(proId,false,type);
                /*}else{
                 debugger
                 }*/
            });
        }else{
            //操作提示
            layer.msg(data.msg,{time:1000});
        }
    });
}
//新增系列
function showAddPrdTypeDiv(){
    //alert();
    $("#prdTypeNumber").val("");
    $("#prdTypeName").val("");
    imgLayer = layer.open({
        title:"<s class='preLine'>信贷产品系列</s>",
        type: 1,
        maxWidth:'auto',
        skin: 'layer_normal',
        shift: 5,
        btn:['确认','取消'],
        shadeClose:false,
        content: $("#addProductTypeDiv"),
        yes: function(index, layerqw){
            debugger
            var prdTypeName = $("#prdTypeName").val();
            var prdTypeNumber = $("#prdTypeNumber").val();
            if(prdTypeNumber==''){
                layer.msg("产品系列序列不能为空！",{time:1000});
                return;
            }
            if(prdTypeName==''){
                layer.msg("产品系列名称不能为空！",{time:1000});
                return;
            }
            var data=$("#addProductType").serializeJson();
            data = JSON.stringify(data);
            Comm.ajaxPost("product/addProductSeries","data="+data,function(data){
                layer.msg(data.msg,{time:1000},function(){
                    layer.closeAll();
                    loadPrdType(data.data,0);
                });
            })
        }
    });
}
//修改系列
function showAddPrdTypeDiv1(id,me){
    $("#parent_id2").val(id);
    $("#prdTypeNumber").val("");
    $("#prdTypeName").val("");
    imgLayer = layer.open({
        title:"<s class='preLine'>信贷产品系列</s>",
        type: 1,
        maxWidth:'auto',
        skin: 'layer_normal',
        shift: 5,
        btn:['确认','取消'],
        shadeClose:false,
        content: $("#addProductTypeDiv1"),
        success:function(index, layerqw){
            Comm.ajaxPost("product/getPrdTypeInfo","prdTypeId="+id,function(data){
                debugger
                $("#prdTypeNumber1").val(data.data.pro_number);
                $("#prdTypeName1").val(data.data.pro_name);
                $("#productType3").val(data.data.pro_series_type);
            });
        },
        yes: function(index, layerqw){
            //表单提交
            var prdTypeName = $("#prdTypeName1").val();
            var prdTypeNumber = $("#prdTypeNumber1").val();
            if(prdTypeNumber==''){
                layer.msg("产品系列序列不能为空！",{time:1000});
                return;
            }
            if(prdTypeName==''){
                layer.msg("产品系列名称不能为空！",{time:1000});
                return;
            }
            var data=$("#addProductType1").serializeJson();
            data = JSON.stringify(data);
            Comm.ajaxPost("product/updateSeries",data,function(data){
                layer.msg(data.msg,{time:1000},function(){
                    var parentId=$(me).next().val();
                    layer.closeAll();
                    loadPrdType(id);
                    /*if(flag==0){//产品系列修改
                     //loadPrdType();
                     loadPrdType(id);
                     }else{//产品名称修改
                     clickPrdType(parentId);
                     }*/
                });
            }, "application/json");
        }
    });
}
//新增产品
function showAddPrdListDiv(){
    debugger
    $("#preview").attr("src","");
    $("#file").val("");
    $("#v3Name").val("");
    imgLayer = layer.open({
        title:"<s class='preLine'>信贷产品</s>",
        type: 1,
        maxWidth:'auto',
        skin: 'layer_normal',
        shift: 5,
        btn:['确认','取消'],
        shadeClose:false,
        content: $("#addV3Prd"),
        success:function(index, layerqw){
            Comm.ajaxPost("product/getNumber","",function(data){
                $("#v3Number").val(data.data);
            });
            $("#province").empty().append("<option>请选择</option>");
            $("#city").empty().append("<option>请选择</option>");
            $("#district").empty().append("<option>请选择</option>");
            $("#productType").val('-1');
            setSelect("1","province");
        },
        yes: function(index, layerqw){
            //表单提交
            debugger
            $("#provinces").val($("#province option:selected").html());
            $("#cityHidden").val($("#city option:selected").html());
            $("#distric").val($("#district option:selected").html());
            var provinces = $("#provinces").val();
            var productType = $("#productType").val();
            var cityHidden = $("#cityHidden").val();
            var distric = $("#distric").val();
            var pro_name = $("#v3Name").val();
            var v3Quota = $("#v3Quota").val();
            // var quotaLimit = $("#quotaLimit").val();//产品额度上限
            // var quotaProportion = $("#quotaProportion").val();//提额比例
            var quotaLimit = "0";//产品额度上限
            var quotaProportion = "0";//提额比例

            if (pro_name==''){
                layer.msg("产品名称不能为空！",{time:1000});return
            }
            // if (quotaProportion==''){
            //     layer.msg("提额比例不能为空！",{time:1000});return
            // }
            if (v3Quota==''){
                layer.msg("产品额度不能为空！",{time:1000});return
            }
            // if (quotaLimit==''){
            //     layer.msg("额度上限不能为空！",{time:1000});return
            // }
            if(productType==-1){
                layer.msg("请选择产品类型！",{time:1000});return
            }
            // if (provinces=='-请选择-'){
            //     layer.msg("省不可为空！",{time:1000});return
            // }
            // if (cityHidden=='-请选择-'){
            //     layer.msg("市不可为空！",{time:1000});return
            // }
            // if (distric=='-请选择-'){
            //     layer.msg("区不可为空！",{time:1000});return
            // }
            // var param = {};
            // param.provinces = provinces;
            // param.productType = productType;
            // param.cityHidden = cityHidden;
            // param.distric = distric;
            //
            // param.pro_name = pro_name;
            // param.productType = productType;
            // param.cityHidden = cityHidden;
            // param.distric = distric;
            // Comm.ajaxPost("product/addProductType","",function(data){
            //     data = eval('('+data+')');
            //     layer.msg(data.msg,{time:1000},function(){
            //         layer.closeAll();
            //         var id=$("#v3ParentId").val();
            //         clickPrdType(id)
            //     });
            // });

            $('#addV3Product').ajaxSubmit({
                type: "POST",
                url: "addProductType",
                success: function (data) {
                    data = eval('('+data+')');
                    layer.msg(data.msg,{time:1000},function(){
                        layer.closeAll();
                        var id=$("#v3ParentId").val();
                        clickPrdType(id)
                    });
                },
                error: function (XMLHttpRequest, textStatus, thrownError) {
                }
            });
        }
    });
}
//修改名称
function showAddPrdTypeDiv2(id,me){
    $("#parent_id3").val(id);
    $("#prdTypeNumber").val("");
    $("#prdTypeName").val("");
    $("#preview1").attr("src","");
    $("#file1").val("");
    $("#province1 option:selected").html("请选择");
    $("#city1 option:selected").html("请选择");
    $("#district1 option:selected").html("请选择");
    imgLayer = layer.open({
        title:"<s class='preLine'>信贷产品系列</s>",
        type: 1,
        maxWidth:'auto',
        skin: 'layer_normal',
        shift: 5,
        btn:['确认','取消'],
        shadeClose:false,
        content: $("#addProductTypeDiv2"),
        success:function(index, layerqw){
            Comm.ajaxPost("product/getPrdTypeInfo","prdTypeId="+id,function(data){
                $("#prdTypeNumber2").val(data.data.pro_number).attr("readOnly","readOnly");
                $("#prdTypeName2").val(data.data.pro_name);
                $("#v3Quota2").val(data.data.pro_quota);
                $("#quotaLimit2").val(data.data.pro_quota_limit);
                $("#quotaProportion2").val(data.data.pro_quota_proportion);
                $("#productType2").val(data.data.type);
                $("#pro_describe").val(data.data.pro_describe);
                $("#preview1").attr("src", _ctx +data.data.img_url);
                // console.log($("#preview1").attr("src"));

                var provinces_id=data.data.provinces_id;
                var city_id=data.data.city_id;
                var distric_id=data.data.distric_id;
                setSelect1(1,'province');
                $("#province1 option:selected").html(data.data.provinces);
                onSelectChange(provinces_id,'city','1');
                $("#city1 option:selected").html(data.data.city);
                onSelectChange(city_id,'district','1');
                $("#district1 option:selected").html(data.data.distric);

                $("#prdTypeId").val(data.data.id);
                $("#picName1").val(data.data.img_url);
            });
        },
        yes: function(index, layerqw){
            //表单提交
            $("#provinces1").val($("#province1 option:selected").html());
            $("#cityHidden1").val($("#city1 option:selected").html());
            $("#distric1").val($("#district1 option:selected").html());
            var provinces = $("#provinces1").val();
            var productType2 = $("#productType2").val();
            var cityHidden = $("#cityHidden1").val();
            var distric = $("#distric1").val();
            var pro_name = $("#prdTypeName2").val();
            var v3Quota2 = $("#v3Quota2").val();
            var quotaLimit2 = $("#quotaLimit2").val();
            var quotaProportion2 = $("#quotaProportion2").val();
            var quotaLimit2 = "0";
            var quotaProportion2 = "0";
            if (pro_name==''){
                layer.msg("产品名称不能为空！",{time:1000});return
            }
            // if (quotaProportion2==''){
            //     layer.msg("提额额度不能为空！",{time:1000});return
            // }
            if (v3Quota2==''){
                layer.msg("产品额度不能为空！",{time:1000});return
            }
            // if (quotaLimit2==''){
            //     layer.msg("额度上限不能为空！",{time:1000});return
            // }
            if(productType2==-1){
                layer.msg("请选择产品类型！",{time:1000});return
            }

            // if (provinces=='-请选择-'){
            //     layer.msg("省不可为空！",{time:1000});return
            // }
            // if (cityHidden=='-请选择-'){
            //     layer.msg("市不可为空！",{time:1000});return
            // }
            // if (distric=='-请选择-'){
            //     layer.msg("区不可为空！",{time:1000});return
            // }

            // var param = {};
            // param
            // Comm.ajaxPost("product/updatePrdTypeInfo","id="+Id+"&flag="+flag+"&status="+status,function(data){
            //     if(data.code == "0"){
            //         layer.msg(data.msg,{time:1000},function(){
            //             var parentId=$(me).next().val();
            //             if(flag=="1"){//产品系列
            //                 loadPrdType(Id);
            //             }else if(flag=="2"){//产品名称
            //                 clickPrdType(parentId,Id);
            //             }
            //         });
            //     }else{
            //         layer.msg(data.msg,{time:1000});
            //     }
            // });




            $('#addProductType2').ajaxSubmit({
                type: "POST",
                url: "updatePrdTypeInfo",
                success: function (data) {
                    data = eval('('+data+')');
                    layer.msg(data.msg,{time:1000},function(){
                        var parentId=$(me).next().val();
                        layer.closeAll();
                        /* if(flag==0){//产品系列修改
                         //loadPrdType();
                         loadPrdType(id);
                         }else{//产品名称修改
                         clickPrdType(parentId);
                         }*/
                        clickPrdType(parentId);
                    });
                },
                error: function (XMLHttpRequest, textStatus, thrownError) {
                }
            });
        }
    });
}
//产品系列停用
function updataProStatus(Id,flag,me,status){
    //弹框提示用户是否改变状态
    layer.confirm('你确定要改变状态？', {icon: 3, title:'操作提示'}, function(index){
        if(index != null || index != ""){
            Comm.ajaxPost("product/disableProduct","id="+Id+"&flag="+flag+"&status="+status,function(data){
                if(data.code == "0"){
                    layer.msg(data.msg,{time:1000},function(){
                        var parentId=$(me).next().val();
                        if(flag=="1"){//产品系列
                            loadPrdType(Id);
                        }else if(flag=="2"){//产品名称
                            clickPrdType(parentId,Id);
                        }
                    });
                }else{
                    layer.msg(data.msg,{time:1000});
                }
            });
        }
    });
}
//产品详情的查看修改或者新增
function showFormView(id,parentId,flag,add,me,type){
     console.log("flag:"+flag+"~~"+"add:"+add);
    debugger
    // var a = $('#product_type').val();
    // if(type=='undefined'){
    //     type = $('#product_type').val();
    // }
    // if ($("#pro_series_type").val() == '0')
    // {
    //     $("#product_periods").text('每期天数');
    //     $("#periods_day").text('天/期');
    // }
    // else
    // {
    //     $("#product_periods").text('期数');
    //     $("#periods_day").text('期');
    // }
    /*
     $("#localImag img").attr("src","");
     $("#file").val("");*/
    $("#cpNumber").val("").attr("readOnly",false);
    $("#cpName").val("").attr("readOnly",false);
    // $("#periods").val("").attr("readOnly",false);
    // $("#contractRate").val("").attr("readOnly",false);
    // $("#multipleRate").val("").attr("readOnly",false);
    //var payment=dataList.payment;
    $("#parentInfo").val("").attr("disabled",false);

    //产品期限单位
    $("#product_term_unit").val("").attr("disabled",false);
    //产品期限起始日期
    $("#product_term_min").val("").attr("readOnly",false);
    //产品期限结束日期
    $("#product_term_max").val("").attr("readOnly",false);
    //申请额度最小值
    $("#apply_quota_min").val("").attr("readOnly",false);
    //申请额度最大值
    $("#apply_quota_max").val("").attr("readOnly",false);
    //是否计算居间服务费
    $("#service_charge").val("").attr("disabled",false);
    //是否提前还款
    $("#repayment").val("").attr("disabled",false);

    $("#repayment_days").val("").attr("readOnly",false);

    // $("#actualLowerLimit").val("").attr("readOnly",false);
    // $("#actualUpperLimit").val("").attr("readOnly",false);
    // $("#creditProtectDay").val("").attr("readOnly",false);
    // $("#overdueProtectDay").val("").attr("readOnly",false);
    // $("#interestRate").val("").attr("readOnly",false);
    // $("#interestRemark").val("").attr("readOnly",false);
    // $("#contractViolateRate").val("").attr("readOnly",false);
    // $("#contractViolateRemark").val("").attr("readOnly",false);
    // $("#bailRate").val("").attr("readOnly",false);
    // $("#giveInfo").attr("disabled",false);
    // $("#bailRemark").val("").attr("readOnly",false);
    // $("#stagingServicesRate").val("").attr("readOnly",false);
    var addOredit=layer.open({
        type : 1,
        title:"<s class='preLine'>信贷产品信息</s>",
        maxmin : true,
        shadeClose : false,
        area : [ '57%', '40%' ],
        btn:['确认','取消'],
        content: $("#container"),
        success:function(index, layerqw){
            if(flag==0||flag==2){//查看
                Comm.ajaxPost("product/getProductInfo","id="+id+"&parentId="+parentId,function(data){
                    //pro_series_type 来判断（0-现金贷 1-商品贷）
                    // if ($("#pro_series_type").val() == '0') {//现金贷
                    //     $('#diy_duoqi').css('display','none');
                    // }else {//商品贷
                    //     $('#diy_duoqi').css('display','table-row');
                    // }
                    debugger
                    var productType=data.data.productType;
                    var dataList=data.data.data[0];
                    // console.log(dataList);
                    if(flag==0){//查看
                        $("#cpNumber").val(productType.pro_number).attr("readOnly","readOnly").css("border","none");
                        $("#cpName").val(productType.pro_name).attr("readOnly","readOnly").css("border","none");
                        //$("#periods").val(dataList.periods).attr("readOnly","readOnly").css("border","none");
                       // $("#contractRate").val(dataList.contractRate).attr("readOnly","readOnly").css("border","none");
                        //$("#multipleRate").val(dataList.multipleRate).attr("readOnly","readOnly").css("border","none");
                        var payment=dataList.payment;
                        $("#parentInfo").attr("value",payment).attr("disabled",true).css("border","none");
                        //产品期限单位
                        var productTermUnit = dataList.productTermUnit;
                        $("#product_term_unit").attr("value",productTermUnit).attr("disabled",true).css("border","none");
                        //产品期限起始日期
                        $("#product_term_min").val(dataList.productTermMin).attr("readOnly","readOnly").css("border","none");
                        //产品期限结束日期
                        $("#product_term_max").val(dataList.productTermMax).attr("readOnly","readOnly").css("border","none");
                        //申请额度最小值
                        $("#apply_quota_min").val(dataList.applyQuotaMin).attr("readOnly","readOnly").css("border","none");
                        //申请额度最大值
                        $("#apply_quota_max").val(dataList.applyQuotaMax).attr("readOnly","readOnly").css("border","none");
                        //是否计算居间服务费
                        var serviceCharge = dataList.serviceCharge;
                        $("#service_charge").attr("value",serviceCharge).attr("disabled",true).css("border","none");
                        //是否提前还款
                        var repayment = dataList.repayment;
                        $("#repayment").attr("value",repayment).attr("disabled",true).css("border","none");

                        if(repayment == '1'){//该选择为提前还款
                            $(".repayment").css({"display":"table-cell"});
                            //提前还款时间
                            $("#repayment_days").val(dataList.repaymentDays).attr("readOnly","readOnly").css("border","none");
                        }else if(repayment == '0'){
                            $(".repayment").css({"display":"none"});
                            $("#repayment_days").val('');
                        }


                        //
                        // $("#actualLowerLimit").val(dataList.actualLowerLimit).attr("readOnly","readOnly").css("border","none");
                        // $("#actualUpperLimit").val(dataList.actualUpperLimit).attr("readOnly","readOnly").css("border","none");
                        // $("#creditProtectDay").val(dataList.creditProtectDay).attr("readOnly","readOnly").css("border","none");
                        // $("#overdueProtectDay").val(dataList.overdueProtectDay).attr("readOnly","readOnly").css("border","none");
                        // $("#interestRate").val(dataList.interestRate).attr("readOnly","readOnly").css("border","none");
                        // $("#interestRemark").val(dataList.interestRemark).attr("readOnly","readOnly").css("border","none");
                        // $("#contractViolateRate").val(dataList.contractViolateRate).attr("readOnly","readOnly").css("border","none");
                        // $("#contractViolateRemark").val(dataList.contractViolateRemark).attr("readOnly","readOnly").css("border","none");
                        // $("#bailRate").val(dataList.bailRate).attr("readOnly","readOnly").css("border","none");
                        // var bailPayMode=dataList.bailPayMode;
                        // $("#giveInfo").attr("value",bailPayMode).attr("disabled",true).css("border","none");
                        // $("#bailRemark").val(dataList.bailRemark).attr("readOnly","readOnly").css("border","none");
                        // $("#stagingServicesRate").val(dataList.stagingServicesRate).attr("readOnly","readOnly").css("border","none");
                        // //清空选择方式 自定义天数输入框
                        // $('#diy_type').val('');
                        // $('#diy_days').val('');
                        //
                        // //选择方式
                        // $('#diy_type').val(dataList.diyType);
                        // if(dataList.diyType == '0'){//该选择为自然月
                        //     $(".diy_days").css({"display":"none"});
                        //     $("#diy_days").val('');
                        // }else if(dataList.diyType == '1'){//该选择为自定义天数
                        //     $(".diy_days").css({"display":"table-cell"});
                        //     $("#diy_days").val(dataList.diyDays);
                        // }
                        //
                        // //设置只读
                        // $("#diy_type,#diy_days").attr("disabled",true);

                    }else{//编辑"../"+dataList.imgUrl
                        $("#cpNumber").val(productType.pro_number).attr("readOnly","readOnly");
                        $("#cpName").val(productType.pro_name).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#periods").val(dataList.periods).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#contractRate").val(dataList.contractRate).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#multipleRate").val(dataList.multipleRate).attr("readOnly",false).css("border","1px solid #ccc");
                        var payment=dataList.payment;
                        $("#parentInfo").attr("value",payment).attr("disabled",false).css("border","1px solid #ccc");
                        //产品期限单位
                        var productTermUnit = dataList.productTermUnit;
                        $("#product_term_unit").attr("value",productTermUnit).attr("disabled",false).css("border","1px solid #ccc");
                        //产品期限起始日期
                        $("#product_term_min").val(dataList.productTermMin).attr("readOnly",false).css("border","1px solid #ccc");
                        //产品期限结束日期
                        $("#product_term_max").val(dataList.productTermMax).attr("readOnly",false).css("border","1px solid #ccc");
                        //申请额度最小值
                        $("#apply_quota_min").val(dataList.applyQuotaMin).attr("readOnly",false).css("border","1px solid #ccc");
                        //申请额度最大值
                        $("#apply_quota_max").val(dataList.applyQuotaMax).attr("readOnly",false).css("border","1px solid #ccc");
                        //是否计算居间服务费
                        var serviceCharge = dataList.serviceCharge;
                        $("#service_charge").attr("value",serviceCharge).attr("disabled",false).css("border","1px solid #ccc");
                        //是否提前还款
                        var repayment = dataList.repayment;
                        $("#repayment").attr("value",repayment).attr("disabled",false).css("border","1px solid #ccc");

                        if(repayment == '1'){//该选择为提前还款
                            $(".repayment").css({"display":"table-cell"});
                            //提前还款
                            $("#repayment_days").val(dataList.repaymentDays).attr("readOnly",false).css("border","1px solid #ccc");

                        }else if(repayment == '0'){
                            $(".repayment").css({"display":"none"});
                            $("#repayment_days").val('');
                        }
                        // $("#actualLowerLimit").val(dataList.actualLowerLimit).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#actualUpperLimit").val(dataList.actualUpperLimit).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#creditProtectDay").val(dataList.creditProtectDay).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#overdueProtectDay").val(dataList.overdueProtectDay).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#interestRate").val(dataList.interestRate).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#interestRemark").val(dataList.interestRemark).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#contractViolateRate").val(dataList.contractViolateRate).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#contractViolateRemark").val(dataList.contractViolateRemark).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#bailRate").val(dataList.bailRate).attr("readOnly",false).css("border","1px solid #ccc");
                        // var bailPayMode=dataList.bailPayMode;
                        // $("#giveInfo").attr("value",bailPayMode).attr("disabled",false).css("border","1px solid #ccc");
                        // $("#bailRemark").val(dataList.bailRemark).attr("readOnly",false).css("border","1px solid #ccc");
                        // $("#stagingServicesRate").val(dataList.stagingServicesRate).attr("readOnly",false).css("border","1px solid #ccc");
                        $("#picName").val(dataList.imgUrl);
                        //清空选择方式 自定义天数输入框
                        // $('#diy_type').val('');
                        // $('#diy_days').val('');
                        // //选择方式
                        // $('#diy_type').val(dataList.diyType);
                        // if(dataList.diyType == '0'){//该选择为自然月
                        //     $(".diy_days").css({"display":"none"});
                        //     $("#diy_days").val('');
                        // }else if(dataList.diyType == '1'){//该选择为自定义天数
                        //     $(".diy_days").css({"display":"table-cell"});
                        //     $("#diy_days").val(dataList.diyDays);
                        // }
                        // //去除只读属性
                        // $("#diy_type,#diy_days").attr("disabled",false);
                    }
                });
            }else if(flag==1){//新增
                debugger;
                Comm.ajaxPost("product/getProductInfo","id="+id+"&parentId="+parentId,function(data){
                    var productType=data.data.productType;
                    $("#productId").val(id);
                    var divFrom=$("#divFrom input");
                    for(var i=0;i<divFrom.length;i++){
                        $(divFrom[i]).css("border","1px solid #ccc");
                    }
                    // $("#interestRemark").css("border","1px solid #ccc");
                    // $("#contractViolateRemark").css("border","1px solid #ccc");
                    // $("#bailRemark").css("border","1px solid #ccc");
                     //产品序列 产品名称
                     $("#cpNumber").val($("#cpNumber1").val()).attr("readOnly","readOnly");
                     $("#cpName").val(productType.pro_name);
                     $("#localImag img").attr("src","");
                     $("#localImag input").val("");
                    // //清空选择方式 自定义天数输入框
                    //
                    // $('#repayment_min').val('');
                    // $('#repayment_max').val('');
                    // //去除只读属性
                    // $("#diy_type,#diy_days").attr("disabled",false);

                });
                //pro_series_type 来判断（0-现金贷 1-商品贷）
                // if ($("#pro_series_type").val() == '0') {//现金贷
                //     $('#diy_duoqi').css('display','none');
                // }else {//商品贷
                //     $('#diy_duoqi').css('display','table-row');
                // }
            }
        },
        yes: function(index, layerqw){//新增
            debugger;
            if(flag == '0'){
                layer.closeAll();
                return ;
            }
            debugger
            if($("#cpName").val()==""){
                layer.msg("请输入产品名称！",{time:2000});
                return;
            }else{
                var reg=/^[\s|\S]*$/;
                var valTest=reg.test($("#cpName").val());
                if(!valTest){
                    layer.msg("请输入合法的产品名称！",{time:2000});
                    return;
                }
            }
            // if($("#periods").val()!=""){
            //     var reg=/^[0-9]*$/;
            //     var valTest=reg.test($("#periods").val());
            //     if(!valTest){
            //         layer.msg("产品期数只能输入数字！！",{time:2000});
            //         return;
            //     }
            // }
            // if($("#contractRate").val()!=""){
            //     var reg=/^[0-9]+(.[0-9]{0,4})?$/;
            //     var valTest=reg.test($("#contractRate").val());
            //     if(!valTest){
            //         layer.msg("只能输入小数点最多四位数字！！！",{time:2000});
            //         return;
            //     }
            // }
            // //贷后编号
            // if($("#actualLowerLimit").val()!=""){
            //     var reg=/^[0-9]*$/;
            //     var valTest=reg.test($("#actualLowerLimit").val());
            //     if(!valTest){
            //         layer.msg("只能输入数字！",{time:2000});
            //         return;
            //     }
            // }
            /**
             * @create 韩梅生
             * 申请额度最小值
             */
            if($("#apply_quota_min").val() === ""){
                layer.msg("请输入最小申请额度！",{time:2000});
                return;
            }else if($("#apply_quota_min").val()!=""){
                var reg=/^[0-9]*$/;
                var valTest=reg.test($("#product_term_min").val());
                if(!valTest){
                    layer.msg("只能输入数字！",{time:2000});
                    return;
                }
            }
            /**
             * @create 韩梅生
             * 申请额度最大值
             */
            if($("#apply_quota_max").val() === ""){
                layer.msg("请输入最大申请额度！",{time:2000});
                return;
            }else if($("#apply_quota_max").val()!=""){
                var reg=/^[0-9]*$/;
                var valTest=reg.test($("#product_term_max").val());
                if(!valTest){
                    layer.msg("只能输入数字！",{time:2000});
                    return;
                }
                if(parseInt($("#apply_quota_max").val()) < parseInt($("#apply_quota_min").val())){
                    layer.msg("最大申请额度不能小于最小申请额度！",{time:2000});
                    return;
                }
            }
            /**
             * @create 韩梅生
             * 申请期限最小天数
             */
            if($("#product_term_min").val() === ""){
                layer.msg("请输入申请期限最小天数！",{time:2000});
                return;
            }else if($("#product_term_min").val()!=""){
                var reg=/^[0-9]*$/;
                var valTest=reg.test($("#product_term_min").val());
                if(!valTest){
                    layer.msg("只能输入数字！",{time:2000});
                    return;
                }

            }
            /**
             * @create 韩梅生
             * 申请期限最大天数
             */
            if($("#product_term_max").val() === ""){
                layer.msg("请输入申请期限最大天数！",{time:2000});
                return;
            }else if($("#product_term_max").val()!=""){
                var reg=/^[0-9]*$/;
                var valTest=reg.test($("#product_term_max").val());
                if(!valTest){
                    layer.msg("只能输入数字！",{time:2000});
                    return;
                }
                if(parseInt($("#product_term_max").val()) < parseInt($("#product_term_max").val())){
                    layer.msg("最大申请期限不能小于最小申请期限！",{time:2000});
                    return;
                }
            }
            /**
             * @create 韩梅生
             * 还款时限
             */
            if($("repayment").val() === "1"){
                if($("#repayment_days").val() === ""){
                    layer.msg("请输入还款时限！",{time:2000});
                    return;
                }else if($("#repayment_days").val()!=""){
                    var reg=/^[0-9]*$/;
                    var valTest=reg.test($("#repayment_days").val());
                    if(!valTest){
                        layer.msg("只能输入数字！",{time:2000});
                        return;
                    }
                }
            }




            //新增字段验证
            // if ($("#pro_series_type").val() == '1') {//商品贷
            //     if($("#diy_type").val() == ''){
            //         layer.msg("选择方式不能为空！",{time:2000});return ;
            //     }
            //     if($("#diy_type").val() == '1'){
            //         if($("#diy_days").val() == '' ){
            //             layer.msg("自定义天数不能为空！",{time:2000});return ;
            //         }
            //         if( !/^[1-9]\d*$/.test($("#diy_days").val())){
            //             layer.msg("自定义天数必须为正整数！",{time:2000});return ;
            //         }
            //     }
            // }
            if(add==0){//新增
                $("#productId").val(parentId);
                //表单提交
                var datad=$("#productForm").serializeJson();
                datad = JSON.stringify(datad);
                Comm.ajaxPost("product/addOrUpdateProductDetail",{data:datad,flag:"add",type:type},function(data){
                    layer.msg(data.msg,{time:1000},function(){
                        layer.closeAll();
                        renderPrd(parentId,false,type);
                    })
                });
            }else if(add==1){//编辑
                $("#productId").val(parentId);
                var productId=$("#productId").val();
                //表单提交
                $("#editId").val(id);
                var datad=$("#productForm").serializeJson();
                datad = JSON.stringify(datad);
                Comm.ajaxPost("product/addOrUpdateProductDetail",{data:datad,flag:"update"},function(data){
                    layer.msg(data.msg,{time:1000},function(){
                        layer.closeAll();
                        renderPrd(productId,false,type);
                    })
                });

            }
        },
    });
}

//下面用于图片上传预览功能
function setImagePreview(avalue) {
    var docObj=document.getElementById("file");

    var imgObjPreview=document.getElementById("preview");
    if(docObj.files &&docObj.files[0])
    {
//火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '272px';
        imgObjPreview.style.height = '180px';
//imgObjPreview.src = docObj.files[0].getAsDataURL();



//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }
    else
    {
//IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag");
//必须设置初始大小
        localImagId.style.width = "272px";
        localImagId.style.height = "180px";
//图片异常的捕捉，防止用户修改后缀来伪造图片
        try{
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        }
        catch(e)
        {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}
function setImagePreview1(avalue) {
    var docObj=document.getElementById("file1");

    var imgObjPreview=document.getElementById("preview1");
    if(docObj.files &&docObj.files[0])
    {
//火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '272px';
        imgObjPreview.style.height = '180px';
//imgObjPreview.src = docObj.files[0].getAsDataURL();

//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }
    else
    {
//IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag1");
//必须设置初始大小
        localImagId.style.width = "272px";
        localImagId.style.height = "180px";
//图片异常的捕捉，防止用户修改后缀来伪造图片
        try{
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        }
        catch(e)
        {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}
//搜索
function searchProduct() {
    var prdName=$("#prdName").val();
    Comm.ajaxPost("product/selectProduct","proName="+prdName+"&SeriesId="+$("#v3ParentId").val(),function(data){
        var productList=data.data;
        var msg = data.msg;  //返回的错误信息
        if(msg=="无此产品！"){
            layer.msg(msg,{time:1000});
            return;
        }
        for(var i=0;i<productList.length;i++){
            renderPrd(productList[i].id,true);
        }
    });
}
//省市
function onSelectChange(obj,toSelId,flag){
    if (flag == 1) {
        setSelect(obj,toSelId);
    }else{
        setSelect(obj.value,toSelId);
    }
}
function setSelect(fromSelVal,toSelId){
    document.getElementById(toSelId).innerHTML="";
    //调省市区接口
    var result = {};
    if (!toSelId) {
        result.flag = "province";
    } else {
        result.flag = toSelId;
    }
    result.parentId = fromSelVal;
    Comm.ajaxPost('pcd/getPCD',JSON.stringify(result),function(result){
        createSelectObj(result,toSelId);
    },"application/json",'','','',false)
}
function createSelectObj(data,toSelId){
    var arr = data.data;
    if(arr != null && arr.length>0){
        var obj = document.getElementById(toSelId);
        obj.innerHTML="";
        var nullOp = document.createElement("option");
        nullOp.setAttribute("value","");
        nullOp.appendChild(document.createTextNode("-请选择-"));
        obj.appendChild(nullOp);
        for(var o in arr){
            var op = document.createElement("option");
            op.setAttribute("value",arr[o].id);
            //op.text=arr[o].name;//这一句在ie下不起作用，用下面这一句或者innerHTML
            if (arr[o].provinceName) {
                op.appendChild(document.createTextNode(arr[o].provinceName));
            } else if (arr[o].cityName) {
                op.appendChild(document.createTextNode(arr[o].cityName));
            } else {
                op.appendChild(document.createTextNode(arr[o].districtName));
            }
            obj.appendChild(op);
        }

    }
    else
    {
        $('#'+toSelId).html("<option value=''>-请选择-</option>");
    }
}


$("#province").on('change',function(){
    var htmlSel=$("#province option:selected").html();
    if(htmlSel=="-请选择-"){
        $("#city").html("<option value=''>-请选择-</option>");
        $("#district").html("<option value=''>-请选择-</option>")
    }
    else if(htmlSel=="")
    {
        $("#city").html("<option value=''>-请选择-</option>");
        $("#district").html("<option value=''>-请选择-</option>")
    }
    else{
        $("#district").html("<option value=''>-请选择-</option>")
    }
})
$("#cityId").on('change',function(){
    var htmlSel=$("#city option:selected").html();
    if(htmlSel=="-请选择-"){
        $("#district").html("<option value=''>-请选择-</option>")
    }
    else if(htmlSel=="")
    {
        $("#district").html("<option value=''>-请选择-</option>")
    }
})

function onSelectChange1(obj,toSelId,flag){
    if (flag == 1) {
        setSelect1(obj,toSelId);
    }else{
        setSelect1(obj.value,toSelId);
    }
}
function setSelect1(fromSelVal,toSelId){
    document.getElementById(toSelId).innerHTML="";
    //调省市区接口
    var result = {};
    if (!toSelId) {
        result.flag = "province";
    } else {
        result.flag = toSelId;
    }
    result.parentId = fromSelVal;
    Comm.ajaxPost('pcd/getPCD',JSON.stringify(result),function(result){
        createSelectObj1(result,toSelId);
    },"application/json",'','','',false)
}
function createSelectObj1(data,toSelId){
    var arr = data.data;
    if(arr != null && arr.length>0){
        var obj = document.getElementById(toSelId+'1');
        obj.innerHTML="";
        var nullOp = document.createElement("option");
        nullOp.setAttribute("value","");
        nullOp.appendChild(document.createTextNode("-请选择-"));
        obj.appendChild(nullOp);
        for(var o in arr){
            var op = document.createElement("option");
            op.setAttribute("value",arr[o].id);
            //op.text=arr[o].name;//这一句在ie下不起作用，用下面这一句或者innerHTML
            if (arr[o].provinceName) {
                op.appendChild(document.createTextNode(arr[o].provinceName));
            } else if (arr[o].cityName) {
                op.appendChild(document.createTextNode(arr[o].cityName));
            } else {
                op.appendChild(document.createTextNode(arr[o].districtName));
            }
            obj.appendChild(op);
        }

    }
    else
    {
        $('#'+toSelId).html("<option value=''>-请选择-</option>");
    }
}
$("#province1").on('change',function(){
    var htmlSel=$("#province1 option:selected").html();
    if(htmlSel=="-请选择-"){
        $("#city1").html("<option value=''>-请选择-</option>");
        $("#district1").html("<option value=''>-请选择-</option>")
    }
    else if(htmlSel=="")
    {
        $("#city1").html("<option value=''>-请选择-</option>");
        $("#district1").html("<option value=''>-请选择-</option>")
    }
    else{
        $("#district1").html("<option value=''>-请选择-</option>")
    }
})
$("#cityId1").on('change',function(){
    var htmlSel=$("#city1 option:selected").html();
    if(htmlSel=="-请选择-"){
        $("#district1").html("<option value=''>-请选择-</option>")
    }
    else if(htmlSel=="")
    {
        $("#district1").html("<option value=''>-请选择-</option>")
    }
})

//动态加载下拉框内容
function apendSelect() {
    Comm.ajaxPost('product/apendSelected',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#productType').append(btndel);$('#productType2').append(btndel);
                }
            }
        },"application/json"
    );

    // Comm.ajaxPost('product/apendSelect',null, function (data) {
    //         if(data.code==0){
    //             var map = [];map = data.data;
    //             for (var i = 0 ;i<map.length;i++){
    //                 var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
    //                 $('#parentInfo').append(btndel);
    //             }
    //         }
    //     },"application/json"
    // );
    Comm.ajaxPost('product/platformType',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#productType4').append(btndel);
                    $('#productType3').append(btndel);
                }
            }
        },"application/json"
    );
}

function sureDiyType(value){
    if(value == '1'){//自定义天数
    $('.diy_days').css('display','table-cell');
    $('#diy_days').val('');
    }else if(value == '0'){//自然
        $('.diy_days').css('display','none');
        $('#diy_days').val('');
    }
}
/*$('select[name="provinces_id"]').on('change',function(){
 var htmlSel=$("#province option:selected").html();
 if(htmlSel=="-请选择-"){
 $('select[name="city_id"]').html("<option value=''>-请选择-</option>");
 $('select[name="distric_id"]').html("<option value=''>-请选择-</option>")
 }
 else if(htmlSel=="")
 {
 $('select[name="city_id"]').html("<option value=''>-请选择-</option>");
 $('select[name="distric_id"]').html("<option value=''>-请选择-</option>")
 }
 else{
 $('select[name="distric_id"]').html("<option value=''>-请选择-</option>")
 }
 })
 $('select[name="city_id"]').on('change',function(){
 var htmlSel=$('select[name="city_id"] option:selected').html();
 if(htmlSel=="-请选择-"){
 $('select[name="distric_id"]').html("<option value=''>-请选择-</option>")
 }
 else if(htmlSel=="")
 {
 $('select[name="distric_id"]').html("<option value=''>-请选择-</option>")
 }
 })*/











































