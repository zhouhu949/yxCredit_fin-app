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
    <title>征信报告</title>
    <%@include file="../common/taglibs.jsp" %>
    <%@include file="../common/importDate.jsp" %>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <%--<script src="${ctx}/resources/js/customerManage/tongdunReport.js${version}"></script>--%>
    <style>
        .tableList {
            text-align: center;
        }

        .tableList th {
            text-align: center;
            border: 1px solid #d0d5d8;
            background-color: #f1f4f6;
            height: 32px;
            font-size: 14px;
        }

        .tableList td {
            text-align: center;
            border: 1px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }

        .tablecusTomer td {
            text-align: center;
            border: 1px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }

        .tableListBorder td {
            text-align: center;
            border: 0px solid #d0d5d8;
            font-size: 13px;
            height: 25px;
        }
        #suspected_team table{width: 2600px;}
        #platform_detail table{width: 1500px;}
        #frequency_detail table{width: 1500px;}

    </style>
    <script>
        //页面初始化
        $(function () {
            var tondunReport=(${customerJson}).customerDeviceInfo.tongdun_json;
            var ANTIFRAUD=JSON.parse(tondunReport).data.ANTIFRAUD;
//            debugger
            var final_score=ANTIFRAUD.final_score;//风险分数
            var final_decision=ANTIFRAUD.final_decision;//决策结果 PASS通过  REVIEW  REJECT拒绝
            var risk_items=ANTIFRAUD.risk_items;//风险项目(数组)
            console.log(risk_items);
            $("#final_score").html(final_score);
            $("#final_score_detail").html(final_score+'分');
            $("#final_decision").html(final_decision);
            if(final_decision == 'REJECT'){
                $("#final_decision_detail").html('拒绝');
            }else if(final_decision == 'PASS'){
                $("#final_decision_detail").html('通过');
            }else if(final_decision == 'REVIEW'){
                $("#final_decision_detail").html('复查');
            }
            //判断风险项目数组是否有数据
            if(risk_items.length==0){
                return false;
            }
            //刷新检测详情内容risk_items  reportDetail
            var html='';
            for(var i=0;i<risk_items.length;i++){
                if(risk_items[i].risk_detail && risk_items[i].risk_detail.length > 0){
                    var risk_item=risk_items[i];
                    //风险名单规则
                    if (risk_item.risk_detail[0].type=='black_list'){
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            if (j==0){
                                html ='<tr>'+
                                        '<td width="20%"  rowspan="'+risk_item.risk_detail.length+'">'+risk_item.risk_name+'</td>' +
                                        '<td width="20%" >'+risk_item.risk_detail[j].fraud_type_display_name+'</td>' +
                                        '<td width="20%" >'+risk_item.risk_detail[j].hit_type_display_name+'</td>' +
                                        '<td width="20%" >'+risk_item.risk_detail[j].description+'</td>' +
                                        '</tr>';
                            }else {
                                html +='<tr>'+
                                        '<td width="20%" >'+risk_item.risk_detail[j].fraud_type_display_name+'</td>' +
                                        '<td width="20%" >'+risk_item.risk_detail[j].hit_type_display_name+'</td>' +
                                        '<td width="20%" >'+risk_item.risk_detail[j].description+'</td>' +
                                        '</tr>';
                            }
                        }
                        $("#black_list_tbody").append(html);
                    }

                    //模糊证据库规则
                    if (risk_item.risk_detail[0].type=='fuzzy_black_list'){//模糊证据库规则
                        var risk_item=risk_items[i];
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];
                            var fuzzy_list_details=risk_item.risk_detail[j].fuzzy_list_details;
                            for(var k=0;k<fuzzy_list_details.length;k++){
                                var fuzzy_list_detail=fuzzy_list_details[k];
                                if (k==0){
                                    html ='<tr>'+
                                            '<td width="16%" rowspan="'+fuzzy_list_details.length+'">'+risk_item.risk_name+'</td>' +
                                            '<td width="16%" >'+risk_detail.fraud_type_display_name+'</td>' +
                                            '<td width="16%" >'+risk_detail.description+'</td>' +
//                                           '<td width="16%" >'+fuzzy_list_detail.fraud_type+'</td>' +
                                            '<td width="16%" >'+fuzzy_list_detail.fuzzy_id_number+'</td>' +
                                            '<td width="16%" >'+fuzzy_list_detail.fuzzy_name+'</td>' +
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
//                                            '<td width="16%" >'+risk_item.risk_name+'</td>' +
                                            '<td width="16%" >'+risk_detail.fraud_type_display_name+'</td>' +
                                            '<td width="16%" >'+risk_detail.description+'</td>' +
//                                           '<td width="16%" >'+fuzzy_list_detail.fraud_type+'</td>' +
                                            '<td width="16%" >'+fuzzy_list_detail.fuzzy_id_number+'</td>' +
                                            '<td width="16%" >'+fuzzy_list_detail.fuzzy_name+'</td>' +
                                            '</tr>';
                                }
                            }
                            $("#fuzzy_black_list_tbody").append(html);
                        }
//                        $("#fuzzy_black_list_tbody").append(html);
                    }


                    //风险群体规则
                    if (risk_item.risk_detail[0].type=='suspected_team'){//风险群体规则
                        var risk_item=risk_items[i];
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];
                            var suspect_team_detail_list=risk_detail.suspect_team_detail_list;
                            for(var k=0;k<suspect_team_detail_list.length;k++){
                                var suspect_team_detail=suspect_team_detail_list[k];
                                if (k==0){
                                    html ='<tr>'+
                                            '<td  rowspan="'+suspect_team_detail_list.length+'">'+risk_item.risk_name+'</td>' + //规则名称1
//                                            '<td  >'+risk_detail.type+'</td>' +   //规则类型2 原二级目录 暂时不用
                                            '<td  >'+suspect_team_detail.dim_type+'</td>' + //	匹配字段名称
                                            '<td  >'+suspect_team_detail.dim_value+'</td>' +//匹配字段值
                                            '<td  >'+suspect_team_detail.group_id +'</td>' +//疑似风险群体编号
                                            '<td  >'+suspect_team_detail.total_cnt +'</td>' +//疑似风险群体成员数
                                            '<td  >'+suspect_team_detail.black_cnt +'</td>' +//风险名单成员数
                                            '<td  >'+suspect_team_detail.grey_cnt +'</td>' +//关注名单成员数
                                            '<td  >'+suspect_team_detail.node_dist +'</td>' +//成员分布
                                            '<td  >'+suspect_team_detail.fraud_dist +'</td>' +//风险名单分布
                                            '<td  >'+suspect_team_detail.black_rat +'</td>' +//风险名单占比
                                            '<td  >'+suspect_team_detail.grey_rat +'</td>' +//关注名单占比
                                            '<td  >'+suspect_team_detail.degree +'</td>' +//一度关联节点个数
                                            '<td  >'+suspect_team_detail.total_cnt_two +'</td>' +//二度关联节点个数
                                            '<td  >'+suspect_team_detail.black_cnt_one+'</td>' +//一度风险名单个数
                                            '<td  >'+suspect_team_detail.fraud_dist_one +'</td>' +//一度风险名单分布
                                            '<td  >'+suspect_team_detail.black_cnt_two +'</td>' +//二度风险名单个数
                                            '<td  >'+suspect_team_detail.fraud_dist_two +'</td>' +//二度风险名单分布
                                            '<td  >'+suspect_team_detail.black_dst +'</td>' +//风险节点距离
                                            '<td  >'+suspect_team_detail.core_dst +'</td>' +//核心节点距离
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
//                                            '<td  >'+risk_item.risk_name+'</td>' + //规则名称1
//                                            '<td  >'+risk_detail.type+'</td>' +   //规则类型2
                                            '<td  >'+suspect_team_detail.dim_type+'</td>' + //	匹配字段名称
                                            '<td  >'+suspect_team_detail.dim_value+'</td>' +//匹配字段值
                                            '<td  >'+suspect_team_detail.group_id +'</td>' +//疑似风险群体编号
                                            '<td  >'+suspect_team_detail.total_cnt +'</td>' +//疑似风险群体成员数
                                            '<td  >'+suspect_team_detail.black_cnt +'</td>' +//风险名单成员数
                                            '<td  >'+suspect_team_detail.grey_cnt +'</td>' +//关注名单成员数
                                            '<td  >'+suspect_team_detail.node_dist +'</td>' +//成员分布
                                            '<td  >'+suspect_team_detail.fraud_dist +'</td>' +//风险名单分布
                                            '<td  >'+suspect_team_detail.black_rat +'</td>' +//风险名单占比
                                            '<td  >'+suspect_team_detail.grey_rat +'</td>' +//关注名单占比
                                            '<td  >'+suspect_team_detail.degree +'</td>' +//一度关联节点个数
                                            '<td  >'+suspect_team_detail.total_cnt_two +'</td>' +//二度关联节点个数
                                            '<td  >'+suspect_team_detail.black_cnt_one+'</td>' +//一度风险名单个数
                                            '<td  >'+suspect_team_detail.fraud_dist_one +'</td>' +//一度风险名单分布
                                            '<td  >'+suspect_team_detail.black_cnt_two +'</td>' +//二度风险名单个数
                                            '<td  >'+suspect_team_detail.fraud_dist_two +'</td>' +//二度风险名单分布
                                            '<td  >'+suspect_team_detail.black_dst +'</td>' +//风险节点距离
                                            '<td  >'+suspect_team_detail.core_dst +'</td>' +//核心节点距离
                                            '</tr>';
                                }
                            }
                            $("#suspected_team_tbody").append(html);
                        }
