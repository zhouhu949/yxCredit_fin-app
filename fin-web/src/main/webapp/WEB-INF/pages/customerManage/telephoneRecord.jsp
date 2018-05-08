<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/26
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>话单报告</title>
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <style>
        .tableList{
            text-align: center;
        }
        .tableList th {
            text-align: center;
            border:1px solid #d0d5d8;
            background-color: #f1f4f6;
            height: 32px;
            font-size: 14px;
        }
        .tableList td {
            text-align: center;
            border:1px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }
        .tablecusTomer td {
            text-align: center;
            border:1px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }
        .tableListBorder td {
            text-align:center;
            border:0px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }
    </style>
    <script>
        //页面初始化
        $(function () {
            debugger;
            //返回的所有报告
            var  json='${json}';
            if (!json || 'null' == json)
            {
                layer.msg("暂无数据",{time:2000});
                return;
            }
            json = eval("(" + json + ")");
            //通话记录数据
            var  jsonArray=eval("(" +  '${jsonArray}' + ")");
            //通讯总数
            var  phoneBookListSize= '${phoneBookListSize}';
            //通讯录匹配个数
            var  phoneMatchingSize= '${phoneMatchingSize}';
            $("#matching").html(phoneMatchingSize+'/'+phoneBookListSize+'(匹配个数/通讯总数)');
            if(json!=""){
                //用户申请表检测
                var application_check=json.reportData.application_check;
                //用户信息检测
                var user_info_check=json.reportData.user_info_check;
                //用户行为检测
                var behavior_check=json.reportData.behavior_check;
                //运营商数据整理
                var cell_behavior=json.reportData.cell_behavior[0];
                //联系人区域汇总
                var contact_region=json.reportData.contact_region;
                //联系人名单
                var collection_contact=json.reportData.collection_contact;
                //出行分析
                var  trip_info=json.reportData.trip_info;

                //用户信息检测
                var contactListHtml="";
                console.log(application_check);
                for (var i=0;i<application_check.length;i++){
                    debugger;
                    var app_point=application_check[i]
                    if(app_point.app_point=='user_name'){
                        $("#customerName").html(app_point.check_points.key_value);
                    }else if(app_point.app_point=='id_card'){
                        $("#customerCard").html(app_point.check_points.key_value);
                        $("#customerCardText").html( app_point.check_points.gender+' | '+app_point.check_points.age+' | '+app_point.check_points.province+'.'+app_point.check_points.city+'.'+app_point.check_points.region);
                    }else if(app_point.app_point=='cell_phone'){
                        $("#customerTel").html(app_point.check_points.website+' | '+app_point.check_points.reliability+' 注册时间：'+app_point.check_points.reg_time);
                        $("#customerTelText").html(app_point.check_points.key_value);
                    }else if(app_point.app_point=='home_addr'){

                    }else if(app_point.app_point=='home_phone'){

                    }else if(app_point.app_point=='contact'){
                        if(app_point.check_points.check_mobile=='没有该联系人电话的通话记录'){
                            contactListHtml+='<tr><td width="50%">'+app_point.check_points.relationship+' | '+app_point.check_points.contact_name+' | '+app_point.check_points.key_value+'</td><td width="50%"><span style="color:red;">'+app_point.check_points.check_mobile+'</span><br />'+app_point.check_points.check_xiaohao+'</tr>';
                        }else {
                            contactListHtml+='<tr><td width="50%">'+app_point.check_points.relationship+' | '+app_point.check_points.contact_name+' | '+app_point.check_points.key_value+'</td><td width="50%">'+app_point.check_points.check_mobile+'<br />'+app_point.check_points.check_xiaohao+'</tr>';
                        }
                    }
                    if(i==application_check.length-1){
                        $("#contactList").html(contactListHtml);
                    }
                }

                //用户信息检测
                if(user_info_check){
                    debugger;
                    //联系人信息
                    if(user_info_check.check_search_info){
                        $('#searched_org_cnt').html(user_info_check.check_search_info.searched_org_cnt);
                        for (var i=0;i<user_info_check.check_search_info.searched_org_type.length;i++){
                            if (i!=0){
                                $('#searched_org_type').append(' | ');
                            }
                            $('#searched_org_type').append(user_info_check.check_search_info.searched_org_type[i]);
                        }
                        for (var i=0;i<user_info_check.check_search_info.idcard_with_other_names.length;i++){
                            if (i!=0){
                                $('#idcard_with_other_names').append(' | ');
                            }
                            $('#idcard_with_other_names').append(user_info_check.check_search_info.idcard_with_other_names[i]);
                        }
                        for (var i=0;i<user_info_check.check_search_info.idcard_with_other_phones.length;i++){
                            if (i!=0){
                                $('#idcard_with_other_phones').append(' | ');
                            }
                            $('#idcard_with_other_phones').append(user_info_check.check_search_info.idcard_with_other_phones[i]);
                        }
                        for (var i=0;i<user_info_check.check_search_info.phone_with_other_names.length;i++){
                            if (i!=0){
                                $('#phone_with_other_names').append(' | ');
                            }
                            $('#phone_with_other_names').append(user_info_check.check_search_info.phone_with_other_names[i]);
                        }
                        for (var i=0;i<user_info_check.check_search_info.phone_with_other_idcards.length;i++){
                            if (i!=0){
                                $('#phone_with_other_idcards').append(' | ');
                            }
                            $('#phone_with_other_idcards').append(user_info_check.check_search_info.phone_with_other_idcards[i]);
                        }
                        $("#register_org_cnt").html(user_info_check.check_search_info.register_org_cnt);

                        for (var i=0;i<user_info_check.check_search_info.register_org_type.length;i++){
                            if (i!=0){
                                $('#register_org_type').append(' | ');
                            }
                            $('#register_org_type').append(user_info_check.check_search_info.register_org_type[i]);
                        }
                        for (var i=0;i<user_info_check.check_search_info.arised_open_web.length;i++){
                            if (i!=0){
                                $('#arised_open_web').append(' | ');
                            }
                            $('#arised_open_web').append(user_info_check.check_search_info.arised_open_web[i]);
                        }
                    }
                    //黑名单数据
                    if(user_info_check.check_black_info){
                        $('#phone_gray_score').html(user_info_check.check_black_info.phone_gray_score);
                        $('#contacts_class1_blacklist_cnt').html(user_info_check.check_black_info.contacts_class1_blacklist_cnt);
                        $('#contacts_class2_blacklist_cnt').html(user_info_check.check_black_info.contacts_class2_blacklist_cnt);
                        $('#contacts_class1_cnt').html(user_info_check.check_black_info.contacts_class1_cnt);
                        $('#contacts_router_cnt').html(user_info_check.check_black_info.contacts_router_cnt);
                        $('#contacts_router_ratio').html(user_info_check.check_black_info.contacts_router_ratio);
                    }
                }

                //用户行为检测
                if (behavior_check){
                    for (var i=0;i<behavior_check.length;i++){
                        $('#behavior_check').append('<tr><td>'+behavior_check[i].check_point_cn+'</td><td>'+behavior_check[i].result+'</td><td>'+behavior_check[i].evidence+'</td></tr>');
                    }
                }

                //运营商数据整理
                if (cell_behavior.behavior){
                    for(var i=0;i<cell_behavior.behavior.length;i++){
                        $('#cell_behavior').append('<tr><td>'+cell_behavior.behavior[i].cell_operator_zh+'</td><td>'+cell_behavior.behavior[i].cell_phone_num+'</td><td>'+cell_behavior.behavior[i].cell_loc+'</td><td>'+cell_behavior.behavior[i].cell_mth+'</td><td>'+cell_behavior.behavior[i].call_cnt+'</td><td>'+cell_behavior.behavior[i].call_out_cnt+'</td><td>'+cell_behavior.behavior[i].call_out_time.toString().substr(0,5)+'</td><td>'+cell_behavior.behavior[i].call_in_cnt+'</td><td>'+cell_behavior.behavior[i].call_in_time.toString().substr(0,5)+'</td><td>'+cell_behavior.behavior[i].sms_cnt+'</td><td>'+cell_behavior.behavior[i].total_amount+'</td></tr>');
                    }
                }

                //联系人区域汇总
                if(contact_region){
                    for(var i=0;i<contact_region.length;i++){
                        $('#contact_region').append('<tr><td width="8%">'+contact_region[i].region_loc+'</td><td width="8%">'+contact_region[i].region_uniq_num_cnt+'</td><td width="8%">'+contact_region[i].region_call_in_cnt+'</td><td width="8%">'+contact_region[i].region_call_out_cnt.toString().substr(0,5)+'</td><td width="8%">'+contact_region[i].region_call_in_time.toString().substr(0,5)+'</td><td width="8%">'+contact_region[i].region_call_out_time.toString().substr(0,5)+'</td><td width="8%">'+contact_region[i].region_avg_call_in_time.toString().substr(0,5)+'</td><td width="8%">'+contact_region[i].region_avg_call_out_time.toString().substr(0,5)+'</td><td width="9%">'+contact_region[i].region_call_in_cnt_pct.toString().substr(0,5)+'</td><td width="9%">'+contact_region[i].region_call_out_cnt_pct.toString().substr(0,5)+'</td><td width="9%">'+contact_region[i].region_call_in_time_pct.toString().substr(0,5)+'</td><td width="9%">'+contact_region[i].region_call_out_time_pct.toString().substr(0,5)+'</td></tr>');
                    }
                }

                //运营商数据分析
                if (jsonArray!=""){
                    var contactListHtml= '<tr> <th width="12%">号码</th><th width="12%">互联网标识</th><th width="10%">通讯录名称</th><th width="11%">需求类型</th> <th width="11%">归属地</th><th width="11%">联系次数</th> <th width="11%">联系时间(分)</th> <th width="11%">主叫次数</th> <th width="11%">被叫次数</th> </tr>';
                    for (var i=0;i<jsonArray.length;i++){
                        var zhongjie='中介';
                        var daikuan='贷款';
                        var cuishou='催收';
                        debugger
                        if (!jsonArray[i].contact_name){
                            jsonArray[i].contact_name='';
                        }
                        if (!jsonArray[i].phoneBookName){
                            jsonArray[i].phoneBookName='';
                        }
                        if(jsonArray[i].contact_name.toString().indexOf(zhongjie)!=-1||jsonArray[i].contact_name.toString().indexOf(daikuan)!=-1||jsonArray[i].contact_name.toString().indexOf(cuishou)!=-1){
                            contactListHtml+='<tr><td style="color: red">'+jsonArray[i].phone_num+'</td><td>'+jsonArray[i].contact_name+'</td><td>'+jsonArray[i].phoneBookName+'</td><td>'+jsonArray[i].needs_type+'</td><td>'+jsonArray[i].phone_num_loc+'</td><td>'+jsonArray[i].call_cnt+'</td><td>'+jsonArray[i].call_len.toString().substr(0,5)+'</td><td>'+jsonArray[i].call_out_cnt+'</td><td>'+jsonArray[i].call_in_cnt+'</td><tr>';
                        }else {
                            contactListHtml+='<tr><td>'+jsonArray[i].phone_num+'</td><td>'+jsonArray[i].contact_name+'</td><td>'+jsonArray[i].phoneBookName+'</td><td>'+jsonArray[i].needs_type+'</td><td>'+jsonArray[i].phone_num_loc+'</td><td>'+jsonArray[i].call_cnt+'</td><td>'+jsonArray[i].call_len.toString().substr(0,5)+'</td><td>'+jsonArray[i].call_out_cnt+'</td><td>'+jsonArray[i].call_in_cnt+'</td><tr>';
                        }
                    }
                    $("#contact_list").html(contactListHtml);
                }

                //出行分析
                if (trip_info){
                    for (var i=0;i<trip_info.length;i++){
                        $('#trip_info').append('<tr><td>'+trip_info[i].trip_type+'</td><td>'+trip_info[i].trip_start_time+'</td><td>'+trip_info[i].trip_end_time+'</td><td>'+trip_info[i].trip_leave+'</td><td>'+trip_info[i].trip_dest+'</td></tr>');
                    }
                }
            }
        })
    </script>

