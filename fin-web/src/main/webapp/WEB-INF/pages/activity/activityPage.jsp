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
        .addMaterial{
            width: 100px;
            height: 100px;
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

        .addBanner tr{
           height: 40px;
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
                        <table  class="addBanner">
                            <tr>
                                <td style="text-align: right">
                                    <label >Banner标题</label>
                                </td>
                                <td colspan="3">
                                    <label  style="width:100%;padding: 0;margin-left: 20px;">
                                        <input  id="activity_titled" type="text" class="text_add"style="width:100%;height:28px;padding: 0;margin: 0px;"/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: right">
                                    <label >图片位置</label>
                                </td>
                                <td width="30%">
                                    <label  style="width:100%;padding: 0;margin-left: 15px;">
                                        <select id="activity_img_addr" style="width:100%;height: 28px;margin-left:3px;">
                                            <option value="-1">请选择</option>
                                            <option value="1">轮播图</option>
                                        </select>
                                    </label>
                                </td>
                                <td  style="text-align: right;padding-left: 50px" >
                                    <label >显示优先级 </label>
                                </td>
                                <td >
                                    <label style="width:100%;padding: 0;margin-left: 15px;">
                                        <select id="priority" style="width:100%;height: 28px;">
                                            <option value="-1">请选择</option>
                                            <option value="1">位置1</option>
                                            <option value="2">位置2</option>
                                            <option value="3">位置3</option>
                                            <option value="4">位置4</option>
                                            <option value="4">位置5</option>
                                        </select>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td  style="text-align: right">
                                    <label >Banner状态</label>
                                </td>
                                <td>
                                    <label style="width:100%;padding: 0;margin-left: 15px;">
                                        <select id="activity_stated" style="width:100%;height: 28px;padding:0;margin-left: 2px">
                                            <option value="-1">请选择</option>
                                            <option value="1">已上架</option>
                                            <option value="2">未上架</option>
                                        </select>
                                    </label>
                                </td>
                                <td  style="text-align: right">
                                    <label >Banner描述</label>
                                </td>
                                <td>
                                    <label style="width:100%;padding: 0;margin-left: 5px;">
                                        <input id="activity_content"  type="text" style="width:100%;height: 28px;padding:0;">
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td  style="text-align: right">
                                    <label >显示期限</label>
                                </td>
                                <td colspan="3">
                                    <label style="width:100%;padding: 0;margin: 0">
                                        <input readonly="true" placeholder="开始"  style="width:35%;margin-left: 0px;" id="beginTimed" type="text"/>
                                        <span class="date-icon"><i class="icon-calendar"></i></span>    <span style="margin-left: 20px;margin-right: 30px">至</span>
                                        <input readonly="true" placeholder="结束" style="width: 35%; margin-left: 0px;" id="endTimed" type="text"/>
                                        <span class="date-icon"><i class="icon-calendar"></i></span>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td  style="text-align: right">
                                    <label >Banner链接</label>
                                </td>
                                <td colspan="3">
                                    <label style="width:100%;margin: 0%;padding: 0;">
                                        <input id="activity_url" type="text" class="text_add" style="width: 100%;height:28px;padding: 0;margin-left: 20px;"/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td  style="text-align: right">
                                    <label class="label_name label_title">图片</label>
                                </td>
                                <td colspan="3">
                                    <form id="activityImgForm" method="post" enctype="multipart/form-data">
                                        <div id="localImag" style="margin-left: 20px"><img id="preview"  src="" style="display: block; width: 162px !important; height: 110px;"></div>
                                        <label id="prompt" style="color: red; text-align: left;width: 100%;margin-left: 4%">*图片大小必须为：750像素*380像素</label>
                                        <input type="file" name="file" id="file" style="width:64px; height:35px; margin-left: 20px" onchange="javascript:setImagePreview1();" >
                                    </form>
                                </td>
                            </tr>
                        </table>

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
                        <th>Banner标题</th>
                        <th>图片位置</th>
                        <th>Banner描述</th>
                        <th>Banner链接</th>
                        <th>显示期限</th>
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


   /* function closeDelete(me){
        $('.addMaterial').attr('src','../resources/images/photoadd.png');
    }*/


</script>
</body>
</html>