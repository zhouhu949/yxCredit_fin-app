<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/css/paperInfo.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/public.css${version}"/>
    <script src="${ctx}/resources/assets/js/jquery.form.min.js${version}"></script>
    <script src="${ctx}/resources/js/productManage/crmProductMain.js${version}"></script>
    <title>信贷产品管理</title>
    <style>
        select:{
            appearance: none;
        }
    </style>
   <%-- <script>
        function selectType(val){
            if(val==1){
                $("#danqi").css("display","table-row");
                $("#duoqi").css({"display":"none"});

            }else{
                $("#danqi").css("display","none");
                $("#duoqi").css("display","table-row");
            }
        }
        function selectday(val){
                $("#dandays").css({"display":"table-row"});
        }
    </script>--%>
</head>
<body>
<div class="ly_positionBar">
    <span class="position"> 信贷产品管理</span>
    <div class="fr positionsc" style="padding-right:10px;margin-right: 18em; ">
        <toolBar:permission  privilege='searchCrmProduct'>
            <input type="text" placeholder="产品名称" name="prdName" style="color: black;" id="prdName">
            <span class="btn_pad_s3 btn_color1" id="searchBtn" onclick="searchProduct()" style="margin-right:2em;"><s class="icon-search"></s>&nbsp;搜索</span>
        </toolBar:permission>
    </div>
