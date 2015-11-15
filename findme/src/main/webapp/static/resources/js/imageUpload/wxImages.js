$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            $("tr:has(td)").remove();
            alert(data.result.message.content);
            $.each(data, function (index, file) {
            	
            }); 
        },
 
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        },
 
        dropZone: $('#dropzone')
    });
    
    var rtMessage = $("#rtMessage");
	if(rtMessage.val() && rtMessage.val() != ''){
		alert(rtMessage.val());
	}
	
	var submitForm = $("#inputForm");
	$('#submitButton').click(function(){
		submitForm.ajaxSubmit(function(data) {  
		    alert(data);//弹出ajax请求后的回调结果
		});
	});
});