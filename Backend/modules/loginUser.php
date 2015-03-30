<?php
	/*
		Hitch back-end module.
		
		Throw an error:
		throwError( message, errorCode );
		
		Database object:
		$db
		
		Module: LoginUser
		Input parameters:
			phoneNumber: The user's phone number
		
		Output parameters;
			userID: The user’s ID.
	*/

	//Check if all required input paramters are set
	if (!isset($_GET['phoneNumber'])) {
		throwError('Missing required parameters');	
	}

	//Input parameters
	$phoneNr = $_GET['phoneNumber'];
		
	//RETURN OUTPUT DATA AS JSON	
	$result = $db->getRow("SELECT userID FROM hitch_users WHERE phoneNumber=?", array($phoneNr));
	
	if ($result == false) {
		echo '{ "userID" : -1 }';
	} else {
		echo '{ "userID" : '.$result->userID.' }';
	}
?>