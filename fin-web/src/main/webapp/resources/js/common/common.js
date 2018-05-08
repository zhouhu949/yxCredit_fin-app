/**
 *正则表达式开始
 *ckEmail：邮件正则表达式
 *ckURL：URL地址正则表达式
 *ckMobi：手机正则表达式
 *ckQQ：QQ正则表达式
 *ckTel：电话正则表达式
 *ckPrice:单价正则表达式,小数点1--4位
 *ckQty：数量正则表达式
 *ckWeight：重量正则表达式
 *ckInt:整数正则表达式
 *ckZip:邮编正则表达式
 */
var ckEmail = /^\w+([+-.]*\w)*@\w+([-.]*\w)*\.\w+([-.]*\w)*$/;
var ckURL = /^http(s)?:\/\/[\S]*$/;
var ckMobi = /^1\d{10}$/;
var ckTel = /^0\d{2,3}-?\d{7,8}$|^\(0\d{2,3}\)\d{7,8}$|^0\d{2,3}-?\d{7,8}-?\d{1,10}$/;
var ckPrice = /^(([1-9]\d*)|0)(\.)?(\d{1,4})?$/;
var ckPrice2 = /^((-[1-9]\d*)|([1-9]\d*)|0)(\.)?(\d{1,4})?$/;
var ckRate = /^(([1-9]\d*)|0)(\.)?(\d{1,6})?$/;
var ckQty = /^(([1-9]\d*)|0)(\.)?(\d{1,4})?$/;
var ckWeight = /^(([1-9]\d*)|0)(\.)?(\d{1,6})?$/;
var ckZip = /^[1-9]\d{5}$/;
var ckInt = /^\d+$/;
var ckInt2 = /^-?[0-9]\d*$/;
var chkAccountID = /^\d{2}$/;
/*正则表达式结束*/

var Comm = Comm || {};

Comm.isIE6 = !window.XMLHttpRequest;	//ie6

/**
 * 获取URL参数值
 * @type {Comm.urlParam}
 */
Comm.getRequest = Comm.urlParam = function () {
    var param, url = location.search, theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0, len = strs.length; i < len; i++) {
            param = strs[i].split("=");
            theRequest[param[0]] = decodeURIComponent(param[1]);
        }
    }
    return theRequest;
};

/**
 * 通用post请求，返回json对象
 * @param url 请求地址
 * @param params 请求参数，没有传null
 * @param callback 成功时的回调函数
 * @param contentType
 * @param timeout 超时时间，参数可以不传
 * @param async 异步标志，true表示异步，false同步，参数可以不传
 * @param errorCallback 错误时的回调函数，参数可以不传
 * @param completeCallback 结束回调函数，参数可以不传
 */
Comm.ajaxPost = function (url, params, callback, contentType,processData, cache,timeout, async, errorCallback, completeCallback) {
    url = _ctx + "/" + url;
    if (url.indexOf('?') != -1) {
        url += "&r=" + _version;
    } else {
        url += "?r=" + _version;
    }
    if (contentType == null || typeof(contentType) == "undefined") {
        contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    }
    if (timeout == null || typeof(timeout) == "undefined") {
        timeout = 30 * 1000;
    }
    if (async == null || typeof(async) == "undefined") {
        async = true;
    }
    if(processData == null || typeof(processData) == "undefined"){
        processData = true;
    }
    if(cache == null || typeof(cache) == "undefined"){
        cache = true;
    }
    $.ajax({
        type: "POST",
        url: url,
        async: async,
        data: params,
        timeout: timeout,
        contentType: contentType,
        processData:processData,
        cache:cache,
        dataType: "json",
        beforeSend: function () {
            // layer.load(1, {
            //     shade: [0.1, '#fff'] //0.1透明度的白色背景
            // });
            layer.load(2);
        },
        success: function (data) {
            if (data.code == 0||data.code == 1) {
                callback(data);
            }  else if (data.code == 3 || data.code == 6) {
                layer.msg("会话失效，请重新登录！", {icon: 2, time: 2000}, function () {
                    Comm.getTopWinow().location = _ctx + '/login';
                });
            } else {
                layer.msg("系统或网络异常,请稍候重试！");
            }
        },
        error: function (err) {
            layer.msg("系统或网络异常,请稍候重试！");
            errorCallback && errorCallback(err);
        },
        complete: function () {
            layer.closeAll('loading');
            completeCallback && completeCallback();
        }
    });
};

/**
 * 通用get请求，返回json对象
 * @param url 请求地址
 * @param params 请求参数，没有传null
 * @param callback 成功时的回调函数
 * @param timeout 超时时间，参数可以不传
 * @param async 异步标志，true表示异步，false同步，参数可以不传
 * @param errorCallback 错误时的回调函数，参数可以不传
 * @param completeCallback 结束回调函数，参数可以不传
 */