//                        $("#fuzzy_black_list_tbody").append(html);
                    }


                    //关注名单规则
                    if (risk_item.risk_detail[0].type=='grey_list'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                            var grey_list_details=risk_detail.grey_list_details;
                            for(var k=0;k<grey_list_details.length;k++){
                                var grey_list_detail=grey_list_details[k];//3
                                if (k==0){
                                    html ='<tr>'+
                                            '<td  rowspan="'+grey_list_details.length+'">'+risk_item.risk_name+'</td>' + //规则名称1
                                            '<td  rowspan="'+grey_list_details.length+'">'+risk_detail.hit_type_display_name+'</td>' +   //匹配类型
                                            '<td  rowspan="'+grey_list_details.length+'">'+risk_detail.fraud_type_display_name+'</td>' +   //风险类型显示名
                                            '<td  rowspan="'+grey_list_details.length+'">'+risk_detail.description+'</td>' +   //描述
                                            '<td  >'+grey_list_detail.evidence_time+'</td>' +   //时间戳
                                            '<td  >'+grey_list_detail.risk_level+'</td>' +   //风险等级
                                            '<td  >'+grey_list_detail.fraud_type_display_name+'</td>' +   //风险类型显示名
                                            '<td  >'+grey_list_detail.value+'</td>' +   //命中关注名单的属性值3
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
                                            '<td  >'+grey_list_detail.evidence_time+'</td>' +   //时间戳
                                            '<td  >'+grey_list_detail.risk_level+'</td>' +   //风险等级
                                            '<td  >'+grey_list_detail.fraud_type_display_name+'</td>' +   //风险类型显示名
                                            '<td  >'+grey_list_detail.value+'</td>' +   //命中关注名单的属性值3
                                            '</tr>';
                                }
                            }
                            $("#grey_list_tbody").append(html);
                        }
                    }//关注名单规则

                    //频度规则
                    if (risk_item.risk_detail[0].type=='frequency_detail'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                            var frequency_detail_list=risk_detail.frequency_detail_list;
                            for(var k=0;k<frequency_detail_list.length;k++){
                                var frequency_detail=frequency_detail_list[k];//3
                                if (k==0){
                                    html ='<tr>'+
                                            '<td  rowspan="'+frequency_detail_list.length+'">'+risk_item.risk_name+'</td>' + //规则名称1
//                                            '<td  >'+risk_detail.type+'</td>' +   //匹配类型
                                            '<td  >'+dealWithUndefined(frequency_detail.data)+'</td>' +   //关联数据列表
                                            '<td  >'+frequency_detail.detail+'</td>' +   //规则命中基本详情

                                            '</tr>';
                                }else {
                                    html +='<tr>'+
//                                            '<td  >'+risk_item.risk_name+'</td>' + //规则名称1
//                                            '<td  >'+risk_detail.type+'</td>' +   //匹配类型
                                            '<td  >'+frequency_detail.data+'</td>' +   //关联数据列表
                                            '<td  >'+frequency_detail.detail+'</td>' +   //规则命中基本详情
                                            '</tr>';
                                }
                            }
                            $("#frequency_detail_tbody").append(html);
                        }
                    }


                    //跨事件频度规则
                    if (risk_item.risk_detail[0].type=='cross_frequency_detail'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                            var cross_frequency_detail_list=risk_detail.cross_frequency_detail_list;
                            for(var k=0;k<cross_frequency_detail_list.length;k++){
                                var cross_frequency_detail=cross_frequency_detail_list[k];//3
                                if (k==0){
                                    html ='<tr>'+
                                            '<td  >'+risk_item.risk_name+'</td>' + //规则名称1
//                                            '<td  >'+risk_detail.type+'</td>' +   //匹配类型
                                            '<td  >'+ dealWithUndefined(cross_frequency_detail.data) +'</td>' +//关联数据列表 sc>=60?("C"):("D");
                                            '<td  >'+cross_frequency_detail.detail+'</td>' +   //规则命中基本详情

                                            '</tr>';
                                }else {
                                    html +='<tr>'+
                                            '<td  >'+risk_item.risk_name+'</td>' + //规则名称1
//                                            '<td  >'+risk_detail.type+'</td>' +   //匹配类型
                                            '<td  >'+dealWithUndefined(cross_frequency_detail.data)+'</td>' +   //关联数据列表
                                            '<td  >'+cross_frequency_detail.detail+'</td>' +   //规则命中基本详情
                                            '</tr>';
                                }
                            }
                            $("#cross_frequency_detail_tbody").append(html);
                        }
                    }


                    //跨事件字段
                    if (risk_item.risk_detail[0].type=='cross_event_detail'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                            debugger
                            var cross_event_detail_list=risk_detail.cross_event_detail_list;
                            for(var k=0;k<cross_event_detail_list.length;k++){
                                var cross_event_detail=cross_event_detail_list[k];//3
                                if (k==0){
                                    html ='<tr>'+
                                            '<td rowspan="'+cross_event_detail_list.length+'" >'+risk_item.risk_name+'</td>' + //规则名称1
                                            '<td  >'+cross_event_detail.detail+'</td>' +   //规则命中基本详情
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
                                            '<td  >'+cross_event_detail.detail+'</td>' +   //规则命中基本详情
                                            '</tr>';
                                }
                            }
                            $("#cross_event_detail_tbody").append(html);
                        }
                    }
                    //信贷逾期统计
                    if (risk_item.risk_detail[0].type=='discredit_count'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                            debugger
                            var overdue_details=risk_detail.overdue_details;
                            for(var k=0;k<overdue_details.length;k++){
                                var overdue_detail=overdue_details[k];//3
                                if (k==0){
                                    html ='<tr>'+
                                            '<td rowspan="'+overdue_details.length+'" >'+risk_item.risk_name+'</td>' + //规则名称1
                                            '<td rowspan="'+overdue_details.length+'" >'+risk_detail.description+'</td>' +   //规则描述2
                                            '<td rowspan="'+overdue_details.length+'" >'+risk_detail.discredit_times+'</td>' +   //信贷逾期次数2
                                            '<td >'+dealWithUndefined(overdue_detail.overdue_amount_range)+'</td>' +   //逾期金额区间3
                                            '<td >'+overdue_detail.overdue_count+'</td>' +   //逾期笔数3
                                            '<td >'+overdue_detail.overdue_day_range+'</td>' +   //逾期时间区间3
                                            '<td >'+overdue_detail.overdue_time+'</td>' +   //逾期入库时间3
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
                                            '<td  >'+dealWithUndefined(overdue_detail.overdue_amount_range)+'</td>' +   //逾期金额区间3
                                            '<td  >'+overdue_detail.overdue_count+'</td>' +   //逾期笔数3
                                            '<td  >'+overdue_detail.overdue_day_range+'</td>' +   //逾期时间区间3
                                            '<td  >'+overdue_detail.overdue_time+'</td>' +   //逾期入库时间3
                                            '</tr>';
                                }
                            }
                            $("#discredit_count_tbody").append(html);
                        }
                    }
                    //自定义列表规则
                    if (risk_item.risk_detail[0].type=='custom_list'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                                if (j==0){
                                    html ='<tr>'+
                                            '<td rowspan="'+risk_item.risk_detail.length+'">'+risk_item.risk_name+'</td>' + //规则名称1
                                            '<td>'+risk_detail.description+'</td>' +   //规则描述2
                                            '<td>'+dealWithUndefined(risk_detail.high_risk_areas)+'</td>' +   //高风险区域2
                                            '<td>'+dealWithUndefined(risk_detail.hit_list_datas)+'</td>' +   //列表数据2
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
                                            '<td>'+risk_detail.description+'</td>' +   //规则描述2
                                            '<td>'+dealWithUndefined(risk_detail.high_risk_areas)+'</td>' +   //高风险区域2
                                            '<td>'+dealWithUndefined(risk_detail.hit_list_datas)+'</td>' +   //列表数据2
                                            '</tr>';
                                }

                        } $("#custom_list_tbody").append(html);
                    }
                    //多平台规则
                    if (risk_item.risk_detail[0].type=='platform_detail'){
                        var risk_item=risk_items[i];//1
                        for(var j=0;j<risk_item.risk_detail.length;j++){
                            var risk_detail = risk_item.risk_detail[j];//2
                            var platform_detail_dimension =risk_detail.platform_detail_dimension;
                            var platform_detail=risk_detail.platform_detail;
                            //加载platform_detail_dimension
                            for(var k=0;k<platform_detail_dimension.length;k++){
                                var one_platform_detail_dimension=platform_detail_dimension[k];//3
                                if (k==0){
                                    html ='<tr>'+
                                            '<td rowspan="'+platform_detail_dimension.length+'">'+risk_item.risk_name+'</td>' + //规则名称1
                                            '<td rowspan="'+platform_detail_dimension.length+'">'+risk_detail.description+'</td>' +   //规则描述2
                                            '<td rowspan="'+platform_detail_dimension.length+'">'+risk_detail.platform_count+'</td>' +   //关联多平台个数2
                                            '<td rowspan="'+platform_detail_dimension.length+'">'+formatDetail(risk_detail.platform_detail) +'</td>' +   //不分维度关联多平台2
                                            '<td>'+one_platform_detail_dimension.dimension+'</td>' +   //维度名称3
                                            '<td>'+one_platform_detail_dimension.count+'</td>' +   //个数3
                                            '<td>'+formatDetail(one_platform_detail_dimension.detail)+'</td>' +   //各维度关联多平台细则3 one_platform_detail_dimension.detail
                                            '</tr>';
                                }else {
                                    html +='<tr>'+
                                            '<td>'+one_platform_detail_dimension.dimension+'</td>' +   //维度名称3
                                            '<td>'+one_platform_detail_dimension.count+'</td>' +   //个数3
                                            '<td>'+formatDetail(one_platform_detail_dimension.detail)+'</td>' +   //各维度关联多平台细则3 one_platform_detail_dimension.detail
                                            '</tr>';
                                }
                            }
                        } $("#platform_detail_tbody").append(html);
                    }
                }

            }
        })
        //处理风险详情数据
       function formatUndefind(obj){
           if(Object.parse(obj).hasOwnProperty("data")){
                return data;
           }
       }
       function formatDetail(obj){
           var label='';
           for(var m=0;m<obj.length;m++){
               label+='<label>行业名称:<span style="color: green;">'+obj[m].industry_display_name+'</span>  ,  个数:<span style="color: green;">'+obj[m].count+'</span></label><br>';
           }
           return label;
       }
       function dealWithUndefined(obj){
        if(typeof(obj) == undefined || obj == '' || obj == null){
            return "";
        }else{
            return obj;
        }
       }
    </script>

