<?php
session_start();
if($_POST && $_POST['email'] == 'root@cydu.XXXX' && 
  	$_POST['password'] == 'XXXpassword') { 
	$_SESSION['loggedin'] = "cydu";
	echo '0';
}else {
	header('HTTP/1.1 403 Forbidden');
}
?> 

