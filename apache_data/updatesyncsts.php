<?php
include_once './db_functions.php';
//Create Object for DB_Functions clas
$db = new DB_Functions(); 

$a=array();
$b=array();

$res = $db->updateSyncSts($_POST["ID"], $_POST["Status"]);
	//Based on inserttion, create JSON response
if($res){
	$b["id"] = $_POST["ID"];
	$b["status"] = 'yes';
	array_push($a,$b);
}else{
	$b["id"] = $_POST["ID"];
	$b["status"] = 'no';
	array_push($a,$b);
}

//Post JSON response back to Android Application
echo json_encode($a);
?>