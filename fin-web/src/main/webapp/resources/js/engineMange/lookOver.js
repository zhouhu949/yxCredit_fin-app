/**
 * Created by Win7 on 2017/5/24.
 */
$(function(){
    var id=$("#resultSetId").val();
    var param={};
    param.resultSetId=id;
    console.log(param);
    Comm.ajaxPost('engine/pageinput', param, function (data) {
        console.log(data);
        var erlist=data.data.er;
        var blackList=data.data.blackList;//黑名单
        var whiteList=data.data.whiteList;//白名单
        var deadRules=data.data.deadRules;//硬性拒绝规则
        var focusRules=data.data.focusRules;//加减分规则
        $("#engineId").html(erlist.engineId);
        $("#engineCode").html(erlist.engineCode);
        $("#engineName").html(erlist.engineName);
        $("#result").html(erlist.result);
        $("#scorecardscore").html(erlist.scorecardscore);
        //黑名单
        var blackhtml="";
        if(blackList==null||blackList.length==0){
            blackhtml+="<p class='target'>未命中</p>";
            $(".emptylistOne ul").html(blackhtml);
        }else{
            for(var i=0;i<blackList.length;i++){
                blackhtml+="<li>"+blackList[i].type+"</li><li>"+blackList[i].name+"</li><li>"+blackList[i].desc+"</li>";
            }
            $(".emptylistOne ul").html(blackhtml);
            var blackliList=$(".emptylistOne ul li");
            for(var i=0;i<blackliList.length;i++){
                if(blackliList[i].html()=='null'){
                    blackliList[i].html("");
                }
            }
        }
        //白名单
        var whitehtml="";
        if(whiteList==null||whiteList.length==0){
            whitehtml+="<p class='target'>未命中</p>";
            $(".emptylistTwo ul").html(whitehtml);
        }else{
            for(var i=0;i<whiteList.length;i++){
                whitehtml+="<li>"+whiteList[i].type+"</li><li>"+whiteList[i].name+"</li><li>"+whiteList[i].desc+"</li>";
            }
            $(".emptylistTwo ul").html(whitehtml);
            var whiteliList=$(".emptylistTwo ul li");
            for(var i=0;i<whiteliList.length;i++){
                if(whiteliList[i].html()=='null'){
                    whiteliList[i].html("");
                }
            }
        }

        //硬性拒绝规则
        var refusehtml="";
        if(deadRules==null||deadRules.length==0){
            refusehtml+="<p class='target'>未命中</p>";
            $(".emptylistThree ul").html(refusehtml);
        }else{
            for(var i=0;i<deadRules.length;i++){
                refusehtml+="<li>"+deadRules[i].id+"</li><li>"+deadRules[i].name+"</li><li>"+deadRules[i].desc+"</li><li>"+deadRules[i].expression+"</li>"
            }
            $(".emptylistThree ul").html(refusehtml);
            var refuseliList=$(".emptylistThree ul li");
            for(var i=0;i<refuseliList.length;i++){
                if($(refuseliList[i]).html()=='null'){
                    $(refuseliList[i]).html("");
                }
            }
        }
        //加减分规则
        var rulehtml="";
        if(focusRules==null||focusRules.length==0){
            rulehtml+="<p class='target'>未命中</p>";
            $(".emptylistFour ul").html(rulehtml);
        }else{
            for(var i=0;i<focusRules.length;i++){
                rulehtml+="<li>"+focusRules[i].id+"</li><li>"+focusRules[i].name+"</li><li>"+focusRules[i].desc+"</li><li>"+focusRules[i].expression+"</li>"
                $(".emptylistFour ul").html(rulehtml);
                var ruleliList=$(".emptylistFour ul li");
                for(var i=0;i<ruleliList.length;i++){
                    if($(ruleliList[i]).html()=='null'){
                        $(ruleliList[i]).html("");
                    }
                }
            }
        }
    })
})
