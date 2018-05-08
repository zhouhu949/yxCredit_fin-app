<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>流程管理</title>
    <%@include file ="../common/taglibs.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/decision/decision-leftMenu.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/decision/rulesDetails.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/decision/commonality.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/procedureMange/procedure.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css${version}" />
    <style>
        span.addCondition{
            display:inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/add.png") no-repeat center center;
        }
        span.delCondition{
            display:inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;
        }
        .icon-delData{
            cursor: pointer;
            display: inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;
        }

        body{
            margin:0;
            padding:0;
        }
        .main-content{
            margin-left: 50px;
        }
        .c-sanbox-lie{
            margin:1em 0;
        }
        .c-enter-proportion{
            float:left;
            margin-left: 1.5em;
        }
        .c-sanbox-img{
            cursor: pointer;
            color:#398DEE;
            float: right;
            margin-right: 1.5em;
        }
        .icon-delData{
            cursor: pointer;
            display: inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;
        }
        .c-sanbox-delete{
            float: right;
            margin-right: 2em;
            cursor: pointer;
        }
        .c-swarm-interior .c-swarm-interior-left .c-swarm-name{
            float: left;
        }
        .c-title{
            margin-left:1.5em
        }
        .c-swarm-interior .c-positon-img{
            position: absolute;
            top:30px;
            right: 0;
            margin-right: 1em;
            cursor: pointer;
        }
        .c-contains-outside{
            margin:5px auto;
        }
        .c-contained-within{
            overflow: hidden;
            clear: both;
            margin-left: 1.5em;
        }
        .c-swarm-dialog input{
            width:80px;
        }
        .c-contained-within div[class^="c-select-"]{
            float:left;
        }
        .c-swarm-interior-left{
            position: relative;
        }
        .c-swarm-interior{
            position: relative;
            line-height: 30px;
        }
        span.addCondition{
            display:inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/add.png") no-repeat center center;
        }
        span.delCondition{
            display:inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;
        }
        .c-black-frame,.c-white-frame{
            float:left;
            margin-left:1.5em;
        }
        .c-black-frame input,.c-white-frame input{
            margin-right:1em;
        }
        .c-black-price,.c-white-price{
            cursor: pointer;
        }
        input,select{
            border-radius: 3px;
        }
        .c-swarm-iexternal{
            position: absolute;
            top: 2px;
            right: 7px;
            line-height: 30px;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 0 .5em;
            background: #2E8DED;
            color: #fff;
            cursor: pointer;
        }

        #fieldsStyle{position: fixed;top:40px;z-index: 5;background: #fff;width: 100%;}
        #first_logical_select,#last_logical_select,#inputParameter_one_select,#inputParameter_two_select,#inputParameter_three_select,.add_last_logical_select,.add_inputParameter_one_select,.add_inputParameter_two_select,.add_inputParameter_three_select{width:100px}
        #input_parameter_style .pagination ul li{width:53px;margin:0}
        #option_popups .c-options-createuser{
            margin:1em auto;
            position: fixed;
            height: 60px;
            width: 100%;
            top: 40px;
        }
        #option_popups .c-decisions-switcher:first-child{
            float: left;
            width: 50%;
        }
        .active_active{
            color:#2E8DED;
        }
    </style>
