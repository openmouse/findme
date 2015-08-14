var $username = $("#username");
var $password = $("#password");
var $loginbtn = $("#loginbtn");
var $lockbtn = $("#lockbtn");

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
	//这里书写登录相关后台处理，例如: Ajax请求用户名和密码验证
});