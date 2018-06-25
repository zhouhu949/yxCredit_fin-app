/**
 * Created by Administrator on 2017/6/12 0012.
 */

var g_userManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};

var typeChange = function (obj) {
    console.log(obj);
    var type = $("#" + obj).val();
    console.log(type);
    if ("1" == type) {
        //类型选择公司
        document.getElementById("dept").style.display = "none";
        document.getElementById("company").style.display = "block";
        $("#type option[value='0']").attr("selected", false);
        $("#type option[value='1']").attr("selected", "selected");

        setSelect('1', 'province');
        var result = {};
        result.name = "公司类型";

        Comm.ajaxPost('sysDepartment/getDetailList', JSON.stringify(result), function (data) {
            console.log(data.data);
            $('#companyType').append('<option value="">请选择</option>');
            for (var i = 0; i < data.data.length; i++) {
                var html = $('<option value="' + data.data[i].code + '">data.data[i].name</option>');

                $('#companyType').append('<option value="' + data.data[i].code + '">' + data.data[i].name + '</option>')
            }

        }, "application/json")


    } else {
        //类型选择部门
        document.getElementById("dept").style.display = "block";
        document.getElementById("company").style.display = "none";
        $("#depttype option[value='1']").attr("selected", false);
        $("#depttype option[value='0']").attr("selected", "selected");


    }
};


var menuChange = function (obj, tobj, id, name) {
    var newValue = $("#" + obj + "").val();
    //判断选择菜单等级的改变事件
    if (newValue == 1) {
        //子菜单
        $("#" + id + "").val("");
        $("#" + name + "").val("");
        $("#" + tobj + "").show();
    } else {
        $("#" + id + "").val("0");
        $("#" + tobj + "").hide();
    }
};

var showDeptZtree = function () {
    //打开部门ztree弹框
    var deptTree = layer.open({
        title: "部门",
        type: 1,
        maxWidth: 'auto',
        area: ['450px', '500px'],
        skin: 'layer_normal',
        shift: 5,
        offset: '150px',
        btn: ['保存', '取消'],
        shadeClose: false,
        content: $("#jstree").show(),
        success: function (layero, index) {
            getDepartmentZtree();

            $('#jstree').delegate('span.text', 'click', function () {
                console.log($(this).attr('title'));
                var parentDeptID = $(this).attr('title');
                var parentDept = $(this).text();
                $('#deptPname').val(parentDept);
                $('#deptPid').val(parentDeptID);

                $('#pname').val(parentDept);
                $('#pid').val(parentDeptID);
            })
        },
        yes: function () {
            layer.close(deptTree);
        }
    });
};

//表格初始化調用，最小級箭頭去掉
function removeArrow() {
    var trs = $('#magData tbody tr');
    for (var i = 0; i < trs.length - 1; i++) {
        if (trs[i + 1]) {
            var Cls1 = trs[i].className.split(" ")[1];
            var n1 = Cls1.substr(5, 1);
            var Cls2 = trs[i + 1].className.split(" ")[1];
            var n2 = Cls2.substr(5, 1);
            if (n1 >= n2) {
                $('#magData tbody').children('tr').eq(i).children('td').children('span').children('a').css('background', 'none');
                $('#magData tbody').children('tr').eq(i).children('td').children('span').removeAttr('onclick');
            }
        }
        console.log(trs[i + 1].className);
        if (trs[i + 1].className.indexOf('level1') != -1 || !trs[i + 1]) {
            $('#magData tbody').children('tr').eq(i).children('td').children('span').children('a').css('background', 'none');
            $('#magData tbody').children('tr').eq(i).children('td').children('span').removeAttr('onclick');
        }
    }
    $('#magData tbody tr:last-child').children('td').children('span').children('a').css('background', 'none');
    $('#magData tbody').children('tr').eq(i).children('td').children('span').removeAttr('onclick');
}

//tree收放功能
function get(obj) {
    var className = $(obj).parent().parent()[0].className.split(" ")[0];
    var className1 = $(obj).parent().parent()[0].className.split(" ")[1];
    if (className == "collapsed") {
        var nextList = $(obj).parent().parent().nextUntil('tr.collapsed');
        var isHide = $(nextList[0]).css("display");
        for (var i = 0; i < nextList.length; i++) {
            var nextclass1 = $(nextList)[i].className.split(" ")[0];
            var nextclass2 = $(nextList)[i].className.split(" ")[1];
            if (nextclass1 == "expanded") {
                if (isHide != "none") {
                    $($(nextList)[i]).hide();
                    $(obj).children('a').addClass('right').removeClass('down');
                } else {
                    $($(nextList)[i]).show();
                    $(obj).children('a').addClass('down').removeClass('right');
                }
            } else if (nextclass1 == "collapsed") {
                return;
            }
        }
    } else {
        var cls1 = $(obj).parent().parent()[0].className.split(" ")[1];
        var n = cls1.substr(5, 1);
        var cName = "level" + n;
        if (className1 == cName) {
            var nextList = $(obj).parent().parent().nextUntil("tr." + cName);
            var isShow = $(nextList[0]).css("display");
            for (var i = 0; i < nextList.length; i++) {
                var nextclass2 = $(nextList)[i].className.split(" ")[1];
                if (nextclass2 != cName && nextclass2 != "level1") {
                    if (isShow != "none") {
                        $($(nextList)[i]).hide();
                        $(obj).children('a').addClass('right').removeClass('down');
                    } else {
                        $($(nextList)[i]).show();
                        $(obj).children('a').addClass('down').removeClass('right');
                    }
                } else if (nextclass2 == cName || nextclass2 == "level1") {
                    return;
                }
            }
        }
    }
}