</head>
<body>
<textarea id="jtopo_textfield" style="width: 60px; z-index:10000; position: absolute; display: none;" onkeydown="if(event.keyCode==13)this.blur();" value=""></textarea>
<div class="outs">
    <div class="out-content">
        <div class="left-decisionMenus">
            <div class="l-leftTopMenu">
                <div class="l_right_buttons">
                    <div class="l_left_buttons">
                        <button  class="btn btn-primary"  onclick="editNode(1)">编辑</button>
                        <button  class="btn btn-primary"  onclick="editNode(2)">删除</button>
                        <button  class="btn btn-primary"  onclick="addNodes()">添加</button>
                    </div>
                </div>
            </div>
            <div class="l_decision_options">
                <ul id="l_decisions">
                </ul>
            </div>
        </div>
        <div class="l_rights">
            <div class="rightTopMenu">
                <div class="left_txts">
                    流程管理布局图
                </div>
            </div>
            <div class="rightOperateArea" style="background:url(${ctx}/resources/images/decision/decisionBcg.jpg)">
                <img alt='' src='${ctx}/resources/images/decision/cha.png' style="display:none; z-index:10001; position:absolute;" id="lineDel" />
                <canvas id="canvas" width="1033px" height="450px">
                </canvas>
                <div class="l_through_line">
							<span id="areaSelect" class="disSelect">
								区域
							</span>
                </div>
                <div class="l_pictures_operate">
                    <div id="stageDel">
                        <div class="fs0">
                            <img src="${ctx}/resources/images/decision/del.png" title="删除" />
                            <%--<span class="icon-dels" style="color:#398dee"></span>--%>
                        </div>
                    </div>
                    <div id="stageSearch">
                        <div class="fs0">
                            <img src="${ctx}/resources/images/decision/search.png" title="搜索" />
                            <%--<span class="icon-search" style="color:#398dee"></span>--%>
                        </div>
                    </div>
                    <div id="stageEnlarge">
                        <div class="fs0">
                            <img src="${ctx}/resources/images/decision/fangda.png" title="放大" />
                            <%--<span class="icon-fangda" style="color:#398dee"></span>--%>
                        </div>
                    </div>
                    <div id="stageNarrow">
                        <div class=" fs0">
                            <img src="${ctx}/resources/images/decision/suoxiao.png" title="缩小" />
                            <%--<span class="icon-suoxiao" style="color:#398dee"></span>--%>
                        </div>
                    </div>
                </div>
                <div class="c-hide-prompt">
                    <div class="c-prompt-content-add">

                    </div>
                    <button class="c-prompt-button"></button>
                </div>
            </div>
            <div class="l_version">
                <div class="l_run_version" style="overflow:auto;">
                </div>
                <div class="l_stop_version" style="overflow: auto;">
                    <div class="l_right_versionTxt">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id='process_id' value="${param.id}" name="id"/>
    <div id="addNodes">
        <div class="labelStyle">
            <label class="label_name">选择类型&nbsp;&nbsp;</label>
            <label>
                <select size="1" name="selectType" id="selectType">
                    <option value=0 selected>自动流程节点</option>
                    <option value=1>人工节点</option>
                </select>
            </label>
        </div>
        <div class="labelStyle" id="executeClass">
            <label class="label_name">执行类</label>&nbsp;&nbsp;&nbsp;
            <label>
                <input id="className" type="text" placeholder="className">
            </label>
        </div>
        <div class="labelStyle" id="engineIds">
            <label class="label_name">引擎id</label>&nbsp;&nbsp;&nbsp;
            <label>
                <input id="engineId" type="text" placeholder="engineId">
            </label>
        </div>
        <div class="labelStyle labelStyle_hide" id="urlInput">
            <label class="label_name">页面路径</label>
            <label>
                <input  id="pageUrl" type="text" placeholder="pageUrl">
            </label>
        </div>
        <div class="labelStyle labelStyle_hide" id="urlDel">
            <label class="label_name">处理路径</label>
            <label>
                <input  id="handUrl" type="text" placeholder="url">
            </label>
        </div>
        <div class="labelStyle" id="name">
            <label class="label_name">节点名称</label>
            <label>
                <input  id="text" type="text" placeholder="text">
            </label>
        </div>
        <div class="labelStyle" id="icons">
            <label class="label_name">选择图标</label>
            <label>
                <input id="icon" type="text" placeholder="icon">
            </label>
        </div>
    </div>
    <div id="jsTreeCheckBox" style="display: none;text-align: left !important;width: 280px;"></div>
</div>


