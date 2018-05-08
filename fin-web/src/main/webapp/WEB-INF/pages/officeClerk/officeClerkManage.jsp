<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src='${ctx}/resources/js/officeClerk/clerkManage.js${version}'></script>
    <script type="text/javascript" src="${ctx}/resources/assets/treeTable/js/jquery.treetable.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/tree.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/assets/zTree/css/zTreeStyle/zTreeStyle.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <title>办单员管理</title>
    <style type="text/css">
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #sign_list{font-size:13px;}
        .checkShow,.uploadContact{color:#05c1bc}
        .checkShow{margin-right: 8px;}

        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}

        #divFrom input{border:none;background-color: #fff!important;text-align: left;}
        .unit{position: relative; top:1px;}
        /*合理性样式*/
        #showReasonable input[name=ReasonableInput],#showReasonable input[name=ReasonableInput_fitment],#showReasonable input[name=ReasonableInput_fitment_money]{border:1px solid #ddd}
        .ReasonableUl li{
            display: inline-block;
            width: 10%;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
        }
        .ReasonableUl li:hover{
            display: inline-block;
            width: 25%;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
        }
        .ReasonableUl li img{
            width: 100%;
            cursor: pointer;
        }
        .answerRemark,#elecRemark,#customerRemark,#giveAmount{width: 100%;position: relative;left:-10px;}
        #customerRemark,#elecRemark,#advice{width: 96%;margin-left: 20px;}
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
        #firstCredit,#passRemark,#cheatRemark{width:95%;float:left;margin-left:0px;margin-left:4px;}
        #cheatRemark{width: 99%;}
        .telRecord{height: 37px;  text-align: left;  padding-left: 35px!important;}
        #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#answerBody td,#answerBody1 td{border:none!important;border-right:1px solid #ccc}
        #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}
        .answerTh th{text-align: center; font-weight: normal;border:none!important;}
        .answerTh thead{border:1px solid #ccc;border-top:none;background: #DFF1D9}
        .cencus{height: 40px;width:100%;line-height:40px;float:left;border-right:1px solid #ccc;border-left:1px solid #ccc;background-color: white; text-align: left;
            padding-left: 29px;
        }
        .MerInput{
            margin:.5em 0;
        }
        #MerMsg tr td{
            text-align: right;
            padding-left:0.2em;
        }
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
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
        .picShow {
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 100%;
            width: 100%;
            top: 1em;
            left: 1em;
        }
        #contract_list{font-size: 13px;}
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
        #clerk_manager_table{
            border:0;

        }
        #clerk_manager_table td{
            background:white;
            width: 250px;
            height: 40px;
            border: 1px solid black;
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
        .imgContainer{display: inline-block;position: relative}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" id="employeeNum">
    <input type="hidden" id="salesmanId">
    <input type="hidden" id="salesmanId_merchant">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li style="width:250px;">
                    <label class="lf">姓名:</label>
                    <label style="width: 100px">
                        <input type="text" id="search_name">
                    </label>
                </li>
                <li style="width:250px;">
                    <label class="lf">手机号:</label>
                    <label style="width: 100px">
                        <input type="text" id="search_tel">
                    </label>
                </li>
                <li style="width:200px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
                <li>
                    <button id="btn_add_clerkmanager" type="button" class="btn btn-primary queryBtn">添加</button>
                </li>
            </ul>
        </div>
        <!-- 办单员添加模块弹出框 -->
        <div id="div_add_clerkmanager" style="display: none" >
            <div class="addCommon clearfix">
                <div>
                    <div id="container" class="of-auto_H" style="padding:20px;">
                        <div id="divFrom">
                            <div class="paddingBox xdproadd" style="width:1200px">
                                <div class="paperBlockfree" style="margin-top:20px">
                                    <div class="block_hd" style="float:left;">
                                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">办单员信息</span>
                                    </div>
                                    <table class="tb_info " style="font-size:12px; color: #000;">
                                        <tbody>
                                        <tr>
                                            <td class="align" width="10%">姓名:</td>
                                            <td id="applyName" width="23%">
                                                <input id='office_clerk_name' type="text" style="min-width:60px;height: 28px;" placeholder="姓名">
                                            </td>
                                            <td class="align" width="10%">性别:</td>
                                            <td width="23%" id="tdSex">
                                                <select id="office_clerk_sex" class="if" style="height: 28px;width: 200px;">
                                                    <option value="" style="font-size: 10px;">请选择</option>
                                                    <option value="1">男</option>
                                                    <option value="0">女</option>
                                                </select></td>
                                            <td class="align" width="10%">手机号:</td>
                                            <td id="proTel" width="23%">
                                                <input id="office_clerk_tel" type="text" class="text_add"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align">出生日期:</td>
                                            <td id="applyIdcard">
                                                <input readonly="true" placeholder="出生日期" class="eg-date" id="beginTime" type="text" style="width: 130px" />
                                                <%--<span class="date-icon" style="width: 20px"><i class="icon-calendar" ></i></span>--%>
                                            </td>
                                            <td class="align">身份证号码:</td>
                                            <td id="applyCensus">
                                                <input id="idcard" type="text" class="text_add"/>
                                            </td>
                                            <td class="align">身份证住址:</td>
                                            <td id="tdBirth">
                                                <input id="idcard_address" type="text" class="text_add" style="width: 200px;"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align">现居住地址:</td>
                                            <td id="applyMerry">
                                                <input id="now_address" type="text" class="text_add" style="width: 250px;"/>
                                            </td>
                                            <td class="align">部门:</td>
                                            <td id="education">
                                                <input type="text" placeholder="点击选取部门" style="background-color: #F4F4F4;" class="inpText inpMax" name="pname" id="deptPname" readonly="readonly" onclick="showDeptZtree()" >
                                                <input type="hidden" name="deptPid" id="deptPid" >
                                            </td>
                                            <td>岗位:</td>
                                            <td id="unitName">
                                                <select id="psot" class="if" style="height: 28px;">
                                                    <option value="" style="font-size: 10px;">请选择</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>办单员账号:</td>
                                            <td>
                                                <input id="employee_num" type="text" class="text_add"/>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                                    <div class="block_hd" style="float:left;" style="float:left;" onclick="shrink(this)">
                                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">影像资料</span>
                                    </div>
                                    <script>
                                        //图片上传
                                        function setImagePreview(me,sign){
//                                            $(me).parent().prev().prev().prev().val($('#activity_id').val());//活动id
//                                            var id = $('#activity_id').val();
                                            var docObj=me;
                                            var imgObjPreview=me.nextElementSibling;
                                            if(docObj.files && docObj.files[0]){
                                                //火狐下，直接设img属性
                                                imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                                                var index =layer.load();

                                                $(me).parent().parent().ajaxSubmit({
                                                    type: "POST",
                                                    url: "merUpload",
                                                    success: function (data) {

                                                        layer.close(index);
                                                        console.log(data);
                                                        data = eval('('+data+')');
                                                        //debugger
                                                        $(me).parent().parent().prev().val(data.data.img_url);//图片url
                                                        $(me).parent().parent().parent().addClass("getFanQiZha");
                                                        layer.msg(data.msg,{time:1000});
                                                    },
                                                    error: function (XMLHttpRequest, textStatus, thrownError) {

                                                        layer.close(index);
                                                    }
                                                });
                                            }else{

                                            }
                                            return true;
                                        }
                                    </script>
                                    <table class="tb_info" style="font-size:12px;">
                                        <tbody>
                                            <tr style="height: 160px;">
                                                <%--身份证正面--%>
                                                <td style="background: #DEF0D8;width: 10%">身份证正面照片</td>
                                                <td colspan="3" style="text-align: left">
                                                    <%--<div class="imgContainer" id="IdCardImgContainer">--%>
                                                    <%--11111--%>
                                                    <%--</div>--%>
                                                    <input type="hidden" class="imgHidden" id="cardImageZhengMian"><%--删除的图片id及名称--%>
                                                    <form action="" enctype="multipart/form-data">
                                                        <input type="hidden" id="id"/><%--活动id--%>
                                                        <input type="hidden" name="type" value="1"><!--type=1代表身份证正面-->
                                                        <input type="hidden" name="businessType" value="2">
                                                        <div class="imagediv">
                                                            <input type="file"  name="pic" id="picShow" class="picShow"  onchange="setImagePreview(this,18)"/>
                                                            <img class="addMaterial" src="../resources/images/photoadd.png" id="show_zhengmian_img"/>
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                            <tr style="height: 160px;">
                                                <%--身份证反面照片--%>
                                                <td style="background: #DEF0D8;width: 10%">身份证反面照片</td>
                                                <td colspan="3" style="text-align: left">
                                                    <input type="hidden" class="imgHidden" id="cardImageFanMian"><%--删除的图片id及名称--%>
                                                    <form action="" enctype="multipart/form-data">
                                                        <input type="hidden" id="id"/><%--活动id--%>
                                                        <input type="hidden" name="type" value="1"><!--type=1代表身份证正面-->
                                                        <input type="hidden" name="businessType" value="2">
                                                        <div class="imagediv">
                                                            <input type="file"  name="pic" id="picShow1" class="picShow"  onchange="setImagePreview(this,18)"/>
                                                            <img class="addMaterial" src="../resources/images/photoadd.png" id="show_fanmian_img" />
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                            <tr style="height: 160px;">
                                                <%--手持身份证照片--%>
                                                <td style="background: #DEF0D8;width: 10%">手持身份证照片</td>
                                                    <td colspan="3" style="text-align: left">
                                                        <input type="hidden" class="imgHidden" id="cardImageShouChi"><%--删除的图片id及名称--%>
                                                        <form action="" enctype="multipart/form-data">
                                                            <input type="hidden" id="id"/><%--活动id--%>
                                                            <input type="hidden" name="type" value="2"><!--type=2代表手持身份证-->
                                                            <input type="hidden" name="businessType" value="2">
                                                            <div class="imagediv">
                                                                <input type="file"  name="pic" id="picShow2" class="picShow"  onchange="setImagePreview(this,18)"/>
                                                                <img class="addMaterial" src="../resources/images/photoadd.png" id="show_shouchi_img"/>
                                                            </div>
                                                        </form>
                                                    </td>
                                            </tr>
                                            <tr style="height: 160px;">
                                                <%--其他照片--%>
                                                <td style="background: #DEF0D8;width: 10%">其他照片</td>
                                                    <td colspan="3" style="text-align: left">
                                                        <input type="hidden" class="imgHidden" id="cardImageQiTa"><%--删除的图片id及名称--%>
                                                        <form action="" enctype="multipart/form-data">
                                                            <input type="hidden" id="id"/><%--活动id--%>
                                                            <input type="hidden" name="type" value="3"><!--type=3代表其他-->
                                                            <input type="hidden" name="businessType" value="2">
                                                            <div class="imagediv">
                                                                <input type="file"  name="pic" id="picShow3" class="picShow"  onchange="setImagePreview(this,18)"/>
                                                                <img class="addMaterial" src="../resources/images/photoadd.png" id="show_qita_img" />
                                                            </div>
                                                        </form>
                                                    </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 查询单个办单员模块弹出框 -->
        <div id="show_manager" style="display: none" >
            <div class="addCommon clearfix">
                <div>
                    <div class="of-auto_H" style="padding:20px;">
                        <div id="">
                            <div class="paddingBox xdproadd" style="width:1200px">
                                <div class="paperBlockfree" style="margin-top:20px">
                                    <div class="block_hd" style="float:left;">
                                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">办单员信息</span>
                                    </div>
                                    <table class="tb_info " style="font-size:12px; color: #000;">
                                        <tbody>
                                        <tr>
                                            <td class="align" width="10%">姓名:</td>
                                            <td  width="23%">
                                                <input id="realname" type="text"  readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td class="align" width="10%">性别:</td>
                                            <td width="23%" >
                                                <input id="sex_name" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td class="align" width="10%">手机号:</td>
                                            <td  width="23%">
                                                <input id="tel" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align">出生日期:</td>
                                            <td >
                                                <input id="date_bath" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td class="align">身份证号码:</td>
                                            <td >
                                                <input id="idcard_show" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td class="align">身份证住址:</td>
                                            <td >
                                                <input id="idcard_address_show" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align">现居住地址:</td>
                                            <td >
                                                <input id="now_address_show" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td class="align">部门:</td>
                                            <td>
                                                <input id="branch_show" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td>岗位:</td>
                                            <td>
                                                <select id="chakan_post_show" class="if" style="height: 28px;" disabled="disabled" style="width: 240px;">
                                                    <option value="" style="font-size: 10px;">请选择</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>办单员账号:</td>
                                            <td>
                                                <input id="employee_num_show" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                            <td>激活状态：</td>
                                            <td>
                                                <input id="activation_state" type="text" readonly="readonly" style="width: 240px;"/>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                                    <div class="block_hd" style="float:left;" style="float:left;" onclick="shrink(this)">
                                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">影像资料</span>
                                    </div>
                                    <table class="tb_info" style="font-size:12px;">
                                        <tbody>
                                        <tr style="height: 160px;">
                                            <%--身份证正面--%>
                                            <td style="background: #DEF0D8;width: 10%">身份证正面照片</td>
                                            <td colspan="3" style="text-align: left">
                                                <div class="imgContainer" id="IdCardImgContainer">
                                                    <img class="addMaterial" src="../resources/images/photoadd.png" id="zhengmian"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="height: 160px;">
                                            <%--身份证反面照片--%>
                                            <td style="background: #DEF0D8;width: 10%">身份证反面照片</td>
                                            <td colspan="3" style="text-align: left">
                                                <div class="imgContainer" id="IdCardImgContainer1">
                                                    <img class="addMaterial" src="../resources/images/photoadd.png" id="fanmian"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="height: 160px;">
                                            <%--手持身份证照片--%>
                                            <td style="background: #DEF0D8;width: 10%">手持身份证照片</td>
                                            <td colspan="3" style="text-align: left">
                                                <div class="imgContainer" id="IdCardImgContainer2">
                                                    <img class="addMaterial" src="../resources/images/photoadd.png" id="shouchi" />
                                                </div>
                                            </td>
                                        </tr>
                                        <tr style="height: 160px;">
                                            <%--其他照片--%>
                                            <td style="background: #DEF0D8;width: 10%">其他照片</td>
                                            <td colspan="3" style="text-align: left">
                                                <div class="imgContainer" id="IdCardImgContainer3">
                                                    <img class="addMaterial" src="../resources/images/photoadd.png" id="qita"/>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 编辑单个办单员模块弹出框 -->
        <div id="edit_manager" style="display: none" >
            <div class="addCommon clearfix">
                <div>
                    <div class="of-auto_H" style="padding:20px;">
                            <div >
                                <div class="paddingBox xdproadd" style="width:1200px">
                                    <div class="paperBlockfree" style="margin-top:20px">
                                        <div class="block_hd" style="float:left;">
                                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">办单员信息</span>
                                        </div>
                                        <table class="tb_info " style="font-size:12px; color: #000;">
                                            <tbody>
                                            <tr>
                                                <td class="align" width="10%">姓名:</td>
                                                <td  width="23%">
                                                    <input id="edit_realname" type="text"  style="width: 240px;"/>
                                                </td>
                                                <td class="align" width="10%">性别:</td>
                                                <td width="23%" >
                                                    <select id="edit_sex" class="if" style="height: 28px;">
                                                        <option value="" style="font-size: 10px;">请选择</option>
                                                        <option value="1">男</option>
                                                        <option value="0">女</option>
                                                    </select>
                                                </td>
                                                <td class="align" width="10%">手机号:</td>
                                                <td  width="23%">
                                                    <input id="edit_tel" type="text"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="align">出生日期:</td>
                                                <td >
                                                    <input readonly="true" placeholder="出生日期" class="eg-date" id="edit_manager_dateBath" type="text" style="width: 240px;" />
                                                    <%--<span class="date-icon" style="width: 20px"><i class="icon-calendar" ></i></span>--%>
                                                </td>
                                                <td class="align">身份证号码:</td>
                                                <td >
                                                    <input id="edit_idcard_show" type="text" />
                                                </td>
                                                <td class="align">身份证住址:</td>
                                                <td >
                                                    <input id="edit_idcard_address_show" type="text" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="align">现居住地址:</td>
                                                <td >
                                                    <input id="edit_now_address_show" type="text" />
                                                </td>
                                                <td class="align">部门:</td>
                                                <td>
                                                    <input type="text" class="inpText inpMax" name="pname" id="edit_deptPname" readonly="readonly" onclick="editShowDeptZtree()" >
                                                    <input type="hidden" name="deptPid" id="edit_deptPid" >
                                                </td>
                                                <td>岗位:</td>
                                                <td>
                                                    <select id="edit_post_show" class="if" style="height: 28px;">
                                                        <option value="" style="font-size: 10px;">请选择</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>办单员账号:</td>
                                                <td>
                                            <input id="edit_employee_num_show" readonly="readonly" type="text" />
                                            </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                                        <div class="block_hd" style="float:left;" style="float:left;" onclick="shrink(this)">
                                            <s class="ico icon-file-text-alt"></s><span class="bl_tit">影像资料</span>
                                        </div>
                                        <script>
                                            //图片上传
                                            function setImagePreviewEdit(me,sign){
                                                var docObj=me;
                                                var imgObjPreview=me.nextElementSibling;
                                                if(docObj.files && docObj.files[0]){
                                                    //火狐下，直接设img属性
                                                    imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                                                    var index =layer.load(2);
                                                    $(me).parent().parent().ajaxSubmit({
                                                            type: "POST",
                                                            url: "merUpload",
                                                            async: false,
                                                            success: function (data) {
                                                                layer.close(index);
                                                                console.log(data);
                                                                data = eval('('+data+')');
                                                                //debugger
                                                                $(me).parent().parent().prev().val(data.data.img_url);//图片url
                                                                $(me).parent().parent().parent().addClass("getFanQiZha");
                                                                layer.msg(data.msg,{time:1000});
                                                        },
                                                        error: function (XMLHttpRequest, textStatus, thrownError) {
                                                            layer.close(index);
                                                        }
                                                    });
                                                }else{

                                                }
                                                return true;
                                            }
                                        </script>
                                        <table class="tb_info" style="font-size:12px;">
                                            <tbody>
                                            <tr style="height: 160px;">
                                                <%--身份证正面--%>
                                                <td style="background: #DEF0D8;width: 10%">身份证正面照片</td>
                                                <td colspan="3" style="text-align: left">
                                                    <input type="hidden" class="imgHidden" id="edit_cardImageZhengMian"><%--删除的图片id及名称--%>
                                                    <form action="" enctype="multipart/form-data">
                                                        <div class="imagediv">
                                                            <input type="file"  name="pic" id="edit_picShow" class="picShow"  onchange="setImagePreviewEdit(this,18)"/>
                                                            <img class="addMaterial" src="" id="edit_zhengmian" />
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                            <tr style="height: 160px;">
                                                <%--身份证反面照片--%>
                                                <td style="background: #DEF0D8;width: 10%">身份证反面照片</td>
                                                <td colspan="3" style="text-align: left">
                                                    <input type="hidden" class="imgHidden" id="edit_cardImageFanMian"><%--删除的图片id及名称--%>
                                                    <form action="" enctype="multipart/form-data">
                                                        <div class="imagediv">
                                                            <input type="file"  name="pic" id="edit_picShow1" class="picShow"  onchange="setImagePreviewEdit(this,18)"/>
                                                            <img class="addMaterial" src="" id="edit_fanmian"/>
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                            <tr style="height: 160px;">
                                                <%--手持身份证照片--%>
                                                <td style="background: #DEF0D8;width: 10%">手持身份证照片</td>
                                                <td colspan="3" style="text-align: left">
                                                    <input type="hidden" class="imgHidden" id="edit_cardImageShouChi"><%--删除的图片id及名称--%>
                                                    <form action="" enctype="multipart/form-data">
                                                        <div class="imagediv">
                                                            <input type="file"  name="pic" id="edit_picShow2" class="picShow"  onchange="setImagePreviewEdit(this,18)"/>
                                                            <img class="addMaterial" src="" id="edit_shouchi"/>
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                            <tr style="height: 160px;">
                                                <%--其他照片--%>
                                                <td style="background: #DEF0D8;width: 10%">其他照片</td>
                                                <td colspan="3" style="text-align: left">
                                                    <input type="hidden" class="imgHidden" id="edit_cardImageQiTa"><%--删除的图片id及名称--%>
                                                    <form action="" enctype="multipart/form-data">
                                                        <div class="imagediv">
                                                            <input type="file"  name="pic" id="edit_picShow3" class="picShow"  onchange="setImagePreviewEdit(this,18)"/>
                                                            <img class="addMaterial" src="" id="edit_qita"/>
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
        <%--查看办单员分管商户弹出框--%>
        <div class="Manager_style" style="display: none ;overflow: hidden;" id="salesmanMerchantList">
            <div class="shanghu_list">
                <table style=" clear:both ;cursor:pointer; font-size: 10px; width: 1000px; margin-left: 50px;margin-top: 10px;"
                       id="meichant_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>商户编号</th>
                        <th>商户简称</th>
                        <th>主营业务</th>
                        <th>营业地址</th>
                        <th>申请人</th>
                        <th>商户状态</th>
                        <th>关联状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody >
                    </tbody>
                </table>
            </div>
        </div>
        <%--添加办单员分管的商户弹出框--%>
        <div class="Manager_style" style="display: none ;overflow: hidden;" id="salesmanCanWorkMerchantList">
            <div class="shanghu_list">
                <table style=" clear:both ;cursor:pointer; font-size: 10px; width: 1000px; margin-left: 50px;margin-top: 10px;"
                       id="officeClerkManager_meichant_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>商户编号</th>
                        <th>商户简称</th>
                        <th>主营业务</th>
                        <th>营业地址</th>
                        <th>申请人</th>
                        <th>商户状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody >
                    </tbody>
                </table>
            </div>
        </div>
        <!-- 弹框部门tree-->
        <div id="jstree" style="height: 463px;overflow:auto;display: none;" class="ztree"></div>
        <%--查看办单员订单弹出窗--%>
        <div class="Manager_style" style="display: none ;overflow: hidden;" id="salesmanOrdersList">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix" style="margin-left: 30px;">
                    <li style="width:250px; ">
                        <label class="lf">客户姓名:</label>
                        <label style="">
                            <input type="text" id="search_name_order">
                        </label>
                    </li>
                    <li style="width:250px; ">
                        <label class="lf">手机号:</label>
                        <label style="">
                            <input type="text" id="search_tel_order">
                        </label>
                    </li>
                    <li style="width:200px;">
                        <button id="btn_search_order" type="button" class="btn btn-primary queryBtn">查询</button>
                        <button id="btn_reset_order" type="button" class="btn btn-primary queryBtn">查询重置</button>
                    </li>
                </ul>
            </div>
            <div class="shanghu_list">
                <table style=" clear:both ;cursor:pointer; font-size: 10px; width: 1000px; margin-left: 50px;margin-top: 10px;"
                       id="salesmanOrdersListTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单编号</th>
                        <th>客户姓名</th>
                        <th>手机号码</th>
                        <th>证件号</th>
                        <th>申请时间</th>
                        <th>秒付产品</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody >
                    </tbody>
                </table>
            </div>
        </div>
        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="sign_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>办单员姓名</th>
                        <th>所在部门</th>
                        <th>岗位</th>
                        <th>联系方式</th>
                        <th>身份证号</th>
                        <th>办单员账号</th>
                        <th>状态</th>
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
</html>

