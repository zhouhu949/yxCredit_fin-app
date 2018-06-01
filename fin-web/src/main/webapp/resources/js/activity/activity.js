/**
 * Created by ndf on 2017/9/26.
 */
//页面初始化
$(function () {
    var beginTime = {
        elem: '#beginTime',
        format: 'YYYY-MM-DD',
        min: '1999-01-01',
        // max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD',
        min: '1999-01-01',
        //max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function(datas){
            beginTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(beginTime);
    laydate(endTime);
    queryList();
    //apendSelect();
    platformType();
})
/****************************************************活动列表*****************************************************************/
function queryList(){
    g_userManage.tableUser = $('#customer_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost("activity/list", JSON.stringify(queryFilter), function (result) {
                console.log(result);
                var returnData = {};
                var resData = result.data.list;
                var resPage = result.data;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.total;
                returnData.recordsFiltered = resPage.total;
                returnData.data = resData;
                callback(returnData);
            }, "application/json")
        },
        "order": [],
        "columns" :[
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false,
                "width" : "30px"
            },
            {"data":'id',"orderable" : false,"class":"hidden"},
            {"data":'activity_title',"searchable":false,"orderable" : false},
            {"data": "activity_img_addr","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data == "1") return "弹框";
                    if(data == "2") return "轮播图";
                    if(data == "3") return "启动页";
                    else return "";
                }
            },
            {"data":'activity_content',"searchable":false,"orderable" : false},
            {"data":'activity_url',"searchable":false,"orderable" : false},
            {"data":'activity_time',"searchable":false,"orderable" : false},
            {"data":'create_time',"searchable":false,"orderable" : false},
            {"data": "activity_state","orderable" : false,
                "render": function (data, type, row, meta) {
                    if(data == "1") return "已上架";
                    if(data == "2") return "未上架";
                    else return "";
                }
            },
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false
            }


        ],
        "createdRow": function ( row, data, index,settings,json ) {
            //时间格式转换
            var creatTime = data.create_time;
            if (!creatTime) {
                creatTime = '';
            }else{
                creatTime = creatTime.substr(0,4)+"-"+creatTime.substr(4,2)+"-"+creatTime.substr(6,2)+" "+creatTime.substr(8,2)+":"+creatTime.substr(10,2)+":"+creatTime.substr(12,2);
            }
            $('td', row).eq(7).html(creatTime);

            var btnUpdate=$('<a href="##" id="btnUpdate" class="operate" style="color:#007eff">修改 </a><input type="hidden"  id="update" value="'+data.id+'"/>');
            var btnLook=$('<a href="##" id="btnLook" class="operate" style="color:#007eff">查看 </a><input type="hidden" id="look" value="'+data.id+'"/>');
            var btnDel=$('<a href="##" id="btnDel" class="operate" style="color:#007eff">删除 </a><input type="hidden" id="del" value="'+data.id+'"/>');
            var btnUp=$('<a href="##" id="btnUp" class="operate" style="color:#007eff">上架 </a><input type="hidden" id="up" value="'+data.id+'"/>');
            var btnDown=$('<a href="##" id="btnDown" class="operate" style="color:#007eff">下架 </a><input type="hidden" id="down" value="'+data.id+'"/>');
            if(data.activity_state==1){
                $('td', row).eq(9).append(btnUpdate).append(btnDel).append(btnDown).append(btnLook);
            }
            else if (data.activity_state==2){
                $('td', row).eq(9).append(btnUpdate).append(btnDel).append(btnUp).append(btnLook);
            }else {
                $('td', row).eq(9).append(btnUpdate).append(btnDel).append(btnLook);
            }
        },






        "initComplete" : function(settings,json) {
            //点击一行显示明细
            // $("#Res_list").delegate( 'tbody tr td:not(:last-child)','click',function(e){
            // });
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            $("#btn_add").click(function() {
                layer.open({
                    type: 1,
                    title: '添加Banner',
                    area : [ '650px', '420px' ],
                    content: $('#Add_procedure_style'),
                    btn: ['保存', '取消'],
                    success: function () {
                        $('#platformType').removeAttr("disabled");
                        $('#activity_titled').removeAttr("disabled");
                        $('#activity_url').removeAttr("disabled");
                        $('#activity_stated').removeAttr("disabled");
                        $('#activity_content').removeAttr("disabled");
                        $('#beginTimed').removeAttr("disabled");
                        $('#endTimed').removeAttr("disabled");
                        $('#activity_img_addr').removeAttr("disabled");
                        $('#priority').removeAttr("disabled");
                        $("#picShow").removeAttr("disabled");
                        $('#activity_id').val(uuid());
                        $('#activity_titled').val('');
                        $('#activity_img_addr').val('');
                        $('#priority').val('');
                        $('#activity_url').val('');
                        $('#activity_stated').val('');
                        $('#activity_content').val('');

                        $('#beginTimed').val('');$('#endTimed').val('');
                    },
                    yes:function(index,layero){

                        var param={};
                        param.id=$('#activity_id').val();
                        param.activity_title = $('#activity_titled').val();
                        param.activity_url = $('#activity_url').val();
                        param.activity_state = $('#activity_stated').val();
                        param.activity_content = $('#activity_content').val();
                        param.activity_img_addr = $('#activity_img_addr').val();
                        param.priority=$('#priority').val();
                        var a = $('#beginTimed').val()+'~'+$('#endTimed').val();
                        param.activity_time = a;
                        if(param.activity_title==''){
                            layer.msg("Banner标题不可为空！",{time:2000});return
                        }
                        if(param.activity_url==''){
                            layer.msg("Banner链接不可为空！",{time:2000});return
                        }
                        var reg = /[\u4E00-\u9FA5]/g;
                        if(reg.test(param.activity_url)){
                            layer.msg("Banner链接不能为中文！",{time:2000});return
                        }

                        if(param.activity_content==''){
                            layer.msg("Banner描述不可为空！",{time:2000});return
                        }
                        if($('#beginTimed').val()=='' && $('#endTimed').val()!=''){
                            layer.msg("Banner期限选择有误！",{time:2000});return
                        }

                        if(param.activity_state=='-1'){
                            layer.msg("Banner状态不可为空！",{time:2000});return
                        }
                        if(param.activity_img_addr=='-1'){
                            layer.msg("请选择Banner图片位置！",{time:2000});return
                        }
                        if(param.priority=='-1'){
                            layer.msg("请选择显示优先级！",{time:2000});return
                        }
                        uploadFile();
                        param.activity_img_url = activityImgUrl ;
                        if(param.activity_img_url==''){
                            layer.msg("请上传Banner图片！",{time:2000});return
                        }
                        if(param.activity_img_url=='-1'){
                            layer.msg("请上传Banner图片大小不符合要求！",{time:2000});return
                        }
                        if(param.activity_img_url==''){
                            layer.msg("请上传Banner图片！",{time:2000});return
                        }
                        Comm.ajaxPost('activity/add',JSON.stringify(param), function (data) {
                                if(data.code==0){
                                    layer.msg(data.msg,{time:2000},function(){
                                        layer.closeAll();
                                        g_userManage.tableUser.ajax.reload();
                                    })
                                }
                            },"application/json"
                        );
                    }
                })
            });
            $("#btn_search_reset").click(function() {
                $("input[name='activity_title']").val("");
                $('#activity_state').val("");
                $('#activity_state').val("");
                $('#beginTime').val("");
                $('#endTime').val("");
                g_userManage.fuzzySearch = false;
                g_userManage.tableUser.ajax.reload();
            });

            $("#customer_list").on("click", "#btnDel",function(e){
                debugger
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                var activity_state = $(target).parents("tr").children().eq(8).html();
                var param = {}; param.id = id;
                if(activity_state == '已上架'){
                    layer.msg("上架Banner不可删除！",{time:2000});
                    return
                }
                Comm.ajaxPost('activity/del',JSON.stringify(param), function (data) {
                        if(data.code==0){
                            layer.msg(data.msg,{time:2000},function(){
                                layer.closeAll();
                                g_userManage.tableUser.ajax.reload();
                            })
                        }
                    },"application/json"
                );
            });
            $("#customer_list").on("click", "#btnLook",function(e){
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                var param = {}; param.id = id;
                Comm.ajaxPost('activity/lookActivity',JSON.stringify(param), function (data) {
                        if(data.code==0){
                            var data = data.data;
                            layer.open({
                                type: 1,
                                title: 'Banner详情',
                                area : [ '650px', '430px' ],
                                content: $('#Add_procedure_style'),
                                success: function () {
                                    $('#activity_titled').val(data.activity_title);
                                    $('#activity_url').val(data.activity_url);
                                    $('#activity_stated').val(data.activity_state);
                                    $('#activity_content').val(data.activity_content);
                                    $('#activity_img_addr').val(data.activity_img_addr);
                                    $('#platformType').val(data.platform_type);
                                    $('#priority').val(data.priority);
                                    var b = data.activity_time.substr(0,19);
                                    if(b.length>6){
                                        $('#beginTimed').val(b);
                                    }else {
                                        $('#beginTimed').val('');
                                    }
                                    var a = data.activity_time.substr(20,38);
                                    if(a.length>6){
                                        $('#endTimed').val(a);
                                    }else {
                                        $('#endTimed').val('');
                                    }

                                    //alert("图片地址："+data.activity_img_url);
                                    $("#preview").attr("src",_ctx +"/activity/byx/imgUrl?bannerImg="+data.activity_img_url);
                                    $('#platformType').attr("disabled",true);
                                    $('#activity_titled').attr("disabled",true);
                                    $('#activity_url').attr("disabled",true);
                                    $('#activity_stated').attr("disabled",true);
                                    $('#activity_content').attr("disabled",true);
                                    $('#beginTimed').attr("disabled",true);
                                    $('#endTimed').attr("disabled",true);
                                    $('#activity_img_addr').attr("disabled",true);
                                    $("#picShow").attr("disabled", true);
                                    $("#priority").attr("disabled", true);

                                    $("#prompt ").css("display"," none");
                                    $("#file").css("display"," none");

                                },
                                yes:function(index,layero){
                                }
                            })
                        }
                    },"application/json"
                );
            });
            $("#customer_list").on("click", "#btnUpdate",function(e){
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                $('#activity_id').val(id);
                layer.open({
                    type: 1,
                    title: 'Banner修改',
                    area : [ '650px', '430px' ],
                    content: $('#Add_procedure_style'),
                    btn: ['保存', '取消'],
                    success: function () {
                        var param = {};param.id = id;
                        Comm.ajaxPost('activity/lookActivity',JSON.stringify(param), function (data) {
                                if(data.code==0){
                                    $('#platformType').removeAttr("disabled");
                                    $('#activity_titled').removeAttr("disabled");
                                    $('#activity_url').removeAttr("disabled");
                                    $('#activity_stated').removeAttr("disabled");
                                    $('#activity_content').removeAttr("disabled");
                                    $('#beginTimed').removeAttr("disabled");
                                    $('#endTimed').removeAttr("disabled");
                                    $('#activity_img_addr').removeAttr("disabled");
                                    $("#picShow").removeAttr("disabled");
                                    $("#priority").removeAttr("disabled");
                                    debugger
                                    var data = data.data;
                                    $("#preview").attr("src",_ctx +"/activity/byx/imgUrl?bannerImg="+data.activity_img_url);
                                    $('#activity_titled').val(data.activity_title);
                                    $('#activity_url').val(data.activity_url);
                                    $('#activity_img_addr').val(data.activity_img_addr);
                                    $('#platformType').val(data.platform_type);
                                    $('#activity_stated').val(data.activity_state);
                                    $('#activity_content').val(data.activity_content);
                                    // $('.addMaterial').attr('src',data.host+data.activity_img_url);

                                    //$('#activity_img_fileName').val(data.activity_img_url);

                                    $('#priority').val(data.priority);
                                    var b = data.activity_time.substr(0,19);
                                    if(b.length>6){
                                        $('#beginTimed').val(b);
                                    }else {
                                        $('#beginTimed').val('');
                                    }
                                    var a = data.activity_time.substr(20,38);
                                    if(a.length>6){
                                        $('#endTimed').val(a);
                                    }else {
                                        $('#endTimed').val('');
                                    }
                                }
                            },"application/json"
                        );
                        $('#activity_titled').attr("disabled",false);
                        $('#activity_titled').attr("disabled",false);
                        $('#activity_url').attr("disabled",false);
                        $('#activity_stated').attr("disabled",false);
                        $('#activity_content').attr("disabled",false);
                        $('#beginTimed').attr("disabled",false);
                        $('#endTimed').attr("disabled",false);


                    },
                    yes:function(index,layero){
                        var param={};
                        debugger

                        //alert("图片地址"+activityImgUrl);
                        //param.id=$('#update').val();
                        param.id=id;
                        param.activity_title = $('#activity_titled').val();
                        param.activity_url = $('#activity_url').val();
                        param.activity_state = $('#activity_stated').val();
                        param.activity_content = $('#activity_content').val();
                        param.activity_img_addr = $('#activity_img_addr').val();
                        param.priority = $('#priority').val();
                        param.platform_type=$('#platformType').val();

                        var a = $('#beginTimed').val()+'~'+$('#endTimed').val();
                        param.activity_time = a;
                        if(param.activity_title==''){
                            layer.msg("Banner标题不可为空！",{time:2000});return
                        }
                        if(param.activity_url==''){
                            layer.msg("Banner链接不可为空！",{time:2000});return
                        }
                        var reg = /[\u4E00-\u9FA5]/g;
                        if(reg.test(param.activity_url)){
                            layer.msg("Bnner连接不能为中文！",{time:2000});return
                        }

                        if(param.activity_content==''){
                            layer.msg("Banner描述不可为空！",{time:2000});return
                        }
                        if($('#beginTimed').val()=='' && $('#endTimed').val()!=''){
                            layer.msg("Banner期限选择有误！",{time:2000});return
                        }
                        if(param.activity_img_url=='../resources/images/photoadd.png'){
                            layer.msg("请上传Banner图片！",{time:2000});return
                        }
                        debugger
                        if(param.activity_img_addr=="-1"){
                            layer.msg("请选择Banner图片位置！",{time:2000});return
                        }
                        if(param.activity_state=='-1'){
                            layer.msg("Bnner状态不可为空！",{time:2000});return
                        }
                        if(param.priority=='-1'){
                            layer.msg("请选择显示优先级！",{time:2000});return
                        }
                        uploadFile();
                        param.activity_img_url = activityImgUrl;
                        if(param.activity_img_url==''){
                            layer.msg("请上传Banner图片！",{time:2000});return
                        }
                        if(param.activity_img_url=='-1'){
                            layer.msg("请上传Banner图片大小不符合要求！",{time:2000});return
                        }
                        Comm.ajaxPost('activity/update',JSON.stringify(param), function (data) {
                                if(data.code==0){
                                    layer.msg(data.msg,{time:2000},function(){
                                        layer.closeAll();
                                        g_userManage.tableUser.ajax.reload();
                                    })
                                }
                            },"application/json"
                        );
                    }
                })
            })

            $("#customer_list").on("click", "#btnUp",function(e){
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                var param = {}; param.id = id;param.state = 1;
                Comm.ajaxPost('activity/updateState',JSON.stringify(param), function (data) {
                        if(data.code==0){
                            layer.msg(data.msg,{time:2000},function(){
                                layer.closeAll();
                                g_userManage.tableUser.ajax.reload();
                            })
                        }
                    },"application/json"
                );
            });

            $("#customer_list").on("click", "#btnDown",function(e){
                var target = e.target || window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
                var param = {}; param.id = id;param.state = 2;
                Comm.ajaxPost('activity/updateState',JSON.stringify(param), function (data) {
                        if(data.code==0){
                            layer.msg(data.msg,{time:2000},function(){
                                layer.closeAll();
                                g_userManage.tableUser.ajax.reload();
                            })
                        }
                    },"application/json"
                );
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function() {
        g_userManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
}
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};
        var page = {};
        if (g_userManage.fuzzySearch) {
            var beginTime = $("#beginTime").val();
            if (beginTime != null && beginTime != '') {
                param.beginTime = beginTime.replace(/[^0-9]/ig, "");//字符串去除非数字
            }
            var endTime = $("#endTime").val();
            if (endTime != null && endTime != '') {
                param.endTime = endTime.replace(/[^0-9]/ig, "");//字符串去除非数字
            }
            var activity_state = $("#activity_state").val();
            if(activity_state !=null && activity_state!=''){
                param.activity_state = activity_state;
            }
            var activity_title = $("#activity_title").val();
            if(activity_title !=null && activity_title!=''){
                param.activity_title = activity_title;
            }
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        console.log(page.firstIndex)
        if (page.pageSize==-1)
        {
            page.pageSize=10;
        }
        paramFilter.page = page;
        return paramFilter;
    }
};

function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}
//动态获取下拉选
/*function apendSelect() {
    Comm.ajaxPost('activity/imgSelect', null, function (data) {
            if (data.code == 0) {
                var map = [];
                map = data.data;
                for (var i = 0; i < map.length; i++) {
                    var btndel = '<option value="' + map[i].code + '">' + map[i].name + '</option>';
                    $('#activity_img_addr').append(btndel);
                }
            }
        }, "application/json"
    );
}*/

