<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${ctx}/resources/js/common/Header.js${version}"></script>
<script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
<script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
<div class="navbar" id="navbar">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="${ctx}/index" class="navbar-brand">
                <span id="titleName">
                    <!--<b>小窝金融消费管理系统</b>-->
                    <img src="${ctx}/resources/images/logo1.png${version}" style="width: 140px;height: 45px">
                </span>
            </a>
        </div>
        <div class="navbar-header pull-right" role="navigation">
            <div class="toolBar">
                <ul class="nav ace-nav">
                    <%--<li><a href="javascript:void(0)" name="taskMsg/monitor" class="iframeurl" title="">--%>
                        <%--<span class="menu-text"> 任务监控 </span>--%>
                    <%--<p><b id="jiankong"></b></p></a></li>--%>
                    <%--<li><a href="javascript:void(0)" class="iframeurl" name="taskMsg/pageNot" ><p>待处理<b id="untreated"></b></p></a></li>
                    <li><a href="javascript:void(0)" class="iframeurl" name="taskMsg/pageIn" ><p>处理中<b id="waite"></b></p></a></li>
                    <li><a href="javascript:void(0)" class="iframeurl" name="taskMsg/pagePast" ><p>已处理<b id="processed"></b></p></a></li>--%>
                    <li><a href="javascript:void(0)" onclick="logout()">退出系统</a></li>
                    <li><a href="javascript:void(0)" onclick="changePwd()" style="border-left:1px solid #BF2025">修改密码</a></li>
                    <li style="position: relative;text-align: center;padding-bottom:30px;border-right: 0" id="showPerssonParent">
                        <img src="${ctx}/resources/images/person.png" alt="" style="margin:0 .5em;cursor: pointer;width:50%;position: relative;top:-2px;left:3px;">
                        <div id="showPreson">
                            <p>用户名:<b id="loginName">${account}</b></p>
                            <p>角色:<b id="loginPreson">${roleNames}</b></p>
                        </div>
                    </li>
                    <li style="position: absolute">
                        <img src="${ctx}/resources/images/person.png" alt="" style="visibility: hidden;margin:0 .5em;cursor: pointer;width:43%">
                    </li>
                    <%--<li style="position: relative;padding-bottom:30px;border-right: 0" id="showTipsParent" onclick="showDiv()">--%>
                        <%--<img src="${ctx}/resources/images/msg.png" alt="" id="img" style="margin:0 .5em;cursor: pointer;">--%>
                        <%--<div id="showTips">--%>
                            <%--<input id="type" type="hidden">--%>
                            <%--<input id="typed" type="hidden">--%>
                            <%--<a href="javascript:void(0)" url="${ctx}/taskMsg/pageIn"><p>处理中:<b id="waite"></b></p></a>--%>
                            <%--<a href="javascript:void(0)" url="${ctx}/taskMsg/pageNot"><p>待处理:<b id="untreated"></b></p></a>--%>
                            <%--<a href="javascript:void(0)" url="${ctx}/taskMsg/monitor"><p>任务监控:<b id="processed"></b></p></a>--%>
                        <%--</div>--%>
                    <%--</li>--%>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- update password -->
<div class="change_Pass_style" id="change_Pass">
    <ul class="xg_style">
        <li>
            <label for="password" class="label_name">原&nbsp;&nbsp;密&nbsp;码</label>
            <input name="oldPwd" type="password" id="password"  maxlength="20"/>
        </li>
        <li>
            <label for="Nes_pas" class="label_name">新&nbsp;&nbsp;密&nbsp;码</label>
            <input name="newPwd" type="password" id="Nes_pas" maxlength="20"/>
        </li>
        <li>
            <label for="c_mew_pas" class="label_name">确认密码</label>
            <input name="confirmPwd" type="password" id="c_mew_pas" maxlength="20"/>
        </li>
    </ul>
</div>

<script type="application/javascript">
    $(document).click(function(e){
        var divTop = $('#showTips');   // 设置目标区域
        var divToped = $('#img');
        if(!divToped.is(e.target) && divToped.has(e.target).length === 0){
            divTop.hide();
            $("#type").val("");
        }
    })
</script>