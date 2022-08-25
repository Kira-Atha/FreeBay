<?php
	include("config.php");

    try {
        $dbh = new PDO($dsn,$user,$password);
    } catch (PDOException  $e) {
        echo $e->getMessage();
    }
	
	if(isset($_POST["idUser"]) && isset($_POST["idItem"])){
		$idUser = $_POST["idUser"];
		$idItem = $_POST["idItem"];

		if(empty($idItem) || empty($idUser)){
			echo "Field empty";
			exit();
		}

	}else{
		echo "Field doesn't exist";
		exit();
	}
	
// S'il n'a pas déjà une liste, alors la créer. S'il l'a déjà, récupérer l'id de cette liste afin de lui ajouter l'objet à suivre
	$req = $dbh->prepare("SELECT IdList,IdUser FROM ".$dbname.".List_tracked_items WHERE IdUser =".$idUser);
	$req -> execute();
	
	$result = $req -> fetchAll(PDO::FETCH_ASSOC);
	$rowCount = $req -> rowCount();
	if($rowCount == 0){
		$req = $dbh ->prepare("INSERT INTO ".$dbname.".List_tracked_items(IdUser) VALUES(?)");
		$req->execute(array($idUser));
		$idList = $dbh -> lastInsertId();
	}else{
		$idList = $result[0]["IdList"];
	}
	
	$req = $dbh ->prepare("INSERT INTO ".$dbname.".Choice_List(IdList,IdItem) VALUES(?,?)");
	$req->execute(array($idList,$idItem));
	$affected_rows = $req -> rowCount();
	
	if($affected_rows > 0){
		echo "ok";
		$dbh = null;
		exit();
	}else{
		echo "not ok";
		$dbh = null;
		exit();
	}
?>