<?php
    include_once 'db_functions.php';
    $db = new DB_Functions();
    $users = $db->getUnSyncRowCount();
	$a = array();
	$b = array();
    if ($users != false){
        $no_of_users = mysql_num_rows($users);
		while ($row = mysql_fetch_array($users)) {		
			$b["Id"] = $row["ID"];
			$b["FirstName"] = $row["FirstName"];
			$b["LastName"] = $row["LastName"];
			$b["Age"] = $row["Age"];
			$b["Qualification"] = $row["Qualification"];

			array_push($a,$b);
		}
		echo json_encode($a);
	}
    /*else{
        $no_of_users = 0;
		$b["count"] = $no_of_users;
		echo json_encode($b);
	}*/
?>