<!-- 右键菜单 -->
<div class="bigCircle" style="display: none; position: absolute;">
    <div class="operate hovers" title="编辑">
        <img src="${ctx}/resources/images/decision/operate.png" class="shows"/>
        <img src="${ctx}/resources/images/decision/operateWhite.png" style="display: none;"  class="hides" />
    </div>
    <div class="look hovers"  dataId='' type="">
        <%--<img src="${ctx}/resources/images/decision/look.png" class="shows" />--%>
        <%--<img src="${ctx}/resources/images/decision/lookWhite.png" style="display: none;" class="hides" />--%>
    </div>
    <div class="copy hovers">
        <%--<img src="${ctx}/resources/images/decision/copy.png" class="shows" />--%>
        <%--<img src="${ctx}/resources/images/decision/copyWhite.png" style="display: none;" class="hides" />--%>
    </div>
    <div class="delete hovers" title="删除">
        <img src="${ctx}/resources/images/decision/delete.png" class="shows" />
        <img src="${ctx}/resources/images/decision/deleteWhite.png" style="display: none;" class="hides" />
    </div>
    <div class="alpha">
    </div>
    <div class="blue" class="hideMenu">
        <img src="${ctx}/resources/images/decision/dele.png" style="margin:0"/>
    </div>
</div>

<div id="youji" style="display: none; position: absolute;">
    <%--<div class="labelStyle">--%>
    <%--<label class="label_name" onclick="editNode();">编辑节点&nbsp;&nbsp;</label>--%>
    <%--</div>--%>
    <%--<div class="labelStyle">--%>
    <%--<label class="label_name" onclick="delNode();">删除节点&nbsp;&nbsp;</label>--%>
    <%--</div>--%>
    <input id ="nodeId" type="hidden">
</div>
<!-- 黑名单 -->
<%--<div id="blackTree"></div>--%>
<!-- 分群参数 -->
<div id="groupTree"></div>

<%--<!-- 决策选项 -->--%>
<%--<div class="c-decision dialog" id="option_dialog"  style="display: none">--%>
<%--<div class="c-decision-head move_part">--%>
<%--</div>--%>
<%--<div class="c-decision-content" id="c-decision-option">--%>

<%--</div>--%>
<%--<c:import url="decision_options.jsp"></c:import>--%>
<%--</div>--%>

<!-- 决策管理 -->
<div class="c-jc-dialog dialog" style="display: none">
    <div class="c-select-one">
        <select  id="jcStatus" class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">
            <option data="0" value="待选">待选</option>
            <option data="1" value="通过">通过</option>
            <option data="2" value="拒绝">拒绝</option>
        </select>
    </div>
</div>