</div>
<div class="ly_padding" style="padding: 15px;">
    <div class="ly_ctnWrap">
        <div class="prtype fl ">
            <span class="leftangle"></span>
            <table id="tb_prtype" class="tablecom fl" style="margin-right:25px;" >
                <thead>
                <tr>
                    <input type="hidden" id="showFirst">
                    <th>产品系列</th>
                    <th style="padding-left:45px;">
                       <span class="btn_pad_s3 btn_color1" onclick="showAddPrdTypeDiv(0);" style="position: relative;left:-33px;">
                            <s class="icon-plus"></s>
                           <!-- 新增系列 -->
                                新增系列
                       </span>
                    </th>
                </tr>
                </thead>
                <%-- <thead>
                     <tr style="height:49px;padding:0px; ">
                         <input type="hidden" id="showFirst">
                         <th style="padding:0px;">产品系列</th>
                         <th style="padding-left:45px; width: 200px;">
                             <span class="btn_pad_s3 btn_color1" onclick="showAddPrdTypeDiv();">
                                 <span class="icon-plus"></span>
                             </span>
                         </th>
                     </tr>
                 </thead>--%>
                <tbody id="prdTypeTmpl">
                <%-- <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('12','A008');">消费金融</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>
 --%>
                <%-- <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('14','A004');">工薪系列</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>

                 <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('18','A010');">宽贷</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>

                 <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('4028970a53f3ca540153f49633340004','A005');">车贷系列</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>

                 <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('402897a9510adba301510e61c0890000','A003');">助业系列</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>

                 <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('55b835ac-1ffe-11e7-b971-005056ae7cd6','A009');">U信现金</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>

                 <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('ff808081544b5e8f01544c68203f0083s2c','A006');">零用系列</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>

                 <tr>
                     <td><span style="cursor: pointer;" onclick="$_crmProductMain.clickPrdType('ff808081544b5e8f01544c68203f0083s8c','A007');">赎楼贷系列</span></td>
                     <td align="right" style="margin-right: 55px;">

                     </td>
                 </tr>--%>
                </tbody>
            </table>
            <table id="tb_prtype1" class="tablecom fr" >
                <thead>
                <tr>
                    <th style="border: 1px solid #dddddd" colspan="2">产品名称
                        <toolBar:permission privilege='addCrmProduct'>
                            <span class="btn_pad_s3 btn_color1" id="addProduct" style="margin-left: 50px;" onclick="showAddPrdListDiv();">
                          <s class="icon-plus"></s>
                              新增产品
                              <input type="hidden" id="searchProductId">
                            </span>
                        </toolBar:permission>
                    </th>
                    <!-- <th style="padding-left:45px;">
                    </th> -->
                </tr>
                </thead>
                <tbody id="prdQueueTmpl">

                <%--<tr>
                    <td>
                        <span style="cursor: pointer;width: 150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;" id="2c908a8257d6a59f0157da9c13fe00b7" onclick="$_crmProductMain.renderPrd({'accurateName' : '麒噶','ncpParentId' : '2c908a8257d6a59f0157da9c13fe00b7'});$_crmProductMain.loadThisPrdId(this);">麒噶</span>
                    </td>
                    <td align="right" style="margin-right: 55px;">
                        <a href="javascript:void(0);" onclick="$_crmProductMain.updataProStatus(1,'2c908a8257d6a59f0157da9c13fe00b7',2);">启用</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="cursor: pointer;width: 150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;" id="2c908a885a895a76015a8ce6fcc8017f" onclick="$_crmProductMain.renderPrd({'accurateName' : 'U信现金','ncpParentId' : '2c908a885a895a76015a8ce6fcc8017f'});$_crmProductMain.loadThisPrdId(this);">U信现金</span>
                    </td>
                    <td align="right" style="margin-right: 55px;">
                        <a href="javascript:void(0);" onclick="$_crmProductMain.updataProStatus(0,'2c908a885a895a76015a8ce6fcc8017f',2);">停用</a>
                        <a href="javascript:void(0);" onclick="editProduct()" &lt;%&ndash;onclick="$_crmProductMain.$_productModel.$f_searchProductNameInfo('2c908a885a895a76015a8ce6fcc8017f')"&ndash;%&gt;>配置</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="cursor: pointer;width: 150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;" id="ff808081544b5e8f01544c68203f0083s8c1" onclick="$_crmProductMain.renderPrd({'accurateName' : '赎楼贷','ncpParentId' : 'ff808081544b5e8f01544c68203f0083s8c1'});$_crmProductMain.loadThisPrdId(this);">赎楼贷</span>
                    </td>
                    <td align="right" style="margin-right: 55px;">
                        <a href="javascript:void(0);" onclick="$_crmProductMain.updataProStatus(0,'ff808081544b5e8f01544c68203f0083s8c1',2);">停用</a>
                        <a href="javascript:void(0);" onclick="$_crmProductMain.$_productModel.$f_searchProductNameInfo('ff808081544b5e8f01544c68203f0083s8c1')">配置</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="cursor: pointer;width: 150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;" id="ff80808156affad00156b0153af30052" onclick="$_crmProductMain.renderPrd({'accurateName' : '过桥贷','ncpParentId' : 'ff80808156affad00156b0153af30052'});$_crmProductMain.loadThisPrdId(this);">过桥贷</span>
                    </td>
                    <td align="right" style="margin-right: 55px;">
                        <a href="javascript:void(0);" onclick="$_crmProductMain.updataProStatus(0,'ff80808156affad00156b0153af30052',2);">停用</a>
                        <a href="javascript:void(0);" onclick="$_crmProductMain.$_productModel.$f_searchProductNameInfo('ff80808156affad00156b0153af30052')">配置</a>
                    </td>
                </tr>--%>
                </tbody>
            </table>
        </div>
        <div class="prlist" style="margin-left: 500px;" >
            <div class="lcprolist">
                <input type="hidden" id="productTypeId" >
                <input type="hidden" id="product_type" >
                <input type="hidden" id="pro_series_type" >
                <input type="hidden" id="productTypeNumber">
                <!-- 触发重新加载 -->
                <input type="hidden" id="reloadProduct" onclick="$_crmProductMain.loadPrdList();">
                <ul id="prdListTmpl">
                    <!-- 显示产品列表 -->
                    <%--<li class="fbon">

                        <div class="lcprxh">

                            产品序号：LCP0024</div>
                        <div class="lcprname">
                            <input type="hidden" id="productDetailId" value="2c908a8859055b080159059278d8000b" detailview="flase" product="ff80808156affad00156b0153af30052" isview="false">
                            <h3 style="width: 190px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden">过桥贷</h3>

                            <p>综合利率：</p>
                            <p><lable class="ransomDetailLable" title="
                            1天-3天，按天计息，利率为0.8%/天
                            4天-15天，按天计息，利率为0.2%/天
                            16天-30天，按天计息，利率为0.3%/天
                            31天-10000天，按天计息，利率为0.08%/天
                            ">
                                1天-3天，按天计息，利率为0.8%/天......</lable></p>
                        </div>
                        <div class="lcprdetail">
                            <table>
                                <tbody><tr>
                                    <td width="100">合同利率</td>
                                    <td width="180" align="right">1.1%</td>
                                </tr>
                                <tr>
                                    <td>期数</td>
                                    <td align="right">1期</td>
                                </tr>
                                <tr>
                                    <td>还款方式</td>
                                    <td align="right">
                                        到期一次还本付息
                                    </td>
                                </tr>
                                <tr>
                                    <td>到手额度</td>
                                    <td align="right">10000~10000000元</td>
                                </tr>
                                <tr>
                                    <td>停用/启用</td>
                                    <td align="right">
                                        <div class="switchs"></div>
                                    </td>
                                </tr>
                                </tbody></table>
                        </div>
                        <div class="lcprbtn">
                        </div>
                    </li>--%>
                </ul>
            </div>
        </div>
    </div>
