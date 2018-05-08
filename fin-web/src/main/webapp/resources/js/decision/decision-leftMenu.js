$(".l_decision_options").on("click","ul li",function(){
	$(".l_decision_options li").removeClass("selected");
	$(this).addClass("selected");
});

