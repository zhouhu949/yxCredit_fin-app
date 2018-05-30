<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/15
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/productManage/feeProduct.js${version}"></script>
    <title>费率管理</title>
    <style>
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
        .tdleft{text-align : left; }
        .tdright {text-align :right; font-weight:900}
        .commonManager .addCommon .liWidth {width: 294px;}
        .licss{ width: 110px}
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="empId" value="${userId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li style="width:155px;">
                    <button id="btnAdd" type="button" class="btn btn-primary queryBtn" onclick="editDetail('1','1','1')">新增</button>
                </li>
                <%--<li style="width:155px;padding-top: 10px;">
                    <span class="onlyMe"><input type="checkbox" id="onlyMe"/>只看自己的客户</span>
                </li>--%>
            </ul>
        </div>
        <div class="Manager_style">
            <input type="hidden" value="" id="customerId" />
            <div class="fee_list">
                <table style="cursor:pointer;" id="fee_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th></th>
                        <th>产品名称</th>
                        <th>产品期数</th>
                        <%--<th>综合费率(%)</th>--%>
                        <th>年利息(%)</th>
                        <%--<th>月利息(%)</th>--%>
                        <th>日利息(%)</th>
                        <%--<th>支付服务费(%)</th>--%>
                        <th>担保费率(%)</th>
                        <%--<th>风险评估费(%)</th>--%>
                        <%--<th>快速信审费(元)</th>--%>
                        <%--<th>滞纳金(%)</th>--%>
                       <th>逾期费率(%)</th>
                        <%--<th>综合日费率(%)</th>--%>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <%--费率信息--%>
        <div id="editDetail" style="display: none">
            <div class="addCommon clearfix">
                <ul id="detailInfo" class="clearfix">
                    <li class="liWidth">
                        <label class="lf licss">产品名称</label>
                        <label>
                            <select id="productAmount" onchange="getPeriods()">
                                <option value="">请选择</option>
                            </select>
                        </label>
                    </li>
                    <li class="liWidth">
                        <label class="lf licss">产品期数</label>
                        <label >
                            <select id="productPeriods">
                                <option value="">请选择</option>
                            </select>
                        </label>
                    </li>
                    <%--<li>--%>
                        <%--<label class="lf"  >综合费率(%)</label>--%>
                        <%--<label >--%>
                           <%--<input type="text" name="productComFee" id="productComFee">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                    <li class="liWidth">
                        <label class="lf licss"  >年利率(%)</label>
                        <label >
                            <input type="text" name="li_xi" id="nianlixi" >
                        </label>
                    </li>
                    <%--<li style="width:260px">--%>
                        <%--<label class="lf licss"  >月利率(%)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text" name="li_xi" id="yuelixi"  readonly="readonly">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                    <li class="liWidth">
                        <label class="lf licss"  >日利率(%)</label>
                        <label >
                            <input type="text" name="li_xi" id="li_xi"  readonly="readonly">
                        </label>
                    </li>
                    <li class="liWidth">
                        <label class="lf licss"  >逾期费率(%)</label>
                        <label >
                            <input type="text" name="yuqi_fee" id="yuqi_fee">
                        </label>
                    </li>
                    <%--<li>--%>
                        <%--<label class="lf"  >支付服务(%)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text" name="zhifu_fee" id="zhifu_fee">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                    <%--<li style="width:260px">--%>
                        <%--<label class="lf licss"  >平台管理(元)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text"  name="zhanghu_fee" id="zhanghu_fee">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                    <%--<li style="width:260px">--%>
                        <%--<label class="lf licss"  >借款利率(%)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text"  name="loan_rate" id="loan_rate">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<label class="lf"  >风险评估(%)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text" name="fengxian_fee" id="fengxian_fee">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                    <li class="liWidth">
                        <label class="lf licss"  >担保费率(%)</label>
                        <label >
                            <input type="text" name="asuer_rate" id="asuer_rate">
                        </label>
                    </li>
                    <%--<li>--%>
                        <%--<label class="lf"  >滞纳金(%)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text" name="zhina_fee" id="zhina_fee">--%>
                        <%--</label>--%>
                    <%--</li>--%>

                    <%--<li>--%>
                        <%--<label class="lf"  >综合日费(%)</label>--%>
                        <%--<label >--%>
                            <%--<input type="text" name="zongheri_fee" id="zongheri_fee">--%>
                        <%--</label>--%>
                    <%--</li>--%>
                </ul>
            </div>
            <div>
                <ul>
                <li style="width:155px;margin: 0 auto;">
                    <button id="zbsAdd" type="button" class="btn btn-primary queryBtn" onclick="addZBS()">新增</button>
                </li>
                </ul>
            </div>

        </div>
    </div>
</div>
</body>
</html>