</div>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/common/zTree/js/jquery.ztree.all-3.5.min.js"></script>--%>

<!-- 添加产品的div -->
<div class="paperBlock" id="addV3Prd" style="display: none;width: 600px;">
    <div class="block_hd" style="float:left;">
        <s class="ico icon-file-text-alt"></s><span class="bl_tit"> 信贷产品</span>
    </div>
    <form id="addV3Product"  enctype="multipart/form-data">
        <table class="tb_info" style="font-size:13px;">
            <tr>
                <td width="130px">产品序列：
                    <input type="hidden" id="v3ParentId" name="parent_id"/>
                </td>
                <td><input type='text' name='pro_number' id='v3Number'  readonly='readonly' style='border-style: solid; border-width: 0;background-color:#fff!important;'></td>
            </tr>
            <tr>
                <td width="130px">产品名称：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_name" id="v3Name" maxlength="20" datatype="/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5.]*$/" errormsg="不能输入特殊字符！"/></td>
            </tr>
            <tr>
                <td width="130px">产品额度：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_quota" id="v3Quota" maxlength="20"/></td>
            </tr>
            <tr style = "display:none" >
                <td width="130px">提额比例(%)：</td>
                <td><input type="text" class="inpText info-inpW1" name="quotaProportion" id="quotaProportion" maxlength="20"/></td>
            </tr>
            <tr style = "display:none">
                <td width="130px">额度上限：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_quota_limit" id="quotaLimit" maxlength="20"/></td>
            </tr>
            <tr>
                <td>产品类型：</td>
                <td>
                    <select class="inpText info-inpW1" name="product_type" id="productType" style="margin-left: 10px;"  >
                        <option value="-1">请选择</option>
                    </select>
                </td>
            </tr>
         <%--   <tr id="danqi" style="display: none;">
                <td width="130px">产品天数：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_quota_limit" id="singleDay" maxlength="20"/></td>
            </tr>
            <tr id="duoqi" style="display: none">
                <td>选择方式：</td>
                <td>
                    <input type="radio" name="type" id="month" checked="checked" value="0">自然月
                    <input type="radio"style="margin-left: 40px" name="type" id="days" value="1"  onclick="selectday(this.value)">自定义
                </td>
            </tr>
            <tr id="dandays" style="display: none">
                <td width="130px">产品天数：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_quota_limit" id="danDay" maxlength="20"/></td>
            </tr>--%>
            <tr>
                <td width="130px">产品描述：</td>
                <td><textarea  name="pro_describe" style="width:220px;height:100px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="v-top" width="130px">产品归属地：</td>
                <td>
                    <div class="flexCtn">
                        <div style="flex:1" class="flexCtn">
                            <input type="hidden" id="provinces" name="provinces">
                            <input type="hidden" id="cityHidden" name="city">
                            <input type="hidden" id="distric" name="distric">
                            <select onchange="onSelectChange(this,'city')"  name="provinces_id" id="province" style="line-height:20px;width:67%;padding-right:0px;">
                                <option value="">请选择</option>
                            </select>
                            <span style="line-height:26px;">&nbsp省&nbsp</span>
                        </div>
                        <div style="flex:1" class="flexCtn">
                            <select onchange="onSelectChange(this,'district')" name="city_id" id="city" style="line-height:20px;width:67%;margin-left:7px;padding-right:0px;">
                                <option value="">请选择</option>
                            </select>
                            <span style="line-height:26px;">&nbsp市&nbsp</span>
                        </div>
                        <div style="flex:1" class="flexCtn">
                            <select class="inpText inpMax" name="distric_id" id="district" style="line-height:20px;width:67%;padding-right:0px;">
                                <option value="">请选择</option>
                            </select>
                            <span style="line-height:26px;">&nbsp区&nbsp</span>
                        </div>
                    </div>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td height="101">--%>
                    <%--<div id="localImag" style="padding-left:3px;"><img id="preview" src="" width="272" height="180" style="display: block; width: 272px; height: 180px;"></div>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td align="left" style="padding-top:10px;"><input type="file" name="file" id="file" style="width:274px;" onchange="javascript:setImagePreview();" class="valid"></td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>
