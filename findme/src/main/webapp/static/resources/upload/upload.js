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
});