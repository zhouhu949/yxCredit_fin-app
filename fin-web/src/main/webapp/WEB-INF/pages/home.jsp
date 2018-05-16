<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="homeApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link rel="stylesheet" href="${ctx}/resources/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace.min.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="${ctx}/resources/css/home.css"/>
    <script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js${version}"></script>
<title></title>
<style type="text/css">
    .index_style  li{
        width: 32% !important;
    }
</style>
</head>
<body>
<%--<div class="page-content"  ng-controller="homeCtrl" >--%>
    <%--<div id="xiaofuBg">--%>
        <%--<img src="${ctx}/resources/images/miaofuBg1.jpg" width="100%"  alt="">--%>
    <%--</div>--%>
    <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="col-xs-12">&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;<div class="index_style">&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<div class="frame xjdd_style">&ndash;%&gt;&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;<span class="title_name">用户信息</span>&ndash;%&gt;&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;<div class="content">&ndash;%&gt;&ndash;%&gt;--%>
                        <%--&lt;%&ndash;&lt;%&ndash;<ul class="xj_list clearfix">&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">用户名</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                   <%--&lt;%&ndash;&lt;%&ndash;<b>${account}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">角色</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                   <%--&lt;%&ndash;&lt;%&ndash;<b>${role}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">最后登录IP</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${lastLoginIp}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                        <%--&lt;%&ndash;&lt;%&ndash;</ul>&ndash;%&gt;&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<!--结束-->&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<div class="frame ddgl_style">&ndash;%&gt;&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;<span class="title_name">系统信息</span>&ndash;%&gt;&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;<div class="content">&ndash;%&gt;&ndash;%&gt;--%>
                        <%--&lt;%&ndash;&lt;%&ndash;<ul class="xj_list clearfix">&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">IP地址</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.hostIp}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">主机名</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.hostName}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">操作系统</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.osName}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">系统架构</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.arch}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">系统版本</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                   <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.osVersion}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">处理器个数</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.processors}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">Java版本</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.javaVersion}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<li style="width: 600px!important;">&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<label class="label_name">Java路径</label>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="xinxi">&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<b>${systemInfo.javaHome}</b>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</li>&ndash;%&gt;&ndash;%&gt;--%>
                        <%--&lt;%&ndash;&lt;%&ndash;</ul>&ndash;%&gt;&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<!--结束-->&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<!-- 首页样式结束 -->&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--</div>--%>
<%--<script>--%>
    <%--$("#xiaofuBg").css("height",screen.height)--%>
<%--</script>--%>
</body>
</html>