//省市区三级联动
function onSelectChange(obj, toSelId, flag) {
    if (flag == 1) {
        setSelect(obj, toSelId);
    } else {
        setSelect(obj.value, toSelId);
    }
}
function setSelect(fromSelVal, toSelId) {
    document.getElementById(toSelId).innerHTML = "";
    //调省市区接口
    var result = {};
    if (!toSelId) {
        result.flag = "province";
    } else {
        result.flag = toSelId;
    }
    result.parentId = fromSelVal;
    Comm.ajaxPost('pcd/getPCD', JSON.stringify(result), function (result) {
        console.log(result);
        createSelectObj(result, toSelId);
    }, "application/json", '', '', '', false)
}

function createSelectObj(data, toSelId) {
    var arr = data.data;
    if (arr != null && arr.length > 0) {
        console.log($('#province'));
        var obj = document.getElementById(toSelId);
        obj.innerHTML = "";
        var nullOp = document.createElement("option");
        nullOp.setAttribute("value", "");
        nullOp.appendChild(document.createTextNode("-请选择-"));
        obj.appendChild(nullOp);
        for (var o in arr) {
            var op = document.createElement("option");
            op.setAttribute("value", arr[o].id);
            //op.text=arr[o].name;//这一句在ie下不起作用，用下面这一句或者innerHTML
            if (arr[o].provinceName) {
                op.appendChild(document.createTextNode(arr[o].provinceName));
            } else if (arr[o].cityName) {
                op.appendChild(document.createTextNode(arr[o].cityName));
            } else {
                op.appendChild(document.createTextNode(arr[o].districtName));
            }
            obj.appendChild(op);
        }
    } else {
        $('#' + toSelId).html("<option value=''>-请选择-</option>");
    }
}
$("#province").on('change', function () {
    var htmlSel = $("#province option:selected").html();
    if (htmlSel == "-请选择-") {
        $("#city").html("<option value=''>-请选择-</option>");
        $("#district").html("<option value=''>-请选择-</option>")
    }
    else if (htmlSel == "") {
        $("#city").html("<option value=''>-请选择-</option>");
        $("#district").html("<option value=''>-请选择-</option>")
    }
    else {
        $("#district").html("<option value=''>-请选择-</option>")
    }
})
$("#cityId").on('change', function () {
    var htmlSel = $("#city option:selected").html();
    if (htmlSel == "-请选择-") {
        $("#district").html("<option value=''>-请选择-</option>")
    }
    else if (htmlSel == "") {
        $("#district").html("<option value=''>-请选择-</option>")
    }
})

