(function() {
	var $inputForm = $("#inputForm");
	var $enumNumSelect=$("#enumNum");
	var $enumValueSelect=$("#enumValue");
	var $enumUrl=$("#enumUrl");
	
	// 表单验证
	$inputForm.validate({
		rules: {
			numBegin: {
				digits: true,
				min: 1
			},
			numEnd: {
				digits: true,
				min: 1
			}
		},
		messages: {
			numBegin: "只允许输入正整数",
			numEnd: "只允许输入正整数"
		}
	});
	
	//获取字段对应形容词
	function retrieveEnum(){
		var _enumNum = $enumNumSelect.val();
		$.ajax({
			  url: kelansi.base + '/enumkey/config/retrieveEnumKey.htm',
			  data: {enumNum:_enumNum},
			  type: 'GET',
			  success: function(data){
				  if(data.size != 0){
					  $enumValueSelect.empty();
				  }
				  for(enumKey in data){
					  //<option value="">--请选择--</option>
					  var enumKeyShow;
					  if(data[enumKey].type == 1){
						  enumKeyShow = data[enumKey].numBegin + " - " + data[enumKey].numEnd;
					  }else if(data[enumKey].type == 0){
						  enumKeyShow = data[enumKey].enumKey;
					  }
					  $enumValueSelect.append('<option value="' + data[enumKey].id + '">' + enumKeyShow + '</option>');
					  if(enumKey == 0){
						  changeEnumId(data[enumKey].id);
					  }
				  }
				  $enumValueSelect.append('<option value="-1">添加新词</option>');
			  },
			  dataType: 'json'
		});
	} 
	
	$enumValueSelect.change(function(){
		var enumId = $enumValueSelect.val();
		changeEnumId(enumId);
	});
	
	function changeEnumId(data){
		$enumUrl.attr('href','delete.htm?id='+data);
	}
	
	retrieveEnum();
	
	$enumNumSelect.change(function(){
		retrieveEnum();
	});
	
	var msg = $("#msg");
	if(msg.val()){
		alert(msg.val());
	}
})();