<!-- 分群管理 -->
<div class="c-swarm-dialog dialog" style="display: none">
    <div class="c-swarm-head move_part">
    </div>
    <div class="c-swarm-content c_content">
        <div class="c-swarm-interior">
            <div class="c-swarm-interior-left">
                <div class="c-swarm-name c-title">分组<b class="datas groupNum">1</b></div>
                <div class="c-contains-outside">
                    <div class="c-contained-within">
                        <div class="c-select-two">
                            <input class="c-swarm-input datas" valueScope="" fieldId="" field_type="" field_code="" type="text" value="待选" readonly>
                            <select  class="l_before datas" style="width: 60px;background-position: 40px 0px;">
                                <option data="0" value="待选">待选</option>
                            </select>
                        </div>
                        <div class="c-swarm-name ">
                            <input type="text" class="datas" value=""/>
                        </div>
                        <div class="c-select-one">
                            <select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">
                                <option data="0" value="待选">待选</option>
                                <option data="&&" value="且">且</option>
                                <option data="||" value="或">或</option>
                            </select>
                        </div>

                        <div class="c-select-two">
                            <input class="c-swarm-input datas" valueScope="" fieldId="" field_type="" field_code="" type="text" value="待选" readonly>
                            <select  class="l_before datas" style="width: 60px;background-position: 40px 0px;">
                                <option data="0" value="待选">待选</option>
                            </select>
                        </div>
                        <div class="c-swarm-name ">
                            <input type="text" class="datas" value=""/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="c-swarm-name">
                <div class="fs5  addCondition"><span class="icon-addData"  style="color:#57a3f1;font-weight: 900;"></span></div>'+
                <div class="fs5 delCondition"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>
            </div>
        </div>
        <%--<div class="c-swarm-interior ">--%>
        <%--<div class="c-swarm-interior-left">--%>
        <%--<div class="c-swarm-name c-title">分组<b class="datas groupNum">2</b></div>--%>
        <%--<div class="c-contains-outside">--%>
        <%--<div class="c-contained-within">--%>
        <%--<div class="c-select-two">--%>
        <%--<input class="c-swarm-input datas" valueScope="" fieldId="" field_type="" field_code="" type="text" value="待选" readonly>--%>
        <%--<select  class="l_before datas" style="width: 60px;background-position: 40px 0px;">--%>
        <%--<option data="0" value="待选">待选</option>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--<div class="c-swarm-name">--%>
        <%--<input type="text" class="datas" value="" />--%>
        <%--</div>--%>
        <%--<div class="c-select-one">--%>
        <%--<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">--%>
        <%--<option data="0" value="待选">待选</option>--%>
        <%--<option data="&&" value="且">且</option>--%>
        <%--<option data="||" value="或">或</option>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--<div class="c-select-two">--%>
        <%--<input class="c-swarm-input datas" valueScope="" fieldId="" field_type="" field_code="" type="text" value="待选" readonly>--%>
        <%--<select  class="l_before" style="width: 60px;background-position: 40px 0px;">--%>
        <%--<option data="0" value="待选">待选</option>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--<div class="c-swarm-name">--%>
        <%--<input type="text" class="datas" value=""/>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="c-swarm-name">--%>
        <%--<div class="fs5  addCondition"><span class="icon-addData"  style="color:#57a3f1;font-weight: 900;"></span></div>'+--%>
        <%--<div class="fs5 delCondition"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
    <div class="c-swarm-iexternal" style="height:30px;border-bottom:none;">
        <div class="c-swarm-name-add">
            添加分组
        </div>
    </div>
    <%--<div align="center">--%>
    <%--<button class="parameter-button setGroupSure" style="margin: 0 40px 0 0;position: relative;">确定</button>--%>
    <%--<button class="parameter-button setGroupClose" id="close" onclick="hideMessage()">取消</button>--%>
    <%--</div>--%>
</div>

<!-- 分群二 -->
<div class="c-swarms dialog" style="display: none">
    <%--<div class="c-swarms-heads move_part">--%>
    <%--<span>分群管理</span>--%>
    <%--<img  class="close" style="cursor: pointer; margin:0px 16px 0px 0px; float: right;" src="${ctx}/resources/images/prop/disapperence.png">--%>
    <%--</div>--%>
    <div class="c-sanbox-content c-groupboxs">
        <div class="c-sanbox-content  c-group-content">
        </div>
        <div align="center">
            <%--<button class="parameter-button l_selGroupSure" style="margin: 0 20px 0 0;">确定</button>--%>
            <%--<button class="parameter-button l_selGroupClose" id="close" >取消</button>--%>
        </div>
    </div>
</div>

<%--<!-- 沙盒比例 -->--%>
<%--<div class="sanbox-popup dialog" style="display: none">--%>
<%--<div class="c-sanbox-head move_part">--%>

<%--</div>--%>
<%--<div class="c-sanbox-content c-setSanbox">--%>

<%--</div>--%>
<%--</div>--%>

<%--<!-- 沙盒比例二 -->--%>
<%--<div class="sanbox-popups dialog" style="display: none">--%>
<%--<div class="c-sanbox-heads move_part">--%>
<%--<span>沙盒比例</span>--%>
<%--<img  class="close" style="cursor: pointer; margin:0px 16px 0px 0px; float: right;" src="${ctx}/resources/images/prop/disapperence.png">--%>
<%--</div>--%>
<%--<div class="c-sanbox-content c-sanboxs">--%>
<%--</div>--%>
<%--<div align="center">--%>
<%--</div>--%>
<%--</div>--%>



