<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetUserHitchhikeData
		Input parameters:
			userID: The ID of the queried user.

		Output parameters:
			location: The location the hitchhiker is at
			destination: The destination of the hitchhiker
			timestamp: The time this data was placed on the server
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	
	//Get data from database
	$result = json_encode($db->getResult("SELECT timestamp, location, destination FROM hitch_hitchhikestatus WHERE userID=?", array($user_id)));
	
	echo $result;
?>