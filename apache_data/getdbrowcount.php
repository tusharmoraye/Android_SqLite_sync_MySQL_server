<?php
    include_once 'db_functions.php';
    $db = new DB_Functions();
    $users = $db->getUnSyncRowCount();
	$a = array();
	$b = array();
    if ($users != false){
        $no_of_users = mysql_num_rows($users);		
		$b["count"] = $no_of_users;
		echo json_encode($b);
	}
    else{
        $no_of_users = 0;
		$b["count"] = $no_of_users;
		echo json_encode($b);
	}
?>