<html>
<head><title>View Users</title>
<style>
body {
  font: normal medium/1.4 sans-serif;
}
table {
  border-collapse: collapse;
  width: 20%;
  margin-left: auto;
  margin-right: auto;
}
tr > td {
  padding: 0.25rem;
  text-align: center;
  border: 1px solid #ccc;
}
tr:nth-child(even) {
  background: #FAE1EE;
}
tr:nth-child(odd) {
  background: #edd3ff;
}
tr#header{
background: #c1e2ff;
}
td#sync{
background: #fff;
}
div.header{
padding: 10px;
background: #e0ffc1;
width:30%;
color: #008000;
margin:5px;
}
div.refresh{
margin-top:10px;
width: 5%;
margin-left: auto;
margin-right: auto;
}
div#norecord{
margin-top:10px;
width: 15%;
margin-left: auto;
margin-right: auto;
}
img{
height: 32px;
width: 32px;
}
</style>
<script>
var val= setInterval(function(){
location.reload();
},2000);
</script>
</head>
<body>
<center>
<div class="header">
Android SQLite and MySQL Sync - View Users
</div>
</center>
<?php
    include_once 'db_functions.php';
    $db = new DB_Functions();
    $users = $db->getAllUsers();
    if ($users != false)
        $no_of_users = mysql_num_rows($users);
    else
        $no_of_users = 0;
		
?>
<?php
    if ($no_of_users > 0) {
?>
<table>
<tr id="header"><td>Id</td><td>FirstName</td><td>LastName</td><td>Age</td><td>Qualification</td><td>Sync Status</td></tr>
<?php
    while ($row = mysql_fetch_array($users)) {
?> 
<tr>
<td><span><?php echo $row["ID"] ?></span></td>
<td><span><?php echo $row["FirstName"] ?></span></td>
<td><span><?php echo $row["LastName"] ?></span></td>
<td><span><?php echo $row["Age"] ?></span></td>
<td><span><?php echo $row["Qualification"] ?></span></td>

<td id="sync"><span>
<?php 
if($row["Status"])
{ 
echo "<img src='img/green.png'/>"; 
}else { 
echo "<img src='img/white.png'/>";
} 
?></span></td>
</tr>
<?php } ?>
</table>
<?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
<?php } ?>
</body>
</html>
                          
    