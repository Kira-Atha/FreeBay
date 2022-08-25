<?php
	include("config.php");

    try {
        $dbh = new PDO($dsn,$user,$password);
    } catch (PDOException  $e) {
        echo $e->getMessage();
    }
	
	if(isset($_POST["idUser"])){
		$idUser = $_POST["idUser"];

		if(empty($idUser)){
			echo "Field empty";
			exit();
		}
	}else{
		echo "Field doesn't exist";
		exit();
	}
	
	$req = $dbh->prepare(	"SELECT List_tracked_items.IdList,List_tracked_items.IdUser,Choice_List.IdItem FROM ".$dbname.".List_tracked_items
							INNER JOIN ".$dbname.".Choice_List
							ON ".$dbname.".List_tracked_items.IdList = ".$dbname.".Choice_List.IdList
							WHERE ".$dbname.".List_tracked_items.IdUser =".$idUser);
	$req -> execute();
	$result = $req -> fetchAll(PDO::FETCH_ASSOC);
	
	$json_result = json_encode($result);
	$dbh = null;
	echo $json_result;
?>