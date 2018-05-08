/**
 * Created by Administrator on 2017/5/2 0002.
 */
function logout() {
    layer.confirm('是否确定退出系统？', {
        btn: ['是', '否']
    }, function () {
        location.href = _ctx + "/logout";
    });
}

function changePwd() {
    $('#password').val("");
    $('#Nes_pas').val("");
    $('#c_mew_pas').val("");
    layer.open({
        type: 1,
        title: '修改密码',
        area: ['300px', '300px'],
        shadeClose: true,
        content: $('#change_Pass'),
        btn: ['确认','取消'],
        yes: function (index) {
            var Oldpwd = $('#password').val();
            var Newpwd = $('#Nes_pas').val();
            var ComfireNewpwd = $('#c_mew_pas').val();
            if (!Oldpwd || !Newpwd || !ComfireNewpwd) {
                layer.msg('密码不能为空!', {time: 2000});
                return;
            }
            if (Newpwd != ComfireNewpwd) {
                layer.msg('新密码与确认密码不一致!', {time: 2000});
                return;
            }
            if(Newpwd.length <6 || ComfireNewpwd<6 ){
                layer.msg('密码至少6位!', {time: 2000});
                return ;
            }
            var patrn=/^(\w){6,20}$/;
            if (!patrn.exec(Newpwd)) {
                layer.msg('密码不能包含特殊符号!', {time: 2000});
                return ;
            }
            Comm.ajaxPost(
                'user/changePwd',
                {
                    originPwd: Oldpwd,
                    confirmPwd: ComfireNewpwd,
                    newPwd: Newpwd
                },
                function (data) {
                    layer.msg(data.msg, {time: 2000},function(){
                        if(data.code=='-1'){
                            return;
                        }else if(data.code=='0'){
                            layer.closeAll();
                        }
                    });
                }
            );
        }
    });
}
$().ready(function(){
    $("#showTips").hide();//隐藏
   // $("#showTipsParent").hover(function () {
        Comm.ajaxGet("taskMsg/getCount", null, function (result) {
            var data = result.data;
            $("#waite").text(data.inCount);
            $("#untreated").text(data.notCount);
            $("#processed").text(data.pastCount);
        },null,false);
        //$("#showTips").slideDown();
  //  },function(){ $("#showTips").slideUp();});
    // $("#showTips").hover(function(){},function () {
    //    $("#showTips").slideUp();
    // });
    $("#showTips a").on("click",function(){
        var href=$(this).attr("url");
        $("#iframe").attr("src",href);
    });

    $("#showPerssonParent").hover(function () {
        $("#showPreson").slideDown();
    },function(){ $("#showPreson").slideUp();})
    // $("#showPreson").hover(function(){},function () {
    //     $("#showPreson").slideUp();
    // });
})

function showDiv(type) {
    debugger
    var flag = $("#type").val();
    if(flag==null||flag==""){
        Comm.ajaxGet("taskMsg/getCount", null, function (result) {
            var data = result.data;
            $("#waite").text(data.inCount);
            $("#untreated").text(data.notCount);
            $("#processed").text(data.pastCount);
        },null,false);
        $("#type").val("on");
        $("#showTips").show();
    }
    else {
        $("#type").val("");
        $("#showTips").hide();
    }
}