</head>
<body>
<div style="width: 100%;text-align: center">
    <div style="width: 1100px;margin:0 auto;border: 5px solid #eaeced;border-radius:10px 10px 10px 10px;"><!--box-shadow: 2px 4px 6px #f0f9ff-->
        <div style="width: 1000px;margin:0 auto;">
            <div style="height:50px;margin-top: 30px;font-size: 30px;font-weight: bolder">
            <span>话 单 报 告</span>
        </div>
            <%--用户申请表检测--%>
            <div>
            <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                <div style="padding-top: 10px;">
                    <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">用户申请表检测</span>
                </div>
            </div>
            <div>
                <table width="100%" class="tablecusTomer" >
                    <tr>
                        <td width="10%">姓名</td><td width="50%" id="customerName"></td><td width="40%" id="customerNameText"></td>
                    </tr>
                    <tr>
                        <td width="10%">身份证</td><td width="50%" id="customerCard"></td><td width="40%" id="customerCardText"></td>
                    </tr>
                    <tr>
                        <td width="10%">手机号</td><td width="50%" id="customerTel"></td><td width="40%" id="customerTelText">3</td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td width="10%">居住地址：</td><td width="50%">2</td><td width="40%">3</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td width="10%">联系人</td><td width="90%" colspan="2"><table id="contactList"  class="tableListBorder" width="100%"></table></td>
                    </tr>
                    <tr>
                        <td width="10%">通讯录匹配</td><td width="90%" colspan="2" id="matching"></td>
                    </tr>
                </table>
            </div>
        </div>
            <br>
            <%--用户信息检测--%>
            <div>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">用户信息检测</span>
                    </div>
                </div>
                <div>
                    <div style="text-align: left"><span >联系人信息</span></div>
                    <table width="100%" class="tableList">
                        <tr>
                            <td width="10%" rowspan="9">用户查询信息</td><td width="25%">查询过该用户的相关企业数量</td><td width="65%" id="searched_org_cnt"></td>
                        </tr>
                        <tr>
                            <td>查询过该用户的相关企业类型</td><td id="searched_org_type"></td>
                        </tr>
                        <tr>
                            <td>身份证组合过的其他姓名</td><td id="idcard_with_other_names"></td>
                        </tr>
                        <tr>
                            <td>身份证组合过其他电话</td><td id="idcard_with_other_phones"></td>
                        </tr>
                        <tr>
                            <td>电话号码组合过其他姓名</td><td id="phone_with_other_names"></td>
                        </tr>
                        <tr>
                            <td>电话号码组合过其他身份证</td><td id="phone_with_other_idcards"></td>
                        </tr>
                        <tr>
                            <td>电话号码注册过的相关企业数量</td><td id="register_org_cnt"></td>
                        </tr>
                        <tr>
                            <td>电话号码注册过的相关企业类型</td><td id="register_org_type"></td>
                        </tr>
                        <tr>
                            <td>电话号码出现过的公开网站</td><td id="arised_open_web"></td>
                        </tr>
                    </table>
                    <br>
                    <div style="text-align: left"><span >黑名单</span></div>
                    <table width="100%" class="tableList"  >
                        <tr>
                            <td width="10%" rowspan="9">黑名单信息</td><td width="25%">用户号码联系黑中介分数</td><td width="65%" id="phone_gray_score"></td>
                        </tr>
                        <tr>
                            <td>直接联系人中黑名单人数</td><td id="contacts_class1_blacklist_cnt"></td>
                        </tr>
                        <tr>
                            <td>间接联系人中黑名单人数</td><td id="contacts_class2_blacklist_cnt"></td>
                        </tr>
                        <tr>
                            <td>直接联系人人数</td><td id="contacts_class1_cnt"></td>
                        </tr>
                        <tr>
                            <td>引起间接黑名单人数</td><td id="contacts_router_cnt"></td>
                        </tr>
                        <tr>
                            <td>直接联系人中引起间接黑名单占比</td><td id="contacts_router_ratio"></td>
                        </tr>
                    </table>
                </div>
            </div>
            <br>
            <%--用户行为检测--%>
            <div>
            <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                <div style="padding-top: 10px;">
                    <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">用户行为检测</span>
                </div>
            </div>
            <div>
                <table width="100%" class="tableList" id="behavior_check" >
                    <tr>
                        <th width="20%">检查项</th><th width="20%">结果</th><th width="60%">依据</th>
                    </tr>
                </table>
            </div>
        </div>
            <br>
            <%--运营商消费数据--%>
            <div>
            <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                <div style="padding-top: 10px;">
                    <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">运营商消费数据</span>
                </div>
            </div>
            <div>
                <table width="100%" class="tableList" id="cell_behavior">
                    <tr>
                        <th width="9%">运营商</th>
                        <th width="9%">号码</th>
                        <th width="9%">归属地</th>
                        <th width="9%">月份</th>
                        <th width="9%">呼叫次数</th>
                        <th width="9%">主叫次数</th>
                        <th width="9%">主叫时间(分钟)</th>
                        <th width="9%">被叫次数</th>
                        <th width="9%">被叫时间(分钟)</th>
                        <th width="9%">短信数目</th>
                        <th width="9%">话费消费</th>
                    </tr>
                </table>
            </div>
        </div>
            <br>
            <%--联系人区域汇总--%>
            <div>
            <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                <div style="padding-top: 10px;">
                    <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">联系人区域汇总</span>
                </div>
            </div>
            <div>
                <table width="100%" class="tableList" id="contact_region">
                    <tr>
                        <th width="8%">地区名称</th>
                        <th width="8%">号码数量</th>
                        <th width="8%">电话呼入次数</th>
                        <th width="8%">电话呼出次数</th>
                        <th width="8%">电话呼入时间</th>
                        <th width="8%">电话呼出时间</th>
                        <th width="8%">平均电话呼入时间</th>
                        <th width="8%">平均电话呼出时间</th>
                        <th width="9%">电话呼入次数百分比</th>
                        <th width="9%">电话呼出次数百分比</th>
                        <th width="9%">电话呼入时间百分比</th>
                        <th width="9%">电话呼出时间百分比</th>
                    </tr>
                </table>
            </div>
        </div>
            <br><br>
            <%--运营商数据分析--%>
            <div>
            <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                <div style="padding-top: 10px;">
                    <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">运营商数据分析</span>
                </div>
            </div>
            <div>
                <table width="100%" id="contact_list"  class="tableList">
                    <tr>
                        <th width="12%">号码</th>
                        <th width="12%">互联网标识</th>
                        <th width="10%">通讯录名</th>
                        <th width="11%">需求类型</th>
                        <th width="11%">归属地</th>
                        <th width="11%">联系次数</th>
                        <th width="11%">联系时间(分)</th>
                        <th width="11%">主叫次数</th>
                        <th width="11%">被叫次数</th>
                    </tr>
                </table>
            </div>
        </div>
            <br>
            <%--&lt;%&ndash;联系人信息和地址信息&ndash;%&gt;--%>
            <%--<div>--%>
                <%--<div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">--%>
                    <%--<div style="padding-top: 10px;">--%>
                        <%--<span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">联系人信息和地址信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div>--%>
                    <%--<table width="100%">--%>
                        <%--<tr>--%>
                            <%--<td width="10%">姓名：</td><td width="50%">2</td><td width="40%">3</td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<br>--%>
            <%--出行数据分析--%>
            <div>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">出行数据分析</span>
                    </div>
                </div>
                <div>
                    <table width="100%"  class="tableList"id="trip_info">
                        <tr>
                            <th width="20%">时间段</th><th width="20%">出发时间</th><th width="20%">回程时间</th><th width="20%">出发地</th><th width="20%">目的地</th>
                        </tr>
                    </table>
                </div>
            </div>
            <br>
            <br>
        </div>
    </div>
</div>
</body>

</html>
