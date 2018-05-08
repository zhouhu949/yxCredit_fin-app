<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/activity/activity.js${version}"></script>
    <title>活动列表</title>
    <style>
        .laydate_body .laydate_y {margin-right: 0;}
        .line-cut{float: left;position: relative;top: 6px;left: -5px;}
        .onlyMe{font-size: 13px;}
        .onlyMe input{margin:0;text-align: center;}
        .tdTitle{text-align: right!important;width: 120px;font-weight:bold;}
        #container td input{background-color: #fff!important; border:none!important;text-align: center!important;}
        #imageCard {width:40px;height: 40px;float:left;margin-right:1em;}
        .imgShow{max-width: 100%;max-height: 100%;}
        #imageCard .imgShowTd{padding-left: 1em;}
        #BigImg{ width: 200px;height: 200px;position: absolute;top:-166px;left: 175px;display: none;z-index: 9999;}
        #showNewImg ul{text-align: left}
        #showNewImg ul li{display: inline-block;width:25%;border:1px solid #ddd;text-align: center;margin:.2em 0;}
        .imgDiv{width:120px;height:160px;border:1px solid #ddd;padding:.2em;margin:.2em auto;}

        .imagediv{position: relative}
        img.addMaterial{
            width: 100px;
            height: 100px;
            position: absolute;
            top: 0;
            left: 0;
            z-index: 10;
            background: #fff;
        }
        .imagediv .picShow{
            display: inline-block;
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
<div class="page-content">
    <input type="hidden" name="empId" value="${userId}"/>
    <div class="commonManager">
        <div class="Manager_style add_user_info search_style">
            <ul class="search_content clearfix">
                <li><label class="lf">活动标题</label>
                    <label>
                        <input name="activity_title" id="activity_title" type="text" class="text_add"/>
                    </label>
                </li>
                <li style="width:190px;"><label class="lf">活动状态 &nbsp;&nbsp;</label>
                    <label>
                        <select id="activity_state" style="min-width:90px;height: 28px;">
                            <option value="">请选择</option>
                            <option value="1">已上架</option>
                            <option value="2">未上架</option>
                        </select>
                    </label>
                </li>
                <li><label class="lf">创建时间</label>
                    <label>
                        <input readonly="true" placeholder="开始" class="eg-date" id="beginTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li><span class="line-cut">--</span>
                <li style="width:200px;">
                    <label>
                        <input readonly="true" placeholder="结束" class="eg-date" id="endTime" type="text"/>
                        <span class="date-icon"><i class="icon-calendar"></i></span>
                    </label>
                </li>
                <li style="width:300px;">
                    <button id="btn_search" type="button" class="btn btn-primary queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="btn btn-primary queryBtn">查询重置</button>
                    <button id="btn_add" type="button" class="btn btn-primary queryBtn">添加活动</button>
                </li>
            </ul>
            <div id="Add_procedure_style" style="display: none" >
                <div class="addCommon clearfix">
                    <div>
                        <input  id="activity_id" type="hidden" />
                        <input  id="activity_img_id" type="hidden" />
                        <input  id="activity_img_fileName" type="hidden" />
                        <ul>
                            <li>
                                <label class="lf">活动标题</label>
                                <label>
                                    <input  id="activity_titled" type="text" class="text_add"/>
                                </label>
                            </li>
                            <li>
                                <label class="lf">活动链接</label>
                                <label>
                                    <input id="activity_url" type="text" class="text_add"/>
                                </label>
                            </li>
                            <li><label class="lf">活动状态</label>
                                <label>
                                    <select id="activity_stated" style="min-width:60px;height: 28px;">
                                        <option value="">请选择</option>
                                        <option value="1">已上架</option>
                                        <option value="2">未上架</option>
                                    </select>
                                </label>
                            </li>
                            <li>
                                <label class="lf">活动描述</label>
                                <label>
                                    <input id="activity_content"  type="text">
                                </label>
                            </li>
                            <li style="width: auto">
                                <label class="lf">活动期限</label>
                                <label class="lf">
                                    <input readonly="true" placeholder="开始"  style="width: 162px; margin-left: 20px;" id="beginTimed" type="text"/>
                                    <span class="date-icon"><i class="icon-calendar"></i></span>    <span style="margin-left: 30px;">--</span>
                                    <input readonly="true" placeholder="结束" style="width: 162px; margin-left: 40px;" id="endTimed" type="text"/>
                                    <span class="date-icon"><i class="icon-calendar"></i></span>
                                </label>
                            </li>
                            <li><label class="lf">图片位置</label>
                                <label>
                                    <select id="activity_img_addr" style="min-width:60px;height: 28px;">
                                        <option value="">请选择</option>
                                    </select>
                                </label>
                            </li>
                            <li><label class="lf">平台类型</label>
                                <label>
                                    <select id="platformType" style="min-width:60px;height: 28px;">
                                    </select>
                                </label>
                            </li>
                            <li>
                                <label class="lf">活动图片</label>
                                <label readonly=“readonly”">
                                    <div class="paperBlockfree" style="overflow: hidden;">
                                        <div style="height:100px;margin:30px 0 20px 0;" id="houseotherpic">
                                            <div class="getFanQiZha businessPic">
                                                <input type="hidden" class="imgHidden"><%--删除的图片id及名称--%>
                                                <form action="" enctype="multipart/form-data">
                                                    <input type="hidden" id="id"/><%--活动id--%>
                                                    <input type="hidden" name="type" value="18">
                                                    <input type="hidden" name="businessType" value="2">
                                                    <div class="imagediv">
                                                        <input type="file"  name="pic" id="picShow" class="picShow"  onchange="setImagePreview(this,18)"/>
                                                        <img class="addMaterial" src="../resources/images/photoadd.png" />
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
        </div>

        <div class="Manager_style">
            <div class="customer_list">
                <table style="cursor:pointer;" id="customer_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th></th>
                        <th>活动标题</th>
                        <th>图片位置</th>
                        <th>活动描述</th>
                        <th>活动链接</th>
                        <th>活动时间</th>
                        <th>创建时间</th>
                        <th>活动状态</th>
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
</div>
</div>
<script>
    var beginTime = {
        elem: '#beginTimed',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
//        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#endTimed',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '1999-01-01 00:00:00',
//        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(beginTime);
    laydate(endTime);

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
</body>
</html>