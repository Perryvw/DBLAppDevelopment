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
	
	//Get data from database
	$db->executeQuery("DELETE FROM hitch_hitchhikestatus WHERE userID=?", array($user_id));
		
?>