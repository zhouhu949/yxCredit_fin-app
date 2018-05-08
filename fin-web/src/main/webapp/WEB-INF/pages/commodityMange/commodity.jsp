<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <link rel="stylesheet" href="${ctx}/resources/css/menu.css" />
    <script src="${ctx}/resources/js/common/customValid.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/checkboxTree.js${version}" type="text/javascript"></script>
    <script src="${ctx}/resources/js/commodityMange/commodity.js${version}"></script>
    <script src="https://www.promisejs.org/polyfills/promise-6.1.0.js"></script>
    <script src="https://gosspublic.alicdn.com/aliyun-oss-sdk-4.4.4.min.js"></script>
    <title>商品配置</title>
    <style type="text/css">
        #menu-edit .add_menu tr{
            margin-bottom:10px;
        }
        #menu-edit .add_menu td{
            line-height:39px;
        }
        #apex1 {
            /* padding-left: 37px;*/
            width: 99px;
            margin-left: 10px;
        }
        #editContent{
            padding:8px 78px 24px;
        }
        .addMaterial {
            width: 100px;
            height: 100px;
        }
        .picShow{
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 100px;
            width: 100px;
        }
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <input type="hidden" id="parentIdHtml" value="">
    <div id="treeMenu" style="width:20%;float: left;border-right: 2px solid #ccc;">
        <div style="height: 40px;line-height: 40px;">
            <div style="text-align: center;background: #0679CC;color: white;">商品配置</div>
            <div>
                <a  href="javascript:void(0)" style="display: none;"></a>
                <a href="javascript:void(0)"></a>
            </div>
        </div>
        <div style="height: 80%;overflow:auto;">
            <div id="jstree"></div>
        </div>
        <div id="menu-edit" style="display:none">
            <input type="hidden" id="parentId" value="">
            <input type="hidden" id="type" value="">
            <form name="form" id="myForm" method="post">
                <div class="add_menu">
                    <div id="Adding_menu">
                        <div  id="editContent" class="page-content">
                            <table cellpadding="0" cellspacing="0" width="100%">
                           <%--     <input type="hidden" id="parentId"  name="parentId">--%>
                                <tr>
                                    <td class="label_name">菜单名称</td>
                                    <td>
                                        <input name="merchandiseName" id="merchandiseName" type="text" class="addtext" value="" style="width: 290px" title="" ht-validate="{maxlength:100}" />
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>
                             <%--   <tr>
                                    <td class="label_name">是否显示</td>
                                    <td>
                                        <div class="radio ">
                                            <label>
                                                <input type="radio" class="ace" name="state" value="1" checked/>
                                                <span class="lbl">是</span>
                                            </label>
                                        </div>
                                        <div class="radio ">
                                            <label>
                                                <input type="radio" class="ace" name="state" value="0" />
                                                <span class="lbl">否</span>
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">类型</td>
                                    <td>
                                        <select name="apex1" size="1" id="apex1">
                                            <option value="0" >菜单</option>
                                            <option value="1" >按钮</option>
                                        </select>
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">商品类型</td>
                                    <td style="padding-left: 10px;">
                                        <select name="type" size="1" id="type">
                                            <option value="">请选择</option>
                                            <option value="1">商品类型</option>
                                            <option value="2">品牌名称</option>
                                            <option value="3">具体型号</option>
                                            <option value="4">具体版本</option>
                                        </select>
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>--%>
                                <tr>
                                    <td class="label_name">描述</td>
                                    <td>
                                        <input name="descri" id="descri" type="text" value="" style="width: 290px"  title=""/>
                                    </td>
                                </tr>
                               <%-- <tr class="label_name" >
                                    <td class="itemsLabel">图片url</td>
                                    <td >
                                        <input type="hidden" class="imgHidden"name="type1" value="1">
                                        <input type="hidden" class="imgHidden"  id="imgUrl" name="imgUrl" value=""/>
                                        <input type="hidden" class="imgHidden" name="originalName" value="">
                                        <input type="hidden" class="imgHidden" name="isfront" value="0">
                                        <form action="" enctype="multipart/form-data">
                                            <div class="" style="padding-left: 10px;width: 120px;">
                                                <input type="file"  name="pic" class="picShow"  onchange="setImagePreview1(this)"/>
                                                <img class="addMaterial" style="z-index:100" id='img' src="../resources/images/photoadd.png"/>
                                                <span style="display: none" class="closeImg" onclick="closeDelete(this)">×</span>
                                            </div>
                                        </form>
                                    </td>
                                </tr>--%>
                            </table>
                        </div>
                    </div>
                </div>
            </form>
            <div style="margin:0 45%;" >
                <button type="button" class="btn btn-primary" onclick="saveData()" id="aaa">保存</button>
            </div>
        </div>
    </div>
    <div id="content" style="width: 80%;float: right;height: 100%;overflow: auto;">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li style="width: 35px;"></li>
                <li style="width: 230px;">
                    <label class="lf" style="width: 45px;">名称</label>
                    <label style="width: 130px;">
                        <input id="mname" type="text" placeholder="请输入商品名称" style="width: 130px;">
                    </label>
                </li>
            <%--    <li style="width: 230px;">
                    <label class="1f" style="width: 70px;" >所属类型</label>
                    <label >
                        <select id="mtype" style="min-width:90px;height: 28px;" >
                            <option value="">请选择</option>
                            <option value="1">商品类型</option>
                            <option value="2">品牌名称</option>
                            <option value="3">具体型号</option>
                            <option value="4">具体版本</option>
                        </select>
                    </label>
                </li>--%>
                <li style="width:300px;">
                    <button id="b_search" type="button" class="btn btn-primary queryBtn" onclick="">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
            </ul>
        </div>
        <div class="Res Manager_style">
            <div class="table_res_list">
                <table style="font-size: 14px; text-align: center;" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th></th>
                        <th>名称</th>
                        <%--<th>上级菜单</th>--%>
                      <%--  <th>类型</th>--%>
                        <th>商品类型</th>
                      <%--  <th>是否显示</th>--%>
                       <%-- <th>URL地址</th>--%>
                        <th>创建日期</th>
                       <%-- <th>最后更新日期</th>--%>
                        <th>描述</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var layerIndex,jstree,currentNode,mid,pid,act;
    (function(){
        jstree = $('#jstree').jstree({
            "core" : {
                "animation" : 0,
                "check_callback" : true,
                "themes" : { "stripes" : true },
                'data' : function (obj, callback) {
                    var jsonstr="[]";
                    var jsonarray = eval('('+jsonstr+')');
                    Comm.ajaxGet("commodity/getCommodityMenu", null, function (result) {
                        var arrays= result.data;
                        for(var i=0 ; i<arrays.length; i++){
                            var arr = {
                                "id":arrays[i].id,
                                "parent":arrays[i].parentId,
                                "text":arrays[i].merchandiseName,
                                "icon":arrays[i].icon,
                                "type":arrays[i].type
                            }
                            jsonarray.push(arr);
                        }
                    },null,false);
                    callback.call(this, jsonarray);
                }
            },
            "types" : {
                "file" : {
                    "icon" : "glyphicon glyphicon-file",
                    "valid_children" : []
                }
            },
            "plugins" : [ "contextmenu","types" ],
            "contextmenu":{
                "items":{
                    "create":null,
                    "rename":null,
                    "remove":null,
                    "ccp":null,
                    "editMenu":{
                        "label":"编辑",
                        "action":function(data){
                            mid = getSelectItem(data).id;
                            var type=getSelectItem(data).original.type;
                            var text=getSelectItem(data).original.text;
                            openMenuEdit("edit",mid,type);
                        }
                    },
                    "addMenu":{
                        "label":"添加同级菜单",
                        "action":function(data){
                            pid = getSelectItem(data).parent;
                            var thisId=getSelectItem(data).id;
                            var text=getSelectItem(data).original.text;
                            var type=getSelectItem(data).original.type;
                            console.log(getSelectItem(data));
                            console.log(type);
                            if(thisId=='0'){
                                layer.msg('根目录不能添加同级菜单',{time:1000});
                                return;
                            }
                            openMenuEdit("add",pid,type);
                        }
                    },
                    "addSubMenu":{
                        "label":"添加子菜单",
                        "action":function(data){
                            var　selectData = getSelectItem(data);
                            console.log(selectData);
                            pid = selectData.id;
                            debugger
                            var type1=getSelectItem(data).original.type;
                            var text=getSelectItem(data).original.text;
                            if(type1=='4'){
                                layer.msg('根目录不能添加子级菜单',{time:1000});
                                return;
                            }
                            var type=(parseInt(type1)+1);
                            openMenuEdit("add",pid,type);
                        }
                    }
                    //,
//                    "delMenu":{
//                        "separator_before":true,
//                        "label":"删除菜单",
//                        "action":function(data){
//                            var param = {};
//                            param.id=getSelectItem(data).id;
//                            Comm.ajaxPost("commodity/deleteCommodity", JSON.stringify(param), function (result) {
//                                layer.msg(result.msg,{time:1000},function(){
//                                    $.jstree.reference("#jstree").refresh();
//                                });
//                            },"application/json");
//                        }
//                    }
                }
            }
        });
        function getSelectItem(data){   //右键点击后的函数
            var inst = $.jstree.reference(data.reference),
                    obj = inst.get_node(data.reference);
            currentNode = obj;
            return obj;
        }
        $('#jstree').bind("activate_node.jstree", function (obj, e) {

            var cId = e.node.id;     //得到被点击节点的id
            if(cId){
                queryList(cId);
            }
        });
        $('#jstree').bind("show_contextmenu.jstree", function (obj, e) {
            // 如果是按钮级别的菜单， 隐藏
            if(e.node.original.type){
                $(".vakata-contextmenu-disabled").hide();
            }
        });
        function openMenuEdit(action,id,type){
            act = action;
            console.log('11111111111');
            console.log(type);
            var title = action == "edit"? "编辑":"添加";
            $("#aaa").show();
            if(action == "edit"){
                var param = {};
                param.id=id;
                Comm.ajaxPost("commodity/getCommodity",  JSON.stringify(param), function (result) {
                    var detailData = result.data;
                    $("input[name='merchandiseName']").val(detailData.merchandiseName);
                    var oRadio = document.getElementsByName("state");
                    for(var i=0;i<oRadio.length;i++) //循环
                    {
                        if(oRadio[i].value==detailData.state) //比较值
                        {
                            oRadio[i].checked=true; //修改选中状态
                            break; //停止循环
                        }
                    }
                    $('.addMaterial').attr('src','../resources/images/photoadd.png');
                    $('#img').attr('src',detailData.imgUrl);
                    $("#apex1").val(detailData.apex1);
                    $("#img").val(detailData.imgUrl);
                    $("input[name='descri']").val(detailData.descri);
                    $("input[name='apex3']").val(detailData.apex3);
                    $("input[name='merchandiseType']").val(detailData.merchandiseType);
                    $("#type").val(detailData.type);
                    $("#parentId").val(detailData.parentId);
                    $('#activity_img_fileName').val(detailData.imgUrl);
                    //取消只读
                    $("#merchandiseName").removeAttr("disabled");
                    $("input[name='state']").removeAttr("disabled");
                    $("#apex1").removeAttr("disabled");
                    $("#type").removeAttr("disabled");
                    $("input[name='descri']").removeAttr("disabled");
                    $("input[name='pic']").removeAttr("disabled");
                },"application/json");
            }
            // 添加菜单
            if(action == "add"){
                $("#img").attr("src","../resources/images/photoadd.png");
                $("#parentId").val(id);
                //$("input[name='imgUrl']").val('');
                $("input[name='merchandiseName']").val('');
                $("input[name='apex3']").val('');
                /*$("input[name='state']").val('');*/
                $('input:radio[name=state]').attr('checked',false);
                $("input[name='descri']").val('');
                $("input[name='merchandiseType']").val('');
                $("#type").val(type);
                //取消只读
                $("#merchandiseName").removeAttr("disabled");
                $("input[name='state']").removeAttr("disabled");
                $("#apex1").removeAttr("disabled");
                $("#type").removeAttr("disabled");
                $("input[name='descri']").removeAttr("disabled");
                $("input[name='pic']").removeAttr("disabled");
            }
            layerIndex = layer.open({
                type : 1,
                title : title,
                shadeClose : false, //点击遮罩关闭层
                area : [ '550px', '300px' ],
                content : $('#menu-edit'),
            });
        }
    })();
    function saveData(){
        var commodity = {};
        var url = "";
        if(act == "edit"){
            url = "commodity/updateCommodity";

            commodity.id = mid;
        }else if(act == "add"){
            url = "commodity/addCommodity";
            commodity.parentId = pid;
        }
        $("input[name='merchandiseName']").val($.trim($("input[name='merchandiseName']").val()));
        $("input[name='descri']").val($.trim($("input[name='descri']").val()));
        commodity.merchandiseName = $("input[name='merchandiseName']").val();
        commodity.imgUrl = $("input[name='imgUrl']").val();
        commodity.parentId=$("#parentId").val();
        commodity.type=$("#type").val();
        console.log('2222');
        console.log(type);
        commodity.apex3 = $("input[name='apex3']").val();
        commodity.state = $('input:radio[name=state]:checked').val();
        commodity.descri = $("input[name='descri']").val();
        commodity.apex1 = $("#apex1").val();
        debugger
        //menu.icon = $("input[name='icon']").val();
        commodity.type = $("#type").val();
      /*  if($("#type").val()==1){
            commodity.merchandiseType = "商品类型";
        }else if($("#type").val()==2){
            commodity.merchandiseType = "品牌名称";
        } else if($("#type").val()==3){
            commodity.merchandiseType = "具体型号";
        }else if($("#type").val()==4){
            commodity.merchandiseType = "具体版本";
        }*/
        if($("input[name='merchandiseName']").val()==""){
            layer.alert("菜单名称不能为空！",{icon: 2, title:'操作提示'});
            return
        }
        if($("input[name='descri']").val()==""){
            layer.alert("商品描述不能为空！",{icon: 2, title:'操作提示'});
            return
        }
       /* if($("#type").val()==0){
            layer.alert("请选择商品类型！",{icon: 2, title:'操作提示'});
            return
        }*/
        Comm.ajaxPost(url, JSON.stringify(commodity), function (result) {
            layer.msg(result.msg,{icon:1,offset:'200px',time:1000},function(){
                console.log('123456')
                console.log(result);
                if (result.code == "0") {
                    layer.closeAll();
                    g_userManage.tableOrder.ajax.reload();
                    $.jstree.reference("#jstree").refresh();
                    layer.close(layerIndex);
                }
            });
            document.getElementById("myForm").reset();
            g_userManage.tableOrder.ajax.reload();
        },"application/json");

    }


    function setImagePreview1(me){
        // layer.load(2);
        var uploadImgNum = $(me).parent().parent().parent().parent().attr("id");
        var uploadImgType = $(me).parent().parent().parent().find('input[name="type1"]').val();
        var imgObjPreview=me.nextElementSibling;
        if(me.files && me.files[0]){
            var type=me.files[0].type;
            if(type.indexOf("image")==-1){
                // layer.closeAll();
                layer.msg("您上传的图片格式不正确，请重新选择!",{time:1000});
                return;
            }
            //火狐下，直接设img属性
            imgObjPreview.src = window.URL.createObjectURL(me.files[0]);
            var index =layer.load(2);
            /**********阿里云js sdk上传文件************/
            var val= me.value;
            var suffix = val.substr(val.indexOf("."));
            var obj=timestamp();  // 这里是生成文件名
            var storeAs = 'upload-file/'+"/"+obj+suffix;  //命名空间
            var file = me.files[0];
            console.log(file.name + ' => ' + storeAs);
            Comm.ajaxUpload('accessToken/getToken','',function (res) {
                var result = res.data;
                var client = new OSS.Wrapper({
                    accessKeyId: result.AccessKeyId,
                    accessKeySecret: result.AccessKeySecret,
                    stsToken: result.SecurityToken,
                    secure: true,
                    endpoint: 'https://oss-cn-beijing.aliyuncs.com',
                    bucket: 'miaofuxianjindai-001'
                });
                client.multipartUpload(storeAs, file).then(function (result) {
                    ;
                    console.log(result);
                    var res = result.res.requestUrls[0].split('?')[0];
                    /* if(uploadImgType!='1'){
                     $("#"+uploadImgNum).append(uploadImgHtml);
                     var num = uploadImgNum.replace(/[^0-9]/ig,"");
                     $("#"+uploadImgNum).find("input[name=type]").val(num);
                     }*/
                    $(me).parent().parent().parent().find("input[name=originalName]").val(file.name);
                    $(me).parent().parent().parent().find("input[name=imgUrl]").val(res);
                    $(me).parent().parent().parent().addClass("getFanQiZha");
                    // $(me).parent().append("<span class='closeImg' onclick='closeDelete(this)'>×</span>");
                    //layer.closeAll();
                    layer.close(index);
                }).catch(function (err) {
                    layer.closeAll();
                    console.log(err);
                });
            })
        }
        return true;
    }
    function closeDelete(me){
        $('.addMaterial').attr('src','../resources/images/photoadd.png');
    }
</script>
</html>