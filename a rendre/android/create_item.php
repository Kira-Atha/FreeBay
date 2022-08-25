<?php
	include("config.php");

    try {
        $dbh = new PDO($dsn,$user,$password);
    } catch (PDOException  $e) {
        echo $e->getMessage();
    }
	
	if(isset($_POST["name"]) && isset($_POST["description"]) && isset($_POST["iduser"]) && isset($_POST["price"]) && isset($_POST["type"])){
		$name = strip_tags($_POST["name"]);
		$description = strip_tags($_POST["description"]);
		$idUser = $_POST["iduser"];
		$price = $_POST["price"];
		$type = $_POST["type"];

		if(empty($name) || empty($description) || empty($idUser) || empty($price) || empty($type)){
			echo "Field empty";
			exit();
		}

	}else{
		echo "Field doesn't exist";
		exit();
	}
	
	
	$req = $dbh ->prepare("INSERT INTO ".$dbname.".Item(Name,Type,Price,Description,IdUser) VALUES(?,?,?,?,?)");
	$req->execute(array($name,$type,$price,$description,$idUser));
	$affected_rows = $req -> rowCount();
	
	if($affected_rows > 0){
		/* Mieux de passer un tableau 
		$req = $dhb -> prepare("INSERT INTO ".$dbname.".Photo_Item(IdItem,Photo) VALUE (?,?)");
		$req -> execute(array($idItem,$photo));
		*/
		echo "ok";
		$dbh = null;
		exit();
	}else{
		echo "not ok";
		$dbh = null;
		exit();
	}
?>