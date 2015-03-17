<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: RemoveUserHitchhikeData
		Input parameters:
			userID: The ID of the user hitchhiking.

		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID'])) {
		throwError('Missing required parameters');
	}
	
	//Input parameters
	$user_id = $_GET['userID'];
	
	//Check if the user exists
	$user = $db->getRow("SELECT userID FROM hitch_users WHERE userID=?", array($user_id));
	if ($user == false) {
		throwError('User not found.');
	}
	
	//Update database	
	$db->executeQuery("UPDATE hitch_hitchhikestatus SET timestamp='1990-01-01 0:00' WHERE userID=?", array($user_id));

	//Output
	echo '{}';		
?>