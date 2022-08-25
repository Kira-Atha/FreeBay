<?php
	include("config.php");

    try {
        $dbh = new PDO($dsn,$user,$password);
    } catch (PDOException  $e) {
        echo $e->getMessage();
    }
	
	$req = $dbh->prepare("SELECT IdItem,Name,Price,Type,Description,IdUser FROM ".$dbname.".Item");
	$req -> execute();
	$result = $req -> fetchAll(PDO::FETCH_ASSOC);
	
	$json_result = json_encode($result);
	
	echo $json_result;
?>
	