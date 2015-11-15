(function() {
	var $inputForm = $("#inputForm");
	
	// 表单验证
	$inputForm.validate({
		rules: {
			oldPwd:{
				required: true,
			},
			newPwd: {
				required: true,
				pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
				minlength: 6,
				maxlength: 20
			},
			checkPwd: {
				required: true,
				pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
				minlength: 6,
				maxlength: 20
			}
		},
		messages: {
			newPwd: "当前字段为必填，只允许输入数字、字母，长度为6-20位",
			checkEnd: "当前字段为必填，只允许输入数字、字母，长度为6-20位",
			oldPwd:"当前字段为必填"
		}
	});
	
	var msg = $("#msg");
	if(msg.val()){
		alert(msg.val());
	}
})();