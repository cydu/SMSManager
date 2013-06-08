<?php
session_start();
if(empty($_SESSION['loggedin']) || $_SESSION['loggedin'] != 'cydu' ) {
	header("Location: http://cydu.XXXX/SMSManager/login.php");
	exit();
}
$mysqli = new mysqli("localhost", 'user', 'password', "sms");
if ($mysqli->connect_errno) {
    printf("Connect failed: %s\n", $mysqli->connect_error);
    exit();
}

$lines = array();
if ($result = $mysqli->query("SELECT id,phone_num,FROM_UNIXTIME(epoch,'%Y/%m/%d %H:%i:%s'),text FROM sms order by id desc LIMIT 100")) {
    while($obj = $result->fetch_object()){ 
        $lines[] = $obj;
    } 
    $result->close();
}
$mysqli->close();

require('./smarty/libs/Smarty.class.php');
$smarty = new Smarty;
$smarty->force_compile = true;
$smarty->debugging = true;
$smarty->caching = true;
$smarty->cache_lifetime = 1;
$smarty->assign("lines",$lines);
$smarty->display('index.tpl');
?> 

