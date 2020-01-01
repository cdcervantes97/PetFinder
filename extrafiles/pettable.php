<?php
$servername = "localhost";
$username = "petfind4_app";
$password = "Pass123#";
$dbname = "petfind4_petfinder";

//open connection to mysql db
$connection = mysqli_connect($servername,$username,$password,$dbname) or die("Error " . mysqli_error($connection));

//fetch table rows from mysql db
$sql = "select * from pet";
$result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));

//create an array
$emparray = array();
while($row =mysqli_fetch_assoc($result)) {
    $emparray[] = $row;
}
echo json_encode($emparray);

//close the db connection
mysqli_close($connection);
?>
