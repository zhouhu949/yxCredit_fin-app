<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/js/customerManage/preCustomer.js${version}"></script>
    <title>准客户</title>
    <style type="text/css">
        .laydate_body .laydate_y {margin-right: 0;}
        .commonManager table ul li {
            float: left;
            margin: 0 15px 10px 0;
        }
        .commonManager table ul li input, .commonManager table ul li select{
            height: 28px;
            padding: 4px;
            margin: 0;
            border: 1px solid #d5d5d5;
            outline: none;
        }
        #btn_search{
            width: 70px;
            height: 28px;
        }
        #menu-edit .add_menu tr{
            margin-bottom:10px;
        }
        #menu-edit .add_menu td{
            line-height:45px !important;
        }
        #Res_list thead th{
            color:#307ecc;
            font-size: 13px;
        }
        #btn{
            display: inline-block;
        }
        #Res_list_detail thead th{
            color:#307ecc;
            font-size: 13px;
        }
        #Res_list_detail_paginate{
            display: none;
        }
        #Res_list_info{
            margin-left:10px;
        }
        #Res_list{font-size: 13px;}
        #topInfo{font-size: 13px;}
        .ownCostomer{position: relative;top: -9px;margin-left: 5px;}
        #Res_list_detail{font-size: 13px;}
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <div id="content" style="width: 100%;float: right;height: 100%;overflow: auto;">
        <div class="Res commonManager" style="padding-left: 20px;padding-right: 20px">
            <div class="table_res_list">
                <table width="100%">
                    <tr>
                        <td>
                            <div class="tb_toolBar toolBar_s1 b_1_1_0">
                                <ul id="topInfo">
                                    <li class="toolBar_col">
                                        <div class="inputGroup">
                                            <span class="inputGroup-addon">客户姓名:</span>
                                            <input id="personName" class="inputGroup-input inpW_sm"/>
                                        </div>
                                    </li>

                                    <li class="toolBar_col">
                                        <div class="inputGroup">
                                            <span class="inputGroup-addon" style="width: 73px;padding-left: 9px;">日期 :</span>
                                            <input readonly="true" id="beginTime" type="text" placeholder="开始" class="eg-date" />
                                            <span class="date-icon"><i class="icon-calendar"></i></span>--
                                            <input readonly="true" id="endTime" type="text" placeholder="结束" class="eg-date">
                                            <span class="date-icon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </li>
                                    <li class="toolBar_col">
                                        <button id="paramSearch" type="button" class="btn btn-primary">查询</button>
                                    </li>
                                    <li style="width:155px;">
                                        <span class="onlyMe"><input type="checkbox" id="onlyMe"/><span class="ownCostomer">只看自己的客户</span></span>
                                    </li>
                                </ul>
                            </div>

                        </td>
                    </tr>
                </table>
                <table style="text-align: center;" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>客户id</th>
                        <%--<th>
                            <input name="checkBox_All" id="checkBox_All" type="checkbox"  class="ace" isChecked="false" />
                            <span class="lbl" style="cursor:pointer;"></span>
                        </th>--%>
                        <th>客户姓名</th>
                        <th>身份证号码</th>
                        <th>手机号</th>
                        <th>订单数</th>
                        <th>归属公司</th>
                        <th>归属部门</th>
                        <th>归属人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <div id="tile" style="display: none">
                    <span style="font-size: 16px;margin: 10px 0px 0px 10px;font-weight: bold;">对应信贷订单</span>
                </div>

                <table style="text-align: center;display:none;" id="Res_list_detail" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单id</th>
                        <th>订单编号</th>
                        <th>产品名称</th>
                        <th>产品期数</th>
                        <th>利率(年%)</th>
                        <th>还款方式</th>
                        <th>授信额度</th>
                        <th>合同金额</th>
                        <th>环节</th>
                        <th>审批状态</th>
                        <th>订单创建时间</th>
                        <th>操作</th>
                        <th>订单状态id</th>
                        <th>审批状态id</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!--添加大类的时候页面-->
    <form name="addform" id="addFrom" method="post">
        <div id="Add_Dict" style="display: none">
            <div class="commonManager ">
                <div class="addCommon">
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">字典名称</label>
                            <label>
                                <input name="name_dict" id ="dictName" type="text" value="" class="text_add" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字典Code</label>
                            <label>
                                <input name="name_code" type="text" value="" class="text_add" id="Code" />
                            </label>
                        </li>
                        <%--<li>--%>
                        <%--<label class="label_name">是否大类</label>&#12288;&#12288;--%>
                        <%--<label>--%>
                        <%--<input name="isCatagory"  type="radio" value="Y" class="text_add"  id="isCatagoryY"/>是&#12288;&#12288;--%>
                        <%--</label>--%>
                        <%--<label>--%>
                        <%--<input name="isCatagory"  type="radio" value="N" class="text_add"  id="isCatagoryN"/>否--%>
                        <%--</label>--%>
                        <%--</li>--%>
                    </ul>
                    <div class="Remark" style="padding-top: 20px;">
                        <label class="label_name">备注</label>
                        <label>
                            <textarea name="remark" id ="remark" cols="" rows="" style="width: 456px; height: 100px; padding: 5px;"></textarea>
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <!--添加子类的时候页面-->
    <form name="form" id="myForm" method="post">
        <div id="Add_Dict_Del" style="display: none">
            <div class="commonManager ">
                <div class="addCommon">
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">父分类</label>
                            <select name="selectType" type="text" class="text_add" id="selectType" placeholder=""></select>
                        </li>
                        <li>
                            <label class="label_name">字典Code</label>
                            <label>
                                <input name="name_code" type="text" value="" class="text_add" id="nameCode" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字典名称</label>
                            <label>
                                <input name="name_dict" id="nameDict" type="text" value="" class="text_add" />
                            </label>
                        </li>
                    </ul>
                    <div class="Remark" style="padding-top: 20px;">
                        <label class="label_name">备注</label>
                        <label>
                            <textarea name="remark" id="remarkdel" cols="" rows="" style="width: 456px; height: 100px; padding: 5px;"></textarea>
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="parentId">
        <input type="hidden" id="id">
        <input type="hidden" id="type">
        <input type="hidden" id="parent">
    </form>
</div>
</body>
</html>

