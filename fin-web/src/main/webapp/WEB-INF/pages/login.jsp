<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录</title>
    <%--<link rel="stylesheet" href="${ctx}/resources/favicon.png"/>--%>
    <link rel="icon" href="${ctx}/resources/logo.png" type="image/x-icon"/>
    <link rel="stylesheet" href="${ctx}/resources/css/login.css${version}"/>
    <%@include file="common/taglibs.jsp" %>
</head>
<body class="login">
<script type="text/javascript">

    $().ready(function () {

        //自动选中文本框
        var auto = document.getElementById('username').focus();

        var TIME_OUT_MSG = '${TIME_OUT_MSG}';
        <% session.removeAttribute("TIME_OUT_MSG");%>
        if (TIME_OUT_MSG != null && TIME_OUT_MSG != '' && TIME_OUT_MSG != 'null') {
            layer.msg("会话失效，请重新登录！", {icon: 2, time: 1000}, function () {
                Comm.getTopWinow().location = _ctx + '/login';
            });
        }
        //回车事 件
        document.onkeydown = function (e) {
            if (e.keyCode == 13) {
                $("#button").click();
            }
        }
        $('#button').click(function () {
            var index=layer.load(2);
            if ($('#username').val() == "") {
                layer.close(index);
                $("#errorMsg").text("帐号不能为空");
                return;
            } else {
                $("#errorMsg").text("");
            }
            if ($('#userpwd').val() == "") {
                layer.close(index);
                $("#errorMsg").text("密码不能为空");
                return;
            } else {
                $("#errorMsg").text("");
            }
            /* var param = "account=" + $('#username').val() + "&password=" + $('#userpwd').val();
             Comm.ajaxPost("/login", param, function (msg) {
             window.location = "/index";
             });*/
            $("#myform").submit();
        });
    });
</script>
<div class="login_logo" style="margin-top: 68px;">
    <img src="${ctx}/resources/images/logo.png"/>
</div>
<div class="login_m">
    <form id="myform" action="${ctx}/login" method="post">
        <div class="login_boder">
            <div class="login_padding">
                <span id="errorMsg">${msg}</span>
                <h2>用户名</h2>
                <label>
                    <input type="text" id="username" name="account" class="txt_input txt_input2" maxlength="20"/>
                </label>
                <h2>密码</h2>
                <label>
                    <input type="password" id="userpwd" name="password" class="txt_input" maxlength="20" />
                </label>
                <div class="rem_sub" style="text-align: center;margin-top:20px">
                    <label>
                        <input type="button" name="loginBtn" id="button" class="sub_button" value="登录"
                               style="opacity: 0.7;"/>
                    </label>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>