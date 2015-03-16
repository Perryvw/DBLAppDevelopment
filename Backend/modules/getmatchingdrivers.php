<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: GetMatchingDrivers
		Input parameters:
			hitchhikerID: The user ID of the hitchhiker.
			amount: The amount of drivers to return. (Optional, default = 5).

		Output parameters:
			drivers: An array of objects representing drivers, each containing the following fields:
				userID: The driver’s user ID.
				routeID: The routeID matched to the hitchhiker
				relevance: The internal score assigned to the match.

	*/
	
	//Check if required parameters are set
	if(!isset($_GET['hitchhikerID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['hitchhikerID'];
	if(isset($_GET['amount'])){
		$amount = $_GET['amount'];
	}
	else {
		$amount = 5;
	}
	
	//Get data from database
	
	// WHAT ABOUT RELEVANCE?
	
	
	$result = json_encode($db->getResult("SELECT * FROM hitch_matches WHERE hitchhikerID=? LIMIT".$amount, array($user_id)));
	
	echo $result;
?>