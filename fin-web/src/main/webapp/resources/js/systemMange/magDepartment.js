/**
 * Created by Administrator on 2017/6/12 0012.
 */



//修改弹框对象
var editLayer;


//用户表格列表
var aoColumns = [
    {
        "mData" : "realName"
    },
    {
        "mData" : "phone"
    }
];

//部门弹框tree
var setting = {
    data : {
        simpleData : {
            enable : true
        }
    },
    callback : {
        onClick : zTreeOnClick  //调用点击事件
    }
};


//部门点击事件
function zTreeOnClick(event, treeId, treeNode){
    //绑定部门id
    $("#pid").val(treeNode.id);
    $("#deptPid").val(treeNode.id);
    //绑定部门name
    $("#pname").val(treeNode.name);
    $("#deptPname").val(treeNode.name);
}






var department = {
    /**
     * ----------------------------------------
     * @模块描述 调用接口中查询省份城市信息
     * @作者 燕娜
     * @备注 将省份城市信息设置到缓存中，缓存中有值时不会重复调用
     * ----------------------------------------
     */
    /*loadTerritory : function(){
        var province = sessionStorage.getItem('provinceId');
        var provinceHtml = "";
        //当缓存中有值时，直接在缓存中取值，无需访问后台
        if(province == null){

            //操作成功提示
            if("1" == districts.responseCode){
                //获取到值存到js缓存中
                sessionStorage.setItem('provinceId',JSON.stringify(districts.province));
                sessionStorage.setItem('cityId',JSON.stringify(districts.city));
                sessionStorage.setItem('areaId',JSON.stringify(districts.district));
                provinceHtml = department.territoryHtml(districts.province);
            }

        }else{
            provinceHtml = department.territoryHtml(province);
        }
        $(".province").html(provinceHtml);
    },*/
    /**
     * ----------------------------------------
     * @模块描述 用户选择省份之后加载对应城市
     * @作者 燕娜
     * @备注 注意：省份带有mark属性，城市带有markName属性
     * ----------------------------------------
     */
    /*loadCity : function(){
        $(".province").change(function(){
            //获取当前select框标识
            var mark = $(this).attr("mark");
            //获取省份id值
            var provinceId = $(this).find("option:checked").attr("id");
            //需要获取html
            var cityHtml = department.cityHtml(provinceId);
            //设置下拉列表的内容
            $("select[markName="+mark+"City]").html(cityHtml);
        });
    },*/
    /**
     * ----------------------------------------
     * @模块描述 用户选择城市之后加载对应区域
     * @作者 燕娜
     * @备注 注意：城市带有mark属性，区域带有markName属性
     * ----------------------------------------
     */
    /*loadArea : function(){
        $(".city").change(function(){
            //获取当前select框标识
            var mark = $(this).attr("mark");
            //获取省份id值
            var cityId = $(this).find("option:checked").attr("id");
            //需要获取html
            var areaHtml = department.areaHtml(cityId);
            //设置下拉列表的内容
            $("select[markName="+mark+"Area]").html(areaHtml);
        });
    },*/
    /**
     * ----------------------------------------
     * @模块描述 获取省份下拉框内容
     * @作者 燕娜
     * @备注
     * ----------------------------------------
     */
    /*territoryHtml : function(){
        //从缓存中获取省份
        //获取缓存中的区域
        var province = sessionStorage.getItem('provinceId');
        //将json字符串转换成数组
        province = JSON.parse(province);
        //封装省份的下拉列表
        var provinceHtml = "<option value=''>请选择</option>";

        for(var i = 0; i < province.length; i++){
            provinceHtml += "<option value ='"+province[i].id+"' id = '"+province[i].id+"'>"+province[i].provinceName+"</option>";
        }
        return provinceHtml;
    },*/
    /**
     * ----------------------------------------
     * @模块描述 获取城市下拉框内容
     * @作者 燕娜
     * @备注
     * ----------------------------------------
     */
    /*cityHtml : function(provinceId){
        //获取缓存中的city
        var city = sessionStorage.getItem('cityId');
        //将json字符串转换成数组
        city = JSON.parse(city);
        var cityHtml = "<option value=''>请选择</option>";
        if(!common.isEmpty(city)){
            for ( var i = 0; i < city.length; i++) {
                if(city[i].provinceId == provinceId){
                    cityHtml += "<option value='"+city[i].id+"' id='"+city[i].id+"' >"+city[i].cityName+"</option>";
                }
            }
        }
        return cityHtml;
    },*/
    /**
     * ----------------------------------------
     * @模块描述 获取区域下拉框内容
     * @作者 燕娜
     * @备注
     * ----------------------------------------
     */
    /*areaHtml : function(cityId){

        //获取缓存中的区域
        var area = sessionStorage.getItem('areaId');
        //将json字符串转换为数组
        area = JSON.parse(area);
        var areaHtml = "<option value=''>请选择</option>";
        for(var i = 0; i < area.length;i++){
            if(area[i].cityId == cityId){
                areaHtml += "<option value ='"+area[i].id+"' id = '"+area[i].id+"'>"+area[i].districtName+"</option>";
            }
        }
        return areaHtml;
    },*/
    /**
     * ----------------------------------------
     * @模块描述 根据id查询部门信息
     * @作者 燕娜
     * @备注 绑定父目录名称
     * ----------------------------------------
     */
    /*findById : function(id,obj){
        //如果id为就去绑定空的值
        if(id == null || id == ""){
            $("#"+obj+"").val("");
        }else{
            //id不为空根据部门id查询该部门对象
            $.post("sys/dept/findAllByMap.html",{"id":id},function(data){
                if(data.responseCode == 1){
                    $("#"+obj+"").val(data.data[0].name);
                }else{
                    //弹框提示
                    layer.alert(data.info,{icon: 2, title:'操作提示'});
                }
            },"json");
        }
    },*/
    /**
     * ----------------------------------------
     * @模块描述 根据员工id查询员工对象
     * @作者 燕娜
     * @备注
     * ----------------------------------------
     */
    /*findEmpById : function(id,obj){
        //如果id等于空就绑定空的数据
        if(id == "" || id == null){
            $("#"+obj+"").val("");
        }else{
            //id不为空根据员工id查询员工对象
            $.post("sys/dept/getEmployeesById.html",{"id":id},function(data){
                if(data.status == 1){
                    $("#"+obj+"").val(data.data.realName);
                }else{
                    //弹框提示
                    layer.alert(data.info,{icon: 2, title:'操作提示'});
                }
            },"json");
        }

    },*/
    /**
     * ----------------------------------------
     * @模块描述 点击部门文本框展示部门ztree
     * @作者 燕娜
     * @备注
     * ----------------------------------------
     */
    showDeptZtree: function(){
        //打开部门ztree弹框
        layer.open({
            title:"<s class='preLine'>部门</s>",
            type: 1,
            maxWidth:'auto',
            area:['450px','600px'],
            skin: 'layer_normal',
            shift: 5,
            btn:['确定'],
            shadeClose:false,
            content: $("#deptTree"),
            success: function(layero, index){
                $.post("sys/dept/getDepartments.html",function(data){
                    $.fn.zTree.init($("#treeDemos"), setting, data);
                },"json");
            }
        });
    },
    /**
     * ----------------------------------------
     * @模块描述 部门等级改变事件
     * @作者 燕娜
     * @备注 obj：部门等级下拉列表id,tobj：父目录选择行id,id:隐藏父目录id,name:隐藏父目录名称id
     * ----------------------------------------
     */
    menuChange : function(obj,tobj,id,name){
        var newValue = $("#"+obj+"").val();
        //判断选择菜单等级的改变事件
        if(newValue == 1){
            //子菜单
            $("#"+id+"").val("");
            $("#"+name+"").val("");
            $("#"+tobj+"").show();
        }else{
            $("#"+id+"").val("0");
            $("#"+tobj+"").hide();
        }
    },

    /**
     * ----------------------------------------
     * @模块描述 刷新部门ztree
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    refreshZtree : function(){
        $.ajax({
            url: "sys/dept/getDepartments.html",
            type: 'post',      //POST方式发送数据
            async: false,      //ajax同步
            data:{"type":"1"},
            success: function(result) {
                var json = eval('(' + result + ')');
                //展示TreeGrid
                department.dataTreeGrid(json);
                //根据id设置某个节点展开
                $("#treeGrid").jqxTreeGrid('expandRow', '1');
            }
        });
    },
    /**
     * ----------------------------------------
     * @模块描述 历史组织架构
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    historyZtree : function(){
        $.ajax({
            url: "sys/dept/getDepartments.html",
            type: 'post',      //POST方式发送数据
            async: false,      //ajax同步
            data:{"type":"2"},
            success: function(data) {
                //展示TreeGrid
                department.dataTreeGrid(data);
                //根据id设置某个节点展开
                $("#treeGrid").jqxTreeGrid('expandRow', '1');
                //展示历史组织架构隐藏操作
                $("#treeGrid").jqxTreeGrid('hideColumn', '操作');
                //展示历史组织架构隐藏排序
                //$("#treeGrid").jqxTreeGrid('hideColumn', 'sequence');
                //改变历史组织架构长度
                $('#treeGrid').jqxTreeGrid({width:"1300px"});
            }
        });
    },
    /**
     * ----------------------------------------
     * @模块描述 部门类型改变事件
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    /*typeChange : function(obj){
        var type = $("#"+obj).val();
        var deptPid = $("#deptPid").val();
        var deptPname = $("#deptPname").val();
        var pid = $("#pid").val();
        var pname = $("#pname").val();
        var deptName = $("#deptName").val();
        var name = $("#abbreviation").val();
        var domain = $("#domain").val();
        var companyMenu = $("#companyMenu").val();
        var deptMenu = $("#deptMenu").val();
        var description = $("#description").val();
        var comDescription = $("#comDescription").val();
        var companyId = $("#companyId").val();
        var deptId = $("#deptId").val();
        var deptNumber = $("#deptNumber").val();
        var deptSequence = $("#deptSequence").val();
        var companyNumber = $("#companyNumber").val();
        var sequence = $("#sequence").val();
        //清空值
        department.clearValue();

        //部门类型等于1就显示公司否则显示部门
        if("1" == type){
            document.getElementById("dept").style.display = "none";
            document.getElementById("company").style.display = "block";
            $("#"+obj+" option[value='1']").attr("selected", "selected");
        }else{
            document.getElementById("dept").style.display = "block";
            document.getElementById("company").style.display = "none";
            $("#"+obj+" option[value='0']").attr("selected", "selected");
        }
        //绑定值
        $("#abbreviation").val(deptName);
        $("#domain").val(domain);
        $("#deptName").val(name);
        $("#deptPid").val(pid);
        $("#deptPname").val(pname);
        $("#pid").val(deptPid);
        $("#pname").val(deptPname);
        $("#companyId").val(deptId);
        $("#deptId").val(companyId);
        $("#description").val(comDescription);
        $("#comDescription").val(description);
        $("#companyNumber").val(deptNumber);
        $("#sequence").val(deptSequence);
        $("#deptNumber").val(companyNumber);
        $("#deptSequence").val(sequence);
        $("#deptMenu option[value='"+companyMenu+"']").attr("selected", "selected");
        $("#companyMenu option[value='"+deptMenu+"']").attr("selected", "selected");
        //如果选择子级部门展示选择父级部门文本框
        if(companyMenu == 1 || deptMenu == 1){
            $("#dept_menu").show();
            $("#company_menu").show();
        }
    },*/
    /**
     * ----------------------------------------
     * @模块描述 删除部门
     * @作者 燕娜
     * @备注 修改状态
     * ----------------------------------------
     */
    /*toDelDept : function(rowId){
        if(rowId == ""){
            layer.alert("参数不正确！",{icon: 2, title:'操作提示'});
        }else{
            //查询该部门下是否有子部门
            $.post("sys/dept/findAllByMap.html",{"pid":rowId},function(data){
                //如果查询又异常直接返回
                if(data.responseCode == 0){
                    //弹框提示
                    layer.alert(data.info,{icon: 2, title:'操作提示'});
                    return;
                }

                //如果该部门下有子部门给出相应的提示
                if(data.data.length > 0){
                    layer.confirm('您确定删除该部门以及该部门下的所有部门？', {icon: 3, title:'操作提示'}, function(index){
                        if(index != null || index != ""){
                            department.delDept(rowId);
                        }
                        layer.close(index);
                    });

                }else{
                    layer.confirm('您确定删除该部门？', {icon: 3, title:'操作提示'}, function(index){
                        if(index != null || index != ""){
                            department.delDept(rowId);
                        }
                        layer.close(index);
                    });

                }
            },"json");
        }
    },*/
    /**
     * ----------------------------------------
     * @模块描述 删除部门
     * @作者 燕娜
     * @备注 修改状态
     * ----------------------------------------
     */
    /*delDept : function(id){
        //去后台操作删除
        $.post("sys/dept/delDeptByIdOrPid.html",{"deptId":id},function(data){
            //成功后返回
            if(data.responseCode == 1){
                //清空值
                department.clearValue();
                //获取历史组织架构的样式
                var value = $("#hisDept").attr("class");
                if(value == "tri"){
                    //刷新数据
                    department.refreshZtree();
                }else{
                    department.historyZtree();
                }

                //操作提示
                layer.alert(data.info,{icon: 1, title:'操作提示'});
            }else{
                //操作提示
                layer.alert(data.info,{icon: 2, title:'操作提示'});
            }
        },"json");
    },*/
    /**
     * ----------------------------------------
     * @模块描述 清空所有值
     * @作者 燕娜
     * @备注 修改状态
     * ----------------------------------------
     */
    /*clearValue : function(){
        $("#dept_menu").hide();
        $("#company_menu").hide();
        $("#deptId").val("");
        $("#companyId").val("");
        $("#pid").val("");
        $("#pname").val("");
        $("#name").val("");
        $("#deptName").val("");
        $("#deptPid").val("");
        $("#deptPname").val("");
        $("#phone").val("");
        $("#address").val("");
        $("#principal").val("");
        $("#description").val("");
        $("#abbreviation").val("");
        $("#domain").val("");
        $("#deptNumber").val("");
        $("#companyNumber").val("");
        $("#comDescription").va;("");
        /!*$("#status").attr("");
         $("#deptStatus").attr("");*!/
        $("#sequence").val("");
        $("#deptSequence").val("");
        $("#deptMenu option[value='']").attr("selected", "selected");
        $("#companyMenu option[value='']").attr("selected", "selected");
        $("#type option[value='1']").attr("selected", "selected");
        $("#deptType option[value='0']").attr("selected", "selected");
        $("#companyType option[value='']").attr("selected", "selected");
        $("#provinceId option[value='']").attr("selected", "selected");
        $("#districtId").html("<option value=''>请选择</option>");
        $("#cityId").html("<option value=''>请选择</option>");
        document.getElementById("type").removeAttribute("disabled");
        document.getElementById("deptType").removeAttribute("disabled");

    },*/
    /**
     * ----------------------------------------
     * @模块描述 查询数据字典
     * @作者 燕娜
     * @备注 修改状态
     * ----------------------------------------
     */
    // getDictionary : function(){
    //     $.post("sys/dept/getDictionary.html",function(data){
    //         //成功后返回
    //         if(data.responseCode == 1){
    //             $.each(data.data,function(i,n){
    //                 //公司类型
    //                 var option = $("<option >"+n.dicName+"</option>");
    //                 option.val(n.dicValue);
    //                 $("#companyType").append(option);
    //             });
    //         }else{
    //             //操作提示
    //             layer.alert(data.info,{icon: 2, title:'操作提示'});
    //         }
    //     },"json");
    // },
    /**
     * ----------------------------------------
     * @模块描述 添加部门、公告
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    /*add_Dept_Form:function add_notice_From (){
        var msgTip;
        //部门表单提交
        $("#add_dept_form").Validform({
            tiptype:function(msg,o,cssctl){
                if(!o.obj.is("form")){
                    if(o.type == 3){					//type  1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态,
                        layer.tips(msg, o.obj);
                    }
                }
            },
            btnSubmit:"#deptBtn",						//绑定点击提交表单事件的按钮
            postonce:true, 								//在数据成功提交后，表单将不能再继续提交。
            ajaxPost:true,
            beforeSubmit:function(curform){
                document.getElementById("deptType").removeAttribute("disabled");
                var pid = $("#deptPid").val();
                if(pid == null || pid == ""){
                    layer.tips("请选择父级部门！", "#deptPname");
                    return false;
                }
                var id = $("#deptId").val();
                if(id == pid){
                    layer.tips("请选择其他部门！", "#deptPname");
                    return false;
                }
                msgTip = common.loadMsg("正在提交申请,请稍后");
            },
            callback:function(data){				//执行成功
                if(data.responseCode == "1"){			//返回状态为1,操作成功
                    //弹框提示
                    layer.alert(data.info,{icon: 1, title:'操作提示'});
                    layer.close(msgTip);
                    //获取历史组织架构的样式
                    var value = $("#hisDept").attr("class");
                    if(value == "tri"){
                        //刷新数据
                        department.refreshZtree();
                    }else{
                        department.historyZtree();
                    }
                    //清空值
                    department.clearValue();
                    //关闭修改弹框
                    layer.close(editLayer);
                }else{
                    layer.close(msgTip);
                    layer.alert(data.info,{icon: 2, title:'操作提示'});
                }
            }
        });

        //公司表单提交
        $("#add_company_form").Validform({
            tiptype:function(msg,o,cssctl){
                if(!o.obj.is("form")){
                    if(o.type == 3){					//type  1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态,
                        layer.tips(msg, o.obj);
                    }
                }
            },
            btnSubmit:"#companyBtn",						//绑定点击提交表单事件的按钮
            postonce:true, 								//在数据成功提交后，表单将不能再继续提交。
            ajaxPost:true,
            beforeSubmit:function(curform){
                document.getElementById("type").removeAttribute("disabled");
                var pid = $("#pid").val();
                if(pid == null || pid == ""){
                    layer.tips("请选择父级部门！", "#pname");
                    return false;
                }
                var id = $("#companyId").val();
                if(id == pid){
                    layer.tips("请选择其他部门！", "#deptPname");
                    return false;
                }
                msgTip = common.loadMsg("正在提交申请,请稍后");
            },
            callback:function(data){				//执行成功
                if(data.responseCode == "1"){			//返回状态为1,操作成功
                    //弹框提示
                    layer.alert(data.info,{icon: 1, title:'操作提示'});
                    layer.close(msgTip);
                    //获取历史组织架构的样式
                    var value = $("#hisDept").attr("class");
                    if(value == "tri"){
                        //刷新数据
                        department.refreshZtree();
                    }else{
                        department.historyZtree();
                    }
                    //清空值
                    department.clearValue();
                    //关闭修改弹框
                    layer.close(editLayer);
                }else{
                    layer.close(msgTip);
                    layer.alert(data.info,{icon: 2, title:'操作提示'});
                }
            }
        });
    },*/
    /**
     * ----------------------------------------
     * @模块描述 查询用户集合参数
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    /*retrieveData : function(sSource, aoData, fnCallback){
        var deptId = $("#pid").val();
        var data={
            "aoData":JSON.stringify(aoData),
            "deptId":deptId
        };
        common.loadAjax(sSource,data,fnCallback);
    },*/
    /**
     * ----------------------------------------
     * @模块描述 根据部门id查询员工用户
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    /*getEmployeesByDept : function(){
        var msgTip;
        //获取部门id
        var deptId = $("#pid").val();
        var companyMenu = $('#companyMenu').val();
        //如果部门id为空给出提示
        if(companyMenu == null || companyMenu == ""){
            layer.tips("请选择部门等级！", "#companyMenu");
            return false;
        }else{
            if(deptId == null || deptId == ""){
                layer.tips("请选择父级部门！", "#pname");
                return false;
            }else{
                msgTip = common.loadMsg("正在加载,请稍后");
                //打开部门ztree弹框
                layer.open({
                    title:"<s class='preLine'>用户</s>",
                    type: 1,
                    maxWidth:'auto',
                    area:['700px','650px'],
                    skin: 'layer_normal',
                    shift: 5,
                    btn:['确定'],
                    shadeClose:false,
                    content: $("#empDiv"),
                    success: function(layero, index){
                        layer.close(msgTip);
                        common.loadTable('empTable', common.getRootPath() + '/sys/dept/getEmployeesByDept.html', department.retrieveData, aoColumns, true, 12);
                    }
                });

            }
        }


    },*/
    /**
     * ----------------------------------------
     * @模块描述 修改部门
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    /*editDept : function(rowId){
        if(rowId == ""){
            layer.alert("参数不正确！",{icon: 2, title:'操作提示'});
        }else{
            //打开部门弹框
            editLayer = layer.open({
                title:"<s class='preLine'>修改</s>",
                type: 1,
                area: '600px',
                skin: 'layer_normal',
                shift: 5,
                btn:'',
                shadeClose:false,
                content: $("#organize"),
                success: function(layero, index){
                    //根据部门id查询该部门对象
                    $.post("sys/dept/findAllByMap.html",{"id":rowId},function(data){
                        if(data.responseCode == 1){
                            //清空值
                            department.clearValue();
                            //如果类型等于0显示部门
                            if(data.data[0].type == 0){
                                //$(".deptTitle").css("display","block");
                                //$(".companyTitle").css("display","none");
                                document.getElementById("dept").style.display = "block";
                                document.getElementById("company").style.display = "none";
                                //绑定部门数据
                                $("#deptId").val(data.data[0].id);
                                $("#deptPid").val(data.data[0].pid);
                                $("#deptName").val(data.data[0].name);
                                $("#deptNumber").val(data.data[0].number);
                                $("#description").val(data.data[0].description);
                                $("#deptStatus").val(data.data[0].status);
                                $("#deptSequence").val(data.data[0].sequence);
                                $("#deptType option[value='"+data.data[0].type+"']").attr("selected", "selected");

                                //展示部门状态
                                document.getElementById("deptStatusTr").style.display = "table-row";

                                //判断当前节点是父节点还是子节点
                                if(data.data[0].pid =="0"  ){
                                    $("#deptMenu option[value='"+data.data[0].pid+"']").attr("selected", "selected");
                                    $("#dept_menu").hide();
                                }else{
                                    //子菜单
                                    $("#deptMenu option[value='1']").attr("selected", "selected");
                                    $("#dept_menu").show();
                                    //绑定父级目录名称
                                    department.findById(data.data[0].pid,'deptPname');
                                }

                            }else{
                                //如果类型等于1显示公司
                                document.getElementById("dept").style.display = "none";
                                document.getElementById("company").style.display = "block";
                                //绑定公司数据
                                $("#companyId").val(data.data[0].id);
                                $("#name").val(data.data[0].name);
                                $("#pid").val(data.data[0].pid);
                                $("#phone").val(data.data[0].phone);
                                $("#status").val(data.data[0].status);
                                $("#companyNumber").val(data.data[0].number);
                                $("#address").val(data.data[0].address);
                                $("#principal").val(data.data[0].principal);
                                $("#abbreviation").val(data.data[0].abbreviation);
                                $("#domain").val(data.data[0].domain);
                                $("#comDescription").val(data.data[0].description);
                                $("#sequence").val(data.data[0].sequence);
                                $("#type option[value='"+data.data[0].type+"']").attr("selected", "selected");
                                $("#companyType option[value='"+data.data[0].companyType+"']").attr("selected", "selected");
                                $("#provinceId option[value='"+data.data[0].provinceId+"']").attr("selected", "selected");
                                $("#cityId").html(department.cityHtml(data.data[0].provinceId));
                                $("#districtId").html(department.areaHtml(data.data[0].cityId));
                                $("#cityId option[value='"+data.data[0].cityId+"']").attr("selected", "selected");
                                $("#districtId option[value='"+data.data[0].districtId+"']").attr("selected", "selected");

                                //展示部门状态
                                document.getElementById("cmpStatusTr").style.display = "table-row";

                                //判断当前节点是父节点还是子节点
                                if(data.data[0].pid =="0"  ){
                                    $("#companyMenu option[value='"+data.data[0].pid+"']").attr("selected", "selected");
                                    $("#company_menu").hide();
                                }else{
                                    //子菜单
                                    $("#companyMenu option[value='1']").attr("selected", "selected");
                                    $("#company_menu").show();
                                    //绑定父级目录名称
                                    department.findById(data.data[0].pid,'pname');
                                }

                            }
                            /!*$("#deptType").attr("disabled", "disabled");
                             $("#type").attr("disabled", "disabled");	*!/
                        }else{
                            //弹框提示
                            layer.alert(data.info,{icon: 2, title:'操作提示'});
                        }
                    },"json");
                }
            });
        }
    },*/
    /**
     * ----------------------------------------
     * @模块描述 添加部门
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    /*addDept : function(rowId){
        if(rowId == ""){
            layer.alert("参数不正确！",{icon: 2, title:'操作提示'});
        }else{
            //打开部门弹框
            editLayer = layer.open({
                title:"<s class='preLine'>添加</s>",
                type: 1,
                area: '600px',
                skin: 'layer_normal',
                shift: 5,
                btn:'',
                shadeClose:false,
                content: $("#organize").show(),

                success: function(layero, index){
                    //清空值
                    department.clearValue();
                    //子菜单
                    $("#deptMenu option[value='1']").attr("selected", "selected");
                    $("#dept_menu").show();
                    $("#deptPid").val(rowId);
                    //绑定父级目录名称
                    department.findById(rowId,'deptPname');
                    //默认添加部门
                    document.getElementById("dept").style.display = "block";
                    document.getElementById("company").style.display = "none";
                    //隐藏部门状态
                    document.getElementById("deptStatusTr").style.display = "none";
                    //隐藏部门状态
                    document.getElementById("cmpStatusTr").style.display = "none";
                    //$(".deptTitle").css("display","block");
                    //$(".companyTitle").css("display","none");
                }
            });
        }
    },*/
    /**
     * ----------------------------------------
     * @模块描述 排序点击事件
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    sortClick : function(row,value){
        //绑定序列值
        $("#sequences").val(value);
        //打开序列弹框
        var sequenceLayer = layer.open({
            title:"<s class='preLine'>序列</s>",
            type: 1,
            maxWidth:'auto',
            area:['300px','200px'],
            skin: 'layer_normal',
            shift: 5,
            btn:['确定'],
            shadeClose:false,
            content: $("#sequenceDiv"),
            //btn1的事件（提交）
            yes: function(index, layerqw){
                //获取修改后的值
                var newValue = $("#sequences").val();

                //如果值为空给出提示
                if(newValue == "" || newValue == null){
                    layer.tips("请输入序列号！", "#sequences");
                    return false;
                }

                //如果值不是数字直接返回
                if(isNaN(newValue)){
                    layer.tips("只能输入数字！", "#sequences");
                    return false;
                }

                //如果值不相等进入后台排序
                if(value != newValue){
                    //修改部门排序
                    $.post("sys/dept/sortDept.html",{"deptId":row,"value":newValue},function(data){
                        //刷新数据
                        department.refreshZtree();
                    },"json");
                }
                //关闭序列弹框
                layer.close(sequenceLayer);
            },
        });
    },
    /**
     * ----------------------------------------
     * @模块描述 列表展示地址
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    getAddress : function(provinceId,cityId){
        var address = "";
        var province = sessionStorage.getItem('provinceId');
        var provinceHtml = "";
        //当缓存中有值时，直接在缓存中取值，无需访问后台
        if(province == null){
            var rootPath = common.getRootPath();
            $.ajax({
                url: rootPath+"/sys/district/getDistrict.html",
                type: 'post',      //POST方式发送数据
                async: false,      //ajax同步
                success: function(result) {
                    var json = eval('(' + result + ')');
                    //操作成功提示
                    if("1" == json.responseCode){
                        //获取到值存到js缓存中
                        province = JSON.stringify(json.province);
                        sessionStorage.setItem('provinceId',JSON.stringify(json.province));
                        sessionStorage.setItem('cityId',JSON.stringify(json.city));
                        sessionStorage.setItem('areaId',JSON.stringify(json.district));

                    }
                }
            });

        }

        if(provinceId != "" ){
            //将json字符串转换成数组
            province = JSON.parse(province);
            for(var i = 0; i < province.length; i++){
                if(province[i].id == provinceId){
                    address += province[i].provinceName;
                }
            }
        }

        if(cityId != ""){
            //获取缓存中的city
            var city = sessionStorage.getItem('cityId');
            //将json字符串转换成数组
            city = JSON.parse(city);
            for(var i = 0; i < city.length; i++){
                if(city[i].id == cityId){
                    address += city[i].cityName;
                }
            }
        }

        return address;
    },
    /**
     * ----------------------------------------
     * @模块描述 展示TreeGrid
     * @作者 燕娜
     * @备注 无
     * ----------------------------------------
     */
    dataTreeGrid : function(data){
        //停用状态字体为红色
        var cellsRendererFunction = function (row, dataField, cellValue, rowData, cellText) {
            if("0" == rowData.status){
                return "<span style='color: #e91b1b;'>" + cellText + "</span>";
            }
        };
        //数据对象
        var source =
        {
            dataType: "json",						//json格式
            //数据对象类型
            dataFields: [
                { name: 'id', type: 'string' },
                { name: 'name', type: 'string' },
                { name: 'pId', type: 'string' },
                { name: 'phone', type: 'string' },
                { name: 'principal', type: 'string' },
                { name: 'cityId', type: 'string' },
                { name: 'provinceId', type: 'string'},
                { name: 'status', type: 'int' },
                { name: 'sequence', type: 'string' },
                { name: 'abbreviation', type: 'string' }
            ],
            hierarchy:
            {
                keyDataField: { name: 'id' },			//id
                parentDataField: { name: 'pId' }		//父节点id
            },
            id: 'id',
            localData: data							//json数据
        };
        var dataAdapter = new $.jqx.dataAdapter(source);//数据适配器对象
        // create Tree Grid
        $("#treeGrid").jqxTreeGrid(
            {
                width: 1700,			//表格宽度
                source: dataAdapter,	//表格数据
                sortable: false,		//是否排序
                //disabled: true,
                columnsHeight:40,
                ready: function()
                {
                    //根据id设置某个节点展开
                    setTimeout(function(){
                        $("#treeGrid").jqxTreeGrid('expandRow', '1');
                    },100);

                },
                columns: [
                    //显示列，对应返回数据的列
                    { text: '名称', dataField: 'name',cellsRenderer: cellsRendererFunction,width:300 },
                    //显示列，对应返回数据的列,居中
                    { text: '公司全称',dataField: 'abbreviation',cellsAlign: 'center', align: "center",width:300,
                        //方法
                        cellsRenderer: function (rowKey, column, cellValue, rowData, cellText) {
                            if(cellText == ""){
                                return "--";
                            }
                            return cellText;
                        }
                    },
                    { text: '电话',dataField: 'phone',cellsAlign: 'center', align: "center",width:150,
                        //方法
                        cellsRenderer: function (rowKey, column, cellValue, rowData, cellText) {
                            if(cellText == ""){
                                return "--";
                            }
                            return cellText;
                        }
                    },
                    { text: '负责人',dataField: 'principal',cellsAlign: 'center', align: "center",width:150,
                        //方法
                        cellsRenderer: function (rowKey, column, cellValue, rowData, cellText) {
                            if(cellText == ""){
                                return "--";
                            }
                            return cellText;
                        }
                    },
                    { text: '地址',dataField: 'provinceId',cellsAlign: 'center', align: "center",width:300,
                        //方法
                        cellsRenderer: function (rowKey, column, cellValue, rowData, cellText) {
                            if(cellText == ""){
                                return "--";
                            }else{
                                return department.getAddress(cellText,rowData.cityId);
                            }
                        }
                    },
                    { text: '序列', dataField: 'sequence',cellsAlign: 'center',align: "center",width:100 },
                    {
                        text: '操作', dataField: '操作', cellsAlign: 'center', align: "center", width:400,
                        cellsRenderer: function (row, column, cellValue, rowData, cellText) {
                            return "<toolBar:permission   privilege='archivesAudCheck'>"+
                                "<a href='javascript:void(0)' onclick=\"department.sortClick('"+row+"','"+rowData.sequence+"')\">更新序列</a>&nbsp&nbsp&nbsp&nbsp" +
                                "<a href='javascript:void(0)' onclick=\"department.addDept('"+row+"')\">添加</a>&nbsp&nbsp&nbsp&nbsp" +
                                "<a href='javascript:void(0)' onclick=\"department.editDept('"+row+"')\">修改</a>&nbsp&nbsp&nbsp&nbsp" +
                                /*"<a href='javascript:void(0)' onclick=\"department.toDelDept('"+row+"')\">停用</a>&nbsp&nbsp&nbsp&nbsp" + */
                                "</toolBar:permission>";
                        }
                    }
                ]
            });
    }
};




$(function(){
    /*department.loadTerritory();
    department.loadCity();
    department.loadArea();*/
    //展示TreeGrid
    /**/department.dataTreeGrid(dataObj);
    //加载省市区域信息
    //公司类型
   /* department.getDictionary();
    //表单验证
    department.add_Dept_Form();*/

});










