<?php
	include("config.php");

    try {
        $dbh = new PDO($dsn,$user,$password);
    } catch (PDOException  $e) {
        echo $e->getMessage();
    }
	
	$req = $dbh->prepare("SELECT IdUser,Pseudo,Password,Longitude,Latitude FROM ".$dbname.".UserInfo");
	$req -> execute();
	$result = $req -> fetchAll(PDO::FETCH_ASSOC);
	
	$json_result = json_encode($result);
	$dbh = null;
	echo $json_result;

?>
	