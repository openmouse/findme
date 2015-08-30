/**
 * 
 */

var kelansi = {base:'/demo'}
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