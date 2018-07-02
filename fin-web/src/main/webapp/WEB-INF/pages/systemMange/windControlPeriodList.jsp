<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@include file ="../common/taglibs.jsp"%>
        <%@ include file="../common/importDate.jsp"%>
        <script src="${ctx}/resources/js/systemMange/windControlPeriodList.js${version}"></script>
        <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
        <script type="text/javascript" src="${ctx}/resources/assets/js/jquery.form.min.js"></script>
        <title>白名单信息</title>
    </head>
    <body>
        <div class="page-content">
            <div class="Manager_style">
                <div class="">
                    <table  id="dictList" style="cursor:pointer;" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>产品名称</th>
                            <th>产品期限（天）</th>
                            <th>详情</th>
                            <th>创建时间</th>
                            <th>操作</th>
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
<!--编辑页面-->
<div id="edit_dict" style="display: none">
    <div class="commonManager ">
        <div class="addCommon">
            <ul class="clearfix">
                <li>
                    <label class="label_name">产品名称</label>
                    <label>
                        <input name="dictName"  type="text" value="" readonly/>
                    </label>
                </li>
                <li>
                    <label class="label_name">产品期限(天)</label>
                    <label>
                        <input name="dictValue"  type="text" value="" />
                    </label>
                </li>
            </ul>
            <div class="Remark" style="padding-top: 20px;">
                <label class="label_name">备注</label>
                <label>
                    <textarea name="dictRemark" cols="" rows="" style="width: 456px; height: 100px; padding: 5px;"></textarea>
                </label>
            </div>
        </div>
    </div>
</div>

