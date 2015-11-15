$(function() {
	var extensions = "";
	$.each(setting.uploadImageExtension.split(","), function(index, extension){
		if(extension != "gif"){
			extensions += "*." + extension.toLowerCase() + ";" + "*." + extension.toUpperCase() + ";";
		}
	});
		
	$('body').append('<input type="hidden" name="token" value="' + getCookie("token") + '" />');
	
	var errorFlag = false;
	
	setTimeout(function(){
		$('#file_upload').uploadify({
	        'swf'      : kelansi.base + '/static/resources/js/imageUpload/uploadify.swf',
	        'uploader' : kelansi.base + '/imageUpload.htm',
	        'fileTypeExts'  : extensions,
	        'fileTypeDesc' : '图片文件',
	        'formData' : {'token' : $("input[name='token']").val()},
	        'fileObjName' : 'filename',
	        'multi'    : true,
	        'buttonText': '请选择文件...',
	        'auto'     : false,
	        'removeCompleted' : false,
	        'removeTimeout' : false,
	        'onUploadSuccess' : function(file, data, response) {
	        	var dataset = $.parseJSON(data);
	        	if(!errorFlag){
		        	if(dataset.type == "error" || dataset.type == "warn"){
		        		errorFlag = true;
		        		alert(dataset.content);
		        	}
	        	}
	        },
	        'onFallback' : function() {
	        	alert("未检测到Flash插件.请安装Flash以便正常运行！");
	        },
	        'onSelectError' : function(file, errorCode, errorMsg) {
	        	switch(errorCode) {
		        	case -130:
		        		alert('文件' + file.name + '类型不正确！');
		        		break;
		        	case -120:
		        		alert('文件' + file.name + '大小异常！');
		        		break;
		        	case -100:
						alert('文件' + file.name + '上传失败，同时上传的文件数量已超出系统限制的' + $("#file_upload").uploadify('settings', 'queueSizeLimit') + '个文件！');
						break;
		        	case -110:
		        		alert('文件' + file.name + '大小超出系统限制的' + $("#file_upload").uploadify('settings', 'fileSizeLimit') + '大小！');
		        		break;
	        	}
	        },
	        'onUploadError' : function(file, errorCode, errorMsg) {
	        	switch(errorCode) {
		        	case -130:
		        		alert('文件' + file.name + '类型不正确！');
		        		break;
		        	case -120:
		        		alert('文件' + file.name + '大小异常！');
		        		break;
		        	case -100:
						alert('文件' + file.name + '上传失败，同时上传的文件数量已超出系统限制的' + $("#file_upload").uploadify('settings', 'queueSizeLimit') + '个文件！');
						break;
		        	case -110:
		        		alert('文件' + file.name + '大小超出系统限制的' + $("#file_upload").uploadify('settings', 'fileSizeLimit') + '大小！');
		        		break;
	        	}
	        }
	        // Put your options here
	    });
	}, 10);
	
	$("#upload").click(function(){
		errorFlag = false;
		$('#file_upload').uploadify('upload', '*');
	});
});