<%--<!-- 数据填写 -->--%>
<%--<div class="write-data-message  dialog" style="display:none;">--%>
<%--<div class="write-data-content">--%>

<%--</div>--%>
<%--</div>--%>

<!-- 选评分卡  字段弹框 -->
<%--<div class="c-createusers-dialogs  dialog option_popup" id="option_popup" style="display: none">--%>
<%--<div  class="tab-content option_popup_content" id="option_popup_content" style="display: none">--%>
<%--</div>--%>
<%--<div class="Manager_style add_user_info search_style">--%>
<%--<input type="hidden" id="isShowScoreFileds">--%>
<%--<ul class="search_content clearfix">--%>
<%--<li style="margin-left:1.5em">--%>
<%--<label class="lf">评分卡名称</label>--%>
<%--<label>--%>
<%--<input name="mobile"  type="text" class="text_add"/>--%>
<%--</label>--%>
<%--</li>--%>
<%--<li style="float:right">--%>
<%--<button id="btn_search_score"  type="button" class="btn btn-primary queryBtn">查询</button>--%>
<%--<button id="btn_search_reset_score"  type="button" class="btn btn-primary queryBtn">查询重置</button>--%>
<%--</li>--%>
<%--</ul>--%>
<%--<table style="text-align: center;cursor: pointer;margin-top: 20px" id="scoreFields_list" class="table table-striped table-bordered table-hover">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th></th>--%>
<%--<th>选择</th>--%>
<%--<th>Id</th>--%>
<%--<th>名称</th>--%>
<%--<th>描述</th>--%>
<%--<th>创建时间</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody></tbody>--%>
<%--</table>--%>
<%--</div>--%>
<%--</div>--%>

<%--决策选项 字段选项 评分卡--%>
<%--<div style="display: none" id="option_popups">--%>
<%--<div  class="c-options-createuser" align="center">--%>
<%--<div class="c-decisions-switcher" >--%>
<%--<a  id="option_fd" class="active active_active" href="#####" hrefs="showOne">字段</a>--%>
<%--</div>--%>
<%--<div class="c-decisions-switcher" style="border-right: none;">--%>
<%--<a class="active" id="option_sc" href="####" hrefs="showTwo">评分卡</a>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="Manager_style add_user_info search_style" id="showOne" style="margin-top: 30px">--%>
<%--<input type="hidden" id="isShowOptionFileds1">--%>
<%--<input type="hidden" id="isShowOptionFileds1_out">--%>
<%--<label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >--%>
<%--当前选项是:--%>
<%--<b id="showFields1">/</b>--%>
<%--<b id="hiddenFields1" style="display: none"></b>--%>
<%--<b id="valueType1" style="display: none"></b>--%>
<%--<b id="hiddenRestrainScope1" style="display: none"></b>--%>
<%--<b id="enName1" style="display: none"></b>--%>
<%--</label>--%>
<%--<div style="padding-top: 35px;" id="fieldsStyle1">--%>
<%--<ul class="search_content clearfix">--%>
<%--<li style="margin-left:1.5em">--%>
<%--<label class="lf">字段名称</label>--%>
<%--<label>--%>
<%--<input name="mobile"  type="text" class="text_add"/>--%>
<%--</label>--%>
<%--</li>--%>
<%--<li style="float:right">--%>
<%--<button id="btn_search_option1"  type="button" class="btn btn-primary queryBtn">查询</button>--%>
<%--<button id="btn_search_reset_option1"  type="button" class="btn btn-primary queryBtn">查询重置</button>--%>
<%--</li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--<table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionFields_list1" class="table table-striped table-bordered table-hover">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th></th>--%>
<%--<th>选择</th>--%>
<%--<th>名称</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody></tbody>--%>
<%--</table>--%>
<%--</div>--%>
<%--<div class="Manager_style add_user_info search_style" id="showTwo" style="display: none;margin-top: 30px">--%>
<%--<input type="hidden" id="isShowOptionFileds2">--%>
<%--<input type="hidden" id="isShowOptionFileds2_out">--%>
<%--<label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >--%>
<%--当前选项是:--%>
<%--<b id="showFields2">/</b>--%>
<%--<b id="hiddenFields2" style="display: none"></b>--%>
<%--<b id="valueType2" style="display: none"></b>--%>
<%--<b id="hiddenRestrainScope2" style="display: none"></b>--%>
<%--<b id="enName2" style="display: none"></b>--%>
<%--</label>--%>
<%--<div style="padding-top: 35px;" id="fieldsStyle2">--%>
<%--<ul class="search_content clearfix">--%>
<%--<li style="margin-left:1.5em">--%>
<%--<label class="lf">评分卡名称</label>--%>
<%--<label>--%>
<%--<input name="mobile"  type="text" class="text_add"/>--%>
<%--</label>--%>
<%--</li>--%>
<%--<li style="float:right">--%>
<%--<button id="btn_search_option2"  type="button" class="btn btn-primary queryBtn">查询</button>--%>
<%--<button id="btn_search_reset_option2"  type="button" class="btn btn-primary queryBtn">查询重置</button>--%>
<%--</li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--<table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionFields_list2" class="table table-striped table-bordered table-hover">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th></th>--%>
<%--<th>选择</th>--%>
<%--<th>名称</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody></tbody>--%>
<%--</table>--%>
<%--</div>--%>
<%--</div>--%>

