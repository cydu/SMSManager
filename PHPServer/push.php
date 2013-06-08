<?php
require_once("./mcrypt.php");
function convertUrlQuery($query)
{ 
    $queryParts = explode('&', $query); 
    $params = array(); 
    foreach ($queryParts as $param) 
	{ 
        $item = explode('=', $param); 
        $params[$item[0]] = $item[1]; 
    } 
    return $params; 
}

$mcrypt = new MCrypt();
$decrypted = $mcrypt->decrypt($_POST['q']);
$params = convertUrlQuery($decrypted);

if($params['magic'] != "magicpassword") {
	exit();
}

$mysqli = new mysqli("localhost", 'user', 'password', "sms");

if ($mysqli->connect_errno) {
    printf("Connect failed: %s\n", $mysqli->connect_error);
    exit();
}

$epoch = $mysqli->real_escape_string(stripslashes($params['epoch']));
$phone_num = $mysqli->real_escape_string(stripslashes($params['phone_num']));
$text = $mysqli->real_escape_string(stripslashes(base64_decode($params['text'])));
$s = 'INSERT INTO sms(epoch,phone_num,text) VALUES('.$epoch.',"'.$phone_num.'","'.$text.'")';
if ($result = $mysqli->query($s)) {
    echo "ok ".$s;
}else {
    echo "faild ".$s;
}
$mysqli->close();

$fn = file_get_contents('weibosdk/weibojs_username');
$token = file_get_contents( 'weibosdk/'.$fn );
$auth = convertUrlQuery($token);

if($token) {
include_once( 'weibosdk/config.php' );
include_once( 'weibosdk/saetv2.ex.class.php' );
$c = new SaeTClientV2( WB_AKEY , WB_SKEY , $auth['access_token']);
$res = $c->update("@cydu ".$epoch." ".$phone_num." ".$text);
var_dump($res);
}

?> 
