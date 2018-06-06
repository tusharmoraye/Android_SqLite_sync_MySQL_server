<?php

class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        include_once './db_connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($firstname, $lastname, $age, $qual, $status) {
        // Insert user into database
        //$result = mysql_query("INSERT INTO user(Name) VALUES('$User')");
		
		$result = mysql_query("INSERT INTO client (FirstName, LastName, Age, Qualification, Status) VALUES ('$firstname', '$lastname', '$age', '$qual', '$status')");
		
        if ($result) {
			return true;
        } else {			
				// For other errors
				return false;
		}
    }
	 /**
     * Getting all users
     */
    public function getAllUsers() {
        $result = mysql_query("select * FROM client");
        return $result;
    }
	/**
     * Get Yet to Sync row Count
     */
    public function getUnSyncRowCount() {
        $result = mysql_query("SELECT * FROM client WHERE Status = FALSE");
        return $result;
    }
	
	public function updateSyncSts($id, $sts){
		$result = mysql_query("UPDATE client SET Status = $sts WHERE ID = $id");
		return $result;
	}
}

?>