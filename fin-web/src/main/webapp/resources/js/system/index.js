$(function () {
    var windowHeight = $(window).height();
    $("#main-container").height(windowHeight - 76);
    $("#iframe").height(windowHeight - 140);
    $(".sidebar").height(windowHeight - 99);
    var navHeight = $("#nav_list").height(windowHeight - 130);
    $("#nav_list").children(".submenu").css("height", navHeight);

    $(window).resize(function () {
        var windowHeight = $(window).height();
        $("#main-container").height(windowHeight - 76);
        $("#iframe").height(windowHeight - 140);
        $(".sidebar").height(windowHeight - 99);
        var navHeight = $("#nav_list").height(windowHeight - 130);
        $("#nav_list").children(".submenu").css("height", navHeight);
    });
    $(document).on("click", '.iframeurl', function () {
        var cid = $(this).attr("name");
        var cname = $(this).attr("title");
        $(".submenu,#nav_list").find(".active").removeClass("active");
        $(this).parent().addClass("active");
        $("#iframe").attr("src", cid).ready();
        $("#Bcrumbs").attr("href", cid).ready();
        $(".Current_page a").attr('href', cid).ready();
        $(".Current_page").html(cname).ready();
        $("#parentIframe").html("").css("display", "none").ready();
    });
    $('#nav_list').find('li.home').click(function () {
        $('#nav_list').find('li.home').removeClass('active');
        $(this).addClass('active');
    });

    var ua=navigator.userAgent.toLowerCase();
    if(/iphone|ipad|ipod/.test(ua)) {
        $("#sidebar").show();
        $(".main-content").css("margin-left","190px");
    }else if(/android/.test(ua)){
        $("#sidebar").show();
        $(".main-content").css("margin-left","190px");
    }


});


