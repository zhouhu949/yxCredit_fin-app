<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css${version}">
    <%@include file="../common/taglibs.jsp" %>
    <%@include file="../common/importDate.jsp" %>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <title>黄金管理</title>
    <style>
        .laydate_body .laydate_y {
            margin-right: 0;
        }

        .layui-layer-btn0 {
            width: 100% !important;
        }

        .layui-layer-shade {
            background-color: #f2f3eb !important;
        }

        .commonManager .addCommon li {
            width: 300px;
        }
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <div id="editDetail">
            <div class="addCommon clearfix">
                <ul class="clearfix">
                    <li>
                        <label class="lf">黄金品牌</label>
                        <label>
                            <input type="text" name="brand" id="brand" value="${brand}" maxlength="50">
                        </label>
                    </li>
                    <li>
                        <label class="lf">黄金单价</label>
                        <label>
                            <input type="text" name="price" id="price" value="${price}" maxlength="8">元/克
                        </label>
                    </li>
                    <li>
                        <label class="lf" style="width: 70px;text-align: left">克数限制</label>
                        <label>
                            <input type="text" name="numberRestriction" id="numberRestriction" value="${number_restriction}" maxlength="8">克
                        </label>
                    </li>
                    <li>
                        <label class="lf" >回收单价</label>
                        <label>
                            <input type="text" name="reclaim_price" id="reclaim_price" value="${reclaim_price}" maxlength="8">元/克
                        </label>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        layerIndex = layer.open({
            type: 1,
            title: "黄金配置",
            shadeClose: false, //点击遮罩关闭层
            area: ['650px', '180px'],
            closeBtn: 0,
            content: $('#editDetail').show(),
            btn: '保存',
            success: function () {
            },
            yes: function (index, layero) {
                var price = $("#price").val().trim();
                var brand = $("#brand").val().trim();
                var reclaim_price = $("#reclaim_price").val().trim();
                var numberRestriction = $("#numberRestriction").val().trim();
                if (brand.IsEmpty()) {
                    layer.msg("黄金品牌不能为空", {time: 2000});
                    return;
                }
                if (price.IsEmpty()) {
                    layer.msg("黄金单价不能为空", {time: 2000});
                    return;
                }
                if (!price.IsMoney()) {
                    layer.msg("黄金单价格式错误", {time: 2000});
                    return;
                }
                if (reclaim_price.IsEmpty()) {
                    layer.msg("回收单价不能为空", {time: 2000});
                    return;
                }
                if (!reclaim_price.IsMoney()) {
                    layer.msg("回收单价格式错误", {time: 2000});
                    return;
                }
                if (numberRestriction.IsEmpty()) {
                    layer.msg("克数限制不能为空", {time: 2000});
                    return;
                }
                if (!numberRestriction.IsMoney()) {
                    layer.msg("克数限制格式错误", {time: 2000});
                    return;
                }
                var param = {};
                param.price = price;
                param.brand = brand;
                param.reclaim_price = reclaim_price;
                param.number_restriction = numberRestriction;
                Comm.ajaxPost('goldConfig/updateConfig', JSON.stringify(param), function (result) {
                    debugger
                    layer.msg(result.msg, {time: 1000}, function () {
                        if (result.code == "0") {
                            //layer.closeAll();
                        }
                    });
                }, "application/json");
            }
        })

    })
</script>
</html>