<!-- 添加产品的div end -->

<!-- 添加产品系列开始 start -->
<div class="paperBlock" id="addProductTypeDiv" style="display: none;width: 600px;">
    <div class="block_hd" style="float:left;">
        <s class="ico icon-file-text-alt"></s><span class="bl_tit"> 产品系列</span>
    </div>
    <form id="addProductType">
        <table class="tb_info" style="font-size:13px;">
            <tr>
                <td >产品系列序列：
                    <input type="hidden" name="parent_id" value="0" id="parent_id1">
                </td>
                <td><input type='text' class="inpText info-inpW1" name='pro_number' id="prdTypeNumber" datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>产品系列名称：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_name" id="prdTypeName" maxlength="20" datatype="/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5.]*$/" errormsg="不能输入特殊字符！"/></td>
            </tr>
            <tr>
                <td>平台类型：</td>
                <td>
                    <select class="inpText info-inpW1" name="pro_series_type" id="productType4" style="margin-left: 10px;"  >
                    </select>
                </td>
            </tr>
            <%--<tr>
                <td>查询函数路径</td>
                <td><input type='text' class="inpText info-inpW1" name='searchFunctionUrl' datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>查询函数</td>
                <td><input type='text' class="inpText info-inpW1" name='searchFunction' datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>添加|修改函数路径</td>
                <td><input type='text' class="inpText info-inpW1" name='saveFunctionUrl' datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>添加|修改函数</td>
                <td><input type='text' class="inpText info-inpW1" name='saveFunction' datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>参数列表</td>
                <td>
                    <textarea style='width:400px; height:55px;' name="formParam" datatype="*" nullmsg="请填写参数列表！"></textarea>
                </td>
            </tr>
            <tr>
                <td>html 页面</td>
                <td>
                    <textarea style='width:400px; height:55px;' name="formDiv" datatype="*" nullmsg="请填写HTML页面！"></textarea>
                </td>
            </tr>--%>
        </table>
    </form>