Comm.ajaxGet = function (url, params, callback, timeout, async, errorCallback, completeCallback) {
    url = _ctx + "/" + url;
    if (url.indexOf('?') != -1) {
        url += "&r=" + _version;
    } else {
        url += "?r=" + _version;
    }
    if (timeout == null || typeof(timeout) == "undefined") {
        timeout = 30 * 1000;
    }
    if (async == null || typeof(async) == "undefined") {
        async = true;
    }
    $.ajax({
        type: "GET",
        url: url,
        async: async,
        timeout: timeout,
        dataType: "json",
        data: params,
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success: function (data) {
            if (data.code == 0) {
                callback(data);
            } else if (data.code == 1) {
                layer.msg(data.msg);
            } else if (data.code == 3) {
                Comm.getTopWinow().location = '/login?msg=会话失效,请重新登录';
            } else {
                layer.msg("系统或网络异常,请稍候重试！");
            }
        },
        error: function (err) {
            layer.msg("系统或网络异常,请稍候重试！");
            errorCallback && errorCallback(err);
        },
        complete: function () {
            layer.closeAll('loading');
            completeCallback && completeCallback();
        }
    });
};

/**
 * 判断是否为null
 * @param str
 * @returns {boolean}
 */
Comm.isNull = function isNull(str) {
    str = $.trim(str);
    if (str == null || str.length == 0) {
        return true;
    } else {
        return false;
    }
};

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
Comm.getTopWinow = function getTopWinow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
};

(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

//去空格
String.prototype.Trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

//去左空格
String.prototype.LTrim = function () {
    return this.replace(/(^\s*)/g, "");
}

//去右空格
String.prototype.Rtrim = function () {
    return this.replace(/(\s*$)/g, "");
}

//是否大于今天,大于今天false，或则true
String.prototype.GtToday = function () {
    var today = new Date();
    var temp = Date.parse(this.replace(/-/g, "/"));
    if (temp > today) {
        return false;
    }
    else {
        return true;
    }
}


//是否超长了
String.prototype.IsOverlength = function (n) {
    var temp = this.replace(/[^\x00-\xff]/g, "**");
    if (temp.length > n) {
        return true;
    }
    else {
        return false;
    }
}

//是否为空
String.prototype.IsEmpty = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    if (temp == "") {
        return true;
    }
    else {
        return false;
    }
}


//是否为会计科目编号
String.prototype.IsAccountID = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    return chkAccountID.test(temp);
}

//是否为国内邮编
String.prototype.IsZipCN = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    return ckZip.test(temp);
}
//是否为汇率
String.prototype.IsCurrRate = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckRate.test(temp);

}

//是否为货币
String.prototype.IsMoney = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckPrice.test(temp);

}
//是否为货币,可以为负数
String.prototype.IsMoney2 = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckPrice2.test(temp);
}
//是否为电话
String.prototype.IsTel = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    return ckTel.test(temp);
}

//是否为手机
String.prototype.IsMobile = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    return ckMobi.test(temp);
}

//是否为Email
String.prototype.IsEmail = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    return ckEmail.test(temp);
}

//是否为Int
String.prototype.IsInt = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckInt.test(temp);
}
//是否为Int,可以负数
String.prototype.IsInt2 = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckInt2.test(temp);
}
////是否为QQ
//String.prototype.IsQQ = function()
//{
//   var temp=this.replace(/(^\s*)|(\s*$)/g, "");
//   return ckQQ.test(temp);
//}
//是否为数量
String.prototype.IsQty = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckQty.test(temp);
}

//是否为重量
String.prototype.IsWeight = function () {
    var temp = this.replace(/(^\s*)|(\s*$)|[,]/g, "");
    return ckWeight.test(temp);
}

//是否为URL
String.prototype.IsURL = function () {
    var temp = this.replace(/(^\s*)|(\s*$)/g, "");
    return ckURL.test(temp);
}

//格式化上传日期
function timestamp(){
    var time = new Date();
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    var ms = time.getMilliseconds();
    var r=parseInt(Math.random() * 1000, 10);

    console.log(y);

    return ""+y+add0(m)+add0(d)+add0(h)+add0(mm)+add0(s)+add0(ms)+r;
}

function add0(m){
    return m<10?'0'+m : m;
}

Comm.ajaxUpload = function (url, params, callback, contentType,processData, cache,timeout, async, errorCallback, completeCallback) {
    url = _ctx + "/" + url;
    if (url.indexOf('?') != -1) {
        url += "&r=" + _version;
    } else {
        url += "?r=" + _version;
    }
    if (contentType == null || typeof(contentType) == "undefined") {
        contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    }
    if (timeout == null || typeof(timeout) == "undefined") {
        timeout = 30 * 1000;
    }
    if (async == null || typeof(async) == "undefined") {
        async = true;
    }
    if(processData == null || typeof(processData) == "undefined"){
        processData = true;
    }
    if(cache == null || typeof(cache) == "undefined"){
        cache = true;
    }
    $.ajax({
        type: "POST",
        url: url,
        async: async,
        data: params,
        timeout: timeout,
        contentType: contentType,
        processData:processData,
        cache:cache,
        dataType: "json",
        success: function (data) {
            if (data.code == 0||data.code == 1||data.code == -1) {
                callback(data);
            }  else if (data.code == 3 || data.code == 6) {
                layer.msg("会话失效，请重新登录！", {icon: 2, time: 2000}, function () {
                    Comm.getTopWinow().location = _ctx + '/login';
                });
            } else {
                layer.msg("系统或网络异常,请稍候重试！");
            }
        },
        error: function (err) {
            layer.msg("系统或网络异常,请稍候重试！");
            errorCallback && errorCallback(err);
        },
    });
};