</head>
<body>
<div style="width: 100%;height:400%;text-align: center">
    <div style="width: 1500px;margin:0 auto;border: 5px solid #eaeced;border-radius:10px 10px 10px 10px;">
        <!--box-shadow: 2px 4px 6px #f0f9ff-->
        <div style="width: 1000px;margin:0 auto;">
            <div style="height:50px;margin-top: 30px;font-size: 30px;font-weight: bolder">
                <%--数据是同盾报告--%>
                <span>征信报告</span>
                <%--<input type="hidden" value="${customerJson}" id="customerJson">--%>
            </div>
        </div>
        <br>
        <%--结果展示--%>
        <div>
            <%--检测结果--%>
            <div id="reportDetail">
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">检测结果</span>
                    </div>
                </div>
                <div>
                    <table width="100%" class="tablecusTomer" >
                        <tr>
                        <td width="10%">风险分数</td><td width="50%" id="final_score"></td><td width="40%" id="final_score_detail"></td>
                    </tr>
                        <tr>
                            <td width="10%">决策结果</td><td width="50%" id="final_decision"></td><td width="40%" id="final_decision_detail"></td>
                        </tr>

                    </table>
                </div>



                <%--多平台规则--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">多平台规则</span><%--platform_detail--%>
                    </div>
                </div>
                <div id="platform_detail" style="width:100%;overflow: scroll;">
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <td  >规则描述</td>        <!--description-->
                            <td  >关联多平台个数</td>  <!--platform_count-->
                            <td>不分维度关联多平台</td> <!--	platform_detail-->
                            <td  >维度名称</td> <!--	dimension-->
                            <td  >个数</td> <!--	count-->
                            <td  >各维度关联多平台细则</td> <!--	detail-->
                        </tr>
                        </thead>
                        <tbody id="platform_detail_tbody"></tbody>
                    </table>
                </div>

                <%--频度规则--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px"> 频度规则 </span><%--frequency_detail--%>
                    </div>
                </div>
                <div id="frequency_detail" style="width:100%;overflow: scroll;" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <%--<td  >规则类型2</td> <!--type-->--%>
                            <td  >关联数据列表</td>         <!--data-->
                            <td  >规则命中基本详情</td>         <!--detail-->
                        </tr>
                        </thead>
                        <tbody id="frequency_detail_tbody"></tbody>
                    </table>
                </div>



                <%--风险名单规则--%>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">风险名单规则</span>
                    </div>
                </div>
                <div id="reportDetailDiv">
                    <table width="100%" class="tablecusTomer" id="black_list">
                        <thead>
                            <tr>
                                <%--<td width="20%" >规则类型</td>--%>
                                <td width="20%" >规则名称</td>
                                <td width="20%" id="fraud_type_display_name">风险类型显示名</td>
                                <td width="20%" id="hit_type_display_name">匹配类型显示名</td>
                                <td width="20%" >描述</td>
                            </tr>
                        </thead>
                        <tbody id="black_list_tbody">

                        </tbody>
                    </table>
                </div>

                <%--模糊证据库规则--%>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">模糊证据库规则</span>
                    </div>
                </div>
                <div id="fuzzy_black_list" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td width="16%" >规则名称</td>     <!--risk_name-->
                            <td width="16%" >风险类型显示名</td> <!--fraud_type_display_name-->
                            <td width="16%" >描述</td>         <!--description-->
                            <%--<td width="20%" >模糊证据库细则2</td> <!--fuzzy_list_details-->--%>
                            <%--<td width="16%" >风险类型3</td> <!--风险类型fraud_type3-->--%>
                            <td width="16%" >模糊身份证</td> <!--模糊身份证fuzzy_id_number3-->
                            <td width="16%" >模糊姓名</td> <!--模糊姓名fuzzy_name-->
                        </tr>
                        </thead>
                        <tbody id="fuzzy_black_list_tbody">

                        </tbody>
                    </table>
                </div>

                <%--风险群体规则--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">风险群体规则</span>
                    </div>
                </div>
                <div id="suspected_team" style="width:100%;overflow: scroll;" >
                    <table  class="tablecusTomer" >
                        <thead>
                        <tr>
                            <td class="suspected_team_td">规则名称</td>     <!--risk_name-->
                            <%--<td class="suspected_team_td">规则类型2</td>     <!--type-->--%>
                            <%--<td class="suspected_team_td">风险群体风险详情细则2</td> <!--suspect_team_detail_list-->--%>
                            <td class="suspected_team_td">匹配字段名称</td><!--dim_type -->
                            <td class="suspected_team_td">匹配字段值</td><!-- dim_value -->
                            <td class="suspected_team_td">疑似风险群体编号</td><!--group_id -->
                            <td class="suspected_team_td">疑似风险群体成员数	</td><%--total_cnt--%>
                            <td class="suspected_team_td">风险名单成员数	</td><%--black_cnt	--%>
                            <td class="suspected_team_td">关注名单成员数	</td><%--grey_cnt	--%>
                            <td class="suspected_team_td">成员分布</td><%--node_dist	--%>
                            <td class="suspected_team_td">风险名单分布</td><%--fraud_dist	--%>
                            <td class="suspected_team_td">风险名单占比</td><%--black_rat	--%>
                            <td class="suspected_team_td">关注名单占比</td><%--grey_rat	--%>
                            <td class="suspected_team_td">一度关联节点个数</td><%--degree	--%>
                            <td class="suspected_team_td">二度关联节点个数</td><%--total_cnt_two	--%>
                            <td class="suspected_team_td">一度风险名单个数</td><%--black_cnt_one	--%>
                            <td class="suspected_team_td">一度风险名单分布</td><%--fraud_dist_one	--%>
                            <td class="suspected_team_td">二度风险名单个数</td><%--black_cnt_two	--%>
                            <td class="suspected_team_td">二度风险名单分布</td><%--fraud_dist_two	--%>
                            <td class="suspected_team_td">风险节点距离</td><%--black_dst	--%>
                            <td class="suspected_team_td">核心节点距离</td><%--core_dst	--%>
                        </tr>
                        </thead>
                        <tbody id="suspected_team_tbody">
                        </tbody>
                    </table>
                </div>



                <%--关注名单规则--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">关注名单规则</span><%--grey_list--%>
                    </div>
                </div>
                <div id="grey_list" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <td  >匹配类型</td> <!--hit_type_display_name-->
                            <td  >风险类型显示名</td>     <!--fraud_type_display_name-->
                            <td  >描述</td>         <!--description-->
                            <td  >证据时间戳形式</td>         <!--evidence_time-->
                            <td  >风险等级</td>         <!--risk_level-->
                            <td  >风险类型显示名</td>         <!--fraud_type_display_name-->
                            <td  >命中关注名单的属性值</td>         <!--value-->
                        </tr>
                        </thead>
                        <tbody id="grey_list_tbody">

                        </tbody>
                    </table>
                </div>




                <%--跨事件频度规则--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">跨事件频度规则</span><%--cross_frequency_detail--%>
                    </div>
                </div>
                <div id="cross_frequency_detail" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <%--<td  >规则类型2</td> <!--type-->--%>
                            <td  >关联数据列表</td>         <!--data-->
                            <td  >规则命中基本详情</td>         <!--detail-->
                        </tr>
                        </thead>
                        <tbody id="cross_frequency_detail_tbody"></tbody>
                    </table>
                </div>

                <%--跨事件字段--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">跨事件字段</span><%--cross_event_detail--%>
                    </div>
                </div>
                <div id="cross_frequency_detail" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <%--<td  >规则类型2</td> <!--type-->--%>
                            <td  >规则命中基本详情</td>         <!--detail-->
                        </tr>
                        </thead>
                        <tbody id="cross_event_detail_tbody"></tbody>
                    </table>
                </div>


                <%--信贷逾期统计 discredit_count--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">信贷逾期统计</span><%--discredit_count--%>
                    </div>
                </div>
                <div id="discredit_count" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <td  >规则描述</td> <!--description-->
                            <td  >信贷逾期次数</td> <!--discredit_times-->
                            <%--<td  >信贷逾期统计细则</td>         <!--overdue_details-->--%>
                            <td  >逾期金额区间</td>         <!--overdue_amount_range-->
                            <td  >逾期笔数</td>         <!--overdue_count-->
                            <td  >逾期时间区间</td>         <!--overdue_day_range-->
                            <td  >逾期入库时间</td>         <!--overdue_time-->

                        </tr>
                        </thead>
                        <tbody id="discredit_count_tbody"></tbody>
                    </table>
                </div>


                <%--自定义列表规则--%>
                <br>
                <br>
                <br>
                <div style="height:40px;background-color: #2778C2;border-radius:5px 5px 0px 0px">
                    <div style="padding-top: 10px;">
                        <span style="font-size: 15px;font-weight: 600;color: #FFFFFF;margin-bottom: 1px">自定义列表规则</span><%--custom_list--%>
                    </div>
                </div>
                <div id="custom_list" >
                    <table width="100%" class="tablecusTomer">
                        <thead>
                        <tr>
                            <td  >规则名称</td>     <!--risk_name-->
                            <td  >规则描述</td> <!--description-->
                            <td  >高风险区域</td> <!--high_risk_areas-->
                            <td  >列表数据</td> <!--hit_list_datas-->
                        </tr>
                        </thead>
                        <tbody id="custom_list_tbody"></tbody>
                    </table>
                </div>






            </div>
        </div>
    </div>
</div>
</body>

</html>
