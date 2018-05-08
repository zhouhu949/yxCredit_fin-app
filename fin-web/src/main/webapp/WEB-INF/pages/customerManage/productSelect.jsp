<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/datepick/laydate.js"></script>
    <script src="${ctx}/resources/js/customerManage/productSelect.js${version}"></script>
    <link rel="stylesheet" href="${ctx}/resources/assets/datepick/need/laydate.css">
   <%-- <link rel="stylesheet" type="text/css" href="static/css/reset.css">--%>
    <style>
        #choosePro td,#choosePro select{
            font-size: 12px;
            color:#393939;
        }
        #choosePro select{
            border: 1px solid #ccc;
        }
        #btn_yes,#btn_no{
            background: #05c1bc;
            border-radius: 2px;
            border: 1px solid #04a8a4;
            height: 28px;
            line-height: 28px;
            color: #fff;
            width: 70px;
        }
    </style>
</head>
<body>
<div id="app">
    <form name="addform" id="addFrom">
        <input type="hidden" id='customerId' value="${customerId}" name="customerId"/>
    <table style="margin:auto;" id='choosePro'>
        <tbody>
        <tr>
            <input type="hidden" id="series" name="series">
            <input type="hidden" id="productType" name="productType">
            <input type="hidden" id="productDetail" name="productDetail">
            <td>产品系列：</td>
            <td>
                <select name="proTypeId" id="provcd" class="" style="width:180px;height: 29px;margin: 5px;" onchange="getProductType()">
                    <option value="">--请选择--</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>产品名称：</td>
            <td>
                <select name="proNameId" id="productName" class="" style="width:180px;height: 29px;margin: 5px;" onchange="getProductDetail()">
                <option value="">--请选择--</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>产品期数：</td>
            <td>
                <select name="proDetailId" id="proDetailId" class="" style="width:180px;margin: 5px;">
                    <option value="">--请选择--</option>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
    <div style="margin:auto;width: 200px;">
        <button class="" id="btn_yes" style="margin:15px 10px 10px 20px">确定</button>
        <button class="" id="btn_no">取消</button>
    </div>
    </form>
</div>
</body>
<script>
    $("#btn_no").click(function(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index)
    })

    $("#btn_yes").click(function () {
        //表单提交
        $("#series").val($("#provcd option:selected").html());
        $("#productType").val($("#productName option:selected").html());
        $("#productDetail").val($("#proDetailId option:selected").html());
        var customerId = $("#customerId").val();
        var data=$("#addFrom").serialize();
        //data = JSON.stringify(data);
        Comm.ajaxPost(
                'percust/addProduct',data,
                function (msg) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.$("#Res_list").dataTable().fnDraw(false);
                    parent.layer.close(index)
                },null,"","","",false
        )
    })
</script>
</html>