$(function () {
    g_userManage.tableUser = $("#magData").dataTable($.extend({
            "bSort": false,
            "bAutoWidth": false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": false,
            "ajax": function (data, callback, settings) {
                var param = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('sysDepartment/getDepartmentZtree', '', function (result) {
                    console.log(result);
                    if (result.msg == 'false') {
                        $('#hasComp').val('no');
                    }
                    var returnData = {};
                    returnData.draw = data.draw;
                    returnData.recordsTotal = result.length;
                    returnData.recordsFiltered = result.length;
                    returnData.data = result.data;
                    callback(returnData);
                }, "application/json")
            },
            "order": [],
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "abbreviation"
                },
                {
                    "data": "phone"
                },
                {
                    "data": "principal"
                },
                {
                    "data": "proAddress"
                },
                {
                    "data": "sequence",
                    "class": "hidden"
                },
                {
                    "className": "cell-operation",
                    "data": null,
                    "defaultContent": "",
                    "orderable": false,
                    "width": "320px"
                },
                {
                    "data": "id",
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "pid",
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "domain",//公司域名
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "status",//公司状态
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "principal",//负责人
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "companyType",//公司类型
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "provinceId",//省
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "cityId",//市
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "districtId",//区
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "address",//详细地址
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "description",//描述
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "grade",//等级
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "pname",//父级部门名称
                    "class": "hidden",
                    "searchable": false
                },
                {
                    "data": "level",//層級
                    "class": "hidden",
                    "searchable": false
                }
            ],
            "createdRow": function (row, data, index, settings, json) {

                /* var btnUpdate = $('<a href="##" class="update">更新序列&nbsp;&nbsp;&nbsp;</a>');*/
                /*var btnAdd = $('<a href="##" class="add">添加&nbsp;&nbsp;&nbsp;</a>');*/
                var btnEdit = $('<a href="##" class="edit" style="color: #307ecc">修改</a>');
                $("td", row).eq(6).append(btnEdit);
                $("td", row).eq(0).css("text-align", "left");
                $(row).attr("class", "expanded");
                $(row).attr("data-tt-id", data.id);
                $(row).attr("data-tt-parent-id", data.pid);
                $("td", row).eq(0).prepend("<span class='indenter' onclick='get(this)' style='padding:0 3px 0 5px'><a href='##' title='Collapse'>&nbsp;</a></span>");
                var parentId = $(row).attr("data-tt-parent-id");
                if (parentId == "0") {
                    $(row).attr("class", "collapsed")
                }

                $(row).addClass('level' + data.level);

                var abbreviation = $("td", row).eq(1).html();
                var phone = $("td", row).eq(2).html();
                var principal = $("td", row).eq(3).html();
                var proAddress = $("td", row).eq(4).html();
                if (!abbreviation) {
                    $("td", row).eq(1).html('--');
                }
                if (!phone) {
                    $("td", row).eq(2).html('--');
                }
                if (!principal) {
                    $("td", row).eq(3).html('--');
                }
                if (!proAddress) {
                    $("td", row).eq(4).html('--');
                }

            },
            "initComplete": function (settings, json) {
                removeArrow();
                $('#magData tbody tr').click(function () {
                    $(this).css('background', '#D1D1D1').siblings('tr').css('background', '#fff');
                })
                //添加按钮
                $("#addBtn").click(function (e) {
                    var target = e.target || window.event.target;
                    layer.open({
                        type: 1,
                        title: "添加",
                        maxmin: true,
                        shadeClose: true,
                        area: '600px',
                        offset: '75px',
                        shift: 5,
                        content: $('#organize').show(),
                        btn: ['保存', '取消'],
                        success: function (layero, index) {
                            $("#deptType option[value='0']").attr("selected", "selected");
                            //清空值
                            $('#deptName').val('');
                            $('#deptPname').val('');
                            $('#description').val('');

                            $('#name').val('');
                            $('#abbreviation').val('');
                            $('#phone').val('');
                            $('#domain').val('');
                            $('#pname').val();
                            $('#principal').val('');
                            $('#address').val('');
                            $('#comDescription').val('');
                            $('#companyType').empty();

                            //子菜单
                            $("#deptMenu option[value='1']").attr("selected", "selected");
                            $("#dept_menu").show();

                            //默认添加部门
                            document.getElementById("dept").style.display = "block";
                            document.getElementById("company").style.display = "none";
                            //隐藏部门状态
                            document.getElementById("deptStatusTr").style.display = "none";
                            //隐藏部门状态
                            document.getElementById("cmpStatusTr").style.display = "none";

                            if ($('#hasComp').val() == 'no') {
                                $("#deptMenu option[value='0']").attr("selected", "selected");
                                $("#companyMenu option[value='0']").attr("selected", "selected");
                                $("#deptMenu").attr('disabled', 'true');
                                $("#companyMenu").attr('disabled', 'true');
                                $('#dept_menu').hide();
                                $('#company_menu').hide();
                            }
                        },
                        yes: function (layero, index) {
                            if ($('#dept').css('display') == 'block') {//部门 保存
                                var type = $('#deptType option:selected').val();
                                if (!type) {
                                    layer.msg("请选择部门", {time: 2000});
                                    return;
                                }
                                var name = $('#deptName').val();
                                if (!name) {
                                    layer.msg("请填写部门名称", {time: 2000});
                                    return;
                                }
                                var grade = $('#deptMenu option:selected').val();
                                var deptPname = $('#deptPname').val();
                                var deptPid = $('#deptPid').val();
                                if (!grade) {
                                    layer.msg("请选择等级", {time: 2000});
                                    return;
                                } else if (grade == 1) {//子级部门
                                    if (!deptPname) {
                                        layer.msg("请选择父级部门", {time: 2000});
                                        return;
                                    }
                                }
                                var description = $('#description').val();
                                var type = $.trim(type);
                                var name = $.trim(name);
                                var grade = $.trim(grade);
                                var deptPid = $.trim(deptPid);
                                var deptPname = $.trim(deptPname);
                                var description = $.trim(description);
                                var result = {};
                                result.type = type;
                                result.name = name;
                                result.grade = grade;
                                result.pid = deptPid;
                                result.pname = deptPname;
                                result.description = description;
                                console.log(result);

                                Comm.ajaxPost('sysDepartment/saveDepartment', JSON.stringify(result), function (data) {

                                    layer.msg(data.msg, {time: 2000}, function () {
                                        layer.closeAll();
                                        g_userManage.tableUser.ajax.reload(function () {
                                            removeArrow();
                                        });
                                    });
                                }, "application/json")

                            } else {//公司保存
                                var type = $('#type option:selected').val();
                                var name = $('#name').val();//公司简称
                                if (!name) {
                                    layer.msg("请填写公司简称", {time: 2000});
                                    return;
                                }
                                var abbreviation = $('#abbreviation').val();//全称
                                if (!abbreviation) {
                                    layer.msg("请填写公司名称", {time: 2000});
                                    return;
                                }
                                var phone = $('#phone').val();
                                if (!phone) {
                                    layer.msg("请填写公司电话", {time: 2000});
                                    return;
                                } else {
                                    var reg = /(^(\d{3,4}-)?\d{7,8})$|(13[0-9]{9})/;
                                    var tel = reg.test(phone);
                                    if (!tel) {
                                        layer.msg("公司电话格式不正确", {time: 2000});
                                        return;
                                    }
                                }
                                var domain = $('#domain').val();
                                if (!domain) {
                                    layer.msg("请填写公司域名", {time: 2000});
                                    return;
                                }
                                var status = $('#status option:selected').val();//状态
                                var companyMenu = $('#companyMenu option:selected').val();//等级
                                var pname = $('#pname').val();
                                var pid = $('#pid').val();
                                if (!companyMenu) {
                                    layer.msg("请选择等级", {time: 2000});
                                    return;
                                } else if (companyMenu == 1) {//子级部门
                                    if (!pname) {
                                        layer.msg("请选择父级部门", {time: 2000});
                                        return;
                                    }
                                }
                                var principal = $('#principal').val();//负责人
                                if (!principal) {
                                    layer.msg("请填写公司负责人", {time: 2000});
                                    return;
                                }
                                var companyType = $('#companyType option:selected').val();//公司类型
                                // if (!companyType) {
                                //     layer.msg("请选择公司类型",{time:2000});
                                //     return;
                                // }
                                var proid = $('#province option:selected').val();
                                var cityid = $('#city option:selected').val();
                                var distid = $('#district option:selected').val();
                                if (!proid || !cityid || !distid) {
                                    layer.msg("请选择公司地址", {time: 2000});
                                    return;
                                }
                                var address = $('#address').val();//详细地址
                                if (!address) {
                                    layer.msg("请填写详细地址", {time: 2000});
                                    return;
                                }
                                var comDescription = $('#comDescription').val();
                                var type = $.trim(type);
                                var name = $.trim(name);
                                var abbreviation = $.trim(abbreviation);
                                var phone = $.trim(phone);
                                var domain = $.trim(domain);
                                var status = $.trim(status);
                                var companyMenu = $.trim(companyMenu);
                                var pname = $.trim(pname);
                                var pid = $.trim(pid);
                                var principal = $.trim(principal);
                                var companyType = $.trim(companyType);
                                var proid = $.trim(proid);
                                var cityid = $.trim(cityid);
                                var distid = $.trim(distid);
                                var address = $.trim(address);
                                var comDescription = $.trim(comDescription);

                                var result = {};
                                result.type = type;
                                result.name = name;
                                result.abbreviation = abbreviation;
                                result.phone = phone;
                                result.domain = domain;
                                result.status = status;
                                result.grade = companyMenu;//公司等级
                                result.pname = pname;//父级部门name
                                result.pid = pid;//父级部门id

                                result.principal = principal;//负责人
                                result.companyType = companyType;//公司类型

                                result.provinceId = proid;
                                result.cityId = cityid;
                                result.districtId = distid;
                                result.address = address;
                                result.description = comDescription;

                                Comm.ajaxPost('sysDepartment/saveDepartment', JSON.stringify(result), function (data) {
                                    layer.closeAll();
                                    layer.msg(data.msg, {time: 2000}, function () {
                                        layer.closeAll();
                                        g_userManage.tableUser.ajax.reload(function () {
                                            removeArrow();
                                        });
                                    });
                                }, "application/json")
                            }
                            ;

                        }
                    });
                });

                //注册
                $("#register").click(function (e) {
                    //点击注册的时候首先查询所有公司
                    var param = {};
                    Comm.ajaxPost('sysDepartment/getAllGongsiName', JSON.stringify(param), function (data) {
                        //清空下拉选
                        $("#companyName").html('<option>-请选择-</option>');
                        var list = data.data;
                        var opt;
                        for (var i = 0; i < list.length; i++) {
                            opt = '<option value="' + list[i].id + '">' + list[i].companyName + '</option>';
                            $("#companyName").append(opt);
                        }

                    }, "application/json", '', '', '', false)
                    var target = e.target || window.event.target;
                    layer.open({
                        type: 1,
                        title: "录入企业信息",
                        maxmin: true,
                        shadeClose: true,
                        area: ['80%', '80%'],
                        offset: '75px',
                        shift: 5,
                        content: $('#companyinfo').show(),
                        btn: ['保存', '取消'],
                        success: function (layero, index) {
                            $("#deptType option[value='0']").attr("selected", "selected");
                            //清空值
                            /*  $("#companyUrl").find("input").attr("disabled",false);*/
                            $("#companyUrl").find("input").removeAttr("disabled");
                            // $('#areaSelect').removeAttr("disabled");
                            $("#gongsiImgType").attr("disabled", false);//认证方式
                            $("#gongsiImgType").val('');//认证方式
                            $('#email').val('');
                            $('#code').val('');
                            $('#companyName').val('');
                            $('#username').val('');
                            $('#card').val('');
                            $('#tel').val('');
                            $('#num').val('');
                            $('#activity_id').val('');
                            $('#activity_img_fileName').val('');
                            $('.addMaterial').attr('src', '../resources/images/photoadd.png');
                        },
                        yes: function (layero, index) {
                            var checkStatus =$("#checkStatus").val();
                            if(checkStatus  !=''){
                                if(checkStatus == '0'){
                                    layer.msg('企业申请认证审核中', {time: 2000}, function () {});
                                    return false;
                                }else if(checkStatus == '1'){
                                    layer.msg('企业申请认证已通过', {time: 2000}, function () {});
                                    return false;
                                }
                            }
                            var param = {};
                            param.email = $('#email').val();
                            param.companyName = $("#companyName").find("option:selected").text();
                            param.companyId = $('#companyName').val();
                            param.username = $('#username').val();
                            param.card = $('#card').val();
                            param.tel = $('#tel').val();
                            param.num = $('#num').val();
                            param.gongsiImgType = $("#gongsiImgType").val();
                            var imgListArray = new Array();
                            if (param.gongsiImgType == '1') {
                                imgListArray[0] = $("#houseotherpic0").children().eq(0).children().eq(0).val();
                                imgListArray[1] = $("#houseotherpic1").children().eq(0).children().eq(0).val();
                            } else if (param.gongsiImgType == '0') {
                                imgListArray[0] = $("#houseotherpic2").children().eq(0).children().eq(0).val();
                                imgListArray[1] = $("#houseotherpic3").children().eq(0).children().eq(0).val();
                                imgListArray[2] = $("#houseotherpic4").children().eq(0).children().eq(0).val();
                                imgListArray[3] = $("#houseotherpic5").children().eq(0).children().eq(0).val();
                                param.code = $('#code').val();
                            } else {
                                layer.msg("公司类型为必选项！", {time: 2000});
                                return
                            }
                            param.imgurls = imgListArray;
                            console.log(imgListArray);
                            if (param.email == '') {
                                layer.msg("企业邮箱不能为空！", {time: 2000});
                                return
                            }
                            if (param.companyName == '') {
                                layer.msg("公司名称不能为空！", {time: 2000});
                                return
                            }
                            if (param.username == '') {
                                layer.msg("法人姓名不能为空！", {time: 2000});
                                return
                            }

                            if (param.card == '') {
                                layer.msg("法人身份证号码不能为空！", {time: 2000});
                                return
                            }
                            if (param.tel == '') {
                                layer.msg("法人手机号码不能为空！", {time: 2000});
                                return
                            }
                            if (param.num == '') {
                                layer.msg("营业执照号不能为空！", {time: 2000});
                                return
                            }
                            if ((param.num).length < 15 || (param.num).length > 18) {
                                layer.msg("营业执照号在十五位到十八位之间", {time: 2000});
                                return
                            }
                            if (param.gongsiImgType == '1') {
                               for(var i=0;i<imgListArray.length;i++){
                                    if(!imgListArray[i]){
                                        layer.msg("图片上传必须为两张！", {time: 2000});
                                      return false;
                                    }
                               }
                            } else if (param.gongsiImgType == '0') {
                                for(var i=0;i<imgListArray.length;i++){
                                    if(!imgListArray[i]){
                                        layer.msg("图片上传必须为四张！", {time: 2000});
                                        return false;
                                    }
                                }
                            }
                            console.log(param);
                            Comm.ajaxPost('sysDepartment/register', JSON.stringify(param), function (data) {
                                    if (data.code == 0) {
                                        layer.msg(data.msg, {time: 2000}, function () {
                                            layer.closeAll();
                                            g_userManage.tableOrder.ajax.reload();
                                        })
                                    }else if(data.code == 1){
                                        layer.msg(data.msg, {time: 2000}, function () {});
                                    }
                                }, "application/json"
                            );
                        }
                    });
                });

                //修改按钮
                $("#magData").on("click", ".edit", function (e) {
                    var target = e.target || window.event.target;
                    layer.open({
                        type: 1,
                        title: "修改",
                        maxmin: true,
                        shadeClose: true,
                        area: '600px',
                        offset: '75px',
                        content: $('#organize').show(),
                        btn: ['保存', '取消'],
                        success: function () {
                            var type = $(target).parents("tr").children().eq(12).html();
                            var name = $(target).parents("tr").children().eq(0).text();
                            var grade = $(target).parents("tr").children().eq(18).html();//等级
                            var pname = $(target).parents("tr").children().eq(19).html();//父级名称
                            var pid = $(target).parents("tr").children().eq(8).html();//父级id
                            var description = $(target).parents("tr").children().eq(17).html();
                            var proid = $(target).parents("tr").children().eq(13).html();//省id
                            var cityid = $(target).parents("tr").children().eq(14).html();//市id
                            var distid = $(target).parents("tr").children().eq(15).html();//区id

                            if (type == 0) {
                                $('#deptPid').val(pid);//父级部门id
                                $('#deptPname').val(pname);//父级部门名称
                                //类型选择部门
                                var id = $(target).parents("tr").children().eq(7).html();
                                $('#deptId').val(id);
                                document.getElementById("dept").style.display = "block";
                                document.getElementById("company").style.display = "none";
                                $("#depttype option[value='0']").attr("selected", "selected");
                                $("#deptMenu option[value='" + grade + "']").attr("selected", "selected");
                                console.log(grade);
                                if (grade == 0) {
                                    $('#dept_menu').hide();
                                } else {
                                    $('#dept_menu').show();
                                }
                                $('#deptName').val(name);
                                $('#deptPname').val(pname);
                                $('#description').val(description);

                            } else {
                                $('#companyType').empty();
                                $('#pid').val(pid);//父级部门id
                                $('#pname').val(pname);//父级部门名称
                                var companyId = $(target).parents("tr").children().eq(7).html();
                                $('#companyId').val(companyId);
                                var result = {};
                                result.name = "公司类型";
                                Comm.ajaxPost('sysDepartment/getDetailList', JSON.stringify(result), function (data) {
                                    $('#companyType').append('<option value="">请选择</option>');
                                    for (var i = 0; i < data.data.length; i++) {
                                        var html = $('<option value="' + data.data[i].code + '">data.data[i].name</option>');

                                        $('#companyType').append('<option value="' + data.data[i].code + '">' + data.data[i].name + '</option>')
                                    }

                                }, "application/json", '', '', '', false)
                                //公司
                                setSelect(proid, 'province');
                                document.getElementById("dept").style.display = "none";
                                document.getElementById("company").style.display = "block";
                                $("#type option[value='1']").attr("selected", "selected");
                                var name = $(target).parents("tr").children().eq(0).text();//公司简称
                                $('#name').val(name);
                                var abbreviation = $(target).parents("tr").children().eq(1).html();//公司名称
                                $('#abbreviation').val(abbreviation);
                                var phone = $(target).parents("tr").children().eq(2).html();//电话
                                $('#phone').val(phone);
                                var domain = $(target).parents("tr").children().eq(9).html();//域名
                                $('#domain').val(domain);
                                var status = $(target).parents("tr").children().eq(10).html();//公司状态
                                $("#status option[value='" + status + "']").attr("selected", "selected");
                                $("#companyMenu option[value='" + grade + "']").attr("selected", "selected");//部门等级
                                if (grade == 1) {
                                    $('#company_menu').show();
                                } else {
                                    $('#company_menu').hide();
                                }

                                var principal = $(target).parents("tr").children().eq(3).html();//负责人
                                $('#principal').val(principal);
                                var companyType = $(target).parents("tr").children().eq(12).html();//公司类型
                                $("#companyType option[value='" + companyType + "']").attr("selected", "selected");

                                $('#province option[value="' + proid + '"]').attr("selected", "selected");
                                onSelectChange(proid, 'city', '1');
                                $('#city option[value="' + cityid + '"]').attr("selected", "selected");
                                onSelectChange(cityid, 'district', '1');
                                $('#district option[value="' + distid + '"]').attr("selected", "selected");
                                var address = $(target).parents("tr").children().eq(16).html();//详细地址
                                $('#address').val(address);
                                var comDescription = $(target).parents("tr").children().eq(17).html();//备注
                                $('#comDescription').val(comDescription);
                            }
                        },
                        yes: function (index, layero) {
                            if ($('#dept').css('display') == 'block') {//部门 修改

                                var type = $('#deptType option:selected').val();
                                if (!type) {
                                    layer.msg("请选择部门", {time: 2000});
                                    return;
                                }
                                var name = $('#deptName').val();
                                if (!name) {
                                    layer.msg("请填写部门名称", {time: 2000});
                                    return;
                                }
                                var grade = $('#deptMenu option:selected').val();
                                var deptPname = $('#deptPname').val();
                                var deptPid = $('#deptPid').val();
                                if (!grade) {
                                    layer.msg("请选择等级", {time: 2000});
                                    return;
                                } else if (grade == 1) {//子级部门
                                    if (!deptPname) {
                                        layer.msg("请选择父级部门", {time: 2000});
                                        return;
                                    }
                                }
                                var description = $('#description').val();
                                var deptId = $('#deptId').val();//父级部门id
                                var deptPname = $('#deptPname').val();//父级部门名称
                                var level = $(target).parents("tr").children().eq(20).html();//層級

                                var type = $.trim(type);
                                var name = $.trim(name);
                                var level = $.trim(level);
                                var deptPid = $.trim(deptPid);
                                var deptPname = $.trim(deptPname);
                                var description = $.trim(description);
                                var result = {};
                                console.log(result);
                                result.id = deptId;
                                result.pname = deptPname;

                                result.level = level;
                                result.type = type;
                                result.name = name;
                                result.pid = deptPid;
                                result.description = description;
                                console.log(result);
                                Comm.ajaxPost('sysDepartment/updateDepartment', JSON.stringify(result), function (data) {
                                    layer.msg(data.msg, {time: 2000}, function () {
                                        layer.closeAll();
                                        g_userManage.tableUser.ajax.reload(function () {
                                            removeArrow();
                                        });
                                    });
                                }, "application/json")

                            } else {// 公司修改
                                var type = $('#type option:selected').val();
                                var name = $('#name').val();//公司简称
                                if (!name) {
                                    layer.msg("请填写公司简称", {time: 2000});
                                    return;
                                }
                                var abbreviation = $('#abbreviation').val();//全称
                                if (!abbreviation) {
                                    layer.msg("请填写公司名称", {time: 2000});
                                    return;
                                }
                                var phone = $('#phone').val();
                                if (!phone) {
                                    layer.msg("请填写公司电话", {time: 2000});
                                    return;
                                } else {
                                    var reg = /(^(\d{3,4}-)?\d{7,8})$|(13[0-9]{9})/;
                                    var tel = reg.test(phone);
                                    if (!tel) {
                                        layer.msg("公司电话格式不正确", {time: 2000});
                                        return;
                                    }
                                }
                                var domain = $('#domain').val();
                                if (!domain) {
                                    layer.msg("请填写公司域名", {time: 2000});
                                    return;
                                }
                                var status = $('#status option:selected').val();//状态
                                var companyMenu = $('#companyMenu option:selected').val();//等级
                                var pname = $('#pname').val();
                                var pid = $('#pid').val();
                                if (!companyMenu) {
                                    layer.msg("请选择等级", {time: 2000});
                                    return;
                                } else if (companyMenu == 1) {//子级部门
                                    if (!pname) {
                                        layer.msg("请选择父级部门", {time: 2000});
                                        return;
                                    }
                                }
                                var principal = $('#principal').val();//负责人
                                if (!principal) {
                                    layer.msg("请填写公司负责人", {time: 2000});
                                    return;
                                }
                                var companyType = $('#companyType option:selected').val();//公司类型
                                // if (!companyType) {
                                //     layer.msg("请选择公司类型",{time:2000});
                                //     return;
                                // }
                                var proid = $('#province option:selected').val();
                                var cityid = $('#city option:selected').val();
                                var distid = $('#district option:selected').val();
                                if (!proid || !cityid || !distid) {
                                    layer.msg("请选择公司地址", {time: 2000});
                                    return;
                                }
                                var address = $('#address').val();//详细地址
                                if (!address) {
                                    layer.msg("请填写详细地址", {time: 2000});
                                    return;
                                }
                                var comDescription = $('#comDescription').val();
                                var id = $('#companyId').val();
                                var level = $(target).parents("tr").children().eq(20).html();//層級

                                var type = $.trim(type);
                                var name = $.trim(name);
                                var abbreviation = $.trim(abbreviation);
                                var phone = $.trim(phone);
                                var domain = $.trim(domain);
                                var status = $.trim(status);
                                var companyMenu = $.trim(companyMenu);
                                var pname = $.trim(pname);
                                var pid = $.trim(pid);
                                var principal = $.trim(principal);
                                var companyType = $.trim(companyType);
                                var proid = $.trim(proid);
                                var cityid = $.trim(cityid);
                                var distid = $.trim(distid);
                                var address = $.trim(address);
                                var comDescription = $.trim(comDescription);

                                var result = {};
                                result.id = id;
                                result.level = level;
                                result.type = type;
                                result.name = name;
                                result.abbreviation = abbreviation;
                                result.phone = phone;
                                result.domain = domain;
                                result.status = status;
                                result.grade = companyMenu;//公司等级
                                result.pname = pname;//父级部门name
                                result.pid = pid;//父级部门id

                                result.principal = principal;//负责人
                                result.companyType = companyType;//公司类型

                                result.provinceId = proid;
                                result.cityId = cityid;
                                result.districtId = distid;
                                result.address = address;
                                result.description = comDescription;

                                console.log(result);
                                Comm.ajaxPost('sysDepartment/updateDepartment', JSON.stringify(result), function (data) {
                                    layer.msg(data.msg, {time: 2000}, function () {
                                        layer.closeAll();
                                        g_userManage.tableUser.ajax.reload(function () {
                                            removeArrow();
                                        });
                                    });
                                }, "application/json")
                            }
                            ;
                        }
                    });
                });
            },
        },
        CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api()
});

