<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/treeTable/css/data.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/treeTable/css/jquery.treetable.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/treeTable/css/jquery.treetable.theme.default.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/treeTable/css/screen.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/zTree/css/zTreeStyle/zTreeStyle.css" />
    <script type="text/javascript" src="${ctx}/resources/js/systemMange/magData.js${version}"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <style>
        #dept td,#company td{border:none}
        .imagediv{
            width:150px;
            height:150px;
            background-color:white;
            text-align: center;
            float: left;
            position: relative;
            margin-right: 1em;
        }
        .addMaterial{
            width:150px;
            height:150px;
            vertical-align: middle;
        }
        .picShow{
            position:absolute;
            z-index:100;
            opacity:0;
            filter:alpha(opacity=0);
            height:150px;
            width:150px;
            readonly:true;
        }
        .container{
            display: inline-block;
        }
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;text-align: center;}
        .tdTitle{text-align: right!important;width: 120px;font-weight:bold;}
        #container td input{background-color: #fff!important; border:none!important;text-align: center!important;}
        #imageCard {width:40px;height: 40px;float:left;margin-right:1em;}
        .imgShow{max-width: 100%;max-height: 100%;}
        #imageCard .imgShowTd{padding-left: 1em;}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        #showNewImg ul{text-align: left}
        #showNewImg ul li{display: inline-block;width:25%;border:1px solid #ddd;text-align: center;margin:.2em 0;}
        .imgDiv{width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto;}
        /*显示图片*/
        #imageCard .imgShow,#cheatImg .imgShow,#phoneImg .imgShow{width:40px;height: 40px;float:left;margin-right:1em;}
        #imageCard .imgShowTd{padding-left: 1em;text-align:left}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        .align{width:110px;}
        #divFrom td{text-align: center;}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
        #divFrom .telRecord{text-align: left;font-weight: bold;}
        .picShow{
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 150px;
            width: 150px;
            top:1px;
            left:0;
        }
    </style>
    <script>
        //图片上传
        function setImagePreview(me,sign){
            debugger
//            $(me).parent().prev().prev().prev().val($('#activity_id').val());//活动id
//            var id = $('#activity_id').val();
            var docObj=me;
            var imgObjPreview=me.nextElementSibling;
            if(docObj.files && docObj.files[0]){
                //火狐下，直接设img属性
                imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                $(me).parent().parent().ajaxSubmit({
                    type: "POST",
                    url: "merUpload",
                    success: function (data) {
                        data = eval('('+data+')');
                        $(me).parent().parent().prev().val(data.data.activity_img_id);//图片id
                        $(me).next().attr("src",data.data.activity_img_id);
                        layer.msg(data.msg,{time:2000});
                    },
                    error: function (XMLHttpRequest, textStatus, thrownError) {
                    }
                });
            }else{

            }
            return true;
        }
        function closeDelete(me){
            $(me).next().attr('src','../resources/images/photoadd.png');
        }

        function xx(val){
            if(val==1){
                $("#tu1").css({"display":"block"});
                $("#tu2").css({"display":"none"});
                $("#code1").css({"display":"none"});
            }else{
                $("#tu1").css({"display":"none"});
                $("#tu2").css({"display":"block"});
                $("#code1").css({"display":"block"});
            }
        }
    </script>
</head>
<body>
<input type="hidden" id="checkStatus" val=""/>
<input type="text" id="hasComp" style="display: none;" value="">
<div class="ly_positionBar">
    <span class="position">组织架构</span>
