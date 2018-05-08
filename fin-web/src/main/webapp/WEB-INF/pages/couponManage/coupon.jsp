<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ include file = "../common/taglibs.jsp"%>
    <%@ include file = "../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css${version}" />
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <script src="${ctx}/resources/js/couponManage/coupon.js${version}"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.checkbox.js${version}"></script>
    <title>常见优惠券列表</title>
    <style type="text/css">
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe input{margin:0;vertical-align:middle;}
        #sign_list{font-size:13px;}
        .checkShow,.uploadContact{color:#05c1bc}
        .checkShow{margin-right: 8px;}

        .commonManager .addCommon li {
            line-height: 52px;
            width: 300px
        }

        .lf{
            margin-left: 10px;
        }
        .commonManager .addCommon li select {
            width: 160px;
            height: 28.89px;
            margin-left:10px;
        }
        #divFrom input{border:none;background-color: #fff!important;text-align: left;}
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
        .telRecord{height: 37px;  text-align: left;  padding-left: 35px!important;}
        #recordList1 td,#recordList2 td,#recordList3 td,#recordList4 td,#answerBody td,#answerBody1 td{border:none!important;border-right:1px solid #ccc}
        #recordList1 tr,#recordList2 tr,#recordList3 tr,#recordList4 tr,#answerBody tr,#answerBody1 tr{border:1px solid #ccc;}
        .answerTh th{text-align: center; font-weight: normal;border:none!important;}
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
        .picShow{
            position: absolute;
            z-index: 100;
            opacity: 0;
            filter: alpha(opacity=0);
            height: 30px;
            width: 70px;
            top:1px;
            left:99px;
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
        .search_style .search_content li {
            width: 250px;
            float: left;
            height: 32px;
            line-height: 32px;
        }
        .search_style .search_content li .btn_search {
            height: 32px;
            background: #2a8bcc !important;
            background-image: -webkit-gradient(linear, left 0, left 100%, from(#3b98d6),
            to(#197ec1)) !important;
            background-image: -moz-linear-gradient(top, #3b98d6 0, #197ec1 100%) !important;
            background-image: linear-gradient(to bottom, #3b98d6 0, #197ec1 100%) !important;
            background-repeat: repeat-x !important;
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff3b98d6',
            endColorstr='#ff197ec1', GradientType=0) !important;
            line-height: 32px;
            border: 0;
            width: 60px;
            text-align: center;
            color: #FFF;
            border-radius: 3px;
            -moz-border-radius: 3px;
            -webkit-border-radius: 3px;
        }
    </style>
    <script>
        //图片上传
        function setImagePreview(me,sign){
            $(me).parent().prev().prev().prev().val($('#activity_id').val());//活动id
            var id = $('#activity_id').val();
            var docObj=me;
            var imgObjPreview=me.nextElementSibling;
            if(docObj.files && docObj.files[0]){
                //火狐下，直接设img属性
                imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
                $('#closeImg').show();
                var html="";
                html+='<div><input type="hidden" class="imgHidden" value="'+id+'">';
                html+='<form action="" enctype="multipart/form-data">';
                html+='<input type="hidden" name="id" value=""/>';
                html+='<input type="hidden" name="type" value="12">';
                html+='<input type="hidden" name="businessType" value="2">';
                html+='<div class="imagediv">';
                html+='<input type="file"  name="pic" class="picShow" onchange="setImagePreview(this,12)"/>';
                html+='<img  class="addMaterial" src="../resources/images/photoadd.png" />';
                html+='</div>';
                //$(me).parent().parent().parent().parent().append(html);
                $(me).parent().parent().ajaxSubmit({
                    type: "POST",
                    url: "merUpload",
                    success: function (data) {
                        data = eval('('+data+')');
                        debugger
                        $(me).parent().parent().prev().val(data.data.activity_img_id);//图片id
                        $(me).parent().parent().parent().addClass("getFanQiZha");
                        debugger
                        $('#activity_img_id').val(data.data.activity_img_id);//图片id
                        $('#activity_img_fileName').val(data.data.activity_img_fileName);//图片url
                        layer.msg(data.msg,{time:1000});
                    },
                    error: function (XMLHttpRequest, textStatus, thrownError) {
                    }
                });
            }else{

            }
            return true;
        }
        function closeDelete(me){
            $('.addMaterial').attr('src','../resources/images/photoadd.png');
        }
    </script>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li style="width: 300px;"><label class="lf">合作平台名称</label>
                    <label>
                        <input name="coupon_name" type="text" class="text_add"/>
                    </label>
                </li>

                <li style="width:300px;margin-left: -40px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button id="btn_add" type="button" class="btn btn-primary queryBtn">添加</button>
                </li>
            </ul>
        </div>
        <div id="Add_procedure_style" style="display: none" >
            <input  id="activity_id" type="hidden" />
            <input  id="activity_img_id" type="hidden" />
            <input  id="activity_img_fileName" type="hidden" />
            <div class="addCommon clearfix">
                <div>
                    <input  id="contract_id" type="hidden" />
                    <input  id="coupon_id" type="hidden" />
                    <ul>
                        <li>
                            <label class="lf">名称</label>
                            <label>
                                <input id="coupon_name" type="text" class="text_add" />
                            </label>
                        </li>
                        <li>
                            <label class="lf">平台分类</label>
                            <label style="width: 200px;margin-right: 30px;">
                                <select id="platformType" style="min-width:60px;height: 28px;">
                                </select>
                            </label>
                        </li>
                        <li style="width: 300px;height: 75px;">
                            <label class="lf" style="margin-top: 20px;">链接地址</label>
                            <label style="width: 200px">
                                <textarea id="coupon_desc" style="width: 200px;height: 75px;margin-left: 10px;margin-top: 12px;"></textarea>
                            </label>
                        </li>

                        <li style="width: 300px;height: 75px;margin:20px auto;">
                            <label class="lf">活动图片</label>
                            <label>
                                <div class="paperBlockfree" style="overflow: hidden;">
                                    <div style="height:100px;margin-left: 10px;margin-top: 0px;" id="houseotherpic">
                                        <div class="getFanQiZha businessPic">
                                            <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                            <form action="" enctype="multipart/form-data">
                                                <input type="hidden" id="id"/><%--活动id--%>
                                                <input type="hidden" name="type" value="18">
                                                <input type="hidden" name="businessType" value="2">
                                                <div class="imagediv">
                                                    <input type="file"  name="pic" class="picShow" onchange="setImagePreview(this,18)" />
                                                    <img class="addMaterial" id="addMaterial" src="../resources/images/photoadd.png" style="margin-left: 15px;margin-top: 12px;" />
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </label>
                            <label>
                                <input id='closeImg' type="button" style='margin-left: 20px;display:none' value="X" onclick='closeDelete(this)' >
                            </label>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="Manager_style">
            <div class="order_list">
                <table style="cursor:pointer;" id="sign_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th></th>
                        <th>名称</th>
                        <th>图片地址</th>
                        <th>链接地址</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="userDiv" style="display: none;padding: 1em;">
            <div style="margin-bottom: 10px;">
                <input id="customer_name" placeholder="(客户姓名)" type="text" class="text_add"/>
                <input id="tel" placeholder="(手机号码)" type="text" class="text_add"/>
                <button id="user_btn_search"  type="button" class="btn btn-primary queryBtn" style="  margin-bottom: .5em;">查询</button>
                <button id="user_btn_search_reset"  type="button" class="btn btn-primary queryBtn" style="margin-bottom: .5em;">查询重置</button>
            </div>
            <table style="cursor:pointer;" id="user_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>
                        <input name="userCheckBox_All" id="userCheckBox_All" type="checkbox"  class="ace" isChecked="false" />
                        <span class="lbl" style="cursor:pointer;"></span>
                    </th>
                    <th>姓名</th>
                    <th>手机号</th>
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