function checkIsNotRegiest(value) {
    $("#checkStatus").val('');
    var map = {};
    map.company_name = value;
    Comm.ajaxPost('sysDepartment/findStatus', JSON.stringify(map), function (data) {
        if (data.data == null || data.data == '') {
                $("#img2").attr('src','../resources/images/photoadd.png');
                $('#img3').attr('src', '../resources/images/photoadd.png');
                $('#img4').attr('src', '../resources/images/photoadd.png');
                $('#img5').attr('src','../resources/images/photoadd.png');
                $("#img0").attr('src','../resources/images/photoadd.png');
                $('#img1').attr('src','../resources/images/photoadd.png');
            $("#companyUrl").find("input").attr("disabled", false);
            $("#gongsiImgType").attr("disabled", false);//认证方式
            $('#tu2').css({"display": "none"});
            $('#tu1').css({"display": "none"});
            $("#email").val('');//邮箱
            $("#username").val('');//法人名称
            $("#card").val('');//身份证
            $("#tel").val('');//手机号
            $("#num").val('');//营业执照
            $("#gongsiImgType").val('');//认证方式
            $("#code").val('');//组织代码
            layer.msg(data.msg, {time: 2000}, function () {
            });
            return false;
        }
        $("#checkStatus").val('');
        $("#checkStatus").val(data.data.status == null ? "":data.data.status);
        if (data.data.status == 0) { //审核中
            //先设置图片不能点击
            $("#companyUrl").find("input").attr("disabled", true);
            $("#gongsiImgType").attr("disabled", "disabled");//认证方式
            //弹窗表示已认证成功
            // layer.msg(data.msg,{time:2000});
            $("#email").val(data.data.email);//邮箱
            $("#username").val(data.data.username);//法人名称
            $("#card").val(data.data.card);//身份证
            $("#tel").val(data.data.tel);//手机号
            $("#num").val(data.data.num);//营业执照
            $("#gongsiImgType").val(data.data.imgtype);//认证方式
            if (data.data.imgtype == '0') {//传统认证
                $('#tu2').css({"display": "block"});
                $('#tu1').css({"display": "none"});
                $("#img2").attr('src', data.data.img0);
                $('#img3').attr('src', data.data.img1);
                $('#img4').attr('src', data.data.img2);
                $('#img5').attr('src', data.data.img3);
                $("#code1").css({"display": "block"});
                $("#code").val(data.data.code);//组织代码
            } else if (data.data.imgtype == '1') {//三证合一
                $('#tu1').css({"display": "block"});
                $('#tu2').css({"display": "none"});
                $("#img0").attr('src', data.data.img0);
                $('#img1').attr('src', data.data.img1);
                $("#code1").css({"display": "none"});
            }
            layer.msg('企业申请认证审核中', {time: 2000}, function () {

            });
        } else if (data.data.status == 1) { //审核通过
            $("#companyUrl").find("input").attr("disabled", true);
            $("#gongsiImgType").attr("disabled", "disabled");//认证方式
            //认证成功 设置弹框不可编辑
            // $("#email").attr("readonly","readonly");
            // $("#username").attr("readonly","readonly");//法人名称
            // $("#card").attr("readonly","readonly");//身份证
            // $("#tel").attr("readonly","readonly");//手机号
            // $("#num").attr("readonly","readonly");//营业执照
            // $("#gongsiImgType").attr("readonly","readonly");//认证方式
            //弹窗表示已认证成功
            // layer.msg(data.msg,{time:2000});
            $("#email").val(data.data.email);//邮箱
            $("#username").val(data.data.username);//法人名称
            $("#card").val(data.data.card);//身份证
            $("#tel").val(data.data.tel);//手机号
            $("#num").val(data.data.num);//营业执照
            $("#gongsiImgType").val(data.data.imgtype);//认证方式
            if (data.data.imgtype == '0') {  //传统认证
                $('#tu2').css({"display": "block"});
                $('#tu1').css({"display": "none"});
                $("#img2").attr('src', data.data.img0);
                $('#img3').attr('src', data.data.img1);
                $('#img4').attr('src', data.data.img2);
                $('#img5').attr('src', data.data.img3);
                $("#code1").css({"display": "block"});
                $("#code").val(data.data.code);//组织代码
            } else if (data.data.imgtype == '1') {//三证合一
                $('#tu1').css({"display": "block"});
                $('#tu2').css({"display": "none"});
                $("#img0").attr('src', data.data.img0);
                $('#img1').attr('src', data.data.img1);
                $("#code1").css({"display": "none"});
            }
            layer.msg('企业申请认证审核成功', {time: 2000}, function () {
            });
        } else {  //审核失败
            $("#companyUrl").find("input").attr("disabled", false);
            $("#email").val(data.data.email);//邮箱
            $("#username").val(data.data.username);//法人名称
            $("#card").val(data.data.card);//身份证
            $("#tel").val(data.data.tel);//手机号
            $("#num").val(data.data.num);//营业执照
            $("#code").val(data.data.code);//组织代码
            $("#gongsiImgType").val(data.data.imgtype);//认证方式
            if (data.data.imgtype == '0') {//传统认证
                $('#tu2').css({"display": "block"});
                $('#tu1').css({"display": "none"});
                $("#img2").attr('src', data.data.img0);
                $('#img3').attr('src', data.data.img1);
                $('#img4').attr('src', data.data.img2);
                $('#img5').attr('src', data.data.img3);
                $("#code1").css({"display": "block"});
                $("#code").val(data.data.code);//组织代码
            } else if (data.data.imgtype == '1') {//三证合一
                $('#tu1').css({"display": "block"});
                $('#tu2').css({"display": "none"});
                $("#img0").attr('src', data.data.img0);
                $('#img1').attr('src', data.data.img1);
                $("#code1").css({"display": "none"});
            }
            layer.msg('企业申请认证审核失败', {time: 2000}, function () {
            });
        }
    }, "application/json")

}
