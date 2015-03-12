<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetHitchhikeMatches
		Input parameters:
			driverID: The user ID of the hitchhiker.
			timeWindow: The amount of seconds since the match was made. (Optional, default = 7200).

		Output parameters:
			matches: An array of objects representing drivers, each containing the following fields:
				userID: The hitchhiker’s user ID.
				routeID: The routeID matched to the hitchhiker
				relevance: The internal score assigned to the match.
				timestamp: A timestamp of when the match was made.

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	if(isset($_GET['timeWindow'])) {
		$time = time() - $_GET['timeWindow'];
	}
	else {
		$time = time() - 7200;
	}
	
	//Get data from database
	$result = json_encode();
?>