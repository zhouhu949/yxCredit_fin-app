<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js"></script>
    <script src="${ctx}/resources/assets/jstree/checkboxTree.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/systemMange/Dict_Ctrl.js${version}"></script>
    <title>字典管理</title>
    <style type="text/css">
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
        #Res_list_info{
            margin-left:10px;
        }
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
   <div id="content" style="width: 100%;float: right;height: 100%;overflow: auto;">
       <div>
            <span style="font-size: 20px;margin: 20px 0px 0px 25px">大类</span>
       </div>
        <div class="Res commonManager" style="padding-left: 20px;padding-right: 20px">
            <div class="table_res_list">
                <!--<form id="formBtn" name="formBtn" method="post" action="">-->
                    <div class="Manager_style add_user_info search_style" style="margin: 1em">
                        <div id="btn">
                            <ul class="search_content clearfix">
                                <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="addDict()">新增大类</button>
                            </ul>
                        </div>
                        <div style="float: right;display:inline-block;margin-right: 20px;">
                            <ul>
                                <input type="text" name="search" id="search" placeholder="字典名称" style="vertical-align: middle"/>
                                <button type="button" class="btn btn-primary queryBtn" id="paramSearch" style="top:0">查询</button>
                                <button onclick="paramSearchReset()" type="button" class="btn btn-primary queryBtn" style="top:0">查询重置</button>
                            </ul>
                        </div>
                    </div>
                <!--</form>-->
                <table style="text-align: center;" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>
                            <input name="selectAll" id="selectAll" type="checkbox"  class="ace" isChecked="false" />
                            <span class="lbl" style="cursor:pointer;"></span>
                        </th>
                        <th width="10%">字典Code</th>
                        <th width="15%">字典名称</th>
                        <th width="20%">创建日期</th>
                        <th width="20%">最后更新日期</th>
                        <th width="15%">描述</th>
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <div id="tile" style="display: none">
                    <span style="font-size: 20px;margin: 10px 0px 0px 10px">明细</span>
                </div>

                <table style="text-align: center;display:none;" id="Res_list_detail" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>
                            <input name="selectAll" id="selectdetailAll" type="checkbox"  class="ace" isChecked="false" />
                            <span class="lbl" style="cursor:pointer;"></span>
                        </th>
                        <th width="">字典Code</th>
                        <th width="">字典名称</th>
                        <th width="">字典value</th>
                        <th width="">创建日期</th>
                        <th width="">最后更新日期</th>
                        <th width="">描述</th>
                        <th width="">操作</th>
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
                            <label class="label_name">Code</label>
                            <label>
                                <input name="name_code" type="text" value="" class="text_add" id="nameCode" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">名称</label>
                            <label>
                                <input name="name_dict" id="nameDict" type="text" value="" class="text_add" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">Value</label>
                            <label>
                                <input name="value"  type="text" value="" class="text_add" />
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
<script>
</script>
</html>

