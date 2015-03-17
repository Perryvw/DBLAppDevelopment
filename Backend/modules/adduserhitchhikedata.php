<?php
	/*
		Hitch back-end module.
		
		Throw an error throwError ( message, errorCode0 );
		
		Database object:
			$db
		
		Module: AddUserHitchhikeData
		Input parameters:
			userID: The ID of the user hitchhiking.
			location: The location the hitchhiker is at.
			destination: The destination of the hitchhiker.
			timestamp: The time this data was placed on the server.

		Output parameters:
			-
			
	*/
	
	//Check if required parameters are set
	if(!isset($_GET['userID']) || !isset($_GET['location']) || !isset($_GET['destination']) || !isset($_GET['timestamp'])) {
		throwError('Missing required parameters');
	}

	//Set the timezone since we're using dates
	date_default_timezone_set('Europe/London');
	
	//Input parameters
	$user_id = $_GET['userID'];
	$location = $_GET['location'];
	$destination = $_GET['destination'];
	$timestamp = date('Y-m-d H:i:s', $_GET['timestamp']);
	
	//Get data from database
	$db->insertRow('hitch_hitchhikestatus', array($user_id, $timestamp, $location, $destination));
?>