</div>
<%--产品系列修改--%>
<div class="paperBlock" id="addProductTypeDiv1" style="display: none;width: 600px;">
    <div class="block_hd" style="float:left;">
        <s class="ico icon-file-text-alt"></s><span class="bl_tit"> 产品系列</span>
    </div>
    <form id="addProductType1">
        <table class="tb_info" style="font-size:13px;">
            <tr>
                <td >产品系列序列：
                    <input type="hidden" name="id"  id="parent_id2">
                </td>
                <td><input type='text' class="inpText info-inpW1" name='pro_number' id="prdTypeNumber1" datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>产品系列名称：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_name" id="prdTypeName1" maxlength="20" datatype="/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5.]*$/" errormsg="不能输入特殊字符！"/></td>
            </tr>
            <tr>
                <td>平台类型：</td>
                <td>
                    <select class="inpText info-inpW1" name="pro_series_type" id="productType3" style="margin-left: 10px;"  >
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<%--产品名称修改--%>
<div class="paperBlock" id="addProductTypeDiv2" style="display: none;width: 600px;">
    <div class="block_hd" style="float:left;">
        <s class="ico icon-file-text-alt"></s><span class="bl_tit"> 产品系列</span>
    </div>
    <form id="addProductType2" enctype="multipart/form-data">
        <input type="hidden" name="id" id="prdTypeId">
        <input type="hidden" name="picName" id="picName1">
        <input type="hidden" name="pro_type" id="pro_type">
        <table class="tb_info" style="font-size:13px;">
            <tr>
                <td >产品序列：
                    <input type="hidden" name="id"  id="parent_id3">
                </td>
                <td><input type='text' class="inpText info-inpW1" name='pro_number' style="border:none;background: #fff!important;" id="prdTypeNumber2" datatype="*" nullmsg="请填写产品系列序列！"/></td>
            </tr>
            <tr>
                <td>产品名称：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_name" id="prdTypeName2" maxlength="20" datatype="/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5.]*$/" errormsg="不能输入特殊字符！"/></td>
            </tr>
            <tr>
                <td width="130px">产品额度：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_quota" id="v3Quota2" maxlength="20"/></td>
            </tr>
            <tr style = "display:none" >
                <td width="130px">提额比例(%)：</td>
                <td><input type="text" style = "display:none" class="inpText info-inpW1" name="quota_proportion" id="quotaProportion2" maxlength="20"/></td>
            </tr>
            <tr style = "display:none">
                <td width="130px">额度上限：</td>
                <td><input type="text" class="inpText info-inpW1" name="pro_quota_limit" id="quotaLimit2" maxlength="20"/></td>
            </tr>
            <tr>
                <td>产品类型：</td>
                <td>
                    <select class="inpText info-inpW1" name="product_type2" id="productType2" style="margin-left: 10px;"  >
                        <option value="-1">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td width="130px">产品描述：</td>
                <td><textarea  name="pro_describe" id="pro_describe" style="width:220px;height:100px;"></textarea>
                    </td>
            </tr>
            <tr>
                <td class="v-top" width="130px">产品归属地：</td>
                <td>
                    <div class="flexCtn">
                        <div style="flex:1" class="flexCtn">
                            <input type="hidden" id="provinces1" name="provinces">
                            <input type="hidden" id="cityHidden1" name="city">
                            <input type="hidden" id="distric1" name="distric">
                            <select onchange="onSelectChange1(this,'city')"  name="provinces_id" id="province1" style="line-height:20px;width:67%;padding-right:0px;">
                                <option value="">请选择</option>
                            </select>
                            <span style="line-height:26px;">&nbsp省&nbsp</span>
                        </div>
                        <div style="flex:1" class="flexCtn">
                            <select onchange="onSelectChange1(this,'district')" name="city_id" id="city1" style="line-height:20px;width:67%;margin-left:7px;padding-right:0px;">
                                <option value="">请选择</option>
                            </select>
                            <span style="line-height:26px;">&nbsp市&nbsp</span>
                        </div>
                        <div style="flex:1" class="flexCtn">
                            <select class="inpText inpMax" name="distric_id" id="district1" style="line-height:20px;width:67%;padding-right:0px;">
                                <option value="">请选择</option>
                            </select>
                            <span style="line-height:26px;">&nbsp区&nbsp</span>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<%--查看--%>
