<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src='${ctx}/resources/js/merchantManagement/merchantAntiFraudMain.js${version}'></script>
    <script type="text/javascript" src="${ctx}/resources/assets/treeTable/js/jquery.treetable.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/resources/assets/zTree/tree.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/assets/zTree/css/zTreeStyle/zTreeStyle.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <script src="https://gosspublic.alicdn.com/aliyun-oss-sdk-4.4.4.min.js"></script>
    <script src="${ctx}/resources/js/areaQuota/city.data-3.js${version}"></script>
    <title>商户反欺诈</title>
    <style type="text/css">
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #sign_list{font-size:13px;}
        .checkShow,.uploadContact{color:#05c1bc}
        .checkShow{margin-right: 8px;}

        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}

        #divFrom input{border:none;background-color: #fff;text-align: left;}
        .unit{position: relative; top:1px;}
        /*合理性样式*/
        #showReasonable input[name=ReasonableInput],#showReasonable input[name=ReasonableInput_fitment],#showReasonable input[name=ReasonableInput_fitment_money]{border:1px solid #ddd}
        .ReasonableUl li{
            display: inline-block;
            width: 10%;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
        }
        .ReasonableUl li:hover{
            display: inline-block;
            width: 25%;
            transition: width 2s;
            -moz-transition: width 2s;
            -webkit-transition: width 2s;
            -o-transition: width 2s;
            border: 1px solid #ddd;
            padding:.5em;
            margin:.5em 0;
        }
        .ReasonableUl li img{
            width: 100%;
            cursor: pointer;
        }
        .answerRemark,#elecRemark,#customerRemark,#giveAmount{width: 100%;position: relative;left:-10px;}
        #customerRemark,#elecRemark,#advice{width: 96%;margin-left: 20px;}
        .imagediv{
            width:150px;
            height:150px;
            background-color:white;
            text-align: center;
            float: left;
            position: relative;
            margin-right: 1em;
        }
        .addMaterial{
            width:150px;
            height:150px;
            vertical-align: middle;
        }
        .picShow{
            position:absolute;
            z-index:100;
            opacity:0;
            filter:alpha(opacity=0);
            height:150px;
            width:150px;
            readonly:true;
        }
        #firstCredit,#passRemark,#cheatRemark{width:95%;float:left;margin-left:0px;margin-left:4px;}
        #cheatRemark{width: 99%;}
        .telRecord{height: 37px;  text-align: left;  padding-left: 35px;}
        #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#answerBody td,#answerBody1 td{border:none;border-right:1px solid #ccc}
        #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}
        .answerTh th{text-align: center; font-weight: normal;border:none;}
        .answerTh thead{border:1px solid #ccc;border-top:none;background: #DFF1D9}
        .cencus{height: 40px;width:100%;line-height:40px;float:left;border-right:1px solid #ccc;border-left:1px solid #ccc;background-color: white; text-align: left;
            padding-left: 29px;
        }
        .MerInput{
            margin:.5em 0;
        }
        #MerMsg tr td{
            text-align: right;
            padding-left:0.2em;
        }
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
        /*显示图片*/
        #imageCard .imgShow,#cheatImg .imgShow,#phoneImg .imgShow{width:40px;height: 40px;float:left;margin-right:1em;}
        #imageCard .imgShowTd{padding-left: 1em;text-align:left}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        .align{width:110px;}
        #divFrom td{text-align: center;}
        #divFrom input{text-align: center;}
        #imageCard tr{height: 37px;}
        .paperBlockfree{cursor: pointer;}
        .paperBlockfree{overflow: hidden;}
        #divFrom .telRecord{text-align: left;font-weight: bold;}
        .picShow {
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 100%;
            width: 100%;
            top: 1em;
            left: 1em;
        }
        #contract_list{font-size: 13px;}
        #auditFinds{
            padding: 1em;
        }
        #auditFinds p{
            height:30px;
            line-height: 30px;
            text-align: left;
        }
        #auditFinds .icon-file-text-alt:before{
            color: #05C1BC;
            font-size: 22px;
        }
        #auditFinds p,#auditFinds p span{
            font-size: 15px;
            font-weight: 700;
        }
        .findsTable{
            width: 800px;
        }
        .findsTable thead tr th,.findsTable tbody tr td{
            height:30px;
            line-height: 30px;
            border: 1px solid #ccc;
            text-align: center;
        }
        #auditFinds .rules,#auditFinds .scoreCard,#auditFinds .blackList{
            margin-bottom: 20px;
        }
        #clerk_manager_table{
            border:0;

        }
        #clerk_manager_table td{
            background:white;
            width: 250px;
            height: 40px;
            border: 1px solid black;
        }

        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;text-align: center;}
        .tdTitle{text-align: right;width: 120px;font-weight:bold;}
        #container td input{background-color: #fff; border:none;text-align: left;
            width: 100%;
            height: 80%;
            text-align: left;
            margin: 0;
            border: 1px solid #ccc;
        }
        #imageCard {width:40px;height: 40px;float:left;margin-right:1em;}
        .imgShow{max-width: 100%;max-height: 100%;}
        #imageCard .imgShowTd{padding-left: 1em;}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        #showNewImg ul{text-align: left}
        #showNewImg ul li{display: inline-block;width:25%;border:1px solid #ddd;text-align: center;margin:.2em 0;}
        .imgDiv{width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto;}
        .imgContainer{display: inline-block;position: relative}

        .closeImg{
            position: absolute;
            top: -10px;
            right: -10px;
            width: 20px;
            height: 20px;
            z-index: 999;
            font-size: 20px;
            /* border: 1px solid; */
            border-radius: 50%;
            background: #000;
            color: #fff;
            line-height: 20px;
            cursor: pointer;
        }
        #divYesAndNO{
            width:200px;
            height:200px;
            border:1px solid #D0D0D0;
            /*background-color: #D0D0D0;*/
            position: fixed;
            right:200px;
            top:100px;
        }
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" id="merchantId " value="">
    <input type="hidden" id="fanQIZhaState " value="">
    <input type="hidden" id="3 ">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li style="width:250px;">
                    <label class="lf">商户名称:</label>
                    <label style="width: 100px">
                        <input type="text" id="search_merchantName">
                    </label>
                </li>
                <li style="width:250px;">
                    <label class="lf">申请人:</label>
                    <label style="width: 100px">
                        <input type="text" id="search_apply_name">
                    </label>
                </li>
                <li style="width:200px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                </li>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="sign_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <%--<th>商户编号</th>--%>
                        <th>名称</th>
                        <th>主营业务</th>
                        <th>门店地址</th>
                        <th>详细地址</th>
                        <th>申请人</th>
                        <th>审核状态</th>
                        <th>反欺诈专员</th>
                        <th>反欺诈状态</th>
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
<!-- 处理弹出窗 -->
<div id="delAntiFraudMerchant" style="display: none" >
    <div class="addCommon clearfix">
        <div>
            <div id="container" class="of-auto_H" style="padding:20px;">
                <div id="divFrom">
                    <div class="paddingBox xdproadd" style="width:1200px">
                        <div class="paperBlockfree" style="position: relative;margin-top:20px;">
                            <%--<div class="block_hd" style="float:left;" style="float:left;" onclick="shrink(this)">--%>
                                <%--<s class="ico icon-file-text-alt"></s><span class="bl_tit">说明</span>--%>
                            <%--</div>--%>
                            <%--<table class="tb_info" style="font-size:12px;">--%>
                                <%--<tbody>--%>
                                <%--<tr style="height: 200px;">--%>
                                    <%--<td style="background: #DEF0D8;width: 10%;">调查说明</td>--%>
                                    <%--<td colspan="3" style="text-align: left;" id="">--%>
                                        <%--<div>--%>

                                        <%--</div>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--</tbody>--%>
                            <%--</table>--%>
                            <div class="block_hd" style="float:left;" style="float:left;" onclick="shrink(this)">
                                <s class="ico icon-file-text-alt"></s><span class="bl_tit">添加反欺诈影像</span>
                            </div>
                            <script>
                                function setImagePreview1(me,tdId){
//                                            debugger
                                    if(tdId =='td_idcard_zhengmian'){
                                        if(($("#td_idcard_zhengmian").children()).length>1){
                                            layer.msg("身份证正面照只能传一张",{time:1000});
                                            return ;
                                        }
                                    }
                                    //td_idcard_fanmian
                                    if(tdId =='td_idcard_fanmian'){
                                        if(($("#td_idcard_fanmian").children()).length>1){
                                            layer.msg("身份证反面照只能传一张",{time:1000});
                                            return ;
                                        }
                                    }
                                    // layer.load(2);
                                    var uploadImgNum = $(me).parent().parent().parent().parent().attr("id");
                                    var uploadImgType = $(me).parent().parent().parent().find('input[name="type"]').val();
                                    var imgObjPreview=me.nextElementSibling;
                                    if(me.files && me.files[0]){
                                        var type=me.files[0].type;
                                        if(type.indexOf("image")==-1){
                                            // layer.closeAll();
                                            layer.msg("您上传的图片格式不正确，请重新选择!",{time:1000});
                                            return;
                                        }
                                        //火狐下，直接设img属性
                                        imgObjPreview.src = window.URL.createObjectURL(me.files[0]);
                                        var index =layer.load(2);
                                        /**********阿里云js sdk上传文件************/
                                        var val= me.value;
                                        var suffix = val.substr(val.indexOf("."));
                                        var obj=timestamp();  // 这里是生成文件名
                                        var storeAs = 'upload-file/'+"/"+obj+suffix;  //命名空间
                                        var file = me.files[0];
                                        Comm.ajaxUpload('accessToken/getToken','',function (res) {
                                            var result = res.data;
                                            var client = new OSS.Wrapper({
                                                accessKeyId: result.AccessKeyId,
                                                accessKeySecret: result.AccessKeySecret,
                                                stsToken: result.SecurityToken,
                                                secure: true,
                                                endpoint: 'https://oss-cn-beijing.aliyuncs.com',
                                                bucket: 'miaofuxianjindai-001'
                                            });
                                            client.multipartUpload(storeAs, file).then(function (result) {
                                                var res = result.res.requestUrls[0].split('?')[0];
                                                if(uploadImgType!='1'){
                                                    $("#"+uploadImgNum).append(uploadImgHtml);
                                                    var num = uploadImgNum.replace(/[^0-9]/ig,"");
                                                    $("#"+uploadImgNum).find("input[name=type]").val(num);
                                                }
                                                $(me).attr('disabled',true);
                                                //$(me).hide();
                                                //$(me).next("img").attr({onclick:"tt(this.src)"});
                                                $(me).parent().parent().parent().find("input[name=originalName]").val(file.name);
                                                $(me).parent().parent().prev().prev().prev().val(res);//图片阿里云地址
                                                //$("#img_idcard_zhengmian").val(res);
                                                console.log(res);
                                                $(me).parent().parent().parent().find("input[name=src]").val(res);
                                                $(me).parent().parent().parent().addClass("getFanQiZha");
                                                //$(me).parent().append("<span class='closeImg' onclick='closeDelete(this)'>×</span>");
                                                //layer.closeAll();
                                                layer.close(index);
                                                if($(me).after().src != '../resources/images/photoadd.png'){
                                                    $(me).prev().css("display","inline-block");
                                                }
                                                var td_img=
                                                        '<div>' +
                                                        '<input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">'+
                                                        '<input type="hidden" class="imgHidden"  name="src" value=""/>'+
                                                        '<input type="hidden" class="imgHidden" name="originalName" value="">'+
                                                        '<input type="hidden" class="imgHidden" name="isfront" value="0">'+
                                                        '<form action="" enctype="multipart/form-data">'+
                                                        '<div class="imagediv">'+
                                                        '<span style="display: none; color: red;" class="closeImg" onclick="closeDelete(this,\''+tdId+'\')">X</span>'+
                                                        '<input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,\''+tdId+'\')"/>'+
                                                        '<img class="addMaterial"  src="../resources/images/photoadd.png" />'+
                                                        '</div>'+
                                                        '</form>'+
                                                        '</div>' ;
                                                $("#"+tdId).append(td_img);
                                            }).catch(function (err) {
                                                //console.log(err);
                                                layer.closeAll();
                                            });
                                        })
                                    }
                                    return true;
                                }
                                function closeDelete(me,tdId){
                                    //删除该div
                                    $(me).parent().parent().parent().remove();
                                    console.log($("#"+tdId).children().length);
                                    //如果一直删到整个td下面没有元素了，就生成一个图片div
                                    if($("#"+tdId).children().length == 0){
                                        var td_img=
                                                '<div>' +
                                                '<input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">'+
                                                '<input type="hidden" class="imgHidden"  name="src" value=""/>'+
                                                '<input type="hidden" class="imgHidden" name="originalName" value="">'+
                                                '<input type="hidden" class="imgHidden" name="isfront" value="0">'+
                                                '<form action="" enctype="multipart/form-data">'+
                                                '<div class="imagediv">'+
                                                '<span style="display: none; color: red;" class="closeImg" onclick="closeDelete(this,\''+tdId+'\')">X</span>'+
                                                '<input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,\''+tdId+'\')"/>'+
                                                '<img class="addMaterial"  src="../resources/images/photoadd.png" />'+
                                                '</div>'+
                                                '</form>'+
                                                '</div>' ;
                                        $("#"+tdId).append(td_img);
                                    }
                                }
                            </script>
                            <table class="tb_info" style="font-size:12px;">
                                <tbody>
                                <tr style="height: 200px;">
                                    <td style="background: #DEF0D8;width: 10%;">法人身份证正面</td>
                                    <td colspan="3" style="text-align: left;" id="td_idcard_zhengmian">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden"  name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span id="xxxx" style="display: none;" class="closeImg" onclick="closeDelete(this,'td_idcard_zhengmian')">X</span>
                                                    <input type="file" name="pic" class="picShow" onchange="setImagePreview1(this,'td_idcard_zhengmian')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <%--身份证反面照片--%>
                                    <td style="background: #DEF0D8;width: 10%">法人身份证反面</td>
                                    <td colspan="3" style="text-align: left" id="td_idcard_fanmian">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden" name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_idcard_fanmian')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_idcard_fanmian')"/>
                                                    <img class="addMaterial"   src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <%--营业执照--%>
                                    <td style="background: #DEF0D8;width: 10%">营业执照</td>
                                    <td colspan="3" style="text-align: left;" id="td_yinyezhizhao">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden" name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_yinyezhizhao')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_yinyezhizhao')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <td style="background: #DEF0D8;width: 10%">门头logo照</td>
                                    <td colspan="3" style="text-align: left;" id="td_touxiang_logo">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden" name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_touxiang_logo')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_touxiang_logo')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <td style="background: #DEF0D8;width: 10%">室内照</td>
                                    <td colspan="3" style="text-align: left;" id="td_shinei">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden" name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_shinei')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_shinei')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <td style="background: #DEF0D8;width: 10%">收银台照</td>
                                    <td colspan="3" style="text-align: left;" id="td_shouyintai">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden" name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_shouyintai')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_shouyintai')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <td style="background: #DEF0D8;width: 10%">街景照</td>
                                    <td colspan="3" style="text-align: left;" id="td_jiejing">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden"  name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_jiejing')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_jiejing')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 200px;">
                                    <td style="background: #DEF0D8;width: 10%">法人银行卡</td>
                                    <td colspan="3" style="text-align: left;" id="td_farenyinhangka">
                                        <div>
                                            <input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">
                                            <input type="hidden" class="imgHidden"  name="src" value=""/>
                                            <input type="hidden" class="imgHidden" name="originalName" value="">
                                            <input type="hidden" class="imgHidden" name="isfront" value="0">
                                            <form action="" enctype="multipart/form-data">
                                                <div class="imagediv">
                                                    <span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_farenyinhangka')">X</span>
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_farenyinhangka')"/>
                                                    <img class="addMaterial"  src="../resources/images/photoadd.png" />
                                                </div>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <%--<tr style="height: 200px;">--%>
                                    <%--<td style="background: #DEF0D8;width: 10%">收款委托书</td>--%>
                                    <%--<td colspan="3" style="text-align: left;" id="td_shoukuanweituoshu">--%>
                                        <%--<div>--%>
                                            <%--<input type="hidden" class="imgHidden" class="imgHidden" name="type" value="1">--%>
                                            <%--<input type="hidden" class="imgHidden"  name="src" value=""/>--%>
                                            <%--<input type="hidden" class="imgHidden" name="originalName" value="">--%>
                                            <%--<input type="hidden" class="imgHidden" name="isfront" value="0">--%>
                                            <%--<form action="" enctype="multipart/form-data">--%>
                                                <%--<div class="imagediv">--%>
                                                    <%--<span style="display: none;color: red;" class="closeImg" onclick="closeDelete(this,'td_shoukuanweituoshu')">X</span>--%>
                                                    <%--<input type="file"  name="pic" class="picShow" onchange="setImagePreview1(this,'td_shoukuanweituoshu')"/>--%>
                                                    <%--<img class="addMaterial"  src="../resources/images/photoadd.png" />--%>
                                                <%--</div>--%>
                                            <%--</form>--%>
                                        <%--</div>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <tr style="height: 100px;">
                                    <td style="background: #DEF0D8;">备注说明：</td>
                                    <td colspan="3" style="text-align: left;">
                                        <textarea style=" width:95%; height: 95px;" id="fanQiZhaBeiiZhu"></textarea>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="paperBlockfree" style="position: relative;margin-top:20px; text-align: left;">
                            <%--<button type="button" class="btn btn-primary queryBtn" style="width: 29%; display: inline; height: 40px;" id="quxiao">取消</button>--%>
                            <%--<button type="button" class="btn btn-primary queryBtn" style="width: 29%; display: inline; height: 40px;" id="queding">确定</button>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

