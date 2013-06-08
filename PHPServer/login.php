<?php
require('./smarty/libs/Smarty.class.php');
$smarty = new Smarty;
$smarty->force_compile = true;
$smarty->debugging = true;
$smarty->caching = true;
$smarty->cache_lifetime = 1;
$smarty->display('login.tpl');
?> 

