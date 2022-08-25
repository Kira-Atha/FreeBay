<?php
	include("config.php");

    try {
        $dbh = new PDO($dsn,$user,$password);
    } catch (PDOException  $e) {
        echo $e->getMessage();
    }
	// Même si verif côté client, important verif côté serveur aussi
	
	if(isset($_POST["pseudo"]) && isset($_POST["password"]) && isset($_POST["latitude"]) && isset($_POST["longitude"])){
		$pseudo = strip_tags($_POST["pseudo"]);
		$password = strip_tags($_POST["password"]);
		$latitude = $_POST["latitude"];
		$longitude = $_POST["longitude"];
	//test :OK
		//echo $pseudo." ".$password." ".$latitude." ".$longitude." ";
		if(empty($pseudo) || empty($password) || empty($latitude) || empty($longitude) ){
			echo "Field empty";
			exit();
		}

		//$hash_password = password_hash($password,PASSWORD_DEFAULT);
	}else{
		echo "Field doesn't exist";
		exit();
	}
	
	$req = $dbh ->prepare("INSERT INTO ".$dbname.".UserInfo(Pseudo,Password,Longitude,Latitude) VALUES(?,?,?,?)");
	$req->execute(array($pseudo,$password,$longitude,$latitude));
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