<div id="container" class="of-auto_H" style="padding:20px;display:none;">
    <input type="hidden" id="cpNumber1">
    <input type="hidden" id="cpName1">
    <form id="productForm"  method="post">
        <!-- 隐藏列 -->
        <!-- 产品类型，产品详细ID, -->
        <!-- 隐藏formId表单编号 -->
        <input type="hidden" id="flagHidden" name="flag">
        <input type="hidden" id="picName" name="picName">
        <input type="hidden" id="productId" name="productId">
        <input type="hidden" id="editId" name="id">
        <div id="divFrom">
            <div class="paddingBox xdproadd" style="width:920px">
                <div class="paperBlockfree">
                    <div class="block_hd" style="float:left;">
                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">自然参数</span>
                    </div>
                    <table class="tb_info " style="font-size:12px;">
                        <tbody>
                        <tr>
                            <td>产品序号：</td>
                            <td style="text-align: center;"><input type="text" name="pro_number" id="cpNumber"  style="border-style: solid; border-width: 0px; background-color: #fff!important; border:none;text-align: center;float:left;"></td>
                            <td style="text-align: center;">产品名称：</td>
                            <td><input type="text" style ='text-align: center' class="inpText info-inpW1" name="pro_name" id="cpName" maxlength="20" datatype="/^[\s|\S]*$/" nullmsg="请输入产品名称！" errormsg="请输入合法名称！" style="border:none;background-color: #fff!important;text-align: right;float:left;width:73%;"><span style="color:red;position: relative;top:6px;">*</span></td>
                            <td>还款方式：</td>
                            <td>
                                <select class="info-selW1" id="parentInfo" style="line-height:11px;float: left; padding-right: 6px;margin-left:24px;width:160px;" name="payment">
                                    <option style="text-align: center;" value="">请选择</option>
                                    <option style="text-align: center;" value="2">一次性还本付息</option>
                                </select>
                            </td>

                        </tr>
                        <tr style="display: none">
                            <td>综合利率：</td>
                            <td><input type="text" class="inpText info-inpW1" name="multipleRate" id="multipleRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left">
                        </tr>
                        <%--<tr style="display: none">--%>
                            <%--<td>资方编号：</td>--%>
                            <%--<td><input type="text" class="inpText info-inpW1" name="contractRate"  id="contractRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;"></td>--%>
                            <%--<td>综合利率：</td>--%>
                            <%--<td><input type="text" class="inpText info-inpW1" name="multipleRate"  id="multipleRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;"></td>--%>
                            <%--<td>还款方式：</td>--%>
                            <%--<td>--%>
                                <%--<select class="info-selW1" id="parentInfo" style="line-height:11px;float: left; padding-right: 6px;margin-left:24px;width:160px;" name="payment">--%>
                                    <%--<option value="">请选择</option>--%>
                                <%--</select>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                        <%--<tr style="display: none">--%>
                            <%--<td>贷后编号：</td>--%>
                            <%--<td><input type="text" class="inpText info-inpW1" name="actualLowerLimit" id="actualLowerLimit" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: right;float:left">--%>
                            <%--</td>--%>
                        <%--</tr>--%>

                        <tr>
                            <td>产品期限单位：</td>
                            <td style="text-align: center;">
                                <select id="product_term_unit" style='height: 28px; margin-left: -13px; width: 163px;' name="product_term_unit" onchange="productTermType(this.options[this.options.selectedIndex].value)">
                                    <option style="text-align: center;" value="">请选择</option>
                                    <option style="text-align: center;" value="日">日</option>
                                    <option style="text-align: center;" value="月">月</option>
                                    <option style="text-align: center;" value="年">年</option>
                                </select>
                            </td>
                            <td>产品期限：</td>
                            <td>
                                <input type="text" class="inpText info-inpW11" name="product_term_min" id="product_term_min" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: center;float:left">
                                <span style="float: left;display: inline-block;width:30px;height:28px;line-height: 28px"> --</span>
                                <input type="text" class="inpText info-inpW11" name="product_term_max" id="product_term_max" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: center;float:left">
                            </td>
                            <td>申请额度：</td>
                            <td>
                                <input type="text"  class="inpText info-inpW11" name="apply_quota_min" id="apply_quota_min" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: center;float:left">
                                <span style="float: left;display: inline-block;width:30px;height:28px;line-height: 28px"> --</span>
                                <input type="text" class="inpText info-inpW11" name="apply_quota_max" id="apply_quota_max" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: center;float:left">
                            </td>

                        </tr>

                        <tr>
                            <td>是否计算居间服务费：</td>
                            <td style="text-align: center;">
                                <select id="service_charge" style='height: 28px; margin-left: -13px; width: 163px;' name="service_charge">
                                    <option style="text-align: center;" value="">请选择</option>
                                    <option style="text-align: center;" value="1">是</option>
                                    <option style="text-align: center;" value="0">否</option>
                                </select>
                            </td>
                            <td>提前还款：</td>
                            <td style="text-align: center;">
                                <select id="repayment" style='height: 28px; margin-left: -13px; width: 163px;' name="repayment" onchange="sureRepayment(this.options[this.options.selectedIndex].value)">
                                    <option style="text-align: center;" value="">请选择</option>
                                    <option style="text-align: center;" value="1">是</option>
                                    <option style="text-align: center;" value="0">否</option>
                                </select>
                            </td>

                            <td class="repayment" style="display: none">还款时限：</td>
                            <td class="repayment"  style="display: none"><input type="text" style ='text-align: center' class="inpText info-inpW1" name="repayment_days" id="repayment_days" maxlength="20" datatype="/^[0-9]*$/"  errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;width:73%;"></td>

                        </tr>


                        </tbody></table>
                </div>
                <%-- 4/10注释（查看不展示这些）--%>
              <div class="paperBlockfree" style="display: none">
                    <div class="block_hd" style="float:left;">
                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">逾期违约惩罚参数</span>
                    </div>
                    <table class="tb_info" style="font-size:12px;">
                        <tbody><tr>
                            <td width="200">征信保护天数：</td>
                            <td width="240"><input type="text" class="inpText info-inpW1" name="creditProtectDay" id="creditProtectDay" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: right;float:left"><span style="position: relative;top:5px;">&nbsp;天</span></td>
                            <td width="160">逾期保护天数：</td>
                            <td width="280"><input type="text" class="inpText info-inpW1" name="overdueProtectDay" id="overdueProtectDay" datatype="/^[0-9]*$/" errormsg="只能输入数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;width:92%;"><span style="position: relative;top:5px;">&nbsp;天</span></td>
                        </tr>
                        <tr>
                            <td>罚息费率：</td>
                            <td><input type="text" class="inpText info-inpW1" name="interestRate" id="interestRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;"><span style="position: relative;top:5px;float:left">&nbsp;%/天</span></td>
                            <td>备注：</td>
                            <td><textarea style="width: 438px; border:none;background-color: #fff!important;text-align: right;float:right" name="interestRemark" id="interestRemark"></textarea><div> </div></td>
                        </tr>
                        <tr>
                            <td>合同违约金费率：</td>
                            <td><input type="text" class="inpText info-inpW1" name="contractViolateRate" id="contractViolateRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;"><span style="position: relative;top:5px;float:left">&nbsp;%</span></td>
                            <td>备注：</td>
                            <td><textarea id="contractViolateRemark" name="contractViolateRemark" style="width: 438px; border:none;background-color: #fff!important;text-align: right;float:left"></textarea></td>
                        </tr>
                        </tbody></table>
                </div>

               <%-- 4/10注释（查看不展示这些）--%>
                <div class="paperBlockfree" style="display: none">
                    <div class="block_hd" style="float:left;">
                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">保证金参数</span>
                    </div>
                    <table class="tb_info" style="font-size:12px;">
                        <tbody><tr>
                            <td width="100">保证金费率：</td>
                            <td><input type="text" class="inpText info-inpW1" name="bailRate"   id="bailRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;width:92%"><span style="position: relative;top:5px;">&nbsp;%</span></td>
                            <td width="100">缴纳方式：</td>
                            <td style="text-align: right">
                                <select class="info-selW1" id="giveInfo" style="line-height:11px;padding-right: 0px;float:left;margin-left:20px;" name="bailPayMode">
                                    <option value="0">退还</option>
                                    <option value="1">不退换</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>备注：</td>
                            <td colspan="3"><textarea id="bailRemark" name="bailRemark" style="width: 778px; border:none;background-color: #fff!important;text-align: right;float:left;width:98.7%;"></textarea></td>
                        </tr>
                        </tbody></table>
                </div>
                <div class="paperBlockfree" style="display: none">
                    <div class="block_hd" style="text-align:left">
                        <s class="ico icon-file-text-alt"></s><span class="bl_tit">服务费参数</span>
                    </div>
                    <div style="text-align:left">分期服务费费率：<input type="text" class="inpText info-inpW1" name="stagingServicesRate"  id="stagingServicesRate" datatype="/^[0-9]+(.[0-9]{0,4})?$/" errormsg="只能输入小数点最多四位数字！" style="border:none;background-color: #fff!important;text-align: right;float:left;position: relative;top:-5px;"><span style="position: relative;">&nbsp;%/月</span></div>
                </div>




                <%-- <div class="paperBlockfree">
                     <tr>
                         <td height="101">
                             <div id="localImag" style="padding-left:3px;"><img id="preview" src="" width="272" height="180" style="display: block; width: 272px; height: 180px;"></div>
                         </td>
                     </tr>
                     <tr>
                         <td align="left" style="padding-top:10px;"><input type="file" name="file" id="file" style="width:274px;" onchange="javascript:setImagePreview();" class="valid"></td>
                     </tr>
                 </div>--%>
            </div>
        </div>
    </form>
</div>
<!-- 产品和各个关联的页面总和 -->
<div id="relation_tree" style="display: none;">
    <!-- 1：理财产品  2：公司  3：素材 -->
    <input type="hidden" id="relationTreeTypeId" />
    <input type="hidden" id="relationProductIds" />
    <input type="hidden" id="relationSignIds" />
    <input type="hidden" id="relationPapersIds" />
    <div>
        <input type="button" style="margin-top: 5px;margin-right:5px; margin-left: 390px;" value="全选" onclick="$_crmProductMain.$_relation.$f_checkAllNodes()" />
        <input type="button" style="margin-top: 5px;margin-right:5px;" value="全取消" onclick="$_crmProductMain.$_relation.$f_cancelAllNodes()" />
    </div>
    <hr style="margin-top: 5px;margin-bottom: 5px;float: inherit;" />
    <div>
        <ul id="jsTreeCheckBox" class="ztree" style="width:230px; overflow:auto;"></ul>
    </div>
</div>
</body>
</html>