<%--<!--评分卡弹出框-->--%>
<%--<div class="scoreCard dialog" style="display: none;"></div>--%>


<%--<!-- 规则集 -->--%>
<%--<c:import url="decision-rules-popup.jsp"></c:import>--%>



</body>
<script  type="text/javascript" src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js"></script>
<script  type="text/javascript" src="${ctx}/resources/js/lib/jtopo/jtopo-0.4.8-min.js" charset="utf-8"></script>
<script  type="text/javascript" src="${ctx}/resources/js/lib/jtopo/toolbar.js"  charset="utf-8"></script>
<script  type="text/javascript" src="${ctx}/resources/js/validate/map.js"></script>
<script src="${ctx}/resources/js/lib/dataTable/jquery.dataTables.js${version}"></script>
<script src="${ctx}/resources/js/lib/dataTable/dataTables.bootstrap.js${version}"></script>
<script src="${ctx}/resources/js/common/timeFormat.js${version}"></script>
<script  type="text/javascript" src="${ctx}/resources/js/procedureMange/createProcedure.js${version}"></script>
<script  type="text/javascript" src="${ctx}/resources/js/procedureMange/decision_content_all.js${version}"></script>
<script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
<script src="${ctx}/resources/assets/jstree/jstree.checkbox.js${version}"></script>


<%--<script>--%>
<%--var _version = "${time}";--%>
<%--//决策流验证框动画--%>
<%--var clicks=true;--%>
<%--var content_one;--%>
<%--$(".c-prompt-button").on("click",function(){--%>
<%--var heightOut=0;--%>
<%--for(var i=0;i<$(".c-prompt-content").length;i++){--%>
<%--heightOut=heightOut+$(".c-prompt-content").eq(i).outerHeight();--%>
<%--}--%>
<%--if(clicks){--%>
<%--content_one=$(".c-prompt-content").eq(0).outerHeight();--%>
<%--$(".c-prompt-content").animate({height:"0"},function(){--%>
<%--$(".c-prompt-content").hide();--%>
<%--clicks=false;--%>
<%--});--%>
<%--}else{--%>
<%--$(".c-prompt-content").animate({height:"31"},function(){--%>
<%--$(".c-prompt-content").eq(0).animate({--%>
<%--height:content_one--%>
<%--});--%>
<%--});--%>
<%--$(".c-prompt-content").show();--%>
<%--clicks=true;--%>
<%--}--%>
<%--});--%>
<%--</script>--%>