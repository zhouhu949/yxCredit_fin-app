<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/zTree/css/zTreeStyle/zTreeStyle.css" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/contractorManage/contractor.js${version}"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/treeTable/js/jquery.treetable.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/js/jquery.form.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/tree.js"></script>
    <title>总包商信息</title>
    <style>
        #organ{outline: none!important;appearance:none; -moz-appearance:none; -webkit-appearance:none;height:28px;line-height:19px;}
        #email{margin-left:0px!important;}
        #isLock{height:28px;}
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <%--<form id="form1" name="form1" method="post" action="">--%>
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <li><label class="lf">总包商名称</label>
                        <label>
                            <input name="trueName" type="text" class="text_add"/>
                        </label>
                    </li>
                    <button id="btn_search"  type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="updateContractor(1)" id="Add_user_btn">添加总包商</button>
                </ul>
                <%--<ul class="search_content clearfix">--%>
                    <%----%>
                    <%--<button type="button" class="btn btn-primary deleteBtn" id="deleteBtn" onclick="deleteUser()">删除</button>--%>
                    <%--<button type="button" class="btn btn-primary resetBtn" id="resetBtn" onclick="resetPwd()">重置密码</button>--%>
                <%--</ul>--%>
                <div id="Add_user_style" style="display: none">
                    <div class="addCommon clearfix">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">总包商名称</label>
                                <label for="contractorName">
                                    <input name="contractor_name"  type="text"  id="contractorName"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">联系人</label>
                                <label for="linkman">
                                    <input name="contractor_linkman"  type="text" id="linkman" maxlength="15"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">联系方式</label>
                                <label for="linkmanPhone">
                                    <input name="contractor_mobile"  type="text" id="linkmanPhone" maxlength="11"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">授信额度</label>
                                <label for="credit">
                                    <input name="contractor_credit"  type="text" id="credit" maxlength="10"/>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">状态</label>
                                <label>
                                    <select name="state" size="1" id="state">
                                        <option value=1 id="qiyong">启用</option>
                                        <option value=0 id="jinyong">停用</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li style="width:524px;">
                                <form id="contractorImgForm" method="post" enctype="multipart/form-data" >
                                    <label class="label_name">营业执照附件</label>
                                    <div id="localImag" style="padding-left:3px;"><img id="preview" src="" width="100" height="20" style="display: block; width: 100px; height: 20px;"></div>
                                    <input type="file" name="file" id="file" style="width:100px;" onchange="javascript:setImagePreview1();" >
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="Manager_style" id="asignRole" style="display: none">
                    <div class="User_list">
                        <table style="cursor:pointer;" id="userList" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <input id="allSelectId" name="allSelectCheckBox" type="checkbox" onclick="selectAll('allSelectCheckBox', 'allSelectId', 'userCheckBox')" class="ace" />
                                    <span class="lbl" style="cursor:pointer;"></span>
                                </th>
                                <th>姓名</th>
                                <th>手机号</th>
                            </tr>
                            </thead>
                            <tbody id="User_list">
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
       <%-- </form>--%>
        <!-- 弹框部门tree-->
        <div id="jstree" style="height: 463px;overflow:auto;display: none;" class="ztree">
        </div>
        <div class="Manager_style">
            <div class="User_list">
                <table style="cursor:pointer;" id="Contractor_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>总包商名称</th>
                        <th>联系人</th>
                        <th>联系方式</th>
                        <th>当前授信额度</th>
                        <th>入驻日期</th>
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