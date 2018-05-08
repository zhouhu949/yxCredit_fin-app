function getDepartmentZtree() {

    var setting = {
        view: {
            dblClickExpand: false,
            selectedMulti: false,
            txtSelectedEnable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };
    var zNodes = "";

    Comm.ajaxPost('sysDepartment/getDepartmentZtree','',function(result){
        console.log(result);
        page = result.data;
        zNodes = page;
        console.log(zNodes);
    },"application/json",'','','',false)
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("jstree");
        var nodes = zTree.getSelectedNodes(),
            v = "";
        nodes.sort(function compare(a, b) {
            return a.id - b.id
        });
        for (var i = 0,
                 l = nodes.length; i < l; i++) {
            v += nodes[i].name + ","
        }
        if (v.length > 0) {
            v = v.substring(0, v.length - 1)
        }
        var cityObj = null;
        cityObj = $("#citySel option");
        cityObj.html(v);
        cityObj.attr("value", v);
        hideMenu();
        return false
    }
    $("#citySel").on("keyup",
        function() {
            console.log($(this).val());
            var val = $(this).val();
            if (val == "") {
                $(this).val()
            }
        });
    $(document).ready(function() {
        $.fn.zTree.init($("#jstree"), setting, zNodes)
    })

}

function showMenu() {
    var A = $("#citySel");
    var B = $("#citySel").offset();
    $("#menuContent").css({
        left: B.left + "px",
        top: B.top + A.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDown)
}
function onBodyDown(A) {
    if (! (A.target.id == "menuBtn" || A.target.id == "menuContent" || $(A.target).parents("#menuContent").length > 0)) {
        hideMenu()
    }
}
function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown)
};