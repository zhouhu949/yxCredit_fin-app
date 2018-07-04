/**
 * Created by 陈淸玉 on 2018-06-29
 */
//页面初始化
$(function () {
    dictManage.methods.initSettings();
})
var dictManage = {
    tableEl: '#dictList',
    editEl: '#edit_dict',
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    requestParameter: {
    },
    template: {

    },
    urls: {
        //数据加载url
        loadDataUrl: 'dict/findWindControlPeriodList',
        editUrl:'dict/edit'
    },
    data: {
        layerIndex:undefined,
        row: undefined,
        table: undefined
    },
    methods: {
        initSettings:function(){
            dictManage.tableUser = $(dictManage.tableEl).dataTable($.extend({
                'iDeferLoading':true,
                "bAutoWidth" : false,
                "iDisplayLength": 4,
                "Processing": true,
                "ServerSide": true,
                "sPaginationType": "full_numbers",
                "bPaginate": true,
                "bLengthChange": false,
                "paging":false,
                "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
                "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
                "ajax" :  function(data, callback, settings){
                    dictManage.methods.loadData(data, callback, settings);
                },
                "order": [],
                "columns" :[
                    {
                        'data':null,
                        "searchable":false,"orderable" : false
                    },
                    {"data":'name',"searchable":false,"orderable" : false},
                    {"data":'value',"searchable":false,"orderable" : false},
                    {"data":'remark',"searchable":false,"orderable" : false, "width": "30%"},
                    {
                        "data":null,
                        "orderable" : false,
                        "render" : function(data, type, row, meta) {
                            return json2TimeStamp(data.createTime);
                        }
                    },
                    {
                       "orderable": false,
                        "width": "100px",
                        "defaultContent":"",
                        "render" : function(data, type, row, meta) {
                           var row = {
                               id:row.id,
                               name:row.name,
                               value:row.value,
                               remark:row.remark
                           }
                           return '<a href="javascript:" class="edit" style="text-decoration: none;color: #307ecc;" onclick=dictManage.methods.toModify('+ JSON.stringify(row) +')>修改</a>';
                        }
                    }
                ],
                "initComplete" : function(settings,json) {
                }
            }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
            dictManage.tableUser.on("order.dt search.dt", function() {
                dictManage.tableUser.column(0, {
                    search : "applied",
                    order : "applied"
                }).nodes().each(function(cell, i) {
                    cell.innerHTML = i + 1
                })
            }).draw();
        },
        /**
         * 加载数据
         */
        loadData: function (data, callback, settings) {
              Comm.ajaxGet(dictManage.urls.loadDataUrl,null,function (result) {
                if(result){
                    var returnData = {};
                        returnData.data =  result.data;
                        callback(returnData);
                }
            },null,false)
        },
        /**
         * 提交编辑
         */
        modify:function(){
            var editEl = $(dictManage.editEl);
            var value = editEl.find("input[name='dictValue']").val();
            var remark = editEl.find("[name='dictRemark']").val();;
            dictManage.data.row.value = value;
            dictManage.data.row.remark = remark;
            if(value === ''){
                layer.msg("授权期限不可为空！",{time:2000});return

            }
            if(isNaN(value)){
                layer.msg("授权期限只能输入数字！",{time:2000});return
            }
            Comm.ajaxPost(dictManage.urls.editUrl,JSON.stringify(dictManage.data.row),
                function(data){
                    if(data.code === 1){
                        layer.msg(data.msg,{time:2000});
                    }
                    layer.close(dictManage.data.layerIndex);
                    layer.msg(data.msg,{time:2000},function(){
                      window.location.reload();//刷新数据
                    });
                }, "application/json"
            );
        },
        /**
         * 编辑渲染
         * @param row 编辑修复内容
         */
        toModify:function (row) {
            dictManage.data.row = row;
                if(row){
                    var editEl = $(dictManage.editEl);
                    editEl.find("input[name='dictValue']").val(row.value);
                    editEl.find("input[name='dictName']").val(row.name);
                    editEl.find("[name='dictRemark']").val(row.remark);
                    dictManage.data.layerIndex = layer.open({
                        type: 1,
                        title: "修改",
                        maxmin: true,
                        shadeClose: false, //点击遮罩关闭层
                        area: ['600px', ''],
                        content: editEl,
                        btn: ['保存', '取消'],
                        success: function (index, layero) {
                        },
                        yes:  function (index, layero) {
                            dictManage.methods.modify();
                        }
                    });
                }
        }
    }
}






