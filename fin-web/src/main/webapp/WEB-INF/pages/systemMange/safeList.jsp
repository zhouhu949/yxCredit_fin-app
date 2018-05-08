<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css${version}" />
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/systemMange/safeList.js${version}"></script>
    <title>安全管理</title>
    <style type="text/css">
        #safe_list thead th{
            color:#307ecc;
            font-size: 13px;
        }
    </style>
</head>
<body>
    <div class="page-content">
        <div class="commonManager">
            <div >
                <input name="confName" placeholder="配置名称" type="text" class="text_add"/>
                <button id="btn_search"  type="button" class="btn btn-primary queryBtn" style="margin-bottom: 10px;">查询</button>
                <button id="btn_search_reset"  type="button" class="btn btn-primary queryBtn" style="margin-bottom: 10px;">查询重置</button>
            </div>
        </div>
        <div id="isShowCode" style="display: none;margin: 2em auto">
            <label for="isShow_is">是<input type="radio" name="isShow_is" id="isShow_is" value="是"></label>
            <label for="isShow_no">否<input type="radio" name="isShow_is" id="isShow_no" value="否" checked></label>
        </div>
        <div id="pwdNum" style="display: none;margin: 2em auto">
            <label for="pwdNum_input">设置密码错误次数 <input type="text" id="pwdNum_input" name="pwdNum_input"></label>
        </div>
        <div class="Manager_style">
            <div class="User_list">
                <table style="cursor:pointer;" id="safe_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>配置名称</th>
                        <th>配置结果</th>
                        <th>描述</th>
                        <th>最后更新时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>

