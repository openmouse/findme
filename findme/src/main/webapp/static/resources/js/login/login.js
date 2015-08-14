(function() {
	var $username = $("#username");
	var $password = $("#password");
	var $loginbtn = $("#loginbtn");
	var $lockbtn = $("#lockbtn");
	var $loginForm = $("#loginForm");
	
	function check_values() {
	        if ($username.val().length != 0 && $password.val().length != 0) {
	        	$loginbtn.animate({ left: '0' , duration: 'slow'});;
	        	$lockbtn.animate({ left: '260px' , duration: 'slow'});;
	        }
	    }
	$password.keypress(function(){
		check_values();
	});
	$username.keypress(function(){
		check_values();
	});
				
	$loginbtn.click(function(){
		$('#loading').removeClass('hidden');
		$.ajax({
			url: $loginForm.attr("action"),
			type: "POST",
			data: {username: $username.val(), password: $password.val()},
			dataType: "json",
			async: false,
			cache: false,
			success: function(message) {
				
			}
		});
	});
})(jQuery);