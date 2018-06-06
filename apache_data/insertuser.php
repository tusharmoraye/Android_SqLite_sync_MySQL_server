<style>
body {
  font: normal medium/1.4 sans-serif;
}
div.header{
padding: 10px;
background: #e0ffc1;
width:30%;
color: #008000;
margin:5px;
}
table {
  border-collapse: collapse;
  width: 25%;
  margin-left: auto;
  margin-right: auto;
}
form{
width: 30%;
  margin-left: auto;
  margin-right: auto;
padding: 10px;
border: 2px solid #edd3ff;
}
div#msg{
margin-top:10px;
width: 30%;
margin-left: auto;
margin-right: auto;
text-align: center;
}
</style>
<center>
<div class="header">
Android SQLite and MySQL Sync - Add Users
</div>
</center>
<form method="POST">
<table>
<tr>
<td>FirstName:</td><td><input name="FirstName" /></td>
</tr>
<tr>
<td>LastName:</td><td><input name="LastName" /></td>
</tr>
<tr>
<td>Age:</td><td><input name="Age" /></td>
</tr>
<tr>
<td>Qualification:</td><td><input name="Qualification" /></td>
</tr>
<tr><td colspan="2" align="center"><input type="submit" value="Add User"/></td></tr>
</table>
</form>
<?php
include_once './db_functions.php';
//Create Object for DB_Functions clas
if((isset($_POST["FirstName"]) && !empty($_POST["FirstName"])) && (isset($_POST["LastName"]) && !empty($_POST["LastName"])) && (isset($_POST["Age"]) && !empty($_POST["Age"])) && (isset($_POST["Qualification"]) && !empty($_POST["Qualification"]))) {
$db = new DB_Functions(); 
//Store User into MySQL DB
$firstname = $_POST["FirstName"];
$lastname = $_POST["LastName"];
$age = $_POST["Age"];
$qual = $_POST["Qualification"];
$status = 0;
$res = $db->storeUser($firstname, $lastname, $age, $qual, $status);
	//Based on inserttion, create JSON response
	if($res){ ?>
		 <div id="msg">Insertion successful</div>
	<?php }else{ ?>
		 <div id="msg">Insertion failed</div>
	<?php }
} else{ ?>
 <div id="msg">Please enter name and submit</div>
<?php }
?>