</div>
<div class="ly_padding" style="padding: 0 10px">
   <%-- <div class="ly_ctnWrap">
        <!-- 用ztree展示部门模块 -->
        <div id="deptTreeGrid" data-options="title:'部门'" class="s_treeGrid" style='padding-top:0;'>
            <div style="width:300px;" class="s_tabPanel">
                <span class='active tri'>组织架构</span>
                &lt;%&ndash; <span class='tri' id="hisDept">历史架构</span>&ndash;%&gt;
            </div>
            <div id="treeGrid"></div>
        </div>
    </div>--%>
    <div>
        <button class="btn btn-primary addBtn" type="button" id="addBtn" style="margin-top:5px">添加</button>
        <button class="btn btn-primary addBtn" type="button" id="register" style="margin-top:5px">注册</button>
    </div>
    <div class="span12" id="div-table-container">
        <table class="treetable" id="magData" cellspacing="0" width="100%" style="border-radius:5px">
            <thead>
            <tr>
                <th style="width:400px">名称</th>
                <th style="width:200px">公司全称</th>
                <th style="width:150px">电话</th>
                <th style="width:150px">负责人</th>
                <th style="width:300px">地址</th>
                <th style="width:100px">序列</th>
                <th style="width:400px">操作</th>
                <th>ID</th>
                <th>parent_id</th>
                <th>公司域名</th>
                <th>公司状态</th>
                <th>负责人</th>
                <th>公司类型</th>
                <th>provinceId</th>
                <th>cityId</th>
                <th>districtId</th>
                <th>详细地址</th>
                <th>描述</th>
                <th>grade</th>
                <th>pname</th>
                <th>level</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <!-- 弹框更新序列 -->
    <div id="update-wrapper" style="height: 100%; display: none;line-height: 140px; text-align: center">
        <span>序列：</span><input type="text" id="orderNum" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
    </div>
        <%--注册按钮弹出窗--%>
       <div id="companyinfo" style="display: none" >
           <input  id="activity_id" type="hidden" />
           <input  id="activity_img_id" type="hidden" />
           <input  id="activity_img_fileName" type="hidden" />
           <div class="addCommon clearfix">
               <div>
                   <ul id="companyUrl">
                       <li style="margin-left: 30px;margin-bottom: 10px;margin-top: 20px;">
                           <label class="lf" style="margin-right: 18px;">企业邮箱</label>
                           <label>
                               <input id="email" type="text" class="text_add" style="height: 28px;width: 320px;margin-left:-15px;" placeholder="企业邮箱格式必须以.net结尾"/>
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px;">
                           <label class="lf">公司名称</label>
                           <label>
                               <select id="companyName" class="text_add" style="height: 28px;width: 320px;margin-left:0px;" onchange="checkIsNotRegiest(this.options[this.options.selectedIndex].text)">
                                   <option>-请选择-</option>
                               </select>
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px;">
                           <label class="lf">法人名称</label>
                           <label>
                               <input id="username" type="text" class="text_add" style="height: 28px;width: 320px;margin-left:0;" />
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px;">
                           <label class="lf" style="margin-right: -10px;">身份证号</label>
                           <label>
                               <input id="card" type="text" class="text_add" style="height: 28px;width: 320px;" />
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px;" id="effectivaStartAndStopTime">
                           <label class="lf" style="margin-right: 14px;">手机号码</label>
                           <label>
                               <input id="tel" type="text" class="text_add" style="height: 28px;width: 320px;margin-left: -14px" />
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px;" id="effectiveTime">
                           <label class="lf" style="margin-right: -10px;">营业执照号</label>
                           <label>
                               <input id="num" type="text" class="text_add" style="height: 28px;width: 320px; margin-left: -6px"  />
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px;">
                           <label class="lf">认证方式</label>
                           <label>
                               <select id="gongsiImgType" class="text_add" style="height: 28px;width: 320px;margin-left:0px;" onchange="xx(this.options[this.options.selectedIndex].value)">
                                   <option>-请选择-</option>
                                   <option value="0">传统多证</option>
                                   <option value="1">三证合一</option>
                               </select>
                           </label>
                       </li>
                       <li style="margin-left: 30px;margin-bottom: 10px; display: none" id="code1">
                           <label class="lf" style="margin-right: -10px;">组织机构代码</label>
                           <label>
                               <input id="code" type="text" class="text_add" style="height: 28px;width: 320px; margin-left: -19px"  />
                           </label>
                       </li>
                       <li style="margin-left: 30px; width: 452px; display: none"id="tu1">
                           <label style="margin-top: 20px;" class="lf">图片</label>
                           <label  style="width: 500px;position: relative;left:70px;top:10px;">
                               <div class="paperBlockfree" style="overflow: hidden; float: left;width: 200px">
                                   <div style="height:100px;margin-left: 10px;margin-top: 40px;" id="houseotherpic0">
                                       <div class="getFanQiZha businessPic">
                                           <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                           <form action="" enctype="multipart/form-data">
                                               <input type="hidden" id="id0"/><%--活动id--%>
                                               <input type="hidden" name="type" value="18">
                                               <input type="hidden" name="businessType" value="2">
                                               <div class="imagediv" style="margin-left: -20px;margin-top:-44px;">
                                                   <input type="file"  name="pic" class="picShow" onchange="setImagePreview(this,0)" />
                                                   <img class="addMaterial" id='img0' src="../resources/images/photoadd.png" style="margin-left: 1px;margin-top: -2px;" />
                                               </div>
                                           </form>
                                       </div>
                                   </div>
                               </div>

                               <div class="paperBlockfree" style="overflow: hidden;float: left;width: 200px">
                                   <div style="height:100px;margin-left: 10px;margin-top: 40px;" id="houseotherpic1">
                                       <div class="getFanQiZha businessPic">
                                           <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                           <form action="" enctype="multipart/form-data">
                                               <input type="hidden" id="id1"/><%--活动id--%>
                                               <input type="hidden" name="type" value="18">
                                               <input type="hidden" name="businessType" value="2">
                                               <div class="imagediv" style="margin-left: -20px;margin-top:-44px;">
                                                   <input type="file"  name="pic" class="picShow" onchange="setImagePreview(this,1)" />
                                                   <img class="addMaterial" id='img1'src="../resources/images/photoadd.png" style="margin-left: 1px;margin-top: -2px;" />
                                               </div>
                                           </form>
                                       </div>
                                   </div>
                               </div>
                           </label>
                           <label>
                               <input id='closeImg' type="button" style='margin-left: 20px;display:none' value="X" onclick='closeDelete(this)' >
                           </label>
                       </li>

                       <li style="margin-left: 30px; width: 1000px;  display: none" id="tu2">
                           <label style="margin-top: 20px;" class="lf">图片</label>
                           <label style="width: 900px;position: relative;right:80px;top:50px;">
                               <div class="container">
                                   <div class="paperBlockfree123" style=" display: inline;float: left;">
                                       <div style="" id="houseotherpic2">
                                           <div class="getFanQiZha businessPic">
                                               <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                               <form action="" enctype="multipart/form-data">
                                                   <input type="hidden" id="id2"/><%--活动id--%>
                                                   <input type="hidden" name="type" value="18">
                                                   <input type="hidden" name="businessType" value="2">
                                                   <div class="imagediv" style="margin-left: -20px;margin-top:-44px;">
                                                       <input type="file"  name="pic" class="picShow  picShow1" style="background-color: #00b4af;border: 1px solid red"  onchange="setImagePreview(this,2)" />
                                                       <img class="addMaterial" id='img2' src="../resources/images/photoadd.png" style="margin-left: 1px;margin-top: -2px;" />
                                                   </div>
                                               </form>
                                           </div>
                                       </div>
                                   </div>
                               </div>

                               <div class="container">
                                   <div class="paperBlockfree123" style="display: inline;float: left;">
                                       <div style="height:100px;margin-left: 10px;margin-top: 40px;" id="houseotherpic3">
                                           <div class="getFanQiZha businessPic">
                                               <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                               <form action="" enctype="multipart/form-data">
                                                   <input type="hidden" id="id3"/><%--活动id--%>
                                                   <input type="hidden" name="type" value="18">
                                                   <input type="hidden" name="businessType" value="2">
                                                   <div class="imagediv" style="margin-left: -20px;margin-top:-44px;">
                                                       <input type="file"  name="pic" class="picShow picShow2" style="background-color: #00b4af"  onchange="setImagePreview(this,3)" />
                                                       <img class="addMaterial" id='img3' src="../resources/images/photoadd.png" style="margin-left: 1px;margin-top: -2px;" />
                                                   </div>
                                               </form>
                                           </div>
                                       </div>
                                   </div>
                               </div>

                                   <div class="container">
                                       <div class="paperBlockfree123" style="display: inline;float: left;">
                                           <div style="height:100px;margin-left: 10px;margin-top: 40px; " id="houseotherpic4">
                                               <div class="getFanQiZha businessPic">
                                                   <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                                   <form action="" enctype="multipart/form-data">
                                                       <input type="hidden" id="id5"/><%--活动id--%>
                                                       <input type="hidden" name="type" value="18">
                                                       <input type="hidden" name="businessType" value="2">
                                                       <div class="imagediv" style="margin-left: -4px;margin-top:-44px;">
                                                           <input type="file"  style="background-color: #00b4af"  name="pic" class="picShow picShow2" onchange="setImagePreview(this,4)" />
                                                           <img class="addMaterial" id='img4' src="../resources/images/photoadd.png" style="margin-left: 1px;margin-top: -2px;" />
                                                       </div>
                                                   </form>
                                               </div>
                                           </div>
                                       </div>
                                   </div>

                                   <div class="container">
                                   <div class="paperBlockfree123" style="display: inline;float: left;">
                                       <div style="height:100px;margin-left: 10px;margin-top: 40px; " id="houseotherpic5">
                                           <div class="getFanQiZha businessPic">
                                               <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                               <form action="" enctype="multipart/form-data">
                                                   <input type="hidden" id="id5"/><%--活动id--%>
                                                   <input type="hidden" name="type" value="18">
                                                   <input type="hidden" name="businessType" value="2">
                                                   <div class="imagediv" style="margin-left: -4px;margin-top:-44px;">
                                                       <input type="file"  style="background-color: #00b4af"  name="pic" class="picShow picShow2" onchange="setImagePreview(this,5)" />
                                                       <img class="addMaterial" id='img5' src="../resources/images/photoadd.png" style="margin-left: 1px;margin-top: -2px;" />
                                                   </div>
                                               </form>
                                           </div>
                                       </div>
                                   </div>
                                   </div>
                               <%--</div>--%>
                           </label>
                           <label>
                               <input id='closeImg1' type="button" style='margin-left: 20px;display:none' value="X" onclick='closeDelete(this)' >
                           </label>
                       </li>

                   </ul>
               </div>
           </div>
       </div>


    <div id="organize" class='border-box' style='display: none; border-left:none;position:relative;padding:20px;padding-bottom:60px;'>
        <form id="add_dept_form" method="post" style=''>
            <input type="hidden" name="id" id="deptId">
            <!-- <input type="hidden" name="status" id="deptStatus"> -->
            <input type="hidden" name="number" id="deptNumber">
            <input type="hidden" name="sequence" id="deptSequence">
            <div id="dept" style="display: block">
                <table class="inputGroup_v space_lg">
                    <tr>
                        <td width='80px'>类型：</td>
                        <td>
                            <select id="deptType" name="type" class="inpText inpMax"  onchange="typeChange('deptType')" >
                                <option value="0">部门</option>
                                <option value="1">公司</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>部门名称：</td>
                        <td>
                            <input class="inpText inpMax" name="name" id="deptName"   maxlength="50" />
                        </td>
                    </tr>
                    <tr id="deptStatusTr">
                        <td>部门状态：</td>
                        <td>
                            <select class="inpText inpMax" name="status" id="deptStatus" >
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>等级：</td>
                        <td><select class="inpText inpMax" name="deptMenu" id = "deptMenu" datatype="*" nullmsg="请选择部门等级！" onchange="menuChange('deptMenu','dept_menu','deptPid','deptPname')">
                            <option value="">请选择</option>
                            <option value="0">父级部门</option>
                            <option value="1">子级部门</option>
                        </select>
                        </td>
                    </tr>
                    <tr id = "dept_menu" style="display: none;">
                        <td>父级部门： </td>
                        <td>
                            <input type="text" class="inpText inpMax" name="pname" id="deptPname" readonly="readonly" onclick="showDeptZtree()" >
                            <input type="hidden" name="pid" id="deptPid" >
                        </td>
                    </tr>
                    <tr>
                        <td class="v-top"><span class="pt3">备 注：</span></td>
                        <td><textarea class="wid-max" rows="5" cols="50" name="description" id="description" maxlength="120"></textarea> </td>
                    </tr>
                </table>
            </div>
        </form>

        <form id="add_company_form" action="sys/dept/saveOrUpdateDepartment.html" method="post" >
            <input type="hidden" name="id" id="companyId">
            <!-- <input type="hidden" name="status" id="status"> -->
            <input type="hidden" name="number" id="companyNumber">
            <input type="hidden" name="sequence" id="sequence">
            <div id="company" style="display: none;">
                <table class='inputGroup_v space_lg'>
                    <tr>
                        <td width='80'>类型：</td>
                        <td>
                            <select class="inpText inpMax" name="type" id="type" onchange="typeChange('type')" >
                                <option value="0" >部门</option>
                                <option value="1" >公司</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>公司简称：</td>
                        <td><input class="inpText inpMax" name="name" id="name" datatype="*" nullmsg="请输入公司简称！" maxlength="20"/></td>
                    </tr>
                    <tr>
                        <td>公司名称：</td>
                        <td><input class="inpText inpMax"  name="abbreviation" id="abbreviation" datatype="*" nullmsg="请输入名称！" maxlength="50" errormsg="名称长度！"/></td>
                    </tr>
                    <tr>
                        <td>公司电话：</td>
                        <td><input class="inpText inpMax" name="phone" id="phone" maxlength="20"  datatype="/^(0|86|17951)?(13[0-9]|15[012356789]|17[01678]|18[0-9]|14[57])[0-9]{8}$/ | /^((0[0-9]{2,3})-)([0-9]{7,8})(-([0-9]{3,}))?$/" nullmsg="请输入电话号码！" errormsg="电话号码格式不正确！"/></td>
                    </tr>
                    <tr>
                        <td>公司域名：</td>
                        <td><input class="inpText inpMax" name="domain" id="domain" maxlength="20"  datatype="*" /></td>
                    </tr>
                    <tr id="cmpStatusTr" >
                        <td>公司状态：</td>
                        <td>
                            <select class="inpText inpMax" name="status" id="status">
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>等级：</td>
                        <td><select class="inpText inpMax" name="companyMenu" id = "companyMenu" datatype="*" nullmsg="请选择部门等级！" onchange="menuChange('companyMenu','company_menu','pid','pname')">
                            <option value="">请选择</option>
                            <option value="0">父级部门</option>
                            <option value="1">子级部门</option>
                        </select>
                        </td>
                    </tr>
                    <tr id = "company_menu" style="display: none;">
                        <td >父级部门： </td>
                        <td>
                            <input class="inpText inpMax" type="text" name="pname" id="pname" readonly="readonly" onclick="showDeptZtree()">
                            <input type="hidden" name="pid" id="pid" >
                        </td>
                    </tr>
                    <tr>
                        <td >负责人：</td>
                        <td>
                            <input class="inpText inpMax" name="principal" id="principal" datatype="*" nullmsg="请输入负责人！"/>
                        </td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td>公司类型：</td>--%>
                        <%--<td>--%>
                            <%--<select class="inpText inpMax" name="companyType" id="companyType" datatype="*" nullmsg="请选择公司类型！">--%>
                                <%--<option value="">请选择</option>--%>
                            <%--</select>--%>
                        <%--</td>--%>
                    <%--</tr>--%>

                    <tr>
                        <td class="v-top">公司地址：</td>
                        <td>
                            <div class="flexCtn">
                                <div style="flex:1" class="flexCtn">
                                    <select onchange="onSelectChange(this,'city')"  name="province" id="province" mark="home" class="inpText province" style="flex:1"  datatype="*" nullmsg="请选择省份！">
                                        <option value="">请选择</option>
                                    </select>
                                    <span style="line-height:26px;">&nbsp省&nbsp</span>
                                </div>
                                <div style="flex:1" class="flexCtn">
                                    <select onchange="onSelectChange(this,'district')" name="city" id="city" mark="home" markName="homeCity" class="inpText city" style="flex:1" datatype="*" nullmsg="请选择市区！">
                                        <option value="">请选择</option>
                                    </select>
                                    <span style="line-height:26px;">&nbsp市&nbsp</span>
                                </div>
                                <div style="flex:1" class="flexCtn">
                                    <select class="inpText inpMax" name="district" id="district" markName="homeArea" style="flex:1" datatype="*" nullmsg="请选择市区！">
                                        <option value="">请选择</option>
                                    </select>
                                    <span style="line-height:26px;">&nbsp区&nbsp</span>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="v-top">详细地址：</td>
                        <td>
                            <div style="flex:2" class="flexCtn">
                                <input class="inpText inpMax" type='text' style='float:none;' name="address" id="address" style="flex:2" datatype="*" nullmsg="请输入地址！" maxlength="120"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="v-top"><span class="pt3">备 注：</span></td>
                        <td><textarea class="wid-max" rows="5" cols="50" name="description" id="comDescription" maxlength="120"></textarea> </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
    <!-- 弹框部门tree-->
    <div id="jstree" style="height: 463px;overflow:auto;display: none;" class="ztree">
    </div>
</div>

<script type="text/javascript" src="${ctx}/resources/assets/treeTable/js/jquery.treetable.js"></script>
<script type="text/javascript" src="${ctx}/resources/assets/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resources/assets/zTree/tree.js"></script>
</body>
</html>