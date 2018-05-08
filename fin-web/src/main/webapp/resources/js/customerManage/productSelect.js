//页面初始化
$(function () {
    getProductSeries('start');

    $("#provcd").on('change',function(){
        var htmlSel=$("#provcd option:selected").html();
        if(htmlSel=="--请选择--"){
            $("#productName").html("<option value=''>--请选择--</option>");
            $("#proDetailId").html("<option value=''>--请选择--</option>")
        }
        else if(htmlSel=="")
        {
            $("#productName").html("<option value=''>--请选择--</option>");
            $("#proDetailId").html("<option value=''>--请选择--</option>")
        }
        else{
            $("#proDetailId").html("<option value=''>--请选择--</option>")
        }
    })
    $("#productName").on('change',function(){
        var htmlSel=$("#productName option:selected").html();
        if(htmlSel=="--请选择--"){
            $("#proDetailId").html("<option value=''>--请选择--</option>")
        }
        else if(htmlSel=="")
        {
            $("#proDetailId").html("<option value=''>--请选择-</option>")
        }
    })
})

function getProductSeries(flag) {
    if(flag=='start'){
        Comm.ajaxPost('product/getProductSeries',"",function (data) {
            var html="";
            var result = data.data.data;
            for ( var i = 0; i < result.length; i++) {
                html += '<option value="' +result[i].id + '">' + result[i].pro_name + '</option>';
            }
            $("#provcd").append(html);
        },'','','','',false);
    }
    getProductType();
}
function getProductType() {
    $("#productName").empty().append("<option value=''>--请选择-</option>");
       var parentId=$("#provcd option:selected").val();
       console.log(parentId);
       var html = "";
       Comm.ajaxPost(
           'product/getProductType',"id="+parentId,
           function (data) {
               var result = data.data.data;
               var html="";
               if(result&&result.length>0){
                   for ( var i = 0; i < result.length; i++) {
                       html = '<option value="' +result[i].id + '">' + result[i].pro_name
                           + "</option>";
                       $("#productName").append(html);
                   }
               }
           }
       );
    getProductDetail();
}

function getProductDetail() {
        $("#proDetailId").empty().append("<option value=''>--请选择-</option>");
        var id=$("#productName option:selected").val();
        var html = "";
        Comm.ajaxPost(
            'product/getProductDetail',"crmProductId="+id,
            function (data) {
                if(data.data!="false"&&data.code!="1"){
                    var result = data.data.data;
                    var html="";
                    for ( var i = 0; i < result.length; i++) {
                        html = '<option value="' +result[i].id + '">' + result[i].periods
                            + "</option>";
                        console.log(result[i].id);
                        console.log(html);
                        $("#proDetailId").append(html);
                    }
                }
            }
        );

}

