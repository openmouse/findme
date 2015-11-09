/**
 * 
 */

var kelansi = {
		base:'/findme',
		file: "/findme/static/upload"
	};
//多语言
function message(code) {
	if (code != null) {
		var content = messages[code] != null ? messages[code] : code;
		if (arguments.length == 1) {
			return content;
		} else {
			if ($.isArray(arguments[1])) {
				$.each(arguments[1], function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			} else {
				$.each(Array.prototype.slice.apply(arguments).slice(1), function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			}
		}
	}
}

(function($) {
	//令牌	
	$(document).ajaxSend(function(event, request, settings) {
		if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
			var token = getCookie("token");
			if (token != null) {
				request.setRequestHeader("token", token);
			}
		}
	});
	
	$(document).ajaxComplete(function(event, request, settings) {
		var loginStatus = request.getResponseHeader("loginStatus");
		var tokenStatus = request.getResponseHeader("tokenStatus");
		
		if (loginStatus == "accessDenied") {
			$.redirectLogin(location.href, message("shop.login.accessDenied"));
		} else if (tokenStatus == "accessDenied") {
			var token = getCookie("token");
			if (token != null) {
				$.extend(settings, {
					global: false,
					headers: {token: token}
				});
				$.ajax(settings);
			}
		}
	});
})(jQuery);

!function(){
	$("form").each(function(){
		if($(this).find("input[name = 'token']").size() == 0){
			$(this).append('<input type="hidden" name="token" value="' + getCookie("token") + '" />');
		}else{
			$(this).find("input[name = 'token']").val(getCookie("token"));
		}
	});
}();

var messages = {
	"shop.message.success": "操作成功",
	"shop.message.error": "操作错误",
	"shop.dialog.ok": "确&nbsp;&nbsp;定",
	"shop.dialog.cancel": "取&nbsp;&nbsp;消",
	"shop.dialog.deleteConfirm": "您确定要删除吗？",
	"shop.dialog.clearConfirm": "您确定要清空吗？",
	"shop.validate.required": "必填",
	"shop.validate.email": "E-mail格式错误",
	"shop.validate.url": "网址格式错误",
	"shop.validate.date": "日期格式错误",
	"shop.validate.dateISO": "日期格式错误",
	"shop.validate.pointcard": "信用卡格式错误",
	"shop.validate.number": "只允许输入数字",
	"shop.validate.digits": "只允许输入零或正整数",
	"shop.validate.minlength": "长度不允许小于{0}",
	"shop.validate.maxlength": "长度不允许大于{0}",
	"shop.validate.rangelength": "长度必须在{0}-{1}之间",
	"shop.validate.min": "不允许小于{0}",
	"shop.validate.max": "不允许大于{0}",
	"shop.validate.range": "必须在{0}-{1}之间",
	"shop.validate.accept": "输入后缀错误",
	"shop.validate.equalTo": "两次输入不一致",
	"shop.validate.remote": "输入错误",
	"shop.validate.integer": "只允许输入整数",
	"shop.validate.positive": "只允许输入正数",
	"shop.validate.negative": "只允许输入负数",
	"shop.validate.decimal": "数值超出了允许范围",
	"shop.validate.pattern": "格式错误",
	"shop.validate.extension": "文件格式错误",
	"shop.login.accessDenied": "请登录后再进行操作"
};

// 添加Cookie
function addCookie(name, value, options) {
  if (arguments.length > 1 && name != null) {
    options = $.extend({path: '/'}, options||{})
    if (value == null) {
      options.expires = -1;
    }
    if (typeof options.expires == "number") {
      var time = options.expires;
      var expires = options.expires = new Date();
      expires.setTime(expires.getTime() + time * 1000);
    }
    document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires ? "; expires=" + options.expires.toUTCString() : "") + (options.path ? "; path=" + options.path : "") + (options.domain ? "; domain=" + options.domain : ""), (options.secure ? "; secure" : "");
  }
}

// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}
