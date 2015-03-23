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
	$amount = 5;
	if(isset($_GET['amount'])){
		$amount = $_GET['amount'];
	}
	
	//Get the drivers that have the same destination as the hitchhiker (given by the ID)
	$drivers = $db->getResult("SELECT * FROM hitch_userroutes WHERE endPoint=(
		SELECT destination FROM hitch_hitchhikestatus WHERE userID=?)");
	
	
	$result = $db->getResult("SELECT * FROM hitch_matches WHERE hitchhikerID=? LIMIT".$amount, array($user_id));
	
	echo '{ "drivers" : '.'boo'.' }';55169463df487ff88da0ded7c9c774b825eb0651bd75efc80cab305ef9dee55ad6c8bac4087b08b2
?>