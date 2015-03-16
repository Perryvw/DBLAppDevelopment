<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetHitchhikeData
		Input parameters:
			userID: The ID of an user for whom the hitchhike data is requested.
			limit: The maximum number of results to be returned (Optional, default is 20).
			destination: The destination of the hitchhikers (Optional).
			location: The location of the user requesting the data (Optional).
			order: The order of the data (Optional).
				Possible values are:
				ta - Sort by time ascending. (Default)
				td - Sort by time descending.
				la - Sort by distance to location ascending (requires location parameter!).
				ld - Sort by distance to location descending (requires location parameter!).

		Output parameters:
			hitchhikers: An array of objects representing hitchhikers, each containing the following fields:
				userID: The hitchhiker’s user ID.
				location: The location of the hitchhiker.
				destination: The destination of the hitchhikers.
				timestamp: The time the hitchhike data was updated.
	
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	
	if(isset($_GET['limit'])){
		$limit = $_GET['limit'];
	}
	else {
		$limit = 20;
	}
	
	if(isset($_GET['destination'])){
		$destination = $_GET['destination'];
	}
	
	if(isset($_GET['location'])){
		$location = $_GET['location'];
	}
	
	if(isset($_GET['order'])){
		if($_GET['order'] == "ta"){
			$order = "ASC";
		}
		elseif ($_GET['order'] == "td") {
			$order = "DESC";
		}
	}
	else {
		$order = "ASC";
	}
	
	//Get data from database
	if($location) {
		//$result = json_encode($db->getResult("SELECT * FROM hitch_hitchhikestatus WHERE userID=? ORDER BY LIMIT ".$limit, array($user_id)));
	}
	else {
		$result = $db->getResult("SELECT * FROM hitch_hitchhikestatus WHERE userID=? ORDER BY timestamp ".$order." LIMIT ".$limit, array($user_id));
	}
	
	//Check result. If empty - error, else return result.
	if(empty($result)) {
		throwError('Result Empty', 403);
	}
	else {
		echo json_encode($result);
	}
	
?>