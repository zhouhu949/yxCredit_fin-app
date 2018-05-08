/**
 * Created by Administrator on 2017/12/5.
 */
//获取全部担保人信息
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        param.merName=$.trim($("#search_merName").val()); //担保人姓名

        //param.idcard=$.trim($("#content").val());
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    addRelationOption();
})
//商户列表展示
$(function (){
    if(g_userManage.tableUser){
        g_userManage.tableUser.ajax.reload();
    }else {
        g_userManage.tableUser = $('#sign_list').dataTable($.extend({
            'iDeferLoading': true,
            "bAutoWidth": false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('reclaimMerchant/getMerchantList', JSON.stringify(queryFilter), function (result) {
                    //封装返回数据
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                }, "application/json");
            },
            "order": [],
            "columns": [
                {
                    "className": "cell-operation",
                    "data": null,
                    "defaultContent": "",
                    "orderable": false,
                    "width": "30px"
                },
                {"data": "depId", "orderable": false},
                {"data": "merName", "orderable": false},
                {"data": "merLogo", "orderable": false},
                {"data": "merInfo", "orderable": false},
                {
                    "data": "status", "orderable": false,
                    "render": function (data, type, row, meta) {
                        if (data == "0") {
                            return "停用";
                        } else if (data == "1") {
                            return "启用";
                        } else {
                            return "";
                        }
                    }
                },
                {"data": "updateUser", "orderable": false,},
                {"data": "updateTime", "orderable": false,
                    "render": function (data, type, row, meta) {
                        return formatTime(data);
                    }
                },
                {"data": "null", "orderable": false, "defaultContent": ""},

            ], "createdRow": function (row, data, index, settings, json) {

                var btnConfig = $('<span style="color: #307ecc;" onclick="config(\'' + data.id + '\')">修改</span>');
                var btnQyong = $('<span style="color: #307ecc;" onclick="updateState(\'' + data.id + '\',\'' + 1 + '\')">启用</span>');
                var btnTyong = $('<span style="color: #307ecc;" onclick="updateState(\'' + data.id + '\',\'' + 0 + '\')">停用</span>');

                var btndel = $('<span style="color: #307ecc;" onclick="deleteContact(\'' + data.id + '\',\'' + data.status + '\')">删除</span>');
                if (data.status == 1) {//启用
                    $("td", row).eq(8).append(btnConfig).append(' ').append(btnTyong).append(' ').append(btndel);
                } else {
                    $("td", row).eq(8).append(btnConfig).append(' ').append(btnQyong).append(' ').append(btndel);
                }
            },

            "initComplete": function (settings, json) {
                //搜索
                $("#btn_search").click(function () {
                    g_userManage.fuzzySearch = true;
                    g_userManage.tableUser.ajax.reload();
                });

                $("#btn_add").on("click", function () {
                    /*重置弹出框图片！！！*/
                    $("#img").attr("src","../resources/images/photoadd.png");
                    layer.open({
                        type: 1,
                        title: '添加商户',
                        maxmin: true,
                        shadeClose: false,
                        area: ['400px', '500px'],
                        content: $('#addMerchantNew').show(),
                        btn: ['保存', '取消'],
                        success: function (index, layero) {

                            $("#depId").val('');
                            $("#merName").val('');
                            //$("#merLogo").val('');
                            $("#merLogo").val('');
                            $("#merInfo").val('');

                            $("#status").val('');
                           // $("#updateUser").val('');
                            $("#updateTime").val('');
                        },
                        yes: function (index, layero) {
                            var param = {};
                            //传入后台参数
                            param.depId = $.trim($("#depId").val());
                            //param.depId=$.trim($.trim($("#depId").val()));
                            param.merName = $.trim($("#merName").val());
                            param.merLogo = $.trim($("#merLogo").val());
                            param.merInfo = $.trim($("#merInfo").val());
                            param.status = $.trim($("#status").val());
                            param.updateUser = $.trim($("#updateUser").val());
                            param.updateTime = $.trim($("#updateTime").val());
                            if( param.depId==''){
                                layer.msg("分公司ID不能为空",{time:1000});return
                            }
                            if( param.merName==''){
                                layer.msg("商户名称不能为空~",{time:1000});return
                            }
                            if( param.status==''){
                                layer.msg("状态不能为空",{time:1000});return
                            }
                            console.log(param);
                            Comm.ajaxPost('reclaimMerchant/addMerchant', JSON.stringify(param), function (data) {
                                    if (data.code == 0) {
                                        layer.msg(data.msg, {time: 1000}, function () {
                                            layer.closeAll();
                                            //添加之后刷新页面
                                            g_userManage.tableUser.ajax.reload();
                                        })
                                    }else {
                                        layer.msg(data.msg, {time: 1000}, function () {
                                        })
                                    }
                                }, "application/json"
                            );
                        }
                    });
                })
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableUser.on("order.dt search.dt", function () {
            g_userManage.tableUser.column(0, {
                search: "applied",
                order: "applied"
            }).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
    }
});
//动态加载下拉选
function addRelationOption(){
    $("#depId").val("");
    Comm.ajaxPost(
        'reclaimMerchant/getRelationList', JSON.stringify(),
        function (data) {
            var opt ="";
            debugger
            for(i=0;i<data.data.length;i++){
               /*opt+=$('<option style="font-size: 10px;" value='+data.data[i].code+'>'+data.data[i].name+'</option>')*/
                opt += "<option value=" + data.data[i].id + ">" + data.data[i].name + "</option><br>";
            }
            $("#depId").append(opt);

        }, "application/json"
    );
}


//时间转换
function formatTime(t){
    debugger

    var time = t.replace(/\s/g,"");
    console.log(time);
    //去掉所有空格
    time = time.substring(0,4)+"-"+time.substring(5,7)+"-"+time.substring(8,10)+" "+
        time.substring(10,12)+":"+time.substring(13,15)+":"+time.substring(16,18);
    return time;
}


//启用或停止
function updateState(id,state) {
    var param = {
        id: id,
        status: state
    }
    Comm.ajaxPost(
        'reclaimMerchant/updateStatus', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 2000}, function () {
                layer.closeAll();
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json"
    );

}
function deleteContact(id,status) {
    var param = {};
    param.id = id;
    if(status==1){
        layer.msg("启用状态下不可删除！",{time:1000});return
    }
    Comm.ajaxPost(
        'reclaimMerchant/deleteMerchant',JSON.stringify(param),
        function (data) {
            layer.msg(data.msg,{time:1000},function(){
                g_userManage.tableUser.ajax.reload();
            });
        }, "application/json");
}






//查询重置
$(function(){
    $("#btn_search_reset").on("click",function () {
        $("#search_merName").val("");
    });

})


function setImagePreview1(me){
debugger
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
        console.log(file.name + ' => ' + storeAs);
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


                debugger;
                console.log(result);
                var res = result.res.requestUrls[0].split('?')[0];
                if(uploadImgType!='1'){
                    $("#"+uploadImgNum).append(uploadImgHtml);
                    var num = uploadImgNum.replace(/[^0-9]/ig,"");
                    $("#"+uploadImgNum).find("input[name=type]").val(num);
                }
                //$(me).attr('disabled',true);
                $(me).parent().parent().parent().find("input[name=originalName]").val(file.name);
               /* $("#merLogo").val(res);*/
                $(me).parent().parent().parent().find("input[name=src]").val(res);
                $(me).parent().parent().parent().addClass("getFanQiZha");
                // $(me).parent().append("<span class='closeImg' onclick='closeDelete(this)'>×</span>");
                //layer.closeAll();
                layer.close(index);
            }).catch(function (err) {
                layer.closeAll();
                console.log(err);
            });
        })

    }
    return true;
}
// 操作模板配置
// 模板配置
function config(id) {
    layer.open({
        type : 1,
        title : '商户配置',
        maxmin : true,
        shadeClose : false,
        area : [ '400', '500'  ],
        content : $('#addMerchantNew'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            var param = {};
            param.id = id;

            Comm.ajaxPost('reclaimMerchant/getById',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        debugger


                        console.log(data.data);
                        $('#depId').val(data.data.depId);
                        $('#merName').val(data.data.merName);
                        $('#merLogo').val(data.data.merLogo);
                        $('#merInfo').val(data.data.merInfo);
                        $('#status').val(data.data.status);
                       $('#updateUser').val(data.data.updateUser);
                        $('#updateTime').val(data.data.updateTime);
                        $('#img').attr('src',data.data.merLogo);
                        // $('#message_desc').val(data.data.message_desc);
                    }
                },"application/json"
            );
        },
        yes:function(index,layero){
            debugger
            var param={};
            param.id=id;
            param.depId = $('#depId').val();
            param.merName = $('#merName').val();
            param.merLogo = $('#merLogo').val();
            param.merInfo = $('#merInfo').val();
            param.status = $('#status').val();
            param.updateUser = $('#updateUser').val();
            param.updateTime = $('#updateTime').val();

            Comm.ajaxPost('reclaimMerchant/updateMerchant',JSON.stringify(param), function (data) {
                    if(data.code==0){
                        layer.msg(data.msg,{time:1000},function(){
                            layer.closeAll();
                            g_userManage.tableUser.ajax.reload();
                        })
                    }
                },"application/json"
            );
        }
    });
}