//动态加载下拉框内容
function platformType() {
    Comm.ajaxPost('activity/platformType',null, function (data) {
            if(data.code==0){
                var map = [];map = data.data;
                for (var i = 0 ;i<map.length;i++){
                    var btndel='<option value="'+map[i].code+'">'+map[i].name+'</option>';
                    $('#platformType').append(btndel);
                }
            }
        },"application/json"
    );
}


/**************************************上传图片********************************/
var activityImgUrl;
function uploadFile() {
    activityImgUrl = "";
    if('' != $("#preview").attr("src")) {
        $("#activityImgForm").ajaxSubmit({
            url : "merUpload",
            type : "POST",
            async: false,
            dataType : "JSON",
            success:function(data){
                if (data != null) {
                    //上传成功
                    activityImgUrl = data.data;
                } else {
                    layer.msg(data.message,{time:2000});
                    return;
                }
            }
        });
    }
}

//下面用于图片上传预览功能
function setImagePreview1() {
    var docObj=document.getElementById("file");

    var imgObjPreview=document.getElementById("preview");
    if(docObj.files &&docObj.files[0])
    {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '162px';
        imgObjPreview.style.height = '110px';
        //imgObjPreview.src = docObj.files[0].getAsDataURL();

        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }
    else
    {
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag");
        //必须设置初始大小
        localImagId.style.width = "162px";
        localImagId.style.height = "110px";
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        try{
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        }
        catch(e)
        {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}