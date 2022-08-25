<?php
	include("config.php");

		try {
			$dbh = new PDO($dsn,$user,$password);
		} catch (PDOException  $e) {
			echo $e->getMessage();
		}	
	
	$pictures = [];
	
	if(isset($_POST["pictures"])){
		$pictures = $_POST["pictures"];
	}else{
		echo "Field doesn't exist";
		exit();
	}
	// Je récupère l'id qui vient d'être créé, c'est le dernier
	$req = $dbh -> prepare("SELECT IdItem FROM ".$dbname."item ORDER BY IdItem DESC LIMIT 1");
	$req -> execute();
	$res = $req -> fetch(PDO::FETCH_ASSOC);
	$idItem = $res["IdItem"];
	
	$affected_rows = 0;
	for($i = 0;$i<count($pictures) ;$i++){
		$req = $dbh ->prepare("INSERT INTO ".$dbname.".photo_item(PHOTO,IdItem) VALUES(?,?)");
		$req->execute(array($pictures[$i],$idItem));
		$affected_rows += $req -> rowCount();
	}
	
// Est-ce que TOUTES les images ont été ajoutées ???
	if($affected_rows == count($pictures){
		echo "ok";
		$dbh = null;
		exit();
	}else{
		echo "not_ok";
		$dbh = null;
		exit();
	}
?>