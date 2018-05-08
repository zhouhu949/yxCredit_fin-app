
$(document).on("click",".l_checkbox",function(){
	if($(this).hasClass("l_back")==false){
		$(this).removeClass("l_back");
	}else{
		$(this).addClass("l_back");
	}		
});
/*$(document).on("click",".l_checkbox",function(){
 if($(this).hasClass("l_back")==false){
 $(this).addClass("l_back");
 }else{
 $(this).removeClass("l_back");
 }
 });*/

$(".l_display_first span").on("click", function() {
	if($(this).hasClass("l_back")==false) {
		$(".l_checkboxTwo").addClass("l_back");

	} else if($(this).hasClass("l_back")) {
		$(".l_checkboxTwo").removeClass("l_back");

	}
});
$(".l_display_create span").on("click", function() {
	/*if($(this).hasClass("l_back") == false) {
	 $(this).addClass("l_back");
	 }else{
	 $(this).removeClass("l_back");
	 }
	 if($(".l_back").length == (--$(".l_checkboxTwo").length)) {
	 $(".l_display_first span").removeClass("l_back");
	 }
	 else {
	 $(".l_display_first span").addClass("l_back");
	 }*/
	var createLen=$(".l_display_create").find(".l_checkboxTwo").length;
	var lenOne=$(".l_bottom_display").find(".l_back").length;
	if($(this).hasClass("l_back") == false) {
		$(this).addClass("l_back");

	}else{
		$(this).removeClass("l_back");
		var lenOne=$(".l_bottom_display").find(".l_back").length;
	}

	if(--createLen==lenOne){
		$(".l_display_first span").addClass("l_back");
	}
	else if(createLen!=lenOne){
		$(".l_display_first span").removeClass("l_back");
	}
});


$(".c-field-choose").find(".l_checkbox").unbind('click').on('click',function() {
	if($(this).hasClass("l_back")==false) {
		$(this).addClass("l_back");
	} else if($(this).hasClass("l_back")) {
		$(this).removeClass("l_back");
	}
});
