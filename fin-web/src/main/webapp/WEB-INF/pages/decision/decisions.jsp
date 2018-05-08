<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>引擎管理主页</title>
    <%@include file ="../common/taglibs.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/dataTables.bootstrap.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/dataTables.fontAwesome.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/css/jquery.dataTables.min.css${version}">
    <style>
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
<input type="hidden" name="initEngineVersionId" value="${initEngineVersionId}" initEngineVersionId="${initEngineVersionId}">
<input type="hidden" name="engineId" value="${engineId}">
<input type="hidden" id="getCtxs" value="${ctx}">
<div class="main-container">
    <div class="main-container-inner">
        <c:import url="leftMenu.jsp" ></c:import>
        <%@include file ="content.jsp"%>
    </div>

    <!--评分卡弹出框-->
    <div class="scoreCard dialog" style="display: none;"></div>


    <!-- 规则集 -->
    <c:import url="decision-rules-popup.jsp"></c:import>

    <!-- 黑名单 -->
    <c:import url="blacklist.jsp"></c:import>

    <!-- 白名单 -->
    <c:import url="whitelist.jsp"></c:import>

    <!-- 决策选项 -->
    <div class="c-decision dialog" id="option_dialog"  style="display: none">
        <div class="c-decision-head move_part">
        </div>
        <div class="c-decision-content" id="c-decision-option">

        </div>
        <c:import url="decision_options.jsp"></c:import>
    </div>

    <!-- 信用评级 -->
    <div class="c-credit-rate dialog" style="position:absolute;display: none">
        <div class="c-decision-head move_part">

        </div>
        <div class="c-decision-content">
            <div class="c-export-variable-head">
                <div class="c-export-content">
                    <div class="c-variable-add">
                        <span>输入变量</span>
                        <img src="${ctx}/resources/images/rules/add.png" />
                        <img src="${ctx}/resources/images/rules/delete.png" />
                    </div>
                    <select  class="l_before" style="width:124px;background-position:98px 0px;">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </div>
                <div class="c-export-variable">
                    <span>输出变量</span>
                    <select class="l_before" style="width: 124px;margin-left:75px;background-position:98px 0px;">
                        <option>信用等级</option>
                        <option>1</option>
                        <option>2</option>
                    </select>
                </div>
            </div>
            <div class="c-decision_region-content" >
                <div id="myTab" class="decision-TabControl-head" role="tablist">
                    <div class="c-decision-switcher">
                        <a href="#credit_decision" role="tab" data-toggle="tab">决策区域</a>
                    </div>
                    <div class="c-decision-switcher" style="border-right: none;">
                        <a href="#credit_editing" role="tab" data-toggle="tab">公式编辑</a>
                    </div>
                </div>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active" id="credit_decision">
                        <div class="c-field-condition">
                            <div class="c-inclusion" style="margin: 0px 0 0 0px;">
                                <div class="c-operation-inclusion">
                                    <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                    <a href="#"><img src="${ctx}/resources/images/rules/delete-2.png" /></a>
                                    <div class="c-field-return">
                                        <input type="text">
                                    </div>
                                    <div class="c-cut-off-rule"></div>
                                </div>
                                <div class="c-optional-rules">
                                    <div class="c-repetition">
                                        <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                        <a href="#"><img src="${ctx}/resources/images/rules/delete-2.png" /></a>
                                        <select  class="l_before" style="width:100px;margin:10px 5px 0 0;background-position:80px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
                                    <div class="c-repetition">
                                        <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                        <a href="#"><img src="${ctx}/resources/images/rules/delete.png" /></a>
                                        <select  class="l_before" style="width:100px;margin:10px 5px 0 0;background-position:80px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="c-inclusion">
                                <div class="c-operation-inclusion">
                                    <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                    <a href="#"><img src="${ctx}/resources/images/rules/delete-2.png" /></a>
                                    <div class="c-field-return">
                                        <input type="text" >
                                    </div>
                                    <div class="c-cut-off-rule"></div>
                                </div>
                                <div class="c-optional-rules">
                                    <div class="c-repetition">
                                        <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                        <a href="#"><img src="${ctx}/resources/images/rules/delete-2.png" /></a>
                                        <select  class="l_before" style="width:100px;margin:10px 5px 0 0;background-position:80px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="c-inclusion">
                                <div class="c-operation-inclusion">
                                    <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                    <a href="#"><img src="${ctx}/resources/images/rules/delete-2.png" /></a>
                                    <div class="c-field-return">
                                        <input type="text" >
                                    </div>
                                    <div class="c-cut-off-rule"></div>
                                </div>
                                <div class="c-optional-rules">
                                    <div class="c-repetition">
                                        <a href="#"><img src="${ctx}/resources/images/rules/add.png" /></a>
                                        <a href="#"><img src="${ctx}/resources/images/rules/delete-2.png" /></a>
                                        <select  class="l_before" style="width:100px;margin:10px 5px 0 0;background-position:80px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        <select  class="l_before" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="credit_editing">
                        <div class="c-field-equation">
                            <div class="c-field-operator-head">
                                <div class="c-operational-character">
                                    <img src="${ctx}/resources/images/scorecard/1.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/1_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-character">
                                    <img src="${ctx}/resources/images/scorecard/2.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/2_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-character">
                                    <img src="${ctx}/resources/images/scorecard/3.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/3_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-character">
                                    <img src="${ctx}/resources/images/scorecard/4.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/4_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-characterTwo">
                                    <img src="${ctx}/resources/images/scorecard/5.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/5_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-characterTwo">
                                    <img src="${ctx}/resources/images/scorecard/6.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/6_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-characterTwo">
                                    <img src="${ctx}/resources/images/scorecard/7.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/7_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-character">
                                    <img src="${ctx}/resources/images/scorecard/8.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/8_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-characterTwo">
                                    <img src="${ctx}/resources/images/scorecard/10.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/10_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-characterTwo">
                                    <img src="${ctx}/resources/images/scorecard/11.png" class="c-operator-show">
                                    <img src="${ctx}/resources/images/scorecard/11_1.png" class="c-operator-hide">
                                </div>
                                <div class="c-operational-characterTwo">
                                    <img src="${ctx}/resources/images/scorecard/19.png" class="c-operator-show">
                                    <!--<img src="img/operator/19-1.png" class="c-operator-hide">-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="c-decision_region-content" style="display: none;">
                <div id="myTab" class="decision-TabControl-head" role="tablist">
                    <div class="c-decision-switcher">
                        <a href="#credit_decisionTwo" role="tab" data-toggle="tab">决策区域</a>
                    </div>
                    <div class="c-decision-switcher" style="border-right: none;">
                        <a href="#credit_editingTwo" role="tab" data-toggle="tab">公式编辑</a>
                    </div>
                </div>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active" id="credit_decisionTwo">
                        <div class="c-decision_region">
                            <div class="c-first-line">
                                <div class="c-front-portion-head">
                                    <div class="c-variate-a">决策变量A</div>
                                </div>
                                <div class="c-queen-portion">
                                    <div class="c-variate-b">
                                        决策变量B
                                    </div>
                                    <div class="c-section-decision">
                                        <div class="c-section-left">
                                            <img src="${ctx}/resources/images/rules/add.png" />
                                            <img src="${ctx}/resources/images/rules/delete.png" />
                                            <input type="text" />
                                            <span>-</span>
                                            <input type="text" />
                                        </div>
                                        <div class="c-dotted-line"></div>
                                        <div class="c-section-right">
                                            <input type="text" />
                                            <span>-</span>
                                            <input type="text" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-next-line">
                                <div class="c-front-portion">
                                    <img src="${ctx}/resources/images/rules/add.png" />
                                    <img src="${ctx}/resources/images/rules/delete.png" />
                                    <input type="text" />
                                    <span>-</span>
                                    <input type="text" />
                                </div>
                                <div class="c-queen-portion">
                                    <div class="ge-through">
                                        <input type="text" value="信用等级">
                                    </div>
                                    <div class="c-dotted-line"></div>
                                    <div class="turn-down">
                                        <input type="text" value="信用等级">
                                    </div>
                                </div>
                            </div>
                            <div class="c-next-line">
                                <div class="c-front-portion">
                                    <img src="${ctx}/resources/images/rules/add.png" />
                                    <img src="${ctx}/resources/images/rules/delete.png" />
                                    <input type="text" />
                                    <span>-</span>
                                    <input type="text" />
                                </div>
                                <div class="c-queen-portion">
                                    <div class="ge-through">
                                        <input type="text" value="信用等级">
                                    </div>
                                    <div class="c-dotted-line"></div>
                                    <div class="turn-down">
                                        <input type="text" value="信用等级">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="credit_editingTwo">
                    </div>
                </div>
            </div>
            <div align="center">
                <button class="parameter-button" style="margin: 0 40px 0 0;">确定</button>
                <button class="parameter-button" id="close" onclick="hideMessage()">取消</button>
            </div>
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
            <div class="c-swarm-interior ">
                <div class="c-swarm-interior-left">
                    <div class="c-swarm-name c-title">分组<b class="datas groupNum">2</b></div>
                    <div class="c-contains-outside">
                        <div class="c-contained-within">
                            <div class="c-select-two">
                                <input class="c-swarm-input datas" valueScope="" fieldId="" field_type="" field_code="" type="text" value="待选" readonly>
                                <select  class="l_before datas" style="width: 60px;background-position: 40px 0px;">
                                    <option data="0" value="待选">待选</option>
                                </select>
                            </div>
                            <div class="c-swarm-name">
                                <input type="text" class="datas" value="" />
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
                                <select  class="l_before" style="width: 60px;background-position: 40px 0px;">
                                    <option data="0" value="待选">待选</option>
                                </select>
                            </div>
                            <div class="c-swarm-name">
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
    <div class="c-swarms dialog" style="position:absolute;display: none">
        <div class="c-swarms-heads move_part">
            <span>分群管理</span>
            <img  class="close" style="cursor: pointer; margin:0px 16px 0px 0px; float: right;" src="${ctx}/resources/images/prop/disapperence.png">
        </div>
        <div class="c-sanbox-content c-groupboxs">
            <div class="c-sanbox-content  c-group-content">
            </div>
            <div align="center">
                <button class="parameter-button l_selGroupSure" style="margin: 0 20px 0 0;">确定</button>
                <button class="parameter-button l_selGroupClose" id="close" >取消</button>
            </div>
        </div>
    </div>

    <!-- 沙盒比例 -->
    <div class="sanbox-popup dialog" style="display: none">
        <div class="c-sanbox-head move_part">

        </div>
        <div class="c-sanbox-content c-setSanbox">

        </div>
        <%--<div align="center">--%>
            <%--<button class="parameter-button rateSure" style="margin: 0 20px 0 0;">确定</button>--%>
            <%--<button class="parameter-button rateClose" id="close" onclick="hideMessage()">取消</button>--%>
        <%--</div>--%>
    </div>

    <!-- 沙盒比例二 -->
    <div class="sanbox-popups dialog" style="display: none">
        <div class="c-sanbox-heads move_part">
            <span>沙盒比例</span>
            <img  class="close" style="cursor: pointer; margin:0px 16px 0px 0px; float: right;" src="${ctx}/resources/images/prop/disapperence.png">
        </div>
        <div class="c-sanbox-content c-sanboxs">
        </div>
        <div align="center">
            <%--<button class="parameter-button l_sanboxSure" style="margin: 0 20px 0 0;">确定</button>--%>
            <%--<button class="parameter-button l_sanboxClose" id="close">取消</button>--%>
        </div>
    </div>

    <!-- 选择字段弹框 -->
    <div class="c-createusers-dialog selWord dialog group-dialog" id="" style="display: none">
        <input type="hidden" value="" id="showFieldsPlace">
        <div style="padding-top: 8px;" id="fieldsStyle">
            <label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
                当前选项是:
                <b id="showFields">/</b><b id="hiddenFields" style="display: none"></b>
                <b id="hiddenRestrainScope" style="display: none"></b>
                <b id="enName" style="display: none"></b>
            </label>
            <ul style="float: right">
                <input type="hidden" id="isShowFileds">
                <input type="text" name="Parameter_search" id="Parameter_search" placeholder="搜索" style="vertical-align: middle"/>
                <button type="button" class="btn btn-primary queryBtn" style="top:0" id="btn_search">查询</button>
                <button type="button" class="btn btn-primary queryBtn" style="top:0" id="btn_search_reset">查询重置</button>
            </ul>
        </div>
        <hr>
        <table style="text-align: center;cursor: pointer;margin-top: 20px" id="swarmFields_list" class="table table-striped table-bordered table-hover">
            <thead><tr><th></th><th></th><th>请选择</th></tr></thead>
            <tbody></tbody>
        </table>
    </div>

    <!-- 数据填写 -->
    <div class="write-data-message  dialog" style="display:none;">
        <div class="write-data-content">

        </div>
        <%--<div align="center">--%>
            <%--<button class="parameter-button write-execute" style="margin: 0 50px 0 0;">执行</button>--%>
            <%--<button class="parameter-button closeWindow" id="close">取消</button>--%>
        <%--</div>--%>
    </div>

    <!-- 选评分卡  字段弹框 -->
    <div class="c-createusers-dialogs  dialog option_popup" id="option_popup" style="display: none">
        <div  class="tab-content option_popup_content" id="option_popup_content" style="display: none">
        </div>
        <div class="Manager_style add_user_info search_style">
            <input type="hidden" id="isShowScoreFileds">
            <ul class="search_content clearfix">
                <li style="margin-left:1.5em">
                    <label class="lf">评分卡名称</label>
                    <label>
                        <input name="mobile"  type="text" class="text_add"/>
                    </label>
                </li>
                <li style="float:right">
                    <button id="btn_search_score"  type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset_score"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
            </ul>
            <table style="text-align: center;cursor: pointer;margin-top: 20px" id="scoreFields_list" class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th></th>
                    <th>选择</th>
                    <th>Id</th>
                    <th>名称</th>
                    <th>描述</th>
                    <th>创建时间</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        </div>
    </div>

    <%--决策选项 字段选项 评分卡--%>
    <div style="display: none" id="option_popups">
        <div  class="c-options-createuser" align="center">
            <div class="c-decisions-switcher" >
                 <a  id="option_fd" class="active active_active" href="#####" hrefs="showOne">字段</a>
            </div>
            <div class="c-decisions-switcher" style="border-right: none;">
                <a class="active" id="option_sc" href="####" hrefs="showTwo">评分卡</a>
            </div>
        </div>
        <div class="Manager_style add_user_info search_style" id="showOne" style="margin-top: 30px">
            <input type="hidden" id="isShowOptionFileds1">
            <input type="hidden" id="isShowOptionFileds1_out">
            <label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
                当前选项是:
                <b id="showFields1">/</b>
                <b id="hiddenFields1" style="display: none"></b>
                <b id="valueType1" style="display: none"></b>
                <b id="hiddenRestrainScope1" style="display: none"></b>
                <b id="enName1" style="display: none"></b>
            </label>
            <div style="padding-top: 35px;" id="fieldsStyle1">
                <ul class="search_content clearfix">
                    <li style="margin-left:1.5em">
                        <label class="lf">字段名称</label>
                        <label>
                            <input name="mobile"  type="text" class="text_add"/>
                        </label>
                    </li>
                    <li style="float:right">
                        <button id="btn_search_option1"  type="button" class="btn btn-primary queryBtn">查询</button>
                        <button id="btn_search_reset_option1"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                    </li>
                </ul>
            </div>
            <table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionFields_list1" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>选择</th>
                    <th>名称</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <div class="Manager_style add_user_info search_style" id="showTwo" style="display: none;margin-top: 30px">
            <input type="hidden" id="isShowOptionFileds2">
            <input type="hidden" id="isShowOptionFileds2_out">
            <label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
                当前选项是:
                <b id="showFields2">/</b>
                <b id="hiddenFields2" style="display: none"></b>
                <b id="valueType2" style="display: none"></b>
                <b id="hiddenRestrainScope2" style="display: none"></b>
                <b id="enName2" style="display: none"></b>
            </label>
            <div style="padding-top: 35px;" id="fieldsStyle2">
                <ul class="search_content clearfix">
                    <li style="margin-left:1.5em">
                        <label class="lf">评分卡名称</label>
                        <label>
                            <input name="mobile"  type="text" class="text_add"/>
                        </label>
                    </li>
                    <li style="float:right">
                        <button id="btn_search_option2"  type="button" class="btn btn-primary queryBtn">查询</button>
                        <button id="btn_search_reset_option2"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                    </li>
                </ul>
            </div>
            <table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionFields_list2" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>选择</th>
                    <th>名称</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

</div>
<script type="text/javascript" src="${ctx}/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/validate/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/decision/danxuan.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/validate/jquery.serialize-object.js" ></script>
<script type="text/javascript" src="${ctx}/resources/js/validate/dialog.js"  charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/resources/js/validate/map.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/lib/jtopo/jtopo-0.4.8-min.js" charset="utf-8"></script>
<script src="${ctx}/resources/js/lib/dataTable/jquery.dataTables.js${version}"></script>
<script src="${ctx}/resources/js/lib/dataTable/dataTables.bootstrap.js${version}"></script>
<script src="${ctx}/resources/js/common/timeFormat.js${version}"></script>
<script type="text/javascript" src="${ctx}/resources/js/decision/decision_content.js${version}" ></script>
</body>
</html>
